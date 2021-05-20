package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * ѡ�񷿼䵯������F7�Ի���
 * @author laiquan_luo
 *
 */
public final class FDCRoomPromptDialog extends KDCommonPromptDialog
{
	protected IUIWindow classDlg = null;

	protected IUIObject owner;

	protected AccountTableInfo tableInfo;

	protected CompanyOrgUnitInfo companyInfo;

	protected FilterInfo filter = null;

	protected boolean isCFreeze = false;

	protected boolean isLeaf = false;

	protected boolean isOnlyParent = false;

	protected boolean isOnlyLeaf = false;

	protected boolean isOnlyLevelOne = false;

	protected boolean isRefresh = false;

	protected boolean isShowLongName = false;
	/**
	 * ���䷶Χ
	 */
	private RoomCollection roomColl = null;
	/**
	 * �����Ƿ��ѡ��
	 */
	private Boolean isMultiSelect = Boolean.FALSE;
	private SellProjectInfo sellProjectInfo = null;
	/**
	 * ¥��
	 */
	private BuildingInfo buildingInfo = null;
	/**
	 * ��Ԫ
	 */
	private BuildingUnitInfo buildUnit = null; 
	/**
	 * ����ϵͳ
	 *
	 */
	private MoneySysTypeEnum moneySysTypeEnum;
	

	public FDCRoomPromptDialog()
	{
	}
	/**
	 * 
	 * @param isMultiSelect �Ƿ�֧�ַ����ѡ
	 * @param buildingInfo ¥��
	 * @param unit ��Ԫ
	 * @param moneySysTypeEnum  ����ϵͳ
	 * @param roomColl �Է�����з�Χ���ƣ��������Ҫ���ƣ���ֱ�Ӵ���ֵ null ����
	 */
	public FDCRoomPromptDialog(Boolean isMultiSelect, BuildingInfo buildingInfo, BuildingUnitInfo buildUnit,MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomColl)
	{
		this.isMultiSelect = isMultiSelect;
		this.buildingInfo = buildingInfo;
		this.buildUnit = buildUnit;
		this.moneySysTypeEnum = moneySysTypeEnum;
		this.roomColl = roomColl;
		
	}
	
	public FDCRoomPromptDialog(Boolean isMultiSelect, BuildingInfo buildingInfo, BuildingUnitInfo buildUnit,MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomColl,SellProjectInfo sellProjectInfo)
	{
		this.isMultiSelect = isMultiSelect;
		this.buildingInfo = buildingInfo;
		this.buildUnit = buildUnit;
		this.moneySysTypeEnum = moneySysTypeEnum;
		this.roomColl = roomColl;
		this.sellProjectInfo = sellProjectInfo;
		
	}

	public FDCRoomPromptDialog(IUIObject owner)
	{
		this.owner = owner;
	}

	public FDCRoomPromptDialog(IUIObject owner,
			AccountTableInfo accountTableInfo)
	{
		this.owner = owner;
		tableInfo = accountTableInfo;
	}

	public FDCRoomPromptDialog(IUIObject owner,
			AccountTableInfo accountTableInfo, FilterInfo filter)
	{
		this.owner = owner;
		tableInfo = accountTableInfo;
		this.filter = filter;
	}

	public FDCRoomPromptDialog(IUIObject owner,
			AccountTableInfo accountTableInfo, FilterInfo filter,
			boolean isOnlyLevelOne)
	{
		this.owner = owner;
		tableInfo = accountTableInfo;
		this.filter = filter;
		this.isOnlyLevelOne = isOnlyLevelOne;
	}

	public FDCRoomPromptDialog(IUIObject owner,
			AccountTableInfo accountTableInfo, FilterInfo filter,
			boolean isOnlyParent, boolean isOnlyLeaf)
	{
		this.owner = owner;
		tableInfo = accountTableInfo;
		this.isOnlyParent = isOnlyParent;
		this.filter = filter;
		this.isOnlyLeaf = isOnlyLeaf;
	}

