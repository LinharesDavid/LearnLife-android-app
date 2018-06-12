package com.learnlife.learnlife.home.view;

public class HomePresenter implements IHomePresenter{
    private IHomeView homeView;

    public HomePresenter(IHomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public void displayUserChallenge() {

    }

    @Override
    public void updateUserChallenge() {

    }
}
