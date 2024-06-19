package net.mamesosu.Schedule;

import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.mamesosu.Main;
import net.mamesosu.Utils.Data;

public class ActivePlayer extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(Main.setting.getGuild() != e.getGuild().getIdLong()) {
            return;
        }

        if(e.getMessage().getChannelIdLong() != Main.setting.getLogChannel()) {
            return;
        }

        JsonNode node = Data.getJsonNode("https://api.mamesosu.net/v1/get_player_count");

        e.getJDA().getPresence().setActivity(Activity.watching("Active Players: " + node.get("counts").get("online").asInt()));
    }
}
