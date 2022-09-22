package com.example.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
    private final GetCurrentTimeBean getCurrentTimeBean;
    private SimpleLoggingProcessingComponent loggingComponent;

    public MyFirstTimerRouter(GetCurrentTimeBean getCurrentTimeBean, SimpleLoggingProcessingComponent loggingComponent) {
        this.getCurrentTimeBean = getCurrentTimeBean;
        this.loggingComponent = loggingComponent;
    }

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        from("timer:first-timer")           // null
                .log("${body}")
                .transform().constant("My Constant Message")
                .log("${body}")
//              .transform().constant("Time now is" + LocalDateTime.now())
//              .bean("getCurrentTimeBean")

                //Processing
                //Transformation

                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}")
                .bean(loggingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");     // database
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is" + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

class SimpleLoggingProcessor implements Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}

