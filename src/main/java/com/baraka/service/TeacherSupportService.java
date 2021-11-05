package com.baraka.service;

import com.baraka.bot.Bot;
import com.baraka.bot.TgBotApi;
import com.baraka.model.Customer;
import com.baraka.repository.CustomerRepository;
import com.baraka.repository.MessageRepository;
import com.baraka.supporting.Listener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.PostConstruct;

//@Service
public class TeacherSupportService implements Listener {

    private CustomerRepository customerRepository;
    private MessageRepository messageRepository;
    private TgBotApi api;
    private Bot tgBot;

    public TeacherSupportService(TgBotApi api, Bot tgBot, CustomerRepository customerRepository, MessageRepository messageRepository) {
        this.api = api;
        this.tgBot = tgBot;
        this.customerRepository = customerRepository;
        this.messageRepository = messageRepository;
    }

    public void getUpdate(Update update){
        User user = update.getMessage().getFrom();
        Message msg = update.getMessage();

        System.out.println(msg.getFrom().getId() + ";\n" + msg.getFrom().getFirstName() +
                ";\n" + (msg.hasText()? msg.getText() : ""));
        Customer cust = customerRepository.findCustomerByTgId(user.getId());
        if(cust == null){
            cust = new Customer(user.getId(), msg.getChatId());
            cust.setFirstName(user.getFirstName());
            cust.setLastName(user.getLastName());
        }


        com.baraka.model.Message message = new com.baraka.model.Message();

        if(msg.hasText()) {
            message.setText(msg.getText());
        }

        message.setAuthor(cust);

        customerRepository.save(cust);

        messageRepository.save(message);

//        customerRepository.findAll().forEach(System.out::println);

        messageRepository.findAllByAuthor_TgId(user.getId()).forEach(System.out::println);
//        messageRepository.findAll().forEach(System.out::println);

    }

    @PostConstruct
    public void init(){
        api.registerBotInTelegramApi(tgBot);
        tgBot.setListener(this);
    }


}
