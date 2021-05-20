package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLoanDataInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractLoanDataInfo()
    {
        this("id");
    }
    protected AbstractLoanDataInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection());
    }
    /**
     * Object: �������� 's �������� property 
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
     * Object: �������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: �������� 's ���ұұ� property 
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
     * Object:��������'s ��������property 
     */
    public int getLoanFixedYear()
    {
        return getInt("loanFixedYear");
    }
    public void setLoanFixedYear(int item)
    {
        setInt("loanFixedYear", item);
    }
    /**
     * Object:��������'s ��֤��property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:��������'s ���Ҷ��property 
     */
    public java.math.BigDecimal getLoanLine()
    {
        return getBigDecimal("loanLine");
    }
    public void setLoanLine(java.math.BigDecimal item)
    {
        setBigDecimal("loanLine", item);
    }
    /**
     * Object:��������'s Ŀǰʹ��property 
     */
    public java.math.BigDecimal getUsedAmount()
    {
        return getBigDecimal("usedAmount");
    }
    public void setUsedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("usedAmount", item);
    }
    /**
     * Object:��������'s ������ϵ��property 
     */
    public String getContactPerson()
    {
        return getString("contactPerson");
    }
    public void setContactPerson(String item)
    {
        setString("contactPerson", item);
    }
    /**
     * Object:��������'s ��ϵ�绰property 
     */
    public String getContactPhone()
    {
        return getString("contactPhone");
    }
    public void setContactPhone(String item)
    {
        setString("contactPhone", item);
    }
    /**
     * Object: �������� 's ���������������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection)get("entrys");
    }
    /**
     * Object:��������'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��������'s �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9698C95");
    }
}