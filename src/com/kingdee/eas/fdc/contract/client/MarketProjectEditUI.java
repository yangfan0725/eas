/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryFactory;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectEntryCollection;
import com.kingdee.eas.fdc.contract.MarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection;
import com.kingdee.eas.fdc.contract.MarketProjectUnitEntryInfo;
import com.kingdee.eas.fdc.contract.MarketYearProjectCollection;
import com.kingdee.eas.fdc.contract.MarketYearProjectFactory;
import com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.market.DecorateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketProjectEditUI extends AbstractMarketProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectEditUI.class);
    public MarketProjectEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.actionRemove.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	
    	this.actionAudit.setVisible(true);
    	this.actionUnAudit.setVisible(true);
    	
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
    	KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDWorkButton btnCAddRowinfo = new KDWorkButton();
		KDWorkButton btnCDeleteRowinfo = new KDWorkButton();

		this.actionACLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnCAddRowinfo = (KDWorkButton)contCostEntry.add(this.actionACLine);
		btnCAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRCLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnCDeleteRowinfo = (KDWorkButton) contCostEntry.add(this.actionRCLine);
		btnCDeleteRowinfo.setText("删除行");
		btnCDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDWorkButton btnUAddRowinfo = new KDWorkButton();
		KDWorkButton btnUDeleteRowinfo = new KDWorkButton();

		this.actionAULine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnUAddRowinfo = (KDWorkButton)contUnitEntry.add(this.actionAULine);
		btnUAddRowinfo.setText("新增行");
		btnUAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRULine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnUDeleteRowinfo = (KDWorkButton) contUnitEntry.add(this.actionRULine);
		btnUDeleteRowinfo.setText("删除行");
		btnUDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
		
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("supplier").setEditor(f7Editor);
		
		this.kdtCostEntry.checkParsed();
		f7Box = new KDBizPromptBox(); 
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$longNumber$;$name$");
		f7Box.setEditFormat("$longNumber$;$name$");
		f7Box.setCommitFormat("$longNumber$;$name$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtCostEntry.getColumn("costAccount").setEditor(f7Editor);
		this.kdtCostEntry.getColumn("costAccount").setRequired(true);
		
		this.kdtCostEntry.getColumn("costAccount").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof CostAccountInfo){
					CostAccountInfo info = (CostAccountInfo)obj;
					return info.getLongNumber().replaceAll("!", ".")+";"+info.getName();
				}
				return super.getText(obj);
			}
		});
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", this.editData.getOrgUnit().getId().toString()));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", this.editData.getOrgUnit().getId().toString()));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(true);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtCostEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtCostEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtCostEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtCostEntry.getColumn("amount").setRequired(true);
		
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < MarketCostTypeEnum.getEnumList().size(); i++){
        	combo.addItem(MarketCostTypeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtCostEntry.getColumn("type").setEditor(comboEditor);
		this.kdtCostEntry.getColumn("type").setRequired(true);
		
		this.kdtCostEntry.getColumn("canAmount").setEditor(amountEditor);
		this.kdtCostEntry.getColumn("canAmount").getStyleAttributes().setLocked(true);
		
		this.kdtEntry.getColumn("supplier").setRequired(true);
		
		this.kdtEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("amount").setRequired(true);
		
		
		this.kdtUnitEntry.getColumn("unit").setRequired(true);
		
		this.kdtUnitEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtUnitEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtUnitEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtUnitEntry.getColumn("amount").setRequired(true);
		
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filterItems.add(new FilterItemInfo("isSub",Boolean.FALSE));
		filterItems.add(new FilterItemInfo("orgUnit.id",this.editData.getOrgUnit().getId().toString()));
		
		view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtMp.setEntityViewInfo(view);
		
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnUpLoad = new KDWorkButton();
		KDWorkButton btnAgreementText = new KDWorkButton();
		KDWorkButton btnAttachment = new KDWorkButton();


		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		
		kDTextArea1.setText("无文本立项成本科目选择说明：\n" 
				+ "  1、事项明确的无文本立项，成本科目选择实际发生的费用科目；\n"
				+ "  2、事项不明确的无文本立项，成本科目选择二级科目下的“其他费用”科目；\n");
		kDTextArea1.setEnabled(false);
		
		this.txtDescription.setRequired(true);
		
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.BIGRANGE);
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.SMALLRANGE);
    }
    
    public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				tblAttachement.checkParsed();
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("seq").setValue(attachment.getAttachID());
					row.getCell("name").setValue(attachment.getName());
					row.getCell("date").setValue(attachment.getCreateTime());
					row.getCell("type").setValue(boaInfo.getAssoType());
				}
			}
		}
	}
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MarketProjectFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("CU.*");
    	sic.add("state");
    	sic.add("source");
    	return sic;
    }
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.storeFields();
		this.verifyInputForSubmint();
		UserInfo u=SysContext.getSysContext().getCurrentUserInfo();
		if(this.editData.getSourceFunction()==null&&u.getPerson()!=null&&!isBillInWorkflow(this.editData.getId().toString())){
			this.editData.setOaPosition(null);
			Map map=ContractBillFactory.getRemoteInstance().getOAPosition(u.getNumber());
			if(map.size()>1){
				UIContext uiContext = new UIContext(this);
				uiContext.put("map", map);
				uiContext.put("editData", this.editData);
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(OaPositionUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        if(this.editData.getOaPosition()==null){
		        	return;
		        }
			}else if(map.size()==1){
				Iterator<Entry<String, String>> entries = map.entrySet().iterator();
				while(entries.hasNext()){
					Entry<String, String> entry = entries.next();
				    String key = entry.getKey();
				    String value = entry.getValue();
				    this.editData.setOaPosition(key+":"+value);
				}
			}else{
				FDCMsgBox.showWarning(this,"获取OA身份失败！");
	    		return;
			}
		}
		super.actionSubmit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
	}
	
	public boolean isBillInWorkflow(String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(MarketProjectSourceEnum.ZHLX.equals(this.editData.getSource())){
			FDCMsgBox.showWarning(this, "综合立项禁止反审批！");
			SysUtil.abort();
		}
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			this.actionACLine.setEnabled(false);
			this.actionRCLine.setEnabled(false);
			
			this.actionAULine.setEnabled(false);
			this.actionRULine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
			this.actionACLine.setEnabled(true);
			this.actionRCLine.setEnabled(true);
			
			this.actionAULine.setEnabled(true);
			this.actionRULine.setEnabled(true);
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		MarketProjectEntryInfo entry = new MarketProjectEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtEntry.removeRow(activeRowIndex);
	}
	public void actionACLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtCostEntry.addRow();
		MarketProjectCostEntryInfo entry = new MarketProjectCostEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRCLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtCostEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtCostEntry.removeRow(activeRowIndex);
	}
	public void actionAULine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtUnitEntry.addRow();
		MarketProjectUnitEntryInfo entry = new MarketProjectUnitEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRULine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtUnitEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtUnitEntry.removeRow(activeRowIndex);
	}
	protected void cbIsSupplier_stateChanged(ChangeEvent e) throws Exception {
		if(cbIsSupplier.isSelected()){
			this.contEntry.setVisible(true);
		}else{
			this.contEntry.setVisible(false);
			this.kdtEntry.removeRows();
		}
	}
			
	protected void cbIsSub_stateChanged(ChangeEvent e) throws Exception {
		if(cbIsSub.isSelected()){
			try {
				getHappenAmount();
			} catch (BOSException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.kdtCostEntry.getColumn("amount").getStyleAttributes().setLocked(true);
			this.contMp.setVisible(true);
			this.prmtMp.setRequired(true);
			this.kdtCostEntry.getColumn("canAmount").getStyleAttributes().setHided(false);
			this.kdtCostEntry.getColumn("type").getStyleAttributes().setHided(true);
			
		}else{
			this.prmtMp.setValue(null);
			for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
				this.kdtCostEntry.getRow(i).getCell("amount").setValue(null);
			}
			this.kdtCostEntry.getColumn("amount").getStyleAttributes().setLocked(false);
			this.contMp.setVisible(false);
			this.prmtMp.setRequired(false);
			
			this.kdtCostEntry.getColumn("canAmount").getStyleAttributes().setHided(true);
			this.kdtCostEntry.getColumn("type").getStyleAttributes().setHided(false);
		}
	}
