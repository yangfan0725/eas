package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTrackRecordInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTrackRecordInfo()
    {
        this("id");
    }
    protected AbstractTrackRecordInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ͻ�������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ͻ�������¼'s ��������property 
     */
    public java.util.Date getEventDate()
    {
        return getDate("eventDate");
    }
    public void setEventDate(java.util.Date item)
    {
        setDate("eventDate", item);
    }
    /**
     * Object:�ͻ�������¼'s ����˵��property 
     */
    public String getTrackDes()
    {
        return getString("trackDes");
    }
    public void setTrackDes(String item)
    {
        setString("trackDes", item);
    }
    /**
     * Object: �ͻ�������¼ 's �¼����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.EventTypeInfo getEventType()
    {
        return (com.kingdee.eas.fdc.sellhouse.EventTypeInfo)get("eventType");
    }
    public void setEventType(com.kingdee.eas.fdc.sellhouse.EventTypeInfo item)
    {
        put("eventType", item);
    }
    /**
     * Object: �ͻ�������¼ 's �̻� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: �ͻ�������¼ 's �Ӵ���ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo getReceptionType()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo)get("receptionType");
    }
    public void setReceptionType(com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo item)
    {
        put("receptionType", item);
    }
    /**
     * Object:�ͻ�������¼'s �����ɹ�property 
     */
    public com.kingdee.eas.fdc.sellhouse.TrackRecordEnum getTrackResult()
    {
        return com.kingdee.eas.fdc.sellhouse.TrackRecordEnum.getEnum(getString("trackResult"));
    }
    public void setTrackResult(com.kingdee.eas.fdc.sellhouse.TrackRecordEnum item)
    {
		if (item != null) {
        setString("trackResult", item.getValue());
		}
    }
    /**
     * Object:�ͻ�������¼'s ��������property 
     */
    public String getRelationContract()
    {
        return getString("relationContract");
    }
    public void setRelationContract(String item)
    {
        setString("relationContract", item);
    }
    /**
     * Object: �ͻ�������¼ 's ������Ŀ property 
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
     * Object: �ͻ�������¼ 's Ӫ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSaleMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("saleMan");
    }
    public void setSaleMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("saleMan", item);
    }
    /**
     * Object:�ͻ�������¼'s ����ϵͳproperty 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getSysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("sysType"));
    }
    public void setSysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("sysType", item.getValue());
		}
    }
    /**
     * Object:�ͻ�������¼'s �������property 
     */
    public com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum getTrackType()
    {
        return com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum.getEnum(getString("trackType"));
    }
    public void setTrackType(com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum item)
    {
		if (item != null) {
        setString("trackType", item.getValue());
		}
    }
    /**
     * Object: �ͻ�������¼ 's Ӫ��� property 
     */
    public com.kingdee.eas.fdc.market.MarketManageInfo getMarketManage()
    {
        return (com.kingdee.eas.fdc.market.MarketManageInfo)get("marketManage");
    }
    public void setMarketManage(com.kingdee.eas.fdc.market.MarketManageInfo item)
    {
        put("marketManage", item);
    }
    /**
     * Object:�ͻ�������¼'s ��ϵ�绰property 
     */
    public String getPhoneNumber()
    {
        return getString("phoneNumber");
    }
    public void setPhoneNumber(String item)
    {
        setString("phoneNumber", item);
    }
    /**
     * Object: �ͻ�������¼ 's ý������ property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("classify", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83DD37C1");
    }
}