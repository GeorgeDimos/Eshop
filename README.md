# A simple e-shop made with Spring Boot.

![home_page](https://i.imgur.com/FW86JcH.jpg)

In order to run you only need to create an "eshop" database and provide valid 
spring.datasource.username and spring.datasource.password in the application.properties file. 
 
```
$ mysql -u root -p

> create database eshop;
```

Run with 
```
./mvnw spring-boot:run
```

Use http://localhost:8080/ and login with "admin" as username and password or create a new user with your email.
