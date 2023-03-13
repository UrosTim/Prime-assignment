import java.io.*;
import java.util.*;
import java.time.*;
class Task implements Serializable{
    private static final String FILE_NAME_TASKS = "data\\tasks.txt";
    public String title;
    private String description;
    public String assigneeEmail;
    private LocalDate dueDate;
    public boolean isCompleted;
    public LocalDate dateCreated;
    public LocalDate dateCompleted;
    public Task(String title, String description, String assigneeEmail, LocalDate dueDate, LocalDate dateCreated){
        this.title = title;
        this.description = description;
        this.assigneeEmail = assigneeEmail;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setAssignee(String assigneeEmail){
        this.assigneeEmail = assigneeEmail;
    }
    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
    public void setCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
    @Override
    public String toString(){
        return"Task{" +
                "title: "+ title + '\'' +
                ", description='" + description + '\'' +
                ", assignee='" + assigneeEmail + '\'' +
                ", dueDate='" + dueDate + '\'' + '}';
    }
    static void createTask(Scanner scanner, ArrayList<Task> tasks) {
        System.out.println("\nCreate new task:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Assignees Email: ");
        String assigneeEmail = scanner.nextLine();
        System.out.print("In how many days is the due date?: ");
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(scanner.nextInt());
        LocalDate dateCreated = LocalDate.now();
        Task newTask = new Task(title, description, assigneeEmail, dueDate, dateCreated);
        tasks.add(newTask);
        System.out.println("New task created successfully.");

    }
    static void readTask(Scanner scanner, ArrayList<Task> tasks) {
        System.out.println("\nRead task information:");
        System.out.print("Enter the title of the task: ");
        String title = scanner.nextLine();
        Task task = findTaskByTitle(title, tasks);
        if (task == null) {
            System.out.println("Task not found.");
        } else {
            System.out.println(task);
        }
    }
    static void updateTask(Scanner scanner, ArrayList<Task> tasks) {
        System.out.println("\nUpdate task information:");
        System.out.print("Enter the title of the task: ");
        String title = scanner.nextLine();
        Task task = findTaskByTitle(title, tasks);
        if (task == null) {
            System.out.println("Task not found.");
        } else {
            System.out.println("Current information: " + task);
            System.out.print("New title (leave blank to keep current value): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                task.setTitle(newTitle);
            }
            System.out.print("New description (leave blank to keep current value): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                task.setDescription(description);
            }
            System.out.print("New assignee (leave blank to keep current value): ");
            String assigneeEmail = scanner.nextLine();
            if (!assigneeEmail.isEmpty()) {
                task.setAssignee(assigneeEmail);
            }
            System.out.print("Do you want to enter new due date?(type yes if you do): ");
            if(scanner.nextLine().equals("yes")){
                LocalDate today = LocalDate.now();
                LocalDate dueDate = today.plusDays(scanner.nextInt());
                task.setDueDate(dueDate);
            }
            System.out.print("Type yes if the task is completed: ");
            if(scanner.nextLine().equals("yes")){
                boolean isCompleted = true;
                task.setCompleted(isCompleted);
                task.dateCompleted = LocalDate.now();
            }
        }
    }
    static void deleteTask(Scanner scanner, ArrayList<Task> tasks) {
        System.out.println("\nDelete task: ");
        System.out.print("Enter the title of the task to delete: ");
        String title = scanner.nextLine();
        Task task = findTaskByTitle(title, tasks);
        if (task == null) {
            System.out.println("Task not found.");
        } else {
            tasks.remove(task);
            System.out.println("Task deleted successfully.");
        }
    }
    public static Task findTaskByTitle(String title, ArrayList<Task> tasks) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }
    static ArrayList<Task> readTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(FILE_NAME_TASKS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e){
            System.out.println("Creating new file.");
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return tasks;
    }
    static void writeTaskToFile(ArrayList<Task> tasks) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME_TASKS);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void showAllTasks(ArrayList<Task> tasks) {
        for(Task task : tasks){
            System.out.println(task);
        }
    }
    static void showCompletedTasks(ArrayList<Task> tasks) {
        for(Task task : tasks){
            if(task.isCompleted){
                System.out.println(task);
            }
        }
    }
    static void showNotCompletedTasks(ArrayList<Task> tasks) {
        for(Task task : tasks){
            if(!task.isCompleted){
                System.out.println(task);
            }
        }
    }
}