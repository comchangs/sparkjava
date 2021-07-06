package com.sh.pojo;

import com.google.gson.Gson;
import com.sh.pojo.account.AccountService;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.JsonTransformer;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        AccountService accountService = AccountService.getBean();

        path("/api", () -> {
            path("/", () -> {

                post("/signUp", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);
                    boolean joined = accountService.joined(signUpForm);
                    res.status(201);
                    return signUpForm;
                }, new JsonTransformer());

                after((req, res) -> {
                    res.type("application/json");
                });
            });
        });
    }
}
