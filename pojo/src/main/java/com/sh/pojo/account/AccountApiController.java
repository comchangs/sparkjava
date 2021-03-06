package com.sh.pojo.account;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.request.LoginRequest;
import com.sh.pojo.account.domain.form.request.PasswordForm;
import com.sh.pojo.account.domain.form.request.SignUpForm;
import com.sh.pojo.account.security.domain.Authentication;
import com.sh.pojo.account.security.domain.SessionData;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.network.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Session;

import java.util.List;

import static com.sh.pojo.config.network.response.JsonUtil.jsend;
import static com.sh.pojo.config.network.response.JsonUtil.json;
import static spark.Spark.*;

public class AccountApiController {

    public AccountApiController(AccountService accountService) {
        Logger log = LoggerFactory.getLogger(this.getClass());

        path("/api",() -> {
            path("/", () -> {
                post("/register", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(request.body(), SignUpForm.class);
                    boolean validSignUp = accountService.isValidSignUp(signUpForm);
                    if(!validSignUp) {
                        response.status(400);
                        return Response.FAIL(jsend("nickname or email", "Information is duplicated"));
                    }
                    if(!accountService.joined(signUpForm)) {
                        response.status(404); // 500
                        return Response.ERROR("Unable to communicate with database");
                    }
                    response.status(201);

                    return Response.OK(jsend("register", signUpForm));
                }, json());

                post("/login", "application/json", (request, response) -> {

                    Gson gson = new Gson();
                    LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);

                    SessionData sessionData = accountService.login(loginRequest);
                    return Response.OK();
                }, json());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    boolean result = accountService.signOut(id);

                    response.status(200);
                    return Response.OK();
                }, json());

            });  // end of path


            path("/accounts",() -> {
                get("/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    AccountResponse getAccount = accountService.getAccount(id);
                    return Response.OK(jsend("account", getAccount));
                }, json());

                // ?????????
                get("", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    Page page = gson.fromJson(request.body(), Page.class);
                    List<AccountAdminResponse> getAccount = accountService.getAccountList(page);
                    return Response.OK(jsend("accounts", getAccount));
                }, json());

                patch("", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    PasswordForm password = gson.fromJson(request.body(), PasswordForm.class);
                    accountService.modifyPassword(password);

                    // TODO session ??????
                    // EXCEIPTION ?????? ?????? ?????? TRUE

                    return Response.OK();
                }, json());

                // TODO ?????? ??? ?????? ????????? ?????? ?????????
 /*               put("/", "application/json", (request, response) -> {
                    Gson gson = new Gson();
                    AccountRequest account = gson.fromJson(request.body(), AccountRequest.class);
                    AccountResponse getAccount = accountService.modifyAccountInfo(account);

                    // TODO session??? ??????
                    // TODO   EXCEIPTION ?????? ?????? ?????? TRUE

                    return body(true, "account/id", getAccount, "");
                }, new JsonTransformer());*/

            });
        });

    }

}
