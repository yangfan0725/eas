package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplPeriodAuditBillInfo extends com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo implements Serializable 
{
    public AbstractFDCSplPeriodAuditBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplPeriodAuditBillInfo(String pkField)
    {
        super(pkField);
        put("auditBill", new com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection());
        put("auditValue", new com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditIndexValueCollection());
    }
    /**
     * Object: �������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: �������� 's ���󵥷�¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection)get("auditBill");
    }
    /**
     * Object: �������� 's ����ָ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditIndexValueCollection getAuditValue()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditIndexValueCollection)get("auditValue");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3BD7751F");
    }
}