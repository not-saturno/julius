package me.deepsea.julius.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandCreator {

    public void addCommands(JDA jda) {
        jda.updateCommands().addCommands(
                Commands.slash("pechinchar", "Vou ver se acho isso por menos de um dólar.")
                        .addOption(OptionType.STRING, "nome", "O nome do jogo."),
                Commands.slash("conselho", "Grandes lições sobre como não gastar meus dois empregos.")
        ).queue();
    }
}
