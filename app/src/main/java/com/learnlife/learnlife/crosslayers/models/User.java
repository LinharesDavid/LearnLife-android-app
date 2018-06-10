package com.learnlife.learnlife.crosslayers.models;

public class User {
    private String id;
    private String token;
    private String email;
    private String firstname;
    private String lastname;
    private String thumbnail;

    public User(String token, String id, String email, String firstname, String lastname) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
