package com.evol.lambda;

import org.junit.jupiter.api.Test;

public class LambdaTests {


    @Test
    public void lambdaTest(){

        new Thread(() -> System.out.print("lambda")).run();
    }

    @Test
    public void customeLambdaTest(){

        new ServiceRequest().Request((int num) -> System.out.print(num), 199);
    }
}
