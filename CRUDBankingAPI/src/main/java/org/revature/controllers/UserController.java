package org.revature.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import org.revature.models.User;
import org.revature.services.UserServiceImpl;

import java.sql.SQLException;

public class UserController {
    UserServiceImpl userServiceImpl;
    Javalin api;

    public UserController(UserServiceImpl userServiceImpl, Javalin api) {
        this.userServiceImpl =userServiceImpl;
        this.api = api;

        api.post("/register", this::postNewUserHandler);
        api.post("/login", this::postLoginHandler);
        api.put("/users/{user_id}", this::putUserHandler);
    }

    private void postNewUserHandler(Context ctx) {
        User endUserInput = ctx.bodyAsClass(User.class);
        User newUser = userServiceImpl.registerUser(endUserInput);

        if (newUser != null) {
            ctx.json(newUser);
            ctx.status(200);
        } else {
            ctx.status(400);
            ctx.json("Invalid credentials");
        }
    }

    private void postLoginHandler(Context ctx) {
        User endUserInput = ctx.bodyAsClass(User.class);
        User user = null;
        try {
            user = userServiceImpl.login(endUserInput.getUsername(), endUserInput.getPassword());
            ctx.json(user);
            Cookie cookie = new Cookie("Credentials", user.getUsername());
            ctx.cookie(cookie);
            ctx.status(200);
        } catch (RuntimeException e){
            ctx.status(401);
            ctx.json("Invalid username and/or password");
        }
    }

    private void putUserHandler(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("user_id"));
        User endUserInput = ctx.bodyAsClass(User.class);

        User updatedUser = userServiceImpl.updateUser(userId, endUserInput);
        ctx.json(updatedUser);
        ctx.status(200);
    }
}
