package net.mamesosu.Message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.mamesosu.Main;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Embed {

    public static EmbedBuilder getPPRecordMessage() {

        EmbedBuilder eb = new EmbedBuilder();

        Connection connection = Main.db.getConnection();
        PreparedStatement ps;
        ResultSet result;

        eb.setTitle("**PP Record**");
        eb.setDescription("This is the PP record of the Mamestagram");

        //WIP

        for(int i = 0; i <= 8; i++) {
            if(i == 7) continue;
            try {
                ps = connection.prepareStatement("select from stats  ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //eb.addField("")
        eb.setColor(Color.ORANGE);

        return eb;
    }
}
