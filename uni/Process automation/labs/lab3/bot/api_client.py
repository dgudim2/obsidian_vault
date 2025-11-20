import requests
from typing import Any
from config import API_BASE_URL


class APIClient:
    """Client for interacting with the backend API"""
    
    def __init__(self, base_url: str = API_BASE_URL):
        self.base_url = base_url
    
    def get_products(self) -> list[dict[str, Any]]:
        """Get all products"""
        try:
            response = requests.get(f"{self.base_url}/products")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Error fetching products: {e}")
            return []
    
    def get_product(self, product_id: int) -> dict[str, Any] | None:
        """Get a specific product by ID"""
        try:
            response = requests.get(f"{self.base_url}/products/{product_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Error fetching product {product_id}: {e}")
            return None
    
    def create_product(self, name: str, description: str, price: float, stock: int, image_url: str = "") -> dict[str, Any] | None:
        """Create a new product"""
        try:
            data = {
                "name": name,
                "description": description,
                "price": price,
                "stock": stock,
                "image_url": image_url
            }
            response = requests.post(f"{self.base_url}/products", json=data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Error creating product: {e}")
            return None
    
    def update_product(self, product_id: int, **kwargs) -> dict[str, Any] | None:
        """Update a product"""
        try:
            response = requests.put(f"{self.base_url}/products/{product_id}", json=kwargs)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Error updating product {product_id}: {e}")
            return None
    
    def delete_product(self, product_id: int) -> bool:
        """Delete a product"""
        try:
            response = requests.delete(f"{self.base_url}/products/{product_id}")
            response.raise_for_status()
            return True
        except Exception as e:
            print(f"Error deleting product {product_id}: {e}")
            return False

