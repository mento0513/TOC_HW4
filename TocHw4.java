// HW4 of Theory-of-Computation in NCKU
// Name:陳威丞
// Student ID:F74002060
// File description:
// To find which road in a city has house trading 
// records spread in #max_distinct_month.
// Example Input:
// http://www.datagarage.io/api/538447a07122e8a77dfe2d86
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;
public class TocHw4
{
	private static String url;
	private static URL data;
    private static URLConnection connect;
    private static BufferedReader in ;
    private static int MAXNUM = 10000;
    private static int MaxPrice = -1;
    private static int MinPrice = 1000000000;
    private static String ParseJson() throws IOException
    {
    	String inputLine;
    	StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        return a.toString();
    }
    private static int max(int a, int b)
    {
    	if( a > b) return a;
    	else return b;
    }
    private static int min(int a, int b)
    {
    	if( a < b) return a;
    	else return b;
    }
	public static void main(String[] args) throws IOException, JSONException
	{
		url = args[0];
		data = new URL(url);
		connect = data.openConnection();
		in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
		String data = ParseJson();
		JSONArray Data = new JSONArray(data);
		JSONObject object;
		String add,key,month;
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<Integer, String> map2add = new HashMap<Integer, String>();
		ArrayList<String>[] L = new ArrayList[MAXNUM];
		int maxP[] = new int[MAXNUM];
		int minP[] = new int[MAXNUM];
		Arrays.fill(maxP, MaxPrice);
		Arrays.fill(minP, MinPrice);
		int id = 1,value;
		for(int i=0;i<Data.length();i++)
		{
			object  = Data.getJSONObject(i);
			add = object.getString("土地區段位置或建物區門牌");
			month = Integer.toString(object.getInt("交易年月"));
			value = object.getInt("總價元");
			if( add.contains("路"))
			{
				key = add.substring(0, add.indexOf("路")+1);
				if( map.get(key) == null)
				{
					map.put(key,id);
					map2add.put(id++,key);
					L[map.get(key)] = new ArrayList<String>();
				}
				if(!L[map.get(key)].contains(month))
					L[map.get(key)].add(month);
				maxP[map.get(key)] = max(maxP[map.get(key)],value);
				minP[map.get(key)] = min(minP[map.get(key)],value);
			}
			else if( add.contains("街"))
			{
				key = add.substring(0, add.indexOf("街")+1);
				if( map.get(key) == null)
				{
					map.put(key,id);
					map2add.put(id++,key);
					L[map.get(key)] = new ArrayList<String>();
				}
				if(!L[map.get(key)].contains(month))
					L[map.get(key)].add(month);
				maxP[map.get(key)] = max(maxP[map.get(key)],value);
				minP[map.get(key)] = min(minP[map.get(key)],value);
			}
			else if( add.contains("大道"))
			{
				key = add.substring(0, add.indexOf("大道")+2);
				if( map.get(key) == null)
				{
					map.put(key,id);
					map2add.put(id++,key);
					L[map.get(key)] = new ArrayList<String>();
				}
				if(!L[map.get(key)].contains(month))
					L[map.get(key)].add(month);
				maxP[map.get(key)] = max(maxP[map.get(key)],value);
				minP[map.get(key)] = min(minP[map.get(key)],value);
			}
			else if( add.contains("巷"))
			{
				key = add.substring(0, add.indexOf("巷")+1);
				if( map.get(key) == null)
				{
					map.put(key,id);
					map2add.put(id++,key);
					L[map.get(key)] = new ArrayList<String>();
				}
				if(!L[map.get(key)].contains(month))
					L[map.get(key)].add(month);
				maxP[map.get(key)] = max(maxP[map.get(key)],value);
				minP[map.get(key)] = min(minP[map.get(key)],value);
			}
		}
		int maxSize = -1;
		for(int i=1;i<id;i++)
			if( L[i].size() > maxSize) maxSize = L[i].size();
		for(int i=1;i<id;i++)
			if( L[i].size() == maxSize)
				System.out.println(map2add.get(i)+", 最高成交價: "+maxP[i]+", 最低成交價: "+minP[i]);
	}
}
