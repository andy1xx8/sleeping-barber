package com.andy.sleepingbarber;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.andy.sleepingbarber.actor.BarberShop;
import com.andy.sleepingbarber.actor.Customer;
import com.andy.sleepingbarber.messages.CustomerWalkIn;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class SleepingBarber {
    public static void main(String[] args) {
        try {
            int numberOfChair = Integer.parseInt(args[0]);
            int numberOfCustomer = Integer.parseInt(args[1]);

            ActorSystem system = ActorSystem.create("sleeping-barber");
            ActorRef barberShop = system.actorOf(Props.create(BarberShop.class, numberOfChair));

            for (int i = 0; i < numberOfCustomer; i++) {
                ActorRef customer = system.actorOf(Props.create(Customer.class, "customer " + i),"customer_" + i);
                barberShop.tell(new CustomerWalkIn(customer), null);
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
            System.err.println("Invalid Argument.");
            ex.printStackTrace();
        }

    }
}
