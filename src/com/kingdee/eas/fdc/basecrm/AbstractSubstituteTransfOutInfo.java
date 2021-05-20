package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteTransfOutInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSubstituteTransfOutInfo()
    {
        this("id");
    }
    protected AbstractSubstituteTransfOutInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection());
    }
    /**
     * Object: ���շ���ת�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection)get("entrys");
    }
    /**
     * Object: ���շ���ת�� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrgUnit");
    }
    public void setSaleOrgUnit(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrgUnit", item);
    }
    /**
     * Object: ���շ���ת�� 's ��Ŀ property 
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
     * Object: ���շ���ת�� 's �������� property 
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
     * Object:���շ���ת��'s ֧�����property 
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
     * Object:���շ���ת��'s �տ���property 
     */
    public String getRevUserName()
    {
        return getString("revUserName");
    }
    public void setRevUserName(String item)
    {
        setString("revUserName", item);
    }
    /**
     * Object: ���շ���ת�� 's �տ����� property 
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
     * Object: ���շ���ת�� 's �������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPayBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("payBank");
    }
    public void setPayBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("payBank", item);
    }
    /**
     * Object:���շ���ת��'s �տ��ʺ�property 
     */
    public String getRevAccountNumber()
    {
        return getString("revAccountNumber");
    }
    public void setRevAccountNumber(String item)
    {
        setString("revAccountNumber", item);
    }
    /**
     * Object: ���շ���ת�� 's �����ʺ� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayAccountNumber()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payAccountNumber");
    }
    public void setPayAccountNumber(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payAccountNumber", item);
    }
    /**
     * Object: ���շ���ת�� 's �տ��Ŀ property 
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
     * Object: ���շ���ת�� 's �Է���Ŀ property 
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
     * Object: ���շ���ת�� 's ���㷽ʽ property 
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
     * Object: ���շ���ת�� 's �ұ� property 
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
     * Object:���շ���ת��'s ����property 
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
     * Object:���շ���ת��'s ��������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:���շ���ת��'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: ���շ���ת�� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: ���շ���ת�� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPayUser()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("payUser");
    }
    public void setPayUser(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("payUser", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("57618B5A");
    }
}