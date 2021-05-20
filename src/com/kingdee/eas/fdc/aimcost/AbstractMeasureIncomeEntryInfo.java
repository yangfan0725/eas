package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureIncomeEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMeasureIncomeEntryInfo()
    {
        this("id");
    }
    protected AbstractMeasureIncomeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������¼ 's ���������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.IncomeAccountInfo getIncomeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.IncomeAccountInfo)get("incomeAccount");
    }
    public void setIncomeAccount(com.kingdee.eas.fdc.basedata.IncomeAccountInfo item)
    {
        put("incomeAccount", item);
    }
    /**
     * Object:��������¼'s ��Ŀ����property 
     */
    public String getEntryName()
    {
        return getString("entryName");
    }
    public void setEntryName(String item)
    {
        setString("entryName", item);
    }
    /**
     * Object: ��������¼ 's ������Ʒ property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: ��������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��������¼'s Ԥ��ƽ���ۼ�property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:��������¼'s �������property 
     */
    public java.math.BigDecimal getSellArea()
    {
        return getBigDecimal("sellArea");
    }
    public void setSellArea(java.math.BigDecimal item)
    {
        setBigDecimal("sellArea", item);
    }
    /**
     * Object:��������¼'s Ԥ�������ܶ�property 
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
     * Object:��������¼'s �仯ԭ��property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7D8F5A2");
    }
}