/**
 * 
 * @author ***
 * 
 * @student_number ***
 * 
 * @content BranchTree: solution tree which contains all possible solutions.
 * 
 * @date ***
 * 
 */

package algorithmassignment2;

public class BranchTree {
	private BranchTreeNode root;
	
	public BranchTree() {
		root = new BranchTreeNode(BranchTreeNode.nullBranchTreeNode, 0, 0, 0, Data.minimumDistance[0][49]);
	}
	
	public BranchTreeNode get_Root() {
		return root;
	}
	
	//Return null node if no valid solutions in its branch, otherwise this function returns the most promising sub node.
	public BranchTreeNode visitBranchTreeNode(BranchTreeNode node, int boundLimit, int[] cities) {
		//Mark this node as defective one if this node has exceeded the bound or the cost limitation.
		if (node.get_Bound() > boundLimit) {
			node.set_IsDefectiveNode(true);
			return BranchTreeNode.nullBranchTreeNode;
		}
		//Return the final node if a valid solution is found, otherwise the node with lowest bound will be returned.
		return node.nextPossibleNode(cities, boundLimit);
	}
}
