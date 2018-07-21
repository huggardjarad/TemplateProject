package com.qa.application.template.service;

import org.springframework.context.ApplicationEvent;

public class UserServiceEvent extends ApplicationEvent {
    public UserServiceEvent(Object source){super(source);}

    public String toString(){return "My UserService Event";}
}
