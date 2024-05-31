# JCA-DAO

Projet de creation d'un template DAO pour faciliter la connection et la manipulation des requetes sgbd en JAVA

## Consignes

- Ce dao utilise l'avantage de JAVA-PROJECT de vscode avec la structure des dossiers , donc il faut mettre les librairies .jar de votre base de donnee dans le dossier lib du projet ou dossier equivalentes
- La librairie jar du framework doit etre inclue dans le lib du projet

## Obtenir le jar

Pour obtenir la version librairie jar du DAO vous devez simplement executer le script **jarify** delivrer avec le projet JCA-DAO qui correspond a votre OS

- **WINDOWS** : jarify.bat
- **LINUX** : jarify.sh

## Fonctionalites

### Creation de connection

- Creation d'une **ConnConfig** , qui contiendra les configuration necessaire a la connection tel que **( driver , database , host , port , dbname , username , password )**.
Selon le SGBD utiliser il existe des configurations minimal si il est parmi la liste suivante mais assurer vous d'integrer la libriraire du sgbd dans le projet:
  - postgresql
- Creation de Connection avec la class **ConnBuilder** , vous devez passer la configuration ConnConfig dans le constructeur

### Implementation des annotations

  | SQL Element | Java Target | Annotaion |
  |:-------------:|:-------------:|:-----------:|
  | Table       | Class       | jca.dao.models.annotations.EntityModels|
  | Colonne     | Attribut (Variable de class)    | jca.dao.models.annotations.Attribute|

### Requests

- **findAll** : Donner un objet qui implemente l'annotation d'une Table
- **insertEntity** : Insertion de donner dans une table
