package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanEntrysSecInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPlanEntrysSecInfo()
    {
        this("id");
    }
    protected AbstractPlanEntrysSecInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �μ���Ա 's null property 
     */
    public com.kingdee.eas.fdc.market.PlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.PlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.PlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�μ���Ա's ��ַproperty 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:�μ���Ա's ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�μ���Ա's �����ʼ�property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:�μ���Ա's ��ϵ�绰property 
     */
    public String getTelphone()
    {
        return getString("telphone");
    }
    public void setTelphone(String item)
    {
        setString("telphone", item);
    }
    /**
     * Object:�μ���Ա's ���property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:�μ���Ա's �ͻ�idproperty 
     */
    public String getCustomId()
    {
        return getString("customId");
    }
    public void setCustomId(String item)
    {
        setString("customId", item);
    }
    /**
     * Object:�μ���Ա's ��Ա���property 
     */
    public String getMemberNumber()
    {
        return getString("memberNumber");
    }
    public void setMemberNumber(String item)
    {
        setString("memberNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D53472F6");
    }
}