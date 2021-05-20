package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSelectQuestionItemInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractSelectQuestionItemInfo()
    {
        this("id");
    }
    protected AbstractSelectQuestionItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ѡ�� 's null property 
     */
    public com.kingdee.eas.fdc.market.SelectQuestionInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.SelectQuestionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.SelectQuestionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ѡ��'s ѡ��������property 
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
     * Object:ѡ��'s �Ƿ����property 
     */
    public boolean isIsTitle()
    {
        return getBoolean("IsTitle");
    }
    public void setIsTitle(boolean item)
    {
        setBoolean("IsTitle", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("162BAAC6");
    }
}