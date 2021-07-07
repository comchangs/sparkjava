package com.sh.pojo.account.security;

import com.google.gson.Gson;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.network.JsonTransformer;
import com.sh.pojo.config.network.ResponseBody;

import static spark.Spark.*;

public class UserApi {

    public UserApi (){
        path("/api",() -> {

                post("/register", "application/json", (req, res) -> {
                    Gson gson = new Gson();
                    SignUpForm signUpForm = gson.fromJson(req.body(), SignUpForm.class);

                    res.status(201);
                    return ResponseBody.of(true, "register",signUpForm, "error");
                }, new JsonTransformer());

                delete("/signOut/:id", (request, response) -> {
                    Long id = Long.parseLong(request.params(":id"));

                    return ResponseBody.of(true, "register",null, "error");
                }, new JsonTransformer());

            });  // end of path
    }
}
