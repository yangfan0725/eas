package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanNewInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractContractPayPlanNewInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanNewInfo(String pkField)
    {
        super(pkField);
        put("PayByStage", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection());
        put("Plam", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection());
        put("PlanMingxi", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection());
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s �汾����property 
     */
    public String getCopyName()
    {
        return getString("CopyName");
    }
    public void setCopyName(String item)
    {
        setString("CopyName", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ��ͬǩԼ���property 
     */
    public java.math.BigDecimal getSignAmount()
    {
        return getBigDecimal("SignAmount");
    }
    public void setSignAmount(java.math.BigDecimal item)
    {
        setBigDecimal("SignAmount", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ��ͬ�������property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("ContractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("ContractPrice", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ������property 
     */
    public String getAuditPerson()
    {
        return getString("AuditPerson");
    }
    public void setAuditPerson(String item)
    {
        setString("AuditPerson", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ����ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("AuditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("AuditTime", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ���ޱ���property 
     */
    public java.math.BigDecimal getMadeRate()
    {
        return getBigDecimal("MadeRate");
    }
    public void setMadeRate(java.math.BigDecimal item)
    {
        setBigDecimal("MadeRate", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ���ȿ����property 
     */
    public java.math.BigDecimal getPayRate()
    {
        return getBigDecimal("PayRate");
    }
    public void setPayRate(java.math.BigDecimal item)
    {
        setBigDecimal("PayRate", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s �Ƿ�������ȸ���property 
     */
    public boolean isProgressPayout()
    {
        return getBoolean("ProgressPayout");
    }
    public void setProgressPayout(boolean item)
    {
        setBoolean("ProgressPayout", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ��ͬ���property 
     */
    public String getContractCd()
    {
        return getString("ContractCd");
    }
    public void setContractCd(String item)
    {
        setString("ContractCd", item);
    }
    /**
     * Object:��ͬ����ƻ�(�°�)'s ����״̬property 
     */
    public String getStatus()
    {
        return getString("Status");
    }
    public void setStatus(String item)
    {
        setString("Status", item);
    }
    /**
     * Object: ��ͬ����ƻ�(�°�) 's  property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection getPayByStage()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection)get("PayByStage");
    }
    /**
     * Object: ��ͬ����ƻ�(�°�) 's null property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection getPlam()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection)get("Plam");
    }
    /**
     * Object: ��ͬ����ƻ�(�°�) 's �ƻ���ϸ property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection getPlanMingxi()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection)get("PlanMingxi");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("736E01F0");
    }
}