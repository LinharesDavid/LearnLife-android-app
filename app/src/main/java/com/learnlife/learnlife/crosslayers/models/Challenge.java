package com.learnlife.learnlife.crosslayers.models;

import java.util.Random;

public class Challenge {
    private String _id;
    private String name;
    private String details;
    private String imageUrl;
    private int pointsGiven;
    private int duration;
    private String user;
    private String[] tags;

    private boolean isSection = false;
    private String sectionTitle;
    private String category;

    public Challenge() {
    }

    public Challenge(boolean isSection, String sectionTitle) {
        this.isSection = isSection;
        this.sectionTitle = sectionTitle;
    }

    public Challenge(String _id, String name, String details, String imageUrl, int pointsGiven, int duration, String user, String[] tags) {
        this._id = _id;
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.pointsGiven = pointsGiven;
        this.duration = duration;
        this.user = user;
        this.tags = tags;
    }

    public Challenge(String name, String details, String imageUrl, int pointsGiven, int duration, String user, String[] tags) {
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.pointsGiven = pointsGiven;
        this.duration = duration;
        this.user = user;
        this.tags = tags;
    }

    public Challenge(String name, String details, int pointsGiven, int duration, String user, String[] tags) {
        this.name = name;
        this.details = details;
        this.pointsGiven = pointsGiven;
        this.duration = duration;
        this.user = user;
        this.tags = tags;
    }

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

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Juste pour les tests.
     *
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

    public int getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(int pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
