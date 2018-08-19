package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class EBalanceUpdated extends EventBase {

    public final BigDecimal amount;
    public final BigDecimal balance;

    @JsonCreator
    public EBalanceUpdated(
            Object source,
            @JsonProperty("venueId") String venueId,
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("balance") BigDecimal balance) {

        super(source, venueId, deviceId);

        this.amount = amount;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timestamp", getTimestamp())
                .append("venueId", getVenueId())
                .append("deviceId", getDeviceId())
                .append("amount", amount)
                .append("balance", balance)
                .append("source", getSource())
                .toString();
    }
}
