package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjectInfo extends com.kingdee.eas.fdc.basedata.ProjectInfo implements Serializable 
{
    public AbstractCurProjectInfo()
    {
        this("id");
    }
    protected AbstractCurProjectInfo(String pkField)
    {
        super(pkField);
        put("curProjProductEntries", new com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection());
    }
    /**
     * Object: 工程项目 's 当前工程项目产品设置分录 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection)get("curProjProductEntries");
    }
    /**
     * Object: 工程项目 's 父结点 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工程项目 's 项目状态 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStatusInfo getProjectStatus()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectStatusInfo)get("projectStatus");
    }
    public void setProjectStatus(com.kingdee.eas.fdc.basedata.ProjectStatusInfo item)
    {
        put("projectStatus", item);
    }
    /**
     * Object: 工程项目 's 项目系列 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.fdc.basedata.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object:工程项目's 启用property 
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
     * Object:工程项目's 项目分期property 
     */
    public String getProjectPeriod()
    {
        return getString("projectPeriod");
    }
    public void setProjectPeriod(String item)
    {
        setString("projectPeriod", item);
    }
    /**
     * Object: 工程项目 's 成本中心组织 property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costOrg");
    }
    public void setCostOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costOrg", item);
    }
    /**
     * Object:工程项目's 项目IDproperty 
     */
    public String getBdProjectID()
    {
        return getString("bdProjectID");
    }
    public void setBdProjectID(String item)
    {
        setString("bdProjectID", item);
    }
    /**
     * Object:工程项目's 工程项目长编码property 
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
     * Object: 工程项目 's 工程项目对应的成本中心 property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostCenter()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costCenter");
    }
    public void setCostCenter(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costCenter", item);
    }
    /**
     * Object:工程项目's 是否开发项目property 
     */
    public boolean isIsDevPrj()
    {
        return getBoolean("isDevPrj");
    }
    public void setIsDevPrj(boolean item)
    {
        setBoolean("isDevPrj", item);
    }
    /**
     * Object:工程项目's 项目地址property 
     */
    public String getProjectAddress()
    {
        return getString("projectAddress");
    }
    public void setProjectAddress(String item)
    {
        setString("projectAddress", item);
    }
    /**
     * Object:工程项目's 是否全期property 
     */
    public boolean isIsWholeAgeStage()
    {
        return getBoolean("isWholeAgeStage");
    }
    public void setIsWholeAgeStage(boolean item)
    {
        setBoolean("isWholeAgeStage", item);
    }
    /**
     * Object: 工程项目 's 运营分期信息 property 
     */
    public com.kingdee.eas.fdc.basedata.OperationPhasesInfo getProjectBase()
    {
        return (com.kingdee.eas.fdc.basedata.OperationPhasesInfo)get("projectBase");
    }
    public void setProjectBase(com.kingdee.eas.fdc.basedata.OperationPhasesInfo item)
    {
        put("projectBase", item);
    }
    /**
     * Object:工程项目's 税务信息property 
     */
    public com.kingdee.eas.fdc.basedata.TaxInfoEnum getTaxInfo()
    {
        return com.kingdee.eas.fdc.basedata.TaxInfoEnum.getEnum(getString("taxInfo"));
    }
    public void setTaxInfo(com.kingdee.eas.fdc.basedata.TaxInfoEnum item)
    {
		if (item != null) {
        setString("taxInfo", item.getValue());
		}
    }
    /**
     * Object:工程项目's 是否OA流程property 
     */
    public boolean isIsOA()
    {
        return getBoolean("isOA");
    }
    public void setIsOA(boolean item)
    {
        setBoolean("isOA", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F9E5E92B");
    }
}