package net.rodrigocarvalho.duckbot.command.subcommands;

import net.rodrigocarvalho.duckbot.command.model.AbstractCommand;
import net.rodrigocarvalho.duckbot.command.model.CommandEvent;
import net.rodrigocarvalho.duckbot.command.model.CommandHandler;
import net.rodrigocarvalho.duckbot.utils.InstagramUtils;

@CommandHandler(name = "follow", aliases = {"seguir"}, rootCommand = true)
public class FollowCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getArgs();
        if (args.length == 0) {
            event.sendMessage("Digite um usuário para seguir.");
            return;
        }

        String username = args[0];
        try {
            event.sendMessage(InstagramUtils.follow(username));
        } catch (Exception e) {
            event.sendMessage("Não foi possível seguir " + username + ", este usuário realmente existe?");
        }
    }
}