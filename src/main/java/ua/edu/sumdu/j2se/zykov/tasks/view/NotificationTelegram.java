package ua.edu.sumdu.j2se.zykov.tasks.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NotificationTelegram extends TelegramLongPollingBot implements Notification {
    private static final Logger log = LoggerFactory.getLogger(NotificationTelegram.class);

    private String token;

    public NotificationTelegram() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("token_telegram_bot.txt.secret"));
            token = reader.readLine();
            reader.close();
        } catch (IOException e) {
               log.error("Failed read token from file: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
    }

    @Override
    public String getBotUsername() {
        return "Task_manager_new_bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public void display(long sec, String title) {
        sendMsg("Task " + title + " must start in " + (sec / 60) + " minutes");
    }

    public synchronized void sendMsg(String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        String chatID = "593292108";
        sendMessage.setChatId(chatID);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send message.");
        }
    }
}
