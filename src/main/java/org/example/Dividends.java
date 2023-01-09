package org.example;

import org.jsoup.Jsoup;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dividends {
    private String value = "";
    private ArrayList<String> name = new ArrayList<>();

    private String url = "https://blackterminal.com/dividends?hl=ru&exchange=MOEX%2CSPBEX";

    public void getUrl() {
        try(FileWriter writer = new FileWriter("src//main//resources//Dividends.html", false))
        {
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
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public String getAll() {
        try {
            //Открыть HTML и путь к нему
            File file = new File("src//main//resources//Dividends.html");
            //Запись файла в объект документ, указываем (файл, расшифровку, шапку сайта)
            var document = Jsoup.parse(file, "UTF-8", "hh.ru");
            //Выбираем элемент с которого будем парсить вместо пробелов ставим точки
            var mainElements = document.select("tr.w14");
            var titleElements = document.select("td.bordered.kv-nowrap.kv-align-middle.w14");
            int i = 0;
            //Разбираем с помощью for each и добавляем в динамический массив
            for (var element : mainElements) {
                name.add("✅Компания:  " + titleElements.get(0 + i).text() + "\n");
                name.add("\uD83D\uDD60Купить до:  " + titleElements.get(3 + i).text() + "\n");
                name.add("\uD83D\uDCB0Цена акции:  " + titleElements.get(2 + i).text() + "\n");
                name.add("\uD83D\uDCB2Дивиденд:  " + titleElements.get(6 + i).text() + "\n");
                name.add("\uD83C\uDFB2Процент:  " + titleElements.get(7 + i).text() + "\n" + "————————————————————————- \n");
                i += 9;
            }
            //Записываем элементы из массива в строку
            for (String element : name) {
                value += element;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Возращаем строку убирая все лишнее
        return value;
    }

    public void setAll() {
        value = "";
        name.removeAll(name);
    }

}
