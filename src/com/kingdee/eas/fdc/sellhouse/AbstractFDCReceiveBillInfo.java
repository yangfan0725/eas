package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceiveBillInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFDCReceiveBillInfo()
    {
        this("id");
    }
    protected AbstractFDCReceiveBillInfo(String pkField)
    {
        super(pkField);
        put("distillCommisionEntry", new com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection());
        put("customerEntrys", new com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection());
    }
    /**
     * Object: �տ 's ������Ŀ property 
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
     * Object: �տ 's ���� property 
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
     * Object: �տ 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: �տ 's �������� property 
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
     * Object: �տ 's ��Ʊ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvoiceInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvoiceInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.sellhouse.InvoiceInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:�տ's ���ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum getPayQuomodo()
    {
        return com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum.getEnum(getString("payQuomodo"));
    }
    public void setPayQuomodo(com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum item)
    {
		if (item != null) {
        setString("payQuomodo", item.getValue());
		}
    }
    /**
     * Object: �տ 's �վ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeInfo getCheque()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeInfo)get("cheque");
    }
    public void setCheque(com.kingdee.eas.fdc.sellhouse.ChequeInfo item)
    {
        put("cheque", item);
    }
    /**
     * Object:�տ's �վݺ�property 
     */
    public String getReceiptNumber()
    {
        return getString("receiptNumber");
    }
    public void setReceiptNumber(String item)
    {
        setString("receiptNumber", item);
    }
    /**
     * Object: �տ 's �ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection getCustomerEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection)get("customerEntrys");
    }
    /**
     * Object:�տ's ��������ϵͳproperty 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getMoneySysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("moneySysType"));
    }
    public void setMoneySysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("moneySysType", item.getValue());
		}
    }
    /**
     * Object: �տ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyContract()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyContract");
    }
    public void setTenancyContract(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyContract", item);
    }
    /**
     * Object:�տ's �տ���ϸ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:�տ's ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum getBillTypeEnum()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum.getEnum(getString("billTypeEnum"));
    }
    public void setBillTypeEnum(com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum item)
    {
		if (item != null) {
        setString("billTypeEnum", item.getValue());
		}
    }
    /**
     * Object:�տ's �տ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum getGatheringObject()
    {
        return com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum.getEnum(getString("gatheringObject"));
    }
    public void setGatheringObject(com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum item)
    {
		if (item != null) {
        setString("gatheringObject", item.getValue());
		}
    }
    /**
     * Object: �տ 's ��ת�� property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getSettlementBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("settlementBill");
    }
    public void setSettlementBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("settlementBill", item);
    }
    /**
     * Object: �տ 's ����������Դ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getTenAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("tenAttach");
    }
    public void setTenAttach(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("tenAttach", item);
    }
    /**
     * Object: �տ 's �����Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSinPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sinPurchase");
    }
    public void setSinPurchase(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sinPurchase", item);
    }
    /**
     * Object: �տ 's ����Ԥ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getSinObligate()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("sinObligate");
    }
    public void setSinObligate(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("sinObligate", item);
    }
    /**
     * Object:�տ's �տ����(��)property 
     */
    public com.kingdee.eas.fdc.sellhouse.GatheringEnum getGathering()
    {
        return com.kingdee.eas.fdc.sellhouse.GatheringEnum.getEnum(getString("gathering"));
    }
    public void setGathering(com.kingdee.eas.fdc.sellhouse.GatheringEnum item)
    {
		if (item != null) {
        setString("gathering", item.getValue());
		}
    }
    /**
     * Object: �տ 's ��Ӧ���Զ��������Ŀ property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getAsstActType()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("asstActType");
    }
    public void setAsstActType(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("asstActType", item);
    }
    /**
     * Object: �տ 's �ֵ���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection getDistillCommisionEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection)get("distillCommisionEntry");
    }
    /**
     * Object:�տ's �Ƿ�������property 
     */
    public boolean isIsBlankOut()
    {
        return getBoolean("isBlankOut");
    }
    public void setIsBlankOut(boolean item)
    {
        setBoolean("isBlankOut", item);
    }
    /**
     * Object: �տ 's ����ԭ���� property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getAdjustSrcBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("adjustSrcBill");
    }
    public void setAdjustSrcBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("adjustSrcBill", item);
    }
    /**
     * Object:�տ's �վ�״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum getReceiptState()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum.getEnum(getString("receiptState"));
    }
    public void setReceiptState(com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum item)
    {
		if (item != null) {
        setString("receiptState", item.getValue());
		}
    }
    /**
     * Object:�տ's ��ӡ����property 
     */
    public long getPrintCount()
    {
        return getLong("printCount");
    }
    public void setPrintCount(long item)
    {
        setLong("printCount", item);
    }
    /**
     * Object:�տ's ��ת����property 
     */
    public com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum getSettleMentType()
    {
        return com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum.getEnum(getString("settleMentType"));
    }
    public void setSettleMentType(com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum item)
    {
		if (item != null) {
        setString("settleMentType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9412AC80");
    }
}