import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Ticket
{
    private String vVO;



    private String originName;
    private String destination;
    private String destinationName;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String carrier;
    private Integer stops;
    private Integer price;

    private static ArrayList<Ticket> ticketsCollection = new ArrayList<>();

    public Ticket ( String vVO, String originName, String destination, String destinationName, String departureDate, String departureTime, String arrivalDate, String arrivalTime, String carrier, Integer stops, Integer price)
    {
        this.vVO = vVO;
        this.originName = originName;
        this.destination = destination;
        this.destinationName = destinationName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
    }



    public  static  ArrayList<Ticket> getTicketsCollection() {
        return ticketsCollection;
    }

    public static int avgTime(ArrayList<Ticket> collectionTickets) {
        int sum = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDate departureTime;
        LocalDate arriveTime;

        for (Ticket ticket : collectionTickets) {
            departureTime = LocalDate.parse(ticket.departureDate + " " + ticket.departureTime, dateTimeFormatter);
            arriveTime = LocalDate.parse(ticket.arrivalDate + " " + ticket.arrivalTime, dateTimeFormatter);
            sum+= arriveTime.until(departureTime, ChronoUnit.MINUTES);
        }
        int avgTime = sum/collectionTickets.size();

        return avgTime;

    }

    public static double calculatePercentile(ArrayList<Ticket> collectionTickets) {
        double percentile = 0;
        percentile = (90/100) * Ticket.avgTime(collectionTickets);
        return percentile;
}
    @Override
    public String toString() {
        return "Ticket{" +
                "vVO='" + vVO + '\'' +
                ", originName='" + originName + '\'' +
                ", destination='" + destination + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", carrier='" + carrier + '\'' +
                ", stops=" + stops +
                ", price=" + price +
                '}';
    }








}
