package org.unknown.plottingapp.hiddriver;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.function.Consumer;

public class HidDriver implements Runnable {

    private static final int maxBufferSize = 10;
    private final StringBuilder stringBuilder;
    private final String[] messageBuffer;
    private int currentMessage;
    private Consumer<String> subscriber;

    public HidDriver() {
        this.stringBuilder = new StringBuilder();
        messageBuffer = new String[maxBufferSize];
        currentMessage = 0;
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

    private void parse(byte[] serialData) {
        stringBuilder.append(new String(serialData));
        int firstReturnPos = stringBuilder.indexOf("\n");
        if (firstReturnPos > 0) {
            messageBuffer[currentMessage] = stringBuilder.substring(0, firstReturnPos);
            currentMessage = (currentMessage + 1) % maxBufferSize;
            stringBuilder.delete(0, firstReturnPos);
        } else if (firstReturnPos == 0) {
            stringBuilder.deleteCharAt(0);
        }
    }

    public void subscribe(Consumer<String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.subscriber.accept(messageBuffer[currentMessage]);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
