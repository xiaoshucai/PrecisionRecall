package precisionRecall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class PR {
	
	private int N = 0;//number of total
	private int M = 0;//number of 1
	private double parentr = 0;
	private double parentp = 0;
	private int depth = 0;
	private int totalM = 0;
	public Node root;
	private int flag = 0;//represent the number of 1s, the end of traverse root 
	private int flagOfBuildTree = 0;
	public Map<Double, String> mapRP = new HashMap<Double, String>();

	//construct function
	public PR(int N, int M, double parentr, double parentp, int totalM, int depth)
	{
		this.N = N;
		this.M = M;
		this.parentr = parentr;
		this.parentp = parentp;
		this.totalM = totalM;
		this.depth = depth;
	}
	// recursive insert the data into node
	public void RPTree(Node node, int n, int key) throws IOException
	{
		if (node == null)
		{
			node = new Node(0, 0);
		}
		
		
			if (n == 0)
			{
				Node lchild = new Node(0, 0);
				node.leftChild = lchild;
				lchild.parent = node;
				RPTree(node.leftChild, n + 1, 0);
				Node rchild = new Node(0, 0);
				node.rightChild = rchild;
				rchild.parent = node;
				RPTree(node.rightChild, n + 1, 1);
				root = node;
				node.flagOfNode = 0;
			} 
			else
			{
				if (n == 1)
				{
					if (key == 0)
					{
						node.r = parentr;
						node.p = (double) (parentp * (depth - 1)) / depth ;
						node.flagOfNode = 0;
					} 
					else
					{
						node.r = parentr + (double) 1 / totalM;
						if (parentp != 0)
							node.p = (double) (parentp * (depth - 1) + 1) / depth;
						else
							node.p = (double) 1 / depth;
						node.flagOfNode = 1;
						flagOfBuildTree++;
					}
				} 
				else
				{
					if (key == 0)
					{
						node.p = (double) (node.p * (depth + n - 2)) / (depth + n - 1);
						node.flagOfNode = 0;
					} 
					else
					{
						node.r += (double) 1 / totalM;
						if (node.p != 0)
							node.p = (double) (node.p * (depth + n - 2) + 1) / (depth + n - 1);
						else
							node.p = (double) 1 / (depth + n - 1);
						node.flagOfNode = 1;
						flagOfBuildTree++;
					}
				}
				if(n != N && flagOfBuildTree != M)//if n is not N, then add left and right child
				{
					Node lchild = new Node(node.r, node.p);
					node.leftChild = lchild;//add the leftchild 
					lchild.parent = node;//add parent of leftchild
					RPTree(node.leftChild, n + 1, 0);
					Node rchild = new Node(node.r, node.p);
					node.rightChild = rchild;
					rchild.parent = node;
					RPTree(node.rightChild, n + 1, 1);
					
				}
				else //if n is N, then return
				{
					flagOfBuildTree = 0; 
					return;
				}
			}
		
	}

	// print out the tree先序
	public void traverse(Node node,int flag) throws IOException
	{
		if (node == null)
			return;
		else
		{
			flag += node.flagOfNode;//every level update flag
			if(flag == M)
			{//if flag is equal to the number of 1,which means the meaningful root,and find the leaf of root
				if (mapRP.containsKey(node.r))
				{
					String temp = mapRP.get(node.r) + "," + String.valueOf(node.p);
					mapRP.put(node.r, temp);
				}
				else 
					mapRP.put(node.r, String.valueOf(node.p));
				node.flagOfHasMap = 1;
				
				while (node.parent != root)
				{
					node = node.parent;
					if(node.flagOfHasMap == 0)//if this node has been save in map once, if had so not save again
					{
						if (mapRP.containsKey(node.r))
						{
							String temp = mapRP.get(node.r) + "," + String.valueOf(node.p);
							mapRP.put(node.r, temp);
						}
						else 
							mapRP.put(node.r, String.valueOf(node.p));
						node.flagOfHasMap = 1;
					}
					else
						break;
				}
				//System.out.println("1");
				return;	
			}
			traverse(node.leftChild,flag);
			traverse(node.rightChild,flag);
		}
	}

	// count the average p of every same r
	public ArrayList<double[]> countAvg()
	{
		ArrayList<double[]> result = new ArrayList<double[]>();
		
		int j = 0;
		
		Iterator iter = mapRP.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue(); 
		    String value = val.toString();
		    String[] tmp = value.split(",");
		    int i = 0;
		    double avg = 0;
		    while (i < tmp.length)
		    {
		    	 avg = avg + Double.parseDouble(tmp[i]);
		    	 i++;
		    }
		    avg = avg / tmp.length;
		    double[] temp = {0, 0};
		    temp[0] = Double.parseDouble(key.toString());
		    temp[1] = avg;
		    result.add(temp);
		    j++;  
		} 
		return result;
	}
	

}

