/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.IBOTRelation;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.spi.SPInfo;
import com.kingdee.bos.spi.SPManager;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.client.BOTMappingSelectUI;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonFilterPanel;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.dap.DAPTransImpl;
import com.kingdee.eas.base.dap.DAPTransformerFactory;
import com.kingdee.eas.base.dap.DAPVoucherTypeEnum;
import com.kingdee.eas.base.dap.IDAPTransformer;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentFullListUI;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.client.OtherBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TenancyImport;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.VoucherCollection;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.client.VoucherEditUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.IBatchOrgListBiz;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.MultiOrgBatchExceptionInfo;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IDAPBillTrans;
import com.kingdee.eas.framework.client.IDAPMultiOrgBillTrans;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.framework.client.IMultiOrgBizInfo;
import com.kingdee.eas.framework.client.context.IDelegationSupport;
import com.kingdee.eas.framework.query.resource.QuickFilterResources;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class PayRequestFullListUI extends AbstractPayRequestFullListUI implements IBatchOrgListBiz
{
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;   
	
	private Map proLongNameMap=new HashMap();
	 IUIWindow uiWindow=null;
	/**
	 * output class constructor
	 */
	public PayRequestFullListUI() throws Exception {
		super();
	}
	protected boolean isShowAttachmentAction() {
		return false;
	}
	
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	    	
    	super.actionView_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
    {
    	super.prepareUIContext(uiContext,e);   	
    	
    	uiContext.put("PayRequestFullListUI",Boolean.TRUE);
    }
    
	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		String id=this.getSelectedKeyValue();
		PayRequestBillInfo info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id));
		if(PayReqUtils.isContractBill(info.getContractId())){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", info.getContractId());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(ContractBillEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", info.getContractId());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(ContractWithoutTextEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}
	}
    
    
	public void actionClose_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasClosed");
			sel.add("state");
			sel.add("bgEntry.*");
			sel.add("originalAmount");
			
			PayRequestBillInfo info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sel);
			
			if(!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this,"��ǰ���ݵ�״̬����ִ�йرղ�����");
				SysUtil.abort();
			}
			BigDecimal total=FDCHelper.ZERO;
			for(int j=0;j<info.getBgEntry().size();j++){
				if(info.getBgEntry().get(j).getActPayAmount()!=null){
					total=total.add(info.getBgEntry().get(j).getActPayAmount());
				}
			}
			if(total.compareTo(info.getOriginalAmount())==0){
				FDCMsgBox.showWarning(this,"��ǰ�����Ѿ�������ϣ�����ִ�йرղ�����");
				SysUtil.abort();
			}
			if (info.isHasClosed()) {
				FDCMsgBox.showWarning(this, "��ǰ�����Ѿ��رգ�����ִ�йرղ�����");
				SysUtil.abort();
			}
		}
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			PayRequestBillFactory.getRemoteInstance().close(new ObjectUuidPK(id));
		}
		showOprtOKMsgAndRefresh();
	}
	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasClosed");
			PayRequestBillInfo info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sel);
			
			if (!info.isHasClosed()) {
				FDCMsgBox.showWarning(this, "��ǰ����δ�رգ�����ִ�з��رղ�����");
				SysUtil.abort();
			}
		}
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			PayRequestBillFactory.getRemoteInstance().unClose(new ObjectUuidPK(id));
		}
		showOprtOKMsgAndRefresh();
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return PayRequestBillEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		auditMap.clear();
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"latestPrice","payAmount","amount","srcAmount","deduct","guerden","actPaiedLocAmount"});
//		FDCClientHelper.initTable(tblMain);
		FDCHelper.formatTableNumber(this.tblMain, "srcOriginalAmount");
		FDCHelper.formatTableNumber(this.tblMain, "srcAmount");
		FDCHelper.formatTableNumber(this.tblMain, "deduct");
		FDCHelper.formatTableNumber(this.tblMain, "guerden");
		FDCHelper.formatTableNumber(this.tblMain, "actPaiedAmount");
		FDCHelper.formatTableNumber(this.tblMain, "actPaiedLocAmount");
		FDCHelper.formatTableNumber(this.tblMain, "payAmount");
		FDCHelper.formatTableNumber(this.tblMain, "latestPrice");
		
		ClientHelper.getFootRow(tblMain, new String[]{"payAmount","amount","srcAmount","deduct","guerden","actPaiedLocAmount"});
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if(viewInfo.getSorter().size()==0){
			viewInfo.getSorter().add(new SorterItemInfo("bookedDate"));
		}else if(viewInfo.getSorter().size()==1){
			viewInfo.getSorter().clear();
			viewInfo.getSorter().add(new SorterItemInfo("bookedDate"));
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return PayRequestBillFactory.getRemoteInstance();
	}

	/****
	 * ��commonFilterPanel���Զ���ļ����¼�
	 * 
	 * @param panel
	 */
	public void addKDLisener(final CommonFilterPanel panel){
		if(panel==null || panel.getKdtTable()==null){
    		return ;
    	}
		/***
    	 * �����������е�����
    	 */
        panel.getKdtTable().addKDTEditListener(new KDTEditListener(){

			public void editStarting(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				int ri = e.getRowIndex();
				 if (e.getColIndex() == 3){
					 if(panel==null || panel.getKdtTable()==null || panel.getKdtTable().getCell(ri, 3)==null ||
							 panel.getKdtTable().getCell(ri, 3).getEditor() == null){
	                		return ;
					 }
					 if(panel.getKdtTable().getCell(ri, 3).getEditor().getComponent() instanceof KDComboBox){
						 KDComboBox comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 3).getEditor().getComponent();
						 if(comBox != null){
							 for(int i=comBox.getItemCount()-1;i>1;i--){
								 if(comBox.getItemAt(i)!=null && comBox.getItemAt(i) instanceof FDCBillStateEnum){
									 FDCBillStateEnum state = (FDCBillStateEnum)comBox.getItemAt(i);
									 if(state.equals(FDCBillStateEnum.VISA)||
											 state.equals(FDCBillStateEnum.ANNOUNCE)||
											 state.equals(FDCBillStateEnum.CANCEL)){
										 comBox.removeItemAt(i);
									 }
								 }
							 }
						 }
					 }
					 
				 }
			}

			public void editStarted(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				
			}

			public void editValueChanged(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				
			}

			public void editStopping(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				
			}

			public void editStopped(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				
			}

			public void editCanceled(KDTEditEvent e) {
				// TODO �Զ����ɷ������
				
			}
			
		});
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		try {
			commonQueryDialog.init();
			commonQueryDialog.getCommonQueryParam().setDirty(false);
		} catch (UIException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		addKDLisener(commonQueryDialog.getCommonFilterPanel());
		
		
		
/*		
		IPromptBoxFactory factory=new DefaultPromptBoxFactory(){
    		public KDPromptBox create(String queryName,
    				EntityObjectInfo entity, String propertyName)
    		{
    			// TODO Auto-generated method stub
    			return super.create(queryName, entity, propertyName);
    		}
    		public KDPromptBox create(String queryName, QueryInfo mainQuery,
    				String queryFieldName)
    		{
    			final KDBizPromptBox f7 = (KDBizPromptBox)super.create(queryName, mainQuery, queryFieldName);
    			if(queryName.equalsIgnoreCase("com.kingdee.eas.fdc.contract.app.ContractF7Query")){
					f7.addSelectorListener(new SelectorListener(){
						public void willShow(SelectorEvent e)
						{
		    				f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view=new EntityViewInfo();
							FilterInfo filter=new FilterInfo();
//							FilterItemCollection filterItems = filter.getFilterItems();
							
//							filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
							view.setFilter(filter);
//							f7.setEntityViewInfo(null);
							f7.setEntityViewInfo(view);
							
						}
					});

    			}
				return f7;
    		}
    	};
		
    	commonQueryDialog.setPromptBoxFactory(factory);
*/
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PayRequestFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		if(this.getUIContext().get("filter")!=null){
			return false;
		}
		if(getUIContext().get("MyFilter") instanceof FilterInfo){
			return false;
		}else
			return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		FDCClientHelper.initTableListener(tblMain,this);
	
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		
		if(auditMap==null){
			auditMap=new HashMap();
		}
		
		/**
		 * ��԰Ҫ���ڸ������뵥��ʱ���͸������뵥��ѯ�������һ�� "�Ƿ���ڸ���"
		 * ������ڸ�������䵽��������  by Cassiel_peng
		 */
		if(attachMap==null){
			attachMap=new HashMap();
		}
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				if(attachMap==null){
					attachMap=new HashMap();
				}
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					String idkey = row.getCell("id").getValue().toString();
					if(attachMap.containsKey(idkey)){
							row.getCell("hasAttachment").setValue(Boolean.TRUE);
					}else{
						row.getCell("hasAttachment").setValue(Boolean.FALSE);
					}
					if(auditMap.containsKey(idkey)){
						//Add by zhiyuan_tang 2010/07/29 ֻ�ڵ�����������������״̬ʱ�Ÿ������˺��������ڸ�ֵ
						if ("������".equals(row.getCell("state").getValue().toString()) || "������".equals(row.getCell("state").getValue().toString())) {
							MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
							row.getCell("auditor.name").setValue(info.getCreator().getName());
							row.getCell("auditDate").setValue(info.getCreateTime());
						}
					}
					BigDecimal originalAmount=(BigDecimal) row.getCell("originalAmount").getValue();
					BigDecimal payAmount=(BigDecimal) row.getCell("payAmount").getValue();
					if(FDCHelper.subtract(originalAmount, payAmount).compareTo(new BigDecimal(0))==0){
						row.getStyleAttributes().setBackground(new Color(128,255,128));
					}
					tblMain.getColumn("originalAmount").getStyleAttributes().setBackground(new Color(192,192,192));
					tblMain.getColumn("payAmount").getStyleAttributes().setBackground(new Color(192,192,192));
				}
			}
		});
		
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setEnabled(false);
		//this.actionEdit.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
