# Integration and API Test Documentation

Authors: Finocchiaro Loredana, Marino Matteo, Mc Mahon Shannon

Date: 26/05/2020

Version: 1

# Contents

- [Dependency graph](#dependency-graph)

- [Integration approach](#integration)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 

     <report the here the dependency graph of the classes in it/polito/Ezgas, using plantuml>
   
```plantuml
@startuml

interface GasStationService
interface UserService
class GasStationServiceimpl
class UserServiceimpl
class GasStationController
class UserController
class GasStationConverter
class UserConverter
class GasStationDto
class UserDto
class LoginDto
class IdPw
class GasStation
class User
interface GasStationRepository
interface UserRepository

UserController --> UserService
UserController --> UserDto
UserController --> LoginDto
UserController --> IdPw
UserService --> UserDto
UserService --> LoginDto
UserService --> IdPw
UserServiceimpl --> UserService
UserServiceimpl --> UserDto
UserServiceimpl --> LoginDto
UserServiceimpl --> IdPw
UserServiceimpl --> User
UserServiceimpl --> UserConverter
UserServiceimpl --> UserRepository
UserConverter --> User
UserConverter --> UserDto
UserRepository --> User
GasStationServiceimpl --> GasStationService
GasStationServiceimpl --> UserRepository
GasStationServiceimpl --> User
GasStationServiceimpl --> GasStationConverter
GasStationServiceimpl --> GasStationRepository
GasStationServiceimpl --> GasStation
/'GasStation --> User
GasStationDto --> UserDto'/
GasStationConverter --> GasStation
GasStationConverter --> GasStationDto
GasStationRepository --> GasStation
GasStationController --> GasStationService
GasStationController --> GasStationDto
GasStationService --> GasStationDto

@enduml
```


     
# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
    <The last integration step corresponds to API testing at level of Service package>
    <Tests at level of Controller package will be done later>

For Integration testing, we adopted a mixed approach, since we followed the bottom up method, but applied also Mockito. </br>

step1: class UserConverter, GasStationConverter, UserRepository, GasStationRepository</br>
step2: class UserServiceimpl + UserConverter, GasStationServiceimpl + GasStationConverter</br>
step3: class UserServiceimpl + UserConverter + UserRepository, GasStationServiceimpl + GasStationConverter + GasStationRepository </br>

In the step1, we tested Converter and Repository classes singularly: in particular, for Repository we worked with TestEntityManager and @DataJpaTest, in order to avoid affecting the real database. </br>
In the step2, we mocked Repository methods. </br>
In the step3, we used again TestEntityManager and @DataJpaTest.

#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them>

## Step 1
| Classes  | JUnit test cases |
|--|--|
| UserConverter | it.polito.ezgas.UserAndGasStationConverterTest.java |
| GasStationConverter | it.polito.ezgas.UserAndGasStationConverterTest.java |
| UserRepository | it.polito.ezgas.UserRepositoryTest.java |
| GasStationRepository | it.polito.ezgas.GasStationRepositoryTest.java |


## Step 2
| Classes  | JUnit test cases |
|--|--|
| UserServiceimpl + UserConverter | it.polito.ezgas.UserServiceimplTest.java |
| GasStationServiceimpl + GasStationConverter | it.polito.ezgas.GasStationServiceimplTest.java |

In the step 2, we mocked the Repository methods.

## Step 3 API Tests

   <The last integration step  should correspond to API testing, or tests applied to all classes implementing the APIs defined in the Service package>

| Classes  | JUnit test cases |
|--|--|
| UserServiceimpl + UserConverter + UserRepository | it.polito.ezgas.UserServiceimplTestAPI.java |
| GasStationServiceimpl + GasStationConverter + GasStationRepository | it.polito.ezgas.GasStationServiceimplTestAPI.java |



# Scenarios


<If needed, define here additional scenarios for the application. Scenarios should be named
 referring the UC they detail>

## Scenario UCx.y

| Scenario |  name |
| ------------- |:-------------:| 
|  Precondition     |  |
|  Post condition     |   |
| Step#        | Description  |
|  1     |  ... |  
|  2     |  ... |



# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  ..         | FRx                             |             |             
|  ..         | FRy                             |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|                            |           |


