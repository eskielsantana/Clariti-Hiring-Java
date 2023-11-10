# Clariti-Hiring-Java

[![Build Status](https://github.com/eskielsantana/Clariti-Hiring-Java/actions/workflows/build.yml/badge.svg)](https://github.com/eskielsantana/Clariti-Hiring-Java/actions)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=eskielsantana_Clariti-Hiring-Java&metric=alert_status)](https://sonarcloud.io/dashboard?id=eskielsantana_Clariti-Hiring-Java)
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

Your manager sent you a .csv file containing fees from several Departments in your company, 
and you need to organize and perform calculations on those fees to retrieve specific information for a given hierarchy.

### Goals

* Calculate the total fees contained in a given node in the company structure.
* Output the total fees for each registered department in the company.
* List the N most expensive fees on a specific node.
* List the N cheapest fees in a specific node.

### Solution

I used the [OpenCSV](https://mvnrepository.com/artifact/com.opencsv/opencsv) library to load the **.csv** file from the resources folder and distributed each line **(Fee)**
into a **Tree structure** in which each level of nodes represents a hierarchy on which the fee belongs *(Department, Category, SubCategory, and Type)*.

## Project Structure

The project follows a clean architecture guideline. There are two main abstractions.

Domain is the place to keep only business rules. The other places can import classes from the domain, but the domain should be isolated from framework classes and annotations that can create a strong coupling between the business rules and the app structure. This approach makes the process of segregating the business from one specific domain to another. Another advantage of this approach is that it lets us better manage our test strategy. The test domain package executes mainly unit tests to check business rules. We use mocks to simulate the interface's implementation and inputs to do this. Otherwise, the test infrastructure package is responsible for keeping the integration tests.

The Infrastructure package will keep the necessary implementations to integrate the app with external services (repositories, queues, etc.), implement contracts defined at the domain level (abstract classes and interfaces) and set up the app, loading data from environment variables or external services (AWS app config). As this package contains mainly classes from frameworks, the test for this package is focused on integrations

The app was created respecting the SOLID principles, using interfaces to define implementation contracts, keeping single responsibility, implementing dependency inversion (infrastructure depends on the domain), etc. This app uses GitHub actions to atomate app build and uses Sonarcloud to check the quality of code and the test coverage.

```
├── .gitignore
├── pom.xml
├── README.md
│   
└─── src
    ├─── main
    │   ├─── java
    │   │   ├─── domain
    │   │   │   ├─── fee
    │   │   │   │       Fee.java
    │   │   │   │       FeeNode.java
    │   │   │   │       FeeReader.java
    │   │   │   │       FeeService.java
    │   │   │   │
    │   │   │   └─── helper
    │   │   │           CurrencyHelper.java
    │   │   │           MathHelper.java
    │   │   │           RandomHelper.java
    │   │   │
    │   │   └─── infrastructure
    │   │       │   FeeRepository.java
    │   │       │   Main.java
    │   │       │
    │   │       └─── reader
    │   │           │   CSVFileReader.java
    │   │           │
    │   │           └─── factory
    │   │                   CSVReaderFactory.java
    │   │                   DefaultCSVReaderFactory.java
    │   │
    │   └─── resources
    │           empty_file.csv
    │           log4j.properties
    │           raw_fees.csv
    │
    └─── test
        └─── java
            ├─── domain
            │   ├─── enumerators
            │   │       Category.java
            │   │       Department.java
            │   │
            │   ├─── fee
            │   │       FeeFactory.java
            │   │       FeeNodeTest.java
            │   │       FeeServiceTest.java
            │   │       FeeTest.java
            │   │
            │   └─── helper
            │           CurrencyHelperTest.java
            │           MathHelperTest.java
            │           RandomHelperTest.java
            │
            └─── infrastructure
                │   FeeRepositoryTest.java
                │   MainTest.java
                │
                └─── reader
                        UseOpenCsvParameterizedTest.java
                        UseOpenCsvTest.java
```

## Performance
I tested the performance on 2 different files, the given .csv and a heavier one I generated using the same columns and similar data to the given file.
The results are displayed below:

| Method                    | 1.74mb file    | 1.4gb file   |
|---------------------------| -------------- | ------------ |
| **loadFees**              | 127814 micro   | 32.110 milli |
| **getNodeTotalFees (1º)** | 998 micro      | 1000 micro   |
| **getNodeTotalFees (2º)** | 0 micro        | 0 micro      |
| **getNodeList()**         | 0 micro        | 0 micro      |
| **getFeesByLayer (1º)**   | 5999 micro     | 1031 milli   |
| **getFeesByLayer (2º)**   | 999 micro      | 535 milli    |

## Install
#### Download the repository
```sh
$ git clone https://github.com/eskielsantana/Clariti-Hiring-Java.git
```

#### Run tests
```sh
$ cd Clariti-Hiring-Java && mvn clean test
```

#### Run application
```sh
mvn exec:java
```
## Author

* **Ezequiel Santana** - [LinkedIn](https://www.linkedin.com/in/ezequiel-santana/)