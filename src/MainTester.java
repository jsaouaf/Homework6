import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        
        // Testing Serialization
		try {
	          FileOutputStream fileOut =
	          new FileOutputStream("company.ser");
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(verizon);
	          out.close();
	          fileOut.close();
	          System.out.println("Serialized data is saved in \"company.ser\"");
	       } catch (IOException i) {
	          System.out.println("IOException");
	          System.out.println(i.getMessage());
	       }
        
        // Testing Deserialization
	  	 Company verizonRestored = null;
		
	  	 	  try {
		         FileInputStream fileIn = new FileInputStream("company.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         verizonRestored = (Company) in.readObject();
		         in.close();
		         fileIn.close();
		      } catch (IOException i) {
		    	 System.out.println("IOException");
		    	 System.out.println(i.getMessage());
		      } catch (ClassNotFoundException c) {
		         System.out.println("Company class not found");
		         System.out.println(c.getMessage());
		      }
	      
	      verizonRestored.printAllProjects();
	      verizonRestored.printAllEmployees();
        
    }

}
