package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillReceiveRateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractBillReceiveRateEntryInfo()
    {
        this("id");
    }
    protected AbstractContractBillReceiveRateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同签订明细 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReceiveInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReceiveInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBillReceiveInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合同签订明细's 合同明细property 
     */
    public String getDetail()
    {
        return getString("detail");
    }
    public void setDetail(String item)
    {
        setString("detail", item);
    }
    /**
     * Object:合同签订明细's 含税金额property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:合同签订明细's 税率property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:合同签订明细's 不含税金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:合同签订明细's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 合同签订明细 's 费用类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F77EC1E3");
    }
}