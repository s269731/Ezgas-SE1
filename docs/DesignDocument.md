# Design Document 


Authors: Finocchiaro Loredana, Marino Matteo, Mc Mahon Shannon

Date: 26/05/2020

Version: 3

Change history

| Version | Changes | 
| ----------------- |:-----------|
| 3 | Fixed low level design|
| 2 | Fixed Verification Sequence Diagrams |
| | Added some methods in Repository interfaces and Converter classes |


# Contents

- [High level design](#package-diagram)
- [Low level design](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document (see EZGas Official Requirements.md ). <br>
The design must comply with interfaces defined in package it.polito.ezgas.service (see folder ServicePackage ) <br>
UML diagrams **MUST** be written using plantuml notation.

# High level design 

The style selected is client - server. Clients can be smartphones, tablets, PCs.
The choice is to avoid any development client side. The clients will access the server using only a browser. 

The server has two components: the frontend, which is developed with web technologies (JavaScript, HTML, Css) and is in charge of collecting user inputs to send requests to the backend; the backend, which is developed using the Spring Framework and exposes API to the front-end.
Together, they implement a layered style: Presentation layer (front end), Application logic and data layer (back end). 
Together, they implement also an MVC pattern, with the V on the front end and the MC on the back end.



```plantuml
@startuml
package "Backend" {

}

package "Frontend" {

}


Frontend -> Backend
@enduml


```


## Front End

The Frontend component is made of: 

Views: the package contains the .html pages that are rendered on the browser and that provide the GUI to the user. 

Styles: the package contains .css style sheets that are used to render the GUI.

Controller: the package contains the JavaScript files that catch the user's inputs. Based on the user's inputs and on the status of the GUI widgets, the JavaScript controller creates REST API calls that are sent to the Java Controller implemented in the back-end.


```plantuml
@startuml
package "Frontend" {

    package "it.polito.ezgas.resources.views" {

    }


package "it.polito.ezgas.resources.controller" {

    }


package "it.polito.ezgas.resources.styles" {

    }



it.polito.ezgas.resources.styles -down-> it.polito.ezgas.resources.views

it.polito.ezgas.resources.views -right-> it.polito.ezgas.resources.controller


}
@enduml

```

## Back End

The backend  uses a MC style, combined with a layered style (application logic, data). 
The back end is implemented using the Spring framework for developing Java Entrerprise applications.

Spring was selected for its popularity and relative simplicity: persistency (M and data layer) and interactions are pre-implemented, the programmer needs only to add the specific parts.

See in the package diagram below the project structure of Spring.

For more information about the Spring design guidelines and naming conventions:  https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3



```plantuml
@startuml
package "Backend" {

package "it.polito.ezgas.service"  as ps {
    interface "GasStationService"
    interface "UserService"
} 


package "it.polito.ezgas.controller" {
    class "GasStationController"
    class "HomeController"
    class "UserController"
}

package "it.polito.ezgas.converter" {
    class "GasStationConverter"
    class "PriceReportConverter"
    class "UserConverter"
}

package "it.polito.ezgas.dto" {
    class "GasStationDto"
    class "PriceReportDto"
    class "UserDto"
    class "LoginDto"
    class "IdPw"
}

package "it.polito.ezgas.entity" {
    class "GasStation"
    class "PriceReport"
    class "User"
}

package "it.polito.ezgas.repository" {
    interface "GasStationRepository"
    interface "UserRepository"
}

    
}
note "see folder ServicePackage" as n
n -- ps
```



The Spring framework implements the MC of the MVC pattern. The M is implemented in the packages Entity and Repository. The C is implemented in the packages Service, ServiceImpl and Controller. The packages DTO and Converter contain classes for translation services.



**Entity Package**

Each Model class should have a corresponding class in this package. Model classes contain the data that the application must handle.
The various models of the application are organised under the model package, their DTOs(data transfer objects) are present under the dto package.

In the Entity package all the Entities of the system are provided. Entities classes provide the model of the application, and represent all the data that the application must handle.




**Repository Package**

This package implements persistency for each Model class using an internal database. 

For each Entity class, a Repository class is created (in a 1:1 mapping) to allow the management of the database where the objects are stored. For Spring to be able to map the association at runtime, the Repository class associated to class "XClass" has to be exactly named "XClassRepository".

Extending class JpaRepository provides a lot of CRUD operations by inheritance. The programmer can also overload or modify them. 



**DTO package**

The DTO package contains all the DTO classes. DTO classes are used to transfer only the data that we need to share with the user interface and not the entire model object that we may have aggregated using several sub-objects and persisted in the database.

For each Entity class, a DTO class is created (in a 1:1 mapping).  For Spring the Dto class associated to class "XClass" must be called "XClassDto".  This allows Spring to find automatically the DTO class having the corresponding Entity class, and viceversa. 




**Converter Package**

The Converter Package contains all the Converter classes of the project.

For each Entity class, a Converter class is created (in a 1:1 mapping) to allow conversion from Entity class to DTO class and viceversa.

For Spring to be able to map the association at runtime, the Converter class associated to class "XClass" has to be exactly named "XClassConverter".




**Controller Package**

The controller package is in charge of handling the calls to the REST API that are generated by the user's interaction with the GUI. The Controller package contains methods in 1:1 correspondance to the REST API calls. Each Controller can be wired to a Service (related to a specific entity) and call its methods.
Services are in packages Service (interfaces of services) and ServiceImpl (classes that implement the interfaces)

The controller layer interacts with the service layer (packages Service and ServieImpl) 
 to get a job done whenever it receives a request from the view or api layer, when it does it should not have access to the model objects and should always exchange neutral DTOs.

The service layer never accepts a model as input and never ever returns one either. This is another best practice that Spring enforces to implement  a layered architecture.



**Service Package**


The service package provides interfaces, that collect the calls related to the management of a specific entity in the project.
The Java interfaces are already defined (see file ServicePackage.zip) and the low level design must comply with these interfaces.


**ServiceImpl Package**

Contains Service classes that implement the Service Interfaces in the Service package.



# Low level design

<Based on the official requirements and on the Spring Boot design guidelines, define the required classes (UML class diagram) of the back-end in the proper packages described in the high-level design section.>


```plantuml
@startuml

scale 1024 width

package "it.polito.ezgas.entity" {
    class "GasStation"
    class "PriceReport"
    class "User"
}

package "it.polito.ezgas.service"  as ps {
    interface "GasStationService"
    interface "UserService"
} 

package "it.polito.ezgas.controller" {
    class "GasStationController"
    class "HomeController"
    class "UserController"
}

package "it.polito.ezgas.dto" {
    class "GasStationDto"
    class "PriceReportDto"
    class "UserDto"
    class "LoginDto"
    class "IdPw"
}

package "it.polito.ezgas.converter" {
    class "GasStationConverter"
    class "PriceReportConverter"
    class "UserConverter"
}

package "it.polito.ezgas.repository" {
    interface "GasStationRepository"
    interface "UserRepository"
}

class User {
    +userId: Integer
    +userName: String
    +password: String
    +email: String
    +reputation: Integer {-5..+5}
    +admin: Boolean
}

class PriceReport {
    +priceReportId: Integer
    +user: User
    +dieselPrice: double
    +superPrice: double
    +superPlusPrice: double
    +methane: double
    +gasPrice: double
}

class GasStation {
    +gasStationId: Integer
    +gasStationName: String
    +gasStationAddress: String
    +hasDiesel: Boolean
    +hasSuper: Boolean
    +hasSuperPlus: Boolean
    +hasGas: Boolean
    +hasMethane: Boolean
    +carSharing: String
    +lat: double
    +lon: double
    +dieselPrice: double
    +superPrice: double
    +superPlusPrice: double
    +gasPrice: double
    +methanPrice: double
    +reportUser: Integer
    +reportTimestamp: String
    +reportDependability: double
    +user: User
}

class "GasStationDto" {
    +gasStationId: Integer
    +gasStationName: String
    +gasStationAddress: String
    +hasDiesel: Boolean
    +hasSuper: Boolean
    +hasSuperPlus: Boolean
    +hasGas: Boolean
    +hasMethane: Boolean
    +carSharing: String
    +lat: double
    +lon: double
    +dieselPrice: double
    +superPrice: double
    +superPlusPrice: double
    +gasPrice: double
    +methanPrice: double
    +reportUser: Integer
    +reportTimestamp: String
    +reportDependability: double
    +userDto: UserDto
    +priceReportDtos: List<PriceReportDto>
}

class "PriceReportDto" {
    +priceReportId: Integer
    +user: UserDto
    +dieselPrice: double
    +superPrice: double
    +superPlusPrice: double
    +methane: double
    +gasPrice: double
}

class "UserDto" {
    +userId: Integer
    +userName: String
    +password: String
    +email: String
    +reputation: Integer {-5..+5}
    +admin: Boolean    
}

class "LoginDto" {
    +userId: Integer
    +userName: String
    +email: String
    +reputation: Integer {-5..+5}
    +admin: Boolean
    +token: String
}

class "IdPw" {
    +user: String
    +pw: String
}

class "GasStationConverter" {
    +toGasStationDto(GasStation gasStation): GasStationDto
    +toGasStationDto(List<GasStation> gasStations): List<GasStationDto>
}

class "PriceReportConverter" {
    +toPriceReportDto(PriceReport priceReport): PriceReportDto
}

class "UserConverter" {
    +toUserDto(User user): UserDto
}

interface "GasStationRepository" {
    +findGasStationById(Integer gasStationId): GasStation
    +findByLatBetweenAndLonBetween(Double myLat_inf, Double myLat_sup, 
    Double myLon_inf, Double myLon_sup): List<GasStation>
    +findByDieselTrueOrderByDieselPriceAsc(): List<GasStation>
    +findBySuperTrueOrderBySuperPriceAsc(): List<GasStation>
    +findBySuperPlusTrueOrderBySuperPlusPriceAsc(): List<GasStation>
    +findByGasTrueOrderByGasPriceAsc(): List<GasStation>
    +findByMethaneTrueOrderByMethanePriceAsc(): List<GasStation>
    +findByPremiumDieselTrueOrderByPremiumDieselPriceAsc(): List<GasStation>
    +findByCarSharing(String carSharing): List<GasStation>
	+findByGasStationAddressAndLatAndLon: GasStation
}

interface "UserRepository" {
    +findUserById(Integer userId): User
    +findUserByAdminTrue(): User
    +findByEmailAndPassword(String email, String password): User
    +findByEmail(String email): User
}

class "GasStationController" {
    +gasStationService: GasStationService
    +getGasStationById(Integer gasStationId): GasStationDto
    +getAllGasStations(): List<GasStationDto>
    +saveGasStation(GasStationDto gasStationDto): void
    +deleteGasStation(Integer gasStationId): void
    +getGasStationsByGasolineType(String gasolinetype): List<GasStationDto>
    +getGasStationsByProximity(Double myLat, Double myLon, int radius): List<GasStationDto>
    +getGasStationsWithCoordinates(Double myLat, Double myLon, int radius,
    String gasolineType, String carSharing): List<GasStationDto>
    +getGasStationsWithoutCoordinates(String gasolinetype, String carsharing): List<GasStationDto>
    +setGasStationReport(PriceReportDto priceReportDto): void
}

interface "GasStationService" {
    +getGasStationById(Integer gasStationId): GasStationDto
    +getAllGasStations(): List<GasStationDto>
    +saveGasStation(GasStationDto gasStationDto): gasStationDto
    +deleteGasStation(Integer gasStationId): Boolean
    +getGasStationsByGasolineType(String gasolinetype): List<GasStationDto>
    +getGasStationsByProximity(Double myLat, Double myLon, int radius): List<GasStationDto>
    +getGasStationsWithCoordinates(Double myLat, Double myLon, int radius,
    String gasolineType, String carSharing): List<GasStationDto>
    +getGasStationsWithoutCoordinates(String gasolinetype, String carsharing): List<GasStationDto>
    +setGasStationReport(Integer gasStationId, double dieselPrice, double superPrice, 
    double superPlusPrice, double gasPrice, double methanePrice, double premiumDieselPrice, Integer userId): void
}

class "HomeController" {
    +admin(): String
    +index(): String
    +map(): String
    +login(): String
    +update(): String
    +signup(): String
}

class "UserController" {
    +userService: UserService
    +getUserById(Integer userId): UserDto
    +getAllUsers(): List<UserDto>
    +saveUser(UserDto userDto): UserDto
    +deleteUser(Integer userId): Boolean
    +increaseUserReputation(Integer userId): Integer
    +decreaseUserReputation(Integer userId): Integer
    +login(IdPw credentials): LoginDto
}

interface "UserService" {
    +getUserById(Integer userId): UserDto
    +getAllUsers(): List<UserDto>
    +saveUser(UserDto userDto): UserDto
    +deleteUser(Integer userId): Boolean
    +increaseUserReputation(Integer userId): Integer
    +decreaseUserReputation(Integer userId): Integer
    +login(IdPw credentials): LoginDto
}

GasStation --> User
PriceReport --> User
GasStationDto --> UserDto
GasStationDto --> PriceReportDto
GasStationController --> GasStationService
UserController --> UserService
GasStationConverter --> GasStation
GasStationConverter --> GasStationDto
PriceReportConverter --> PriceReport
PriceReportConverter --> PriceReportDto
UserConverter --> User
UserConverter --> UserDto
UserService --> UserRepository
GasStationService --> GasStationRepository
UserRepository --> User
GasStationRepository --> GasStation
UserService --> UserConverter
GasStationService --> GasStationConverter

@enduml
```


# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>


|          | User | GasStation | PriceReport | UserDto | GasStationDto | PriceReportDto | LoginDto | IdPw | UserRepository | GasStationRepository | PriceReportRepository | UserConverter | GasStationConverter | PriceReportConverter | UserController | GasStationController | UserService | GasStationService |
| ------------- |:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:| 
| FR1.1    | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |  |
| FR1.2    | X |  |  |  |  |  |  |  | X |  |  |  |  |  | X |  | X |  |
| FR1.3    | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |  |
| FR1.4    | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |  |
| FR2      | X | X |  | X | X |  |  |  | X | X |  | X | X |  | X | X | X | X |
| FR3.1    |  | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |
| FR3.2    |  | X |  |  |  |  |  |  |  | X |  |  |  |  |  | X |  | X |
| FR3.3    |  | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |
| FR4      |  | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |
| FR5.1    |  | X | X |  | X | X |  |  |  | X | X |  | X | X |  | X |  | X |
| FR5.2    |  | X |  |  | X |  |  |  |  | X |  |  | X |  |  | X |  | X |
| FR5.3    | X |  |  | X |  |  | X |  | X |  |  | X |  |  | X |  | X |  |



# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

Note that the functions from GUI to Controller classes are not present in the UML diagram, since we didn't analyze the GUI (that regards the front end part).

### Use case 1, UC1 - Create User Account

```plantuml

@startuml
autonumber
actor User

GUI -> UserController: signUp()
activate UserController
UserController -> UserService: saveUser()
deactivate UserController
activate UserService
note right: Note that we are considering the nominal scenario, \nin which the user is not registered to EZGas. \nWe should check whether the email is not already \npresent in the database with the findByEmail() \nmethod of UserRepository before executing the \nfollowing actions. 
UserService -> UserEntity: setUsername()
activate UserEntity
UserService -> UserEntity: setEmail()
UserService -> UserEntity: setPassword()
UserService -> UserEntity: setAdmin()
deactivate UserEntity
UserService -> UserRepository: save()
UserService -> UserConverter: toUserDto()
UserService -> UserController: return userDto
deactivate UserService


@enduml

```


### Use case 4, UC4 - Create Gas Station 

```plantuml

@startuml
autonumber
actor Administrator

GUI -> GasStationController: addGasStation()
activate GasStationController
GasStationController -> GasStationService: saveGasStation()
deactivate GasStationController
activate GasStationService
GasStationService -> GasStationEntity: setGasStationName()
activate GasStationEntity
GasStationService -> GasStationEntity: setGasStationAddress()
GasStationService -> GasStationEntity: setLon()
GasStationService -> GasStationEntity: setLat()
GasStationService -> GasStationEntity: setCarSharing()
GasStationService -> GasStationEntity: setHasDiesel()
GasStationService -> GasStationEntity: setHasSuper()
GasStationService -> GasStationEntity: setHasSuperPlus()
GasStationService -> GasStationEntity: setHasGas()
GasStationService -> GasStationEntity: setHasMethane()
deactivate GasStationEntity
GasStationService -> GasStationRepository: save()
GasStationService -> GasStationConverter: toGasStationDto()
GasStationService -> GasStationController: return gasStationDto
deactivate GasStationService

@enduml

```


### Use case 7, UC7 - Report fuel price for a gas station
```plantuml

@startuml

autonumber
actor User
GUI -> GasStationController: newReport()
activate GasStationController
GasStationController -> GasStationService: setGasStationReport()
deactivate GasStationController
activate GasStationService
GasStationService -> GasStationRepository: findByGasStationId()
GasStationService -> GasStationEntity: setDieselPrice()
activate GasStationEntity
GasStationService -> GasStationEntity: setSuperPrice()
GasStationService -> GasStationEntity: setSuperPlusPrice()
GasStationService -> GasStationEntity: setGasPrice()
GasStationService -> GasStationEntity: setMethanePrice()
GasStationService -> GasStationEntity: setReportUser()
GasStationService -> GasStationEntity: setReportTimestamp()
deactivate GasStationEntity
GasStationService -> GasStationRepository: save()
deactivate GasStationService

@enduml

```


### Use case 8, UC8 - Obtain price of fuel for gas stations in a certain geographic area

```plantuml

@startuml
autonumber
actor AnonymousUser
GUI -> GasStationController: searchGasStations()
activate GasStationController
GasStationController -> GasStationService: getGasStationsByProximity()
activate GasStationService
GasStationService -> GasStationRepository: findByLatBetweenAndLonBetween()
GasStationService -> GasStationConverter: toGasStationDto()
GasStationService -> GasStationController: return List<GasStationDto>
deactivate GasStationService
deactivate GasStationController
note right: This is the nominal scenario, the other variants present in\n the requirement document can be obtained by simply \nsubstituting "getGasStationsByProximity()" with the following options: \n"getGasStationsByGasolineType()", \n"getGasStationByCarSharing()",\n"getGasStationsWithoutCoordinates()",\n"getGasStationsWithCoordinates()".

@enduml

```


### Use case 10, UC10 - Evaluate price

##### Scenario 10.1 

```plantuml

@startuml

autonumber
actor User
GUI -> GasStationController: evaluatePriceOk()
activate GasStationController
GasStationController -> UserService: increaseUserReputation()
activate UserService
UserService -> UserRepository: findByUserId()
UserService -> UserEntity: getReputation()
activate UserEntity
UserService -> UserEntity: setReputation()
deactivate UserEntity
UserService -> UserRepository: save()
UserService -> GasStationController: return getReputation()
deactivate UserService
deactivate GasStationController
note right: If the reputation of the User is lower than 5, it will be \nincremented by 1; if it is already equal to 5, it will not \nbe incremented.

@enduml

```


##### Scenario 10.2 

```plantuml

@startuml

autonumber
actor User
GUI -> GasStationController: evaluatePriceWrong()
activate GasStationController
GasStationController -> UserService: decreaseUserReputation()
activate UserService
UserService -> UserRepository: findByUserId()
UserService -> UserEntity: getReputation()
activate UserEntity
UserService -> UserEntity: setReputation()
deactivate UserEntity
UserService -> UserRepository: save()
UserService -> GasStationController: return getReputation()
deactivate UserService
deactivate GasStationController
note right: If the reputation of the User is greater than -5, it will be \ndecremented by 1; if it is already equal to -5, it will not \nbe decremented.

@enduml

```
