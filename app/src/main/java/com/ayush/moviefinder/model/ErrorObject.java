package com.ayush.moviefinder.model;


/**
 * Created by ayushkedia on 21/05/16.
 */
public class ErrorObject {

    private String message;

    public ErrorObject(String errorMessage) {
        super();
        this.message = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        if (message != null) {
            return message;
        } else {
            return "Something went wrong!! Please try again later.";
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }

}

