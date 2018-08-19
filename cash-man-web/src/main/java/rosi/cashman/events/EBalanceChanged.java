package rosi.cashman.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class EBalanceChanged extends EventBase {

    private final String accountId;
    private final BigDecimal amount;
    private final BigDecimal balance;

    @JsonCreator
    public EBalanceChanged(
            Object source,
            @JsonProperty("venueId") String venueId,
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("accountId") String accountId,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("balance") BigDecimal balance) {

        super(source, venueId, deviceId);

        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
    }

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timestamp", getTimestamp())
                .append("venueId", getVenueId())
                .append("deviceId", getDeviceId())
                .append("accountId", getAccountId())
                .append("amount", amount)
                .append("balance", balance)
                .append("source", getSource())
                .toString();
    }
}
