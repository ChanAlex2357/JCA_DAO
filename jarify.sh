#!/bin/bash
# Vérifier si l'utilisateur a fourni le nom du fichier JAR en tant qu'argument
if [ -z "$1" ]; then
    # Demander le nom du fichier JAR à l'utilisateur
    read -p "Entrez le nom du fichier JAR (par exemple, jca-spring-framework.jar) : " JAR_NAME
else
    # Utiliser l'argument fourni comme nom du fichier JAR
    JAR_NAME="$1"
fi
# Chemin vers le dossier source
SOURCE_DIR="./src"
# Chemin vers le dossier de destination du JAR
OUTPUT_DIR="./dist"
# Le dossier des fichiers compilee
BIN_DIR="./bin"
README="README.md"

# Supprimer le dossier de destination s'il existe déjà
if [ -d "$OUTPUT_DIR" ]; then
  rm -rf $OUTPUT_DIR
fi
# Copier les fichier compiles
cp -R $BIN_DIR $OUTPUT_DIR 

# Copier le readme dans la distribution
cp -R $README $OUTPUT_DIR

# Créer le fichier JAR
jar cf $JAR_NAME -C $OUTPUT_DIR .

echo "Le framework a été déployé avec succès sous forme de fichier JAR."
