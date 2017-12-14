import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Deserializes a company file and sets the Company instance to match the file
 * @author jennifersaouaf
 *
 */
public class Deserializer {
	
	/**
	 * Deserializes the given company file and sets the Company instance to match the file
	 * @param filename
	 */
	public Deserializer(String filename) {
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Company.getInstance().setCompany((Company) in.readObject());
			in.close();
			fileIn.close();
		} catch (IOException i) {
			System.out.println("IOException");
			System.out.println(i.getMessage());
		} catch (ClassNotFoundException c) {
			System.out.println("Company class not found");
			System.out.println(c.getMessage());
		}
	}
	
}
