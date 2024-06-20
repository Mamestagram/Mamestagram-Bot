package net.mamesosu.Server;

import net.dv8tion.jda.api.EmbedBuilder;
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
            for (EmbedBuilder eb : Embed.getRuleJAMessage()) {
                e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(eb.build()).queue();
            }

            for (EmbedBuilder eb : Embed.getRuleEnMessage()) {
                e.getGuild().getTextChannelById(1253278610735890483L).sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}
