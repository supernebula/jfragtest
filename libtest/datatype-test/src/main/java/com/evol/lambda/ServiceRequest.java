package com.evol.lambda;

public class ServiceRequest {

    public void Request(Callbackable callback, int num){
        callback.run(num);
    }
}
