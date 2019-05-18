#!/bin/bash
set -e
# ENVIRONMENT $POSTGRES_PASSWORD AND $POSTGRES_DB
cd /opt/veronica/veronica-web/src/main/resources
sed -i "s|spring.datasource.url=jdbc:postgresql://localhost:5432/veronica-db|spring.datasource.url=jdbc:postgresql://db:5432/$POSTGRES_DB|g" application.properties
sed -i "s|spring.datasource.password=123456|spring.datasource.password=$POSTGRES_PASSWORD|g" application.properties
# ENVIRONMENT $DEPLOY
cd /opt/veronica/veronica-web
if [ "$DEPLOY" = 'production']; then
    mvn spring-boot:run -Pproduction
else
    mvn spring-boot:run -Pdevelopment
fi