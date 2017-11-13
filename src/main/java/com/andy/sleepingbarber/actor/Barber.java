package com.andy.sleepingbarber.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.andy.sleepingbarber.messages.*;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class Barber extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public Receive createReceive() {
        return receiveBuilder().match(CutHair.class, e -> {
            cutHair(e);
        }).build();
    }

    private void cutHair(CutHair e) {
        ActorRef customer = e.getCustomer();
        try {
            log.info("Cutting in 5s...");
            Thread.sleep(5);
            log.info("Hair cut Done.");
        } catch (InterruptedException e1) {
            //Do nothing
        }
        getSender().tell(new HairCutDone(customer), getSelf());

    }
}
