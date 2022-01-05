package cn.soft.camel.module1;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 路由规则
 */
public class HelloWorld extends RouteBuilder {

    public static void main(String[] args) throws Exception {


        DefaultCamelContext context = new DefaultCamelContext();
        context.start();

        context.addRoutes(new HelloWorld());

        synchronized (HelloWorld.class) {
            HelloWorld.class.wait();
        }
    }

    @Override
    public void configure() throws Exception {
        from("jetty:http://127.0.0.1:8080/helloWorld").process(new HttpProcessor()).to("log:helloWorld?showExchangeId=true");
    }
}
