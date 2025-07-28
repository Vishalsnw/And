#!/bin/bash
SRC_DIR="app/src/main/java/com/example/goalguru"

find "$SRC_DIR" -name "*.kt" | while read file; do
    echo "🔍 Checking: $file"
    WILDCARDS=$(grep -E '^import .*\.\*$' "$file" || true)

    if [ -z "$WILDCARDS" ]; then
        echo "✅ No wildcard imports in $file"
        continue
    fi

    IMPORTS=""
    echo "$WILDCARDS" | while read import_line; do
        base_pkg=$(echo "$import_line" | sed -E 's/import (.*)\.\*/\1/')
        used_classes=$(grep -oP "${base_pkg//./\\.}\\.[A-Z][A-Za-z0-9_]*" "$file" | sort | uniq | sed "s|$base_pkg\.||g")

        echo "🚫 Removing wildcard: $import_line"
        sed -i "\|$import_line|d" "$file"

        for cls in $used_classes; do
            IMPORTS+="import $base_pkg.$cls"$'\n'
            echo "✅ Adding import $base_pkg.$cls"
        done
    done

    if [ -n "$IMPORTS" ]; then
        tmpfile=$(mktemp)
        awk -v new_imports="$IMPORTS" '
        BEGIN { done=0 }
        {
            print $0
            if (!done && /^package /) {
                print ""
                print new_imports
                done=1
            }
        }' "$file" > "$tmpfile"
        mv "$tmpfile" "$file"
    fi
done
