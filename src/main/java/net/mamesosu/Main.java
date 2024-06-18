package net.mamesosu;

import net.mamesosu.Object.Bot;
import net.mamesosu.Object.DataBase;
import net.mamesosu.Object.Setting;

public class Main {

    public static Bot bot;
    public static DataBase db;
    public static Setting setting;

    public static void main(String[] args) {

        db = new DataBase();
        bot = new Bot();
        setting = new Setting();

        bot.loadJDA();
    }
}