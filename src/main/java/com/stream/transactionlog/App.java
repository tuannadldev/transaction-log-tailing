package com.stream.transactionlog;

/**
 * Main application
 *
 */
import com.stream.transactionlog.delivery.kafka.StreamRunner;

public class App {
    public static void main(String[] args) {
        StreamRunner.start();
    }
}
