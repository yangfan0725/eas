package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBatchManageInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractBatchManageInfo()
    {
        this("id");
    }
    protected AbstractBatchManageInfo(String pkField)
    {
        super(pkField);
        put("batchRoomEntry", new com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection());
    }
    /**
     * Object: ���ι��� 's ������Ŀ property 
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
     * Object: ���ι��� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection getBatchRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection)get("batchRoomEntry");
    }
    /**
     * Object:���ι���'s ��Դproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum getSource()
    {
        return com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum.getEnum(getString("source"));
    }
    public void setSource(com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum item)
    {
		if (item != null) {
        setString("source", item.getValue());
		}
    }
    /**
     * Object: ���ι��� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C8DDF84");
    }
}