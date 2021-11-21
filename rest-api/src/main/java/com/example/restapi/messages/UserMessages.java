package com.example.restapi.messages;

public class UserMessages {
    // Register
    public static final String REGISTRATION_NOT_SUCCESSFUL = "Registration was not successful.";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists.";

    // Login
    public static final String ACCOUNT_INACTIVE = "User account is not currently active!";
    public static final String INVALID_CREDENTIALS = "Invalid credentials!";
    public static final String INVALID_JWT = "Invalid JWT token!";
    public static final String EMPTY_PRINCIPAL = "Principal cannot be null!";

    // Binding
    public static final String USERNAME_VALIDATION = "Username length must be between 3 and 10 characters.";
    public static final String FIRST_NAME_VALIDATION = "Enter valid first name.";
    public static final String LAST_NAME_VALIDATION = "Enter valid last name.";
    public static final String EMAIL_VALIDATION = "Enter valid email address.";
    public static final String PASSWORD_VALIDATION = "Password length must be more than 3 characters long.";

}
