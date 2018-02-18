# CountMeUp
#Real-time votes counter for BBC

The app counts user votes in a competition. One user can vote only three time in competition. User need to authorize in the system to take part in vote competittion.

The app has server and client. The server side consists of REST-API and WebSocket endpoint.MySQL database is used for data storage.The database is ran in Docker Container. The package model will give an idea of data tables. Interaction of client and server goes through REST-API. API let's authorize, vote, add competition, start or stop it. Websocket is used for updating competition data in real time.

The app is develepoed using Java for server and JavaScript for client.

Server-side technologies:
* Gradle
* Spring-boot
* Spring-websocket
* MyBatis
* Jackson
* Junit
   

Client-side technologies:
* AngularJS
* Bootstrap
* SockJS

# Running 
```yaml
mysql -u root -p -h 127.0.0.1 < db_create.sql

gradle build

java -jar /build/libs/CountMeUp-ver_num.jar
```
Please check these [Assumptions](Assumptions.md)
