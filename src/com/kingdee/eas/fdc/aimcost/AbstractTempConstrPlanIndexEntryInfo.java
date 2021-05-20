package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTempConstrPlanIndexEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTempConstrPlanIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractTempConstrPlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ָ���¼ģ�� 's ָ��ģ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ����ָ���¼ģ�� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:����ָ���¼ģ��'s ָ��һ������property 
     */
    public String getIndexName1()
    {
        return getString("indexName1");
    }
    public void setIndexName1(String item)
    {
        setString("indexName1", item);
    }
    /**
     * Object:����ָ���¼ģ��'s ָ���������property 
     */
    public String getIndexName2()
    {
        return getString("indexName2");
    }
    public void setIndexName2(String item)
    {
        setString("indexName2", item);
    }
    /**
     * Object:����ָ���¼ģ��'s ָ����������property 
     */
    public String getIndexName3()
    {
        return getString("indexName3");
    }
    public void setIndexName3(String item)
    {
        setString("indexName3", item);
    }
    /**
     * Object:����ָ���¼ģ��'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("382A7863");
    }
}