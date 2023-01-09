package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NewsDay {
    private String img = "";
    private String href = "";
    private String value = "";
    private String url = "https://quote.rbc.ru/category/Daily/";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//NewsDay.html", false)) {
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
            File file = new File("src//main//resources//NewsDay.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.selectFirst("div.q-item__wrap.l-col-center-590");
            var titleElements1 = titleElements.select("span.q-item__title.js-rm-central-column-item-text");
            var titleElements2 = titleElements.select("span.q-item__description");
            var titleElements3 = titleElements.select("div.q-item__company__slider__wrapper");
            var titleElements4 = titleElements.select("a");
            var titleElements5 = titleElements.select("img");

            String titleElement[] = titleElements1.text().split(":");

            value += "***" + titleElement[0] + "***" + "\n";
            value += "————————————————————————- \n";
            value += titleElements2.text() + "\n";
            value += titleElements3.text() + "\n";
            img = titleElements5.attr("src");
            href += titleElements4.attr("href");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setAll() {
        value = "";
        img = "";
        href = "";
    }

    public String getImg () {
        return img;
    }

    public String getHref () {
        return href;
    }
}
