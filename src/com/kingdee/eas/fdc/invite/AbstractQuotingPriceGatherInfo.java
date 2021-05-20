package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuotingPriceGatherInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractQuotingPriceGatherInfo()
    {
        this("id");
    }
    protected AbstractQuotingPriceGatherInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ñ�������ܱ�'s �ϱ����property 
     */
    public boolean isStatus()
    {
        return getBoolean("status");
    }
    public void setStatus(boolean item)
    {
        setBoolean("status", item);
    }
    /**
     * Object:���ñ�������ܱ�'s �ϱ����˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: ���ñ�������ܱ� 's �����б��嵥 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getInviteListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("inviteListing");
    }
    public void setInviteListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("inviteListing", item);
    }
    /**
     * Object:���ñ�������ܱ�'s �������property 
     */
    public float getBuildArea()
    {
        return getFloat("buildArea");
    }
    public void setBuildArea(float item)
    {
        setFloat("buildArea", item);
    }
    /**
     * Object:���ñ�������ܱ�'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:���ñ�������ܱ�'s �ϱ�������property 
     */
    public String getGatherName()
    {
        return getString("gatherName");
    }
    public void setGatherName(String item)
    {
        setString("gatherName", item);
    }
    /**
     * Object:���ñ�������ܱ�'s �������property 
     */
    public String getAreaType()
    {
        return getString("areaType");
    }
    public void setAreaType(String item)
    {
        setString("areaType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("68D5F0CF");
    }
}