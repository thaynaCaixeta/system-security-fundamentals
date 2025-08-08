To run the module:

1. ```docker-compose build```
2. ```docker-compose up -d```
3. ```docker-compose logs -f```
4. ```http://localhost:8080/ldap-example?name=username&password=pass```

To verify Env variables

```docker exec -it oauth2-gcp-example printenv | grep OAUTH```