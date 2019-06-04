package net.rodrigocarvalho.duckbot.command;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.rodrigocarvalho.duckbot.DuckBot;
import net.rodrigocarvalho.duckbot.command.model.AbstractCommand;
import net.rodrigocarvalho.duckbot.command.model.CommandEvent;
import net.rodrigocarvalho.duckbot.command.model.CommandHandler;
import net.rodrigocarvalho.duckbot.utils.ReflectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Command extends ListenerAdapter {

    private final String PREFIX = "!";
    private final Set<AbstractCommand> COMMANDS = new HashSet<>();

    public Command() {
        for (AbstractCommand command : ReflectionUtils.getAllCommandss()) {
            COMMANDS.add(command);
            DuckBot.print("Registred command " + command.getClass().getSimpleName());
        }
    }

    public void addCommand(AbstractCommand command) {
        COMMANDS.add(command);
        DuckBot.print("Registred command " + command.getClass().getSimpleName());
    }

    public void removeCommand(String name) {
        COMMANDS.removeIf(x -> {
            CommandHandler annotation = x.getClass().getAnnotation(CommandHandler.class);
            return annotation.name().equalsIgnoreCase(name) || contains(annotation.aliases(), name);
        });
        DuckBot.print("Unregistred command " + name);
    }

    public AbstractCommand getCommand(String message) {
        String command = message.substring(PREFIX.length());
        return COMMANDS.stream().filter(x -> {
            CommandHandler annotation = x.getClass().getAnnotation(CommandHandler.class);
            return annotation.name().equalsIgnoreCase(command) || contains(annotation.aliases(), command);
        }).findFirst().orElse(null);
    }

    private boolean contains(String[] array, String content) {
        for (String s : array) {
            if (s.equalsIgnoreCase(content)) return true;
        }
        return false;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User user = event.getAuthor();
        if (user.isBot()) return;
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            String[] split = content.split( " ");
            AbstractCommand command = getCommand(content.contains(" ") ? split[0] : content);
            if (command != null) {
                DuckBot.print("[G] - " + user.getName() + ": " + content);
                CommandHandler annotation = command.getClass().getAnnotation(CommandHandler.class);
                if (annotation.rootCommand() && !user.getId().equals("352901571543171074")) {
                    channel.sendMessage("Somente pessoas especiais podem usar esse comando <:tux:563133833155969034>.").queue();
                    return;
                }
                CommandEvent commandEvent = new CommandEvent(user, member, guild, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[0]);
                command.execute(commandEvent);
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        User user = event.getAuthor();
        if (user.isBot()) return;
        PrivateChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            String[] split = content.split( " ");
            AbstractCommand command = getCommand(content.contains(" ") ? split[0] : content);
            if (command != null) {
                DuckBot.print("[PM] - " + user.getName() + ": " + content);
                CommandHandler annotation = command.getClass().getAnnotation(CommandHandler.class);
                if (annotation.rootCommand() && !user.getId().equals("352901571543171074")) {
                    channel.sendMessage("Somente pessoas especiais podem usar esse comando <:tux:563133833155969034>.").queue();
                    return;
                }
                CommandEvent commandEvent = new CommandEvent(user, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[0]);
                command.execute(commandEvent);
            }
        }
    }
}