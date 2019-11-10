md bin
javac -d bin src/Fractions/Fraction.java
javac -d bin src/FractionOperations.java
jar -cmf manifest.mf FractionOperations.jar -C bin .
java -jar FractionOperations.jar