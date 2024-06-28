package net.mamesosu.Server;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.mamesosu.Main;
import net.mamesosu.Message.Embed;

import java.util.ArrayList;
import java.util.List;

public class Setup extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.getGuild().getIdLong() != Main.setting.getGuild()) {
            return;
        }

        if(e.getMessage().getChannel().getIdLong() != Main.setting.getSetupChannel()) {

            if (e.getAuthor().isBot()) {
                return;
            }
        }

        if(e.getMessage().getContentRaw().equals("setup")) {

            List<MessageChannel> channel = new ArrayList<>();


            //channel.add(e.getGuild().getTextChannelById(1073989576462438420L));
            //channel.add(e.getGuild().getTextChannelById(1012666778003976243L));
            //channel.add(e.getGuild().getTextChannelById(1253278610735890483L));
            channel.add(e.getGuild().getTextChannelById(1087987989600280686L));
            //channel.add(e.getGuild().getTextChannelById(1175719230650449940L));
            //channel.add(e.getGuild().getTextChannelById(1171766574588370944L));

            //map request
            //channel.add(e.getGuild().getTextChannelById(1012678670252523530L));

            //delete priv message
            for(MessageChannel c : channel) {
                MessageHistory history = MessageHistory.getHistoryFromBeginning(c).complete();
                for(Message m : history.getRetrievedHistory()) {
                    m.delete().queue();
                }
            }

            /*for (EmbedBuilder eb : Embed.getRuleJAMessage()) {
                e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(eb.build()).queue();
            }
            for (EmbedBuilder eb : Embed.getRuleEnMessage()) {
                e.getGuild().getTextChannelById(1253278610735890483L).sendMessageEmbeds(eb.build()).queue();
            }*/

            for(EmbedBuilder eb : Embed.getCommandMessage()) {
                e.getGuild().getTextChannelById(1087987989600280686L).sendMessageEmbeds(eb.build()).queue();
            }

            /*for (EmbedBuilder eb : Embed.getDanMessage()) {
                e.getGuild().getTextChannelById(1175719230650449940L).sendMessageEmbeds(eb.build()).queue();
            }*/

            /*for(EmbedBuilder eb : Embed.getAboutMessage()) {
                e.getGuild().getTextChannelById(1073989576462438420L).sendMessageEmbeds(eb.build()).queue();
            }*/

            /*for(EmbedBuilder eb : Embed.getDonateMessage()) {
                e.getGuild().getTextChannelById(1171766574588370944L).sendMessageEmbeds(eb.build()).queue();
            }*/

            /*e.getGuild().getTextChannelById(1012678670252523530L).sendMessageEmbeds(Embed.getMapRequestMessage().build()).
                    addActionRow(
                            Button.success("btn_ranked", "Ranked"),
                            Button.danger("btn_unranked", "UnRanked")
                    ).queue();
             */
        }
    }
}
