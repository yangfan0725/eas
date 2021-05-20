/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.app.SheRoomStateFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PurchaseListUI extends AbstractPurchaseListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(PurchaseListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private SellProjectInfo sellProject = null;

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("prePurchaseAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("prePurchaseAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}
	
	/**
	 * ʵ����FilterUI  by Pope
	 * @return
	 */
	private CustomerQueryPanel getFilterUI()
	{
		if (this.filterUI == null)
		{
			try
			{
				this.filterUI = new PurchaseFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e)
			{
				this.handUIException(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * ��FilterUI����ѯ���� by Pope
	 */
	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{   
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{ // ��Ԫ
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				this.actionPrePurchase.setEnabled(true);
			}
			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(false);
			BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo) node.getUserObject();
			if(buildUnitInfo != null){
				sellProject = buildUnitInfo.getBuilding().getSellProject();
			}
			
		} else if (node.getUserObject() instanceof BuildingInfo)
		{ // ¥��
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				if (saleOrg.isIsBizUnit())
				{
					this.actionAddNew.setEnabled(true);
					this.actionPrePurchase.setEnabled(true);
				}
			} else
			{
				this.actionAddNew.setEnabled(false);
				this.actionPrePurchase.setEnabled(false);
			}
			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(false);
			if(building != null){
				sellProject = building.getSellProject();
			}
		} else if (node.getUserObject() instanceof SubareaInfo)
		{ // ����

			this.actionAddNew.setEnabled(false);
			this.actionPrePurchase.setEnabled(false);
			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(false);
			SubareaInfo subAreaInfo =(SubareaInfo) node.getUserObject();
			if(subAreaInfo != null){
				sellProject = subAreaInfo.getSellProject();
			}
		} else
		{
			this.actionAddNew.setEnabled(false);
			this.actionPrePurchase.setEnabled(false);
			if (node.getUserObject() instanceof SellProjectInfo)
			{
				SellProjectInfo proNode = (SellProjectInfo) node
						.getUserObject();
				if(proNode != null){
					sellProject=proNode;
				}			
				
				if (proNode.getSubarea() == null
						|| proNode.getSubarea().isEmpty())
				{
					this.tblMain.getColumn("subarea.name").getStyleAttributes()
							.setHided(false);
				} else
				{
					this.tblMain.getColumn("subarea.name").getStyleAttributes()
							.setHided(false);
				}
			}
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(false);
		}

		this.execQuery();
	}
	
	protected void execQuery()
	{
		super.execQuery();
//		initGatheringState();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
//					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
//					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
//					�����ѽ���ΪbuildUnit����ֶ� ������Ĺ�������Ҳ�ĵ� xiaoao_liu
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionProofOfPayment_actionPerformed(ActionEvent e)
			throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			this.abort();
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
				"purchaseState").getValue();
		if (isHasBlankOut(purchaseState))
		{
			MsgBox.showInfo("�Ϲ����Ѿ����ϣ��������ø���֤����");
			this.abort();
		}
		if ((purchaseState != null)
				&& (!PurchaseStateEnum.PURCHASEAUDIT_VALUE.equals(purchaseState
						.getValue())))
		{
			MsgBox.showInfo("�Ϲ���ûͨ����ˣ��������ø���֤����");
			this.abort();
		}

		super.actionProofOfPayment_actionPerformed(e);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);		
		uiContext.put("sourceName", "purchase");
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ProofOfPaymentEditUI.class.getName(), uiContext, null,
						OprtState.ADDNEW);
		uiWindow.show();
	}

	public void actionPurchasePrint_actionPerformed(ActionEvent e)
			throws Exception
	{
		String purchaseID = this.getSelectedKeyValue();
		HashMap parmterMap = this.checkPurchase(purchaseID);
		PurchasePrintDataProvider data = new PurchasePrintDataProvider(
				purchaseID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.BeforehandPurchasePrintQuery"),
				parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/purchase", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPurchasePrint_actionPerformed(e);
	}

	public HashMap checkPurchase(String purchaseID) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			this.abort();
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
				"purchaseState").getValue();
		if (isHasBlankOut(purchaseState))
		{
			MsgBox.showInfo("�Ϲ����Ѿ�����,���ܴ�ӡ!");
			this.abort();
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("customerInfo.*");
		sels.add("room.*");
		PurchaseInfo purchaseInfo = PurchaseFactory.getRemoteInstance()
				.getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchaseID)),
						sels);
		BigDecimal prePurchaseAmount = purchaseInfo.getPrePurchaseAmount();
		HashMap parmterMap = new HashMap();
		ArrayList list = new ArrayList();
		if (prePurchaseAmount == null)
		{
			MsgBox.showInfo("Ԥ�����Ϊ��,���ܴ�ӡԤ����!");
			this.abort();
		} else
		{
			RoomInfo room = purchaseInfo.getRoom();
			PurchaseCustomerInfoCollection purCusCollection = (PurchaseCustomerInfoCollection) purchaseInfo
					.getCustomerInfo();
//			if (purCusCollection.size() > 2)
//			{
//				MsgBox.showInfo("���ֻ��2���Ϲ��ͻ�!");
//				this.abort();
//			} else
//			{
				for (int i = 0; i < purCusCollection.size(); i++)
				{
					PurchaseCustomerInfoInfo info = purCusCollection.get(i);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					selector.add("customer.*");
					FDCCustomerInfo customer;
					customer = FDCCustomerFactory
							.getRemoteInstance()
							.getFDCCustomerInfo(
									new ObjectUuidPK(BOSUuid.read(info
											.getCustomer().getId().toString())),
									selector);
					list.add(customer);
					parmterMap.put("customerList", list);
				}
				parmterMap.put("room", room);
				parmterMap.put("purchaseInfo", purchaseInfo);
			//}
		}
		return parmterMap;
	}

	/**
	 * output actionPurchasePrintView_actionPerformed method
	 */
	public void actionPurchasePrintView_actionPerformed(ActionEvent e)
			throws Exception
	{
		String purchaseID = this.getSelectedKeyValue();
		HashMap parmterMap = this.checkPurchase(purchaseID);
		PurchasePrintDataProvider data = new PurchasePrintDataProvider(
				purchaseID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.BeforehandPurchasePrintQuery"),
				parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/purchase", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPurchasePrintView_actionPerformed(e);
	}

	public void onLoad() throws Exception
	{
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionBlankOut.setEnabled(false);
		}
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionWebMark.setEnabled(true);
		this.actionPurchasePrint.setEnabled(true);
		this.actionPurchasePrintView.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionUncheckPrePurchase.setVisible(false);

		this.actionAuditResult.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAttachment.setVisible(false);// ���ú��и��쳣����ע�� zhicheng_jin
		// 081101

		this.btnProofOfPayment.setEnabled(true);
		this.menuItemProofOfPayment.setVisible(true);
		this.menuItemProofOfPayment.setEnabled(true);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		
		/**
		 * �޸��Ƶ����ڸ�ʽΪYYYY��MM��DD
		 * by renliang
		 * at 2010-10-25
		 */
		
		if(tblMain.getColumn("createTime")!=null){
			tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
		}
		
		if(this.tblMain.getColumn("specilAgio")!=null){
			tblMain.getColumn("specilAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			
		}
	}
	/**
	 * ���������ص��ݵ�һ��״̬
	 * @author pu_zhang @date 2010-08-06
	 */
	protected String getBillStatePropertyName() {
		// TODO Auto-generated method stub
		return "purchaseState";
	}
	/**
	 * ��ʼ�� �տ�״̬ by Pope
	 */
	public void initGatheringState()
	{
		List list = new ArrayList();
		Map map = new HashMap();
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			list
					.add(this.tblMain.getRow(i).getCell("id").getValue()
							.toString());
		}
		IRowSet rs = null;
		if (list != null && list.size() > 0)
		{
			try
			{
				String sql = "select a.fid as id,a.FPurchaseState as state,sum(c.FAmount) as sumAmount from T_SHE_Purchase as a left join T_SHE_FDCReceiveBill as b on a.fid=b.FPurchaseID left join T_CAS_ReceivingBill as c on b.Fid=c.FFdcReceiveBillID group by a.fid,a.FPurchaseState";
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				rs = builder.executeQuery();
			} catch (BOSException e)
			{
				this.handUIExceptionAndAbort(e);
			}
			if (rs != null && rs.size() > 0)
			{
				try
				{
					while (rs.next())
					{
						for (int i = 0; i < list.size(); i++)
						{
							String id = "";
							String fs = "";
							double fa = 0;
							try
							{
								id = rs.getString("id").trim();
								fs = rs.getString("state");
								fa = rs.getDouble("sumAmount");
							} catch (SQLException e)
							{
								this.handUIExceptionAndAbort(e);
							}
							if (list.get(i).toString().trim().equals(id))
							{
								if (PurchaseStateEnum.PREPURCHASEAPPLY_VALUE
										.equals(fs)
										|| PurchaseStateEnum.PREPURCHASECHECK_VALUE
												.equals(fs))
								{
									if (fa > 0)
									{
										map.put(id, "Ԥ�����տ�");
									} else
									{
										map.put(id, "Ԥ��δ�տ�");
									}
								} else if ((!PurchaseStateEnum.PREPURCHASEAPPLY_VALUE
										.equals(fs))
										&& (!PurchaseStateEnum.PREPURCHASECHECK_VALUE
												.equals(fs)))
								{
									if (fa > 0)
									{
										map.put(id, "�Ϲ����տ�");
									} else
									{
										map.put(id, "�Ϲ�δ�տ�");
									}
								} else
								{
									map.put(id, "����");
								}
							}
						}

					}
				} catch (SQLException e)
				{
					this.handUIExceptionAndAbort(e);
				}
			}
			if (map.size() > 0)
			{
				for (int i = 0; i < this.tblMain.getRowCount(); i++)
				{
					String id = this.tblMain.getRow(i).getCell("id").getValue()
							.toString();
					if (map.containsKey(id))
					{
						String value = map.get(id).toString();
						this.tblMain.getRow(i).getCell("gatheringState")
								.setValue(value);
					}
				}
			}
		}
	}

	/**
	 * output class constructor
	 */
	public PurchaseListUI() throws Exception
	{
		super();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		setUIContextAboutTreeNode(uiContext);
	}

	private void setUIContextAboutTreeNode(UIContext uiContext)
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof Integer)
		{ // ������
			Integer unit = (Integer) node.getUserObject();
			uiContext.put("unit", unit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			uiContext.put("buildUnit", buildUnit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		} else if (node.getUserObject() instanceof BuildingInfo)	{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				uiContext.put("building", building);
				uiContext.put("sellProject", building.getSellProject());
				uiContext.put("unit", new Integer(0));
			}
		}
		else if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{
		if (isCanSetEditTextToPurchase())
		{
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			if (rowIndex != -1)
			{
				String msg = null;
				IRow row = this.tblMain.getRow(rowIndex);
				BizEnumValueInfo purchaseState = (BizEnumValueInfo) row
						.getCell("purchaseState").getValue();
				if (purchaseState != null
						&& PurchaseStateEnum.PREPURCHASECHECK_VALUE
								.equals(purchaseState.getValue()))
				{
					msg = "�Ϲ�";
				} else
				{
					msg = "�޸�";
				}
				btnEdit.setText(msg);
				btnEdit.setToolTipText(msg);
				menuItemEdit.setText(msg);
				menuItemEdit.setToolTipText(msg);
			}
		}
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * Ԥ������״̬�ļ�¼,���޸İ�ť�Ƿ��Ϊ�Ϲ�
	 * */
	protected boolean isCanSetEditTextToPurchase()
	{
		return true;
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception
	{
		super.menuItemImportData_actionPerformed(e);
	}

	protected String getEditUIName()
	{
		return PurchaseEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return PurchaseFactory.getRemoteInstance();
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
				"purchaseState").getValue();
		if (purchaseState.getValue().equals(
				PurchaseStateEnum.PREPURCHASEAPPLY_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.PREPURCHASECHECK_VALUE))
		{
			MsgBox.showInfo("�Ϲ���û���ύ!");
			return;
		}
		if (purchaseState.getValue().equals(
				PurchaseStateEnum.PURCHASEAUDIT_VALUE))
		{
			MsgBox.showInfo("�Ϲ����Ѿ����!");
			return;
		}
		if (isHasBlankOut(purchaseState))
		{
			MsgBox.showInfo("�Ϲ����Ѿ�����!");
			return;
		}
		if (purchaseState.getValue().equals(PurchaseStateEnum.PURCHASECHANGE_VALUE))
		{
			MsgBox.showInfo("�Ϲ�����в��ܱ�������");
			return;
		}
		
		
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PurchaseFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell("purchaseState").getValue();
		if (purchaseState.getValue().equals(
				PurchaseStateEnum.PREPURCHASEAPPLY_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.PREPURCHASECHECK_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.PURCHASEAPPLY_VALUE))
		{
			MsgBox.showInfo("�Ϲ���û�����!");
			return;
		}

		if (isHasBlankOut(purchaseState))
		{
			MsgBox.showInfo("�Ϲ����Ѿ�����!");
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		filter.getFilterItems().add(new FilterItemInfo("purchase.id", id));
		RoomSignContractCollection signs = RoomSignContractFactory
				.getRemoteInstance().getRoomSignContractCollection(view);
		if (signs.size() > 0)
		{
			MsgBox.showInfo("�Ѿ���ǩԼ��,���ܷ�����!");
			return;
		}
		
		if(PurchaseChangeFactory.getRemoteInstance().exists("where purchase.id = '"+id+"'")){
			MsgBox.showInfo("���Ϲ����Ѵ��ڶ�Ӧ�ı���������ܷ�������");
			return;
		}
		
//		Set moneySet = new HashSet();
//		moneySet.add(MoneyTypeEnum.EARNESTMONEY_VALUE);
//		moneySet.add(MoneyTypeEnum.FISRTAMOUNT_VALUE);
//		moneySet.add(MoneyTypeEnum.HOUSEAMOUNT_VALUE);
//		moneySet.add(MoneyTypeEnum.LOANAMOUNT_VALUE);
//		moneySet.add(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//		moneySet.add(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE);
//		if (SHEHelper.isHaveGatheringBySpecialMoney(moneySet, id))
//		{
//			MsgBox.showWarning("���Ϲ�����Ӧ�ĸ�����ϸ�ѽ��й�ʵ���տ���ܽ��з�����������");
//			return;
//		}
		if (MsgBox.showConfirm2New(this, "ȷ�Ϸ������?") == MsgBox.YES)
		{
			PurchaseFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			this.refresh(null);
		}
	}

	/**
	 * ��ȡ�Ϲ�����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	private PurchaseInfo getPurchaseInfo(String id)
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("payListEntry.*");
		selColl.add("payListEntry.moneyDefine.*");
		PurchaseInfo purchase = null;
		try
		{
			purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
					new ObjectUuidPK(BOSUuid.read(id)), selColl);
		} catch (Exception e)
		{
			this.handUIExceptionAndAbort(e);
		}
		return purchase;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
				"purchaseState").getValue();
		if (isHasBlankOut(purchaseState))
		{
			MsgBox.showInfo("�Ϲ����Ѿ�����,�������޸�!");
			return;
		}
		if (purchaseState.getValue().equals(
				PurchaseStateEnum.PURCHASEAUDITING_VALUE))
		{
			MsgBox.showInfo("�Ϲ�����������,�����޸�!");
			return;
		}
		if (purchaseState.getValue().equals(
				PurchaseStateEnum.PURCHASECHANGE_VALUE))
		{
			MsgBox.showInfo("�Ϲ����ѱ��,�����޸�!");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * �ж�purchaseState״̬�Ƿ�Ϊ����״̬
	 */
	private boolean isHasBlankOut(BizEnumValueInfo purchaseState)
	{
		return purchaseState.getValue().equals(
				PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.QUITROOMBLANKOUT_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.NOPAYBLANKOUT_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.MANUALBLANKOUT_VALUE)
				|| purchaseState.getValue().equals(
						PurchaseStateEnum.ADJUSTBLANKOUT_VALUE);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);

		BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
				"purchaseState").getValue();
		if (!purchaseState.getValue().equals(
				PurchaseStateEnum.MANUALBLANKOUT_VALUE)
				&& !purchaseState.getValue().equals(
						PurchaseStateEnum.NOPAYBLANKOUT_VALUE))
		{
			MsgBox.showInfo("�ֹ�����״̬��δ�������ϵ��Ϲ����ſ���ɾ��!");
			return;
		}

		String purchaseId = (String) row.getCell("id").getValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("purchase.id", purchaseId));
		if (FDCReceiveBillFactory.getRemoteInstance().exists(filter))
		{
			MsgBox.showInfo("�ѹ����տ,����ɾ��!");
			return;
		}
		FilterInfo purFilter = new FilterInfo();
		purFilter.getFilterItems().add(
				new FilterItemInfo("newPurchase.id", purchaseId));
		
		if(ChangeRoomFactory.getRemoteInstance().exists(purFilter)){
			MsgBox.showInfo("�ѹ���������,����ɾ��!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String idStr = (String)row.getCell(this.getKeyFieldName()).getValue();
		PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
				"select sincerityPurchase.*,state,purchaseState,payListEntry.*,elsePayListEntry.*,room.* where id = '"+idStr+"' ");

		if (!purInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply)
				&& !purInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck)
				&& !purInfo.getPurchaseState().equals(PurchaseStateEnum.PurchaseApply))		{
			MsgBox.showInfo("ֻ��״̬ΪԤ�����룬Ԥ�����ˣ��Ϲ�������Ϲ�����������!");
			return;
		}

		boolean hasRev = false;	//ֻҪ�չ�������տ�
		for(int i=0;i<purInfo.getPayListEntry().size();i++) {
			PurchasePayListEntryInfo payEntry = purInfo.getPayListEntry().get(i);
			if(payEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
				hasRev = true;
				break;
			}
		}
		if(!hasRev) {
			for(int i=0;i<purInfo.getElsePayListEntry().size();i++) {
				PurchaseElsePayListEntryInfo elseEntry = purInfo.getElsePayListEntry().get(i);
				if(elseEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					hasRev = true;
					break;
				}
			}	
		}
		
		if (hasRev)		{
			MsgBox.showInfo("�Ѿ����տ,��������!");
			return;
		}
		
		if (MsgBox.showConfirm2New(this, "���Ϻ󲻿ɻָ�����ȷ���Ƿ�����?") == MsgBox.YES)	{
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setPurchaseState(PurchaseStateEnum.ManualBlankOut);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			sels.add("purchaseState");
			PurchaseFactory.getRemoteInstance().updatePartial(purInfo, sels);

			//������ĳ����Ϲ���Ϊ�ź�״̬ 
			SincerityPurchaseInfo spur = null;
			if(purInfo.getSincerityPurchase() != null){
				spur = purInfo.getSincerityPurchase();
				spur.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
				SelectorItemCollection sic  = new SelectorItemCollection();
				sic.add("sincerityState");
				SincerityPurchaseFactory.getRemoteInstance().updatePartial(spur, sic);
			}
			RoomInfo roomInfo =  purInfo.getRoom();
			roomInfo.setSellState(RoomSellStateEnum.OnShow);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			roomInfo.setToPurchaseDate(null);
			roomInfo.setToSaleDate(null);
			roomInfo.setToPrePurchaseDate(null);
			roomInfo.setToSignDate(null);
			roomInfo.setLastPurchase(null);
			roomInfo.setLastSignContract(null);
			roomInfo.setLastAreaCompensate(null);
			roomInfo.setAreaCompensateAmount(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("toPrePurchase");
			roomUpdateSel.add("toSignDate");
			roomUpdateSel.add("lastPurchase");
			roomUpdateSel.add("lastSignContract");
			roomUpdateSel.add("lastAreaCompensate");
			roomUpdateSel.add("areaCompensateAmount");
			RoomFactory.getRemoteInstance().updatePartial(roomInfo, roomUpdateSel);
			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(purInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++)
			{
				if ("open.running".equals(procInsts[j].getState()))
				{
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}

			}
			this.refresh(null);
		}
		super.actionBlankOut_actionPerformed(e);
	}

	/**
	 * ��ǩ output actionWebMark_actionPerformed method
	 */
	public void actionWebMark_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		UIContext uiContext = new UIContext(this);
		String sOprt = OprtState.ADDNEW;
		uiContext.put(UIContext.ID, null);
		uiContext.put(UIContext.OWNER, this);
		String sellProjId = "";
		try
		{
			// ��Ŀ
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node.getUserObject() instanceof Integer)
			{ // ������
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				SellProjectInfo sellProjInfo = building.getSellProject();
				sellProjId = sellProjInfo.getId().toString();
			} else if (node.getUserObject() instanceof BuildingUnitInfo)
			{
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				SellProjectInfo sellProjInfo = building.getSellProject();
				sellProjId = sellProjInfo.getId().toString();
			} else if (node.getUserObject() instanceof BuildingInfo)
			{
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				if (!building.getCodingType().equals(
						CodingTypeEnum.UnitFloorNum))
				{
					// buildingId = building.getId().toString();
					SellProjectInfo sellProjInfo = building.getSellProject();
					sellProjId = sellProjInfo.getId().toString();
				}
			}
			// ����
			ICell cell = tblMain.getRow(
					tblMain.getSelectManager().getActiveRowIndex()).getCell(
					"room.number");
			String roomNumber = cell.getValue().toString();

			// String roomID = this.tblMain.getCell(
			// tblMain.getSelectManager().getActiveRowIndex(), "id")
			// .toString();
			if (sellProjId.trim().equals(""))
			{
				FDCMsgBox.showInfo("��ĿȡֵΪ�գ�");
				return;
			}
			if (roomNumber.trim().equals(""))
			{
				FDCMsgBox.showInfo("����ȡֵΪ�գ�");
				return;
			}
			uiContext.put("sellProjID", sellProjId);
			uiContext.put("roomNumber", roomNumber);
