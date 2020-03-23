# Coronavirus-tracker
Coronavirus is a rapidly spreading outbreak and a pandemic. This coronavirus tracker helps display the number of confirmed cases, the number of deaths and the details of countries affected globally. Not having to go through a lot of options this tracker makes it easy by providing information of the results monitored timely in an elaborate way from the data repository.
Note that the data is taken from the data repository by by the Johns Hopkins University Center for Systems Science and Engineering https://github.com/CSSEGISandData/COVID-19.
This application is made using Angular8 in the frontend and Spring Boot2 in the backend.
![Springboot-Angular](images/springboot-angular.png)
# Spring Boot App
Spring Intializr is used to create the project Spring boot 2.2.5 and java 11 version with spring web dependency. This project is imported as a maven project. Spring Boot application is developed to build RESTful APIs while leveraging the stackless features of REST.
# Angular app
First install npm and node js.Run npm install.
This installs most of the modules, but configuring this angular app requires installing few more node modules as listed below.
  Install @angular/material in Angular applications for Material Design styled table using
   - ng add @angular/material

  Install fusion charts in Angular applications for charts and its features using
   - npm install fusioncharts angular-fusioncharts --save

  Install Font Awesome icons in Angular applications using
   - npm install @fortawesome/free-solid-svg-icons
   - npm install @fortawesome/angular-fontawesome
   - npm install @fortawesome/fontawesome-svg-core
