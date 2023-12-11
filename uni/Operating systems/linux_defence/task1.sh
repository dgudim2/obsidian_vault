#!/bin/bash

RED='\033[0;31m'
L_RED='\033[1;31m'
GREEN='\033[0;32m'
L_GREEN='\033[1;32m'
YELLOW='\033[0;33m'
L_YELLOW='\033[1;33m'
BLUE='\033[0;34m'
L_BLUE='\033[1;34m'
PURPLE='\033[0;35m'
L_PURPLE='\033[1;35m'
CYAN='\033[0;36m'
L_CYAN='\033[1;36m'
GRAY='\033[1;30m'
NC='\033[0m'

if [ $# -eq 0 ]; then echo "Usage: $0 <file.txt>"; exit 1; fi

file="$1"

if [ ! -f "$file" ]; then echo -e "${L_RED}File $file not found.$NC"; exit 1; fi

letter() {
    echo "Enter the letter to search for:"
    read letter
    echo -e "Lines containing the letter '${L_YELLOW}$letter$NC':"
    grep "$letter" "$file"
}

phones() {
    echo -e "Phone numbers matching the format ${L_YELLOW}x - xxx - xxxxx:$NC"
    grep -E "\b[0-9] - [0-9]{3} - [0-9]{5}\b" "$file"
}

name() {
    echo "Enter the name to count:"
    read name
    count=$(grep -c "$name" "$file")
    echo -e "Number of lines containing '${L_YELLOW}$name$NC': ${L_PURPLE}$count$NC"
}

#prints this menu till user exit program
while true; do
    echo -e "${L_YELLOW}=======Menu=======${NC}"
    echo "1. Print lines containing a specific letter"
    echo "2. Print phone numbers matching format x - xxx - xxxxx"
    echo "3. Count occurrences of a name"
    echo -e "4. ${RED}Exit${NC}"
    read -p "Enter an option (1-4): " choice

    case $choice in
        1) letter ;;
        2) phones ;;
        3) name ;;
        4) exit ;;
        *) echo -e "${L_RED}Invalid choice$NC" ;;
    esac
done

