package com.evol.datatype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class StringConvertTests {

    @Test
    public void string2Int()
    {
        String str = "14a";
        int num1 = Integer.parseInt(str);
        System.out.format("num1 = %d", num1);
        int num2 = Integer.getInteger(str);

        System.out.format("num %d", num2);

        assertTrue(str instanceof String);

    }

    @Test
    public void stringValueOf()
    {
        String str = "14";
        Integer num2 = Integer.valueOf(str);
        Object objNum = num2;
        //assertNotNull(num2);

        System.out.format("num2 = %d", num2);
        System.out.format("\n num2 = class %s", objNum.getClass());

        //assertNotNull(num2);
        assertTrue(str instanceof String);

    }
}
