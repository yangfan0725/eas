/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 * modify by qinyouzhen,��¥�Ż� V7.0.3
 * @date 20110613
 */
public class RoomModelTypeEditUI extends AbstractRoomModelTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomModelTypeEditUI.class);
	  SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public RoomModelTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}else{
			if (STATUS_ADDNEW.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			}else if(STATUS_EDIT.equals(getOprtState())){
				actionEdit.setEnabled(false);
			}else if(STATUS_VIEW.equals(getOprtState())){
				actionSubmit.setEnabled(false);
			}else{
				actionAddNew.setEnabled(true);
				actionEdit.setEnabled(true);
				actionRemove.setEnabled(true);
			}
		}
	}

	protected IObjectValue createNewData() {
		RoomModelTypeInfo roomModelType = new RoomModelTypeInfo();
		roomModelType.setIsEnabled(true);
		return roomModelType;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomModelTypeFactory.getRemoteInstance();
	}
	
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}
	
	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (verifyCancelorCancelCancelByID("ɾ��",
				selectID)) {// �ж��Ƿ����ý���
			return;
//		}else if(selectID != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("buildingProperty.id", selectID));
//			if (BuildingFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("�Ѿ���¥��ʹ��,����ɾ��!");
//				return;
//			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (verifyCancelorCancelCancelByID("�޸�",
				selectID)) {// �ж��Ƿ����ý���
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * �жϼ�¼�Ƿ�����,�޸ĺ�ɾ��ʱ����
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCancelorCancelCancelByID(
			String words, String selectID) throws Exception {
		boolean flag = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", selectID));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));// �ж��Ƿ�����
		if (getBizInterface().exists(filter)) {// �жϼ�¼�Ƿ����
			MsgBox.showWarning("����¼�Ѿ����ã�����" + words + "!");
			flag = true;
		}
		return flag;
	}
}