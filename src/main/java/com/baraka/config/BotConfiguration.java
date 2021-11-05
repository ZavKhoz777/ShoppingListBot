package com.baraka.config;

import com.baraka.bot.Bot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class BotConfiguration {

    @Value("${telegram.bot.token}")
    private String BOT_TOKEN;
    private final String BOT_NAME = "ShoppingAssistantBot";

    @Bean
    public Bot getBot(){

        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(5);

        Bot bot = new Bot(options,BOT_TOKEN,BOT_NAME);

        return bot;
    }

}
