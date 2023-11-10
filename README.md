# Clariti-Hiring-Java

[![Build Status](https://github.com/eskielsantana/Clariti-Hiring-Java/actions/workflows/build.yml/badge.svg)](https://github.com/seu-usuario/seu-repositorio/actions)
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
    │   │           │   FileReader.java
    │   │           │   UseOpenCsv.java
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