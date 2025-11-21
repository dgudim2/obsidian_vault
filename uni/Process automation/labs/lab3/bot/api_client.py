import requests
from typing import Any
from config import API_BASE_URL


class APIClient:
    def __init__(self, base_url: str = API_BASE_URL):
        self.base_url = base_url

    def req[T](self, response: requests.Response) -> T:
        response.raise_for_status()
        return response.json()

    def get_products(self) -> list[dict[str, Any]]:
        try:
            return self.req(requests.get(f"{self.base_url}/products"))
        except Exception as e:
            print(f"Error fetching products: {e}")
            return []

    def get_product(self, product_id: int) -> dict[str, Any]:
        try:
            return self.req(requests.get(f"{self.base_url}/products/{product_id}"))
        except Exception as e:
            print(f"Error fetching product {product_id}: {e}")
            return {}

    def create_product(
        self, name: str, description: str, price: float, stock: int, image_url: str = ""
    ) -> dict[str, Any]:
        try:
            data = {"name": name, "description": description, "price": price, "stock": stock, "image_url": image_url}
            return self.req(requests.post(f"{self.base_url}/products", json=data))
        except Exception as e:
            print(f"Error creating product: {e}")
            return {}

    def update_product(self, product_id: int, **kwargs) -> dict[str, Any]:
        try:
            return self.req(requests.put(f"{self.base_url}/products/{product_id}", json=kwargs))
        except Exception as e:
            print(f"Error updating product {product_id}: {e}")
            return {}

    def delete_product(self, product_id: int) -> bool:
        try:
            response = requests.delete(f"{self.base_url}/products/{product_id}")
            response.raise_for_status()
            return True
        except Exception as e:
            print(f"Error deleting product {product_id}: {e}")
            return False
