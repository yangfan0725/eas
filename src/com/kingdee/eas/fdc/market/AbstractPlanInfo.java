package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPlanInfo()
    {
        this("id");
    }
    protected AbstractPlanInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.PlanEntryCollection());
    }
    /**
     * Object: Ӫ���ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.PlanEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.PlanEntryCollection)get("entrys");
    }
    /**
     * Object:Ӫ���ƻ�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:Ӫ���ƻ�'s ��֯����property 
     */
    public String getOrgName()
    {
        return getString("orgName");
    }
    public void setOrgName(String item)
    {
        setString("orgName", item);
    }
    /**
     * Object: Ӫ���ƻ� 's Ӫ����Ŀ property 
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
     * Object:Ӫ���ƻ�'s ����ϵͳproperty 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getBelongSystem()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("belongSystem"));
    }
    public void setBelongSystem(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("belongSystem", item.getValue());
		}
    }
    /**
     * Object:Ӫ���ƻ�'s ��������property 
     */
    public com.kingdee.eas.fdc.market.CycleEnum getCycleType()
    {
        return com.kingdee.eas.fdc.market.CycleEnum.getEnum(getString("cycleType"));
    }
    public void setCycleType(com.kingdee.eas.fdc.market.CycleEnum item)
    {
		if (item != null) {
        setString("cycleType", item.getValue());
		}
    }
    /**
     * Object:Ӫ���ƻ�'s �ƻ����property 
     */
    public String getPlanYear()
    {
        return getString("planYear");
    }
    public void setPlanYear(String item)
    {
        setString("planYear", item);
    }
    /**
     * Object:Ӫ���ƻ�'s �ƻ��·�property 
     */
    public com.kingdee.eas.hr.train.MonthEnum getPlanMonth()
    {
        return com.kingdee.eas.hr.train.MonthEnum.getEnum(getString("planMonth"));
    }
    public void setPlanMonth(com.kingdee.eas.hr.train.MonthEnum item)
    {
		if (item != null) {
        setString("planMonth", item.getValue());
		}
    }
    /**
     * Object:Ӫ���ƻ�'s �ܼƻ�����property 
     */
    public float getTotalCharge()
    {
        return getFloat("totalCharge");
    }
    public void setTotalCharge(float item)
    {
        setFloat("totalCharge", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("20ECA07A");
    }
}