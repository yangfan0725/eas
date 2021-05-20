package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyReportInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPropertyReportInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyReportInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ݲ�Ȩ��Ϣ 's ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��Դ����property 
     */
    public String getRoomResName()
    {
        return getString("roomResName");
    }
    public void setRoomResName(String item)
    {
        setString("roomResName", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��Դ����property 
     */
    public String getRoomResProperty()
    {
        return getString("roomResProperty");
    }
    public void setRoomResProperty(String item)
    {
        setString("roomResProperty", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��ַproperty 
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
     * Object:���ݲ�Ȩ��Ϣ's ���property 
     */
    public String getArea()
    {
        return getString("area");
    }
    public void setArea(String item)
    {
        setString("area", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��Ȩ��property 
     */
    public String getPropertyOwner()
    {
        return getString("propertyOwner");
    }
    public void setPropertyOwner(String item)
    {
        setString("propertyOwner", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��Ȩ����property 
     */
    public String getProperty()
    {
        return getString("property");
    }
    public void setProperty(String item)
    {
        setString("property", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's �䶯ԭ��property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ��Ȩ���property 
     */
    public String getPropertyNumber()
    {
        return getString("propertyNumber");
    }
    public void setPropertyNumber(String item)
    {
        setString("propertyNumber", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ����ʱ��property 
     */
    public java.sql.Timestamp getCertifiedTime()
    {
        return getTimestamp("certifiedTime");
    }
    public void setCertifiedTime(java.sql.Timestamp item)
    {
        setTimestamp("certifiedTime", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's ���˵��property 
     */
    public String getInformationnote()
    {
        return getString("informationnote");
    }
    public void setInformationnote(String item)
    {
        setString("informationnote", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's �ֶ�1property 
     */
    public String getRemark1()
    {
        return getString("remark1");
    }
    public void setRemark1(String item)
    {
        setString("remark1", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's �ֶ�2property 
     */
    public String getRemark2()
    {
        return getString("remark2");
    }
    public void setRemark2(String item)
    {
        setString("remark2", item);
    }
    /**
     * Object:���ݲ�Ȩ��Ϣ's �ֶ�3property 
     */
    public String getRemark3()
    {
        return getString("remark3");
    }
    public void setRemark3(String item)
    {
        setString("remark3", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("80C3D68D");
    }
}