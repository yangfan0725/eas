package com.kingdee.eas.fdc.contract;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.EnactmentServiceProxy;
import com.kingdee.bos.workflow.service.IWfDefineService;
import com.kingdee.bos.workflow.service.WfDefineService;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.IPeriod;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.VoucherTypeFactory;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.finance.IProjectPeriodStatus;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusCollection;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;
import com.kingdee.util.db.SQLUtils;

/**
 * ���ز�ϵͳ������
 * @author jinxp
 *
 */
public class FDCUtils {

    /**
     * �������жϵ����Ƿ��ڹ�������
     * ����ʱ�䣺2006-12-27 <p>
     */
    public static boolean isRunningWorkflow(Context ctx, String objId) throws BOSException {
        boolean hasWorkflow = false;
        IWfDefineService service = WfDefineService.getService(ctx);
        String procDefID = service.findSubmitProcDef((BOSUuid.read(objId)).getType(), ContextUtil.getCurrentUserInfo(ctx).getId().toString());
        if (procDefID != null) {
            IEnactmentService service2 = EnactmentServiceProxy.getEnacementService(ctx);
            ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
            for (int i = 0, n = procInsts.length; i < n; i++) {
                if ("open.running".equals(procInsts[i].getState())) {
                    hasWorkflow = true;
                    break;
                }
            }
        }
        return hasWorkflow;
    }
    
    /**
     * �������жϵ����Ƿ��ڹ������� client
     * ����ʱ�䣺2009-1-8 <p>
     */
    public static boolean isRunningWorkflow(String objId) throws BOSException {
        boolean hasWorkflow = false;
        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if ("open.running".equals(procInsts[i].getState())) {
                hasWorkflow = true;
                break;
            }
        }
        return hasWorkflow;
    }
    /**
     * ��÷��ز�ϵͳ����
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getDefaultFDCParam(Context ctx, String companyID) throws BOSException, EASBizException {
        IObjectPK comPK = null; 
        if(companyID!=null){	
        	comPK =  new ObjectUuidPK(companyID);
        }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put(FDCConstants.FDC_PARAM_STARTMG, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTPERSON, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID, comPK);  // Added by Owen_wen 2010-07-28
        //���Ų������ٴι鵵ʱ���º�ͬ���
        hmParamIn.put(FDCConstants.FDC_PARAM_UPDATECONTRACTNO, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCORPORATION, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_ACCOUNTVIEW, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTINDEX, null);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_WFISPASSISFALSENOTPRINT, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_WFIsDuplicateNotPrint, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO, null); //������Ŀ������ʱ���б��Ƿ��տ���˳������
        
        //�����Ƿ���ʾ�ɱ��ϼ���
        hmParamIn.put(FDCConstants.FDC_PARAM_TOTALCOST, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCLUDENOCOSTACCOUNT, null);
        
        //
        hmParamIn.put(FDCConstants.FDC_PARAM_COSTMEASURE, null);
        
        //����ƾ֤��ƾ֤����
        hmParamIn.put(FDCConstants.FDC_PARAM_VOUCHERTYPE, comPK);
        
        //��Ʒ���㵥ʹ�óɱ��ڼ仹�ǲ����ڼ�
        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTORFINANCE, comPK);
        //�������뵥������������ɸ�����
        hmParamIn.put(FDCConstants.FDC_PARAM_OUTPAYAMOUNT, comPK);
        //��Ŀ��ͬǩԼ�ܽ�����Ŀ
        hmParamIn.put(FDCConstants.FDC_PARAM_OUTCOST, comPK);
        //�Զ������
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTROLTYPE, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_ACCTBUDGET, null);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_CONSETTTYPE, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_COSTCENTERCONSTRATE, null);
        
        //��ͬ����ǰ���в��
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITBFAUDIT, comPK);
        //�������뵥����ֱ�Ӹ����޽𣬶�����Ҫ�������
        hmParamIn.put(FDCConstants.FDC_PARAM_KEEPBEFORESETTLEMENT, comPK);
        //δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ����  ���Ų��� by jian_wen 2009.12.15
        hmParamIn.put(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT, comPK);
        //��ͬ����ʱ�Ƿ�Ҫ��������������,�Ǽ��ſ��� by zhiyuan_tang 2010/07/28
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE, comPK);
        
        //�������뵥�տ����к��տ��˺�Ϊ��¼��
        hmParamIn.put(FDCConstants.FDC_PARAM_BANKREQURE, comPK);
        //Ŀ��ɱ���ѯ¼�빦��
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTINPUT, null);
        //�������ϵ��
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
        //Ʒ������
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
        //ʹ���Զ���ָ��
        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
        //Ŀ��ɱ��������¼�Ƿ�����ֱ��¼��
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTADJUSTINPUT,null);
        //����ɾ���������ɱ��ĵ�����¼
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE, null);
        //Ŀ��ɱ�����ʱ������Ʒ��̯�Ƿ񰴼��Ź涨��ָ����з�̯
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREINDEX, null);
        //���뵥���ȿ������Զ�Ϊ100%
        hmParamIn.put(FDCConstants.FDC_PARAM_PAYPROGRESS, comPK);
        //��ִͬ�������ƻ���
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTRACTEXEC, null);
        //�������뵥����ǰ����¼���ͬ����ƻ�
        hmParamIn.put(FDCConstants.FDC_PARAM_CONPAYPLAN, comPK);
        //���ز�����ǿ�Ʋ����н������ϵͳ
        hmParamIn.put(FDCConstants.FDC_PARAM_NOTENTERCAS, comPK);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_VIEWPLAN, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PARTA_MAINCONTRACT, comPK);
        //����������ƾ֤
        hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT, null);
        
        //Ԥ����Ʋ���
        //��ͬ�ƻ�����
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY, comPK);
        //�ɱ���Ŀ����ƻ�����
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY, comPK);
        //�ϸ����
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL, comPK);
        //Ԥ��ϵͳ����
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY, comPK);
        //Ԥ��ϵͳ����ʱ���Ƶ��ɱ�/����������
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT, comPK);
        //��ʾ�ɱ���
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST, comPK);
        //��ͬ�¶ȸ���ƻ��Ƿ��в��ź�ͬ����ƻ�ֱ������
        hmParamIn.put(FDCConstants.FDC_PARAM_DEPCONPAYPLAN, null);
        //����ȫ��Ŀ��ʵ�ֳɱ� by sxhong
        hmParamIn.put(FDCConstants.FDC_PARAM_HIDEREALIZEDCOST, null);
        //��ͬ������Ŀ 2008-12-10
        hmParamIn.put(FDCConstants.FDC_PARAM_CHARGETYPE,null);
        //������ά����ͬ
        hmParamIn.put(FDCConstants.FDC_PARAM_CREATORATTACHMENT,null);
        //��������ʾ���в��
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITAFTERAUDIT,null);
        //����Ŀ��ֵĹ�����ĿҲ�ɽ��к�ͬ���㼰���
        hmParamIn.put(FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT, null);
        //Ŀ��ɱ��޶�����֮���Ӱ�춯̬�ɱ�
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTAUDIT, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_ISOPENPAYMENTEDITUI, null);
        
        /*
         * by Cassiel_peng
         */
    	//����ƻ��������
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_PLANDIF,null);
        //��Ŀ�ƻ�ִ�б��Ƿ���ʾ�����ʵ�ʸ����ֽ���Ӧ������
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_ACTDIF, null);
        //���ز�ҵ��ϵͳ�����������ñʼ����۹���
        hmParamIn.put(FDCConstants.FDC_PARAM_WRITEMARK, null);
        //�Ƿ��������ύ״̬�ĵ���
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITSUBMIT, null);
        //����״̬�ĵ��ݿ����ϴ�����
        hmParamIn.put(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL,comPK);
        //��ͬʵ�ʸ���������ݸ������
