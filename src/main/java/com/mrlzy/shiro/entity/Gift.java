package com.mrlzy.shiro.entity;

public class Gift {
	private int index;
    private String gitfId;
    private String giftName;
    private double probability;
    private int    icount;
 
    public Gift(int index, String gitfId, String giftName, double probability, int icount) {
        this.index = index;
        this.gitfId = gitfId;
        this.giftName = giftName;
        this.probability = probability;
        this.icount = icount;
    }
 
    public int getIndex() {
        return index;
    }
 
    public void setIndex(int index) {
        this.index = index;
    }
 
    public String getGitfId() {
        return gitfId;
    }
 
    public void setGitfId(String gitfId) {
        this.gitfId = gitfId;
    }
 
    public String getGiftName() {
        return giftName;
    }
 
    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }
 
    public double getProbability() {
        return probability;
    }
 
    public void setProbability(double probability) {
        this.probability = probability;
    }
 
    
    public int getIcount() {
		return icount;
	}

	public void setIcount(int icount) {
		this.icount = icount;
	}

	@Override
    public String toString() {
        return "Gift [index=" + index + ", gitfId=" + gitfId + ", giftName=" + giftName + ", probability=" + probability + ", icount=" + icount +"]";
    }
}
