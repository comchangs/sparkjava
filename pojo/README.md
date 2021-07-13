
# project
personal, 2021.07/06 ~ ing

### BE
* java project
* JDK 11
* MySql 
* sparkjava
* gradle

### API
* 회원가입 
* 로그인, 로그아웃 
* 사용자 목록

### work
* jsend
* jdbc, dbcp
* AccountDaoTest

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
