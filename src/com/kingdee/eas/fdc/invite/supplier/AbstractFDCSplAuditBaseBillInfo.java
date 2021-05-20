package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractFDCSplAuditBaseBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCSplAuditBaseBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplAuditBaseBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���󵥻��� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:���󵥻���'s �ۺ�����property 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object:���󵥻���'s ҵ������property 
     */
    public java.util.Date getBusinessDate()
    {
        return getDate("businessDate");
    }
    public void setBusinessDate(java.util.Date item)
    {
        setDate("businessDate", item);
    }
    /**
     * Object: ���󵥻��� 's ����ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo item)
    {
        put("template", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F2E1651");
    }
}