package com.learnlife.learnlife.crosslayers.models;

import com.google.gson.annotations.SerializedName;
import com.learnlife.learnlife.tags.modele.Tag;

import java.util.ArrayList;

public class User {
    @SerializedName("_id")
    private String id;
    private String token;
    private String email;
    private String firstname;
    private String lastname;
    private String thumbnailUrl;
    private ArrayList<Tag> tags;
    private ArrayList<Badge> badges;

    public User(String token, String id, String email, String firstname, String lastname, String thumbnailUrl, ArrayList<Tag> tags, ArrayList<Badge> badges) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.thumbnailUrl = thumbnailUrl;
        this.tags = tags;
        this.badges = badges;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Badge> getBadges() {
        return badges;
    }

    public void setBadges(ArrayList<Badge> badges) {
        this.badges = badges;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getthumbnailUrl() {
        return thumbnailUrl;
    }

    public void setthumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
