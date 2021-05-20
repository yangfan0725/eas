package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRefPriceInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRefPriceInfo()
    {
        this("id");
    }
    protected AbstractRefPriceInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.RefPriceEntryCollection());
    }
    /**
     * Object: �ο��� 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.invite.ListingItemInfo getItem()
    {
        return (com.kingdee.eas.fdc.invite.ListingItemInfo)get("item");
    }
    public void setItem(com.kingdee.eas.fdc.invite.ListingItemInfo item)
    {
        put("item", item);
    }
    /**
     * Object:�ο���'s ����property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object: �ο��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.RefPriceEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.RefPriceEntryCollection)get("entrys");
    }
    /**
     * Object: �ο��� 's ������Ϣ property 
     */
    public com.kingdee.eas.fdc.invite.NewQuotingPriceInfo getQuotingContent()
    {
        return (com.kingdee.eas.fdc.invite.NewQuotingPriceInfo)get("quotingContent");
    }
    public void setQuotingContent(com.kingdee.eas.fdc.invite.NewQuotingPriceInfo item)
    {
        put("quotingContent", item);
    }
    /**
     * Object: �ο��� 's �嵥 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("listing");
    }
    public void setListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("listing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("51008B9A");
    }
}