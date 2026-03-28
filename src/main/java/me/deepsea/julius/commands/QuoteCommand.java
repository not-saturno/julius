package me.deepsea.julius.commands;

import me.deepsea.julius.quotes.JuliusQuotes;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class QuoteCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NonNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("conselho")) {
            event.reply(JuliusQuotes.getRandomQuote()).queue();
        }
    }
}
