# Fake Store

## Developed using Java, Spring Boot, and MySQL

### Technologies Used
- Spring Tool Suite 4 IDE v4.16.0
- MySQL v8.0.22
- Java JDK 8
- Apache Maven v3.8.6


1. Java
    -  Java 8
    -  Apache Maven v3.8.6
    -  Spring Tool Suite v4.16.0
    - Settings:
        - Maven Project
        - Spring Boot v2.7.5
        - War Packaging 
        - Java 8 
    - Dependencies
        - Spring Web
        - MySQL Driver
        - Spring Data JPA
        - Spring Boot DevTools
2. MySQL
    -  MySQL Server v8.0.22
    -  MySQL WorkBench v8.0.22

3.  script dependacy in /PopulateTool
```
npm install
```


4. Run the App
    - In /src/main/resources/application.properties update the username and password to what you set when installing MySQL

5. Populate the DB
    - Run the "index.js" script in the /PopulateTool folder

```
node index.js
```


### Features

1. Browse products by category or searching a keyword

2. Admin Page where you can edit users, orders, and create and edit items
    - Orders can be updated to "shipped" or "not shipped"
    - Admins can update other users "admin" status
    - Create or update items

3. Create Fake orders
    - Stores who ordered, what they ordered, total price, and if the order has been shipped or not

