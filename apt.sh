mvnDebug clean install -pl reduck-goup -DskipTests=true
mvn -DargLine="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"