package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNoTradingSellBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNoTradingSellBillEntryInfo()
    {
        this("id");
    }
    protected AbstractNoTradingSellBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.NoTradingSellBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.NoTradingSellBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.NoTradingSellBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼'s ���۽��property 
     */
    public java.math.BigDecimal getSellAmount()
    {
        return getBigDecimal("sellAmount");
    }
    public void setSellAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmount", item);
    }
    /**
     * Object:�ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼'s �ؿ���property 
     */
    public java.math.BigDecimal getBackAmount()
    {
        return getBigDecimal("backAmount");
    }
    public void setBackAmount(java.math.BigDecimal item)
    {
        setBigDecimal("backAmount", item);
    }
    /**
     * Object: �ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼ 's ��Ŀ property 
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
     * Object: �ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼ 's ���� property 
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
     * Object: �ǲ�����Ŀ���ۻؿ�ȷ�ϵ���¼ 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E0BADD4");
    }
}