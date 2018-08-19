package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rosi.cashman.events.EBalanceChanged;
import rosi.cashman.events.EBetslipSold;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.server.ServerResponse.accepted;

@Component
public class TransactionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHandler.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public TransactionHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Mono<ServerResponse> recordSlipSale(ServerRequest request) {

        String venueId = request.queryParam("venueId")
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        String deviceId = request.queryParam("deviceId")
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        String slipId = request.queryParam("slipId")
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        BigDecimal amount = new BigDecimal(request.queryParam("amount")
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST)));

        LOGGER.debug("recordSlipSale(venueId={}, deviceId={}, slipId={}, amount={})", venueId, deviceId, slipId, amount);

        // TODO: Find the accountId of the "cash box" inside the device
        String accountId = String.format("A/C-%s", deviceId);

        applicationEventPublisher.publishEvent(new EBetslipSold(this, venueId, deviceId, slipId, amount));
        applicationEventPublisher.publishEvent(new EBalanceChanged(this, venueId, deviceId, accountId, amount, BigDecimal.ZERO));

        return accepted().build();
    }

}
