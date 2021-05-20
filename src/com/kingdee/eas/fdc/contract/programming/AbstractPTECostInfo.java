package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPTECostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPTECostInfo()
    {
        this("id");
    }
    protected AbstractPTECostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ����� 's ��Լ���ģ���¼ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ɱ����� 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:�ɱ�����'s ���������property 
     */
    public java.math.BigDecimal getAssignScale()
    {
        return getBigDecimal("assignScale");
    }
    public void setAssignScale(java.math.BigDecimal item)
    {
        setBigDecimal("assignScale", item);
    }
    /**
     * Object:�ɱ�����'s ����Լ����property 
     */
    public java.math.BigDecimal getContractScale()
    {
        return getBigDecimal("contractScale");
    }
    public void setContractScale(java.math.BigDecimal item)
    {
        setBigDecimal("contractScale", item);
    }
    /**
     * Object:�ɱ�����'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A1B5BF1E");
    }
}