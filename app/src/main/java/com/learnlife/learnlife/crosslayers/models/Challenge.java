package com.learnlife.learnlife.crosslayers.models;

import java.util.Random;

public class Challenge {
    private String _id;
    private String name;
    private String details;
    private String imageUrl;
    private String category;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        this.details = "Your life today is, basically, the sum of your  habits. We—mostly unconsciously—repeat 95 percent of our physical and mental";
        this.name = "Challenge of the day : RUN 5KM";
        this._id = String.valueOf(new Random().nextInt());
        this.imageUrl = "http://netdoctor.cdnds.net/17/20/980x490/landscape-1495031594-two-women-running-along-road.jpg";
        this.category = "Sport";
        return this;
    }
}
