/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomDisplaySetViewUI extends AbstractRoomDisplaySetViewUI
{
	private static final Logger logger = CoreUIObject.getLogger(RoomDisplaySetViewUI.class);
	
	public RoomDisplaySetViewUI() throws Exception
	{
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//RoomDisplaySetting setting= new RoomDisplaySetting();
		
		SellProjectResourceEnum proResenum =(SellProjectResourceEnum)this.getUIContext().get("sellProjectResEnum");
		Color c = new Color(0);
		if(SellProjectResourceEnum.DEVELOPER.equals(proResenum) || SellProjectResourceEnum.INVESTOR.equals(proResenum))
		{
			this.kDLabel1.setBackground(c.LIGHT_GRAY);
			this.kDLabel1.setOpaque(true);
			this.kDLabel1.setText("      ����");
			this.kDLabel2.setBackground(c.CYAN);
			this.kDLabel2.setOpaque(true);
			this.kDLabel2.setText("      ����");
			this.kDLabel3.setBackground(c.ORANGE);
			this.kDLabel3.setOpaque(true);
			this.kDLabel3.setText("      ����");
			this.kDLabel4.setBackground(c.PINK);
			this.kDLabel4.setOpaque(true);
			this.kDLabel4.setText("      ����");
			this.kDLabel5.setVisible(false);
			this.kDLabel6.setVisible(false);
			this.kDLabel7.setVisible(false);
			this.kDLabel8.setVisible(false);
		}else
		{
			this.kDLabel1.setVisible(false);
			this.kDLabel2.setVisible(false);
			this.kDLabel3.setVisible(false);
			this.kDLabel4.setVisible(false);
			this.kDLabel5.setBackground(c.LIGHT_GRAY);
			this.kDLabel5.setOpaque(true);
			this.kDLabel5.setText("      ί����ҵ");
			this.kDLabel6.setBackground(c.CYAN);
			this.kDLabel6.setOpaque(true);
			this.kDLabel6.setText("      ί������");
			this.kDLabel7.setBackground(c.ORANGE);
			this.kDLabel7.setOpaque(true);
			this.kDLabel7.setText("      ί������");
			this.kDLabel8.setBackground(c.PINK);
			this.kDLabel8.setOpaque(true);
			this.kDLabel8.setText("      ί������");
		}	
	}
	
	public static void insertUIToScrollPanel(KDScrollPane panel,SellProjectResourceEnum projectenum)
	throws UIException {
		UIContext uiContext = new UIContext();
		uiContext.put("sellProjectResEnum",projectenum);
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(
				RoomDisplaySetViewUI.class.getName(), uiContext, null, "VIEW");
		if(panel==null)return;
		panel.setViewportView(detailUI);
		panel.setKeyBoardControl(true);
	}
	
	public void storeFields() {
		super.storeFields();
	}
}