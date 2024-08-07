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
                        ルールはプライベートサーバーとDiscordサーバーの2点が存在します。どちらも利用する上でかなり重要なものですので、よく読んだ上で参加をお願いします。
                        ルールを守らない場合、運営は警告なしでアカウントを凍結することがあります
                        """, false);

        e.setColor(Color.CYAN);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
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
        e.addField("Rule",
                """
                Thank you for joining Mamestagram.
                The following rules must be strictly followed while using Mamestagram.
                There are rules for both the private server and the Discord server. It is crucial to read and understand these rules before participating.
                If the rules are not followed, the administration reserves the right to suspend accounts without warning.
                """, false);

        e.setColor(Color.CYAN);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("Prohibited Acts on the Discord",
                """
                1. Acts that cause inconvenience to members (such as spam or posting phishing URLs)
                2. Sending messages that put a load on members' devices
                3. Use of discriminatory language or any acts that amount to hatred or discrimination based on race, gender, religion, creed, social status, or sexual orientation
                4. Making excessively abusive remarks
                5. Malicious impersonation of other users
                6. Distributing cheats, viruses, or self-made software
                7. Posting or attaching NSFW content
                8. Any act deemed inappropriate by the administration or a large number of users
                """, false);

        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Prohibited Acts on the Server**",
                """
                        1. Creating alternate accounts (creating them will result in automatic restrictions)
                        2. Broadcasting gameplay using cheat engines
                        3. Using tablet filter functions (may trigger cheat detection systems)
                        4. Using inappropriate avatars, banners, self-introductions, or usernames
                        5. Use of discriminatory language or any acts that amount to hatred or discrimination based on race, gender, religion, creed, social status, or sexual orientation
                        6. Malicious impersonation of other users
                        7. Performing unnecessary computations on the server
                        8. Any act deemed inappropriate by the administration or a large number of users
                        """, false);


        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Reporting Procedures**", """
                If you witness or discover any misconduct, please report it to the administration at https://discord.com/channels/944248031136587796/1171728223407710208.
                \n__**False reporting may result in account restrictions.**__ \n
                """, false);


        e.setColor(Color.CYAN);
        eb.add(e);
        e = new EmbedBuilder();

        e.addField("**Appeals Process**", """
                If you disagree with the administration's response, please report it to the administration at https://discord.com/channels/944248031136587796/1171728223407710208.
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

    public static List<EmbedBuilder> getAboutMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();
        EmbedBuilder e = new EmbedBuilder();

        e.setTitle(":pushpin: **What is Mamestagram?**");
        e.setDescription("Mamestagram is a brand new private osu! server unlike any other. Enjoy many fun features that are not available on Bancho!");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":eyes: **What is private server?**");
        e.setDescription("A private osu! server is an unofficial server that allows players to play the game osu! with custom features or modifications that differ from the official osu! servers. Private servers are maintained by individuals or groups and are not affiliated with the creators of the official game.");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":gem: **How to Connect to the Server?**");
        e.setDescription("Please connect to ``mamesosu.net``. For detailed connection instructions, see [here](https://web.mamesosu.net/documents#connection-en).");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":musical_keyboard: **How Can I Play the Dan Courses?**");
        e.setDescription("You can earn titles by playing the specified Dan course maps. For more details, please see https://discord.com/channels/944248031136587796/1175719230650449940");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":lock: **Can I use my Bancho account on Mamestagram?**");
        e.setDescription("No, you cannot. Mamestagram requires a separate account to be created and under no circumstance will request access to your Bancho account.");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":book: **I Don't Know the Server Commands! Where Can I Find Them?**");
        e.setDescription("Please see https://discord.com/channels/944248031136587796/1087987989600280686 to learn all the commands.");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":warning: **How can I appeal my restriction?**");
        e.setDescription("Please report it to the administration at https://discord.com/channels/944248031136587796/1171728223407710208");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":bug: **How to Report a Bug?**");
        e.setDescription("If you find a bug, please create a post at https://discord.com/channels/944248031136587796/1117062398596108298, select the appropriate tag, and submit your post.");
        e.setColor(Color.CYAN);

        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":rocket: **How to Suggest a New Feature?**");
        e.setDescription("To suggest a new feature, please create a post at https://discord.com/channels/944248031136587796/1200023545015697529!");
        e.setColor(Color.CYAN);

        eb.add(e);

        return eb;
    }

    public static List<EmbedBuilder> getCommandMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();
        EmbedBuilder e = new EmbedBuilder();

        e.setTitle("**Mamestagram 's command list**");
        e.setDescription("By sending the following command to the bot, **Momiji**, in the server, you can enjoy playing Mamestagram more comfortably!");

        e.setTitle(":earth_asia: **Profile settings**");
        e.setDescription(
                """
                ``!changecountry`` Change the country of your account
                ``!changename <name>`` Change your name"""
        );
        e.setColor(Color.white);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":musical_keyboard: **Dan settings**");
        e.setDescription(
                """
                ``!dantitle <edit/list> <Dan ID>`` Change the type of Dan displayed on the leaderboard (available only for osu!mania)
                ``!nopp <on/off>`` Switch to 0pp mode! (available only for vn!taiko/ctb/mania)
                """);
        e.setColor(Color.red);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":link: **Friends and Goals**");
        e.setDescription(
                """
                ``!friends`` Display your friends list
                ``!goals`` Set goals for maps or any mode (progress is shown after each play)
                """);
        e.setColor(Color.magenta);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":trophy: **Leaderboard settings**");
        e.setDescription(
                """
                ``!leaderboard dan <on/off>`` Toggle Dan display on the leaderboard
                ``!leaderboard sort <score/pp/default>`` Change the leaderboard ranking order
                """);
        e.setColor(Color.pink);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":file_folder:  **Submission setting**");
        e.setDescription(
                """
                ``!submit <on/off>`` Toggle the submission of scores to the leaderboard
                """
        );
        e.setColor(Color.cyan);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":zap: **Rival settings**");
        e.setDescription(
                """
                ``!rival <add/remove> <userid or player name>`` Set your rival (you will be notified if your score is sniped by the rival)
                ``!rival <msg> <message>`` Change the message to players who have set you as their rival
                """);
        e.setColor(Color.blue);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":bell: **Notification settings**");
        e.setDescription(
                """
                ``!scorenotice <on/off>`` Toggle notifications for rank and PP display (default is off on Bancho)
                ``!snipe <on/off>`` Toggle notifications when someone takes the 1st place from you
                """);
        e.setColor(Color.GREEN);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":rocket: **Map settings**");
        e.setDescription(
                """
                ``!unsub <rank/unrank>`` Update maps not posted on Bancho to Loved (you can update NotSubmitted maps)
                ``!update`` Fix maps that are not available on the ranking
                """);
        e.setColor(Color.yellow);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":paintbrush: **PP Calculation**");
        e.setDescription(
                """
                ``!with +<mod (optional)> <acc (optional)>`` Calculate and display PP under specified conditions
                """);
        e.setColor(Color.orange);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":notebook_with_decorative_cover: **List of Clan Commands**");
        e.setDescription(
                """
                ``!clan help`` Display help for clan commands
                ``!clan create <tag> <name>`` Create a new clan
                ``!clan edit <tag / name>`` Change the clan name or tag name
                ``!clan disband`` Disband the clan
                ``!clan info`` Display clan information
                ``!clan leave`` Leave the clan
                ``!clan public <on/off>`` Change the clan's public settings (can make the clan invitation-only)
                ``!clan pending`` Display players who have applied to join your clan
                ``!clan accept <user ID>`` Accept the specified user ID to join the clan
                ``!clan kick <user ID>`` Kick the specified player from your clan (available only to clan admins)
                """);
        e.setColor(Color.PINK);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":video_game: **List of Multiplayer Commands (Custom Commands Only)**");
        e.setDescription(
                """
                ``!mp r <enable/disable>`` Rotate the clan host from top to bottom after each play
                ``!mp invite <message>`` Sends an invitation notification to Discord
        """
        );
        e.setColor(Color.white);
        eb.add(e);

        return eb;
    }

    public static List<EmbedBuilder> getDanMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();
        EmbedBuilder e = new EmbedBuilder();

        e.setTitle(":pushpin: **段位とは**");
        e.setDescription("""
                [段位](https://web.mamesosu.net/documents#dans-en)は、プレイヤーの真の技量を測定することができる多くのゲームで採用されているシステムです。
                \nMamestagramでは、現在**全てのモード**で段位をプレイすることができ、段位を取得することで、他のプレイヤーとの技量を比較することができます。
             """);

        e.setColor(Color.BLUE);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":trophy: **ランキングの確認方法**");
        e.setDescription("""
                段位のランキングは、[リーダーボード](https://web.mamesosu.net/leaderboard/std/dans)やプロフィールで確認することができます。
                """);
        e.setColor(Color.BLUE);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":zap: **段位の取得方法**");
        e.setDescription( """
                段位を取得するには、[段位マップ](https://web.mamesosu.net/documents#dans-en)をプレイする必要があります。
                \n**段位マップは、段位を取得するための特別な譜面で、段位マップをプレイすることで段位を取得することができます。**
                """);
        e.setColor(Color.BLUE);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":rocket: **譜面のダウンロード**");
        e.setDescription("""
                [全てのモードの段位をダウンロードする](https://mega.nz/file/MTcFXKiS#osaecmefYp5BnGRBjeYJu3dAR2aOKKUkTo8BOhJC0XU)
                <:osu:1100702517119168562> [osu!](https://mega.nz/file/QKsHlLhS#xZfttKbJQqt-2mvT0uD9sIKGJH4VQKf41zXIU_JW81U)
                <:taiko:1100702510152429588> [Taiko](https://mega.nz/file/1X1FDDqS#QLP5ZjbH8k1FN4IqYJbH_Agq_onXLd66r8UiYxKUDGw)
                <:fruits:1100702512681599089> [Catch the beat](https://mega.nz/file/BS9CQbzK#pjq5WrFbbpap7G0NA2LYNvNFATyZ1dQDRUseSkTdS-g)
                <:mania:1100702514501910630> [Mania](https://mega.nz/file/BCMihKRK#oifqLN7kbyf1gw_0_QVN7um_-SJKLw-Vb_-r_pRvJ1I)
                """);
        e.setImage("https://media.discordapp.net/attachments/945876677358129184/1253368998620434593/2024-03-24_015956.png");
        e.setColor(Color.BLUE);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":pushpin: **What is Dan?**");
        e.setDescription("""
        [Dan](https://web.mamesosu.net/documents#dans-en) is a system used in many games to measure a player's true skill.
        \nIn Mamestagram, you can currently play Dan in **all modes**, allowing you to compare your skill level with other players.
     """);

        e.setColor(Color.BLUE);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":trophy: **How to Check the Rankings**");
        e.setDescription("""
        You can check the Dan rankings on the [leaderboard](https://web.mamesosu.net/leaderboard/std/dans) or in your profile.
        """);
        e.setColor(Color.BLUE);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":zap: **How to Obtain Dan**");
        e.setDescription("""
        To obtain Dan, you need to play the [Dan maps](https://web.mamesosu.net/documents#dans-en).
        \n**Dan maps are special charts used to obtain Dan. By playing Dan maps, you can achieve a Dan rank.**
        """);
        e.setColor(Color.BLUE);
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":rocket: **Download Maps**");
        e.setDescription("""
        [Download all mode Dan maps](https://mega.nz/file/MTcFXKiS#osaecmefYp5BnGRBjeYJu3dAR2aOKKUkTo8BOhJC0XU)
        <:osu:1100702517119168562> [osu!](https://mega.nz/file/QKsHlLhS#xZfttKbJQqt-2mvT0uD9sIKGJH4VQKf41zXIU_JW81U)
        <:taiko:1100702510152429588> [Taiko](https://mega.nz/file/1X1FDDqS#QLP5ZjbH8k1FN4IqYJbH_Agq_onXLd66r8UiYxKUDGw)
        <:fruits:1100702512681599089> [Catch the Beat](https://mega.nz/file/BS9CQbzK#pjq5WrFbbpap7G0NA2LYNvNFATyZ1dQDRUseSkTdS-g)
        <:mania:1100702514501910630> [Mania](https://mega.nz/file/BCMihKRK#oifqLN7kbyf1gw_0_QVN7um_-SJKLw-Vb_-r_pRvJ1I)
        """);
        e.setImage("https://media.discordapp.net/attachments/945876677358129184/1253368998620434593/2024-03-24_015956.png");
        e.setColor(Color.BLUE);
        eb.add(e);

        return eb;
    }

    public static List<EmbedBuilder> getDonateMessage() {

        List<EmbedBuilder> eb = new ArrayList<>();
        EmbedBuilder e = new EmbedBuilder();

        e.setTitle(":pushpin: **知っていますか？**");
        e.addField(":student: **私たちは学生です!**", """
                * 毎月多額のサーバーの費用をポケットマネーから出しています。サーバーの維持には、多くの費用がかかります。あなたの寄付がサーバーの維持を助けることができます。
                """, false);
        e.addField(":earth_asia: **快適なウェブサイトを提供**", """
                * Mamestagramのウェブサイトでは、一切広告を表示していません。
                """, false);
        e.addField(":zap: **快適なサービスを提供**", """
                * Mamestagramで使用しているハードウェアは高性能なものですが、ユーザー数や機能が増えるにつれ、サーバーをアップグレードしています。あなたの寄付がサーバーの発展を助けることができます。
                """, false);

        e.setColor(Color.ORANGE);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":money_with_wings: **サーバーへの寄付方法**");

        e.addField(":bulb: **寄付方法**", """
                Mamestagramでは[Kofi](https://ko-fi.com/mames1)と呼ばれる寄付サイト、またはPayPayにて寄付を募っています。
                \n__Kofiはアカウント登録なしで簡単に寄付を行うことできるサイトです。__
                """, false);
        e.addField(":zap: **寄付特典**",
                """
                      * Websiteのプロフィールページの恒久的なサポーターバッジ
                      * あなたのアカウントを目立たせるDiscordサポーターロール
                      \n__必ず寄付を行う時にメッセージ欄にDiscordとMamestagramアカウントの情報を記載してください__
                      """, false);
        e.addField(":link: **リンク**", """
                [Kofi](https://ko-fi.com/mames1)
                PayPay (以下のQRコードからお願いします)
                """, false);
        e.setColor(Color.ORANGE);
        e.setImage("https://media.discordapp.net/attachments/944984741826932767/1253755792910319667/IMG_2683.png");
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":pushpin: **Did You Know?**");
        e.addField(":student: **We Are Students!**", """
            * Every month, we pay a large amount of server costs out of our own pockets. Maintaining the server is expensive. Your donation can help us keep the server running.
            """, false);
        e.addField(":earth_asia: **Providing a Comfortable Website**", """
            * We do not display any advertisements on the Mamestagram website.
            """, false);
        e.addField(":zap: **Providing a Comfortable Service**", """
            * The hardware we use for Mamestagram is high-performance, but as the number of users and features increases, we upgrade the server. Your donation can help us improve the server.
            """, false);

        e.setColor(Color.ORANGE);
        e.setThumbnail("https://media.discordapp.net/attachments/944984741826932767/1253408271004467331/1865_20240206160538.png");
        eb.add(e);

        e = new EmbedBuilder();

        e.setTitle(":money_with_wings: **How to Donate to the Server**");

        e.addField(":bulb: **Donation Methods**", """
            Mamestagram accepts donations via a site called [Kofi](https://ko-fi.com/mames1) or PayPay.
            \n__Kofi is a site where you can easily make donations without registering an account.__
            """, false);
        e.addField(":zap: **Donation Benefits**",
                """
                      * Permanent supporter badge on the profile page of the website
                      * Discord supporter role to make your account stand out
                      \n__Be sure to include your Discord and Mamestagram account information in the message section when making a donation.__
                      """, false);
        e.addField(":link: **Links**", """
            [Kofi](https://ko-fi.com/mames1)
            PayPay (please use the QR code below)
            """, false);
        e.setColor(Color.ORANGE);
        e.setImage("https://media.discordapp.net/attachments/944984741826932767/1253755792910319667/IMG_2683.png");
        eb.add(e);


        return eb;
    }

    public static EmbedBuilder getMapRequestMessage() {

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("**Map Status Change Request**");
        eb.addField(":white_check_mark: **Changeable status**", """
                <:ranked:1143570271974989914> **Ranked**
                <:loved:1100846331418914857> **Loved**
                """, false);
        eb.addField(":pencil: How to Request", """
                * Click the button for the type of map.
                * Enter the map's setid in the form.
                * Submit the form to complete.
                """, true);
        eb.addField(":warning: **Note**", """
                * Maps that have not been played on the server cannot be requested.
                * Maps with an OD of 0 cannot be requested.
                * Maps that deviate significantly from Bancho's Ranked criteria, such as Vibro, cannot be requested.
                """, true);
        eb.addField("**:information_source: About Set ID**", """
                The set ID is the first number in the beatmap's URL.
                Example: In ``https://osu.ppy.sh/beatmapsets/1234567#osu/7654321``, the set ID is ``1234567``.
                """, false);

        eb.setColor(Color.CYAN);

        return eb;
    }
}
