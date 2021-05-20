package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRoomPayListEntryInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractTenancyRoomPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyRoomPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���丶��ƻ���¼ 's ���޷��� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo getTenRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo)get("tenRoom");
    }
    public void setTenRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo item)
    {
        put("tenRoom", item);
    }
    /**
     * Object: ���丶��ƻ���¼ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:���丶��ƻ���¼'s �������property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:���丶��ƻ���¼'s ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:���丶��ƻ���¼'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:���丶��ƻ���¼'s ��������property 
     */
    public int getFreeDays()
    {
        return getInt("freeDays");
    }
    public void setFreeDays(int item)
    {
        setInt("freeDays", item);
    }
    /**
     * Object:���丶��ƻ���¼'s ������property 
     */
    public java.math.BigDecimal getRemissionAmount()
    {
        return getBigDecimal("remissionAmount");
    }
    public void setRemissionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remissionAmount", item);
    }
    /**
     * Object: ���丶��ƻ���¼ 's ���Ӻ�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenBill");
    }
    public void setTenBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenBill", item);
    }
    /**
     * Object:���丶��ƻ���¼'s ��עproperty 
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
     * Object:���丶��ƻ���¼'s �Ƿ�����property 
     */
    public com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum getCollectionFlag()
    {
        return com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum.getEnum(getString("collectionFlag"));
    }
    public void setCollectionFlag(com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum item)
    {
		if (item != null) {
        setString("collectionFlag", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("31D11A7E");
    }
}