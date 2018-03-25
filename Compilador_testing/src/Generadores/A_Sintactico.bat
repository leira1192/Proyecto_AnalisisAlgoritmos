SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_111\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
cd C:\Users\leirA\Documents\NetBeansProjects\Compilador_testing\src\Analizadores
java -jar C:\java-cup-bin-11b-20160615\java-cup-11b.jar -parser analisis_sintactico -symbols Simbolos A_Sintactico.cup
pause

