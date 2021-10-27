package com.vicky.jabazon.Util;

public class Util {

    //Database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jabazon_db";
    public static final String USER_TABLE_NAME = "users";
    public static final String VACCINE_TABLE_NAME = "vaccines";
    public static final String HEALTHCARE_TABLE_NAME = "healthcare";

    //User
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //Vaccines
    public static final int KEY_VACCINEID = 1;
    public static final String KEY_VACCINENAME = "vaccine name";
    public static final String KEY_MANUFACTURER = "manufacturer";
    public static final int KEY_QTYAVAIL = 1;
    public static final int KEY_QTYADMINISTERED = 1;

    //Healthcare Centres
    public static final String KEY_HCNAME = "healthcare name";
    public static final String KEY_ADDRESS = "centre address";


    public static User USER;
    public static Vaccines VACCINES;
    public static Healthcare HEALTHCARECENTRES;
}
