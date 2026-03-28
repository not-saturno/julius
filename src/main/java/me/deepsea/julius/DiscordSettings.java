package me.deepsea.julius;

import me.deepsea.julius.commands.CommandCreator;
import me.deepsea.julius.commands.DealCommand;
import me.deepsea.julius.commands.QuoteCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordSettings {


    @Value("${discord.bot.token}")
    private String token;

    @Bean
    public JDA jdaInstance(DealCommand dealCommand, QuoteCommand quoteCommand) {
        return JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(dealCommand)
                .addEventListeners(quoteCommand)
                .build();
    }

    @Bean
    public CommandLineRunner initCommands(CommandCreator commandCreator, JDA jdaInstance) {
        return args -> {
            commandCreator.addCommands(jdaInstance);
            System.out.println("Commands have been sent to Discord!");
        };
    }
}
