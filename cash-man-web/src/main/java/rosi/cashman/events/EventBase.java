package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationEvent;

public abstract class EventBase extends ApplicationEvent {

    protected EventBase(Object source) {
        super(source);
    }

    @JsonProperty("eventName")
    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    @JsonIgnore
    @Override
    public Object getSource() {
        return super.getSource();
    }
}
