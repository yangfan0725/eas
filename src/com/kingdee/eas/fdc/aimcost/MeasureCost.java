package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class MeasureCost extends FDCBill implements IMeasureCost
{
    public MeasureCost()
    {
        super();
        registerInterface(IMeasureCost.class, this);
    }
    public MeasureCost(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("99193494");
    }
    private MeasureCostController getController() throws BOSException
    {
        return (MeasureCostController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection() throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ģ������-User defined method
     *@param id ģ��ID
     *@param orgId ��֯ID
     *@return
     */
    public String storeFromTemplate(String id, String orgId) throws BOSException, EASBizException
    {
        try {
            return getController().storeFromTemplate(getContext(), id, orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ϊģ��-User defined method
     *@param id id
     *@param isTemplate isTemplate
     */
    public void storeToTmplate(String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            getController().storeToTmplate(getContext(), id, isTemplate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��storeToTmplateͬ-User defined method
     *@param id id
     *@param isTemplate isTemplate
     */
    public void storeToTemplate(String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            getController().storeToTemplate(getContext(), id, isTemplate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ָ��-User defined method
     *@param id id
     */
    public void exportIndex(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().exportIndex(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ŀ��ɱ�-User defined method
     *@param measureId ����ID
     *@param projectId ������Ŀ��
     */
    public void exportAimCost(String measureId, String projectId) throws BOSException, EASBizException
    {
        try {
            getController().exportAimCost(getContext(), measureId, projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ģ��ȡ��-User defined method
     *@param templateId ģ��ID
     *@param orgId ��֯ID
     *@return
     */
    public MeasureCostInfo getMeasureFromTemplate(String templateId, String orgId) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureFromTemplate(getContext(), templateId, orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������Ȼ�ȡ���ݷ���-User defined method
     *@param params �����б�
     *@return
     */
    public Map getImportData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getImportData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������ģ��������-User defined method
     *@return
     */
    public Object getTemplateDataStream() throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateDataStream(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ܱ�ȡ��-User defined method
     *@param params ����
     *@return
     */
    public Map getMeasureRptData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureRptData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��д������Ŀ������Ŀ��ɱ�������Ϊ���հ汾��ʱ��д������Ŀ��ͬʱ���·�¼�ϵĿ�Ŀ��Ϣ-User defined method
     *@param measureId ����
     *@param targetPrjId Ŀ�깤����Ŀ
     */
    public void reverseWriteProject(String measureId, String targetPrjId) throws BOSException, EASBizException
    {
        try {
            getController().reverseWriteProject(getContext(), measureId, targetPrjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���桢�ύʱͬ���������-User defined method
     *@param measureId �ɱ�����Id
     */
    public void costSycMeasureIncome(String measureId) throws BOSException, EASBizException
    {
        try {
            getController().costSycMeasureIncome(getContext(), measureId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ����Ŀ��Ʒ-User defined method
     *@param param param
     */
    public void sysProduct(Map param) throws BOSException, EASBizException
    {
        try {
            getController().sysProduct(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����޶��汾���ύ��������ʱ����Ҫ����ĳ��Ŀ��汾1.0ʱ�ܼƵ������ʣ�eg: �ڶ����޶����Ƿ�Ŀ��ɱ��������ٷ���=��1.2ʵʩ�ɱ��ܶ�-1.0ʵʩ�ɱ��ܶ��1.0ʵʩ�ɱ��ܶ-User defined method
     *@param billid �μӹ�������Ŀ��ɱ����ݵ�ID
     *@param costaccountLongNumber �ɱ���Ŀ�ĳ�����
     *@param projectid ������ĿID
     *@return
     */
    public double getIncreaseRate(String billid, String costaccountLongNumber, String projectid) throws BOSException
    {
        try {
            return getController().getIncreaseRate(getContext(), billid, costaccountLongNumber, projectid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡĿ��ɱ��ܼ�-User defined method
     *@param billId billId
     *@return
     */
    public BigDecimal getTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalCostAccount(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���°汾Ŀ��ɱ��ܼ�-User defined method
     *@param billId billId
     *@return
     */
    public BigDecimal getLastTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getLastTotalCostAccount(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}