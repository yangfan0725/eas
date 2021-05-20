package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIntentionRoomsEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractIntentionRoomsEntryInfo()
    {
        this("id");
    }
    protected AbstractIntentionRoomsEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ⷿ���¼ 's �����Ϲ���ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���ⷿ���¼ 's ���򷿼� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getIntentionRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("intentionRoom");
    }
    public void setIntentionRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("intentionRoom", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("669F46AB");
    }
}