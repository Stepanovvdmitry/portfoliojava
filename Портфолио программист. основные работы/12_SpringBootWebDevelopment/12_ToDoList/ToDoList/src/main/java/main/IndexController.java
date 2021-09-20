package main;

import main.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.IntStream;

@Controller
public class IndexController
{
    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        taskIterable.forEach(t -> tasks.add(t));
        model.addAttribute("tasks",tasks);
        System.out.println("Составляйте свой список задач");
        return "index";
    }

}
