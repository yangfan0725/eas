/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */

public class SaleBalanceListUI extends AbstractSaleBalanceListUI {

	private static final Logger logger = CoreUIObject.getLogger(SaleBalanceListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private Set idSet = new HashSet();
	private SellProjectInfo sellProject = null;
	private CompanyOrgUnitInfo companyInfo = null;
	private PeriodInfo companyCurrentPeriod = null;
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		getAllSellProjectIds((TreeNode) this.treeMain.getModel().getRoot());
	}

	protected String[] getLocateNames() {
		String[] localNames = new String[6];
		localNames[0] = "operateType";
		localNames[1] = "startDate";
		localNames[2] = "endDate";
		localNames[3] = "remark";
		localNames[4] = "creator.name";
		localNames[5] = "balanceDate";
		return localNames;
	}

	public void actionSaleBalance_actionPerformed(ActionEvent e) throws Exception {
		
		/**
		 * �½�ǰ��������Ŀ�Ƿ������̣�û�����̲����½᣻
		 * �����˼������ʱ���Ƿ����½������Ժ��ǵĻ��������̡�
		 * */
		if (sellProject == null) {
			MsgBox.showInfo("��ûѡȡ��������Ŀ�����顣");
			return;
		} else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id", sellProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			if (SellOrderFactory.getRemoteInstance().exists(filter)) {
				filter.getFilterItems().add(new FilterItemInfo("orderDate", new Date(), CompareType.GREATER));
				if (SellOrderFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(sellProject.getName() + " ��û������ʱ�䣬���顣");
					return;
				} else {
					UIContext context = new UIContext(ui);
					context.put("sellProject", sellProject);
					context.put("sellProjectIdSet", idSet);
					context.put(UIContext.OWNER, SaleBalanceListUI.this);
					try {
						IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SaleBalanceEditUI.class.getName(), context, null, OprtState.ADDNEW);
						window.show();
					} catch (UIException e1) {
						this.handleException(e1);
					}
					finally{
						DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
						if (node == null) {
							return;
						}
						this.execQuery();
					}
				}

			} else {
				MsgBox.showInfo(sellProject.getName() + " ��û���̣����顣");
				return;
			}
		}

	}

	public void actionUnSaleBalance_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if(id==null) return;		
		try{
			SaleBalanceInfo saleBalInfo = SaleBalanceFactory.getRemoteInstance().getSaleBalanceInfo(new ObjectUuidPK(BOSUuid.read(id)));
			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProject.getId().toString())));
			if(saleBalInfo!=null) {	
				if(saleBalInfo.getOperateType().equals(SaleBalanceTypeEnum.UnBalance)){
					MsgBox.showInfo("�Ѿ����½ᣬ�����ٷ��½ᡣ");
					return;
				}
				if(sellProject.getBalanceEndDate()==null){
					MsgBox.showInfo("�ϴ��½�û�з�д������Ŀ���½��ֹ���ڡ�");
					return;
				}
				if(saleBalInfo.getStartDate()==null){
					MsgBox.showInfo("�ϴ��½���½���ʼ���ڸ���ʧ�ܡ�");
					return;
				}
				if(saleBalInfo.getEndDate()==null){
					MsgBox.showInfo("�ϴ��½���½�������ڸ���ʧ�ܡ�");
					return;
				}
				String dateStr1= FDCDateHelper.formatDate2(sellProject.getBalanceEndDate());
				String dateStr2= FDCDateHelper.formatDate2(saleBalInfo.getEndDate());
				if(!dateStr1.equals(dateStr2)){
					MsgBox.showInfo("���ܷ��½���½ᣬ���ȷ��½�     �½��������Ϊ  "+dateStr1+" ���ڵ��½ᡣ");
					return;
				}				
				if(MsgBox.showConfirm2("ȷ��Ҫ���½᣿")==MsgBox.YES){									
					SellProjectInfo sellProInfo = saleBalInfo.getSellProject();
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("*");
					sels.add("salePeriod.*");
					sels.add("saleNowPeriod.*");
					sellProInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProInfo.getId()),sels);
					companyInfo = SysContext.getSysContext().getCurrentFIUnit();
					//��˾��Ӧ�ĵ�ǰ�ڼ�
					companyCurrentPeriod = SystemStatusCtrolUtils
					.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
					//��Ŀ��Ӧ�ĵ�ǰ�ڼ�
					PeriodInfo projectPeriod = sellProInfo.getSaleNowPeriod();
					//�����˾��Ӧ���ڼ���ڵ�����Ŀ��Ӧ���ڼ䣬��ô���ܽ��з��½�
					//�ڼ�Ƚ�
					if(companyCurrentPeriod.getPeriodNumber()>=projectPeriod.getPeriodNumber())
					{
						MsgBox.showInfo("��Ӧ������֯�ڶ�Ӧ�ڼ��Ѿ��½ᣬ��������ĩ���˴����з��½ᣬ�ٽ��б�����");
						this.abort();
					}
					//�����Ŀ�ĵ�ǰ�ڼ�Ϊ�����ڼ���ôҲ���ܽ��з��½�
					if(sellProInfo.getSaleNowTerm().equals(sellProInfo.getSaleTerm()))
					{
						MsgBox.showInfo("��ǰ�ڼ�Ϊ�����ڼ䣬���ܽ��з��½�");
						this.abort();
					}
					//ǰһ���ڼ�
					PeriodInfo beforePeriod = PeriodUtils.getPrePeriodInfo(projectPeriod);
					if(beforePeriod!=null)
					{
						sellProInfo.setSaleNowTerm(new Integer(beforePeriod.getNumber()).toString());
						sellProInfo.setSaleNowPeriod(beforePeriod);
						SelectorItemCollection sels2 = new SelectorItemCollection();
						sels2.add("saleNowTerm");
						sels2.add("saleNowPeriod.id");
						SellProjectFactory.getRemoteInstance().updatePartial(sellProInfo, sels2);
					}
					SettleMentFacadeFactory.getRemoteInstance().dealAntiSaleBalance(new ObjectUuidPK(BOSUuid.read(id)));					
					saleBalInfo.setOperateType(SaleBalanceTypeEnum.UnBalance);
					SaleBalanceFactory.getRemoteInstance().update(new ObjectUuidPK(BOSUuid.read(id)),saleBalInfo);
					MsgBox.showInfo("���½�ɹ���");
					this.refreshList();
				}
			}
		}catch(Exception ee) {
			this.handleException(ee);
			this.abort();
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionSaleBalance.setVisible(true);
		this.actionUnSaleBalance.setVisible(true);
		
		this.actionSaleBalance.setEnabled(false);
		this.actionUnSaleBalance.setEnabled(false);

		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);		
		this.actionInitDataBld.setVisible(false);
		this.actionInitDataPty.setVisible(false);
		//this.actionInitDataBld.setEnabled(true);
		//this.actionInitDataPty.setEnabled(true);
		this.btnBalance.setText("�½�");
		this.btnUnBalance.setText("���½�");		
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output class constructor
	 */
	public SaleBalanceListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("��ѡ�����������Ŀ��");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			sellProject = (SellProjectInfo) node.getUserObject();
		}
		super.prepareUIContext(uiContext, e);
	}

	private void getAllSellProjectIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) thisNode.getUserObject();
			if (sellProject != null) {
				idSet.add(sellProject.getId().toString());
			}
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSellProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			sellProject = (SellProjectInfo) node.getUserObject();
			if (this.saleOrg.isIsBizUnit()) {
				this.actionSaleBalance.setEnabled(true);
				this.actionUnSaleBalance.setEnabled(true);
			}
		} else {
			this.actionSaleBalance.setEnabled(false);
			this.actionUnSaleBalance.setEnabled(false);
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			return;
		}
		if (id == null) {
			return;
		}
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			return;
		}
		if (id == null) {
			return;
		}
		super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {

		return null;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SaleBalanceFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SaleBalanceEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	
	public void actionInitDataBld_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataBldListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	
	public void actionInitDataPty_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataPtyListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	
	
}