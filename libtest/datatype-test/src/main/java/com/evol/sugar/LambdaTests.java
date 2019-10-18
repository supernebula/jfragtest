package com.evol.sugar;

import com.evol.datatype.model.PayModeEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LambdaTests {


    @Test
    public void lambdaTest(){

        new Thread(() -> System.out.print("lambda")).run();
    }
}
