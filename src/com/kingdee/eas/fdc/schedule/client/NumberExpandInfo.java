package com.kingdee.eas.fdc.schedule.client;

public class NumberExpandInfo {
	private String id;
    private String number;
    private String longNumber;
    private boolean isExpandStatus;
    private int level;
    private boolean isLeaf;

    /**
     * @return ���� isExpandStatus��
     */
    public boolean isExpandStatus() {
        return isExpandStatus;
    }

    /**
     * @param isExpandStatus Ҫ���õ� isExpandStatus��
     */
    public void setExpandStatus(boolean isExpandStatus) {
        this.isExpandStatus = isExpandStatus;
    }

    /**
     * @return ���� number��
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number Ҫ���õ� number��
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public String toString() {
        return number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getLongNumber() {
		return longNumber;
	}

	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
}
