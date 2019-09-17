package com.evol.annotate;

import com.evol.annotate.model.Column;
import com.evol.annotate.model.Entity;
import com.evol.annotate.model.Table;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotateToSqlTest {

    @Test
    public void GenSqlTest(){
        StringBuilder sb = new StringBuilder();
        Class c = Entity.class;

        boolean anTableExist = c.isAnnotationPresent(Table.class);
        assertTrue(anTableExist);
        Table t = (Table)c.getAnnotation(Table.class);
        String tableName = t.name();

        ArrayList<String> columnNames = new ArrayList<>();
        Field[] fieldArr = c.getDeclaredFields();
        for (Field field : fieldArr){
            boolean fieldExist = field.isAnnotationPresent(Column.class);
            if(!fieldExist) continue;
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            columnNames.add(columnName);
        }


        //String sql = String.format("SELECT %s FROM %s", columnNames, tableName);
        String sql = String.format("SELECT %s FROM %s", StringUtils.join(columnNames, ','), tableName);
        System.out.println(sql);

    }

}
