package services;

import models.User;

public class userService {
    public User createUser(
        final int id,
        final String username,
        final String password
    ){
        String usernameStrip = username.strip();
        String passwordStrip = password.strip();

        if (id < 1 || usernameStrip.isBlank() || passwordStrip.isBlank()){
            throw new IllegalArgumentException("400: Invalid user data.");
        }
        User user = new User(id, usernameStrip, passwordStrip); // throws an error if the user info is invalid
        return user;
    }
}
