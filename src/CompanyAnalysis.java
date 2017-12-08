import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * This class performs analysis on all the projects in the entire company.
 * @author cgui1
 *
 */
public class CompanyAnalysis {
	private Company company;
	private LinkedHashMap<Integer, Project> projectIdMap;

	/**
	 * The constructor
	 * @param c the company
	 */
	public CompanyAnalysis (Company c){
		company = c;
		projectIdMap = company.getProjectIdMap();
	}

	/**
	 * This method returns the number of active projects.
	 * @return number of active projects
	 */
	public int activeProjects(){
		int active = 0;
		for (Integer id : projectIdMap.keySet()){
			if (projectIdMap.get(id).isActive()){
				active++;
			}
		}
		return active;
	}

	/**
	 * This method returns the amount of projects that have been late in the past by comparing the actual end date and the original target end date.
	 * @return
	 */
	public int longerThanExpProjects(){
		int longerThanExpProjects = 0;
		for (Integer id : projectIdMap.keySet()){
			if (projectIdMap.get(id).getActualEndDate().isAfter(projectIdMap.get(id).getTargetEndDate())){
				longerThanExpProjects++;
			}
		}
		return longerThanExpProjects;
	}

	/**
	 * This method returns IDs of the projects that are late by comparing the deadline to today's date.
	 * @return
	 */
	public ArrayList<Integer> lateProjects(){
		ArrayList<Integer> lateProjects = new ArrayList<Integer>();
		for (Integer id : projectIdMap.keySet()){
			if (projectIdMap.get(id).getDeadline().isBefore(LocalDate.now())){
				lateProjects.add(id);
			}
		}
		return lateProjects;
	}



}
