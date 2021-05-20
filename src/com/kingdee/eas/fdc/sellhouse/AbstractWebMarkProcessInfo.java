package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebMarkProcessInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractWebMarkProcessInfo()
    {
        this("id");
    }
    protected AbstractWebMarkProcessInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.WebMarkFieldCollection());
    }
    /**
     * Object:��ǩ����'s nullproperty 
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
     * Object:��ǩ����'s ˳��property 
     */
    public short getSeq()
    {
        return getShort("seq");
    }
    public void setSeq(short item)
    {
        setShort("seq", item);
    }
    /**
     * Object:��ǩ����'s ��������property 
     */
    public String getProcessName()
    {
        return getProcessName((Locale)null);
    }
    public void setProcessName(String item)
    {
		setProcessName(item,(Locale)null);
    }
    public String getProcessName(Locale local)
    {
        return TypeConversionUtils.objToString(get("processName", local));
    }
    public void setProcessName(String item, Locale local)
    {
        put("processName", item, local);
    }
    /**
     * Object:��ǩ����'s URLproperty 
     */
    public String getUrl()
    {
        return getString("url");
    }
    public void setUrl(String item)
    {
        setString("url", item);
    }
    /**
     * Object: ��ǩ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ǩ���� 's �ֶη�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkFieldCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkFieldCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("762CEA89");
    }
}