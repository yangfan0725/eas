package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayAccumulateBldInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayAccumulateBldInfo()
    {
        this("id");
    }
    protected AbstractDayAccumulateBldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½�֮��¥���ۼ� 's �����½� property 
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
     * Object: �½�֮��¥���ۼ� 's ������� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object: �½�֮��¥���ۼ� 's §�� property 
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
     * Object:�½�֮��¥���ۼ�'s �ۼ�Ԥ����ͬ��property 
     */
    public int getPreCount()
    {
        return getInt("preCount");
    }
    public void setPreCount(int item)
    {
        setInt("preCount", item);
    }
    /**
     * Object:�½�֮��¥���ۼ�'s �ۼ�Ԥ����ͬ�ܼ�property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9136737");
    }
}