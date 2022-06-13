package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentRemissionInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractRentRemissionInfo()
    {
        this("id");
    }
    protected AbstractRentRemissionInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection());
    }
    /**
     * Object: K�����ⵥ����ͬǩ���� 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object:K�����ⵥ����ͬǩ����'s ����property 
     */
    public String getRoomName()
    {
        return getString("roomName");
    }
    public void setRoomName(String item)
    {
        setString("roomName", item);
    }
    /**
     * Object:K�����ⵥ����ͬǩ����'s ������Դproperty 
     */
    public String getAttachResource()
    {
        return getString("attachResource");
    }
    public void setAttachResource(String item)
    {
        setString("attachResource", item);
    }
    /**
     * Object:K�����ⵥ����ͬǩ����'s �ͻ�����property 
     */
    public String getCustomerName()
    {
        return getString("customerName");
    }
    public void setCustomerName(String item)
    {
        setString("customerName", item);
    }
    /**
     * Object:K�����ⵥ����ͬǩ����'s ��ͬ����property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object: K�����ⵥ����ͬǩ���� 's �������¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection)get("entry");
    }
    /**
     * Object: K�����ⵥ����ͬǩ���� 's ������Ŀ property 
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
     * Object:K�����ⵥ����ͬǩ����'s ��עproperty 
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
     * Object: K�����ⵥ����ͬǩ���� 's ������ID property 
     */
    public com.kingdee.eas.fdc.tenancy.BatchRentRemissionInfo getBatchBill()
    {
        return (com.kingdee.eas.fdc.tenancy.BatchRentRemissionInfo)get("batchBill");
    }
    public void setBatchBill(com.kingdee.eas.fdc.tenancy.BatchRentRemissionInfo item)
    {
        put("batchBill", item);
    }
    /**
     * Object:K�����ⵥ����ͬǩ����'s ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.RemissionTypeEnum getType()
    {
        return com.kingdee.eas.fdc.tenancy.RemissionTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.tenancy.RemissionTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DADF01B7");
    }
}