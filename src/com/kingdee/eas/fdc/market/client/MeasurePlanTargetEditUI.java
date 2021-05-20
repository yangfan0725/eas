/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasurePhaseCollection;
import com.kingdee.eas.fdc.basedata.MeasurePhaseFactory;
import com.kingdee.eas.fdc.basedata.MeasurePhaseInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.VerifyInputUtil;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetEntryCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetEntryInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.VersionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MeasurePlanTargetEditUI extends AbstractMeasurePlanTargetEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasurePlanTargetEditUI.class);
    
    private int houseCount = 0;
    private int publicBuildCount = 0;
    private int parkingCount = 0;
    private int businessCount = 0;
    
    /**
     * output class constructor
     */
    public MeasurePlanTargetEditUI() throws Exception
    {
        super();
    }

    MeasurePlanTargetEntryCollection MPTargetCollection =null;
    ProductTypeInfo productTypeInfo_sub = null;
    final static String HJ_NAME = "�ϼ�";
    //wyh
    final static String XJ_NAME = "С��";
    public void onLoad() throws Exception {
    	initkdtEntryTable();
		super.onLoad();
		this.setPreferredSize(getMaximumSize());
		initUI();
	}
    
    public void initkdtEntryTable(){
    	kdtEntry.checkParsed();
    	MarketHelpForSec.changeTableNumberFormat(kdtEntry, new String[]{"acreage","sumAmount","price"});
		//�ϣ����£���
		for(int i = 0;i<kdtEntry.getColumnCount();i++){
			if(i==0){
				//��Ʒ����
				KDTextField textField=new  KDTextField();
				kdtEntry.getColumn(i).setEditor(new KDTDefaultCellEditor(textField));
				kdtEntry.getColumn(i).getStyleAttributes().setLocked(true);
			}else if(i==1){
				//��Ʒ���� F7
				String queryName = "com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery";
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				view.setFilter(filter);
				ICellEditor f7Editor = new KDTDefaultCellEditor(TableUtils.getF7productType(queryName,view));
				kdtEntry.getColumn(i).setEditor(f7Editor);
			}else if(i==2){
				//wyh �����
				kdtEntry.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
				KDBizPromptBox f7productType = new KDBizPromptBox();
		    	EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				view.setFilter(filter);
				f7productType.setQueryInfo("com.kingdee.eas.fdc.market.app.AreaSetQuery");
				f7productType.setEntityViewInfo(view);
				f7productType.setEditable(true);
				f7productType.setDisplayFormat("$description$");
				f7productType.setEditFormat("$number$");
				f7productType.setCommitFormat("$number$");
		    	ICellEditor f7Area = new KDTDefaultCellEditor(f7productType);
		    	kdtEntry.getColumn("newAreaRange").setEditor(f7Area);
			}else if(i==4){
				//���� ����
				ICellEditor integerEditor = TableUtils.getCellIntegerNumberEdit();
				kdtEntry.getColumn(i).setEditor(integerEditor);
				kdtEntry.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}else {
				//���  ���� ��� bigdecimal
				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
				kdtEntry.getColumn(i).setEditor(bigDecimalEditor);
				kdtEntry.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntry.getColumn("sumAmount").getStyleAttributes().setLocked(true);
    	kdtEntry.getColumn("productType").setRequired(true);
    }
    
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("entry.*");
    	sel.add("entry.areaRange");
    	sel.add("entry.productType.*");
    	sel.add("entry.newAreaRange.*");
    	sel.add("CU.*");
    	return sel;
    }
    public void initUI(){
    	pkCreateTime.setEnabled(false);
    	btnInsertRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
    	btnRemoveRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		
		contMeasureType.setEnabled(false);
		comboState.setEnabled(false);
		contProjectAgin.setEnabled(false);
		contVersions.setEnabled(false);
		
		txtNumber.setRequired(true);
		prmtOrgUnit.setRequired(true);
		prmtMeasurePhases.setRequired(true);
		String version = txtVersions.getText();
		if(version!=null){
			int index = version.indexOf(".");
			int v = Integer.parseInt(version.substring(index+1));
			if(v!=0){
				txtAdjustCause.setRequired(true);
//				versionType.setEnabled(false);
			}
		}
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		
		this.actionAddNew.setVisible(false);
    }
    public void loadFields() 
    {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		setAuditButtonStatus(this.getOprtState());
		loadkdtEntryTable();
		
		attachListeners();
	}
    
	protected boolean isContinueAddNew() {
		return false;
	}
	public void storeFields()
    {
		this.setExpNull();
		for(int i=kdtEntry.getRowCount()-1;i>=0;i--){
			if(publicBuildCount > 2){
				if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.publicBuild) && 
						kdtEntry.getRow(i).getCell("productType").getValue() == null){
					kdtEntry.removeRow(i);
					publicBuildCount --;
					continue;
				}
			}
			if(parkingCount > 2){
				if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.parking) && 
						kdtEntry.getRow(i).getCell("productType").getValue() == null){
					kdtEntry.removeRow(i);
					parkingCount --;
					continue;
				}
			}
			if(businessCount > 2){
				if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.business) && 
						kdtEntry.getRow(i).getCell("productType").getValue() == null){
					kdtEntry.removeRow(i);
					businessCount --;
					continue;
				}
			}
			if(houseCount > 2){
				if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house) && 
						kdtEntry.getRow(i).getCell("productType").getValue() == null &&
						kdtEntry.getRow(i).getCell("newAreaRange").getValue() == null){
					kdtEntry.removeRow(i);
					houseCount --;
					continue;
				}
			}
		}
		this.reSetExpressions();
		
		
        super.storeFields();
        storeEntry();
        IRow footRow = null;
        KDTFootManager footRowManager = kdtEntry.getFootManager();
        footRow = footRowManager.getFootRow(0);
        txtTotalAmount.setValue(footRow.getCell("sumAmount").getValue());
        if(footRow.getCell("sumAmount").getValue() != null){
            editData.setTotalAmount(new BigDecimal(footRow.getCell("sumAmount").getValue().toString()));
        }
    }
    //���¼ֵ
    public void storeEntry(){
    	editData.getEntry().clear();
    	for (int i=0; i<kdtEntry.getRowCount();i++){
    		IRow row = kdtEntry.getRow(i);
    		if(row.getCell("productType").getValue() instanceof String)
    			continue ;
    		if(row.getCell("newAreaRange").getValue() != null)
    			if(row.getCell("newAreaRange").getValue().equals("С��"))
    				continue ;
    		MeasurePlanTargetEntryInfo entry = new MeasurePlanTargetEntryInfo();
    		if(row.getCell("id").getValue()!=null){
    			String id = (String)row.getCell("id").getValue();
    			entry.setId(BOSUuid.read(id));
    		}
    		entry.setProductConstitute((PlanIndexTypeEnum)row.getCell("productConstitute").getValue());
    		entry.setProductType((ProductTypeInfo)row.getCell("productType").getValue());
    		if(row.getCell("acreage").getValue()!=null){
    			entry.setAcreage(new BigDecimal(row.getCell("acreage").getValue().toString()));
    		}
    		if(row.getCell("quantity").getValue()!=null){
    			entry.setQuantity(Integer.parseInt(row.getCell("quantity").getValue().toString()));
    		}
    		if(row.getCell("price").getValue()!=null && !row.getCell("price").getValue().equals("#DIV0 ����Ϊ��")){
    			entry.setPrice(new BigDecimal(row.getCell("price").getValue().toString()));
    		}
    		if(row.getCell("sumAmount").getValue()!=null){
    			entry.setSumAmount(new BigDecimal(row.getCell("sumAmount").getValue().toString()));
    		}
    		if(row.getCell("newAreaRange").getValue() instanceof AreaSetInfo){
    			Object obj = row.getCell("newAreaRange").getValue();
    			entry.setNewAreaRange((AreaSetInfo)obj);
    		}
    		entry.setParent(editData);
    		editData.getEntry().add(entry);
    	}
    }
    public void loadkdtEntryTable(){
    	kdtEntry.removeRows();
    	if("V1.0".equals(editData.getVersions()) && editData.getEntry()!=null && editData.getEntry().size()==0){//������ʼ�����
    		//���ò�Ʒ����
    		for(int i=0;i<4;i++){
    			IRow row = kdtEntry.addRow();
    			if(i==0){
    				row.getCell("productConstitute").setValue(PlanIndexTypeEnum.house);
    			}
    			if(i==1 ){
    				row.getCell("productConstitute").setValue(PlanIndexTypeEnum.business);
    			}
    			if(i==2 ){
    				row.getCell("productConstitute").setValue(PlanIndexTypeEnum.parking);
    			}
    			if(i==3 ){
    				row.getCell("productConstitute").setValue(PlanIndexTypeEnum.publicBuild);
    			}
    		}
    	}else{//�����ݳ�ʼ�����
    		MeasurePlanTargetEntryCollection coll = editData.getEntry();
    		CRMHelper.sortCollection(coll, "seq", true);
    		for(int i=0;coll!=null && i<coll.size();i++){
    			MeasurePlanTargetEntryInfo entry = coll.get(i);
				IRow row = kdtEntry.addRow();
				if(OprtState.ADDNEW.equals(oprtState))
					row.getCell("id").setValue(null);
				else
					row.getCell("id").setValue(entry.getId().toString());
				row.setUserObject(entry);
    			row.getCell("productConstitute").setValue(entry.getProductConstitute());
    			row.getCell("productType").setValue(entry.getProductType());
    			row.getCell("newAreaRange").setValue(entry.getNewAreaRange());
    			row.getCell("acreage").setValue(entry.getAcreage());
    			row.getCell("quantity").setValue(new Integer(entry.getQuantity()));
    			row.getCell("price").setValue(entry.getPrice());
    			row.getCell("sumAmount").setValue(entry.getSumAmount());
    		}
    	}
    	MarketHelpForSec.setTotalRow(kdtEntry);
    	reSetExpressions();
    }
    private void setExpNull(){
    	kdtEntry.getScriptManager().removeAll();
    }
    private void reSetExpressions(){
    	houseCount = 0;
    	publicBuildCount = 0;
    	parkingCount = 0;
    	businessCount = 0;
		//�õ�סլС�Ƶ��к�;
    	int hjColumn = 3;//wyh
    	int dynRowBase = 0;
		int houseSubIndex=0;
		int businessSubIndex=0;
		int parkingSubIndex=0;
		int publicSubIndex=kdtEntry.getRowCount()-1;
		//С������
		HashMap XJ_MAP = new HashMap();
		int num = 0;
		
		for(int i=0;i<kdtEntry.getRowCount()-1;i++){
			IRow row=kdtEntry.getRow(i);
			//��ȡС���к�
			if(row.getCell(dynRowBase).getValue()==PlanIndexTypeEnum.house && row.getCell("newAreaRange").getValue() instanceof String){
				if(row.getCell("newAreaRange").getValue().equals(XJ_NAME)){
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					XJ_MAP.put(String.valueOf(num),String.valueOf(i));
					num++;
				}
			}
			if(row.getCell(dynRowBase).getValue()==PlanIndexTypeEnum.house && isSubTotalRow(row)){
				houseSubIndex=i;
			}
			if(row.getCell(dynRowBase).getValue()==PlanIndexTypeEnum.business && isSubTotalRow(row)){
				businessSubIndex=i;
			}
			if(row.getCell(dynRowBase).getValue()==PlanIndexTypeEnum.parking && isSubTotalRow(row)){
				parkingSubIndex=i;
			}
		}
		//סլ
		if(houseSubIndex>dynRowBase){
			IRow houseRow = null;;
			//С��ֵ����
			for(int i=0;i<XJ_MAP.size();i++){
				int rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString());
				int old_rowNum = 1;
				if(i>0){
					old_rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i-1)).toString()) + 2;
				}
				houseRow=kdtEntry.getRow(rowNum);
				for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
					if(j==5){
						String exp=null;
						exp="="+"(G"+(rowNum+1)+"/"+"D"+(rowNum+1)+")";
						houseRow.getCell(j).setExpressions(exp);
						houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					char c=(char)('A'+j);
					String exp=null;
					exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
					houseRow.getCell(j).setExpressions(exp);
				}
			}
			houseRow=kdtEntry.getRow(houseSubIndex);
			//��С�������ݺϼƼ���
			if(XJ_MAP.size() > 0){
				for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
					if(j==5){
						String exp=null;
						exp="="+"(G"+(houseSubIndex+1)+"/"+"D"+(houseSubIndex+1)+")";
						houseRow.getCell(j).setExpressions(exp);
						houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					char c=(char)('A'+j);
					String exp="=";
					for(int i=0;i<XJ_MAP.size();i++){
						exp = exp+c+(Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString())+1)+"+";
					}
					houseRow.getCell(j).setExpressions(exp.substring(0, exp.length()-1));
				}
			}else{
				for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
					if(j==5){
							String exp=null;
							exp="="+"(G"+(houseSubIndex+1)+"/"+"D"+(houseSubIndex+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					char c=(char)('A'+j);
					String exp=null;
					exp="=sum("+c+(1)+":"+c+(houseSubIndex)+")";
					houseRow.getCell(j).setExpressions(exp);
				}
			}
		}
		//��ҵ
		if(businessSubIndex>(houseSubIndex+1)){
			IRow businessRow=kdtEntry.getRow(businessSubIndex);
			for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
				if(j==5){
//					Long area = (Long)businessRow.getCell("acreage").getValue();
//					if(area!=null && area.intValue()!=0){ //���� �������Ϊ0
						String exp=null;				
						exp="="+"(G"+(businessSubIndex+1)+"/"+"D"+(businessSubIndex+1)+")";
						businessRow.getCell(j).setExpressions(exp);
						businessRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					}else{
//						businessRow.getCell(j).setValue(FDCHelper.ZERO);
//					}
					continue;
				}
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(houseSubIndex+2)+":"+c+(businessSubIndex)+")";
				businessRow.getCell(j).setExpressions(exp);
			}
		}
		//ͣ��
		if(parkingSubIndex>(businessSubIndex+1)){
			IRow parkRow=kdtEntry.getRow(parkingSubIndex);
			for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
				if(j==5){
//					Long area = (Long)parkRow.getCell("quantity").getValue();
//					if(area!=null && area.intValue()!=0){ //���� �������Ϊ0
						String exp=null;				
						exp="="+"(G"+(parkingSubIndex+1)+"/"+"E"+(parkingSubIndex+1)+")";
						parkRow.getCell(j).setExpressions(exp);
						parkRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					}else{
//						parkRow.getCell(j).setValue(FDCHelper.ZERO);
//					}
					continue;
				}
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(businessSubIndex+2)+":"+c+(parkingSubIndex)+")";
				parkRow.getCell(j).setExpressions(exp);
			}
		}
		//����
		if(publicSubIndex>(parkingSubIndex+1)){
			IRow publicRow=kdtEntry.getRow(publicSubIndex);
			for(int j=hjColumn;j<kdtEntry.getColumnCount()-1;j++){
				if(j==5){
//					Long area = (Long)publicRow.getCell("acreage").getValue();
//					if(area!=null && area.intValue()!=0){ //���� �������Ϊ0
						String exp=null;				
						exp="="+"(G"+(publicSubIndex+1)+"/"+"D"+(publicSubIndex+1)+")";
						publicRow.getCell(j).setExpressions(exp);
						publicRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					}else{
//						publicRow.getCell(j).setValue(FDCHelper.ZERO);
//					}
					continue;
				}
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(parkingSubIndex+2)+":"+c+(publicSubIndex)+")";
				publicRow.getCell(j).setExpressions(exp);
			}
		}
		getRowSum();
		for(int i=0;i<kdtEntry.getRowCount();i++){
			if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.parking)){
				kdtEntry.getRow(i).getCell("newAreaRange").getStyleAttributes().setLocked(true);
				parkingCount ++;
			}
			else if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.business)){
				kdtEntry.getRow(i).getCell("newAreaRange").getStyleAttributes().setLocked(true);
				businessCount ++;
			}
			else if(kdtEntry.getRow(i).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.publicBuild)){
				kdtEntry.getRow(i).getCell("newAreaRange").getStyleAttributes().setLocked(true);
				publicBuildCount ++;
			}
			else{
				houseCount ++;
			}
		}
	}
    private boolean isSubTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(1).getValue()!=null&&row.getCell(1).getValue().equals("�ϼ�")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
    
    /*������*/
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		this.kdtEntry.addRow();
		reSetExpressions();
	}
	/*������*/
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		this.insertLine("addNew");
	}
	
	//wyh �����з�������
	public void insertLine(String value){
		if(kdtEntry == null)
			return;
        IRow row = null;
        if(kdtEntry.getSelectManager().size() > 0)
        {    
            int top = kdtEntry.getSelectManager().get().getTop();
            //wyh סլ���ںϼ���������һ�м�¼,סլ�����б����ѡ��һ�в���������
            int bottom = kdtEntry.getSelectManager().get().getBottom();
            int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
            Object productType = kdtEntry.getRow(rowIndex).getCell("productType").getValue();
            if(value.equals("addNew")){
                Object productConstitutes = kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue();
        		if(productConstitutes.equals(PlanIndexTypeEnum.house)){
                	if(kdtEntry.getSelectManager().getActiveColumnIndex() > 0){
                		FDCMsgBox.showInfo("סլ�����б����ѡ��һ��!");
                		SysUtil.abort();
                	}
                }
            }
    		if(productType!=null){//��Ʒ���Ͳ�Ϊ��
    			productType = kdtEntry.getRow(rowIndex).getCell("productType").getValue().toString();
    			if(HJ_NAME.equals(productType)){//��Ʒ���͵ĺϼ���
        			row = kdtEntry.addRow(top+1);
    				PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)kdtEntry.getRow(top-1).getCell("productConstitute").getValue();    
	                row.getCell("productConstitute").setValue(productConstitute);
    			}else{
    				if(kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house) && value.equals("addNew")){
        				row = kdtEntry.addRow(bottom);
        			}else{
        				row = kdtEntry.addRow(top+1);
        			}
    				PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)kdtEntry.getRow(top).getCell("productConstitute").getValue();    
	                row.getCell("productConstitute").setValue(productConstitute);
    			}
    		}
    		else if(productType==null){//��Ʒ����Ϊ��
    			//wyh �����Ʒ������סլ���ںϼ���������һ�м�¼
    			if(kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house) && value.equals("addNew")){
    				row = kdtEntry.addRow(bottom);
    			}else{
    				row = kdtEntry.addRow(top+1);
    			}
	            PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)kdtEntry.getRow(top).getCell("productConstitute").getValue();    
	            row.getCell("productConstitute").setValue(productConstitute);
    		}
        } else
        {
//            row = kdtEntry.addRow();
        	MsgBox.showInfo("��ѡ��ĳһ��Ʒ���������У�");
			SysUtil.abort();
        }
        reSetExpressions();
	}
	
	/**
	 * ���ܼ�
	 * */
	private void getRowSum(){
		MarketHelpForSec.getAmtFootRow_Measure(kdtEntry, new String[]{"acreage","quantity","price","sumAmount"});
		IRow footRow = null;
        KDTFootManager footRowManager = kdtEntry.getFootManager();
        footRow = footRowManager.getFootRow(0);
        BigDecimal sumAmount = FDCHelper.ZERO;
        BigDecimal acreage = FDCHelper.ZERO;
        if(footRow.getCell("sumAmount").getValue()!=null)
        	sumAmount = (BigDecimal)footRow.getCell("sumAmount").getValue();
        if(footRow.getCell("acreage").getValue()!=null)
        	acreage = (BigDecimal)footRow.getCell("acreage").getValue();
        if(acreage.intValue()!=0)
        	footRow.getCell("price").setValue(sumAmount.divide(acreage,4,2));
        txtTotalAmount.setValue(footRow.getCell("sumAmount").getValue());
        footRow.getCell("sumAmount").getStyleAttributes().setFontColor(Color.RED);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		IRow footRow = null;
        KDTFootManager footRowManager = kdtEntry.getFootManager();
        footRow = footRowManager.getFootRow(0);
        txtTotalAmount.setValue(footRow.getCell("sumAmount").getValue());
        if(footRow.getCell("sumAmount").getValue() != null){
            editData.setTotalAmount(new BigDecimal(footRow.getCell("sumAmount").getValue().toString()));
        }
        footRow.getCell("sumAmount").getStyleAttributes().setFontColor(Color.RED);
	}

	/**
	 * �жϸ����ܷ�ɾ��
	 * */
	public void isCanRemoveRow(){
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		int num =0;
		if(rowIndex!=-1){
			PlanIndexTypeEnum planenum =(PlanIndexTypeEnum)kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue();
			num = sameNum(planenum);
			if(num<=2){
//				MsgBox.showInfo("����ɾ�����һ�У�");
				kdtEntry.getRow(rowIndex).getCell("productType").setValue(null);
				kdtEntry.getRow(rowIndex).getCell("newAreaRange").setValue(null);
				kdtEntry.getRow(rowIndex).getCell("acreage").setValue(FDCHelper.ZERO);
				kdtEntry.getRow(rowIndex).getCell("quantity").setValue(FDCHelper.ZERO);
				kdtEntry.getRow(rowIndex).getCell("price").setValue(FDCHelper.ZERO);
				kdtEntry.getRow(rowIndex).getCell("sumAmount").setValue(FDCHelper.ZERO);
				SysUtil.abort();
			}
			if(kdtEntry.getRow(rowIndex).getCell("productType").getValue()!=null){
				String productType = kdtEntry.getRow(rowIndex).getCell("productType").getValue().toString();
				if(HJ_NAME.equals(productType)){
					MsgBox.showInfo("����ɾ���ϼ��У�");
					SysUtil.abort();
				}
			}
		}
		else{
			MsgBox.showInfo("��ѡ����ɾ����");
			SysUtil.abort();
		}
	}
	/**
	 * ��ȡ��Ʒ��������
	 * */
	public int sameNum(PlanIndexTypeEnum planenum){
		int  planIndexRowCount =0;
		for(int i = 0 ; i < kdtEntry.getRowCount() ; i ++){
			IRow row = kdtEntry.getRow(i);
			PlanIndexTypeEnum planEnum = (PlanIndexTypeEnum)row.getCell("productConstitute").getValue();
			if(planEnum!=null&&planEnum.equals(planenum)){
				planIndexRowCount = planIndexRowCount +1;
			}
		}
		return planIndexRowCount;
	}
	/**
	 * �жϸò�Ʒ�����Ƿ��ѱ�ѡ��
	 * */
	public void isCanSelectProductType(int rowIndex){
		String productTypeId = "";
		AreaSetInfo areaInfo = new AreaSetInfo();
		String areaId = "";
//		boolean isAdd = true;
		ProductTypeInfo productTypeInfo = (ProductTypeInfo)kdtEntry.getRow(rowIndex).getCell("productType").getValue();
		if(kdtEntry.getRow(rowIndex).getCell("newAreaRange").getValue() instanceof AreaSetInfo){
			areaInfo = (AreaSetInfo)kdtEntry.getRow(rowIndex).getCell("newAreaRange").getValue();
			areaId = areaInfo.getId().toString();
		}
		if(productTypeInfo!=null){
			productTypeId = productTypeInfo.getId().toString();//�༭�еĲ�Ʒ����ID
			for(int i=0;i<kdtEntry.getRowCount();i++){
				if(i!=rowIndex){//���뵱ǰ�༭�бȽ�
					if(kdtEntry.getRow(i).getCell("productType").getValue()!=null 
							&& kdtEntry.getRow(i).getCell("productType").getValue() instanceof ProductTypeInfo){
						productTypeInfo = (ProductTypeInfo)kdtEntry.getRow(i).getCell("productType").getValue();
						//�Ƚϵ�ǰ�Ĳ�Ʒ�����Ƿ�һ��
						if(productTypeId.equals(productTypeInfo.getId().toString())){
							//��Ʒ����Ϊסլ
							if(kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house)){
								//wyh 
								//��Ʒ�����Ѿ�ѡ�����������Ҳ�Ѿ�ѡ��������ʾ��Ϣ
								if(kdtEntry.getRow(i).getCell("newAreaRange").getValue() instanceof AreaSetInfo){
									areaInfo = (AreaSetInfo)kdtEntry.getRow(i).getCell("newAreaRange").getValue();
									if(areaId.equals(areaInfo.getId().toString())){
										MsgBox.showInfo("�ò�Ʒ�����ѱ�ѡ��,��Ʒ���Ͳ����ظ���");
										setExpNull();
										kdtEntry.removeRow(rowIndex);
										this.reSetExpressions();
									}
								}
			    			}else{
			    				MsgBox.showInfo("�ò�Ʒ�����ѱ�ѡ��,��Ʒ���Ͳ����ظ���");
			    				kdtEntry.getRow(rowIndex).getCell("productType").setValue(null);
			    			}
							SysUtil.abort();
						}
					}
				}
			}
		}
	}
	
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			int houseCount = 0;
			for(int i=0;i<table.getRowCount();i++){
				if(!table.getRow(i).getCell(0).getValue().equals(PlanIndexTypeEnum.house)){
					break;
				}
				houseCount++;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				int rowIndex_1 = rowIndex + 1;
				if(kdtEntry.getRow(rowIndex_1).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house) && houseCount == indexArr.length+1){
					if(kdtEntry.getRow(rowIndex_1).getCell("productConstitute").getValue() != null && kdtEntry.getRow(rowIndex_1).getCell("productType").getValue() != null){
						if(kdtEntry.getRow(rowIndex_1).getCell("productConstitute").getValue().equals(PlanIndexTypeEnum.house) && kdtEntry.getRow(rowIndex_1).getCell("productType").getValue().equals("�ϼ�") && rowIndex == 0){
							IRow row = kdtEntry.getRow(rowIndex);
							row.getCell("productType").setValue(null);
							row.getCell("productType").getStyleAttributes().setLocked(false);
							row.getCell("newAreaRange").setValue(null);
							row.getCell("acreage").setValue(FDCHelper.ZERO);
							row.getCell("acreage").getStyleAttributes().setLocked(false);
							row.getCell("quantity").setValue(FDCHelper.ZERO);
							row.getCell("quantity").getStyleAttributes().setLocked(false);
							row.getCell("price").setValue(FDCHelper.ZERO);
							row.getCell("price").getStyleAttributes().setLocked(false);
							row.getCell("sumAmount").setValue(FDCHelper.ZERO);
							row.getCell("sumAmount").getStyleAttributes().setLocked(false);
							row.getCell("productConstitute").setValue(PlanIndexTypeEnum.house);
							row.getStyleAttributes().setBackground(null);
						}
						else{
							table.removeRow(rowIndex);
						}
					}
					else{
						table.removeRow(rowIndex);
					}
				}
				else{
					table.removeRow(rowIndex);
				}
			}
			int rowCount = 0;
			for(int i=0;i<table.getRowCount();i++){
				if(!table.getRow(i).getCell(0).getValue().equals(PlanIndexTypeEnum.house)){
					break;
				}
				rowCount++;
			}
			if(rowCount == 2){
				table.getMergeManager().mergeBlock(0, 0, 1, 0);
			}
		}
	}
	
	protected void setAuditButtonStatus(String oprtType){
		super.setAuditButtonStatus(oprtType);
		 if(STATUS_VIEW.equals(oprtType)){
			 this.btnInsertRow.setEnabled(false);
			 this.btnRemoveRow.setEnabled(false);
		 }else{
			 this.btnInsertRow.setEnabled(true);
			 this.btnRemoveRow.setEnabled(true);
		 }
	}
	/*ɾ����*/
	public void actionRemoveLine_actionPerformed(ActionEvent arg0)
			throws Exception {
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		Object productConstitutes = kdtEntry.getRow(rowIndex).getCell("productConstitute").getValue();
//        if(productConstitutes.equals(PlanIndexTypeEnum.house)){
//        	if(kdtEntry.getSelectManager().getActiveColumnIndex() > 1 || kdtEntry.getSelectManager().getActiveColumnIndex() < 1){
//        		FDCMsgBox.showInfo("סլɾ���б����ѡ�ڶ���!");
//        		SysUtil.abort();
//        	}
//        }
		isCanRemoveRow();
		setExpNull();
		removeLine(this.kdtEntry);
		reSetExpressions();
		//ɾ���к��ٴμ����ܻ�ֵ=�������*���۾���
	}
	
	//�༭ǰȡ�� wyh
	protected void kdtEntry_editStarted(KDTEditEvent e) throws Exception {
		//���༭ǰ�����ݱ��浽ȫ�ֱ�����
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		int cellIndex = kdtEntry.getSelectManager().getActiveColumnIndex();
		if(cellIndex == 1){
			productTypeInfo_sub = (ProductTypeInfo)kdtEntry.getRow(rowIndex).getCell("productType").getValue();
		}
	}

	//�ı��¼�-�����ܻ�ֵ=�������*���۾���
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntry_editStopped(e);
		String keyName = kdtEntry.getColumnKey(e.getColIndex());
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		IRow row   = kdtEntry.getRow(rowIndex);
		PlanIndexTypeEnum  planEnum =(PlanIndexTypeEnum) row.getCell("productConstitute").getValue();
		
		//wyh �����Ʒ�ṹ��סլ�� ѡ��ò�Ʒ���ͺ� �Զ��������������ȥ ѡ��ò�Ʒ���ͺ������Ժ������ݽ�������
		
		if("newAreaRange".equals(keyName)){
			isCanSelectProductType(e.getRowIndex());
		}
		if(("acreage".equals(keyName)||"price".equals(keyName))&&!PlanIndexTypeEnum.parking.equals(planEnum)){ //sumAmount
			BigDecimal area = (BigDecimal)kdtEntry.getRow(e.getRowIndex()).getCell("acreage").getValue();
			BigDecimal price = (BigDecimal)kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue();
			if(area!=null && price!=null)
				kdtEntry.getRow(e.getRowIndex()).getCell("sumAmount").setValue(area.multiply(price));
			reSetExpressions();
		}
		if(("acreage".equals(keyName) || "quantity".equals(keyName)||"price".equals(keyName))&&PlanIndexTypeEnum.parking.equals(planEnum)){ //����
			Object obj = kdtEntry.getRow(e.getRowIndex()).getCell("quantity").getValue();
			BigDecimal price = new BigDecimal(0);
			BigDecimal quantity = new BigDecimal(0);
			if(obj!=null){
				quantity = new BigDecimal(obj.toString());
			    price = (BigDecimal)kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue();
			}
			if(quantity!=null && price!=null)
				kdtEntry.getRow(e.getRowIndex()).getCell("sumAmount").setValue(quantity.multiply(price));
			reSetExpressions();
		}
	}
	//�ɲ���׶δ�����������
	protected void prmtMeasurePhases_dataChanged(DataChangeEvent e)throws Exception {
       if(prmtMeasurePhases.getValue()!=null)
       {
    	   EntityViewInfo goodEVInfo1 = new EntityViewInfo();
   		   FilterInfo goodFilter1 = new FilterInfo();
   		   goodFilter1.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));
   		   goodEVInfo1.setFilter(goodFilter1);
   		   prmtMeasurePhases.setEntityViewInfo(goodEVInfo1);
    	   MeasurePhaseInfo measurePhasesInfo =(MeasurePhaseInfo)prmtMeasurePhases.getValue();
    	   measurePhasesInfo = MeasurePhaseFactory.getRemoteInstance().getMeasurePhaseInfo(new ObjectUuidPK(measurePhasesInfo.getId().toString()));
    	   comboMeasureType.setSelectedItem(measurePhasesInfo.getPhaseType());
    	   /**�ֹ��޸ġ�����׶Ρ�����һ�׶Σ���汾���Զ�Ϊ����һ�汾.0��������v1.1�䵽v2.0*/
    	   if(prmtProjectAgin.getValue()!=null && prmtMeasurePhases.getValue()!=null){//
    		   SellProjectInfo projectInfo = (SellProjectInfo)prmtProjectAgin.getValue();
    		   MeasurePhaseInfo measureInfo = (MeasurePhaseInfo)prmtMeasurePhases.getValue();
    		   if(checkMeasurePhase(projectInfo,measureInfo))
    			   prmtMeasurePhases.setValue(e.getOldValue()); 
    		   String projectid = projectInfo.getId().toString();
    		   String measureid = measureInfo.getId().toString();
    		   MeasurePlanTargetInfo info = loadLastVersionMeasureData(projectid,measureid);
    		   if(info.getId()==null){
    			   MeasurePhaseInfo mpInfo = (MeasurePhaseInfo)prmtMeasurePhases.getValue();
    			   String number = mpInfo.getNumber();
    			   int index = Integer.parseInt(number);
    			   txtVersions.setText("V"+index+".0");
    			   txtAdjustCause.setText(null);
//    			   txtNumber.setText(null);
    			   txtAdjustCause.setRequired(false);
//    			   versionType.setEnabled(true);
    		   }else{
    			   info = loadLastVersionMeasureData(projectid,null);
    			   String version = info.getVersions();
    			   if(version!=null){
        			   int index = version.indexOf(".");
        			   txtVersions.setText(setNestVersion(version));
        			   int v = Integer.parseInt(version.substring(index+1));
        			   if(v!=0){
    						txtAdjustCause.setRequired(true);
//    						versionType.setEnabled(false);
    					}
    			   }
    		   }
    	   }
        }
    }
	/**
	 * ����޸Ĳ���׶Σ�����ѡ����ǰ���°汾��ǰ�Ľ׶�
	 * */
	public boolean checkMeasurePhase(SellProjectInfo projectInfo,MeasurePhaseInfo mpInfo){
		boolean flag = false;
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number",mpInfo.getNumber(),CompareType.GREATER));
		evInfo.setFilter(filter);
		try {
			Set set = new HashSet();
			MeasurePhaseCollection coll = MeasurePhaseFactory.getRemoteInstance().getMeasurePhaseCollection(evInfo);
			for(int i=0;coll!=null && i<coll.size();i++){
				MeasurePhaseInfo info = coll.get(i);
				if(info!=null)
					set.add(info.getId().toString());
			}
			if(set!=null && set.size()>0){
				evInfo = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectAgin",projectInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("measurePhases",set,CompareType.INCLUDE));
				evInfo.setFilter(filter);
				MeasurePlanTargetCollection mptcoll = null;
				try {
					mptcoll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(evInfo);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				if(mptcoll!=null && mptcoll.size()>0){
					flag = true;
					MsgBox.showInfo("����ѡ����ǰ���°汾��ǰ�Ľ׶Σ�������ѡ����ȷ����׶Σ�");
					SysUtil.abort();
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ȡ���°汾�Ļ�ֵ����
	 * projectId ��Ϊ�գ�measurePhasesId Ϊ��  ȡ����Ŀ�������°汾
	 * projectId ��Ϊ�գ�measurePhasesId ��Ϊ��  ȡ����ǰ����׶ε����°汾
	 * */
	public MeasurePlanTargetInfo loadLastVersionMeasureData(String projectId ,String measurePhasesId){
		MeasurePlanTargetInfo info = new MeasurePlanTargetInfo();
		if(projectId!=null){
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectAgin",projectId));
			evInfo.setFilter(filter);
			evInfo.setSelector(this.getSelectors());
			MeasurePlanTargetCollection coll = null;
			try {
				coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(evInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			CRMHelper.sortCollection(coll, "versions", false);
			if(coll!=null && coll.size()>0){
				info = coll.get(0);
			}
			if(measurePhasesId!=null){
				evInfo = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectAgin",projectId));
				filter.getFilterItems().add(new FilterItemInfo("measurePhases",measurePhasesId));
				evInfo.setFilter(filter);
				evInfo.setSelector(this.getSelectors());
				coll = null;
				try {
					coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(evInfo);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				CRMHelper.sortCollection(coll, "versions", false);
				if(coll!=null && coll.size()>0){
					info = coll.get(0);
				}else{
				   info = new MeasurePlanTargetInfo() ;
				 
				}
			}
		}
		return info;
	}
	protected void verifyInputForSave() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			VerifyInputUtil.verifyNull(this, txtNumber,"���ݱ��" );
		}
		VerifyInputUtil.verifyNull(this, prmtMeasurePhases,"����׶�" );
		VerifyInputUtil.verifyNull(this, prmtOrgUnit,"��֯����" );
		VerifyInputUtil.verifyNull(this, versionType,"�汾����" );
		
		SellProjectInfo sellProjectInfo = (SellProjectInfo)prmtProjectAgin.getValue();
		
		if(txtVersions.getText()!=null){
			int index = txtVersions.getText().indexOf(".");
			int v = Integer.parseInt(txtVersions.getText().substring(index+1));
			if(v!=0 ){
				VerifyInputUtil.verifyNull(this, txtAdjustCause,"����ԭ��" );
			}
		}
		
		int last = txtVersions.getText().lastIndexOf(".");
		String version = txtVersions.getText().substring(0,last);
		if(VersionTypeEnum.check.equals(versionType.getSelectedItem())){
			if(MonthPlanEditUI.checkVersionType(editData.getId(),sellProjectInfo.getId().toString(),version,new MeasurePlanTargetInfo())){
				MsgBox.showWarning(this,"��ǰ�׶εĿ��˰��Ѿ����ڣ��������������˰棡");
				SysUtil.abort();
			}
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		for(int i=0;i<kdtEntry.getRowCount();i++){
//			if(kdtEntry.getRow(i).getCell("productType").getValue()==null){
//				int colInex = kdtEntry.getColumnIndex("productType");
//				kdtEntry.getEditManager().editCellAt(i,colInex);
//				MsgBox.showError(this,"��Ʒ���Ͳ���Ϊ�գ�");
//				SysUtil.abort();
//			}
			if(kdtEntry.getRow(i).getCell("productType").getValue()==null) continue;
			if(kdtEntry.getRow(i).getCell("acreage").getValue()==null){
//				int colInex = kdtEntry.getColumnIndex("acreage");
//				kdtEntry.getEditManager().editCellAt(i,colInex);
//				MsgBox.showError(this,"�����������Ϊ�գ�");
//				SysUtil.abort();
				kdtEntry.getRow(i).getCell("acreage").setValue("0");
			}
			if(kdtEntry.getRow(i).getCell("quantity").getValue()==null){
//				int colInex = kdtEntry.getColumnIndex("quantity");
//				kdtEntry.getEditManager().editCellAt(i,colInex);
//				MsgBox.showError(this,"��������Ϊ�գ�");
//				SysUtil.abort();
				kdtEntry.getRow(i).getCell("quantity").setValue("0");
			}
			if(kdtEntry.getRow(i).getCell("price").getValue()==null){
//				int colInex = kdtEntry.getColumnIndex("price");
//				kdtEntry.getEditManager().editCellAt(i,colInex);
//				MsgBox.showError(this,"���۾��۲���Ϊ�գ�");
//				SysUtil.abort();
				kdtEntry.getRow(i).getCell("price").setValue("0");
			}
			if(kdtEntry.getRow(i).getCell("sumAmount").getValue()==null){
//				int colInex = kdtEntry.getColumnIndex("sumAmount");
//				kdtEntry.getEditManager().editCellAt(i,colInex);
//				MsgBox.showError(this,"���۽���Ϊ�գ�");
//				SysUtil.abort();
				kdtEntry.getRow(i).getCell("sumAmount").setValue("0");
			}
		}
	}

	/**
	 * ͨ�÷���-������
	 * */
	public IRow addLine(KDTable table,IObjectValue detailData){
		IRow row = null;
		if(detailData != null)
        {
			row = table.addRow();
            getUILifeCycleHandler().fireOnAddNewLine(table, detailData);
            loadLineFields(table, row, detailData);
            afterAddLine(table, detailData);
        }
	    appendFootRow(kdtEntry);
	    return row;
	}
	  /**
	 * ͨ�÷���-ɾ����
	 * */
	public void removeTable(KDTable table){
		if(table != null)
            removeLine(table);
	}
   
	protected void attachListeners() {
		addDataChangeListener(this.prmtMeasurePhases);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtMeasurePhases);
	}
	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	}else if(com instanceof KDSpinner){
    		listeners = com.getListeners(ChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDSpinner)com).removeChangeListener((ChangeListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
	 protected void addDataChangeListener(JComponent com) {
	    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDSpinner){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDSpinner)com).addChangeListener((ChangeListener)listeners[i]);
	    		}
	    	}
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return MeasurePlanTargetFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}
	
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	SellProjectInfo SellInfo;
	String sellProjectId = null;
	protected IObjectValue createNewData() {
		MeasurePlanTargetInfo info = new MeasurePlanTargetInfo();
		sellProjectId = (String)getUIContext().get("sellProjectId");
		if(sellProjectId != null){
			info = loadLastVersionMeasureData(sellProjectId,null);
			try {
				SellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
				info.setProjectAgin(SellInfo);
				info.setBizDate(SysUtil.getAppServerTime(null));
				MeasurePhaseInfo measurePhasesInfo = info.getMeasurePhases();
				if(measurePhasesInfo!=null){
					measurePhasesInfo = MeasurePhaseFactory.getRemoteInstance().getMeasurePhaseInfo(new ObjectUuidPK(measurePhasesInfo.getId().toString()));
					info.setMeasurePhases(measurePhasesInfo);
					info.setMeasureType(measurePhasesInfo.getPhaseType());
					info.setName(BOSUuid.create(info.getBOSType()).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		info.setId(null);
		info.setIsNew(false);
		info.setNumber(null);
		info.setVersions(setNestVersion(info.getVersions()));
		info.setVersionsName(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setAdjustCause(null);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
	}
	/**
	 * ������һ�汾
	 * */
	public String setNestVersion(String version){
		if(version!=null){
			int index = version.indexOf(".");
			int v = Integer.parseInt(version.substring(index+1))+1;
			version = version.substring(0, index+1)+v;
		}else{
			version = "V1.0";
		}
		return version;
	}
}