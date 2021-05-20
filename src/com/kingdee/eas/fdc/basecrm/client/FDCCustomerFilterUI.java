/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class FDCCustomerFilterUI extends AbstractFDCCustomerFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCustomerFilterUI.class);
    
    /**
     * output class constructor
     */
    public FDCCustomerFilterUI() throws Exception
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
    
    public void onLoad() throws Exception{  //add by shilei 
    	super.onLoad();
    	//�ѿͻ����ͺͽ�����ʽ��ʼ��Ϊ��
    	comboCustomerType.addItem("");
    	comboCustomerType.addItem(CustomerTypeEnum.PERSONALCUSTOMER);
    	comboCustomerType.addItem(CustomerTypeEnum.ENTERPRICECUSTOMER);
    	EstablishWay.addItem("");
    	EstablishWay.addItem(CreateWayEnum.HAND);
    	EstablishWay.addItem(CreateWayEnum.CHILDGENERATE);
    	EstablishWay.addItem(CreateWayEnum.PARENTREFERENCE);
    	EstablishWay.addItem(CreateWayEnum.UNITSHARE);
    	EstablishWay.addItem(CreateWayEnum.SYSTEMIMPORT);
    	pkCreateTime.setValue(null);
    	pkLastUpdateTime.setValue(null);
    	chkIsEnabled.setSelected(true);
    }
    public FilterInfo getFilterInfo(){ //���ù�������  //add by shilei
    	super.getFilterInfo();
    	FilterInfo  filter = new FilterInfo();
    	//�ͻ�����FDCOrgCustomer
		 if(this.comboCustomerType.getSelectedItem() instanceof CustomerTypeEnum){
   		 filter.getFilterItems().add(new FilterItemInfo("FDCOrgCustomer.customerType",((CustomerTypeEnum)this.comboCustomerType.getSelectedItem()).getValue(),CompareType.EQUALS));
        }
		 //������ʽ
		 if(this.EstablishWay.getSelectedItem() instanceof CreateWayEnum){
			 filter.getFilterItems().add(new FilterItemInfo("createWay",((CreateWayEnum)this.EstablishWay.getSelectedItem()).getValue(),CompareType.EQUALS));
		 }
		 //��������
		 if(this.pkCreateTime.getValue()!=null ){
			 getDateFilter(filter,pkCreateTime,"createTime",CompareType.LESS_EQUALS);
			 getDateFilter(filter,pkCreateTime,"createTime",CompareType.GREATER_EQUALS);
		 }

		 //����޸�ʱ��
		 if(this.pkLastUpdateTime.getValue()!=null){
			 getDateFilter(filter,pkLastUpdateTime,"lastUpdateTime",CompareType.LESS_EQUALS);
			 getDateFilter(filter,pkLastUpdateTime,"lastUpdateTime",CompareType.GREATER_EQUALS);
		 }
		 //�Ƿ�ѡ���ͻ�
		 if(this.chkIsChooseRoom.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isChooseRoom","1",CompareType.EQUALS));
		 }
		 //�Ƿ�����
		 if(this.chkIsEnabled.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isEnabled","1",CompareType.EQUALS));
		 }
		 //�Ƿ��Ա
		 if(this.chkIsInsider.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isInsider","1",CompareType.EQUALS));
		 }
		 
		return filter;
    	
    }
    /**ʱ��ؼ���ѯ
     * compareType.less������ʱ�䣩 or compareType.GREATER(��ʼʱ��)
     * @param compareType  �������� ������Ϊ�� null
     * @param filterItem �������� 
     * @param filter 
     * */
    public static FilterInfo getDateFilter(FilterInfo filter, KDDatePicker pkEndBizDate, String filterItem, CompareType compareType) {
	    Date applyDate = (Date) pkEndBizDate.getValue();
	    if (applyDate != null ) {
		    Calendar calendar=null; 
		    calendar = Calendar.getInstance();
		    calendar.setTime(applyDate);
		    if(CompareType.GREATER_EQUALS.equals(compareType))
		    	calendar.add(Calendar.DATE, -1);
		    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),23, 59, 59);
		    filter.getFilterItems().add(new FilterItemInfo(filterItem,calendar.getTime(),compareType));
	    }
	    return filter;
    }

	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onInit(RptParams rptparams) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setCustomCondition(RptParams rptparams) {
		// TODO Auto-generated method stub
		
	}

}