package org.unknown.plottingapp.gamengine.datatypes;

public enum CardinalDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST;

    public static CardinalDirection getCardinalDirection(double heading) {
        float headingInDeg = (float) (Math.toDegrees(heading));
        CardinalDirection cardinalDirection = null;
        if (isClose(headingInDeg, 0.0f) || isClose(headingInDeg, 360.0f)) {
            cardinalDirection = NORTH;
        } else if (isClose(headingInDeg, 90.0f)) {
            cardinalDirection = EAST;
        } else if (isClose(headingInDeg, 180.0f)) {
            cardinalDirection = SOUTH;
        } else if (isClose(headingInDeg, 270.0f)) {
            cardinalDirection = WEST;
        } else if (headingInDeg > 0.0f && headingInDeg < 90.0f) {
            cardinalDirection = NORTH_EAST;
        } else if (headingInDeg > 90.0f && headingInDeg < 180.0f) {
            cardinalDirection = SOUTH_EAST;
        } else if (headingInDeg > 180f && headingInDeg < 270f) {
            cardinalDirection = SOUTH_WEST;
        } else if (headingInDeg > 270.0f && headingInDeg < 360f) {
            cardinalDirection = NORTH_WEST;
        }

        return cardinalDirection;
    }

    private static boolean isClose(float v1, float v2) {
        return Math.abs(v1 - v2) < 0.001;
    }
}
