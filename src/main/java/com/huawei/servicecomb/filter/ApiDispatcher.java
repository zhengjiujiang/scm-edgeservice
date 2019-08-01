package com.huawei.servicecomb.filter;

import java.util.Map;

import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.edge.core.EdgeInvocation;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ApiDispatcher
 */
public class ApiDispatcher extends AbstractEdgeDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDispatcher.class);

    @Override
    public int getOrder() {
        return 10002;
    }

    @Override
    public void init(Router router) {
        String regex = "/";
        router.routeWithRegex(regex).handler(CookieHandler.create());
        router.routeWithRegex(regex).handler(createBodyHandler());
        router.routeWithRegex(regex).failureHandler(this::onFailure).handler(this::onRequest);

//        router.route().handler(this::onRequest);
    }

    protected void onRequest(RoutingContext context) {
        Map<String, String> pathParams = context.pathParams();
        String microserviceName = pathParams.get("param0");
        String path = "/" + pathParams.get("param1");

        context.response().putHeader("token",context.request().getHeader("token"));

        EdgeInvocation invoker = new EdgeInvocation() {
            /**
             * @throws Exception
             */
            // Authentication. Notice: adding context must after setContext or will override by network
            protected void setContext() throws Exception {
                super.setContext();
                LOGGER.info("context",context);
                // get session id from header and cookie for debug reasons
                String token = context.request().getHeader("token");
                LOGGER.info("token",token);
                if (token != null) {
                    this.invocation.addContext("token", token);
                } else {
                    Cookie tokenCookie = context.getCookie("token");
                    if (tokenCookie != null) {
                        this.invocation.addContext("token", tokenCookie.getValue());
                    }
                }
            }
        };

        invoker.init(microserviceName, context, path, httpServerFilters);
        invoker.edgeInvoke();
    }
}