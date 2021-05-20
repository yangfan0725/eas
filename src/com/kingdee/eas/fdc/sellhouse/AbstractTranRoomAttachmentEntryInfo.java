package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranRoomAttachmentEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTranRoomAttachmentEntryInfo()
    {
        this("");
    }
    protected AbstractTranRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����������Ϣ���� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:����������Ϣ����'s ������property 
     */
    public java.math.BigDecimal getMergeAmount()
    {
        return getBigDecimal("mergeAmount");
    }
    public void setMergeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mergeAmount", item);
    }
    /**
     * Object:����������Ϣ����'s �Ƿ����ͬproperty 
     */
    public boolean isIsAttachcmentToContract()
    {
        return getBoolean("isAttachcmentToContract");
    }
    public void setIsAttachcmentToContract(boolean item)
    {
        setBoolean("isAttachcmentToContract", item);
    }
    /**
     * Object: ����������Ϣ���� 's �������� property 
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
     * Object:����������Ϣ����'s �����׼�ܼۿ���property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:����������Ϣ����'s �ۿ�property 
     */
    public String getAgioDes()
    {
        return getString("agioDes");
    }
    public void setAgioDes(String item)
    {
        setString("agioDes", item);
    }
    /**
     * Object:����������Ϣ����'s ���佨���������property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:����������Ϣ����'s ���������������property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:����������Ϣ����'s ���佨�����ۿ���property 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:����������Ϣ����'s �������ڵ��ۿ���property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95A68C4E");
    }
}