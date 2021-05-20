package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierEvaluationContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierEvaluationContractEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierEvaluationContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ�������ͬ��¼'s ���޺�ͬproperty 
     */
    public boolean isIsHasContract()
    {
        return getBoolean("isHasContract");
    }
    public void setIsHasContract(boolean item)
    {
        setBoolean("isHasContract", item);
    }
    /**
     * Object:��Ӧ�������ͬ��¼'s ��ͬ����property 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    /**
     * Object: ��Ӧ�������ͬ��¼ 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: ��Ӧ�������ͬ��¼ 's ��Ӧ������ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��Ӧ�������ͬ��¼'s ��ͬǩ�����property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A733E400");
    }
}