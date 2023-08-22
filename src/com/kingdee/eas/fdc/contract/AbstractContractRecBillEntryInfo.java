package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractRecBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractRecBillEntryInfo()
    {
        this("id");
    }
    protected AbstractContractRecBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������ͬ�տ��ϸ 's ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractRecBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractRecBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractRecBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �������ͬ�տ��ϸ 's �տ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:�������ͬ�տ��ϸ's �տ���property 
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
     * Object: �������ͬ�տ��ϸ 's �Է���Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppAccount");
    }
    public void setOppAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppAccount", item);
    }
    /**
     * Object:�������ͬ�տ��ϸ's ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("66C6DAE8");
    }
}