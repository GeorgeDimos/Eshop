[![CircleCI](https://circleci.com/gh/GeorgeDimos/Eshop.svg?style=svg)](https://circleci.com/gh/GeorgeDimos/Eshop)
# A simple e-shop made with Spring Boot.

![home_page](https://i.imgur.com/FW86JcH.jpg)

## AWS

Accessible at [AWS](http://springeshop-env.eba-av3dpkac.us-east-2.elasticbeanstalk.com/home)

## Localhost

In order to run on localhost, you need to

1. create an "eshop" database with

```
$ mysql -u root -p

> create database eshop;
```

2. edit src/main/resources/application.properties and provide the datasource along with a valid username and password

e.g.

```asciidoc
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/eshop
spring.datasource.username=root
spring.datasource.password=mypassword
```

Run with

```
./mvnw spring-boot:run
```

Use http://localhost:5000/ and login with admin/admin or create a new user with your email.