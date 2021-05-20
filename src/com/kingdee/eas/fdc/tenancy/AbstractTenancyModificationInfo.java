package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyModificationInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyModificationInfo()
    {
        this("id");
    }
    protected AbstractTenancyModificationInfo(String pkField)
    {
        super(pkField);
        put("newIncreasedRents", new com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection());
        put("newAttachDealAmountEntry", new com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection());
        put("oldIncreasedRents", new com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection());
        put("newDealAmountEntry", new com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection());
        put("newRentFrees", new com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection());
        put("newPayList", new com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection());
        put("oldDealAmountEntry", new com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection());
        put("oldAttachDealAmountEntry", new com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection());
        put("oldRentFrees", new com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection());
    }
    /**
     * Object: ��ͬ��� 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object:��ͬ���'s ���ԭ��property 
     */
    public String getModificationReason()
    {
        return getString("modificationReason");
    }
    public void setModificationReason(String item)
    {
        setString("modificationReason", item);
    }
    /**
     * Object:��ͬ���'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:��ͬ���'s �½�������property 
     */
    public java.util.Date getNewEndDate()
    {
        return getDate("newEndDate");
    }
    public void setNewEndDate(java.util.Date item)
    {
        setDate("newEndDate", item);
    }
    /**
     * Object: ��ͬ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: ��ͬ��� 's ���������б��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection getNewIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection)get("newIncreasedRents");
    }
    /**
     * Object: ��ͬ��� 's ��ͬ������¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection getOldIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection)get("oldIncreasedRents");
    }
    /**
     * Object: ��ͬ��� 's �������¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection getNewRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection)get("newRentFrees");
    }
    /**
     * Object: ��ͬ��� 's ��ͬ�����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection getOldRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection)get("oldRentFrees");
    }
    /**
     * Object: ��ͬ��� 's ����ɽ��۸�����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection getNewDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection)get("newDealAmountEntry");
    }
    /**
     * Object: ��ͬ��� 's ��ͬ����ɽ��۸��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection getOldDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection)get("oldDealAmountEntry");
    }
    /**
     * Object: ��ͬ��� 's ������Դ�ɽ��۸�����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection getNewAttachDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection)get("newAttachDealAmountEntry");
    }
    /**
     * Object: ��ͬ��� 's ��ͬ������Դ�ɽ��۸�����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection getOldAttachDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection)get("oldAttachDealAmountEntry");
    }
    /**
     * Object: ��ͬ��� 's �¸���ƻ� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection getNewPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection)get("newPayList");
    }
    /**
     * Object:��ͬ���'s ���Ƿ����ɺ�ͬproperty 
     */
    public boolean isOldIsFreeContract()
    {
        return getBoolean("oldIsFreeContract");
    }
    public void setOldIsFreeContract(boolean item)
    {
        setBoolean("oldIsFreeContract", item);
    }
    /**
     * Object:��ͬ���'s �¾��Ƿ����ɺ�ͬproperty 
     */
    public boolean isNewIsFreeContract()
    {
        return getBoolean("newIsFreeContract");
    }
    public void setNewIsFreeContract(boolean item)
    {
        setBoolean("newIsFreeContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4E1D0473");
    }
}