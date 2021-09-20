package main;

import main.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

@RestController
public class DefaultController

{   @Autowired
    TaskRepository taskRepository;



    @GetMapping("/tasks/{id}")
    public Task getID(@PathVariable Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            System.out.println("Задача не найденна");
            return null;
        }
        return optionalTask.get();
}

    @GetMapping("/tasks")
    public ArrayList<Task> getView() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        taskIterable.forEach(t -> tasks.add(t));
        return tasks;
    }


    @PostMapping("/tasks")
    public String addTaskInToDoList(@RequestBody Task task){
               Task newTask = (Task) taskRepository.save(task);
               return "Задача добавлена: id: " + newTask.getId();
            }

   @PatchMapping("/tasks/{id}")
    public String   correctTaskInToDoList(@PathVariable int id, @RequestBody String name ){ ;
    Optional<Task> patchingTask = taskRepository.findById(id);
    Task newPatchingTask = patchingTask.get();
    newPatchingTask.setNameOfTask(name);
    taskRepository.save(newPatchingTask);
   return "Отредактированна задача с id: " + id;
        }

    @PutMapping("/tasks/{id}")
    public String updateTask(@PathVariable int id, @RequestBody Task task)
    {  Optional<Task> originalTask = taskRepository.findById(id);
       Task newOriginalTask = originalTask.get();
       newOriginalTask.setNameOfTask(task.getNameOfTask());
       newOriginalTask.setTimeOfCreateTask(task.getTimeOfCreateTask());
       taskRepository.save(newOriginalTask);
       return "Задача с id: " + id + " обновленна";
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Integer id) {
        Optional<Task> deletedTask = taskRepository.findById(id);
        taskRepository.delete(deletedTask.get());
        return "Удаленна задача с ID: " + id;
    }
    @DeleteMapping("/tasks")
    public String deleteAllTasks()
        {
            taskRepository.deleteAll();
            return "Список дел очищен";
        }

}
