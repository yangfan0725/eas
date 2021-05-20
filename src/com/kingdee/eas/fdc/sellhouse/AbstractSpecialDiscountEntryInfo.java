package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialDiscountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSpecialDiscountEntryInfo()
    {
        this("id");
    }
    protected AbstractSpecialDiscountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����Ż��ۿ۵���¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����Ż��ۿ۵���¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �������property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �������property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ��������property 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ���ڵ���property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ��׼�ܼ�property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �Żݽ��property 
     */
    public java.math.BigDecimal getDiscountAmount()
    {
        return getBigDecimal("discountAmount");
    }
    public void setDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAmount", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �Żݺ󷿼��ܼ�property 
     */
    public java.math.BigDecimal getDiscountAfAmount()
    {
        return getBigDecimal("discountAfAmount");
    }
    public void setDiscountAfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfAmount", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ����Ż��ۿ۱���property 
     */
    public java.math.BigDecimal getDiscountPercent()
    {
        return getBigDecimal("discountPercent");
    }
    public void setDiscountPercent(java.math.BigDecimal item)
    {
        setBigDecimal("discountPercent", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �Żݺ�������property 
     */
    public java.math.BigDecimal getDiscountAfBPrice()
    {
        return getBigDecimal("discountAfBPrice");
    }
    public void setDiscountAfBPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfBPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �Żݺ����ڵ���property 
     */
    public java.math.BigDecimal getDiscountAfRPrice()
    {
        return getBigDecimal("discountAfRPrice");
    }
    public void setDiscountAfRPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfRPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �׼�property 
     */
    public java.math.BigDecimal getBaseStandardPrice()
    {
        return getBigDecimal("baseStandardPrice");
    }
    public void setBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseStandardPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �׼����Żݵ���property 
     */
    public java.math.BigDecimal getBasePercent()
    {
        return getBigDecimal("basePercent");
    }
    public void setBasePercent(java.math.BigDecimal item)
    {
        setBigDecimal("basePercent", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �׼����Żݽ��property 
     */
    public java.math.BigDecimal getBaseDiscountAmount()
    {
        return getBigDecimal("baseDiscountAmount");
    }
    public void setBaseDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("baseDiscountAmount", item);
    }
    /**
     * Object: �����Ż��ۿ۵���¼ 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ����Ż��ۿ�-�ܾ����ۿ�property 
     */
    public java.math.BigDecimal getSubManagerAgio()
    {
        return getBigDecimal("subManagerAgio");
    }
    public void setSubManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subManagerAgio", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ����Ż��ۿ�-���������ۿ�property 
     */
    public java.math.BigDecimal getSubSceneManagerAgio()
    {
        return getBigDecimal("subSceneManagerAgio");
    }
    public void setSubSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subSceneManagerAgio", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ����Ż��ۿ�-Ӫ���ܼ��ۿ�property 
     */
    public java.math.BigDecimal getSubSalesDirectorAgio()
    {
        return getBigDecimal("subSalesDirectorAgio");
    }
    public void setSubSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subSalesDirectorAgio", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �����ۿۺ�������property 
     */
    public java.math.BigDecimal getAgioPrice()
    {
        return getBigDecimal("agioPrice");
    }
    public void setAgioPrice(java.math.BigDecimal item)
    {
        setBigDecimal("agioPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �����ۿ۷����ܼ�property 
     */
    public java.math.BigDecimal getAgioAmount()
    {
        return getBigDecimal("agioAmount");
    }
    public void setAgioAmount(java.math.BigDecimal item)
    {
        setBigDecimal("agioAmount", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s �׵���property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ���۲��property 
     */
    public java.math.BigDecimal getSubPrice()
    {
        return getBigDecimal("subPrice");
    }
    public void setSubPrice(java.math.BigDecimal item)
    {
        setBigDecimal("subPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ��Ŀ�׼�property 
     */
    public java.math.BigDecimal getProjectStandardPrice()
    {
        return getBigDecimal("projectStandardPrice");
    }
    public void setProjectStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("projectStandardPrice", item);
    }
    /**
     * Object:�����Ż��ۿ۵���¼'s ��Ŀ�͵���property 
     */
    public java.math.BigDecimal getProjectPrice()
    {
        return getBigDecimal("projectPrice");
    }
    public void setProjectPrice(java.math.BigDecimal item)
    {
        setBigDecimal("projectPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8D90D2D3");
    }
}