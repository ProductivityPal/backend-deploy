package pl.edu.agh.productivitypal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.PomodoroRequest;
import pl.edu.agh.productivitypal.model.Task;
import pl.edu.agh.productivitypal.request.TaskRequest;
import pl.edu.agh.productivitypal.service.TaskService;

import java.util.List;

import static pl.edu.agh.productivitypal.config.SecurityConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET, allowedHeaders = "Authorization")
    @GetMapping
    public ResponseEntity<List<Task>> getTasksOfCurrentUser(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt,
                                            @RequestParam(required = false, defaultValue = "asc") String order,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy,
                                            @RequestParam(required = false, defaultValue = "0") int offset,
                                            @RequestParam(required = false, defaultValue = "50") int pageSize) {
        return ResponseEntity.ok(taskService.getAllTasksOfCurrentUser(jwt, order, sortBy, offset, pageSize));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/algosort")
    public ResponseEntity<List<Task>> getTasksSortedByAlgosort(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt) {
        return ResponseEntity.ok(taskService.getTasksSortedByAlgosort(jwt));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Integer> addTask(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(jwt, task));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/subtask")
    public ResponseEntity<Integer> addSubtask(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.addSubtask(jwt, task));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task " + id + " was deleted");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}/subtask")
    public ResponseEntity<String> deleteTaskAndAllSubtask(@PathVariable Integer id) {
        taskService.deleteTaskAndAllSubtask(id);
        return ResponseEntity.ok("Task " + id + " and all its subtasks were deleted");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{taskId}/subtask/{subtaskId}")
    public ResponseEntity<String> deleteSubTask(@PathVariable Integer taskId, @PathVariable Integer subtaskId){
        taskService.deleteSubtask(taskId, subtaskId);
        return ResponseEntity.ok("Subtask " + subtaskId + " of task " + taskId + " was deleted");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/subtask")
    public ResponseEntity<List<Task>> getSubtasks(@PathVariable Integer id, @RequestHeader(AUTHORIZATION_HEADER) Jwt jwt) {
        return ResponseEntity.ok(taskService.getSubtasks(jwt, id));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{taskId}/subtask/{subtaskId}")
    public ResponseEntity<Task> getSubtask(@PathVariable Integer taskId, @PathVariable Integer subtaskId){
        return ResponseEntity.ok(taskService.getSubtask(taskId, subtaskId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{taskId}/subtask/{subtaskId}")
    public ResponseEntity<Task> updateSubtask(@PathVariable Integer taskId, @PathVariable Integer subtaskId, @RequestBody Task task){
        return ResponseEntity.ok(taskService.updateSubtask(taskId, subtaskId, task));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/criteria")
    public ResponseEntity<List<Task>> getTasksByCriteria(@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.getTasksByCriteria(taskRequest));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/pomodoro")
    public ResponseEntity<String> addCompletionTimePomodoro(@RequestBody PomodoroRequest pomodoroRequest){
        taskService.addCompletionTimePomodoro(pomodoroRequest.getTaskId(), pomodoroRequest.getCompletionTime());
        return ResponseEntity.ok("Completion time " + pomodoroRequest.getCompletionTime() + " was added to task " + pomodoroRequest.getTaskId());
    }

}
