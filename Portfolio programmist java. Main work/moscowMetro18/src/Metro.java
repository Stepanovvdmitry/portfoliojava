import com.google.gson.annotations.JsonAdapter;

import java.util.*;

@JsonAdapter(MetroJsonAdapter.class)
public class Metro
{
    private TreeMap<String, List<String>> stations = new TreeMap<>((string1, string2) -> Double.compare(Double.parseDouble(string1.replaceAll("[^0-9]",".5")), Double.parseDouble(string2.replaceAll("[^0-9]",".5"))));
    private List<Line> lines = new ArrayList<>();
    private TreeMap<Station, TreeSet<Station>> connections = new TreeMap<>();

    public TreeMap<Station, TreeSet<Station>> getConnections() {
        return connections;
    }

    public void setConnections(TreeMap<Station, TreeSet<Station>> connections) {
        this.connections = connections;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }

    public void setStations(TreeMap<String, List<String>> stations) {
        this.stations = stations;
    }


    public List<Line> getLines() {
        Collections.sort(lines, (line1, line2) -> Double.compare(Double.parseDouble(line1.getNumber().replaceAll("[^0-9]",".5")), Double.parseDouble(line2.getNumber().replaceAll("[^0-9]",".5"))));
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
