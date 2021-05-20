package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPropertyDoSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPropertyDoSchemeInfo()
    {
        this("id");
    }
    protected AbstractPropertyDoSchemeInfo(String pkField)
    {
        super(pkField);
        put("EntryTwo", new com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection());
        put("Entry", new com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection());
    }
    /**
     * Object: ��Ȩ������ 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ȩ������ 's �������̰��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection)get("Entry");
    }
    /**
     * Object: ��Ȩ������ 's ��2������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection getEntryTwo()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection)get("EntryTwo");
    }
    /**
     * Object: ��Ȩ������ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C65D47E0");
    }
}