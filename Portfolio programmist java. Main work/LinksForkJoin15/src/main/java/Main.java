import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;


public class Main {


    public static void main(String[] args) throws IOException {

        Link link = new Link("https://skillbox.ru/");
        LinkForkAndJoin linkForkAndJoin = new LinkForkAndJoin(link);
        try (FileWriter writer = new FileWriter("notes.txt", false)) {
            Link listLinks = new ForkJoinPool().invoke(linkForkAndJoin);

            System.out.println(listLinks);

            writer.write(listLinks.toString());
            writer.flush();
        }
    }

}

