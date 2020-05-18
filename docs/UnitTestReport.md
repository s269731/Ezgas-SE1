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



### **Class *GasStation* - method *setGasStationId( Integer gasStationId )***

**Criteria for method *setGasStationId(Integer gasStationId)*:**
	
- Type of gasStationId
- Range of gasStationId

**Predicates for method *setGasStationId(Integer gasStationId)*:**

| Criteria | Predicate |
| -------- | --------- |
| Type of gasStationId |     Integer      |
|                |     Others      |
|    Range of gasStationId  |     >=-32768 and <=32767     |
|          |     > 32767      |
|          |    < -32768      |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of gasStationId   |   None   |
|     Range of gasStationId  |  -32768, -32769, -32767, 32767, 32768, 32766  |

**Combination of predicates**:

| Type of gasStationId | Range of gasStationId | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Integer | >=-32768 and <=32767 | Valid | GasStation gs = new GasStation(); <br/> gs.setGasStationId(5); | it.polito.ezgas.GasStationTest.testSetGasStationId() |
| | > 32767 | Invalid | GasStation gs = new GasStation(); <br/> gs.setGasStationId(45000) -> error | it.polito.ezgas.GasStationTest.testSetGasStationId() |
| | < -32768 | Invalid | GasStation gs = new GasStation(); <br/> gs.setGasStationId(-45000) -> error | it.polito.ezgas.GasStationTest.testSetGasStationId() |
| Others | - | Invalid | GasStation gs = new GasStation(); <br/> gs.setGasStationId("abc") -> error | it.polito.ezgas.GasStationTest.testSetGasStationId() |



### **Class *GasStation* - method *getGasStationId()***

**Criteria for method *getGasStationId()*:**

 - Type of output
 
**Predicates for method *getGasStationId()*:**

| Criteria | Predicate |
| -------- | --------- |
| Type of output | Integer |
|| Others |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of output  |   None   |

**Combination of predicates**:

| Type of output | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Integer | Valid | GasStation gs = new GasStation(); <br/> gs.setGasStationId(); <br/> Integer id = gs.getGasStationId(); | it.polito.ezgas.GasStationTest.testGetGasStationId() |
| Other | Invalid | GasStation gs = new GasStation(); <br/> gs.setGasStationId(); <br/> Integer id = gs.getGasStationId();-> error (if the type of output is not Integer) | it.polito.ezgas.GasStationTest.testGetGasStationId() |



### **Class *GasStation* - method *setGasStationName(String gasStationName)***

**Criteria for method setGasStationName(String gasStationName):**
	
 - Type of gasStationName
 - Length of gasStationName

**Predicates for method setGasStationName(String gasStationName):**

| Criteria | Predicate |
| -------- | --------- |
| Type of gasStationName |   String   |
|                |   Others   |
| Length of gasStationName | >= 0 and <= maxValue |
|                    | > maxValue |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of gasStationName   |   None   |
|   Length of gasStationName | 0, maxValue, maxValue+1 |


**Combination of predicates**:


| Type of gasStationName | Length of gasStationName | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| String | >= 0 and <= maxValue | Valid | GasStation gasStation = new GasStation(); <br/> gasStation.setGasStationName("GasStationTest"); | it.polito.ezgas.GasStationTest.testSetGasStationName() |
| String | > maxValue | Invalid | GasStation gasStation = new GasStation(); <br/> gasStation.setGasStationName(("abcde...") -> error (because I try to set a string whose length is > maxValue) | it.polito.ezgas.GasStationTest.testSetGasStationName() |
| Others | - | Invalid |  GasStation gasStation = new GasStation(); <br/> gasStation.setGasStationName(23.5) -> error | it.polito.ezgas.GasStationTest.testSetGasStationName() |



### **Class *GasStation* - method *getGasStationName()***


**Criteria for method getGasStationName():**
	
 - Type of output

**Predicates for method getGasStationName():**

| Criteria | Predicate |
| -------- | --------- |
| Type of output |     String      |
|                |     Others      |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|   Type of output   |   None   |

**Combination of predicates**:

| Type of output |  Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| String |  Valid | GasStation gasStation = new GasStation(); <br/> gasStation.setGasStationName("GasStationTest"); <br/> String gs = gasStation.getGasStationName(); | it.polito.ezgas.GasStationTest.testGetGasStationName() |
| Others |  Invalid |  GasStation gasStation = new GasStation(); <br/> gasStation.setGasStationName("GasStationTest"); <br/> String gs = gasStation.getGasStationName() -> error (if the type of output is not String) | it.polito.ezgas.GasStationTest.testGetGasStationName() |



### **Class *GasStation* - method *setHasDiesel(Boolean hasDiesel)***

**Criteria for method *setHasDiesel(Boolean hasDiesel)*:**
	
 - Type of hasDiesel

**Predicates for method *setHasDiesel(Boolean hasDiesel)*:**

| Criteria | Predicate |
| -------- | --------- |
| Type of hasDiesel | Boolean |
|          | Others |

**Boundaries**:

| Criteria | Boundary values|
| -------- | ---------------|
| Type of hasDiesel | None |

**Combination of predicates**:

| Type of hasDiesel | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Boolean | Valid | GasStation gs = new GasStation(); <br/>gs.setHasDiesel(true); | it.polito.ezgas.GasStationTest.testSetHasDiesel()|
| Others | Invalid | GasStation gs = new GasStation(); <br/> gs.setHasDiesel(5) -> error | it.polito.ezgas.GasStationTest.testSetHasDiesel()


### **Class *GasStation* - method *getHasDiesel()***

**Criteria for method *getHasDiesel()*:**
	
 - Type of output

**Predicates for method *getHasDiesel()*:**

| Criteria | Predicate |
| -------- | --------- |
| Type of output | Boolean |
|          | Others |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Type of output | None |

**Combination of predicates**:

| Types of output | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Boolean | Valid | GasStation gs = new GasStation(); <br/> gs.setHasDiesel(true); <br/> Boolean bool = gs.getHasDiesel();|it.polito.ezgas.GasStationTest.testGetHasDiesel()|
| Other | Invalid | GasStation gs = new GasStation(); <br/> gs.setHasDiesel(true); <br/> Boolean bool = gs.getHasDiesel() -> error (if the type of output is not Boolean) | it.polito.ezgas.GasStationTest.testGetHasDiesel() |



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



