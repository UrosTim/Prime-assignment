import java.io.*;
import java.time.LocalDate;
import java.util.*;
class Report implements Serializable{
    private static final String FILE_NAME_REPORTS = "data\\reports.txt";
    private final String reportName;
    private final LocalDate dateCreated;
    private final LocalDate dateCompleted;
    private final String completedBy;
    public Report(String reportName, LocalDate dateCreated, LocalDate dateCompleted, String completedBy){
        this.reportName = reportName;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
        this.completedBy = completedBy;
    }
    public String getReportName(){
        return reportName;
    }
    @Override
    public String toString(){
        return  "Report: " + reportName + "\n" +
                "Task created: " + dateCreated + "\n" +
                "Task completed: " + dateCompleted + "\n"+
                "Completed by: " + completedBy + "\n";
    }
    static void createReport(Scanner scanner, ArrayList<Task> tasks,ArrayList<Report> reports){
        System.out.println("\nCreate new report: ");
        System.out.print("Enter the title of the task that you want to make report on: ");
        String reportName = scanner.nextLine();
        Task task = Task.findTaskByTitle(reportName, tasks);
        if (task == null) {
            System.out.println("Task not found.");
        } else {
            if (!task.isCompleted){
                System.out.println("Can't create report, the task is not completed.");
            }else {
                Report newReport = new Report(reportName, task.dateCreated, task.dateCompleted, task.assigneeEmail);
                reports.add(newReport);
                System.out.println("New report created successfully.");
            }
        }
    }
    static void readReport(Scanner scanner, ArrayList<Report> reports){
        System.out.println("\nRead report information:");
        System.out.print("Enter the title of the task that you want report on: ");
        String reportName = scanner.nextLine();
        Report report = findReportByName(reportName, reports);
        if (report == null){
            System.out.println("Report not found.");
        }else {
            System.out.println(report);
        }
    }
    public static void deleteReport(Scanner scanner, ArrayList<Report> reports) {
        System.out.println("\nDelete report:");
        System.out.print("Enter the name of the report you want to delete: ");
        String reportName = scanner.nextLine();
        Report report = findReportByName(reportName, reports);
        if (report == null){
            System.out.println("Report not found.");
        }else {
            reports.remove(report);
            System.out.println("Report deleted successfully.");
        }
    }
    public static Report findReportByName(String reportName, ArrayList<Report> reports){
        for (Report report : reports){
            if (report.getReportName().equals(reportName)){
                return report;
            }
        }
        return null;
    }
    public static ArrayList<Report> readReportFromFile() {
        ArrayList<Report> reports = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME_REPORTS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reports = (ArrayList<Report>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e){
            System.out.println("Creating new file.");
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return reports;
    }
    public static void writeReportToFile(ArrayList<Report> reports) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME_REPORTS);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(reports);
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void showAllReports(ArrayList<Report> reports) {
        for (Report report : reports){
            System.out.println(report);
        }
    }
}
