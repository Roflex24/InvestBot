package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Index {
    private String value = "";
    private String url = "https://xn--j1amdg.online/indices-mcx-rtsi-spx-500/";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//Index.html", false)) {
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
            File file = new File("src//main//resources//Index.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleName = document.select("b.v-v-title.v-v-inline");
            var titleElement = document.select("b.v-v-info-main-small.v-v-inline.v-v-bad-val");

            value = "\uD83D\uDFE2Индекс МосБиржи: " + titleName.get(0).text() + "\n";
            value += "\uD83D\uDCB2Изменение за день : " + titleElement.get(0).text() + "\n";
            value += "————————————————————————- \n";
            value += "\uD83D\uDFE2Индекс Американской биржи: " + titleName.get(3).text() + "\n";
            value += "\uD83D\uDCB2Изменение за день : " + titleElement.get(3).text() + "\n";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setAll() {
        value = "";
    }
}
