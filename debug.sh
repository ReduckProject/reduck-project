#  mvn -DargLine="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Djps.track.ap.dependencies=false -Dcompiler.process.debug.port=8000 " clean install -pl reduck-goup
mvn clean -pl reduck-goup
# export MAVEN_OPTS="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -Djps.track.ap.dependencies=false -Dcompiler.process.debug.port=8000"
export MAVEN_OPTS=""
mvnDebug install -pl reduck-goup  -Dmaven.surefire.debug -Djps.track.ap.dependencies=true