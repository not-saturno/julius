package me.deepsea.julius.commands;

import lombok.extern.slf4j.Slf4j;
import me.deepsea.julius.itadClient.DealClient;
import me.deepsea.julius.itadClient.game.Game;
import me.deepsea.julius.itadClient.prices.Deal;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DealCommand extends ListenerAdapter {

    private final DealClient client;

    public DealCommand(DealClient client) {
        this.client = client;
    }

    @Override
    public void onSlashCommandInteraction(@NonNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("pechinchar")){
            String title = Objects.requireNonNull(event.getOption("nome")).getAsString();
            event.deferReply().queue();

            client.searchGame(title)
                    .next()
                    .flatMap(game -> client.searchDeals(game.id())
                            .next()
                            .map(dealResponse -> buildEmbed(game, dealResponse.deals()))
                            .switchIfEmpty(Mono.fromCallable(() -> buildNoDealsEmbed(game))))
                    .switchIfEmpty(Mono.fromCallable(this::buildGameNotFoundEmbed))
                    .subscribe(
                            finalEmbed -> event.getHook().editOriginalEmbeds(finalEmbed.build()).queue(),
                            error -> {
                                log.error("Erro ao buscar jogo '{}': {}", title, error.getMessage(), error);
                                event.getHook().editOriginal("Ocorreu um erro técnico!").queue();
                            }
                    );
        }
    }

    public EmbedBuilder buildEmbed(Game game, List<Deal> deals) {
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder barganhas = new StringBuilder();
        embed.setTitle(game.title());
        for(Deal deal : deals) {
            // [Shop Name](URL) | Price
            barganhas.append("[")
                    .append(deal.shop().name())
                    .append("](")
                    .append(deal.url())
                    .append(") | R$")
                    .append(deal.price().amount())
                    .append("\n");
        }

        embed.appendDescription("**Lojas:**\n");
        embed.appendDescription(barganhas);
        embed.setImage(game.assets().banner600());
        embed.setFooter("\"O Chris é o meu maior investimento. Se ele falhar, eu perco tudo!\"");

        return embed;
    }

    private EmbedBuilder buildGameNotFoundEmbed() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Não encontrei o seu jogo.");
        embed.setColor(Color.RED);
        embed.setFooter("\"São 49 centavos de leite derramado em toda a mesa. Alguém vai ter que beber esse leite!\"");

        return embed;
    }

    public EmbedBuilder buildNoDealsEmbed(Game game) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Ah não! Não encontrei ofertas para o jogo "+game.title()+".");
        embed.setColor(Color.RED);
        embed.setFooter("\"São 49 centavos de leite derramado em toda a mesa. Alguém vai ter que beber esse leite!\"");

        return embed;
    }


}

