package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingEntryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractNewListingEntryInfo()
    {
        this("id");
    }
    protected AbstractNewListingEntryInfo(String pkField)
    {
        super(pkField);
        put("values", new com.kingdee.eas.fdc.invite.NewListingValueCollection());
    }
    /**
     * Object: �嵥��¼ 's ҳǩ property 
     */
    public com.kingdee.eas.fdc.invite.NewListingPageInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.NewListingPageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.NewListingPageInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�嵥��¼'s ��Ŀ����property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:�嵥��¼'s ��Ŀ����property 
     */
    public String getItemNumber()
    {
        return getString("itemNumber");
    }
    public void setItemNumber(String item)
    {
        setString("itemNumber", item);
    }
    /**
     * Object:�嵥��¼'s ����property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: �嵥��¼ 's ��Ŀ property 
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
     * Object:�嵥��¼'s �Ƿ�ؼ���Ŀproperty 
     */
    public boolean isIsKey()
    {
        return getBoolean("isKey");
    }
    public void setIsKey(boolean item)
    {
        setBoolean("isKey", item);
    }
    /**
     * Object:�嵥��¼'s �Ƿ񵥼۹���property 
     */
    public boolean isIsCompose()
    {
        return getBoolean("isCompose");
    }
    public void setIsCompose(boolean item)
    {
        setBoolean("isCompose", item);
    }
    /**
     * Object: �嵥��¼ 's ֵ property 
     */
    public com.kingdee.eas.fdc.invite.NewListingValueCollection getValues()
    {
        return (com.kingdee.eas.fdc.invite.NewListingValueCollection)get("values");
    }
    /**
     * Object:�嵥��¼'s ��������Ŀ��property 
     */
    public boolean isIsCanZeroProAmount()
    {
        return getBoolean("isCanZeroProAmount");
    }
    public void setIsCanZeroProAmount(boolean item)
    {
        setBoolean("isCanZeroProAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D5E62E6A");
    }
}