#!/bin/bash
version=${1?Version param required, eg 1.0}
mvn clean install
mvn deploy:deploy-file \
  -Durl=file:./repo/ -Dfile=target/com.salesforce.function.sdk.java-$version.jar \
  -DgroupId=com.salesforce.function \
  -DartifactId=com.salesforce.function.sdk.java \
  -Dpackaging=jar \
  -Dversion=$version
