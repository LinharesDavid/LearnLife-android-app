package com.learnlife.learnlife.login.view;

public interface ILoginPresenter {
    void loginUser(String email, String password);
    void registerUser(String firstname, String lastname, String email, String password);
}
