import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTester {
	
	private Employee e;
	private Project a;
	private Project b;
	private Project c;
	
	@Before
	public void setUp() {
		e = new Employee(1, "Jane");
		// set up some projects
		LocalDate deadline = LocalDate.of(2017, 12, 25);
		a = new Project(33, "Proj A", deadline);
		b = new Project(6, "Proj B", deadline);
		c = new Project(5, "Proj C", deadline);
		
		// add projects to employee
		e.addProject(a.getId());
		e.addProject(b.getId());
		e.addProject(c.getId());
	}
	
	@Test
	public void testGetProjects() {
	
		
		LinkedHashSet<Integer> projects = new LinkedHashSet<>();
		projects.add(a.getId());
		projects.add(b.getId());
		projects.add(c.getId());
		
		assertEquals("Should return LinkedHashSet of the project IDs", projects, e.getProjects());		
	}
	
	@Test
	public void testRemoveProject() {
		e.removeProject(6);
		LinkedHashSet<Integer> projects = new LinkedHashSet<>();
		projects.add(a.getId());
		projects.add(c.getId());
		
		assertEquals("Project B should be removed", projects, e.getProjects());	
	}

}
