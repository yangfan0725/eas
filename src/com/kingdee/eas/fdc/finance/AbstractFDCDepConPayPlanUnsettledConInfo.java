package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanUnsettledConInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanUnsettledConInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanUnsettledConInfo(String pkField)
    {
        super(pkField);
        put("entrys1", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection());
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ����� property 
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
     * Object: ��ǩ����ͬ��¼ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
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
     * Object:��ǩ����ͬ��¼'s ����ͬ����property 
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
     * Object: ��ǩ����ͬ��¼ 's ֧�� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection getEntrys1()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection)get("entrys1");
    }
    /**
     * Object:��ǩ����ͬ��¼'s �Ƿ񱻴��property 
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
     * Object:��ǩ����ͬ��¼'s ��غ��Ƿ��޸Ĺ�property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("636FFEEA");
    }
}