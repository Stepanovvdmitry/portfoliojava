package main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootApplication

public class Main
{
    public static void main(String[] args) {

        try {SpringApplication.run(Main.class, args);}
        catch (Exception ex) {
            System.out.println(new ResponseEntity(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }
}
