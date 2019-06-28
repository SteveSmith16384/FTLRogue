mkdirs bin\data
javac -d bin -encoding ISO-8859-1 -sourcepath src src/com/scs/astrocommander/Main.java
xcopy src\data bin\data\ /E /K /Y
xcopy src\media bin\media\ /E /K /Y
pause
