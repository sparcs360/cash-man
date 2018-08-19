package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rosi.cashman.events.EBalanceUpdated;
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

        LOGGER.debug("recordSlipSale(deviceId={}, slipId={}, amount={})", deviceId, slipId, amount);

        applicationEventPublisher.publishEvent(new EBetslipSold(this, venueId, deviceId, slipId, amount));
        applicationEventPublisher.publishEvent(new EBalanceUpdated(this, venueId, deviceId, amount, BigDecimal.ZERO));

        return accepted().build();
    }

}
