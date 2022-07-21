import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Link
{
    private static Set<String> result = new TreeSet<>(String::compareTo);
    private URL url;
    private Set<Link> childrenOfLink = new HashSet<>();

    public Link(String nameOfLink)  {
        isAdd(nameOfLink);
        try {
            this.url = new URL(nameOfLink);
        } catch (MalformedURLException e) { }
    }

    public Set<Link> getChildren() {
        try {
            Document doc = Jsoup.connect(url.toString()).maxBodySize(0).get();
            doc.select("a[href]").forEach(e -> {
                String current = e.absUrl("href");
                if (checkURL(current) && isAdd(current)) {
                    childrenOfLink.add(new Link(current));
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return childrenOfLink;
    }

    private boolean checkURL(String url){
        return (!this.url.toString().equals(url)) &&
            url.startsWith(this.url.toString()) &&
            url.length() > this.url.toString().length() &&
            url.endsWith("/");
    }

    @Override
    public String toString() {
        return url.toString();
    }

    public String getResult() {
        StringBuilder sb = new StringBuilder();
        result.forEach(str -> sb.append("\n").append(getAmountOfTabs(str)).append(str));
        return sb.toString();
    }

    private String getAmountOfTabs(String str) {
        Pattern patternOfStringSlash = Pattern.compile("/");
        String[] linkWithOutStartPage = str.split(this.url.toString(),2);
        String stringLinkWithOutStartPage = linkWithOutStartPage[1];
        Matcher matcherOfStringSlash = patternOfStringSlash.matcher(stringLinkWithOutStartPage);
        int amountOfSlash = 0;
        StringBuilder containerOftabs = new StringBuilder();
        while (matcherOfStringSlash.find()){
            amountOfSlash++;
        }
        int amountOftabs = amountOfSlash;
        for (int i = 1; i <= amountOftabs; i++) {
        containerOftabs.append("\t");
        }
        return containerOftabs.toString();
    }

    private static synchronized boolean isAdd(String current) {
        return result.add(current);
    }
}
