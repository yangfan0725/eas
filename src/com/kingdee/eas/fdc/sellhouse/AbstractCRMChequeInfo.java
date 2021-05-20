package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCRMChequeInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractCRMChequeInfo()
    {
        this("id");
    }
    protected AbstractCRMChequeInfo(String pkField)
    {
        super(pkField);
        put("chequeDetailEntry", new com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection());
        put("sellProjectEntry", new com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection());
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ�����κ�property 
     */
    public String getChequeBathNumber()
    {
        return getString("chequeBathNumber");
    }
    public void setChequeBathNumber(String item)
    {
        setString("chequeBathNumber", item);
    }
    /**
     * Object: Ʊ�ݹ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection getSellProjectEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection)get("sellProjectEntry");
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getChequeType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("chequeType"));
    }
    public void setChequeType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("chequeType", item.getValue());
		}
    }
    /**
     * Object: Ʊ�ݹ��� 's �ұ� property 
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
     * Object: Ʊ�ݹ��� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getKeeper()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("keeper");
    }
    public void setKeeper(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("keeper", item);
    }
    /**
     * Object: Ʊ�ݹ��� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getKeepOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("keepOrgUnit");
    }
    public void setKeepOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("keepOrgUnit", item);
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ���޶�property 
     */
    public java.math.BigDecimal getLimitAmount()
    {
        return getBigDecimal("limitAmount");
    }
    public void setLimitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("limitAmount", item);
    }
    /**
     * Object:Ʊ�ݹ���'s ժҪproperty 
     */
    public String getResume()
    {
        return getString("resume");
    }
    public void setResume(String item)
    {
        setString("resume", item);
    }
    /**
     * Object:Ʊ�ݹ���'s ����property 
     */
    public int getNumberOfCheque()
    {
        return getInt("numberOfCheque");
    }
    public void setNumberOfCheque(int item)
    {
        setInt("numberOfCheque", item);
    }
    /**
     * Object:Ʊ�ݹ���'s ��ʼ��ˮ��property 
     */
    public int getSerialNumber()
    {
        return getInt("serialNumber");
    }
    public void setSerialNumber(int item)
    {
        setInt("serialNumber", item);
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ�ݱ������property 
     */
    public String getCodeRule()
    {
        return getString("codeRule");
    }
    public void setCodeRule(String item)
    {
        setString("codeRule", item);
    }
    /**
     * Object: Ʊ�ݹ��� 's Ʊ����ϸ  property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection getChequeDetailEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection)get("chequeDetailEntry");
    }
    /**
     * Object:Ʊ�ݹ���'s ��Ŀ����ֶ�property 
     */
    public String getSellProjectNumbers()
    {
        return getString("sellProjectNumbers");
    }
    public void setSellProjectNumbers(String item)
    {
        setString("sellProjectNumbers", item);
    }
    /**
     * Object:Ʊ�ݹ���'s ��Ŀ����property 
     */
    public String getSellProjectNames()
    {
        return getString("sellProjectNames");
    }
    public void setSellProjectNames(String item)
    {
        setString("sellProjectNames", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF7E1D24");
    }
}