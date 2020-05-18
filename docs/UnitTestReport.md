# Unit Testing Documentation

Authors: Cao Peng, Finocchiaro Loredana, Marino Matteo, Mc Mahon Shannon

Date: 18/05/2020

Version: 1

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)


- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

## Note from Authors

In it.polito.ezgas.GasStationTest and it.polito.ezgas.UserTest you can find tests for all setters, getters and Constructors.
However, for the scope of this document we chose specific methods per class to test. Specifically, for each class we tested the Constructor and one getter and setter per type of field (getter ad setter for a field of type Integer, getter and setter for a field of type Boolean, etc.).

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezgas   You find here, and you can use  class EZGasApplicationTests.java that is executed before 
    the set up of all Spring components>




### **Class *GasStation* - method *setDieselPrice(double dieselPrice)***

**Criteria for method setDieselPrice(double dieselPrice):**
	
 - Type of dieselPrice
 - Range of dieselPrice

**Predicates for method setDieselPrice(double dieselPrice):**

| Criteria | Predicate |
| -------- | --------- |
| Type of dieselPrice |     Double      |
|                |     Others      |
|    Range    |     >=minDouble and <=maxDouble     |
|          |     > maxDouble      |
|          |    < minDouble      |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of dieselPrice   |   None   |
|     Range of dieselPrice |  minDouble, minDouble-1, maxDouble, maxDouble+1  |

**Combination of predicates**:


| Type of dieselPrice | Range | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Double |  >=minDouble and <=maxDouble  | Valid | GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice(1.678); | it.polito.ezgas.GasStationTest.testSetDieselPrice() |
| |  > maxDouble | Invalid |  GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice(maxDouble+1) -> error | it.polito.ezgas.GasStationTest.testSetDieselPrice() |
| | < minDouble  | Invalid |  GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice(minDouble-1) -> error | it.polito.ezgas.GasStationTest.testSetDieselPrice() |
| Others | - | Invalid | GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice("too much") -> error | it.polito.ezgas.GasStationTest.testSetDieselPrice() |



### **Class *GasStation* - method *getDieselPrice()***


**Criteria for method getDieselPrice():**
	
 - Type of output



**Predicates for method getDieselPrice():**

| Criteria | Predicate |
| -------- | --------- |
| Type of output |     Double      |
|                |     Others      |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of output  |   None   |


**Combination of predicates**:


| Type of output | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Double |   Valid | GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice(1.678); <br/> Double price = gasStation.getDieselPrice() | it.polito.ezgas.GasStationTest.testGetDieselPrice() |
| Others | Invalid | GasStation gasStation = new GasStation(); <br/> gasStation.setDieselPrice(1.678);  <br/> Double dieselPrice = gasStation.getDieselPrice() -> error (if the type of output is not Double) | it.polito.ezgas.GasStationTest.testGetDieselPrice() |



### **Class *GasStation* - method *GasStation(String gasStationName, String gasStationAddress, boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane, String carSharing, double lat, double lon, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, Integer reportUser, String reportTimestamp, double reportDependability)***

**Criteria for method *GasStation(String gasStationName, String gasStationAddress, ..)*:**	

 - Number of parameters
 - Types of input sequence

**Predicates for method *GasStation(String gasStationName, String gasStationAddress, ..)*:**

| Criteria | Predicate |
| -------- | --------- |
| Number of parameters | 18 |
|          | Different from 18 |
| Types of input sequence | (String, String, boolean, boolean, boolean, boolean, boolean, String, double, double, double, double, double, double, double, Integer, String, double) |
|          | Other combinations |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Number of parameters | None |
| Types of input sequence | None |

**Combination of predicates**:

| Number of parameters | Types of input sequence | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|18|String, String, boolean, boolean, boolean, boolean, boolean, String, double, double, double, double, double, double, double, Integer, String, double|Valid|Gas Station gs = new GasStation("Esso", "Viale Macallè 15 Piemont Italy", true, true, false, true, true, "Enjoy", 45.5517866, 8.050702, 1.50, 1.30, 0, 1.30, 1.20, 10, "2020/05/17-18:30:17", 5) |it.polito.ezgas.GasStationTest.testGasStationConstructor()||
||Other combinations|Invalid|Gas Station gs = new GasStation(*5*, "Viale Macallè 15 Piemont Italy", true, true, false, true, true, "Enjoy", 45.5517866, 8.050702, 1.50, 1.30, 0, 1.30, 1.20, 10, "2020/05/17-18:30:17", 5) -> error | it.polito.ezgas.GasStationTest.testGasStationConstructor() |
|Different from 18 | - | Invalid | GasStation gs = new GasStation(5) -> error | it.polito.ezgas.GasStationTest.testGasStationConstructor() |




# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezgas>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
| User | it.polito.ezgas.UserTest.testSetUserId() |
|| it.polito.ezgas.UserTest.testGetUserId() |
|| it.polito.ezgas.UserTest.testSetUserName() | 
|| it.polito.ezgas.UserTest.testGetUserName() |
|| it.polito.ezgas.UserTest.testSetAdmin() |
|| it.polito.ezgas.UserTest.testGetAdmin() |
|| it.polito.ezgas.UserTest.testUserConstructor() |
| GasStation | it.polito.ezgas.GasStationTest.testSetGasStationId()|
|| it.polito.ezgas.GasStationTest.testGetGasStationId()|
|| it.polito.ezgas.GasStationTest.testSetGasStationName()|
|| it.polito.ezgas.GasStationTest.testGetStationName()|
|| it.polito.ezgas.GasStationTest.testSetHasDiesel()|
|| it.polito.ezgas.GasStationTest.testGetHasDiesel()|
|| it.polito.ezgas.GasStationTest.testSetDieselPrice()|
|| it.polito.ezgas.GasStationTest.testGetDieselPrice()|
|| it.polito.ezgas.GasStationTest.testGasStationConstructor() |



### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



