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
    private URL url;
    private Set<Link> childrenOfLink = new HashSet<>();

    public Link(String nameOfLink)  {
        System.out.println(nameOfLink);
        try {
            this.url = new URL(nameOfLink);
        } catch (MalformedURLException e) { }
    }

    public Set<Link> getChildren() {
        try {
            Document doc = Jsoup.connect(url.toString()).maxBodySize(0).get();
            doc.select("a[href]").forEach(e -> {
                String current = e.absUrl("href");
                if (checkURL(current)) {
                    childrenOfLink.add(new Link(current));
                }
            });
            return childrenOfLink;
        } catch (IOException e) {
            return Collections.emptySet();
        }
    }

    private boolean checkURL(String url){
        return !this.url.toString().equals(url) &&
            url.startsWith(this.toString()) &&
            url.endsWith("/");
    }

    @Override
    public String toString() {
        return url.toString();
    }

}
