package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountCreatInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDefaultAmountCreatInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountCreatInfo(String pkField)
    {
        super(pkField);
        put("parent", new com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryCollection());
    }
    /**
     * Object: ����ΥԼ�� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ����ΥԼ�� 's ΥԼ����㹫ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo getDefCalFormula()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo)get("defCalFormula");
    }
    public void setDefCalFormula(com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo item)
    {
        put("defCalFormula", item);
    }
    /**
     * Object: ����ΥԼ�� 's ����ͷ-��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryCollection getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryCollection)get("parent");
    }
    /**
     * Object:����ΥԼ��'s ΥԼ���������property 
     */
    public java.util.Date getDefCalDate()
    {
        return getDate("defCalDate");
    }
    public void setDefCalDate(java.util.Date item)
    {
        setDate("defCalDate", item);
    }
    /**
     * Object:����ΥԼ��'s ˵��property 
     */
    public String getExplain()
    {
        return getString("explain");
    }
    public void setExplain(String item)
    {
        setString("explain", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5BA83C2B");
    }
}