/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;
/**
 * output class name
 */
public class TranCustomerSelectUI extends AbstractTranCustomerSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(TranCustomerSelectUI.class);
    
    public static String SHE_FILTER_CUSTOMER ="cus";
    public static final String CUSTOMER ="�ͻ�����";
    public static final String PHONE ="�ƶ��绰";
    public static final String TEL ="סլ�绰";
    public String isSignManage = "";
    protected List customer=null;
    protected SellProjectInfo sellProject=null;
    protected Boolean isCanEditMainCustomer=null;
    public TranCustomerSelectUI() throws Exception
    {
        super();
    }
	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		customer=(ArrayList)this.getUIContext().get("customer");
		
		//wyh ǩԼ��������
		if(this.getUIContext().get("isSignManage") != null){
			isSignManage = this.getUIContext().get("isSignManage").toString();
		}
		isCanEditMainCustomer=(Boolean)this.getUIContext().get("isCanEditMainCustomer");
		if(!isCanEditMainCustomer.booleanValue()){
			this.tblSelect.getColumn("isMain").getStyleAttributes().setLocked(true);
		}
		
		sellProject=SHEManageHelper.getParentSellProject(null,(SellProjectInfo)this.getUIContext().get("sellProject"));
//		setCustomerTable(null,null,null);
		setSelectTable();
		
		this.toolBar.setVisible(false);
		
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { PHONE }));
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { TEL }));
		
		this.btnAddCustomer.setVisible(false);
		
		for(int i=0;i<this.tblSelect.getColumnCount();i++){
			if(this.tblSelect.getColumnKey(i).equals("isMain")){
				this.tblSelect.getColumn(i).getStyleAttributes().setLocked(false);
			}else{
				this.tblSelect.getColumn(i).getStyleAttributes().setLocked(true);
			}
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
	}
	private void initTable(){
		this.tblCustomer.checkParsed();
		
		this.tblSelect.checkParsed();
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Boolean(true)));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblCustomer.getColumn("certificate").setEditor(f7Editor);
		this.tblSelect.getColumn("certificate").setEditor(f7Editor);
		
		this.tblCustomer.getStyleAttributes().setLocked(true);
		this.tblCustomer.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblSelect.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		
		this.tblSelect.getColumn("name").getStyleAttributes().setLocked(true);
		this.tblSelect.getColumn("name").setEditor(SHEManageHelper.getTxtCellEditor(0,80, true));
		this.tblSelect.getColumn("phone").setEditor(SHEManageHelper.getTxtCellEditor(0,80, true));
		this.tblSelect.getColumn("tel").setEditor(SHEManageHelper.getTxtCellEditor(0,80, true));
		this.tblSelect.getColumn("number").setEditor(SHEManageHelper.getTxtCellEditor(0,80, true));
		this.tblSelect.getColumn("address").setEditor(SHEManageHelper.getTxtCellEditor(0,80, true));
		
		KDCheckBox checkBox = new KDCheckBox();
		ICellEditor checkBoxEditor = new KDTDefaultCellEditor(checkBox);
		this.tblSelect.getColumn("isMain").setEditor(checkBoxEditor);
		
		String[] fields=new String[this.tblCustomer.getColumnCount()];
		for(int i=0;i<this.tblCustomer.getColumnCount();i++){
			fields[i]=this.tblCustomer.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblCustomer,fields);
		
		fields=new String[this.tblSelect.getColumnCount()];
		for(int i=0;i<this.tblSelect.getColumnCount();i++){
			fields[i]=this.tblSelect.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblSelect,fields);
	}
	private void addSHECustomerRow(SHECustomerInfo info) throws EASBizException, BOSException{
		if(info==null)return;
		IRow row =this.tblCustomer.addRow();
		row.setUserObject(info);
		row.getCell("id").setValue(info.getId());
		row.getCell("name").setValue(info.getName());
		row.getCell("phone").setValue(info.getPhone());
		row.getCell("tel").setValue(info.getTel());
		row.getCell("certificate").setValue(info.getCertificateType());
		row.getCell("number").setValue(info.getCertificateNumber());
		row.getCell("address").setValue(info.getMailAddress());
	}
	private void addSelectCustomerRow(TranCustomerEntryInfo info){
		if(info==null)return;
		IRow row =this.tblSelect.addRow();
		row.setUserObject(info);
		row.getCell("id").setValue(info.getId());
		row.getCell("isMain").setValue(new Boolean(info.isIsMain()));
		row.getCell("name").setValue(info.getName());
		row.getCell("phone").setValue(info.getPhone());
		row.getCell("tel").setValue(info.getTel());
		row.getCell("certificate").setValue(info.getCertificate());
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (info.getCustomer().getCustomerType().equals(CustomerTypeEnum.PERSONALCUSTOMER)) {
			filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Boolean(true)));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblSelect.getColumn("certificate").setEditor(f7Editor);
		
		row.getCell("number").setValue(info.getCertificateNumber());
		row.getCell("address").setValue(info.getAddress());
	}
	private void setCustomerTable(String customer,String phone,String tel) throws BOSException, EASBizException{
		SHECustomerCollection col =getSHECustomerCollection(customer,phone,tel);
		if(col==null) return;
		
		this.tblCustomer.removeRows();
		for(int i = 0 ; i < col.size() ; i++){
			addSHECustomerRow(col.get(i));
		}
	}
	private void setSelectTable(){
		if(customer==null) return;
		for(int i = 0 ; i < customer.size() ; i++){
			addSelectCustomerRow((TranCustomerEntryInfo)customer.get(i));
		}
	}
	private SHECustomerInfo setTranCustomerInfo(TranCustomerEntryInfo tranInfo,IRow row){
		SHECustomerInfo info=tranInfo.getCustomer();
		info.setName(row.getCell("name").getValue().toString());
		if(row.getCell("address").getValue()!=null){
			info.setMailAddress(row.getCell("address").getValue().toString());
		}
		info.setCertificateType((FDCCusBaseDataInfo)row.getCell("certificate").getValue());
		info.setCertificateNumber(row.getCell("number").getValue().toString());
		info.setPhone(row.getCell("phone").getValue().toString());
		if(row.getCell("tel").getValue()!=null){
			info.setTel(row.getCell("tel").getValue().toString());
			tranInfo.setTel(row.getCell("tel").getValue().toString());
		}
		tranInfo.setName(row.getCell("name").getValue().toString());
		if(row.getCell("address").getValue()!=null){
			tranInfo.setAddress(row.getCell("address").getValue().toString());
		}
		tranInfo.setCertificate((FDCCusBaseDataInfo)row.getCell("certificate").getValue());
		tranInfo.setCertificateNumber(row.getCell("number").getValue().toString());
		tranInfo.setPhone(row.getCell("phone").getValue().toString());
		tranInfo.setIsMain(((Boolean)row.getCell("isMain").getValue()).booleanValue());
		return info;
	}
	private void actionAddSelectCustomer(){
		SHEManageHelper.checkSelected(this,this.tblCustomer);
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblCustomer);
		if(selectRows.length+this.tblSelect.getRowCount()>5){
			FDCMsgBox.showWarning(this,"���ѡ��5���ͻ���¼��");
			SysUtil.abort();
		}
		for(int s=0;s<selectRows.length;s++){
			IRow row = this.tblCustomer.getRow(selectRows[s]);
			SHECustomerInfo info=(SHECustomerInfo)row.getUserObject();
			for(int i=0;i<this.tblSelect.getRowCount();i++){
				 if(info.getId().equals(((TranCustomerEntryInfo)this.tblSelect.getRow(i).getUserObject()).getCustomer().getId())){
					FDCMsgBox.showWarning(this,"��ѡ�ͻ��Ѿ����ڣ�");
					SysUtil.abort();
				}
			}
		}
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblCustomer.getRow(selectRows[i]);
			SHECustomerInfo info=(SHECustomerInfo)row.getUserObject();
			TranCustomerEntryInfo tranInfo=new TranCustomerEntryInfo();
			if(this.tblSelect.getRowCount()==0){
				tranInfo.setIsMain(true);
			}else{
				tranInfo.setIsMain(false);
			}
			tranInfo.setCustomer(info);
			tranInfo.setName(info.getName());
			tranInfo.setAddress(info.getMailAddress());
			tranInfo.setCertificate((FDCCusBaseDataInfo)this.tblCustomer.getRow(selectRows[i]).getCell("certificate").getValue());
			tranInfo.setCertificateNumber(info.getCertificateNumber());
			tranInfo.setPhone(info.getPhone());
			tranInfo.setTel(info.getTel());
			
			addSelectCustomerRow(tranInfo);
		}
	}
	protected void btnDown_actionPerformed(ActionEvent e) throws Exception {
		actionAddSelectCustomer();
	}
	protected void btnUp_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this,this.tblSelect);
		int activeRowIndex = this.tblSelect.getSelectManager().getActiveRowIndex();
		if(!isCanEditMainCustomer.booleanValue()&&((Boolean)this.tblSelect.getRow(activeRowIndex).getCell("isMain").getValue()).booleanValue()){
			FDCMsgBox.showWarning(this,"���ͻ�������ɾ����");
			return;
		}
		this.tblSelect.removeRow(activeRowIndex);
	}
	protected void tblCustomer_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			actionAddSelectCustomer();
		}
	}
	private SHECustomerCollection getSHECustomerCollection(String customer,String phone,String tel) throws BOSException, EASBizException{
		SHECustomerCollection sheCusCol=null;
		
		EntityViewInfo view = NewCommerceHelper.getPermitCustomerView(sellProject,SysContext.getSysContext().getCurrentUserInfo());
						
		if(view!=null){
			EntityViewInfo newView=(EntityViewInfo)view.clone();
			FilterInfo filter = new FilterInfo();		
			if(customer!=null){
				filter.getFilterItems().add(new FilterItemInfo("name","%"+customer+"%",CompareType.LIKE));
			}
			if(phone!=null){
				filter.getFilterItems().add(new FilterItemInfo("phone","%"+phone+"%",CompareType.LIKE));
			}
			if(tel!=null){
				filter.getFilterItems().add(new FilterItemInfo("tel","%"+tel+"%",CompareType.LIKE));
			}
			filter.getFilterItems().add(new FilterItemInfo("isPublic",Boolean.FALSE));
			newView.getFilter().mergeFilter(filter, "and");
			
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("id");
			sel.add("name");
			sel.add("phone");
			sel.add("tel");
			sel.add("certificateType.*");
			sel.add("certificateNumber");
			sel.add("mailAddress");
			sel.add("customerType");
			sel.add("propertyConsultant.*");
			sel.add("propertyConsultant.group.name");
			sel.add("propertyConsultant.CU.name");
			sel.add("mainCustomer.*");
			sel.add("recommended");
			sel.add("*");
			newView.setSelector(sel);
			sheCusCol = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(newView);
		}
		return sheCusCol;
	}
	private boolean isNull(Object value){
		if(value==null) return true;
		if(value instanceof String && value.toString().trim().equals("")) return true;
		return false;
	}
	
    protected void btnAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		FilterItemInfo scope = new FilterItemInfo("sellProject.id",sellProject.getId().toString());
		Map linkedCus = CusClientHelper.addNewCusBegin(this, sellProject.getId().toString());
		UIContext uiContext = new UIContext(this);
		if (linkedCus != null) {
			if(linkedCus.get("isAbort")!=null&&(Boolean)linkedCus.get("isAbort")){
				return;
			}
			uiContext.putAll(linkedCus);
			uiContext.put("sellProject", sellProject);
			
			//wyh ��ǩԼ�������ͻ�Ҫ����д�ʾ�İ�ť����
			uiContext.put("isSignManage", isSignManage);
			TranCustomerEntryInfo tranInfo=new TranCustomerEntryInfo();
			uiContext.put("tranInfo", tranInfo);
			if(isSignManage.equals("yes")){
				List list = new ArrayList();
				for(int i=0;i<tblSelect.getRowCount();i++){
					IRow row = tblSelect.getRow(i);
					TranCustomerEntryInfo sei = (TranCustomerEntryInfo)row.getUserObject();
					row.getCell("id").setValue(sei.getCustomer());
					list.add(row);
				}
				uiContext.put("selectCustomer", list);
			}
			
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			
			
			if(this.tblSelect.getRowCount()==0){
				tranInfo.setIsMain(true);
			}else{
				tranInfo.setIsMain(false);
			}
			if(tranInfo.getCustomer()!=null){
				addSelectCustomerRow(tranInfo);
			}
		}
		if(getTextValue(txtFilterValue)!=null){
			fillCusomterTable();
		}
	}
    protected String getTextValue(KDTextField text) {
		if (text.getText().trim().equals("")) {
			return null;
		} else {
			return text.getText();
		}
	}
    private void fillCusomterTable() throws EASBizException, BOSException{
    	String customer=null;
		String phone=null;
		String tel=null;
		if (comboFilter.getSelectedItem().toString().equals(CUSTOMER)) {
			customer = getTextValue(txtFilterValue);
		}
		if (comboFilter.getSelectedItem().toString().equals(PHONE)) {
			phone = getTextValue(txtFilterValue);
		}
		if (comboFilter.getSelectedItem().toString().equals(TEL)) {
			tel = getTextValue(txtFilterValue);
		}
		setCustomerTable(customer,phone,tel);
    }
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		if(getTextValue(txtFilterValue)==null){
			FDCMsgBox.showWarning(this,"��ѯ��������Ϊ�գ�");
			SysUtil.abort();
		}
		fillCusomterTable();
	}
	protected void tblSelect_editStarting(KDTEditEvent e) throws Exception {
		if("isMain".equals(this.tblSelect.getColumn(e.getColIndex()).getKey())){
			if(!((Boolean)e.getValue()).booleanValue()){
				for(int i=0;i<this.tblSelect.getRowCount();i++){
					if(i!=e.getRowIndex()){
						this.tblSelect.getRow(i).getCell("isMain").setValue(new Boolean(false));
					}
				}
			}
		}
	}
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	List customerEntry=new ArrayList();
    	SHECustomerCollection sheCustomerEntry=new SHECustomerCollection();
