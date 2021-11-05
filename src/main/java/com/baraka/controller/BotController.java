package com.baraka.controller;

import com.baraka.bot.Bot;
import com.baraka.bot.TgBotApi;
import com.baraka.model.Customer;
import com.baraka.repository.CustomerRepository;
import com.baraka.repository.MessageRepository;
import com.baraka.supporting.Listener;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class BotController implements Listener {

    TgBotApi api;
    Bot shoppingBot;
    MessageRepository messageRepository;
    CustomerRepository customerRepository;

    public BotController(TgBotApi api, Bot shoppingBot, MessageRepository messageRepository, CustomerRepository customerRepository) {
        this.api = api;
        this.shoppingBot = shoppingBot;
        this.messageRepository = messageRepository;
        this.customerRepository = customerRepository;
    }

    private Customer prepareUser(User user, Long chatId){
        Customer customer = customerRepository.findCustomerByTgId(user.getId());

        if(customer == null){
            customer = new Customer(user.getId(), chatId);
            customer.setFirstName(user.getFirstName());
            customer.setLastName(user.getLastName());
            customer.setLanguageCode(user.getLanguageCode());
            customerRepository.save(customer);
        }

        return customer;
    }

    private Customer prepareUser(Message message){
        User user = message.getFrom();
        Integer userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        Customer customer = customerRepository.findCustomerByTgId(userId);

        if(customer == null){
            customer = new Customer(userId, chatId);
            customer.setFirstName(user.getFirstName());
            customer.setLastName(user.getLastName());
            customer.setLanguageCode(user.getLanguageCode());
            customerRepository.save(customer);
        }

        return customer;
    }

    private void saveMessage(Message message){
        Customer customer = prepareUser(message.getFrom(), message.getChatId());
        com.baraka.model.Message msg = new com.baraka.model.Message();
        msg.setAuthor(customer);
        if(message.hasText()) msg.setText(message.getText());

        messageRepository.save(msg);
    }

    public void sendReply(ReplyKeyboardMarkup reply, Customer customer){
        SendMessage message = new SendMessage();
        message.setReplyMarkup(reply);
        message.setChatId(customer.getChat_id() + "");
        message.setText("Choose: ");
        try{
            shoppingBot.execute(message);
        }catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }


    }

    public void getUpdate(Update update){
        Message message = update.getMessage();
        if(message.hasText()){
            System.out.println(message.getFrom().getLanguageCode() + "\n" +
                    message.getFrom().getId() + "\n" + message.getFrom().getFirstName()+ "\n" + message.getText());
        }
        System.out.println(prepareUser(update.getMessage()));
//        sendReply(getKeyBoard(KEYBOARDS.BUYING_LIST), prepareUser(message.getFrom(), message.getChatId()));

    }

    private InlineKeyboardMarkup getInlineKeyboard(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton button = InlineKeyboardButton.builder().text("Press me").switchInlineQuery("query").callbackData("callBackdata").build();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<List<InlineKeyboardButton>> listButtons = new LinkedList<>();
        keyboard.setKeyboard(listButtons);

        buttons.add(button);

        return keyboard;
    }


    private ReplyKeyboardMarkup getKeyBoard(KEYBOARDS kboard){
        ReplyKeyboardMarkup keyboard = null;
        switch (kboard){
            case BUYING_LIST -> {
                KeyboardRow row = new KeyboardRow();
                KeyboardButton button = new KeyboardButton();
                button.setText("/Add new note to buying list");
                KeyboardButton button1 = new KeyboardButton();
                button1.setText("/Show buying list");
                KeyboardButton button2 = new KeyboardButton();
                button2.setText("/Share buying list");
                KeyboardButton button3 = new KeyboardButton();
                button3.setText("/Delete from list");

                row.add(button);
                row.add(button1);
                row.add(button2);
                row.add(button3);

                keyboard = ReplyKeyboardMarkup.builder().keyboardRow(row).build();
            }
        }
        return keyboard;
    }

    private void buyings(){

    }


    @PostConstruct
    public void init(){
        shoppingBot.setListener(this);
        api.registerBotInTelegramApi(shoppingBot);
    }

    private enum KEYBOARDS{
        BUYING_LIST;
    }

}
