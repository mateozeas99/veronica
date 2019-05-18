FROM maven:3.6.1-jdk-8
#---------------------------
COPY ./additional_libs /opt/veronica/additional_libs/
COPY ./src /opt/veronica/src/
COPY ./pom.xml /opt/veronica/
COPY ./veronica-bo /opt/veronica/veronica-bo/
COPY ./veronica-common /opt/veronica/veronica-common/
COPY ./veronica-dto /opt/veronica/veronica-dto/
COPY ./veronica-mapper /opt/veronica/veronica-mapper/
COPY ./veronica-model /opt/veronica/veronica-model/
COPY ./veronica-persistence /opt/veronica/veronica-persistence/
COPY ./veronica-ride /opt/veronica/veronica-ride/
COPY ./veronica-soap /opt/veronica/veronica-soap/
COPY ./veronica-web /opt/veronica/veronica-web/
#---------------------------
WORKDIR /opt/veronica/additional_libs
#---------------------------
RUN mvn install:install-file -Dfile=jss-4.2.5.jar -DgroupId=org.mozilla -DartifactId=jss -Dversion=4.2.5 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibAPI-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=api -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibCert-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=cert -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibOCSP-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=ocsp  -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibPolicy-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=policy -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibTrust-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=trust -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibTSA-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=tsa -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=MITyCLibXADES-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=xades -Dversion=1.0.4 -Dpackaging=jar
RUN mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=org.apache.xmlsec-adsi -DartifactId=xmlsec-adsi -Dversion=1.4.2 -Dpackaging=jar
#---------------------------
WORKDIR /opt/veronica
RUN mvn clean install
EXPOSE 8080
COPY ./docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
