package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStoreItemInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractStoreItemInfo()
    {
        this("id");
    }
    protected AbstractStoreItemInfo(String pkField)
    {
        super(pkField);
        put("options", new com.kingdee.eas.fdc.market.StoreOptionCollection());
    }
    /**
     * Object:�����Ŀ����'s �������property 
     */
    public String getTopic()
    {
        return getString("topic");
    }
    public void setTopic(String item)
    {
        setString("topic", item);
    }
    /**
     * Object:�����Ŀ����'s �������property 
     */
    public java.math.BigDecimal getItemNumber()
    {
        return getBigDecimal("itemNumber");
    }
    public void setItemNumber(java.math.BigDecimal item)
    {
        setBigDecimal("itemNumber", item);
    }
    /**
     * Object: �����Ŀ���� 's ��ĿID property 
     */
    public com.kingdee.eas.fdc.market.StoreSubjectInfo getSubjectId()
    {
        return (com.kingdee.eas.fdc.market.StoreSubjectInfo)get("subjectId");
    }
    public void setSubjectId(com.kingdee.eas.fdc.market.StoreSubjectInfo item)
    {
        put("subjectId", item);
    }
    /**
     * Object: �����Ŀ���� 's ѡ�� property 
     */
    public com.kingdee.eas.fdc.market.StoreOptionCollection getOptions()
    {
        return (com.kingdee.eas.fdc.market.StoreOptionCollection)get("options");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("571A24E3");
    }
}