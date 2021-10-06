import com.google.gson.*;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{private static final String FILENAME= "data/map.json";

    public static void main(String[] args) throws IOException
    {
        URL linkOfMoscowMetro = new URL("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0");

        Metro metro = Parser.parse(linkOfMoscowMetro);

        System.out.println("Чтение из ссылки: ");
        System.out.println();
        System.out.println("Переходы между станциями:");

        for ( Station station : metro.getConnections().keySet())
        {   System.out.println("---------------");
            System.out.println("Линия: "+station.getLine().getNumber() + " | Станция: " + station);
            metro.getConnections().get(station).forEach(station1 ->System.out.println( "Линия: "+station1.getLine().getNumber()+ " | Станция: "+station1));

        }
        System.out.println("---------------------------------------------");
        System.out.println();
        System.out.println("Линии и станции:");

        for ( Line line : metro.getLines())
        {   System.out.println("---------------");
            System.out.println("Название линии: " + line.getName() + "\n" + "Номер линии: "  + line.getNumber() + "\n" + "Станации: " + line.getStations());

        }
        System.out.println("---------------------------------------------");

        String json =
                new GsonBuilder().registerTypeAdapter(Metro.class, new MetroJsonAdapter()).setPrettyPrinting().
                        create().toJson(metro);




        try(FileWriter writer = new FileWriter(FILENAME))
        {
            writer.write(json);
            writer.flush();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        Metro metroIsReaded = new GsonBuilder().registerTypeAdapter(Metro.class, new MetroJsonAdapter()).create().fromJson( json, Metro.class );
        System.out.print("\n\n\n");
        System.out.println("Чтение из Json-файла: ");
        System.out.println();
        System.out.println("Количества станций на каждой линии:");
        System.out.println("---------------");
        for (String keyNumberOfLine : metroIsReaded.getStations().keySet())
        {
            System.out.println("Номер линии: " + keyNumberOfLine + " | Количество станций: " + metroIsReaded.getStations().get(keyNumberOfLine).size());
        }
        System.out.println("---------------------------------------------");


}}
