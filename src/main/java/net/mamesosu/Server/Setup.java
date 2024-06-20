package net.mamesosu.Server;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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
            channel.add(e.getGuild().getTextChannelById(1012666778003976243L));
            channel.add(e.getGuild().getTextChannelById(1253278610735890483L));

            //delete priv message
            for(MessageChannel c : channel) {
                MessageHistory history = MessageHistory.getHistoryFromBeginning(c).complete();
                for(Message m : history.getRetrievedHistory()) {
                    m.delete().queue();
                }
            }

            for (EmbedBuilder eb : Embed.getRuleJAMessage()) {
                e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(eb.build()).queue();
            }
            for (EmbedBuilder eb : Embed.getRuleEnMessage()) {
                e.getGuild().getTextChannelById(1253278610735890483L).sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}
