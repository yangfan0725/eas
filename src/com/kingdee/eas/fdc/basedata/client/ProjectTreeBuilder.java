/*
 * @(#)ProjectTreeBuilder.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * ����:���ݵ�ǰ��֯���칤����Ŀ����ʵ�����ǣ� ͬʱ�߱�������֯�ͳɱ��������Ե���֯�� �� ������Ŀ��
 * 
 * @author liupd date:2006-7-20
 *         <p>
 * @version EAS5.1.3
 */
public class ProjectTreeBuilder {

	// parent UI
	private CoreUIObject ui;
	// ��������Ŀ��
	private KDTree projTree = new KDTree();

	// internal var, a flag
	private boolean getIt = false;

	// �Ƿ�����ѽ��õĹ�����Ŀ
	private boolean isIncludeDisabled;

	// ��������֯���룿
	private boolean noOrgIsolation;

	// �Ƿ������Ʒ����
	private boolean isIncludeProductType;

	private String projectTypeId;

	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;

	/** �Ƿ񿪷���Ŀ */
	private boolean isDevPrjFilter = true;
	
	// �Ƿ�ȫ��
	private boolean isWholeAgeStage=false;

	/**
	 * 
	 * ���������ݵ�ǰ��֯���칤����Ŀ����������֯��������Ŀ��
	 * 
	 * @param ui
	 *            Ҫ���������б����ʵ��
	 * @param projectTree
	 *            Ҫ�����KDTree
	 * @param actionOnload
	 *            �б�����ActionOnLoad����this.actionOnLoad����
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad)
			throws Exception {
		final KDTree prjTree=projectTree;
		prjTree.addTreeWillExpandListener(new TreeWillExpandListener(){

			public void treeWillCollapse(TreeExpansionEvent event)
					throws ExpandVetoException {
				
			}

			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {				
				updateTreeNodeColor(prjTree);
			}
			
		});
		build(ui, prjTree, actionOnLoad, null);
	}
	
	public void updateTreeNodeColor(KDTree projectTree){
		DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode)projectTree.getModel().getRoot();
		visitAllNodes(root);
	}
	public void visitAllNodes(DefaultKingdeeTreeNode node) {	   
	       if (node.getChildCount() >= 0) {
	           for (Enumeration e=node.children(); e.hasMoreElements(); ) {
	        	   DefaultKingdeeTreeNode n = (DefaultKingdeeTreeNode)e.nextElement();
	        	   if(n.getUserObject() instanceof CurProjectInfo){
	        		   CurProjectInfo info=(CurProjectInfo)n.getUserObject();
	        		   //Boolean.parseBoolean(info.get("hasAimCost").toString());
	        		   if(info.get("hasAimCost")!=null){//NP
		        		   boolean b =Boolean.valueOf(info.get("hasAimCost").toString()).booleanValue();
		        		   if(!b && n.isLeaf()){
		        			   n.setTextColor(Color.RED);
		        		   }
	        		   }
	        	   }
	               visitAllNodes(n);
	           }
	       }
	   }


	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad,
			String curOrgId) throws Exception {
		this.ui = ui;

		if (curOrgId == null && !noOrgIsolation) {
			curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId()
					.toString();
		}

		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", curOrgId, null, getActionPK(actionOnLoad));
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel
				.getRoot();

		if (root.getUserObject() != null) {
			// �������֯���ǳɱ����ģ���ʾ
			OrgStructureInfo orgStru = (OrgStructureInfo) root.getUserObject();
			if (!orgStru.getUnit().isIsCostOrgUnit()) {
				MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
				SysUtil.abort();
			}
			removeNoneCostCenterNode(root);
			buildByOrgTree(projectTree, orgTreeModel);
		} else {
			buildByCurOrg(projectTree);
		}

	}

	/**
	 * 
	 * ������ȥ��û�гɱ�������֯���ԵĽڵ�
	 * 
	 * @param node
	 * @author:liupd ����ʱ�䣺2006-9-25
	 *               <p>
	 */
	private void removeNoneCostCenterNode(DefaultKingdeeTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node
					.getChildAt(i);
			OrgStructureInfo orgStru = (OrgStructureInfo) curNode
					.getUserObject();
			if (!orgStru.getUnit().isIsCostOrgUnit()) {
				node.remove(i);
				i--;
				continue;
			}
			if (curNode.getChildCount() > 0) {
				removeNoneCostCenterNode(curNode);
			}
		}

	}

	/**
	 * 
	 * ��������ǰ��֯�ǳɱ�����
	 * 
	 * @param tree
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public void buildByCurOrg(KDTree orgTree) throws Exception {
		FilterInfo projFilter = getProjectFilterByCurOrg();
		if (projectTypeId != null) {
			projFilter.getFilterItems().add(
					new FilterItemInfo("projectType.id", projectTypeId));
		}
		buildProjTree(projFilter);
		TreeNode root = (TreeNode) projTree.getModel().getRoot();
		if (root.getChildCount() == 0) {
			MsgBox.showWarning(ui, "�޷����칤����Ŀ������ȷ��\"������Ŀ��ɱ����ĵĶ�Ӧ��ϵ\"�����Ƿ���ȷ");
			SysUtil.abort();
		}
		TreeModel model = new DefaultTreeModel(root.getChildAt(0));
		orgTree.setModel(model);

	}

	/**
	 * 
	 * @return �����ȡ��ǰ��֯�µĹ�����Ŀ��Filter
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public FilterInfo getProjectFilterByCurOrg() throws BOSException,
			EASBizException {
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = ProjectWithCostCenterOUFactory
				.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		if (relations == null || relations.size() == 0) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}

		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(projUuidPK);

		if (!curProjectInfo.isIsEnabled()) {
			MsgBox.showWarning(FDCClientUtils.getRes("curOrgRelProjNotEnab"));
			SysUtil.abort();
		}

		String projNumber = curProjectInfo.getLongNumber();
		// Filter
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber + "%",
						CompareType.LIKE));

		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit()
				.getId().toString();

		projFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", orgUnitId));
		if (!isIncludeDisabled) {
			projFilter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
		}
		 if (isDevPrjFilter) {
		 projFilter.getFilterItems().add(
		 new FilterItemInfo("isDevPrj", Boolean.TRUE));
		 }
//		if (!isContainDevPrj) {
//			projFilter.getFilterItems().add(
//					new FilterItemInfo("isDevPrj", Boolean.FALSE));
//		}
		return projFilter;
	}

	public ProjectTreeBuilder() {
		this.isIncludeDisabled = false;
		noOrgIsolation = false;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public ProjectTreeBuilder(boolean isIncludeDisabled) {
		this.isIncludeDisabled = isIncludeDisabled;
		noOrgIsolation = false;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public ProjectTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @param isIncludeProductType
	 *            �Ƿ������Ʒ����(������Ŀ�¹Ҳ�Ʒ����)
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public ProjectTreeBuilder(boolean isIncludeDisabled,
			boolean noOrgIsolation, boolean isIncludeProductType) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @param isIncludeProductType
	 *            �Ƿ������Ʒ����(������Ŀ�¹Ҳ�Ʒ����)
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public ProjectTreeBuilder(boolean isIncludeDisabled,
			boolean noOrgIsolation, boolean isIncludeProductType,
			String projectTypeId) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
		this.projectTypeId = projectTypeId;
	}
	public ProjectTreeBuilder(int isWholeAgeStage) {
		this.isIncludeDisabled = false;
		this.noOrgIsolation = false;
		if(isWholeAgeStage==1){
			this.isWholeAgeStage=true;
		}
	}
	/**
	 * 
	 * ��������ǰ��֯�ǲ�����֯
	 * 
	 * @param orgTree
	 * @param orgTreeModel
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
	 * 
	 *               <p>
	 */
	public void buildByOrgTree(KDTree orgTree, TreeModel orgTreeModel)
			throws Exception {
		orgTree.setModel(orgTreeModel);
		authorizedOrgs = (Set) ActionCache
				.get("ContractListBaseUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId()), OrgType.CostCenter,
					null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}

		TreeNode root2 = (TreeNode) orgTreeModel.getRoot();

		Set leafNodesIdSet = new HashSet();
		FDCClientUtils.genLeafNodesIdSet(root2, leafNodesIdSet);

		// Filter
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", leafNodesIdSet,
						CompareType.INCLUDE));
		if (!isIncludeDisabled) {
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
		}

		if (this.projectTypeId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectType.id", projectTypeId));
		}

		if (isDevPrjFilter) {
			if (isContainDevPrj) {
				filter.getFilterItems().add(
						new FilterItemInfo("isDevPrj", Boolean.FALSE));
			}else{
				filter.getFilterItems().add(
						new FilterItemInfo("isDevPrj", Boolean.TRUE));
			}
		}
		if(this.isWholeAgeStage){
			filter.getFilterItems().add(
					new FilterItemInfo("isWholeAgeStage", Boolean.TRUE));

		}

		// ����������ʱ ���ӶԵ�ǰ�û���סȨ�޵Ĺ���
		// �����Ŀû�гɱ��������������� by sxhong 2008-11-10 16:48:45
		FilterInfo authorizedOrgsFilter = new FilterInfo();
		if (this.ui == null || !(this.ui instanceof ProjectListUI)) {
			// ������Ŀ��ʱ����������
			if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
				// û����Ȩ��ֻ��ʾû�����ĵ�����
				authorizedOrgsFilter.getFilterItems().add(
						new FilterItemInfo("costCenter.id", null));
			} else {
				authorizedOrgsFilter.getFilterItems().add(
						new FilterItemInfo("costCenter.id", authorizedOrgs,
								CompareType.INCLUDE));
				authorizedOrgsFilter.getFilterItems().add(
						new FilterItemInfo("costCenter.id", null));
				authorizedOrgsFilter.setMaskString("#0 or #1");
			}
			filter.mergeFilter(authorizedOrgsFilter, "and");
		}
		buildProjTree(filter);

		TreeNode root = (TreeNode) projTree.getModel().getRoot();

		TreeNode orgRoot = (TreeNode) orgTreeModel.getRoot();

		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root
						.getChildAt(i);
				orgTree.addNodeInto((MutableTreeNode) projNode,
						(MutableTreeNode) orgRoot);
				i--;
			}
		} else {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root
						.getChildAt(i);
				CurProjectInfo projectInfo = (CurProjectInfo) projNode
						.getUserObject();
				String orgId = projectInfo.getFullOrgUnit().getId().toString();

				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode,
						leafNodesIdSet);

				if (getIt) {
					i--;
				}
				getIt = false;
			}

		}
	}
	

	/**
	 * 
	 * ���������ݳɱ�������֯�����칤����Ŀ��
	 * 
	 * @param orgTree
	 *            �ڴ����Ϲ��칤����Ŀ
	 * @param orgTreeModel
	 *            �ɱ�������Model
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public void buildByCostOrgTree(KDTree orgTree, TreeModel orgTreeModel)
			throws Exception {
		TreeNode root2 = (TreeNode) orgTreeModel.getRoot();

		Set costLeafNodesIdSet = new HashSet();

		FDCClientUtils.genLeafNodesIdSet(root2, costLeafNodesIdSet);

		FDCClientUtils.removeNoneCompanyNode((DefaultKingdeeTreeNode) root2);

		Set companyNodesIdSet = new HashSet();

		FDCClientUtils.genNodesIdSet(root2, companyNodesIdSet);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("id", companyNodesIdSet,
								CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("isBizUnit");
		Set companyIdSet = new HashSet();
		CompanyOrgUnitCollection companyOrgUnitCollection = CompanyOrgUnitFactory
				.getRemoteInstance().getCompanyOrgUnitCollection(view);
		for (Iterator iter = companyOrgUnitCollection.iterator(); iter
				.hasNext();) {
			CompanyOrgUnitInfo element = (CompanyOrgUnitInfo) iter.next();
			if (element.isIsBizUnit()) {
				companyIdSet.add(element.getId().toString());
			}

		}

		orgTree.setModel(orgTreeModel);

		view = new EntityViewInfo();
		filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("costCenterOU.id", costLeafNodesIdSet,
						CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("curProject.longNumber");
		ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory
				.getRemoteInstance().getProjectWithCostCenterOUCollection(view);

		if (projectWithCostCenterOUCollection == null
				|| projectWithCostCenterOUCollection.size() == 0) {
			return;
		}
		StringBuffer maskStr = new StringBuffer();
		int idx = 0;
		String mark = "#";
		String or = " OR ";
		maskStr.append("(");
		FilterInfo projFilter = new FilterInfo();
		for (Iterator iter = projectWithCostCenterOUCollection.iterator(); iter
				.hasNext();) {
			ProjectWithCostCenterOUInfo element = (ProjectWithCostCenterOUInfo) iter
					.next();
			String longNumber = element.getCurProject().getLongNumber();
			projFilter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber + "%",
							CompareType.LIKE));
			maskStr.append(mark + idx++ + or);
		}

		String mask = maskStr.toString();
		mask = mask.substring(0, mask.length() - or.length());
		maskStr.setLength(0);
		maskStr.append(mask);
		maskStr.append(")");

		// ��ǰ��֯Ϊʵ��ɱ�����ʱ, û�в�����֯, ���ｫ��ǰ������֯����
		if (companyIdSet.size() == 1 || companyIdSet.size() == 0) {
			companyIdSet.add(SysContext.getSysContext().getCurrentFIUnit()
					.getId().toString());
		}

		projFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", companyIdSet,
						CompareType.INCLUDE));
		maskStr.append(" and " + mark + idx++);

		if (!isIncludeDisabled) {
			projFilter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			maskStr.append(" and " + mark + idx++);
		}

		projFilter.setMaskString(maskStr.toString());

		buildProjTree(projFilter);

		TreeNode root = (TreeNode) projTree.getModel().getRoot();

		TreeNode orgRoot = (TreeNode) orgTreeModel.getRoot();

		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root
						.getChildAt(i);
				orgTree.addNodeInto((MutableTreeNode) projNode,
						(MutableTreeNode) orgRoot);
				i--;
			}
		} else {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root
						.getChildAt(i);
				CurProjectInfo projectInfo = (CurProjectInfo) projNode
						.getUserObject();
				String orgId = projectInfo.getFullOrgUnit().getId().toString();

				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode,
						companyIdSet);

				if (getIt) {
					i--;
				}
				getIt = false;
			}

		}
	}

	/**
	 * 
	 * ������������֯ID������֯���ڵ�, ������Ŀ�ڵ����ӵ�����������֯�ڵ�����
	 * 
	 * @param result
	 *            ���ҽ��
	 * @param node
	 * @param orgId
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	private void searchNodeByOrgId(TreeNode orgNode, String orgId,
			KDTree orgTree, TreeNode projNode, Set leafNodesIdSet) {
		if (getIt) {
			return;
		}
		/*
		 * ���Ӷ���֯�����ڵ�����������⵱ʵ�������֯��������ʵ�������֯ʱ���޷���ʾ�ϼ�ʵ�������֯�µ���Ŀ�����07.06.08
		 */
		Object obj = ((DefaultKingdeeTreeNode) orgNode).getUserObject();
		String orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
				.toString();
		if (orgRootId.equals(orgId)) {
			orgTree.addNodeInto((MutableTreeNode) projNode,
					(MutableTreeNode) orgNode);
			getIt = true;
			return;
		}

		int count = orgNode.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) orgNode.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			if (userObj instanceof OrgStructureInfo
					&& leafNodesIdSet.contains(((OrgStructureInfo) userObj)
							.getUnit().getId().toString())) {

				String orgId2 = ((OrgStructureInfo) userObj).getUnit().getId()
						.toString();

				if (orgId2.equals(orgId)) {
					orgTree.addNodeInto((MutableTreeNode) projNode,
							(MutableTreeNode) treeNode);
					getIt = true;
					break;
				}
			} else {
				searchNodeByOrgId(treeNode, orgId, orgTree, projNode,
						leafNodesIdSet);
			}

		}

	}

	private IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}

	private TreeSelectionListener treeSelectionListener;

	private ITreeBuilder treeBuilder;

	/**
	 * ������Ŀ��
	 */
	private void buildProjTree(FilterInfo filter) throws Exception {
		KDTree treeMain = projTree;
		TreeSelectionListener[] listeners = treeMain
				.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		treeBuilder = TreeBuilderFactory.createTreeBuilder(
				getProjLNTreeNodeCtrl(), getTreeInitialLevel(),
				getTreeExpandLevel(), filter);

		treeBuilder.buildTree(treeMain);

		TreeModel treeModel = null;
		String paramValue = ParamControlFactory.getRemoteInstance().getParamValue(null, FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO);
		if (Boolean.valueOf(paramValue).booleanValue()) {
			
			/*
			 * ����sortNo�ֶ�����
			 */
			treeModel = new KingdeeTreeModel((TreeNode)((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()).clone());
			this.addNode((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(),(DefaultKingdeeTreeNode)treeModel.getRoot());
		} else {
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()));
		}
		
		// ���ѡ���˰�����Ʒ����,��Ҫ���������ϼӲ�Ʒ����
		if (isIncludeProductType) {
			DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) treeModel.getRoot();
			addProductType(rootNode);
		}

		treeMain.setModel(treeModel);

		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);
	}

	/**
	 * �Ӳ�Ʒ����
	 * 
	 * @param node
	 * @throws Exception
	 */
	private void addProductType(DefaultKingdeeTreeNode node) throws Exception {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node
					.getChildAt(i);
			if (childAt.getChildCount() == 0) {
				CurProjectInfo proj = (CurProjectInfo) childAt.getUserObject();
				if (proj != null) {
					String id = proj.getId().toString();
					CurProjProductEntriesCollection col = getProductTypes(id);
					for (Iterator iter = col.iterator(); iter.hasNext();) {
						CurProjProductEntriesInfo element = (CurProjProductEntriesInfo) iter
								.next();
						DefaultKingdeeTreeNode productTypeNode = new DefaultKingdeeTreeNode(
								element.getProductType());
						childAt.add(productTypeNode);

					}
				}
			} else {
				addProductType(childAt);
			}
		}
	}

	/**
	 * ���ݹ�����Ŀ�Ҳ�Ʒ����
	 * 
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	private CurProjProductEntriesCollection getProductTypes(String projId)
			throws Exception {
		CurProjProductEntriesCollection coll = new CurProjProductEntriesCollection();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject", projId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("productType.id");
		view.getSelector().add("productType.number");
		view.getSelector().add("productType.name");
		coll = CurProjProductEntriesFactory.getRemoteInstance()
				.getCurProjProductEntriesCollection(view);
		return coll;
	}

	/**
	 * ������
	 * 
	 * @param node
	 * @param newNode
	 */
	private void addNode(DefaultKingdeeTreeNode node,
			DefaultKingdeeTreeNode newNode) {
		TreeMap map = new TreeMap();
		BigDecimal b = new BigDecimal("0.001");
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node
					.getChildAt(i);
			int index = ((CurProjectInfo) childAt.getUserObject()).getSortNo();
			if (map.containsKey(new BigDecimal(index))) {
				map.put(new BigDecimal(index).add(b), childAt);
				b = b.add(b);
			} else {
				map.put(new BigDecimal(index), childAt);
			}
		}
		Collection collection = map.values();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			DefaultKingdeeTreeNode element = (DefaultKingdeeTreeNode) iter
					.next();
			DefaultKingdeeTreeNode newElement = (DefaultKingdeeTreeNode) element
					.clone();
			newNode.add(newElement);
			addNode(element, newElement);
		}

	}

	/**
	 * 
	 * ������һ��������ʵ���������޷�ʹ����ģʽȡ������ܲ�֧�֣������ֱ�Ӱѳ�ʼ������һ���Ƚϴ�������Ϳ����ƹ���ģʽȡ��
	 * ���������Ժ���ʱ��дһ�׻���������������ʵ��Ĺ������Ŀ��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-29
	 *               <p>
	 */
	private int getTreeInitialLevel() {
		return 50;
	}

	private int getTreeExpandLevel() {
		return 5;
	}

	private ILNTreeNodeCtrl getProjLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getProjTreeInterface());
	}

	private ITreeBase getProjTreeInterface() throws Exception {

		ITreeBase treeBase = CurProjectFactory.getRemoteInstance();

		return treeBase;
	}

	/**
	 * ��ȡ����������Ŀ��Model
	 * 
	 * @return
	 * @throws Exception
	 */
	public TreeModel getFullProjTreeModel() throws Exception {
		KDTree tree = new KDTree();

		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", null, null, null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel
				.getRoot();

		if (root.getUserObject() != null) {
			// �������֯���ǳɱ����ģ���ʾ
			OrgStructureInfo orgStru = (OrgStructureInfo) root.getUserObject();
			if (!orgStru.getUnit().isIsCostOrgUnit()) {
				MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
				SysUtil.abort();
			}
			removeNoneCostCenterNode(root);
			buildByOrgTree(tree, orgTreeModel);
		} else {
			buildByCurOrg(tree);
		}

		return tree.getModel();
	}

	/**
	 * 
	 * ����������ָ���ڵ㼰�¼��ڵ��Map[id, node]
	 * 
	 * @param root
	 * @param map
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public void genNodeMap(TreeNode root, Map map) {

		int count = root.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
			Object userObj = treeNode.getUserObject();

			String id = null;
			if (userObj instanceof OrgStructureInfo) {

				id = ((OrgStructureInfo) userObj).getUnit().getId().toString();

			} else if (userObj instanceof CurProjectInfo) {
				id = ((CurProjectInfo) userObj).getId().toString();
			}

			if (id != null) {
				map.put(id, treeNode);
			}

			genNodeMap(treeNode, map);
		}

	}

	// ����projectTree���ҵ�unitIdΪRoot������
	public static DefaultKingdeeTreeNode getDefaultKingdeeTreeNode(
			DefaultKingdeeTreeNode root, String unitId) {
		if (unitId == null) {
			return root;
		} else {

			Object obj = ((DefaultKingdeeTreeNode) root).getUserObject();

			if (!(obj instanceof OrgStructureInfo)) {
				return null;
			}

			String orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
					.toString();
			if (!orgRootId.equals(unitId)) {
				int count = root.getChildCount();
				DefaultKingdeeTreeNode treeNode = null;
				for (int i = 0; i < count; i++) {
					treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);

					obj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
					if (!(obj instanceof OrgStructureInfo)) {
						continue;
					}
					orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
							.toString();

					if (!orgRootId.equals(unitId)) {
						DefaultKingdeeTreeNode value = getDefaultKingdeeTreeNode(
								treeNode, unitId);
						if (value != null) {
							return value;
						}
					} else {
						return treeNode;
					}
				}

			} else {
				return root;
			}
		}
		return null;

	}

	/**
	 * ���������ɱ���Ŀ���²������в������²�������ģ����������ɼ�������Ŀ�����в�����Ŀ������ֻ����ʾ������Ŀ��
	 * 
	 * @author hpw date:2009-8-24
	 * @param isDevPrjFilter
	 * @return
	 */
	public void setDevPrjFilter(boolean isDevPrjFilter) {
		this.isDevPrjFilter = isDevPrjFilter;
	}

	/**
	 * ר�ù��˿��гɱ�
	 */
	private boolean isContainDevPrj = false;

	public void setDevPrjFilter(boolean isDevPrjFilter, boolean isContainDevPrj) {
		this.isDevPrjFilter = isDevPrjFilter;
		this.isContainDevPrj = isContainDevPrj;

	}

}
