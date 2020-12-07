# researchbr

## Description
This software aims to help scientific researches working in brasilian universities in their reports to the national funding agencies.

## This system was built using:
[IntelliJ idea community 2020.3](https://www.jetbrains.com/pt-br/idea/download/#section=linux);<br>
[Java](https://www.java.com/pt-BR/download/help/java8_pt-br.html)  v. 1.8.0_275;
[MySQL8](https://dev.mysql.com/);
[React](https://pt-br.reactjs.org/);
[Spring Boot](https://spring.io/projects/spring-boot);
[Visual Studio Code](https://code.visualstudio.com/download).

## Starting:
Make sure you have a backendresearch empty database in your MySQL.
Change the username and password in the application.yaml file for the database access.
Clone the repository:
`git clone https://github.com/tiagofalconlopes/researchbr.git`
Start too run the back-end application:
`cd $PWD/backendresearchbr`
Build skipping the tests:
`gradle build -x test`
Go to the frontendresearchbr directory:
`cd $PWD/frontendresearchbr`
Start using npm:
`npm start`
