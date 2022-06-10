# Example GraphQL server (base on Micronaut Guide)

## Setup
### Init database 
```bash
./up-db.sh
```
### Run server
```bash
 ./gradlew run --args="-datasources.default.username=dbuser -datasources.default.password=theSecretPassword"
```

## Tests
Run requests from `test.http`