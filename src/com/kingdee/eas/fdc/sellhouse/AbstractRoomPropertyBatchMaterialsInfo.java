package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBatchMaterialsInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomPropertyBatchMaterialsInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBatchMaterialsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������Ȩ�������� 's ������Ȩ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:������Ȩ��������'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:������Ȩ��������'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:������Ȩ��������'s ˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:������Ȩ��������'s �Ƿ����property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:������Ȩ��������'s ��������property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78AADD1D");
    }
}