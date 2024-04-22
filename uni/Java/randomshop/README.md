## Hello :)

Please download from here -> https://github.com/dgudim2/obsidian_vault

## How to download without a bunch of junk

git clone https://github.com/dgudim2/obsidian_vault --depth=1
cd obsidian_vault
git sparse-checkout init
git config --worktree core.sparseCheckoutCone false
git sparse-checkout set uni/Java/randomshop

> After this you should only have 1 folder (obsidian_vault/uni/Java/randomshop/) this is the project

Default user: admin
Default password: admin123

start the server with the '--mode server' cli arg, gui is started by default with no arguments
