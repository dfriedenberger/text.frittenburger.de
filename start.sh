export MAVEN_OPTS="-Xmx6g"
mvn exec:java -Dexec.mainClass=de.frittenburger.text.app.TextParserWebService > log.txt &
