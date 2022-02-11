package ru.gb;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT);
    private static CountDownLatch startCount = new CountDownLatch(CARS_COUNT);
    private static CountDownLatch finishCount = new CountDownLatch(CARS_COUNT);
    final static Lock lock = new ReentrantLock();

    public static Lock getLock() {
        return lock;
    }

    public static CountDownLatch getStartCount() {
        return startCount;
    }

    public static CountDownLatch getFinishCount() {
        return finishCount;
    }

    public static CyclicBarrier getStartBarrier() {
        return startBarrier;
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
//            for (int i = 0; i < cars.length; i++) {
//                new Thread(cars[i]).start();
//            }
            ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < cars.length; i++) {
            executorService.execute(cars[i]);
        }
        startCount.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        finishCount.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
//        if(lock.tryLock()) {
//            System.out.println();
//        }


        executorService.shutdown();

       // lock.unlock();
    }

}
