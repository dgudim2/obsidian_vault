import logging
from telegram import Update, InlineKeyboardButton, InlineKeyboardMarkup, WebAppInfo
from telegram.ext import (
    Application,
    CommandHandler,
    CallbackQueryHandler,
    ConversationHandler,
    MessageHandler,
    ContextTypes,
    filters
)
from api_client import APIClient
from config import BOT_TOKEN, MINI_APP_URL, SUCCESS_STICKER, FAIL_STICKER

logging.basicConfig(
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    level=logging.INFO
)
logger = logging.getLogger(__name__)

api = APIClient()

(
    MAIN_MENU,
    ADD_NAME, ADD_DESCRIPTION, ADD_PRICE, ADD_STOCK, ADD_IMAGE,
    EDIT_SELECT, EDIT_FIELD, EDIT_VALUE,
    DELETE_CONFIRM,
    VIEW_PRODUCT
) = range(11)


async def start(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    await show_main_menu(update, context)
    return MAIN_MENU


async def show_main_menu(update: Update, context: ContextTypes.DEFAULT_TYPE, message_text: str = "🛍️ *E-Shop Product Manager*\n\nWhat would you like to do?") -> None:

    keyboard = [
        [InlineKeyboardButton("➕ Add Product", callback_data="add")],
        [InlineKeyboardButton("📝 Edit Product", callback_data="edit")],
        [InlineKeyboardButton("📋 List Products", callback_data="list")],
        [InlineKeyboardButton("🔍 View Product", callback_data="view")],
        [InlineKeyboardButton("🗑️ Delete Product", callback_data="delete")],
    ]
    
    # Only add Mini App button if valid HTTPS URL is configured
    if MINI_APP_URL and MINI_APP_URL.startswith('https://'):
        keyboard.append([InlineKeyboardButton("🛒 Open Mini App", web_app=WebAppInfo(url=MINI_APP_URL))])
    
    reply_markup = InlineKeyboardMarkup(keyboard)
    
    if update.callback_query:
        await update.callback_query.edit_message_text(
            message_text,
            reply_markup=reply_markup,
            parse_mode='Markdown'
        )
    else:
        await update.message.reply_text(
            message_text,
            reply_markup=reply_markup,
            parse_mode='Markdown'
        )


async def button_handler(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    """Handle inline keyboard button presses"""
    query = update.callback_query
    await query.answer()
    
    if query.data == "add":
        return await start_add_product(update, context)
    elif query.data == "edit":
        return await start_edit_product(update, context)
    elif query.data == "list":
        return await list_products(update, context)
    elif query.data == "view":
        return await start_view_product(update, context)
    elif query.data == "delete":
        return await start_delete_product(update, context)
    elif query.data == "cancel":
        await show_main_menu(update, context)
        return MAIN_MENU
    
    return MAIN_MENU


# ===== ADD PRODUCT =====
async def start_add_product(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    query = update.callback_query
    await query.edit_message_text("➕ *Add New Product*\n\nPlease enter the product name:", parse_mode='Markdown')
    context.user_data.clear()
    return ADD_NAME


async def add_name(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    context.user_data['name'] = update.message.text
    await update.message.reply_text("Enter the product description:")
    return ADD_DESCRIPTION


async def add_description(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    context.user_data['description'] = update.message.text
    await update.message.reply_text("Now enter the price (e.g., 19.99):")
    return ADD_PRICE


async def add_price(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    try:
        price = float(update.message.text)
        if price < 0:
            await update.message.reply_text("❌ Price cannot be negative. Please enter a valid price:")
            return ADD_PRICE
        context.user_data['price'] = price
        await update.message.reply_text("Enter the stock quantity:")
        return ADD_STOCK
    except ValueError:
        await update.message.reply_text("❌ Invalid price format. Please enter a number (e.g., 19.99):")
        return ADD_PRICE


async def add_stock(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    try:
        stock = int(update.message.text)
        if stock < 0:
            await update.message.reply_text("❌ Stock cannot be negative. Please enter a valid quantity:")
            return ADD_STOCK
        context.user_data['stock'] = stock
        
        keyboard = [[InlineKeyboardButton("Skip Image URL", callback_data="skip_image")]]
        reply_markup = InlineKeyboardMarkup(keyboard)

        await update.message.reply_text(
            "Enter an image URL (or click Skip):",
            reply_markup=reply_markup
        )
        return ADD_IMAGE
    except ValueError:
        await update.message.reply_text("❌ Invalid stock format. Please enter a whole number:")
        return ADD_STOCK


async def add_image(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    if update.callback_query:
        await update.callback_query.answer()
        image_url = ""
        message = update.callback_query.message
    else:
        image_url = update.message.text
        message = update.message
    
    product = api.create_product(
        name=context.user_data['name'],
        description=context.user_data['description'],
        price=context.user_data['price'],
        stock=context.user_data['stock'],
        image_url=image_url
    )
    
    # Create a fake update to force show_main_menu to send a new message
    class FakeUpdate:
        def __init__(self, msg):
            self.message = msg
            self.callback_query = None

    if product:
        await message.reply_sticker(SUCCESS_STICKER)
        await message.reply_text(
            f"✅ *Product Created Successfully!*\n\n"
            f"*Name:* {product['name']}\n"
            f"*Description:* {product['description']}\n"
            f"*Price:* ${product['price']:.2f}\n"
            f"*Stock:* {product['stock']}\n"
            f"*ID:* {product['id']}",
            parse_mode='Markdown'
        )
        
        fake_update = FakeUpdate(message)
        await show_main_menu(fake_update, context)
    else:
        await message.reply_sticker(FAIL_STICKER)
        await message.reply_text("❌ Failed to create product. Please try again.")
        
        
        fake_update = FakeUpdate(message)
        await show_main_menu(fake_update, context)
    
    context.user_data.clear()
    return MAIN_MENU


# ===== LIST PRODUCTS =====
async def list_products(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    query = update.callback_query
    products = api.get_products()
    
    if not products:
        await query.edit_message_text(
            "📋 *Product List*\n\n_No products found._",
            parse_mode='Markdown'
        )
    else:
        message = "📋 *Product List*\n\n"
        for product in products:
            message += (
                f"🆔 *ID:* {product['id']}\n"
                f"📦 *Name:* {product['name']}\n"
                f"💰 *Price:* ${product['price']:.2f}\n"
                f"📊 *Stock:* {product['stock']}\n"
                f"{'─' * 30}\n\n"
            )
        
        await query.edit_message_text(message, parse_mode='Markdown')
    
    keyboard = [[InlineKeyboardButton("« Back to Menu", callback_data="cancel")]]
    reply_markup = InlineKeyboardMarkup(keyboard)
    await query.message.reply_text("Choose an action:", reply_markup=reply_markup)
    
    return MAIN_MENU


# ===== VIEW PRODUCT =====
async def start_view_product(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    query = update.callback_query
    products = api.get_products()
    
    if not products:
        await query.edit_message_text("❌ No products available to view.")
        await show_main_menu(update, context)
        return MAIN_MENU
    
    keyboard = []
    for product in products:
        keyboard.append([InlineKeyboardButton(
            f"{product['name']} (ID: {product['id']})",
            callback_data=f"view_{product['id']}"
        )])
    keyboard.append([InlineKeyboardButton("« Cancel", callback_data="cancel")])
    reply_markup = InlineKeyboardMarkup(keyboard)
    
    await query.edit_message_text(
        "🔍 *View Product*\n\nSelect a product to view:",
        reply_markup=reply_markup,
        parse_mode='Markdown'
    )
    return VIEW_PRODUCT


async def view_product_details(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    """Display product details"""
    query = update.callback_query
    await query.answer()
    
    if query.data == "cancel":
        await show_main_menu(update, context)
        return MAIN_MENU
    
    product_id = int(query.data.split("_")[1])
    product = api.get_product(product_id)
    
    if product:
        message = (
            f"🔍 *Product Details*\n\n"
            f"🆔 *ID:* {product['id']}\n"
            f"📦 *Name:* {product['name']}\n"
            f"📝 *Description:* {product['description']}\n"
            f"💰 *Price:* ${product['price']:.2f}\n"
            f"📊 *Stock:* {product['stock']}\n"
        )
        if product.get('image_url'):
            message += f"🖼️ *Image:* {product['image_url']}\n"
        
        message += f"\n📅 *Created:* {product.get('created_at', 'N/A')}"
        
        await query.edit_message_text(message, parse_mode='Markdown')
    else:
        await query.edit_message_text("❌ Product not found.")
    
    keyboard = [[InlineKeyboardButton("« Back to Menu", callback_data="cancel")]]
    reply_markup = InlineKeyboardMarkup(keyboard)
    await query.message.reply_text("Choose an action:", reply_markup=reply_markup)
    
    return MAIN_MENU


# ===== EDIT PRODUCT =====
async def start_edit_product(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    """Start editing a product"""
    query = update.callback_query
    products = api.get_products()
    
    if not products:
        await query.edit_message_text("❌ No products available to edit.")
        await show_main_menu(update, context)
        return MAIN_MENU
    
    keyboard = []
    for product in products:
        keyboard.append([InlineKeyboardButton(
            f"{product['name']} (ID: {product['id']})",
            callback_data=f"edit_{product['id']}"
        )])
    keyboard.append([InlineKeyboardButton("« Cancel", callback_data="cancel")])
    reply_markup = InlineKeyboardMarkup(keyboard)
    
    await query.edit_message_text(
        "📝 *Edit Product*\n\nSelect a product to edit:",
        reply_markup=reply_markup,
        parse_mode='Markdown'
    )
    return EDIT_SELECT


async def edit_select_field(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    query = update.callback_query
    await query.answer()
    
    if query.data == "cancel":
        await show_main_menu(update, context)
        return MAIN_MENU
    
    product_id = int(query.data.split("_")[1])
    context.user_data['edit_product_id'] = product_id
    
    product = api.get_product(product_id)
    if not product:
        await query.edit_message_text("❌ Product not found.")
        await show_main_menu(update, context)
        return MAIN_MENU
    
    keyboard = [
        [InlineKeyboardButton("Name", callback_data="field_name")],
        [InlineKeyboardButton("Description", callback_data="field_description")],
        [InlineKeyboardButton("Price", callback_data="field_price")],
        [InlineKeyboardButton("Stock", callback_data="field_stock")],
        [InlineKeyboardButton("Image URL", callback_data="field_image_url")],
        [InlineKeyboardButton("« Cancel", callback_data="cancel")]
    ]
    reply_markup = InlineKeyboardMarkup(keyboard)
    
    await query.edit_message_text(
        f"📝 *Editing: {product['name']}*\n\nSelect the field to edit:",
        reply_markup=reply_markup,
        parse_mode='Markdown'
    )
    return EDIT_FIELD


async def edit_ask_value(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:
    """Ask for new value"""
    query = update.callback_query
    await query.answer()
    
    if query.data == "cancel":
        await show_main_menu(update, context)
        return MAIN_MENU
    
    field = query.data.split("_")[1]
    if len(query.data.split("_")) > 2:
        field = "_".join(query.data.split("_")[1:])
    
    context.user_data['edit_field'] = field
    
    await query.edit_message_text(
        f"Please enter the new value for *{field}*:",
        parse_mode='Markdown'
    )
    return EDIT_VALUE


async def edit_save_value(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:

    product_id = context.user_data['edit_product_id']
    field = context.user_data['edit_field']
    value = update.message.text
    
    update_data = {}
    try:
        if field == "price":
            value = float(value)
            if value < 0:
                await update.message.reply_text("❌ Price cannot be negative. Please try again:")
                return EDIT_VALUE
            update_data['price'] = value
        elif field == "stock":
            value = int(value)
            if value < 0:
                await update.message.reply_text("❌ Stock cannot be negative. Please try again:")
                return EDIT_VALUE
            update_data['stock'] = value
        else:
            update_data[field] = value
    except ValueError:
        await update.message.reply_text(f"❌ Invalid value format. Please try again:")
        return EDIT_VALUE
    
    product = api.update_product(product_id, **update_data)
    
    if product:
        await update.message.reply_sticker(SUCCESS_STICKER)
        await update.message.reply_text(
            f"✅ *Product Updated Successfully!*\n\n"
            f"Updated field: *{field}*",
            parse_mode='Markdown'
        )
    else:
        await update.message.reply_sticker(FAIL_STICKER)
        await update.message.reply_text("❌ Failed to update product.")
    
    class CallbackQuery:
        def __init__(self, message):
            self.message = message
        async def edit_message_text(self, *args, **kwargs):
            await self.message.reply_text(*args, **kwargs)
    
    fake_update = type('obj', (object,), {
        'callback_query': CallbackQuery(update.message),
        'message': update.message
    })()
    
    await show_main_menu(fake_update, context)
    context.user_data.clear()
    return MAIN_MENU


# ===== DELETE PRODUCT =====
async def start_delete_product(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:

    query = update.callback_query
    products = api.get_products()
    
    if not products:
        await query.edit_message_text("❌ No products available to delete.")
        await show_main_menu(update, context)
        return MAIN_MENU
    
    keyboard = []
    for product in products:
        keyboard.append([InlineKeyboardButton(
            f"{product['name']} (ID: {product['id']})",
            callback_data=f"delete_{product['id']}"
        )])
    keyboard.append([InlineKeyboardButton("« Cancel", callback_data="cancel")])
    reply_markup = InlineKeyboardMarkup(keyboard)
    
    await query.edit_message_text(
        "🗑️ *Delete Product*\n\nSelect a product to delete:",
        reply_markup=reply_markup,
        parse_mode='Markdown'
    )
    return DELETE_CONFIRM


async def delete_confirm(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:

    query = update.callback_query
    await query.answer()
    
    if query.data == "cancel":
        await show_main_menu(update, context)
        return MAIN_MENU
    
    if query.data.startswith("delete_"):
        product_id = int(query.data.split("_")[1])
        product = api.get_product(product_id)
        
        if not product:
            await query.edit_message_text("❌ Product not found.")
            await show_main_menu(update, context)
            return MAIN_MENU
        
        keyboard = [
            [InlineKeyboardButton("✅ Yes, Delete", callback_data=f"confirm_delete_{product_id}")],
            [InlineKeyboardButton("❌ No, Cancel", callback_data="cancel")]
        ]
        reply_markup = InlineKeyboardMarkup(keyboard)
        
        await query.edit_message_text(
            f"⚠️ *Confirm Deletion*\n\n"
            f"Are you sure you want to delete:\n\n"
            f"*{product['name']}* (ID: {product['id']})\n\n"
            f"This action cannot be undone!",
            reply_markup=reply_markup,
            parse_mode='Markdown'
        )
        return DELETE_CONFIRM
    
    elif query.data.startswith("confirm_delete_"):
        product_id = int(query.data.split("_")[2])
        success = api.delete_product(product_id)
        
        if success:
            await query.edit_message_text("✅ *Product Deleted Successfully!*", parse_mode='Markdown')
        else:
            await query.edit_message_text("❌ Failed to delete product.")
        
        await show_main_menu(update, context)
        return MAIN_MENU
    
    return DELETE_CONFIRM


async def cancel(update: Update, context: ContextTypes.DEFAULT_TYPE) -> int:

    await update.message.reply_text("Operation cancelled.")
    await show_main_menu(update, context)
    context.user_data.clear()
    return MAIN_MENU


def main() -> None:

    application = Application.builder().token(BOT_TOKEN).build()
    
    conv_handler = ConversationHandler(
        entry_points=[CommandHandler("start", start)],
        states={
            MAIN_MENU: [CallbackQueryHandler(button_handler)],
            ADD_NAME: [MessageHandler(filters.TEXT & ~filters.COMMAND, add_name)],
            ADD_DESCRIPTION: [MessageHandler(filters.TEXT & ~filters.COMMAND, add_description)],
            ADD_PRICE: [MessageHandler(filters.TEXT & ~filters.COMMAND, add_price)],
            ADD_STOCK: [MessageHandler(filters.TEXT & ~filters.COMMAND, add_stock)],
            ADD_IMAGE: [
                MessageHandler(filters.TEXT & ~filters.COMMAND, add_image),
                CallbackQueryHandler(add_image, pattern="^skip_image$")
            ],
            EDIT_SELECT: [CallbackQueryHandler(edit_select_field)],
            EDIT_FIELD: [CallbackQueryHandler(edit_ask_value)],
            EDIT_VALUE: [MessageHandler(filters.TEXT & ~filters.COMMAND, edit_save_value)],
            DELETE_CONFIRM: [CallbackQueryHandler(delete_confirm)],
            VIEW_PRODUCT: [CallbackQueryHandler(view_product_details)],
        },
        fallbacks=[CommandHandler("cancel", cancel)],
    )
    
    application.add_handler(conv_handler)
    
    logger.info("Bot started successfully!")
    application.run_polling(allowed_updates=Update.ALL_TYPES)


if __name__ == '__main__':
    main()

