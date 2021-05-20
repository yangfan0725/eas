package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepConPayPlanSplitBillInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractDepConPayPlanSplitBillInfo()
    {
        this("id");
    }
    protected AbstractDepConPayPlanSplitBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryCollection());
    }
    /**
     * Object:���ź�ͬ����ƻ����'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:���ź�ͬ����ƻ����'s �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: ���ź�ͬ����ƻ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryCollection)get("entrys");
    }
    /**
     * Object: ���ź�ͬ����ƻ���� 's ���ź�ͬ����ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getSplitPayPlanBill()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("splitPayPlanBill");
    }
    public void setSplitPayPlanBill(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("splitPayPlanBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF140E32");
    }
}