# E-Shop Backend Service

TypeScript backend service providing REST API for product management.

## Features

- RESTful API for CRUD operations
- SQLite database for persistence
- CORS enabled for Mini App
- Input validation
- Error handling

## Installation

```bash
npm install
```

## Development

```bash
npm run watch  # Start with auto-reload
```

## Production

```bash
npm run build  # Compile TypeScript
npm start      # Start server
```

## API Endpoints

### GET /health
Health check endpoint

### GET /api/products
Get all products

**Response:**
```json
[
  {
    "id": 1,
    "name": "Product Name",
    "description": "Product description",
    "price": 99.99,
    "stock": 10,
    "image_url": "https://...",
    "created_at": "2023-01-01 00:00:00",
    "updated_at": "2023-01-01 00:00:00"
  }
]
```

### GET /api/products/:id
Get product by ID

### POST /api/products
Create new product

**Request Body:**
```json
{
  "name": "Product Name",
  "description": "Product description",
  "price": 99.99,
  "stock": 10,
  "image_url": "https://..." // optional
}
```

### PUT /api/products/:id
Update product (partial update supported)

**Request Body:**
```json
{
  "price": 79.99,
  "stock": 5
}
```

### DELETE /api/products/:id
Delete product

## Environment Variables

- `PORT` - Server port (default: 3000)

## Database

SQLite database (`products.db`) is created automatically on first run.

### Schema

```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    price REAL NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    image_url TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

