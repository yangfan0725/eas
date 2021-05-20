package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChooseRoomInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChooseRoomInfo()
    {
        this("id");
    }
    protected AbstractChooseRoomInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection());
    }
    /**
     * Object: ѡ���� 's ������Ϣ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: ѡ���� 's ��¥�ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection)get("customerEntry");
    }
    /**
     * Object: ѡ���� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ѡ���� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesMan");
    }
    public void setSalesMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesMan", item);
    }
    /**
     * Object: ѡ���� 's Ԥ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSincerityPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sincerityPurchase");
    }
    public void setSincerityPurchase(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sincerityPurchase", item);
    }
    /**
     * Object:ѡ����'s ѡ����property 
     */
    public String getChooser()
    {
        return getString("chooser");
    }
    public void setChooser(String item)
    {
        setString("chooser", item);
    }
    /**
     * Object:ѡ����'s ��ϵ�绰property 
     */
    public String getLinkPhone()
    {
        return getString("linkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("linkPhone", item);
    }
    /**
     * Object:ѡ����'s ѡ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum getChooseRoomStateEnum()
    {
        return com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum.getEnum(getString("chooseRoomStateEnum"));
    }
    public void setChooseRoomStateEnum(com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum item)
    {
		if (item != null) {
        setString("chooseRoomStateEnum", item.getValue());
		}
    }
    /**
     * Object:ѡ����'s ѡ������property 
     */
    public int getChooseRoomNo()
    {
        return getInt("chooseRoomNo");
    }
    public void setChooseRoomNo(int item)
    {
        setInt("chooseRoomNo", item);
    }
    /**
     * Object: ѡ���� 's �����ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCus()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCus");
    }
    public void setCluesCus(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCus", item);
    }
    /**
     * Object:ѡ����'s ѡ���ͻ����property 
     */
    public String getCusStr()
    {
        return getString("cusStr");
    }
    public void setCusStr(String item)
    {
        setString("cusStr", item);
    }
    /**
     * Object:ѡ����'s ѡ�����property 
     */
    public String getChooseNo()
    {
        return getString("chooseNo");
    }
    public void setChooseNo(String item)
    {
        setString("chooseNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B45858AD");
    }
}