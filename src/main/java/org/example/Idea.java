package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Idea {
    private String value = "";
    private String url = "https://ru.tradingview.com/markets/stocks-russia/ideas/";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//Idea.html", false)) {
            // запись всей строки
            String html = String.valueOf(Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(20000)
                    .followRedirects(true)
                    .get());
            writer.write(html);
            // запись по символам
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public String getAll() {
        try {
            File file = new File("src//main//resources//Idea.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.selectFirst("div.tv-card-container__ideas.tv-card-container__ideas--with-padding.js-balance-content");
            var titleElements1 = document.select("div.tv-widget-idea__title-row");
            var titleElements11 = document.select("div.tv-social-row__start");
            var titleElements2 = titleElements11.select("span.tv-card-social-item__count");
            var titleElements22 = titleElements.select("div.tv-widget-idea__cover-wrap");
            var titleElements3 = titleElements22.select("a");

            for (int i = 0; i <= 17; i++) {
                if (Integer.parseInt(titleElements2.get(i).text()) > 50) {
                    value += titleElements1.get(i).text() + "\n";
                    value += "https://ru.tradingview.com" + titleElements3.get(i).attr("href") + "\n";
                    value += "Оценка стратегии: " + titleElements2.get(i).text() + "\n";
                    value += "————————————————————————-\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setAll() {
        value = "";
    }
}
