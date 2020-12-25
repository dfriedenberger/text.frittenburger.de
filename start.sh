export MAVEN_OPTS="-Xmx6g"
mvn exec:java -Dexec.mainClass=de.frittenburger.text.app.WebService > log.txt &
