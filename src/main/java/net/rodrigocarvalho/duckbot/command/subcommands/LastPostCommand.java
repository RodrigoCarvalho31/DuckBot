package net.rodrigocarvalho.duckbot.command.subcommands;

import net.rodrigocarvalho.duckbot.command.model.AbstractCommand;
import net.rodrigocarvalho.duckbot.command.model.CommandEvent;
import net.rodrigocarvalho.duckbot.command.model.CommandHandler;
import net.rodrigocarvalho.duckbot.utils.InstagramUtils;

@CommandHandler(name = "lastpost", aliases = {"ultimopost", "últimopost"})
public class LastPostCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getArgs();
        String username = "rodrigocarvalho52";
        if (args.length > 0) {
            username = args[0];
        }

        try {
            event.sendMessage(InstagramUtils.getLastPostUrl(username));
        } catch (Exception e) {
            event.sendMessage("Não foi possível encontrar o usuário " + username + ", este usuário realmente existe? " + e.getMessage());
        }
    }
}