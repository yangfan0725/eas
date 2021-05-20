/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.app.TaxerQuaEnum;
import com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelCollection;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SupplierChangeConfirmEditUI extends AbstractSupplierChangeConfirmEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierChangeConfirmEditUI.class);
    
    /**
     * output class constructor
     */
    public SupplierChangeConfirmEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.actionAddNew.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	
    	if(FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
	   		this.actionSave.setEnabled(false);
	    }
    }
    
    @Override
    public void onShow() throws Exception {
    	// TODO Auto-generated method stub
    	super.onShow();
    	this.txtAddress.setForeground(Color.red);
    }
    
    @Override
    public void loadFields() {
    	// TODO Auto-generated method stub
    	super.loadFields();
    	
    	SupplierStockInfo supplier = editData.getSupplier();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("city.*");
    	sic.add("province.*");
    	sic.add("quaLevelEntry.quaLevel.*");
    	//加载资质等级信息
       try {
		supplier = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(supplier.getId()),sic);
		SupplierQuaLevelEntryCollection quaCols = supplier.getQuaLevelEntry();
	       SupplierQuaLevelEntryInfo info = null;
	       int size = quaCols.size();
	       if(size>0){
	    	   StringBuffer str = new StringBuffer();
	    	   for(int i=-0;i<size;i++){
	    		   info = quaCols.get(i);
	    		   str.append(info.getQuaLevel().getName());
	    		   if(i<size-1){
	    			   str.append(",");
	    		   }
	    		   
	    	   }
	    	   this.txtQuaLevel.setText(str.toString());
	       }
	} catch (EASBizException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (BOSException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      
       //加载修改信息
       ContWeCharInfo.setEnabled(true);
       SupplierChangeEntryCollection cols = editData.getEntry();
       SupplierChangeEntryInfo entryInfo = null;
       int size =cols.size();
       String key = null;
       String value = null;
       KDTextField ctrl  = null;
       Color c =Color.RED;
       boolean isSame = false;
       for (int i = 0;i<size;i++){
    	   entryInfo = cols.get(i);
    	   key = entryInfo.getPropertyName();
    	   value = entryInfo.getPropertyValue();
    	   ctrl = null;
    	   if(value == null){
    		   continue;
    	   }
    	   if(key.equalsIgnoreCase("province")){
    		  ctrl = this.txtProvinceNew;
    		  try {
				value = ProvinceFactory.getRemoteInstance().getProvinceInfo(new ObjectUuidPK(BOSUuid.read(value))).getName();
				if(supplier.getProvince() == null){
					isSame = false;
				}else{
					isSame = value.equals(supplier.getProvince().getName());
				}
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }else if(key.equalsIgnoreCase("city")){
    		   ctrl = this.txtCityNew;
    		   try {
				value = CityFactory.getRemoteInstance().getCityInfo(new ObjectUuidPK(BOSUuid.read(value))).getName();
				if(supplier.getCity() == null){
					isSame = false;
				}else{
					isSame = value.equals(supplier.getCity().getName());
				}
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }else if(key.equalsIgnoreCase("contractor")){
    		   ctrl = this.txtContractorNew;
    		   if(supplier.getContractor() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getContractor());
    		   }
    	   }else if(key.equalsIgnoreCase("address")){
    		   ctrl = this.txtAddressNew;
    		   if(supplier.getAddress() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getAddress());
    		   }
    	   }else if(key.equalsIgnoreCase("enterpriseMaster")){
    		   ctrl = this.txtMasterNew;
    		   if(supplier.getEnterpriseMaster() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getEnterpriseMaster());
    		   }
    	   }else if(key.equalsIgnoreCase("linkFax")){
    		   ctrl = this.txtFaxNew;
    		   if(supplier.getLinkFax() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getLinkFax());
    		   }
    	   }else if(key.equalsIgnoreCase("enterpriseKind")){
    		   EnterpriseKindEnum eke =  EnterpriseKindEnum.getEnum(value);
    		   value = eke.getAlias();
    		   ctrl = this.txtEnterpriseKindNew;
    		   if(supplier.getEnterpriseKind() == null){
    			   isSame = false;
    		   }else{
    			   isSame = supplier.getEnterpriseKind().getAlias().equals(value);
    		   }
    	   }else if(key.equalsIgnoreCase("registerMoney")){
    		   ctrl = this.txtRegisterMoneyNew;
    		   if(supplier.getRegisterMoney() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getRegisterMoney());
    		   }
    	   }else if(key.equalsIgnoreCase("bizRegisterNo")){
    		   ctrl = this.txtRegisterNumberNew;
    		   if(supplier.getBizRegisterNo() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getBizRegisterNo());
    		   }
    	   }else if(key.equalsIgnoreCase("linkPhone")){
    		   ctrl = this.txtTelephoneNew;
    		   if(supplier.getLinkPhone() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getLinkPhone());
    		   }
    	   }else if(key.equalsIgnoreCase("managerPhone")){
    		   ctrl = this.txtPMPhoneNew;
    		   if(supplier.getManagerPhone() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getManagerPhone());
    		   }
    	   }else if(key.equalsIgnoreCase("manager")){
    		   ctrl = this.txtPMNew;
    		   if(supplier.getManager() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getManager());
    		   }
    	   }else if(key.equalsIgnoreCase("contractorPhone")){
    		   ctrl = this.txtContractorPhoneNew;
    		   if(supplier.getContractorPhone() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getContractorPhone());
    		   }
    	   }else if(key.equalsIgnoreCase("taxerQua")){
    		   ctrl = this.txtTaxerQuaNew;
    		   TaxerQuaEnum taxerQuaEnum = TaxerQuaEnum.getEnum(value);
    		   if(taxerQuaEnum != null){
    			   value = taxerQuaEnum.getAlias();
    		   }
    		   if(supplier.getTaxerQua() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getTaxerQua().getAlias());
    		   }
    		   
    	   }else if(key.equalsIgnoreCase("description")){
    		  //特殊处理
    		   if(supplier.getDescription() == null){
    			   isSame = false;
    		   }else{
    			   isSame = value.equals(supplier.getDescription());
    		   }
    		   this.contDescription.setBoundLabelText("备注【已修改】：");
    		   this.txtDescription.setText(value);
    		   continue;
    		   
    	   }else if(key.equalsIgnoreCase("splArea")){
    		   ctrl = this.txtSpl;
    		   if(supplier.getSplArea() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getSplArea());
    		   }
    		   
    	   }else if(key.equalsIgnoreCase("bankName")){
    		   ctrl = this.txtWechatBank;
    		   if(supplier.getBankName() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getBankName());
    		   }
    		   
    	   }else if(key.equalsIgnoreCase("bankCount")){
    		   ctrl = this.txtWechatBankAccount;
    		   if(supplier.getBankCount() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getBankCount());
    		   }
    		   
    	   }else if(key.equalsIgnoreCase("taxRegisterNo")){
    		   ctrl = this.txtTaxRegisterNo;
    		   if(supplier.getTaxRegisterNo() == null){
    			   isSame =false;
    		   }else{
    			   isSame = value.equals(supplier.getTaxRegisterNo());
    		   }
    	   }else if(key.equalsIgnoreCase("quaLevel")){
    		   ctrl = this.txtQuaLevleNew;
    		   String [] ids = value.split(",");
    		   Set<String> idset = new HashSet<String>();
    		   for(String r:ids){
    			   idset.add(r);
    		   }
    		   EntityViewInfo view = new EntityViewInfo();
    		   FilterInfo filter  = new FilterInfo();
    		   filter.getFilterItems().add(new FilterItemInfo("id",idset,CompareType.INCLUDE));
    		   view.setFilter(filter);
    		   try {
				    QuaLevelCollection quaColsWX = QuaLevelFactory.getRemoteInstance().getQuaLevelCollection(view);
				    int qsize = quaColsWX.size();
				    QuaLevelInfo quaInfo = null;
			        if(qsize>0){
			    	   StringBuffer str = new StringBuffer();
			    	   for(int j=0;j<qsize;j++){
			    		   quaInfo = quaColsWX.get(j);
			    		   str.append(quaInfo.getName());
			    		   if(j<qsize-1){
			    			   str.append(",");
			    		   }
			    		   
			    	   }
			    	   value =str.toString();
			       }
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String txtValue = this.txtQuaLevel.getText();
			if(txtValue == null){
				isSame = false;
			}else{
				isSame = txtValue.equals(value);
			}
    		   
    	   }
    	
    	   if(ctrl == null){
    		   continue;
    	   }
    	   if(!isSame){
    		   String boundText = ctrl.getBoundLabelText();
    		   ctrl.setBoundLabelText(boundText+"【已修改】");
    	   }
    	   ctrl.setText(value);
       }
       
    }
    

    /**
     * output storeFields method
     */
    public void storeFields()
    {
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
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionXunTongFeed_actionPerformed
     */
    public void actionXunTongFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionXunTongFeed_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
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
    	if(!editData.getState().equals(FDCBillStateEnum.SAVED)&&!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
			FDCMsgBox.showError("当前状态单据不适合修改.");
			SysUtil.abort();
		}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!editData.getState().equals(FDCBillStateEnum.SAVED)&&!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
			FDCMsgBox.showError("当前状态单据不适合删除.");
			SysUtil.abort();
		}
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
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return SupplierChangeConfirmFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return this.kdtEntry;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		SupplierChangeConfirmInfo info = new SupplierChangeConfirmInfo();
		return info;
	}
	
	@Override
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("entry.*");
		sic.add("supplier.quaLevelEntry.*");
		sic.add("supplier.quaLevelEntry.quaLevel.id");
		sic.add("supplier.quaLevelEntry.quaLevel.name");
		sic.add("supplier.quaLevelEntry.quaLevel.number");
		return sic;
		
		
	}
	
	@Override
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return new SupplierChangeEntryInfo();
	}
	
	

}