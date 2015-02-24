package precisionRecall;

public class Node {
	
	Node leftChild;
	Node rightChild;
	Node parent; //parent of current node
	double r;
	double p;
	int flagOfNode = 0;//0/1 of the route
	int flagOfHasMap = 0;//check if the node is read when traverse

	Node(double newDataR, double newDataP)
	{
		leftChild = null;
		rightChild = null;
		parent = null;
		r = newDataR;
		p = newDataP;
	}
}
