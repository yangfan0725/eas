/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OUPartFIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.AccountBalanceCollection;
import com.kingdee.eas.fi.gl.AccountBalanceFactory;
import com.kingdee.eas.fi.gl.AccountBalanceInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CostAccountWithAccountBalanceUI extends AbstractCostAccountWithAccountBalanceUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountWithAccountBalanceUI.class);
    protected CommonQueryDialog queryDlg;
    CostAccountWithAccountBalanceFilterUI myPanel;
    
    private boolean isFirstDefaultQuery = true;
    
    private Date[] pkdate ;
    
    public PeriodInfo curPeriod = null;
    
    private boolean isInvoiceMgr = false;
    
    /**
     * output class constructor
     */
    public CostAccountWithAccountBalanceUI() throws Exception
    {
        super();
        pkdate = FDCClientHelper.getCompanyCurrentDate();
    }
    
    protected void execQuery() {
        return;
    }
    protected void refresh(ActionEvent e) throws Exception
    {
        //CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
        //execQuery();
    	return;
    }
    protected boolean isAllowDefaultSolutionNull() {
		return true;
	}
    protected boolean initDefaultFilter() {
		return false;
	}
	
	protected boolean isIgnoreCUFilter()
    {
        return true;
    }	

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
   
	protected String getEditUIName() {
		// TODO �Զ����ɷ������
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO �Զ����ɷ������
		return null;
	}
	public void onLoad() throws Exception{
		//ֻ��ʵ�������֯�ſ��Բ鿴
		FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		if ((!org.isIsCompanyOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}else{
			String id = org.getPartFI().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id));
			filter.getFilterItems().add(new FilterItemInfo("isBizUnit",new Integer(1)));
			if(!OUPartFIFactory.getRemoteInstance().exists(filter)){
				MsgBox.showWarning(this, PaymentBillClientUtils.getRes("notBizUnitFI"));
				SysUtil.abort();
			}
		}
        Date curDate = pkdate[0];
		CompanyOrgUnitInfo companyInfo = CompanyOrgUnitFactory.getRemoteInstance()
			.getCompanyOrgUnitInfo(new ObjectUuidPK(SysContext.getSysContext().getCurrentFIUnit().getId()),GlUtils.getCompanySic());
		curPeriod = FDCUtils.fetchPeriod(null, companyInfo.getAccountPeriodType().getId().toString(),pkdate[0] );
		
		if(curPeriod==null){
			curPeriod = PeriodUtils.getPeriodInfo(null ,new Date() ,companyInfo);
		}
		isInvoiceMgr = FDCUtils.getDefaultFDCParamByKey(null, SysContext
				.getSysContext().getCurrentFIUnit().getId().toString(),
				FDCConstants.FDC_PARAM_INVOICEMRG);
		
		myPanel = new CostAccountWithAccountBalanceFilterUI();
		queryDlg = new CommonQueryDialog();
		queryDlg.setOwner(this);
		queryDlg.setParentUIClassName(this.getClass().getName());
		queryDlg.setShowFilter(false);
        queryDlg.setShowSorter(false);
        queryDlg.setShowToolbar(true);
        queryDlg.setHeight(380);
        queryDlg.setWidth(420);
        queryDlg.setTitle(myPanel.getUITitle());
        queryDlg.addUserPanel(myPanel);
        queryDlg.setQueryObjectPK(mainQueryPK);
        queryDlg.init();
        queryDlg.getCommonQueryParam().setDirty(false);
        btnQuery.setVisible(false);
        super.onLoad();
        btnQuery.setVisible(true);
        btnQuery.setEnabled(true);
        if(!this.showQueryDlg()){
			SysUtil.abort();
		}
        setQueryPreference(true);
        this.actionAddNew.setEnabled(false);
        this.actionAddNew.setVisible(false);
        this.actionEdit.setEnabled(false);
        this.actionEdit.setVisible(false);
        this.actionAttachment.setVisible(false);
        this.actionAttachment.setEnabled(false);
        this.actionRemove.setEnabled(false);
        this.actionRemove.setVisible(false);
        this.menuEdit.setVisible(false);
        this.actionLocate.setEnabled(false);
        this.actionLocate.setVisible(false);
        
        FDCHelper.formatTableNumber(tblMain, new String[] {
				"costAccount.amount", "accountView.amount",
				"invoiceAccount.name", "diffentAmt" }, FDCHelper.strDataFormat);
	}
	public boolean showQueryDlg() throws Exception {
		
		IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory.getRemoteInstance();
		String queryName = (getQueryInfo(mainQueryPK)).getFullName();
		EntityViewInfo ev = null;
		if (isFirstDefaultQuery && !isPerformDefaultQuery(iQuery, queryName)){
			ev = (EntityViewInfo) iQuery.getDefaultFilterInfo(this.getClass().getName(), queryName);
			if (ev != null && ev.getFilter() != null){
				isFirstDefaultQuery = false;
				return showQueryDlg(ev);
			}
		}
		
		if(queryDlg.show()){
			isFirstDefaultQuery = false;
            ev = queryDlg.getEntityViewInfoResult();
            return showQueryDlg(ev);
		}
		else
			return false;
    }
	public boolean showQueryDlg(EntityViewInfo ev) throws EASBizException, BOSException, Exception{
		
		if(fillData(ev)){
			return true;
		}
		else{
			if(MsgBox.YES == MsgBox.showConfirm2(this,"û�з�������������!")){
				return showQueryDlg();
			}
			else{
				return false;
			}
		}
	}
	public boolean verify(Integer periodYear,Integer periodNumber) {

		String msg = "��ǰ������֯���ڼ仹δ������ĩ���ˣ����ܲ鿴���ʱ�";
		if(curPeriod.getPeriodYear() > periodYear.intValue())
			return true;
		else if(curPeriod.getPeriodYear() == periodYear.intValue()&&
				curPeriod.getPeriodNumber() > periodNumber.intValue())
			return true;
		else{
	  	    //R101104-183 �½�ǰ���ɽ��в���ɱ��������ݺ˶�
//			MsgBox.showInfo(this,msg);
//			SysUtil.abort();
		}	
		return true;
	}
	
	public boolean fillData(EntityViewInfo view) throws BOSException, EASBizException{
		FilterInfo periodFilter = view.getFilter();
		Integer periodYear = null;
		Integer periodNumber = null;
		String costID = null;
		Set project = null;
		for(Iterator it = periodFilter.getFilterItems().iterator();it.hasNext();)
		{
			FilterItemInfo filterItem = (FilterItemInfo) it.next();
			if(filterItem.getPropertyName().equals(CostAccountWithAccountBalanceFilterUI.YEAR))
			{
				periodYear = (Integer)filterItem.getCompareValue();
			}else if(filterItem.getPropertyName().equals(CostAccountWithAccountBalanceFilterUI.MONTH)){
				periodNumber = (Integer)filterItem.getCompareValue();
			}else if(CostAccountWithAccountBalanceFilterUI.PROJECT.equals(filterItem.getPropertyName())){
				//�������������ȡ����
				Set prjIds = (Set) filterItem.getCompareValue();
				if (project != null) {
					Set tmp = new HashSet();
					for (Iterator it2 = project.iterator(); it2.hasNext();) {
						String id = (String) it2.next();
						if(!prjIds.contains(prjIds)){
							tmp.add(id);
						}
					}
					project = tmp;
				} else {
					project = prjIds;
				}
			}else if(CostAccountWithAccountBalanceFilterUI.COSTCENTER.equals(filterItem.getPropertyName())){
				costID = (String) filterItem.getCompareValue();
			}
		}
		if(periodYear==null||periodNumber==null){
			throw new BOSException("û��ѡ�񹤳���Ŀ�ڼ�");
		}
		verify(periodYear,periodNumber);
		this.kDLabel1.setText("��ѡ�ڼ䣺"+periodYear+"��"+periodNumber+"��");
		KDTable table = this.getMainTable();
		table.checkParsed();
		table.removeRows();
		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		/**** 
		 * ��ʱ��д�ڿͻ��ˣ��õ���
		 * ��ͷ�������ĵ���������
		 * 1.��ȡ��ǰ������֯�µ����й�����Ŀ
		 * 2.���ݹ�����Ŀ��óɱ���Ŀ���ƿ�Ŀ���ձ�
		 * 3.���ݹ�����Ŀid+�ɱ���Ŀid+�ڼ䣬��ȡ�ɱ���Ŀ���
		 * 
		 * 4.���ݻ�ƿ�Ŀid+�ڼ�+�����+��ǰ������֯+��ƿ�Ŀ�Ľ�����򣬻�ȡ��ƿ�Ŀ�����
		 * 5.����Map
		 */
		Map dataMap = new HashMap();
		Map curProjectMap = new HashMap();
//		Map costAccountMap = new HashMap();
		Map accountViewMap = new HashMap();
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set curProjectIds = new HashSet();
		viewInfo.setFilter(filter);
		if(project == null){
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",orgUnitId));
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("name");
			viewInfo.getSelector().add("longnumber");
			viewInfo.getSelector().add("fullOrgUnit.id");
			viewInfo.getSelector().add("fullOrgUnit.name");			
			
			CurProjectCollection curProjects = CurProjectFactory.getRemoteInstance().getCurProjectCollection(viewInfo);
			if(curProjects==null||curProjects.size()==0)
				return false;
			for(Iterator it = curProjects.iterator();it.hasNext();){
				CurProjectInfo curProject = (CurProjectInfo) it.next();
				curProjectMap.put(curProject.getId().toString(),curProject);
			}
			//HashMap.keySet()�޷����л���������ת��һ�¡�
			curProjectIds = new HashSet(curProjectMap.keySet());
		}else{
			curProjectIds.addAll(project);
		}
		//��ù�����Ŀ�µĳɱ���Ŀ
		Map costAccountMap = FDCUtils.getCostAccountMap(null,curProjectIds);
		Map costAccountWithAccountMap = FDCUtils.getCostAccountWithAccountMapAll(null,curProjectIds,null);
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		
		viewInfo.getSelector().clear();
		viewInfo.getSelector().add("costAccount.id");
		viewInfo.getSelector().add("costAccount.longnumber");
		viewInfo.getSelector().add("costAccount.name");
		viewInfo.getSelector().add("costAccount.isLeaf");
		viewInfo.getSelector().add("costAccount.level");
		viewInfo.getSelector().add("costAccount.parent.*");
		viewInfo.getSelector().add("costAccount.curProject.id");
		viewInfo.getSelector().add("costAccount.curProject.name");	
		viewInfo.getSelector().add("costAccount.curProject.longnumber");
		viewInfo.getSelector().add("costAccount.curProject.fullOrgUnit.id");
		viewInfo.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		viewInfo.getSelector().add("invoiceAccount.id");
		viewInfo.getSelector().add("invoiceAccount.name");
		viewInfo.getSelector().add("invoiceAccount.number");
		viewInfo.getSelector().add("invoiceAccount.longNumber");
		viewInfo.getSelector().add("account.id");
		viewInfo.getSelector().add("account.number");
		viewInfo.getSelector().add("account.name");
		viewInfo.getSelector().add("account.DC");
		if(viewInfo.getFilter() != null && viewInfo.getFilter().getFilterItems() != null){
			viewInfo.getFilter().getFilterItems().clear();			
		}
		//�ӵ��÷������ݹ�����curProjectIds����Map.keySet()������ã��޷����л�������ת����
		curProjectIds=new  HashSet(curProjectIds);
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.curProject.id",curProjectIds,CompareType.INCLUDE));
		SorterItemInfo sorterItemInfo = new SorterItemInfo();
		sorterItemInfo.setSortType(SortType.ASCEND);
		sorterItemInfo.setPropertyName("account.longNumber");
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo();
		sorterItemInfo.setSortType(SortType.ASCEND);
		sorterItemInfo.setPropertyName("costAccount.curProject.name");
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo();
		sorterItemInfo.setSortType(SortType.ASCEND);
		sorterItemInfo.setPropertyName("costAccount.longNumber");
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo();
		sorterItemInfo.setSortType(SortType.ASCEND);
		sorterItemInfo.setPropertyName("invoiceAccount.longNumber");
		viewInfo.getSorter().add(sorterItemInfo);
//		sorterItemInfo = new SorterItemInfo();
//		sorterItemInfo.setSortType(SortType.ASCEND);
//		sorterItemInfo.setPropertyName("account.id");
//		viewInfo.getSorter().add(sorterItemInfo);
//		sorterItemInfo = new SorterItemInfo();
//		sorterItemInfo.setSortType(SortType.ASCEND);
//		sorterItemInfo.setPropertyName("costAccount.curProject.id");
//		viewInfo.getSorter().add(sorterItemInfo);
		CostAccountWithAccountCollection costAccountWithAccountColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(viewInfo);
		if(costAccountWithAccountColl==null||costAccountWithAccountColl.size()==0)
			throw new ContractException(
					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		Map costViewMap = new HashMap();
		for(Iterator it=costAccountWithAccountColl.iterator();it.hasNext();){
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			String key = info.getCostAccount().getCurProject().getId().toString();
			key += "_" + info.getCostAccount().getId().toString();
			costViewMap.put(key, info.getAccount().getId().toString());
			//costAccountWithAccountMap.put(key,info);
			//costAccountMap.put(info.getCostAccount().getId().toString(),info.getCostAccount());
			accountViewMap.put(info.getAccount().getId().toString(),info.getAccount());
			if(isInvoiceMgr){
				if(info.getInvoiceAccount()==null){
					continue;
				}
				key = info.getCostAccount().getCurProject().getId().toString() + "_" + info.getInvoiceAccount().getId().toString();
				costViewMap.put(key,info.getInvoiceAccount().getId().toString());
				accountViewMap.put(info.getInvoiceAccount().getId().toString(), info.getInvoiceAccount());
			}
		}
		
		viewInfo.getFilter().getFilterItems().clear();
		viewInfo.getSorter().clear();
		viewInfo.getSelector().clear();
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("costPayedAmt");
		viewInfo.getSelector().add("costAccountId");
		viewInfo.getSelector().add("projectId");		
		//viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("costAccountId",costAccountMap.keySet(),CompareType.INCLUDE));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("projectId",curProjectIds,CompareType.INCLUDE));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("period.periodYear",periodYear,CompareType.EQUALS));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("period.periodNumber",periodNumber,CompareType.EQUALS));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("savedType",CostMonthlySaveTypeEnum.FINANCEMONTHLY_VALUE,CompareType.EQUALS));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("costPayedAmt",FDCHelper.ZERO,CompareType.GREATER));
		DynCostSnapShotCollection dynCostSnapShots = DynCostSnapShotFactory.getRemoteInstance().getDynCostSnapShotCollection(viewInfo);
		/**
		 * ���ɱ���Ŀ������Ŀ����ϸ��Ӧʱ��ԭ���߼��޷�����ϼ���Ŀ�ۼ����ݣ������ 2009-06-11
		 */
		//�ԡ�������Ŀid��+���ɱ���Ŀ�����롱Ϊkey�������ʵ������
		/**
		 * �޸Ļ� �ԡ�������Ŀid��+���ɱ���Ŀid��Ϊkey
		 */
		Map costPayedMap = new HashMap();
		for(Iterator it = dynCostSnapShots.iterator();it.hasNext();){
			DynCostSnapShotInfo dynCostSnapShot = (DynCostSnapShotInfo) it.next();
			String key = dynCostSnapShot.getProjectId().toString();
			String costAccountId =  dynCostSnapShot.getCostAccountId().toString();
			CostAccountInfo costAccountInfo = (CostAccountInfo)costAccountMap.get(costAccountId);
//			key += "_"+costAccountInfo.getLongNumber();
			key += "_"+costAccountId;
			BigDecimal getCostPayedAmt = FDCHelper.toBigDecimal(dynCostSnapShot.getCostPayedAmt());
			if(getCostPayedAmt.compareTo(FDCHelper.ZERO) > 0){
				costPayedMap.put(key, getCostPayedAmt);
			}
			
//			if(getCostPayedAmt.compareTo(FDCHelper.ZERO)>0
//					&&costAccountWithAccountMap.containsKey(key)
//					&&costAccountWithAccountMap.get(key)!=null){
//				CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMap.get(key);
//				BigDecimal costPayedAmt = FDCHelper.ZERO;
//				if(info.containsKey("dynCostAmt"))
//					costPayedAmt = FDCHelper.toBigDecimal(info.get("dynCostAmt")) ;
//				costPayedAmt = costPayedAmt.add(FDCHelper.toBigDecimal(dynCostSnapShot.getCostPayedAmt()));
//				info.put("dynCostAmt",costPayedAmt);
//			}
		}
		viewInfo.getFilter().getFilterItems().clear();
		viewInfo.getSorter().clear();
		viewInfo.getSelector().clear();
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("account.id");		
		viewInfo.getSelector().add("debitLocal");
		viewInfo.getSelector().add("creditLocal");
		viewInfo.getSelector().add("endBalanceLocal");
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("orgUnit",orgUnitId,CompareType.EQUALS));
		//��Map.keySet()������õ����ݣ��޷����л�������ת����
		Set acctIds = new HashSet(accountViewMap.keySet());
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("account.id",acctIds,CompareType.INCLUDE));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("period.periodYear",periodYear,CompareType.EQUALS));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("period.periodNumber",periodNumber,CompareType.EQUALS));
		viewInfo.getFilter().getFilterItems().add(new FilterItemInfo("currency.id",FDCHelper.getBaseCurrency(null).getId().toString(),CompareType.EQUALS));
		
		AccountBalanceCollection accountBalances = AccountBalanceFactory.getRemoteInstance().getCollection(viewInfo);
		for(Iterator it = accountBalances.iterator();it.hasNext();){
			AccountBalanceInfo accountBalanceInfo = (AccountBalanceInfo)it.next();
			String key = accountBalanceInfo.getAccount().getId().toString();
			if(accountViewMap.containsKey(key)&&accountViewMap.get(key)!=null){
				AccountViewInfo accountView = (AccountViewInfo)accountViewMap.get(key);
				accountView.put("accountBalanceAmt",accountBalanceInfo.getEndBalanceLocal());
//				if(accountView.getDC().equals(BalanceDirectionEnum.DEBIT))
//					accountView.put("accountBalanceAmt",accountBalanceInfo.getDebitLocal());
//				else
//					accountView.put("accountBalanceAmt",accountBalanceInfo.getCreditLocal());
			}
		}
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		String lastAccountId = "";
		String lastPrjId = "";
		int rowMergStart = 0;
		int rowMergEnd = 0;
		BigDecimal costAccountSumAmt = FDCHelper.ZERO;
		BigDecimal accountAmt = FDCHelper.ZERO;
		BigDecimal singCostAmt = FDCHelper.ZERO;
		BigDecimal singAccountAmt = FDCHelper.ZERO;
		
		String currencyID = FDCHelper.getBaseCurrency(null).getId().toString();
		Map balance = getAccountViewBalance(periodYear, periodNumber,
				currencyID, curProjectIds, costID);
		
		//R101216-164 ����С��λλ���������λС��
		for(Iterator it=costAccountWithAccountColl.iterator();it.hasNext();){			
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			IRow iRow = table.addRow();
			iRow.setUserObject(info.getCostAccount());
			String key = info.getCostAccount().getCurProject().getId().toString();
			key += "_" + info.getCostAccount().getId().toString();
//			BigDecimal getCostPayedAmt = FDCHelper.toBigDecimal(((CostAccountWithAccountInfo)costAccountWithAccountMap.get(key)).get("dynCostAmt"));
			BigDecimal getCostPayedAmt = FDCHelper.ZERO;
			if(info.getCostAccount().isIsLeaf()){
				getCostPayedAmt = FDCHelper.toBigDecimal(costPayedMap.get(key), 2) ;
			}else{
//				getCostPayedAmt = FDCHelper.toBigDecimal(getCostPayedAmtSum(costPayedMap, costAccountMap,costViewMap,key), 2);
				
			}
			iRow.getCell("costAccount.id").setValue(info.getCostAccount().getId().toString());
			iRow.getCell("costAccount.longNumber").setValue(info.getCostAccount().getLongNumber().replace('!','.'));
			iRow.getCell("costAccount.name").setValue(info.getCostAccount().getName());
			iRow.getCell("costAccount.amount").setValue(getCostPayedAmt);
			iRow.getCell("accountView.id").setValue(info.getAccount().getId().toString());
			iRow.getCell("accountView.longNumber").setValue(info.getAccount().getNumber());
			iRow.getCell("accountView.name").setValue(info.getAccount().getName());
//			iRow.getCell("accountView.amount").setValue(FDCHelper.toBigDecimal(info.getAccount().get("accountBalanceAmt"), 2));
			Object amont = balance.get(info.getAccount().getId().toString());
			iRow.getCell("accountView.amount").setValue(FDCHelper.toBigDecimal(amont,2));
			if(isInvoiceMgr){
				if (info.getInvoiceAccount() != null) {
					iRow.getCell("invoiceAccount.longNumber").setValue(info.getInvoiceAccount().getNumber());
					iRow.getCell("invoiceAccount.name").setValue(info.getInvoiceAccount().getName());
					iRow.getCell("invoiceAccount.amount").setValue(FDCHelper.toBigDecimal(info.getInvoiceAccount().get("accountBalanceAmt"), 2));
				}
			}
			iRow.getCell("orgUnit").setValue(info.getCostAccount().getCurProject().getFullOrgUnit().getName());
			iRow.getCell("curproject.Name").setValue(info.getCostAccount().getCurProject().getName());
			iRow.getCell("curProject.id").setValue(info.getCostAccount().getCurProject().getId().toString());
			String prjId = info.getCostAccount().getCurProject().getId().toString();
			
			costAccountSumAmt = FDCHelper.add(costAccountSumAmt,getCostPayedAmt);				
			if(lastAccountId.length()>0&& lastAccountId.equals(info.getAccount().getId().toString())
					){
				accountAmt = FDCHelper.toBigDecimal(balance.get(info.getAccount().getId().toString()), 2);
				if(isInvoiceMgr){
					if(info.getInvoiceAccount()!=null){
						accountAmt = FDCHelper.add(accountAmt, FDCHelper.toBigDecimal(info.getInvoiceAccount().get("accountBalanceAmt"), 2));
					}
				}
				BigDecimal dif = FDCHelper.toBigDecimal(FDCHelper.subtract(accountAmt,costAccountSumAmt), 2);
				table.getRow(rowMergStart).getCell("status").setValue(new Boolean(dif.compareTo(FDCHelper.ZERO)==0));
				table.getRow(rowMergStart).getCell("diffentAmt").setValue(dif);
				rowMergEnd++;
				lastPrjId = info.getCostAccount().getCurProject().getId().toString();
				lastAccountId = info.getAccount().getId().toString();
				continue;
			}
			costAccountSumAmt = getCostPayedAmt;
			mergAccountColumns(table, rowMergStart, rowMergEnd);
			rowMergStart = rowMergEnd;
			lastPrjId = info.getCostAccount().getCurProject().getId().toString();
			lastAccountId = info.getAccount().getId().toString();
//			costAccountSumAmt.add(FDCHelper.toBigDecimal(getCostPayedAmt));
			singAccountAmt = FDCHelper.toBigDecimal(balance.get(info.getAccount().getId().toString()), 2);
			if(isInvoiceMgr){
				if(info.getInvoiceAccount()!=null){
					singAccountAmt = FDCHelper.add(singAccountAmt, FDCHelper.toBigDecimal(info.getInvoiceAccount().get("accountBalanceAmt"), 2));
				}
			}
			BigDecimal dif = FDCHelper.toBigDecimal(FDCHelper.subtract(singAccountAmt,getCostPayedAmt), 2);
			iRow.getCell("status").setValue(new Boolean(dif.compareTo(FDCHelper.ZERO)==0));
			iRow.getCell("diffentAmt").setValue(dif);
			rowMergEnd ++;
		}
		mergAccountColumns(table, rowMergStart, rowMergEnd);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		/**
		 * ǰ���Ѿ��Է���ϸ��Ŀ���ݽ������ۼƸ�ֵ������Ҫ�ٴλ����� 2009-06-11
		 * 
		 * ����Ļ����Ƕ�ֻ���ϼ���Ŀʱ�Ļ���,��������ǶԴ����ϼ����¼�ʱ,���¼����ܵ��ϼ����޸�bug:PBG029866 by hpw 2009-08-01
		 */
