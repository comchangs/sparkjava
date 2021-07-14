
# project
personal, 2021.07/06 ~ ing
* code rafactoring 
  * repository : [board and comment (Servlet/JSP)](https://github.com/sunhwa-kim/servlet-JSP-board-and-comment)
* JAVA and understand web application (Spring)

### BackEnd
* java project
* JDK 11
* MySql 
* sparkjava
* gradle

### API
* 회원가입 
* 로그인, 로그아웃 
* 사용자 목록

### My work
* rest api - [jsend](https://github.com/omniti-labs/jsend)
* jdbc
  * JdbcContext : separate jdbc for decreasing duplicated code (try/catch, preparestatement, resultset) 
  * MakePrepareStatement interface : jdbc method's parameter
  * MapperRow interface : resultset
* dbcp 
  * ConnectionMaker interface : connection information, Dao and DBConnectionManager
  * ConnectionPool interface : control the number of connection / realease 
* session 
  * SecurityService interface : when login, change input information to security user
  * SecurityContext : singleton pattern / when login, isAuthenticated method make user sessionID
  * Authentication : make token and sessionID, using UUID and PasswordHashing class
    * refactoring - encapsulation, static constructor
  * SessionData : the session information send client (after check user, login recode) 
  
### TEST
* AccountDaoTest : test for the dbcp code refactoring
* AccountApiControllerTest : test for POST /api/register , /api/login before code refactoring


### Configuration
* AppConfig
  * DB connection information
  
### how to Run
* AppConfigEx -> AppConfig, and input DB connection information
* Main
  * run application 
  * http://localhost:4567/api/ + request
* http://localhost:4567/api/register
    ~~~json
     {
        "nickname" : "test_Nickname",
        "email" : "testEmail20216@email.com",
        "password" : "asdf"
      }
    ~~~
  * jsend response
    * success
      ~~~json
       {
          "status": "SUCCESS",
          "data": { "register": 
                      {   "nickname": "test_Nickname",
                          "password": "asdf",
                          "email": "testEmail20216@email.com" 
                      }
                  }
      }
      ~~~
    * fail
      ~~~json
      {
        "status": "FAIL",
        "data": {"nickname or email": "Information is duplicated"}
      }
      ~~~

* postman
<div style="inline;">
<img src="https://user-images.githubusercontent.com/66774973/125237068-0b1f6e80-e320-11eb-8183-1edcca884485.PNG" width="300"> 
</div>
<div style="inline;">
</div>
