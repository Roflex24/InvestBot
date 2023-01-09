package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Cryptocurrency {

    private ArrayList<String> name = new ArrayList<>();
    private String value = "";
    private String url = "https://mainfin.ru/crypto/rates";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//Cryptocurrency.html", false)) {
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
            File file = new File("src//main//resources//Cryptocurrency.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.selectFirst("tr.row.body.odd").text();
            var dataElements = document.selectFirst("tr.row.body.even").text();

            String elements = titleElements + " " + dataElements;
            String[] words = elements.split("\\s");
            for (String element : words) {
                name.add(element);
            }

            for (int i = 0; i <= 9; i += 9) {
                value += "\uD83D\uDFE2" + name.get(0+i) + "\n" +
                        "\uD83D\uDCB2Цена: " + name.get(2+i) + "\n" +
                        "\uD83D\uDCC8Изменение за день: " + name.get(3+i) + "$" + " (" + name.get(8+i) + ")\n————————————————————————- \n";
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

