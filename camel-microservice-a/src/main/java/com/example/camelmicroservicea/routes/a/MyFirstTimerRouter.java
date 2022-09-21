package com.example.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
    private final GetCurrentTimeBean getCurrentTimeBean;

    public MyFirstTimerRouter(GetCurrentTimeBean getCurrentTimeBean) {
        this.getCurrentTimeBean = getCurrentTimeBean;
    }

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        from("timer:first-timer")           // null
//              .transform().constant("My Constant Message")
//              .transform().constant("Time now is" + LocalDateTime.now())
//              .bean("getCurrentTimeBean")
                .bean(getCurrentTimeBean, "getCurrentTime")
                .to("log:first-timer");     // database
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is" + LocalDateTime.now();
    }
}