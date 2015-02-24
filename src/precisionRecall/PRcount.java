package precisionRecall;

import java.io.IOException;
import java.util.ArrayList;

public class PRcount {
	
	public static void main(String arcs[]) throws IOException
	{
		readfile readFile = new readfile();
		readFile.readDrugScore();
		int totalM = readFile.gettotalM();//get totalM
		System.out.println(totalM);
		
		//get ListDrugScore. switch it to array
		ArrayList<Integer> listDrugScore = new ArrayList<Integer>();//save 0/1/2 in a list
		listDrugScore = readFile.getListDrugScore();
		int all = 0;
		int ll = 0;
		//for(int l = 0; l < listDrugScore.size(); l++)
		//{
			//if (listDrugScore.get(l) == 2)
			//{
				//ll++;
			//}
			//all++;
		//}
		//System.out.println(ll);
		//System.out.println(listDrugScore.get(170));
		
		//get ListMN. switch it to array
		ArrayList<int[]> ListMN = new ArrayList<int[]>();//save M and N in an arraylist
		ListMN = readFile.getListMN();
		//System.out.println(ListMN.get(19)[0]+ " "+ListMN.get(19)[1]);
		
		//count r and p for the whole file
		double r = 0;
		double p = 0;
		ArrayList<double[]> arrayRP = new ArrayList<double[]>();
		int i = 0;//token for while loop
		int j = 0;//token for number in arrayMN. 第几个有相同分数的group
		//int k = 0; 
		while (i < listDrugScore.size())
		{
			if (listDrugScore.get(i) == 0)
			{
				p = (double) p * i / (i + 1);
				double[] temp = {0, 0};
				temp[0] = r;
				temp[1] = p;
				arrayRP.add(temp);
			}
			if (listDrugScore.get(i) == 1)
			{
				r += (double) 1 / totalM;
				if (p == 0)
					p = (double) 1 / (i + 1);
				else
					p = (double) (p * i + 1) / (i + 1); 
				double[] temp = {0, 0};
				temp[0] = r;
				temp[1] = p;
				arrayRP.add(temp);
				//k++;
			}
			if (listDrugScore.get(i) == 2)
			{
				//k = k + ListMN.get(j)[0];
				if (ListMN.get(j)[0] == 0)
				{
					p = (double) p * i / (i + ListMN.get(j)[1]);
					double[] temp = {0, 0};
					temp[0] = r;
					temp[1] = p;
					arrayRP.add(temp);
					j++;
				}
				else
				{
					
					PR rp = new PR(ListMN.get(j)[1], ListMN.get(j)[0], r, p, totalM, i + 1); //new an instance of PR class
					Node root = rp.root; //use the variable root of PR class
					Node roott = null;
					rp.RPTree(roott, 0, 0); //invoke the method RPTree of PR class 
					root = rp.root; //reset the root in PR to root重新赋值
					rp.traverse(root,0);
					ArrayList<double[]> result = new ArrayList<double[]>();
					result = rp.countAvg();//r,p array  
					int token = 0;
					int max = 0;
					while (token < result.size())
					{
						double[] temp = {0, 0};
						temp[0] = result.get(token)[0];
						temp[1] = result.get(token)[1];
						arrayRP.add(temp);
						if (result.get(token)[0] > result.get(max)[0])
						{
							max = token;
						}
						token++;
					}
					r = result.get(max)[0];
					p = result.get(max)[1];
					j++;
				}	
			}	
			System.out.println(j);
			System.out.println(i);
			i++;
		}
		//System.out.println(k);
		for (i = 0; i < arrayRP.size(); i++)
		{
			System.out.println("r:" + arrayRP.get(i)[0] + " p:" + arrayRP.get(i)[1]);
		}
	}
	
}
