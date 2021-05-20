package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;

public class RoomSelectDisPlaySetViewUI extends DisplaySetViewUI{

	public RoomSelectDisPlaySetViewUI() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		RoomDisplaySetting setting = new RoomDisplaySetting();
		this.kDLabel7.setVisible(false);
		this.kDLabel1.setBackground(setting.getBaseRoomSetting().getInitColor());
		this.kDLabel1.setOpaque(true);
		this.kDLabel1.setText(" δ����(" + RoomSelectUI.initCount + ")");
		this.kDLabel2.setBackground(setting.getBaseRoomSetting().getOnShowColor());
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText(" ����(" + RoomSelectUI.onShowCount + ")");
		this.kDLabel5.setBackground(setting.getBaseRoomSetting().getPrePurColor());
		this.kDLabel5.setOpaque(true);
		this.kDLabel5.setText(" Ԥ��(" + RoomSelectUI.prePur + ")");
		this.kDLabel6.setBackground(setting.getBaseRoomSetting().getPurColor());
		this.kDLabel6.setOpaque(true);
		this.kDLabel6.setText(" �Ϲ�(" + RoomSelectUI.purchaseCount + ")");
		this.kDLabel8.setBackground(setting.getBaseRoomSetting().getSignColor());
		this.kDLabel8.setOpaque(true);
		this.kDLabel8.setText(" ǩԼ(" + RoomSelectUI.signContractCount + ")");
		this.kDLabel3.setBackground(setting.getBaseRoomSetting().getKeepDownColor());
		this.kDLabel3.setOpaque(true);
		this.kDLabel3.setText(" Ԥ��(" + RoomSelectUI.keepDownCount + ")");
		this.kDLabel4.setBackground(setting.getBaseRoomSetting().getSincerPurColor());
		this.kDLabel4.setOpaque(true);
		this.kDLabel4.setText(" �ź�(" + RoomSelectUI.sincerPurCount + ")");
		this.kDLabel9.setVisible(false);
		
		this.kDLabel10.setBackground(setting.getBaseRoomSetting().getControlColor());
		this.kDLabel10.setOpaque(true);
		this.kDLabel10.setText(" ����(" + RoomSelectUI.controlCount + ")");
	}
	public static void insertUIToScrollPanel(KDScrollPane panel) throws UIException {
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomSelectDisPlaySetViewUI.class.getName(), uiContext, null, "VIEW");
		panel.setViewportView(detailUI);
		panel.setKeyBoardControl(true);

	}
}
