/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CodeGenerater;
import com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChequeBookInUI extends AbstractChequeBookInUI {
	private static final Logger logger = CoreUIObject
	.getLogger(ChequeBookInUI.class);

	//	private CompanyOrgUnitInfo cur = null;

	public ChequeBookInUI() throws Exception {
		super();
		//		cur = ContextHelperFactory.getRemoteInstance().getCurrentCompany();
		//		// ��ǰ��˾Ϊ����ʱ����ʾ�û������л�ʵ�幫˾����
		//		if (!cur.isIsBizUnit()) {
		//			MsgBox.showInfo(this, "���л���ʵ�幫˾��");
		//			this.abort();
		//		}
		initHeadData();
		// ����˵���ı����еĻس����з�
		String changeLine = this.txtRemark.getText().replaceAll("\\\\r", "\r")
		.replaceAll("\\\\n", "\n");
		this.txtRemark.setText(changeLine);
	}

	private void initHeadData() throws Exception {
		// ������ʼ��ˮ�����ޣ���������
		SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 999999, 1);
		this.spinStartNum.setModel(sm);
		SpinnerNumberModel count = new SpinnerNumberModel(1, 1, 999999, 1);
		this.spinCount.setModel(count);

		this.comboChequeType.setRequired(true);
		this.f7Currency.setRequired(true);
		this.spinCount.setRequired(true);
		this.spinStartNum.setRequired(true);
		this.txtCodeRule.setRequired(true);
		this.f7Keeper.setRequired(true);
		this.f7KeepOrgUnit.setEnabled(false);
		this.f7Creator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		SHEHelper.setTextFormat(this.txtLimitAmount);
		this.txtLimitAmount.setNegatived(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.menuSubmitOption.setVisible(false);
		
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			UserInfo curUser = SysContext.getSysContext().getCurrentUserInfo();

			this.f7Keeper.setValue(curUser);
			this.f7Creator.setValue(curUser);
			SaleOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentSaleUnit();
			if (orgInfo != null) {
				this.f7KeepOrgUnit.setValue(orgInfo);
			}
		}
		this.loadFields();
	}

	public void loadFields() {
		super.loadFields();
		
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		this.verifyEmpty(this, f7Currency, "�ұ���Ϊ��");

		this.verifyEmpty(this, spinCount, "��������Ϊ��");
		this.verifyEmpty(this, spinStartNum, "��ʼ��ˮ�Ų���Ϊ��");

		// ���������Ϊ��
		if (this.txtCodeRule.getText() == null
				|| this.txtCodeRule.getText().trim().equals("")) {
			MsgBox.showInfo(this, "���������Ϊ�գ�");
			this.abort();
		}

		// ����������һ��*��
		if (this.txtCodeRule.getText() != null
				&& this.txtCodeRule.getText().indexOf("*") < 0) {
			MsgBox.showInfo(this, "����������һ��*�ţ�");
			this.abort();
		}
		//
		String number = this.txtCodeRule.getText()
		+ this.spinCount.getIntegerVlaue();
		if (number.length() > 80) {
			MsgBox.showInfo(this, "֧Ʊ������򳤶�+�������Ȳ��ܳ���80���ַ�");
			SysUtil.abort();
		}

		this.verifyEmpty(this, f7Keeper, "�����˲���Ϊ��");
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);// У��
		boolean checkIsDump = checkIsDump();

		if (checkIsDump) {
			MsgBox.showInfo(this, "�����ظ���Ʊ�ݱ��");
			return;
		}

		// ������ӿհ�Ʊ��
		doAddBatch();

		// ��ʾ�ɹ���Ϣ
		setMessageText("�Ǽǳɹ�");
		showMessage();
		loadFields();
		// initOldData(editData);
		// this.txtCodeRule.setText("*");
	}

	private void doAddBatch() throws Exception, BOSException, EASBizException {
		ChequeCollection collection = new ChequeCollection();
		String codeRule = this.txtCodeRule.getText();// ��������ɽ��洫��

		int count = Integer.parseInt(this.spinCount.getValue().toString());// �������ɽ��洫��
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue()
				.toString());// ��ʼ��ˮ�ţ��ɽ��洫��

		//		String batch = new Long(System.currentTimeMillis()).toString();// ��ʱʹ��ϵͳʱ�䣬�Ժ���ϱ������

		// ���ñ��������������ʼ��ˮ��
		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);

		for (int i = 0; i < count; i++) {
			ChequeInfo item = new ChequeInfo();
			item.setChequeType((ChequeTypeEnum) this.comboChequeType.getSelectedItem());
			item.setStatus(ChequeStatusEnum.Booked);
			item.setVerifyStatus(VerifyStatusEnum.NotVerified);
			item.setLimitAmount(this.txtLimitAmount.getBigDecimalValue());
			item.setCurrency((CurrencyInfo) this.f7Currency.getValue());
			//			item.setBatch(batch);//TODO �����Ƿ���Ҫ
			item.setNumber(codeGen.nextNumber());// ���ݱ��������������ʼ��ˮ������֧Ʊ����
			item.setKeeper((UserInfo) this.f7Keeper.getValue());

			Object obj = this.f7KeepOrgUnit.getValue();
			if (obj instanceof SaleOrgUnitInfo) {
				SaleOrgUnitInfo saleOrgUnit = (SaleOrgUnitInfo) obj;
				item.setKeepOrgUnit(saleOrgUnit.castToFullOrgUnitInfo());	
			}

			item.setCreator((UserInfo) this.f7Creator.getValue());
			item.setCreateTime(this.pkCreateTime.getTimestamp());
			item.setDescription(this.txtDescription.getText());

			collection.add(item);
		}

		ChequeFactory.getRemoteInstance().addBatch(collection);
	}

	/**
	 * �ж��Ƿ����ظ������Ʊ��
	 * */
	private boolean checkIsDump() throws BOSException, EASBizException {
		String codeRule = this.txtCodeRule.getText();// ��������ɽ��洫��
		int count = Integer.parseInt(this.spinCount.getValue().toString());// �������ɽ��洫��
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue().toString());// ��ʼ��ˮ�ţ��ɽ��洫��

		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);
		String[] numbers = new String[count];

		for (int i = 0; i < count; i++) {
			numbers[i] = codeGen.nextNumber();
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", FDCHelper.getSetByArray(numbers),CompareType.INCLUDE));

		return ChequeFactory.getRemoteInstance().exists(filter);		
	}

	//��д������౨��
	protected void checkIsOUSealUp() throws Exception {
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.txtCodeRule.setText("*");
		super.actionAddNew_actionPerformed(e);
	}

	private void verifyEmpty(ChequeBookInUI inUI, KDSpinner spinner, String msg) {
		Integer integer = spinner.getIntegerVlaue();
		if (integer == null) {
			spinner.requestFocus(true);
			MsgBox.showWarning(inUI, msg);
			SysUtil.abort();
		}

	}

	private void verifyEmpty(CoreUIObject inUI, KDBizPromptBox bizBox,
			String msg) {
		Object content = bizBox.getData();
		if (content == null) {
			bizBox.requestFocus(true);
			MsgBox.showWarning(inUI, msg);
			SysUtil.abort();
		}
	}

	//bt338400 ���EDIT���洫��IDΪ�յ�BUG,����LIST�Ľ���Ǽ�Ʊ��ʱ������opertstateΪaddnew,��������chequeBookInUI����ʱû��
	//����opertstate�������ڱ���ʱ�ᱨ��ָ�� bt338997 by lijun
	protected void inOnload() throws Exception {
		this.setOprtState("ADDNEW");
		// super.inOnload();
	}
}