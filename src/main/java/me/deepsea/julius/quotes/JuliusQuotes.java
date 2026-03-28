package me.deepsea.julius.quotes;

import java.util.Random;

public class JuliusQuotes {
    private static final String[] QUOTES = {
            "Se eu não comprar nada, o desconto é maior.",
            "Eu tenho dois empregos!",
            "Aceita vale-refeição? É que eu tenho dois empregos.",
            "Sabe quanto custou isso? Cento e trinta e cinco dólares e quarenta e dois centavos!",
            "Dirigir? Isso custa 25 centavos a cada quilômetro!",
            "Tira o pé do chão, você está gastando o solado do sapato!"
    };

    public static String getRandomQuote() {
        Random rand = new Random();
        return QUOTES[rand.nextInt(QUOTES.length)];
    }
}
