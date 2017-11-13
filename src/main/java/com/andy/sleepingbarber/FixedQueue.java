package com.andy.sleepingbarber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tale (anhlt) on 13/11/2017.
 */
public class FixedQueue<T> {

    private List<T> items;
    private int count;

    public FixedQueue(int size) {
        items = new ArrayList<>();
        count = size;
    }

    public boolean enqueue(T m) {
        if (items.size() >= count) {
            return false;
        } else {
            items.add(m);
            return true;
        }
    }


    public T dequeue() {
        if (items.isEmpty())
            return null;
        else {
            T m = items.get(0);
            items.remove(0);
            return m;
        }
    }

    public boolean isFull(){
        return items.size() >= count;
    }
}
