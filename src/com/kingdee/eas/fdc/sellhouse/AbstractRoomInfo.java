package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractRoomInfo()
    {
        this("id");
    }
    protected AbstractRoomInfo(String pkField)
    {
        super(pkField);
        put("shePriceHistoryEntrys", new com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryCollection());
        put("attachmentEntry", new com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection());
        put("habitationRecords", new com.kingdee.eas.fdc.sellhouse.HabitationRecordCollection());
    }
    /**
     * Object: ���� 's �̴� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderInfo getSellOrder()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderInfo)get("sellOrder");
    }
    public void setSellOrder(com.kingdee.eas.fdc.sellhouse.SellOrderInfo item)
    {
        put("sellOrder", item);
    }
    /**
     * Object: ���� 's ¥�� property 
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
     * Object:����'s ��Ԫproperty 
     */
    public int getUnit()
    {
        return getInt("unit");
    }
    public void setUnit(int item)
    {
        setInt("unit", item);
    }
    /**
     * Object: ���� 's ��Ԫ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getBuildUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("buildUnit");
    }
    public void setBuildUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("buildUnit", item);
    }
    /**
     * Object:����'s ¥��property 
     */
    public int getFloor()
    {
        return getInt("floor");
    }
    public void setFloor(int item)
    {
        setInt("floor", item);
    }
    /**
     * Object:����'s ���property 
     */
    public int getSerialNumber()
    {
        return getInt("serialNumber");
    }
    public void setSerialNumber(int item)
    {
        setInt("serialNumber", item);
    }
    /**
     * Object:����'s �ںϽ�����property 
     */
    public int getEndSerialNumber()
    {
        return getInt("endSerialNumber");
    }
    public void setEndSerialNumber(int item)
    {
        setInt("endSerialNumber", item);
    }
    /**
     * Object: ���� 's ���� property 
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
     * Object:����'s �������property 
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
     * Object:����'s �������property 
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
     * Object:����'s ��̯���property 
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
     * Object:����'s ��̨���property 
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
     * Object:����'s ��԰���property 
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
     * Object:����'s �������property 
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
     * Object:����'s ʹ�����property 
     */
    public java.math.BigDecimal getUseArea()
    {
        return getBigDecimal("useArea");
    }
    public void setUseArea(java.math.BigDecimal item)
    {
        setBigDecimal("useArea", item);
    }
    /**
     * Object:����'s ƽ̨���property 
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
     * Object:����'s ���property 
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
     * Object: ���� 's �������� property 
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
     * Object:����'s ��������property 
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
     * Object:����'s ������׼property 
     */
    public String getDeliverHouseStandard()
    {
        return getString("deliverHouseStandard");
    }
    public void setDeliverHouseStandard(String item)
    {
        setString("deliverHouseStandard", item);
    }
    /**
     * Object:����'s װ�ޱ�׼property 
     */
    public String getFitmentStandard()
    {
        return getString("fitmentStandard");
    }
    public void setFitmentStandard(String item)
    {
        setString("fitmentStandard", item);
    }
    /**
     * Object:����'s ʵ�⽨�����property 
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
     * Object:����'s ʵ���������property 
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
     * Object:����'s ��������property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:����'s ���ڵ���property 
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
     * Object:����'s ��׼�ܼ�property 
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
     * Object:����'s �Ƿ������������property 
     */
    public boolean isIsCalByRoomArea()
    {
        return getBoolean("isCalByRoomArea");
    }
    public void setIsCalByRoomArea(boolean item)
    {
        setBoolean("isCalByRoomArea", item);
    }
    /**
     * Object:����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getSellState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("sellState"));
    }
    public void setSellState(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("sellState", item.getValue());
		}
    }
    /**
     * Object:����'s ƽ��ͼproperty 
     */
    public byte[] getImgData()
    {
        return (byte[])get("imgData");
    }
    public void setImgData(byte[] item)
    {
        put("imgData", item);
    }
    /**
     * Object: ���� 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection getAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection)get("attachmentEntry");
    }
    /**
     * Object:����'s �Ƿ��Ѻproperty 
     */
    public boolean isIsMortagaged()
    {
        return getBoolean("isMortagaged");
    }
    public void setIsMortagaged(boolean item)
    {
        setBoolean("isMortagaged", item);
    }
    /**
     * Object:����'s �������״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum getRoomJoinState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum.getEnum(getString("roomJoinState"));
    }
    public void setRoomJoinState(com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum item)
    {
		if (item != null) {
        setString("roomJoinState", item.getValue());
		}
    }
    /**
     * Object:����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum getRoomLoanState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum.getEnum(getString("roomLoanState"));
    }
    public void setRoomLoanState(com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum item)
    {
		if (item != null) {
        setString("roomLoanState", item.getValue());
		}
    }
    /**
     * Object:����'s ��Ȩ�Ǽ�״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum getRoomBookState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum.getEnum(getString("roomBookState"));
    }
    public void setRoomBookState(com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum item)
    {
		if (item != null) {
        setString("roomBookState", item.getValue());
		}
    }
    /**
     * Object:����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum getRoomCompensateState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum.getEnum(getString("roomCompensateState"));
    }
    public void setRoomCompensateState(com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum item)
    {
		if (item != null) {
        setString("roomCompensateState", item.getValue());
		}
    }
    /**
     * Object:����'s ���������״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum getRoomACCFundState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum.getEnum(getString("roomACCFundState"));
    }
    public void setRoomACCFundState(com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum item)
    {
		if (item != null) {
        setString("roomACCFundState", item.getValue());
		}
    }
    /**
     * Object:����'s �ɽ�����property 
     */
    public java.math.BigDecimal getDealPrice()
    {
        return getBigDecimal("dealPrice");
    }
    public void setDealPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealPrice", item);
    }
    /**
     * Object:����'s �ɽ��ܼ�property 
     */
    public java.math.BigDecimal getDealTotalAmount()
    {
        return getBigDecimal("dealTotalAmount");
    }
    public void setDealTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dealTotalAmount", item);
    }
    /**
     * Object:����'s תԤ������property 
     */
    public java.util.Date getToPrePurchaseDate()
    {
        return getDate("toPrePurchaseDate");
    }
    public void setToPrePurchaseDate(java.util.Date item)
    {
        setDate("toPrePurchaseDate", item);
    }
    /**
     * Object:����'s ת�Ϲ�����property 
     */
    public java.util.Date getToPurchaseDate()
    {
        return getDate("toPurchaseDate");
    }
    public void setToPurchaseDate(java.util.Date item)
    {
        setDate("toPurchaseDate", item);
    }
    /**
     * Object:����'s תǩԼ����property 
     */
    public java.util.Date getToSignDate()
    {
        return getDate("toSignDate");
    }
    public void setToSignDate(java.util.Date item)
    {
        setDate("toSignDate", item);
    }
    /**
     * Object: ���� 's ���µ��Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getLastPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("lastPurchase");
    }
    public void setLastPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("lastPurchase", item);
    }
    /**
     * Object: ���� 's ���µ�ǩԼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo getLastSignContract()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo)get("lastSignContract");
    }
    public void setLastSignContract(com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo item)
    {
        put("lastSignContract", item);
    }
    /**
     * Object: ���� 's ���µĲ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo getLastAreaCompensate()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo)get("lastAreaCompensate");
    }
    public void setLastAreaCompensate(com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo item)
    {
        put("lastAreaCompensate", item);
    }
    /**
     * Object:����'s �����property 
     */
    public java.math.BigDecimal getAreaCompensateAmount()
    {
        return getBigDecimal("areaCompensateAmount");
    }
    public void setAreaCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("areaCompensateAmount", item);
    }
    /**
     * Object:����'s ���۶�property 
     */
    public java.math.BigDecimal getSellAmount()
    {
        return getBigDecimal("sellAmount");
    }
    public void setSellAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmount", item);
    }
    /**
     * Object:����'s �Ƿ���ǰ����property 
     */
    public boolean isIsAreaAudited()
    {
        return getBoolean("isAreaAudited");
    }
    public void setIsAreaAudited(boolean item)
    {
        setBoolean("isAreaAudited", item);
    }
    /**
     * Object:����'s �Ƿ�ʵ�⸴��property 
     */
    public boolean isIsActualAreaAudited()
    {
        return getBoolean("isActualAreaAudited");
    }
    public void setIsActualAreaAudited(boolean item)
    {
        setBoolean("isActualAreaAudited", item);
    }
    /**
     * Object:����'s �Ƿ��Ѿ�Ԥ������property 
     */
    public boolean isIsPlanAudited()
    {
        return getBoolean("isPlanAudited");
    }
    public void setIsPlanAudited(boolean item)
    {
        setBoolean("isPlanAudited", item);
    }
    /**
     * Object: ���� 's ���� property 
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
     * Object: ���� 's ���� property 
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
     * Object: ���� 's ���±����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo getLastKeepDownBill()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo)get("lastKeepDownBill");
    }
    public void setLastKeepDownBill(com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo item)
    {
        put("lastKeepDownBill", item);
    }
    /**
     * Object:����'s �ܼ۵׼�property 
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
     * Object:����'s �������۵׼�property 
     */
    public java.math.BigDecimal getBaseBuildingPrice()
    {
        return getBigDecimal("baseBuildingPrice");
    }
    public void setBaseBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseBuildingPrice", item);
    }
    /**
     * Object:����'s �������۵׼�property 
     */
    public java.math.BigDecimal getBaseRoomPrice()
    {
        return getBigDecimal("baseRoomPrice");
    }
    public void setBaseRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseRoomPrice", item);
    }
    /**
     * Object:����'s �׼��Ƿ񸴺�property 
     */
    public boolean isIsBasePriceAudited()
    {
        return getBoolean("isBasePriceAudited");
    }
    public void setIsBasePriceAudited(boolean item)
    {
        setBoolean("isBasePriceAudited", item);
    }
    /**
     * Object: ���� 's ������ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomFormInfo getRoomForm()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomFormInfo)get("roomForm");
    }
    public void setRoomForm(com.kingdee.eas.fdc.sellhouse.RoomFormInfo item)
    {
        put("roomForm", item);
    }
    /**
     * Object:����'s ����ʼ����property 
     */
    public java.util.Date getTenancyStartDate()
    {
        return getDate("tenancyStartDate");
    }
    public void setTenancyStartDate(java.util.Date item)
    {
        setDate("tenancyStartDate", item);
    }
    /**
     * Object:����'s ��������property 
     */
    public java.util.Date getHandoverRoomDate()
    {
        return getDate("handoverRoomDate");
    }
    public void setHandoverRoomDate(java.util.Date item)
    {
        setDate("handoverRoomDate", item);
    }
    /**
     * Object:����'s ��׼���property 
     */
    public java.math.BigDecimal getStandardRent()
    {
        return getBigDecimal("standardRent");
    }
    public void setStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRent", item);
    }
    /**
     * Object:����'s ��׼��𵥼�property 
     */
    public java.math.BigDecimal getStandardRentPrice()
    {
        return getBigDecimal("standardRentPrice");
    }
    public void setStandardRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRentPrice", item);
    }
    /**
     * Object:����'s ���״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    /**
     * Object:����'s �������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rentType"));
    }
    public void setRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rentType", item.getValue());
		}
    }
    /**
     * Object: ���� 's ��Ʒ���� property 
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
     * Object: ���� 's ��ǰ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getLastTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("lastTenancy");
    }
    public void setLastTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("lastTenancy", item);
    }
    /**
     * Object: ���� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProductDetialInfo getProductDetail()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProductDetialInfo)get("productDetail");
    }
    public void setProductDetail(com.kingdee.eas.fdc.sellhouse.ProductDetialInfo item)
    {
        put("productDetail", item);
    }
    /**
     * Object: ���� 's ������ʷ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryCollection getShePriceHistoryEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryCollection)get("shePriceHistoryEntrys");
    }
    /**
     * Object:����'s ת��������property 
     */
    public java.util.Date getToSaleDate()
    {
        return getDate("toSaleDate");
    }
    public void setToSaleDate(java.util.Date item)
    {
        setDate("toSaleDate", item);
    }
    /**
     * Object:����'s ��¥����property 
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
     * Object:����'s ��������property 
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
     * Object:����'s ��ҵ����property 
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
     * Object: ���� 's ��ס��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationRecordCollection getHabitationRecords()
    {
        return (com.kingdee.eas.fdc.sellhouse.HabitationRecordCollection)get("habitationRecords");
    }
    /**
     * Object:����'s ������㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("tenancyModel"));
    }
    public void setTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("tenancyModel", item.getValue());
		}
    }
    /**
     * Object:����'s ������𵥼�property 
     */
    public java.math.BigDecimal getBuildingRentPrice()
    {
        return getBigDecimal("buildingRentPrice");
    }
    public void setBuildingRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingRentPrice", item);
    }
    /**
     * Object:����'s ������𵥼�property 
     */
    public java.math.BigDecimal getRoomRentPrice()
    {
        return getBigDecimal("roomRentPrice");
    }
    public void setRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomRentPrice", item);
    }
    /**
     * Object: ���� 's ������; property 
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
     * Object:����'s �������property 
     */
    public java.math.BigDecimal getSaleArea()
    {
        return getBigDecimal("saleArea");
    }
    public void setSaleArea(java.math.BigDecimal item)
    {
        setBigDecimal("saleArea", item);
    }
    /**
     * Object:����'s ʵ�ⷿ��property 
     */
    public String getRoomNo()
    {
        return getString("roomNo");
    }
    public void setRoomNo(String item)
    {
        setString("roomNo", item);
    }
    /**
     * Object:����'s �Ƿ��ǳ�λproperty 
     */
    public boolean isIsParking()
    {
        return getBoolean("isParking");
    }
    public void setIsParking(boolean item)
    {
        setBoolean("isParking", item);
    }
    /**
     * Object: ���� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo getBuildingFloor()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo)get("buildingFloor");
    }
    public void setBuildingFloor(com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo item)
    {
        put("buildingFloor", item);
    }
    /**
     * Object:����'s ��λ״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ParkStateEnum getParkingState()
    {
        return com.kingdee.eas.fdc.sellhouse.ParkStateEnum.getEnum(getString("parkingState"));
    }
    public void setParkingState(com.kingdee.eas.fdc.sellhouse.ParkStateEnum item)
    {
		if (item != null) {
        setString("parkingState", item.getValue());
		}
    }
    /**
     * Object:����'s ��ҵ����property 
     */
    public String getRoomPropNo()
    {
        return getString("roomPropNo");
    }
    public void setRoomPropNo(String item)
    {
        setString("roomPropNo", item);
    }
    /**
     * Object: ���� 's ���� property 
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
     * Object: ���� 's װ�ޱ�׼ property 
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
     * Object: ���� 's ��Ұ property 
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
     * Object: ���� 's ������׼ property 
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
     * Object: ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.csm.RoomHandoverInfo getRoomHandover()
    {
        return (com.kingdee.eas.fdc.csm.RoomHandoverInfo)get("roomHandover");
    }
    public void setRoomHandover(com.kingdee.eas.fdc.csm.RoomHandoverInfo item)
    {
        put("roomHandover", item);
    }
    /**
     * Object:����'s ������property 
     */
    public String getDisplayName()
    {
        return getString("displayName");
    }
    public void setDisplayName(String item)
    {
        setString("displayName", item);
    }
    /**
     * Object:����'s �Ƿ��д�property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum getWindow()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum.getEnum(getString("window"));
    }
    public void setWindow(com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum item)
    {
		if (item != null) {
        setString("window", item.getValue());
		}
    }
    /**
     * Object:����'s �������property 
     */
    public java.math.BigDecimal getTenancyArea()
    {
        return getBigDecimal("tenancyArea");
    }
    public void setTenancyArea(java.math.BigDecimal item)
    {
        setBigDecimal("tenancyArea", item);
    }
    /**
     * Object:����'s �տ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum getReceiptTypeState()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum.getEnum(getString("ReceiptTypeState"));
    }
    public void setReceiptTypeState(com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum item)
    {
		if (item != null) {
        setString("ReceiptTypeState", item.getValue());
		}
    }
    /**
     * Object:����'s �յ���property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    /**
     * Object:����'s ���۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("sellType"));
    }
    public void setSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("sellType", item.getValue());
		}
    }
    /**
     * Object:����'s ���䶨�۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.CalcTypeEnum getCalcType()
    {
        return com.kingdee.eas.fdc.sellhouse.CalcTypeEnum.getEnum(getString("calcType"));
    }
    public void setCalcType(com.kingdee.eas.fdc.sellhouse.CalcTypeEnum item)
    {
		if (item != null) {
        setString("calcType", item.getValue());
		}
    }
    /**
     * Object:����'s ������״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.ChangeStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:����'s Ԥ���������property 
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
     * Object:����'s Ԥ���������property 
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
     * Object:����'s Ԥ������property 
     */
    public boolean isIsPlan()
    {
        return getBoolean("isPlan");
    }
    public void setIsPlan(boolean item)
    {
        setBoolean("isPlan", item);
    }
    /**
     * Object:����'s Ԥ�۸���property 
     */
    public boolean isIsPre()
    {
        return getBoolean("isPre");
    }
    public void setIsPre(boolean item)
    {
        setBoolean("isPre", item);
    }
    /**
     * Object:����'s �Ƿ�����property 
     */
    public boolean isIsPush()
    {
        return getBoolean("isPush");
    }
    public void setIsPush(boolean item)
    {
        setBoolean("isPush", item);
    }
    /**
     * Object:����'s �����֤����property 
     */
    public String getRoomCertifiName()
    {
        return getString("roomCertifiName");
    }
    public void setRoomCertifiName(String item)
    {
        setString("roomCertifiName", item);
    }
    /**
     * Object: ���� 's �����ṹ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo getBuilStruct()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo)get("builStruct");
    }
    public void setBuilStruct(com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo item)
    {
        put("builStruct", item);
    }
    /**
     * Object:����'s ��Դ�ָ���property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum getListSeparatorState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum.getEnum(getString("listSeparatorState"));
    }
    public void setListSeparatorState(com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum item)
    {
		if (item != null) {
        setString("listSeparatorState", item.getValue());
		}
    }
    /**
     * Object:����'s ռλ��property 
     */
    public String getSub()
    {
        return getString("sub");
    }
    public void setSub(String item)
    {
        setString("sub", item);
    }
    /**
     * Object:����'s �����������property 
     */
    public java.math.BigDecimal getMainAmount()
    {
        return getBigDecimal("mainAmount");
    }
    public void setMainAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mainAmount", item);
    }
    /**
     * Object:����'s �������������property 
     */
    public java.math.BigDecimal getAttAmout()
    {
        return getBigDecimal("attAmout");
    }
    public void setAttAmout(java.math.BigDecimal item)
    {
        setBigDecimal("attAmout", item);
    }
    /**
     * Object:����'s ʵ�ʲ����property 
     */
    public java.math.BigDecimal getActualAmount()
    {
        return getBigDecimal("actualAmount");
    }
    public void setActualAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actualAmount", item);
    }
    /**
     * Object: ���� 's ������ property 
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
     * Object: ���� 's �¾��� property 
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
     * Object: ���� 's ����Ұ property 
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
     * Object: ���� 's ��װ�ޱ�׼ property 
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
     * Object: ���� 's �½�����׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getNewPossStd()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("newPossStd");
    }
    public void setNewPossStd(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("newPossStd", item);
    }
    /**
     * Object: ���� 's �·�����; property 
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
     * Object: ���� 's �²�Ʒ���� property 
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
     * Object:����'s Ԥ������״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum getPlanChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum.getEnum(getString("planChangeState"));
    }
    public void setPlanChangeState(com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum item)
    {
		if (item != null) {
        setString("planChangeState", item.getValue());
		}
    }
    /**
     * Object:����'s Ԥ�۸���״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum getPreChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum.getEnum(getString("preChangeState"));
    }
    public void setPreChangeState(com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum item)
    {
		if (item != null) {
        setString("preChangeState", item.getValue());
		}
    }
    /**
     * Object:����'s ʵ�⸴��״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum getActChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum.getEnum(getString("actChangeState"));
    }
    public void setActChangeState(com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum item)
    {
		if (item != null) {
        setString("actChangeState", item.getValue());
		}
    }
    /**
     * Object:����'s �Ƿ��Ѿ�ʵ�⸴��property 
     */
    public boolean isIsActAudited()
    {
        return getBoolean("isActAudited");
    }
    public void setIsActAudited(boolean item)
    {
        setBoolean("isActAudited", item);
    }
    /**
     * Object:����'s �Ƿ��Ѿ�Ԥ�۸���property 
     */
    public boolean isIsPreAudited()
    {
        return getBoolean("isPreAudited");
    }
    public void setIsPreAudited(boolean item)
    {
        setBoolean("isPreAudited", item);
    }
    /**
     * Object:����'s �����������property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum getSellAreaType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum.getEnum(getString("sellAreaType"));
    }
    public void setSellAreaType(com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum item)
    {
		if (item != null) {
        setString("sellAreaType", item.getValue());
		}
    }
    /**
     * Object:����'s ��������Ƿ����ı�property 
     */
    public boolean isIsChangeSellArea()
    {
        return getBoolean("isChangeSellArea");
    }
    public void setIsChangeSellArea(boolean item)
    {
        setBoolean("isChangeSellArea", item);
    }
    /**
     * Object:����'s �������ҽ������property 
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
     * Object:����'s ���������������property 
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
     * Object:����'s ʵ�⺬�����ҽ������property 
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
     * Object:����'s ʵ�⺬�������������property 
     */
    public java.math.BigDecimal getInsside()
    {
        return getBigDecimal("Insside");
    }
    public void setInsside(java.math.BigDecimal item)
    {
        setBigDecimal("Insside", item);
    }
    /**
     * Object:����'s �ܾ����ۿ�property 
     */
    public java.math.BigDecimal getManagerAgio()
    {
        return getBigDecimal("managerAgio");
    }
    public void setManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("managerAgio", item);
    }
    /**
     * Object:����'s ���������ۿ�property 
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
     * Object:����'s Ӫ���ܼ��ۿ�property 
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
     * Object:����'s ����˰��property 
     */
    public java.math.BigDecimal getSaleRate()
    {
        return getBigDecimal("saleRate");
    }
    public void setSaleRate(java.math.BigDecimal item)
    {
        setBigDecimal("saleRate", item);
    }
    /**
     * Object:����'s �Ƿ���property 
     */
    public boolean isIsReturn()
    {
        return getBoolean("isReturn");
    }
    public void setIsReturn(boolean item)
    {
        setBoolean("isReturn", item);
    }
    /**
     * Object:����'s ��Ŀ�׼�property 
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
     * Object:����'s ��Ŀ�������۵׼�property 
     */
    public java.math.BigDecimal getProjectBuildingPrice()
    {
        return getBigDecimal("projectBuildingPrice");
    }
    public void setProjectBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("projectBuildingPrice", item);
    }
    /**
     * Object:����'s ��Ŀ�������۵׼�property 
     */
    public java.math.BigDecimal getProjectRoomPrice()
    {
        return getBigDecimal("projectRoomPrice");
    }
    public void setProjectRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("projectRoomPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("903E0236");
    }
}