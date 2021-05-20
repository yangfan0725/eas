/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.analysis.web.chart.GetChartServletHandler;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.ChooseInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseChooseEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseChooseEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.client.AgioPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			SupplierAppraiseTemplateEditUI.java				
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		����ģ��༭����
 *		
 * @author		
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierAppraiseTemplateEditUI extends AbstractSupplierAppraiseTemplateEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8937159002901505691L;

	private static final Logger logger = CoreUIObject.getLogger(SupplierAppraiseTemplateEditUI.class);
    /*
     * ����ά��
     */
    private final String TYPE_COL = "guideType";
    /*
     * ����ָ��
     */
    private final String NAME_COL = "guideName";
    /*
     * Ȩ��
     */
    private final String WEIGHT_COL = "weight";
    /*
     * ���ֱ�׼
     */
    private final String DESC_COL = "fullNum";
    /*
     * ״̬���к�
     */
    private int state = 0;
    
	private KDBizPromptBox F7NameCol = new KDBizPromptBox();
    
    /**
     * output class constructor
     */
    public SupplierAppraiseTemplateEditUI() throws Exception
    {
        super();
    }
	 /**
	     * @description		��ȡ��Դ�ļ�
	     * @author			
	     * @createDate		2010-12-9
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);

	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	for(int rowIndex=0;rowIndex<this.kdtGuideEnty.getRowCount();rowIndex++){
    		SupplierGuideEntryInfo entry=(SupplierGuideEntryInfo) this.kdtGuideEnty.getRow(rowIndex).getUserObject();
    		entry.getChooseEntry().clear();
    		
    		Object[] value=(Object[]) this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getValue();
    		if(value!=null&&value.length>0){
    			for(int j=0;j<value.length;j++){
    				SupplierAppraiseChooseEntryInfo chooseEntry=new SupplierAppraiseChooseEntryInfo();
    				chooseEntry.setChoose((ChooseInfo) value[j]);
    				entry.getChooseEntry().add(chooseEntry);
    			}
    		}
		}
        super.storeFields();
    }

    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {	
    	
    	if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,getSupplierResources("auditedNotEdit"));
    		SysUtil.abort();
    	}
    	deleteTrim();
        super.actionSave_actionPerformed(e);
        setColumMerger();
    }
     /**
         * @description		ȥ�ո�
         * @author			�㿭	
         * @createDate		2010-12-6
         * @param	
         * @return					
         *	
         * @version			EAS1.0
         * @see						
         */
    private void deleteTrim(){
    	if(!("".equals(txtNumber.getText().trim())) || null !=txtNumber.getText()){
    		txtNumber.setText(txtNumber.getText().trim());
    	}
    	if(!("".equals(txtName.getText().trim())) || null !=txtName.getText()){
    		txtName.setText(txtName.getText().trim());
    	}
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	BigDecimal big = getNumWeight();
		if(big.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		SysUtil.abort();
    	}
        super.actionSubmit_actionPerformed(e);
        if(state == 1){
        	editData = (SupplierAppraiseTemplateInfo) createNewData();
			loadFields();
			this.txtName.setText(null);
			this.txtNumber.setText(null);
			this.txtRemark.setText(null);
//			this.comboPhase.setSelectedIndex(-1);
			this.kdtGuideEnty.removeRows();
			this.btnSave.setEnabled(true);
			this.setOprtState(OprtState.ADDNEW);
        }
        setColumMerger();
        logger.info(e.getSource());
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        initEntyStyle();
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
        setFootRowSum();
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
        setFootRowSum();
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {

    	kdtGuideEnty.checkParsed();
    	if(getOprtState().equals(STATUS_ADDNEW)){
    		state = 1;
		}
    	/*
    	 * ��ʼ����¼��ʽ
    	 */
    	initEntyStyle();
    	initEditStyle();
    	super.onLoad();
    	initGuideNameEntry();
    	if(!getOprtState().equals(OprtState.ADDNEW)){
    		setFootRowSum();
    	}
    	setColumMerger();
    	initBtn();
    	
    	KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEnabledMultiSelection(true);
		f7Box.setEditable(false);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.ChooseQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtGuideEnty.getColumn("choose").getStyleAttributes().setLocked(true);
		this.kdtGuideEnty.getColumn("choose").setEditor(f7Editor);
		
		this.kdtGuideEnty.getColumn("choose").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof Object[]){
					Object[] info = (Object[])obj;
					String str="";
					for(int i=0;i<info.length;i++){
						if(i==info.length-1){
							str=str+((ChooseInfo)info[i]).getName();
						}else{
							str=str+((ChooseInfo)info[i]).getName()+";";
						}
					}
					return str;
				}
				return super.getText(obj);
			}
		});
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC606_INVITESUPPLIEREVAL", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			Boolean isShow=true;
			if(hmAllParam.get("FDC606_INVITESUPPLIEREVAL")!=null){
				isShow=Boolean.valueOf(hmAllParam.get("FDC606_INVITESUPPLIEREVAL").toString()).booleanValue();
			}
			if(!isShow){
				this.kdtGuideEnty.getColumn("weight").getStyleAttributes().setHided(true);
			}else{
				this.kdtGuideEnty.getColumn("choose").getStyleAttributes().setHided(true);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    private void initBtn(){
    	contEntryGuide.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
    	FDCTableHelper.disableDelete(this.kdtGuideEnty);
    	FDCTableHelper.setColumnLocked(kdtGuideEnty, 0, true);
    	this.btnUnAudit.setVisible(false);
    	this.setUITitle(getSupplierResources("supplier")+getSupplierResources("evaluateModel"));
    	this.btnAuditResult.setVisible(false);
    	this.btnAttachment.setVisible(false);
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		this.btnSave.setEnabled(false);
    	}
    }
    
    public void loadFields() {
    	kdtGuideEnty.checkParsed();
    	this.setDataObject(editData);
    	super.loadFields();
    	//��������ָ��table
    	
    	for(int rowIndex=0;rowIndex<this.kdtGuideEnty.getRowCount();rowIndex++){
    		SupplierGuideEntryInfo entry=(SupplierGuideEntryInfo) this.kdtGuideEnty.getRow(rowIndex).getUserObject();
    		
			if(entry.getChooseEntry().size()>0){
				SupplierAppraiseChooseEntryCollection col=entry.getChooseEntry();
	    		CRMHelper.sortCollection(col, "choose.number", true);
	    		
    			Object[] object=new Object[col.size()];
    			for(int j=0;j<col.size();j++){
    				object[j]=col.get(j).getChoose();
    			}
    			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(object);
			}
			
			if(AppraiseTypeEnum.PASS.equals(this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").getValue())){
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
			}else if(AppraiseTypeEnum.WEIGHT.equals(this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").getValue())){
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
		    	if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
		    		kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
		    	}
			}else if(AppraiseTypeEnum.CHOOSE.equals(this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").getValue())){
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(false);
			}else{
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
			}
		}
    }
     /**
         * @description		��ʼ����¼��ʽ
         * @author				
         * @createDate		2010-11-26
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private void initEntyStyle(){
    	this.contEntryGuide.removeAllButton();
    	kdtGuideEnty.getColumn(DESC_COL).getStyleAttributes().setLocked(true);
    	kdtGuideEnty.getColumn("guideDescription").getStyleAttributes().setLocked(true);
		KDWorkButton btnAddLineGuide = new KDWorkButton();
		KDWorkButton btnInsertLineGuide = new KDWorkButton();
		KDWorkButton btnDelLineGuide = new KDWorkButton();
		btnAddLineGuide.setName("btnAddLineGuide");
		btnAddLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddLineGuide.setText(getSupplierResources("addNew")+getSupplierResources("distract"));
		btnAddLineGuide.setSize(22, 19);
		btnAddLineGuide.setEnabled(true);
		btnAddLineGuide.setVisible(true);
		/*
		 * ��ť�¼���
		 */
		btnAddLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLine(kdtGuideEnty);
			}
		});
		btnInsertLineGuide.setName("btnInsertLineGuide");
		btnInsertLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnInsertLineGuide.setText(getSupplierResources("insert")+getSupplierResources("distract"));
		btnInsertLineGuide.setSize(22, 19);
		btnInsertLineGuide.setEnabled(true);
		btnInsertLineGuide.setVisible(true);
		/*
		 * ��ť�¼���
		 */
		btnInsertLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertLine(kdtGuideEnty);
			}
		});
		btnDelLineGuide.setName("btnDelLineGuide");
		btnDelLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnDelLineGuide.setText(getSupplierResources("delete")+getSupplierResources("distract"));
		btnDelLineGuide.setSize(22, 19);
		btnDelLineGuide.setEnabled(true);
		btnDelLineGuide.setVisible(true);
		/*
		 * ��ť�¼���
		 */
		btnDelLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeLine(kdtGuideEnty);
				setFootRowSum();
			}
		});
		if(getOprtState().equals(OprtState.VIEW)){
			btnAddLineGuide.setEnabled(false);
			btnInsertLineGuide.setEnabled(false);
			btnDelLineGuide.setEnabled(false);
		}
    	this.contEntryGuide.addButton(btnAddLineGuide);
		this.contEntryGuide.addButton(btnInsertLineGuide);
		this.contEntryGuide.addButton(btnDelLineGuide);
		this.kdtGuideEnty.getColumn(WEIGHT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtGuideEnty.getColumn(TYPE_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		KDComboBox combo = new KDComboBox();
		Iterator iterator = AppraiseTypeEnum.iterator();
		while (iterator.hasNext()) {
			combo.addItem(iterator.next());
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtGuideEnty.getColumn("appraiseType").setEditor(comboEditor);
		
    }
     /**
         * @description		��ʼ���ؼ���ʽ
         * @author				
         * @createDate		2010-11-26
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private void initEditStyle(){
		this.contCreator.setEnabled(false);
		this.contCreateTime.setEnabled(false);
		this.contAuditor.setEnabled(false);
		this.contAuditTime.setEnabled(false);
		this.contAppraiseType.setEnabled(false);
		this.btnAudit.setVisible(false);
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
    }
    /**
     * ��ȡ����󶨵Ķ�����Ϣ
     */
    protected IObjectValue createNewDetailData(KDTable table) {
    	SupplierGuideEntryInfo sgEntry =new SupplierGuideEntryInfo();
//    	sgEntry.setAppraiseType(AppraiseTypeEnum.WEIGHT);
    	sgEntry.setWeight(FDCHelper.ZERO);
    	return sgEntry;
    }
    
	protected void attachListeners() {
	}

	protected void detachListeners() {
		
	}
	/**���෽��������ʵ��*/
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierAppraiseTemplateFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() { 
		return this.kdtGuideEnty;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	/**
	 * ��ʼ������״̬
	 */
	protected IObjectValue createNewData() {
		SupplierAppraiseTemplateInfo tempInfo = new SupplierAppraiseTemplateInfo();
		SupplierAppraiseTypeInfo type = (SupplierAppraiseTypeInfo)(getUIContext().get("APPRAISETYPE"));
		tempInfo.setAppraiseType(type);
		tempInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		tempInfo.setCreateTime(new Timestamp(createDate.getTime()));
		tempInfo.setState(FDCBillStateEnum.SAVED);
		return tempInfo;
	}
	 /**
	     * @description		��ʼ����¼��������ָ�����F7
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void initGuideNameEntry(){
		F7NameCol.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditIndexQuery");
		F7NameCol.setDisplayFormat("$name$");
		F7NameCol.setEditFormat("$number$");
		F7NameCol.setCommitFormat("$number$");
		F7NameCol.setEditable(false);
		SelectorItemCollection selectors = new SelectorItemCollection();		//�ֶ���ȡ��������ֵ
		selectors.add("*");
		selectors.add("indexGroup.name");
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		evInfo.setFilter(filter);
		F7NameCol.setSelectorCollection(selectors);
		F7NameCol.setEntityViewInfo(evInfo);;
		/*
		 * F7���ݸı��¼�
		 */
		F7NameCol.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				setTypeColValue(eventObj);
			}
		});
		KDTDefaultCellEditor F7GuideName = new KDTDefaultCellEditor(F7NameCol);
		IColumn guideTypeCol = this.kdtGuideEnty.getColumn(TYPE_COL);
		IColumn guideNameCol = this.kdtGuideEnty.getColumn(NAME_COL);
		guideNameCol.setEditor(F7GuideName);
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		kdtGuideEnty.getColumn(WEIGHT_COL).setEditor(weight);
		guideTypeCol.getStyleAttributes().setLocked(true);
		kdtGuideEnty.getColumn(WEIGHT_COL).getStyleAttributes().setNumberFormat("#0.00");
		/*
		 * ���úϼ���
		 */
		KDTFootManager footRowManager= kdtGuideEnty.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			footRowManager = new KDTFootManager(kdtGuideEnty);
			footRowManager.addFootView();
			kdtGuideEnty.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			kdtGuideEnty.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtGuideEnty.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			/*
			 * ���õ���һ��������
			 */
			footRowManager.addIndexText(0, getSupplierResources("sum"));
		}else{
			footRow=kdtGuideEnty.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtGuideEnty.addFootRow(1);
			}
		}
	}
	
	
	 /**
	     * @description		����Id���FDCSplAuditIndexInfo
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected FDCSplAuditIndexInfo getSelectedInfo(String id) throws EASBizException, BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	  
        view.getSelector().add(new SelectorItemInfo("indexGroup.*"));   
		FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("id", id));
	    view.setFilter(filter);
	    FDCSplAuditIndexCollection facoll = FDCSplAuditIndexFactory.getRemoteInstance().getFDCSplAuditIndexCollection(view);
	    if(facoll.size()>0){
	    	return facoll.get(0);
	    }
        return null;
 
	}
	 /**
	     * @description		F7���ݸı�ʱ����������Ԫ���Զ��������
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void setTypeColValue(DataChangeEvent e)
    {
    	int index = this.kdtGuideEnty.getSelectManager().getActiveRowIndex();
    	IRow row = this.kdtGuideEnty.getRow(index);
    	if(e.getNewValue() != null)
    	{
    		FDCSplAuditIndexInfo auditIndexInfo = (FDCSplAuditIndexInfo)(e.getNewValue());
    		if(null == auditIndexInfo.getIndexGroup().getName()){
					try {
						auditIndexInfo=getSelectedInfo(auditIndexInfo.getId().toString());
					} catch (Exception e1) {
						handUIException(e1);
					}
    		}
    		for(int i = 0; i < kdtGuideEnty.getRowCount(); ++i)
    		{
    			if(i != index)
    			{
    				IRow tmpRow = kdtGuideEnty.getRow(i);
    				if(tmpRow.getCell(NAME_COL).getValue() != null)
    				{
    					if(tmpRow.getCell(NAME_COL).getValue() instanceof FDCSplAuditIndexInfo)
    					{
    						FDCSplAuditIndexInfo tmpLineInfo = (FDCSplAuditIndexInfo)(tmpRow.getCell(NAME_COL).getValue());
    						if(auditIndexInfo.getId().equals(tmpLineInfo.getId()))
    						{
    							Integer indexInt = new Integer(index+1);
    							Integer iInt = new Integer(i+1);
    							StringBuffer buffer = new StringBuffer(getSupplierResources("next") + indexInt.toString());
    							buffer.append(getSupplierResources("rowAdd"));
    							buffer.append(getSupplierResources("next"));
    							buffer.append(iInt.toString() +getSupplierResources("notSameIndex"));
    							FDCMsgBox.showWarning(this,buffer.toString());
    							kdtGuideEnty.removeRow(index);
    							abort();
    						}
    					}
    				}
    			}
    		}
    		FDCSplAuditIndexGroupInfo groupInfo = auditIndexInfo.getIndexGroup();
    		row.getCell(NAME_COL).setUserObject(auditIndexInfo);
    		row.getCell(TYPE_COL).setValue(groupInfo.getName());
    		row.getCell(DESC_COL).setValue(auditIndexInfo.getFullMarkStandard());
    		row.getCell("guideDescription").setValue(auditIndexInfo.getDescription());
    	}
    	else
    	{
    		row.getCell(TYPE_COL).setValue(null);
    	}
    	
    	
    
    }
	
	/**
	 * @description		����ָ��
	 * @author			��ΰ		
	 * @createDate		2010-11-20
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void insertGuideEnty(int index,FDCSplAuditIndexInfo  info ) {
		 kdtGuideEnty.checkParsed();
		 IRow row = kdtGuideEnty.getRow(index); 
		 String type = (String)row.getCell(TYPE_COL).getValue();
		 SupplierGuideEntryInfo	guideInfo= new SupplierGuideEntryInfo();
//		 guideInfo.setGuideType((String)kdtGuideEnty.getRow(index).getCell(TYPE_COL).getValue());
		 guideInfo.setSplAuditIndex(info); 
		 guideInfo.setAppraiseType((AppraiseTypeEnum) kdtGuideEnty.getRow(index).getCell("appraiseType").getValue());
//		 guideInfo.setGuideName(info.getName());
//		 guideInfo.setGuideDescription(info.getDescription());
		 guideInfo.setRemark((String)kdtGuideEnty.getRow(index).getCell("remark").getValue());
		 guideInfo.setWeight((BigDecimal)kdtGuideEnty.getRow(index).getCell(WEIGHT_COL).getValue());
//		 guideInfo.setFullNum((String)kdtGuideEnty.getRow(index).getCell(DESC_COL).getValue());
		 
		 kdtGuideEnty.removeRow(index);
		 int longth = kdtGuideEnty.getRowCount();	
		 if(null == type){
			 abort();
		 }
		 int startEnd[] =getStartEnd( -1 , -1 , type, longth); 
         if(startEnd[0]== -1){
        	 row =kdtGuideEnty.addRow(longth); 
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(longth).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(longth).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(longth).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(longth).getCell(DESC_COL).setValue(guideInfo.getFullNum());  

//        	 kdtGuideEnty.getRow(longth).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(longth).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(longth).getCell("remark").setValue(guideInfo.getRemark());
    
         }else if(startEnd[1] ==-1){
        	 int rowindex =0;
        	 if(startEnd[0]==index){
        		 rowindex =index;
        	 }else{
        		 rowindex =startEnd[0]+1;
        	 }
        	 row = kdtGuideEnty.addRow(rowindex);
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(rowindex).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(rowindex).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(rowindex).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(rowindex).getCell(DESC_COL).setValue(guideInfo.getFullNum());  
        	 
//        	 kdtGuideEnty.getRow(rowindex).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(rowindex).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(rowindex).getCell("remark").setValue(guideInfo.getRemark());
    
         }else if(startEnd[0]<startEnd[1]){
        	 int rowindex =0;
        	 if(startEnd[0]<=index &&  startEnd[1] >= index ){
        		 rowindex =index;
        	 }else{
        		 rowindex =startEnd[1]+1;
        	 }
        	 row = kdtGuideEnty.addRow(rowindex);
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(rowindex).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(rowindex).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(rowindex).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(rowindex).getCell(DESC_COL).setValue(guideInfo.getFullNum());  
        	 
//        	 kdtGuideEnty.getRow(rowindex).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(rowindex).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(rowindex).getCell("remark").setValue(guideInfo.getRemark());
        	 
         }       
	}
	 /**
	     * @description		F7���ݸı�ʱ������ά����ͬ�ϲ���Ԫ��
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void setColumMerger(){
		kdtGuideEnty.checkParsed();
		KDTMergeManager mm =kdtGuideEnty.getMergeManager();
		int longth = kdtGuideEnty.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * ȡ�����е�γ��
			 */
			  row = kdtGuideEnty.getRow(i);
			  String type = (String)row.getCell(TYPE_COL).getValue();
			  if(map.get(type) == null){
				  map.put(type, Boolean.TRUE);
			  }
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();
			int startEnd[] =getStartEnd( -1 , -1 , type, longth); 
            if(startEnd[0]<startEnd[1]){
			  mm.mergeBlock(startEnd[0],0,startEnd[1],0,KDTMergeManager.SPECIFY_MERGE);
            }
		}
	}
	
	 /**
	     * @description		
	     * @author			
	     * @createDate		2010-11-26
	     * @param			int int String int 
	     * @return			int []		
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i++){
			//ȡ�����е�γ��
			  row = kdtGuideEnty.getRow(i);
			  String str = (String)row.getCell(TYPE_COL).getValue();
			  if(type == null || str == null){
				  continue;
			  }
			  if(str.equals(type)){
				  if(ben < 0){
					  ben = i ;   
				  }else{
					  end = i ; 
				  }
			  }
		} 
       int obj [] = new int[2];
       obj[0]=ben;
       obj[1]=end;
       return obj;
	}
	
	 /**
	     * @description		����Ȩ��֮��
	     * @author				
	     * @createDate		2010-11-26
	     * @param	
	     * @return			int		
	     * @�޸�			�㿭
	     * @version			EAS7.0
	     * @see						
	     */
	private int setFootRowSum()
	{
		kdtGuideEnty.checkParsed();
		int i = 0;
		IRow footRow =  kdtGuideEnty.getFootRow(0);
    	BigDecimal sum =getNumWeight();
    	if(sum.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		i=1;
    	}
    	footRow.getCell(WEIGHT_COL).setValue(sum);
    	return i;
	}
	 /**
	     * @description		�������Ȩ�صĺ�
	     * @author			�㿭	
	     * @createDate		2010-11-26
	     * @param	
	     * @return			BigDecimal	
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private BigDecimal getNumWeight(){
		BigDecimal sum = new BigDecimal(0);
		for(int i= 0; i < kdtGuideEnty.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtGuideEnty.getRow(i);
    		if(!AppraiseTypeEnum.WEIGHT.equals(tmpRow.getCell("appraiseType").getValue())){
    			continue;
    		}
    		if(tmpRow.getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			sum = sum.add(tmp);
    		}else{
    			tmpRow.getCell(WEIGHT_COL).setValue(new BigDecimal("1.00"));
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			sum = sum.add(tmp);
    		}
    	}
		return sum;
	}
	 /**
	     * @description		�ж�Ȩ���Ƿ�ϸ�	
	     * @author			�㿭	
	     * @createDate		2010-11-26
	     * @param			String int
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void isWeightPoint(String colName,int rowIndex){
		if(colName.equals(WEIGHT_COL))
    	{	
    		if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue().toString());	
    			if(tmp.compareTo(FDCHelper.ZERO) < 0)
    			{
    				FDCMsgBox.showWarning(this,getSupplierResources("weight")+getSupplierResources("lessZero"));
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(1));
    				abort();
    			}
    			if(tmp.compareTo(new BigDecimal("100") ) > 0)
    			{
    				FDCMsgBox.showWarning(this,getSupplierResources("weight")+getSupplierResources("moreHundred"));
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(1));
    				abort();
    			}
    			int i = setFootRowSum();
    			if(i == 1){
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(0));
    				setFootRowSum();
    			}
    		}
    	}
	}
 
	 /**
	     * @description		����ָ������F7�ı��¼�����ͬ������ά�Ⱥϲ���Ԫ��
	     * @author			����
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     * @�޸�			�㿭
	     * @version			EAS7.0
	     * @see						
	     */
	public void kdtGuideEnty_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e){
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		Boolean isShow=true;
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC606_INVITESUPPLIEREVAL", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC606_INVITESUPPLIEREVAL")!=null){
				isShow=Boolean.valueOf(hmAllParam.get("FDC606_INVITESUPPLIEREVAL").toString()).booleanValue();
			}
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		if(e.getColIndex()==this.kdtGuideEnty.getColumnIndex("appraiseType")){
			if(AppraiseTypeEnum.PASS.equals(e.getValue())){
				if(!isShow){
					FDCMsgBox.showWarning(this,"����Ϊ�����ƣ�");
					this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").setValue(e.getOldValue());
					return;
				}
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
			}else if(AppraiseTypeEnum.WEIGHT.equals(e.getValue())){
				if(!isShow){
					FDCMsgBox.showWarning(this,"����Ϊ�����ƣ�");
					this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").setValue(e.getOldValue());
					return;
				}
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
		    	if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
		    		kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
		    	}
			}else if(AppraiseTypeEnum.CHOOSE.equals(e.getValue())){
				if(isShow){
					FDCMsgBox.showWarning(this,"����Ϊ����ƣ�");
					this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").setValue(e.getOldValue());
					return;
				}
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(false);
			}else{
				if(isShow){
					FDCMsgBox.showWarning(this,"����Ϊ����ƣ�");
					this.kdtGuideEnty.getRow(rowIndex).getCell("appraiseType").setValue(e.getOldValue());
					return;
				}
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
				
				this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
				this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
			}
		}
