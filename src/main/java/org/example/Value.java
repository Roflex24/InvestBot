package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Value {

    private ArrayList<String> name = new ArrayList<>();
    private String value = "";
    private String url = "https://xn----dtbfdbwspgnceulm.xn--p1ai/chart-online.php";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//Value.html", false)) {
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
            File file = new File("src//main//resources//Value.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleName = document.select("div.name_mobile");
            var titleValue = document.select("td.curs_zavtra");
            var titleProcent = document.select("td.spred_dwn");

            for (int i = 0; i <= 1; i++) {
                name.add("\uD83D\uDFE2" + titleName.get(i).text() + "\n");
                name.add("\uD83D\uDCB2Курс ЦБ: " + titleValue.get(i).text() + "\n");
                name.add("\uD83D\uDCC8Изменение за день: " + titleProcent.get(i).text() + "%\n");
                name.add("————————————————————————- \n");
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
