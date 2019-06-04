package net.rodrigocarvalho.duckbot.command.model;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CommandHandler {

    String name();
    String[] aliases() default {};
    boolean rootCommand() default false;

}