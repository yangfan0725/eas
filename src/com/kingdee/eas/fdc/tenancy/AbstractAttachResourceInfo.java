package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachResourceInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAttachResourceInfo()
    {
        this("id");
    }
    protected AbstractAttachResourceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Դ's ������Դ����(ö�ٲ�Ҫ��)property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum getAttachType()
    {
        return com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum.getEnum(getString("attachType"));
    }
    public void setAttachType(com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum item)
    {
		if (item != null) {
        setString("attachType", item.getValue());
		}
    }
    /**
     * Object:������Դ's ������Դ״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResourceEnum getAttachState()
    {
        return com.kingdee.eas.fdc.tenancy.AttachResourceEnum.getEnum(getString("attachState"));
    }
    public void setAttachState(com.kingdee.eas.fdc.tenancy.AttachResourceEnum item)
    {
		if (item != null) {
        setString("attachState", item.getValue());
		}
    }
    /**
     * Object: ������Դ 's ��Ŀ property 
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
     * Object: ������Դ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SubareaInfo getSubarea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SubareaInfo)get("subarea");
    }
    public void setSubarea(com.kingdee.eas.fdc.sellhouse.SubareaInfo item)
    {
        put("subarea", item);
    }
    /**
     * Object: ������Դ 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:������Դ's �������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rentType"));
    }
    public void setRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rentType", item.getValue());
		}
    }
    /**
     * Object:������Դ's ��׼���property 
     */
    public java.math.BigDecimal getStandardRent()
    {
        return getBigDecimal("standardRent");
    }
    public void setStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRent", item);
    }
    /**
     * Object: ������Դ 's ������Դ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResTypeInfo getAttachResType()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachResTypeInfo)get("attachResType");
    }
    public void setAttachResType(com.kingdee.eas.fdc.tenancy.AttachResTypeInfo item)
    {
        put("attachResType", item);
    }
    /**
     * Object:������Դ's ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C947175C");
    }
}