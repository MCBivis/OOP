package org.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class OrderQueueTest {

    @Test
    void testTakeOrderBlocksUntilOrderIsAdded() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean isOpen = new AtomicBoolean(true);
        OrderQueue orderQueue = new OrderQueue(latch, isOpen);

        Thread consumer = new Thread(() -> {
            Integer order = orderQueue.takeOrder();
            assertNotNull(order, "Заказ не должен быть null");
            assertEquals(42, order, "Ожидаемый заказ 42");
        });

        consumer.start();

        Thread.sleep(1000);

        orderQueue.addOrder(42);

        consumer.join();
    }
}