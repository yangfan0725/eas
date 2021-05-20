package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteProjectInfo()
    {
        this("id");
    }
    protected AbstractInviteProjectInfo(String pkField)
    {
        super(pkField);
        put("authorizedEnlistPerson", new com.kingdee.eas.fdc.invite.AuthorizedPersonCollection());
        put("inviteListEntry", new com.kingdee.eas.fdc.invite.IPInviteListTypeEntryCollection());
        put("supplierEntry", new com.kingdee.eas.fdc.invite.InviteSupplierEntryCollection());
        put("entries", new com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection());
    }
    /**
     * Object: �б����� 's ����ƻ� property 
     */
    public com.kingdee.eas.fdc.invite.InvitePlanInfo getInvitePlan()
    {
        return (com.kingdee.eas.fdc.invite.InvitePlanInfo)get("invitePlan");
    }
    public void setInvitePlan(com.kingdee.eas.fdc.invite.InvitePlanInfo item)
    {
        put("invitePlan", item);
    }
    /**
     * Object: �б����� 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: �б����� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object:�б�����'s ��ϵ�绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:�б�����'s �����ܼ�property 
     */
    public java.math.BigDecimal getInputedAmount()
    {
        return getBigDecimal("inputedAmount");
    }
    public void setInputedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("inputedAmount", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public java.util.Date getPrjDate()
    {
        return getDate("prjDate");
    }
    public void setPrjDate(java.util.Date item)
    {
        setDate("prjDate", item);
    }
    /**
     * Object:�б�����'s ��Ӧ���ʸ��������property 
     */
    public java.util.Date getSupQuaAduitDate()
    {
        return getDate("supQuaAduitDate");
    }
    public void setSupQuaAduitDate(java.util.Date item)
    {
        setDate("supQuaAduitDate", item);
    }
    /**
     * Object:�б�����'s ��Ӧ���ʸ���˽�������property 
     */
    public java.util.Date getSupQuaAduitEndDate()
    {
        return getDate("supQuaAduitEndDate");
    }
    public void setSupQuaAduitEndDate(java.util.Date item)
    {
        setDate("supQuaAduitEndDate", item);
    }
    /**
     * Object:�б�����'s �������嵥����property 
     */
    public java.util.Date getPrjAmountListDate()
    {
        return getDate("prjAmountListDate");
    }
    public void setPrjAmountListDate(java.util.Date item)
    {
        setDate("prjAmountListDate", item);
    }
    /**
     * Object:�б�����'s �������嵥��������property 
     */
    public java.util.Date getPrjAmountListEndDate()
    {
        return getDate("prjAmountListEndDate");
    }
    public void setPrjAmountListEndDate(java.util.Date item)
    {
        setDate("prjAmountListEndDate", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public java.util.Date getTechBluePrintDate()
    {
        return getDate("techBluePrintDate");
    }
    public void setTechBluePrintDate(java.util.Date item)
    {
        setDate("techBluePrintDate", item);
    }
    /**
     * Object:�б�����'s ���������������property 
     */
    public java.util.Date getTechBluePrintEndDate()
    {
        return getDate("techBluePrintEndDate");
    }
    public void setTechBluePrintEndDate(java.util.Date item)
    {
        setDate("techBluePrintEndDate", item);
    }
    /**
     * Object:�б�����'s �б��ļ��ϳ�����property 
     */
    public java.util.Date getInviteFileMergeDate()
    {
        return getDate("inviteFileMergeDate");
    }
    public void setInviteFileMergeDate(java.util.Date item)
    {
        setDate("inviteFileMergeDate", item);
    }
    /**
     * Object:�б�����'s �б��ļ��ϳɽ�������property 
     */
    public java.util.Date getInviteFileMergeEndDate()
    {
        return getDate("inviteFileMergeEndDate");
    }
    public void setInviteFileMergeEndDate(java.util.Date item)
    {
        setDate("inviteFileMergeEndDate", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public java.util.Date getReleaseInviteDate()
    {
        return getDate("releaseInviteDate");
    }
    public void setReleaseInviteDate(java.util.Date item)
    {
        setDate("releaseInviteDate", item);
    }
    /**
     * Object:�б�����'s �����������property 
     */
    public java.util.Date getReleaseInviteEndDate()
    {
        return getDate("releaseInviteEndDate");
    }
    public void setReleaseInviteEndDate(java.util.Date item)
    {
        setDate("releaseInviteEndDate", item);
    }
    /**
     * Object:�б�����'s �ر��������property 
     */
    public java.util.Date getPreTenderDate()
    {
        return getDate("preTenderDate");
    }
    public void setPreTenderDate(java.util.Date item)
    {
        setDate("preTenderDate", item);
    }
    /**
     * Object:�б�����'s �ر������������property 
     */
    public java.util.Date getPreTenderEndDate()
    {
        return getDate("preTenderEndDate");
    }
    public void setPreTenderEndDate(java.util.Date item)
    {
        setDate("preTenderEndDate", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public java.util.Date getShowInviteDate()
    {
        return getDate("showInviteDate");
    }
    public void setShowInviteDate(java.util.Date item)
    {
        setDate("showInviteDate", item);
    }
    /**
     * Object:�б�����'s �����������property 
     */
    public java.util.Date getShowInviteEndDate()
    {
        return getDate("showInviteEndDate");
    }
    public void setShowInviteEndDate(java.util.Date item)
    {
        setDate("showInviteEndDate", item);
    }
    /**
     * Object:�б�����'s ��������������property 
     */
    public java.util.Date getTechInviteAppDate()
    {
        return getDate("techInviteAppDate");
    }
    public void setTechInviteAppDate(java.util.Date item)
    {
        setDate("techInviteAppDate", item);
    }
    /**
     * Object:�б�����'s ������������������property 
     */
    public java.util.Date getTechInviteAppEndDate()
    {
        return getDate("techInviteAppEndDate");
    }
    public void setTechInviteAppEndDate(java.util.Date item)
    {
        setDate("techInviteAppEndDate", item);
    }
    /**
     * Object:�б�����'s ���ñ���������property 
     */
    public java.util.Date getEconInviteDate()
    {
        return getDate("econInviteDate");
    }
    public void setEconInviteDate(java.util.Date item)
    {
        setDate("econInviteDate", item);
    }
    /**
     * Object:�б�����'s ���ñ�������������property 
     */
    public java.util.Date getEconInviteEndDate()
    {
        return getDate("econInviteEndDate");
    }
    public void setEconInviteEndDate(java.util.Date item)
    {
        setDate("econInviteEndDate", item);
    }
    /**
     * Object:�б�����'s �ۺ����걨������property 
     */
    public java.util.Date getSynAppInviteDate()
    {
        return getDate("synAppInviteDate");
    }
    public void setSynAppInviteDate(java.util.Date item)
    {
        setDate("synAppInviteDate", item);
    }
    /**
     * Object:�б�����'s �ۺ����걨���������property 
     */
    public java.util.Date getSynAppInviteEndDate()
    {
        return getDate("synAppInviteEndDate");
    }
    public void setSynAppInviteEndDate(java.util.Date item)
    {
        setDate("synAppInviteEndDate", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public java.util.Date getFixInviteDate()
    {
        return getDate("fixInviteDate");
    }
    public void setFixInviteDate(java.util.Date item)
    {
        setDate("fixInviteDate", item);
    }
    /**
     * Object:�б�����'s �����������property 
     */
    public java.util.Date getFixInviteEndDate()
    {
        return getDate("fixInviteEndDate");
    }
    public void setFixInviteEndDate(java.util.Date item)
    {
        setDate("fixInviteEndDate", item);
    }
    /**
     * Object:�б�����'s ͼֽĿ¼property 
     */
    public java.util.Date getBluePrintDate()
    {
        return getDate("bluePrintDate");
    }
    public void setBluePrintDate(java.util.Date item)
    {
        setDate("bluePrintDate", item);
    }
    /**
     * Object:�б�����'s ͼֽĿ¼����ʱ��property 
     */
    public java.util.Date getBluePrintEndDate()
    {
        return getDate("bluePrintEndDate");
    }
    public void setBluePrintEndDate(java.util.Date item)
    {
        setDate("bluePrintEndDate", item);
    }
    /**
     * Object:�б�����'s ������/Ͷ����֪property 
     */
    public java.util.Date getInviteBookDate()
    {
        return getDate("inviteBookDate");
    }
    public void setInviteBookDate(java.util.Date item)
    {
        setDate("inviteBookDate", item);
    }
    /**
     * Object:�б�����'s ������/Ͷ����֪����ʱ��property 
     */
    public java.util.Date getInviteBookEndDate()
    {
        return getDate("inviteBookEndDate");
    }
    public void setInviteBookEndDate(java.util.Date item)
    {
        setDate("inviteBookEndDate", item);
    }
    /**
     * Object:�б�����'s ��ͬ����property 
     */
    public java.util.Date getBargainTermDate()
    {
        return getDate("bargainTermDate");
    }
    public void setBargainTermDate(java.util.Date item)
    {
        setDate("bargainTermDate", item);
    }
    /**
     * Object:�б�����'s ��ͬ�����������property 
     */
    public java.util.Date getBargainTermEndDate()
    {
        return getDate("bargainTermEndDate");
    }
    public void setBargainTermEndDate(java.util.Date item)
    {
        setDate("bargainTermEndDate", item);
    }
    /**
     * Object: �б����� 's ����ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateInfo getAppraiseTemplate()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateInfo)get("appraiseTemplate");
    }
    public void setAppraiseTemplate(com.kingdee.eas.fdc.invite.AppraiseTemplateInfo item)
    {
        put("appraiseTemplate", item);
    }
    /**
     * Object: �б����� 's �б��ļ����Ʋ����� property 
     */
    public com.kingdee.eas.fdc.invite.AuthorizedPersonCollection getAuthorizedEnlistPerson()
    {
        return (com.kingdee.eas.fdc.invite.AuthorizedPersonCollection)get("authorizedEnlistPerson");
    }
    /**
     * Object:�б�����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectStateEnum getInviteProjectState()
    {
        return com.kingdee.eas.fdc.invite.InviteProjectStateEnum.getEnum(getString("inviteProjectState"));
    }
    public void setInviteProjectState(com.kingdee.eas.fdc.invite.InviteProjectStateEnum item)
    {
		if (item != null) {
        setString("inviteProjectState", item.getValue());
		}
    }
    /**
     * Object:�б�����'s �Ƿ���ר������property 
     */
    public boolean isIsExpertAppraise()
    {
        return getBoolean("isExpertAppraise");
    }
    public void setIsExpertAppraise(boolean item)
    {
        setBoolean("isExpertAppraise", item);
    }
    /**
     * Object: �б����� 's ������ property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�б�����'s �Ƿ���ϸ����property 
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
     * Object:�б�����'s �����property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectSegmentEnum getSegments()
    {
        return com.kingdee.eas.fdc.invite.InviteProjectSegmentEnum.getEnum(getInt("segments"));
    }
    public void setSegments(com.kingdee.eas.fdc.invite.InviteProjectSegmentEnum item)
    {
		if (item != null) {
        setInt("segments", item.getValue());
		}
    }
    /**
     * Object:�б�����'s �Ƿ�����б�property 
     */
    public boolean isIsRate()
    {
        return getBoolean("isRate");
    }
    public void setIsRate(boolean item)
    {
        setBoolean("isRate", item);
    }
    /**
     * Object:�б�����'s �Ƿ�����豸�б�property 
     */
    public boolean isIsMaterial()
    {
        return getBoolean("isMaterial");
    }
    public void setIsMaterial(boolean item)
    {
        setBoolean("isMaterial", item);
    }
    /**
     * Object:�б�����'s �ƻ��б�ʱ��property 
     */
    public java.util.Date getInviteDate()
    {
        return getDate("inviteDate");
    }
    public void setInviteDate(java.util.Date item)
    {
        setDate("inviteDate", item);
    }
    /**
     * Object:�б�����'s �ƻ��б�ص�property 
     */
    public String getInviteLoc()
    {
        return getString("inviteLoc");
    }
    public void setInviteLoc(String item)
    {
        setString("inviteLoc", item);
    }
    /**
     * Object: �б����� 's ��ܺ�Լ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object:�б�����'s ��ܺ�ԼIDproperty 
     */
    public String getSrcProID()
    {
        return getString("srcProID");
    }
    public void setSrcProID(String item)
    {
        setString("srcProID", item);
    }
    /**
     * Object:�б�����'s �Ƽ۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.invite.PricingModeEnum getPriceMode()
    {
        return com.kingdee.eas.fdc.invite.PricingModeEnum.getEnum(getString("PriceMode"));
    }
    public void setPriceMode(com.kingdee.eas.fdc.invite.PricingModeEnum item)
    {
		if (item != null) {
        setString("PriceMode", item.getValue());
		}
    }
    /**
     * Object:�б�����'s �ƻ�����ʱ��property 
     */
    public java.util.Date getSendInviteDate()
    {
        return getDate("sendInviteDate");
    }
    public void setSendInviteDate(java.util.Date item)
    {
        setDate("sendInviteDate", item);
    }
    /**
     * Object:�б�����'s �ƻ�����ʱ��property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    /**
     * Object:�б�����'s �ܹ���property 
     */
    public java.math.BigDecimal getAllDays()
    {
        return getBigDecimal("allDays");
    }
    public void setAllDays(java.math.BigDecimal item)
    {
        setBigDecimal("allDays", item);
    }
    /**
     * Object:�б�����'s ͼֽ�Ƿ����ƣ����ֻر꣩property 
     */
    public boolean isPaperIsComplete()
    {
        return getBoolean("paperIsComplete");
    }
    public void setPaperIsComplete(boolean item)
    {
        setBoolean("paperIsComplete", item);
    }
    /**
     * Object:�б�����'s �������property 
     */
    public boolean isScalingRules()
    {
        return getBoolean("scalingRules");
    }
    public void setScalingRules(boolean item)
    {
        setBoolean("scalingRules", item);
    }
    /**
     * Object: �б����� 's �ɹ���ʽ property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getInviteForm()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("inviteForm");
    }
    public void setInviteForm(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("inviteForm", item);
    }
    /**
     * Object: �б����� 's �ɹ�ģʽ property 
     */
    public com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo getInvitePurchaseMode()
    {
        return (com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo)get("invitePurchaseMode");
    }
    public void setInvitePurchaseMode(com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo item)
    {
        put("invitePurchaseMode", item);
    }
    /**
     * Object: �б����� 's ������� property 
     */
    public com.kingdee.eas.fdc.invite.ScalingRuleInfo getScalingRule()
    {
        return (com.kingdee.eas.fdc.invite.ScalingRuleInfo)get("scalingRule");
    }
    public void setScalingRule(com.kingdee.eas.fdc.invite.ScalingRuleInfo item)
    {
        put("scalingRule", item);
    }
    /**
     * Object: �б����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection getEntries()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection)get("entries");
    }
    /**
     * Object:�б�����'s �ɹ�����property 
     */
    public com.kingdee.eas.fdc.invite.ProcurementType getProcurementType()
    {
        return com.kingdee.eas.fdc.invite.ProcurementType.getEnum(getString("procurementType"));
    }
    public void setProcurementType(com.kingdee.eas.fdc.invite.ProcurementType item)
    {
		if (item != null) {
        setString("procurementType", item.getValue());
		}
    }
    /**
     * Object:�б�����'s �ɹ���Ȩproperty 
     */
    public com.kingdee.eas.fdc.invite.InvitePurchaseAuthorization getAuthorization()
    {
        return com.kingdee.eas.fdc.invite.InvitePurchaseAuthorization.getEnum(getString("authorization"));
    }
    public void setAuthorization(com.kingdee.eas.fdc.invite.InvitePurchaseAuthorization item)
    {
		if (item != null) {
        setString("authorization", item.getValue());
		}
    }
    /**
     * Object:�б�����'s ������Ŀ����property 
     */
    public String getCurProjectName()
    {
        return getString("curProjectName");
    }
    public void setCurProjectName(String item)
    {
        setString("curProjectName", item);
    }
    /**
     * Object: �б����� 's pricingApproach property 
     */
    public com.kingdee.eas.custom.purchasebaseinfomation.PricingApproachInfo getPricingApproach()
    {
        return (com.kingdee.eas.custom.purchasebaseinfomation.PricingApproachInfo)get("pricingApproach");
    }
    public void setPricingApproach(com.kingdee.eas.custom.purchasebaseinfomation.PricingApproachInfo item)
    {
        put("pricingApproach", item);
    }
    /**
     * Object: �б����� 's �ɹ�Ȩ�� property 
     */
    public com.kingdee.eas.custom.purchasebaseinfomation.PurchaseAuthorInfo getPurchaseAuthority()
    {
        return (com.kingdee.eas.custom.purchasebaseinfomation.PurchaseAuthorInfo)get("purchaseAuthority");
    }
    public void setPurchaseAuthority(com.kingdee.eas.custom.purchasebaseinfomation.PurchaseAuthorInfo item)
    {
        put("purchaseAuthority", item);
    }
    /**
     * Object: �б����� 's �ɹ����� property 
     */
    public com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo getPurchaseCategory()
    {
        return (com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo)get("purchaseCategory");
    }
    public void setPurchaseCategory(com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo item)
    {
        put("purchaseCategory", item);
    }
    /**
     * Object: �б����� 's ����Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.InviteSupplierEntryCollection getSupplierEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteSupplierEntryCollection)get("supplierEntry");
    }
    /**
     * Object:�б�����'s reason1property 
     */
    public boolean isReason1()
    {
        return getBoolean("reason1");
    }
    public void setReason1(boolean item)
    {
        setBoolean("reason1", item);
    }
    /**
     * Object:�б�����'s reason2property 
     */
    public boolean isReason2()
    {
        return getBoolean("reason2");
    }
    public void setReason2(boolean item)
    {
        setBoolean("reason2", item);
    }
    /**
     * Object:�б�����'s reason3property 
     */
    public boolean isReason3()
    {
        return getBoolean("reason3");
    }
    public void setReason3(boolean item)
    {
        setBoolean("reason3", item);
    }
    /**
     * Object:�б�����'s reason4property 
     */
    public boolean isReason4()
    {
        return getBoolean("reason4");
    }
    public void setReason4(boolean item)
    {
        setBoolean("reason4", item);
    }
    /**
     * Object:�б�����'s reason5property 
     */
    public boolean isReason5()
    {
        return getBoolean("reason5");
    }
    public void setReason5(boolean item)
    {
        setBoolean("reason5", item);
    }
    /**
     * Object:�б�����'s reason6property 
     */
    public boolean isReason6()
    {
        return getBoolean("reason6");
    }
    public void setReason6(boolean item)
    {
        setBoolean("reason6", item);
    }
    /**
     * Object:�б�����'s reason7property 
     */
    public boolean isReason7()
    {
        return getBoolean("reason7");
    }
    public void setReason7(boolean item)
    {
        setBoolean("reason7", item);
    }
    /**
     * Object:�б�����'s reason8property 
     */
    public boolean isReason8()
    {
        return getBoolean("reason8");
    }
    public void setReason8(boolean item)
    {
        setBoolean("reason8", item);
    }
    /**
     * Object:�б�����'s reason9property 
     */
    public boolean isReason9()
    {
        return getBoolean("reason9");
    }
    public void setReason9(boolean item)
    {
        setBoolean("reason9", item);
    }
    /**
     * Object:�б�����'s reason10property 
     */
    public boolean isReason10()
    {
        return getBoolean("reason10");
    }
    public void setReason10(boolean item)
    {
        setBoolean("reason10", item);
    }
    /**
     * Object:�б�����'s ��������property 
     */
    public String getOtherReason()
    {
        return getString("otherReason");
    }
    public void setOtherReason(String item)
    {
        setString("otherReason", item);
    }
    /**
     * Object:�б�����'s ��עproperty 
     */
    public String getTxtReason1()
    {
        return getString("txtReason1");
    }
    public void setTxtReason1(String item)
    {
        setString("txtReason1", item);
    }
    /**
     * Object:�б�����'s Ͷ���쳣���property 
     */
    public String getTxtReason2()
    {
        return getString("txtReason2");
    }
    public void setTxtReason2(String item)
    {
        setString("txtReason2", item);
    }
    /**
     * Object: �б����� 's �ɹ���ϸ property 
     */
    public com.kingdee.eas.fdc.invite.IPInviteListTypeEntryCollection getInviteListEntry()
    {
        return (com.kingdee.eas.fdc.invite.IPInviteListTypeEntryCollection)get("inviteListEntry");
    }
    /**
     * Object:�б�����'s Ԥ�Ʋɹ����property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("planAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount", item);
    }
    /**
     * Object: �б����� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:�б�����'s �����б깫��property 
     */
    public boolean isPublishInvitation()
    {
        return getBoolean("publishInvitation");
    }
    public void setPublishInvitation(boolean item)
    {
        setBoolean("publishInvitation", item);
    }
    /**
     * Object:�б�����'s �����б���Ϣproperty 
     */
    public boolean isPublishWinInfo()
    {
        return getBoolean("publishWinInfo");
    }
    public void setPublishWinInfo(boolean item)
    {
        setBoolean("publishWinInfo", item);
    }
    /**
     * Object:�б�����'s �Ƿ��б�property 
     */
    public boolean isIsHit()
    {
        return getBoolean("isHit");
    }
    public void setIsHit(boolean item)
    {
        setBoolean("isHit", item);
    }
    /**
     * Object:�б�����'s �б깩Ӧ��property 
     */
    public String getWinSupplier()
    {
        return getString("winSupplier");
    }
    public void setWinSupplier(String item)
    {
        setString("winSupplier", item);
    }
    /**
     * Object:�б�����'s �б굥����property 
     */
    public String getWinBillNo()
    {
        return getString("winBillNo");
    }
    public void setWinBillNo(String item)
    {
        setString("winBillNo", item);
    }
    /**
     * Object:�б�����'s �б굥 IDproperty 
     */
    public String getAccepterBillID()
    {
        return getString("accepterBillID");
    }
    public void setAccepterBillID(String item)
    {
        setString("accepterBillID", item);
    }
    /**
     * Object: �б����� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo getTender()
    {
        return (com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo)get("tender");
    }
    public void setTender(com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo item)
    {
        put("tender", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4BEE1F2C");
    }
}