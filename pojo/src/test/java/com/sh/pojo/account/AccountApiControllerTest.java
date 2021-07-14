package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.Main;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.config.db.Dao;
import com.sh.pojo.config.network.response.ResponseStatus;
import com.sh.pojo.utils.TestResponse;
import org.junit.jupiter.api.*;
import spark.Spark;

import javax.naming.NamingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.sh.pojo.utils.TestRequest.request;
import static com.sh.pojo.utils.TestRequest.sendUrl;
import static org.junit.jupiter.api.Assertions.*;

class AccountApiControllerTest {
    AccountRepository accountRepository = Dao.getInstance(AccountRepository.class);

    private static final String URI = "http://localhost";
    private static final String PORT = "4567";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";

    private String nickname = "sunhwa";
    private String email = "sunhwa21@eamil.com";
    private String password = "1234asdf";

    @BeforeAll
    static void beforeAll() throws SQLException, NamingException {
        String[] args = {};
        Main.main(args);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @AfterAll
    static void afterAll() {
        Spark.stop();
    }

    @Test
    @DisplayName("가입 후 로그인 확인")
    void registerAndLoginTest() {
        Map<String, String> data = new HashMap<>();

        data.put("nickname", nickname);
        data.put("email", email);
        data.put("password", password);

        URL url = sendUrl(URI, PORT, "/api/register");
        TestResponse response = request(POST, url, new Gson().toJson(data));

        assert response != null;
        Account byEmail = accountRepository.findByEmail("sunhwa21@eamil.com");
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS.toString(), response.getStatus(), "status"),
                () -> assertEquals("sunhwa", byEmail.getNickname(), "post nickname"),
                () -> assertEquals("sunhwa21@eamil.com", byEmail.getEmail(), "post email")
        );

        URL url2 = sendUrl(URI, PORT, "/api/login");
        data.put("loginId", nickname);
        TestResponse response2 = request(POST, url2, new Gson().toJson(data));

        assert response != null;
        assertEquals(ResponseStatus.SUCCESS.toString(), response.getStatus(), "status");
    }

}