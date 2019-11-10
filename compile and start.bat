md bin
javac -d bin src/fractions/Fraction.java
javac -d bin src/FractionOperations.java
jar -cmf manifest.mf FractionOperations.jar -C bin .
java -jar FractionOperations.jar