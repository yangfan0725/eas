package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLandDeveloperInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractLandDeveloperInfo()
    {
        this("id");
    }
    protected AbstractLandDeveloperInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:甲方's 纳税人识别号property 
     */
    public String getTaxNo()
    {
        return getString("taxNo");
    }
    public void setTaxNo(String item)
    {
        setString("taxNo", item);
    }
    /**
     * Object:甲方's 地址电话property 
     */
    public String getAddressAndPhone()
    {
        return getString("addressAndPhone");
    }
    public void setAddressAndPhone(String item)
    {
        setString("addressAndPhone", item);
    }
    /**
     * Object:甲方's 开户行及账号property 
     */
    public String getBankNo()
    {
        return getString("bankNo");
    }
    public void setBankNo(String item)
    {
        setString("bankNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D0EB06D");
    }
}