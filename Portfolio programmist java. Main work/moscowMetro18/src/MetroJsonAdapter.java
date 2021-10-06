import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MetroJsonAdapter extends TypeAdapter<Metro>
{

    @Override
    public void write(JsonWriter jsonWriter, Metro metro) throws IOException
    {
        jsonWriter.beginObject();

        jsonWriter.name("stations");
        jsonWriter.beginObject();

      for (String keyNumberofLine : metro.getStations().keySet())
      {
          jsonWriter.name(keyNumberofLine);
          jsonWriter.beginArray();

        for  (String station : metro.getStations().get(keyNumberofLine))
        {
            jsonWriter.value(station);
        }
          jsonWriter.endArray();
      }

        jsonWriter.endObject();


        jsonWriter.name("lines");
        jsonWriter.beginArray();

            for (Line line : metro.getLines())
            {
                jsonWriter.beginObject();
                jsonWriter.name("number").value(line.getNumber());
                jsonWriter.name("name").value(line.getName());
                jsonWriter.endObject();
            }

        jsonWriter.endArray();


        jsonWriter.name("connections");
        jsonWriter.beginArray();

            for ( Station station : metro.getConnections().keySet())
            {   jsonWriter.beginArray();

                jsonWriter.beginObject();
                jsonWriter.name("line").value(station.getLine().getNumber());
                jsonWriter.name("station").value(station.getName());
                jsonWriter.endObject();
                metro.getConnections().get(station).forEach(station1 -> {
                    try {
                        jsonWriter.beginObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonWriter.name("line").value(station1.getLine().getNumber());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonWriter.name("station").value(station1.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonWriter.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                jsonWriter.endArray();

            }

        jsonWriter.endArray();

        jsonWriter.endObject();

    }


    @Override
    public Metro read(JsonReader jsonReader) throws IOException {

        Metro metro = new Metro();
        TreeMap<String, List<String>> stations = new TreeMap<>((string1, string2) -> Double.compare(Double.parseDouble(string1.replaceAll("[^0-9]",".5")), Double.parseDouble(string2.replaceAll("[^0-9]",".5"))));
        String station;

        jsonReader.beginObject();
        while (jsonReader.hasNext())
        {

                if (jsonReader.nextName().equals("stations")){
                    jsonReader.beginObject();
                    while (jsonReader.hasNext())
                    {
                    String numberOfLine = jsonReader.nextName();
                    ArrayList<String> arrayOfStations = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                    station = jsonReader.nextString();
                    arrayOfStations.add(station);}
                    stations.put(numberOfLine, arrayOfStations);
                    jsonReader.endArray();
                    }
                    jsonReader.endObject();}

                else jsonReader.skipValue();

        }
        jsonReader.endObject();

        metro.setStations(stations);

        return metro;
    }

}


