package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierAppraiseChooseEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSupplierAppraiseChooseEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierAppraiseChooseEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ģ��ѡ���¼ 's ����ģ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ģ��ѡ���¼ 's ѡ���� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.ChooseInfo getChoose()
    {
        return (com.kingdee.eas.fdc.invite.supplier.ChooseInfo)get("choose");
    }
    public void setChoose(com.kingdee.eas.fdc.invite.supplier.ChooseInfo item)
    {
        put("choose", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B51CA8C");
    }
}