//		this.actionLocate.setEnabled(false);
//		this.actionLocate.setVisible(false);
		//this.actionCreateTo.setEnabled(false);
		//this.actionCreateTo.setVisible(false);
		//this.actionTraceDown.setEnabled(false);
		//this.actionTraceDown.setVisible(false);
		//new update by renliang 2010-5-26
		this.actionCreateTo.setEnabled(true);
		this.actionCreateTo.setVisible(true);
		this.actionTraceDown.setEnabled(true);
		this.actionTraceDown.setVisible(true);
		this.actionTraceUp.setEnabled(true);
		this.actionTraceUp.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionEdit.setVisible(true);
		
		//this.actionTraceUp.setEnabled(false);
		///this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setEnabled(false);
		this.actionCopyTo.setVisible(false);

		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		actionClose.setEnabled(true);
		actionUnClose.setEnabled(true);
		
		menuEdit.setVisible(true);
		menuEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.menuItemEdit.setVisible(true);
		
		this.menuBiz.setEnabled(true);
		this.menuBiz.setVisible(true);
		
		this.actionAuditResult.setEnabled(true);
		this.actionAuditResult.setVisible(true);
		
		
		//new add by renliang 2010-5-26
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		
		FDCClientHelper.initTable(tblMain);
		
		this.actionViewBg.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		
		if(this.getUIContext().get("filter")!=null){
			this.toolBar.setVisible(false);
			mainQuery=new EntityViewInfo();
			mainQuery.setFilter((FilterInfo) this.getUIContext().get("filter"));
		}
		this.actionImportData.setVisible(true);
		
		KDWorkButton btnMultiSubmit=new KDWorkButton();
		btnMultiSubmit.setText("�����ύ");
		btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.toolBar.add(btnMultiSubmit);
		btnMultiSubmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	btnMultiSubmit_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
	        });
		btnMultiSubmit.setVisible(false);
		if(SysContext.getSysContext().getCurrentUserInfo().getPerson()==null){
			this.actionRemove.setEnabled(true);
			this.actionRemove.setVisible(true);
		}
		
		this.actionClose.setVisible(false);
		this.actionUnClose.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionViewBg.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.btnViewVoucher.setIcon(this.btnTraceDown.getIcon());
		
		if(SPManager.isSPInstalled("DAPTransImpl"))
        {
/* <-MISALIGNED-> */ /* 558*/                SPInfo spInfo = SPManager.getInstance().getSeviceProvider("DAPTransImpl");
/* <-MISALIGNED-> */ /* 559*/                Constructor constructor = spInfo.getProviderClass().getConstructor(new Class[] {
/* <-MISALIGNED-> */ /* 559*/                    CoreBillListUI.class
            });
/* <-MISALIGNED-> */ /* 560*/                Object dapObject = constructor.newInstance(new Object[] {
/* <-MISALIGNED-> */ /* 560*/                    this
            });
/* <-MISALIGNED-> */ /* 561*/                dapTrans = (IDAPTrans)dapObject;
/* <-MISALIGNED-> */ /* 562*/                dapTrans.init();
        }
	}
	public void btnMultiSubmit_actionPerformed(ActionEvent e) {
		checkSelected();
		   Window win = SwingUtilities.getWindowAncestor(this);
	       LongTimeDialog dialog = null;
	       if(win instanceof Frame)
	           dialog = new LongTimeDialog((Frame)win);
	       else
	       if(win instanceof Dialog)
	           dialog = new LongTimeDialog((Dialog)win);
	       
	       dialog.setLongTimeTask(new ILongTimeTask() {
	           public Object exec()
	               throws Exception
	           {
	        	   TenancyImport.tenancyUpdata();
	        	   ArrayList id = getSelectedIdValues();
	        	   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   PayRequestBillEditUI ui=(PayRequestBillEditUI) UIFactoryHelper.initUIObject(PayRequestBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   FDCBillStateEnum state = ((PayRequestBillInfo)ui.getEditData()).getState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(FDCBillStateEnum.SAVED.equals(state)||FDCBillStateEnum.SUBMITTED.equals(state))){
        					MsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����ύ������");
        					SysUtil.abort();
        			   }
        			   ui.loadFields();
        			   ui.verifyInputForSubmint();
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
	               return new Boolean(true);
	           }
	           public void afterExec(Object result)
	               throws Exception
	           {
	        	   FDCMsgBox.showWarning("�����ɹ���");
	           }
	       });
       dialog.show();
       try {
		this.refreshList();
	} catch (Exception e1) {
		e1.printStackTrace();
	}
   }
	/**
	 * �ø������뵥�Ƿ���ڸ���  by Cassiel_peng
	 */
	private Map attachMap=new HashMap();
	//���������� ����ʱ�� by cassiel_peng 2010-05-26
	private Map auditMap = new HashMap();
	public void onGetRowSet(IRowSet rowSet) {
		try {
//			final BOSObjectType type = new ContractWithoutTextInfo().getBOSType();
			
			Set attachIds=new HashSet();
			rowSet.beforeFirst();
			while (rowSet.next()) {
				//��԰Ҫ���ڸ������뵥��ʱ���͸������뵥��ѯ�������һ�� "�Ƿ���ڸ���"  by Cassiel_peng
				String boID=rowSet.getString("id");
				attachIds.add(boID);
				
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
				
				//�ж���Դ
				String contractId = rowSet.getString("contractId");
				if(PayReqUtils.isContractBill(contractId)){
					rowSet.updateString("source","��ͬ");
				}else{
					rowSet.updateString("source","���ı�");
				}
				
				
				/*			����������ı���ͬɾ���������
				 * 
				 * 			�ı�PayRequestBillQuery��contractNumber��contractName����ѯ
				*/
/*				String contractId=rowSet.getString("contractId");
				BOSObjectType type = BOSUuid.read(contractId).getType();
				if(type.equals(new ContractBillInfo().getBOSType())){
					ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
					rowSet.updateString("contractNumber",info.getNumber());
					rowSet.updateString("contractName",info.getName());
				}else{
					ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
					rowSet.updateString("contractNumber",info.getNumber());
					rowSet.updateString("contractName",info.getName());
				}*/
//				//ɾ�����ı���ͬ�ڵı�֤,�ύ״̬�µ����뵥
//				String contractId=rowSet.getString("contractId");
//				if(contractId!=null&&BOSUuid.read(contractId).getType().equals(type)){
//					String state=rowSet.getString("state");
//					if(FDCBillStateEnum.SAVED.getAlias().equals(state)||FDCBillStateEnum.SUBMITTED.getAlias().equals(state)){
//						rowSet.deleteRow();
//					}
//				}
			}
			attachMap=PayReqTableHelper.handleAttachment(attachIds);
			auditMap=PayReqTableHelper.handleAuditPersonTime(attachIds);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rowSet.beforeFirst();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		super.onGetRowSet(rowSet);
	}
	protected String getEditUIModal() {
		// TODO �Զ����ɷ������
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
	private String getRes(String resName)
	{
		return PayReqUtils.getRes(resName);
	}
	protected void initWorkButton()
	{
		super.initWorkButton();
        actionClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_close"));
        actionUnClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_fclose"));   
	}
	
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		if(getUIContext().get("MyFilter") instanceof FilterInfo){
			FilterInfo myFilter=(FilterInfo)getUIContext().get("MyFilter");
			ev.setFilter(myFilter);
//			isNeedDefaultFilter=false;
			actionQuery.setVisible(false);
			actionAttachment.setVisible(false);
			actionPrint.setVisible(false);
			actionPrintPreview.setVisible(false);
			actionWorkFlowG.setVisible(false);
			menuWorkFlow.setVisible(false);
			return;
		}
		super.beforeExcutQuery(ev);
		
	}
	
	public void onShow() throws Exception {
		super.onShow();
/*		this.actionRemove.setEnabled(false);
		menuItemRemove.setEnabled(false);
		btnRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
		ActionMap actionMap2 = getActionMap();
		Object[] allKeys = actionMap2.allKeys();
		for(int i=0;i<allKeys.length;i++){
			if(actionMap2.get(allKeys[i]).equals(actionRemove)){
				actionMap2.remove(allKeys[i]);
			}
		}*/
//		getActionMap().get("EASRemove").setEnabled(false);
//		getActionMap().remove("EASRemove");
//		getActionMap().get("EASRemove").setEnabled(false);
	}
	
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
    	super.actionRemove_actionPerformed(e);
    }
    
    protected boolean checkBeforeRemove() throws Exception {
    	checkSelected();
    	
    	List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

    	if(idList==null || idList.size()==0){
    		return false;
    	}
    	
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = getRemoteInterface().getCoreBillBaseCollection(view);

		
		String[] states = getBillStateForEditOrRemove();
		
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if(billState.equals(states[i])) {
					pass = true;
				}
			}
			if(!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
			
			ContractClientUtils.checkContractBillRefForRemove(this, element.getId().toString());
		}
		
		return true;
    }
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }
    protected ICoreBillBase getRemoteInterface() throws BOSException {
		return (ICoreBillBase) PayRequestBillFactory.getRemoteInstance();
	}
	/**
     * �����Ƿ���ʾ�ϼ���
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
    /**
	 * ��������������by renliang 2010-5-26
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		// ��鵥��״̬
		
		 //super.actionAudit_actionPerformed(e);
		 
		checkBillState(new String[] { getStateForAudit() },
				"selectRightRowForAudit");

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if (idList != null && idList.size() > 0) {
			IPayRequestBill payRequestBill = (IPayRequestBill) getBizInterface();
			payRequestBill.audit(idList);
			// super.actionAudit_actionPerformed(e);

			showOprtOKMsgAndRefresh();
		} else {
			MsgBox.showWarning(this, "����ѡ���У�");
			SysUtil.abort();
		}

	}

	/**
	 * �����ӹ������ɷ��� by renliang 2010-5-26
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		
		//����״̬��check
		checkBeforeCreateTo();
		super.actionCreateTo_actionPerformed(e);
		
	}
	
	/**
     * 
     * @return
     */
    protected KDTable getBillListTable() {

		return this.tblMain;
	}
   
    /**
	 * 
	 * ��������鵱ǰ���ݵ�Table�Ƿ�ѡ����
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
    public void checkSelected(KDTable table) {
    	if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource
	                .getString(FrameWorkClientUtils.strResource
	                        + "Msg_MustSelected"));
	        SysUtil.abort();
	    }
    }
    
    /**
     * �������ɹ���״̬�ļ��
     * @throws UuidException 
     * @throws BOSException 
     * @throws EASBizException 
     */
	private void checkBeforeCreateTo() throws EASBizException, BOSException, UuidException {
		
		checkSelected(getBillListTable());

		// ״̬�Ƿ����ת��
		// �Ѿ����ɹ����
		String number = "";

		KDTable table = getBillListTable();
		ArrayList blocks = table.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();
			if(top!=bottom){
				MsgBox.showWarning(this, "ֻ��ѡ��һ���������뵥��¼���й������ɣ�");
				SysUtil.abort();
			}
			for (int rowIndex = top;rowIndex <= bottom; rowIndex++) {
				ICell cell = table.getRow(rowIndex).getCell(getKeyFieldName());

				// ��¼ѡ�еķ�¼ID
				if (cell.getValue() != null) {
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("state");
					selector.add("number");
					selector.add("hasClosed");
					selector.add("actPaiedLocAmount");
					selector.add("amount");
					String id = cell.getValue().toString();
					PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(id)), selector);
					if (!info.getState().equals(FDCBillStateEnum.AUDITTED) || info.isHasClosed()) {
						MsgBox.showWarning(this, PayReqUtils.getRes("canntCreatTo"));
						SysUtil.abort();
					}
					if(info.getActPaiedLocAmount()!=null&&info.getActPaiedLocAmount().compareTo(info.getAmount())>=0){
//						MsgBox.showWarning(this, "�����ɸ�����ܴ��������");
//						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * �������·����� by renliang 2010-5-26
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		
		checkSelected();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		String id = this.getSelectedKeyValue();
		filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", id));

		Map uiContxt = new HashMap();
		uiContxt.put(UIContext.OWNER, this);
		PaymentFullListUI.showUI(view, uiContxt);
//		super.actionTraceDown_actionPerformed(e);

	}

	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * 
	 * @author:renliang
	 * @date 2010-5-19
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
//		MsgBox.showWarning(this, "�����ɹ�,�������뵥�ѹرգ�");
		refreshList();
//		SysUtil.abort();
	}



	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * ����״̬�ļ��
	 * @param states
	 * @param res
	 * @throws Exception
	 */
	protected void checkBillState(String[] states, String res) throws Exception {

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if (idList == null) {
			MsgBox.showWarning(this, "��ѡ����");
			abort();
			return;
		}

		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();

			// ��鵥���Ƿ��ڹ�������
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!stateSet.contains(element
					.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	/**
	 * 
	 * ����������״̬�������ƣ������ṩȱʡʵ��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	protected String getBillStatePropertyName() {
		return "state";
	}
	
	/**
	 * �����޸Ĺ���
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	
	 /**
     * �޸�ǰ״̬�ļ��
     * @throws renliang
     * @date 2010-5-26
     */
    private void checkBeforeEdit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "��ѡ����");
			abort();
			return ;
		}
		
		if(idList.size()>1){
			MsgBox.showWarning(this, "��ѡ���˶��м�¼��������ѡ��");
			abort();
			return ;
		}
	
	   String id = idList.get(0).toString();
	   PayRequestBillInfo info =PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
	   
	   if(!info.getState().equals(FDCBillStateEnum.SAVED) && !info.getState().equals(FDCBillStateEnum.SUBMITTED)){
		   MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ��޸Ĳ�����");
			abort();
	   }
	   
    }
    
    protected String[] getLocateNames() {
    	 String[] locateNames = new String[12];
         locateNames[0] = "bookedDate";
         locateNames[1] = "number";
         locateNames[2] = "contractNumber";
         locateNames[3] = "contractName";
         locateNames[4] = "supplier.name";
         locateNames[5] = "payDate";
         locateNames[6] = "originalAmount";
         locateNames[7] = "amount";
         locateNames[8] = "creator.name";
         locateNames[9] = "createTime";
         locateNames[10] = "auditor.name";
         locateNames[11] = "auditDate";
         return locateNames;
    }
	public void actionViewBg_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		if(selectedRows.length>1){
			MsgBox.showWarning(this, "ֻ��ѡ��һ���������뵥��¼����Ԥ���ѯ��");
			SysUtil.abort();
		}
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("costedCompany.*");
			sel.add("costedDept.*");
			sel.add("bgEntry.bgItem.*");
			
			PayRequestBillInfo pay=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sel);
			if(pay!=null){
				Object[] bgItem=new Object[pay.getBgEntry().size()];
				for(int k=0;k<pay.getBgEntry().size();k++){
					bgItem[k]=pay.getBgEntry().get(k).getBgItem();
				}
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				RptParams param=new RptParams();
				if(pay.getCostedCompany()==null)
		        {
		            FDCMsgBox.showWarning("Ԥ��е���˾����Ϊ�գ�");
		            return;
		        }
		        if(pay.getCostedDept()==null)
		        {
		            FDCMsgBox.showWarning("Ԥ��е����Ų���Ϊ�գ�");
		            return;
		        }
		        if(bgItem.length==0)
		        {
		            FDCMsgBox.showWarning("Ԥ����Ŀ����Ϊ�գ�");
		            return;
		        }
		        if(uiWindow!=null){
					uiWindow.close();
				}
				param.setObject("costedCompany", pay.getCostedCompany());
				param.setObject("costedDept", pay.getCostedDept());
				param.setObject("bgItem", bgItem);
				uiContext.put("RPTFilter", param);
				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(AccActOnLoadBgGatherUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception{
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
	}
	protected ArrayList getImportParam()
    {	
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		param.setContextParam(hs);//
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	protected String getSolutionName(){
		return "eas.fdc.contract.PayRequestBill";
    }
    protected String getDatataskAlias(){
    	return "�������뵥";
    } 
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	PayRequestBillInfo info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id));
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
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	PayRequestBillInfo info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id));
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
    		super.actionAuditResult_actionPerformed(e);
    	}
	}
	public void actionViewVoucher_actionPerformed(ActionEvent e)throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}
	private IDAPTrans dapTrans = null;
	private boolean isDAPTrans;
	private boolean canVoucher;
	private void setCanVoucher(boolean canVoucher)
    {
/* <-MISALIGNED-> */ /* 164*/        this.canVoucher = canVoucher;
    }
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = new ArrayList();
		int selectRows[] = getTableSelectRows(tblMain);
		if(selectRows.length > 0)idList = getSelectedIdValues();
		CoreBillBaseCollection sourceBillCollection = getNewBillList();
		for(int i=0;i<sourceBillCollection.size();i++){
			PayRequestBillInfo payInfo=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(sourceBillCollection.get(i).getId()));
			if(payInfo.isFivouchered()){
				FDCMsgBox.showInfo("�õ����Ѿ�����ƾ֤��");
				SysUtil.abort();
			}
		}
		IDAPTransformer iDAPTransformer = DAPTransformerFactory.getRemoteInstance();
		BOTMappingCollection col=BOTMappingFactory.getRemoteInstance().getBOTMappingCollection("select *,extRule.* from where srcEntityName='C9A5A869' and destEntityName='2652E01E' and extRule.isEffected=1");
		if(col.size()>1){
			UIContext uiContext = new UIContext(this);
			uiContext.put("BOTMappings", col);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(BOTMappingSelectUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        BOTMappingInfo mpInfo=((BOTMappingSelectUI)uiWindow.getUIObject()).getSelectBotMappingInfo();
	        if(mpInfo!=null){
	        	iDAPTransformer.generateVoucher(sourceBillCollection,DAPVoucherTypeEnum.FIVoucher, new ObjectUuidPK(mpInfo.getId()));
	        	IBOTRelation iBOTRelation = BOTRelationFactory.getRemoteInstance();
	        	EntityViewInfo view=new EntityViewInfo();
	        	FilterInfo filter=new FilterInfo();
	        	filter.getFilterItems().add(new FilterItemInfo("srcObjectID",idList.get(0)));
	        	filter.getFilterItems().add(new FilterItemInfo("destEntityID","2652E01E"));
	        	view.setFilter(filter);
	        	BOTRelationCollection rotCol=iBOTRelation.getCollection(view);
	        	if(rotCol.size()>0){
	        		uiContext = new UIContext(this);
	    			uiContext.put("ID", rotCol.get(0).getDestObjectID());
	    	        uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
	    	        uiWindow = uiFactory.create(VoucherEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	    	        uiWindow.show();
	        	}
	        	this.refreshList();
	        }
		}else{
			IDAPBillTrans dapTransform = (IDAPBillTrans)dapTrans;
			dapTransform.init();
			actionVoucherByID(dapTransform, e);
		}
	}
	public String getMainBizOrgColumnName() {
		return "fullOrgUnit.id";
	}
	public String getMainBizOrgPropertyName() {
		return "fullOrgUnit.name";
	}
}