package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAgioBillInfo()
    {
        this("id");
    }
    protected AbstractAgioBillInfo(String pkField)
    {
        super(pkField);
        put("roomEntry", new com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection());
    }
    /**
     * Object: �ۿ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�ۿ�'s ���㷽��property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum getCalType()
    {
        return com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum.getEnum(getString("calType"));
    }
    public void setCalType(com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum item)
    {
		if (item != null) {
        setString("calType", item.getValue());
		}
    }
    /**
     * Object:�ۿ�'s �ٷֱ�property 
     */
    public java.math.BigDecimal getPro()
    {
        return getBigDecimal("pro");
    }
    public void setPro(java.math.BigDecimal item)
    {
        setBigDecimal("pro", item);
    }
    /**
     * Object:�ۿ�'s ��Ч����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:�ۿ�'s ʧЧ����property 
     */
    public java.util.Date getCancelDate()
    {
        return getDate("cancelDate");
    }
    public void setCancelDate(java.util.Date item)
    {
        setDate("cancelDate", item);
    }
    /**
     * Object:�ۿ�'s �Ƿ������ۿ�property 
     */
    public boolean isIsEspecial()
    {
        return getBoolean("isEspecial");
    }
    public void setIsEspecial(boolean item)
    {
        setBoolean("isEspecial", item);
    }
    /**
     * Object: �ۿ� 's ָ�������б� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection getRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection)get("roomEntry");
    }
    /**
     * Object:�ۿ�'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �ۿ� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getEnabledMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("enabledMan");
    }
    public void setEnabledMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("enabledMan", item);
    }
    /**
     * Object:�ۿ�'s ��������property 
     */
    public java.util.Date getEnabledDate()
    {
        return getDate("enabledDate");
    }
    public void setEnabledDate(java.util.Date item)
    {
        setDate("enabledDate", item);
    }
    /**
     * Object: �ۿ� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUnenableMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("unenableMan");
    }
    public void setUnenableMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("unenableMan", item);
    }
    /**
     * Object:�ۿ�'s ��������property 
     */
    public java.util.Date getUnenabledDate()
    {
        return getDate("unenabledDate");
    }
    public void setUnenabledDate(java.util.Date item)
    {
        setDate("unenabledDate", item);
    }
    /**
     * Object: �ۿ� 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object:�ۿ�'s ����ģʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioTypeEnum getAgioType()
    {
        return com.kingdee.eas.fdc.sellhouse.AgioTypeEnum.getEnum(getString("agioType"));
    }
    public void setAgioType(com.kingdee.eas.fdc.sellhouse.AgioTypeEnum item)
    {
		if (item != null) {
        setString("agioType", item.getValue());
		}
    }
    /**
     * Object:�ۿ�'s ��ʱǩԼ�ۿ�property 
     */
    public com.kingdee.eas.fdc.sellhouse.BooleanEnum getIsAS()
    {
        return com.kingdee.eas.fdc.sellhouse.BooleanEnum.getEnum(getString("isAS"));
    }
    public void setIsAS(com.kingdee.eas.fdc.sellhouse.BooleanEnum item)
    {
		if (item != null) {
        setString("isAS", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C93ED5CE");
    }
}