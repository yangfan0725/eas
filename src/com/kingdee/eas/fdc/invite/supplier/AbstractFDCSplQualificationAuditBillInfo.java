package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplQualificationAuditBillInfo extends com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo implements Serializable 
{
    public AbstractFDCSplQualificationAuditBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplQualificationAuditBillInfo(String pkField)
    {
        super(pkField);
        put("contractEntry", new com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryCollection());
        put("indexValue", new com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateCollection());
        put("auditResult", new com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection());
    }
    /**
     * Object:��Ӧ������'s ����ص�property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:��Ӧ������'s ������Աproperty 
     */
    public String getPerson()
    {
        return getString("person");
    }
    public void setPerson(String item)
    {
        setString("person", item);
    }
    /**
     * Object: ��Ӧ������ 's ������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection getAuditResult()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection)get("auditResult");
    }
    /**
     * Object: ��Ӧ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateCollection getIndexValue()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateCollection)get("indexValue");
    }
    /**
     * Object: ��Ӧ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo)get("evaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo item)
    {
        put("evaluationType", item);
    }
    /**
     * Object: ��Ӧ������ 's ��ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryCollection getContractEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryCollection)get("contractEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("36BC9435");
    }
}