//        hmParamIn.put(FDCConstants.FDC_PARAM_BASEONPAYMENTBILL, null);
        //���ñ�����㹤��������
        hmParamIn.put(FDCConstants.FDC_PARAM_CHANGESETTAUDIT, comPK);
        //��Ʊ�����������������ʩ����λ���Ƿ���� 
    	hmParamIn.put(FDCConstants.FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION, null);
    	//�Ƿ����ñ���·�  
    	hmParamIn.put(FDCConstants.FDC_PARAM_ALLOWDISPATCH, null);
    	//���ָ��Ƿ��Զ���Ϊ����ǩ֤��  
    	hmParamIn.put(FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA, null);
        //ָ��Ƿ�������������������������
    	hmParamIn.put(FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT, null);
    	//��������������Ӻ�ͬ������
    	hmParamIn.put(FDCConstants.FDC_PARAM_ADDCONTENTAUDITED, null);
    	//���㵥�ϱ��޽�����Ƿ���6λС��
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE, null);
    	//��Ŀ�¶ȼƻ��������ʾ��ͬ��������������
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC322_DISECOITEMPROCESS, null);
    	// ��ͬ����ʱ�������Ƿ�ȡ��ͬǩ��ʱ�Ļ���
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE, null);
    	// ��ͬ�Ƿ��ϴ����Ŀ��Ƹ������뵥������
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT, comPK);   
    	// ��ͬ�������������󷽿ɱ�����������ã�������ְ����������֡������֡���������֡�
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED, null);   
    	
    	//�ۼƸ������ͬ�������Լ�������ϸ����
    	hmParamIn.put(FDCConstants.FDC_PARAM_ALLPAIDMORETHANCONPRICE, null);
    	//���ø���ͼ�ĵ�������ģʽ
    	hmParamIn.put(FDCConstants.FDC_PARAM_ADJUSTBYGRANT, comPK);
    	//�������Ƿ񰴵�ǰ�û�������֯���й���
    	hmParamIn.put(FDCConstants.FDC_PARAM_FILTERRESPPERSON, comPK);
    	//���񹤳�����Ƿ�ֻ�ܰ��������
    	hmParamIn.put(FDCConstants.FDC_PARAM_FILLBYRESPPERSON, comPK);
    	////�ƻ����š����β��ſ�ѡȫ������֯
    	hmParamIn.put(FDCConstants.FDC_PARAM_CHOOSEALLORG, comPK);
    	//��ͬ�ύ����ʱ�Ƿ������ƻ�������й��� 2010-08-09
    	hmParamIn.put(FDCConstants.FDC_PARAM_RELATEDTASK, comPK);
    	
    	
    	
        //��ͬ�깤������ȡ����ϵͳ�����������
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTFILLBILL,null);
    	
        //��ʵ�ֲ�ֵΪ0ʱ�Ŀ���
        hmParamIn.put(FDCConstants.FDC_PARAM_REALIZEDZEROCTRL,null);
        //��ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���
        hmParamIn.put(FDCConstants.FDC_PARAM_IMPORTCONSPLIT, comPK);
        ////���������������Ƿ�����ʹ�ð��������
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITBASEONPROP, comPK);
        //��ͬδ��֣�����¼�븶�����뵥
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKALLSPLIT,null);
        //�������ƾ֤ʱ��鸶��Ƿ��Ѳ��
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT, comPK);
        //����ύʱ��鸶��Ѿ�������
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT, comPK);
        //�����ֿ�Ŀ�Ľ���ܶ�Ӧ��Ŀ�Ѳ�ֳɱ���������
        hmParamIn.put(FDCConstants.FDC_PARAM_LIMITCOST, comPK);
        
        //�Ƿ��������۲��
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASURESPLIT,null);
        //������Ŀ�ƻ�������Ȩ
        hmParamIn.put(FDCConstants.FDC_PARAM_PLANMANDATE, comPK);
        //�����ͷ���������ͬһ���û�
        hmParamIn.put(FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTSUPPLY, comPK);
        
        //һ�廯����
        hmParamIn.put(FDCConstants.FDC_PARAM_FINACIAL, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEOFFSET, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND, comPK);
        //����һ�廯���óɱ����½�
        hmParamIn.put(FDCConstants.FDC_PARAM_UNINCORPORATION, comPK);
    	//����ɱ�һ�廯����ƾ֤ģʽ
        hmParamIn.put(FDCConstants.FDC_PARAM_ADJUSTVOURCHER, comPK);
        //�������븶�����
    	hmParamIn.put(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT, comPK);
    	//�������ʱ�������Ǹ������뵥�Ĵ����˻���������
    	hmParamIn.put(FDCConstants.FDC_PARAM_PAYMENTCREATOR, comPK);
    	//�������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEREQUIRED, null);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);
    	// �������뵥�����㵥��������ʱ�������ڼ�ֵ
		hmParamIn.put(FDCConstants.FDC_PARAM_RESETPERIOD, comPK);
		// ���гɱ����㼰Ŀ��ɱ�����ʱ���滮ָ����ϣ����е����㳡�����ڡ��̻��õغϼơ���
		hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
		// ���гɱ����㡢Ŀ��ɱ���������Ŀ�����������㣬Ŀ��ɱ����㵼����Ŀ�������ָ�궼ʹ�ò����̯�Ĳ�Ʒ�������֮�ͣ��������ܽ������
		hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
		hmParamIn.put(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT, null);
		hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT, comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER,comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_LIMITSELLAREA, null);
		
		//�ɹ���Ͷ��
    	//�б��ļ��ϳɡ���Ӧ���ʸ�Ԥ��ȷ������ר�ҡ�ȷ������ģ��һ������
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_EXPERT_TEMPLATE, comPK);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_PURCHASEBOMMUSTPARTA, comPK);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER, comPK);
    	
    	//��Ӧ��
    	hmParamIn.put(FDCConstants.FDC_PARAM_SUPPLIER_LIMIT, comPK);
    	
    	
    	//Ͷ�ʲ���
    	hmParamIn.put(FDCConstants.FDC_PARAM_MEASURE_COSTMEASURE, null);
    	
    	//�ɱ���������������Ƿ�����
    	hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
    	
    	// �Ƿ����������뵥�ۼƷ�Ʊ�����ں�ͬ�������
		hmParamIn.put(FDCConstants.FDC_PARAM_OVERRUNCONPRICE, comPK);
