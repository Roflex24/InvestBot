package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BankRate {
    private String value = "";
    private String url = "https://www.cbr.ru/hd_base/KeyRate/";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//СentralBankRate.html", false)) {
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
            File file = new File("src//main//resources//СentralBankRate.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.select("td");
            value = "\uD83D\uDCE2На " + titleElements.get(0).text() + "\n✔Cтавка ЦБ: " + titleElements.get(1).text() + '%';

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setAll() {
        value = "";
    }
}