package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractContractPayPlanInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ����ƻ�'s ˳��property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: ��ͬ����ƻ� 's ����ƻ����� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractPayPlanTypeInfo getPayPlanType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractPayPlanTypeInfo)get("payPlanType");
    }
    public void setPayPlanType(com.kingdee.eas.fdc.basedata.ContractPayPlanTypeInfo item)
    {
        put("payPlanType", item);
    }
    /**
     * Object:��ͬ����ƻ�'s �ƻ���������property 
     */
    public java.util.Date getPayDate()
    {
        return getDate("payDate");
    }
    public void setPayDate(java.util.Date item)
    {
        setDate("payDate", item);
    }
    /**
     * Object:��ͬ����ƻ�'s ����ڵ�property 
     */
    public String getPayNode()
    {
        return getString("payNode");
    }
    public void setPayNode(String item)
    {
        setString("payNode", item);
    }
    /**
     * Object:��ͬ����ƻ�'s �������property 
     */
    public java.math.BigDecimal getPayProportion()
    {
        return getBigDecimal("payProportion");
    }
    public void setPayProportion(java.math.BigDecimal item)
    {
        setBigDecimal("payProportion", item);
    }
    /**
     * Object: ��ͬ����ƻ� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrecy()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currecy");
    }
    public void setCurrecy(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currecy", item);
    }
    /**
     * Object:��ͬ����ƻ�'s ����ԭ�ҽ��property 
     */
    public java.math.BigDecimal getPayOriAmount()
    {
        return getBigDecimal("payOriAmount");
    }
    public void setPayOriAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payOriAmount", item);
    }
    /**
     * Object:��ͬ����ƻ�'s ����property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:��ͬ����ƻ�'s ������property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:��ͬ����ƻ�'s �Ѹ�����property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object: ��ͬ����ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��ͬ����ƻ� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractId()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractId");
    }
    public void setContractId(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractId", item);
    }
    /**
     * Object:��ͬ����ƻ�'s ������Լ��֤��property 
     */
    public boolean isIsBailAmt()
    {
        return getBoolean("isBailAmt");
    }
    public void setIsBailAmt(boolean item)
    {
        setBoolean("isBailAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("350A8590");
    }
}