//		���ȹ����깤��������ȷ���Ƿ��ϸ����
		hmParamIn.put(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT, comPK);
		hmParamIn.put(FDCConstants.FDCSCH_PARAM_BASEONTASK, comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_OLDCONTRACT_PROGRAM, null);
		
		hmParamIn.put(FDCConstants.FDC_PARAM_OLDCONTRACT_PROGRAM, comPK);
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
      
        
        return hmAllParam;
    }
    
  
    /**
     * �����Է���,ͨ���������ֵMap,����������ȡ����
     * ��:
     * Map paramMap=FDCUtils.getDefaultFDCParam(...);
     * boolean flag=FDCUtils.getParamValue(paramMap,FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST);
     *  by sxhong 2008-09-23 17:03:05
     * @param paramMap
     * @param fdcParamKey
     * @return
     */
    public static boolean getParamValue(Map paramMap,String fdcParamKey){
        Object theValue = paramMap.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * ��÷��ز�ϵͳ����
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getDefaultCashOrGLParam(Context ctx, String companyID) throws BOSException, EASBizException {
        IObjectPK comPK = null; 
        if(companyID!=null){	
        	comPK =  new ObjectUuidPK(companyID);
        }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put("CS050", comPK);
        hmParamIn.put("CS017", comPK);
        hmParamIn.put("CS031", comPK);
        hmParamIn.put("CS034", comPK);
        hmParamIn.put("CS036", comPK);
        hmParamIn.put("FDC302_MODIFY", comPK);
        
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
        
        return hmAllParam;
    }
    
    /**
     * ��÷��ز�ϵͳ����,������ֵΪ��/�����͵Ĳ���
     * 
     * @param ctx
     * @param companyID
     * @param fdcParamKey
     * @return
     */
    public static boolean getDefaultFDCParamByKey(Context ctx, String companyID,String fdcParamKey) throws BOSException, EASBizException {
        HashMap hmAllParam = getDefaultFDCParam(ctx,companyID);
        Object theValue = hmAllParam.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * ��÷��ز�ϵͳ����ֵ, ������ <b>ö��</b> ���͵Ĳ���
     * @param ctx
     * @param companyID ������֯ID
     * @param paramNumber ��������
     * @return fdcParamValue ��String��ʽ���ؽ��
     * @throws EASBizException
     * @throws BOSException
     * @author owen_wen 2010-11-19
     */
    public static String getFDCParamByKey(Context ctx, String companyID,String paramNumber) throws EASBizException, BOSException{
    	String fdcParamValue = "";
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		fdcParamValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(comPK, paramNumber);
		return fdcParamValue;
    }
    
    /**
     * ��÷��ز�ϵͳ����ֵ, ������ <b>����</b> ���͵Ĳ��� 
     * @param ctx
     * @param companyID ������֯ID������Ǽ��Ų���������null���ɡ��Ǽ��Ų�����SysContext.getSysContext().getCurrentOrgUnit().getId().toString()
     * @param paramNumber ��������
     * @return ����ֵ
     * @throws EASBizException
     * @throws BOSException
     * @author owen_wen 2010-11-30
     * @see com.kingdee.eas.fdc.basedata.FDCHelper.getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber)
     */
    public static boolean getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber) throws EASBizException, BOSException{
		return FDCHelper.getBooleanValue4FDCParamByKey(ctx, companyID, paramNumber);
    }
    
    
    //��ȡĳ��������Ŀ��ǰ���ڼ�
    public static PeriodInfo fetchCostPeriod(String projectId, java.util.Date bookedDate) throws Exception{
    	
    	return ProjectPeriodStatusFactory.getRemoteInstance().fetchPeriod(projectId,  bookedDate,true);
    }
    
    //��ȡĳ��������Ŀ��ǰ���ڼ�
    public static PeriodInfo fetchFinacialPeriod(String projectId, java.util.Date bookedDate) throws Exception{
    	
    	return ProjectPeriodStatusFactory.getRemoteInstance().fetchPeriod(projectId,  bookedDate,false);
    }
    
    /**
     * ��ȡ�ڼ� ����ʱ�� 2004-12-2
     * 
     * @param periodTypeId
     * @param time
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    public static PeriodInfo fetchPeriod(Context ctx, String periodTypeId, Date time) throws BOSException, EASBizException {
        EntityViewInfo view = new EntityViewInfo();
        SelectorItemCollection sic = view.getSelector();
        sic.add(new SelectorItemInfo("periodYear"));
        sic.add(new SelectorItemInfo("periodNumber"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("number"));

        FilterInfo filter = new FilterInfo();
        view.setFilter(filter);
        FilterItemCollection fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("periodType.id", periodTypeId));
        fic.add(new FilterItemInfo("beginDate", time, CompareType.LESS_EQUALS));
        fic.add(new FilterItemInfo("endDate", time, CompareType.GREATER_EQUALS));

        SorterItemCollection soter = view.getSorter();
        soter.add(new SorterItemInfo("number"));
        
        IPeriod prdCtrl = null;
        if(ctx!=null){
        	prdCtrl = PeriodFactory.getLocalInstance(ctx);
        }else{
        	prdCtrl = PeriodFactory.getRemoteInstance();
        }

        CoreBaseCollection objs = prdCtrl.getCollection(view);

        if (objs.size() > 0) {
            return (PeriodInfo) objs.getObject(0);
        } else {
            
	        //if(period==null){
        	//throw new ProjectPeriodStatusException(ProjectPeriodStatusException.BOOKEDDATENOTPERIOD);
	        //	throw new PeriodException(PeriodException.COMPANYNOPERIOD);
	        //}
        	return null;
        }
    }
    
    /**
     * ��ǰ������Ŀ�ɱ� �Ƿ񶳽�
     * @param isCost  TrueΪ�ɱ���ǰ�ڼ� FasleΪ����ǰ�ڼ�
     * @throws BOSException 
     * @throws EASBizException 
     */
    static public boolean isFreeze(Context ctx, String projectId,boolean isCost) throws BOSException, EASBizException{
    	boolean isFreeze = false;
		IProjectPeriodStatus iProjectCrrol  = ProjectPeriodStatusFactory.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",projectId));
		filter.getFilterItems().add(new FilterItemInfo(isCost?"isCostFreeze":"isFinaclaFreeze",new Integer(1)));
		
        if (iProjectCrrol.exists(filter)) {
        	isFreeze = true ;
        }
        
    	return isFreeze;
    }
    
    /**
     * ���ݹ�����Ŀ����ڼ�
     * @param ctx
     * @param projectId
     * @param isCost  TrueΪ�ɱ� FasleΪ����
     * @return PeriodInfo
     * @throws BOSException
     * @throws EASBizException
     */
    static public PeriodInfo getCurrentPeriod(Context ctx, String projectId,boolean isCost) throws BOSException, EASBizException {
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        view.setFilter(filter);

        FilterItemCollection fic = filter.getFilterItems();
        //fic.add(new FilterItemInfo("systemStatus.name", new Integer(SystemEnum.FDC.getValue())));
        fic.add(new FilterItemInfo("project.id", projectId));       

        SelectorItemCollection sic = view.getSelector();
	    if(isCost){
	        sic.add(new SelectorItemInfo("costPeriod.periodYear"));
	        sic.add(new SelectorItemInfo("costPeriod.periodNumber"));
	        sic.add(new SelectorItemInfo("costPeriod.beginDate"));
	        sic.add(new SelectorItemInfo("costPeriod.endDate"));
	        sic.add(new SelectorItemInfo("costPeriod.number"));
        }else{

            sic.add(new SelectorItemInfo("finacialPeriod.periodYear"));
            sic.add(new SelectorItemInfo("finacialPeriod.periodNumber"));
            sic.add(new SelectorItemInfo("finacialPeriod.beginDate"));
            sic.add(new SelectorItemInfo("finacialPeriod.endDate"));
            sic.add(new SelectorItemInfo("finacialPeriod.number"));	
        }

        ProjectPeriodStatusCollection col = new ProjectPeriodStatusCollection();
        if(ctx!=null){
        	col = ProjectPeriodStatusFactory.getLocalInstance(ctx).getProjectPeriodStatusCollection(view);
        }else{
        	col = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(view);
        }
        
        PeriodInfo prd = null;
        if (col.size() > 0) {
        	 if(isCost){
        		 prd = col.get(0).getCostPeriod();
        	 }else{
        		 prd = col.get(0).getFinacialPeriod();
        	 }
        }

        if (prd == null) {        	
			sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("isEnabled");
			sic.add("startDate");
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject =null;
			if(ctx!=null){
				curProject =  CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),sic);
			}else{
				curProject= CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId),sic);
			}
    		// ���óɱ�����һ�廯
    		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, curProject.getFullOrgUnit().getId().toString());
    		if(!isIncorporation){
    			CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx,  curProject.getFullOrgUnit().getId().toString(),
    					null, false);
    			prd = FDCUtils.fetchPeriod(ctx, companyInfo
						.getAccountPeriodType().getId().toString(), curProject
						.getStartDate());
    			
    			if(prd==null){
    				prd = PeriodUtils.getPeriodInfo(ctx ,new Date() ,companyInfo);
    			}
			}
        }

        return prd;
    }
    
    /***************************************************************************
	 * ������ȡ--���ݹ�����Ŀ����ڼ� ����ֵ��key=projectId value=PeriondInfo
	 * 
	 * @param ctx
	 * @param projectIds
	 * @param isCost
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	static public Map getCurrentPeriod(Context ctx, Set projectIds,
			boolean isCost) throws BOSException, EASBizException {
		Map periods = new HashMap();
		if (projectIds == null || projectIds.size() == 0)
			return periods;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("project.id", projectIds,
				CompareType.INCLUDE));

		SelectorItemCollection sic = view.getSelector();
		if (isCost) {
			sic.add(new SelectorItemInfo("costPeriod.periodYear"));
			sic.add(new SelectorItemInfo("costPeriod.periodNumber"));
			sic.add(new SelectorItemInfo("costPeriod.beginDate"));
			sic.add(new SelectorItemInfo("costPeriod.endDate"));
			sic.add(new SelectorItemInfo("costPeriod.number"));
		} else {
			sic.add(new SelectorItemInfo("finacialPeriod.periodYear"));
			sic.add(new SelectorItemInfo("finacialPeriod.periodNumber"));
			sic.add(new SelectorItemInfo("finacialPeriod.beginDate"));
			sic.add(new SelectorItemInfo("finacialPeriod.endDate"));
			sic.add(new SelectorItemInfo("finacialPeriod.number"));
		}
		ProjectPeriodStatusCollection col = new ProjectPeriodStatusCollection();
		if (ctx != null) {
			col = ProjectPeriodStatusFactory.getLocalInstance(ctx)
					.getProjectPeriodStatusCollection(view);
		} else {
			col = ProjectPeriodStatusFactory.getRemoteInstance()
					.getProjectPeriodStatusCollection(view);
		}
		for (Iterator it = col.iterator(); it.hasNext();) {
			ProjectPeriodStatusInfo info = (ProjectPeriodStatusInfo) it.next();
			if (isCost)
				periods.put(info.getProject().getId().toString(), info
						.getCostPeriod());
			else
				periods.put(info.getProject().getId().toString(), info
						.getFinacialPeriod());
		}
		for (Iterator it = projectIds.iterator(); it.hasNext();) {
			String projectId = (String) it.next();
			if (!periods.containsKey(projectId)) {
				PeriodInfo prd = null;
				sic = new SelectorItemCollection();
				sic.add("isLeaf");
				sic.add("isEnabled");
				sic.add("startDate");
				sic.add("fullOrgUnit.id");
				CurProjectInfo curProject;
				if (ctx != null)
					curProject = CurProjectFactory
							.getLocalInstance(ctx)
							.getCurProjectInfo(new ObjectUuidPK(projectId), sic);
				else
					curProject = CurProjectFactory
							.getRemoteInstance()
							.getCurProjectInfo(new ObjectUuidPK(projectId), sic);
				// ���óɱ�����һ�廯
				boolean isIncorporation = FDCUtils.IsInCorporation(ctx,
						curProject.getFullOrgUnit().getId().toString());
				if (!isIncorporation) {
					CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(
							ctx,
							curProject.getFullOrgUnit().getId().toString(),
							null, false);
					prd = FDCUtils.fetchPeriod(ctx, companyInfo
							.getAccountPeriodType().getId().toString(),
							curProject.getStartDate());

					if (prd == null) {
						prd = PeriodUtils.getPeriodInfo(ctx, new Date(),
								companyInfo);
					}
				}
				if (prd != null)
					periods.put(projectId, prd);
			}
		}
		return periods;
	}
    /**
     * ��÷��ز�ϵͳ��������ת��Ŀ
     * 
     * @param ctx
     * @param companyID
     * @return AccountViewInfo
     */
    public static AccountViewInfo getDefaultFDCParamAccount(Context ctx, String companyID) throws BOSException, EASBizException {
    	AccountViewInfo account = new AccountViewInfo();
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_ACCOUNTVIEW)!=null){
			String accountId = param.get(FDCConstants.FDC_PARAM_ACCOUNTVIEW).toString();
			if(accountId!=null && accountId.trim().length()>0 &&!accountId.equals("none")){
				if (ctx == null) {
					account = AccountViewFactory.getRemoteInstance()
							.getAccountViewInfo(
									new ObjectUuidPK(BOSUuid.read(accountId)));
				} else {
					account = AccountViewFactory.getLocalInstance(ctx)
							.getAccountViewInfo(
									new ObjectUuidPK(BOSUuid.read(accountId)));
				}
			}
		}
        return account;
    }
    
    /**
     * ��÷��ز�ϵͳ��������ת��Ŀ
     * 
     * @param ctx
     * @param companyID
     * @return AccountViewInfo
     */
    public static VoucherTypeInfo getDefaultFDCParamVoucherType(Context ctx, String companyID) throws BOSException, EASBizException {
    	VoucherTypeInfo voucherType = null;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_VOUCHERTYPE)!=null){
			String typeId = param.get(FDCConstants.FDC_PARAM_VOUCHERTYPE).toString();
			if(!FDCHelper.isEmpty(typeId)&&!typeId.equals("none")){
				if (ctx == null) {
					voucherType = VoucherTypeFactory.getRemoteInstance()
							.getVoucherTypeInfo(
									new ObjectUuidPK(BOSUuid.read(typeId)));
				} else {
					voucherType = VoucherTypeFactory.getLocalInstance(ctx)
							.getVoucherTypeInfo(
									new ObjectUuidPK(BOSUuid.read(typeId)));
				}
			}
		}
        return voucherType;
    }
    
    /**
     * ��÷��ز�ϵͳ�������Ƿ����óɱ��½�
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsInCorporation(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isInCorporation = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_INCORPORATION.toString())!=null){
			isInCorporation = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			
		}
        return isInCorporation;
    }
    
    /**
     * ��÷��ز�ϵͳ�������Ƿ����ø���ģʽ����ɱ�һ�廯
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsFinacial(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isFinacial = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_FINACIAL.toString())!=null){
			isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
		}
        return isFinacial;
    }
    /**
     * ��÷��ز�ϵͳ�������Ƿ����ý���������ƾ֤
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsSettlementCostSplitVoucher(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isSettlementCostSplitVoucher = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT.toString())!=null){
			isSettlementCostSplitVoucher = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT).toString()).booleanValue();
		}
        return isSettlementCostSplitVoucher;
    }
    
    /**
     * ��÷��ز�ϵͳ�����Ĳ�Ʒ���㵥���óɱ��ڼ仹�ǲ����ڼ�
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsUseCostOrFinance(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isUseCostOrFinance = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_USECOSTORFINANCE)!=null){
			isUseCostOrFinance = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_USECOSTORFINANCE).toString()).booleanValue();
		}
        return isUseCostOrFinance;
    }
    
    /**
     * ��ȡ�ɱ�����Id
     * @param ctx
     * @param prjId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    public static String findCostCenterOrgId(Context ctx,String prjId) throws BOSException, EASBizException {
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("parent.id");
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		
		CurProjectInfo project = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		
		CostCenterOrgUnitInfo costCenterOrg = FDCHelper.getCostCenter(project,ctx);
		
		if(costCenterOrg==null){
			return null;
		}
    	
		return costCenterOrg.getId().toString();					
    }
    
    
    private static void lockAssignAccount(Context ctx, String accountType) throws BOSException {
		// ��������
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
	        con = EJBFactory.getConnection(ctx);
	        // ת��ΪTimestamp,ʱ�侫ȷ��¼
	        Timestamp stnow = GlUtils.getTime();
	        int timeCount = 0;
	        //String comId = "00000000-0000-0000-0000-000000000000CCE7AED4";	        	
	        // cimid!=null �Ǿ�ֻ����һ����˾,���Ƿ���T_BAS_Transaction�м�¼,û�в���һ��
	        
	        //2.������һ��ִ�е�ʱ�䣬�Լ���Ҫִ�е�ʱ��t_bas_trasaction
			String selectSql = "select fupdateTime,ftimecount from T_BAS_Transaction where fsystype=37 and fnumber='" + accountType + "' ";
	        // ����ʱ��,����ס������¼
			String updateSql = "update T_BAS_Transaction set fupdateTime=?,ftimecount=? where fsystype=37 and fnumber='" + accountType
					+ "' ";
	        
	        //R110130-015�����ڲ����²�����t_bas_trasaction��������һ���ֶ�FISUPDATING,������Ҫ�ж�����ֶ��Ƿ���� by zhiyuan_tang
			String insertSql = "insert into T_BAS_Transaction (FID,FSYSTYPE,FNUMBER,FUPDATETIME,FTIMECOUNT) values('00000000-0000-0000-0000-000000000000CCE7AED4',37,'"
					+ accountType + "',getDate(),0)";
	        String hasFieldSql = "select 1 from KSQL_USERCOLUMNS where KSQL_COL_NAME ='FISUPDATING' and KSQL_COL_TABNAME='T_BAS_Transaction'";
	        PreparedStatement hasFieldPs = con.prepareStatement(hasFieldSql);
	        ResultSet hasFieldRs = hasFieldPs.executeQuery();
	        if (hasFieldRs != null && hasFieldRs.next()) {
	        	//����FISUPDATING�ֶΣ���insertʱ��Ҫ�����ֶθ�ֵΪ0
				insertSql = "insert into T_BAS_Transaction (FID,FSYSTYPE,FNUMBER,FUPDATETIME,FTIMECOUNT,FISUPDATING) values('00000000-0000-0000-0000-000000000000CCE7AED4',37,'"
						+ accountType + "',getDate(),0,0)";
			}	        
	        String comsql = "and  fid='00000000-0000-0000-0000-000000000000CCE7AED4'";
	        if (true) {
	            selectSql += comsql;
	            ps = con.prepareStatement(selectSql);
	            rs = ps.executeQuery();
	
	            if (rs == null || !rs.next()) {
	                ps = con.prepareStatement(insertSql);
	                ps.execute();
	            } else {
	                timeCount = rs.getInt("ftimecount");
	                if (timeCount > 99999999)
	                    timeCount = 0;
	            }
	            updateSql += comsql;
	        }
	
	        // Update T_BAS_Transaction ��ס��ǰ��˾,ȷ��һ����˾����ִ��
	        ps = con.prepareStatement(updateSql);
	        ps.setTimestamp(1, stnow);
	        ps.setInt(2, timeCount + 1);
	        ps.execute();
	        
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new BOSException(e);
        } finally {
            SQLUtils.cleanup(rs, ps, con);
        }
    }
    
    // ��������
	public static void lockAssignCostAccount(Context ctx) throws BOSException {
		lockAssignAccount(ctx, "assignCostAccount");
	}
    
  //�����Ŀ ��������
    public static void lockAssignIncomeAccount(Context ctx) throws BOSException{
    	lockAssignAccount(ctx, "assignIncomeAccount"); 
    }    
    
	/**
	 * ��ͬ�������:���������
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	public static BigDecimal getContractLastPrice (Context ctx,String contractId,boolean needCon) throws BOSException, SQLException, EASBizException, UuidException{
		BigDecimal lastAmount = FDCHelper.ZERO;
		boolean hasSettled = false;
		IContractBill iContractBill = null;
		IContractChangeBill iContractChangeBill = null;
		if(ctx==null){
			iContractBill = ContractBillFactory.getRemoteInstance();
			iContractChangeBill = ContractChangeBillFactory.getRemoteInstance();
		}else{
			iContractBill = ContractBillFactory.getLocalInstance(ctx);
			iContractChangeBill = ContractChangeBillFactory.getLocalInstance(ctx);
		}
		
		//������ͬ���
		if(needCon){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("amount");
			selector.add("hasSettled");
			selector.add("settleAmt");
			
			ContractBillInfo contract = iContractBill.getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(contractId)),selector);
			
			hasSettled = contract.isHasSettled();
			if (contract.isHasSettled()) {
				lastAmount = contract.getSettleAmt();				
			} else {
				lastAmount = contract.getAmount();
			}
			if(lastAmount==null){
				lastAmount=FDCHelper.ZERO;
			}
		}
		
		//��û�н���
		if(!hasSettled){		
			//���
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
			filter.setMaskString("#0 and (#1 or #2 or #3)");
			view.setFilter(filter);
			view.getSelector().add("amount");
			view.getSelector().add("balanceAmount");
			view.getSelector().add("hasSettled");
			IObjectCollection collection = iContractChangeBill.getContractChangeBillCollection(view);
			ContractChangeBillInfo billInfo;
	
			BigDecimal changeAmount = FDCHelper.ZERO;
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				billInfo = (ContractChangeBillInfo) iter.next();
				if (billInfo.getAmount() != null) {
					if(billInfo.isHasSettled()){
						if(billInfo.getBalanceAmount()!=null) //by tim_gao
						changeAmount = changeAmount.add(billInfo.getBalanceAmount());
					}else{
						changeAmount = changeAmount.add(billInfo.getAmount());
					}
				}
			}
			
			if(changeAmount==null){
				changeAmount =FDCHelper.ZERO;
			}
			
			lastAmount = lastAmount.add(changeAmount);
		}

//		
//		//����
//		BigDecimal guerdonAmt=FDCHelper.ZERO;
//		BigDecimal compenseAmt=FDCHelper.ZERO;
//		BigDecimal deductAmt=FDCHelper.ZERO;
//		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//		builder.appendSql("select sum(famount) as amount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
//		builder.addParam(contractId);
//		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//		IRowSet rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
//		
//		//ΥԼ
//		builder.clear();
//		builder.appendSql("select sum(famount) as amount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
//		builder.addParam(contractId);
//		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//		rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
//		
//		//�ۿ�
//		builder.clear();
//		builder.appendSql("select sum(famount) as amount from T_CON_DeductOfPayReqBill " +
//				"where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");
//		builder.addParam(contractId);
//		rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
		
		/*
		 * �������Ҫ��,δ����ĺ�ͬ�������=��ͬ���+������+����-����,�ۿ�(�ѱ����뵥������) 
		 * by sxhong 2007/09/28
		 */
		//BigDecimal lastAmount = conAmount.add(FDCHelper.toBigDecimal(changeAmount));
