import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
class Main {
    public static void main(String[] args) {

        ArrayList<Employee> employees = Employee.readEmployeesFromFile();
        ArrayList<Task> tasks = Task.readTasksFromFile();
        ArrayList<Report> reports = Report.readReportFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("----------Menu----------");
            System.out.println("1. Menage Employees");
            System.out.println("2. Menage Tasks");
            System.out.println("3. Show top 5 monthly");
            System.out.println("4. Manage Reports");
            System.out.println("5. Show employee of the year");
            System.out.println("0. Exit");
            System.out.print("Enter the number of your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("----------------------------------");
            switch (choice){
                case 1:
                    int choiceOne;
                    do{
                        System.out.println("----------Menage Employees----------");
                        System.out.println("1.Create Employee");
                        System.out.println("2.Read Employee");
                        System.out.println("3.Update Employee");
                        System.out.println("4.Delete Employee");
                        System.out.println("5.Show all Employees");
                        System.out.println("0.Go back");
                        System.out.print("Enter the number of your choice: ");
                        choiceOne = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("----------------------------------");
                        switch (choiceOne){
                            case 1:
                                Employee.createEmployee(scanner, employees);
                                break;
                            case 2:
                                Employee.readEmployee(scanner, employees);
                                break;
                            case 3:
                                Employee.updateEmployee(scanner, employees);
                                break;
                            case 4:
                                Employee.deleteEmployee(scanner, employees);
                                break;
                            case 5:
                                Employee.showAllEmployees(employees);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                        Employee.writeEmployeesToFile(employees);
                    }
                    while(choiceOne != 0);
                    break;
                case 2:
                    int choiceTwo;
                    do{
                        System.out.println("----------Menage Tasks----------");
                        System.out.println("1.Create Task");
                        System.out.println("2.Read Task");
                        System.out.println("3.Update Task");
                        System.out.println("4.Delete Task");
                        System.out.println("5.Show all Tasks");
                        System.out.println("6.Show completed Tasks");
                        System.out.println("7.Show not completed Tasks");
                        System.out.println("0.Go back");
                        System.out.print("Enter the number of your choice: ");
                        choiceTwo = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("----------------------------------");
                        switch (choiceTwo){
                            case 1:
                                Task.createTask(scanner, tasks);
                                break;
                            case 2:
                                Task.readTask(scanner, tasks);
                                break;
                            case 3:
                                Task.updateTask(scanner, tasks);
                                break;
                            case 4:
                                Task.deleteTask(scanner, tasks);
                                break;
                            case 5:
                                Task.showAllTasks(tasks);
                                break;
                            case 6:
                                Task.showCompletedTasks(tasks);
                                break;
                            case 7:
                                Task.showNotCompletedTasks(tasks);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                        Task.writeTaskToFile(tasks);
                    }
                    while(choiceTwo != 0);
                    break;
                case 3:
                    showTopFiveMonthly(tasks);
                    break;
                case 4:
                    int choiceFour;
                    do {
                        System.out.println("----------Menage Reports----------");
                        System.out.println("1.Create Report");
                        System.out.println("2.Read Report");
                        System.out.println("3.Show all Reports");
                        System.out.println("4.Delete Report");
                        System.out.println("0.Go back");
                        System.out.print("Enter the number of your choice: ");
                        choiceFour = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("----------------------------------");
                        switch (choiceFour){
                            case 1:
                                Report.createReport(scanner, tasks, reports);
                                break;
                            case 2:
                                Report.readReport(scanner, reports);
                                break;
                            case 3:
                                Report.showAllReports(reports);
                                break;
                            case 4:
                                Report.deleteReport(scanner, reports);
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                        Report.writeReportToFile(reports);
                    }
                    while (choiceFour != 0);
                    break;
                case 5:
                    showEmployeeOfTheYear(tasks);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid number. Try again.");
            }
        }
        while(choice != 0);
    }
    private static void showEmployeeOfTheYear(ArrayList<Task> tasks) {
        ArrayList<Employee> employees = Employee.readEmployeesFromFile();
        for (Task task : tasks){
            Employee employee = Employee.findEmployeeByEmail(task.assigneeEmail, employees);
            if (task.isCompleted && task.dateCompleted != null){
                long timeBetween = ChronoUnit.MONTHS.between(task.dateCompleted, LocalDate.now());
                if(timeBetween < 12){
                    if (employee != null) {
                        employee.counterY++;
                    }
                }
            }
        }
        Comparator<Employee> comparator = (employee, employee1) -> Integer.compare(employee1.counterY, employee.counterY);
        employees.sort(comparator);
        for (int i = 0; i < 1; i++){
            System.out.println("Employee of the year is: " + employees.get(i) + " with " );
        }
    }
    private static void showTopFiveMonthly(ArrayList<Task> tasks) {
        ArrayList<Employee> employees = Employee.readEmployeesFromFile();
        for (Task task : tasks){
            if (task.isCompleted && task.dateCompleted != null){
                Employee employee = Employee.findEmployeeByEmail(task.assigneeEmail, employees);
                long timeBetween = ChronoUnit.MONTHS.between(task.dateCompleted, LocalDate.now());
                if(task.isCompleted && timeBetween < 1){
                    if (employee != null) {
                        employee.counterM++;
                    }
                }
            }

        }
        Comparator<Employee> comparator = (employee, employee1) -> Integer.compare(employee1.counterM, employee.counterM);
        employees.sort(comparator);
        int placement = 0;
        for (int i = 0; i < Math.min(5, employees.size()); i++){
            placement++;
            System.out.println(placement + ". " + employees.get(i));
        }
    }
}