package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rosi.cashman.events.EventBase;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class EventFeedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventFeedHandler.class);

    private final ApplicationEventProcessor applicationEventProcessor;

    public EventFeedHandler(ApplicationEventProcessor applicationEventProcessor) {
        this.applicationEventProcessor = applicationEventProcessor;
    }

    public Mono<ServerResponse> allEvents(ServerRequest request) {

        LOGGER.debug("allEvents");

        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(applicationEventProcessor.getEventFeed()
                        .share()
                        .log("rosi.cashman.events.all"), EventBase.class);
    }

    public Mono<ServerResponse> eventsByVenue(ServerRequest request) {

        LOGGER.debug("eventsByVenue");

        String venueId = request.queryParam("venueId")
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(applicationEventProcessor.getEventFeed()
                        .filter(event -> event.getVenueId().equals(venueId))
                        .share()
                        .log("rosi.cashman.events.byVenueId"), EventBase.class);
    }
}
