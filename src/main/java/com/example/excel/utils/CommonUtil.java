package com.example.excel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    /**
     * generate unique id
     * @return
     */
    public static String getUUID(){
        String UUID = java.util.UUID.randomUUID().toString().trim().replace("-","");
        return UUID.toUpperCase();
    }

    /**
     * get the formatted date
     * @return
     */
    public static String getToday(){
        return new SimpleDateFormat("yyyy-mm-dd").format(new Date());
    }


//    public static <T> String toJson(T t) throws JsonProcessingException{
//        return OBJECT_MAPPER.get().writeValueAsString(t);
//    }
}
