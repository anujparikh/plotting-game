package org.unknown.plottingapp.hiddriver;

public class Main {

    public static void main(String[] args) {
        HidDriver driver = new HidDriver(10);
        driver.init();
        driver.subscribe(System.out::println);
        Thread thread = new Thread(driver);
        thread.start();
    }
}
