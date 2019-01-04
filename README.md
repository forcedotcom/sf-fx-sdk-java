# Salesforce Function SDK for Java

# Build
```
$ mvn install
```

To develop Salesforce Function Runtime, deploy to local repository, then build the runtime:
```
$ mvn deploy:deploy-file \
    -Durl=file:./repo/ \
    -Dfile=target/com.salesforce.function.sdk.java-1.0-SNAPSHOT.jar \
    -DgroupId=com.salesforce.function \
    -DartifactId=com.salesforce.function.sdk.java \
    -Dpackaging=jar \
    -Dversion=1.0
```