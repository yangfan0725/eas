/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.IPlanIndexConfig;
import com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum;
import com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fi.gr.cslrpt.ServerException;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.IBgItem;
import com.kingdee.eas.tools.datatask.DIETemplateInfo;
import com.kingdee.eas.tools.datatask.client.PropertySetUI;
import com.kingdee.eas.tools.datatask.client.UIUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class PlanIndexConfigEditUI extends AbstractPlanIndexConfigEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanIndexConfigEditUI.class);
    public PlanIndexConfigEditUI() throws Exception
    {
        super();
    }
    private PlanIndexConfigInfo parentInfo;

	private String parentNumber = null;

	private String strTemp = null;

	protected IObjectValue createNewData() {
		PlanIndexConfigInfo info=new PlanIndexConfigInfo();
		info.setIsEdit(true);
		parentInfo = (PlanIndexConfigInfo) getUIContext().get(UIContext.PARENTNODE);
		if(parentInfo!=null){
			info.setIsProductType(parentInfo.isIsProductType());
		}
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return PlanIndexConfigFactory.getRemoteInstance();
	}
	public void loadFields() {
		super.loadFields();
		parentInfo = (PlanIndexConfigInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			parentNumber = strTemp;
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
//				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
//				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
//				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
//				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		}
		this.txtFormula.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.cbFormulaType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.cbIsProductType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		if(this.cbIsEdit.isSelected()){
			this.cbFormulaType.setEnabled(false);
		}else{
			this.cbFormulaType.setEnabled(true);
		}
		if(!this.cbIsEdit.isSelected()){
			if(PlanIndexFormulaTypeEnum.NORMAL.equals(this.cbFormulaType.getSelectedItem())
				||PlanIndexFormulaTypeEnum.PRODUCTTYPE.equals(this.cbFormulaType.getSelectedItem())){
				this.txtFormula.setEnabled(true);
			}else{
				this.txtFormula.setEnabled(false);
			}
		}else{
			this.txtFormula.setEnabled(false);
		}
		if(parentInfo!=null||this.editData.getParent()!=null){
			this.cbIsProductType.setEnabled(false);
		}
		
		if(this.editData.getParent()!=null){
			parentNumber=this.editData.getParent().getNumber().replace('!', '.');
		}
	}
	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		String longNumber = this.txtLongNumber.getText().trim();
		if (longNumber == null || longNumber.trim().length() < 1 || longNumber.lastIndexOf(".")+1==longNumber.length() || longNumber.indexOf(".")==0
				 || longNumber.lastIndexOf("!")+1==longNumber.length() || longNumber.indexOf("!")==0 ) {
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		if (getOprtState().equals(OprtState.ADDNEW)){
//			FDCBaseTypeValidator.validate(((PlanIndexConfigListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());
		
			if (parentNumber != null && (longNumber.equals(parentNumber + ".") || longNumber.length() < parentNumber.length() + 1)) {
				// ���벻����
				txtLongNumber.requestFocus(true);
				throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
			}
		}
		if (parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('.') >= 0)// �û����˵�
				|| (!longNumber.substring(0, parentNumber.length()).equals(parentNumber)))) {// ��ʼ�����뱻�޸�
			// ���벻���Ϲ淶
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		
		//�û�����̾�Ż����޸��˳�ʼ�����룬���׳����벻���Ϲ淶�쳣
		if(parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('!') >= 0)
				||(!longNumber.substring(0, parentNumber.length()).equals(parentNumber))))
		{
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
//		this.editData.setNumber(longNumber);
		
		longNumber = longNumber.replace('.', '!');
		this.editData.setLongNumber(longNumber);

		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.cbFieldType);
		if(this.txtFormula.getText()!= null && this.txtFormula.getText().length() != 0){
			FDCClientVerifyHelper.verifyEmpty(this, this.cbFormulaType);
		}
		if(this.cbIsProductType.isSelected()&&this.cbFormulaType.getSelectedItem()!=null&&this.cbFormulaType.getSelectedItem().equals(PlanIndexFormulaTypeEnum.PRODUCTTYPE)){
			FDCMsgBox.showWarning(this,"ҵָ̬�겻��ѡ��ҵ̬��͹�ʽ");
			SysUtil.abort();
		}
		if(!this.cbIsEdit.isSelected()){
			Pattern pt = Pattern.compile("([\\(]*)([^\\+|^\\-|^\\*|^\\/|^\\(|^\\))]{1,})([\\+|\\-|\\*|\\/|\\(|\\)]*)");
			
			if(this.txtFormula.getText()!= null && this.txtFormula.getText().length() != 0){
				Matcher matcher = pt.matcher(this.txtFormula.getText());
				
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				List sumItemNumber = new ArrayList();
				String itemNumber = null;
				StringBuffer maskString = new StringBuffer("(");
				int count = 0;
				do{
					if(!matcher.find())break;
					itemNumber = matcher.group(2);
					if(sumItemNumber.contains(this.txtLongNumber.getText().trim())){
						FDCMsgBox.showWarning(this,"��ʽ:������"+itemNumber.toString()+"�����Լ�");
						SysUtil.abort();
					}
					if(sumItemNumber.contains(itemNumber)){
						FDCMsgBox.showWarning(this,"��ʽ:������"+itemNumber.toString()+"�ظ�");
						SysUtil.abort();
					}
					sumItemNumber.add(itemNumber);
					if(itemNumber != null && itemNumber.trim().length() != 0){
						filter.getFilterItems().add(new FilterItemInfo("number", itemNumber, CompareType.EQUALS));
						if(count != 0)
							maskString.append(" OR ");
						maskString.append("#").append(count);
						count++;
						}
					} while(true);
				maskString.append(")");
				filter.setMaskString(maskString.toString());
				view.setFilter(filter);
				IPlanIndexConfig ie = PlanIndexConfigFactory.getRemoteInstance();
				PlanIndexConfigCollection subItems = ie.getPlanIndexConfigCollection(view);
				if(subItems == null || subItems.isEmpty()){
					FDCMsgBox.showWarning(this,"��ʽ:������"+sumItemNumber.toString()+"������");
					SysUtil.abort();
				}
				if(sumItemNumber.size() != subItems.size()){
					for(int i = 0; i < subItems.size(); i++){
						sumItemNumber.remove(subItems.get(i).getNumber().replaceAll("!", "."));
					}
					if(!sumItemNumber.isEmpty()){
						FDCMsgBox.showWarning(this,"��ʽ:������"+sumItemNumber.toString()+"������");
						SysUtil.abort();
					}
				}else{
					for(int i = 0; i < subItems.size(); i++){
						if(subItems.get(i).getFieldType().equals(PlanIndexFieldTypeEnum.TEXT)){
							FDCMsgBox.showWarning(this,"��ʽ:������"+subItems.get(i).getNumber().replaceAll("!", ".")+"���Ͳ���Ϊ�ı�");
							SysUtil.abort();
						}
						if(!subItems.get(i).isIsEnabled()){
							FDCMsgBox.showWarning(this,"��ʽ:������"+subItems.get(i).getNumber().replaceAll("!", ".")+"�ѽ���");
							SysUtil.abort();
						}
						if(this.cbFormulaType.getSelectedItem().equals(PlanIndexFormulaTypeEnum.NORMAL)){
							if(subItems.get(i).isIsProductType()!=this.cbIsProductType.isSelected()){
								FDCMsgBox.showWarning(this,"��ʽ:������"+subItems.get(i).getNumber().replaceAll("!", ".")+"��ָ�����Ͳ�����");
								SysUtil.abort();
							}
						}else if(!subItems.get(i).isIsProductType()){
							FDCMsgBox.showWarning(this,"��ʽ:������"+subItems.get(i).getNumber().replaceAll("!", ".")+"����ҵָ̬��");
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "�滮ָ��");
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		if (parentInfo != null && parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(true);
		}
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				PlanIndexConfigInfo parent =editData.getParent();
				if (/*STATUS_VIEW.equals(getOprtState()) ||*/ parent == null)
					return;
				String longNumber = parent.getNumber() + '.';
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// ����״̬
			this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			this.btnCancel.setVisible(false);// ���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�״̬
			if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
				this.btnCancel.setVisible(true);// ���ð�ť����
				this.btnCancel.setEnabled(true);// ���ð�ť����
				this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			} else {// �����ǰΪ����״̬
				this.btnCancelCancel.setVisible(true);// ���ð�ť�ɼ�
				this.btnCancelCancel.setEnabled(true);// ���ð�ť����
				this.btnCancel.setEnabled(false);// ���ð�ť���ɼ�
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// �鿴״̬
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
					this.btnCancel.setVisible(true);// ���ð�ť����
					this.btnCancel.setEnabled(true);// ���ð�ť����
					this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
				} else {// �����ǰΪ����״̬
					this.btnCancelCancel.setVisible(true);// ���ð�ť�ɼ�
					this.btnCancelCancel.setEnabled(true);// ���ð�ť����
					this.btnCancel.setEnabled(false);// ���ð�ť���ɼ�
				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnAddNew.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			this.btnSelect.setEnabled(false);
		}
	}

	protected void initOldData(IObjectValue dataObject) {
		parentInfo = (PlanIndexConfigInfo) getUIContext().get(UIContext.PARENTNODE);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((PlanIndexConfigInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((PlanIndexConfigInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((PlanIndexConfigInfo) dataObject).setLongNumber(strTemp);
			}
		}
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IPlanIndexConfig) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IPlanIndexConfig) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
			this.btnRemove.setEnabled(false);
		}
	}
	protected void cbIsEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.cbIsEdit.isSelected()){
			this.cbFormulaType.setEnabled(false);
			this.cbFormulaType.setSelectedItem(PlanIndexFormulaTypeEnum.NULL);
		}else{
			this.cbFormulaType.setEnabled(true);
		}
	}
	protected void cbFormulaType_itemStateChanged(ItemEvent e) throws Exception {
		if(!this.cbIsEdit.isSelected()){
			if(PlanIndexFormulaTypeEnum.NORMAL.equals(this.cbFormulaType.getSelectedItem())
				||PlanIndexFormulaTypeEnum.PRODUCTTYPE.equals(this.cbFormulaType.getSelectedItem())){
				this.txtFormula.setEnabled(true);
			}else{
				this.txtFormula.setEnabled(false);
				this.txtFormula.setText(null);
			}
		}else{
			this.txtFormula.setEnabled(false);
			this.txtFormula.setText(null);
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		DIETemplateInfo info=new DIETemplateInfo();
		UIContext uiContext = new UIContext(this);
		uiContext.put("Owner", this);
		uiContext.put("editData", info);
		if(this.cbIsProductType.isSelected()){
			uiContext.put("bosAlias", "�滮ָ���¼");
			uiContext.put("bostype", "DCB88FE0");
		}else{
			uiContext.put("bosAlias", "�滮ָ��");
			uiContext.put("bostype", "768287B2");
		}
		UIUtil.showDialog(PropertySetUI.class.getName(), uiContext, OprtState.ADDNEW, this);
		if(info.getFieldEntries().size()>0){
			this.txtProp.setText(info.getFieldEntries().get(0).getEntityPropName());
		}else{
			this.txtProp.setText(null);
		}
	}
}