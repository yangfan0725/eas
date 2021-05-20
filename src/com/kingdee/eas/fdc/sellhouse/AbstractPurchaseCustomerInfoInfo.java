package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseCustomerInfoInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseCustomerInfoInfo()
    {
        this("id");
    }
    protected AbstractPurchaseCustomerInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Ϲ����ͻ���Ϣ 's �Ϲ�ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�Ϲ����ͻ���Ϣ's ��Ȩ����property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    /**
     * Object: �Ϲ����ͻ���Ϣ 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:�Ϲ����ͻ���Ϣ's �ͻ�����property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("54C584A8");
    }
}