#!/bin/bash

# Check if input arguments are provided
if [ $# -ne 2 ]; then
    echo "Usage: $0 <yaml_file> <new_id>"
    exit 1
fi

# Input parameters
yaml_file=$1
new_id=$2

# Check if the file exists
if [ ! -f "$yaml_file" ]; then
    echo "Error: File $yaml_file not found."
    exit 1
fi

# Update the id field in the YAML file
sed -i '' "s#^id: .*#id: \"$new_id\"#" "$yaml_file"

echo "ID updated to: $new_id"
