/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
/**
 * output class name
 */
public class MarketingCostUI extends AbstractMarketingCostUI
{
    /**
	 * Ӫ������ͳ�Ʋ�ѯ��������
	 */
	private static final long serialVersionUID = -4494140624057695476L;
	private static final Logger logger = CoreUIObject.getLogger(MarketingCostUI.class);
		public static final String BIZDATEBEGIN="bizDateBegin" ;
		public static final String BIZDATEEND = "bizDateEnd";
		public static final String SELLPROJECT = "sellProject";

		public boolean verify()
		{
			if(stDate.getTimestamp()==null){
				MsgBox.showInfo("ҵ�����ڿ�ʼ����Ϊ�գ�");
				SysUtil.abort();
			}
			if(endDate.getTimestamp()==null){
				MsgBox.showInfo("ҵ�����ڽ�������Ϊ�գ�");
				SysUtil.abort();
			}
			if(stDate.getTimestamp().after(endDate.getTimestamp()))
			{
				MsgBox.showInfo("ҵ��ʼ���ڲ��ܴ��ڽ�������");
				SysUtil.abort();
			}
			return super.verify();
		}
		
		//��ʼ����ѯ����
    public void onLoad() throws Exception {
			// TODO Auto-generated method stub
    	Calendar   ca   =   Calendar.getInstance();   
		 ca.setTime(new Date());   //  
		 ca.set(Calendar.DAY_OF_MONTH,   1);   
		 
		 Date   firstDate   =   ca.getTime(); 
		 ca.add(ca.MONTH, 1);
		 ca.set(ca.DATE, 1);
		 ca.add(ca.DATE, -1);
		 Date lastDate = ca.getTime();
		 this.stDate.setValue(firstDate);
		 this.endDate.setValue(lastDate);
			super.onLoad();
		}


	/**
     * output class constructor
     */
    public MarketingCostUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		return getCustomFilterCondition();
		
	}

	public void onInit(RptParams initParams) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setCustomCondition(RptParams params) {
		// TODO Auto-generated method stub
		
	}
	public RptParams getCustomFilterCondition() {
		RptParams rptParams = new RptParams();
		if(this.stDate.getValue()!=null){
		   rptParams.setObject(BIZDATEBEGIN,stDate.getTimestamp());
		}
		if(endDate.getValue()!=null){
			rptParams.setObject(BIZDATEEND,endDate.getTimestamp());
		}
		return rptParams;
	}

}