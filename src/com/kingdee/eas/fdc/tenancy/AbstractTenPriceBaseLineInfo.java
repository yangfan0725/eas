package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenPriceBaseLineInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTenPriceBaseLineInfo()
    {
        this("id");
    }
    protected AbstractTenPriceBaseLineInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.TenPriceEntryCollection());
    }
    /**
     * Object: ��𶨼ۻ�׼�� 's null property 
     */
    public com.kingdee.eas.fdc.tenancy.TenPriceEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenPriceEntryCollection)get("entry");
    }
    /**
     * Object:��𶨼ۻ�׼��'s ��ͬ����property 
     */
    public com.kingdee.eas.fdc.tenancy.ContractTypeEnum getContractType()
    {
        return com.kingdee.eas.fdc.tenancy.ContractTypeEnum.getEnum(getString("contractType"));
    }
    public void setContractType(com.kingdee.eas.fdc.tenancy.ContractTypeEnum item)
    {
		if (item != null) {
        setString("contractType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62429D5A");
    }
}