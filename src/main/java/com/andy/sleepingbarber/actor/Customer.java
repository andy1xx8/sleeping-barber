package com.andy.sleepingbarber.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.andy.sleepingbarber.messages.*;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class Customer extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String name;

    public Customer(String n) {
        name = n;
    }

    public Receive createReceive() {
        return receiveBuilder().match(EnterShop.class, e -> {
            log.info(String.format("Customer %s enter the shop.", name));
        }).match(InBarberChair.class, e -> {
            log.info(String.format("Customer %s sit on the barber chair.", name));
        }).match(WaitingRoomFull.class, e -> {
            log.info(String.format("Customer %s,waiting room full - leaving.", name));
        }).match(SitInWaitingRoom.class, e -> {
            log.info(String.format("Customer %s is sitting in the waiting room.", name));
        }).match(ExitShop.class, e -> {
            log.info(String.format("Customer %s left the shop.", name));
        }).build();
    }

    public String getName() {
        return name;
    }
}
