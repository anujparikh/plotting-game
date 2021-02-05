package org.unknown.plottingapp.hiddriver;

import org.unknown.plottingapp.hiddriver.driver.HIDDriver;

public class Main {

    public static void main(String[] args) {
        HIDDriver driver = new HIDDriver(10);
        driver.init();
        driver.subscribe(System.out::println);
        Thread thread = new Thread(driver);
        thread.start();
    }
}
