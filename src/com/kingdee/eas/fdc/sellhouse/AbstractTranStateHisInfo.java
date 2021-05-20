package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranStateHisInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTranStateHisInfo()
    {
        this("id");
    }
    protected AbstractTranStateHisInfo(String pkField)
    {
        super(pkField);
        put("tranBusinessOverView", new com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection());
    }
    /**
     * Object:������ʷ's ��ǰ����property 
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
     * Object:������ʷ's ��һ����property 
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
     * Object:������ʷ's ��ʷ����idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getHisBillID()
    {
        return getBOSUuid("hisBillID");
    }
    public void setHisBillID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("hisBillID", item);
    }
    /**
     * Object: ������ʷ 's ���׵�id property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TransactionInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.TransactionInfo item)
    {
        put("head", item);
    }
    /**
     * Object:������ʷ's ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:������ʷ's ���׷�������property 
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
     * Object: ������ʷ 's ҵ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection getTranBusinessOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection)get("tranBusinessOverView");
    }
    /**
     * Object:������ʷ's �ͻ�����property 
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
     * Object: ������ʷ 's ��ʷ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getHisRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("hisRoom");
    }
    public void setHisRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("hisRoom", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9A4933E7");
    }
}