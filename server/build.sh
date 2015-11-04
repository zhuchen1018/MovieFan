cd src

javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/utils/*.java
#javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/model/*.java
javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/entity/*.java
javac  -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/accessor/*.java
javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/storage/*.java
javac -classpath WEB-INF/lib/*:WEB-INF/classes -d WEB-INF/classes com/myapp/servlet/*.java

jar -cvf servlet.war *.jsp html images css js WEB-INF .ebextensions
#cp servlet.war /Library/Tomcat/webapps
mv servlet.war ../

