package ua.edu.sumdu.j2se.zykov.tasks.view;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class NotificationTelegram extends TelegramLongPollingBot implements Notification {

    private String chatID = "593292108";

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
        return "867775440:AAFUOf7OCl6eQRu-NDhrm9s6FEqBd65ahIE";
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
            e.printStackTrace();
        }
    }
}
