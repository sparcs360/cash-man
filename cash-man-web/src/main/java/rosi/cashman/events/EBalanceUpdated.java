package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class EBalanceUpdated extends EventBase {

    public final String deviceId;
    public final BigDecimal amount;
    public final BigDecimal balance;

    @JsonCreator
    public EBalanceUpdated(
            Object source,
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("balance") BigDecimal balance) {

        super(source);

        this.deviceId = deviceId;
        this.amount = amount;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("source", getSource())
                .append("timestamp", getTimestamp())
                .append("deviceId", deviceId)
                .append("amount", amount)
                .append("balance", balance)
                .toString();
    }
}
