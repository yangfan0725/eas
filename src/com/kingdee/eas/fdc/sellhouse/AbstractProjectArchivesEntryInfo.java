package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectArchivesEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractProjectArchivesEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectArchivesEntryInfo(String pkField)
    {
        super(pkField);
        put("archivesEntrys", new com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection());
    }
    /**
     * Object: ��Ŀ������¼ 's Ӫ����Ŀ property 
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
     * Object: ��Ŀ������¼ 's ���嵵�����Է�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection getArchivesEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection)get("archivesEntrys");
    }
    /**
     * Object:��Ŀ������¼'s ��Χ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum getScopeType()
    {
        return com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum.getEnum(getString("scopeType"));
    }
    public void setScopeType(com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum item)
    {
		if (item != null) {
        setString("scopeType", item.getValue());
		}
    }
    /**
     * Object:��Ŀ������¼'s ��������property 
     */
    public String getArchivesName()
    {
        return getString("archivesName");
    }
    public void setArchivesName(String item)
    {
        setString("archivesName", item);
    }
    /**
     * Object:��Ŀ������¼'s �����������property 
     */
    public com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum getBaseDataProperty()
    {
        return com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum.getEnum(getString("baseDataProperty"));
    }
    public void setBaseDataProperty(com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum item)
    {
		if (item != null) {
        setString("baseDataProperty", item.getValue());
		}
    }
    /**
     * Object:��Ŀ������¼'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum getArchivesType()
    {
        return com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum.getEnum(getString("archivesType"));
    }
    public void setArchivesType(com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum item)
    {
		if (item != null) {
        setString("archivesType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("92090FC3");
    }
}