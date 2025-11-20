import os
from dotenv import load_dotenv

load_dotenv()

BOT_TOKEN = os.getenv('BOT_TOKEN', '')
ADMIN_USER_ID = os.getenv('ADMIN_USER_ID', '')

API_BASE_URL = 'http://localhost:3000/api'
MINI_APP_URL = 'https://black-bird.neko-ionian.ts.net/miniapp.html'

# Validate Mini App URL
if MINI_APP_URL and not MINI_APP_URL.startswith('https://'):
    print(f"Mini App URL must use HTTPS. Got: {MINI_APP_URL}")
    MINI_APP_URL = ''  # Disable invalid URL

# Stickers
SUCCESS_STICKER = "https://data.chpic.su/stickers/o/OkeyStickersss/OkeyStickersss_001.webp"
FAIL_STICKER = "https://cdn2.combot.org/line219970325b3e_by_moe_sticker_bot/webp/6xe2ad90.webp"

if not BOT_TOKEN:
    raise ValueError("BOT_TOKEN is required")

