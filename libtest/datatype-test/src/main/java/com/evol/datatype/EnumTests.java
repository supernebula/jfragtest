package com.evol.datatype;

import com.evol.datatype.model.PayModeEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnumTests {


    @Test
    public void test1(){

        System.out.format("PayModeEnum:  %s", PayModeEnum.ALIPAY);
        assertTrue(PayModeEnum.ALIPAY != PayModeEnum.BALANCE);


    }
}
