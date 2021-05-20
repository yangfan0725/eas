package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMortagageControlInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMortagageControlInfo()
    {
        this("id");
    }
    protected AbstractMortagageControlInfo(String pkField)
    {
        super(pkField);
        put("roomEntry", new com.kingdee.eas.fdc.sellhouse.MortagageControlEntryCollection());
    }
    /**
     * Object: ��Ѻ���� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MortagageControlEntryCollection getRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MortagageControlEntryCollection)get("roomEntry");
    }
    /**
     * Object:��Ѻ����'s ��Ѻ�����Ƿ��������property 
     */
    public boolean isIsSell()
    {
        return getBoolean("isSell");
    }
    public void setIsSell(boolean item)
    {
        setBoolean("isSell", item);
    }
    /**
     * Object: ��Ѻ���� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: ��Ѻ���� 's �����Ѻ��Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAntiMortagagor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("antiMortagagor");
    }
    public void setAntiMortagagor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("antiMortagagor", item);
    }
    /**
     * Object:��Ѻ����'s �����Ѻ����property 
     */
    public java.util.Date getAntiMortagageDate()
    {
        return getDate("antiMortagageDate");
    }
    public void setAntiMortagageDate(java.util.Date item)
    {
        setDate("antiMortagageDate", item);
    }
    /**
     * Object:��Ѻ����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.MortagageEnum getMortagageState()
    {
        return com.kingdee.eas.fdc.sellhouse.MortagageEnum.getEnum(getString("mortagageState"));
    }
    public void setMortagageState(com.kingdee.eas.fdc.sellhouse.MortagageEnum item)
    {
		if (item != null) {
        setString("mortagageState", item.getValue());
		}
    }
    /**
     * Object: ��Ѻ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DDBA6243");
    }
}