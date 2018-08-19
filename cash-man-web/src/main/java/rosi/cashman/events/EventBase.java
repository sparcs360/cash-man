package rosi.cashman.events;

import org.springframework.context.ApplicationEvent;

public abstract class EventBase extends ApplicationEvent {

    protected EventBase(Object source) {
        super(source);
    }
}
