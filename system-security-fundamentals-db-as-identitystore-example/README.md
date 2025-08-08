### To run the module:

1. Create the .env file with the desired port to be exposed
2. Run in a terminal:
```
mvn clean package
docker compose up --build -d
docker logs -f db-as-identity-store-example
```

### Request example:
```
curl -v -u rudy:secret1 http://localhost:8081/db-as-identity-store-example/test
```

### Expected output:
```
This is a servlet
web username: rudy
```
