package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomKeepCustomerEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractRoomKeepCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomKeepCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����Ԥ���ͻ���¼ 's �ͻ���Ϣ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: ����Ԥ���ͻ���¼ 's ����Ԥ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("615EA819");
    }
}