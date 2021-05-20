package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHandleRoomEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractHandleRoomEntrysInfo()
    {
        this("id");
    }
    protected AbstractHandleRoomEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���佻�ӷ�¼'s ��������property 
     */
    public String getHandleType()
    {
        return getString("handleType");
    }
    public void setHandleType(String item)
    {
        setString("handleType", item);
    }
    /**
     * Object:���佻�ӷ�¼'s ԭ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getOldHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("oldHandleState"));
    }
    public void setOldHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("oldHandleState", item.getValue());
		}
    }
    /**
     * Object:���佻�ӷ�¼'s �ֽ���״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getNewHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("newHandleState"));
    }
    public void setNewHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("newHandleState", item.getValue());
		}
    }
    /**
     * Object: ���佻�ӷ�¼ 's ���޺�ͬ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo getTenancyRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo)get("tenancyRoom");
    }
    public void setTenancyRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo item)
    {
        put("tenancyRoom", item);
    }
    /**
     * Object: ���佻�ӷ�¼ 's ����������Դ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("attach");
    }
    public void setAttach(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("attach", item);
    }
    /**
     * Object:���佻�ӷ�¼'s �������property 
     */
    public String getTenancyRoomNumber()
    {
        return getString("tenancyRoomNumber");
    }
    public void setTenancyRoomNumber(String item)
    {
        setString("tenancyRoomNumber", item);
    }
    /**
     * Object:���佻�ӷ�¼'s ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TransactStateEnum getTransactState()
    {
        return com.kingdee.eas.fdc.tenancy.TransactStateEnum.getEnum(getString("transactState"));
    }
    public void setTransactState(com.kingdee.eas.fdc.tenancy.TransactStateEnum item)
    {
		if (item != null) {
        setString("transactState", item.getValue());
		}
    }
    /**
     * Object:���佻�ӷ�¼'s �������property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object: ���佻�ӷ�¼ 's ���佻�ӵ� property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleTenancyInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.HandleTenancyInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.HandleTenancyInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("89BC1AED");
    }
}