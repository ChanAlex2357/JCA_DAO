@echo off
REM Vérifier si l'utilisateur a fourni le nom du fichier JAR en tant qu'argument
if "%1"=="" (
    REM Demander le nom du fichier JAR à l'utilisateur
    set /p NAME="Entrez le nom du fichier JAR :"
    echo "%NAME%"
    set JAR_NAME="%NAME%".jar
) else (
    REM Utiliser l'argument fourni comme nom du fichier JAR
    set JAR_NAME=%1.jar
)

REM Chemin vers le dossier source
set SOURCE_DIR=.\src
REM Chemin vers le dossier de destination du JAR
set OUTPUT_DIR=.\dist
REM Le dossier des fichiers compilés
set BIN_DIR=.\bin

set README=README.md

REM Supprimer le dossier de destination s'il existe déjà
if exist "%OUTPUT_DIR%" (
  rmdir /s /q "%OUTPUT_DIR%"
)

REM Copier les fichiers compilés
xcopy /E /I /Q "%BIN_DIR%" "%OUTPUT_DIR%"

REM Copier le readme dans la distribution
xcopy /Y "%README%" "%OUTPUT_DIR%"

REM Créer le fichier JAR
jar cf "%JAR_NAME%" -C "%OUTPUT_DIR%" .

echo Le framework a ete deploye avec succes sous forme de fichier JAR.