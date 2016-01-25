/**
 * 
 * @author ***
 * 
 * @student_number ***
 * 
 * @content Path: store the ordered cities you've gone through.
 * 
 * @date ***
 * 
 */

package algorithmassignment2;

import java.util.LinkedList;

public class Path {
	private int[] solutionPath;
	private int distance;
	private int cost;
	private int step;
	private LinkedList<Integer> orderedCityInThisPath;
	
	public Path() {
		solutionPath = new int[50];
		solutionPath[0] = 0;
		distance = 0;
		cost = 0;
		step = 0; //the last index of city in path.
		orderedCityInThisPath = new LinkedList<Integer>();
		orderedCityInThisPath.add(step, new Integer(0));
	}
	
	//Public methods
	public void moveTo(int city) {
		for (int i = step; i > -1; i--) {
			if (orderedCityInThisPath.get(i).intValue() < city) {
				orderedCityInThisPath.add(i + 1, new Integer(city));
				break;
			}
		}
		distance += Data.distance[solutionPath[step]][city];
		cost += Data.cost[solutionPath[step]][city];
		step++;
		solutionPath[step] = city;
	}
	
	public int goBack() {
		int city = solutionPath[step];
		if (step > 0) {
			step--;
			cost -= Data.cost[solutionPath[step]][city];
			distance -= Data.distance[solutionPath[step]][city];
			for (int i = step; i > -1; i--) {
				if (orderedCityInThisPath.get(i).intValue() == city) {
					orderedCityInThisPath.remove(i);
					break;
				}
			}
		}
		return city;
	}
	
	//Return the array of cities that are available in next step.
	public int[] availableCities(int city) {
		int count = Data.availableCities[city][0];
		int[] cities = new int[count + 1];
		int currentPositionInOrderedList = 0;
		int currentPositionInFinalArray = 0;
		int cityPassed = orderedCityInThisPath.get(currentPositionInOrderedList).intValue();
		int largestPassed = orderedCityInThisPath.get(step).intValue();
		int cityAvailable;
		for (int i = 1; i <= count; i++) {
			cityAvailable = Data.availableCities[city][i];
			while (largestPassed >= cityAvailable && cityPassed < cityAvailable) {
				cityPassed = orderedCityInThisPath.get(++currentPositionInOrderedList).intValue();
			}
			if (cityPassed != cityAvailable) {
				cities[++currentPositionInFinalArray] = cityAvailable;
			}
		}
		cities[0] = currentPositionInFinalArray;
		return cities;
	}
	
	public void printPath() {
		System.out.printf("Distance: %4d   Cost: %4d\n", distance, cost);
		System.out.print("Path:  1");
		for (int i = 1; i <= step; i++) {
			System.out.printf("->%2d", solutionPath[i] + 1);
		}
		System.out.println("");
		System.out.println("============");
	}
	
	public Path copyTo() {
		Path path = new Path();
		for (int i = 1; i < get_CityCount(); i++) {
			path.moveTo(solutionPath[i]);
		}
		return path;
	}
	
	public boolean canMeetRequirements(int boundLimit) {
		if (get_Distance() <= boundLimit && get_Cost() <= Data.costUpperLimit) {
			return true;
		}
		return false;
	}
	
	public boolean hasSmallerBoundThan(int boundLimit) {
		return get_Distance() < boundLimit;
	}
	
	//Variables' interface
	public int get_Distance() {
		return distance;
	}
	public int get_Cost() {
		return cost;
	}
	public int get_CityCount() {
		return step + 1;
	}
}
