package net.mamesosu.Server;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.mamesosu.Main;
import net.mamesosu.Message.Embed;

public class Setup extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.getGuild().getIdLong() != Main.setting.getGuild()) {
            return;
        }

        if(e.getMessage().getChannel().getIdLong() != Main.setting.getSetupChannel()) {
            return;
        }

        if(e.getAuthor().isBot()) {
            return;
        }

        if(e.getMessage().getContentRaw().equals("setup")) {
            e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(Embed.getRuleJAMessage().build()).queue();
            e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(Embed.getRuleEnMessage().build()).queue();
            return;
        }
    }
}
