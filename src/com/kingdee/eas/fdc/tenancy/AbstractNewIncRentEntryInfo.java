package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewIncRentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewIncRentEntryInfo()
    {
        this("id");
    }
    protected AbstractNewIncRentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����������¼ 's �µ�����¼�ĺ�ͬ���ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�����������¼'s ��������property 
     */
    public java.util.Date getIncreaseDate()
    {
        return getDate("increaseDate");
    }
    public void setIncreaseDate(java.util.Date item)
    {
        setDate("increaseDate", item);
    }
    /**
     * Object:�����������¼'s ������ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum getIncreaseType()
    {
        return com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum.getEnum(getString("increaseType"));
    }
    public void setIncreaseType(com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum item)
    {
		if (item != null) {
        setString("increaseType", item.getValue());
		}
    }
    /**
     * Object:�����������¼'s ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum getIncreaseStyle()
    {
        return com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum.getEnum(getString("increaseStyle"));
    }
    public void setIncreaseStyle(com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum item)
    {
		if (item != null) {
        setString("increaseStyle", item.getValue());
		}
    }
    /**
     * Object:�����������¼'s ֵproperty 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("646A8F72");
    }
}