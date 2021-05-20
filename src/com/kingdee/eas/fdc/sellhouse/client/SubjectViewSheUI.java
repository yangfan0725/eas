/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocSubItemTypeEnum;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.JumpConditionEnum;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SubjectViewSheUI extends AbstractSubjectViewSheUI
{
    private static final Logger logger = CoreUIObject.getLogger(SubjectViewSheUI.class);
    
    protected DataBinder subjectDataBinder = new DataBinder();
    ValidateHelper subjectHelper ;//=  new ValidateHelper( getDataObject() , this.dataBinder ) ;
    //
    private boolean isClosingWidOk = false;
    
    public final static String SUBJECT_INFO_NAME = "documentSubject";
    
    public final static String INTEM_INFO_NAME = "documentItem";
    
    private KDBizPromptBox f7ColPrompt = new KDBizPromptBox();
    
    
    /**
     * output class constructor
     */
    public SubjectViewSheUI() throws Exception {
		super();
	}

    protected void kdtItems_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    { 
    	/*
        int index=kdtOptions.getSelectManager().getActiveRowIndex();// wangxiaomin
			if(kdtOptions.getRow(index).getCell("xLength").getValue() instanceof Integer){
				int length=((Integer)kdtOptions.getRow(index).getCell("xLength").getValue()).intValue();
				if(length>10000||length<0){
					kdtOptions.getRow(index).getCell("xLength").setValue("");
					MsgBox.showInfo("���ȳ�����");
					return;
				}
			}else if(kdtOptions.getRow(index).getCell("xLength").getValue() instanceof String){
				kdtOptions.getRow(index).getCell("xLength").setValue("");
				MsgBox.showInfo("����ӦΪ������");
				return ;
			}
			*/
    }

    public void loadFields() {
    	this.txtDespLength.setValue(new Integer(50));//���ó��ȣ������ʼֵ
    	this.txtDespFontSize.setValue(new Integer(12));
    	
    	EventListener[] listeners = this.combDocItemType.getListeners(ItemListener.class);	//������
		for(int i=0;i<listeners.length;i++){
			((KDComboBox)this.combDocItemType).removeItemListener((ItemListener)listeners[i]);
		}
    	
    	if(documentSubject.getSubItemType()==null)
    		documentSubject.setSubItemType(DocSubItemTypeEnum.Manual);//Ĭ��Ϊ���ֹ�"
    		
		this.comSubjectNumberShow.setSelected(documentSubject!=null && documentSubject.getIsShowNumber()==1);//Ĭ�ϼ����
		//src start
		this.kDIsRequired.setSelected(documentSubject!=null && documentSubject.getIsRequired()==1);//��Ŀ����
		//src end
        dataBinder.loadFields();
		subjectDataBinder.loadFields();//��������
		
		if(documentSubject.getSubItemType()!=null && documentSubject.getSubItemType().equals(DocSubItemTypeEnum.Relation)){
			this.kdtItems.getColumn("assisType").getStyleAttributes().setHided(false);
			this.contSelPro.setVisible(true);//�������ϼ���Ŀ
		}
		
		if(documentSubject.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)){//����
			if(documentSubject.getItems().size()>0) {
				DocumentItemInfo itemInfo = documentSubject.getItems().get(0);
				if(itemInfo.getOptions().size()>0) {					
					this.txtDespFontSize.setValue(new Integer(itemInfo.getOptions().get(0).getXFontSize()));
					this.txtDespLength.setValue(new Integer(itemInfo.getOptions().get(0).getXLength()));
				}
			}
		}
		
		
		for(int i=0;i<listeners.length;i++){//�Ӽ�����
			((KDComboBox)this.combDocItemType).addItemListener((ItemListener)listeners[i]);
		}		
	}

	public void onLoad() throws Exception {
        registerItemTable();//ע�����
        registerSubjectValidator();//���棿����
		super.onLoad();		
		
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionSure.setEnabled(true);
		this.toolBar.remove(this.btnAttachment);
				
		this.txtSubjectTopic.setRequired(true);
		this.kdtItems.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.kdtOptions.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
				
		Integer txtSubjectNumber = (Integer)this.getUIContext().get("txtSubjectNumber");
		if(txtSubjectNumber!=null)
			this.txtSubjectNumber.setText((new Integer(txtSubjectNumber.intValue()+1)).toString());
		
    	f7ColPrompt.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssisGroupF7Query");
    	f7ColPrompt.setEditable(true);
    	f7ColPrompt.setDisplayFormat("$name$");
    	f7ColPrompt.setEditFormat("$name$");
    	f7ColPrompt.setCommitFormat("$number$");
    	f7ColPrompt.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent arg0) {
				SellProjectInfo sellProInfo = (SellProjectInfo)SubjectViewSheUI.this.prmtSellPro.getValue();
				if(sellProInfo==null) {
					FDCMsgBox.showWarning("������ѡ����Ŀ��");
					SysUtil.abort();
				}	
				//������Ŀ���˸����������
				//f7ColPrompt.setEntityViewInfo(null);
		}});
		this.kdtItems.getColumn("assisType").setEditor(new KDTDefaultCellEditor(f7ColPrompt));
		
		for(int i=0;i<this.kdtItems.getRowCount();i++) {
			IRow thisRow = this.kdtItems.getRow(i);
			DocumentItemInfo itemInfo = (DocumentItemInfo)thisRow.getUserObject();
			if(itemInfo.getRelatTypeId()!=null){
				SHECusAssistantDataGroupInfo groupInfo = SHECusAssistantDataGroupFactory.getRemoteInstance().getSHECusAssistantDataGroupInfo(
							"select number,name where id = '"+itemInfo.getRelatTypeId()+"'");
				thisRow.getCell("assisType").setValue(groupInfo);
			}
		}
		
		if(documentSubject.getTopic()==null || !documentSubject.getTopic().equals(""))
			this.setUITitle("������Ŀ");
		
		this.txtSubjectTopic.setMaxLength(80);
		
		
		this.prmtSellPro.setEntityViewInfo(NewCommerceHelper.getPermitProjectView());
		
		//src start ���ؿؼ�
		comVisable();
		//src end
	}
	/**
	 * ���ؿؼ� ��ť�����鼰���ⲻ���޸�
	 */
	public void comVisable(){
		this.addLine.setVisible(false);//���а�ť����
		this.btnAddItem.setVisible(false);//���ӷ���
		this.btnDeleItem.setVisible(false);//ɾ������
		this.kdtItems.setVisible(false);//������
		this.kDLabel8.setVisible(false);//��������
		this.txtSubjectTopic.setEnabled(false);
		this.kdtOptions.getColumn("topic").getStyleAttributes().setLocked(true);
		this.kdtOptions.setLocation(14,109);
		this.addLine.setLocation(670, 72);
		this.deleLine.setLocation(670, 72);
	}
	public void registerSubjectValidator() {
		ValidateHelper vh = getSubjectValidateHelper();
		vh.setCustomValidator(getValidator());
		vh.registerBindProperty("items", ValidateHelper.ON_SAVE);		
		vh.registerBindProperty("items.optionNumber", ValidateHelper.ON_SAVE);		
		vh.registerBindProperty("items.topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontSize", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontName", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("xFontSize", ValidateHelper.ON_SAVE);
	}
	
	private void whenSubjectItemRowChanged(int currRow, int currColu,int prevRow, int prevColu) {
		if (currRow >= 0 && (prevRow != prevColu || currRow != prevRow)) {
			// ����������
			storeFields();
			//subjectDataBanderStoreFields();
			// ���в���
			DocumentItemInfo currItem = documentSubject.getItems().get(currRow);
			// ֻ������ OPTION �ı��
			setDataObject(currItem);
			// ֻ��LOAD OPTION �ı��
			dataBinder.loadFields();
		}
	}
	
	private void whenSubjectItemCellEdited(int currRow,int currColu){
		whenSubjectItemRowChanged(currRow,currColu,-1,-1);
	}
	
	/**
	 * ע��һЩ����
	 */
	public void registerItemTable(){
		this.kdtItems.addKDTSelectListener(new SubjectSelectListener());
		kdtItems.addKDTEditListener(new SubjectEditListener());
		subjectDataBinder.registerBinding("items", DocumentItemInfo.class, this.kdtItems, "userObject");
		subjectDataBinder.registerBinding("items.optionNumber", String.class, this.kdtItems, "topic.optionNumber");
		subjectDataBinder.registerBinding("items.topic", String.class, this.kdtItems, "topic.text");
		subjectDataBinder.registerBinding("items.xFontSize", int.class, this.kdtItems, "xFontSize.text");
		subjectDataBinder.registerBinding("items.xFontName", String.class, this.kdtItems, "xFontName.text");
		subjectDataBinder.registerBinding("topic", String.class, this.txtSubjectTopic, "text");
		subjectDataBinder.registerBinding("xFontSize", int.class, this.subjectFontSize, "value");
		subjectDataBinder.registerBinding("subjectType", String.class, this.comSubjectType, "selectedItem");
		subjectDataBinder.registerBinding("alignType", int.class, this.comOptonAlignType, "selectedItem");
		subjectDataBinder.registerBinding("horizontalAlign", int.class, this.comHorizonType, "selectedItem");
		subjectDataBinder.registerBinding("showNumber", int.class, this.txtSubjectNumber, "value");
		subjectDataBinder.registerBinding("subItemType", String.class, this.combDocItemType, "selectedItem");
		subjectDataBinder.registerBinding("sellProjectId", SellProjectInfo.class, this.prmtSellPro, "value");
		//TODO �Ƿ��������Ҫע�����
		kdtItems.putBindContents("editData",new String[] {"topic","xFontSize","xFontName"});
		kdtItems.checkParsed();
	}      
	
	
	public final ValidateHelper getSubjectValidateHelper() {
        if( subjectHelper == null ) {
        	subjectHelper = new ValidateHelper( documentSubject , subjectDataBinder ) ;
        }
        //subjectHelper.setDataObject( documentSubject ) ;
        return this.subjectHelper ;
    }
	
	public boolean isModify() {
		return false;
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {//��������
		subjectDataBanderStoreFields();
		dataBinder.storeFields();
	}
    
    public void subjectDataBanderStoreFields(){
    	subjectDataBinder.storeFields();
    	if(documentSubject!=null){
    		documentSubject.setIsShowNumber(this.comSubjectNumberShow.isSelected()?1:0);
    		//src start
    		documentSubject.setIsRequired(this.kDIsRequired.isSelected()?1:0);
    		//src end
    		
    	}//TODO  ������Ҫ���ö�ȡ�Ƿ�Ϊ������ 
//    	if(kdtOptions!=null){
//    		for(int i=0;i<kdtOptions.getRowCount();i++){
//    			if(null!=kdtOptions.getRow(i).getCell("xlength").getValue()){
//    				int length=((Integer)kdtOptions.getRow(i).getCell("xlength").getValue()).intValue();
//        		    if(length==0){
//        		    	
//        		    }
//    			}
//    		
//    		}
//    		
//    	}
    }
    
	public void setDataObject(String key, IObjectValue dataObject) {//�������ݶ���
		if(SUBJECT_INFO_NAME.equals(key) && dataObject!=null){
			subjectDataBinder.setValueObject(dataObject);
		}else if(INTEM_INFO_NAME.equals(key)){
			setDataObject(dataObject);
		}
		super.setDataObject(key, dataObject);
	}

	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
	}

	/**
	 * ����һ����
	 */
    protected IObjectValue createNewData() {
    	DocumentItemInfo dItem = null;
    	if(documentSubject == null||documentSubject.getItems().size() == 0){
    		//�����ǰ����ĿΪ�գ���ô������һ����Ŀ
    		if(documentSubject == null){
    			documentSubject = new DocumentSubjectInfo();
    			documentSubject.setTopic("��������Ŀ");
    		}
    		//����һ������
    		dItem = new DocumentItemInfo();
        	dItem.setTopic("");
    		documentSubject.getItems().add(dItem);
    		//ע��
    		subjectDataBinder.setValueObject(documentSubject);
    	}else{
    		dItem = documentSubject.getItems().get(0);
    	}
		return dItem;
	}
    
    /**
     * ����һ��ѡ��
     */
	protected IObjectValue createNewDetailData(KDTable table) {
		IObjectValue ov = null;
		if(table == kdtOptions){
			DocumentOptionInfo option = new DocumentOptionInfo();
			option = new DocumentOptionInfo();
			option.setXFontName("Dialog");
			option.setXFontSize(12);
			option.setXLength(0);
			option.setIsTopicInverse(false);
			option.setOptionNumber(kdtOptions.getRowCount()+1);
			ov = option;
		}else if(table == kdtItems){
			DocumentItemInfo item = new DocumentItemInfo();
			item.setTopic("");
			item.setXFontSize(12);
			item.setXFontName("Dialog");
			item.setItemNumber(kdtItems.getRowCount()+1);
			ov = item;
		}
		
		return ov;
	}

	/**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	//actionSure_actionPerformed
    }
    
    private boolean verifyDocumentSubject(DocumentSubjectInfo documentSubject,StringBuffer feekback){
    	boolean ok = true;
    	if(!(ok = !isStringNullOrBlank(documentSubject.getTopic()))){
    		feekback.append("��Ŀ����Ϊ������д����Ϣ��");
    	}else if(!(ok = isNumberBetween(8,80,documentSubject.getXFontSize()))){
    		feekback.append("���峬����Χ��8~80��");
    	}else{
    		DocumentItemCollection ic = documentSubject.getItems();
    		int icCount = ic.size();
    		for(int i=0 ; ok && i<icCount ; i++){
    			DocumentItemInfo itemInfo = ic.get(i);
    			
    			if(!documentSubject.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)) {
    				//���ﱾ�����õģ�����itemInfo�ϵ�xFontSize�󶨲��ϣ�ȡ����ֵ���������д��ȥһ�� (��Ȼ����ʱ���ԣ���)
	    			if(this.kdtItems.getRow(i)!=null) {
		    			Object xfontSize = (Object)this.kdtItems.getRow(i).getCell("xFontSize").getValue();
		    			if(xfontSize==null) xfontSize = new Integer(0);
		    			if(xfontSize instanceof String)
		    				itemInfo.setXFontSize(Integer.parseInt((String)xfontSize));
		    			else if(xfontSize instanceof Integer)
		    				itemInfo.setXFontSize(((Integer)xfontSize).intValue());
	    			}
    			}
    			
    			ok = verifyDocumentItem(itemInfo,feekback,icCount,documentSubject.getSubjectType());
    		}
    	}
    	return ok;
    }
    
    private boolean verifyDocumentItem(DocumentItemInfo item,StringBuffer feedback,int allItemsCount,DocumentSubjectTypeEnum  subjectType){
    	boolean ok = true;
    	if(isStringNullOrBlank(item.getTopic())){
    		if(!(ok = allItemsCount==1)){
    			feedback.append("���ڶ�����飬�������������Ǳ������Ϣ��");
    		}
    	}else if(!(ok = isNumberBetween(8,80,item.getXFontSize()))){
			feedback.append("�����д������峬����Χ��8~80��");
		}else if(item.getTopic().length()>80){
			feedback.append("�����д�������������Χ��80��");
			ok = false;
		}
    	if(ok){
			DocumentOptionCollection oc = item.getOptions();
			int optionTopicIsNotNullCount = 0;
			for(int i=0 ; ok && i<oc.size() ; i++){
				DocumentOptionInfo oi = oc.get(i);
				ok = verifyDocumentOption(oi,feedback);
				if(!isStringNullOrBlank(oi.getTopic())){
					optionTopicIsNotNullCount++;
				}
			}
			if(ok){
				if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(subjectType) == 0 || DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL.compareTo(subjectType) == 0 ){
					//������⡢�������ֲ���ѡ�������ϵ�У��
				}else if(!( ok = (optionTopicIsNotNullCount>1))){
					feedback.append("����ѡ���⣬��Чѡ��������������1��");
    				}
    			}
    	}
    	return ok;
    }
    
    private boolean verifyDocumentOption(DocumentOptionInfo option,StringBuffer feedback){
    	boolean ok = true;
    	if(!(ok = (isStringNullOrBlank(option.getTopic()) || isNumberBetween(8,80,option.getXFontSize())))){
    		feedback.append("ѡ���д������峬����Χ��8~80��");
    	}else if(option.getTopic()!=null && option.getTopic().length()>80) {
    		feedback.append("ѡ���д�������������Χ��80��");
    		ok = false;
    	}
    	return ok;
    }
    
    private void trimDocumentSubject(DocumentSubjectInfo documentSubject){
/*    	if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(documentSubject.getSubjectType()) == 0){
    		documentSubject.getItems().clear();
    	}else*/
    	
    	if(true){
    		DocumentItemCollection ic = documentSubject.getItems();
    		for(int i=0 ; i<ic.size() ; i++){
    			DocumentItemInfo ii = ic.get(i);
    			ii.setItemNumber(i+1);
    			DocumentOptionCollection oc = ii.getOptions();
    			for(int j=0 ; j<oc.size() ; j++){
    				DocumentOptionInfo optio = oc.get(j);
    				if(isStringNullOrBlank(optio.getTopic()) && (oc.size()>1 || ic.size()>1)){
    					oc.removeObject(j);
    					j--;
    				}
/*    				else{
    					optio.setOptionNumber(j);
    				}*/
    			}
    			if(oc.size() == 0){
    				ic.removeObject(i);
    				i--;
    			}
    			
    			CRMHelper.sortCollection(oc, "optionNumber", true);
    		}
    	}
    }
    
    
    
    
    private boolean isStringNullOrBlank(String str){
    	return str == null || "".equals(str.trim());
    }
    
    private boolean isNumberBetween(int min,int max,int num){
    	return min<=num && max>=num;
    }
    

    protected void txtSubjectTopic_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.txtSubjectTopic_actionPerformed(e);
	}

	protected KDTable getDetailTable() {
		return this.kdtOptions;
	}
    
    
    

	protected ICoreBase getBizInterface() throws Exception {
		return DocumentItemFactory.getRemoteInstance();
	}
	

	protected void addLine_actionPerformed(ActionEvent e) throws Exception {
		addLine(kdtOptions);
        storeFields();
	}

	protected void btnNO_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	protected void deleLine_actionPerformed(ActionEvent e) throws Exception {
		this.removeLine(kdtOptions);
	}
	
	/**
	 * 
	 * ����������һ���µķ���
	 */
	protected void btnAddItem_actionPerformed(ActionEvent e) throws Exception {
		IObjectValue detailData = createNewDetailData(kdtItems);
        IRow row = kdtItems.addRow();
        //��������ڱ�kdtItems�����һ�еĵ��Ѹ���Ԫ�񣬿��Դ����¼�����һ��������б���
        this.kdtItems.getSelectManager().select(row.getRowIndex(),0);
        subjectDataBinder.loadLineFields(kdtItems, row, detailData);
        //
        //kdtItems.
        //
        setDataObject(detailData);
        dataBinder.loadFields();
        //
        storeFields();
        
        
        //this.actionAttachment.setVisible(false);
	}

	/**
	 * ɾ��һ������
	 */
	protected void btnDeleItem_actionPerformed(ActionEvent e) throws Exception {
		//��δ���
		if(kdtItems.getRowCount() > 1){
			removeLine(kdtItems);
		}else{
			MsgBox.showWarning("����ɾ��������Ҫ����һ�����飡");
		}
		
		
		
	}

	boolean hasDescription = false;//�Ƿ�����ѡ�������
	DocumentSubjectTypeEnum prevSubjectEnum = null;
	/**
	 * ѡ������֮��
	 */
	protected void comSubjectType_actionPerformed(ActionEvent e) throws Exception {
		DocumentSubjectTypeEnum currSubjectEnum = (DocumentSubjectTypeEnum)comSubjectType.getSelectedItem();
		if(prevSubjectEnum == null || currSubjectEnum.compareTo(prevSubjectEnum) != 0){
			prevSubjectEnum = currSubjectEnum;
			if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(currSubjectEnum) == 0){
				if(!hasDescription){
					descriptionText.setText(txtSubjectTopic.getText());
				}
				hasDescription = true;
				//���������ؼ�����ʾ������ˮƽ
				hideAndShowSomeing(false);
				this.documentSubject.setIsShowNumber(0);
				kDSeparator7.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.contDespLength.setVisible(true);
				this.contDespFontSize.setVisible(true);
				
			}else{
				//��ʾ�����ؼ�������������ˮƽ
				hideAndShowSomeing(true);
				this.comHorizonType.setSelectedItem(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
				this.documentSubject.setIsShowNumber(1);
				//src start
				kDSeparator7.setVisible(false);
				//src end
				this.kDLabelContainer1.setVisible(true);
				this.contDespLength.setVisible(false);
				this.contDespFontSize.setVisible(false);
			}			
		}
		

		this.kdtOptions.getColumn("xLength").getStyleAttributes().setHided(false);
		this.kdtOptions.getColumn("isTopicInverse").getStyleAttributes().setHided(false);		
		
	}
	/**
	 * 
	 */
	private void hideAndShowSomeing(boolean hideDescripAndHorizon){
		//����
		boolean self = !hideDescripAndHorizon;
		kdtOptions.setVisible(hideDescripAndHorizon);
		kdtItems.setVisible(hideDescripAndHorizon);
		deleLine.setVisible(hideDescripAndHorizon);
		kDLabel8.setVisible(hideDescripAndHorizon);
		//txtTopic.setVisible(hideDescripAndHorizon);
		//txtXFontSize.setVisible(hideDescripAndHorizon);
		//txtSubjectTopic.setVisible(hideDescripAndHorizon);
		//subjectFontSize.setVisible(hideDescripAndHorizon);
		//kDSeparator6.setVisible(hideDescripAndHorizon);
		btnAddItem.setVisible(hideDescripAndHorizon);
		btnDeleItem.setVisible(hideDescripAndHorizon);
		//comOptonAlignType.setVisible(hideDescripAndHorizon);
		//������ˮƽ
//		kDLabel6.setVisible(self);
		comHorizonType.setVisible(self);
		descriptionText.setVisible(self);
		//src start
		//addLine.setVisible(hideDescripAndHorizon);
		comVisable();//���ط���
		//src end
	}



	protected boolean isClosingWidOk() {
		return isClosingWidOk;
	}

	protected void setClosingWidOk(boolean isClosingWidOk) {
		this.isClosingWidOk = isClosingWidOk;
	}

	protected DataBinder getSubjectDataBinder() {
		return subjectDataBinder;
	}

	protected void setSubjectDataBinder(DataBinder subjectDataBinder) {
		this.subjectDataBinder = subjectDataBinder;
	}
    
    
    
    /////////// ������
    
    class SubjectSelectListener implements  KDTSelectListener{
		//���м���
		public void tableSelectChanged(KDTSelectEvent e) { 
			int currRow = -1;
			int currColu = -1;
			if(e.getSelectBlock()!=null){
				currRow = e.getSelectBlock().getTop();
				currColu = e.getSelectBlock().getLeft();
			}
			int prevRow = -1;
			int prevColu = -1;
			if(e.getPrevSelectBlock()!=null){
				prevRow = e.getPrevSelectBlock().getTop();
				prevColu = e.getPrevSelectBlock().getLeft();
			}
			
			SubjectViewSheUI.this.whenSubjectItemRowChanged(currRow,currColu,prevRow,prevColu);
		}
		
//		public void editStopping(KDTEditEvent e) {
//			int index=kdtOptions.getSelectManager().getActiveRowIndex();// wangxiaomin
//			if(kdtOptions.getRow(index).getCell("xLength").getValue() instanceof Integer){
//				int length=((Integer)kdtOptions.getRow(index).getCell("xLength").getValue()).intValue();
//				if(length>65536){
//					kdtOptions.getRow(index).getCell("xLength").setValue("");
//					MsgBox.showInfo("���ȳ�����");
//					return;
//				}
//			}else{
//				kdtOptions.getRow(index).getCell("xLength").setValue("");
//				MsgBox.showInfo("����ӦΪ������");
//				return ;
//			}
//		}
	}
    
    class SubjectEditListener implements KDTEditListener{

		public void editStarting(KDTEditEvent e) {
			// TODO �Զ����ɷ������
		}

		public void editStarted(KDTEditEvent e) {
			// TODO �Զ����ɷ������
		}

		public void editValueChanged(KDTEditEvent e) {
		}

		public void editStopping(KDTEditEvent e) {
		}

		public void editStopped(KDTEditEvent e) { 
			int assTypeColIndex = SubjectViewSheUI.this.kdtItems.getColumn("assisType").getColumnIndex(); 
				if(assTypeColIndex>=0 && e.getColIndex()==assTypeColIndex){
				SellProjectInfo thisProInfo = (SellProjectInfo)SubjectViewSheUI.this.prmtSellPro.getValue();
				SubjectViewSheUI.this.kdtOptions.removeRows();
				IRow thisRow = SubjectViewSheUI.this.kdtItems.getRow(e.getRowIndex());
				DocumentItemInfo objInfo = (DocumentItemInfo)thisRow.getUserObject();
				SHECusAssistantDataGroupInfo assisTypeInfo = (SHECusAssistantDataGroupInfo)thisRow.getCell(assTypeColIndex).getValue();
				if(assisTypeInfo!=null) {
					thisRow.getCell(assTypeColIndex+1).setValue(assisTypeInfo.getName());
					objInfo.setRelatTypeId(assisTypeInfo.getId().toString());
					try {
						SHECusAssistantDataCollection asisColl = SHECusAssistantDataFactory.getRemoteInstance()
											.getSHECusAssistantDataCollection("where group.id = '"+assisTypeInfo.getId()+"' " +
													(thisProInfo==null?"":"and (project.id is null or project.id = '"+thisProInfo.getId()+"') ") );
						for(int i=0;i<asisColl.size();i++) {
							SHECusAssistantDataInfo assisInfo = asisColl.get(i);
							SubjectViewSheUI.this.addLine(kdtOptions);
							IRow addNewRow = SubjectViewSheUI.this.kdtOptions.getRow(i); 
							addNewRow.getCell("topic").setValue(assisInfo.getName());
							DocumentOptionInfo newOptionInfo = (DocumentOptionInfo)addNewRow.getUserObject();
							newOptionInfo.setRelationId(assisInfo.getId().toString());
							newOptionInfo.setItemId(objInfo);
						}
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
				}else{
					thisRow.getCell(assTypeColIndex+1).setValue(null);
					objInfo.setRelatTypeId(null);
				}
			}
			
			//SubjectViewSheUI.this.whenSubjectItemCellEdited(e.getRowIndex(),e.getColIndex());
		}

		public void editCanceled(KDTEditEvent e) {
			// TODO �Զ����ɷ������
		}}


    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   

    protected void combDocItemType_itemStateChanged(ItemEvent e)
    		throws Exception {
    	DocSubItemTypeEnum itemTypeEnum = (DocSubItemTypeEnum)this.combDocItemType.getSelectedItem();
    	if(DocSubItemTypeEnum.Manual.equals(itemTypeEnum)){//������ֹ��Ļ�
    		this.contSelPro.setVisible(false);
    		this.addLine.setEnabled(true);
    		//this.deleLine.setEnabled(true);
    		this.kdtItems.getColumn("assisType").getStyleAttributes().setHided(true);
    		this.kdtItems.getColumn("topic").getStyleAttributes().setLocked(false);
    		this.kdtOptions.getColumn("topic").getStyleAttributes().setLocked(false);
    		this.comSubjectType.setEnabled(true);
    		this.kdtOptions.getColumn("xLength").getStyleAttributes().setHided(false);
    		this.kdtOptions.getColumn("isTopicInverse").getStyleAttributes().setHided(false);    		
    	}else{    		
    		this.contSelPro.setVisible(true);   
    		this.prmtSellPro.setEnabled(true);
    		this.prmtSellPro.setRequired(true);
    		this.addLine.setEnabled(false);
    		//this.deleLine.setEnabled(false);
    		this.kdtItems.getColumn("assisType").getStyleAttributes().setHided(false);
    		this.kdtItems.getColumn("topic").getStyleAttributes().setLocked(true);
    		this.kdtOptions.getColumn("topic").getStyleAttributes().setLocked(true);
    		this.comSubjectType.setSelectedItem(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
    		this.comSubjectType.setEnabled(false);
    		this.kdtOptions.getColumn("xLength").getStyleAttributes().setHided(true);
    		this.kdtOptions.getColumn("isTopicInverse").getStyleAttributes().setHided(true);
    	}
    	
    	this.kdtItems.removeRows();
    	
    	this.kdtOptions.removeRows();
    	super.combDocItemType_itemStateChanged(e);
    }
    
    protected void prmtSellPro_dataChanged(DataChangeEvent e) throws Exception {
    	//�ڷ���������Զ�������ǰѡ����Ŀ�µĿͻ������������
    	
    }
    

//    protected void kdtOptions_editStopping(KDTEditEvent e) throws Exception {
//		   int index=kdtOptions.getSelectManager().getActiveRowIndex();// wangxiaomin
//		   if(null!=kdtOptions.getRow(index).getCell("xLength").getValue()){
//			   try{
//				   int length=Integer.parseInt((String)kdtOptions.getRow(index).getCell("xLength").getValue());
//				  
//				   if(length>10000||length<0){
//						MsgBox.showInfo("���ȳ�����");
//						return;
//					}
//				   }catch(Exception nfe){
//					   Integer in=(Integer)kdtOptions.getRow(index).getCell("xLength").getValue();
//					   int i=in.intValue();
//					   if(i==0){
//						 
//					   }else{
//						   MsgBox.showInfo("����ӦΪ������111111��");
//					   }
						
//				   }
//		   }else{
//			   MsgBox.showInfo("����ӦΪ��������");
//		   }
//	}


	public void actionSure_actionPerformed(ActionEvent e) throws Exception {//ȷ�ϵ�ʱ��
        	super.actionSure_actionPerformed(e);
        	
        	storeFields();
        	
        	if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(comSubjectType.getSelectedItem()) == 0){        		
        		//�������ͬ�ڣ� 1��������⣬�ұ���=�������ݣ���Ӧһ������ѡ�����ѡ�����Ϊ��
        		String despString = this.descriptionText.getText().trim();
        		DocumentItemInfo itemInfo = new DocumentItemInfo();
        		itemInfo.setXFontSize(12);
        		itemInfo.setItemNumber(1);
        		itemInfo.setTopic(despString);
        		DocumentOptionInfo optionInfo = new DocumentOptionInfo();
        		optionInfo.setXFontSize(this.txtDespFontSize.getIntegerValue().intValue());
        		optionInfo.setXLength(this.txtDespLength.getIntegerValue().intValue());
        		optionInfo.setItemId(itemInfo);
        		optionInfo.setOptionNumber(1);
        		optionInfo.setXFontName("Dialog");
        		itemInfo.getOptions().add(optionInfo);
        		itemInfo.setSubjectId(documentSubject);        		
        		documentSubject.getItems().clear();
        		documentSubject.getItems().add(itemInfo);
        		documentSubject.setTopic(despString);
        		documentSubject.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
        	}
        	
        	DocSubItemTypeEnum docItemType = (DocSubItemTypeEnum)this.combDocItemType.getSelectedItem();
        	if(docItemType.equals(DocSubItemTypeEnum.Relation)) {	//������������ʱ����Ŀ����ѡ��
        		if(this.prmtSellPro.getValue()==null){
        			FDCMsgBox.showWarning("��Ŀ����ѡ��");
        			this.abort();
        		}
        	}
        	
        	//��Ҫ���м�飬���ݵ��Ƿ�������
        	//���ⲻ��Ϊ�յ�
    		StringBuffer message = new StringBuffer();
    		if(!verifyDocumentSubject(documentSubject,message)){
    			MsgBox.showWarning(message.toString());
    		}else{
    			trimDocumentSubject(documentSubject);
    			isClosingWidOk = true;
    			this.getUIWindow().close();
        	}
    		logger.debug(documentSubject.getTopic());
            logger.debug(documentSubject.getSubjectType());
            logger.debug(String.valueOf(documentSubject.getXFontSize()));
            logger.debug(documentSubject.getXFontName());
            logger.debug(documentSubject.getAlignType());
            logger.debug(documentSubject.getHorizontalAlign());
        	for(int i=0 ;i<documentSubject.getItems().size() ;i ++){
        		DocumentItemInfo ic = documentSubject.getItems().get(i);
        		logger.debug(" " + ic.getTopic());
        		for(int j=0 ; j<ic.getOptions().size() ;j++){
        			logger.debug("   " + ic.getOptions().get(j).getTopic());
        		}
        	}        	
    }

    
    
}