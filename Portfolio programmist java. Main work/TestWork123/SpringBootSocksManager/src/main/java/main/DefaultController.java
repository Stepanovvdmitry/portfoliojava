package main;

import main.model.Socks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class DefaultController
{
    @Autowired
    SocksRepositiry socksRepositiry;

    @PostMapping("/api/socks/income")
    public ResponseEntity addSocks(@RequestBody Socks socks)
    {   Integer socksQuantity = socks.getQuantity();
        if(socksQuantity >= 0) {

            Socks newSocks = socksRepositiry.save(socks);
            return new ResponseEntity("Задача добавлена: id: " + newSocks.getId(), HttpStatus.OK);

        }

    else  return new ResponseEntity("Сумма должная быть положительной", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/api/socks/outcome")
    public ResponseEntity putSocks(@RequestBody Socks socksRequest) {
        Iterable<Socks> socksIterable = socksRepositiry.findAll();
        ArrayList<Socks> socks = new ArrayList<>();
        socksIterable.forEach(s -> socks.add(s));
        Socks newSocks = new Socks();
        ArrayList<String> arrayIDs = new ArrayList<>();
        for (Socks socksItem : socks) {
            if (socksItem.getQuantity() - socksRequest.getQuantity() >= 0 && socksRequest.getColor().equals(socksItem.getColor()) && socksRequest.getCottonPart().equals(socksItem.getCottonPart())) {

                socksItem.setQuantity(socksItem.getQuantity() - socksRequest.getQuantity());
               newSocks = socksRepositiry.save(socksItem);

               arrayIDs.add(String.valueOf(newSocks.getId()));

            } else return new ResponseEntity("Сумма ухода должна быть меньше суммы прихода,цвет с таким содержанием хлопка не найден", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity("Задача обновлена: id: " + arrayIDs, HttpStatus.OK);
    }


    @GetMapping("api/socks")
    public ResponseEntity getCount(@RequestParam String color, @RequestParam String operation, @RequestParam String cottonPart ) {
        Integer count = 0;
        Iterable<Socks> socksIterable = socksRepositiry.findAll();
        ArrayList<Socks> socks = new ArrayList<>();
        socksIterable.forEach(s -> socks.add(s));
        if (operation.equals("moreThan")){
            for (Socks socksItem : socks){
               if (socksItem.getColor().equals(color) && socksItem.getCottonPart() > Integer.parseInt(cottonPart)){
                count+= socksItem.getQuantity();
               }
            }
        }
        else if (operation.equals("lessThan")){
            for (Socks socksItem : socks){
                if (socksItem.getColor().equals(color) && socksItem.getCottonPart() < Integer.parseInt(cottonPart)){
                    count+= socksItem.getQuantity();
                }
            }
        }
        else if (operation.equals("equal")){
            for (Socks socksItem : socks){
                if (socksItem.getColor().equals(color) && socksItem.getCottonPart() == Integer.parseInt(cottonPart)){
                    count+= socksItem.getQuantity();
                }
            }
        }
        else return  new ResponseEntity("Неправильный формат операции", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(count.toString(), HttpStatus.OK);
    }

}
