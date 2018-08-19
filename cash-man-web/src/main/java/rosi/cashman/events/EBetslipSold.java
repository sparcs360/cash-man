package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class EBetslipSold extends EventBase {

    public final String deviceId;
    public final String slipId;
    public final BigDecimal amount;

    @JsonCreator
    public EBetslipSold(
            Object source,
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("slipId") String slipId,
            @JsonProperty("amount") BigDecimal amount) {

        super(source);

        this.deviceId = deviceId;
        this.slipId = slipId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("source", getSource())
                .append("timestamp", getTimestamp())
                .append("deviceId", deviceId)
                .append("slipId", slipId)
                .append("amount", amount)
                .toString();
    }
}
