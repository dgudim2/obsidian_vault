
```toc
```

## Overview

The system has 2 parts:

**Telegram Bot** - Written in Python using the python-telegram-bot library.

**Backend API** - Handles all data operations, connects to the database. Written in TypeScript. Bot connects using REST. The database is SQLite and stores all product information (name, description, price, stock, images)

### Data flow 

When a user adds a product, the bot asks and gets the information, sends it to the backend, which then puts it into the database. Same goes for updating, deleting and listing

---

## Bot

### Main Menu

When users start the bot with `/start`, they see a main menu with five options:
- ➕ Add Product
- 📝 Edit Product  
- 📋 List Products
- 🔍 View Product
- 🗑️ Delete Product

The menu uses inline keyboard buttons for easy navigation.

![[Pasted image 20251121195416.png|400]]

### Adding Products

To add a new product, the bot asks the user for information step by step:
1. Product name
2. Description
3. Price
4. Stock quantity
5. Image URL (optional)

The bot checks that prices and stock are valid numbers and not negative. 
If the user enters something wrong, the bot asks them to try again.

![[Pasted image 20251121195700.png|400]]

After successfully adding a product, the bot shows a success sticker and displays a summary of the new product.

![[Pasted image 20251121195914.png|300]]

### Viewing Products

**List All Products** - Shows a list of all products.

![[Pasted image 20251121200222.png|300]]

**View Product Details** - Users can select a specific product to see the details.

![[Pasted image 20251121200258.png|300]]

### Editing Products

The edit feature works in two steps:
1. User selects which product to edit
2. User chooses which field to change

The bot then asks for the new value and validates it before saving.

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20251121214616.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20251121214514.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20251121214529.png]]

```` 
`````

### Deleting Products

Before deleting a product, the bot shows a confirmation message. The user must click "Yes, Delete" to confirm or "No, Cancel" to go back.

![[Pasted image 20251121214800.png|300]]


---

## Backend

### API Endpoints

The backend provides 5 API endpoints for product operations:
- Get all products
- Get one product by ID
- Create new product
- Update existing product
- Delete product

### Database

The SQLite database stores products with these fields:
- ID (unique identifier)
- Name
- Description
- Price
- Stock quantity
- Image URL
- Created date
- Updated date

The database is created automatically when the backend starts for the first time.

### Mini Web App

The project also includes a mini web application that can be opened directly from Telegram. 
This provides an alternative way to browse products.


