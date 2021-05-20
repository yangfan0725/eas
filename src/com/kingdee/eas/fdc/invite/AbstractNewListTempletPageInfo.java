package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListTempletPageInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewListTempletPageInfo()
    {
        this("id");
    }
    protected AbstractNewListTempletPageInfo(String pkField)
    {
        super(pkField);
        put("templetColumns", new com.kingdee.eas.fdc.invite.NewListTempletColumnCollection());
        put("entrys", new com.kingdee.eas.fdc.invite.NewListTempletEntryCollection());
    }
    /**
     * Object: �嵥ģ��ҳǩ 's ҳǩ property 
     */
    public com.kingdee.eas.fdc.invite.PageHeadInfo getPageHead()
    {
        return (com.kingdee.eas.fdc.invite.PageHeadInfo)get("pageHead");
    }
    public void setPageHead(com.kingdee.eas.fdc.invite.PageHeadInfo item)
    {
        put("pageHead", item);
    }
    /**
     * Object: �嵥ģ��ҳǩ 's ��ͷ���� property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getHeadType()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("headType");
    }
    public void setHeadType(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("headType", item);
    }
    /**
     * Object: �嵥ģ��ҳǩ 's �� property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletColumnCollection getTempletColumns()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletColumnCollection)get("templetColumns");
    }
    /**
     * Object: �嵥ģ��ҳǩ 's ģ����Ŀ property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletEntryCollection)get("entrys");
    }
    /**
     * Object: �嵥ģ��ҳǩ 's �嵥ģ��ͷ property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletInfo getTempletHead()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletInfo)get("templetHead");
    }
    public void setTempletHead(com.kingdee.eas.fdc.invite.NewListTempletInfo item)
    {
        put("templetHead", item);
    }
    /**
     * Object:�嵥ģ��ҳǩ's ҳǩ������property 
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
     * Object:�嵥ģ��ҳǩ's ����property 
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
        return new BOSObjectType("492E9DDC");
    }
}