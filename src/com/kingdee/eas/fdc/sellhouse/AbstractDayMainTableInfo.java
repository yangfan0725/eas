package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayMainTableInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayMainTableInfo()
    {
        this("id");
    }
    protected AbstractDayMainTableInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½����� 's �����½� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo getSaleBalance()
    {
        return (com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo)get("saleBalance");
    }
    public void setSaleBalance(com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo item)
    {
        put("saleBalance", item);
    }
    /**
     * Object:�½�����'s ����property 
     */
    public int getDayNum()
    {
        return getInt("dayNum");
    }
    public void setDayNum(int item)
    {
        setInt("dayNum", item);
    }
    /**
     * Object: �½����� 's ��Ŀ property 
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
     * Object:�½�����'s Ԥ����ͬ�ܶ�property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    /**
     * Object:�½�����'s �Ϲ���ͬ�ܶ�property 
     */
    public java.math.BigDecimal getPurAmount()
    {
        return getBigDecimal("purAmount");
    }
    public void setPurAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purAmount", item);
    }
    /**
     * Object:�½�����'s ǩԼ��ͬ�ܶ�property 
     */
    public java.math.BigDecimal getSignAmount()
    {
        return getBigDecimal("signAmount");
    }
    public void setSignAmount(java.math.BigDecimal item)
    {
        setBigDecimal("signAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82E5D3B4");
    }
}