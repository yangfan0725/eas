package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAreaSetInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAreaSetInfo()
    {
        this("id");
    }
    protected AbstractAreaSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������趨's minAreaproperty 
     */
    public java.math.BigDecimal getMinArea()
    {
        return getBigDecimal("minArea");
    }
    public void setMinArea(java.math.BigDecimal item)
    {
        setBigDecimal("minArea", item);
    }
    /**
     * Object:������趨's maxAreaproperty 
     */
    public java.math.BigDecimal getMaxArea()
    {
        return getBigDecimal("maxArea");
    }
    public void setMaxArea(java.math.BigDecimal item)
    {
        setBigDecimal("maxArea", item);
    }
    /**
     * Object:������趨's �ȽϷ�һproperty 
     */
    public com.kingdee.eas.fdc.market.MarketCompareEnum getMinCompare()
    {
        return com.kingdee.eas.fdc.market.MarketCompareEnum.getEnum(getString("minCompare"));
    }
    public void setMinCompare(com.kingdee.eas.fdc.market.MarketCompareEnum item)
    {
		if (item != null) {
        setString("minCompare", item.getValue());
		}
    }
    /**
     * Object:������趨's �ȽϷ���property 
     */
    public com.kingdee.eas.fdc.market.MarketCompareEnum getMaxCompare()
    {
        return com.kingdee.eas.fdc.market.MarketCompareEnum.getEnum(getString("maxCompare"));
    }
    public void setMaxCompare(com.kingdee.eas.fdc.market.MarketCompareEnum item)
    {
		if (item != null) {
        setString("maxCompare", item.getValue());
		}
    }
    /**
     * Object:������趨's ��С���ֵproperty 
     */
    public String getMinAreaVal()
    {
        return getString("minAreaVal");
    }
    public void setMinAreaVal(String item)
    {
        setString("minAreaVal", item);
    }
    /**
     * Object:������趨's ������ֵproperty 
     */
    public String getMaxAreaVal()
    {
        return getString("maxAreaVal");
    }
    public void setMaxAreaVal(String item)
    {
        setString("maxAreaVal", item);
    }
    /**
     * Object: ������趨 's ������֯ID property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCompanyId()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("companyId");
    }
    public void setCompanyId(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("companyId", item);
    }
    /**
     * Object:������趨's ������֯����property 
     */
    public String getCompanyNum()
    {
        return getString("companyNum");
    }
    public void setCompanyNum(String item)
    {
        setString("companyNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("617A29A4");
    }
}