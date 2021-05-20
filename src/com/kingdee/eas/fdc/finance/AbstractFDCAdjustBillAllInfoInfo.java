package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCAdjustBillAllInfoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCAdjustBillAllInfoInfo()
    {
        this("id");
    }
    protected AbstractFDCAdjustBillAllInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������Ӧ��ͬȫ��Ϣ's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: ��������Ӧ��ͬȫ��Ϣ 's ����� property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCAdjustBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��������Ӧ��ͬȫ��Ϣ's ��������IDproperty 
     */
    public String getObjectId()
    {
        return getString("objectId");
    }
    public void setObjectId(String item)
    {
        setString("objectId", item);
    }
    /**
     * Object:��������Ӧ��ͬȫ��Ϣ's ��������property 
     */
    public com.kingdee.eas.fdc.finance.FDCBillTypeEnum getType()
    {
        return com.kingdee.eas.fdc.finance.FDCBillTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.finance.FDCBillTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8C4D6F83");
    }
}