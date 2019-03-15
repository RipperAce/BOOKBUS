import java.util.*;
class City
{
	int id,cnt;
	City(){}
	City(int id,int cnt)
	{
		this.id=id;
		this.cnt=cnt;
	}
	void setId(int id)
	{this.id=id;}
	int getId()
	{return this.id;}
	void setCnt(int c)
	{cnt=c;}
	int getCnt()
	{return cnt;}
	public String toString()
	{
		return "\tId :"+id+" Cnt :"+cnt;
	}
}
class CityNode
{
	int src,dest,weight;
	City id;
	HashMap<City,LinkedList<Integer>> destPath=new HashMap<City,LinkedList<Integer>>();	
	HashMap<Integer,Integer> destId;
	//LinkedList<Integer> destId;
	CityNode(int id)
	{
		this.id=new City(id,0);
		this.src=0;
		this.dest=0;	
		this.weight=0;	
		destPath=new HashMap<City,LinkedList<Integer>>();
		destId=new HashMap<Integer,Integer>();
	}	
	 HashMap<City,LinkedList<Integer>> sortByValue()//HashMap<City,LinkedList<Integer>> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<City,LinkedList<Integer>> > list = 
               new LinkedList<Map.Entry<City,LinkedList<Integer>>> (this.destPath.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<City,LinkedList<Integer>>>() { 
            public int compare(Map.Entry<City,LinkedList<Integer>> o1,  
                               Map.Entry<City,LinkedList<Integer>> o2) 
            { 
                return ((o2.getKey()).cnt)-((o1.getKey()).cnt); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<City,LinkedList<Integer>> temp = new LinkedHashMap<City,LinkedList<Integer>>(); 
        for (Map.Entry<City,LinkedList<Integer>> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	void clearNode()
	{
		CityNode cn;
		Set entrySet = destId.entrySet();
		Iterator it = entrySet.iterator();
 		int val,i=-1,mx=-1; //l=destId.size();
		Map.Entry me,temp=null;
	    while(it.hasNext())
	    {
	        me = (Map.Entry)it.next();
	   		me.setValue(0);
   	    }
	}
	int get_Src(){return this.src;}
	void set_Src(int src){this.src=src;}
	void incrementSrc(){this.src+=1;}
	void decrementSrc(){this.src-=1;}
	
	int getDest(){return this.dest;}
	void setDest(int dest){this.dest=dest;}
	void incrementDest(){this.dest+=1;}
	void decrementDest(){this.dest-=1;}

	int getCnt()
	{return this.weight;}

	void setSrcDest(int src,int dest)
	{
		this.src=src;
		this.dest=dest;
	}
	int getId()
	{return this.id.id;}

	void setWeight(int w)
	{ this.weight=w;}

	boolean isDidPresent(int did)
	{
			Set entrySet = (destPath).entrySet();
			Iterator it = entrySet.iterator();
 			while(it.hasNext())
	        {
	        	Map.Entry me = (Map.Entry)it.next();
		        if(((City)me.getKey()).id==did)
		        	return true;
	        }	
	        return false;
	}
	void setValueForDid(int did,int c,LinkedList<Integer> ll)
	{
			Set entrySet = (destPath).entrySet();
			Iterator it = entrySet.iterator();
 			while(it.hasNext())
	        {
	        	Map.Entry me = (Map.Entry)it.next();
		        if(((City)me.getKey()).id==did)
		        {
		        	me.setValue(ll);
		        	((City)me.getKey()).setCnt(c);
		        }
	        }	
	}
	Integer maxDest(HashMap<Integer,CityNode> sl)
	{
		CityNode cn;
		Set entrySet = destId.entrySet();
		Iterator it = entrySet.iterator();
 		int val,i=-1,mx=-1; 
		Map.Entry me,temp=null;
	   	try
	   	{	   		
	   		while(it.hasNext()){
		       me = (Map.Entry)it.next();
		       val=(sl.get(me.getKey())).dest;
		       if(mx<val && (Integer)(me.getValue())==0)
		       {
		       		mx=val;
		       		temp=(Map.Entry)me;
	            	i=(Integer)(me.getKey());
	   	       }
	   	    }
	   	    temp.setValue((Integer)temp.getValue()+1);
	   	}catch(Exception e){}
	   	if(i==-1)
	   		this.clearNode();
	   		
	    return i; 
	}
}

class CityRouteGood
{
	CityRouteGood()
	{

	}

	static void check(int sid,HashMap<Integer,CityNode> stationList,LinkedList<Integer> tempStationList)
	{
		CityNode cn,cn1;
		int did=sid;
		LinkedList<Integer> path=new LinkedList<Integer>();
		LinkedList<Integer> temppath=new LinkedList<Integer>();
		cn=stationList.get(sid);
		cn1=stationList.get(sid);
		//System.out.println("SL in fun :"+stationList);
		int cnt=0;//=cn.getSrc();
		int tempDid;
		while(true)
		{
			//System.out.println("\n");
			try
			{
				//cnt-=cn.getDest();
				cnt=cnt+cn.getDest();
				if(cn.getId()==sid)
				{
					path.clear();
					temppath.clear();
				}
				path.add(cn.getId());
				temppath.add(cn.getId());
				if(tempStationList.contains(did))
				{
					if(cn1.isDidPresent(did))
					{
						cn1.setValueForDid(did,cnt,temppath);
					}
					else
					{
						(cn1.destPath).put(new City(did,cnt),temppath);
					}
					cnt=0;
					temppath=new LinkedList<Integer>(); 
					try{
						did=path.removeLast();
						cn=stationList.get(did);
						cn.clearNode();
						
						did=path.removeLast();
						cn=stationList.get(did);
						continue;
					}
					catch(Exception e)
					{
						break;
					}
				}
				try{
					did=cn.maxDest(stationList);
					if(did==-1)
					{
						//did=cn.getId();
						//stationList.remove(did);
						try
						{
							did=path.removeLast();
							cn=stationList.get(did);
							cn.clearNode();
							did=path.removeLast();
							cn=stationList.get(did);
							continue;
						}
						catch(Exception e1)
						{
							break;
						}
					}
				}
				catch(Exception e)
				{
					try
						{
							path.removeLast();
							did=path.removeLast();
							cn=stationList.get(did);
							continue;
						}
						catch(Exception e1)
						{
							break;
						}
				}
				
				cn=stationList.get(did);
				if(cn==null)
				{
					break;}
				if(path.isEmpty())
				{
					break;
				}
			}
			catch(Exception e){}
		}
	}
	static void printSrcDest(int sid,HashMap<Integer,CityNode> stationList)
	{
		//System.out.println("sid:"+sid);
		CityNode cn=stationList.get(sid);
		if(cn==null)
			System.out.println("Not Found");
		try
		{

        	HashMap<City,LinkedList<Integer>> temp = cn.sortByValue(); 
			Set entrySet = (temp).entrySet();
			Iterator it = entrySet.iterator();
 			LinkedList<Integer> ll;
	        while(it.hasNext())
	        {
	        	Map.Entry me = (Map.Entry)it.next();
		        System.out.println("\n\nDest: "+me.getKey());
		        ll=(LinkedList<Integer>)me.getValue();
		        ListIterator iit=ll.listIterator(0);
		        while(iit.hasNext())
		        {	
		            System.out.print("->"+iit.next());
		        }	
	        }
	        System.out.println();
		}
		catch(Exception e){}
		
	}
	public static void main(String []args)	
	{
		Scanner sc=new Scanner(System.in);
		CityNode cn;

		HashMap<Integer,CityNode> stationList=new HashMap<Integer,CityNode>();	
		LinkedList<Integer> tempStationList=new LinkedList<Integer>();
		int opt,src,dest,sid,did;
		while(true)
		{
			System.out.println("\n 1. AddUser\n 2.AddStation\n 3.AddPath\n 4.FindRoute");
			opt=sc.nextInt();
			if(opt>4)
				break;
			switch(opt)
			{
				case 1:
					System.out.println("Enter Start Station id and Dest Station");
					sid=sc.nextInt();		
					did=sc.nextInt();
					cn=(CityNode)stationList.get(sid);
					if(cn==null){System.out.println("Station " +sid+ " Not Found");}
					else cn.incrementSrc();
					cn=(CityNode)stationList.get(did);
					if(cn==null){System.out.println("Station " +did+ " Not Found");}
					else cn.incrementDest();
					break;
					
				case 2:
					System.out.println("Enter Station id");
					sid=sc.nextInt();
					cn=new CityNode(sid);
					stationList.put(sid,cn);
					break;

				case 3:
					System.out.println("Enter Start Station id and Dest Station");
					sid=sc.nextInt();		
					did=sc.nextInt();
					cn=(CityNode)stationList.get(did);
					if(cn==null){System.out.println("Station " +did+ " Not Found");}
  					else
					{ 	cn=(CityNode)stationList.get(sid);
					 	if(cn==null){System.out.println("Station " +sid+ " Not Found");}
					 	else
							cn.destId.put(did,0);					
					}
					break;

				case 4:
					System.out.println("Enter Start Station id and Multiple Dest Station Enter 0 to stop");
					sid=sc.nextInt();		
					did=sc.nextInt();
					while(did!=0)
					{
						tempStationList.add(did);
						did=sc.nextInt();
 					}
					check(sid,stationList,tempStationList);
					System.out.println("Src Path :"+sid);
					printSrcDest(sid,stationList);
					cn=(CityNode)stationList.get(sid);
					tempStationList.clear();
					cn.clearNode();
					break;
			}		
		}		
	}
}


/*
2
1
2
2
2
3
2
4
3
1 2
3
2 3
3
1 4
1
1 2
1 
1 2
1
1 3
1
2 3
1
1 4
1
1 4
1
1 4
4
1
2 4 0
*/
/*
2
12
2
13
2
14
2
15
2
16
3
12 13
3 
13 14
3
14 15
3 12 16
3
16 14
1
13 15
1
12 13
1
12 13
1
16 15
1 
12 14
1
14 15
4
12 
15 16 0
*/