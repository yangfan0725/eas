package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierReviewGatherSurveyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierReviewGatherSurveyEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierReviewGatherSurveyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ſ���¼'s ������property 
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
     * Object:����ſ���¼'s �������property 
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
     * Object: ����ſ���¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object:����ſ���¼'s ����property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10806321");
    }
}