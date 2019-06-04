package net.rodrigocarvalho.duckbot.command.model;

import net.dv8tion.jda.api.JDA;
import net.rodrigocarvalho.duckbot.DuckBot;

public abstract class AbstractCommand {

    public static JDA jda = DuckBot.getInstance().getJda();

    public static void setJda(JDA jda) {
        AbstractCommand.jda = jda;
    }

    public abstract void execute(CommandEvent event);

}