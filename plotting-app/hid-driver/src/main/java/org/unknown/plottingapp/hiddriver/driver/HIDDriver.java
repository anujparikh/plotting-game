package org.unknown.plottingapp.hiddriver.driver;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.unknown.plottingapp.hiddriver.datatypes.HIDState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HIDDriver implements Runnable {

    private final StringBuilder stringBuilder;
    private final List<String> messageBuffer;
    private final int samplingDelay;
    private final int publishDelay;
    private final HIDState hidState;
    private Consumer<HIDState> subscriber;
    private boolean initialized;

    public HIDDriver(int samplingDelay, int publishDelay) {
        this.samplingDelay = samplingDelay;
        this.publishDelay = publishDelay;
        this.stringBuilder = new StringBuilder();
        this.messageBuffer = new ArrayList<>(1);
        this.hidState = new HIDState();
    }

    public void init() {
        SerialPort[] commPorts = SerialPort.getCommPorts();
        if (commPorts.length > 0) {
            SerialPort compPort = commPorts[0];
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
            initialized = true;
        }
    }

    public void subscribe(Consumer<HIDState> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void run() {
        int elapsedTime = 0;
        while (initialized) {
            try {
                setHidState();
                if (elapsedTime >= publishDelay && this.subscriber != null) {
                    this.subscriber.accept(this.hidState);
                }
                Thread.sleep(this.samplingDelay);
                elapsedTime += this.samplingDelay;
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
