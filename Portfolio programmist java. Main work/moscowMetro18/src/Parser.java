import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    private static Metro metro = new Metro();
    static Metro parse(URL url) throws IOException {

        TreeMap<String, List<String>> mapStations = new TreeMap<>((string1, string2) -> Double.compare(Double.parseDouble(string1.replaceAll("[^0-9]",".5")), Double.parseDouble(string2.replaceAll("[^0-9]",".5"))));
        List<Line> lines = new ArrayList<>();
        Document doc = Jsoup.connect(url.toString()).maxBodySize(0).get();

        Elements table = doc.select("table");
        Elements attrs = table.attr("class", "standard sortable");
        Elements tds = attrs.select("td");

        StringBuilder containerForTds = new StringBuilder();
        StringBuilder containerForStations = new StringBuilder();
        StringBuilder containerForLines = new StringBuilder();
        tds.forEach(td -> containerForTds.append(td.text()  +"\n"));

        String [] stringsOfText = containerForTds.toString().split("\n");


        for (int i = 8; i < stringsOfText.length; i +=8)

        {  String [] fragmentsOfStringOftext = stringsOfText[i-1].split(" ");
            if (stringsOfText[i-1].equals("1301")) {break;}
            else if (fragmentsOfStringOftext.length == 2) { containerForStations.append(fragmentsOfStringOftext[1].charAt(0) + "" + fragmentsOfStringOftext[1].charAt(1) + " " + stringsOfText[i]+ "\n" );}

            else if (stringsOfText[i-1].charAt(0) != '0') {containerForStations.append( stringsOfText[i-1].charAt(0) + ""  + stringsOfText[i-1].charAt(1) + " " + stringsOfText[i]+ "\n" );}
            else if (stringsOfText[i-1].charAt(3) == 'А') {containerForStations.append( stringsOfText[i-1].charAt(1) + ""  + stringsOfText[i-1].charAt(2)+ ""  + stringsOfText[i-1].charAt(3)+ " " + stringsOfText[i]+ "\n" );}

            else   containerForStations.append(stringsOfText[i-1].charAt(1)+ " " + stringsOfText[i]+ "\n" );

        }


        String[] stations = containerForStations.toString().split("\n");


        for (Element td : tds)
        {
            containerForLines.append(td.select("span").text() + "/" + td.select("span").select("a").select("img").attr("alt") + "\n");
        }

        String[] array = containerForLines.toString().replaceAll("(?m)^\\s*$[\n\r]{1,}", "").split("\n");

        List<String> linesArray = Arrays.asList(array);
        List<String> lineListBeforeFormating = new ArrayList<>();
        for(  String line : linesArray)
        {
            String[] fragmentsOfLine = line.split("/");

            if (Pattern.matches(".+линия$", line))
            {   String formatedFragment = fragmentsOfLine[0];
                if(fragmentsOfLine[0].charAt(0) == '0') {formatedFragment = fragmentsOfLine[0].replaceAll("0","");}

                String newLine = formatedFragment.substring(0,fragmentsOfLine[0].indexOf(" ")) + " " + fragmentsOfLine[1];
                lineListBeforeFormating.add(newLine);}

        }

        Set<String> lineSetWithoutrRepetition = new TreeSet<String>(lineListBeforeFormating);
        List<String> stringsOfLines = new ArrayList<>();
        for(String s: lineSetWithoutrRepetition)
        { if (Pattern.matches(".+линия$", s))
            stringsOfLines.add(s. replaceAll("\\s+", " "));}

        for (String line : stringsOfLines)
        {
            String[] number2nameOfLine =  line.split(" ");
            lines.add(new Line(number2nameOfLine[0],number2nameOfLine[1]));

        }

        for (String station : stations) {

            String[] number2station = station.split(" ", 2);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).getNumber().equals(number2station[0])) {
                    lines.get(i).addStation(new Station(number2station[1], lines.get(i)));
                }



            }
        }

        for (int i = 0; i < lines.size(); i++)
        {
            List<String> listOfNameStations = new ArrayList<>();
            List<Station> listOfStations = lines.get(i).getStations();
            listOfStations.stream().forEach(stn -> listOfNameStations.add(stn.getName()));

            mapStations.put(lines.get(i).getNumber(),listOfNameStations);


        }
        Elements trs = attrs.select("tr");

        metro.setLines(lines);
        metro.setStations(mapStations);
        parseConnections(trs);
        return metro;



    }

    private static void parseConnections(Elements tempTr) { //передается выборка по всем тегам TR
        HashMap<String, Line> number2line = new HashMap<>();
        metro.getLines().forEach(line -> number2line.put(line.getNumber(), line));
    tempTr.forEach(t -> {


        Elements tdAll = t.select("td");

        if (tdAll.size() >= 7) {


            Element td0 = tdAll.get(0);

            Elements span0 = td0.select("span");



            Element td1 = tdAll.get(1);

            Elements span1 = td1.select("a");

            Station tempStation = new Station(span1.get(0).text(), new Line(span0.get(0).text().replaceAll("^[0]", ""), span0.get(1).attr("title")));

            Elements spanConn = tdAll.get(3).select("span");

            String iS = "";

            TreeSet<Station> tempStations = new TreeSet<>();

            for (int i = 0; i < spanConn.size(); i += 2) {

                iS = "";

                iS = spanConn.get(i).text().replaceAll("^[0]", "").trim();
                if(iS.equals("14") || iS.equals("13")) {continue;}

                Element conn = spanConn.get(i + 1);

                final String[] sn = {""};

                final Station[] st = new Station[1];



                   st[0] = new Station("", new Line(iS,  number2line.get(iS).getName()));
                metro.getLines().forEach( line -> line.getStations().forEach(station -> { //аналог индекса для питерского метро

                    Pattern pat = Pattern.compile(station.getName() + " ", Pattern.UNICODE_CHARACTER_CLASS + Pattern.CASE_INSENSITIVE);

                    Matcher mat = pat.matcher(conn.attr("title"));

                    if (mat.find() && station.getLine().getNumber().equals(st[0].getLine().getNumber())) {

                        sn[0] = station.getName();

                        st[0] = station;

                    }

                }));



                if (!sn[0].equals("")) {

                    tempStations.add(st[0]);

                }

            }

            if(!iS.equals("14") && !iS.equals("") && !iS.equals("13")) {metro.getConnections().put(tempStation, tempStations);}

            if (span0.size() == 5) {

                tempStation = new Station(span1.get(0).text(), new Line(span0.get(2).text().replaceAll("^[0]", ""), span0.get(3).attr("title")));

                spanConn = tdAll.get(3).select("span");

                iS = "";

                tempStations = new TreeSet<>();

                for (int i = 0; i < spanConn.size(); i += 2) {

                    //System.out.println();

                    iS = "";


                    iS = spanConn.get(i).text().replaceAll("^[0]", "").trim();
                    if(iS.equals("14") || iS.equals("13")) {continue;}
                    Element conn = spanConn.get(i + 1);

                    final String[] sn = {""};

                    final Station[] st = new Station[1];

                    st[0] = new Station("", new Line(iS, number2line.get(iS).getName()));

                    metro.getLines().forEach( line -> line.getStations().forEach(station -> {

                        Pattern pat = Pattern.compile(station.getName() + " ", Pattern.UNICODE_CHARACTER_CLASS + Pattern.CASE_INSENSITIVE);

                        Matcher mat = pat.matcher(conn.attr("title"));

                        if (mat.find() && station.getLine().getNumber().equals(st[0].getLine().getNumber())) {

                            sn[0] = station.getName();

                            st[0] = station;

                        }

                    }));

                    if (!sn[0].equals("")) {

                        tempStations.add(st[0]);

                    }

                }

                if(!iS.equals("14") && !iS.equals("") && !iS.equals("13")) {metro.getConnections().put(tempStation, tempStations);}

            }

        }

    });

}
}
