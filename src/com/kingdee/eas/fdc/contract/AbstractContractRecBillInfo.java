package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractRecBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractRecBillInfo()
    {
        this("id");
    }
    protected AbstractContractRecBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractRecBillEntryCollection());
    }
    /**
     * Object: �������ͬ�տ 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReceiveInfo getContractBillReceive()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReceiveInfo)get("contractBillReceive");
    }
    public void setContractBillReceive(com.kingdee.eas.fdc.contract.ContractBillReceiveInfo item)
    {
        put("contractBillReceive", item);
    }
    /**
     * Object: �������ͬ�տ 's ���λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: �������ͬ�տ 's �տ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getRevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("revAccount");
    }
    public void setRevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("revAccount", item);
    }
    /**
     * Object: �������ͬ�տ 's �տ��˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("accountBank");
    }
    public void setAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("accountBank", item);
    }
    /**
     * Object: �������ͬ�տ 's ���㷽ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:�������ͬ�տ's �����property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    /**
     * Object: �������ͬ�տ 's �տ����� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("bank", item);
    }
    /**
     * Object: �������ͬ�տ 's ��ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractRecBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractRecBillEntryCollection)get("entry");
    }
    /**
     * Object: �������ͬ�տ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:�������ͬ�տ's ����property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("05E377AA");
    }
}