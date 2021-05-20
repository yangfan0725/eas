/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CodeGenerater;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUiHelper;

/**
 * output class name
 */
public class ChequePickEditUI extends AbstractChequePickEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChequePickEditUI.class);
    private CRMChequeInfo editdata = null;
    
    /**
     * output class constructor
     */
    public ChequePickEditUI() throws Exception
    {
        super();
    }
    
    /**
     * �ж��Ƿ�����Ŀ һ����Ŀ��ʾ���������ʾ��Ŀ�ؼ�
     */
    protected void F7Cheque_dataChanged(DataChangeEvent e) throws Exception {
    	CRMChequeInfo cheque = (CRMChequeInfo)this.F7Cheque.getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("CRMCheque.id",cheque.getId().toString()));
    	SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add("sellProject.*");
    	view.setFilter(filter);
    	view.setSelector(sel);
    	//��Ҫ����ȡһ��
    	ChequeSellProjectEntryCollection  sellColl = ChequeSellProjectEntryFactory.getRemoteInstance().getChequeSellProjectEntryCollection(view);
    	if(sellColl.size()==1){
    		ChequeSellProjectEntryInfo sellentryInfo =  sellColl.get(0);
    		this.F7SellProject.setValue(sellentryInfo.getSellProject());
    		this.F7SellProject.setEnabled(false);
    	} else {
    		this.contSellProject.setVisible(false);
    	}
    	
    }
    
    public void onLoad() throws Exception {
    	//��ȡһ������
    	String id = (String)this.getUIContext().get("chequeid");
    	editdata = CRMChequeFactory.getRemoteInstance().getCRMChequeInfo(new ObjectUuidPK(id));
    	this.contStart.setEnabled(false);
		this.contEnd.setEnabled(false);
    	super.onLoad();
    	loadFields();
    	
    }
  
    public void loadFields() {
    	this.F7Cheque.setValue(this.editdata);
    	this.spinnerStart.setValue(new Integer(this.editdata.getSerialNumber()));
    	this.spinnerEnd.setValue(new Integer(this.editdata.getSerialNumber()+this.editdata.getNumberOfCheque()-1));
    	
    	this.F7picker.setValue(SysContext.getSysContext().getCurrentUserInfo());
    }
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	verifyInput();
    	if(this.radPart.isSelected()){
    		verifyIsCanPick();
    	}
    	CRMChequeFactory.getRemoteInstance().pickCheque(prepareData());
    	this.destroyWindow();
    }

    /**
     * ��������ʱ����ѡ��ˮ���Ƿ�������
     */
    String numberString = null;
	private void verifyIsCanPick() throws EASBizException, BOSException {
		// ���ñ��������������ʼ��ˮ��
		CRMChequeInfo cheque = (CRMChequeInfo)this.F7Cheque.getValue();
		CodeGenerater codeGen = new CodeGenerater();
		int beginIndex = Integer.parseInt(this.spinnerStart.getValue().toString());
		int endIndex = Integer.parseInt(this.spinnerEnd.getValue().toString());
		int count = endIndex - beginIndex +1;
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(cheque.getCodeRule());
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i< count ; i++){
			sb.append(",");
			sb.append(codeGen.nextNumber(cheque.getNumberOfCheque(),cheque.getSerialNumber()));
		}
		numberString = sb.toString().replaceFirst(",","");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("status",new Integer(ChequeStatusEnum.BOOKED_VALUE),CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number",numberString,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("cheque.id",cheque.getId().toString()));
		if(ChequeDetailEntryFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"ֻ��Ʊ��״̬Ϊ�ѵǼǵĲ��ܱ����ã����ٴ�ѡ��");
			this.abort();
		}
	}

	private Map prepareData() {
		Map dataMap = new HashMap();
		dataMap.put("cheque", this.F7Cheque.getValue());
		dataMap.put("start",this.spinnerStart.getValue());
		dataMap.put("end", this.spinnerEnd.getValue());
		dataMap.put("picker",this.F7picker.getValue());
		dataMap.put("pickDate", this.pickDate.getValue());
		dataMap.put("radAll", new Boolean(this.radAll.isSelected()));
		dataMap.put("radpart", new Boolean(this.radPart.isSelected()));
		dataMap.put("numberString", numberString);
		return dataMap;
	}

	private void verifyInput() {
		if(this.F7Cheque.getValue()==null){
			FDCMsgBox.showWarning(this,"��ѡ��Ʊ�����κţ�");
			this.abort();
		}
		//Ч����ˮ���Ƿ�Խ����
		if(!this.radAll.isSelected()){
			Integer start = new Integer(((CRMChequeInfo)this.F7Cheque.getValue()).getSerialNumber());
			Integer numberOfCheque =  new Integer(((CRMChequeInfo)this.F7Cheque.getValue()).getNumberOfCheque());
			if(((Integer)this.spinnerStart.getValue()).compareTo(((Integer)this.spinnerEnd.getValue()))>0){
				FDCMsgBox.showWarning(this,"��ʼ��ˮ�Ų���С�ڽ�����ˮ��  ");
				this.abort();
			}
			if(((Integer)this.spinnerStart.getValue()).compareTo(start)<0){
				FDCMsgBox.showWarning(this,"��ʼ��ˮ�Ų���С��Ʊ�ݵǼ��ϵ���ˮ��  "+start);
				this.abort();
			}
			
			if(((Integer)this.spinnerEnd.getValue()).compareTo(new Integer(start.intValue()+numberOfCheque.intValue()-1))>0){
				FDCMsgBox.showWarning(this,"��ʼ��ˮ�Ų��ܴ���Ʊ�ݵǼ��ϵ���ˮ��" + (start.intValue()+numberOfCheque.intValue()-1));
				this.abort();
			}
		}
		//Ч��������
		
		if(this.F7picker.getValue()==null){
			FDCMsgBox.showWarning(this,"��ѡ�������ˣ�");
			this.abort();
		}
		
		if(this.pickDate.getValue()==null){
			FDCMsgBox.showWarning(this,"����д�������ڣ�");
			this.abort();
		}
	}
	
	
	/**
	 * ȫѡ����ˮ�Ų��ɱ༭
	 */
	protected void radAll_actionPerformed(ActionEvent e) throws Exception {
		if(this.radAll.isSelected()){
			this.contStart.setEnabled(false);
			this.contEnd.setEnabled(false);
		}
	}
	
	
	/**
	 * ��������ˮ�ſɱ༭
	 */
	protected void radPart_actionPerformed(ActionEvent e) throws Exception {
		if(this.radPart.isSelected()){
			this.contStart.setEnabled(true);
			this.contEnd.setEnabled(true);
		}
	}
	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
    

}