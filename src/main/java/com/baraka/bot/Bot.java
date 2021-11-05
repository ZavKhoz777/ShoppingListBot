package com.baraka.bot;

import com.baraka.supporting.Listener;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private String token;
    private String botName;

    private Listener listener;

    public Bot(DefaultBotOptions options, String token, String botName) {
        super(options);
        this.token = token;
        this.botName = botName;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public String getBotToken() {
        return token;
    }

    public void onUpdateReceived(Update update) {
        listener.getUpdate(update);

    }
//    public Message execute(SendMessage message) throws TelegramApiException {
//        return super.execute(message);
//    }


    public String getBotUsername() {
        return botName;
    }
}
