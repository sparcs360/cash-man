package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationEvent;

public abstract class EventBase extends ApplicationEvent {

    private final String venueId;

    private final String deviceId;
    protected EventBase(Object source, String venueId, String deviceId) {

        super(source);

        this.venueId = venueId;
        this.deviceId = deviceId;
    }

    @JsonIgnore
    @Override
    public Object getSource() {
        return super.getSource();
    }

    @JsonProperty("eventType")
    public String getEventType() {
        return this.getClass().getSimpleName();
    }

    @JsonProperty("venueId")
    public String getVenueId() {
        return venueId;
    }

    @JsonProperty("deviceId")
    public String getDeviceId() {
        return deviceId;
    }
}
