import java.io.*;
import java.util.*;
class Employee implements Serializable {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private double monthlySalary;
    public int counterM;
    public int counterY;
    public Employee(String fullName, String email, String phoneNumber, String dateOfBirth, double monthlySalary, int counterM, int counterY){
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.monthlySalary = monthlySalary;
        this.counterM = counterM;
        this.counterY = counterY;
    }
    private static final String FILE_NAME_EMPLOYEES = "data\\employees.txt";
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    public void setMonthlySalary(double monthlySalary){
        this.monthlySalary = monthlySalary;
    }
    @Override
    public String toString(){
        return "Employee{" +
                " fullName:'"+ fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", monthlySalary=" + monthlySalary + '}';
    }
    static void createEmployee(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("\nCreate new employee:");
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Date of birth(yyyy-dd-mm): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Monthly salary: ");
        double monthlySalary = scanner.nextDouble();
        int counterM = 0;
        int counterY = 0;
        Employee newEmployee = new Employee(fullName, email, phoneNumber, dateOfBirth, monthlySalary, counterM, counterY);
        employees.add(newEmployee);
        System.out.println("New employee created successfully.");
    }
    static void readEmployee(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("\nRead employee information:");
        System.out.print("Enter the email of the employee: ");
        String email = scanner.nextLine();
        Employee employee = findEmployeeByEmail(email, employees);
        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            System.out.println(employee);
        }
    }
    static void updateEmployee(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("\nUpdate employee information:");
        System.out.print("Enter the email of the employee: ");
        String email = scanner.nextLine();
        Employee employee = findEmployeeByEmail(email, employees);
        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            System.out.println("Current information: " + employee);
            System.out.print("New full name (leave blank to keep current value): ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                employee.setFullName(fullName);
            }
            System.out.print("New email (leave blank to keep current value): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                employee.setEmail(newEmail);
            }
            System.out.print("New phone number (leave blank to keep current value): ");
            String phoneNumber = scanner.nextLine();
            if (!phoneNumber.isEmpty()) {
                employee.setPhoneNumber(phoneNumber);
            }
            System.out.print("New date of birth (leave blank to keep current value): ");
            String dateOfBirth = scanner.nextLine();
            if (!dateOfBirth.isEmpty()) {
                employee.setDateOfBirth(dateOfBirth);
            }
            System.out.print("New monthly salary (leave blank to keep current value): ");
            String monthlySalaryString = scanner.nextLine();
            if (!monthlySalaryString.isEmpty()) {
                double monthlySalary = Double.parseDouble(monthlySalaryString);
                employee.setMonthlySalary(monthlySalary);
            }
            System.out.println("Employee information updated successfully.");
        }
    }
    static void deleteEmployee(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("\nDelete employee:");
        System.out.print("Enter the email of the employee to delete: ");
        String email = scanner.nextLine();
        Employee employee = findEmployeeByEmail(email, employees);
        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            employees.remove(employee);
            System.out.println("Employee deleted successfully.");
        }
    }
    static Employee findEmployeeByEmail(String email, ArrayList<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email)) {
                return employee;
            }
        }
        return null;
    }
    static ArrayList<Employee> readEmployeesFromFile() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME_EMPLOYEES);
            ObjectInputStream ois = new ObjectInputStream(fis);
            employees = (ArrayList<Employee>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Creating new file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }
    static void writeEmployeesToFile(ArrayList<Employee> employees) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME_EMPLOYEES);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employees);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void showAllEmployees(ArrayList<Employee> employees) {
        for(Employee employee : employees){
            System.out.println(employee);
        }
    }
}
