package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTransactionInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTransactionInfo()
    {
        this("id");
    }
    protected AbstractTransactionInfo(String pkField)
    {
        super(pkField);
        put("tranStateHis", new com.kingdee.eas.fdc.sellhouse.TranStateHisCollection());
        put("tranBusinessOverView", new com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection());
    }
    /**
     * Object:��������'s ���µ���IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBillId()
    {
        return getBOSUuid("billId");
    }
    public void setBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("billId", item);
    }
    /**
     * Object:��������'s ���׷�������property 
     */
    public java.util.Date getTranDate()
    {
        return getDate("tranDate");
    }
    public void setTranDate(java.util.Date item)
    {
        setDate("tranDate", item);
    }
    /**
     * Object:��������'s ��һ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getPreLink()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("preLink"));
    }
    public void setPreLink(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("preLink", item.getValue());
		}
    }
    /**
     * Object:��������'s ��ǰ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getCurrentLink()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("currentLink"));
    }
    public void setCurrentLink(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("currentLink", item.getValue());
		}
    }
    /**
     * Object: �������� 's ���軷����ʷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranStateHisCollection getTranStateHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranStateHisCollection)get("tranStateHis");
    }
    /**
     * Object:��������'s �Ƿ�����property 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    /**
     * Object: �������� 's ���� property 
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
     * Object: �������� 's ҵ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection getTranBusinessOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection)get("tranBusinessOverView");
    }
    /**
     * Object:��������'s �ͻ�����property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object: �������� 's �ͻ��̻� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BB402523");
    }
}