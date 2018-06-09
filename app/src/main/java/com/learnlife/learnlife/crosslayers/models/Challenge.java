package com.learnlife.learnlife.crosslayers.models;

import java.util.Random;

public class Challenge {
    private boolean isSection = false;
    private String sectionTitle;
    private int idChallenge;
    private String _id;
    private String title;
    private String description;
    private String url_image;
    private String category;
    private int state = -1;

    public Challenge() {
    }

    public Challenge(boolean isSection, String sectionTitle) {
        this.isSection = isSection;
        this.sectionTitle = sectionTitle;
        state = -1;
    }

    public Challenge(String idChallenge, String title, String description, String url_image, int state) {
        this._id = idChallenge;
        this.title = title;
        this.description = description;
        this.url_image = url_image;
        this.category = category;
        this.state = state;
    }

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "Challenge{" +
                "idChallenge=" + idChallenge +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url_image='" + url_image + '\'' +
                ", category='" + category + '\'' +
                ", state=" + state +
                '}';
    }

    /**
     * Juste pour les tests.
     *
     * @return Challenge
     */
    public Challenge falseChallengeGenerator() {
        this.description = "Your life today is, basically, the sum of your  habits. We—mostly unconsciously—repeat 95 percent of our physical and mental";
        this.title = "Challenge of the day : RUN 5KM";
        this.idChallenge = new Random().nextInt();
        this.url_image = "http://netdoctor.cdnds.net/17/20/980x490/landscape-1495031594-two-women-running-along-road.jpg";
        this.category = "Sport";
        return this;
    }
}
