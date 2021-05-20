package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChanceInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommerceChanceInfo()
    {
        this("id");
    }
    protected AbstractCommerceChanceInfo(String pkField)
    {
        super(pkField);
        put("hopedRooms", new com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection());
        put("changeRoom", new com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection());
        put("shareSaleMan", new com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection());
        put("questionPaperAnser", new com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection());
    }
    /**
     * Object:�̻�'s ����ϵͳproperty 
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
     * Object: �̻� 's ���ز��ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object: �̻� 's ������Ŀ property 
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
     * Object:�̻�'s ��ϵ�绰property 
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
     * Object: �̻� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo getCommerceLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo)get("commerceLevel");
    }
    public void setCommerceLevel(com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo item)
    {
        put("commerceLevel", item);
    }
    /**
     * Object:�̻�'s �̻�״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum getCommerceStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum.getEnum(getString("commerceStatus"));
    }
    public void setCommerceStatus(com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum item)
    {
		if (item != null) {
        setString("commerceStatus", item.getValue());
		}
    }
    /**
     * Object: �̻� 's ���������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getHopedBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("hopedBuildingProperty");
    }
    public void setHopedBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("hopedBuildingProperty", item);
    }
    /**
     * Object: �̻� 's �����Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getHopedProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("hopedProductType");
    }
    public void setHopedProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("hopedProductType", item);
    }
    /**
     * Object: �̻� 's ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getHopedDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("hopedDirection");
    }
    public void setHopedDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("hopedDirection", item);
    }
    /**
     * Object: �̻� 's ������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo getHopedAreaRequirement()
    {
        return (com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo)get("hopedAreaRequirement");
    }
    public void setHopedAreaRequirement(com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo item)
    {
        put("hopedAreaRequirement", item);
    }
    /**
     * Object: �̻� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getHopedRoomModelType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("hopedRoomModelType");
    }
    public void setHopedRoomModelType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("hopedRoomModelType", item);
    }
    /**
     * Object: �̻� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SightRequirementInfo getHopedSightRequirement()
    {
        return (com.kingdee.eas.fdc.sellhouse.SightRequirementInfo)get("hopedSightRequirement");
    }
    public void setHopedSightRequirement(com.kingdee.eas.fdc.sellhouse.SightRequirementInfo item)
    {
        put("hopedSightRequirement", item);
    }
    /**
     * Object: �̻� 's ���򵥼� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo getHopedUnitPrice()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo)get("hopedUnitPrice");
    }
    public void setHopedUnitPrice(com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo item)
    {
        put("hopedUnitPrice", item);
    }
    /**
     * Object: �̻� 's �����ܼ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo getHopedTotalPrices()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo)get("hopedTotalPrices");
    }
    public void setHopedTotalPrices(com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo item)
    {
        put("hopedTotalPrices", item);
    }
    /**
     * Object: �̻� 's ����¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedFloorInfo getHopedFloor()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedFloorInfo)get("hopedFloor");
    }
    public void setHopedFloor(com.kingdee.eas.fdc.sellhouse.HopedFloorInfo item)
    {
        put("hopedFloor", item);
    }
    /**
     * Object: �̻� 's �̻�ԭ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo getBuyHouseReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo)get("buyHouseReason");
    }
    public void setBuyHouseReason(com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo item)
    {
        put("buyHouseReason", item);
    }
    /**
     * Object: �̻� 's ����ǿ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopePitchInfo getHopedPitch()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopePitchInfo)get("hopedPitch");
    }
    public void setHopedPitch(com.kingdee.eas.fdc.sellhouse.HopePitchInfo item)
    {
        put("hopedPitch", item);
    }
    /**
     * Object: �̻� 's �׸����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo getFirstPayProportion()
    {
        return (com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo)get("firstPayProportion");
    }
    public void setFirstPayProportion(com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo item)
    {
        put("firstPayProportion", item);
    }
    /**
     * Object:�̻�'s Ԥ������property 
     */
    public java.util.Date getIntendingDate()
    {
        return getDate("intendingDate");
    }
    public void setIntendingDate(java.util.Date item)
    {
        setDate("intendingDate", item);
    }
    /**
     * Object:�̻�'s Ԥ�Ƴɽ���property 
     */
    public java.math.BigDecimal getIntendingMoney()
    {
        return getBigDecimal("intendingMoney");
    }
    public void setIntendingMoney(java.math.BigDecimal item)
    {
        setBigDecimal("intendingMoney", item);
    }
    /**
     * Object: �̻� 's Ӫ������ property 
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
     * Object:�̻�'s �ɽ�����property 
     */
    public String getBargainOnCondition()
    {
        return getString("bargainOnCondition");
    }
    public void setBargainOnCondition(String item)
    {
        setString("bargainOnCondition", item);
    }
    /**
     * Object:�̻�'s ��ֹԭ��property 
     */
    public String getTerminateReason()
    {
        return getString("terminateReason");
    }
    public void setTerminateReason(String item)
    {
        setString("terminateReason", item);
    }
    /**
     * Object: �̻� 's ����Դ�б� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection getHopedRooms()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection)get("hopedRooms");
    }
    /**
     * Object:�̻�'s �̻�����property 
     */
    public java.util.Date getCommerceDate()
    {
        return getDate("commerceDate");
    }
    public void setCommerceDate(java.util.Date item)
    {
        setDate("commerceDate", item);
    }
    /**
     * Object:�̻�'s ��ҵĿ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum getCommerceIntention()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum.getEnum(getString("commerceIntention"));
    }
    public void setCommerceIntention(com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum item)
    {
		if (item != null) {
        setString("commerceIntention", item.getValue());
		}
    }
    /**
     * Object: �̻� 's ��Ʒ���� property 
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
     * Object: �̻� 's ������ʽ property 
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
     * Object:�̻�'s �ɸ����ڶ�property 
     */
    public java.math.BigDecimal getFirstPayAmount()
    {
        return getBigDecimal("firstPayAmount");
    }
    public void setFirstPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayAmount", item);
    }
    /**
     * Object: �̻� 's �ʾ�ش� property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection getQuestionPaperAnser()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection)get("questionPaperAnser");
    }
    /**
     * Object: �̻� 's �ͻ���Ϣ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: �̻� 's ���̻����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getNewLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("newLevel");
    }
    public void setNewLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("newLevel", item);
    }
    /**
     * Object: �̻� 's �̻��׶� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceChanceStage()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceChanceStage");
    }
    public void setCommerceChanceStage(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceChanceStage", item);
    }
    /**
     * Object: �̻� 's ��ҵĿ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getPurpose()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("purpose");
    }
    public void setPurpose(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("purpose", item);
    }
    /**
     * Object: �̻� 's �̻�������ԭ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceChangeReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceChangeReason");
    }
    public void setCommerceChangeReason(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceChangeReason", item);
    }
    /**
     * Object:�̻�'s �̻���Ч��property 
     */
    public java.util.Date getEffectiveDate()
    {
        return getDate("effectiveDate");
    }
    public void setEffectiveDate(java.util.Date item)
    {
        setDate("effectiveDate", item);
    }
    /**
     * Object:�̻�'s �����ֵproperty 
     */
    public java.math.BigDecimal getWorth()
    {
        return getBigDecimal("worth");
    }
    public void setWorth(java.math.BigDecimal item)
    {
        setBigDecimal("worth", item);
    }
    /**
     * Object:�̻�'s �ɽ�����property 
     */
    public java.math.BigDecimal getProbability()
    {
        return getBigDecimal("probability");
    }
    public void setProbability(java.math.BigDecimal item)
    {
        setBigDecimal("probability", item);
    }
    /**
     * Object:�̻�'s �̻�״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum getStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    /**
     * Object:�̻�'s ��ֹԭ��(����)property 
     */
    public String getStopReason()
    {
        return getString("stopReason");
    }
    public void setStopReason(String item)
    {
        setString("stopReason", item);
    }
    /**
     * Object:�̻�'s �̻���Դproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum getCommerceSrc()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum.getEnum(getString("commerceSrc"));
    }
    public void setCommerceSrc(com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum item)
    {
		if (item != null) {
        setString("commerceSrc", item.getValue());
		}
    }
    /**
     * Object: �̻� 's �������� property 
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
     * Object: �̻� 's ��Ʒ���� property 
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
     * Object: �̻� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getSign()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("sign");
    }
    public void setSign(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("sign", item);
    }
    /**
     * Object: �̻� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomType");
    }
    public void setRoomType(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomType", item);
    }
    /**
     * Object: �̻� 's �����Χ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getAreaScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("areaScope");
    }
    public void setAreaScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("areaScope", item);
    }
    /**
     * Object: �̻� 's ¥������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getFloorScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("floorScope");
    }
    public void setFloorScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("floorScope", item);
    }
    /**
     * Object: �̻� 's �ܼ۷�Χ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getTotal()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("total");
    }
    public void setTotal(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("total", item);
    }
    /**
     * Object: �̻� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getPriceScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("priceScope");
    }
    public void setPriceScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("priceScope", item);
    }
    /**
     * Object: �̻� 's ����Դ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection getChangeRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection)get("changeRoom");
    }
    /**
     * Object: �̻� 's �̻����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo getTrack()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo)get("track");
    }
    public void setTrack(com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo item)
    {
        put("track", item);
    }
    /**
     * Object:�̻�'s ԭ��˵��property 
     */
    public String getReasonDesc()
    {
        return getString("reasonDesc");
    }
    public void setReasonDesc(String item)
    {
        setString("reasonDesc", item);
    }
    /**
     * Object: �̻� 's �������۹����б� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection getShareSaleMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection)get("shareSaleMan");
    }
    /**
     * Object: �̻� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getOrientations()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("orientations");
    }
    public void setOrientations(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("orientations", item);
    }
    /**
     * Object: �̻� 's �����ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCustomer");
    }
    public void setCluesCustomer(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCustomer", item);
    }
    /**
     * Object:�̻�'s �Ƿ�ת�Ϲ�property 
     */
    public boolean isIsToPur()
    {
        return getBoolean("isToPur");
    }
    public void setIsToPur(boolean item)
    {
        setBoolean("isToPur", item);
    }
    /**
     * Object:�̻�'s �Ƿ�תԤ��property 
     */
    public boolean isIsToPre()
    {
        return getBoolean("isToPre");
    }
    public void setIsToPre(boolean item)
    {
        setBoolean("isToPre", item);
    }
    /**
     * Object:�̻�'s �Ƿ�תǩԼproperty 
     */
    public boolean isIsToSign()
    {
        return getBoolean("isToSign");
    }
    public void setIsToSign(boolean item)
    {
        setBoolean("isToSign", item);
    }
    /**
     * Object:�̻�'s �Ƿ�����̻��ͻ����property 
     */
    public boolean isIsChange()
    {
        return getBoolean("isChange");
    }
    public void setIsChange(boolean item)
    {
        setBoolean("isChange", item);
    }
    /**
     * Object: �̻� 's ��ֹԭ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getLinkStopReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("linkStopReason");
    }
    public void setLinkStopReason(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("linkStopReason", item);
    }
    /**
     * Object: �̻� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getWorkArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("workArea");
    }
    public void setWorkArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("workArea", item);
    }
    /**
     * Object: �̻� 's ��ס���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getStayArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("stayArea");
    }
    public void setStayArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("stayArea", item);
    }
    /**
     * Object: �̻� 's ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getIntentionType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("intentionType");
    }
    public void setIntentionType(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("intentionType", item);
    }
    /**
     * Object: �̻� 's ý������ property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("classify", item);
    }
    /**
     * Object:�̻�'s ���¸�������property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    /**
     * Object:�̻�'s ���¸�������property 
     */
    public String getTrackContent()
    {
        return getString("trackContent");
    }
    public void setTrackContent(String item)
    {
        setString("trackContent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AB97E58A");
    }
}