package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceivingBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCReceivingBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCReceivingBillEntryInfo(String pkField)
    {
        super(pkField);
        put("sourceEntries", new com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection());
    }
    /**
     * Object: ���ز��տ��¼ 's ���ز��տ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���ز��տ��¼'s �տ���property 
     */
    public java.math.BigDecimal getRevAmount()
    {
        return getBigDecimal("revAmount");
    }
    public void setRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revAmount", item);
    }
    /**
     * Object:���ز��տ��¼'s ��λ�ҽ��property 
     */
    public java.math.BigDecimal getRevLocAmount()
    {
        return getBigDecimal("revLocAmount");
    }
    public void setRevLocAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revLocAmount", item);
    }
    /**
     * Object: ���ز��տ��¼ 's ���㷽ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:���ز��տ��¼'s �����property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    /**
     * Object: ���ز��տ��¼ 's �տ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getRevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("revAccount");
    }
    public void setRevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("revAccount", item);
    }
    /**
     * Object: ���ز��տ��¼ 's �Է���Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppAccount");
    }
    public void setOppAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppAccount", item);
    }
    /**
     * Object: ���ز��տ��¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: ���ز��տ��¼ 's �տ������ʻ� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getRevAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("revAccountBank");
    }
    public void setRevAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("revAccountBank", item);
    }
    /**
     * Object:���ز��տ��¼'s �ͻ��˺�property 
     */
    public String getBankNumber()
    {
        return getString("bankNumber");
    }
    public void setBankNumber(String item)
    {
        setString("bankNumber", item);
    }
    /**
     * Object:���ز��տ��¼'s �տ���ϸIDproperty 
     */
    public String getRevListId()
    {
        return getString("revListId");
    }
    public void setRevListId(String item)
    {
        setString("revListId", item);
    }
    /**
     * Object:���ز��տ��¼'s �տ���ϸ����property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getRevListType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("revListType"));
    }
    public void setRevListType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("revListType", item.getValue());
		}
    }
    /**
     * Object: ���ز��տ��¼ 's ת����Դ property 
     */
    public com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection getSourceEntries()
    {
        return (com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection)get("sourceEntries");
    }
    /**
     * Object: ���ز��տ��¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: ���ز��տ��¼ 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:���ز��տ��¼'s ����˵��property 
     */
    public String getSupplyDes()
    {
        return getString("supplyDes");
    }
    public void setSupplyDes(String item)
    {
        setString("supplyDes", item);
    }
    /**
     * Object:���ز��տ��¼'s ��עproperty 
     */
    public String getReMark()
    {
        return getString("reMark");
    }
    public void setReMark(String item)
    {
        setString("reMark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("26EEC414");
    }
}