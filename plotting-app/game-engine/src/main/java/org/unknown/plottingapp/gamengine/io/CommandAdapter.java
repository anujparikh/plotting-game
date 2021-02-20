package org.unknown.plottingapp.gamengine.io;

import org.unknown.plottingapp.hiddriver.datatypes.HIDState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface CommandAdapter extends KeyListener {
    void hidCommandHandler(HIDState hidState);

    void keyPressed(KeyEvent e);
}
