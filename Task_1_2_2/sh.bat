javac -d Build2/classes src/main/java/org/example/*.java
jar --create --file Build2/jar/HashTable.jar --main-class=org.example.Main -C Build2/classes .
javadoc -d Build2/doc src/main/java/org/example/*.java
java -jar Build2/jar/HashTable.jar