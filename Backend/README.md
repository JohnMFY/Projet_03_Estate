# Estate Backend

Backend de l'application Estate développé avec Spring Boot.

L'API permet de gérer l'authentification des utilisateurs, les locations, les messages et le stockage des images avec AWS S3.

## Technologies utilisées

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Security
- JWT
- JPA / Hibernate
- PostgreSQL
- AWS S3
- Swagger / OpenAPI
- Maven

## Prérequis

Installer :

- Java 17
- PostgreSQL
- Maven

Créer une base de données PostgreSQL nommée :

```
estate
```
### Connexion à PostgreSQL

```
$env:DB_PASSWORD="MOT_DE_PASSE_POSTGRESQL"
```

### JWT

Créer une clé JWT :

```
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$jwtSecret = [Convert]::ToBase64String($bytes)
$rng.Dispose()

[Environment]::SetEnvironmentVariable("JWT_SECRET", $jwtSecret, "User")
```

Après la création de la variable, relancer le terminal

### AWS S3

Définir les variables nécessaires à AWS :

```
$env:AWS_ACCESS_KEY_ID="ACCESS_KEY"
$env:AWS_SECRET_ACCESS_KEY="SECRET_KEY"
$env:AWS_REGION="eu-west-3"
$env:AWS_S3_BUCKET="NOM_DU_BUCKET"
```

Les images sont stocké dans un bucket AWS S3 privé. L'API génère ensuite une URL temporaire permettant au frontend d'afficher les images.

## Lancement du backend

Depuis le dossier du projet :

```
cd Backend
```

Puis lancer :

```
.\mvnw.cmd spring-boot:run
```

Le backend est disponible à l'adresse :

```
http://localhost:8080
```

## Documentation de l'API

La documentation Swagger est disponible une fois le backend lancé :

```
http://localhost:8080/swagger-ui/index.html
```

## Endpoints principaux

### Authentification

```
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
```

### Locations

```
GET  /api/rentals
GET  /api/rentals/{id}
POST /api/rentals
PUT  /api/rentals/{id}
```

### Utilisateurs

```
GET /api/user/{id}
```

### Messages

```
POST /api/messages
```

Les routes `/api/auth/register` et `/api/auth/login` sont publiques.

Les autres routes nécessitent un token JWT valide.

## Upload des images


La taille maximale autorisée est de :

```
10 MB
```
Possibilité de modifier la taille max dans application.properties.

Les images sont stockés sur AWS S3.

## Base de données

Le projet utilise PostgreSQL avec JPA / Hibernate.

Configuration principale :

```
Base : estate
Port : 5432
Utilisateur : postgres
```

Hibernate met à jour automatiquement le schéma de la base de données au lancement de l'application.