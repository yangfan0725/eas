/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;
import org.jdesktop.jdic.desktop.Desktop;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.etl.value.Value;
import com.kingdee.bos.ctrl.extendcontrols.BizComboQueryAgent;
import com.kingdee.bos.ctrl.extendcontrols.KDBizComboboxModel;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.plaf.KDComboBoxMultiColumnRenderer;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Collection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Factory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntry;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.client.CustomerRptEditUI;
import com.kingdee.eas.fdc.sellhouse.client.TranCustomerSelectUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SupplierReviewGatherEditUI extends AbstractSupplierReviewGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierReviewGatherEditUI.class);
    private List coloums=new ArrayList();
    private Set audittempid=new HashSet();
    private Set supplierEvaluationid=new HashSet();
    private FDCSplQualificationAuditTemplateCollection pcol=new FDCSplQualificationAuditTemplateCollection();
    public SupplierReviewGatherEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
//    	if(this.prmtSupplier.getValue()!=null){
//    		((SupplierStockInfo) this.prmtSupplier.getValue()).setStorageDate((Date) this.pkStorageDate.getValue());
//    	}
    	if(this.prmtSupplier!=null){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("partProject");
    		SupplierStockInfo supplier=(SupplierStockInfo) this.prmtSupplier.getValue();
    		supplier.setPartProject(this.txtPartProject.getText());
    		try {
				SupplierStockFactory.getRemoteInstance().updatePartial(supplier, sel);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	
    	storeContractEntry();
    	
    	editData.getSurveyEntry().clear();
    	for(int i=0;i<this.kdtSurveyEntry.getRowCount();i++){
    		IRow row = this.kdtSurveyEntry.getRow(i);
    		SupplierReviewGatherSurveyEntryInfo entry=new SupplierReviewGatherSurveyEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierReviewGatherSurveyEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		entry.setDescription((String)row.getCell("description").getValue());
    		editData.getSurveyEntry().add(entry);
    	}
    	
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		this.txtSplArea.setText(getSupplierSplArea(this.editData.getSupplier()));
		if(this.editData.getSupplier()!=null){
			this.txtSplArea.setText(getSupplierSplArea(this.editData.getSupplier()));
			if(this.editData.getSupplier().getInviteType()!=null){
				try {
					this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(this.editData.getSupplier().getInviteType().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
       	 	}
			if(this.editData.getSupplier().getSupplierBusinessMode()!=null){
     			try {
					this.txtBusinessMode.setText(SupplierBusinessModeFactory.getRemoteInstance().getSupplierBusinessModeInfo(new ObjectUuidPK(this.editData.getSupplier().getSupplierBusinessMode().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		this.loadEntry();
		this.loadContractEntry();
		
		SupplierReviewGatherSurveyEntryCollection col=editData.getSurveyEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSurveyEntry.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierReviewGatherSurveyEntryInfo entry=col.get(i);
			IRow row=this.kdtSurveyEntry.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("description").setValue(entry.getDescription());
		}
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
    protected void loadContractEntry(){
    	supplierEvaluationid=new HashSet();
    	for(int i=0;i<this.editData.getEvaluationEntry().size();i++){
			if(this.editData.getEvaluationEntry().get(i).getSupplierEvaluation()==null){
				FDCMsgBox.showWarning(this,"评审分录存在异常！");
				this.editData.getEvaluationEntry().clear();
				this.editData.getContractEntry().clear();
				break;
			}
			supplierEvaluationid.add(this.editData.getEvaluationEntry().get(i).getSupplierEvaluation().getId().toString());
		}
    	this.kdtContractEntry.removeRows();
    	for(int i=0;i<this.editData.getContractEntry().size();i++){
    		SupplierRGContractEntryInfo entry =this.editData.getContractEntry().get(i);
			IRow row=this.kdtContractEntry.addRow();
			row.setUserObject(entry);
			row.getCell("isHasContract").setValue(Boolean.valueOf(entry.isIsHasContract()));
			if(entry.isIsHasContract()){
				row.getCell("contract").setValue(entry.getContractBill().getName());
			}else{
				row.getCell("contract").setValue(entry.getContract());
			}
			row.getCell("contractAmount").setValue(entry.getContractAmount());
			row.getCell("manager").setValue(entry.getManager());
			row.getCell("managerPhone").setValue(entry.getManagerPhone());
		}
    }
    protected void storeContractEntry(){
    	for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
    		IRow row=this.kdtContractEntry.getRow(i);
    		SupplierRGContractEntryInfo entry =(SupplierRGContractEntryInfo) row.getUserObject();
    		entry.setManager((String) row.getCell("manager").getValue());
    		entry.setManagerPhone((String) row.getCell("managerPhone").getValue());
		}
    }
    protected void clearColoums(List list){
    	for(int i=0;i<coloums.size();i++){
    		this.kdtEntry.removeColumn(this.kdtEntry.getColumnIndex(coloums.get(i).toString()));
    	}
    	coloums.clear();
    }
    protected void loadEntry(){
    	SupplierReviewGatherEntryCollection col=this.editData.getEntry();
    	this.kdtEntry.removeRows();
    	this.clearColoums(coloums);
    	Map id=new HashMap();
    	List objectEntry=new ArrayList();
    	for(int i=0;i<col.size();i++){
    		SupplierReviewGatherEntryInfo entry=col.get(i);
    		if(id.get(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString())==null){
    			List value=new ArrayList();
    			value.add(entry);
    			id.put(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString(), value);
    		}else{
    			((List)id.get(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString())).add(entry);
    		}
    	}
        Iterator it=id.entrySet().iterator();  
        while(it.hasNext()){
        	Map.Entry entry = (Map.Entry) it.next();
        	IRow row=this.kdtEntry.addRow();
        	List value=(ArrayList)entry.getValue();
        	for(int i=0;i<value.size();i++){
        		SupplierReviewGatherEntryInfo srgentry=(SupplierReviewGatherEntryInfo) value.get(i);
        		if(!coloums.contains(srgentry.getAuditTemplate().getAuditPerson().getId().toString())){
        			IColumn coloum=null;
        			for(int j=0;j<coloums.size();j++){
        				if(srgentry.getAuditTemplate().getAuditDept().getId().toString().equals(this.kdtEntry.getColumn(coloums.get(j).toString()).getExpressions())){
        					coloum=this.kdtEntry.addColumn(this.kdtEntry.getColumnIndex(coloums.get(j).toString())+1);
        					break;
        				}
        			}
        			if(coloum==null){
        				coloum=this.kdtEntry.addColumn(this.kdtEntry.getColumnCount()-1);
        			}
        			coloum.setKey(srgentry.getAuditTemplate().getAuditPerson().getId().toString());
        			coloum.setExpressions(srgentry.getAuditTemplate().getAuditDept().getId().toString());
        			coloum.getStyleAttributes().setLocked(true);
        			this.kdtEntry.getHeadRow(0).getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditDept().getName());
        			if(this.kdtEntry.getHeadRow(1)!=null){
        				this.kdtEntry.getHeadRow(1).getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditPerson().getName());
        			}else{
        				this.kdtEntry.addHeadRow().getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditPerson().getName());
        			}
        			
        			objectEntry.add(srgentry.getAuditTemplate().getAuditPerson());
        			coloums.add(srgentry.getAuditTemplate().getAuditPerson().getId().toString());
        		}
        		if(AppraiseTypeEnum.PASS.equals(srgentry.getAuditTemplate().getTemplateEntry().getAppraiseType())){
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getIsPass().getAlias());
        			row.getCell("score").getStyleAttributes().setLocked(true);
        		}else if(AppraiseTypeEnum.WEIGHT.equals(srgentry.getAuditTemplate().getTemplateEntry().getAppraiseType())){
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setNumberFormat("#0.00");
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getScore());
        		}else if(AppraiseTypeEnum.CHOOSE.equals(srgentry.getAuditTemplate().getTemplateEntry().getAppraiseType())){
        			KDTextArea indexValue_TextField = new KDTextArea();
        			indexValue_TextField.setAutoAdjustCaret(true);
        			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setWrapText(true);
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setEditor(indexValue_CellEditor);
    				
        			if(srgentry.getAuditTemplate().getChoose()!=null)
        				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getChoose().getName());
    				row.getCell("score").getStyleAttributes().setLocked(true);
        		}else if(AppraiseTypeEnum.WRITE.equals(srgentry.getAuditTemplate().getTemplateEntry().getAppraiseType())){
        			KDTextArea indexValue_TextField = new KDTextArea();
        			indexValue_TextField.setAutoAdjustCaret(true);
        			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setWrapText(true);
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setEditor(indexValue_CellEditor);
        			
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getWrite());
    				row.getCell("score").getStyleAttributes().setLocked(true);
        		}
        		row.setUserObject(srgentry.getAuditTemplate().getTemplateEntry());
        		row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setUserObject(srgentry);
        		row.getCell("guideType").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getIndexGroup().getName());
        		row.getCell("guideName").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getName());
        		row.getCell("fullNum").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getFullMarkStandard());
        		row.getCell("weight").setValue(srgentry.getAuditTemplate().getTemplateEntry().getWeight());
        		if(row.getCell("remark").getValue()!=null){
        			row.getCell("remark").setValue(row.getCell("remark").getValue().toString()+"\n"+srgentry.getAuditTemplate().getAuditPerson().getName()+":"+srgentry.getAuditTemplate().getRemark());
        		}else{
        			row.getCell("remark").setValue(srgentry.getAuditTemplate().getAuditPerson().getName()+":"+srgentry.getAuditTemplate().getRemark());
        		}
        		row.getCell("score").setValue(srgentry.getScore());
        	}
        }
        List sort=new ArrayList();
        for(int i=0;i<this.kdtEntry.getRowCount();i++){
        	if(((SupplierGuideEntryInfo)this.kdtEntry.getRow(i).getUserObject()).getSeq()>this.kdtEntry.getRowCount()){
        		sort.add(this.kdtEntry.getRow(i).clone());
        		continue;
        	}
        	for(int j=0;j<this.kdtEntry.getRowCount();j++){
        		if(i+1==((SupplierGuideEntryInfo)this.kdtEntry.getRow(j).getUserObject()).getSeq()){
            		sort.add(this.kdtEntry.getRow(j).clone());
            		continue;
            	}
        	}
        }
        this.kdtEntry.removeRows();
        for(int i=0;i<sort.size();i++){
        	IRow row=this.kdtEntry.addRow();
        	IRow sortRow=(IRow) sort.get(i);
        	row.setUserObject(sortRow.getUserObject());
        	for(int j=0;j<this.kdtEntry.getColumnCount();j++){
        		row.getCell(j).setValue(sortRow.getCell(j).getValue());
        		row.getCell(j).setUserObject(sortRow.getCell(j).getUserObject());
        		if(this.kdtEntry.getColumnKey(j).equals("remark")&&row.getCell("remark").getValue()!=null){
        			int height=row.getCell("remark").getValue().toString().split(":").length;
        			if(height>1){
        				row.setHeight(row.getHeight()*(height-1));
        			}
        		}
        	}
        	SupplierGuideEntryInfo sg=(SupplierGuideEntryInfo) row.getUserObject();
        	if(!AppraiseTypeEnum.WEIGHT.equals(sg.getAppraiseType())){
        		row.getCell("score").getStyleAttributes().setLocked(true);
			}
        }
        HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC606_INVITESUPPLIEREVAL", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		Boolean isShow=true;
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			
			if(hmAllParam.get("FDC606_INVITESUPPLIEREVAL")!=null){
				isShow=Boolean.valueOf(hmAllParam.get("FDC606_INVITESUPPLIEREVAL").toString()).booleanValue();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(isShow){
			getFootRow(this.kdtEntry,new String[]{"weight"});
	        BigDecimal score =SupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
			this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
		}
		
        if(coloums.size()>0){
        	int merger=0;
        	for(int i=0;i<this.kdtEntry.getColumnCount();i++){
    			if(i>0){
    				boolean isMerge=false;
    				if(this.kdtEntry.getHeadRow(0).getCell(i).getValue().toString().equals(this.kdtEntry.getHeadRow(0).getCell(i-1).getValue().toString())){
    					isMerge=true;
    					merger=i;
    				}
    				if(isMerge){
    					this.kdtEntry.getHeadMergeManager().mergeBlock(0, i-1, 0, merger);
    				}
    			}
    		}
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("guideType"), 1, this.kdtEntry.getColumnIndex("guideType"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("guideName"), 1, this.kdtEntry.getColumnIndex("guideName"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("fullNum"), 1, this.kdtEntry.getColumnIndex("fullNum"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("weight"), 1, this.kdtEntry.getColumnIndex("weight"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("remark"), 1, this.kdtEntry.getColumnIndex("remark"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("score"), 1, this.kdtEntry.getColumnIndex("score"));
        
    		if(isShow){
    			for(int i=0;i<coloums.size();i++){
    				this.kdtEntry.getColumn(coloums.get(i).toString()).getStyleAttributes().setNumberFormat("#0.00");
    				this.kdtEntry.getColumn(coloums.get(i).toString()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    			
    				this.kdtEntry.getFootManager().getFootRow(0).getCell(coloums.get(i).toString()).setValue(SupplierEvaluationEditUI.getTotalScore(this.kdtEntry,coloums.get(i).toString(),"weight"));
    			}
    		}
        }
		
        if(objectEntry.size()>0){
    		this.prmtEntry.setValue(objectEntry.toArray());
    	}else{
    		this.prmtEntry.setValue(null);
    	}
    }
    public void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
    public BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal){
        			sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        		}else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("supplier.purchaseOrgUnit.*");
    	sel.add("supplier.inviteType.*");
    	sel.add("supplier.grade.*");
    	sel.add("supplier.isPass");
    	sel.add("supplier.storageDate");
    	sel.add("supplier.storageNumber");
    	sel.add("srcIsPass");
    	sel.add("entry.*");
    	sel.add("entry.auditTemplate.*");
    	sel.add("entry.auditTemplate.templateEntry.*");
    	sel.add("entry.auditTemplate.auditPerson.*");
    	sel.add("entry.auditTemplate.auditDept.*");
    	sel.add("entry.auditTemplate.choose.*");
    	sel.add("entry.auditTemplate.templateEntry.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.indexGroup.*");
    	
    	sel.add("evaluationEntry.*");
    	sel.add("evaluationEntry.supplierEvaluation.id");
    	
    	sel.add("contractEntry.*");
    	sel.add("contractEntry.contractBill.*");
    	
    	sel.add("surveyEntry.*");
    	sel.add("procInstId");
    	
		return sel;
	}
	protected String getSupplierSplArea(SupplierStockInfo info){
		if(info==null) return null;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplierSplAreaEntry.fdcSplArea.*");
		try {
			info=SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(info.getId()), sel);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		String text="";
		SupplierSplAreaEntryCollection col=info.getSupplierSplAreaEntry();
		for(int i=0;i<col.size();i++) {
			SupplierSplAreaEntryInfo entry = col.get(i);
			if(i==col.size()-1){
				text=text+entry.getFdcSplArea().getName();
			}else{
				text=text+entry.getFdcSplArea().getName()+";";
			}
		}
    	return text;
    }
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
	}
	protected void initTable(){
		this.kdtEntry.checkParsed();
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(new BigDecimal(10));
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtEntry.getColumn("score").setEditor(weight);
		this.kdtEntry.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
	 	this.kdtEntry.getStyleAttributes().setLocked(true);
	 	this.kdtEntry.getColumn("score").getStyleAttributes().setLocked(false);
	 	
	 	this.kdtEntry.getColumn("weight").getStyleAttributes().setNumberFormat("#0.00");
	 	this.kdtEntry.getColumn("weight").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	 	
		KDTextArea indexValue_TextField = new KDTextArea();
		indexValue_TextField.setAutoAdjustCaret(true);
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
		this.kdtEntry.getColumn("remark").getStyleAttributes().setWrapText(true);
		this.kdtEntry.getColumn("remark").setEditor(indexValue_CellEditor);
		
		this.kdtContractEntry.checkParsed();
		this.kdtContractEntry.getStyleAttributes().setLocked(true);
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtContractEntry.getColumn("isHasContract").setEditor(editor);
 		
 		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtContractEntry.getColumn("manager").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtContractEntry.getColumn("managerPhone").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtContractEntry.getColumn("manager").getStyleAttributes().setLocked(false);
		this.kdtContractEntry.getColumn("managerPhone").getStyleAttributes().setLocked(false);
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC606_INVITESUPPLIEREVAL", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			Boolean isShow=true;
			if(hmAllParam.get("FDC606_INVITESUPPLIEREVAL")!=null){
				isShow=Boolean.valueOf(hmAllParam.get("FDC606_INVITESUPPLIEREVAL").toString()).booleanValue();
			}
			if(!isShow){
				this.kdtEntry.getColumn("weight").getStyleAttributes().setHided(true);
				this.kdtEntry.getColumn("score").getStyleAttributes().setHided(true);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		this.kdtSurveyEntry.checkParsed();
	}
	protected void setF7Filter(SupplierStockInfo supplier,SupplierEvaluationTypeInfo type,SupplierAppraiseTemplateInfo template) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		audittempid=new HashSet();
		pcol=new FDCSplQualificationAuditTemplateCollection();
		
		if(supplier!=null&&type!=null&&template!=null){
			Set pid=new HashSet();
			if(editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("head.id",editData.getId().toString(),CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("head.supplier.id",supplier.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("head.orgUnit.id",this.editData.getOrgUnit().getId().toString()));
			view.setFilter(filter);
			
			SupplierReviewGatherEntryCollection srgentryCol=SupplierReviewGatherEntryFactory.getRemoteInstance().getSupplierReviewGatherEntryCollection(view);
			for(int i=0;i<srgentryCol.size();i++){
				audittempid.add(srgentryCol.get(i).getAuditTemplate().getId().toString());
			}
			
			SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("templateEntry.splAuditIndex.indexGroup.name");
    		sel.add("templateEntry.splAuditIndex.id");
    		sel.add("templateEntry.splAuditIndex.name");
    		sel.add("templateEntry.splAuditIndex.fullMarkStandard");
    		sel.add("templateEntry.appraiseType");
    		sel.add("templateEntry.weight");
    		sel.add("templateEntry.seq");
    		sel.add("remark");
    		sel.add("auditPerson.id");
    		sel.add("auditPerson.name");
    		sel.add("auditDept.id");
    		sel.add("auditDept.name");
    		
    		sel.add("score");
    		sel.add("isPass");
    		sel.add("parent.contract");
    		sel.add("parent.contractAmount");
    		sel.add("parent.contractBill.*");
    		sel.add("parent.description");
    		sel.add("parent.creator.name");
    		
    		sel.add("choose.*");
    		sel.add("write");
    		view=new EntityViewInfo();
    		filter=new FilterInfo();
    		if(audittempid.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("id",audittempid,CompareType.NOTINCLUDE));
			}
    		filter.getFilterItems().add(new FilterItemInfo("auditPerson",null,CompareType.NOTEQUALS));
    		if(type.getNumber().equals("003")||type.getNumber().equals("004")||type.getNumber().equals("005")){
    			filter.getFilterItems().add(new FilterItemInfo("parent.id",supplierEvaluationid,CompareType.INCLUDE));
    		}else{
    			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
        		filter.getFilterItems().add(new FilterItemInfo("parent.evaluationType.id",type.getId().toString()));
        		filter.getFilterItems().add(new FilterItemInfo("parent.supplier.id",supplier.getId().toString()));
        		filter.getFilterItems().add(new FilterItemInfo("parent.template.id",template.getId().toString()));
        		filter.getFilterItems().add(new FilterItemInfo("parent.orgUnit.id",this.editData.getOrgUnit().getId().toString()));
    		}
//    		FilterInfo valueFilter=new FilterInfo();
//    		valueFilter.getFilterItems().add(new FilterItemInfo("score",null,CompareType.NOTEQUALS));
//    		valueFilter.getFilterItems().add(new FilterItemInfo("isPass",null,CompareType.NOTEQUALS));
//    		valueFilter.setMaskString("#0 or #1");
//    		
//    		filter.mergeFilter(valueFilter, "and");
    		
    		view.setFilter(filter);
    		view.setSelector(sel);
			pcol=FDCSplQualificationAuditTemplateFactory.getRemoteInstance().getFDCSplQualificationAuditTemplateCollection(view);
			
			for(int i=0;i<pcol.size();i++){
				pid.add(pcol.get(i).getAuditPerson().getId().toString());
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("PositionMember.isPrimary",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("deletedStatus",DeletedStatusEnum.NORMAL_VALUE));
			if(pid.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("id",pid,CompareType.INCLUDE));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
			}
			view.setFilter(filter);
			this.prmtEntry.setEntityViewInfo(view);
		}else{
			Set org=new HashSet();
			if(editData.getOrgUnit()!=null){
				org.add(editData.getOrgUnit().getId().toString());
			}
			org.add(OrgConstants.DEF_CU_ID);
			filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
			view.setFilter(filter);
			this.prmtSupplier.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			view.setFilter(filter);
			this.prmtEvaluationType.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",this.editData.getOrgUnit().getId().toString()));
			if(this.prmtEvaluationType.getValue()!=null){
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.id",((SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue()).getId()));
			}
			if(this.prmtSupplier.getValue()!=null){
				filter.getFilterItems().add(new FilterItemInfo("supplier.id",((SupplierStockInfo)this.prmtSupplier.getValue()).getId()));
			}
			SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("template.evaluationType.*");
    		sel.add("template.*");
			view.setFilter(filter);
			view.setSelector(sel);
			FDCSplQualificationAuditBillCollection col=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillCollection(view);
			Set tempId=new HashSet();
			SupplierAppraiseTemplateInfo tempInfo=null;
			for(int i=0;i<col.size();i++){
				if(col.get(i).getTemplate()!=null){
					tempInfo=col.get(i).getTemplate();
					tempId.add(col.get(i).getTemplate().getId().toString());
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",tempId,CompareType.INCLUDE));
			view.setFilter(filter);
			this.prmtTemplate.setEntityViewInfo(view);
//			this.prmtTemplate.setValue(tempInfo);
		}
	}
	protected void btnSelectContract_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
		
		this.storeFields();
		
		UIContext uiContext = new UIContext(this);
		
		Boolean isCancel=Boolean.FALSE;
		uiContext.put("supplier", this.prmtSupplier.getValue());
		uiContext.put("type", this.prmtEvaluationType.getValue());
		uiContext.put("template", this.prmtTemplate.getValue());
		uiContext.put("editData", this.editData);
		uiContext.put("table", this.kdtContractEntry);
		uiContext.put("orgUnit", this.editData.getOrgUnit());
		uiContext.put("isCancel",isCancel);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectContractUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		
		if(!(Boolean) this.editData.get("isCancel")){
			this.prmtEntry.setValue(null);
			
			this.loadContractEntry();
			
			setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		    setComEnable();
		}
	}
	protected void setComEnable(){
		SupplierStockInfo supplier=(SupplierStockInfo) this.prmtSupplier.getValue();
		SupplierEvaluationTypeInfo type=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
		SupplierAppraiseTemplateInfo template=(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue();
		if(type!=null&&supplier!=null&&template!=null){
			if(type.getNumber().equals("002")){
//				this.pkStorageDate.setEnabled(true);
			}else{
				this.pkStorageDate.setEnabled(false);
			}
			if(type.getNumber().equals("006")||type.getNumber().equals("007")){
				this.cbIsOver.setEnabled(true);
				if(this.cbIsOver.isSelected()){
					this.prmtLevel.setEnabled(false);
				}else{
					this.prmtLevel.setEnabled(true);
				}
			}else{
				this.cbIsOver.setEnabled(false);
				this.prmtLevel.setEnabled(false);
			}
			if(type.getNumber().equals("002")||type.getNumber().equals("003")||
					type.getNumber().equals("004")||type.getNumber().equals("005")){
        		this.prmtGrade.setEnabled(true);
				this.cbIsPass.setEnabled(true);
			}else{
				this.prmtGrade.setEnabled(false);
				this.cbIsPass.setEnabled(false);
			}
        	if(type.getNumber().equals("003")||type.getNumber().equals("004")||
					type.getNumber().equals("005")){
        		if(this.editData.getContractEntry().size()>0){
    				this.prmtEntry.setEnabled(true);
    			}else{
    				this.prmtEntry.setEnabled(false);
    			}
        		if(this.oprtState.equals(OprtState.VIEW)){
					this.btnSelectContract.setEnabled(false);
				}else{
					this.btnSelectContract.setEnabled(true);
				}
        	}else{
        		this.prmtEntry.setEnabled(true);
        		this.btnSelectContract.setEnabled(false);
        	}
        	this.prmtTemplate.setEnabled(true);
        }else{
        	if(type!=null&&supplier!=null){
        		this.prmtTemplate.setEnabled(true);
        	}else{
        		this.prmtTemplate.setEnabled(false);
        	}
        	this.pkStorageDate.setEnabled(false);
        	
        	this.cbIsOver.setEnabled(false);
			this.prmtLevel.setEnabled(false);
			
			this.prmtGrade.setEnabled(false);
			this.cbIsPass.setEnabled(false);
			
        	this.prmtEntry.setEnabled(false);
        	this.btnSelectContract.setEnabled(false);
        }
		if(this.oprtState.equals(OprtState.ADDNEW)){
			this.actionViewBPM.setEnabled(false);
		}
	}
	protected void initControl() {
		try {
			setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
		if(info!=null&&(info.getNumber().equals("004"))){
			this.txtLYHRate.setEnabled(true);
			this.txtLYGCRate.setEnabled(true);
			this.txtLYGCScore.setEnabled(true);
    		this.txtLYHRate.setRequired(true);
    		this.txtLYGCRate.setRequired(true);
    		this.txtLYGCScore.setRequired(true);
	    }else{
	    	this.txtLYHRate.setEnabled(false);
			this.txtLYGCRate.setEnabled(false);
			this.txtLYGCScore.setEnabled(false);
			this.txtLYHRate.setRequired(false);
    		this.txtLYGCRate.setRequired(false);
    		this.txtLYGCScore.setRequired(false);
		}
		this.txtLYGCScore.setNegatived(false);
		this.txtLYGCScore.setMinimumValue(new BigDecimal(0));
		this.txtLYGCScore.setMaximumValue(new BigDecimal(100));
		this.txtLYHRate.setNegatived(false);
		this.txtLYHRate.setMinimumValue(new BigDecimal(0));
		this.txtLYHRate.setMaximumValue(new BigDecimal(100));
		this.txtLYGCRate.setNegatived(false);
		this.txtLYGCRate.setMinimumValue(new BigDecimal(0));
		this.txtLYGCRate.setMaximumValue(new BigDecimal(100));
		
		setComEnable();
		
		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.kdtContractEntry.setEditable(true);
			
			this.kdtContractEntry.getColumn("isHasContract").getStyleAttributes().setLocked(true);
	 		this.kdtContractEntry.getColumn("contract").getStyleAttributes().setLocked(true);
			this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setLocked(true);
		}
		Color color = Color.BLUE;
		this.txtSupplierName.setForeground(color);
		
		this.btnViewBPM.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
	}
	protected void prmtSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception{
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         SupplierStockInfo info=(SupplierStockInfo) this.prmtSupplier.getValue();
         if(info==null){
        	 this.txtSplArea.setText(null);
     		 this.txtInviteType.setText(null);
     		 this.txtSupplierName.setText(null);
    		 this.txtBusinessMode.setText(null);
    		 this.prmtSrcGrade.setValue(null);
    		 this.prmtSrcLevel.setValue(null);
    		 
    		 this.txtPartProject.setText(null);
     		 this.txtLinkPerson.setText(null);
     		 this.txtLinkPhone.setText(null);
     		 this.txtLinkJob.setText(null);
     		 this.txtContractor.setText(null);
     		 this.txtContractorPhone.setText(null);
     		 this.txtManager.setText(null);
     		 this.txtManagerPhone.setText(null);
     		 
     		 this.txtStorageNumber.setText(null);
     		 this.pkStorageDate.setValue(null);
     		 
    		 this.editData.setSrcIsPass(null);
    		 
    		 this.txtOrg.setText(null);
         }else{
        	 this.txtSplArea.setText(getSupplierSplArea(info));
        	 if(info.getInviteType()!=null){
        		 this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(info.getInviteType().getId())).getName());
        	 }
     		 this.txtSupplierName.setText(info.getName());
     		 if(info.getSupplierBusinessMode()!=null){
     			this.txtBusinessMode.setText(SupplierBusinessModeFactory.getRemoteInstance().getSupplierBusinessModeInfo(new ObjectUuidPK(info.getSupplierBusinessMode().getId())).getName());
     		 }
     		 if(info.getGrade()!=null){
    			 this.prmtSrcGrade.setValue(GradeSetUpFactory.getRemoteInstance().getGradeSetUpInfo(new ObjectUuidPK(info.getGrade().getId())));
    		 }
     		 if(info.getLevel()!=null){
     			 this.prmtSrcLevel.setValue(LevelSetUpFactory.getRemoteInstance().getLevelSetUpInfo(new ObjectUuidPK(info.getLevel().getId())));
     		 }
     		
     		 this.txtPartProject.setText(info.getPartProject());
     		 this.txtLinkPerson.setText(info.getAuthorizePerson());
     		 this.txtLinkPhone.setText(info.getAuthorizePhone());
     		 this.txtLinkJob.setText(info.getAuthorizeJob());
     		 this.txtContractor.setText(info.getContractor());
     		 this.txtContractorPhone.setText(info.getContractorPhone());
     		 this.txtManager.setText(info.getManager());
     		 this.txtManagerPhone.setText(info.getManagerPhone());
     		 
     		 this.txtStorageNumber.setText(info.getStorageNumber());
     		 this.pkStorageDate.setValue(info.getStorageDate());
     		 
     		 this.editData.setSrcIsPass(info.getIsPass());
     		 
     		 if(info.getPurchaseOrgUnit()!=null)
     			 this.txtOrg.setText(PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(info.getPurchaseOrgUnit().getId())).getName());
         }
         setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
         
         setComEnable();
	}
	protected void prmtEvaluationType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
        if(this.prmtSupplier.getValue()!=null&&this.prmtEvaluationType.getValue()!=null){
			if(this.prmtTemplate.getValue()!=null){
				if(!((SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue()).getId().toString().equals(((SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getEvaluationType().getId().toString())){
					this.prmtTemplate.setValue(null);
				}
			}
        }else{
			this.prmtTemplate.setValue(null);
		}
        if(info!=null){
        	if(!(info.getNumber().equals("006")||info.getNumber().equals("007"))){
        		this.cbIsOver.setSelected(false);
        		this.prmtLevel.setValue(null);
        	}
        	if(!(info.getNumber().equals("002")||info.getNumber().equals("003")||
        			info.getNumber().equals("004")||info.getNumber().equals("005"))){
            	this.prmtGrade.setValue(null);
            	this.cbIsPass.setSelectedItem(null);
    		}
            if(!(info.getNumber().equals("003")||info.getNumber().equals("004")||
            		info.getNumber().equals("005"))){
    			this.kdtContractEntry.removeRows();
    			this.editData.getContractEntry().clear();
    		}
            
            if(info.getNumber().equals("004")){
        		this.txtLYHRate.setEnabled(true);
        		this.txtLYGCRate.setEnabled(true);
        		this.txtLYGCScore.setEnabled(true);
        		this.txtLYHRate.setRequired(true);
        		this.txtLYGCRate.setRequired(true);
        		this.txtLYGCScore.setRequired(true);
        	}else{
        		removeDataChangeListener(this.txtLYGCRate);
        		removeDataChangeListener(this.txtLYHRate);
        		removeDataChangeListener(this.txtLYGCScore);
        		this.txtLYHRate.setEnabled(false);
        		this.txtLYGCRate.setEnabled(false);
        		this.txtLYGCScore.setEnabled(false);
        		this.txtLYHRate.setRequired(false);
        		this.txtLYGCRate.setRequired(false);
        		this.txtLYGCScore.setRequired(false);
        		
        		this.txtLYHScore.setValue(null);
        		this.txtLYHRate.setValue(null);
        		this.txtLYGCScore.setValue(null);
        		this.txtLYGCRate.setValue(null);
        		if(this.kdtEntry.getFootManager()!=null)
        			this.txtAmount.setValue(this.kdtEntry.getFootManager().getFootRow(0).getCell("score").getValue());
        		
        		addDataChangeListener(this.txtLYGCRate);
        		addDataChangeListener(this.txtLYHRate);
        		addDataChangeListener(this.txtLYGCScore);
        	}
        }else{
        	removeDataChangeListener(this.txtLYGCRate);
    		removeDataChangeListener(this.txtLYHRate);
    		removeDataChangeListener(this.txtLYGCScore);
    		
        	this.txtLYHRate.setEnabled(false);
    		this.txtLYGCRate.setEnabled(false);
    		this.txtLYGCScore.setEnabled(false);
    		this.txtLYHRate.setRequired(false);
    		this.txtLYGCRate.setRequired(false);
    		this.txtLYGCScore.setRequired(false);
    		
    		this.txtLYHScore.setValue(null);
    		this.txtLYHRate.setValue(null);
    		this.txtLYGCScore.setValue(null);
    		this.txtLYGCRate.setValue(null);
    		this.txtAmount.setValue(this.kdtEntry.getFootManager().getFootRow(0).getCell("score").getValue());
    		
    		addDataChangeListener(this.txtLYGCRate);
    		addDataChangeListener(this.txtLYHRate);
    		addDataChangeListener(this.txtLYGCScore);
        }
        setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		setComEnable();
	}
	protected void prmtTemplate_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
        
        setComEnable();
        
        SupplierAppraiseTemplateInfo template=(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue();
        boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtSurveyEntry.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(FDCMsgBox.showConfirm2(this, "评审模板改变会覆盖 考察概况 数据，是否继续？")== FDCMsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        if(isUpdate){
        	this.kdtSurveyEntry.removeRows();
        	
        	if(template!=null){
        		SorterItemCollection sort=new SorterItemCollection();
       		 	sort.add(new SorterItemInfo("number"));
       		 	EntityViewInfo view=new EntityViewInfo();
       		 	FilterInfo filter = new FilterInfo();
       		 	filter.getFilterItems().add(new FilterItemInfo("template.id" , template.getId().toString()));
       		 	filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
           	 	view.setFilter(filter);
           	 	view.setSorter(sort);
                SupplierReviewGatherSurveyCollection col=SupplierReviewGatherSurveyFactory.getRemoteInstance().getSupplierReviewGatherSurveyCollection(view);
                for(int i=0;i<col.size();i++){
                	SupplierReviewGatherSurveyInfo sp=col.get(i);
               	 	IRow row=this.kdtSurveyEntry.addRow();
               	 	row.getCell("number").setValue(sp.getNumber());
               	 	row.getCell("name").setValue(sp.getName());
       	 		}
            }
        }
	}
	protected void prmtGrade_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        GradeSetUpInfo info=(GradeSetUpInfo) this.prmtGrade.getValue();
        if(info!=null){
        	this.cbIsPass.setSelectedItem(info.getIsPass());
        }
	}
	
	protected void prmtEntry_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtEntry.getRowCount()>0||this.kdtEntry.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(FDCMsgBox.showConfirm2(this, "评审结果改变会覆盖供应商评审汇总数据，是否继续？")== FDCMsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        Object value[]=(Object[]) this.prmtEntry.getValue();
        if(isUpdate){
        	this.kdtEntry.removeRows();
        	this.clearColoums(coloums);
        	this.editData.getEntry().clear();
        	if(value!=null){
        		this.storeFields();
        		Set id=new HashSet();
        		for(int i=0;i<value.length;i++){
        			if(value[i]!=null){
        				id.add(((PersonInfo)value[i]).getId().toString());
        			}
        		}
        		SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
        		String remark="";
        		Set parentId=new HashSet();
        		for(int i=0;i<pcol.size();i++){
        			FDCSplQualificationAuditTemplateInfo qt=pcol.get(i);
        			if(id.contains(qt.getAuditPerson().getId().toString())){
        				if(info!=null&&info.getNumber().equals("001")){
        					if(IsGradeEnum.ENGRADE.equals(qt.getIsPass())){
        						this.editData.getEntry().clear();
        						FDCMsgBox.showWarning(this,"存在不合格评审指标，不能进行供应商评审汇总！");
        						break;
        					}
        				}
        				if(!parentId.contains(qt.getParent().getId().toString())&&qt.getParent().getDescription()!=null){
        					remark=remark+qt.getParent().getCreator().getName()+":"+qt.getParent().getDescription()+";";
            				parentId.add(qt.getParent().getId().toString());
        				}
        				SupplierReviewGatherEntryInfo entry=new SupplierReviewGatherEntryInfo();
            			entry.setAuditTemplate(qt);
            			this.editData.getEntry().add(entry);
        			}
        		}
        		if(!"".equals(remark)){
        			this.editData.setDescription(remark);
        		}
        		this.loadFields();
        	}else{
        		getFootRow(this.kdtEntry,new String[]{"weight"});
        		BigDecimal score =SupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
                this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
                SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
    	    	if(info!=null&&info.getNumber().equals("004")){
    	    		this.txtLYHScore.setValue(score);
    	    	}else{
    	    		this.txtAmount.setValue(score);
    	    	}
        	}
        }
	}
	protected void cbIsOver_itemStateChanged(ItemEvent e) throws Exception {
		SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
		if(info!=null){
			if(info.getNumber().equals("006")||info.getNumber().equals("007")){
				if(this.cbIsOver.isSelected()){
					this.prmtLevel.setEnabled(false);
					this.prmtLevel.setValue(null);
				}else{
					this.prmtLevel.setEnabled(true);
				}
			}
		}
	}
	protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception{
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("score")){
			IRow row=this.kdtEntry.getRow(e.getRowIndex());
			BigDecimal setvalue=null;
			BigDecimal score =SupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
            
	    	if(score.compareTo(new BigDecimal("100")) >0){
	    		FDCMsgBox.showWarning(this, "综合得分之和不能大于100！");
	    		this.kdtEntry.getRow(e.getRowIndex()).getCell("score").setValue(null);
	    	}else{
	    		setvalue=(BigDecimal) e.getValue();
	    	}
	    	for(int i=0;i<this.coloums.size();i++){
				SupplierReviewGatherEntryInfo entry=(SupplierReviewGatherEntryInfo) row.getCell(this.coloums.get(i).toString()).getUserObject();
				if(entry!=null){
					entry.setScore(setvalue);
				}
			}
	    	this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
	    	SupplierEvaluationTypeInfo info=(SupplierEvaluationTypeInfo) this.prmtEvaluationType.getValue();
	    	if(info!=null&&info.getNumber().equals("004")){
	    		this.txtLYHScore.setValue(score);
	    	}else{
	    		this.txtAmount.setValue(score);
	    	}
		}
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplier);
		addDataChangeListener(this.prmtEvaluationType);
		addDataChangeListener(this.prmtGrade);
		addDataChangeListener(this.prmtEntry);
		addDataChangeListener(this.prmtTemplate);
		addDataChangeListener(this.cbIsOver);
		addDataChangeListener(this.txtLYGCRate);
		addDataChangeListener(this.txtLYHRate);
		addDataChangeListener(this.txtLYGCScore);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplier);
		removeDataChangeListener(this.prmtEvaluationType);
		removeDataChangeListener(this.prmtGrade);
		removeDataChangeListener(this.prmtEntry);
		removeDataChangeListener(this.prmtTemplate);
		removeDataChangeListener(this.cbIsOver);
		removeDataChangeListener(this.txtLYGCRate);
		removeDataChangeListener(this.txtLYHRate);
		removeDataChangeListener(this.txtLYGCScore);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierReviewGatherFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		SupplierReviewGatherInfo info= new SupplierReviewGatherInfo();
		if(getUIContext().get("org")!=null){
			info.setOrgUnit(((OrgStructureInfo) getUIContext().get("org")).getUnit());
		}
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setIsOver(false);
		info.setAmount(FDCHelper.ZERO);
		return info;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEntry);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRemark);
		
		SupplierEvaluationTypeInfo type=(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue();
//		if(type.getNumber().equals("002")){
//			FDCClientVerifyHelper.verifyEmpty(this, this.pkStorageDate);
//		}
		if(type.getNumber().equals("006")||type.getNumber().equals("007")){
			if(!this.cbIsOver.isSelected()){
//				FDCClientVerifyHelper.verifyEmpty(this, this.prmtLevel);
			}
		}
		if(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005")){
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtGrade);
			FDCClientVerifyHelper.verifyEmpty(this, this.cbIsPass);
		}
		if(type.getNumber().equals("003")||type.getNumber().equals("004")||
				type.getNumber().equals("005")){
			if(this.kdtContractEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"合同分录不能为空！");
				SysUtil.abort();
			}
			for(int i = 0; i < this.kdtContractEntry.getRowCount(); i++){
				IRow row=this.kdtContractEntry.getRow(i);
				if(row.getCell("manager").getValue()==null||"".equals(row.getCell("manager").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"项目经理不能为空！");
					this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtContractEntry.getColumnIndex("manager"));
					SysUtil.abort();
				}
				if(row.getCell("managerPhone").getValue()==null||"".equals(row.getCell("managerPhone").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"项目经理联系电话不能为空！");
					this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtContractEntry.getColumnIndex("managerPhone"));
					SysUtil.abort();
				}
			}
		}
		if(type.getNumber().equals("004")){
//			FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtLYGCScore);
//			FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtLYGCRate);
//			FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtLYHRate);
		}
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"供应商评审汇总不能为空！");
			SysUtil.abort();
		}
		setF7Filter((SupplierStockInfo)this.prmtSupplier.getValue(),(SupplierEvaluationTypeInfo)this.prmtEvaluationType.getValue(),(SupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row=this.kdtEntry.getRow(i);
			SupplierGuideEntryInfo sg=(SupplierGuideEntryInfo) row.getUserObject();
			for(int j=0;j<this.coloums.size();j++){
				SupplierReviewGatherEntryInfo entry=(SupplierReviewGatherEntryInfo) row.getCell(this.coloums.get(j).toString()).getUserObject();
				if(entry!=null&&audittempid.contains(entry.getAuditTemplate().getId().toString())){
					FDCMsgBox.showWarning(this,"供应商评审已经被汇总，请重新选择评审结果！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex(entry.getId().toString()));
					SysUtil.abort();
				}
			}
			if(AppraiseTypeEnum.WEIGHT.equals(sg.getAppraiseType())){
				if(row.getCell("score").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商评审汇总综合得分不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("score"));
					SysUtil.abort();
				}
			}
		}
	}
	protected void setAuditButtonStatus(String oprtType){
    	super.setAuditButtonStatus(oprtType);
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		}
    }
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
		setComEnable();
	}
	protected void txtLYGCRate_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
	
		removeDataChangeListener(this.txtLYHRate);
		this.txtLYHRate.setValue(new BigDecimal(100).subtract(this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue()));
		addDataChangeListener(this.txtLYHRate);
		
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	protected void txtLYHRate_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		removeDataChangeListener(this.txtLYGCRate);
		this.txtLYGCRate.setValue(new BigDecimal(100).subtract(this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue()));
		addDataChangeListener(this.txtLYGCRate);
		
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	protected void txtLYGCScore_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	public boolean checkBeforeWindowClosing() {
		if (this.getOprtState().equals(OprtState.VIEW)) {
			SelectorItemCollection sel=new SelectorItemCollection();
	    	sel.add("manager");
	    	sel.add("managerPhone");
			for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
	    		IRow row=this.kdtContractEntry.getRow(i);
	    		SupplierRGContractEntryInfo entry =(SupplierRGContractEntryInfo) row.getUserObject();
	    		entry.setManager((String) row.getCell("manager").getValue());
	    		entry.setManagerPhone((String) row.getCell("managerPhone").getValue());
	    		try {
					SupplierRGContractEntryFactory.getRemoteInstance().updatePartial(entry, sel);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}
	protected void btnViewSupplier_actionPerformed(ActionEvent e) throws Exception {
		SupplierStockInfo supplier=(SupplierStockInfo) this.prmtSupplier.getValue();
		if(supplier!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", supplier.getId());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(NewSupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"请先选择供应商！");
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	    
//	    EAS_Supplier_ContractualCapacityQuestionnaire	1.5合作商履约情况调查
//	    EAS_Supplier_Performance	1.4承建商履约情况评价
//	    EAS_Supplier_ContractualCapacityEvaluation	1.3供应商履约情况评价
//	    EAS_Supplier_NewMonopolize	1.2新增垄断商审批
//	    EAS_Supplier_QualifiedInStock	1.1考察合格单位入库审批
		SupplierAppraiseTemplateInfo template=(SupplierAppraiseTemplateInfo) this.prmtTemplate.getValue();
		if(this.editData.getId()!=null&&template!=null){
			StringBuffer sql = new StringBuffer();
		    sql.append("select furl from T_WC_URL where fnumber='BPM001'");
		    ISQLExecutor isql = SQLExecutorFactory.getRemoteInstance(sql.toString());
		    IRowSet rs = isql.executeSQL();
		    String url=null;
		    String param=null;
		    if(rs.size()!=0){
		    	while (rs.next()){
		    		url=rs.getString("furl");
		    	}
		    	String BSID=null;
		    	if(template.getNumber()=="01.01"){
		    		BSID="EAS_Supplier_QualifiedInStock";
		    	}else if(template.getNumber()=="01.02"){
		    		BSID="EAS_Supplier_NewMonopolize";
		    	}else if(template.getNumber()=="01.03"){
		    		BSID="EAS_Supplier_MaterialInStock";
		    	}else if(template.getNumber()=="02.02"){
		    		BSID="EAS_Supplier_ContractualCapacityEvaluation";
		    	}else if(template.getNumber()=="02.01"){
		    		BSID="EAS_Supplier_Performance";
		    	}else if(template.getNumber()=="02.03"){
		    		BSID="EAS_Supplier_ContractualCapacityQuestionnaire";
		    	}
			    String BOID=this.editData.getId().toString();
			    String key=GetEnCodeStr(BOID);
			    String userid=SysContext.getSysContext().getCurrentUserInfo().getNumber();
			    
			    url = url+"BSID="+BSID+"&BOID="+URLEncoder.encode(BOID)+"&Key="+key+"&userid="+userid;
			    
			    String procInstId=this.editData.getProcInstId();
			    if(procInstId==null) procInstId=SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherInfo(new ObjectUuidPK(this.editData.getId())).getProcInstId();
			    if(procInstId!=null){
			    	url = url+"&workflowID="+procInstId;
			    }
			    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
		    }
		}
	}
	public void actionViewBPM_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getId()!=null){
			StringBuffer sql = new StringBuffer();
		    sql.append("select furl from T_WC_URL where fnumber='BPM002'");
		    ISQLExecutor isql = SQLExecutorFactory.getRemoteInstance(sql.toString());
		    IRowSet rs = isql.executeSQL();
		    String url=null;
		    String param=null;
		    if(rs.size()!=0){
		    	while (rs.next()){
		    		url=rs.getString("furl");
		    	}
			    String procInstId=this.editData.getProcInstId();
			    if(procInstId==null) procInstId=SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherInfo(new ObjectUuidPK(this.editData.getId())).getProcInstId();
			    if(procInstId==null) return;
			    String key=GetEnCodeStr(procInstId);
			    String userid=SysContext.getSysContext().getCurrentUserInfo().getNumber();
			    
			    url = url+"procInstId="+procInstId+"&Key="+key+"&userId="+userid;
			    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);   
		    }
		}
	}
	
	public String GetEnCodeStr(String str) throws BOSException, NoSuchAlgorithmException {
		Date now=FDCCommonServerHelper.getServerTimeStamp();
		String strNow=new SimpleDateFormat("yyyy-MM-dd").format(now);
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		byte b[] = md.digest((str+strNow+"K2").getBytes());

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
    }
}