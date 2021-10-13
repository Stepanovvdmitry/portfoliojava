package main;
import main.model.Socks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController

public class DefaultController
{
    @Autowired
    SocksRepositiry socksRepositiry;

    @PostMapping("/api/socks/income")
    public String addSocks(@RequestBody Socks socks)
    {   Integer socksQuantity = socks.getQuantity();
        if(socksQuantity >= 0) {

            Socks newSocks = socksRepositiry.save(socks);
            return  "Задача добавлена: id: " + newSocks.getId();
        }

    else return "Сумма должная быть положительной";

    }
    @PostMapping("/api/socks/{id}/outcome")
    public String putSocks( @PathVariable Integer id, @RequestBody String quantity)
    {
        Optional<Socks> patchingSocks = socksRepositiry.findById(id);
        Socks newPatchingSocks = patchingSocks.get();
        Integer socksQuantity = newPatchingSocks.getQuantity();
        if(socksQuantity  - Integer.parseInt(quantity)>= 0) {
            newPatchingSocks.setQuantity(socksQuantity  - Integer.parseInt(quantity));
            Socks newSocks = socksRepositiry.save(newPatchingSocks);
            return  "Задача обновлена: id: " + newSocks.getId();
        }

        else return "Сумма ухода должна быть меньше суммы прихода";
    }

    @GetMapping("api/socks")
    public String getID(@RequestParam String color, @RequestParam String operation, @RequestParam String cottonPart ) {
        Integer count = null;
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
        else return "Неправильный формат операции";
        return count.toString();
    }

}
