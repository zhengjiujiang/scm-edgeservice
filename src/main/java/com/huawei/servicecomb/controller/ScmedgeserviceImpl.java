package com.huawei.servicecomb.controller;


import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.servicecomb.provider.rest.common.RestSchema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.CseSpringDemoCodegen", date = "2019-07-12T03:43:23.878Z")

@RestSchema(schemaId = "scmedgeservice")
@RequestMapping(path = "/rest", produces = MediaType.APPLICATION_JSON)
public class ScmedgeserviceImpl {

    @Autowired
    private ScmedgeserviceDelegate userScmedgeserviceDelegate;


    @RequestMapping(value = "/helloworld",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    public String helloworld( @RequestParam(value = "name", required = true) String name){

        return userScmedgeserviceDelegate.helloworld(name);
    }

}
