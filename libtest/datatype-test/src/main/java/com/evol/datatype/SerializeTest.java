package com.evol.datatype;

import com.alibaba.fastjson.JSON;
import com.evol.datatype.model.GenderEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.evol.datatype.model.SerialUser;

import java.util.Date;

public class SerializeTest {

    private SerialUser createUser(){
        SerialUser user = new SerialUser();
        user.setName("zhanshan");
        user.setAge(18);
        user.setCreateTime(new Date());
        user.setEnabled(true);
        user.setMoney(100.01);
        user.setGender(GenderEnum.MALE);
        return user;
    }

    @Test
    public void fastToJsonTest(){
        SerialUser user = createUser();
        user.setName("zhanshan1");
        user.setAge(19);
        user.setCreateTime(new Date());
        user.setEnabled(false);
        user.setMoney(109.1);
        user.setGender(GenderEnum.FEMALE);
        String jsonStr = JSON.toJSONString(user);
        System.out.println(jsonStr);

    }

    @Test
    public void fastJsonToObjectTest(){

        //String jsonStr = "{\"name\":\"zhanshan\",\"age\":18,\"money\":100.01,\"enabled\":true,\"gender\":\"MALE\",\"createTime\":\"2019-09-25 12:25:29\"}";
        String jsonStr = JSON.toJSONString(createUser());

        System.out.println(jsonStr);
        SerialUser user = JSON.parseObject(jsonStr, SerialUser.class);
        assertTrue(user.getName().equals("zhanshan")
                && user.getAge() == 18
                && user.getMoney() == 100.01
                && user.getGender() == GenderEnum.MALE);
        System.out.print(user);


    }

    @Test
    public void JackToJsonTest(){
        SerialUser user = createUser();
        user.setName("zhanshan");
        user.setAge(18);
        user.setCreateTime(new Date());
        user.setEnabled(true);
        user.setMoney(100.01);
        user.setGender(GenderEnum.MALE);

        ObjectMapper objMapper = new ObjectMapper();

        try {
            String jsonStr = objMapper.writeValueAsString(user);
            System.out.println(jsonStr);

        }catch (Exception ex){
            System.out.println(ex);
        }
    }




}
