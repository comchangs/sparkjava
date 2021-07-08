package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.PasswordForm;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.response.Response;

import static com.sh.pojo.config.network.response.JsonUtil.jsend;
import static com.sh.pojo.config.network.response.JsonUtil.json;
import static spark.Spark.*;

public class AccountApi {

    public AccountApi(AccountService accountService) {

        path("/api",() -> {
            path("/", () -> {

                post("/register", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);
                    boolean joined = accountService.joined(signUpForm);
                    res.status(201);
                    return Response.OK(jsend("register", signUpForm));
                }, json());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    boolean result = accountService.signOut(id);

                    response.status(200);
                    return Response.OK();
                }, json());

            });  // end of path


            path("/account",() -> {
                get("/:id", "application/json", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    AccountResponse getAccount = accountService.getAccount(id);
                    return Response.OK(jsend("account", getAccount));
                }, json());

                patch("", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    PasswordForm password = gson.fromJson(request.body(), PasswordForm.class);
                    accountService.modifyPassword(password);

                    // TODO session 변경
                    // EXCEIPTION 없이 진행 되면 TRUE

                    return Response.OK();
                }, json());

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
