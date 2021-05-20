package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplKeepAuditIndexValueInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCSplKeepAuditIndexValueInfo()
    {
        this("id");
    }
    protected AbstractFDCSplKeepAuditIndexValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ģ���¼'s ����÷�property 
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
     * Object:����ģ���¼'s ������property 
     */
    public String getAuditPerson()
    {
        return getString("auditPerson");
    }
    public void setAuditPerson(String item)
    {
        setString("auditPerson", item);
    }
    /**
     * Object:����ģ���¼'s ������property 
     */
    public String getAuditDept()
    {
        return getString("auditDept");
    }
    public void setAuditDept(String item)
    {
        setString("auditDept", item);
    }
    /**
     * Object:����ģ���¼'s ����ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object: ����ģ���¼ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo)get("auditBill");
    }
    public void setAuditBill(com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo item)
    {
        put("auditBill", item);
    }
    /**
     * Object: ����ģ���¼ 's ����ָ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo item)
    {
        put("template", item);
    }
    /**
     * Object:����ģ���¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("69F97413");
    }
}