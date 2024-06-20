package net.mamesosu.Utils;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class Discord {

    public static String getModeEmoji(int mode) {
        switch (mode) {
            case 0, 4, 8 -> {
                return "<:osu:1100702517119168562>";
            }
            case 1, 5 -> {
                return "<:taiko:1100702510152429588>";
            }
            case 2, 6 -> {
                return "<:fruits:1100702512681599089>";
            }
            case 3 -> {
                return "<:mania:1100702514501910630>";
            }
            default -> {
                return null;
            }
        }
    }

    public static String getOnlineEmoji(int userid) {
        String endpoint = "https://api.mamesosu.net/v1/get_player_status?id=" + userid;
        JsonNode jsonNode = Data.getJsonNode(endpoint);
        if(jsonNode.get("player_status").get("online").asBoolean()) {
            if (jsonNode.get("player_status").get("status").get("action").asInt() != 1) {
                return "<:online:1253033210657509539> ";
            } else {
                return "<:afk:1253149532636844073> ";
            }
        } else {
            return "<:offline:1253033236573978767> ";
        }
    }

    public static String getStatusEmoji(int status) {
        switch (status) {
            case 2, 3, 4 -> {
                return "<:ranked:1143570271974989914> ";
            }
            case 5 -> {
                return "<:loved:1100846331418914857> ";
            }
            default -> {
                return "<:graveyard:1100846539368316979> ";
            }
        }
    }

    public static String getRankEmoji(String grade) {

        switch (grade) {

            case "C" -> {
                return "<:rankC:1100664705997082755>";
            }
            case "B" -> {
                return "<:rankB:1100664703224664125>";
            }
            case "A" -> {
                return "<:rankA:1100664700905205900>";
            }
            case "S" -> {
                return "<:rankS:1100664689588969523>";
            }
            case "SH" -> {
                return "<:rankSH:1100664691270893678>";
            }
            case "X" -> {
                return "<:rankX:1100664694982836346>";
            }
            case "XH" -> {
                return "<:rankXH:1100664698237624430>";
            }
            case "D" -> {
                return "<:rankD:1100664707892920340>";
            }
            default -> {
                return ":x:";
            }
        }

    }
}
