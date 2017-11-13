package com.andy.sleepingbarber.messages;

import akka.actor.ActorRef;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class HairCutDone implements EventMessage {
    private ActorRef customer;

    public HairCutDone(ActorRef cus) {
        customer = cus;
    }

    public ActorRef getCustomer() {
        return customer;
    }
}
