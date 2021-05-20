/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.IFDCOrgStructure;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class FDCOrgStructureListUI extends AbstractFDCOrgStructureListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5727055504462761476L;
	private static final Logger logger = CoreUIObject
			.getLogger(FDCOrgStructureListUI.class);

	private static final String DEF_CU = "00000000-0000-0000-0000-00000000000632B85C74";
	private static final String DEF_CU_UNIT = "00000000-0000-0000-0000-000000000000CCE7AED4";
	private HashMap selectMap = new HashMap();
	private List idList = new ArrayList();
	private List rowList = new ArrayList();
	private boolean isAll = false;
	private HashMap valueMap = new HashMap();
	private HashMap valueCustMap = new HashMap();
	private String alertMsg = "";
	private String alertCustomerMsg = "";

	/**
	 * output class constructor
	 */
	public FDCOrgStructureListUI() throws Exception {
		super();

	}

	protected void initTree() throws Exception {
		DefaultTreeModel root = null;
		if (isAll) {
			root = FDCTreeHelper.getSaleOrgTreeForAll(actionOnLoad,
					OrgConstants.SALE_VIEW_ID, OrgConstants.DEF_CU_ID, true,
					false);

		} else {
			root = FDCTreeHelper.getSaleOrgTreeForAll(actionOnLoad,
					OrgConstants.SALE_VIEW_ID, OrgConstants.DEF_CU_ID, false,
					false);

		}
		if (root != null) {
			treeMain.setModel(root);
			treeMain.expandOnLevel(1);
		} else {
			treeMain.setModel(null);
			return;
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			FDCMsgBox.showWarning("�Ǽ����û������޸�ҵ����֯����!");
			this.abort();
		}
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);

		this.tblMain.getStyleAttributes().setLocked(true);
		this.selectMap.clear();
		updateData();
		
	}

	private void updateData() throws BOSException, EASBizException {
		// ����updateData������ͬ������
		IFDCOrgStructure fdcOrg = FDCOrgStructureFactory.getRemoteInstance();
		fdcOrg.updateData();
		/**
		 * ���ĵ�̬��...�������ݲ���ͬ��
		 * �����ڸ��������ݺ�,������ô˷�����ͬ�����ݲſ��԰�!
		 */
		FDCSysContext.getInstance().synOrgmap();
	}

	public void onShow() throws Exception {
		super.onShow();
		this.btnAll.setIcon(EASResource.getIcon("imgTbtn_find"));
		this.btnPart.setIcon(EASResource.getIcon("imgTbtn_showsealup"));
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.actionSave.setVisible(true);
		this.actionSave.setEnabled(false);
		this.actionViewAll.setVisible(true);
		this.actionViewAll.setEnabled(true);
		this.actionViewPart.setVisible(false);
		this.actionViewPart.setEnabled(false);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);

		if (!this.selectMap.isEmpty()) {
			checkGroup();
			getSelectData();
			int comfim = JOptionPane.NO_OPTION;
			if (!"".equals(this.alertMsg) && this.alertMsg.length() > 0
					&& !"".equals(this.alertCustomerMsg)
					&& this.alertCustomerMsg.length() > 0) {
				String temp1 = "����:��¥��֯����������Ҫ��������Ӫ���Ŷӣ���������Ӧ��ϵͳ!";
				temp1 = temp1 + "\n";
				temp1 = temp1 + "����:�ͻ�������֯��������ǳ���ʱ�������������";
				comfim = FDCMsgBox.showConfirm3(this, temp1);

			} else if (!"".equals(this.alertMsg) && this.alertMsg.length() > 0) {
				comfim = FDCMsgBox.showConfirm3(this,
						"���棺��¥��֯����������Ҫ��������Ӫ���Ŷӣ���������Ӧ��ϵͳ����ȷ��Ҫ�����¼��?");
			} else if (!"".equals(this.alertCustomerMsg)
					&& this.alertCustomerMsg.length() > 0) {
				comfim = FDCMsgBox.showConfirm3(this,
						"���棺�ͻ�������֯��������ǳ���ʱ���������������ȷ��Ҫ�����¼��?");
			} else {
				comfim = FDCMsgBox.showConfirm3(this, "��ȷ��Ҫ�����¼��?");
			}

			if (comfim == JOptionPane.YES_OPTION) {
				// �����¼
				try {

					IFDCOrgStructure fdcOrg = FDCOrgStructureFactory
							.getRemoteInstance();
					fdcOrg.saveData(this.idList);
				} catch (Exception ex) {
					logger.error(ex.getMessage() + "��������ʧ�ܣ���");
				} finally {
					this.selectMap.clear();
					this.tblMain.removeRows();
					this.actionSave.setEnabled(false);
					this.actionEdit.setEnabled(true);
					this.idList.clear();
					this.valueMap.clear();
					this.valueCustMap.clear();
					this.alertMsg = "";
					this.alertCustomerMsg = "";
				}
				//ͬ��FDCSysContext eric_wang;
				FDCSysContext.getInstance().synOrgmap();
			} else if (comfim == JOptionPane.NO_OPTION) {
				this.selectMap.clear();
				this.tblMain.removeRows();
				this.actionSave.setEnabled(false);
				this.actionEdit.setEnabled(true);
				this.idList.clear();
				this.valueMap.clear();
				this.valueCustMap.clear();
				this.alertMsg = "";
				this.alertCustomerMsg = "";
			}

		}
	
	}

	private void checkGroup() {

		for (int i = 0; i < this.rowList.size(); i++) {
			int select = Integer.parseInt(rowList.get(i).toString());
			IRow row = this.tblMain.getRow(select);
			if (row == null) {
				return;
			}
			if (row.getCell("unit.id").getValue() != null) {
				if (DEF_CU_UNIT.equals((String) row.getCell("unit.id")
						.getValue())) {
					if (row.getCell("custMangageUnit").getValue() != null) {
						Boolean cust = (Boolean) row.getCell("custMangageUnit")
								.getValue();
						if (!cust.booleanValue()) {
							FDCMsgBox.showWarning(this,
									"��ѡ������д�����߼������Ԫ,�������޸Ŀͻ�������֯����!");
							this.selectMap.clear();
							this.tblMain.removeRows();
							this.actionSave.setEnabled(false);
							this.actionEdit.setEnabled(true);
							this.idList.clear();
							this.valueMap.clear();
							this.valueCustMap.clear();
							this.alertMsg = "";
							this.alertCustomerMsg = "";
							SysUtil.abort();
							
							break;
						}
					}

				}
			}
		}
	}

	public void actionViewAll_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewAll_actionPerformed(e);
		this.actionViewAll.setVisible(false);
		this.actionViewAll.setEnabled(false);
		this.actionViewPart.setVisible(true);
		this.actionViewPart.setEnabled(true);
		this.isAll = true;
		this.initTree();
		this.execQuery();

	}

	public void actionViewPart_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewPart_actionPerformed(e);
		this.actionViewPart.setVisible(false);
		this.actionViewPart.setEnabled(false);
		this.actionViewAll.setVisible(true);
		this.actionViewAll.setEnabled(true);
		this.isAll = false;
		this.initTree();
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo selectInfo = (OrgStructureInfo) node
						.getUserObject();

				if (DEF_CU.equals(selectInfo.getId().toString())) {
					filter.getFilterItems().add(
							new FilterItemInfo("tree.id",
									OrgConstants.SALE_VIEW_ID,
									CompareType.EQUALS));
				} else {

					if (this.isAll) {
						OrgStructureInfo selectOrgInfo = (OrgStructureInfo) node
								.getUserObject();

						filter.getFilterItems().add(
								new FilterItemInfo("tree.id",
										OrgConstants.SALE_VIEW_ID,
										CompareType.EQUALS));

						filter.getFilterItems().add(
								new FilterItemInfo("longNumber", selectOrgInfo
										.getLongNumber(), CompareType.EQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("longNumber", selectOrgInfo
										.getLongNumber()
										+ "!%", CompareType.LIKE));

					} else {
					
						OrgStructureInfo selectOrgInfo = (OrgStructureInfo) node
						.getUserObject();
						
						FilterInfo newFilter = new FilterInfo();
						newFilter.getFilterItems().add(
								new FilterItemInfo("tree.id",
										OrgConstants.SALE_VIEW_ID,
										CompareType.EQUALS));

						newFilter.getFilterItems().add(
								new FilterItemInfo("longNumber", selectOrgInfo
										.getLongNumber(), CompareType.EQUALS));
						newFilter.getFilterItems().add(
								new FilterItemInfo("longNumber", selectOrgInfo
										.getLongNumber()
										+ "!%", CompareType.LIKE));
						
						if (!this.isAll) {
							newFilter.getFilterItems().add(
									new FilterItemInfo("unit.isOUSealUp",
											Boolean.FALSE, CompareType.EQUALS));

						}
						newFilter.setMaskString("#0 and (#1 or #2) and #3");
						if(newFilter!=null){
							try {
								filter.mergeFilter(newFilter, "and");
							} catch (BOSException e) {
								logger.error(e.getMessage());
							}
						}
					}

				}

				if (!this.isAll) {
					filter.getFilterItems().add(
							new FilterItemInfo("unit.isOUSealUp",
									Boolean.FALSE, CompareType.EQUALS));

				}

			}
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			if (!this.isAll) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.isOUSealUp", Boolean.FALSE,
								CompareType.EQUALS));

			}
		}

		filter.getFilterItems().add(
				new FilterItemInfo("unit.name", "null", CompareType.NOTEQUALS));

		if (this.isAll) {
			if (node != null) {
				if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo selectInfo = (OrgStructureInfo) node
							.getUserObject();
					if (DEF_CU.equals(selectInfo.getId().toString())) {
						filter.setMaskString("#0 and #1");
					} else {
						filter.setMaskString("#0 and (#1 or #2) and #3");
					}
				}
			}
		}
		// �ϲ���ѯ����
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!this.isAll){
			FilterInfo isHisFilter =new FilterInfo();
			isHisFilter.getFilterItems().add(new FilterItemInfo("isHis",Boolean.TRUE,CompareType.NOTEQUALS));
			try {
				viewInfo.getFilter().mergeFilter(isHisFilter, "and");
			} catch (BOSException e) {
				logger.error(e.getMessage()+"�ϲ�filterʧ��!");
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void refresh(ActionEvent e) throws Exception {

		if (!this.selectMap.isEmpty()) {
			checkGroup();
			getSelectData();
			int comfim = JOptionPane.NO_OPTION;

			if (!"".equals(this.alertMsg) && this.alertMsg.length() > 0
					&& !"".equals(this.alertCustomerMsg)
					&& this.alertCustomerMsg.length() > 0) {
				String temp1 = "���棺��¥��֯����������Ҫ��������Ӫ���Ŷӣ���������Ӧ��ϵͳ!";
				temp1 = temp1 + "\n";
				temp1 = temp1 + "���棺�ͻ�������֯��������ǳ���ʱ�������������";
				comfim = FDCMsgBox.showConfirm3(this, temp1);

			} else if (!"".equals(this.alertMsg) && this.alertMsg.length() > 0) {
				comfim = FDCMsgBox.showConfirm3(this,
						"���棺��¥��֯����������Ҫ��������Ӫ���Ŷӣ���������Ӧ��ϵͳ�����Ѿ��޸��˼�¼,Ҫ������?");
			} else if (!"".equals(this.alertCustomerMsg)
					&& this.alertCustomerMsg.length() > 0) {
				comfim = FDCMsgBox.showConfirm3(this,
						"���棺�ͻ�������֯��������ǳ���ʱ���������������ȷ��Ҫ�����޸ĵļ�¼��?");
			} else {
				comfim = FDCMsgBox.showConfirm3(this, "���Ѿ��޸��˼�¼��Ҫ������?");
			}

			if (comfim == JOptionPane.YES_OPTION) {
				// �����¼
				try {

					IFDCOrgStructure fdcOrg = FDCOrgStructureFactory
							.getRemoteInstance();
					fdcOrg.saveData(this.idList);
				} catch (Exception ex) {
					logger.error(ex.getMessage() + "��������ʧ�ܣ���");
				} finally {
					this.selectMap.clear();
					this.tblMain.removeRows();
					this.idList.clear();
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.valueMap.clear();
					this.valueCustMap.clear();
					this.alertMsg = "";
					this.alertCustomerMsg = "";
				}
			} else if (comfim == JOptionPane.NO_OPTION) {
				this.selectMap.clear();
				this.tblMain.removeRows();
				this.idList.clear();
				this.actionEdit.setEnabled(true);
				this.actionSave.setEnabled(false);
				this.valueMap.clear();
				this.valueCustMap.clear();
				this.alertMsg = "";
				this.alertCustomerMsg = "";
			}
		} else {
			updateData();
			this.tblMain.removeRows();
		}
		
		this.initTree();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private void getSelectData() {
		if (!this.selectMap.isEmpty()) {

			for (int i = 0; i < this.rowList.size(); i++) {

				int select = Integer.parseInt(rowList.get(i).toString());
				IRow row = this.tblMain.getRow(select);
				if (row == null) {
					return;
				}
				FDCOrgStructureInfo info = new FDCOrgStructureInfo();

				if (row.getCell("id").getValue() != null) {
					info.setId(BOSUuid.read(row.getCell("id").getValue()
							.toString()));
				}
				if (row.getCell("custMangageUnit").getValue() != null) {
					Boolean cust = (Boolean) row.getCell("custMangageUnit")
							.getValue();
					if (!this.valueCustMap.isEmpty()) {
						String temp = (String) this.valueCustMap.get(select
								+ "");
						Boolean valueTemp = Boolean.valueOf(temp);
						if (valueTemp.booleanValue() && !cust.booleanValue()) {
							this.alertCustomerMsg = this.alertCustomerMsg
									+ "custMange change,";
						}
					}

					if (cust.booleanValue()) {
						info.setCustMangageUnit(true);
					} else {
						info.setCustMangageUnit(false);
					}
				} else {
					info.setCustMangageUnit(false);
				}
				if (row.getCell("sellHouseStrut").getValue() != null) {

					Boolean sell = (Boolean) row.getCell("sellHouseStrut")
							.getValue();
					if (!this.valueMap.isEmpty()) {
						String temp = (String) this.valueMap.get(select + "");
						Boolean valueTemp = Boolean.valueOf(temp);
						if (valueTemp.booleanValue() && !sell.booleanValue()) {
							this.alertMsg = this.alertMsg + "change,";
						}
					}
					if (sell.booleanValue()) {
						info.setSellHouseStrut(true);
					} else {
						info.setSellHouseStrut(false);
					}
				} else {
					info.setSellHouseStrut(false);
				}
				this.idList.add(info);
			}

		}
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		if (this.selectMap.isEmpty()) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			if (selectRows == null || selectRows.length <= 0) {
				FDCMsgBox.showWarning(this, "����ѡ���¼!");
				SysUtil.abort();
			}
			List list = new ArrayList();
			for (int i = 0; i < selectRows.length; i++) {
				int select = selectRows[i];
				IRow row = this.tblMain.getRow(select);
				if (row == null) {
					continue;
				}
				if (row.getCell("unit.isOUSealUp").getValue() != null) {
					boolean res = ((Boolean) row.getCell("unit.isOUSealUp")
							.getValue()).booleanValue();

					if (res) {
						FDCMsgBox.showWarning(this, "��ѡ��ļ�¼�Ѿ����,�������޸�!");
						SysUtil.abort();
						break;
					}
				}

				list.add(select + "");
			}
			for (int i = 0; i < list.size(); i++) {
				int select = Integer.parseInt(list.get(i).toString());
				IRow row = this.tblMain.getRow(select);
				if (row == null) {
					continue;
				}
				row.getCell("custMangageUnit").getStyleAttributes().setLocked(
						false);
				row.getCell("sellHouseStrut").getStyleAttributes().setLocked(
						false);
				this.valueCustMap.put(select + "", row.getCell(
						"custMangageUnit").getValue().toString());
				this.valueMap.put(select + "", row.getCell("sellHouseStrut")
						.getValue().toString());
				this.selectMap.put(select + "", row.getCell("id").getValue()
						.toString());
				this.actionEdit.setEnabled(false);
				this.rowList.add(select + "");
			}
		}
		this.actionSave.setEnabled(true);

	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCOrgStructureFactory.getRemoteInstance();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.execQuery();
	}

	protected String[] getLocateNames() {

		String[] locateNames = new String[4];
		locateNames[0] = "unit.name";
		locateNames[1] = "unit.number";
		locateNames[2] = "unit.code";
		locateNames[3] = "unit.simpleName";
		return locateNames;
	}
}