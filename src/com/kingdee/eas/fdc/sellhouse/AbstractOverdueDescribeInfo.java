package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOverdueDescribeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractOverdueDescribeInfo()
    {
        this("id");
    }
    protected AbstractOverdueDescribeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s ԭ������property 
     */
    public String getCauseDescribe()
    {
        return getString("causeDescribe");
    }
    public void setCauseDescribe(String item)
    {
        setString("causeDescribe", item);
    }
    /**
     * Object: �������� 's ����ԭ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo getOverdueSort()
    {
        return (com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo)get("overdueSort");
    }
    public void setOverdueSort(com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo item)
    {
        put("overdueSort", item);
    }
    /**
     * Object: �������� 's �������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getLoanBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("loanBank");
    }
    public void setLoanBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("loanBank", item);
    }
    /**
     * Object: �������� 's ����ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo getRoomHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo)get("roomHead");
    }
    public void setRoomHead(com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo item)
    {
        put("roomHead", item);
    }
    /**
     * Object:��������'s ����ؿ�����property 
     */
    public java.util.Date getLatestDate()
    {
        return getDate("latestDate");
    }
    public void setLatestDate(java.util.Date item)
    {
        setDate("latestDate", item);
    }
    /**
     * Object:��������'s Ԥ�ƻؿ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.PeriodEnum getReturnPeriod()
    {
        return com.kingdee.eas.fdc.sellhouse.PeriodEnum.getEnum(getString("returnPeriod"));
    }
    public void setReturnPeriod(com.kingdee.eas.fdc.sellhouse.PeriodEnum item)
    {
		if (item != null) {
        setString("returnPeriod", item.getValue());
		}
    }
    /**
     * Object:��������'s ���ڽ����ʩproperty 
     */
    public String getResolve()
    {
        return getString("resolve");
    }
    public void setResolve(String item)
    {
        setString("resolve", item);
    }
    /**
     * Object:��������'s ��������IDproperty 
     */
    public String getTransOviewId()
    {
        return getString("transOviewId");
    }
    public void setTransOviewId(String item)
    {
        setString("transOviewId", item);
    }
    /**
     * Object:��������'s ��ҵ���������׶�property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommercialStageEnum getCommercialStage()
    {
        return com.kingdee.eas.fdc.sellhouse.CommercialStageEnum.getEnum(getString("commercialStage"));
    }
    public void setCommercialStage(com.kingdee.eas.fdc.sellhouse.CommercialStageEnum item)
    {
		if (item != null) {
        setString("commercialStage", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("030F4B50");
    }
}