package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanUnsettledInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanUnsettledInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanUnsettledInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryCollection());
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's �� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ǩ����ͬ������property 
     */
    public String getUnConNumber()
    {
        return getString("unConNumber");
    }
    public void setUnConNumber(String item)
    {
        setString("unConNumber", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ǩ����ͬ����property 
     */
    public String getUnConName()
    {
        return getString("unConName");
    }
    public void setUnConName(String item)
    {
        setString("unConName", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s Ԥ��ǩԼ���property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("planAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryCollection)get("entrys");
    }
    /**
     * Object:��ǩ����ͬ��¼'s �Ƿ���property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ���� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("department");
    }
    public void setDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("department", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ��ͬ�����ƻ���Ӧ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo getDepPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo)get("depPlan");
    }
    public void setDepPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo item)
    {
        put("depPlan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8BCE2F99");
    }
}