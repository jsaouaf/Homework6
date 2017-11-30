/**
 * This class is for testing out classes
 * @author zyud
 *
 */
public class MainTester {

    public static void main(String[] args) {
        Company verizon = new Company("Verizon Wireless");
        verizon.newEmployee("Ajit Pai");
        verizon.newEmployee("Joseph Goebbels");
        verizon.newEmployee("Beelzebub");
        verizon.newEmployee("Succubus");
        
        verizon.printAllEmployees();
        
        System.out.println("");
        
        verizon.newProject("DJT Propaganda System");
        verizon.newProject("Fresh Poop Delivery System");
        
        verizon.printAllProjects();
        
        verizon.newTask(30, "Destroy net neutrality");
        
        // ProjectId #2 is "Fresh poop delivery system"
        verizon.newTask(10, "Harvest poop", 2);
        
        // Adding Ajit Pai to project "Fresh Poop Delivery System"
        verizon.addEmployeeToProject(1, 2);
        
        // Adding Ajit Pai to task "Harvest Poop"
        verizon.addEmployeeToTask(1, 2);
    }

}
