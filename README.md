# SLEEPING BARBER

This is a demo project for a famous problem called `Sleeping Barber`.
By using Akka's actor model, it solves the problems which we often face to, such as: race-condition, synchronization.


## How to run this sample

1. Build `jar` file.
2. Run with the following command
```$ssh
java -jar sleeping-barber.jar <chair> <customers>
```
Where:
- `chair` is the number of chair in waiting room.
- `customer`: the number of customers we wanna generate. Every 2 seconds, 1 customer will be created to demonstrate the action that a customer walks into the barber shop.
