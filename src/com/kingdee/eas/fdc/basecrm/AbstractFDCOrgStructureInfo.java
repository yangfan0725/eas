package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCOrgStructureInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCOrgStructureInfo()
    {
        this("id");
    }
    protected AbstractFDCOrgStructureInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ҵ����֯����'s �Ƿ���property 
     */
    public boolean isIsPreservation()
    {
        return getBoolean("isPreservation");
    }
    public void setIsPreservation(boolean item)
    {
        setBoolean("isPreservation", item);
    }
    /**
     * Object:ҵ����֯����'s �Ƿ���ʷproperty 
     */
    public boolean isIsHis()
    {
        return getBoolean("isHis");
    }
    public void setIsHis(boolean item)
    {
        setBoolean("isHis", item);
    }
    /**
     * Object:ҵ����֯����'s �ͻ�����Ԫproperty 
     */
    public boolean isCustMangageUnit()
    {
        return getBoolean("custMangageUnit");
    }
    public void setCustMangageUnit(boolean item)
    {
        setBoolean("custMangageUnit", item);
    }
    /**
     * Object:ҵ����֯����'s ��¥��֯property 
     */
    public boolean isSellHouseStrut()
    {
        return getBoolean("sellHouseStrut");
    }
    public void setSellHouseStrut(boolean item)
    {
        setBoolean("sellHouseStrut", item);
    }
    /**
     * Object:ҵ����֯����'s �Ƿ���ִ��property 
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
     * Object:ҵ����֯����'s ��֯�ṹidproperty 
     */
    public com.kingdee.bos.util.BOSUuid getOrgStructure()
    {
        return getBOSUuid("orgStructure");
    }
    public void setOrgStructure(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("orgStructure", item);
    }
    /**
     * Object:ҵ����֯����'s ������property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:ҵ����֯����'s ����property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:ҵ����֯����'s �Ƿ���property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object: ҵ����֯���� 's ��Χ property 
     */
    public com.kingdee.eas.basedata.org.OrgTreeInfo getTree()
    {
        return (com.kingdee.eas.basedata.org.OrgTreeInfo)get("tree");
    }
    public void setTree(com.kingdee.eas.basedata.org.OrgTreeInfo item)
    {
        put("tree", item);
    }
    /**
     * Object: ҵ����֯���� 's ��֯��Ԫ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: ҵ����֯���� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ҵ����֯����'s �Ƿ�ʧЧproperty 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    /**
     * Object:ҵ����֯����'s ��ʾ����property 
     */
    public String getDisplayName()
    {
        return getDisplayName((Locale)null);
    }
    public void setDisplayName(String item)
    {
		setDisplayName(item,(Locale)null);
    }
    public String getDisplayName(Locale local)
    {
        return TypeConversionUtils.objToString(get("displayName", local));
    }
    public void setDisplayName(String item, Locale local)
    {
        put("displayName", item, local);
    }
    /**
     * Object:ҵ����֯����'s ������property 
     */
    public String getSortCode()
    {
        return getString("sortCode");
    }
    public void setSortCode(String item)
    {
        setString("sortCode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A12CC18");
    }
}