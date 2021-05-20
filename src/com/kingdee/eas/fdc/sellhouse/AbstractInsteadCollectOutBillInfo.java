package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsteadCollectOutBillInfo extends com.kingdee.eas.fdc.propertymgmt.PPMProjectBillInfo implements Serializable 
{
    public AbstractInsteadCollectOutBillInfo()
    {
        this("id");
    }
    protected AbstractInsteadCollectOutBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection());
    }
    /**
     * Object: ���շ�ת�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection)get("entrys");
    }
    /**
     * Object:���շ�ת��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���շ�ת��'s ���δ������property 
     */
    public java.math.BigDecimal getMoneyInsteadCur()
    {
        return getBigDecimal("moneyInsteadCur");
    }
    public void setMoneyInsteadCur(java.math.BigDecimal item)
    {
        setBigDecimal("moneyInsteadCur", item);
    }
    /**
     * Object: ���շ�ת�� 's ������Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getManInsteadPay()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("manInsteadPay");
    }
    public void setManInsteadPay(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("manInsteadPay", item);
    }
    /**
     * Object: ���շ�ת�� 's �Ϲ�������Ӧ�շ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo getPurchaseElsePayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo)get("purchaseElsePayListEntry");
    }
    public void setPurchaseElsePayListEntry(com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo item)
    {
        put("purchaseElsePayListEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2A687D2");
    }
}