package org.unknown.plottingapp.hiddriver.models;

public class HIDState {
    private StickPosition leftLR;
    private StickPosition leftUD;
    private StickPosition rightLR;
    private StickPosition rightUD;

    public HIDState() {
        leftLR = StickPosition.CENTER;
        leftUD = StickPosition.CENTER;
        rightLR = StickPosition.CENTER;
        rightUD = StickPosition.CENTER;
    }

    public void setState(int rightX, int rightY, int leftX, int leftY) {
        rightLR = getHorizontal(rightY);
        rightUD = getVertical(rightX);
        leftLR = getHorizontal(leftY);
        leftUD = getVertical(leftX);
    }

    public StickPosition getLeftLR() {
        return leftLR;
    }

    public StickPosition getLeftUD() {
        return leftUD;
    }

    public StickPosition getRightLR() {
        return rightLR;
    }

    public StickPosition getRightUD() {
        return rightUD;
    }

    @Override
    public String toString() {
        return "HIDState{" +
                "leftLR=" + leftLR +
                ", leftUD=" + leftUD +
                ", rightLR=" + rightLR +
                ", rightUD=" + rightUD +
                '}';
    }


    private StickPosition getHorizontal(int yPos) {
        if (yPos > 700) return StickPosition.RIGHT;
        if (yPos < 200) return StickPosition.LEFT;
        return StickPosition.CENTER;
    }

    private StickPosition getVertical(int xPos) {
        if (xPos > 700) return StickPosition.UP;
        if (xPos < 200) return StickPosition.DOWN;
        return StickPosition.CENTER;
    }


}
