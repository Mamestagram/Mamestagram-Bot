package net.mamesosu.Schedule;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.mamesosu.Main;
import net.mamesosu.Message.Embed;


public class Record extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(Main.setting.getGuild() != e.getGuild().getIdLong()) {
            return;
        }

        if(e.getMessage().getChannelIdLong() != Main.setting.getRecordChannel()) {
            return;
        }

        long latestMessageIdLong = e.getJDA().getGuildById(e.getGuild().getIdLong()).getTextChannelById(Main.setting.getPostChannel()).getLatestMessageIdLong();
        Message latestMessage = e.getJDA().getGuildById(e.getGuild().getIdLong()).getTextChannelById(Main.setting.getPostChannel()).retrieveMessageById(latestMessageIdLong).complete();

        if(!latestMessage.getAuthor().isBot()) {
            e.getGuild().getTextChannelById(Main.setting.getPostChannel()).sendMessageEmbeds(Embed.getPPRecordMessage().build()).queue();
        } else {
            e.getGuild().getTextChannelById(Main.setting.getPostChannel()).editMessageEmbedsById(latestMessageIdLong, Embed.getPPRecordMessage().build()).queue();
        }
    }
}
