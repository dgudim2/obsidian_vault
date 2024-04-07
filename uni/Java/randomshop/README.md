## Hello :)

Please donwnload from here -> https://github.com/dgudim2/obsidian_vault

## How to download without a bunch of junk

git clone https://github.com/dgudim2/obsidian_vault --filter=blob:none
cd obsidian_vault
git sparse-checkout init
git config --worktree core.sparseCheckoutCone false
git sparse-checkout set uni/Java/randomshop

> After this you should only have 1 folder (obsidian_vault/uni/Java/randomshop/) this is the project
