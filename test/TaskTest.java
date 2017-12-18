import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TaskTest {
	Task t;
	
	@Before
	public void setUpProject() {
		t = new Task(1, "Sample Task", 20);
	}
	
	@Test
	public void testCompleteTask() {
		t.completeTask(20);
		
		assertEquals("Task should be complete now", true, t.isComplete());
	}
}
