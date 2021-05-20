package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEnterprisePlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractEnterprisePlanInfo()
    {
        this("id");
    }
    protected AbstractEnterprisePlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection());
    }
    /**
     * Object: �󻮼ƻ� 's Ӫ����Ŀ property 
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
     * Object: �󻮼ƻ� 's �󻮷�¼ property 
     */
    public com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection)get("entry");
    }
    /**
     * Object:�󻮼ƻ�'s �ƻ����property 
     */
    public int getPlanYear()
    {
        return getInt("planYear");
    }
    public void setPlanYear(int item)
    {
        setInt("planYear", item);
    }
    /**
     * Object:�󻮼ƻ�'s �ƻ��·�property 
     */
    public int getPlanMonth()
    {
        return getInt("planMonth");
    }
    public void setPlanMonth(int item)
    {
        setInt("planMonth", item);
    }
    /**
     * Object:�󻮼ƻ�'s �汾��property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object:�󻮼ƻ�'s �ܼƻ�����property 
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
     * Object:�󻮼ƻ�'s �Ƿ�����property 
     */
    public boolean isUseing()
    {
        return getBoolean("useing");
    }
    public void setUseing(boolean item)
    {
        setBoolean("useing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B290FE3B");
    }
}