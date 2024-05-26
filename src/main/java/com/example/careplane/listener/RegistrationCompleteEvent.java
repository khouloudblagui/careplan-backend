package com.example.careplane.listener;

import com.example.careplane.entity.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Admin user;
    private String applicationUrl;

    public RegistrationCompleteEvent(Admin user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
