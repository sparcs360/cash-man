package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class EBetslipSold extends EventBase {

    protected final String slipId;
    protected final BigDecimal amount;

    @JsonCreator
    public EBetslipSold(
            Object source,
            @JsonProperty("venueId") String venueId,
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("slipId") String slipId,
            @JsonProperty("amount") BigDecimal amount) {

        super(source, venueId, deviceId);

        this.slipId = slipId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timestamp", getTimestamp())
                .append("venueId", getVenueId())
                .append("deviceId", getDeviceId())
                .append("slipId", slipId)
                .append("amount", amount)
                .append("source", getSource())
                .toString();
    }
}
