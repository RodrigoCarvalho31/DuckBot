package net.rodrigocarvalho.duckbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.rodrigocarvalho.duckbot.DuckBot;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

import java.awt.*;
import java.util.List;

public class InstagramUtils {

    public static MessageEmbed follow(String username) throws Exception {
        Instagram4j instagram = DuckBot.getInstance().getInstagram();
        InstagramSearchUsernameResult result = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
        if (result == null || result.getUser() == null || result.isLock()) throw new Exception();
        InstagramUser user = result.getUser();
        instagram.sendRequest(new InstagramFollowRequest(result.getUser().getPk()));
        return new EmbedBuilder()
                .setTitle("Usuário seguido.")
                .setDescription("Você seguiu " + user.getFull_name() + " com sucesso.")
                .setThumbnail(user.getProfile_pic_url())
                .build();
    }

    public static MessageEmbed getInfo(String username) throws Exception {
        Instagram4j instagram = DuckBot.getInstance().getInstagram();
        InstagramSearchUsernameResult result = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
        if (result == null || result.getUser() == null || result.isLock()) throw new Exception();
        InstagramUser user = result.getUser();
        String biography = user.getBiography();
        String email = user.getPublic_email();
        return new EmbedBuilder()
                .setTitle("Informações de: " + user.getFull_name())
                .setColor(Color.RED)
                .addField("Seguidores", String.valueOf(user.getFollower_count()), true)
                .addField("Seguindo", String.valueOf(user.getFollowing_count()), true)
                .addField("E-mail", email == null ? "Nenhum" : email, false)
                .addField("Biografia", biography == null || biography.isEmpty() ? "Nenhuma" : biography, false)
                .setThumbnail(user.getProfile_pic_url())
                .build();
    }

    public static MessageEmbed getLastPostUrl(String username) throws Exception {
        Instagram4j instagram = DuckBot.getInstance().getInstagram();
        InstagramSearchUsernameResult result = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
        if (result == null || result.getUser() == null || result.isLock()) throw new Exception();
        InstagramUser user = result.getUser();
        InstagramGetUserReelMediaFeedRequest request = new InstagramGetUserReelMediaFeedRequest(user.getPk());
        /*InstagramFeedResult feed = instagram.sendRequest(new InstagramUserFeedRequest(user.getPk()));
        InstagramFeedItem item = feed.getItems().get(0);*/
        return new EmbedBuilder()
                .setTitle("Teste")
                .setDescription(
                        "URL=" + request.getUrl() + "\n" +
                        "Payload=" + request.getPayload())
                .build();
    }
}