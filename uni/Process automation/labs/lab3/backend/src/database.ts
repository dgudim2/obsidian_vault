import sqlite3 from 'sqlite3';

const db = new sqlite3.Database('./products.db');

// Promisify database methods with proper typing
const dbRun = (sql: string, params?: any[]): Promise<sqlite3.RunResult> => {
  return new Promise((resolve, reject) => {
    db.run(sql, params || [], function(err) {
      if (err) reject(err);
      else resolve(this);
    });
  });
};

const dbGet = (sql: string, params?: any[]): Promise<any> => {
  return new Promise((resolve, reject) => {
    db.get(sql, params || [], (err, row) => {
      if (err) reject(err);
      else resolve(row);
    });
  });
};

const dbAll = (sql: string, params?: any[]): Promise<any[]> => {
  return new Promise((resolve, reject) => {
    db.all(sql, params || [], (err, rows) => {
      if (err) reject(err);
      else resolve(rows);
    });
  });
};

export interface Product {
  id?: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  image_url?: string;
  created_at?: string;
  updated_at?: string;
}

// Initialize database schema
export async function initDatabase(): Promise<void> {
  await dbRun(`
    CREATE TABLE IF NOT EXISTS products (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL,
      description TEXT,
      price REAL NOT NULL,
      stock INTEGER NOT NULL DEFAULT 0,
      image_url TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `);
  
  console.log('Database initialized successfully');
}

// CRUD operations
export async function getAllProducts(): Promise<Product[]> {
  return await dbAll('SELECT * FROM products ORDER BY created_at DESC') as Product[];
}

export async function getProductById(id: number): Promise<Product | undefined> {
  return await dbGet('SELECT * FROM products WHERE id = ?', [id]) as Product | undefined;
}

export async function createProduct(product: Product): Promise<number> {
  const result = await dbRun(
    'INSERT INTO products (name, description, price, stock, image_url) VALUES (?, ?, ?, ?, ?)',
    [product.name, product.description, product.price, product.stock, product.image_url || null]
  );
  return result.lastID;
}

export async function updateProduct(id: number, product: Partial<Product>): Promise<boolean> {
  const fields: string[] = [];
  const values: any[] = [];
  
  if (product.name !== undefined) {
    fields.push('name = ?');
    values.push(product.name);
  }
  if (product.description !== undefined) {
    fields.push('description = ?');
    values.push(product.description);
  }
  if (product.price !== undefined) {
    fields.push('price = ?');
    values.push(product.price);
  }
  if (product.stock !== undefined) {
    fields.push('stock = ?');
    values.push(product.stock);
  }
  if (product.image_url !== undefined) {
    fields.push('image_url = ?');
    values.push(product.image_url);
  }
  
  if (fields.length === 0) {
    return false;
  }
  
  fields.push('updated_at = CURRENT_TIMESTAMP');
  values.push(id);
  
  const result = await dbRun(
    `UPDATE products SET ${fields.join(', ')} WHERE id = ?`,
    values
  );
  
  return result.changes > 0;
}

export async function deleteProduct(id: number): Promise<boolean> {
  const result = await dbRun('DELETE FROM products WHERE id = ?', [id]);
  return result.changes > 0;
}