//		if(e.getValue() instanceof FDCSplAuditIndexInfo ){
//			FDCSplAuditIndexInfo  aInfo =(FDCSplAuditIndexInfo )(e.getValue());
////			insertGuideEnty(rowIndex,aInfo);
//		}else if(AppraiseTypeEnum.PASS.equals(e.getValue())){
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
//			
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
//		}else if(AppraiseTypeEnum.WEIGHT.equals(e.getValue())){
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
//			
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
//	    	if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
//	    		kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
//	    	}
//		}else if(AppraiseTypeEnum.CHOOSE.equals(e.getValue())){
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
//			
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(false);
//		}else{
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").getStyleAttributes().setLocked(true);
//			
//			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
//			this.kdtGuideEnty.getRow(rowIndex).getCell("choose").setValue(null);
//		}
		setColumMerger();
    	String colName  = kdtGuideEnty.getColumnKey(colIndex);
    	isWeightPoint(colName,rowIndex);
	}
	 /**
     * @description		�����������ж��Ƿ��ظ�
     * @author			�㿭	
     * @createDate		2010-11-18
     * @param			filedName  ��Ӧ�ֶ�
     * @param			filedValue  �������������
     * @return			���ھͷ���true ����false	
     *	
     * @version			EAS7.0
     * @see						
     */
	private boolean isExist(String filedName,String filedValue)throws Exception{
		
		FilterInfo filterInfo=new FilterInfo();
		FilterItemInfo itemInfo=new FilterItemInfo(filedName,filedValue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
		filterInfo.getFilterItems().add(itemInfo);
		filterInfo.setMaskString(" #0");
		if(STATUS_EDIT.equals(this.getOprtState())){
			if(editData.getId() == null){
				return false;
			}
			FilterItemInfo itemInfo_2=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
			filterInfo.getFilterItems().add(itemInfo_2);
			filterInfo.setMaskString("#0 and #1");
		}
		boolean flag=SupplierAppraiseTemplateFactory.getRemoteInstance().exists(filterInfo);
		return flag;
	}
	
	
	 /**
	     * @description		������֤
	     * @author			�㿭	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	protected void verifyInputForSave() throws Exception {
		validateInPut();
	}
	 /**
	     * @description		�ύ��֤
	     * @author			�㿭	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	protected void verifyInputForSubmint() throws Exception {
		
		validateInPut();
	}
	/**
	 * @description		
	 * @author			��ΰ		
	 * @createDate		2010-12-1
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected void disposeUIWindow() {
		super.disposeUIWindow();
	}
	 /**
	     * @description		������֤
	     * @author			�㿭	
	     * @createDate		2010-12-2
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	private void validateInPut() throws Exception{
//		editData.setPhase((AppraisePhaseEnum)comboPhase.getSelectedItem());
//		editData.setName(txtName.getText());
//		editData.setNumber(txtNumber.getText());
//		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
//			FDCMsgBox.showWarning(this,getSupplierResources("modelNumber")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//
//		if(editData.getPhase()==null){
//			FDCMsgBox.showWarning(this,getSupplierResources("syndicPhase")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//		if(editData.getName()==null||editData.getName().trim().length()==0){
//			FDCMsgBox.showWarning(this,getSupplierResources("model")+getSupplierResources("name")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//		if(isExist("name",editData.getName())==true){
//			MsgBox.showInfo(this,getSupplierResources("model")+getSupplierResources("name")+editData.getName()+getSupplierResources("againInput"));
//    		SysUtil.abort();
//    		this.txtName.setFocusable(true);
//		}
//		if(isExist("number",editData.getNumber())==true){
//			MsgBox.showInfo(this,getSupplierResources("modelNumber")+editData.getNumber()+getSupplierResources("againInput"));
//    		SysUtil.abort();
//    		this.txtNumber.setFocusable(true);
//		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtAppraiseType);
		
		kdtGuideEnty.checkParsed();
		int count = kdtGuideEnty.getRowCount();
		if(count <0){
			return;
		}else{
			for(int i =0;i<count;i++){
				if("".equals(kdtGuideEnty.getRow(i).getCell(NAME_COL).getValue()) || null == kdtGuideEnty.getRow(i).getCell(NAME_COL).getValue()){
					FDCMsgBox.showWarning(this, getSupplierResources("index")+getSupplierResources("notNull"));
					SysUtil.abort();
				}
			}
		}
		BigDecimal big = getNumWeight();
		if(big.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		SysUtil.abort();
    		
    	}
	}
	
	/**
	 * @description		
	 * @author			��ΰ		
	 * @createDate		2010-11-21
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("guideEntry.*"));
		sic.add(new SelectorItemInfo("guideEntry.splAuditIndex.*"));
		sic.add(new SelectorItemInfo("guideEntry.chooseEntry.choose.*"));
		
		return sic;
	}
}