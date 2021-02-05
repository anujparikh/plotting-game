package org.unknown.plottingapp.hiddriver;

public class Main {

    public static void main(String[] args) {
        HidDriver driver = new HidDriver();
        driver.init();
        driver.subscribe((x) -> {
            if (x != null) {
                System.out.println(x);
            }
        });
        Thread thread = new Thread(driver);
        thread.start();
    }
}
