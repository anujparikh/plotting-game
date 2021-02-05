package org.unknown.plottingapp.hiddriver;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HidDriver implements Runnable {

    private final StringBuilder stringBuilder;
    private final List<String> messageBuffer;
    private final int delay;
    private Consumer<String> subscriber;

    public HidDriver(int delay) {
        this.delay = delay;
        this.stringBuilder = new StringBuilder();
        messageBuffer = new ArrayList<>(1);
    }

    public void init() {
        SerialPort compPort = SerialPort.getCommPorts()[0];
        compPort.openPort();
        SerialPortDataListener serialPortDataListener = new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[compPort.bytesAvailable()];
                compPort.readBytes(newData, newData.length);
                parse(newData);

            }
        };
        compPort.addDataListener(serialPortDataListener);
    }

    public void subscribe(Consumer<String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!messageBuffer.isEmpty()) {
                    String message = messageBuffer.get(0);
                    messageBuffer.remove(0);
                    if (message.split(",").length == 4) {
                        this.subscriber.accept(message);
                    }
                }
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void parse(byte[] serialData) {
        for (byte serialDatum : serialData) {
            char ch = (char) serialDatum;
            if (ch != '\n') {
                stringBuilder.append(ch);
            } else {
                messageBuffer.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    }
}
