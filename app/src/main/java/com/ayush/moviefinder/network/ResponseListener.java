package com.ayush.moviefinder.network;

import com.ayush.moviefinder.model.ErrorObject;

/**
 * Created by ayushkedia on 22/05/16.
 */
public interface ResponseListener {
    void onResponse(Object pResponse);
    void onError(ErrorObject error);
}
