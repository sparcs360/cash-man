package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

    public Mono<ServerResponse> eventFeed(ServerRequest serverRequest) {

        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(applicationEventProcessor.getEventFeed()
                        .share()
                        .log("rosi.cashman.events"), EventBase.class);
    }
}
