package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBoutiqueRoomTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBoutiqueRoomTypeInfo()
    {
        this("id");
    }
    protected AbstractBoutiqueRoomTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ʒ���� 's ��� property 
     */
    public com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: ��Ʒ���� 's ƽ������ property 
     */
    public com.kingdee.eas.fdc.market.FlatLayerTypeInfo getFlatLayerType()
    {
        return (com.kingdee.eas.fdc.market.FlatLayerTypeInfo)get("flatLayerType");
    }
    public void setFlatLayerType(com.kingdee.eas.fdc.market.FlatLayerTypeInfo item)
    {
        put("flatLayerType", item);
    }
    /**
     * Object: ��Ʒ���� 's ������ property 
     */
    public com.kingdee.eas.fdc.market.DeveloperManageInfo getDeveloper()
    {
        return (com.kingdee.eas.fdc.market.DeveloperManageInfo)get("developer");
    }
    public void setDeveloper(com.kingdee.eas.fdc.market.DeveloperManageInfo item)
    {
        put("developer", item);
    }
    /**
     * Object:��Ʒ����'s �������property 
     */
    public java.math.BigDecimal getArchitectArea()
    {
        return getBigDecimal("architectArea");
    }
    public void setArchitectArea(java.math.BigDecimal item)
    {
        setBigDecimal("architectArea", item);
    }
    /**
     * Object:��Ʒ����'s �������property 
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
     * Object:��Ʒ����'s ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:��Ʒ����'s ���property 
     */
    public java.math.BigDecimal getWideSide()
    {
        return getBigDecimal("wideSide");
    }
    public void setWideSide(java.math.BigDecimal item)
    {
        setBigDecimal("wideSide", item);
    }
    /**
     * Object:��Ʒ����'s ����ʱ��property 
     */
    public java.util.Date getBuildTime()
    {
        return getDate("buildTime");
    }
    public void setBuildTime(java.util.Date item)
    {
        setDate("buildTime", item);
    }
    /**
     * Object:��Ʒ����'s ����˵��property 
     */
    public String getRoomTypeExplain()
    {
        return getString("roomTypeExplain");
    }
    public void setRoomTypeExplain(String item)
    {
        setString("roomTypeExplain", item);
    }
    /**
     * Object:��Ʒ����'s ���͵���property 
     */
    public String getRoomTypeReview()
    {
        return getString("roomTypeReview");
    }
    public void setRoomTypeReview(String item)
    {
        setString("roomTypeReview", item);
    }
    /**
     * Object: ��Ʒ���� 's ������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getRoomType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("roomType");
    }
    public void setRoomType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("roomType", item);
    }
    /**
     * Object: ��Ʒ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("direction");
    }
    public void setDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("direction", item);
    }
    /**
     * Object: ��Ʒ���� 's ������ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getRoomStyle()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("roomStyle");
    }
    public void setRoomStyle(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("roomStyle", item);
    }
    /**
     * Object:��Ʒ����'s ��Ʒ���ͷ���property 
     */
    public String getBoutiqueHouseTy()
    {
        return getString("boutiqueHouseTy");
    }
    public void setBoutiqueHouseTy(String item)
    {
        setString("boutiqueHouseTy", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A051FA0A");
    }
}