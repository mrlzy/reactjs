package com.mrlzy.shiro.tool;


import com.mrlzy.shiro.entity.Gift;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RandomUtil {

	private static List<String> listOpenids;
	private static List<Gift>  gifts;
	private static Map<Integer, Integer> count;
		
	public static Map<Integer, Integer> getCount() {
		return count;
	}

	public static void setCount(Map<Integer, Integer> count) {
		RandomUtil.count = count;
	}

	
	public static List<String> getListOpenids() {
		return listOpenids;
	}

	public static void setListOpenids(List<String> listOpenids) {
		RandomUtil.listOpenids = listOpenids;
	}

	public static void setGifts(List<Gift> gifts) {
		RandomUtil.gifts = gifts;
	}


	private static class RandomUtilInstance { 
	    private static final RandomUtil instance = new RandomUtil(); 
	} 

	public static RandomUtil getInstance() { 
	    return RandomUtilInstance.instance; 
	} 

	private RandomUtil() { 
		gifts = RandomUtil.getGifts();
		count = new HashMap<Integer, Integer>();
		listOpenids = new ArrayList();
	} 
	
	public static int getGiftId(String openid){
		int orignalIndex=0;
		if(listOpenids.contains(openid))
			return orignalIndex;
		List<Double> orignalRates = new ArrayList<Double>(gifts.size());
        for (Gift gift : gifts) {
            double probability = gift.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }
 
        // statistics
        orignalIndex = RandomUtil.lottery(orignalRates);
        Integer value = count.get(orignalIndex);
        if(value==null)
            	value = 0;
        Gift gift = gifts.get(orignalIndex);
        if(gift.getIcount()>0 && value>=gift.getIcount()){
        	orignalIndex = getGiftId(openid) ;
        }
        else{
            count.put(orignalIndex, value == 0 ? 1 : value + 1);
            if(orignalIndex>0){
            	listOpenids.add(openid);
            }
        }
        
        return orignalIndex;
	}
	  
	public static List<Gift> getGifts(){
		int lottery=0;
		List<Gift> gifts = new ArrayList<Gift>();
		ClassLoader loader = RandomUtil.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("random.properties");
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(in));  
			String data="";//一次读入一行，直到读入null为文件结束  
			while( (data = br.readLine())!=null){  
			     String[] obj = data.split(",");
			     if(obj.length==5){
			    	 gifts.add(new Gift(Integer.parseInt(obj[0].toString().trim()),
			    			 obj[1].toString().trim(),
			    			 obj[2].toString().trim(),
			    			 Double.parseDouble(obj[3].toString().trim()),
			    			 Integer.parseInt(obj[4].toString().trim())));
			     }
			}  
			br.close();
			if(in!=null)
				in.close();
			return gifts;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gifts;
		
	}
	
	/*public static void main(String[] args) {  
		RandomUtil tr = new RandomUtil();  
	    // 用List随机  
	    long begin = System.currentTimeMillis();  
	    List<Gift> gifts = getGifts();
	    String openid="test";
	    double num=10000;
	    for(int i=0; i<num; i++){
	    	int idx = RandomUtil.getInstance().getGiftId(openid);
	    }
	    for (Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability=" + entry.getValue() / num);
        }
	    
//	    RandomUtil.setListOpenids(new ArrayList());
//	    RandomUtil.setCount(new HashMap<Integer, Integer>());
//	    for(int i=0; i<num; i++){
//	    	int idx = RandomUtil.getInstance().getGiftId(openid);
//	    }
	    
//        List<Double> orignalRates = new ArrayList<Double>(gifts.size());
//        for (Gift gift : gifts) {
//            double probability = gift.getProbability();
//            if (probability < 0) {
//                probability = 0;
//            }
//            orignalRates.add(probability);
//        }
// 
//        // statistics
//        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
//        double num = 10000;
//        for (int i = 0; i < num; i++) {
//            int orignalIndex = RandomUtil.lottery(orignalRates);
// 
//            Integer value = count.get(orignalIndex);
//            if(value==null)
//            	value = 0;
//            Gift gift = gifts.get(orignalIndex);
//            if(gift.getIcount()>0 && value>=gift.getIcount()){
//            	 continue;
//            }
//            else
//            	count.put(orignalIndex, value == 0 ? 1 : value + 1);
//        }
// 
        for (Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability=" + entry.getValue() / num);
        }
        
	    System.out.println("list使用时间：" + (System.currentTimeMillis() - begin));  
	}  */
	
	public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }
 
        int size = orignalRates.size();
 
        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }
 
        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }
 
        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);
 
        return sortOrignalRates.indexOf(nextDouble);
    }
}
