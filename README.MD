This is a simple application to store bookmarks exposing REST API. 
This application uses Spring Boot and MySql RDMS to store data.

To connect the application to the database it is necessary that settings in 
src/main/resources/application.settings be changed. One should create a 
bookmarks database and provide connection credentials. The tables will be 
created automatically by Flyway migrations integrated into Spring Boot.

~~~~
spring.datasource.url=jdbc:mysql://localhost:3306/bookmarks
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
~~~~

A user with name *Phil* and password *1* will be created as well as several bookmarks.

The API is secured with Basic Authentication, so the aforementioned credentials 
can be used to access the data.

To get all the bookmarks stored by user Phil one should key in the following.

~~~~
curl -w "\n" 2>/dev/null localhost:8080/Phil/bookmarks -u Phil:1
~~~~

To extract data for a single particular bookmark one should type in a command:

~~~~
curl -w "\n" 2>/dev/null localhost:8080/Phil/bookmarks/1 -u Phil:1
~~~~

To edit a bookmark the HTTP PUT method is used.

~~~~
curl -X PUT -w "\n" 2>/dev/null localhost:8080/Phil/bookmarks/1 -u Phil:1 \
 -H "Content-Type: application/json" -d '{"url":"github.com"}'
~~~~

To add a bookmark use the following.

~~~~
 curl -X POST -w "\n" 2>/dev/null localhost:8080/Phil/bookmarks \
 -u Phil:1 -H "Content-Type: application/json" \
 -d '{"url":"http://github.com", "description":"A lot of great projects"}'
~~~~

To remove a bookmark the HTTP DELETE method is used.

~~~~
curl -X DELETE -w "\n" 2>/dev/null localhost:8080/Phil/bookmarks/3 \
 -u Phil:1
~~~~

The project contains a lot of test examples based on Spring Boot Test Starter
and Spring Security Test. There are examples of how to test repositories and 
resource methods including the case when the latter are secured with Basic
Authentication.