import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private Long stops;
    private Long price;

    private static ArrayList<Ticket> ticketsCollection = new ArrayList<>();

    public Ticket ( String vVO, String originName, String destination, String destinationName, String departureDate, String departureTime, String arrivalDate, String arrivalTime, String carrier, Long stops, Long price)
    {
        this.vVO = vVO;
        this.originName = originName;
        this.destination = destination;
        this.destinationName = destinationName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
    }



    public  static  ArrayList<Ticket> getTicketsCollection() {
        return ticketsCollection;
    }

    public static long avgTime(ArrayList<Ticket> collectionTickets) {
        long sumDate = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate departureDatelocal;
        LocalDate arriveDatelocal;
        LocalTime departureTimelocal;
        LocalTime arriveTimelocal;

        for (Ticket ticket : collectionTickets) {
            String[] hoursMinutesArrive = ticket.arrivalTime.split(":");
            String[] hoursMinutesDeparture = ticket.departureTime.split(":");
            String[] dayMonthYearArrive = ticket.arrivalDate.replace(".", " ").split(" ");
            String[] dayMonthYearDeparture = ticket.arrivalDate.replace(".", " ").split(" ");
            LocalDateTime toDateTimeArrive = LocalDateTime.of(Integer.parseInt(dayMonthYearArrive[0]), Integer.parseInt(dayMonthYearArrive[1]), Integer.parseInt(dayMonthYearArrive[2]), Integer.parseInt(hoursMinutesArrive[0]), Integer.parseInt(hoursMinutesArrive[1]));
            LocalDateTime toDateTimeDeparture = LocalDateTime.of(Integer.parseInt(dayMonthYearDeparture[0]), Integer.parseInt(dayMonthYearDeparture[1]), Integer.parseInt(dayMonthYearDeparture[2]), Integer.parseInt(hoursMinutesDeparture[0]), Integer.parseInt(hoursMinutesDeparture[1]));
            sumDate += Math.abs(ChronoUnit.MINUTES.between(toDateTimeArrive, toDateTimeDeparture));

        }



        long avgTime = sumDate/collectionTickets.size();

        return avgTime;

    }

    public static double calculatePercentile(ArrayList<Ticket> collectionTickets) {
        double percentile = 0;
        percentile = ((90* Ticket.avgTime(collectionTickets))/100) ;
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
