package com.evol.concurrent;

public class LockTests {



    public void syncTest()
    {


    }

    private void setA()
    {
        synchronized(LockTests.class){
            //something
        }
    }

    private void setB()
    {
        synchronized(this){
            //something
        }
    }

    private synchronized void setC(){
        //something
    }


}