//		return  changeAmount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
		
		//�������Ҫ�� 20080801 ȡ������-����,�ۿ�
		
		return  lastAmount;
	}

	/**
	 * 
	 * @param dateType
	 * @param yearFrom
	 * @param yearTo
	 * @param monthFrom
	 * @param MonthTo
	 * @return
	 */
	public static Date getBeginQueryDate(int dateType,int yearFrom,int monthFrom) {
		Date date = null;
		if (dateType == 0) {
			//date = para.getDate(DATE_FROM);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, monthFrom - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, (monthFrom-1) * 3 );
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	
	public static Date getEndQueryDate(int dateType,int yearTo,int monthTo) {
		Date date = null;
		if (dateType == 0) {
			//date = para.getDate(DATE_TO);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,yearTo);
			cal.set(Calendar.MONTH, monthTo);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 		yearTo);
			cal.set(Calendar.MONTH, monthTo* 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearTo+1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	
	/**
	 * ȡ������ͬ��������ۣ���������ȡ������
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BigDecimal getContractLastAmt(Context ctx,String contractId) throws BOSException, EASBizException {
		Map map=(Map)FDCUtils.getLastAmt_Batch(ctx, new String[]{contractId});
		BigDecimal latestPrice=(BigDecimal)map.get(contractId);
		return latestPrice;
	}
	
	public static boolean isIncludeChangeAudit(Context ctx){
		boolean isIncludeChangeAudit = false;
		try {
			if(ctx == null){
				isIncludeChangeAudit = getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT);	
			}else{
				isIncludeChangeAudit = getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT);
			}
			
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isIncludeChangeAudit;
	}

	/**
	 * ����-ȡ��ͬ���±�λ����ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ��ͬ���+������+����-����-�ۿ�(�ѱ����뵥������)��
	 * <p>
	 * ���㷨�����иĶ�����ͬ������ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ ��ͬ���+������ By Owen_wen 2011-07-12
	 * 
	 * @param String
	 *            []
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getLastAmt_Batch(Context ctx,String[] contractIdList) throws BOSException, EASBizException {
		if(contractIdList==null || contractIdList.length==0){
			return new HashMap();
		}
		BigDecimal bdZero = FDCNumberHelper.ZERO;
		String noSettleContractIdList =  null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount from T_CON_ContractBill where ");
			builder.appendParam("fid", contractIdList, "varchar(44)");

			IRowSet rs = builder.executeQuery();
			if (rs == null || rs.size() == 0) {
				return new HashMap();
			}
			Map lastAmtMap = new HashMap(rs.size());
			try {
				while (rs.next()) {
					String contractId = rs.getString("FID");
					BigDecimal contractAmount = FDCNumberHelper.ZERO;
					if (rs.getBoolean("FHasSettled")) {
						contractAmount = rs.getBigDecimal("FSettleAmt");
					} else {
						if (noSettleContractIdList == null) {
							noSettleContractIdList = contractId;
						} else {
							noSettleContractIdList = noSettleContractIdList + "," + contractId;
						}
						contractAmount = rs.getBigDecimal("FAmount");
					}
					lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
				}
				/*
				 * �������Ҫ��,δ����ĺ�ͬ�������=��ͬ���+������+����-����,�ۿ�(�ѱ����뵥������) by sxhong 2007/09/28
				 */
				if (noSettleContractIdList != null) {
					String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
					// ���

					if (isIncludeChangeAudit(ctx)) {
						builder.clear();
						builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
						builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
						builder.appendSql("where FHasSettled=1 and ");
						builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
						builder.appendSql(" and (");
						builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
						builder.appendSql(" ) union all ");
						builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
						builder.appendSql("where FHasSettled=0 and ");
						builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
						builder.appendSql(" and (");
						builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
						builder.appendSql(" )) change group by FContractBillID");
						rs = builder.executeQuery();
						while (rs.next()) {
							String contractId = rs.getString("FContractBillID");
							BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
							if (lastAmtMap.containsKey(contractId) && changeAmount != null) {
								lastAmtMap.put(contractId, ((BigDecimal) lastAmtMap.get(contractId)).add(changeAmount));
							}
						}
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			return lastAmtMap;
		} finally {
			builder.releasTempTables();
		}
	}

	/**
	 * ����-ȡ��ͬ����ԭ����ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ��ͬ���+������+����-����-�ۿ�(�ѱ����뵥������)��
	 * <p>
	 * ���㷨�����иĶ�����ͬ������ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ ��ͬ���+������ By Owen_wen 2011-07-12
	 * 
	 * @param String
	 *            []
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getLastOriginalAmt_Batch(Context ctx,String[] contractIdList) throws Exception {
		if(contractIdList==null || contractIdList.length==0){
			return new HashMap();
		}
		BigDecimal bdZero = FDCNumberHelper.ZERO;
		String noSettleContractIdList =  null;		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			builder
					.appendSql("select fcontractbillid,FTotalOriginalAmount as FTotalOriginalAmount from t_con_contractsettlementbill where FIsFinalSettle=1 and ");
			builder.appendParam("fcontractbillid", contractIdList, "varchar(44)");

			IRowSet rs = builder.executeQuery();
			Map lastAmtMapSettlement = new HashMap();
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					lastAmtMapSettlement.put(rs.getString("fcontractbillid"), rs.getBigDecimal("FTotalOriginalAmount"));
				}
			}

			builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FID,FHasSettled,FSettleAmt,FOriginalAmount from T_CON_ContractBill where ");
			builder.appendParam("fid", contractIdList, "varchar(44)");
			rs = builder.executeQuery();
			if (rs == null || rs.size() == 0) {
				return new HashMap();
			}
			Map lastAmtMap = new HashMap(rs.size());
			while (rs.next()) {
				String contractId = rs.getString("FID");
				BigDecimal contractAmount = FDCNumberHelper.ZERO;
				if (lastAmtMapSettlement.containsKey(contractId)) {
					contractAmount = (BigDecimal) lastAmtMapSettlement.get(contractId);
				} else {
					if (noSettleContractIdList == null) {
						noSettleContractIdList = contractId;
					} else {
						noSettleContractIdList = noSettleContractIdList + "," + contractId;
					}
					contractAmount = rs.getBigDecimal("FOriginalAmount");
				}
				lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
			}
			/*
			 * �������Ҫ��,δ����ĺ�ͬ�������=��ͬ���+������+����-����,�ۿ�(�ѱ����뵥������) by sxhong 2007/09/28
			 */
			if (noSettleContractIdList != null) {
				if (isIncludeChangeAudit(ctx)) {
					String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
					// ���
					builder.clear();
					builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
					// ��Ϊȡԭ��FBalanceAmount--��FOriBalanceAmount
					builder.appendSql("select FContractBillID,FOriBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
					builder.appendSql("where FHasSettled=1 and ");
					builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
					builder.appendSql(" and (");
					builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
					builder.appendSql(" ) union all ");
					// ��Ϊȡԭ��FAmount --> FOriginalAmount
					builder.appendSql("select FContractBillID,FOriginalAmount as fchangeAmount from T_CON_ContractChangeBill ");
					builder.appendSql("where FHasSettled=0 and ");
					builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
					builder.appendSql(" and (");
					builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
					builder.appendSql(" )) change group by FContractBillID");

					rs = builder.executeQuery();
					while (rs.next()) {
						String contractId = rs.getString("FContractBillID");
						BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
						if (lastAmtMap.containsKey(contractId) && changeAmount != null) {
							lastAmtMap.put(contractId, ((BigDecimal) lastAmtMap.get(contractId)).add(changeAmount));
						}
					}
				}
			}
			return lastAmtMap;
		} finally {
			builder.releasTempTables();
		}
	}
	
	/***
	 * �ݹ��ҵ��ɱ���Ŀ������Ӧ�Ļ�ƿ�Ŀ
	 * @param key
	 * @param costAccountInfo
	 * @param costAccountMap
	 * @param costAccountWithAccountMap
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static Object getCostAccountWithAccount(String key,CostAccountInfo costAccountInfo,Map costAccountMap,Map costAccountWithAccountMap) throws BOSException, EASBizException{
		if(costAccountInfo.getParent()==null)
			return null;
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getParent().getId().toString();
		if(costAccountWithAccountMap.containsKey(key))
			return costAccountWithAccountMap.get(key);
		else{
			if(costAccountMap.containsKey(costAccountInfo.getParent().getId().toString())){
				costAccountInfo = (CostAccountInfo)costAccountMap.get(costAccountInfo.getParent().getId().toString());
				return getCostAccountWithAccount(key,costAccountInfo,costAccountMap,costAccountWithAccountMap);
			}
			else
				throw new BOSException();//can't arrive
		}
			
	}
	private static Object getCostAccountWithAccount(String key,CostAccountInfo costAccountInfo,Map costAccountMap,Map costAccountWithAccountMap,boolean keyIsCostAccountLongNumber) throws BOSException, EASBizException{
		if(costAccountInfo.getParent()==null)
			return null;
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getParent().getId().toString();
		String key2 = key;
		if(keyIsCostAccountLongNumber)
			key2 = costAccountInfo.getCurProject().getId().toString()+ "_" + costAccountInfo.getLongNumber();
		if(costAccountWithAccountMap.containsKey(key2))
			return costAccountWithAccountMap.get(key2);
		else{
			if(costAccountMap.containsKey(costAccountInfo.getParent().getId().toString())){
				costAccountInfo = (CostAccountInfo)costAccountMap.get(costAccountInfo.getParent().getId().toString());
				return getCostAccountWithAccount(key2,costAccountInfo,costAccountMap,costAccountWithAccountMap,keyIsCostAccountLongNumber);
			}
			else
				throw new BOSException();//can't arrive
		}
			
	}
	/**
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountMap(Context ctx, Set curProjectIds) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * ȡ������Ŀ�����еĳɱ���Ŀ
		 */
		Map costAccountMap = new HashMap();
		ICostAccount iCostAccount = null;
		if(ctx==null)
			iCostAccount = CostAccountFactory.getRemoteInstance();
		else
			iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("level");
		view.getSelector().add("parent.id");
		//view.getSelector().add("parent.name");
		view.getSelector().add("parent.level");
		view.getSelector().add("curProject.id");
		//view.getSelector().add("curProject.name");
		view.getFilter().getFilterItems().clear();
		//����curProjectIds��Map.keySet()��ã��޷����л����ᵼ��NotSerializableException������ת��ΪHashSet(�����л�)
		curProjectIds = new HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountCollection costAccountColl = iCostAccount.getCostAccountCollection(view);
		/****
		 * 
		 */
//		if(costAccountColl==null||costAccountColl.size()==0)
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNT);
		for(it=costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
			if(costAccountInfo.getId().toString().equals("959+ZAEdEADgABR3wKgQs4Qj/24=")){
				System.out.print("959+ZAEdEADgABR3wKgQs4Qj/24=");
			}
			costAccountMap.put(costAccountInfo.getId().toString(),costAccountInfo);
		}
		return costAccountMap;
	}
	/**
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountWithAccountMap(Context ctx, Set curProjectIds) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * ȡ�ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
		 */
		Map costAccountWithAccountMap = new HashMap();
		ICostAccountWithAccount iCostAccountWithAccount = null;
		if(ctx==null)
			iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		else
			iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		
		view.getSelector().add("costAccount.id");
		view.getSelector().add("costAccount.longnumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("costAccount.level");
		view.getSelector().add("costAccount.parent.*");
		view.getSelector().add("costAccount.curProject.id");
		view.getSelector().add("costAccount.curProject.name");	
		view.getSelector().add("costAccount.curProject.longnumber");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.id");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		view.getSelector().add("account.id");
		view.getSelector().add("account.number");
		view.getSelector().add("account.name"); 
		//���������ͳ�Ʊ��530ͬ����600�������   by ling_peng 2009-6-24
		view.getSelector().add("account.longnumber");
		view.getSelector().add("account.level");
		view.getSelector().add("account.isLeaf");
		
		view.getSelector().add("account.DC");
		view.getFilter().getFilterItems().clear();
		//�ӵ��÷������ݹ�����curProjectIds����Map.keySet()������ã��޷����л�������ת����
		curProjectIds=new  HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountWithAccountCollection costAccountWithAccountColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(view);
		if(costAccountWithAccountColl==null||costAccountWithAccountColl.size()==0)
			throw new ContractException(
					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		for(it=costAccountWithAccountColl.iterator();it.hasNext();){
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			String key = info.getCostAccount().getCurProject().getId().toString();
			key += "_" + info.getCostAccount().getId().toString();
			costAccountWithAccountMap.put(key,info);
		}
		return costAccountWithAccountMap;
	}
	/***
	 * 
	 * @param ctx
	 * @param curProjectIds
	 * @param keyIsCostAccountLongNumber
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountWithAccountMap(Context ctx, Set curProjectIds,boolean keyIsCostAccountLongNumber) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * ȡ�ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
		 */
		Map costAccountWithAccountMap = new HashMap();
		ICostAccountWithAccount iCostAccountWithAccount = null;
		if(ctx==null)
			iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		else
			iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		
		view.getSelector().add("costAccount.id");
		view.getSelector().add("costAccount.longnumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("costAccount.level");
		view.getSelector().add("costAccount.parent.*");
		view.getSelector().add("costAccount.curProject.id");
		view.getSelector().add("costAccount.curProject.name");	
		view.getSelector().add("costAccount.curProject.longnumber");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.id");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		view.getSelector().add("account.id");
		view.getSelector().add("account.number");
		view.getSelector().add("account.name"); 
		//���������ͳ�Ʊ��530ͬ����600�������   by ling_peng 2009-6-24
		view.getSelector().add("account.longnumber");
		view.getSelector().add("account.level");
		view.getSelector().add("account.isLeaf");
		
		view.getSelector().add("account.DC");
		view.getFilter().getFilterItems().clear();
		//�ӵ��÷������ݹ�����curProjectIds����Map.keySet()������ã��޷����л�������ת����
		curProjectIds=new  HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountWithAccountCollection costAccountWithAccountColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(view);
		if(costAccountWithAccountColl==null||costAccountWithAccountColl.size()==0)
			throw new ContractException(
					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		for(it=costAccountWithAccountColl.iterator();it.hasNext();){
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			String key = info.getCostAccount().getCurProject().getId().toString();
			if(keyIsCostAccountLongNumber)
				key += "_" + info.getCostAccount().getLongNumber();
			else
				key += "_" + info.getCostAccount().getId().toString();
			costAccountWithAccountMap.put(key,info);
		}
		return costAccountWithAccountMap;
	}
	/**
	 * ��ȡ������Ŀ�µ����гɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ��map
	 * key   = curProjectIds_costAccountIds
	 * value = AccountViewInfo
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 * @throws EASBizException
	 * @deprecated
	 * @see com.kingdee.eas.fdc.basedata.util.CostAccountWithAccountHelper.getCostAcctWithAcctMapByCostAccountIds
	 */
	public static Map getCostAccountWithAccountMapAll(Context ctx, Set curProjectIds,Set costAccountIds) throws BOSException, ContractException, EASBizException {
		return getCostAccountWithAccountMapAll(ctx,curProjectIds,false);
	}
	/***
	 * 
	 * @param ctx
	 * @param curProjectIds
	 * @param keyIsCostAccountLongNumber
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 * @throws EASBizException
	 */
	public static Map getCostAccountWithAccountMapAll(Context ctx, Set curProjectIds,boolean keyIsCostAccountLongNumber) throws BOSException, ContractException, EASBizException {
		if(curProjectIds==null||curProjectIds.size()==0)
			return new HashMap();
		Iterator it;
		Map costAccountWithAccountMap = getCostAccountWithAccountMap(ctx, curProjectIds,keyIsCostAccountLongNumber);
		Map costAccountMap = getCostAccountMap(ctx, curProjectIds);
		/***
		 * ����ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
		 * ���磺	������Ŀ1
		 * 	1		�ɱ���Ŀ1       -------   ��ƿ�Ŀ1
		 * 	2		 |+��ϸ�ɱ���Ŀ1 -------   |+��ϸ��ƿ�Ŀ1
		 * 	3		 |+��ϸ�ɱ���Ŀ2 -------   ��û�ж�Ӧ��
		 * Ŀǰ  
		 * 	costAccountWithAccountMap ������1��2
		 * 	costAccountMap ������3��ϸ�ɱ���Ŀ2 
		 * 
		 * ��Ҫ��
		 *       ��ϸ�ɱ���Ŀ2 --------- ��ƿ�Ŀ1
		 *       ����costAccountWithAccountMap
		 *       
		 * ����costAccountWithAccountMap�Ľ��
		 * 	1		�ɱ���Ŀ1       -------   ��ƿ�Ŀ1
		 * 	2		 |+��ϸ�ɱ���Ŀ1 -------   |+��ϸ��ƿ�Ŀ1
		 * 	3		 |+��ϸ�ɱ���Ŀ2 -------   |+��ƿ�Ŀ1
		 */
		Set costAccountKeys = costAccountMap.keySet();
		
		for(it=costAccountKeys.iterator();it.hasNext();){
			String key = (String) it.next();
			CostAccountInfo costAccountInfo = (CostAccountInfo)costAccountMap.get(key);
			key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getId().toString();
			String key2 = key;
			if(keyIsCostAccountLongNumber)
				key2 = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getLongNumber();
			if(!costAccountWithAccountMap.containsKey(key2)){
				costAccountWithAccountMap.put(key2,getCostAccountWithAccount(key2,costAccountInfo,costAccountMap,costAccountWithAccountMap,keyIsCostAccountLongNumber));
			}
		}
		return costAccountWithAccountMap;
	}
	
    /* �Ѿ����õ���ƾ֤ģʽ�����ڿ��Ʋ���ɱ�һ�廯ʱ�õ���ƾ֤��
    * @param ctx
    * @return
    * @throws EASBizException
    * @throws BOSException
    */
   public static boolean isAdjustVourcherModel(Context ctx,String companyId) throws EASBizException, BOSException{
   	return getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
   }
   /**
    * ��÷��ز�ϵͳ�������Ƿ����ü�ģʽ����ɱ�һ�廯
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsSimpleFinacial(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isFinacial = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL.toString())!=null){
			isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue();
		}
       return isFinacial;
   }
   /**
    * ��÷��ز�ϵͳ�������Ƿ����ü�ģʽ����ɱ�һ�廯����ۿΥԼ������
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsSimpleFinacialExtend(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isFinacialExtend = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND.toString())!=null){
			isFinacialExtend = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND).toString()).booleanValue();
		}
       return isFinacialExtend;
   }
   
   /**
    * ��÷��ز�ϵͳ�������Ƿ����óɱ����½�
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsUnInCorporation(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isUnInCorporation = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);
   	if(param.get(FDCConstants.FDC_PARAM_UNINCORPORATION.toString())!=null){
   		isUnInCorporation = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_UNINCORPORATION).toString()).booleanValue();
   	}
   	return isUnInCorporation;
   }
   
   /**
    * ��ͬ�����ı����Ƿ���붯̬�ɱ�
    * @param ctx
    * @param contractID
    * @return
    * @throws BOSException
    * @throws EASBizException
    * @throws SQLException 
    */
   public static boolean isCostSplit(Context ctx, String contractID) throws BOSException,EASBizException, SQLException{
	   boolean isCostSplit = false;
	   if(contractID==null)
		   return isCostSplit;
	   FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	   String table = isContractBill(ctx, contractID)?"t_con_contractBill":"t_con_contractwithouttext";
	   builder.appendSql("select fiscostsplit from "+table+" where fid =? ");
	   builder.addParam(contractID);
	   IRowSet rowSet = builder.executeQuery();
	   if(rowSet.size()==1){
		   rowSet.next();
		   isCostSplit = rowSet.getBoolean("fiscostsplit");
	   }
	   return isCostSplit;
   }
   
   /**
    * �ж��Ǻ�ͬ�����ı���ͬ
    * @param ctx
    * @param contractID
    * @return true��ͬ,false���ı���ͬ
    * @throws BOSException
    * @throws EASBizException
    */
   public static boolean isContractBill(Context ctx, String contractID)
			throws BOSException, EASBizException {
		return  contractID==null?false:(new ContractBillInfo().getBOSType()).equals(BOSUuid.read(contractID).getType());
   }
   
   /**
	 * ��ǰ�û�Ȩ����֯�µĹ�����Ŀ
	 * @author pengwei_hou date:2009-07-18
	 * @param ctx
	 * @param orgUnitIDSet
	 * @return map
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Map getOrgUnitProjects(Context ctx, String objectID)
			throws BOSException, EASBizException {
		BOSObjectType CurProjectType = (new CurProjectInfo()).getBOSType();
		BOSObjectType FullOrgUnitType = (new FullOrgUnitInfo()).getBOSType();
		BOSObjectType ObjectType = BOSUuid.read(objectID).getType();
		Set idSet = new HashSet();

		Map retMap = new HashMap();
		ICurProject iCurProject = null;
		CurProjectCollection colls = null;
		if (ctx == null) {
			iCurProject = CurProjectFactory.getRemoteInstance();
		} else {
			iCurProject = CurProjectFactory.getLocalInstance(ctx);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
		SorterItemInfo sort = new SorterItemInfo("longNumber");
		sort.setSortType(SortType.ASCEND);
		view.getSorter().add(sort);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", new Integer(1)));
		if (CurProjectType.equals(ObjectType)) {
			filter.getFilterItems().add(new FilterItemInfo("id", objectID));
		} else if (FullOrgUnitType.equals(ObjectType)) {
			if(OrgConstants.DEF_CU_ID.equals(objectID)){
				//����,��ǰ�û���Ȩ����֯��Ӧ�Ĺ�����Ŀ
				idSet = getAuthorizedOrgs(ctx);
				//�ɱ������´��ڶ༶������Ŀ���
				filter.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.id", idSet,
								CompareType.INCLUDE));
				filter.getFilterItems().add(
						new FilterItemInfo("costCenter.id", idSet,
								CompareType.INCLUDE));
				filter.setMaskString(" #0 and #1 and (#2 or #3) ");
			} else{
				// ȡ��֯���¼�������֯
				idSet = getCompanyOrgUnitIDSet(ctx, objectID);
				if (idSet.size() == 1) {
					//�ɱ�����
					filter.getFilterItems().add(
							new FilterItemInfo("costCenter.id", idSet,
									CompareType.INCLUDE));
				} else {
					//�ɱ������´��ڶ༶������Ŀ���
					filter.getFilterItems().add(
							new FilterItemInfo("fullOrgUnit.id", idSet,
									CompareType.INCLUDE));
					filter.getFilterItems().add(
							new FilterItemInfo("costCenter.id", idSet,
									CompareType.INCLUDE));
					filter.setMaskString(" #0 and #1 and (#2 or #3) ");
				}
			}
		} 
		colls = iCurProject.getCurProjectCollection(view);
		if (colls != null) {
			for (Iterator iter = colls.iterator(); iter.hasNext();) {
				CurProjectInfo info = (CurProjectInfo) iter.next();
				retMap.put(info.getId().toString(), info);
			}
		}
		retMap.put("colls", colls);
		return retMap;
	}
	
	/**
	 * �����¼����в�����֯
	 * @param ctx
	 * @param objctID ����һ��֯ID
	 * @return ���ظ���֯�¼����в�����֯
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private static Set getCompanyOrgUnitIDSet(Context ctx, String objectID)
			throws BOSException, EASBizException {
		Set leafIDSet = new HashSet();
		leafIDSet.add(objectID);
		IFullOrgUnit iFullOrgUnit = null;
		FullOrgUnitCollection colls = null;
		if (ctx == null) {
			iFullOrgUnit = FullOrgUnitFactory.getRemoteInstance();
		} else {
			iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("*"));
		//TODO
		//ֻ��Ҫid,parent.idΪʲôselector���Բ���?
		
//		view.getSelector().add("id");
//		view.getSelector().add("parent.id");
//		filter.getFilterItems().add(
//				new FilterItemInfo("isFreeze", Boolean.valueOf(false)));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isCompanyOrgUnit", Boolean.valueOf(true)));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isCostOrgUnit", Boolean.valueOf(true)));
		colls = iFullOrgUnit.getFullOrgUnitCollection(view);
		Map companyMap = new HashMap();
		if (colls != null) {
			for (int i=0;i<colls.size();i++) {
				FullOrgUnitInfo info = (FullOrgUnitInfo)colls.get(i);
				companyMap.put(info.getId().toString(), info);
			}
			companyMap.remove(objectID);
			return getCompanyIDSet(companyMap,leafIDSet,objectID);
		}
		return new HashSet();
	}
	
	private static Set getCompanyIDSet(Map companyMap, Set leafIDSet,String orgUnitID){
		for (Iterator iter = companyMap.keySet().iterator(); iter.hasNext();) {
			FullOrgUnitInfo info = (FullOrgUnitInfo)companyMap.get(iter.next());
			if(info!=null){
				if(info.getParent()!=null&&leafIDSet.contains(info.getParent().getId().toString())){
					leafIDSet.add(info.getId().toString());
					companyMap.remove(info.getId().toString());
					return getCompanyIDSet(companyMap,leafIDSet,info.getId().toString());
				} else{
					
				}
			}
		}
		return leafIDSet;
	}
	
	/**
	 * �û�Ȩ����֯
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	public static Set getAuthorizedOrgs(Context ctx) throws BOSException,
			EASBizException {
		IPermission iPermission = null;
		BOSUuid userID = null;
		if (ctx == null) {
			iPermission = PermissionFactory.getRemoteInstance();
			userID = SysContext
			.getSysContext().getCurrentUserInfo().getId();
		} else {
			iPermission = PermissionFactory.getLocalInstance(ctx);
			userID = ContextUtil.getCurrentUserInfo(ctx).getId();
		}
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		/*if (orgs == null) {
			orgs = iPermission.getAuthorizedOrgs(new ObjectUuidPK(userID),
					OrgType.CostCenter, null, null, null);
		}*/
		//update by renliang
		orgs = iPermission.getAuthorizedOrgs(new ObjectUuidPK(userID),
				OrgType.CostCenter, null, null, null);
		
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}
		return authorizedOrgs;
	}
	
	public static boolean isBillAudited(FDCBillInfo info) {
		return info != null && FDCBillStateEnum.AUDITTED == info.getState();
	}

	public static String buildBillIds(Collection ids) {
		if (ids == null || ids.isEmpty()) {
			throw new IllegalArgumentException("param ids is empty");
		}
		StringBuffer idsSB = new StringBuffer("(");
		for (Iterator it = ids.iterator(); it.hasNext();) {
			idsSB.append("'").append(it.next().toString()).append("',");
		}
		idsSB.setLength(idsSB.length() - 1);
		return idsSB.append(")").toString();
	}
}
