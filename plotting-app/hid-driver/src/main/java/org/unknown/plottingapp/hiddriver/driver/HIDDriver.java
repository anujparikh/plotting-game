package org.unknown.plottingapp.hiddriver.driver;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.unknown.plottingapp.hiddriver.models.HIDState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HIDDriver implements Runnable {

    private final StringBuilder stringBuilder;
    private final List<String> messageBuffer;
    private final int delay;
    private final HIDState hidState;
    private Consumer<HIDState> subscriber;

    public HIDDriver(int delay) {
        this.delay = delay;
        this.stringBuilder = new StringBuilder();
        this.messageBuffer = new ArrayList<>(1);
        this.hidState = new HIDState();
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

    public void subscribe(Consumer<HIDState> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void run() {
        while (true) {
            try {
                setHidState();
                this.subscriber.accept(this.hidState);
                Thread.sleep(this.delay);
            } catch (IndexOutOfBoundsException | NumberFormatException | InterruptedException | NullPointerException e) {
                // @pass
            }
        }
    }

    private void parse(byte[] serialData) {
        for (byte serialDatum : serialData) {
            char ch = (char) serialDatum;
            if (ch != '\n' && ch != '\r') {
                stringBuilder.append(ch);
            } else if (ch == '\n') {
                messageBuffer.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    }

    private void setHidState() {
        String message = messageBuffer.get(0);
        messageBuffer.remove(0);
        List<Integer> stickPositions = Arrays.stream(message.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        if (stickPositions.size() == 4) {
            this.hidState.setState(
                    stickPositions.get(0),
                    stickPositions.get(1),
                    stickPositions.get(2),
                    stickPositions.get(3));
        }
    }
}