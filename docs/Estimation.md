# Project Estimation  

Authors: Finocchiaro Loredana, Marino Matteo, Mc Mahon Shannon

Date: 02/05/2020

Version: 1

# Contents



- [Estimate by product decomposition]
- [Estimate by activity decomposition]



# Estimation approach

<Consider the EZGas  project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>

# Estimate by product decomposition

### 

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed | 20 |             
| A = Estimated average size per class, in LOC | 80 | 
| S = Estimated size of project, in LOC (= NC * A) | 1600 |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  | 160 ph |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 4800€ | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) | 5 days |               

The number of estimated classes was calculated referring to the Spring framework (including all packages).
Note that for the estimation by product decomposition we have considered only LOC related to the coding part (since it is written that the estimated size of project had to be computed as NC * A). It follows that the estimated calendar time (5 days, i.e. 160 ph) will correspond only to the coding activity in the estimation by activity decomposition.

# Estimate by activity decomposition

### 

|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- | 
| Requirements Document | 64 ph |
| Design document | 48 ph |
| Coding | 160 ph |
| Testing | 128 ph |


###
Insert here Gantt chart with above activities

```plantuml

@startuml

[Req doc] lasts 2 days
[Design] lasts 2 days
[Req doc] -> [Design]
[Design] -> [Coding]
[Coding] lasts 5 days
[Testing] lasts 4 days
[Coding] -> [Testing]

@enduml

```

# Final estimation

This is the final estimation required for the last deadline. We will be considering the our own productivity that can be found in Dashboard.md.
We remind that the calculations are based on three people working, and not four.

# Estimate by product decomposition

### 

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed | 20 |             
| A = Estimated average size per class, in LOC | 80 | 
| S = Estimated size of project, in LOC (= NC * A) | 1600 |
| E = Estimated effort, in person hours (here use the real productivity, i.e. 5.6 LOC per person hour)  | 286 ph |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 8580€ | 
| Estimated calendar time, in calendar weeks (assume team of 3 people, 8 hours per day, 5 days per week ) | 12 days |               