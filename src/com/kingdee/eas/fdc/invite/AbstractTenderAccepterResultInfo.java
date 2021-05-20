package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenderAccepterResultInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractTenderAccepterResultInfo()
    {
        this("id");
    }
    protected AbstractTenderAccepterResultInfo(String pkField)
    {
        super(pkField);
        put("listEntry", new com.kingdee.eas.fdc.invite.TenderAccepterRListEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection());
    }
    /**
     * Object: �б����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection)get("entrys");
    }
    /**
     * Object:�б�����'s �г�����property 
     */
    public String getMarket()
    {
        return getString("market");
    }
    public void setMarket(String item)
    {
        setString("market", item);
    }
    /**
     * Object:�б�����'s ���property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:�б�����'s �ɱ����ݿ�property 
     */
    public String getCostData()
    {
        return getString("costData");
    }
    public void setCostData(String item)
    {
        setString("costData", item);
    }
    /**
     * Object:�б�����'s Ԥ����ͬ���property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:�б�����'s Ŀ��ɱ�property 
     */
    public String getAimCost()
    {
        return getString("aimCost");
    }
    public void setAimCost(String item)
    {
        setString("aimCost", item);
    }
    /**
     * Object:�б�����'s �ر�˵��property 
     */
    public String getSpecialNote()
    {
        return getString("specialNote");
    }
    public void setSpecialNote(String item)
    {
        setString("specialNote", item);
    }
    /**
     * Object:�б�����'s ��Ŀ��ɱ�ԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object: �б����� 's Ŀ��ɱ����䷽ʽ property 
     */
    public com.kingdee.eas.fdc.invite.DeployTypeInfo getDeployType()
    {
        return (com.kingdee.eas.fdc.invite.DeployTypeInfo)get("deployType");
    }
    public void setDeployType(com.kingdee.eas.fdc.invite.DeployTypeInfo item)
    {
        put("deployType", item);
    }
    /**
     * Object: �б����� 's ͼֽ��� property 
     */
    public com.kingdee.eas.fdc.invite.DrawingDepthInfo getDrawingDepth()
    {
        return (com.kingdee.eas.fdc.invite.DrawingDepthInfo)get("drawingDepth");
    }
    public void setDrawingDepth(com.kingdee.eas.fdc.invite.DrawingDepthInfo item)
    {
        put("drawingDepth", item);
    }
    /**
     * Object:�б�����'s �ο���property 
     */
    public String getRefPrice()
    {
        return getString("refPrice");
    }
    public void setRefPrice(String item)
    {
        setString("refPrice", item);
    }
    /**
     * Object:�б�����'s ��Ŀ��ɱ��Ƚ�property 
     */
    public com.kingdee.eas.fdc.invite.RangeEnum getCompareAim()
    {
        return com.kingdee.eas.fdc.invite.RangeEnum.getEnum(getString("compareAim"));
    }
    public void setCompareAim(com.kingdee.eas.fdc.invite.RangeEnum item)
    {
		if (item != null) {
        setString("compareAim", item.getValue());
		}
    }
    /**
     * Object: �б����� 's �ɹ���ϸ property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterRListEntryCollection getListEntry()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterRListEntryCollection)get("listEntry");
    }
    /**
     * Object:�б�����'s ����ʱ��property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:�б�����'s ����ʱ��property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:�б�����'s �������property 
     */
    public com.kingdee.eas.fdc.invite.TenderTypeEnum getTenderType()
    {
        return com.kingdee.eas.fdc.invite.TenderTypeEnum.getEnum(getString("tenderType"));
    }
    public void setTenderType(com.kingdee.eas.fdc.invite.TenderTypeEnum item)
    {
		if (item != null) {
        setString("tenderType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6C66BD4A");
    }
}