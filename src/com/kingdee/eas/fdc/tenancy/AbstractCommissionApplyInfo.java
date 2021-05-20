package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionApplyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommissionApplyInfo()
    {
        this("id");
    }
    protected AbstractCommissionApplyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӷ������ 's ����ͻ� property 
     */
    public com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo getIntentionCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo)get("intentionCustomer");
    }
    public void setIntentionCustomer(com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo item)
    {
        put("intentionCustomer", item);
    }
    /**
     * Object: Ӷ������ 's �ɽ���Ŀ property 
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
     * Object: Ӷ������ 's �ɽ����� property 
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
     * Object:Ӷ������'s �ɽ��ܼ�property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:Ӷ������'s Ӷ����property 
     */
    public java.math.BigDecimal getCommissionAmount()
    {
        return getBigDecimal("commissionAmount");
    }
    public void setCommissionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("commissionAmount", item);
    }
    /**
     * Object:Ӷ������'s Ӷ�����property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:Ӷ������'s ��֧��property 
     */
    public boolean isIsPayed()
    {
        return getBoolean("isPayed");
    }
    public void setIsPayed(boolean item)
    {
        setBoolean("isPayed", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("616C187A");
    }
}