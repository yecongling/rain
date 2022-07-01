package cn.soft.camel.module1;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpMessage;


/**
 * http的处理器（相当于执行了组件的功能而已）
 */
public class HttpProcessor implements Processor {

    /**
     * 处理消息
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        HttpMessage message = (HttpMessage)exchange.getIn();
        System.out.println("http处理器");
    }
}
