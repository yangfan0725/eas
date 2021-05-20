package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplPeriodAuditBillEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCSplPeriodAuditBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCSplPeriodAuditBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������󵥷�¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo)get("auditBill");
    }
    public void setAuditBill(com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo item)
    {
        put("auditBill", item);
    }
    /**
     * Object: �������󵥷�¼ 's ��Ӧ������ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo getSupplierType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo)get("supplierType");
    }
    public void setSupplierType(com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo item)
    {
        put("supplierType", item);
    }
    /**
     * Object:�������󵥷�¼'s ����ǰ״̬property 
     */
    public String getBeforeState()
    {
        return getString("beforeState");
    }
    public void setBeforeState(String item)
    {
        setString("beforeState", item);
    }
    /**
     * Object:�������󵥷�¼'s �Ƿ��������property 
     */
    public boolean isIsAudit()
    {
        return getBoolean("isAudit");
    }
    public void setIsAudit(boolean item)
    {
        setBoolean("isAudit", item);
    }
    /**
     * Object:�������󵥷�¼'s �������property 
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
     * Object:�������󵥷�¼'s �ȼ�property 
     */
    public String getGrade()
    {
        return getString("grade");
    }
    public void setGrade(String item)
    {
        setString("grade", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("726FFE93");
    }
}