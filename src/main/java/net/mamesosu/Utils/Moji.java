package net.mamesosu.Utils;

public abstract class Moji {

    public static String getSubString(String str) {
        if(str.length() >= 16) {
            return str.substring(0, 16) + "...";
        } else {
            return str;
        }
    }
}
