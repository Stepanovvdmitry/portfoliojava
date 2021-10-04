
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main
{
    public static String filePath = "C:\\Users\\User\\portgolioJAVA\\Portfolio programmist java. Main work\\TestWork\\JsonTicketsParser\\src\\main\\resources\\1.json";
    public static void main(String[] args) throws ParseException {
        System.out.println(getJsonFile());
       parseTickets(parseJsonFile());

        System.out.println("Массив билетов: ");
        Ticket.getTicketsCollection().forEach(ticket -> {System.out.println(ticket.toString());});
        System.out.println("Cреднее время полета между городами Владивосток и Тель-Авив в минутах: " + Ticket.avgTime(Ticket.getTicketsCollection()));
        System.out.println("90-й процентиль времени полета между городами  Владивосток и Тель-Авив в минутах: " + Ticket.percentile(Ticket.getLatencies(),90));


    }


    private static JSONArray parseJsonFile() throws ParseException {


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(getJsonFile());
            JSONArray ticketsArray = (JSONArray) jsonObject.get("tickets");


        return ticketsArray ;
    }

    private static void parseTickets(JSONArray ticketArray)
    {
        ticketArray.forEach(ticketObject -> {
            JSONObject ticketJsonObject = (JSONObject) ticketObject;
            Ticket ticket = new Ticket(
                    (String) ticketJsonObject.get("origin"),
                    (String) ticketJsonObject.get("origin_name"),
                    (String) ticketJsonObject.get("destination"),
                    (String) ticketJsonObject.get("destination_name"),
                    (String) ticketJsonObject.get("departure_date"),
                    (String) ticketJsonObject.get("departure_time"),
                    (String) ticketJsonObject.get("arrival_date"),
                    (String) ticketJsonObject.get("arrival_time"),
                    (String) ticketJsonObject.get("carrier"),
                    (Long) ticketJsonObject.get("stops"),
                    (Long) ticketJsonObject.get("price")
            );
            Ticket.getTicketsCollection().add(ticket);
        });
    }
    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        String str =  builder.toString().trim();
        return builder.toString();
    }
}
