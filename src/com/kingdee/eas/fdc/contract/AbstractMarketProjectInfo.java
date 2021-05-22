package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMarketProjectInfo()
    {
        this("id");
    }
    protected AbstractMarketProjectInfo(String pkField)
    {
        super(pkField);
        put("costEntry", new com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection());
        put("entry", new com.kingdee.eas.fdc.contract.MarketProjectEntryCollection());
        put("unitEntry", new com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection());
    }
    /**
     * Object:营销立项's 签约单位明确property 
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
     * Object: 营销立项 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectEntryCollection)get("entry");
    }
    /**
     * Object: 营销立项 's 负数立项 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getMp()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("mp");
    }
    public void setMp(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("mp", item);
    }
    /**
     * Object:营销立项's 是否负数立项property 
     */
    public boolean isIsSub()
    {
        return getBoolean("isSub");
    }
    public void setIsSub(boolean item)
    {
        setBoolean("isSub", item);
    }
    /**
     * Object: 营销立项 's 费用归属 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection)get("costEntry");
    }
    /**
     * Object: 营销立项 's 比价单位 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection getUnitEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection)get("unitEntry");
    }
    /**
     * Object:营销立项's 项目名称property 
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
     * Object:营销立项's 内外部合同property 
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
     * Object:营销立项's 执行完毕是否需要集团后评估property 
     */
    public boolean isIsJT()
    {
        return getBoolean("isJT");
    }
    public void setIsJT(boolean item)
    {
        setBoolean("isJT", item);
    }
    /**
     * Object:营销立项's 立项来源property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectSourceEnum getSource()
    {
        return com.kingdee.eas.fdc.contract.MarketProjectSourceEnum.getEnum(getString("source"));
    }
    public void setSource(com.kingdee.eas.fdc.contract.MarketProjectSourceEnum item)
    {
		if (item != null) {
        setString("source", item.getValue());
		}
    }
    /**
     * Object:营销立项's oa职位property 
     */
    public String getOaPosition()
    {
        return getString("oaPosition");
    }
    public void setOaPosition(String item)
    {
        setString("oaPosition", item);
    }
    /**
     * Object:营销立项's 审批意见property 
     */
    public String getOaOpinion()
    {
        return getString("oaOpinion");
    }
    public void setOaOpinion(String item)
    {
        setString("oaOpinion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82E888E2");
    }
}