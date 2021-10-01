
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class Main
{
    public static String filePath = "src\\tickets.json";
    public static void main(String[] args) {


       parseTickets(parseJsonFile());

        System.out.println("Массив билетов: ");
        Ticket.getTicketsCollection().forEach(ticket -> {System.out.println(ticket.toString());});
        System.out.println("Cреднее время полета между городами Владивосток и Тель-Авив: " + Ticket.avgTime(Ticket.getTicketsCollection()));
        System.out.println("90-й процентиль времени полета между городами  Владивосток и Тель-Авив: " + Ticket.calculatePercentile(Ticket.getTicketsCollection()));



    }


    private static JSONArray parseJsonFile(){
        JSONArray ticketsArray = null;
        try {
            FileReader reader = new FileReader(filePath);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            ticketsArray = (JSONArray) jsonObject.get("tickets");
            parseTickets(ticketsArray);
        }
        catch (Exception ex) {ex.printStackTrace();
        }
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
                    (Integer) ticketJsonObject.get("stops"),
                    (Integer) ticketJsonObject.get("price")
            );
            Ticket.getTicketsCollection().add(ticket);
        });
    }
}
