package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractYZEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractYZEntryInfo()
    {
        this("id");
    }
    protected AbstractContractYZEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:合同印章信息's 印章名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:合同印章信息's 印章类型property 
     */
    public String getType()
    {
        return getString("type");
    }
    public void setType(String item)
    {
        setString("type", item);
    }
    /**
     * Object:合同印章信息's 印章管理员名称property 
     */
    public String getAdmin()
    {
        return getString("admin");
    }
    public void setAdmin(String item)
    {
        setString("admin", item);
    }
    /**
     * Object:合同印章信息's 印章次数property 
     */
    public String getCount()
    {
        return getString("count");
    }
    public void setCount(String item)
    {
        setString("count", item);
    }
    /**
     * Object: 合同印章信息 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合同印章信息's 印章管理员idproperty 
     */
    public String getAdminID()
    {
        return getString("adminID");
    }
    public void setAdminID(String item)
    {
        setString("adminID", item);
    }
    /**
     * Object:合同印章信息's 印章idproperty 
     */
    public String getYzID()
    {
        return getString("yzID");
    }
    public void setYzID(String item)
    {
        setString("yzID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("63D730E4");
    }
}