import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class ProjectTest {
	Project p;
	
	@Before
	public void setUpProject() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate deadline = LocalDate.parse("2018-02-12", dtf);
		
		p = new Project(1, "Sample Project", deadline);
	}
	
	@Test
	public void testCompleteProject() {
		p.completeProject();
		
		assertEquals("Project should be inactive now", false, p.isActive());
	}
}
