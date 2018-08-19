package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.TopicProcessor;
import reactor.core.scheduler.Schedulers;
import rosi.cashman.events.EventBase;

@Component
public class ApplicationEventProcessor implements ApplicationListener<EventBase> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventProcessor.class);

    FluxProcessor<EventBase, EventBase> eventFeed = TopicProcessor.create();

    ApplicationEventProcessor() {

    }

    public Flux<EventBase> getEventFeed() {

        return eventFeed.publishOn(Schedulers.single());
        //return allEvents.publishOn(Schedulers.newSingle("Events", true));
        //return allEvents.publishOn(Schedulers.newElastic("Events", 10, true));
        //return allEvents.publishOn(Schedulers.parallel());
    }

    @Override
    public void onApplicationEvent(EventBase event) {
        LOGGER.debug("Received: {}", event);
        eventFeed.onNext(event);
    }
}
