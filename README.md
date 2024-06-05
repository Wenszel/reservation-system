## Autorzy
Maksymilian Katolik
Wiktor Smaga

## Technologie
Hibernate, Spring Data, PostgreSQL, Java 21

## Dokumentacja
https://github.com/Wenszel/reservation-system/blob/main/dokumentacja.pdf

## How to run

Pre-requisites:
- [Java 21](https://www.oracle.com/pl/java/technologies/downloads/#java21)
- [Docker](https://docs.docker.com/engine/install/)

1. Setting up the database
```
docker pull postgres
docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```

