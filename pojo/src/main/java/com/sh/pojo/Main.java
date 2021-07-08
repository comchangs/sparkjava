package com.sh.pojo;

import com.sh.pojo.account.AccountApiController;
import com.sh.pojo.account.AccountService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        new AccountApiController(AccountService.getBean());

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
