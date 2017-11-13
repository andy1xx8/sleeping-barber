package com.andy.sleepingbarber.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.andy.sleepingbarber.FixedQueue;
import com.andy.sleepingbarber.messages.*;

import java.util.Optional;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class BarberShop extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private int numberOfWaitingChair;
    private FixedQueue<ActorRef> waitingRoom;
    private Optional<ActorRef> chair = Optional.empty();
    private ActorRef barber;

    public BarberShop(int maxChair) {
        numberOfWaitingChair = maxChair;
        waitingRoom = new FixedQueue<>(numberOfWaitingChair);
        barber = context().actorOf(Props.create(Barber.class));

    }

    public Receive createReceive() {
        return receiveBuilder().match(CustomerWalkIn.class, e -> {
            customerComming(e);
        }).match(HairCutDone.class, e -> {
            hairCutDone(e);
        }).build();
    }

    private void customerComming(CustomerWalkIn e) {
        ActorRef customer = e.getCustomer();
        customer.tell(new EnterShop(), getSelf());

        if (chair.isPresent() == false) {
            chair = Optional.of(customer);
            customer.tell(new InBarberChair(), getSelf());
            barber.tell(new CutHair(customer), getSelf());
        } else if (waitingRoom.isFull() == false) {
            waitingRoom.enqueue(customer);
            customer.tell(new SitInWaitingRoom(), getSelf());
        } else {
            customer.tell(new WaitingRoomFull(), getSelf());
            customer.tell(new ExitShop(), getSelf());
        }
    }

    private void hairCutDone(HairCutDone e) {
        ActorRef customer = chair.get();
        chair = Optional.empty();
        customer.tell(new ExitShop(), getSelf());

        ActorRef nextCustomer = waitingRoom.dequeue();
        if (nextCustomer != null) {
            chair = Optional.of(nextCustomer);
            nextCustomer.tell(new InBarberChair(), getSelf());
            barber.tell(new CutHair(nextCustomer), getSelf());
        } else {
            log.info("There is no customer in the shop.The barber will go to sleep.");
        }

    }
}
