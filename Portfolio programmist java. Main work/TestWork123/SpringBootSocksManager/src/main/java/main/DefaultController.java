package main;
import jdk.net.SocketFlow;
import main.model.Socks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
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
    @PostMapping("/api/socks/{id}/outcome")
    public ResponseEntity putSocks( @PathVariable Integer id, @RequestBody String quantity)
    {
        Optional<Socks> patchingSocks = socksRepositiry.findById(id);
        Socks newPatchingSocks = patchingSocks.get();
        Integer socksQuantity = newPatchingSocks.getQuantity();
        if(socksQuantity  - Integer.parseInt(quantity)>= 0) {
            newPatchingSocks.setQuantity(socksQuantity  - Integer.parseInt(quantity));
            Socks newSocks = socksRepositiry.save(newPatchingSocks);
            return new ResponseEntity("Задача обновлена: id: " + newSocks.getId(), HttpStatus.OK);
        }

        else return new ResponseEntity("Сумма ухода должна быть меньше суммы прихода", HttpStatus.BAD_REQUEST);
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
