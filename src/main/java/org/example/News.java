package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class News {
    private ArrayList<String> name = new ArrayList<>();
    private String value = "";
    private String url = "https://1prime.ru/Financial_market/";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//News.html", false)) {
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
            File file = new File("src//main//resources//News.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.select("article.rubric-list__article.rubric-list__article_default");
            var titleHref = titleElements.select("a");

            for (int i = 0; i <= 4; i++) {
                name.add(titleElements.get(i).text() + "\n");
                name.add("https://1prime.ru" + titleHref.get(i*2).attr("href") + "\n");
                name.add("————————————————————————-\n");
            }

            for (String element : name) {
                value += element;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setAll() {
        value = "";
        name.removeAll(name);
    }
}
