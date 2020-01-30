package ua.edu.sumdu.j2se.zykov.tasks.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;

public class NotificationTelegram extends TelegramLongPollingBot implements Notification {
    private static final Logger log = LoggerFactory.getLogger(NotificationTelegram.class);

    private String token;
    private long chatID;

    public NotificationTelegram() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("token_telegram_bot.txt.secret"));
            token = reader.readLine();
            reader = new BufferedReader(new FileReader("chatID.txt"));
            chatID = Long.parseLong(reader.readLine());
            reader.close();
        } catch (IOException e) {
               log.warn("Failed read token or chatID from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            log.warn("File chatID is empty.");
        } catch (IllegalArgumentException e) {
            log.error("Incorrectly format telegram token.");
            System.out.println("Incorrectly format telegram token. Decrypt token and try again.");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        chatID = update.getMessage().getChatId();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("chatID.txt"));
            writer.write(chatID + "");
            writer.close();
        } catch (IOException e) {
            log.error("Failed save chatID to file: " + e.getMessage());
        }
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
        sendMessage.setChatId(chatID);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send message.");
        }
    }
}
