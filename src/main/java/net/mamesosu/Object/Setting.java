package net.mamesosu.Object;

import io.github.cdimascio.dotenv.Dotenv;

public class Setting {

    private final long recordChannel;
    private final long postChannel;
    private final long guild;

    public Setting() {
        Dotenv dotenv = Dotenv.configure()
                .load();

        guild = Long.parseLong(dotenv.get("GUILD"));
        recordChannel = Long.parseLong(dotenv.get("SCORE_CHANNEL"));
        postChannel = Long.parseLong(dotenv.get("POST_CHANNEL"));
    }

    public long getGuild() {
        return guild;
    }

    public long getRecordChannel() {
        return recordChannel;
    }

    public long getPostChannel() {
        return postChannel;
    }
}
