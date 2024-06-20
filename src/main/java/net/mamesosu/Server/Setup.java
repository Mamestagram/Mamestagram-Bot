package net.mamesosu.Server;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
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

        if(e.getMessage().getChannel().getIdLong() == Main.setting.getSetupChannel()) {

            if(e.getAuthor().isBot()) {
                return;
            }

            e.getGuild().getTextChannelById(1012691799350988810L).sendMessageEmbeds(Embed.getRoleMessage().build()).queue();

        }  else if (e.getMessage().getChannelIdLong() == 1012691799350988810L) {
            e.getMessage().addReaction(Emoji.fromUnicode("U+1F4E1")).queue();
            e.getMessage().addReaction(Emoji.fromFormatted("<:osu:1100702517119168562>")).queue();
            e.getMessage().addReaction(Emoji.fromFormatted("<:taiko:1100702510152429588>")).queue();
            e.getMessage().addReaction(Emoji.fromFormatted("<:fruits:1100702512681599089>")).queue();
            e.getMessage().addReaction(Emoji.fromFormatted("<:mania:1100702514501910630>")).queue();
            e.getMessage().addReaction(Emoji.fromUnicode("U+1F3AE")).queue();
            e.getMessage().addReaction(Emoji.fromUnicode("U+1F4F9")).queue();
        }

        /*if(e.getMessage().getContentRaw().equals("setup")) {
            for (EmbedBuilder eb : Embed.getRuleJAMessage()) {
                e.getGuild().getTextChannelById(1012666778003976243L).sendMessageEmbeds(eb.build()).queue();
            }

            for (EmbedBuilder eb : Embed.getRuleEnMessage()) {
                e.getGuild().getTextChannelById(1253278610735890483L).sendMessageEmbeds(eb.build()).queue();
            }
        }*/
    }
}
