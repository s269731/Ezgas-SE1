# GUI  Testing Documentation 

Authors: Finocchiaro Loredana, Marino Matteo, Mc Mahon Shannon

Date: 01/06/2020

Version: 1

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR

Names of the GUI tests refer to .py files.

### 

| Scenario ID | Functional Requirements covered | GUI Test(s) |
|:-----------:|:-------------------------------:| ----------- | 
| UC1 | FR1.1 | Signup |       
|     |  | CreateAccount_admin |  
| No UC | FR2 | Login |       
| UC2 | FR1.1 | UpdateAccount |        
|     |  | UpdateAccount_admin |       
| UC3 | FR1.2 | DeleteUser |         
| UC4 | FR3.1 | CreateGS |             
| UC5 | FR3.1 | UpdateGS |             
| UC6 | FR3.2 | DeleteGS |     
| UC7 | FR5.1 | SetReport |  
|     |     | SetReportOverwritten |       
| UC8 | FR4   | SearchByProximity |     
|     |     | SearchByGasType |
|     |     | SearchByCarSh |
|     |     | SearchWithCoordinates |        
| UC9 | FR5.2 | UpdateDependability |             
| UC10.1 | 5.3 | EvaluateRight |     
| UC10.2| 5.3 | EvaluateWrong |         



# REST  API  Testing

This part of the document reports about testing the REST APIs of the back end. The REST APIs are implemented by classes in the Controller package of the back end. 
Tests should cover each function of classes in the Controller package

## Coverage of Controller methods

For the Controller tests we used a pre-filled database that can be found in GUITests folder.


| class.method name | Functional Requirements covered |REST  API Test(s) | 
| ----------- |:-------------------------------:| ----------- | 
| UserService.getUserById()  | FR1.4 | TestController.testGetUserById() |     
| UserService.getAllUsers() | FR1.3 | TestController.testGetAllUsers() |   
| UserService.saveUser() | FR1.1 |  TestController.testSaveUser()  |                 
| UserService.deleteUser() | FR1.2 | TestController.testDeleteUser() |                   
| UserService.increaseUserReputation  | FR5.3 | TestController.testIncreaseUserReputation() |         
| UserService.decreaseUserReputation  | FR5.3 | TestController.testDecreaseUserReputation() |     
| UserService.login() | FR2 | TestController.testLogin()   |               
| GasStationService.getGasStationById() | FR4 | TestController.testGetGasStationById() |         
| GasStationService.getAllGasStations() | FR3.3 | TestController.testGetAllGasStations() |       
| GasStationService.saveGasStation() | FR3.1 | TestController.testSaveGasStation()|     
| GasStationService.deleteGasStation() | FR3.2 | TestController.testDeleteGasStation() |             
| GasStationService.getGasStationsByGasolineType()  | FR4 | TestController.testGetGasStationsByGasolineType() |     
| GasStationService.getGasStationsByProximity() | FR4 | TestController.testGetGasStationsByProximity()|     
| GasStationService.getGasStationsWithCoordinates()  | FR4 | TestController.testGetGasStationsWithCoordinates() |     
| GasStationService.setGasStationReport() | FR5.1 | TestController.testSetGasStationReport() |     
