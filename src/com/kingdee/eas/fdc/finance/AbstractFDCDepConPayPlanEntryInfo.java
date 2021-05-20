package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection());
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s ��ͬIDproperty 
     */
    public String getContractBillId()
    {
        return getString("contractBillId");
    }
    public void setContractBillId(String item)
    {
        setString("contractBillId", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s ��ͬ����property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s ��ͬ����property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s ��ͬ�������property 
     */
    public java.math.BigDecimal getContractLastestPrice()
    {
        return getBigDecimal("contractLastestPrice");
    }
    public void setContractLastestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractLastestPrice", item);
    }
    /**
     * Object: ���ź�ͬ����ƻ���¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���ź�ͬ����ƻ���¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection)get("items");
    }
    /**
     * Object:���ź�ͬ����ƻ���¼'s �Ƿ��Ǵ�ǩ����ͬproperty 
     */
    public boolean isIsUnsettledCon()
    {
        return getBoolean("isUnsettledCon");
    }
    public void setIsUnsettledCon(boolean item)
    {
        setBoolean("isUnsettledCon", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EB6C02A");
    }
}