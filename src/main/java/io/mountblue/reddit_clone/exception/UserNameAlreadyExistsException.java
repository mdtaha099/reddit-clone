package io.mountblue.reddit_clone.exception;

public class UserNameAlreadyExistsException extends Exception{
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
