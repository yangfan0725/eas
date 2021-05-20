package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEnterpriseSellPlanInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEnterpriseSellPlanInfo()
    {
        this("id");
    }
    protected AbstractEnterpriseSellPlanInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ���ƻ�_�󻮼ƻ� 's ������ property 
     */
    public com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo item)
    {
        put("head", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ����״̬property 
     */
    public com.kingdee.eas.fdc.market.ThemeEnum getState()
    {
        return com.kingdee.eas.fdc.market.ThemeEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.market.ThemeEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ����property 
     */
    public String getProceeding()
    {
        return getString("proceeding");
    }
    public void setProceeding(String item)
    {
        setString("proceeding", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ��ʼʱ��property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ����ʱ��property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s Ͷ����property 
     */
    public int getQuantity()
    {
        return getInt("quantity");
    }
    public void setQuantity(int item)
    {
        setInt("quantity", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s �ƻ�����property 
     */
    public java.math.BigDecimal getPlanCost()
    {
        return getBigDecimal("planCost");
    }
    public void setPlanCost(java.math.BigDecimal item)
    {
        setBigDecimal("planCost", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: Ӫ���ƻ�_�󻮼ƻ� 's ���ÿ�Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getSubject()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("subject");
    }
    public void setSubject(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("subject", item);
    }
    /**
     * Object: Ӫ���ƻ�_�󻮼ƻ� 's �������� property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeTreeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeTreeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeTreeInfo item)
    {
        put("classify", item);
    }
    /**
     * Object: Ӫ���ƻ�_�󻮼ƻ� 's ý������ property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getMediaName()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("mediaName");
    }
    public void setMediaName(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("mediaName", item);
    }
    /**
     * Object:Ӫ���ƻ�_�󻮼ƻ�'s ʵʩ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getEsEntryId()
    {
        return getBOSUuid("esEntryId");
    }
    public void setEsEntryId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("esEntryId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C1486FAD");
    }
}