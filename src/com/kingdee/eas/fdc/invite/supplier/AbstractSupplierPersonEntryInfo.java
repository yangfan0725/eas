package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierPersonEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierPersonEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierPersonEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ְԱ���ɷ�¼'s ������property 
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
     * Object:ְԱ���ɷ�¼'s �������property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object:ְԱ���ɷ�¼'s ����property 
     */
    public int getAmount()
    {
        return getInt("amount");
    }
    public void setAmount(int item)
    {
        setInt("amount", item);
    }
    /**
     * Object: ְԱ���ɷ�¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9361AB79");
    }
}