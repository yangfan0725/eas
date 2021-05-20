package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunctionSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSHEFunctionSetInfo()
    {
        this("id");
    }
    protected AbstractSHEFunctionSetInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryCollection());
        put("projectSet", new com.kingdee.eas.fdc.sellhouse.SHEProjectSetCollection());
        put("chooseRoomSet", new com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetCollection());
    }
    /**
     * Object:��¥��������'s ����ֵproperty 
     */
    public byte[] getValues()
    {
        return (byte[])get("values");
    }
    public void setValues(byte[] item)
    {
        put("values", item);
    }
    /**
     * Object: ��¥�������� 's ����Ŀ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEProjectSetCollection getProjectSet()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEProjectSetCollection)get("projectSet");
    }
    /**
     * Object: ��¥�������� 's ѡ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetCollection getChooseRoomSet()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetCollection)get("chooseRoomSet");
    }
    /**
     * Object: ��¥�������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DF288E35");
    }
}