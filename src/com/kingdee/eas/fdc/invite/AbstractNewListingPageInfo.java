package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingPageInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewListingPageInfo()
    {
        this("id");
    }
    protected AbstractNewListingPageInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.NewListingEntryCollection());
        put("columns", new com.kingdee.eas.fdc.invite.NewListingColumnCollection());
        put("sumEntry", new com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection());
    }
    /**
     * Object: �嵥ҳǩ 's ҳǩ property 
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
     * Object: �嵥ҳǩ 's ��ͷ���� property 
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
     * Object: �嵥ҳǩ 's �� property 
     */
    public com.kingdee.eas.fdc.invite.NewListingColumnCollection getColumns()
    {
        return (com.kingdee.eas.fdc.invite.NewListingColumnCollection)get("columns");
    }
    /**
     * Object: �嵥ҳǩ 's ģ����Ŀ property 
     */
    public com.kingdee.eas.fdc.invite.NewListingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.NewListingEntryCollection)get("entrys");
    }
    /**
     * Object: �嵥ҳǩ 's �嵥ģ��ͷ property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�嵥ҳǩ's ������������property 
     */
    public byte[] getTableData()
    {
        return (byte[])get("tableData");
    }
    public void setTableData(byte[] item)
    {
        put("tableData", item);
    }
    /**
     * Object:�嵥ҳǩ's ҳǩ������property 
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
     * Object: �嵥ҳǩ 's ���ۻ����� property 
     */
    public com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection getSumEntry()
    {
        return (com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection)get("sumEntry");
    }
    /**
     * Object:�嵥ҳǩ's ����property 
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
        return new BOSObjectType("BC989D97");
    }
}