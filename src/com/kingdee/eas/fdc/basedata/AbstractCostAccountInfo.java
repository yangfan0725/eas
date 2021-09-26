package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostAccountInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractCostAccountInfo()
    {
        this("id");
    }
    protected AbstractCostAccountInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ���Ŀ 's ���ڵĹ�����Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �ɱ���Ŀ 's ����� property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ɱ���Ŀ 's ���ڵ���֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    /**
     * Object:�ɱ���Ŀ's �ѷ���property 
     */
    public boolean isAssigned()
    {
        return getBoolean("assigned");
    }
    public void setAssigned(boolean item)
    {
        setBoolean("assigned", item);
    }
    /**
     * Object:�ɱ���Ŀ's ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:�ɱ���Ŀ's �Ƿ���Դproperty 
     */
    public boolean isIsSource()
    {
        return getBoolean("isSource");
    }
    public void setIsSource(boolean item)
    {
        setBoolean("isSource", item);
    }
    /**
     * Object:�ɱ���Ŀ's �ɱ���ĿԴidproperty 
     */
    public String getSrcCostAccountId()
    {
        return getString("srcCostAccountId");
    }
    public void setSrcCostAccountId(String item)
    {
        setString("srcCostAccountId", item);
    }
    /**
     * Object:�ɱ���Ŀ's ���property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountTypeEnum getType()
    {
        return com.kingdee.eas.fdc.basedata.CostAccountTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.basedata.CostAccountTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:�ɱ���Ŀ's ��ʶproperty 
     */
    public int getFlag()
    {
        return getInt("flag");
    }
    public void setFlag(int item)
    {
        setInt("flag", item);
    }
    /**
     * Object:�ɱ���Ŀ's �Ƿ����ɱ����ݿ�property 
     */
    public boolean isIsEnterDB()
    {
        return getBoolean("isEnterDB");
    }
    public void setIsEnterDB(boolean item)
    {
        setBoolean("isEnterDB", item);
    }
    /**
     * Object:�ɱ���Ŀ's �Ƿ�ɱ���property 
     */
    public boolean isIsCostAccount()
    {
        return getBoolean("isCostAccount");
    }
    public void setIsCostAccount(boolean item)
    {
        setBoolean("isCostAccount", item);
    }
    /**
     * Object:�ɱ���Ŀ's �ɱ���Ŀ����property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    /**
     * Object:�ɱ���Ŀ's �Ƿ��Լ�滮����property 
     */
    public boolean isIsProgramming()
    {
        return getBoolean("isProgramming");
    }
    public void setIsProgramming(boolean item)
    {
        setBoolean("isProgramming", item);
    }
    /**
     * Object:�ɱ���Ŀ's ����˰��property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:�ɱ���Ŀ's Ӫ����property 
     */
    public boolean isIsMarket()
    {
        return getBoolean("isMarket");
    }
    public void setIsMarket(boolean item)
    {
        setBoolean("isMarket", item);
    }
    /**
     * Object:�ɱ���Ŀ's �ɱ���Ŀ����property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum getYjType()
    {
        return com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum.getEnum(getString("yjType"));
    }
    public void setYjType(com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum item)
    {
		if (item != null) {
        setString("yjType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8423FF6E");
    }
}