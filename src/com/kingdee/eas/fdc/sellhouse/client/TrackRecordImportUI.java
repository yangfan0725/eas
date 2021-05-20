/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TrackRecordImportUI extends AbstractTrackRecordImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(TrackRecordImportUI.class);
    
    private Map mapReceptionType = null; 
    private Map mapEventType = null;
    private Map mapSaleMan = null;     
    
    boolean existNumberRule = CommerceHelper.isExistNumberRule(new TrackRecordInfo());
    /**
     * output class constructor
     */
    public TrackRecordImportUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		return TrackRecordEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TrackRecordFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		
		this.prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		SellProjectInfo sellProInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
		if(sellProInfo!=null)
			this.prmtSellProject.setValue(sellProInfo);
		
		
	
		//this.actionExcelSave.setEnabled(false);
		this.actionExcelImport.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.menuView.setVisible(false);
		
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		
	}
	
	
	public void onShow() throws Exception {
		super.onShow();
		
     	int indexNum = this.tblMain.getColumn("importStatus").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
	}
	
	public void actionExcelImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelImport_actionPerformed(e);
		
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		if(sellProInfo==null) {
			MsgBox.showInfo("������ѡ��'������Ŀ'!");
			return;
		}
		
		initColumnEditor();  
		
		//�����������
        int rowCount = tblMain.getRowCount();
		for(int i=0;i<rowCount;i++) {
		   tblMain.removeRow(0);
		}		
		
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.txtFilePath.setText(fileName);
		
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(true);
		int excelNum = CommerceHelper.importExcelToTable(fileName, this.tblMain);  //�����ص��в�д��
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(false);
		
		this.tblMain.setRowCount(excelNum);
		this.txtExcelCount.setText(String.valueOf(excelNum));
		
	    //�������м�¼У�� 
		for(int i=0;i<this.tblMain.getRowCount();i++)
			vertifyARowImportDate(i);
		
		
		setTheTextCount();
		
		
		this.actionExcelSave.setEnabled(true);
		
		
	}
	
	
	/**
	 *���ò��ɵ������� �������� �� �޸�����
	 */
	private void setTheTextCount() {
		//Map modifyRowMap = new HashMap();  //Ҫ�޸ĵ��е�ӳ��
		Map valuidRowMap = new HashMap();  //�ɵ�����е�ӳ��
		
		int invalildCount = 0;
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);			
			if(isHasErrorRow(row))
				invalildCount ++;
			else{
				String number = (String)row.getCell("number").getValue();				
				if(number!=null && !number.trim().equals("")) {
					number = number.trim();
					if(valuidRowMap.get(number)==null)  {
						valuidRowMap.put(number,row);
						row.getCell("importStatus").setValue("У��ͨ��,��������");
					}else{                 //���ܴ��ڵ���������ı������ظ���
						IRow dupRow = (IRow)valuidRowMap.get(number);
						this.setRowStyleLockStatus(row,"���� �� ��"+dupRow.getRowIndex()+1+ "�ı����ظ�!");
					
					}					
				}				
			}
		}
		this.txtInvaluableCont.setText(String.valueOf(invalildCount));
		
		int modifyCount = 0;
		//ֻ��Ҫ����޴����
		//�����޸� ����һ�����ڱ��� ���ұ����ڿ��д���
		Set numberSet = new HashSet();
		numberSet.add("null");
		numberSet.addAll(valuidRowMap.keySet());		
		EntityViewInfo view = new EntityViewInfo(); 
		FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",numberSet,CompareType.INCLUDE));
		view.setFilter(filter);
		try {
			TrackRecordCollection trackColl = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
			for(int i=0;i<trackColl.size();i++)  {
				TrackRecordInfo trackInfo = trackColl.get(i);
				//�ҵ�Ҫ�޸ĵ��У�����id��䵽����
				IRow mdRow = (IRow)valuidRowMap.get(trackInfo.getNumber());
				mdRow.getCell("id").setValue(trackInfo.getId().toString());
				((TrackRecordInfo)mdRow.getUserObject()).setId(trackInfo.getId());
				mdRow.getCell("importStatus").setValue("У��ͨ��,�޸�����");
			}		
			modifyCount = trackColl.size();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.txtModifyCount.setText(String.valueOf(modifyCount));
		this.txtAddNewCount.setText(String.valueOf(this.tblMain.getRowCount()-invalildCount-modifyCount));
	}
	
	
	
	private boolean isHasErrorRow(IRow row) {
		boolean hasErrorRow = false;
		String importStatus = (String)row.getCell("importStatus").getValue();
		if(importStatus!=null && !importStatus.trim().equals("")) {
			if(importStatus.indexOf("У��ͨ��")<0 && importStatus.indexOf("����ɹ�")<0)
				hasErrorRow = true;			
		}
		return hasErrorRow;
	}
	
	
	
	
	
	public void actionExcelSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelSave_actionPerformed(e);
		//����ȫ��У��ͨ������ܱ���
		String invalildCount = this.txtInvaluableCont.getText();
		if(invalildCount==null) return;
		if(invalildCount!=null && !invalildCount.equals("0")) {
			MsgBox.showInfo("����"+invalildCount+"��δУ��ͨ��,����ȫ��У��ͨ������ܱ���!");
			return;
		}
		
		
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);
			String importStatus = (String)row.getCell("importStatus").getValue(); 
			//ֻ���У��ͨ�����н��б��� (�ѱ�����ĳ���)
			if(importStatus!=null && importStatus.indexOf("У��ͨ��")>=0){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("eventDate"));
				selector.add(new SelectorItemInfo("receptionType"));
				selector.add(new SelectorItemInfo("head"));
				selector.add(new SelectorItemInfo("phoneNumber"));
				selector.add(new SelectorItemInfo("sellProject"));
				selector.add(new SelectorItemInfo("saleMan"));
				selector.add(new SelectorItemInfo("commerceChance"));
				selector.add(new SelectorItemInfo("description"));
				selector.add(new SelectorItemInfo("trackDes"));
				selector.add(new SelectorItemInfo("eventType"));
				TrackRecordInfo trackInfo = (TrackRecordInfo)row.getUserObject();
				if(trackInfo.getId()!=null) {  //�޸�
					try{
						TrackRecordFactory.getRemoteInstance().updatePartial(trackInfo,selector);
						row.getCell("importStatus").setValue("����ɹ�,�޸�����");
					}catch(Exception ee){
						this.setRowStyleLockStatus(row,"����ʧ��,�޸����� \r\n"+ee.getMessage());				
					}
				} else {   //����
					String number = trackInfo.getNumber();
					if(number==null || number.trim().equals("")) {
						if(existNumberRule) {
							number = CommerceHelper.getNumberByRule(new TrackRecordInfo());
							trackInfo.setNumber(number);
						}
					}
					
					if(number!=null && !number.trim().equals("")) {					
					  try{
						TrackRecordFactory.getRemoteInstance().addnew(trackInfo); //IObjectPK ioPk = 
						row.getCell("importStatus").setValue("����ɹ�,��������");
					  }catch(Exception ee) {
						  this.setRowStyleLockStatus(row,"����ʧ��,�������� \r\n"+ee.getMessage());		
					  }
					}else{
						this.setRowStyleLockStatus(row,"����ʧ��,�������� \r\n"+"����Ϊ��");	
					}
				}
			}
		}
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());

	}
	
	
	
	
	/**
	 * ��ʼ����Ԫ����Ŀؼ�
	 */
	private void initColumnEditor(){    
		//this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("importStatus").setWidth(200);  // ����״̬
		
		try{
			this.tblMain.getColumn("seller").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.base.permission.app.F7UserQuery",CommerceHelper.getPermitSalemanView((SellProjectInfo)this.prmtSellProject.getValue())));  // Ӫ������
			this.tblMain.getColumn("seller").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMain.getColumn("customer").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery",CommerceHelper.getPermitCustomerView((SellProjectInfo)this.prmtSellProject.getValue())));  // �ͻ�
			this.tblMain.getColumn("customer").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		}catch(Exception e){
			e.printStackTrace();
			this.abort();
		}
		/*  �������õ�editor,��cell���޷����    ���Ҫ��ʱ�ı�query���� ����������ʱ��䵥Ԫ��Ŀؼ� 
		EntityViewInfo nullView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id","null"));
		nullView.setFilter(filter);
		this.tblMain.getColumn("commerceChance").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceQuery",nullView));  // �̻�
		*/
		//number   ��������
		if(!existNumberRule) 
			this.tblMain.getColumn("number").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("eventDate").setEditor(CommerceHelper.getKDDatePickerEditor());  //��������
		this.tblMain.getColumn("eventDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblMain.getColumn("eventDate").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("receptionType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ReceptionTypeQuery",null));  //�Ӵ���ʽ
		this.tblMain.getColumn("receptionType").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("eventType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.EventTypeQuery",null));  // �����¼�
		this.tblMain.getColumn("eventType").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		
		//description  ����˵��
		
	}
	
	
	
	/**
	 * ���ĳһ�н���У��
	 * 	 * ���뵼�����:  Ӫ������ �ͻ�  ��������  �Ӵ���ʽ �����¼�
	 * @param rowNum
	 */
	private void vertifyARowImportDate(int rowNum) {
		IRow row = this.tblMain.getRow(rowNum);
		if(row==null)  return;
		
		TrackRecordInfo trackInfo = (TrackRecordInfo)row.getUserObject();
		if(trackInfo==null) {
			trackInfo = new TrackRecordInfo();
			SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
			if(sellProInfo!=null)  trackInfo.setSellProject(sellProInfo);
			trackInfo.setSysType(MoneySysTypeEnum.SalehouseSys);
			trackInfo.setTrackType(TrackRecordTypeEnum.Clew);
		}	
		
		StringBuffer errorBuffer = new StringBuffer();
		

		//Ӫ������   mapSaleMan
		if(mapSaleMan==null) {
			try{
				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
				mapSaleMan = CommerceHelper.getPermitUserMap((SellProjectInfo)this.prmtSellProject.getValue(),currentUserInfo);
			}catch(BOSException e){this.abort();}
		}
	    Object seller = row.getCell("seller").getValue();  
    	if(seller!=null) {
    		if(seller instanceof String) {        			
    			UserInfo thisInfo = (UserInfo)mapSaleMan.get(((String)seller).trim());
    			if(thisInfo!=null){
    				row.getCell("seller").setValue(thisInfo);
    				trackInfo.setSaleMan(thisInfo);
    			}else{
    				trackInfo.setSaleMan(null);
    				errorBuffer.append("'Ӫ������'�޷�ʶ��!\r\n");
    			}        
    		}else if(seller instanceof UserInfo){
    			trackInfo.setSaleMan((UserInfo)seller);    
    		}
    	}else{
    		trackInfo.setSaleMan(null);
//    		trackInfo.setHead(null);
//    		row.getCell("customer").setValue(null);
//    		trackInfo.setCommerceChance(null);
//    		row.getCell("commerceChance").setValue(null);
    		errorBuffer.append("'Ӫ������'����¼��'!\r\n");
    	}
		
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
    	
    	
		//�ͻ�����   -ֻ����Ե�ǰ���۹�����Ȩ�޵�����������Ա�µĿͻ�     
		Object customer = row.getCell("customer").getValue();
		if(customer!=null) {
			if(customer instanceof String)  {
				FDCCustomerCollection custColl = null;
				String tempPhone = (String)row.getCell("phoneNumber").getValue();
				try {					
					if(trackInfo.getSaleMan()!=null){
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
						filter.getFilterItems().add(new FilterItemInfo("name",customer));
						if(tempPhone!=null && !tempPhone.trim().equals("")){
							filter.getFilterItems().add(new FilterItemInfo("phone","%"+tempPhone+"%",CompareType.LIKE));
						}
						try{
							UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
							String sqlPerSalmanIdStr = CommerceHelper.getPermitSaleManIdSql((SellProjectInfo)this.prmtSellProject.getValue(),currentUserInfo);	
							filter.getFilterItems().add(new FilterItemInfo("salesman.id",sqlPerSalmanIdStr,CompareType.INNER));
						} catch (EASBizException e) {
							e.printStackTrace();
							SysUtil.abort();
						}							
						view.setFilter(filter);
						custColl = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
					}
				} catch (BOSException e) {
					this.handUIException(e);
					this.abort();	
				}	

				if(custColl!=null && custColl.size()==1){
					row.getCell("customer").setValue(custColl.get(0));
					trackInfo.setHead(custColl.get(0));
				}else{
					trackInfo.setHead(null);
					if(custColl.size()>1)
						errorBuffer.append("'�ͻ�����'���ظ�����ָ��!\r\n");
					else
						errorBuffer.append("'�ͻ�����',�޷�ʶ��!\r\n");
				}
			}else if(customer instanceof FDCCustomerInfo){
				FDCCustomerInfo thisInfo = (FDCCustomerInfo)customer;
				trackInfo.setHead(thisInfo);
				if(((FDCCustomerInfo) customer).getPhone()!=null && !("").equals(((FDCCustomerInfo) customer).getPhone())){
					row.getCell("phoneNumber").setValue(((FDCCustomerInfo) customer).getPhone());
				}
			}
		}else{
			trackInfo.setHead(null);
//			trackInfo.setCommerceChance(null);
//			row.getCell("commerceChance").setValue(null);
			errorBuffer.append("'�ͻ�����'����¼��!\r\n");
		}

    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
		
    	//�̻���ϵ�绰	 phoneNumber
		String phoneNumber = (String)row.getCell("phoneNumber").getValue();
		if(phoneNumber==null || phoneNumber.trim().equals("")) {
			setRowStyleLockStatus(row,"'�̻���ϵ�绰'����Ϊ��!");
			return;
		}if (phoneNumber.length()>80) {
			setRowStyleLockStatus(row,"'�̻���ϵ�绰'�ַ�����>80!");
			return;				
		}
		trackInfo.setPhoneNumber(phoneNumber.trim());
		
		//�����̻���f7�ؼ�
		EntityViewInfo f7ComView = new EntityViewInfo();
		FilterInfo f7ComFilter = new FilterInfo();
		if(trackInfo.getSaleMan()!=null)
			f7ComFilter.getFilterItems().add(new FilterItemInfo("saleMan.id",trackInfo.getSaleMan().getId().toString()));
		else
			f7ComFilter.getFilterItems().add(new FilterItemInfo("saleMan.id","null"));
		if(trackInfo.getHead()!=null)
			f7ComFilter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",trackInfo.getHead().getId().toString()));
		else
			f7ComFilter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id","null"));						
		f7ComView.setFilter(f7ComFilter);
		row.getCell("commerceChance").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceQuery",f7ComView));

		
		//�̻�   -- ֱ�Ӳ�ѯ  
		Object comChance = row.getCell("commerceChance").getValue();
		if(comChance!=null) {
			if(comChance instanceof String)  {
				CommerceChanceCollection comColl = null;
				try {
					if(trackInfo.getSaleMan()!=null && trackInfo.getHead()!=null) {
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();	
						filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",trackInfo.getHead().getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("saleMan.id",trackInfo.getSaleMan().getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("name",comChance));	
						view.setFilter(filter);
						comColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);					
					}
				} catch (BOSException e) {
					this.handUIException(e);
					this.abort();	
				}	
				if(comColl!=null && comColl.size()==1){
					row.getCell("commerceChance").setValue(comColl.get(0));
					trackInfo.setCommerceChance(comColl.get(0));
				}else{
					trackInfo.setCommerceChance(null);
					errorBuffer.append("'�̻�',�޷�ʶ��!\r\n");			
				}
			}else if(comChance instanceof CommerceChanceInfo){
				CommerceChanceInfo thisInfo = (CommerceChanceInfo)comChance;
				trackInfo.setCommerceChance(thisInfo);
			}	
		}else{
			trackInfo.setCommerceChance(null);
		}
		
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
		
		if(trackInfo.getCommerceChance()!=null) {
			trackInfo.setTrackType(TrackRecordTypeEnum.Intency);
		}
    	
		
		//��������
		String number = (String)row.getCell("number").getValue();
		if(number==null || number.trim().equals("")) {
			if(!existNumberRule) 
				errorBuffer.append("'��������'����Ϊ��!");
		}else if(number.length()>80){
			errorBuffer.append("'��������'�ַ�̫��>80!");
		}
		trackInfo.setNumber(number);

    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
				
		
		//��������
    	Object eventDate  = row.getCell("eventDate").getValue();	
    	if(eventDate!=null && eventDate instanceof String){
    		if(CommerceHelper.isDateFormat(eventDate.toString(),"yyyy-MM-dd")) {
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date thisDate = formatter.parse(eventDate.toString());
					trackInfo.setEventDate(thisDate);
        			row.getCell("eventDate").setValue(eventDate);
				} catch (ParseException e) {
					//e.printStackTrace();
					errorBuffer.append("'��������'�޷�ʶ��!\r\n");
				}        			
    		}else{
    			errorBuffer.append("'��������'�޷�ʶ��!\r\n");
    		}
    	}else if(eventDate instanceof Date) {
    		trackInfo.setEventDate((Date)eventDate);
    	}else{
    		errorBuffer.append("'��������'����¼��!\r\n");
    	}			
	
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
    	 
		
		//�Ӵ���ʽ
		if(mapReceptionType==null) {
			try{
				mapReceptionType = CommerceHelper.getReceptionTypeMap();
			}catch(BOSException e){this.abort();}
		}	 
	    Object receptionType = row.getCell("receptionType").getValue();   
    	if(receptionType!=null) {
    		if(receptionType instanceof String) {        			
    			ReceptionTypeInfo thisInfo = (ReceptionTypeInfo)mapReceptionType.get(((String)receptionType).trim());
    			if(thisInfo!=null){
    				row.getCell("receptionType").setValue(thisInfo);
    				trackInfo.setReceptionType(thisInfo);
    			}else{
    				errorBuffer.append("'�Ӵ���ʽ'�޷�ʶ��!\r\n");
    			}        
    		}else if(receptionType instanceof ReceptionTypeInfo){
    			trackInfo.setReceptionType((ReceptionTypeInfo)receptionType);        			
    		}
    	}else{
    		errorBuffer.append("'�Ӵ���ʽ'����¼��!\r\n");
    	}
    	
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}

		//�����¼�
		if(mapEventType==null) {
			try{
				mapEventType = CommerceHelper.getEventTypeMap();
			}catch(BOSException e){this.abort();}
		}	
	    Object eventType = row.getCell("eventType").getValue(); 
    	if(eventType!=null) {
    		if(eventType instanceof String) {        			
    			EventTypeInfo thisInfo = (EventTypeInfo)mapEventType.get(((String)eventType).trim());
    			if(thisInfo!=null){
    				row.getCell("eventType").setValue(thisInfo);
    				trackInfo.setEventType(thisInfo);
    			}else{
    				errorBuffer.append("'�����¼�'�޷�ʶ��!\r\n");
    			}        
    		}else if(eventType instanceof EventTypeInfo){
    			trackInfo.setEventType((EventTypeInfo)eventType);        			
    		}
    	}else{
    		errorBuffer.append("'�����¼�'����¼��!\r\n");
    	}
		
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}
    	
		
		//����˵��  trackDes
    	String trackDes = (String)row.getCell("trackDes").getValue(); 
		if(trackDes!=null) {
			if(trackDes.length()>80) 
				errorBuffer.append("'����˵��'�ַ�����>80!\r\n");
			else
				trackInfo.setTrackDes(trackDes);
		}
		
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}		
		
		//��������
    	String description = (String)row.getCell("description").getValue(); 
		if(description!=null) {
			if(description.length()>80)
				errorBuffer.append("'��������'�ַ�����>80!\r\n");
			else
				trackInfo.setDescription(description);
		
		}
		row.setUserObject(trackInfo);
		
    	if(!errorBuffer.toString().equals("")) {
    		setRowStyleLockStatus(row,errorBuffer.toString());
    		return;
    	}

		setRowStyleLockStatus(row,"У��ͨ��");
	}
	
	
	private void setRowStyleLockStatus(IRow row,String str ) {
		if(str==null || str.trim().equals("")) return;
		
		if(str.indexOf("У��ͨ��")>=0 || str.indexOf("����ɹ�")>=0) {
			row.getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.WHITE);	
			if(str.indexOf("����ɹ�")>=0)
			   row.getStyleAttributes().setLocked(true);
		}else{			
			row.getStyleAttributes().setLocked(false);
			
			row.getCell("importStatus").getStyleAttributes().setLocked(true);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.RED);
		}
		row.getCell("importStatus").setValue(str);
		
		
	}
	
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		
		
		int rowIndex = e.getRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
