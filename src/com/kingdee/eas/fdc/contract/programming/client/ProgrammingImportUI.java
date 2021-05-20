/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProgrammingImportUI extends AbstractProgrammingImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingImportUI.class);
    
	public ProgrammingImportUI() throws Exception {
		super();
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	menuBar.setVisible(false);
		toolBar.setVisible(false);
		actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    	tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    }
    
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		EntityViewInfo view = this.getMainQuery();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled" , new Integer(1) , CompareType.EQUALS));
		view.setFilter(filter);
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				executeImport();
			}
		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		executeImport();
	}
	
	private void executeImport() throws Exception {
		if (MsgBox.OK != MsgBox.showConfirm2(this, "��ģ�嵼�뽫���ǵ�ǰ���ݣ�����ô��")) {
			return;
		}
		Map uiContext = getUIContext();
		ProgrammingInfo programming = (ProgrammingInfo) uiContext.get("editData"); // ������ĺ�Լ�滮
		DataChangeListener dataChangeListener = (DataChangeListener) uiContext.get("dataChangeListener");
		//����ģ���ʱ��Ĭ����û��Ŀ��ɱ���
		ProgrammingEditUI editUI = (ProgrammingEditUI)uiContext.get("Owner");
//		editUI.prmtAimCost.removeDataChangeListener(dataChangeListener);
//		editUI.prmtAimCost.setValue(null);
//		editUI.prmtAimCost.addDataChangeListener(dataChangeListener);
		AimCostInfo aimCost = (AimCostInfo) uiContext.get("aimCost"); // Ŀ��ɱ�
//		aimCost = null;
		programming.setAimCost(aimCost);
		CurProjectInfo project = (CurProjectInfo) uiContext.get("project"); // ��ܺ�Լ�Ĺ����Ĺ�����Ŀ
		if (programming == null || project == null) {
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", getSelectedKeyValue()));
		
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		
		ProgrammingTemplateEntireCollection templateEntireCollection = 
			ProgrammingTemplateEntireFactory.getRemoteInstance().getProgrammingTemplateEntireCollection(evi);
		
		templateImport(programming, templateEntireCollection, project, aimCost);
		MsgBox.showInfo(this, "����ɹ�");
		disposeUIWindow();
	}
	
	private void templateImport(ProgrammingInfo programming, ProgrammingTemplateEntireCollection 
			templateEntryCollection, CurProjectInfo project, AimCostInfo aimCost) 
	throws EASBizException, BOSException {
		ProgrammingContractCollection entries = programming.getEntries();
		entries.clear();
		for (int i = 0, size = templateEntryCollection.size(); i < size; i++) {
			ProgrammingTemplateEntireInfo templateEntry = templateEntryCollection.get(i);
			ProgrammingContractInfo programmingEntry = new ProgrammingContractInfo();
			initEntryAttribute(templateEntry, programmingEntry);
			copyAttachment(templateEntry, programmingEntry);
			entryImport(programmingEntry, templateEntry, project, aimCost);
			initEntryOtherAmout(programmingEntry);
			entries.add(programmingEntry);
		}
		
		setEntryParent(entries);
	}
	
	/**
	 * ��ʼ������ĺ�Լ���������
	 */
	private void initEntryOtherAmout(ProgrammingContractInfo info) {
		info.setControlBalance(FDCHelper.ZERO); // �������
		info.setSignUpAmount(FDCHelper.ZERO);	// ǩԼ���
		info.setChangeAmount(FDCHelper.ZERO);	// ������
		info.setSettleAmount(FDCHelper.ZERO);	// ������
	}

	private void initEntryAttribute(ProgrammingTemplateEntireInfo templateEntry,
			ProgrammingContractInfo programmingEntry) {
		programmingEntry.setId(BOSUuid.create(programmingEntry.getBOSType()));
		programmingEntry.setLevel(templateEntry.getLevel());
		programmingEntry.setLongNumber(templateEntry.getLongNumber());
		programmingEntry.setNumber(templateEntry.getNumber());
		programmingEntry.setDisplayName(templateEntry.getDisplayName());
		programmingEntry.setName(templateEntry.getName().trim());
		programmingEntry.setWorkContent(templateEntry.getScope());
		programmingEntry.setSupMaterial(templateEntry.getProblem());
		programmingEntry.setSortNumber(templateEntry.getSortNumber());
		if(templateEntry.getAttachment() != null){
			programmingEntry.setAttachment("���и���");
		}
		programmingEntry.setDescription(templateEntry.getDescription());
		programmingEntry.setContractType(templateEntry.getContractType());
	}

	// ���÷�¼���Ӽ���ϵ
	private void setEntryParent(ProgrammingContractCollection entries) {
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			for (int j = 0; j < size; j++) {
				ProgrammingContractInfo entryParent = entries.get(j);
				String longNumberParent = entryParent.getLongNumber();
				if (longNumber.startsWith(longNumberParent)) {
					int count = longNumber.split("\\.").length;
					int countParent = longNumberParent.split("\\.").length;
					if (count - countParent == 1) {
						entry.setParent(entryParent);
						break;
					}
				}
			}
		}
	}
	
	/**
     * ���Ƹ���
     */
    private void copyAttachment(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingContractInfo proInfo){
    	FDCSQLBuilder sql = new FDCSQLBuilder("select * from T_BAS_BoAttchAsso where FBOID = '"+proTempInfo.getId()+"'");
    	BoAttchAssoInfo info = null;
    	try {
			IRowSet rs = sql.executeQuery();
			while(rs.next()){
				info = new BoAttchAssoInfo();
				String attID = rs.getString("FATTACHMENTID");
				if(!StringUtils.isEmpty(attID))
					info.setAttachment(AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID)));
				info.setBoID(proInfo.getId().toString());
				info.setAssoBusObjType(proInfo.getBOSType().toString());
				info.setAssoType("ϵͳ���и���");
				BoAttchAssoFactory.getRemoteInstance().addnew(info);
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		} catch (EASBizException e) {
			logger.error(e);
		}
    	
    }
	
	private void entryImport(ProgrammingContractInfo programmingEntry, 
			ProgrammingTemplateEntireInfo templateEntry, CurProjectInfo project, AimCostInfo aimCost) 
	throws EASBizException, BOSException {
		BigDecimal planAmountTotal = FDCHelper.ZERO; // ���гɱ����󱾺�Լ����ɹ滮���
		// ģ���гɱ�����
		PTECostCollection pteCostCollection = templateEntry.getPteCost();
		// ��Լ�гɱ�����
		ProgrammingContracCostCollection costEntries = programmingEntry.getCostEntries();
		StringBuffer costAccountNames = new StringBuffer(); 
		for (int i = 0, size = pteCostCollection.size(); i < size; i++) {
			PTECostInfo pteCostInfo = pteCostCollection.get(i);
			// ģ�嵼�������ĳɱ�����
			ProgrammingContracCostCollection programmingCollection = costEntryImport(pteCostInfo, project, aimCost);
			
			// �����ģ���еĳɱ���Ŀ���ظ�
			// ֱ����ӵ���¼��
			for (int j = 0, sizeJ = programmingCollection.size(); j < sizeJ; j++) {
				ProgrammingContracCostInfo info = programmingCollection.get(j);
				planAmountTotal = planAmountTotal.add(info.getContractAssign());
				costEntries.add(programmingCollection.get(j));
				
				CostAccountInfo costAccountInfo = info.getCostAccount();
				if(j > 0){
					costAccountNames.append(";");
				}
				costAccountNames.append(costAccountInfo.getName());
			}
		}
		programmingEntry.setCostAccountNames(costAccountNames.toString());
		programmingEntry.setAmount(planAmountTotal);
		programmingEntry.setControlAmount(FDCHelper.ZERO);
		// ģ���о�������
		PTEEnonomyCollection pteEnonomyCollection = templateEntry.getPteEnonomy();
		enonomyImport(programmingEntry, pteEnonomyCollection);
	}
	
	/**
	 * ���뾭������
	 * @param programmingCollection
	 * @param pteEnonomyCollection
	 */
	private void enonomyImport(ProgrammingContractInfo programmingEntry, 
			PTEEnonomyCollection pteEnonomyCollection) {
		ProgrammingContractEconomyCollection programmingEconomyEntries = programmingEntry.getEconomyEntries();
		programmingEconomyEntries.clear();
		for (int i = 0, size = pteEnonomyCollection.size(); i < size; i ++) {
			ProgrammingContractEconomyInfo economy = new ProgrammingContractEconomyInfo();
			PTEEnonomyInfo pteEnonomy = pteEnonomyCollection.get(i);
			economy.setScale(pteEnonomy.getScale()); // ������� 
			economy.setCondition(pteEnonomy.getCondition()); // ��������
			economy.setPaymentType(pteEnonomy.getPaymentType()); // ��������
			economy.setDescription(pteEnonomy.getDescription()); // ��ע
			economy.setAmount(pteEnonomy.getScale().multiply(programmingEntry.getAmount())); // ������
			economy.setAmount(FDCHelper.divide(economy.getAmount(), FDCHelper.ONE_HUNDRED)); // ������
			programmingEconomyEntries.add(economy);
		}
	}
	
	/**
	 * ����ɱ�
	 * @param pteCostInfo
	 * @param project
	 * @param aimCost
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private ProgrammingContracCostCollection costEntryImport(PTECostInfo pteCostInfo, 
			CurProjectInfo project, AimCostInfo aimCost) 
	throws BOSException, EASBizException {
		ProgrammingContracCostCollection retCollection = new ProgrammingContracCostCollection();
		CostAccountInfo pteCostAccount = pteCostInfo.getCostAccount(); // ģ���гɱ���Ŀ
		CostAccountInfo costAccount = changePteCost2CurProject(pteCostAccount, project);
		if (costAccount == null) {
			return retCollection;
		}
		
		CostAccountCollection proCostCollection = getProCostAccount(costAccount);
		if (aimCost != null) {
			// ָ����Ŀ��ɱ�
			BigDecimal aimCostValue = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(
					costAccount, aimCost);
			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(aimCostValue); // Ŀ��ɱ�
				
				BigDecimal contractScale = pteCostInfo.getContractScale() == null ? FDCHelper.ZERO :
					pteCostInfo.getContractScale();
				contractScale = FDCHelper.divide(contractScale, FDCHelper.ONE_HUNDRED);
				info.setContractAssign(aimCostValue.multiply(contractScale)); // ����Լ����
				
				BigDecimal assignScale = pteCostInfo.getAssignScale() == null ? FDCHelper.ZERO :
					pteCostInfo.getAssignScale();
				assignScale = FDCHelper.divide(assignScale, FDCHelper.ONE_HUNDRED);
				BigDecimal assigning = aimCostValue.multiply(assignScale);
				info.setAssigning(assigning); // ������
				info.setAssigned(aimCostValue.subtract(assigning)); // �ѷ���
				info.setDescription(pteCostAccount.getDescription()); // ��ע
				retCollection.add(info);
			}
		} else {
			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(FDCHelper.ZERO); // Ŀ��ɱ�
				info.setAssigned(FDCHelper.ZERO); // �ѷ���
				info.setAssigning(FDCHelper.ZERO); // ������
				info.setContractAssign(FDCHelper.ZERO); // ����Լ����
				info.setDescription(pteCostAccount.getDescription()); // ��ע
				retCollection.add(info);
			}
		}
		return retCollection;
	}
	
	/**
	 * ת��ģ���е���֯�ɱ���ĿΪ ������Ŀ�µĳɱ���Ŀ
	 * @param pteCostAccount
	 * @param project
	 * @return
	 */
	private CostAccountInfo changePteCost2CurProject(CostAccountInfo pteCostAccount, CurProjectInfo project) {
		String longNumber = pteCostAccount.getLongNumber();
		String projectId = project.getId().toString();
		StringBuffer oql = new StringBuffer();
		
		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longnumber = '").append(longNumber).append("'");
		oql.append(" and curProject = '").append(projectId).append("'");
		oql.append(" and fullOrgUnit is null");
		try {
			return CostAccountFactory.getRemoteInstance().getCostAccountInfo(oql.toString());
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * ���ģ���е�ĩ���ɱ���Ŀ����Ŀ�б�Ϊ�����ɱ���Ŀ
	 * ���ڿ�ܺ�Լ�ĳɱ����ɹ�ϵ���Զ��Ѹø�����Ŀ�滻Ϊ��Ӧ������ĩ���ɱ���Ŀ
	 * @param pteCostAccount ģ��ɱ���Ŀ
	 * @return CostAccountCollection
	 * @throws BOSException
	 */
	private CostAccountCollection getProCostAccount(CostAccountInfo pteCostAccount) throws BOSException {
		CostAccountCollection costCollection = new CostAccountCollection();
		String longNumber = pteCostAccount.getLongNumber();
		
		StringBuffer oql = new StringBuffer();
		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longNumber like '").append(longNumber).append("!%'");
		oql.append(" and fullOrgUnit is null");
		costCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(oql.toString());
		if (costCollection.isEmpty()) {
			costCollection.add(pteCostAccount);
		}
		return costCollection;
	}
	
	private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("pteCost.costAccount");
		sic.add("pteCost.costAccount.id");
		sic.add("pteCost.costAccount.number");
		sic.add("pteCost.costAccount.name");
		sic.add("pteCost.costAccount.longNumber");
		sic.add("pteCost.assignScale");
		sic.add("pteCost.contractScale");
		sic.add("pteEnonomy.scale");
		sic.add("pteEnonomy.condition");
		sic.add("pteEnonomy.description");
		sic.add("pteEnonomy.parent");
		sic.add("pteEnonomy.paymentType");
		sic.add("pteEnonomy.paymentType.name");
		sic.add("pteEnonomy.paymentType.number");
		sic.add("name");
		sic.add("number");
		sic.add("description");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("attachment");
		sic.add("displayName");
		sic.add("sortNumber");
		sic.add("contractType.*");
		return sic;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.programming.client.ProgrammingTemplateEditUI.class.getName();
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
}