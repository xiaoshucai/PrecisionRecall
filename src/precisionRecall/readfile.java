package precisionRecall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class readfile 
{
	private static int totalM = 0;
	private static final String READFILENAME = "/Users/xiaoshucai/Desktop/test1.txt";
	private static final String READFILENAME2 = "/Users/xiaoshucai/Desktop/test2.txt";
	public static ArrayList<Integer> listDrugScore = new ArrayList<Integer>();//save 0/1/2 in a list
	public static ArrayList<int[]> ListMN = new ArrayList<int[]>();//save M(number of 1) and N(total number) in an arraylist
	public static Map<String, Integer> mapDrug = new HashMap<String, Integer>();
	
	//read file drug_score to write the list into a 0/1 array list and figure out the drug with the same score
	public static void readDrugScore() throws IOException
	{
		File Readfile = new File(READFILENAME);
		BufferedReader Bufferedreader = new BufferedReader(new FileReader(Readfile));
		
		int flagOfM = 0;
		int flagOfN = 1;//number of N in which the score is the same
		String testSameScore = "";
		String Readstring = null;
		String[] Array = new String[2];
		
		ReadDrug();
		
		//the first line in the file of drugscore
		Readstring = Bufferedreader.readLine();
		Array = Readstring.split("\\|");
		testSameScore = Array[1];
		if(IsContained(Array[0]))
		{
			flagOfM += 1;//number of M increase
			totalM += 1;
		}
		//the rest lines in the file
		while ((Readstring = Bufferedreader.readLine()) != null)
		{
			Array = Readstring.split("\\|");
			if (Array[1].equals(testSameScore))//if find same score, increase numbers
			{
				flagOfN += 1;
				if(IsContained(Array[0]))
				{
					flagOfM += 1;//number of M increase
					totalM += 1;
				}
			}
			else//if not, write the last part into arraylist and initiate next finding process
			{
				if (flagOfN == 1)//if last part contains no same score(only one element)
				{
					if (flagOfM == 0)//if last part was not approved
						listDrugScore.add(0);//0 represent
					else
						listDrugScore.add(1);//1 represent
				}
				else//if last part contains same score
				{
					listDrugScore.add(2);//2 represent all
					int[] temp = {0,0};
					temp[0] = flagOfM;//M(if M = 0, r and p equals 0)
					temp[1] = flagOfN;//N
					ListMN.add(temp);
				}	
				//initiation for the next round
				testSameScore = Array[1];
				flagOfN = 1;
				flagOfM = 0;
				if (IsContained(Array[0]))
				{
					flagOfM += 1;
					totalM += 1;
				}
			}
		}	
		if (flagOfN == 1)//if last part contains no same score(only one element)
		{
			if (flagOfM == 0)//if last part was not approved
				listDrugScore.add(0);//0 represent
			else
				listDrugScore.add(1);//1 represent
		}
		else//if last part contains same score
		{
			listDrugScore.add(2);//2 represent all
			int[] temp = {0,0};
			temp[0] = flagOfM;//M(if M = 0, r and p equals 0)
			temp[1] = flagOfN;//N
			ListMN.add(temp);
		}	
		Bufferedreader.close();
	}	
	
	//read file approved drug. save it into a map
	public static void ReadDrug() throws IOException
	{
		File Readfile2 = new File(READFILENAME2);
		BufferedReader Bufferedreader2 = new BufferedReader(new FileReader(Readfile2));
		
		String Readstring2 = null;
		while ((Readstring2 = Bufferedreader2.readLine()) != null)
		{
			if(!mapDrug.containsKey(Readstring2))
				mapDrug.put(Readstring2, 1);
		}
		Bufferedreader2.close();
	}
	
	//if the drug approved or not
	public static boolean IsContained(String drug) throws IOException
	{
		if (mapDrug.get(drug) != null)
			return true;
		else
			return false;
	}
	
	//get 0/1 list
	public ArrayList<Integer> getListDrugScore()
	{
		return listDrugScore;
	}
	
	//get M/N list
	public ArrayList<int[]> getListMN()
	{
		return ListMN;
	}
	
	//get totalM
	public int gettotalM()
	{
		return totalM;
	}
}
