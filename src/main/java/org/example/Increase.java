package org.example;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Increase {
    private String value = "";
    private String url = "https://smart-lab.ru/q/shares/order_by_last_to_month_price/desc/?val_middle_gt=10000000&capitalization_gt=100000000000";

    public void getUrl() {
        try (FileWriter writer = new FileWriter("src//main//resources//Increase.html", false)) {
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
            File file = new File("src//main//resources//Increase.html");
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            var titleElements = document.selectFirst("table.simple-little-table.trades-table");
            var titleElements1 = titleElements.select("tr");

            for (int i = 2; i <= 8; i++) {
                String[] b = titleElements1.get(i).text().replace(" ап", "").replace(" ао", "").replace(" зао", "")
                        .replace(" зап", "").replace(" clA", "").split(" ");
                value += "\uD83D\uDCC8" + b[0] + "." + b[2] + " /" + b[3] + "\n\uD83D\uDCB2Цена: " + b[4] + " (" + b[8] + ")\n————————————————————————- \n";
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
