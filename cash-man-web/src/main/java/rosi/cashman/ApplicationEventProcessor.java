package rosi.cashman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.TopicProcessor;
import reactor.core.scheduler.Schedulers;

@Component
public class ApplicationEventProcessor implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventProcessor.class);

    FluxProcessor<ApplicationEvent, ApplicationEvent> hotSource = TopicProcessor.create();

    ApplicationEventProcessor() {

    }

    public Flux<ApplicationEvent> getHotEventFlux() {

        return hotSource.publishOn(Schedulers.single());
        //return hotSource.publishOn(Schedulers.newSingle("Events", true));
        //return hotSource.publishOn(Schedulers.newElastic("Events", 10, true));
        //return hotSource.publishOn(Schedulers.parallel());
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        LOGGER.debug("Received: {}", event);
        hotSource.onNext(event);
    }
}
