package com.huawei.servicecomb.controller;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestScmedgeservice {

        ScmedgeserviceDelegate scmedgeserviceDelegate = new ScmedgeserviceDelegate();


    @Test
    public void testhelloworld(){

        String expactReturnValue = "hello"; // You should put the expect String type value here.

        String returnValue = scmedgeserviceDelegate.helloworld("hello");

        assertEquals(expactReturnValue, returnValue);
    }

}