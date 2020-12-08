# researchbr

## Description
This software aims to help scientific researches working in brasilian universities in their reports to the national funding agencies.

## This system was built using:
[IntelliJ idea community 2020.3](https://www.jetbrains.com/pt-br/idea/download/#section=linux);<br>
[Java](https://www.java.com/pt-BR/download/help/java8_pt-br.html)  v. 1.8.0_275;<br>
[MySQL8](https://dev.mysql.com/);<br>
[React](https://pt-br.reactjs.org/);<br>
[Spring Boot](https://spring.io/projects/spring-boot);<br>
[Visual Studio Code](https://code.visualstudio.com/download).

## Starting:
Make sure you have a backendresearch empty database in your MySQL.<br>
Clone the repository:<br>
`git clone https://github.com/tiagofalconlopes/researchbr.git`<br>
Change the username and password in the application.yaml file for the database access.<br>
Start too run the back-end application:<br>
`cd $PWD/backendresearchbr`<br>
Build skipping the tests:<br>
`gradle build -x test`<br>
Run the back-end:
`java -jar build/libs/backendresearchbr-0.0.1-SNAPSHOT.jar`<br>
Go to the frontendresearchbr directory:<br>
`cd $PWD/frontendresearchbr`<br>
Install dependency modules (commonly necessary in first use): <br>
`npm install`<br>
Start using npm:<br>
`npm start`<br>