//			IUIWindow uiWindow = UIFactory
//					.createUIFactory(UIFactoryName.NEWTAB).create(
//							WebMarkPutInEditUI.class.getName(), uiContext,
//							null, sOprt);
//			uiWindow.show();
		} catch (Exception ex)
		{
			ex = null;
		}
	}

	/**
	 * Ԥ�����˲���
	 */
	public void actionCheckPrePurchase_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionCheckPrePurchase_actionPerformed(e);

		checkSelected();
		if (!verifySelectedState(PurchaseStateEnum.PrePurchaseApply))
		{
			MsgBox.showInfo(this, "ֻ��Ԥ������ļ�¼�ſ��Ա�����");
			return;
		}

		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;

		ArrayList idList = this.getSelectedIdValues();

		if (idList != null)
		{
			for (int j = 0; j < idList.size(); j++)
			{
				ProcessInstInfo[] procInsts = service
						.getProcessInstanceByHoldedObjectId(idList.get(j)
								.toString());
				for (int i = 0, n = procInsts.length; i < n; i++)
				{
					if (procInsts[i].getState().startsWith("open"))
					{
						processInstInfo = procInsts[i];
					}
				}
				if (processInstInfo != null)
				{
					MsgBox.showInfo("�˵��ݴ������̵��У����������и��ˣ�");
					this.abort();

				}
			}
		}
		PurchaseFactory.getRemoteInstance().checkPrePurchase(
				FDCHelper.idListToPKArray(this.getSelectedIdValues()));
		this.refresh(null);
	}

	/**
	 * ��֤Tableѡ���м�¼���Ϲ�״̬�Ƿ񶼺�targetStateƥ��
	 */
	private boolean verifySelectedState(PurchaseStateEnum targetState)
	{
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++)
		{
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo purchaseState = (BizEnumValueInfo) row.getCell(
					"purchaseState").getValue();
			if (purchaseState == null)
			{
				logger.warn("����������,����״̬ΪNull,���顣");
				continue;
			}

			if (!purchaseState.getValue().equals(targetState.getValue()))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Ԥ�������˲���
	 */
	public void actionUncheckPrePurchase_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionUncheckPrePurchase_actionPerformed(e);
		checkSelected();
		if (!verifySelectedState(PurchaseStateEnum.PrePurchaseCheck))
		{
			MsgBox.showInfo(this, "ֻ��Ԥ�����˵ļ�¼�ſ��Ա�������");
			return;
		}
		PurchaseFactory.getRemoteInstance().uncheckPrePurchase(
				FDCHelper.idListToPKArray(this.getSelectedIdValues()));
		this.refresh(null);
	}

	/**
	 * Ԥ������
	 * */
	public void actionPrePurchase_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionPrePurchase_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("isPrePurchase", Boolean.TRUE);// ���Ϲ���Ԥ������
		setUIContextAboutTreeNode(uiContext);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(PurchaseEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	protected String[] getLocateNames()
	{
		String[] locateNames = new String[4];
		locateNames[0] = "number";
		locateNames[1] = "state";
		locateNames[2] = "room.number";
		locateNames[3] = "customer";
		return locateNames;
	}
}