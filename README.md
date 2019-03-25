![Veronica Logo](https://raw.githubusercontent.com/rolandopalermo/Veronica/master/static/veronica.jpg)

[![PayPal Donate](http://ionicabizau.github.io/badges/paypal.svg?style=plastic&colorB=68B7EB)]()
[![Platforms](https://img.shields.io/badge/plaform-windows%20%7C%20linux%20%7C%20macOS-blue.svg?style=plastic&colorB=68B7EB)]()
[![Release](https://img.shields.io/badge/version-1.0.0-green.svg?style=plastic&colorB=68B7EB)]()

`Veronica` es una API Rest utilizada para la emisión y autorización de comprobantes electrónicos según la normativa vigente del [Sistema de Rentas Internas del Ecuador](http://www.sri.gob.ec/). El proyecto ha sido desarrollado a través de una aplicación [Spring-Boot 1.5.9.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot/1.5.9.RELEASE). Adicionalmente, `Veronica` almacena los comprobantes en una base de datos PostgreSQL lo cual le permite realizar posteriores consultas más allá de las comunes como por ejemplo, consultar detalles de facturas, consultar totales o listar comprobantes por emisor o receptor.

Todo comprobante electrónico gestionado a través de `Veronica` manejará un ciclo de vida basado en 4 fases:
<p align="center">
<img src="https://raw.githubusercontent.com/rolandopalermo/veronica/master/static/veronica_ciclo_vida.png" width="500">
</p>

Cotenidos
=================
- [Software requerido](#software-requerido)
- [Pasos previos](#pasos-previos)
- [Instalación](#instalación)
	- [Instalación de base de datos](#instalación-de-base-de-datos)
	- [Instalación de dependencias](#instalación-de-dependencias)
	- [Despliegue](#despliegue)
- [Documentación](#documentación)
	- [Swagger](#swagger)
	- [Postman](#postman)
- [Bitácora](#bitácora)
- [Autores](#autores)

## Software requerido
- JDK 1.8.0_121
- Apache Maven 3.5.3
- PostgreSQL 11.1-1

## Pasos previos
- [Instalar y configurar Maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)

## Instalación

### Instalación de base de datos
1. Abrir una consola o shell y crear la base de datos.
```bash
$ psql -U postgres
# CREATE DATABASE "veronica-db";
# \q
```

2. Crear la estructura de tablas ejecutando el script **veronica_schema.sql**.
```bash
$ cd /veronica/sql
$ psql -U postgres veronica-db < veronica_schema.sql
```

3. Ejecutar el script **veronica_data.sql** de carga inicial de datos.
```bash
$ cd /veronica/sql
$ psql -U postgres veronica-db < veronica_data.sql
```

4. Agregar la siguiente entrada al final del archivo **postgresql.conf** y reiniciar el servidor de base de datos.
```bash
encrypt.key = 8qxBjzCdQkwdpu
```

5. Para modificar los atributos de conexión a la base de datos, dirigirse al archivo **/veronica/veronica-web/src/main/resources/application.properties**.

### Instalación de dependencias
1. `Veronica` posee una lista de dependencias que no se encuentran disponibles en el repositorio remoto de Maven por lo que se tendrá que hacer la instalación de forma manual. Para esto, ejecutar los comandos listados a continuación.
```bash
$ cd /veronica/additional_libs
$ mvn install:install-file -Dfile=jss-4.2.5.jar -DgroupId=org.mozilla -DartifactId=jss -Dversion=4.2.5 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibAPI-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=api -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibCert-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=cert -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibOCSP-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=ocsp  -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibPolicy-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=policy -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibTrust-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=trust -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibTSA-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=tsa -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibXADES-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=xades -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=org.apache.xmlsec-adsi -DartifactId=xmlsec-adsi -Dversion=1.4.2 -Dpackaging=jar
```

2. Instalar `Veronica`.
```bash
$ cd /veronica
$ mvn clean install
```

### Despliegue
`Veronica` proporciona dos perfiles de despliegue: Desarrollo y Producción. Cada uno de estos perfiles posee un archivo de configuración situado en **/veronica/src/filters**. Para desplegar el proyecto con el perfil adecuado, indicar el ambiente como argumento de ejecución.

`Desarrollo`
```bash
$ cd /veronica/veronica-web
$ mvn spring-boot:run -Pdevelopment
```

`Producción`
```bash
$ cd /veronica/veronica-web
$ mvn spring-boot:run -Pproduction
```

## Lista de recursos
Los tipos de documentos soportados por la API de `Veronica` son:

### Versión 1.0 (/api/v1.0)
- Facturas
- Retenciones
- Guías de remisión

Estos documentos pueden ser gestionados a través de los siguientes recursos:

#### Facturas

| Recurso                                       | Método | Descripción                                                                 |
|-----------------------------------------------|:------:|-----------------------------------------------------------------------------|
| /facturas                            |  POST  | Crea una factura y la almacena en base de datos                 |
| /facturas/{clave_acceso}/enviar      |   PUT  | Envía una factura al SRI y actualiza su estado en base de datos |
| /facturas/ {clave_acceso}/autorizar  |   PUT  | Autoriza una factura y actualiza su estado en base de datos     |
| /facturas/{clave_acceso}             | DELETE | Elimina una factura de la base de datos                                     |
| /facturas/{claveAcceso}/archivos/pdf |   GET  | Retorna la representación PDF de una factura                    |
| /facturas/{claveAcceso}/archivos/xml |   GET  | Retorna la representación XML de una factura                    |

#### Retenciones

| Recurso                                       | Método | Descripción                                                                 |
|-----------------------------------------------|:------:|-----------------------------------------------------------------------------|
| /retenciones                            |  POST  | Crea una retención y la almacena en base de datos                 |
| /retenciones/{clave_acceso}/enviar      |   PUT  | Envía una retención al SRI y actualiza su estado en base de datos |
| /retenciones/{clave_acceso}/autorizar  |   PUT  | Autoriza una retención y actualiza su estado en base de datos     |
| /retenciones/{clave_acceso}             | DELETE | Elimina una retención de la base de datos                                     |
| /retenciones/{claveAcceso}/archivos/pdf |   GET  | Retorna la representación PDF de una retención                    |
| /retenciones/{claveAcceso}/archivos/xml |   GET  | Retorna la representación XML de una retención                    |

#### Guías de remisión

| Recurso                                       | Método | Descripción                                                                 |
|-----------------------------------------------|:------:|-----------------------------------------------------------------------------|
| /guias-remision                            |  POST  | Crea una guía de remision y la almacena en base de datos                 |
| /guias-remision/{clave_acceso}/enviar      |   PUT  | Envía una guía de remision al SRI y actualiza su estado en base de datos |
| /guias-remision/{clave_acceso}/autorizar  |   PUT  | Autoriza una guía de remision y actualiza su estado en base de datos     |
| /guias-remision/{clave_acceso}             | DELETE | Elimina una guía de remision de la base de datos                                     |
| /guias-remision/{claveAcceso}/archivos/pdf |   GET  | Retorna la representación PDF de una guía de remision                    |
| /guias-remision/{claveAcceso}/archivos/xml |   GET  | Retorna la representación XML de una guía de remision                    |

## Documentación
### Swagger
http://localhost:8080/veronica/swagger-ui.html

### Postman
`Veronica` también pone a disposición de los usuarios una colección de llamadas y ejemplos que se encuentra en la ruta **/veronica/Verónica API Reference.postman_collection.json**.

## Bitácora

- V1: 2018-04-12, Primera versión.
- V2: 2018-04-27, Perfiles de Maven.
- V3: 2018-04-28, Habilitar Swagger2.
- V4: 2018-11-10, Generación de RIDEs.
- V5: 2018-11-19, Integración con Postman.
- V6: 2019-01-09, Integración con PostgreSQL.
- V7: 2019-02-21, Retenciones y Guías de remisión.

## Autores

| [![](https://avatars1.githubusercontent.com/u/11875482?v=4&s=80)](https://github.com/rolandopalermo) | [![](https://avatars2.githubusercontent.com/u/24358710?s=80&v=4)](https://github.com/XaviMontero) | [![](https://avatars0.githubusercontent.com/u/3452663?s=80&v=4)](https://github.com/XaviMontero) |
|-|-|-|
| [@RolandoPalermo](https://github.com/rolandopalermo) | [@XaviMontero](https://github.com/XaviMontero) | [@Japstones](https://github.com/japstones) |