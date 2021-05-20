/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.MarketYearProjectCollection;
import com.kingdee.eas.fdc.contract.MarketYearProjectFactory;
import com.kingdee.eas.fdc.contract.ZHMarketProjectCollection;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectFactory;
import com.kingdee.eas.fdc.contract.ZHMarketProjectInfo;
import com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum;
import com.kingdee.eas.fdc.invite.client.CellTextRenderImpl;
import com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ZHMarketProjectEditUI extends AbstractZHMarketProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ZHMarketProjectEditUI.class);
    
    public ZHMarketProjectEditUI() throws Exception
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

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnDeleteRowinfo.setText("插入行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		
		this.kdtEntry.checkParsed();
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
		
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("supplier").setEditor(f7Editor);
		
		f7Box = new KDBizPromptBox(); 
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$longNumber$;$name$");
		f7Box.setEditFormat("$longNumber$;$name$");
		f7Box.setCommitFormat("$longNumber$;$name$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("costAccount").setEditor(f7Editor);
		this.kdtEntry.getColumn("costAccount").setRequired(true);
    	
		this.kdtEntry.getColumn("costAccount").setRenderer(new ObjectValueRender(){
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
		
		String formatString = "yyyy-MM-dd";
		this.kdtEntry.getColumn("bizDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("bizDate").setRequired(true);
		
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.kdtEntry.getColumn("bizDate").setEditor(dateEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(true);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("amount").setRequired(true);
		
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < MarketCostTypeEnum.getEnumList().size(); i++){
        	combo.addItem(MarketCostTypeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("type").setEditor(comboEditor);
		this.kdtEntry.getColumn("type").setRequired(true);
		
		
//		this.kdtEntry.getColumn("supplier").setRequired(true);
		
		this.kdtEntry.getColumn("unit").setRequired(true);
		
		this.kdtEntry.getColumn("sdAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("sdAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("sdAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//		this.kdtEntry.getColumn("sdAmount").setRequired(true);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnUpLoad = new KDWorkButton();
		KDWorkButton btnAgreementText = new KDWorkButton();
		KDWorkButton btnAttachment = new KDWorkButton();


		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		this.txtDescription.setRequired(true);
		
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.BIGRANGE);
		this.cbNw.removeItem(ContractTypeOrgTypeEnum.SMALLRANGE);
		
		this.kdtEntry.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.kdtEntry.getGroupManager().setGroup(true);
    	this.kdtEntry.getColumn("bizDate").setGroup(true);
    	this.kdtEntry.getGroupManager().group();
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
		return ZHMarketProjectFactory.getRemoteInstance();
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
    	return sic;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
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
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.actionILine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
		}
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(kdtEntry)) {
				row = kdtEntry.addRow();
				ZHMarketProjectEntryInfo entry = new ZHMarketProjectEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			} else {
				row = kdtEntry.addRow(top);
				ZHMarketProjectEntryInfo entry = new ZHMarketProjectEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
		} else {
			row = kdtEntry.addRow();
			ZHMarketProjectEntryInfo entry = new ZHMarketProjectEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			row.setUserObject(entry);
		}
	}

	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		ZHMarketProjectEntryInfo entry = new ZHMarketProjectEntryInfo();
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
			
	public void loadFields() {
		this.kdtEntry.checkParsed();
		super.loadFields();
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
		ZHMarketProjectInfo info=new ZHMarketProjectInfo();
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
		
		return info;
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
	}
	protected void verifyInputForSubmint() throws Exception {
		if(this.editData.getId()!=null){
			ZHMarketProjectCollection col=ZHMarketProjectFactory.getRemoteInstance().getZHMarketProjectCollection("select * from where name='"+this.txtName.getText()+"' and id!='"+this.editData.getId()+"'");
			if(col.size()>0){
				FDCMsgBox.showWarning(this,"综合立项事项重名，请修改后再提交！");
				SysUtil.abort();
			}
		}else{
			ZHMarketProjectCollection col=ZHMarketProjectFactory.getRemoteInstance().getZHMarketProjectCollection("select * from where name='"+this.txtName.getText()+"'");
			if(col.size()>0){
				FDCMsgBox.showWarning(this,"综合立项事项重名，请修改后再提交！");
				SysUtil.abort();
			}
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDescription,"立项说明");
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.cbNw);
		
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"立项事项明细不能为空！");
			SysUtil.abort();
		}
		Set costAccount=new HashSet();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("bizDate").getValue()==null){
				FDCMsgBox.showWarning(this,"事项预估发生时间不能为空！");
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("costAccount").getValue()==null){
				FDCMsgBox.showWarning(this,"成本科目不能为空！");
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"金额不能为空！");
				SysUtil.abort();
			}
			if(((BigDecimal)this.kdtEntry.getRow(i).getCell("amount").getValue()).compareTo(FDCHelper.ZERO)<=0){
				FDCMsgBox.showWarning(this,"金额必须大于0！");
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("type").getValue()==null){
				FDCMsgBox.showWarning(this,"控制单据不能为空！");
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("unit").getValue()==null){
				FDCMsgBox.showWarning(this,"比价单位不能为空！");
				SysUtil.abort();
			}
//			if(this.kdtEntry.getRow(i).getCell("supplier").getValue()==null){
//				FDCMsgBox.showWarning(this,"签约单位不能为空！");
//				SysUtil.abort();
//			}
//			if(this.kdtEntry.getRow(i).getCell("sdAmount").getValue()==null){
//				FDCMsgBox.showWarning(this,"商定价不能为空！");
//				SysUtil.abort();
//			}
			CostAccountInfo cost=(CostAccountInfo) this.kdtEntry.getRow(i).getCell("costAccount").getValue();
			costAccount.add(cost.getId().toString());
		}
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			 Date bizDate=(Date) this.kdtEntry.getRow(i).getCell("bizDate").getValue();
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(bizDate);
			 int year=cal.get(Calendar.YEAR);
			 
			CostAccountInfo caInfo=(CostAccountInfo) this.kdtEntry.getRow(i).getCell("costAccount").getValue();
			BigDecimal total=getCostAccountAmount(caInfo.getId().toString(),String.valueOf(year));
			BigDecimal yearAmount=getYearAmount(caInfo.getId().toString(),String.valueOf(year));
			if(yearAmount==null){
				yearAmount=FDCHelper.ZERO;
			}
			if(FDCHelper.add(total, this.kdtEntry.getRow(i).getCell("amount").getValue()).compareTo(yearAmount)>0){
				 FDCMsgBox.showWarning(this,"科目："+caInfo.getName()+" 超出年度预算！");
				 SysUtil.abort();
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
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("amount").getValue());
		}
		this.txtAmount.setValue(amount);
		super.storeFields();
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		 IRow r = this.kdtEntry.getRow(e.getRowIndex());
		 int colIndex = e.getColIndex();
		 if(colIndex == this.kdtEntry.getColumnIndex("amount")){
			 BigDecimal amount=FDCHelper.ZERO;
			 for(int i=0;i<this.kdtEntry.getRowCount();i++){
				 amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("amount").getValue());
			 }
			 this.txtAmount.setValue(amount);
		 }
		 if(colIndex == this.kdtEntry.getColumnIndex("bizDate")){
			 Date bizDate=(Date) this.kdtEntry.getRow(e.getRowIndex()).getCell("bizDate").getValue();
			 if(bizDate!=null){
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(bizDate);
				 int year=cal.get(Calendar.YEAR);
				
				 MarketYearProjectCollection yearCol=MarketYearProjectFactory.getRemoteInstance().getMarketYearProjectCollection("select state,name from where year="+year+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"' order by version desc");
				 if(yearCol.size()==0||!yearCol.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
					 FDCMsgBox.showWarning(this,"营销年度预算未审批通过！");
					 this.kdtEntry.getRow(e.getRowIndex()).getCell("bizDate").setValue(null);
					 SysUtil.abort();
				 }
				 
				 Date thisDate=new Date();
				 if(FDCDateHelper.dateDiff(FDCDateHelper.getDayBegin(thisDate), bizDate)<0){
					 FDCMsgBox.showWarning(this,"事项预估发生日期不允许小于系统当前日期！");
					 this.kdtEntry.getRow(e.getRowIndex()).getCell("bizDate").setValue(null);
					 SysUtil.abort();
				 }
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