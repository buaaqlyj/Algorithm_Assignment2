/**
 * 
 * @author ***
 * 
 * @student_number ***
 * 
 * @content Assignment2: use Algorithm "Branch And Bound" to calculate the best solution for NP problem.
 * 
 * @date ***
 * 
 */

package algorithmassignment2;

import java.util.ArrayList;

public class Assignment2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//printArray(Data.availableCities, 50);
		printSolutions(branchAndBound());
	}
	
	//Return a list of solution paths.
	private static ArrayList<Path> branchAndBound() {
		ArrayList<Path> solutionPaths = new ArrayList<Path>();
		Path path = new Path();
		
		BranchTree tree = new BranchTree();
		BranchTreeNode currentNode = tree.get_Root();
		BranchTreeNode nextNode;
		
		int boundLimit = Integer.MAX_VALUE;
		
		System.out.println("Solving the problem with Algo. Branch and Bound...");
		System.out.println("============");
		
		while (!tree.get_Root().get_IsDefectiveNode()) {
			//visit current node and return the destination node or the sub node with lowest bound.
			nextNode = tree.visitBranchTreeNode(currentNode, boundLimit, path.availableCities(currentNode.get_Number()));
			
			if (nextNode.equals(BranchTreeNode.nullBranchTreeNode)) {
				//No solution in this branch, then mark it as defective node and go back to its father node.
				currentNode.set_IsDefectiveNode(true);
				currentNode = currentNode.get_FatherNode();
				path.goBack();
			} else {
				path.moveTo(nextNode.get_Number());
				if (nextNode.hasReachedDestination()) {
					//Solution found
					if (path.canMeetRequirements(boundLimit)) {
						if (path.hasSmallerBoundThan(boundLimit)) {
							System.out.println("Better path found. Now we can tighten the bound limit: " + boundLimit + " --> " + path.get_Distance());
							solutionPaths.clear();
							boundLimit = path.get_Distance();
						} else {
							System.out.println("Path with the same distance found.");
						}
						solutionPaths.add(path.copyTo());
						path.printPath();
					}
					nextNode.set_IsDefectiveNode(true);
					path.goBack();
				} else {
					//Not a solution, so continue.
					currentNode = nextNode;
				}
			}
		}
		System.out.println("Finish!");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		return solutionPaths;
	}
	
	//Show solutions.
	private static void printSolutions(ArrayList<Path> solutions) {
		System.out.println("Result: " + solutions.size() + (solutions.size() > 1 ? " paths have" : " path has") + " been found. The details are showed below:");
		System.out.println("============");
		int count = 1;
		for (Path x : solutions) {
			System.out.println("Solution #" + count++ + ":");
			x.printPath();
		}
	}
}