package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
    //вместо звездочек подставляйте свои данные
    final private String BOT_TOKEN = "5626900383:AAENyWQSJE2oOdXYI5lts0AeD-th_bHwBkQ";
    final private String BOT_NAME = "Zametki228_bot";
    Storage storage;
    Dividends dividends;
    Cryptocurrency cryptoCurrency;
    Value value;
    BankRate bankRate;
    News news;
    Index index;
    NewsDay newsDay;
    Idea idea;
    Increase increase;
    Fall fall;
    ReplyKeyboardMarkup replyKeyboardMarkup;
    InlineKeyboardMarkup inlineKeyboardMarkup;

    Bot() {
        storage = new Storage();
        dividends = new Dividends();
        cryptoCurrency = new Cryptocurrency();
        value = new Value();
        bankRate = new BankRate();
        news = new News();
        index = new Index();
        newsDay = new NewsDay();
        idea = new Idea();
        increase = new Increase();
        fall = new Fall();
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String [] message = new String[3];
                message = parseMessage(inMess.getText(), chatId);
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                InputFile file = new InputFile(message[1]);
                SendMessage text = new SendMessage();
                SendPhoto photo = new SendPhoto();
                //Добавляем в наше сообщение id чата а также наш ответ
                text.setChatId(chatId);
                text.setText(message[0]);
                photo.setChatId(chatId);
                photo.setCaption(message[0]);
                photo.setPhoto(file);
                //Отправка в чат
                if (message[1].equals("photo")) { text.setReplyMarkup(replyKeyboardMarkup); execute(text); }
                else if (message[2].equals(("href"))) { text.setReplyMarkup(replyKeyboardMarkup); execute(photo); }
                else {
                    photo.setReplyMarkup(inlineKeyboardMarkup); text.setReplyMarkup(replyKeyboardMarkup);
                    execute(photo);
                }
                //Обнуление данных
                dividends.setAll();cryptoCurrency.setAll();value.setAll();bankRate.setAll();news.setAll();index.setAll();newsDay.setAll();idea.setAll();
                increase.setAll();fall.setAll();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String [] parseMessage(String textMsg, String chatId) {
        String[] message = {"message", "photo", "href"};
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId);
        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (textMsg.length() < 7 & !textMsg.equals("/start") & !textMsg.equals("➡") & !textMsg.equals("↩")) {
            message[0] = Company.getValue(textMsg.replace("/", ""));
        } else {
            switch (textMsg) {
                case ("/start") -> {
                    message[0] = "Ботов готов к использованию";
                    initKeyboard();
                }
            //дивиденды
                case ("обновить дивиденды") -> {
                    dividends.getUrl();
                    message[0] = "Данные дивидендов обновлены";
                }
                case ("\uD83D\uDD34дивиденды") -> message[0] = dividends.getAll();
            //крипта
                case ("обновить крипту") -> {
                    cryptoCurrency.getUrl();
                    message[0] = "Данные крипты обновлены";
                }
                case ("\uD83D\uDFE0крипта") -> {
                    message[0] = cryptoCurrency.getAll();
                    message[1] = "https://inbusiness.kz/uploads/55/images/lBDR9rdK.jpg";
                }
            //валюта
                case ("обновить валюты") -> {
                    value.getUrl();
                    message[0] = "Данные валют обновлены";
                }
                case ("\uD83D\uDFE1валюта") -> {
                    message[0] = value.getAll();
                    message[1] = "https://sib.fm/storage/article/May2022/istockphoto-1062637876-612x612.jpg";
                }
            //ставка ЦБ
                case ("обновить ставку ЦБ") -> {
                    bankRate.getUrl();
                    message[0] = "Данные ставки ЦБ обновлены";
                }
                case ("\uD83D\uDFE2ставка ЦБ") -> {
                    message[0] = bankRate.getAll();
                    message[1] = "https://zakupki-kontur.ru/wp-content/uploads/2022/10/postavshhik-otslezhivat-izmeneniya-klyuchevoj-stavki-cb.png";
                }
            //новости
                case ("обновить новости") -> {
                    news.getUrl();
                    message[0] = "Новости обновлены";
                }
                case ("\uD83D\uDD34новости") -> {
                    message[0] = news.getAll();
                    message[1] = "https://thumbs.dreamstime.com/b/newspaper-headline-news-51970209.jpg";
                }
            //Индексы
                case ("обновить индексы") -> {
                    index.getUrl();
                    message[0] = "Индексы обновлены";
                }
                case ("\uD83D\uDFE3индекс") -> {
                    message[0] = index.getAll();
                    message[1] = "https://cdn-st1.rtr-vesti.ru/vh/pictures/hd/332/594/0.jpg";
                }
            //Событие дня
                case ("обновить события дня") -> {
                    newsDay.getUrl();
                    message[0] = "события дня обновлены";
                }
                case ("\uD83D\uDD0D\uD83D\uDD0D\uD83D\uDD0D события дня \uD83D\uDD0E\uD83D\uDD0E\uD83D\uDD0E") -> {
                    message[0] = newsDay.getAll();
                    message[1] = newsDay.getImg();
                    message[2] = "have";
                    inlineKeyboard("подробнее", newsDay.getHref());
                }
            //Идеи стратегии
                case ("обновить стратегии") -> {
                    idea.getUrl();
                    message[0] = "Стратегии обновлены";
                }
                case ("\uD83D\uDFE0стратегии") -> {
                    message[0] = idea.getAll();
                    message[1] = "https://rusokulu.ru/wp-content/uploads/2019/05/shahi-1233x500.jpg";
                }
            //Топ роста за месяц
                case ("обновить топ роста") -> {
                    increase.getUrl();
                    message[0] = "топ роста обновлен";
                }
                case ("\uD83D\uDFE2топ роста") -> {
                    message[0] = increase.getAll();
                    message[1] = "https://www.shkolazhizni.ru/img/content/i144/144006_or.jpg";
                }
            //Топ роста за месяц
                case ("обновить топ падения") -> {
                    fall.getUrl();
                    message[0] = "топ падения обновлен";
                }
                case ("\uD83D\uDFE3топ падения") -> {
                    message[0] = fall.getAll();
                    message[1] = "https://rossaprimavera.ru/static/files/3beb7e2ecc70.jpg";
                }
            //Цитаты
                case ("\uD83D\uDCC8\uD83D\uDCC9\uD83D\uDCC8 цитаты инвесторов \uD83D\uDCC8\uD83D\uDCC9\uD83D\uDCC8") -> {
                    message[0] = storage.getRandQuote();
                }
                //Обновить все данные
                case ("обновить все") -> {
                    InputFile file = new InputFile("https://miro.medium.com/max/1200/1*mg5YaPigfU8-cwKtkxV9gw.png");
                    photo.setCaption("Данные обновляются. Пожалуйста подождите...");
                    photo.setPhoto(file);
                    try {
                        execute(photo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    dividends.getUrl();
                    cryptoCurrency.getUrl();
                    value.getUrl();
                    bankRate.getUrl();
                    news.getUrl();
                    index.getUrl();
                    newsDay.getUrl();
                    idea.getUrl();
                    increase.getUrl();
                    fall.getUrl();
                    message[0] = "Данные обновлены";
                }
                case ("➡") -> {
                    initKeyboard1();
                    message[0] = "Кнопки обновлены";
                }
                case ("↩") -> {
                    initKeyboard();
                    message[0] = "Кнопки обновлены";
                }
                // Любое другое сообщение
                default -> message[0] = "Сообщение не распознано";
            }
        }
        return message;
    }
    public void initKeyboard()
    {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        //Добавляем одну кнопку с текстом "текст" наш ряд
        keyboardRow.add("\uD83D\uDD34дивиденды");
        keyboardRow.add("\uD83D\uDFE0крипта");
        keyboardRow.add("\uD83D\uDFE1валюта");
        keyboardRow2.add("\uD83D\uDFE2ставка ЦБ");
        keyboardRow2.add("\uD83D\uDFE3индекс");
        keyboardRow2.add("➡");
        keyboardRow3.add("\uD83D\uDD0D\uD83D\uDD0D\uD83D\uDD0D события дня \uD83D\uDD0E\uD83D\uDD0E\uD83D\uDD0E");
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public void initKeyboard1()
    {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        //Добавляем одну кнопку с текстом "текст" наш ряд
        keyboardRow.add("\uD83D\uDFE2топ роста");
        keyboardRow.add("\uD83D\uDFE3топ падения");
        keyboardRow2.add("\uD83D\uDD34новости");
        keyboardRow2.add("\uD83D\uDFE0стратегии");
        keyboardRow2.add("↩");
        keyboardRow3.add("\uD83D\uDCC8\uD83D\uDCC9\uD83D\uDCC8 цитаты инвесторов \uD83D\uDCC8\uD83D\uDCC9\uD83D\uDCC8");
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public void inlineKeyboard(String text, String href) {
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(text);
        inlineKeyboardButton1.setUrl(href);
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
    }
}