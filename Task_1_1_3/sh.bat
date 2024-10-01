javac -d Build2/classes src/main/java/org/example/*.java src/main/java/org/expressions/*.java src/main/java/org/parser/*.java src/main/java/org/Printable/*.java
jar --create --file Build2/jar/Expressions.jar --main-class=org.example.Main -C Build2/classes .
javadoc -d Build2/doc src/main/java/org/example/*.java src/main/java/org/expressions/*.java src/main/java/org/parser/*.java src/main/java/org/Printable/*.java
java -jar Build2/jar/Expressions.jar