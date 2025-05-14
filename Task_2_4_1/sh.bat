javac -cp lib/* -d Build2/classes src/main/java/dslchecker/config/*.java src/main/java/dslchecker/model/*.java src/main/java/dslchecker/report/*.java src/main/java/dslchecker/repos/*.java src/main/java/dslchecker/*.java
jar --create --file Build2/jar/Checker.jar --main-class=dslchecker.Main -C Build2/classes .
javadoc -d Build2/doc src/main/java/dslchecker/config/*.java src/main/java/dslchecker/model/*.java src/main/java/dslchecker/report/*.java src/main/java/dslchecker/repos/*.java src/main/java/dslchecker/*.java
java -jar Build2/jar/Checker.jar