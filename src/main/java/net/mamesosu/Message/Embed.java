package net.mamesosu.Message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.mamesosu.Main;
import net.mamesosu.Utils.Bancho;
import net.mamesosu.Utils.Discord;
import net.mamesosu.Utils.Moji;
import net.mamesosu.Utils.Numeric;

import java.awt.*;
import java.sql.*;
import java.util.Date;

public abstract class Embed {

    public static EmbedBuilder getPPRecordMessage() {

        EmbedBuilder eb = new EmbedBuilder();

        Connection connection = Main.db.getConnection();
        PreparedStatement ps;
        ResultSet result;
        double pp = 0.0;

        eb.setTitle("**Mamestagram PP Record**", "https://web.mamesosu.net/leaderboard/std/performance");

        try {

            for(int i = 0; i <= 8; i++) {
                if(i == 7) continue;
                ps = connection.prepareStatement("select u.name as name, u.country as country, u.id as id, s.pp as pp from stats s join users u where mode = ? and u.id = s.id and u.priv % 2 != 0 order by pp desc limit 1");
                ps.setInt(1, i);
                result = ps.executeQuery();
                if(result.next()) {
                    String title = Discord.getModeEmoji(i) + " **" + Bancho.getModeToString(i);
                    String field = ":flag_" + result.getString("country") + ": **" + result.getString("name") + "** | ";

                    ps = connection.prepareStatement("select * from scores s join maps m where s.userid = ? and m.md5 = s.map_md5 and s.mode = ? and m.status in (2, 3) and s.status in (1, 2) order by pp desc limit 1");
                    ps.setInt(1, result.getInt("id"));
                    ps.setInt(2, i);
                    result = ps.executeQuery();

                    if(result.next()) {
                        title += " (" + Numeric.getRoundNumeric(result.getDouble("pp"), 2) + "pp) +" + Bancho.getModsToString(result.getInt("mods")) + "**";
                        field += "**[" + Moji.getSubString(result.getString("artist")) + " - " + Moji.getSubString(result.getString("title")) +
                                    " [" + Moji.getSubString(result.getString("version")) + "]](" + Bancho.getWebsiteLink(result.getInt("m.mode"), result.getInt("set_id"), result.getInt("m.id")) +") ** with ** " + Numeric.getRoundNumeric(result.getDouble("acc"), 2) +
                                    "%** (" + String.format("%,d", result.getInt("score")) + "score)" + "\n" +
                                    Discord.getRankEmoji(result.getString("grade"))  + " ▸ **" + String.format("%,d", result.getInt("s.max_combo")) + "x** / " + String.format("%,d", result.getInt("m.max_combo")) + "x\n" +
                                    "__[Replay](https://api.mamesosu.net/v1/get_replay?id=" + result.getInt("id") + ")__";


                        if (result.getDouble("pp") > pp) {
                            pp = result.getDouble("pp");
                            eb.setThumbnail("https://a.mamesosu.net/" + result.getInt("userid"));
                        }
                    }

                    eb.addField(title, field, false);
                }
            }

            eb.setColor(Color.YELLOW);
            eb.setImage("https://img.mamesosu.net/2?4");
            eb.setTimestamp(new Date().toInstant());

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eb;
    }
}
