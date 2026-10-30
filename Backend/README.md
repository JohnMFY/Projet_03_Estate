# Estate Backend

Backend de l'application Estate développé avec Spring Boot, REST, Spring Security, JPA/Hibernate et PostgreSQL.

## Install Backend

### Prérequis

Installer :

Java 17

PostgreSQL 17

Créer une base de données PostgreSQL nommée :

estate

### Configuration JWT

Créer une clé JWT :
```console
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$jwtSecret = [Convert]::ToBase64String($bytes)
$rng.Dispose()

[Environment]::SetEnvironmentVariable("JWT_SECRET", $jwtSecret, "User")
```
Relancer VS Code

## Launch Backend

```console
cd Backend
```

Rentrer son mot de passe PostgreSQL et lancer le backend :

```console
$env:DB_PASSWORD="MOT_DE_PASSE"
.\mvnw.cmd spring-boot:run
```

Le backend :

http://localhost:8080

L'API GraphQL :

http://localhost:8080/graphql