package com.kingdee.eas.fdc.finance.client;

/**
 * ��װ��
 * ���ز�����ʹ��Ԥ����Ŀ�������ļ�����������Ԥ��BgItemID,BgItemNumber,BgItemName
 * @author cassiel 
 * @date 2010-09-30
 *
 */
public class FDCBgItemProp {
	String bgItemIDProp = null;
	String bgItemNumberProp = null;
	String bgItemNameProp = null;
	
	/**bgItemIDProp��bgItemNumberProp��bgItemNameProp �������� */
	public FDCBgItemProp(String bgItemIDProp, String bgItemNumberProp, String bgItemNameProp){
		this.bgItemIDProp = bgItemIDProp;
		this.bgItemNumberProp = bgItemNumberProp;
		this.bgItemNameProp = bgItemNameProp;
	}
	public String getBgItemIDProp(){
		return this.bgItemIDProp ;
	};
	public String getBgItemNumberProp(){
		return this.bgItemNumberProp ;
	}
	public String getBgItemNameProp(){
		return this.bgItemNameProp ;
	}
}
