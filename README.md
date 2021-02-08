[![CircleCI](https://circleci.com/gh/GeorgeDimos/Eshop.svg?style=svg)](https://circleci.com/gh/GeorgeDimos/Eshop)

# A simple e-shop made with Spring Boot.

![home_page](https://i.imgur.com/FW86JcH.jpg)

## AWS

Accessible at [AWS](http://springeshop-env.eba-av3dpkac.us-east-2.elasticbeanstalk.com/home)

## Localhost

In order to run on localhost, you should

1. edit src/main/resources/application.properties and provide a valid spring.datasource.username and
   spring.datasource.password
2. create an "eshop" database with

```
$ mysql -u root -p

> create database eshop;
```

Run with

```
./mvnw spring-boot:run
```

Use http://localhost:8080/ and login with admin/admin or create a new user with your email.