package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAFMortgagedDataEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAFMortgagedDataEntryInfo()
    {
        this("id");
    }
    protected AbstractAFMortgagedDataEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���� 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:����'s ����property 
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
     * Object:����'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EDB8F0C0");
    }
}