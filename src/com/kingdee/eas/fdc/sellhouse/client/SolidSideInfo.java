package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

public class SolidSideInfo {
	/**
	 * ��Ԫ��󶨵Ķ���
	 */
	private Object bizObj;
	/**
	 * ��Ԫ��󶨵Ķ���ID
	 */
	private String id;	
	/**
	 * ������ �и�
	 */
	private BigDecimal rowhigh;	
	/**
	 * ��Ԫ��
	 */
	private BigDecimal extent;
	/**
	 * ����������
	 */
	private int rowIndex;
	/**
	 * ����������
	 */
	private int colIndex;
	/**
	 * ��Ԫ����ʾ����
	 */
	private String text;
	/**
	 * ���������� ��������������ʾ
	 */
	private String rowName;
	/**
	 * ״̬ ������ʾ��ɫ
	 */
	private String state;
	/**
	 *  ���� ����ͼ������λ����
	 */
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Object getBizObj() {
		return bizObj;
	}
	public void setBizObj(Object bizObj) {
		this.bizObj = bizObj;
	}
	public BigDecimal getExtent() {
		return extent;
	}
	public void setExtent(BigDecimal extent) {
		this.extent = extent;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColIndex() {
		return colIndex;
	}
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	public BigDecimal getRowhigh() {
		return rowhigh;
	}
	public void setRowhigh(BigDecimal rowhigh) {
		this.rowhigh = rowhigh;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
