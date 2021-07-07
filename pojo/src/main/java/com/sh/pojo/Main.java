package com.sh.pojo;

import com.sh.pojo.account.AccountApi;
import com.sh.pojo.account.AccountService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        new AccountApi(AccountService.getBean());

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
