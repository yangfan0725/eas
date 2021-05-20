package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettForPayVoucherEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractSettForPayVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractSettForPayVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ԥ�������¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.finance.SettForPayVoucherInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.SettForPayVoucherInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.SettForPayVoucherInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:Ԥ�������¼'s ��ַ�¼IDproperty 
     */
    public String getSplitEntryID()
    {
        return getString("splitEntryID");
    }
    public void setSplitEntryID(String item)
    {
        setString("splitEntryID", item);
    }
    /**
     * Object:Ԥ�������¼'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: Ԥ�������¼ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: Ԥ�������¼ 's ��Ʒ property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2F644AE4");
    }
}