<h1 align="center">
<img src="https://raw.githubusercontent.com/rolandopalermo/Veronica/master/static/veronica.jpg" alt="Markdownify" width="420">
<p align="center">
  <a href="https://www.paypal.me/rolandopalermo" target="_blank"><img alt="PayPal Donate" src="http://ionicabizau.github.io/badges/paypal.svg"></a>
  <a href="blog.rolandopalermo.com" target="_blank"><img alt="PayPal Donate" src="https://img.shields.io/badge/plaform-windows%20%7C%20linux%20%7C%20macOS-blue.svg"></a>
  <a href="blog.rolandopalermo.com" target="_blank"><img alt="PayPal Donate" src="https://img.shields.io/badge/version-1.0.0-green.svg"></a>
</p>
<h4 align="center">E-Invoicing Rest API for the integration with "Servicio de Rentas Internas" Web services.</h4>
</h1>

<!-- TOC depthFrom:1 depthTo:2 withLinks:1 updateOnSave:1 orderedList:0 -->
Table of contents
=================
- [Veronica REST API](#veronica-rest-api)
	- [Software Stack](#software-stack)
	- [Preamble](#preamble)
	- [Startup Settings](#startup-settings)
	- [Deployment](#deployment)
	- [Documentation](#documentation)
	- [Postman API Reference](#postman-api-reference)
	- [Documentation history](#documentation-history)
	- [Authors](#authors)

<!-- /TOC -->
## Software Stack
- JDK 1.8.0_121
- Apache Maven 3.5.3
- PostgreSQL 11.1-1

## Preamble
`Veronica REST API` is a set of RESTful web services that provide an abstraction layer which allows for easy issue of electronic invoicing, according with the Ecuadorian regulations imposed by the "Servicio de Rentas Internas".

## Startup Settings

1. Create a database with the next command:
```bash
postgres=# CREATE DATABASE "veronica-db";
```

2. Import the database tables using the **veronica/sql/veronica_schema.sql** script.
```bash
$ psql -U postgres veronica-db < veronica/sql/veronica_schema.sql
```

3. Execute the veronica_data sql script located in **veronica/sql/veronica_data.sql**

4. Add the following property at the end of the **postgresql.conf** file located in **{postgreSQL_path}/11/data/**
```bash
encrypt.key = 8qxBjzCdQkwdpu
```

5. Restart PostgreSQL Server.

6. As next step, you must install all the JAR files localted in the **additional_libs** folder into your local Maven repository using the following commands:
```bash
$ cd additional_libs
mvn install:install-file -Dfile=jss-4.2.5.jar -DgroupId=org.mozilla -DartifactId=jss -Dversion=4.2.5 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibAPI-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=api -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibCert-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=cert -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibOCSP-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=ocsp  -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibPolicy-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=policy -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibTrust-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=trust -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibTSA-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=tsa -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibXADES-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=xades -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=org.apache.xmlsec-adsi -DartifactId=xmlsec-adsi -Dversion=1.4.2 -Dpackaging=jar
```

7. Now move to root project directory and execute the next maven command:
```bash
$ cd veronica
$ mvn install
```

8. This project provides two maven profiles. Using the next command, you will  be able the choose the correct profile according to your environment:

`DEV`
```bash
$ cd veronica-web
$ mvn spring-boot:run -Pdevelopment
```

`PRD`
```bash
$ cd veronica-web
$ mvn spring-boot:run -Pproduction
```

:warning: To modify the database conection properties, open the file **veronica/veronica-web/src/main/resources/application.properties** and edit the next properties:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/veronica-db
spring.datasource.username=postgres
spring.datasource.password=#######
```

## Documentation
http://localhost:8080/veronica/swagger-ui.html

## Postman API Reference
- Postman collection file: **veronica/Verónica API Reference.postman_collection.json**

## Documentation history

- V1: 2018-04-12, first draft.
- V2: 2018-04-27, enable maven profiles.
- V3: 2018-04-28, enable swagger2 for api documentation.
- V4: 2018-11-10, Invoice RIDE generation.
- V5: 2018-11-19, Postman collection.
- V6: 2019-01-09, Postgresql integration.
- V7: 2019-02-21, Enable Withholding tax.

## Authors

| [![](https://avatars1.githubusercontent.com/u/11875482?v=4&s=80)](https://github.com/rolandopalermo) | [![](https://avatars2.githubusercontent.com/u/24358710?s=80&v=4)](https://github.com/XaviMontero) | [![](https://avatars0.githubusercontent.com/u/3452663?s=80&v=4)](https://github.com/XaviMontero) |
|-|-|-|
| [@RolandoPalermo](https://github.com/rolandopalermo) | [@XaviMontero](https://github.com/XaviMontero) | [@Japstones](https://github.com/japstones) |
