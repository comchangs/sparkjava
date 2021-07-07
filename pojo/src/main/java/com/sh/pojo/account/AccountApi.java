package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.PasswordForm;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.JsonTransformer;
import com.sh.pojo.config.network.ResponseBody;
import com.sh.pojo.config.network.header.Header;

import static spark.Spark.*;

public class AccountApi {

    public AccountApi(AccountService accountService) {

        path("/api",() -> {
            path("/", () -> {

                post("/register", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);
                    boolean joined = accountService.joined(signUpForm);
                    System.out.println("main > result : "+joined+" data "+ signUpForm.toString());
                    res.status(201);
                    return ResponseBody.of(joined, "register",signUpForm, "error");
                }, new JsonTransformer());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    boolean result = accountService.signOut(id);
                    System.out.println("main signOut > "+ id+" result : "+result);

                    if (result) {
                        response.redirect("/", 204);
                    }
                    return ResponseBody.of(result, null, null, "error");
                }, new JsonTransformer());

            });  // end of path


            path("/account",() -> {
                get("/:id", "application/json", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    AccountResponse getAccount = accountService.getAccount(id);
                    return ResponseBody.of(true, "account/id",getAccount, "");
                }, new JsonTransformer());

                patch("", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    PasswordForm password = gson.fromJson(request.body(), PasswordForm.class);
                    accountService.modifyPassword(password);

                    // TODO session 변경
                    // EXCEIPTION 없이 진행 되면 TRUE

                    return ResponseBody.of(true, "account/id",null, "");
                }, new JsonTransformer());

                // TODO 알람 등 정보 초기화 요청 로직시
 /*               put("/", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    AccountRequest account = gson.fromJson(request.body(), AccountRequest.class);
                    AccountResponse getAccount = accountService.modifyAccountInfo(account);

                    // TODO session과 무관
                    // TODO   EXCEIPTION 없이 진행 되면 TRUE

                    return body(true, "account/id", getAccount, "");
                }, new JsonTransformer());*/

            });
        });

    }

}
