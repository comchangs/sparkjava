package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.JsonTransformer;

import static spark.Spark.*;

public class AccountController {
    public AccountController(AccountService accountService) {
        path("/api",() -> {
            path("/", () -> {

                post("/register", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);
                    boolean joined = accountService.joined(signUpForm);
                    res.status(201);
                    return signUpForm;
                }, new JsonTransformer());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    System.out.println("req > "+ id);
                    response.redirect("/", 301);
                    boolean result = accountService.signOut(id);
                    return null;
                });
            });
        });
    }


}
