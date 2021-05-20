package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;

public class FDCCostRptFacade extends AbstractBizCtrl implements IFDCCostRptFacade
{
    public FDCCostRptFacade()
    {
        super();
        registerInterface(IFDCCostRptFacade.class, this);
    }
    public FDCCostRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B118BCD5");
    }
    private FDCCostRptFacadeController getController() throws BOSException
    {
        return (FDCCostRptFacadeController)getBizController();
    }
    /**
     *ȡ����Ŀ��Ŀ��ɱ�-User defined method
     *@param orgOrPrjId ��Ŀ����֯��
     *@param period �ڼ�
     *@return
     */
    public AimCostMap getAimCost(String orgOrPrjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCost(getContext(), orgOrPrjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ���Ŀ�����в�Ʒ��Ŀ��ɱ�-User defined method
     *@param prjId ��ĿID
     *@param indexProjectStage ָ��İ汾�׶�
     *@param period period
     *@return
     */
    public ProductAimCostMap getProductAimCost(String prjId, ProjectStageEnum indexProjectStage, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getProductAimCost(getContext(), prjId, indexProjectStage, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ��Ŀ��̬�ɱ�-User defined method
     *@param prjId ��ĿID
     *@param period �ڼ�
     *@return
     */
    public FullDynamicCostMap getFullDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getFullDynamicCost(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ʒ��̬�ɱ�-User defined method
     *@param prjId ��ĿID
     *@param period �ڼ�
     *@return
     */
    public ProductDynamicCostMap getProductDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getProductDynamicCost(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ���Ͷ�̬Ŀ��ɱ��ύ-User defined method
     *@param costEntryMap costEntryMap
     *@return
     */
    public Map submitAimProductCost(Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitAimProductCost(getContext(), costEntryMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ����Ŀ��ɱ�-User defined method
     *@param costEntryMap costEntryMap
     *@return
     */
    public Map submitAIMAimProductCost(Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitAIMAimProductCost(getContext(), costEntryMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ��̬�ɱ�����-User defined method
     *@param dynCostMap dynCostMap
     *@return
     */
    public Map submitDynProductCost(Map dynCostMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitDynProductCost(getContext(), dynCostMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ��Ŀ��̬�ɱ�-User defined method
     *@param projOrgId ��֯����
     *@param period �ڼ�
     *@return
     */
    public FullDynamicCostMap getFullPrjDynamicCost(String projOrgId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getFullPrjDynamicCost(getContext(), projOrgId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ��Ŀ����-User defined method
     *@param prjId ��ĿID
     *@param savedType ��������
     */
    public void saveSnapShot(String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException
    {
        try {
            getController().saveSnapShot(getContext(), prjId, savedType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *0���½ᱣ��-User defined method
     */
    public void autoSaveSnapShot() throws BOSException
    {
        try {
            getController().autoSaveSnapShot(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������������׼��-User defined method
     *@param prjId ��Ŀ��
     *@param period �ڼ�
     *@param isLeaf �Ƿ�Ҷ���
     *@return
     */
    public DynamicCostMap getDynamicCost(String prjId, PeriodInfo period, boolean isLeaf) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCost(getContext(), prjId, period, isLeaf);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ɱ����½�-User defined method
     *@param prjId ��ĿID
     *@param period �ڼ�
     */
    public void reverseCostMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().reverseCostMonthSettled(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����½�-User defined method
     *@param prjId ��ĿId
     *@param period �ڼ�
     */
    public void reverseFinanceMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().reverseFinanceMonthSettled(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����������-User defined method
     *@param prjId ��ĿId
     *@param period �ڼ�
     */
    public void deleteDynSnapShot(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().deleteDynSnapShot(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����Ŀ��Ŀ��ɱ�-User defined method
     *@param prjId ��ĿId
     *@return
     */
    public BigDecimal getAimCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ���̬�ɱ�-User defined method
     *@param prjId ��ĿId
     *@return
     */
    public BigDecimal getDynCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ��ѷ����ɱ�-User defined method
     *@param prjId ��ĿId
     *@return
     */
    public BigDecimal getHappendCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getHappendCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ������������ɱ�-User defined method
     *@param prjId ��ĿID
     *@return
     */
    public BigDecimal getAdjustCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ�������ϸ��Ŀ��Ŀ��ɱ�-User defined method
     *@param prjId ������Ŀ
     *@return
     */
    public Map getCostAccountAimCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountAimCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ϸ��Ŀ�Ķ�̬�ɱ�-User defined method
     *@param prjId ��ĿID
     *@return
     */
    public Map getCostAccountDynCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountDynCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ȡ�ɱ���Ŀ�ķ���-User defined method
     *@param param ����
     *@return
     */
    public Map getCostMap(Map param) throws BOSException
    {
        try {
            return getController().getCostMap(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���¿�����ʵ�ַ�̯����-User defined method
     *@param param ������Ŀid����̯����id�Ȳ���
     */
    public void updatePeriodCostPayedAmt(Map param) throws BOSException, EASBizException
    {
        try {
            getController().updatePeriodCostPayedAmt(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ������r��Ľ����-User defined method
     *@param param ����
     *@return
     */
    public Map getPaymentCostStatistics(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentCostStatistics(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����ϸ������Ŀ������r��Ľ����-User defined method
     *@param param ����
     *@return
     */
    public Map getNonLeafPaymentCostStatistics(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getNonLeafPaymentCostStatistics(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ǰ��֯���й�����Ŀ�ɱ�����-User defined method
     *@param param ����
     *@return
     */
    public Map getProjectAnalysisCost(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectAnalysisCost(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ɱ��½�����ɱ��½�����-User defined method
     *@param isCost true�ɱ��½�,false����ɱ��½�
     *@param param ����
     *@return
     */
    public Map getCostCloseData(boolean isCost, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getCostCloseData(getContext(), isCost, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}