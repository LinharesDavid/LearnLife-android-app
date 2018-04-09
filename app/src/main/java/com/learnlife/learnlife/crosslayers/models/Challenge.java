package com.learnlife.learnlife.crosslayers.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Challenge {
    private int idChallenge;
    private String title;
    private String description;
    private String url_image;
    private String category;

    public int getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(int idChallenge) {
        this.idChallenge = idChallenge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * Juste pour les tests.
     * @return Challenge
     */
    public Challenge falseChallengeGenerator(){
        this.description = "Your life today is, basically, the sum of your  habits. We—mostly unconsciously—repeat 95 percent of our physical and mental";
        this.title = "Challenge of the day : RUN 5KM";
        this.idChallenge = new Random().nextInt();
        this.url_image = "http://netdoctor.cdnds.net/17/20/980x490/landscape-1495031594-two-women-running-along-road.jpg";
        this.category = "Sport";
        return this;
    }
}
