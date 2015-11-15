cd src

javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/utils/*.java
javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/entity/*.java
javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/accessor/*.java
javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/*.java
javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/servlet/*.java
#javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes/jsp jsp/*.jsp
javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/SQL/*.java

jar -cvf servlet.war jsp htmls images css WEB-INF 
mv servlet.war ../

