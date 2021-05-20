package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteContentInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractInviteContentInfo()
    {
        this("id");
    }
    protected AbstractInviteContentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�б��ļ�����'s ����property 
     */
    public byte[] getContentFile()
    {
        return (byte[])get("contentFile");
    }
    public void setContentFile(byte[] item)
    {
        put("contentFile", item);
    }
    /**
     * Object:�б��ļ�����'s �ļ�����property 
     */
    public String getFileType()
    {
        return getString("fileType");
    }
    public void setFileType(String item)
    {
        setString("fileType", item);
    }
    /**
     * Object:�б��ļ�����'s �ļ�����property 
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
     * Object:�б��ļ�����'s ��������IDproperty 
     */
    public String getParent()
    {
        return getString("parent");
    }
    public void setParent(String item)
    {
        setString("parent", item);
    }
    /**
     * Object:�б��ļ�����'s �汾��property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9715088C");
    }
}