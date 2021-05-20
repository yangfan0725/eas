package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValueInputEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractValueInputEntryInfo()
    {
        this("id");
    }
    protected AbstractValueInputEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ֵ���¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.market.ValueInputInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.ValueInputInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.ValueInputInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��ֵ���¼'s ����property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:��ֵ���¼'s �ƻ���Ӧ����property 
     */
    public String getBatch()
    {
        return getString("batch");
    }
    public void setBatch(String item)
    {
        setString("batch", item);
    }
    /**
     * Object:��ֵ���¼'s ¥��property 
     */
    public String getBuild()
    {
        return getString("build");
    }
    public void setBuild(String item)
    {
        setString("build", item);
    }
    /**
     * Object:��ֵ���¼'s ��Ʒ����property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:��ֵ���¼'s ��������property 
     */
    public String getModelName()
    {
        return getString("modelName");
    }
    public void setModelName(String item)
    {
        setString("modelName", item);
    }
    /**
     * Object:��ֵ���¼'s �������property 
     */
    public String getModelType()
    {
        return getString("modelType");
    }
    public void setModelType(String item)
    {
        setString("modelType", item);
    }
    /**
     * Object:��ֵ���¼'s �������property 
     */
    public String getModelArea()
    {
        return getString("modelArea");
    }
    public void setModelArea(String item)
    {
        setString("modelArea", item);
    }
    /**
     * Object:��ֵ���¼'s ���ι�Ӧ����property 
     */
    public int getAccount()
    {
        return getInt("account");
    }
    public void setAccount(int item)
    {
        setInt("account", item);
    }
    /**
     * Object:��ֵ���¼'s ���ι�Ӧ���property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:��ֵ���¼'s ��Ӧʱ��property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object:��ֵ���¼'s ���۵���property 
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
     * Object:��ֵ���¼'s ���㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.CalculateTypeEnum getCalculateType()
    {
        return com.kingdee.eas.fdc.basedata.CalculateTypeEnum.getEnum(getString("calculateType"));
    }
    public void setCalculateType(com.kingdee.eas.fdc.basedata.CalculateTypeEnum item)
    {
		if (item != null) {
        setString("calculateType", item.getValue());
		}
    }
    /**
     * Object:��ֵ���¼'s ���۽��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��ֵ���¼'s ��עproperty 
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
     * Object:��ֵ���¼'s װ�ޱ�׼property 
     */
    public com.kingdee.eas.fdc.market.DecorateEnum getDecorate()
    {
        return com.kingdee.eas.fdc.market.DecorateEnum.getEnum(getString("decorate"));
    }
    public void setDecorate(com.kingdee.eas.fdc.market.DecorateEnum item)
    {
		if (item != null) {
        setString("decorate", item.getValue());
		}
    }
    /**
     * Object:��ֵ���¼'s ������¥��Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBuildId()
    {
        return getBOSUuid("buildId");
    }
    public void setBuildId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("buildId", item);
    }
    /**
     * Object:��ֵ���¼'s ��Ʒ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProductTypeId()
    {
        return getBOSUuid("productTypeId");
    }
    public void setProductTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("productTypeId", item);
    }
    /**
     * Object:��ֵ���¼'s ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjectId()
    {
        return getBOSUuid("projectId");
    }
    public void setProjectId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projectId", item);
    }
    /**
     * Object: ��ֵ���¼ 's ���۱�ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo getPriceHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo)get("priceHead");
    }
    public void setPriceHead(com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo item)
    {
        put("priceHead", item);
    }
    /**
     * Object:��ֵ���¼'s srcIdproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcId()
    {
        return getBOSUuid("srcId");
    }
    public void setSrcId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("67F44128");
    }
}