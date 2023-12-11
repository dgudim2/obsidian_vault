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

while true; do
    echo -e "${L_GREEN}=======Menu=======${NC}"
    echo -e "1. ${PURPLE}Files${NC}"
    echo -e "2. ${YELLOW}Random number generation${NC}"
    echo -e "3. ${RED}Exit${NC}"
    read -p "Select an option (1-3): " choice

    case $choice in
        1)
            while true; do
                read -p "Enter a file name (or 'Stop' to go back to the main menu): " filename
                
                if [ "$filename" == "Stop" ]; then break; fi
                
                if [ -x "$filename" ]; then
                    read -p "File $filename is executable. Do you want to execute it? (yes/no): " execute
                    if [ "$execute" == "yes" ]; then ./"$filename"; fi
                else
                    echo "${YELLOW}File $filename is not an executable.${NC}"
                fi
            done
            ;;
        2)
            echo -e "${YELLOW}Random number generation:${NC}"
            while true; do
                read -p "Enter lower limit: " min
                read -p "Enter upper limit: " max
                
                if [ "$min" -ge "$max" ]; then
                    echo -e "${L_RED}min > max, invalid range$NC"
                else
                    gen_number=$((RANDOM % ($max - $min + 1) + $min))
                    echo -e "${L_PURPLE}Generated number: $gen_number${NC}"
                    echo "$(date) | Range: $min-$max | Generated number: $gen_number" >> history.txt
                fi
                
                read -p "Do you want to continue generating numbers? (yes/no): " cont
                if [ "$cont" == "no" ]; then break; fi
            done
            ;;
        3)
            echo -e "${GRAY}Exiting the program...${NC}"
            exit ;;
        *)
            echo -e "${L_RED}Invalid choice${NC}" ;;
    esac
done
