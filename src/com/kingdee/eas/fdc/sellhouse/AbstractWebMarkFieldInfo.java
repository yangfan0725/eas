package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebMarkFieldInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWebMarkFieldInfo()
    {
        this("id");
    }
    protected AbstractWebMarkFieldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ǩ�ֶ�'s ������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��ǩ�ֶ�'s ��ҳԪ�ر�ʾproperty 
     */
    public String getWebID()
    {
        return getString("webID");
    }
    public void setWebID(String item)
    {
        setString("webID", item);
    }
    /**
     * Object:��ǩ�ֶ�'s ��ҳԪ����property 
     */
    public String getWebName()
    {
        return getString("webName");
    }
    public void setWebName(String item)
    {
        setString("webName", item);
    }
    /**
     * Object:��ǩ�ֶ�'s ��ǩ����property 
     */
    public String getWebValue()
    {
        return getString("webValue");
    }
    public void setWebValue(String item)
    {
        setString("webValue", item);
    }
    /**
     * Object:��ǩ�ֶ�'s ��ҳԪ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum getWebMetaType()
    {
        return com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum.getEnum(getString("webMetaType"));
    }
    public void setWebMetaType(com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum item)
    {
		if (item != null) {
        setString("webMetaType", item.getValue());
		}
    }
    /**
     * Object:��ǩ�ֶ�'s ��ҳ�ֶζ�Ӧ��¥ϵͳ���ֶ���property 
     */
    public String getFileName()
    {
        return getString("fileName");
    }
    public void setFileName(String item)
    {
        setString("fileName", item);
    }
    /**
     * Object:��ǩ�ֶ�'s �ַ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.FiledEnum getFieldType()
    {
        return com.kingdee.eas.fdc.sellhouse.FiledEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.sellhouse.FiledEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    /**
     * Object:��ǩ�ֶ�'s Ĭ���ֶ�property 
     */
    public String getDefultValue()
    {
        return getString("defultValue");
    }
    public void setDefultValue(String item)
    {
        setString("defultValue", item);
    }
    /**
     * Object: ��ǩ�ֶ� 's ��ǩ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C46AB94");
    }
}