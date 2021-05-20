package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.fdc.propertymgmt.RoomChargeInfo;
import com.kingdee.eas.fdc.propertymgmt.client.RoomChargeEditUI;

public class TenRoomChargeSelectUI extends RoomChargeEditUI {

	private static final String KEY_ROOM_CHARGE = "roomCharge";
	public TenRoomChargeSelectUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//���ø�ֵ�ᴥ��ֵ�ı��¼����Ӷ����ڿͻ�
		this.prmtroom.setValue(this.getUIContext().get("room"));
//		prmtroom_propertyChange(null);
		
		this.menuBar.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnSubmit.setText("ȷ��");
		this.btnSubmit.setToolTipText("ȷ��");
		
		
		this.prmtroom.setEnabled(false);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//TODO ��װTenancyRoomChargeEntryInfo�������Է���.���ܻ�����Щ��֤�Ĺ���
		super.storeFields();
		RoomChargeInfo roomCharge = this.editData;
		
		this.getUIContext().put(KEY_ROOM_CHARGE, roomCharge);
		this.disposeUIWindow();
	}

	public RoomChargeInfo getRoomCharge() {
		return (RoomChargeInfo)this.getUIContext().get(KEY_ROOM_CHARGE);
	}
	
}
