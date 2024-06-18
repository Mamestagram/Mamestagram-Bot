package net.mamesosu.Object;

import io.github.cdimascio.dotenv.Dotenv;

public class Setting {

    private final long recordChannel;
    private final long guild;

    public Setting() {
        Dotenv dotenv = Dotenv.configure()
                .load();

        guild = Long.parseLong(dotenv.get("GUILD"));
        recordChannel = Long.parseLong(dotenv.get("SCORE_CHANNEL"));
    }

    public long getGuild() {
        return guild;
    }

    public long getRecordChannel() {
        return recordChannel;
    }
}
