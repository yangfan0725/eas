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
     * Object:Ӫ������'s ǩԼ��λ��ȷproperty 
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
     * Object: Ӫ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectEntryCollection)get("entry");
    }
    /**
     * Object: Ӫ������ 's �������� property 
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
     * Object:Ӫ������'s �Ƿ�������property 
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
     * Object: Ӫ������ 's ���ù��� property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection)get("costEntry");
    }
    /**
     * Object: Ӫ������ 's �ȼ۵�λ property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection getUnitEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectUnitEntryCollection)get("unitEntry");
    }
    /**
     * Object:Ӫ������'s ��Ŀ����property 
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
     * Object:Ӫ������'s ���ⲿ��ͬproperty 
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
     * Object:Ӫ������'s ִ������Ƿ���Ҫ���ź�����property 
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
     * Object:Ӫ������'s ������Դproperty 
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
     * Object:Ӫ������'s oaְλproperty 
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
     * Object:Ӫ������'s �������property 
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