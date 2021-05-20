package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomDesInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractRoomDesInfo()
    {
        this("id");
    }
    protected AbstractRoomDesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���䶨�� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:���䶨��'s ��ʼ��Ԫproperty 
     */
    public int getUnitBegin()
    {
        return getInt("unitBegin");
    }
    public void setUnitBegin(int item)
    {
        setInt("unitBegin", item);
    }
    /**
     * Object:���䶨��'s ������Ԫproperty 
     */
    public int getUnitEnd()
    {
        return getInt("unitEnd");
    }
    public void setUnitEnd(int item)
    {
        setInt("unitEnd", item);
    }
    /**
     * Object:���䶨��'s ��ʼ¥��property 
     */
    public int getFloorBegin()
    {
        return getInt("floorBegin");
    }
    public void setFloorBegin(int item)
    {
        setInt("floorBegin", item);
    }
    /**
     * Object:���䶨��'s ����¥��property 
     */
    public int getFloorEnd()
    {
        return getInt("floorEnd");
    }
    public void setFloorEnd(int item)
    {
        setInt("floorEnd", item);
    }
    /**
     * Object:���䶨��'s ��ʼ��Ȼ¥��property 
     */
    public int getNatrueFloorBegin()
    {
        return getInt("natrueFloorBegin");
    }
    public void setNatrueFloorBegin(int item)
    {
        setInt("natrueFloorBegin", item);
    }
    /**
     * Object:���䶨��'s ������Ȼ¥��property 
     */
    public int getNatrueFloorEnd()
    {
        return getInt("natrueFloorEnd");
    }
    public void setNatrueFloorEnd(int item)
    {
        setInt("natrueFloorEnd", item);
    }
    /**
     * Object:���䶨��'s ��ʼ���property 
     */
    public int getSerialNumberBegin()
    {
        return getInt("serialNumberBegin");
    }
    public void setSerialNumberBegin(int item)
    {
        setInt("serialNumberBegin", item);
    }
    /**
     * Object:���䶨��'s �������property 
     */
    public int getSerialNumberEnd()
    {
        return getInt("serialNumberEnd");
    }
    public void setSerialNumberEnd(int item)
    {
        setInt("serialNumberEnd", item);
    }
    /**
     * Object:���䶨��'s �������property 
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
     * Object:���䶨��'s �������property 
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
     * Object:���䶨��'s ��̯���property 
     */
    public java.math.BigDecimal getApportionArea()
    {
        return getBigDecimal("apportionArea");
    }
    public void setApportionArea(java.math.BigDecimal item)
    {
        setBigDecimal("apportionArea", item);
    }
    /**
     * Object:���䶨��'s ��̨���property 
     */
    public java.math.BigDecimal getBalconyArea()
    {
        return getBigDecimal("balconyArea");
    }
    public void setBalconyArea(java.math.BigDecimal item)
    {
        setBigDecimal("balconyArea", item);
    }
    /**
     * Object:���䶨��'s ���property 
     */
    public java.math.BigDecimal getFloorHeight()
    {
        return getBigDecimal("floorHeight");
    }
    public void setFloorHeight(java.math.BigDecimal item)
    {
        setBigDecimal("floorHeight", item);
    }
    /**
     * Object: ���䶨�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomModel()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomModel");
    }
    public void setRoomModel(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomModel", item);
    }
    /**
     * Object: ���䶨�� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("buildingProperty");
    }
    public void setBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("buildingProperty", item);
    }
    /**
     * Object:���䶨��'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.HousePropertyEnum getHouseProperty()
    {
        return com.kingdee.eas.fdc.sellhouse.HousePropertyEnum.getEnum(getString("houseProperty"));
    }
    public void setHouseProperty(com.kingdee.eas.fdc.sellhouse.HousePropertyEnum item)
    {
		if (item != null) {
        setString("houseProperty", item.getValue());
		}
    }
    /**
     * Object:���䶨��'s �Ƿ����ת������ĸproperty 
     */
    public boolean isIsConvertToChar()
    {
        return getBoolean("isConvertToChar");
    }
    public void setIsConvertToChar(boolean item)
    {
        setBoolean("isConvertToChar", item);
    }
    /**
     * Object:���䶨��'s ʵ�⽨�����property 
     */
    public java.math.BigDecimal getActualBuildingArea()
    {
        return getBigDecimal("actualBuildingArea");
    }
    public void setActualBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualBuildingArea", item);
    }
    /**
     * Object:���䶨��'s ʵ���������property 
     */
    public java.math.BigDecimal getActualRoomArea()
    {
        return getBigDecimal("actualRoomArea");
    }
    public void setActualRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualRoomArea", item);
    }
    /**
     * Object: ���䶨�� 's ���� property 
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
     * Object: ���䶨�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SightRequirementInfo getSight()
    {
        return (com.kingdee.eas.fdc.sellhouse.SightRequirementInfo)get("sight");
    }
    public void setSight(com.kingdee.eas.fdc.sellhouse.SightRequirementInfo item)
    {
        put("sight", item);
    }
    /**
     * Object:���䶨��'s ����ǰ׺��ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomNumPrefixEnum getNumPrefixType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomNumPrefixEnum.getEnum(getString("numPrefixType"));
    }
    public void setNumPrefixType(com.kingdee.eas.fdc.sellhouse.RoomNumPrefixEnum item)
    {
		if (item != null) {
        setString("numPrefixType", item.getValue());
		}
    }
    /**
     * Object: ���䶨�� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:���䶨��'s ��¥����property 
     */
    public boolean isIsForSHE()
    {
        return getBoolean("isForSHE");
    }
    public void setIsForSHE(boolean item)
    {
        setBoolean("isForSHE", item);
    }
    /**
     * Object:���䶨��'s ��������property 
     */
    public boolean isIsForTen()
    {
        return getBoolean("isForTen");
    }
    public void setIsForTen(boolean item)
    {
        setBoolean("isForTen", item);
    }
    /**
     * Object:���䶨��'s ��ҵ����property 
     */
    public boolean isIsForPPM()
    {
        return getBoolean("isForPPM");
    }
    public void setIsForPPM(boolean item)
    {
        setBoolean("isForPPM", item);
    }
    /**
     * Object: ���䶨�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.NoiseInfo getNoise()
    {
        return (com.kingdee.eas.fdc.sellhouse.NoiseInfo)get("noise");
    }
    public void setNoise(com.kingdee.eas.fdc.sellhouse.NoiseInfo item)
    {
        put("noise", item);
    }
    /**
     * Object: ���䶨�� 's װ�ޱ�׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo getDecoraStd()
    {
        return (com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo)get("decoraStd");
    }
    public void setDecoraStd(com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo item)
    {
        put("decoraStd", item);
    }
    /**
     * Object: ���䶨�� 's ��Ұ property 
     */
    public com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo getEyeSignht()
    {
        return (com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo)get("eyeSignht");
    }
    public void setEyeSignht(com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo item)
    {
        put("eyeSignht", item);
    }
    /**
     * Object: ���䶨�� 's ������׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo getPosseStd()
    {
        return (com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo)get("posseStd");
    }
    public void setPosseStd(com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo item)
    {
        put("posseStd", item);
    }
    /**
     * Object: ���䶨�� 's ������; property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomUsageInfo getRoomUsage()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomUsageInfo)get("roomUsage");
    }
    public void setRoomUsage(com.kingdee.eas.fdc.sellhouse.RoomUsageInfo item)
    {
        put("roomUsage", item);
    }
    /**
     * Object:���䶨��'s ƽ̨���property 
     */
    public java.math.BigDecimal getFlatArea()
    {
        return getBigDecimal("flatArea");
    }
    public void setFlatArea(java.math.BigDecimal item)
    {
        setBigDecimal("flatArea", item);
    }
    /**
     * Object:���䶨��'s �������property 
     */
    public java.math.BigDecimal getCarbarnArea()
    {
        return getBigDecimal("carbarnArea");
    }
    public void setCarbarnArea(java.math.BigDecimal item)
    {
        setBigDecimal("carbarnArea", item);
    }
    /**
     * Object:���䶨��'s Ԥ���������property 
     */
    public java.math.BigDecimal getPlanRoomArea()
    {
        return getBigDecimal("planRoomArea");
    }
    public void setPlanRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("planRoomArea", item);
    }
    /**
     * Object:���䶨��'s Ԥ���������property 
     */
    public java.math.BigDecimal getPlanBuildingArea()
    {
        return getBigDecimal("planBuildingArea");
    }
    public void setPlanBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("planBuildingArea", item);
    }
    /**
     * Object:���䶨��'s ʹ�����property 
     */
    public java.math.BigDecimal getUserArea()
    {
        return getBigDecimal("userArea");
    }
    public void setUserArea(java.math.BigDecimal item)
    {
        setBigDecimal("userArea", item);
    }
    /**
     * Object:���䶨��'s ��԰���property 
     */
    public java.math.BigDecimal getGuardenArea()
    {
        return getBigDecimal("guardenArea");
    }
    public void setGuardenArea(java.math.BigDecimal item)
    {
        setBigDecimal("guardenArea", item);
    }
    /**
     * Object: ���䶨�� 's �����ṹ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo getBuildStruct()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo)get("buildStruct");
    }
    public void setBuildStruct(com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo item)
    {
        put("buildStruct", item);
    }
    /**
     * Object:���䶨��'s ҵ������property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object: ���䶨�� 's ��Ԫ��Ϣ  property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: ���䶨�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewNoise()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newNoise");
    }
    public void setNewNoise(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newNoise", item);
    }
    /**
     * Object: ���䶨�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewSight()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newSight");
    }
    public void setNewSight(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newSight", item);
    }
    /**
     * Object: ���䶨�� 's ��Ұ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewEyeSight()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newEyeSight");
    }
    public void setNewEyeSight(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newEyeSight", item);
    }
    /**
     * Object: ���䶨�� 's װ�ޱ�׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewDecorastd()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newDecorastd");
    }
    public void setNewDecorastd(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newDecorastd", item);
    }
    /**
     * Object: ���䶨�� 's ������׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewPossstd()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newPossstd");
    }
    public void setNewPossstd(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newPossstd", item);
    }
    /**
     * Object: ���䶨�� 's ������;  property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewRoomUsage()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newRoomUsage");
    }
    public void setNewRoomUsage(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newRoomUsage", item);
    }
    /**
     * Object: ���䶨�� 's ��Ʒ����  property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewProduceRemark()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newProduceRemark");
    }
    public void setNewProduceRemark(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newProduceRemark", item);
    }
    /**
     * Object:���䶨��'s �������ҽ������property 
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
     * Object:���䶨��'s ���������������property 
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
     * Object:���䶨��'s ʵ�⺬�����ҽ������property 
     */
    public java.math.BigDecimal getIbameasured()
    {
        return getBigDecimal("Ibameasured");
    }
    public void setIbameasured(java.math.BigDecimal item)
    {
        setBigDecimal("Ibameasured", item);
    }
    /**
     * Object:���䶨��'s ʵ�⺬�������������property 
     */
    public java.math.BigDecimal getInsside()
    {
        return getBigDecimal("Insside");
    }
    public void setInsside(java.math.BigDecimal item)
    {
        setBigDecimal("Insside", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A00455FC");
    }
}