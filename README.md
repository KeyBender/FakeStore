# Fake Store

## Developed using Java, Spring Boot, and MySQL

### Technologies Used
- Spring Tool Suite 4 IDE v4.16.0
- MySQL v8.0.22
- Java JDK 8
- Apache Maven v3.8.6

### Installation Instructions
1. Java
    - Install Java 8
    - Install Apache Maven v3.8.6
    - Install Spring Tool Suite v4.16.0
2. MySQL
    - Install MySQL Server v8.0.22
    - Install MySQL WorkBench v8.0.22
    - Forward Engineer the FakeStore.wmb in MySQL Workbench

3. Install script dependacy in /PopulateTool
```
npm install axios
```
    - Make sure to add "type" : "module" in package.json if errors pop up

3. Run the App
    - In /src/main/resources/application.properties update the username and password to what you set when installing MySQL
    - run App through the IDE

4. Populate the DB
    - Run the "index.js" script in the /PopulateTool folder
```
node index.js
```
5. Now Navigate to localhost:8080/

### Features

1. Browse products by category or searching a keyword

2. Admin Page where you can edit users, orders, and create and edit items
