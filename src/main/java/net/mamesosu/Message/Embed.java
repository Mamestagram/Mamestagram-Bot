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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                    String field = Discord.getOnlineEmoji(result.getInt("id"))  +  ":flag_" + result.getString("country") + ": **" + result.getString("name") + "** | ";

                    ps = connection.prepareStatement("select * from scores s join maps m where s.userid = ? and m.md5 = s.map_md5 and s.mode = ? and m.status in (2, 3) and s.status in (1, 2) order by pp desc limit 1");
                    ps.setInt(1, result.getInt("id"));
                    ps.setInt(2, i);
                    result = ps.executeQuery();

                    if (result.next()) {
                        title += " (" + Numeric.getRoundNumeric(result.getDouble("pp"), 2) + "pp) +" + Bancho.getModsToString(result.getInt("mods")) + "**";
                        field += Discord.getStatusEmoji(2) + "**[" + Moji.getSubString(result.getString("artist")) + " - " + Moji.getSubString(result.getString("title")) +
                                " [" + Moji.getSubString(result.getString("version")) + "]](" + Bancho.getWebsiteLink(result.getInt("m.mode"), result.getInt("set_id"), result.getInt("m.id")) + ") ** with ** " + Numeric.getRoundNumeric(result.getDouble("acc"), 2) +
                                "%** (" + String.format("%,d", result.getInt("score")) + "score)" + "\n" +
                                Discord.getRankEmoji(result.getString("grade")) + " ▸ **" + String.format("%,d", result.getInt("s.max_combo")) + "x** / " + String.format("%,d", result.getInt("m.max_combo")) + "x\n" +
                                "__[Replay](https://api.mamesosu.net/v1/get_replay?id=" + result.getInt("id") + ")__";


                        if (result.getDouble("pp") > pp) {
                            pp = result.getDouble("pp");
                            eb.setThumbnail("https://a.mamesosu.net/" + result.getInt("userid"));
                            eb.setImage("https://assets.ppy.sh/beatmaps/" + result.getInt("set_id") + "/covers/cover.jpg");
                        }
                    }

                    eb.addField(title, field, false);
                }
            }

            eb.setColor(Color.YELLOW);
            eb.setTimestamp(new Date().toInstant());

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eb;
    }

public static List<EmbedBuilder> getRuleJAMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();

        EmbedBuilder e = new EmbedBuilder();

        e.setTitle("**Welcome to Mamestagram!**");
        e.addField("**Rule / ルール**",
                """
                        この度は、Mamestagramへ参加していただきありがとうございます。
                        以下のルールはMamestagramで遊ぶ上で必ず守っていただく必要のあるルールです。
                        ルールはプライベートサーバーとDiscordサーバーの2点が存在します。
                        どちらも利用する上でかなり重要なものですので、よく読んだ上で参加をお願いします。
                        ルールを守らない場合、運営は警告なしでアカウントを凍結することがあります
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Prohibited acts on the Discord / Discordサーバーの禁止行為**",
                """
                        1. メンバーに迷惑をかける行為 (スパム等や乗っ取りURLの添付など)
                        2. メンバーの端末に負荷をかけるようなメッセージの送信
                        3. 差別用語の使用、人種、性別、宗教、信条、門地、同性愛の嫌悪や差別に値するあらゆる行為
                        4. 節度の超えた暴言を発言する行為
                        5. 他ユーザーの悪質ななりすまし行為
                        6. チートやウイルス、自作ソフトウェアなどを配布する行為
                        7. NSFWコンテンツの発言、添付行為
                        8. 運営や多数のユーザーが不適切だと判断する行為
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Prohibited acts on the Server / プライベートサーバーの禁止行為**",
                """
                        1. サブアカウントを作成する行為 (作成すると自動で制限されます)
                        2. チートエンジンを使用したプレイを送信する行為
                        3. ペンタブレットのフィルター機能を利用する行為 (チート検知機能が反応する可能性があります)
                        4. 不適切なアバター、バナー、自己紹介、ユーザーネームを記載、設定する行為
                        5. 差別用語の使用、人種、性別、宗教、信条、門地、同性愛の嫌悪や差別に値するあらゆる行為
                        6. 他ユーザーへの悪質ななりすまし行為
                        7. サーバーに対して不必要な計算を行わせるような行為
                        8. 運営や多数のユーザーが不適切だと判断する行為
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**申告について**", """
                もし不正行為を目撃、発見した場合、運営に https://discord.com/channels/944248031136587796/1171728223407710208 にて報告をしてください。
                \n__**虚偽の申告はアカウントの制限対象になります**__ \n
                """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**異議申し立てについて**", """
                もし運営の対応に異議がある場合は、運営に https://discord.com/channels/944248031136587796/1171728223407710208 にて報告をしてください。
                """, false);

        e.setColor(Color.CYAN);
        eb.add(e);

        return eb;
    }

    public static List<EmbedBuilder> getRuleEnMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();

        EmbedBuilder e = new EmbedBuilder();

        e.setTitle("**Welcome to Mamestagram!**");
        e.addField("**Rule**",
                """
                        Thank you for joining Mamestagram. The following rules are rules that you must follow when playing on Mamestagram.
                        There are two sets of rules, one for the private server and the other for the Discord server. Both are quite important to use, so please read them carefully before participating.
                        If you do not follow the rules, the management may restrict/ban your account without warning!
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Prohibited acts on the Discord**",
                """
                        1. Behavior that may cause inconvenience to members (e.g., spamming, attaching hijacked URLs, etc.)
                        2. sending messages that overload members' terminals
                        3. using discriminatory terms, or any actions that are hateful or discriminatory in terms of race, gender, religion, creed, family origin, or homosexuality
                        4. any act of speaking out of turn in an abusive manner
                        5. malicious impersonation of other users
                        6. distribution of cheats, viruses, home-made software, etc.
                        7. speaking or attaching NSFW contents
                        8. acts that are deemed inappropriate by the management or many users
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Prohibited acts on the Server**",
                """
                        1. creating a sub-account (automatic restriction upon creation)
                        2. sending a play that uses the cheat engine.
                        3. using the filter function of the pen tablet (the cheat detection function may react)
                        4. uploading or setting up inappropriate avatars, banners, self-introductions, or usernames
                        5. use of discriminatory terms, or any actions that are hateful or discriminatory in terms of race, gender, religion, creed, family origin, or homosexuality
                        6. malicious impersonation of another user
                        7. any action that causes the server to perform unnecessary calculations
                        8. any action that is deemed inappropriate by the management or by many users.
                        """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**About Declarations**", """
                If you witness or discover any fraudulent activity, please report it to moderator at https://discord.com/channels/944248031136587796/1171728223407710208.
                \n__**False reports will result in account restrictions**__. \n
                """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Regarding objections**", """
                If you disagree with moderator's decision, please report it to moderator at https://discord.com/channels/944248031136587796/1171728223407710208.
                """, false);

        e.setColor(Color.CYAN);
        eb.add(e);

        return eb;
    }

    public static EmbedBuilder getRoleMessage() {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("By reacting to this message, the following roles can be granted.");
        eb.addField("**Roles**", """
                :satellite: Mamestagram Updates
                <:osu:1100702517119168562> osu!
                <:taiko:1100702510152429588> Taiko
                <:fruits:1100702512681599089> Catch the beat
                <:mania:1100702514501910630> Mania
                :video_game: Multiplayer
                :video_camera: Stream Alert
                """, false);

        eb.setColor(Color.GREEN);

        return eb;
    }
}