//		String importStatus = (String)row.getCell("importStatus").getValue();
//		if(importStatus!=null && importStatus.indexOf("����ɹ�")>=0)
//			return;
		if(e.getColIndex()==this.tblMain.getColumnIndex("seller") ||
				e.getColIndex()==this.tblMain.getColumnIndex("customer")) {
			if(e.getValue()==null || (e.getOldValue()!=null && !e.getOldValue().equals(e.getValue()))) {
				row.getCell("commerceChance").setValue(null);
				if(row.getUserObject()!=null){
					TrackRecordInfo trackInfo = (TrackRecordInfo)row.getUserObject();
					trackInfo.setCommerceChance(null);
				}
			}			
		}
		
		
		
		vertifyARowImportDate(rowIndex);
		
		setTheTextCount();
	}

    
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String key = this.getSelectedKeyValue();
		if(key==null) return;		
		
		super.tblMain_tableClicked(e);
	}
	
	
	
    
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
			//super.actionRemove_actionPerformed(e);
        checkSelected();
        if (confirmRemove())
        {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			for(int i=selectRows.length-1;i>-1;i--) {
				this.tblMain.removeRow(selectRows[i]);		
			}	
			
			setTheTextCount();
        }
		
	}
	
	
	
	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		
		
		
	}
	
	public int getRowCountFromDB(){
		return -1;
	}


    
}