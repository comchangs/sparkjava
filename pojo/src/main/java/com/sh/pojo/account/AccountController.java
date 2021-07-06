package com.sh.pojo.account;

import com.google.gson.Gson;
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
                    res.status(201);
                    System.out.println("main > result : "+joined+" data "+ signUpForm.toString());
                    return body(joined, "register",signUpForm, "error");
                }, new JsonTransformer());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));
                    boolean result = accountService.signOut(id);
                    System.out.println("main signOut > "+ id+" result : "+result);

                    if (result) {
                        response.redirect("/", 204);
                    }
//                    return (result)? Header.OK() : Header.error();
                    return body(result, "signOut", null, "error");
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
