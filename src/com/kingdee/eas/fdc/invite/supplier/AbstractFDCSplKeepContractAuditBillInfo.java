package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplKeepContractAuditBillInfo extends com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo implements Serializable 
{
    public AbstractFDCSplKeepContractAuditBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplKeepContractAuditBillInfo(String pkField)
    {
        super(pkField);
        put("auditBill", new com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection());
        put("auditValue", new com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection());
    }
    /**
     * Object: ��Լ���� 's ������Ŀ property 
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
     * Object: ��Լ���� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: ��Լ���� 's ����ȼ� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection)get("auditBill");
    }
    /**
     * Object: ��Լ���� 's ����ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection getAuditValue()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection)get("auditValue");
    }
    /**
     * Object:��Լ����'s �Ƿ񿢹�����property 
     */
    public boolean isIsCompleteAudit()
    {
        return getBoolean("isCompleteAudit");
    }
    public void setIsCompleteAudit(boolean item)
    {
        setBoolean("isCompleteAudit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC287569");
    }
}