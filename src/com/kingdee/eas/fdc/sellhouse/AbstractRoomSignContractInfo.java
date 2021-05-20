package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomSignContractInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomSignContractInfo()
    {
        this("id");
    }
    protected AbstractRoomSignContractInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ǩԼ 's ���� property 
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
     * Object:����ǩԼ's ǩԼ����property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ񱸰�property 
     */
    public boolean isIsOnRecord()
    {
        return getBoolean("isOnRecord");
    }
    public void setIsOnRecord(boolean item)
    {
        setBoolean("isOnRecord", item);
    }
    /**
     * Object:����ǩԼ's ��������property 
     */
    public java.util.Date getOnRecordDate()
    {
        return getDate("onRecordDate");
    }
    public void setOnRecordDate(java.util.Date item)
    {
        setDate("onRecordDate", item);
    }
    /**
     * Object:����ǩԼ's Լ���������property 
     */
    public java.util.Date getSignJoinDate()
    {
        return getDate("signJoinDate");
    }
    public void setSignJoinDate(java.util.Date item)
    {
        setDate("signJoinDate", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ�����property 
     */
    public boolean isIsBlankOut()
    {
        return getBoolean("isBlankOut");
    }
    public void setIsBlankOut(boolean item)
    {
        setBoolean("isBlankOut", item);
    }
    /**
     * Object: ����ǩԼ 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object:����ǩԼ's ��ͬ��property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ�ǩ��property 
     */
    public boolean isIsStamp()
    {
        return getBoolean("isStamp");
    }
    public void setIsStamp(boolean item)
    {
        setBoolean("isStamp", item);
    }
    /**
     * Object:����ǩԼ's ǩ������property 
     */
    public java.util.Date getStampDate()
    {
        return getDate("stampDate");
    }
    public void setStampDate(java.util.Date item)
    {
        setDate("stampDate", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ���ȡproperty 
     */
    public boolean isIsPullDown()
    {
        return getBoolean("isPullDown");
    }
    public void setIsPullDown(boolean item)
    {
        setBoolean("isPullDown", item);
    }
    /**
     * Object:����ǩԼ's ��ȡ����property 
     */
    public java.util.Date getPullDownDate()
    {
        return getDate("pullDownDate");
    }
    public void setPullDownDate(java.util.Date item)
    {
        setDate("pullDownDate", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ������͹�������property 
     */
    public boolean isIsIntegral()
    {
        return getBoolean("isIntegral");
    }
    public void setIsIntegral(boolean item)
    {
        setBoolean("isIntegral", item);
    }
    /**
     * Object:����ǩԼ's �Ƿ�ֱ��ǩԼproperty 
     */
    public boolean isIsImmediacySign()
    {
        return getBoolean("isImmediacySign");
    }
    public void setIsImmediacySign(boolean item)
    {
        setBoolean("isImmediacySign", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA57FB45");
    }
}