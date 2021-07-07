package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.PasswordForm;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.JsonTransformer;
import com.sh.pojo.config.network.header.Header;

import static spark.Spark.*;

public class AccountController {

    public AccountController(AccountService accountService) {

        path("/api",() -> {
                path("/", () -> {

                post("/register", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);
                    boolean joined = accountService.joined(signUpForm);
                    System.out.println("main > result : "+joined+" data "+ signUpForm.toString());
                    res.status(201);
                    return body(joined, "register",signUpForm, "error");
                }, new JsonTransformer());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    boolean result = accountService.signOut(id);
                    System.out.println("main signOut > "+ id+" result : "+result);

                    if (result) {
                        response.redirect("/", 204);
                    }
                    return body(result, "signOut", null, "error");
                }, new JsonTransformer());

            });  // end of path
        });

        path("/account",() -> {
            path("/", () -> {

                get("/:id", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    Long id = gson.fromJson(req.body(), Long.class);

                    Account getAccount = accountService.getAccountById(id);
                    return body(true, "account/id",getAccount, "");
                }, new JsonTransformer());

                patch("/:id", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    PasswordForm password = gson.fromJson(request.body(), PasswordForm.class);
                    accountService.updatePassword(password);

                    // TODO session 변경
                    // EXCEIPTION 없이 진행 되면 TRUE

                    return body(true, "account/id",null, "");
                }, new JsonTransformer());

                put("/", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    Account account = gson.fromJson(request.body(), Account.class);
                    AccountResponse getAccount = accountService.update(account);

                    // TODO session과 무관
                    // TODO   EXCEIPTION 없이 진행 되면 TRUE

                    return body(true, "account/id",getAccount, "");
                }, new JsonTransformer());

            });  // end of path
        });
    }

    public Header body(boolean result, String api, Object body, String message ){
        if(result) {
            if(body == null) {
                return Header.OK();
            }
            return Header.OK(api, body);
        } else {
            if(body != null) {
                return Header.fail(api, body);
            }
            return Header.error();
        }
    }
}
