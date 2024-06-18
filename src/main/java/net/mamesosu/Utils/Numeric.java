package net.mamesosu.Utils;

public abstract class Numeric {

    public static double getRoundNumeric(double base, int x) {

        return Math.round(base * Math.pow(10, x)) / Math.pow(10, x);
    }

    public static double getCeilNumeric(double base, int x) {
        return Math.ceil(base * Math.pow(10, x)) / Math.pow(10, x);
    }

    public static double getFloorNumeric(double base, int x) {
        return Math.floor(base * Math.pow(10, x)) / Math.pow(10, x);
    }


}
