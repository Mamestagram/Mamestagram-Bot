package net.mamesosu.Utils;

import java.util.ArrayList;

public abstract class Bancho {

    public static String getWebsiteLink(int mode, int mapsetID, int mapID) {
        String modeString = switch (mode) {
            case 0, 4, 8 -> "osu";
            case 1, 5 -> "taiko";
            case 2, 6 -> "fruits";
            case 3 -> "mania";
            default -> throw new IllegalArgumentException("Invalid mode: " + mode);
        };

        return String.format("https://osu.ppy.sh/beatmapsets/%d#%s/%d", mapsetID, modeString, mapID);
    }

    public static String getModsToString(int n) {
        ArrayList<String> mod = new ArrayList<>();
        final String[] mods = {"NF", "EZ", "TS", "HD", "HR", "SD", "DT", "RX", "HT", "NC", "FL", "", "SO", "AP", "PF", "4K", "5K", "6K", "7K", "8K", "FD", "RD", "CM", "TG", "9K", "KC", "1K", "3K", "2K", "V2", "MR"};
        StringBuilder rMods = new StringBuilder();

        for (int i = 30; i >= 0; i--) {
            if (i != 2 && i != 11 && n >= Math.pow(2, i)) {
                switch (i) {
                    case 14 -> n -= Math.pow(2, 5);
                    case 9 -> n -= Math.pow(2, 6);
                }
                mod.add(mods[i]);
                n -= Math.pow(2, i);
            }
        }

        for (String s : mod) {
            rMods.append(s);
        }

        if(!rMods.toString().equals("")) {
            return rMods.toString();
        } else {
            return "NM";
        }
    }

    public static int getModeToBit(String mode) {
        switch (mode) {
            case "vn!std" -> {
                return  0;
            }
            case "vn!taiko" -> {
                return 1;
            }
            case "vn!ctb" -> {
                return 2;
            }
            case "vn!mania" -> {
                return 3;
            }
            case "rx!std" -> {
                return 4;
            }
            case "rx!taiko" -> {
                return 5;
            }
            case "rx!ctb" -> {
                return 6;
            }
            case "ap!std" -> {
                return 8;
            }
            default -> {
                return 7; //not happen
            }
        }

    }

    public static String getModeToString (int mode) {
        switch (mode) {
            case 0 -> {
                return "vn!std";
            }
            case 1 -> {
                return "vn!taiko";
            }
            case 2 -> {
                return "vn!ctb";
            }
            case 3 -> {
                return "vn!mania";
            }
            case 4 -> {
                return "rx!std";
            }
            case 5 -> {
                return "rx!taiko";
            }
            case 6 -> {
                return "rx!ctb";
            }
            case 8 -> {
                return "ap!std";
            }
            default -> {
                return "Unknown!";
            }
        }
    }
}
