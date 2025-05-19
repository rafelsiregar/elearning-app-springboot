#!/bin/bash

# Create a temporary directory for the export
mkdir -p temp_export

# Copy necessary files and directories
cp -r \
  backend/src \
  backend/pom.xml \
  src \
  public \
  .gitignore \
  README.md \
  package.json \
  package-lock.json \
  tsconfig.json \
  next.config.ts \
  postcss.config.mjs \
  components.json \
  temp_export/

# Create the zip file
cd temp_export
zip -r ../elearning-project.zip ./*

# Clean up
cd ..
rm -rf temp_export

echo "Project has been exported to elearning-project.zip"
