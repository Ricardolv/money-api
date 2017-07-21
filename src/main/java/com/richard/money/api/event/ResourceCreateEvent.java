package com.richard.money.api.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class ResourceCreateEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long code;

    public ResourceCreateEvent(Object source, HttpServletResponse response, Long code) {
        super(source);
        this.response = response;
        this.code = code;

    }
}
