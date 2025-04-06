package org.example.interfaces_abstractClasses;

import java.io.IOException;

public interface PizzeriaInterfaceWithSerialization {
    void start();
    void stopWithSerialization(String filename) throws IOException;
    void acceptOrder(int orderId);
    void loadOldOrders(String filename) throws IOException, ClassNotFoundException;
}
