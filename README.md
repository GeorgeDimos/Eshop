[![CircleCI](https://circleci.com/gh/GeorgeDimos/Eshop.svg?style=svg)](https://circleci.com/gh/GeorgeDimos/Eshop)

# A simple e-shop made with Spring Boot.

An account is required to complete an order. You can create one with your email or use GitHub to login. OAuth2 available
only on AWS.

REST api available at /api. Accessible only from the admin account. To login as admin use admin/admin as username and
password.

![home_page](https://i.imgur.com/FW86JcH.jpg)

## AWS

Available at [AWS](http://springeshop-env.eba-av3dpkac.us-east-2.elasticbeanstalk.com/home)

## Docker

Run on Docker with

```
docker-compose up -d
```

http://localhost:8080/

## Localhost

To run on localhost

1. create an "eshop" database with

```
$ mysql -u root -p

> create database eshop;
```

2. edit src/main/resources/application-localhost.properties and provide the datasource along with a valid username and
   password

e.g.

```asciidoc
spring.datasource.url=jdbc:mysql://localhost:3306/eshop
spring.datasource.username=root
spring.datasource.password=mypassword
```

Run with

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=localhost
```

http://localhost:8080/