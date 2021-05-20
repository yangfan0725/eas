/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Arrays;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgCollection;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgFactory;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:������֯��ͼά��
 * @author adward_huang  date:2012-10-16
 * @version EAS6.1
 */
public class ScheduleReportTreeMaintenanceUI extends AbstractScheduleReportTreeMaintenanceUI {

	private static final long serialVersionUID = 6817881287428224769L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportTreeMaintenanceUI.class);

	private static final String NUMBER = "number";// ����
	private static final String NAME = "name";// ����
	private static final String PARENT_NUMBER = "parentNumber";//������
	private static final String PARENT_NAME = "parentName";// ������
	private static final String REMARK = "remark";// ��ע
	private static final String TREE_CELL = "treeCell";// ������Ԫ��

	// ������
	private KDWorkButton addRowBtn;

	// ɾ����
	private KDWorkButton deleteRowBtn;

	// ������
	private KDWorkButton insertRowBtn;

	/*	// ������
		private KDWorkButton preRemoveRowBtn;
		
		// ������
		private KDWorkButton nextRemoveRowBtn;*/

	// ����ı�Ľڵ���Ϣ�������������޸ĵĽڵ���Ϣ
	private Map<String, ScheduleReportOrgInfo> cacheUpdateTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// ��������ݿ��м��صĽڵ���Ϣ��������һ�δ����ݿ���ص����нڵ���Ϣ
	private Map<String, ScheduleReportOrgInfo> cacheLoadTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// ����ı�Ľڵ���Ϣ������ɾ���Ľڵ���Ϣ
	private Map<String, ScheduleReportOrgInfo> cacheDeleteTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// ���游�ڵ���ӽڵ������Ϣ
	private Map<String, Integer> cacheChildrenSize = new HashMap<String, Integer>();

	// �Ƿ�Ϊ������֯
	private boolean isGroupOrg = false;

	// ���ڱ�����Ĳ�������
	private int actionType;

	// ���ı༭״̬  
	private boolean isEditable = false;

	public ScheduleReportTreeMaintenanceUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		isGroupOrg = isGroupOrg();
		if (isGroupOrg) {
			super.onLoad();
			initUIState();
			initPanel();
		} else {
			MsgBox.showInfo("���л���������֯�½��б�����֯��ͼά��������");
			return;
		}
	}

	/**
	 * �������жϵ�ǰ��֯�Ƿ�Ϊ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	private boolean isGroupOrg() {
		String curOrgID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();

		boolean isGroup = false;
		// �����ǰ��֯Ϊ����
		if (FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID.equals(curOrgID)) {
			isGroup = true;
			// ����Ƿ���ڼ��Žڵ㣬�������������ӣ����������
			addDefaultGroupTreeNode();

		}
		return isGroup;
	}

	/**
	 * ��������ʼ������
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��adward_huang
	 * @CreateTime��2012-10-12
	 */
	private void initPanel() throws EASBizException, BOSException {
		addBtnOnContainer();
		fillTable();
	}

	/**
	 * ��������Ӱ�ť���¼�
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	private void addBtnOnContainer() {
		addRowBtn = new KDWorkButton("������");
		deleteRowBtn = new KDWorkButton("ɾ����");
		insertRowBtn = new KDWorkButton("������");
		//		preRemoveRowBtn = new KDWorkButton("������");
		//		nextRemoveRowBtn = new KDWorkButton("������");
		this.addRowBtn.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.deleteRowBtn.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.insertRowBtn.setIcon(EASResource.getIcon("imgTbtn_insert"));
		//		this.preRemoveRowBtn.setIcon(EASResource.getIcon("imgTree_derivation"));
		//		this.nextRemoveRowBtn.setIcon(EASResource.getIcon("imgTree_import"));
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_ADD;
				// �����в���
				addRowData();
			}
		});

		deleteRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_DELETE;
				// ɾ���в���
				deleteRowData();
			}
		});

		insertRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// �����в���
				insertRowData();
			}
		});

		/*preRemoveRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// �����в���
				preRemoveRowData();
			}
		});
		
		nextRemoveRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// �����в���
				nextRemoveRowData();
			}
		});*/

		//		tableContainer.addButton(preRemoveRowBtn);
		//		tableContainer.addButton(nextRemoveRowBtn);
		tableContainer.addButton(addRowBtn);
		tableContainer.addButton(deleteRowBtn);
		tableContainer.addButton(insertRowBtn);
	}

	/**
	 * ��������ʼ�������齨״̬
	 * @Author��adward_huang
	 * @CreateTime��2012-10-12
	 */
	private void initUIState() {
		this.saveBtn.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.updateBtn.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.refreshBtn.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.treeTable.setEditable(true);
		this.isEditable = false;
		this.treeTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * ���������������
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	public void fillTable() throws EASBizException, BOSException {
		KDTable table = this.treeTable;
		table.checkParsed();
		table.removeRows();
		treeTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		// ��ȡ���ڵ�����
		ScheduleReportOrgInfo rootInfo = ScheduleChartHelper.getRootNodeInfo();

		// ���ظ��ڵ�
		addRootNode(table, rootInfo);

		// ��ȡ������������
		List<ScheduleReportOrgInfo> allTreeNodeData = ScheduleChartHelper.getAllTreeNodeData(rootInfo);

		// ��ʼ����������
		initCacheData(rootInfo, allTreeNodeData);

		// ���س����ڵ����������������
		generateTreeNode(table, rootInfo, allTreeNodeData);

		// ��ʼ����Ԫ��Ŀɱ༭״̬
		setEditColumn(!isEditable);
	}

	/**
	 * ���������ñ��Ԫ��༭״̬
	 * @param flag		false���ɱ༭״̬  true:���ɱ༭״̬
	 * @Author��adward_huang
	 * @CreateTime��2012-10-28
	 */
	private void setEditColumn(boolean flag) {

		int rowCount = this.treeTable.getRowCount();
		IRow row = null;
		for (int i = 0; i < rowCount; i++) {
			row = treeTable.getRow(i);
			row.getCell(TREE_CELL).getStyleAttributes().setLocked(false);
			row.getCell(NUMBER).getStyleAttributes().setLocked(flag);
			row.getCell(NAME).getStyleAttributes().setLocked(flag);
			row.getCell(REMARK).getStyleAttributes().setLocked(flag);
			row.getCell(PARENT_NUMBER).getStyleAttributes().setLocked(true);
			row.getCell(PARENT_NAME).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * ��������ʼ����������
	 * @param rootInfo
	 * @param allTreeNodeData
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	private void initCacheData(ScheduleReportOrgInfo rootInfo, List<ScheduleReportOrgInfo> allTreeNodeData) {

		Map<String, ScheduleReportOrgInfo> allDataMap = ScheduleChartHelper.getAllDataMap(allTreeNodeData);
		allDataMap.put(rootInfo.getId().toString(), rootInfo);
		// ��������ݿ��м��صĽڵ���Ϣ
		cacheLoadTreeNodeMap.putAll(allDataMap);

		for (ScheduleReportOrgInfo org : allTreeNodeData) {
			if (org.getParent() != null) {
				String parentID = org.getParent().getId().toString();
				// ��������ӽڵ㣬���1
				if (cacheChildrenSize.containsKey(parentID)) {
					int size = cacheChildrenSize.get(parentID);
					cacheChildrenSize.put(parentID, size + 1);
				} else {
					cacheChildrenSize.put(parentID, 1);
				}
			}
		}
	}

	/**
	 * ������������ȡ�������ڵ�
	 * @param node
	 * @throws BOSException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	private void generateTreeNode(KDTable table, ScheduleReportOrgInfo orgInfo, List<ScheduleReportOrgInfo> orderedTreeNodeList)
			throws BOSException {
		List<ScheduleReportOrgInfo> childrenOrgs = ScheduleChartHelper.getChildrenOrgs(orgInfo, orderedTreeNodeList);
		ScheduleChartHelper.sortedListBySortNumber(childrenOrgs);
		ScheduleReportOrgInfo child = null;
		for (int i = 0; i < childrenOrgs.size(); i++) {
			child = childrenOrgs.get(i);
			IRow row = table.addRow();
			row.setUserObject(child);
			if (child.getParent() != null && cacheLoadTreeNodeMap.containsKey(child.getParent().getId().toString())) {
				child.setParent(cacheLoadTreeNodeMap.get(child.getParent().getId().toString()));
			}
			loadRow(row);
			generateTreeNode(table, child, orderedTreeNodeList);
		}
	}

	/**
	 * ��������Ӹ��ڵ�
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	private void addRootNode(KDTable table, ScheduleReportOrgInfo root) {
		IRow row = table.addRow();
		row.setUserObject(root);
		root.setParent(null);
		loadRow(row);
	}

	/**
	 * ���������������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	private void addRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("��ѡ����������!");
			return;
		}

		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		Object parentNumber = treeTable.getRow(index).getCell(NUMBER).getValue();
		Object parentName = treeTable.getRow(index).getCell(NAME).getValue();
		if (FDCHelper.isEmpty(parentNumber) || FDCHelper.isEmpty(parentName)) {
			MsgBox.showWarning("����д���ڵ�ı�������ƺ������!");
			return;
		}

		ScheduleReportOrgInfo parent = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		parent.setNumber(parentNumber.toString());
		parent.setName(parentName.toString());
		org.setParent(parent);
		org.setLevel(parent.getLevel() + 1);
		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);

		//		int addIndex = getChildrenSize(parent);
		int addIndex = getAllChildrenSize(parent);
		cacheChildrenSize.put(parent.getId().toString(), addIndex + 1);
		IRow row = treeTable.addRow(index + 1 + addIndex);
		org.setSortNumber(addIndex + 1);
		row.setUserObject(org);
		loadRow(row);
	}

	/**
	 * ����������Ƿ�����ӽڵ���Ϣ
	 * @param parent
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	private boolean hasChildren(ScheduleReportOrgInfo parent) {
		BOSUuid parentId = parent.getId();
		int rowCount = treeTable.getRowCount();

		boolean flag = false;
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// ��������и��ڵ�ID������ID��������ӽڵ㣬����true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parentId.toString())) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	/**
	 * ��������ȡ�ӽڵ�����
	 * @param parent
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	private List<Integer> getAllChildrenIndex(ScheduleReportOrgInfo parent) {
		int rowCount = treeTable.getRowCount();

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		List<Integer> indexList = new ArrayList<Integer>();
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// ��������и��ڵ�ID������ID��������ӽڵ㣬����true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parent.getId().toString())) {
				indexList.add(i);
			}
		}
		return indexList;
	}

	/**
	 * ��������ȡ�ڵ������
	 * @param parent
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	@SuppressWarnings("unused")
	private int getNodeIndex(ScheduleReportOrgInfo node) {
		BOSUuid nodeId = node.getId();
		int rowCount = treeTable.getRowCount();

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		int index = 0;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// ��������и��ڵ�ID������ID��������ӽڵ㣬����true
			if (org != null && org.getId().toString().equals(nodeId.toString())) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * �������޸ĸýڵ��������нڵ����������
	 * @param orgInfo
	 * @param curIndex
	 * @param type		��������������Ӳ��������������ɾ������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-24
	 */
	private void updateChildrenSortNumber(ScheduleReportOrgInfo orgInfo, int curIndex, int type) {

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		// ��ȡ�����ӽڵ�����
		List<Integer> childrenIndexList = getAllChildrenIndex(orgInfo);
		for (Integer index : childrenIndexList) {

			if (curIndex > index) {
				continue;
			}

			// �������ӻ����в�����������ͬ�����ڲ���ʱ��������ǰ�еļ���
			if (actionType == FDCScheduleConstant.ACTION_TYPE_INSERT) {
				if (curIndex == index) {
					continue;
				}
			}

			row = treeTable.getRow(index);
			org = (ScheduleReportOrgInfo) row.getUserObject();

			if (type > 0) {
				org.setSortNumber(org.getSortNumber() + 1);
			} else {
				org.setSortNumber(org.getSortNumber() - 1);
			}
		}
	}

	/**
	 * ����������Ƿ������ͬ�Ľڵ���Ϣ
	 * @param parent
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	private boolean existSameNode(ScheduleReportOrgInfo info, List<String> list, ScheduleReportOrgInfo curOrg) {

		int rowCount = treeTable.getRowCount();
		boolean flag = false;
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 1.�жϹ��������Ƿ�����ͬһ����֯
			if (org.getRelateOrg() != null && info.getRelateOrg() != null) {
				if (org.getRelateOrg().getId().toString().equals(info.getRelateOrg().getId().toString())) {
					if (!info.getRelateOrg().getId().toString().equals(org.getRelateOrg().getId().toString())) {
						list.add(org.getName());
						flag = true;
						continue;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * �������ж��Ƿ�����ظ��Ľڵ���Ϣ
	 * @param sb
	 * @param duplicateIndex
	 * @param org
	 * @param dataMap
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	@SuppressWarnings("unused")
	private int hasDuplicateNode(StringBuffer sb, int duplicateIndex, ScheduleReportOrgInfo org, Map<String, ScheduleReportOrgInfo> dataMap) {
		ScheduleReportOrgInfo info = null;

		for (Iterator<Map.Entry<String, ScheduleReportOrgInfo>> iter = dataMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, ScheduleReportOrgInfo> map = iter.next();
			info = map.getValue();
			// �жϹ��������Ƿ�����ͬһ����֯
			if (org.getRelateOrg() != null && info.getRelateOrg() != null) {
				if (org.getRelateOrg().getId().toString().equals(info.getRelateOrg().getId().toString())) {
					duplicateIndex++;
					if (duplicateIndex > 1) {
						sb.append(",").append(org.getName());
					} else {
						sb.append(org.getName());
					}
					continue;
				}
			}
			// �ж��Ƿ������ͬ
			if (org.getNumber().equals(info.getNumber())) {
				duplicateIndex++;
				if (duplicateIndex > 1) {
					sb.append(",").append(org.getName());
				} else {
					sb.append(org.getName());
				}
				continue;
			}

		}
		return duplicateIndex;
	}

	/**
	 * ������ɾ��������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	private void deleteRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("��ѡ���������ݣ�����ɾ��!");
			return;
		}
		ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		if (hasChildren(org)) {
			MsgBox.showWarning("����֯����������֯�����ܽ���ɾ��!");
			return;
		}

		if (org.getRelateOrg() != null
				&& org.getRelateOrg().getId().toString().equals(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID)) {
			MsgBox.showWarning("������֯���ܽ���ɾ������!");
			return;
		}
		int deleteSize = getChildrenSize(org.getParent());
		cacheChildrenSize.put(org.getParent().getId().toString(), deleteSize - 1);

		IRow row = treeTable.removeRow(index);
		if (row != null) {
			updateChildrenSortNumber(org.getParent(), index, -1);
			// ��������ݴ����ڼ������ݼ��У��򻺴������ɾ������Ϣ
			if (cacheLoadTreeNodeMap.containsKey(org.getId().toString())) {
				cacheDeleteTreeNodeMap.put(org.getId().toString(), org);
			}
			MsgBox.showWarning("ɾ���ɹ�!");
		}
	}

	/**
	 * ����������������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	private void insertRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("��ѡ����������!");
			return;
		}

		if (index == 0) {
			MsgBox.showWarning("���ܲ����뼯��ƽ������֯������� ������ ����!");
			return;
		}

		ScheduleReportOrgInfo currentOrg = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();

		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		ScheduleReportOrgInfo parent = currentOrg.getParent();
		org.setParent(parent);
		org.setLevel(currentOrg.getLevel());
		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);

		int childrenSize = getChildrenSize(parent);
		cacheChildrenSize.put(parent.getId().toString(), childrenSize + 1);
		IRow row = treeTable.addRow(index);
		int sortNumber = getInsertRowSortNumber(parent, index);
		org.setSortNumber(sortNumber);
		row.setUserObject(org);
		loadRow(row);
		// �޸�ͬ���ýڵ�����ڵ����������
		updateChildrenSortNumber(parent, index, 1);
	}

	/**
	 * ���������ݲ���������ݻ�ȡ���������
	 * @param parent
	 * @param curIndex
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	private int getInsertRowSortNumber(ScheduleReportOrgInfo parent, int curIndex) {
		List<Integer> childrenIndex = getAllChildrenIndex(parent);
		int number = 0;
		for (Integer index : childrenIndex) {
			if (curIndex > index) {
				number++;
			}
		}
		return number + 1;
	}

	/**
	 * ��������ȡparent�µ��ӽڵ���
	 * @param parent
	 * @Author��adward_huang
	 * @CreateTime��2012-10-24
	 */
	private int getChildrenSize(ScheduleReportOrgInfo parent) {
		// ��ȡ�����и��ڵ����ӽڵ���
		Integer childrenSize = cacheChildrenSize.get(parent.getId().toString());
		int addIndex = 0;
		if (childrenSize != null && childrenSize.intValue() > 0) {
			addIndex = childrenSize.intValue();
		}
		return addIndex;
	}

	/**
	 * ��������ȡparent�µ��ӽڵ���
	 * @param parent
	 * @Author��adward_huang
	 * @CreateTime��2012-10-24
	 */
	private int getAllChildrenSize(ScheduleReportOrgInfo parent) {

		List<ScheduleReportOrgInfo> childrenList = new ArrayList<ScheduleReportOrgInfo>();
		getAllChildrenByParent(parent, childrenList);

		return childrenList.size();
	}

	/**
	 * ��������ȡ�ýڵ��������ӽڵ�
	 * @param parent
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-29
	 */
	private void getAllChildrenByParent(ScheduleReportOrgInfo parent, List<ScheduleReportOrgInfo> childrenList) {
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		int rowCount = treeTable.getRowCount();
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// ��������и��ڵ�ID������ID��������ӽڵ㣬����true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parent.getId().toString())) {
				childrenList.add(org);
				getAllChildrenByParent(org, childrenList);
			}
		}
	}

	/**
	 * ����������ScheduleReportOrgInfoʵ���BOSTYPE����BOSUuid
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	private BOSUuid getScheduleReportBOSUuid() {
		ScheduleReportOrgInfo info = new ScheduleReportOrgInfo();
		return BOSUuid.create(info.getBOSType());
	}

	/**
	 * �����������ƽ���F7��������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	@SuppressWarnings("serial")
	private void addF7CellEdit() {

		KDBizPromptBox prmtTask = new KDBizPromptBox() {

			@SuppressWarnings("unchecked")
			@Override
			protected Object popupSelector(Object param) {
				Object obj = super.popupSelector(param);

				int index = treeTable.getSelectManager().getActiveRowIndex();
				Object curObj = treeTable.getRow(index).getUserObject();
				// �����ȡ�������򷵻�
				if (KDPromptBox.DEFAULTVALUE == obj) {
					return curObj;
				}

				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();

				if (org.getRelateOrg() != null
						&& FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID.equals(org.getRelateOrg().getId().toString())) {
					MsgBox.showWarning("���Ų����޸�!");
					return org;
				}

				if (org.getName() != null && hasChildren(org)) {
					MsgBox.showWarning("����֯����������֯����������ָ����֯!");
					return org;
				}

				// ����ѡ����֯ʱ����Ҫ��֮ǰ����֯ɾ��
				if (cacheLoadTreeNodeMap.containsKey(org.getId().toString())) {
					cacheDeleteTreeNodeMap.put(org.getId().toString(), org);
				}

				List<Map.Entry<String, DefaultKingdeeTreeNode>> checkedNodes = null;
				try {
					checkedNodes = (List<Map.Entry<String, DefaultKingdeeTreeNode>>) obj;
				} catch (Exception e) {// �˴��쳣Ϊȡ������ʱ�׳����ڴ˷��ؿն���
					e.printStackTrace();
					logger.error("F7�ؼ�ѡ��������쳣��������ˢ�½����ٲ���!");
					return null;
				}

				Object first = null;

				// �Ƿ������ͬ�ڵ㣬�����򷵻�Null,�������򷵻�û���ظ��Ľڵ���Ϣ
				boolean flag = false;
				if (checkedNodes != null && !checkedNodes.isEmpty()) {
					IRow selectRow = treeTable.getRow(index);
					ScheduleReportOrgInfo selectOrg = (ScheduleReportOrgInfo) selectRow.getUserObject();
					first = getScheduleReportOrg(checkedNodes.get(0).getValue(), selectOrg);

					flag = addTableTreeNode(checkedNodes, selectOrg);
					if (!flag) {
						selectRow.getCell(NUMBER).setValue(((ScheduleReportOrgInfo) first).getNumber());
						selectRow.setUserObject(first);
					}
				}
				return flag ? curObj : first;
			}
		};
		// F7��֯����������
		F7ScheduleReportTreeMaintenaceDialog scheduleReportDialog = new F7ScheduleReportTreeMaintenaceDialog(this);
		prmtTask.setSelector(scheduleReportDialog);

		prmtTask.setHistoryRecordEnabled(false);
		ICellEditor editor = new KDTDefaultCellEditor(prmtTask);
		treeTable.getColumn(NAME).setEditor(editor);
	}

	/**
	 * ��������ӱ�����ڵ�
	 * @param checkedNodes
	 * @param curNode
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	private boolean addTableTreeNode(List<Map.Entry<String, DefaultKingdeeTreeNode>> checkedNodes, ScheduleReportOrgInfo curNode) {

		int size = checkedNodes.size();
		List<String> existNameList = new ArrayList<String>();
		boolean exist = false;
		int index = treeTable.getSelectManager().getActiveRowIndex();
		ScheduleReportOrgInfo curOrg = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		for (int i = 0; i < size; i++) {
			ScheduleReportOrgInfo org = getScheduleReportOrg(checkedNodes.get(i).getValue(), curNode);

			if (!exist) {
				exist = existSameNode(org, existNameList, curOrg);
				if (exist) {
					break;
				}
			}
			if (i != 0 && org != null) {
				int addIndex = getChildrenSize(org.getParent());
				cacheChildrenSize.put(org.getParent().getId().toString(), addIndex + 1);
				// ����ǲ����в������������������ѡ�ڵ�����
				if (actionType == FDCScheduleConstant.ACTION_TYPE_INSERT) {
					org.setSortNumber(curNode.getSortNumber() + i);
				} else {
					// ����������в�����������������ӽڵ��С���������
					org.setSortNumber(addIndex);
				}
				IRow row = treeTable.addRow(index + i);
				row.setUserObject(org);
				loadRow(row);
				// �޸�ͬ���ýڵ�����ڵ����������
				updateChildrenSortNumber(org.getParent(), index + i, 1);
			}
		}

		if (exist) {
			MsgBox.showWarning("��������ظ�����֯��Ϣ����������Ϊ��" + Arrays.toString(existNameList.toArray()) + "����ѡ��������֯��");
		}
		return exist;
	}

	/**
	 * �����������ڵ���Ϣ��װΪ���ȱ������
	 * @param node
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-15
	 */
	private ScheduleReportOrgInfo getScheduleReportOrg(DefaultKingdeeTreeNode node, ScheduleReportOrgInfo selectOrg) {
		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		if (node == null) {
			return org;
		}
		Object obj = node.getUserObject();
		String name = node.getText();
		BOSUuid unitId = null;
		int level = selectOrg.getLevel();
		String number = null;

		// ����ڵ��ǹ�����Ŀ
		if ((obj != null) && (obj instanceof CurProjectInfo)) {
			CurProjectInfo info = (CurProjectInfo) obj;
			unitId = info.getId();
			number = info.getNumber();
		}

		// ����ڵ�����֯�ṹ
		if ((obj != null) && (obj instanceof OrgStructureInfo)) {
			OrgStructureInfo info = (OrgStructureInfo) obj;
			unitId = info.getId();
			number = info.getUnit().getNumber();
		}

		// ����ڵ�����֯��Ԫ
		if ((obj != null) && (obj instanceof FullOrgUnitInfo)) {
			FullOrgUnitInfo info = (FullOrgUnitInfo) obj;

			unitId = info.getId();
			number = info.getNumber();
		}
		FullOrgUnitInfo unit = new FullOrgUnitInfo();
		unit.setId(unitId);
		unit.setName(name);
		unit.setNumber(number);
		org.setRelateOrg(unit);

		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);
		org.setName(name);
		org.setNumber(number);
		if (selectOrg.getParent() != null) {
			org.setLongNumber(getNodeLongNumber(selectOrg.getParent().getLongNumber(), number));
		} else {
			org.setLongNumber(number);
		}
		org.setLevel(level);
		org.setSortNumber(selectOrg.getSortNumber());
		org.setRelateOrg(unit);
		org.setParent(selectOrg.getParent());
		return org;
	}

	/**
	 * ���������ճ��������֯�ṹ�����й���
	 * @param orgCollection
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleReportOrgInfo> orderedOrgTreeNode1(ScheduleReportOrgCollection orgCollection) {
		List<ScheduleReportOrgInfo> list = new ArrayList<ScheduleReportOrgInfo>();
		for (Iterator iter = orgCollection.iterator(); iter.hasNext();) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) iter.next();
			list.add(org);
			// ��������ݿ��м��صĽڵ���Ϣ
			cacheLoadTreeNodeMap.put(org.getId().toString(), org);
			if (org.getParent() != null) {
				String parentID = org.getParent().getId().toString();
				// ��������ӽڵ㣬���1
				if (cacheChildrenSize.containsKey(parentID)) {
					int size = cacheChildrenSize.get(parentID);
					cacheChildrenSize.put(parentID, size + 1);
				} else {
					cacheChildrenSize.put(parentID, 1);
				}
			}
		}
		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScheduleReportOrgInfo source = (ScheduleReportOrgInfo) arg0;
				ScheduleReportOrgInfo target = (ScheduleReportOrgInfo) arg1;
				int result = 0;
				if (source.getLongNumber() != null && target.getLongNumber() != null) {
					if (source.getLevel() == target.getLevel()) {
						result = String.valueOf(source.getSortNumber()).compareTo(String.valueOf(target.getSortNumber()));
					} else {
						result = source.getLongNumber().compareTo(target.getLongNumber());
					}
					return result;
				}
				return result;
			}
		});
		return list;
	}

	/**
	 * ���������ر��������
	 * @param row
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	protected void loadRow(IRow row) {
		ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) row.getUserObject();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setTreeLevel(org.getLevel());// ���Σ���1��ʼ���˴�Ϊ�����������
		treeNode.setHasChildren(!org.isIsLeaf());
		treeNode.isCollapse();
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode source, ICell cell, int type) {
				treeTable.revalidate();
			}
		});
		row.getCell(TREE_CELL).setValue(treeNode);
		//		row.getCell(TREE_CELL).getStyleAttributes().setLocked(false);
		row.getCell(NUMBER).setValue(org.getNumber());
		//		row.getCell(NUMBER).getStyleAttributes().setLocked(false);
		//		row.getCell(NAME).getStyleAttributes().setLocked(false);

		row.getCell(NAME).setValue(org.getName());
		ScheduleReportOrgInfo parent = org.getParent();
		if (parent != null) {
			row.getCell(PARENT_NAME).setValue(parent.getName());
			row.getCell(PARENT_NUMBER).setValue(parent.getNumber());
		} else {
			row.getCell(PARENT_NAME).setValue("");
			row.getCell(PARENT_NUMBER).setValue("");
		}
		row.getCell(REMARK).setValue(org.getRemark());
		//		row.getCell(REMARK).getStyleAttributes().setLocked(false);
		if (org.getLevel() != 0) {
			addF7CellEdit();
		} else {
			row.setEditor(null);
			row.getCell(NAME).setEditor(null);

		}

		// �����������ݼ��뻺��
		cacheUpdateTreeNodeMap.put(org.getId().toString(), org);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actionSave_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			int rowCount = this.treeTable.getRowCount();
			// �Ƿ񱣴�ɹ�
			boolean flag = true;
			List<ScheduleReportOrgInfo> updateList = new ArrayList<ScheduleReportOrgInfo>();

			if (rowCount > 1) {
				IRow row = null;
				ScheduleReportOrgInfo org = null;
				Object name = null;
				Object number = null;
				Object remark = null;
				// ������Ϣ�������޸ģ���0�в�ȡ
				for (int i = 1; i < rowCount; i++) {
					row = treeTable.getRow(i);
					org = (ScheduleReportOrgInfo) row.getUserObject();
					number = row.getCell(NUMBER).getValue();
					name = row.getCell(NAME).getValue();
					remark = row.getCell(REMARK).getValue();
					if (FDCHelper.isEmpty(number) || FDCHelper.isEmpty(name)) {
						flag = false;
						continue;
					}
					if (FDCHelper.isEmpty(remark)) {
						remark = "";
					}
					org.setName(name.toString());
					org.setNumber(number.toString());
					org.setRemark(remark.toString());
					org.setLongNumber(getNodeLongNumber(org.getParent().getLongNumber(), org.getNumber()));
					updateList.add(org);
					//	ScheduleReportOrgFactory.getRemoteInstance().save(getScheduleReportBOSUuidPK(org.getId()),org);
				}

			}
			if (!flag) {
				MsgBox.showInfo("����Ϊ���ƻ����Ϊ�յ����ݣ���������Ϣ�ٱ���!");
				return;
			}
			List<ScheduleReportOrgInfo> deleteList = new ArrayList<ScheduleReportOrgInfo>();
			if (!cacheDeleteTreeNodeMap.isEmpty()) {
				for (Iterator<Map.Entry<String, ScheduleReportOrgInfo>> iter = cacheDeleteTreeNodeMap.entrySet().iterator(); iter.hasNext();) {
					ScheduleReportOrgInfo info = iter.next().getValue();
					deleteList.add(info);
				}
			}

			if (!updateList.isEmpty() || !deleteList.isEmpty()) {
				ScheduleReportOrgFactory.getRemoteInstance().updateScheduleReportData(updateList, deleteList);
			}
			if (flag) {
				fillTable();
				cacheUpdateTreeNodeMap.clear();
				cacheDeleteTreeNodeMap.clear();
				MsgBox.showInfo("����ɹ�!");
			}
		}
	}

	/**
	 * ��������ȡ�ڵ�ĳ�����
	 * @param parentLongNumber
	 * @param longNumber
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-12
	 */
	private String getNodeLongNumber(String parentLongNumber, String longNumber) {
		if (parentLongNumber == null) {
			return longNumber;
		}
		return parentLongNumber + FDCScheduleConstant.REPORT_TREE_SEPARATOR + longNumber;
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actionUpdate_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			if (!isEditable) {
				isEditable = true;
				setEditColumn(false);
			}
		}

	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actioRefresh_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actioRefresh_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			clearCacheData();
			fillTable();
		}
	}

	/**
	 * ��������ջ�������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-24
	 */
	private void clearCacheData() {
		cacheLoadTreeNodeMap.clear();
		cacheUpdateTreeNodeMap.clear();
		cacheDeleteTreeNodeMap.clear();
		cacheChildrenSize.clear();
	}

	/**
	 * ��������Ӽ�����֯
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	private void addDefaultGroupTreeNode() {

		try {
			boolean flag = ScheduleReportOrgFactory.getRemoteInstance().checkDefaultGroupOrg();
			if (!flag) {
				String number = FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID;
				String name = FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NAME;
				String remark = "���Žڵ㣬�����޸ĺ�ɾ��!";

				ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
				org.setName(name.toString());
				org.setNumber(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER);
				org.setRemark(remark == null ? "" : remark.toString());
				org.setLongNumber(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER);
				org.setLevel(0);
				org.setParent(null);
				org.setDisplayName(name);
				org.setIsLeaf(false);
				org.setSortNumber(0);
				FullOrgUnitInfo unit = new FullOrgUnitInfo();
				unit.setId(BOSUuid.read(number));
				unit.setName(name);
				org.setRelateOrg(unit);
				ScheduleReportOrgFactory.getRemoteInstance().save(new ObjectUuidPK(org.getId()), org);
			}
		} catch (EASBizException e) {
			this.handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			this.handUIExceptionAndAbort(e);
		}
	}
}