	public FDCRoomPromptDialog(IUIObject owner,
			AccountTableInfo accountTableInfo, boolean isOnlyLevelOne)
	{
		this.owner = owner;
		this.tableInfo = accountTableInfo;
		this.isOnlyLevelOne = isOnlyLevelOne;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company)
	{
		this.owner = owner;
		companyInfo = company;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			FilterInfo filter)
	{
		this.owner = owner;
		companyInfo = company;
		this.filter = filter;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			FilterInfo filter, boolean isOnlyParent, boolean isOnlyLeaf)
	{
		this.owner = owner;
		companyInfo = company;
		this.filter = filter;
		this.isOnlyParent = isOnlyParent;
		this.isOnlyLeaf = isOnlyLeaf;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			boolean isCFreeze, boolean isLeaf)
	{
		this.owner = owner;
		this.companyInfo = company;
		this.isCFreeze = isCFreeze;
		this.isLeaf = isLeaf;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			AccountTableInfo accountTableInfo, FilterInfo filter,
			boolean isOnlyParent, boolean isOnlyLeaf)
	{
		this.owner = owner;
		this.companyInfo = company;
		this.tableInfo = accountTableInfo;
		this.filter = filter;
		this.isOnlyParent = isOnlyParent;
		this.isOnlyLeaf = isOnlyLeaf;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			AccountTableInfo accountTableInfo, FilterInfo filter,
			boolean isOnlyParent, boolean isOnlyLeaf, boolean isShowLongName)
	{
		this.owner = owner;
		this.companyInfo = company;
		this.tableInfo = accountTableInfo;
		this.filter = filter;
		this.isOnlyParent = isOnlyParent;
		this.isOnlyLeaf = isOnlyLeaf;
		this.isShowLongName = isShowLongName;
	}

	public FDCRoomPromptDialog(IUIObject owner, CompanyOrgUnitInfo company,
			FilterInfo filter, boolean isOnlyParent, boolean isOnlyLeaf,
			boolean isRefresh)
	{
		this.owner = owner;
		companyInfo = company;
		this.filter = filter;
		this.isOnlyParent = isOnlyParent;
		this.isOnlyLeaf = isOnlyLeaf;
		this.isRefresh = isRefresh;
	}

	public String getUITitle()
	{
		return new String("����ѡ��");
	}

	public void show()
	{

		Map map = new HashMap();
		
		map.put("isMultiSelect",this.isMultiSelect);
		map.put("unit",buildUnit);
		map.put("buildingInfo",this.buildingInfo);
		map.put("moneySysTypeEnum",this.moneySysTypeEnum);
		map.put("roomColl",this.roomColl);
		map.put("sellProjectInfo", sellProjectInfo);
		IUIFactory uiFactory = null;
		try
		{

			if (isRefresh || classDlg == null)
			{
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(RoomSelectUIForF7.class.getName(),map,null,"VIEW");
			}
			//ȷ��ÿ����ʾisCanceled=true
			if (classDlg != null && classDlg.getUIObject() != null)
			{
				((RoomSelectUIForF7)classDlg.getUIObject()).isCanceled=true;
			}
			classDlg.show();
		} catch (BOSException ex)
		{
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	/**
	 * �ж��Ƿ���ȡ����ť�������Ƿ����ȡֵ����
	 * @author laiquan_luo
	 */
	public boolean isCanceled()
	{

        if(classDlg!=null)
        {
            return((RoomSelectUIForF7)classDlg.getUIObject()).isCanceled ;
        }
        else
        {
            return true;
        }
	}

	/**
	 * ����ֵ
	 * @author laiquan_luo
	 */
	public Object getData()
	{
		RoomSelectUIForF7 ui = (RoomSelectUIForF7) classDlg.getUIObject();
		Object[] returnValue;
		try
		{
			//�����ѡ����ûʵ����֤����
			if(this.isMultiSelect.booleanValue())
			{
//				RoomInfo [] roomInfo = (RoomInfo[]) ui.getReturnValueArray();
//				if(roomInfo != null)
//				{
//					returnValue = new Object[roomInfo.length];
//					returnValue = roomInfo;
//				}
//				else
//				{
//					return null;
//				}
				return ui.getReturnValueArray();
			}
			//��ѡ
			else
			{
				RoomInfo tempRoom = (RoomInfo) ui.getReturnValue();
				if(tempRoom != null)
				{
					returnValue = new Object[]{tempRoom};
				}
				else
				{
					return null;
				}
			}
		} catch (Exception err)
		{
			return null;
		}
		return returnValue;
	}
}
