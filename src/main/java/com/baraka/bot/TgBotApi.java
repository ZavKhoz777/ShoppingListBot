package com.baraka.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TgBotApi {

    private TelegramBotsApi api;

    public TgBotApi() {
    }

    public void registerBotInTelegramApi(Bot bot){
        if(api == null){
            try{
                api = new TelegramBotsApi(DefaultBotSession.class);
            }catch (TelegramApiException apiException){
                System.out.println(apiException.getMessage());
            }
        }
        try {
            api.registerBot(bot);
        } catch (TelegramApiException apiException) {
            apiException.printStackTrace();
        }
    }

}
