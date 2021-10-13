package com.example.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPojoRes {
    private static final long seriaVersionUID = -2145503717390503506L;

    @ExcelProperty(value = "ID" , index = 0)
    private String id;

    @ExcelProperty(value = "name" , index = 1)
    private String name;

    public UserPojoRes(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserPojoRes(){

    }
}

