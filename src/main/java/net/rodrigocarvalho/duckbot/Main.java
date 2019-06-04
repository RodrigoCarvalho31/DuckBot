package net.rodrigocarvalho.duckbot;

public class Main {

    public static void main(String[] args) {
        DuckBot bot = DuckBot.getInstance();
        bot.setToken("token");
        bot.setUsername("user");
        bot.setPassword("password");
        bot.start();
    }
}