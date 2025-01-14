package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceManageInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomPriceManageInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceManageInfo(String pkField)
    {
        super(pkField);
        put("buildingEntry", new com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryCollection());
        put("valueEntry", new com.kingdee.eas.fdc.market.ValueInputEntryCollection());
        put("priceAdjustEntry", new com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection());
    }
    /**
     * Object: 房间价目管理 's 价格分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection getPriceAdjustEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection)get("priceAdjustEntry");
    }
    /**
     * Object:房间价目管理's 单据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:房间价目管理's 是否执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object: 房间价目管理 's 楼栋分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryCollection getBuildingEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryCollection)get("buildingEntry");
    }
    /**
     * Object: 房间价目管理 's 销售项目 property 
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
     * Object:房间价目管理's 定/调价类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum getPriceBillType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.getEnum(getInt("priceBillType"));
    }
    public void setPriceBillType(com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum item)
    {
		if (item != null) {
        setInt("priceBillType", item.getValue());
		}
    }
    /**
     * Object:房间价目管理's 是否作废property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:房间价目管理's 建筑单价均价property 
     */
    public java.math.BigDecimal getAvgBulidPrice()
    {
        return getBigDecimal("avgBulidPrice");
    }
    public void setAvgBulidPrice(java.math.BigDecimal item)
    {
        setBigDecimal("avgBulidPrice", item);
    }
    /**
     * Object:房间价目管理's 最高建筑单价property 
     */
    public java.math.BigDecimal getMaxBuildPrice()
    {
        return getBigDecimal("maxBuildPrice");
    }
    public void setMaxBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("maxBuildPrice", item);
    }
    /**
     * Object:房间价目管理's 最低建筑单价property 
     */
    public java.math.BigDecimal getMinBuildPrice()
    {
        return getBigDecimal("minBuildPrice");
    }
    public void setMinBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("minBuildPrice", item);
    }
    /**
     * Object:房间价目管理's 套内单价均价property 
     */
    public java.math.BigDecimal getAvgRoomPrice()
    {
        return getBigDecimal("avgRoomPrice");
    }
    public void setAvgRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("avgRoomPrice", item);
    }
    /**
     * Object:房间价目管理's 最高套内单价property 
     */
    public java.math.BigDecimal getMaxRoomPrice()
    {
        return getBigDecimal("maxRoomPrice");
    }
    public void setMaxRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("maxRoomPrice", item);
    }
    /**
     * Object:房间价目管理's 最低套内单价property 
     */
    public java.math.BigDecimal getMinRoomPrice()
    {
        return getBigDecimal("minRoomPrice");
    }
    public void setMinRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("minRoomPrice", item);
    }
    /**
     * Object:房间价目管理's 审批日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:房间价目管理's 定/调价方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getPriceBillMode()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("priceBillMode"));
    }
    public void setPriceBillMode(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("priceBillMode", item.getValue());
		}
    }
    /**
     * Object:房间价目管理's 销售面积类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum getSalesareatype()
    {
        return com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum.getEnum(getString("Salesareatype"));
    }
    public void setSalesareatype(com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum item)
    {
		if (item != null) {
        setString("Salesareatype", item.getValue());
		}
    }
    /**
     * Object:房间价目管理's 含地下室建筑面积property 
     */
    public java.math.BigDecimal getIbasement()
    {
        return getBigDecimal("Ibasement");
    }
    public void setIbasement(java.math.BigDecimal item)
    {
        setBigDecimal("Ibasement", item);
    }
    /**
     * Object:房间价目管理's 含地下室套内面积property 
     */
    public java.math.BigDecimal getIbaInnside()
    {
        return getBigDecimal("IbaInnside");
    }
    public void setIbaInnside(java.math.BigDecimal item)
    {
        setBigDecimal("IbaInnside", item);
    }
    /**
     * Object:房间价目管理's managerAigoproperty 
     */
    public java.math.BigDecimal getManagerAigo()
    {
        return getBigDecimal("managerAigo");
    }
    public void setManagerAigo(java.math.BigDecimal item)
    {
        setBigDecimal("managerAigo", item);
    }
    /**
     * Object:房间价目管理's sceneManagerAgioproperty 
     */
    public java.math.BigDecimal getSceneManagerAgio()
    {
        return getBigDecimal("sceneManagerAgio");
    }
    public void setSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("sceneManagerAgio", item);
    }
    /**
     * Object:房间价目管理's salesDirectorAgioproperty 
     */
    public java.math.BigDecimal getSalesDirectorAgio()
    {
        return getBigDecimal("salesDirectorAgio");
    }
    public void setSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("salesDirectorAgio", item);
    }
    /**
     * Object:房间价目管理's preAdjValueproperty 
     */
    public java.math.BigDecimal getPreAdjValue()
    {
        return getBigDecimal("preAdjValue");
    }
    public void setPreAdjValue(java.math.BigDecimal item)
    {
        setBigDecimal("preAdjValue", item);
    }
    /**
     * Object:房间价目管理's aftAdjValueproperty 
     */
    public java.math.BigDecimal getAftAdjValue()
    {
        return getBigDecimal("aftAdjValue");
    }
    public void setAftAdjValue(java.math.BigDecimal item)
    {
        setBigDecimal("aftAdjValue", item);
    }
    /**
     * Object:房间价目管理's 开盘批次property 
     */
    public com.kingdee.eas.fdc.sellhouse.OpenBatchEnum getOpenBatch()
    {
        return com.kingdee.eas.fdc.sellhouse.OpenBatchEnum.getEnum(getString("openBatch"));
    }
    public void setOpenBatch(com.kingdee.eas.fdc.sellhouse.OpenBatchEnum item)
    {
		if (item != null) {
        setString("openBatch", item.getValue());
		}
    }
    /**
     * Object: 房间价目管理 's 货值填报分录 property 
     */
    public com.kingdee.eas.fdc.market.ValueInputEntryCollection getValueEntry()
    {
        return (com.kingdee.eas.fdc.market.ValueInputEntryCollection)get("valueEntry");
    }
    /**
     * Object:房间价目管理's 定价期限property 
     */
    public java.util.Date getPriceDate()
    {
        return getDate("priceDate");
    }
    public void setPriceDate(java.util.Date item)
    {
        setDate("priceDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33DCA7F8");
    }
}