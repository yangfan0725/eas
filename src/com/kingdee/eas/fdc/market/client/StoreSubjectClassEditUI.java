/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.market.MediaTypeFactory;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.StoreSubjectClassFactory;
import com.kingdee.eas.fdc.market.StoreSubjectClassInfo;
import com.kingdee.eas.fdc.market.StoreSubjectFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;

/**
 * output class name
 */
public class StoreSubjectClassEditUI extends AbstractStoreSubjectClassEditUI {

	private static final Logger logger = CoreUIObject.getLogger(StoreSubjectClassEditUI.class);

	/**
	 * output class constructor
	 */
	public StoreSubjectClassEditUI() throws Exception {
		super();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		initUIStatus();
		handleCodingRule();
	}
	/**
	 * @author tim_gao
	 * @description ��ʼ״̬
	 * @date 2010-11-2
	 */
	protected void initUIStatus(){
		this.setSize(300, 270);
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtName.setRequired(true);
		this.txtName.setMaxLength(80);
		this.txtNumber.setRequired(true);
	
	}
	
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSubmit_actionPerformed(e);
	}

	private void checkNullAndDump() throws Exception {
		StoreSubjectClassInfo info = (StoreSubjectClassInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}
		StoreSubjectClassInfo parentInfo = info.getParent();
		String number = this.txtNumber.getText().trim();
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;
		if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
			if (number == null ||("").equals(number)) {
				MsgBox.showInfo("���벻��Ϊ�գ�");
				SysUtil.abort();
			}
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
			}
			if (StoreSubjectClassFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("��ͬ�����±��벻���ظ���");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("number", number));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
				}
				if (StoreSubjectClassFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("��ͬ�����±��벻���ظ���");
					SysUtil.abort();
				}
			}

		}
		String name = this.txtName.getSelectedItemData().toString();
		if (name == null || "".equals(name) ) {
			MsgBox.showInfo("���Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
			}
			if (StoreSubjectClassFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("��ͬ���������Ʋ����ظ���");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
				}
				if (StoreSubjectClassFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("��ͬ���������Ʋ����ظ���");
					SysUtil.abort();
				}
			}

		}

	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		StoreSubjectClassInfo info = this.editData;
		if (info != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", info.getId().toString()));
			if (StoreSubjectFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("��Ŀ����ѱ���Ŀ���ã�����ɾ����");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.StoreSubjectClassFactory.getRemoteInstance();
	}

	/**
	 * output SelectorItemCollection method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add(new SelectorItemInfo("longNumber"));
		selectors.add(new SelectorItemInfo("parent.*"));
		return selectors;
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		if (STATUS_ADDNEW.equals(getOprtState())) {
			dataObject.put("parent", getUIContext().get(UIContext.PARENTNODE));
		}
		super.setDataObject(dataObject);
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.StoreSubjectClassInfo objectValue = new com.kingdee.eas.fdc.market.StoreSubjectClassInfo();
		StoreSubjectClassInfo storeSubjectClassInfo = (StoreSubjectClassInfo) this.getUIContext().get("storeSubjectClass");
		if (storeSubjectClassInfo != null) {
			objectValue.setParent(storeSubjectClassInfo);
		}
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return objectValue;
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(objValue, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
							prepareNumber(objValue, number);
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
    
    public void setNumberTextEnabled(){
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
	 //ͨ����������ȡ���롣������á�
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }
           
        } catch (Exception err) {
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }

    protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}


}