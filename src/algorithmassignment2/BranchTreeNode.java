/**
 * 
 * @author ***
 * 
 * @student_number ***
 * 
 * @content BranchTreeNode: store the node for the solution tree.
 * 
 * @date ***
 * 
 */

package algorithmassignment2;

import java.util.LinkedList;

public class BranchTreeNode {
	//Read-only variables
	private int number; //cityId
	private BranchTreeNode fatherNode;
	private int accumulatedCost; //the accumulated cost when getting to current node.
	private int accumulatedDistance; //the accumulated distance when getting to current node.
	private int innerBound; //the minimum value of total distance among all further choices when choosing this node.
	
	//Variables
	private boolean isDefectiveNode; //indicate if this node has exceeded the bound or the cost limitation, or its children's branches have been confirmed no solution in them.
	private boolean hasCreatedChildren; //indicate if the children branches of this node has been created.
	
	private LinkedList<BranchTreeNode> childList; //the sub nodes of current nodes.
	
	public static final BranchTreeNode nullBranchTreeNode = new BranchTreeNode(null, -1, 0, 0, 9999);
	
	public BranchTreeNode(BranchTreeNode fatherNode, int number, int accumulatedCost, int accumulatedDistance, int innerBound) {
		this.fatherNode = fatherNode;
		this.number = number;
		this.accumulatedCost = accumulatedCost;
		this.accumulatedDistance = accumulatedDistance;
		this.innerBound = innerBound;
		isDefectiveNode = (accumulatedCost > 1500) ? true : false;
		hasCreatedChildren = false;
		childList = new LinkedList<BranchTreeNode>();
	}
	
	//Public methods
	public BranchTreeNode nextPossibleNode(int[] cities, int currentBoundLimit) {
		//Get the sub node with lowest bound from the remaining children nodes.
		BranchTreeNode subNode;
		if (!get_HasCreatedChildren()) {
			addSubNodes(cities);
		}
		subNode = getSubNodeWithLowestBound(currentBoundLimit);	
		return subNode;
	}
	
	public boolean hasReachedDestination() {
		return 49 == get_Number();
	}
	
	//Variables' interface
	public int get_Number() {
		return number;
	}
	public BranchTreeNode get_FatherNode() {
		return fatherNode;
	}
	public int get_Bound() {
		return innerBound;
	}
	public int get_AccumulatedCost() {
		return accumulatedCost;
	}
	public int get_AccumulatedDistance() {
		return accumulatedDistance;
	}
	public boolean get_IsDefectiveNode() {
		return isDefectiveNode;
	}
	public void set_IsDefectiveNode(boolean isDefectiveNode) {
		this.isDefectiveNode = isDefectiveNode;
	}
	public boolean get_HasCreatedChildren() {
		return hasCreatedChildren;
	}
	public void set_HasCreatedChildren(boolean hasCreatedChildren) {
		this.hasCreatedChildren = hasCreatedChildren;
	}
	
	//Private methods
	private void addSubNodes(int[] cities) {
		int cityId, cost, distance, bound;
		for (int i = 1; i <= cities[0]; i++) {
			cityId = cities[i];
			cost = get_AccumulatedCost() + Data.cost[get_Number()][cityId];
			distance = get_AccumulatedDistance() + Data.distance[get_Number()][cityId];
			bound = get_AccumulatedDistance() + Data.minimumDistance[cityId][49];
			childList.add(new BranchTreeNode(this, cityId, cost, distance, bound));
		}
		set_HasCreatedChildren(true);
	}

	private BranchTreeNode getSubNodeWithLowestBound(int currentBoundLimit) {
		int minimal = Integer.MAX_VALUE;
		BranchTreeNode nodeWithMinimalBound = nullBranchTreeNode;
		if (!childList.isEmpty()) {
			for (BranchTreeNode node : childList) {
				if (!node.isDefectiveNode) {
					if (minimal > node.get_Bound()) {
						nodeWithMinimalBound = node;
					}
					if (49 == node.get_Number()) {
						return node;
					}
				}
			}
		}
		return nodeWithMinimalBound;
	}
}
