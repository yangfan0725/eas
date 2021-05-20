package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHeadColumnInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractHeadColumnInfo()
    {
        this("id");
    }
    protected AbstractHeadColumnInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͷ's ������property 
     */
    public com.kingdee.eas.fdc.invite.ColumnTypeEnum getColumnType()
    {
        return com.kingdee.eas.fdc.invite.ColumnTypeEnum.getEnum(getString("columnType"));
    }
    public void setColumnType(com.kingdee.eas.fdc.invite.ColumnTypeEnum item)
    {
		if (item != null) {
        setString("columnType", item.getValue());
		}
    }
    /**
     * Object: ��ͷ 's ��ͷ���� property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getHeadType()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("headType");
    }
    public void setHeadType(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("headType", item);
    }
    /**
     * Object: ��ͷ 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.invite.HeadColumnInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.HeadColumnInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.HeadColumnInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��ͷ's ����property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:��ͷ's �Ƿ񱨼���property 
     */
    public boolean isIsQuoting()
    {
        return getBoolean("isQuoting");
    }
    public void setIsQuoting(boolean item)
    {
        setBoolean("isQuoting", item);
    }
    /**
     * Object:��ͷ's �Ƿ����¼����property 
     */
    public boolean isIsHasChild()
    {
        return getBoolean("isHasChild");
    }
    public void setIsHasChild(boolean item)
    {
        setBoolean("isHasChild", item);
    }
    /**
     * Object:��ͷ's �Ƿ�۸����property 
     */
    public boolean isIsRefPrice()
    {
        return getBoolean("isRefPrice");
    }
    public void setIsRefPrice(boolean item)
    {
        setBoolean("isRefPrice", item);
    }
    /**
     * Object:��ͷ's ����property 
     */
    public com.kingdee.eas.fdc.invite.DescriptionEnum getProperty()
    {
        return com.kingdee.eas.fdc.invite.DescriptionEnum.getEnum(getString("property"));
    }
    public void setProperty(com.kingdee.eas.fdc.invite.DescriptionEnum item)
    {
		if (item != null) {
        setString("property", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52E98B7A");
    }
}