package com.learnlife.learnlife.crosslayers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by davidlinhares on 19/06/2018.
 */

public class Badge {
    @SerializedName("_id")
    private String id;
    private String name;
    private String thumbnailUrl;
    private int achievementPoints;

    public Badge(String id, String name, String thumbnailUrl, int achievementPoints) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.achievementPoints = achievementPoints;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getthumbnailUrl() {
        return thumbnailUrl;
    }

    public void setthumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", achievementPoints=" + achievementPoints +
                '}';
    }
}
