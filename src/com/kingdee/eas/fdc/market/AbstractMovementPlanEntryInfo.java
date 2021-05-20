package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractMovementPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:��¼'s Ԥ�Ʒ���property 
     */
    public java.math.BigDecimal getPredictCost()
    {
        return getBigDecimal("predictCost");
    }
    public void setPredictCost(java.math.BigDecimal item)
    {
        setBigDecimal("predictCost", item);
    }
    /**
     * Object:��¼'s Ԥ������property 
     */
    public int getPredictCall()
    {
        return getInt("predictCall");
    }
    public void setPredictCall(int item)
    {
        setInt("predictCall", item);
    }
    /**
     * Object:��¼'s Ԥ������property 
     */
    public int getPredictVisit()
    {
        return getInt("predictVisit");
    }
    public void setPredictVisit(int item)
    {
        setInt("predictVisit", item);
    }
    /**
     * Object:��¼'s Ԥ�Ƴɽ�property 
     */
    public int getPredictBargain()
    {
        return getInt("predictBargain");
    }
    public void setPredictBargain(int item)
    {
        setInt("predictBargain", item);
    }
    /**
     * Object:��¼'s ���⼰����property 
     */
    public String getSubject()
    {
        return getString("subject");
    }
    public void setSubject(String item)
    {
        setString("subject", item);
    }
    /**
     * Object:��¼'s ��עproperty 
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
     * Object:��¼'s �ۿ�property 
     */
    public String getDiscount()
    {
        return getString("discount");
    }
    public void setDiscount(String item)
    {
        setString("discount", item);
    }
    /**
     * Object:��¼'s ��ϸʱ��property 
     */
    public String getActiveTime()
    {
        return getString("activeTime");
    }
    public void setActiveTime(String item)
    {
        setString("activeTime", item);
    }
    /**
     * Object: ��¼ 's ý�� property 
     */
    public com.kingdee.eas.fdc.market.MediaInfo getMedia()
    {
        return (com.kingdee.eas.fdc.market.MediaInfo)get("media");
    }
    public void setMedia(com.kingdee.eas.fdc.market.MediaInfo item)
    {
        put("media", item);
    }
    /**
     * Object:��¼'s ��ʼʱ��property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:��¼'s ����ʱ��property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object: ��¼ 's ����� property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMmType()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("mmType");
    }
    public void setMmType(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("mmType", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public String getMmParent()
    {
        return getString("mmParent");
    }
    public void setMmParent(String item)
    {
        setString("mmParent", item);
    }
    /**
     * Object:��¼'s ѡ��property 
     */
    public boolean isIsSelect()
    {
        return getBoolean("isSelect");
    }
    public void setIsSelect(boolean item)
    {
        setBoolean("isSelect", item);
    }
    /**
     * Object:��¼'s �idproperty 
     */
    public String getMovementId()
    {
        return getString("movementId");
    }
    public void setMovementId(String item)
    {
        setString("movementId", item);
    }
    /**
     * Object: ��¼ 's Ӫ����Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56F46929");
    }
}