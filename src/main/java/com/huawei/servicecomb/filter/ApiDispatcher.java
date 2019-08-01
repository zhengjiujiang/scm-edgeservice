package com.huawei.servicecomb.filter;

import java.util.Map;

import com.netflix.config.DynamicPropertyFactory;
import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.edge.core.DefaultEdgeDispatcher;
import org.apache.servicecomb.edge.core.EdgeInvocation;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.apache.servicecomb.swagger.invocation.context.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ApiDispatcher
 */
public class ApiDispatcher extends DefaultEdgeDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDispatcher.class);
    private static final String KEY_ENABLED = "servicecomb.http.dispatcher.edge.api.enabled";

    @Override
    public int getOrder() {
        return 11;
    }



    @Override
    public boolean enabled() {
        return DynamicPropertyFactory.getInstance().getBooleanProperty(KEY_ENABLED, false).get();
    }

    @Override
    protected void onRequest(RoutingContext context) {
        Map<String, String> pathParams = context.pathParams();
        String microserviceName = pathParams.get("param0");
        String path = "/" + pathParams.get("param1");

        InvocationContext invocationContext = ContextUtils.getInvocationContext();
        context.response().putHeader("token",context.request().getHeader("token"));

        EdgeInvocation invoker = new EdgeInvocation();
        LOGGER.info("context",context);
        // get session id from header and cookie for debug reasons
        String token = "eyJhbGciOiJIUzUxMiJ9.ZXlKNmFYQWlPaUpFUlVZaUxDSmhiR2NpT2lKa2FYSWlMQ0psYm1NaU9pSkJNVEk0UTBKRExVaFRNalUySW4wLi5Qc2VYcUpGZDFub1c3aTJkYnJwcEZBLm9uOS1lOHhUcEVXZEJKMjVlSmNvZzBrVjJwdnJxQ0x2cUlsSkltbXNBVVg0dlBPU3BucUR6bFBBYXhqX3k3ZUJ0T1E0eDNWbFpnWnNkZ20wc0owUnhhSVVWZjJoZEFIelA2U2IyZVl3U2JsLTEwRHFLZ1J0QzVwaG9YdHhnTkZ1SGNWNnh3Zk80aWltNmREdHJtcDBWNWczdWFwWms3Q0ZGRkNnbW5KYXhyanpGQmg1LVdWbDN5WmZIZEdrZ09lSFNlVkJTSUtfVFBlTmd3M3FuOWdBMlRNMWJtYXdGYTVQSkJsUlZENExtY0RDOVFUZzFYekRXRlNjN1RpbGN3ODl1TExoMjZXWklYN0lDWDBGUkN2LUVlUnQtWi1xRGE4bm5JSkdFcmRBYzNiNkFnYmdVUDcxNkVFUFVhZTQ5RlhTcEU2S0lqZWFKSUgxLXpDc0ZhWUt2eXE2VFFxcnFFaVRCaUJwX3NoWENnQW1JbmEwY3N1OEV6alVydnRRRDhqYmhTZjluRURDYkN4MXk4YmZIdFZiSXdhcGc4bXl1OERyaDQ3aDVKU3haZ2dWQkpuSTFkZTFoRkVyUm1IcW5BVEN3cHJnb2ZBdnc5dTlXMjcwUVpERFJrVll5czNmc2o4Q20zdXhab2h4aGdzSFNlajJzc19HbFRYMFE4VlJZYkdaOVFSNDJMTjBVTVhpYTBYTlVNQV9pSUF4X2RBRTRyWml5MkRkQVg5SmtNakI0RS1VNmIyWDNDODZ6b2tuNmdwSy4zXzEzXzNHN2NNYzJjMUIyUERGQ1l3.8Pbh98r-KOUNPV6MKj2tR4DpQzlmovStlIdgoV2TfR_nbZmxv1fFEsTB1tJcL4wVxrjJ0jGkY8cWIc0u7U7dKA";//context.request().getHeader("token");
        LOGGER.info("token",token);
        if (token != null) {
            invocationContext.addContext("token", token);
        } else {
            Cookie tokenCookie = context.getCookie("token");
            if (tokenCookie != null) {
                invocationContext.addContext("token", tokenCookie.getValue());
            }
        }

        context.request().headers().add("token", token);
        super.onRequest(context);
//        invoker.init(microserviceName, context, path, httpServerFilters);
//        invoker.edgeInvoke();
    }
}