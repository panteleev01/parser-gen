#!bin/bash

for input in *.txt; do
    if [ -f "$input" ]; then
        output="${input%.txt}.png"
        dot -Tpng "$input" -o "$output"
    fi
done
