package net.rodrigocarvalho.duckbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.rodrigocarvalho.duckbot.command.Command;
import net.rodrigocarvalho.duckbot.command.model.AbstractCommand;
import org.apache.log4j.Logger;
import org.brunocvcunha.instagram4j.Instagram4j;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class DuckBot {

    private static final DuckBot INSTANCE = new DuckBot(null, null, null);
    private static final Logger LOGGER = Logger.getLogger(DuckBot.class);

    private String token, username, password;
    private JDA jda;
    private Instagram4j instagram;

    private DuckBot(String token, String username, String password) {
        this.token = token;
        this.username = username;
        this.password = password;
    }

    public void start() {
        try {
            this.jda = new JDABuilder(token).build();
            this.jda.awaitReady();
            this.instagram = Instagram4j.builder().username(username).password(password).build();
            this.instagram.setup();
            this.instagram.login();
            AbstractCommand.setJda(jda);
            this.jda.addEventListener(new Command());
        } catch (LoginException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JDA getJda() {
        return jda;
    }

    public Instagram4j getInstagram() {
        return instagram;
    }

    public static DuckBot getInstance() {
        return INSTANCE;
    }

    public static void print(String message) {
        LOGGER.info(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }
}