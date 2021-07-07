package com.sh.pojo;

import com.sh.pojo.account.AccountController;
import com.sh.pojo.account.AccountService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        new AccountController(AccountService.getBean());

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
