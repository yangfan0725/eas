package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreconcertPostponeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPreconcertPostponeInfo()
    {
        this("id");
    }
    protected AbstractPreconcertPostponeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ԥ������'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:Ԥ������'s ��Ԥ������property 
     */
    public java.util.Date getNowDat()
    {
        return getDate("nowDat");
    }
    public void setNowDat(java.util.Date item)
    {
        setDate("nowDat", item);
    }
    /**
     * Object:Ԥ������'s ԭԤ������property 
     */
    public java.util.Date getOriginalDate()
    {
        return getDate("originalDate");
    }
    public void setOriginalDate(java.util.Date item)
    {
        setDate("originalDate", item);
    }
    /**
     * Object:Ԥ������'s ԭԤ����Ч��property 
     */
    public java.util.Date getOriginalAvailab()
    {
        return getDate("originalAvailab");
    }
    public void setOriginalAvailab(java.util.Date item)
    {
        setDate("originalAvailab", item);
    }
    /**
     * Object:Ԥ������'s ��Ԥ����Ч��property 
     */
    public java.util.Date getNowavailab()
    {
        return getDate("nowavailab");
    }
    public void setNowavailab(java.util.Date item)
    {
        setDate("nowavailab", item);
    }
    /**
     * Object: Ԥ������ 's ���� property 
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
     * Object: Ԥ������ 's Ԥ�������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59919274");
    }
}