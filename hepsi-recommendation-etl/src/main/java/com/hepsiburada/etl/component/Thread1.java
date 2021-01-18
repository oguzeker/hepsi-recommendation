package com.hepsiburada.etl.component;

//@Component
public class Thread1 implements Runnable {

    //private ResettableCountDownLatch resettableCountDownLatch = new ResettableCountDownLatch();

//    @PostConstruct
//    public void init() throws Exception {
//        run();
//    }

//    @Scheduled(cron = "0/60 * * * * *")
    public void run() {

//            synchronized (lockingComponent) {
//
//
//                try {
//
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    lockingComponent.wait();
//                    System.out.println("ALDIMMMMMM: " + lockingComponent.isLocked());
//                } catch (InterruptedException e) {
//                    System.out.println("Could not wait");
//                    e.printStackTrace();
//                }

//                boolean isLocked = lockingComponent.isLocked();
//                while (true) {
//                    System.out.println("What time is it ?" + isLocked);
//                    try {
//                        while (lockingComponent.isLocked() == isLocked)
//                            wait();
//                        isLocked = lockingComponent.isLocked();
//                    } catch (Exception e) {
//                        System.out.println("Could not wait");
//                        e.printStackTrace();
//                    }
//                    System.out.println("It's "+lockingComponent.isLocked()+" -> Yepeeeee !!!! I can do something before asking the time again...");
//                    System.out.println("-----------------------------------");
//                }

//            }

    }

}