//    	SelectorItemCollection sel=new SelectorItemCollection();
//    	sel.add("name");
//    	sel.add("mailAddress");
//    	sel.add("certificateType");
//    	sel.add("certificateNumber");
//    	sel.add("phone");
//    	sel.add("tel");
    	boolean hasIsMain=false;
    	for(int i=0;i<this.tblSelect.getRowCount();i++){
    		IRow row=this.tblSelect.getRow(i);
    		if(isNull(row.getCell("name").getValue())){
    			this.tblSelect.getEditManager().editCellAt(row.getRowIndex(), this.tblSelect.getColumnIndex("name"));
    			FDCMsgBox.showWarning(this,"�ͻ����Ʋ���Ϊ�գ�");
    			SysUtil.abort();
    		}
    		if(isNull(row.getCell("phone").getValue())){
    			this.tblSelect.getEditManager().editCellAt(row.getRowIndex(), this.tblSelect.getColumnIndex("phone"));
    			FDCMsgBox.showWarning(this,"�ƶ��绰����Ϊ�գ�");
    			SysUtil.abort();
    		}
    		if(isNull(row.getCell("certificate").getValue())){
    			this.tblSelect.getEditManager().editCellAt(row.getRowIndex(), this.tblSelect.getColumnIndex("certificate"));
    			FDCMsgBox.showWarning(this,"֤�����Ͳ���Ϊ�գ�");
    			SysUtil.abort();
    		}
    		if(isNull(row.getCell("number").getValue())){
    			this.tblSelect.getEditManager().editCellAt(row.getRowIndex(), this.tblSelect.getColumnIndex("number"));
    			FDCMsgBox.showWarning(this,"֤�����벻��Ϊ�գ�");
    			SysUtil.abort();
    		}
    		if(isNull(row.getCell("address").getValue())){
    			this.tblSelect.getEditManager().editCellAt(row.getRowIndex(), this.tblSelect.getColumnIndex("address"));
    			FDCMsgBox.showWarning(this,"ͨ�ŵ�ַ����Ϊ�գ�");
    			SysUtil.abort();
    		}
    		if(((Boolean)row.getCell("isMain").getValue()).booleanValue()){
    			hasIsMain=true;
    		}
    		SHECustomerInfo info=setTranCustomerInfo((TranCustomerEntryInfo)row.getUserObject(),row);
    		
    		String	paramValue="true";
    		try {
    			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_QD");
    		} catch (EASBizException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (BOSException e1) {
    			// TODO Auto-generated catch block
    			
    			e1.printStackTrace();
    		}
    		if("false".equals(paramValue)){
    			if(info.getFirstDate()==null&&info.getReportDate()==null){
        			FDCMsgBox.showWarning(this,"�ͻ��������ں��׷����ڶ�Ϊ�գ�");
            		SysUtil.abort();
        		}
    		}
    		
    		sheCustomerEntry.add(info);
    		customerEntry.add(row.getUserObject());
    	}
    	if(!hasIsMain){
    		FDCMsgBox.showWarning(this,"��ѡ��һ�����ͻ���");
    		SysUtil.abort();
    	}
    	Set certificateNumber=new HashSet();
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("sysCustomer.*");
    	for(int i=0;i<sheCustomerEntry.size();i++){
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("certificateNumber", sheCustomerEntry.get(i).getCertificateNumber()));
    		filter.getFilterItems().add(new FilterItemInfo("id", sheCustomerEntry.get(i).getId().toString(),CompareType.NOTEQUALS));
			if (certificateNumber.contains(sheCustomerEntry.get(i).getCertificateNumber())||SHECustomerFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this,"�Ѿ�����֤������Ϊ"+sheCustomerEntry.get(i).getCertificateNumber()+"����¥�ͻ���");
				SysUtil.abort();
			}
			certificateNumber.add(sheCustomerEntry.get(i).getCertificateNumber());
			
    		if(sheCustomerEntry.get(i).getMainCustomer()!=null){
    			FDCMainCustomerInfo mainCus= FDCMainCustomerFactory.getRemoteInstance().getFDCMainCustomerInfo(new ObjectUuidPK(sheCustomerEntry.get(i).getMainCustomer().getId()),sel);
    			if(mainCus.getSysCustomer()!=null){
        			CustomerInfo custom=CustomerFactory.getRemoteInstance().getCustomerInfo(new ObjectUuidPK(mainCus.getSysCustomer().getId()));
        			if(!custom.getNumber().equals(sheCustomerEntry.get(i).getCertificateNumber())){
        				filter=new FilterInfo();
        				filter.getFilterItems().add(new FilterItemInfo("number",sheCustomerEntry.get(i).getCertificateNumber()));
        				if(CustomerFactory.getRemoteInstance().exists(filter)){
        					FDCMsgBox.showWarning(this,"�Ѿ����ڱ���Ϊ"+sheCustomerEntry.get(i).getCertificateNumber()+"�������ݿͻ���");
        					SysUtil.abort();
        				}
        			}
        		}
    		}
    	}
    	for(int i=0;i<sheCustomerEntry.size();i++){
			SHECustomerFactory.getRemoteInstance().submit(sheCustomerEntry.get(i));
    	}
    	
    	this.getUIContext().put(SHE_FILTER_CUSTOMER, customerEntry);
    	super.destroyWindow();
    }
    
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }
    public boolean destroyWindow()
    {
    	this.getUIContext().put(SHE_FILTER_CUSTOMER, this.customer);
    	return super.destroyWindow();
    }
}