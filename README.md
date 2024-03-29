# Case Study - myRetail RESTful Service

## Application Description

An end-to-end Proof-of-Concept for a products API, that aggregates product data from multiple sources and returns 
it as JSON to the caller. This is a RESTful service that can retrieve product and price details by ID as well as 
update pricing information for a product.

### Tech Stack
***
The application uses the following:
 - Spring Boot - RESTful service that interacts with a myRetail service and a NoSQL data store
 - MongoDB - NoSQL data store that holds product pricing information  

### Setting up the database
***
The application was built using a MongoDB. There is not an in-memory representation of this database for this project.
The database was installed and run locally alongside the project as a means for local development. Similar steps will
need to be taken for anyone else who wishes to run the application locally.

Once MongoDB has been installed locally:
 1. Create a database called `myRetail`
 2. Create a collection called `products`
 
A resource that includes initial products to seed the database with can be found at: 
> Unix: `{rootdir}/src/main/resources/static/initial_products.json` <br/>
> Windows: `{rootdir}\src\main\resources\static\initial_products.json`

### Running the Application
***
A mvn wrapper at the root of the project allows cloners of the repository to run the application without any installs.
The way the application will run depends on the OS.

#### Windows
```bash
cd rootdir
.\mvnw.cmd install
.\mvnw.cmd spring-boot:run
```

#### Unix

```bash
cd rootdir
./mvnw install
./mvnw spring-boot:run
```

After this step has been completed you should see a Spring Boot application start in your terminal and a port number
for localhost that the application can be used from.

### Testing
***
The application comes with a test suite that can be run again using the maven wrapper in the project.

#### Windows
```bash
cd rootdir
.\mvnw.cmd test
```

#### Unix

```bash
cd rootdir
./mvnw test
```

### See the project in an IDE
***
The application is a maven project so you can import the `pom.xml` file in the root of the project to work in your 
favorite IDE.