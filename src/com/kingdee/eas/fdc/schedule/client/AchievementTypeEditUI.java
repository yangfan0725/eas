/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.AchievementProfessionInfo;
import com.kingdee.eas.fdc.schedule.AchievementStageInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeFactory;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.LbFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;


/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author ���
 * @version EAS7.0
 * @createDate 2011-8-12
 * @see
 */
public class AchievementTypeEditUI extends AbstractAchievementTypeEditUI
{
	/**
	 *  ������󳤶ȷſ���9999��added by Owen_wen 2013-8-26
	 */
	private static final int MaxNumberLength = 4;
	private static final Logger logger = CoreUIObject.getLogger(AchievementTypeEditUI.class);
    
    public AchievementTypeEditUI() throws Exception
    {
        super();
    }

    protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		AchievementTypeInfo info = new AchievementTypeInfo();
		String maxNumber = null;
		try {
			// �õ�������
			maxNumber = LbFacadeFactory.getRemoteInstance().getBiggestNumber();
		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		}
		String number = (Integer.parseInt(maxNumber) + 1) + "";
		if (number.length() > MaxNumberLength) { 
			FDCMsgBox.showInfo("�����Ѵﵽ��󳤶ȣ���������");
			abort();
		}
		if (number.length() == 1) {
			number = "0" + number;
		}
		info.setNumber(number);
		AchievementStageInfo stage = (AchievementStageInfo) getUIContext().get("stage");
		AchievementProfessionInfo profession = (AchievementProfessionInfo) getUIContext().get("profession");
		if (stage != null) {
			info.setStage(stage);
		}
		if (profession != null) {
			info.setProfession(profession);
		}
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AchievementTypeFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		// ��������֯�Ƿ��ǳɱ�����
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("��ǰ��֯���ǳɱ����ģ����ܽ��룡");
			SysUtil.abort();
		}
		super.onLoad();
		this.windowTitle = "�׶��Գɹ�������ñ༭����";
		if (OprtState.VIEW.equals(getOprtState())) {
			this.windowTitle = "�׶��Գɹ�������ò鿴����";
		}
		// ��ʼ������F7
		initF7();
		// ���ν��ã����ã�ɾ����ť
		this.btnCancel.setEnabled(false);
		this.btnCancel.setVisible(false);
		this.btnRemove.setEnabled(false);
		this.btnRemove.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.btnCancelCancel.setVisible(false);
	}

	private void initF7() {
		// �ɹ��׶�
		prmtStage.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementStageQuery");
		prmtStage.setDisplayFormat("$name$");
		prmtStage.setCommitFormat("$name$ $number$");
		prmtStage.setEditable(true);
		prmtStage.setRequired(true);
		// �ɹ�רҵ
		prmtProfession.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementProfessionQuery");
		// ����ÿ���ʾ����ʲô
		prmtProfession.setDisplayFormat("$name$");
		prmtProfession.setCommitFormat("$name$ $number$");
		prmtProfession.setEditable(true);
		prmtProfession.setRequired(true);

		// �鵵Ŀ¼
		prmtDocDirectory.setQueryInfo("com.kingdee.eas.fdc.schedule.app.Copy_CategoryQuery");
		prmtDocDirectory.setDisplayFormat("$docArea.name$ / $name$");
		prmtDocDirectory.setCommitFormat("$name$ $number$");
		prmtDocDirectory.setEditable(true);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (FDCHelper.isEmpty(editData.getName())) {
			FDCMsgBox.showInfo("�ɹ�������Ʋ���Ϊ��");
			SysUtil.abort();
		}
		// �жϳɹ��׶β���Ϊ��
		if (prmtStage.getValue() == null) {
			FDCMsgBox.showInfo("�ɹ��׶β���Ϊ�գ�");
			SysUtil.abort();
		}
		// �жϳɹ�רҵ����Ϊ��
		if (prmtProfession.getValue() == null) {
			FDCMsgBox.showInfo("�ɹ�רҵ����Ϊ�գ�");
			SysUtil.abort();
		}
		String num = this.txtNumber.getText();
		if (FDCHelper.isEmpty(num)) {
			FDCMsgBox.showInfo("�ɹ������벻��Ϊ��");
			SysUtil.abort();
		}
		boolean flag = num.matches("\\d+");
		// �ж�����ı���
		
		if (flag == false) {
			FDCMsgBox.showInfo("ֻ��������ֵ�ͱ���,����08");
			SysUtil.abort();
		}
		if (num.length() > MaxNumberLength) {
			FDCMsgBox.showInfo("���벻�ܳ���2λ");
			SysUtil.abort();
		} else {
			if (num.length() == 1) {
				num = "0" + num;
			}
		}
		
	}
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("docDirectory.docArea.*"));
		return sic;
    }
}