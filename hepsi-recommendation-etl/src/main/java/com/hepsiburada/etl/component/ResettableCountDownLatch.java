package com.hepsiburada.etl.component;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Data
@Component
public class ResettableCountDownLatch {

    private final int initialCount = 1;
    private volatile CountDownLatch latch;

    public ResettableCountDownLatch() {
        latch = new CountDownLatch(initialCount);
    }

    public void reset() {
        latch = new CountDownLatch(initialCount);
    }

    public void countDown() {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return latch.await(timeout, unit);
    }

}
