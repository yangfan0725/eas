package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStoreSubjectInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractStoreSubjectInfo()
    {
        this("id");
    }
    protected AbstractStoreSubjectInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.market.StoreItemCollection());
    }
    /**
     * Object:������'s ����property 
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
     * Object: ������ 's �����Ŀ��� property 
     */
    public com.kingdee.eas.fdc.market.StoreSubjectClassInfo getStoreSubClass()
    {
        return (com.kingdee.eas.fdc.market.StoreSubjectClassInfo)get("storeSubClass");
    }
    public void setStoreSubClass(com.kingdee.eas.fdc.market.StoreSubjectClassInfo item)
    {
        put("storeSubClass", item);
    }
    /**
     * Object:������'s ��Ŀ����property 
     */
    public com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum getSubjectType()
    {
        return com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum.getEnum(getInt("subjectType"));
    }
    public void setSubjectType(com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum item)
    {
		if (item != null) {
        setInt("subjectType", item.getValue());
		}
    }
    /**
     * Object:������'s ����������property 
     */
    public String getXCellCreter()
    {
        return getString("xCellCreter");
    }
    public void setXCellCreter(String item)
    {
        setString("xCellCreter", item);
    }
    /**
     * Object: ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.market.StoreItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.market.StoreItemCollection)get("items");
    }
    /**
     * Object: ������ 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:������'s ���з�ʽproperty 
     */
    public com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum getAlignType()
    {
        return com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum.getEnum(getInt("alignType"));
    }
    public void setAlignType(com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum item)
    {
		if (item != null) {
        setInt("alignType", item.getValue());
		}
    }
    /**
     * Object:������'s ���property 
     */
    public int getSubjectNumber()
    {
        return getInt("subjectNumber");
    }
    public void setSubjectNumber(int item)
    {
        setInt("subjectNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3DF3995C");
    }
}