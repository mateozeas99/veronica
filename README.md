![Veronica Logo](https://raw.githubusercontent.com/rolandopalermo/Veronica/master/static/veronica.jpg)

[![PayPal Donate](http://ionicabizau.github.io/badges/paypal.svg?style=plastic&colorB=68B7EB)]()
[![Platforms](https://img.shields.io/badge/plaform-windows%20%7C%20linux%20%7C%20macOS-blue.svg?style=plastic&colorB=68B7EB)]()
[![Release](https://img.shields.io/badge/version-1.0.0-green.svg?style=plastic&colorB=68B7EB)]()

`Veronica` es una API Rest utilizada para la emisión y autorización de comprobantes electrónicos según la normativa vigente del [Sistema de Rentas Internas del Ecuador](http://www.sri.gob.ec/). El proyecto ha sido desarrollado a través de una aplicación [Spring-Boot 1.5.9.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot/1.5.9.RELEASE). Adicionalmente, `Veronica` almacena los comprobantes en una base de datos PostgreSQL lo cual le permite realizar posteriores consultas más allá de las comunes como por ejemplo, consultar detalles de facturas, consultar totales o listar comprobantes por emisor o receptor.

Todo comprobante electrónico gestionado a través de `Veronica` manejará un ciclo de vida basado en 4 fases:
<p align="center">
<img src="https://raw.githubusercontent.com/rolandopalermo/veronica/master/static/veronica_ciclo_vida.png" width="500">
</p>

## Software requerido
- JDK 1.8.0_121
- Apache Maven 3.5.3
- PostgreSQL 11.1-1

## Pasos previos
- [Instalar y configurar Maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)

## Instalación
1. Luego de instalar PostgreSQL, abrir una consola o shell y Crear la base de datos.
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

## Bitácora

- V1: 2018-04-12, Primera versión.
- V2: 2018-04-27, Perfiles de Mave.
- V3: 2018-04-28, Habilitar Swagger2.
- V4: 2018-11-10, Generación de RIDEs.
- V5: 2018-11-19, Integración con Postman.
- V6: 2019-01-09, Integración con PostgreSQL.
- V7: 2019-02-21, Retenciones y Guías de remisión.

## Autores

| [![](https://avatars1.githubusercontent.com/u/11875482?v=4&s=80)](https://github.com/rolandopalermo) | [![](https://avatars2.githubusercontent.com/u/24358710?s=80&v=4)](https://github.com/XaviMontero) | [![](https://avatars0.githubusercontent.com/u/3452663?s=80&v=4)](https://github.com/XaviMontero) |
|-|-|-|
| [@RolandoPalermo](https://github.com/rolandopalermo) | [@XaviMontero](https://github.com/XaviMontero) | [@Japstones](https://github.com/japstones) |