package org.example;

import java.io.Serializable;

public class Task implements Serializable {
    public final int[] numbers;

    public Task(int[] numbers) {
        this.numbers = numbers;
    }
}