//	
//	提交OA审批=========================================W========A=========T==========S=========O=========N===============================
//	
//	@Override
//	public void submitToOA_actionPerformed(ActionEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super.submitToOA_actionPerformed(e);
////		返回体
//		JSONObject returnVO = new JSONObject();
//		
//		String number=this.editData.getNumber().toString();
//		if(number!=null && number!="") { returnVO.put("number", number); }
//		
//		String name=this.editData.getName().toString();
//		if(name!=null && name!="") { returnVO.put("name", name); }
//		
//		String org=this.editData.getOrgUnit().toString();
//		if(org!=null && org!="") { returnVO.put("org", org); }
//		
//		String bizDate=this.editData.getBizDate().toString();
//		if(bizDate!=null && bizDate!="") { returnVO.put("bizDate", bizDate); }
//		
//		String amount=this.editData.getAmount().toString();
//		if(amount!=null && amount!="") { returnVO.put("amount", amount); }
//		
//		String projectName=this.editData.getSellProjecttxt().toString();
//		if(projectName!=null && projectName!="") { returnVO.put("projectName", projectName); }
//		
//		String nw=this.editData.getNw().toString();
//		if(nw!=null && nw!="") { returnVO.put("nw", nw); }
//
//		Boolean bol=this.editData.isIsSub();
//		if(bol==true) {String mp="true"; returnVO.put("mp", mp);}
//
//		Boolean bole=this.editData.isIsJT();
//		if(bole==true) {String jt="true"; returnVO.put("jt", jt);}
//		
//		String creator = this.editData.getCreator().getName();
//		returnVO.put("creator", creator == null ? null : creator);
//		
//		Timestamp createtime = this.editData.getCreateTime();
//		if(createtime!=null){
//		Date createTime = new Date(createtime.getTime());
//		returnVO.put("createTime", createTime);
//		}
//		
//		String auditor = this.editData.getAuditor().getName();
//		returnVO.put("auditor", auditor == null ? null : auditor);
//		
//		Date auditTime = this.editData.getAuditTime();
//		returnVO.put("auditTime", auditTime == null ? null : auditTime);
//		
////		费用归属集合
//		JSONArray costArray = new JSONArray();
//		JSONObject cost = new JSONObject(); 
//		MarketProjectCostEntryCollection costs = this.editData.getCostEntry();
//		if(costs.size()>0){
//			for(int i=0; i<costs.size(); i++){
//				MarketProjectCostEntryInfo costEntry = costs.get(i);
//				if(costEntry!=null){
//					if(costEntry.getCostAccount()!=null){
//						String costAccount=costEntry.getCostAccount().getName().toString();
//						if(costAccount!=null && costAccount!="") { cost.put("costAccount", costAccount); }
//					}
//					BigDecimal costAmount = costEntry.getAmount();
//					if(costAmount!=null) { cost.put("costAmount", costAmount.toString()); }
//					
//					String type = costEntry.getType().getName().toString();
//					if(type!=null && type!="") { cost.put("controlBill", type); }
//				}
//				costArray.add(cost);
//			}
//			returnVO.put("costEntry", costArray);
//		}
//		
////		比价单位
//		JSONArray unitArray = new JSONArray();
//		JSONObject unit = new JSONObject(); 
//		MarketProjectUnitEntryCollection units = this.editData.getUnitEntry();
//		if(units.size()>0){
//			for(int i=0; i<units.size(); i++){
//				MarketProjectUnitEntryInfo unitEntry = units.get(i);
//				if(unitEntry!=null){
//					String un = unitEntry.getUnit();
//					if(un!=null && un!="") { unit.put("unit", un); }
//					
//					BigDecimal unitAmount = unitEntry.getAmount();
//					if(unitAmount!=null){ unit.put("unitAmount", unitAmount.toString()); }
//					
//					String remark = unitEntry.getRemark();
//					if(remark!=null && remark!=""){ unit.put("unitRemark", remark); }
//				}
//				unitArray.add(unit);
//			}
//			returnVO.put("unitArray",unitArray);
//		}
//		
////		签约单位明确
//		JSONArray entryArray = new JSONArray();
//		JSONObject entry = new JSONObject(); 
//		MarketProjectEntryCollection entrys = this.editData.getEntry();
//		if(entrys.size()>0){
//			for(int i=0;i<entrys.size();i++){
//				MarketProjectEntryInfo en = entrys.get(i);
//				SupplierInfo supplier = en.getSupplier();
//				if(supplier!=null) { entry.put("supplier", supplier.getName()); }
//				
//				BigDecimal enAmount = en.getAmount();
//				if(enAmount!=null) { entry.put("enAmount", enAmount); }
//				
//				entry.put("remark", en.getRemark()==null ? null : en.getRemark());
//				
//				entryArray.add(entry);
//			}
//			returnVO.put("entryArray", entryArray);
//		}
////		附件  获取附件地址返回
		
		
//	}
//	===============================================================================================================================
	protected void prmtMp_dataChanged(DataChangeEvent e) throws Exception {
		try {
			getHappenAmount();
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	public void loadFields() {
		this.kdtEntry.checkParsed();
		this.kdtCostEntry.checkParsed();
		this.kdtUnitEntry.checkParsed();
		super.loadFields();
		if(cbIsSupplier.isSelected()){
			this.contEntry.setVisible(true);
		}else{
			this.contEntry.setVisible(false);
		}
		if(cbIsSub.isSelected()){
			try {
				getHappenAmount();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.kdtCostEntry.getColumn("amount").getStyleAttributes().setLocked(true);
			this.contMp.setVisible(true);
			this.prmtMp.setRequired(true);
			
			this.kdtCostEntry.getColumn("type").getStyleAttributes().setHided(true);
			this.kdtCostEntry.getColumn("canAmount").getStyleAttributes().setHided(false);
		}else{
			this.kdtCostEntry.getColumn("amount").getStyleAttributes().setLocked(false);
			this.contMp.setVisible(false);
			this.prmtMp.setRequired(false);
			
			this.kdtCostEntry.getColumn("type").getStyleAttributes().setHided(false);
			this.kdtCostEntry.getColumn("canAmount").getStyleAttributes().setHided(true);
		}
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	protected IObjectValue createNewData() {
		MarketProjectInfo info=new MarketProjectInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit((FullOrgUnitInfo) this.getUIContext().get("org"));
		info.setSource(MarketProjectSourceEnum.DXLX);
		return info;
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(MarketProjectSourceEnum.ZHLX.equals(this.editData.getSource())){
			FDCMsgBox.showWarning(this, "综合立项禁止修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	//zhs OA
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	String id = this.editData.getId().toString();
    	MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
	
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		if(this.cbIsSub.isSelected()){
			MarketProjectInfo info=(MarketProjectInfo) this.prmtMp.getValue();
			if(info!=null){
				MarketProjectCollection col=null;
				if(this.editData.getId()!=null){
					col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where isSub=1 and mp.id='"+info.getId().toString()+"' and id!='"+this.editData.getId()+"'");
				}else{
					col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where isSub=1 and mp.id='"+info.getId().toString()+"'");
				}
				if(col.size()>0){
					FDCMsgBox.showWarning(this,"该费用已有负立项，不能重复发起！");
					SysUtil.abort();
				}
			}
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		if(this.editData.getId()!=null){
			MarketProjectCollection col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where name='"+this.txtName.getText()+"' and id!='"+this.editData.getId()+"'");
			if(col.size()>0){
				FDCMsgBox.showWarning(this,"立项事项重名，请修改后再提交！");
				SysUtil.abort();
			}
		}else{
			MarketProjectCollection col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where name='"+this.txtName.getText()+"'");
			if(col.size()>0){
				FDCMsgBox.showWarning(this,"立项事项重名，请修改后再提交！");
				SysUtil.abort();
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.pkBizDate.getSqlDate());
		int year=cal.get(Calendar.YEAR);
		
		MarketYearProjectCollection yearCol=MarketYearProjectFactory.getRemoteInstance().getMarketYearProjectCollection("select state,name from where year="+year+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"' order by version desc");
		if(yearCol.size()==0||!yearCol.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
			FDCMsgBox.showWarning(this,"营销年度预算未审批通过！");
			SysUtil.abort();
		}
		
		
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDescription,"立项说明");
		
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbNw);
		Date thisDate=new Date();
		Date bizDate=(Date) this.pkBizDate.getValue();
		if(!this.cbIsSub.isSelected()&&FDCDateHelper.dateDiff(FDCDateHelper.getDayBegin(thisDate), bizDate)<0){
			FDCMsgBox.showWarning(this,"事项预估发生日期不允许小于系统当前日期！");
			SysUtil.abort();
		}
		
		
		if(this.kdtCostEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"费用归属不能为空！");
			SysUtil.abort();
		}
		if(!this.cbIsSub.isSelected()){
			if(this.kdtUnitEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"比价单位不能为空！");
				SysUtil.abort();
			}
		}
		if(this.cbIsSupplier.isSelected()){
			if(this.kdtEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"签约单位不能为空！");
				SysUtil.abort();
			}
		}
		Set costAccount=new HashSet();
		for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
			if(this.kdtCostEntry.getRow(i).getCell("costAccount").getValue()==null){
				FDCMsgBox.showWarning(this,"成本科目不能为空！");
				SysUtil.abort();
			}
			if(this.kdtCostEntry.getRow(i).getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"金额不能为空！");
				SysUtil.abort();
			}
			if(!this.cbIsSub.isSelected()&&((BigDecimal)this.kdtCostEntry.getRow(i).getCell("amount").getValue()).compareTo(FDCHelper.ZERO)<=0){
				FDCMsgBox.showWarning(this,"金额必须大于0！");
				SysUtil.abort();
			}
			if(!this.cbIsSub.isSelected()){
				if(this.kdtCostEntry.getRow(i).getCell("type").getValue()==null){
					FDCMsgBox.showWarning(this,"控制单据不能为空！");
					SysUtil.abort();
				}
			}
			CostAccountInfo cost=(CostAccountInfo) this.kdtCostEntry.getRow(i).getCell("costAccount").getValue();
			costAccount.add(cost.getId().toString());
		}
		for(int i=0;i<this.kdtUnitEntry.getRowCount();i++){
			if(this.kdtUnitEntry.getRow(i).getCell("unit").getValue()==null){
				FDCMsgBox.showWarning(this,"比价单位不能为空！");
				SysUtil.abort();
			}
			if(this.kdtUnitEntry.getRow(i).getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"报价不能为空！");
				SysUtil.abort();
			}
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("supplier").getValue()==null){
				FDCMsgBox.showWarning(this,"签约单位不能为空！");
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"商定价不能为空！");
				SysUtil.abort();
			}
		}
		if(costAccount.size()!=this.kdtCostEntry.getRowCount()){
			 FDCMsgBox.showWarning(this,"费用归属存在重复科目！");
			 SysUtil.abort();
		}
		
		for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
			CostAccountInfo caInfo=(CostAccountInfo) this.kdtCostEntry.getRow(i).getCell("costAccount").getValue();
			BigDecimal total=getCostAccountAmount(caInfo.getId().toString(),String.valueOf(year));
			BigDecimal yearAmount=getYearAmount(caInfo.getId().toString(),String.valueOf(year));
			if(yearAmount==null){
				yearAmount=FDCHelper.ZERO;
			}
			if(FDCHelper.add(total, this.kdtCostEntry.getRow(i).getCell("amount").getValue()).compareTo(yearAmount)>0){
				 FDCMsgBox.showWarning(this,"科目："+caInfo.getName()+" 超出年度预算！");
				 SysUtil.abort();
			}
		}
		if(this.cbIsSub.isSelected()){
			for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
				CostAccountInfo caInfo=(CostAccountInfo) this.kdtCostEntry.getRow(i).getCell("costAccount").getValue();
				MarketProjectInfo mp=(MarketProjectInfo) this.prmtMp.getValue();
				BigDecimal total=getMpAmount(mp.getId().toString(),caInfo.getId().toString());
				BigDecimal happen=getHappenAmount(mp.getId().toString(),caInfo.getId().toString());
				if(happen==null){
					happen=FDCHelper.ZERO;
				}
				if(FDCHelper.add(total, this.kdtCostEntry.getRow(i).getCell("amount").getValue()).compareTo(happen)<0){
					 FDCMsgBox.showWarning(this,"科目："+caInfo.getName()+" 超出立项已发生！");
					 SysUtil.abort();
				}
			}
		}
		if(this.cbIsSub.isSelected()){
			MarketProjectInfo info=(MarketProjectInfo) this.prmtMp.getValue();
			if(info!=null){
				MarketProjectCollection col=null;
				if(this.editData.getId()!=null){
					col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where isSub=1 and mp.id='"+info.getId().toString()+"' and id!='"+this.editData.getId()+"'");
				}else{
					col=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select * from where isSub=1 and mp.id='"+info.getId().toString()+"'");
				}
				if(col.size()>0){
					FDCMsgBox.showWarning(this,"该费用已有负立项，不能重复发起！");
					SysUtil.abort();
				}
			}
		}
	}
	public BigDecimal getMpAmount(String marketProjectId,String costAccountId) throws SQLException, BOSException{
		StringBuilder sql = new StringBuilder();
		sql.append("  /*dialect*/  select entry.famount amount from T_CON_MarketProjectCostEntry entry left join T_CON_MarketProject head on head.fid=entry.fheadid");
		sql.append(" where entry.fcostaccountid='"+costAccountId+"' and head.fid='"+marketProjectId+"'");
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	public BigDecimal getHappenAmount(String marketProjectId,String costAccountId) throws SQLException, BOSException{
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(t.famount) amount from (select con.fmarketProjectId,con.FMpCostAccountId,entry.famount from");
		sql.append(" t_con_contractbill con left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate!='1SAVED') t on t.fcontractbillid=con.fid left join T_CON_MarketProjectCostEntry entry on entry.fcostAccountid=con.FMpCostAccountId and con.fmarketProjectId=entry.fheadid  where con.fstate!='1SAVED'");
    	
		sql.append(" union all select con.fmarketProjectId,con.FMpCostAccountId,con.famount from");
		sql.append(" T_CON_ContractWithoutText con where con.fstate!='1SAVED') t where t.FMpCostAccountId='"+costAccountId+"' and t.fmarketProjectId='"+marketProjectId+"' group by t.FMpCostAccountId,fmarketProjectId ");
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	public BigDecimal getYearAmount(String costAccountId,String year) throws SQLException, BOSException{
		StringBuilder sql = new StringBuilder();
		sql.append(" select sum(entry.famount) amount from T_CON_MarketYearProjectEntry entry left join T_CON_MarketYearProject head on head.fid=entry.fheadid");
		sql.append(" where entry.fcostaccountid='"+costAccountId+"' and head.fyear="+year+" and head.FIsLatest=1");
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	public BigDecimal getCostAccountAmount(String costAccountId,String year) throws BOSException, SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("  /*dialect*/  select sum(case when t.famount is null then entry.famount else t.famount end) amount from T_CON_MarketProjectCostEntry entry left join T_CON_MarketProject head on head.fid=entry.fheadid");
		sql.append(" left join (select (case when con.fhasSettled = 1 then t.fsettleprice else con.famount end)famount,FMarketProjectId,FMpCostAccountId from t_con_contractBill con left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid where fstate='4AUDITTED')t on t.FMarketProjectId=head.fid and t.FMpCostAccountId=entry.fcostaccountid");
		sql.append(" where entry.fcostaccountid='"+costAccountId+"' and to_char(head.fbizDate,'yyyy')='"+year+"' and head.fstate!='1SAVED'");
		if(this.editData.getId()!=null){
			sql.append(" and head.fid!='"+this.editData.getId()+"'");
		}
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	public void storeFields() {
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
			amount=FDCHelper.add(amount,this.kdtCostEntry.getRow(i).getCell("amount").getValue());
		}
		this.txtAmount.setValue(amount);
		super.storeFields();
	}
	protected void kdtCostEntry_editStopped(KDTEditEvent e) throws Exception {
		 IRow r = this.kdtCostEntry.getRow(e.getRowIndex());
		 int colIndex = e.getColIndex();
		 if(colIndex == this.kdtCostEntry.getColumnIndex("amount")){
			 BigDecimal amount=FDCHelper.ZERO;
			 for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
				 amount=FDCHelper.add(amount,this.kdtCostEntry.getRow(i).getCell("amount").getValue());
			 }
			 this.txtAmount.setValue(amount);
			 
		 }
		 if(this.cbIsSub.isSelected()&&colIndex == this.kdtCostEntry.getColumnIndex("costAccount")){
			 CostAccountInfo caInfo=(CostAccountInfo) r.getCell("costAccount").getValue();
			 MarketProjectInfo mp=(MarketProjectInfo) this.prmtMp.getValue();
			 if(caInfo!=null&&mp!=null){
				 BigDecimal amount=FDCHelper.subtract(getMpAmount(mp.getId().toString(), caInfo.getId().toString()), getHappenAmount(mp.getId().toString(),caInfo.getId().toString()));
				 r.getCell("canAmount").setValue(amount);
				 r.getCell("amount").setValue(amount.negate());	 
			 }
		 }
	}
	private void getHappenAmount() throws BOSException, SQLException{
		for(int i=0;i<this.kdtCostEntry.getRowCount();i++){
			IRow r = this.kdtCostEntry.getRow(i);
			CostAccountInfo caInfo=(CostAccountInfo) r.getCell("costAccount").getValue();
			MarketProjectInfo mp=(MarketProjectInfo) this.prmtMp.getValue();
			if(caInfo!=null&&mp!=null){
				 BigDecimal amount=FDCHelper.subtract(getMpAmount(mp.getId().toString(), caInfo.getId().toString()), getHappenAmount(mp.getId().toString(),caInfo.getId().toString()));
				 r.getCell("canAmount").setValue(amount);
				 r.getCell("amount").setValue(amount.negate());
			}
		}
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
		{
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
			
		}
}
}