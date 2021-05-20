/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherSurveyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SupplierReviewGatherSurveyEditUI extends AbstractSupplierReviewGatherSurveyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierReviewGatherSurveyEditUI.class);
    public SupplierReviewGatherSurveyEditUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		SupplierReviewGatherSurveyInfo info=new SupplierReviewGatherSurveyInfo();
		info.setIsEnabled(true);
		info.setTemplate((SupplierAppraiseTemplateInfo)this.getUIContext().get("template"));
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierReviewGatherSurveyFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
//		super.verifyInput(e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.txtDescription.setMaxLength(255);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.prmtTemplate.setRequired(true);
	}
}