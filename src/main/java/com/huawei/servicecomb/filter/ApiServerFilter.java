package com.huawei.servicecomb.filter;

import java.util.HashMap;
import java.util.Map;

import org.apache.servicecomb.common.rest.filter.HttpServerFilter;
import org.apache.servicecomb.core.Invocation;

import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.swagger.invocation.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApiServerFilter
 */
public class ApiServerFilter implements HttpServerFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiServerFilter.class);

    @Override
    public int getOrder() {
        return 10002;
    }

    @Override
    public Response afterReceiveRequest(Invocation var1, HttpServletRequestEx var2)
    {
        String token = var2.getHeader("token");
        Map<String, String> map = new HashMap<String, String>();
        map.put("token",token);
        var1.setContext(map);
        return null;
    }
}