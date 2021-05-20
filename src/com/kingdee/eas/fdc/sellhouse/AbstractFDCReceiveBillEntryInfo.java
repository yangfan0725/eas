package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceiveBillEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFDCReceiveBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCReceiveBillEntryInfo(String pkField)
    {
        super(pkField);
        put("sources", new com.kingdee.eas.fdc.sellhouse.FdcBillSourcesCollection());
    }
    /**
     * Object:null's ���property 
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
     * Object:null's ժҪproperty 
     */
    public String getSummary()
    {
        return getString("summary");
    }
    public void setSummary(String item)
    {
        setString("summary", item);
    }
    /**
     * Object:null's �����property 
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
     * Object:null's ��עproperty 
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
     * Object: null 's ���㷽ʽ property 
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
     * Object: null 's �տ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object:null's ��������property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:null's ���������ʺ�property 
     */
    public String getBankNumber()
    {
        return getString("bankNumber");
    }
    public void setBankNumber(String item)
    {
        setString("bankNumber", item);
    }
    /**
     * Object: null 's �տ property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getReceivingBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("receivingBill");
    }
    public void setReceivingBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("receivingBill", item);
    }
    /**
     * Object: null 's �տ��ʻ� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getRevAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("revAccountBank");
    }
    public void setRevAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("revAccountBank", item);
    }
    /**
     * Object: null 's �տ����� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getRevBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("revBank");
    }
    public void setRevBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("revBank", item);
    }
    /**
     * Object: null 's �������� property 
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
     * Object:null's �����Ľ��property 
     */
    public java.math.BigDecimal getCounteractAmount()
    {
        return getBigDecimal("counteractAmount");
    }
    public void setCounteractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("counteractAmount", item);
    }
    /**
     * Object:null's ���IDproperty 
     */
    public String getFCounteractId()
    {
        return getString("FCounteractId");
    }
    public void setFCounteractId(String item)
    {
        setString("FCounteractId", item);
    }
    /**
     * Object:null's ������ϸIDproperty 
     */
    public String getPayListId()
    {
        return getString("payListId");
    }
    public void setPayListId(String item)
    {
        setString("payListId", item);
    }
    /**
     * Object: null 's �Է���Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppSubject()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppSubject");
    }
    public void setOppSubject(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppSubject", item);
    }
    /**
     * Object:null's �ɺ����property 
     */
    public java.math.BigDecimal getCanCounteractAmount()
    {
        return getBigDecimal("canCounteractAmount");
    }
    public void setCanCounteractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("canCounteractAmount", item);
    }
    /**
     * Object:null's ˳���property 
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
     * Object: null 's ��¼��Դ property 
     */
    public com.kingdee.eas.fdc.sellhouse.FdcBillSourcesCollection getSources()
    {
        return (com.kingdee.eas.fdc.sellhouse.FdcBillSourcesCollection)get("sources");
    }
    /**
     * Object:null's �Ƿ�����property 
     */
    public boolean isIsPartial()
    {
        return getBoolean("isPartial");
    }
    public void setIsPartial(boolean item)
    {
        setBoolean("isPartial", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("87253BD2");
    }
}