package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestorHouseLinkmanEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractInvestorHouseLinkmanEntrysInfo()
    {
        this("id");
    }
    protected AbstractInvestorHouseLinkmanEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ͷ�ʷ�Դ��ϵ�˷�¼ 's Ͷ�ʷ�Դͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo item)
    {
        put("head", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s �Ƿ�ҵ��property 
     */
    public boolean isIsOwner()
    {
        return getBoolean("isOwner");
    }
    public void setIsOwner(boolean item)
    {
        setBoolean("isOwner", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s ��ϵ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.LinkmanTypeEnum getLinkmanType()
    {
        return com.kingdee.eas.fdc.sellhouse.LinkmanTypeEnum.getEnum(getString("linkmanType"));
    }
    public void setLinkmanType(com.kingdee.eas.fdc.sellhouse.LinkmanTypeEnum item)
    {
		if (item != null) {
        setString("linkmanType", item.getValue());
		}
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s �Ա�property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s �绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s ֤������property 
     */
    public String getPaperName()
    {
        return getString("paperName");
    }
    public void setPaperName(String item)
    {
        setString("paperName", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s ֤������property 
     */
    public String getPaperNumber()
    {
        return getString("paperNumber");
    }
    public void setPaperNumber(String item)
    {
        setString("paperNumber", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ��ϵ�˷�¼'s ��������property 
     */
    public String getPostalcode()
    {
        return getString("postalcode");
    }
    public void setPostalcode(String item)
    {
        setString("postalcode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("72085814");
    }
}