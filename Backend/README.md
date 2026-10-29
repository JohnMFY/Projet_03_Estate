# Estate Backend

Backend de l'application Estate développé avec Spring Boot, GraphQL, Spring Security, JPA/Hibernate et PostgreSQL.

## Install Backend

### Prérequis

Installer :

Java 17

PostgreSQL 17

Créer une base de données PostgreSQL nommée :

```
estate
```

Le projet utilise Maven Wrapper, il n'est donc pas nécessaire d'installer Maven.

## Launch Backend

Ouvrir le projet dans Visual Studio Code puis ouvrir un terminal dans le dossier :

Backend

Définir le mot de passe PostgreSQL :

```
$env:DB_PASSWORD="VOTRE_MOT_DE_PASSE"
```

Créer une clé JWT :

```
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$env:JWT_SECRET = [Convert]::ToBase64String($bytes)
$rng.Dispose()
```

Lancer le backend :

```
$env:DB_PASSWORD="VOTRE_MOT_DE_PASSE"
.\mvnw.cmd spring-boot:run
```

Le backend :

http://localhost:8080

L'API GraphQL :

http://localhost:8080/graphql