package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEnterpriseSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractEnterpriseSchemeInfo()
    {
        this("id");
    }
    protected AbstractEnterpriseSchemeInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.market.EnterpriseSchemeEntryCollection());
    }
    /**
     * Object: ��ʵʩ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.EnterpriseSchemeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.market.EnterpriseSchemeEntryCollection)get("entry");
    }
    /**
     * Object: ��ʵʩ 's Ӫ����Ŀ property 
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
     * Object: ��ʵʩ 's �󻮼ƻ� property 
     */
    public com.kingdee.eas.fdc.market.EnterprisePlanInfo getEnterprisePlan()
    {
        return (com.kingdee.eas.fdc.market.EnterprisePlanInfo)get("enterprisePlan");
    }
    public void setEnterprisePlan(com.kingdee.eas.fdc.market.EnterprisePlanInfo item)
    {
        put("enterprisePlan", item);
    }
    /**
     * Object:��ʵʩ's ������property 
     */
    public String getThemeName()
    {
        return getString("themeName");
    }
    public void setThemeName(String item)
    {
        setString("themeName", item);
    }
    /**
     * Object:��ʵʩ's ������property 
     */
    public String getThemeDescription()
    {
        return getString("themeDescription");
    }
    public void setThemeDescription(String item)
    {
        setString("themeDescription", item);
    }
    /**
     * Object:��ʵʩ's ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:��ʵʩ's ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:��ʵʩ's ʵ�ʽ��ϼ�property 
     */
    public java.math.BigDecimal getTotalFactAmount()
    {
        return getBigDecimal("totalFactAmount");
    }
    public void setTotalFactAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalFactAmount", item);
    }
    /**
     * Object:��ʵʩ's ��ͬ���ϼ�property 
     */
    public java.math.BigDecimal getTotalContractAmount()
    {
        return getBigDecimal("totalContractAmount");
    }
    public void setTotalContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalContractAmount", item);
    }
    /**
     * Object:��ʵʩ's ������ϼ�property 
     */
    public java.math.BigDecimal getTotalPayAmount()
    {
        return getBigDecimal("totalPayAmount");
    }
    public void setTotalPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalPayAmount", item);
    }
    /**
     * Object:��ʵʩ's ����״̬property 
     */
    public boolean isIsEnd()
    {
        return getBoolean("isEnd");
    }
    public void setIsEnd(boolean item)
    {
        setBoolean("isEnd", item);
    }
    /**
     * Object:��ʵʩ's Ч������property 
     */
    public String getResult()
    {
        return getString("result");
    }
    public void setResult(String item)
    {
        setString("result", item);
    }
    /**
     * Object:��ʵʩ's �ƻ����ϼ�property 
     */
    public java.math.BigDecimal getTotalPlanAmount()
    {
        return getBigDecimal("totalPlanAmount");
    }
    public void setTotalPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalPlanAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56ED2C37");
    }
}