//		�Է���ϸ�Ŀ�Ŀ����
		for(int i = 0 ; i < table.getRowCount();i++){
			IRow row = table.getRow(i);
			if(row.getUserObject() != null && row.getUserObject() instanceof CostAccountInfo){
				CostAccountInfo costAccount = (CostAccountInfo)row.getUserObject();
				if(costAccount.isIsLeaf()) 
					continue;
				String longNumber = costAccount.getLongNumber();
				//��ƿ�Ŀid
				String accountId = (String)row.getCell("accountView.id").getValue();
				BigDecimal costAmt = FDCHelper.ZERO;
				for(int j = 1;j < table.getRowCount();j++){
					IRow row2 = table.getRow(j);
					CostAccountInfo costAccount2 = (CostAccountInfo)row2.getUserObject();
					if(!costAccount2.isIsLeaf() )
						continue;
					String longNumber2 = ((String)row2.getCell("costAccount.longNumber").getValue()).replace('.', '!');
					String accountId2 = (String)row2.getCell("accountView.id").getValue();
					if(longNumber2.startsWith(longNumber+"!") && accountId2.equals(accountId)){
						BigDecimal temp = (BigDecimal)row2.getCell("costAccount.amount").getValue();
						costAmt = FDCHelper.add(costAmt, temp);
					}
				}
				row.getCell("costAccount.amount").setValue(costAmt);
			}
		}
		if (!isInvoiceMgr) {
			this.tblMain.getColumn("invoiceAccount.longNumber")
					.getStyleAttributes().setHided(true);
			this.tblMain.getColumn("invoiceAccount.name").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("invoiceAccount.amount")
					.getStyleAttributes().setHided(true);
		}
		return true;
	}

	/**
	 * ��ͬһ������Ŀ��ͬһ��ƿ�Ŀ���н����ں�
	 * @param table
	 * @param rowMergStart ��ʼ��
	 * @param rowMergEnd   ������
	 */
	private void mergAccountColumns(KDTable table, int rowMergStart, int rowMergEnd) {
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("accountView.id"),
				rowMergEnd-1, table.getColumnIndex("accountView.id"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("accountView.longNumber"),
				rowMergEnd-1, table.getColumnIndex("accountView.longNumber"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("accountView.name"),
				rowMergEnd-1, table.getColumnIndex("accountView.name"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("accountView.amount"),
				rowMergEnd-1, table.getColumnIndex("accountView.amount"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("invoiceAccount.longNumber"),
				rowMergEnd-1, table.getColumnIndex("invoiceAccount.longNumber"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("invoiceAccount.name"),
				rowMergEnd-1, table.getColumnIndex("invoiceAccount.name"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("invoiceAccount.amount"),
				rowMergEnd-1, table.getColumnIndex("invoiceAccount.amount"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("status"),
				rowMergEnd-1, table.getColumnIndex("status"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("diffentAmt"),
				rowMergEnd-1, table.getColumnIndex("diffentAmt"));
		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("orgUnit"),
				rowMergEnd-1, table.getColumnIndex("orgUnit"));
//		table.getMergeManager().mergeBlock(rowMergStart, table.getColumnIndex("curproject.Name"),
//				rowMergEnd-1, table.getColumnIndex("curproject.Name"));
	}
	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
		//super.btnQuery_actionPerformed(e);
		//fillTable();
		if (isFirstDefaultQuery) {
            return;
        }
        showQueryDlg();
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		btnQuery_actionPerformed(e);
	}

	protected String getKeyFieldName() {

		return "accountView.id";
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo v = queryDlg.getEntityViewInfoResult();
		if(v==null||v.getFilter()==null){
//			isFirstDefaultQuery = true;
//			showQueryDlg();
			//2009-2-20 ���Ϊnull��˵������δ�仯����Ӧ����ʾ��ѯ���棬Ӧֱ�ӷ��ء�
			return;
		}
		else
			fillData(v);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.checkSelected();
    	
    	String accountid = getSelectedKeyValue();
    	UIContext uiContext = new UIContext(this);
		uiContext.put("accountid", accountid);
		uiContext.put("periodYear",myPanel.getPeriodYear());
		uiContext.put("periodNumber",myPanel.getPeriodNumber());
		
		// ����UI������ʾ
		IUIWindow impUI = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CostAccountWithAccountDetailUI.class.getName(), uiContext, null,
						"View");
		impUI.show();
	}
	/**
	 * ����¼���ϸ��Ŀ�ۼ�����
	 * @param costPayedMap   ��ʵ������Map,�� ������Ŀid+�ɱ���Ŀ������ Ϊkey�������
	 * @param costAccountMap �ɱ���ĿMap���� �ɱ���Ŀid Ϊkey �ɱ���ĿVO Ϊvalue �������
	 * @param accountViewMap �ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵMap �Գɱ���ĿidΪ��key ��ƿ�Ŀid Ϊvalue �������
	 * @param key ������Ŀid+�ɱ���Ŀid
	 * @return
	 */
	private BigDecimal getCostPayedAmtSum(Map costPayedMap , Map costAccountMap,Map costViewMap,String key){
		BigDecimal costPayedAmtSum = FDCHelper.ZERO;
		for(Iterator it = costPayedMap.keySet().iterator();it.hasNext();){
			String keyTemp = (String)it.next();
			String temp1[] = keyTemp.split("_");
			String temp2[] = key.split("_");
			//���������Ŀid��ͬ
 			if(!temp1[0].equals(temp2[0])){
 				continue;
 			}
 			//������Ǹóɱ���Ŀ���¼���Ŀ
 			CostAccountInfo costAccount1 = (CostAccountInfo)costAccountMap.get(temp1[1]);
 			CostAccountInfo costAccount2 = (CostAccountInfo)costAccountMap.get(temp2[1]);
 			if(!costAccount2.getLongNumber().equals(costAccount1.getLongNumber()) 
 					&& !costAccount1.getLongNumber().startsWith(costAccount2.getLongNumber()+"!")){
 				continue;
 			}
// 			AccountViewInfo accountView2 = (AccountViewInfo)accountViewMap.get(temp2[1]);
 			//����ͬһ��key ���������˶�Ӧ��ϵ
 			if(!key.equals(keyTemp) && costViewMap.containsKey(keyTemp)){
 				continue;
 			}
			BigDecimal costPayedAmt = FDCHelper.toBigDecimal(costPayedMap.get(keyTemp));
			costPayedAmtSum = FDCHelper.add(costPayedAmtSum, costPayedAmt);
		}
		return costPayedAmtSum;
	}

	/**
	 * ����������<br>
	 * 1�����ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��Ϊ��֯�¿�����<br>
	 * 2�����˽������ѡ����֯<br>
	 * 3����Ŀ���ȡ�����º�����Ŀ������<br>
	 * ����Ҫ��ƾ֤����ҿ��˹�����Ŀ���߳ɱ����Ĳſ���
	 * 
	 * �˷���Ϊ���������º�����Ŀ����ȡ�����������������ݣ����ǲ����Թ�����ƾ֤������ķ���ֱ�Ӵ�ƾ֤ȡ��<br>
	 * 2��������ѡ������ĳЩ�ͻ�ƾ֤������ֻ��������һ�֣�һ�ֲ��о�������һ��
	 * 
	 * @return
	 */
	private Map getAccountViewBalance(Integer periodYear,Integer periodNum,String currencyID,Set prjIdSet,String costCenter) {
		CompanyOrgUnitInfo currCompany = RptClientUtil.getCompany(periodYear.intValue(), periodNum.intValue());
		String accountTableId = currCompany.getAccountTable().getId().toString();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select d.fid accountID, ");
		sql.appendSql(" sum(case when C.FPeriodYear = ? and C.FPeriodNumber = ? then A.FEndBalanceFor else 0 end) endBalance ");
		sql.addParam(periodYear);
		sql.addParam(periodNum);
		sql.appendSql(" from T_GL_AssistBalance A ");
		sql.appendSql(" inner join T_BD_AssistantHG B on A.FAssistGrpID=B.FID ");
		sql.appendSql(" inner join T_BD_Period C on A.FPeriodID=C.FID ");
		sql.appendSql(" inner join T_BD_AccountView AV on AV.Fid=A.Faccountid ");
		sql.appendSql(" inner join T_BD_AccountView D on charIndex(D.FNumber, AV.FNumber)=1 ");
		sql.appendSql(" and D.Faccounttableid =AV.Faccounttableid and D.fcompanyid =AV.fcompanyid ");
		sql.appendSql(" left join T_FDC_CurProject T_0 on T_0.FID=B.FCurProjectID ");
		sql.appendSql(" left join T_ORG_CostCenter T_1 on T_1.FID=B.FCostOrgID ");
		sql.appendSql(" where A.FCurrencyID = ? ");
		sql.addParam(currencyID);
		sql.appendSql(" and A.FBalType = 1 ");
		sql.appendSql(" and C.FPeriodYear = ? ");
		sql.addParam(periodYear);
		sql.appendSql(" and C.FPeriodNumber = ? ");
		sql.addParam(periodNum);
		sql.appendSql(" and D.Faccounttableid = ? ");
		sql.addParam(accountTableId);
		if (!FDCHelper.isEmpty(costCenter)) {
			sql.appendSql(" and T_1.FID = ? ");
			sql.addParam(costCenter);
		} else if (!FDCHelper.isEmpty(prjIdSet)) {
			sql.appendSql(" and T_0.FID in ");
			sql.appendSql(FMHelper.setTran2String(prjIdSet));
		}
		sql.appendSql(" group by d.fid ");
		try {
			IRowSet rs = sql.executeQuery();
			Map rt = new HashMap();
			while(rs.next()){
				String accountID = rs.getString("accountID");
				BigDecimal endBalance = rs.getBigDecimal("endBalance");
				rt.put(accountID, endBalance);
			}
			return rt;
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		return null;
	}
    
	/**
	 * ����������<br>
	 * 1�����ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��Ϊ��֯�¿�����<br>
	 * 2�����˽������ѡ����֯<br>
	 * 3����Ŀ���ȡ�����º�����Ŀ������<br>
	 * ����Ҫ��ƾ֤����ҿ��˹�����Ŀ���߳ɱ����Ĳſ���
	 * 
	 * @return
	 */
	private Map getAccountViewBalanceNew(Integer periodYear,Integer periodNum,String currencyID,Set prjIdSet,String costCenter) {
		logger.info("new amount\n\n\n\n\n\n\n\n\n\n");
		CompanyOrgUnitInfo currCompany = RptClientUtil.getCompany(periodYear.intValue(), periodNum.intValue());
		String accountTableId = currCompany.getAccountTable().getId().toString();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select AV2.FID as accountID, ");
		//�跽Ϊ������Ϊ��
		sql.appendSql(" sum(CASE vet.FEntryDC when 1 then ass.FLocalAmount else 0 - ass.FLocalAmount end) as endBalance ");
		sql.appendSql(" from T_GL_Voucher as vch ");
		sql.appendSql(" inner join T_GL_VoucherEntry as vet ");
		sql.appendSql(" on vet.FBillID = vch.FID ");
		sql.appendSql(" inner join T_GL_VoucherAssistRecord as ass ");
		sql.appendSql(" on ass.FEntryID = vet.FID ");
		sql.appendSql(" inner join T_BD_AssistantHG as ahg ");
		sql.appendSql(" on ahg.FID = ass.FAssGrpID ");
		sql.appendSql(" inner join T_BD_Period as prd ");
		sql.appendSql(" on prd.FID = vch.FPeriodID ");
		sql.appendSql(" inner join T_BD_AccountView as AV ");
		sql.appendSql(" on AV.Fid = vet.Faccountid  ");
		sql.appendSql(" inner join T_BD_AccountView as AV2 ");
		sql.appendSql(" on charIndex(AV2.FNumber, AV.FNumber)=1 ");
		sql.appendSql(" and AV2.Faccounttableid =AV.Faccounttableid ");
		sql.appendSql(" and AV2.fcompanyid =AV.fcompanyid ");
		sql.appendSql(" left join T_FDC_CurProject as prj ");
		sql.appendSql(" on ahg.FCurProjectID = prj.FID ");
		sql.appendSql(" left join T_ORG_CostCenter as cost ");
		sql.appendSql(" on cost.FID=ahg.FCostOrgID ");
		// �ֹ����˵Ĳ���
		sql.appendSql(" where vch.FSourceType <> 0 ");
		sql.appendSql(" and prd.FPeriodYear = ? ");
		sql.addParam(periodYear);
		sql.appendSql(" and prd.FPeriodNumber = ? ");
		sql.addParam(periodNum);
		sql.appendSql(" and AV2.Faccounttableid = ? ");
		sql.addParam(accountTableId);
		if (!FDCHelper.isEmpty(costCenter)) {
			sql.appendSql(" and cost.FID = ? ");
			sql.addParam(costCenter);
		} else if (!FDCHelper.isEmpty(prjIdSet)) {
			sql.appendSql(" and prj.FID in ");
			sql.appendSql(FMHelper.setTran2String(prjIdSet));
		}
		sql.appendSql(" group by AV2.FID ");
		try {
			IRowSet rs = sql.executeQuery();
			Map rt = new HashMap();
			while(rs.next()){
				String accountID = rs.getString("accountID");
				BigDecimal endBalance = rs.getBigDecimal("endBalance");
				rt.put(accountID, endBalance);
			}
			return rt;
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		return null;
	}
}