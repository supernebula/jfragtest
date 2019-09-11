package com.evol;

import com.evol.model.Man;
import com.evol.model.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class GenericTests {


    @Test
    public void staticFieldTest(){
        Node.num = 1;
        System.out.format("Node.num : %s", Node.num);
    }

    @Test
    public void staticMethodTest(){
        Node.num = 1;
        Node.doPrint("message1");
    }



}
