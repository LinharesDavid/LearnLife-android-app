package com.learnlife.learnlife;

public final class Constants {

    public static final String BASE_URL = "http://192.168.1.69:8080/";
    public static final String EXTENDED_URL_TAGS = "tags/";
    public static final String EXTENDED_URL_USERS = "users/";
    public static final String EXTENDED_URL_USERCHALLENGES = "userChallenges/";
    public static final String EXTENDED_URL_USERCHALLENGES_DECLINED = "declined/";
    public static final String EXTENDED_URL_USERCHALLENGES_ACCEPTED = "accept/";
    public static final String EXTENDED_URL_USERCHALLENGES_SUCCEED = "succeed/";
    public static final String EXTENDED_URL_USERCHALLENGES_FAILED = "failed/";
    public static final String EXTENDED_URL_USERCHALLENGES_LIST = "list/";
    public static final String EXTENDED_URL_LOGIN = "auth/login/";
    public static final String EXTENDED_URL_REGISTER = "users/";
    public static final String EXTENDED_URL_ALL_TAGS = "tags/";
    public static final String EXTENDED_URL_UPDATE_USERTAGS = "users/";
    public static final String EXTENDED_URL_BADGES = "badges/";

    public static final int CHALLENGE_DECLINED = 1;
    public static final int CHALLENGE_ACCEPTED = 2;
    public static final int CHALLENGE_FAILED = 3;
    public static final int CHALLENGE_SUCCEED = 4;

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String RESPONSE_KEY_USER_TOKEN = "token";
    public static final String RESPONSE_KEY_USER_ID = "_id";
    public static final String RESPONSE_KEY_USER_EMAIL = "email";
    public static final String RESPONSE_KEY_USER_FIRSTNAME = "firstname";
    public static final String RESPONSE_KEY_USER_LASTNAME = "lastname";
    public static final String RESPONSE_KEY_LOGIN_USER = "user";
    public static final String RESPONSE_KEY_USERCHALLENGES_CHALLENGE = "challenge";
    public static final String RESPONSE_KEY_USERCHALLENGES_ID = "_id";
    public static final String RESPONSE_KEY_USERCHALLENGES_NAME = "name";
    public static final String RESPONSE_KEY_USERCHALLENGES_DETAILS = "details";
    public static final String RESPONSE_KEY_USERCHALLENGES_IMAGE = "imageUrl";
    public static final String RESPONSE_KEY_USERCHALLENGES_STATE = "state";

    public static final String REQUEST_KEY_LOGIN_EMAIL = "email";
    public static final String REQUEST_KEY_LOGIN_PASSWORD = "password";

    public static final String REQUEST_KEY_REGISTER_EMAIL = "email";
    public static final String REQUEST_KEY_REGISTER_PASSWORD = "password";
    public static final String REQUEST_KEY_REGISTER_FIRSTNAME = "firstname";
    public static final String REQUEST_KEY_REGISTER_LASTNAME = "lastname";

}
