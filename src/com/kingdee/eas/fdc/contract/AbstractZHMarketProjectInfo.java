package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractZHMarketProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractZHMarketProjectInfo()
    {
        this("id");
    }
    protected AbstractZHMarketProjectInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ZHMarketProjectEntryCollection());
    }
    /**
     * Object:综合营销立项's 签约单位明确property 
     */
    public boolean isIsSupplier()
    {
        return getBoolean("isSupplier");
    }
    public void setIsSupplier(boolean item)
    {
        setBoolean("isSupplier", item);
    }
    /**
     * Object: 综合营销立项 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ZHMarketProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ZHMarketProjectEntryCollection)get("entry");
    }
    /**
     * Object:综合营销立项's 项目名称property 
     */
    public String getSellProjecttxt()
    {
        return getString("sellProjecttxt");
    }
    public void setSellProjecttxt(String item)
    {
        setString("sellProjecttxt", item);
    }
    /**
     * Object:综合营销立项's 内外部合同property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getNw()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("nw"));
    }
    public void setNw(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("nw", item.getValue());
		}
    }
    /**
     * Object:综合营销立项's 执行完毕是否需要集团后评估property 
     */
    public boolean isIsJT()
    {
        return getBoolean("isJT");
    }
    public void setIsJT(boolean item)
    {
        setBoolean("isJT", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BF423B34");
    }
}