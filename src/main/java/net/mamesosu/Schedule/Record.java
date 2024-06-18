package net.mamesosu.Schedule;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.mamesosu.Main;


public class Record extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(Main.setting.getGuild() != e.getGuild().getIdLong()) {
            return;
        }

        if(e.getMessage().getIdLong() != Main.setting.getRecordChannel()) {
            return;
        }


    }
}
