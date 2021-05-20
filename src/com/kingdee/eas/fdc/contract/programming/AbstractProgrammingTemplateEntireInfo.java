package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateEntireInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateEntireInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateEntireInfo(String pkField)
    {
        super(pkField);
        put("pteCost", new com.kingdee.eas.fdc.contract.programming.PTECostCollection());
        put("pteEnonomy", new com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection());
    }
    /**
     * Object: ��Լ��� 's ��Լ���ģ�� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Լ���'s ������Χproperty 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:��Լ���'s �׹�����ָ����property 
     */
    public String getProblem()
    {
        return getString("problem");
    }
    public void setProblem(String item)
    {
        setString("problem", item);
    }
    /**
     * Object:��Լ���'s ����property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: ��Լ��� 's �ϼ���ܺ�Լ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��Լ��� 's �ɱ����� property 
     */
    public com.kingdee.eas.fdc.contract.programming.PTECostCollection getPteCost()
    {
        return (com.kingdee.eas.fdc.contract.programming.PTECostCollection)get("pteCost");
    }
    /**
     * Object: ��Լ��� 's �������� property 
     */
    public com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection getPteEnonomy()
    {
        return (com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection)get("pteEnonomy");
    }
    /**
     * Object:��Լ���'s ������property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object: ��Լ��� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B9EEC6B4");
    }
}