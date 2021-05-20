/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountControlAttribute;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.LowTimer;

/**
 * �ɱ���Ŀ����
 */
public class NoCostSplitAcctUI extends AbstractNoCostSplitAcctUI{
	private static final LowTimer timer=new LowTimer();
	private static final Logger logger = CoreUIObject.getLogger(NoCostSplitAcctUI.class);
	private static final Color canntSelectColor = new Color(0xFEFED3);

	private CurProjectInfo addressProject = null;
	private FilterInfo filter = null;
	private BOSUuid cuId=null; //���ڿ��ƿ�Ŀ�ĸı�ˢ��
	private AccountViewCollection accountViewCollection = new AccountViewCollection();
    
    private boolean isOk = false;		//add by jelon

	/**
	 * output class constructor
	 */
	public NoCostSplitAcctUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
//		tblMain.checkParsed();// table����!
//		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
//			public void afterDataFill(KDTDataRequestEvent e) {
//				// do something
//				String strTemp = "";
//				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
//					strTemp = tblMain.getRow(i).getCell(1).getValue().toString();
//					strTemp = strTemp.replace('!', '.');
//					tblMain.getRow(i).getCell(1).setValue(strTemp);
//					tblMain.getRow(i).getCell(0).setValue(strTemp);
//					tblMain.getRow(i).getCell(2).setValue(strTemp);
//				}
//			}
//		});
		super.onLoad();
		this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
//		this.btnAllSelect.setText("ȫѡ");
		this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
//		this.btnNoneSelect.setText("ȫ��");
		if (getUIContext().get("address") != null) {
			if (getUIContext().get("address") instanceof FullOrgUnitInfo) {
				
			} else if (getUIContext().get("address") instanceof CurProjectInfo) {
				addressProject = (CurProjectInfo) getUIContext().get("address");
			}
		}
		buildProjectTree();
//		this.treeMain.setSelectionRow(0);
//		loadDatas(filter);
	}
	public void onShow() throws Exception {
		super.onShow();

		selectCurOrgTreeNode();
	}
	
	/**
	 * 
	 * @author sxhong  		Date 2007-3-6
	 * @throws BOSException
	 */
	private void selectCurOrgTreeNode() throws BOSException {
		final OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
//		CurProjectInfo curProj=null;
		CurProjectInfo curProj=(CurProjectInfo)getUIContext().get("curProject");
		if(curProj==null){
			if(currentOrgUnit.isIsCostOrgUnit()){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo myfilter=new FilterInfo();
				myfilter.appendFilterItem("costCenterOU.id", currentOrgUnit.getId().toString());
				view.setFilter(myfilter);
				final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
				if(projectWithCostCenterOUCollection.size()==1){
					curProj=projectWithCostCenterOUCollection.get(0).getCurProject();
				}
				
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
	
		DefaultKingdeeTreeNode node=root;
		while(node!=null){
//			System.out.println(node.getUserObject().getClass());
			if(node.getUserObject() instanceof OrgStructureInfo){
				final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
				if(info.getUnit().getId().equals(currentOrgUnit.getId())){
					treeMain.setSelectionNode(node);
					int row=treeMain.getSelectionRows()[0];
					treeMain.expandRow(row);
				}
			}else if(curProj!=null&&node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo info =(CurProjectInfo) node.getUserObject();
				if(info.getId().equals(curProj.getId())){
					treeMain.setSelectionNode(node);
					int row=treeMain.getSelectionRows()[0];
					treeMain.expandRow(row);
					break;
				}
			}
			node=(DefaultKingdeeTreeNode)node.getNextNode();
		}
	}
	private void buildProjectTree() throws Exception {
		//�������л��洦��,���л����Ʋ���ϸ,���浽�ڴ�ı��ر��,�����лỰ����Ļ���
/*		Object obj=SysContext.getSysContext().getProperty("treeMain_Model");
		if(obj instanceof TreeModel){
			treeMain.setModel((TreeModel)obj);
			return;
		}*/
/*		if(obj instanceof File&&((File)obj).exists()){
			ObjectInputStream input=new ObjectInputStream(new FileInputStream((File)obj));
			Object readObject = input.readObject();
			if(readObject instanceof TreeModel ){
				TreeModel model=(TreeModel)readObject;
//				treeMain=new KDTree(model);
				treeMain.setModel(model);
//				treeMain.set
				input.close();
				return;
			}
			input.close();
		}*/

		//����ʾ�Ѿ����õĹ�����Ŀ�������Ƿ���֯���룿��	jelon 12/30/2006
		//ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true, true);
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, true);
		
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
//		SysContext.getSysContext().setProperty("treeMain_Model", treeMain.getModel());
/*		File file=File.createTempFile("treeMain_"+SysContext.getSysContext().getSessionID(), ".tree");
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(treeMain.getModel());
		out.close();
		SysContext.getSysContext().setProperty("treeMain_File", file);*/
			
		
	}


	//TODO ȡ�������ܸĽ�
	private void loadDatas(CurProjectInfo prj) throws BOSException {
		if(prj==null){
			tblMain.removeRows();
			cuId=null;
			return;
		}
		FullOrgUnitInfo fullOrgUnit = prj.getFullOrgUnit();
		if(fullOrgUnit.getId().equals(cuId)){
			//�����Ԫ���ѡ��
			for(int i=0;i<tblMain.getRowCount();i++){
				tblMain.getCell(i, "select").setValue(Boolean.FALSE);
			}
			return;
		}
		this.cuId=fullOrgUnit.getId();
		
/*		while(!fullOrgUnit.isIsCU()){
			fullOrgUnit=fullOrgUnit.getParent();
		}*/
		if(fullOrgUnit.getPartFI()==null||fullOrgUnit.getPartFI().getAccountTable()==null){
			return;
		}
		String accountTableId=fullOrgUnit.getPartFI().getAccountTable().getId().toString();
		
		this.tblMain.removeRows();
		tblMain.checkParsed();// table����!
		tblMain.getColumn("select").setWidth(50);
		tblMain.getColumn("number").setWidth(211);
		tblMain.getColumn("name").setWidth(130);
		testTime("start");
		createTableByORMap(accountTableId,cuId);
//		createTableBySql(accountTableId, cuId);
		testTime("end");
	}

	/**
	 * 
	 * @param accountTableId
	 * @param c�����빫˾��Ŀ
	 * @throws BOSException
	 */
	private void createTableByORMap(String accountTableId,BOSUuid companyid) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		view.put("selector", getAccountViewSelector());
		//filter=cu+��Ŀ��+�Ƿ�����
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("accountTableID",accountTableId));
//		filter.getFilterItems().add(new FilterItemInfo("CU.id",cuId));
		filter.getFilterItems().add(new FilterItemInfo("companyID",cuId));
		filter.getFilterItems().add(new FilterItemInfo("control",new Integer(AccountControlAttribute.NONE_VALUE)));
		view.setFilter(filter);
		
		SorterItemInfo sorter = new SorterItemInfo();
		sorter.setPropertyName("longNumber");
		sorter.setSortType(SortType.ASCEND);
		view.getSorter().add(sorter);
//		testTime("accat start");
		AccountViewCollection accountViewCollection = AccountViewFactory.getRemoteInstance().getAccountViewCollection(view);

		
		IRow row=null;
		CellTreeNode node = null; 
		for (int i = 0; i < accountViewCollection.size(); i++) {
			AccountViewInfo accountViewInfo = accountViewCollection.get(i);
			row = this.tblMain.addRow();
			row.getCell("select").setValue(Boolean.FALSE);
			row.getCell("name").setValue(accountViewInfo.getName(SysContext.getSysContext().getLocale()));
			row.getCell("id"). setValue(accountViewInfo.getId().toString());
			row.setUserObject(accountViewInfo);
			
			node = new CellTreeNode();
			node.setValue(accountViewInfo.getNumber());
			node.setTreeLevel(accountViewInfo.getLevel());
			if(!accountViewInfo.isIsLeaf()){
				if(accountViewInfo.getLevel()==1){
				//����ϸ��Ŀ������ѡ��	Jelon Dec 11, 2006
//				row.getStyleAttributes().setLocked(true);
					node.setCollapse(true);
				}else{
					row.getStyleAttributes().setHided(true);
				}
				
				row.getStyleAttributes().setBackground(canntSelectColor);
				node.setHasChildren(true);
				
			}else{
				node.setHasChildren(false);
				if(accountViewInfo.getLevel()!=1){
					row.getStyleAttributes().setHided(true);
				}
			}
			row.getCell("number").setValue(node);
		}
	}
	
	private void createTableBySql(String accountTableId,BOSUuid cuId){
		String loc=LocaleUtils.getLocaleString(SysContext.getSysContext().getLocale());
		StringBuffer sql=new StringBuffer("select fid,fnumber,fisleaf,flevel,flongnumber,");
		sql.append("fname_L2 ");
		sql.append("from T_BD_AccountView where ");
		sql.append("FAccountTableID=?");
		sql.append(" and FControlUnitID=?");
		sql.append(" and FControl=? order by fnumber asc");
		
		try {
			List params=Arrays.asList(new Object[]{accountTableId,cuId.toString(),new Integer(AccountControlAttribute.NONE_VALUE)});
			IRowSet rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), params);
			IRow row=null;
			CellTreeNode node = null; 
			int level=0;
			while(rowSet.next()){
				level=rowSet.getInt("flevel");
				row=tblMain.addRow();
				
				row.getCell("select").setValue(Boolean.FALSE);
				row.getCell("name").setValue(rowSet.getString("fname_L2"));
				row.getCell("id"). setValue(rowSet.getString("fid"));
				node = new CellTreeNode();
				node.setValue(rowSet.getString("fnumber"));
				node.setTreeLevel(level);
				if(!rowSet.getBoolean("fisleaf")){
					if(level==1){
					//����ϸ��Ŀ������ѡ��	Jelon Dec 11, 2006
//					row.getStyleAttributes().setLocked(true);
						node.setCollapse(true);
					}else{
						row.getStyleAttributes().setHided(true);
					}
					
					row.getStyleAttributes().setBackground(canntSelectColor);
					node.setHasChildren(true);
					
				}else{
					node.setHasChildren(false);
					if(level!=1){
						row.getStyleAttributes().setHided(true);
					}
				}
				row.getCell("number").setValue(node);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		IRow row;
		accountViewCollection.clear();
		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				AccountViewInfo accountViewInfo = (AccountViewInfo) row.getUserObject();
				accountViewCollection.add(accountViewInfo);
				flag = true;
			}
		}
		if (flag) {
			//TODO sxhong
//			AccountViewFactory.getRemoteInstance().importDatas(accountViewCollection, this.addressProject.getId());
//			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
	}

	/**
	 * output actionCancel_actionPerformed method
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	/**
	 * output actionAllSelect_actionPerformed method
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
		}
	}

	/**
	 * output actionNoneSelect_actionPerformed method
	 */
	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
	}

	/**
	 * 
	 * �����������ѡ��ı䣬���¹�������ִ�в�ѯ
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	private void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
//			testTime("node");
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if(projectInfo.getFullOrgUnit().getPartFI()==null){//����Ƿ��Ѿ������ݿ���ȡ��ֵ��
				try{
					projectInfo=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectInfo.getId()), getCurProjectSelector());
				} catch (EASBizException e)
				{
					handUIException(e);
				}
				node.setUserObject(projectInfo);
			}
//			testTime("node end");
			this.loadDatas(projectInfo);
//			testTime("loadDatas");
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			//TODO �ݲ�����ѡ��˾�Ŀ�Ŀ	sxhong
			loadDatas(null);
		}

		if (this.tblMain.getRowCount() > 0) {
			tblMain.getSelectManager().select(0, 0);
		}
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
    	setConfirm(false);
    	disposeUIWindow();
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }
    
	private void confirm() throws Exception {
		//checkSelected();
    	//getData();
    	setConfirm(true);
	}

    public HashMap getData() throws Exception {
    	HashMap map=new HashMap();
		IRow row;
		accountViewCollection.clear();
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				AccountViewInfo accountViewInfo = (AccountViewInfo) row.getUserObject();
				
				//ֻ������ϸ��Ŀ
				if(accountViewInfo.isIsLeaf()){
					accountViewCollection.add(accountViewInfo);
				}
				
			}
		}
		disposeUIWindow();
		map.put("accountViewCollection", accountViewCollection);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			map.put("curProject", projectInfo);
		} 
		return map;
	}

    public boolean isOk() {
    	return isOk;
    }

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;

		disposeUIWindow();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.framework.client.AbstractCoreUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO �Զ����ɷ������
		return super.getSelectors();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_editStopped(e);
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_editValueChanged(e);
		
		if(e.getColIndex()==tblMain.getColumnIndex("select")){
			Boolean old=(Boolean)e.getOldValue();
			Boolean now=(Boolean)e.getValue();
			select(e.getRowIndex(),old.booleanValue(),now.booleanValue());
			/*
			
			int idx=e.getRowIndex();
			AccountViewInfo acct=(AccountViewInfo)tblMain.getRow(idx).getUserObject();
			int level=acct.getLevel();
			Boolean selected=(Boolean)e.getValue();

			IRow row=null;
			boolean isSelected=false;
			if(acct.isIsLeaf()){		//��ϸ��Ŀ
				isSelected=false;
				if(selected.equals(Boolean.TRUE)){	//ѡ��
					isSelected=true;
					
					//�����ϼ���Ŀ��ѡ��״̬
					for(int i=idx-1; i>=0; i--){
						row=tblMain.getRow(i);
						acct=(AccountViewInfo)row.getUserObject();
						
						if(acct.getLevel()==level-1){
							row.getCell("select").setValue(Boolean.TRUE);

							if((level-1)==1){
								break;
							}
							level-=1;
						}
					}
					
				}else{		//ȡ��ѡ��
					
					//��鵱ǰ��Ŀ����ı�����Ŀ�Ƿ���ѡ��
					for(int i=idx+1; i<tblMain.getRowCount(); i++){
						row=tblMain.getRow(i);
						acct=(AccountViewInfo)row.getUserObject();
						if(acct.getLevel()==level){
							if(row.getCell("select").getValue().equals(Boolean.TRUE)){
								isSelected=true;
								break;
							}
						}else{
							break;
						}
					}
					
					//��鵱ǰ��Ŀǰ��ı�����Ŀ�Ƿ���ѡ��
					if(!isSelected){
						for(int i=idx-1; i>=0; i--){
							row=tblMain.getRow(i);
							acct=(AccountViewInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isSelected=true;
								}
							}else{
								break;
							}
						}						
					}
					
					

					//���ͬ����Ŀ�Ƿ���ѡ�������ѡ����ȡ�����ϼ���Ŀ�Ĳ���
					boolean isFound=false;
					
					boolean isTrue=true;
					while(isTrue){
						isTrue=false;
						
						isFound=false;
						
						//��鵱ǰ��Ŀ����ı�����Ŀ�Ƿ���ѡ��
						for(int i=idx+1; i<tblMain.getRowCount(); i++){
							row=tblMain.getRow(i);
							acct=(AccountViewInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isFound=true;
									break;
								}
							}else if(acct.getLevel()>level){
								continue;
							}else{
								break;
							}
						}
						
						//��鵱ǰ��Ŀǰ��ı�����Ŀ�Ƿ���ѡ��
						if(!isFound){
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(AccountViewInfo)row.getUserObject();
								if(acct.getLevel()==level){
									if(row.getCell("select").getValue().equals(Boolean.TRUE)){
										isFound=true;
									}
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}						
						}
						

						if(!isFound){							
							level--;
							
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(AccountViewInfo)row.getUserObject();
								
								if(acct.getLevel()==level){
									row.getCell("select").setValue(Boolean.FALSE);
									
									isTrue=true;									
									break;
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}

						}
					}

					
				}
				
				
				//�����ϼ���Ŀ��ѡ��״̬
				for(int i=idx-1; i>=0; i--){
					row=tblMain.getRow(i);
					acct=(AccountViewInfo)row.getUserObject();
					
					if(acct.getLevel()==level-1){
						if(isTrue){
							row.getCell("select").setValue(Boolean.TRUE);
						}else{
							row.getCell("select").setValue(Boolean.FALSE);
						}

						if((level-1)==1){
							break;
						}
						level-=1;
					}
				}
				
			}else{		//����ϸ��Ŀ
				
				//boolean isTrue=(e.getValue().equals(Boolean.TRUE));
				
				for(int i=e.getRowIndex()+1; i<tblMain.getRowCount(); i++){
					row=tblMain.getRow(i);
					acct=(AccountViewInfo)row.getUserObject();
					if(acct.getLevel()>level){
						row.getCell("select").setValue(selected);
					}else{
						break;
					}
				}
			}
			
		*/}
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_tableClicked(e);
	}

    
    public boolean destroyWindow() {
    	setConfirm(false);
    	return super.destroyWindow();
    }
	
    private SelectorItemCollection getCurProjectSelector(){
		SelectorItemCollection selector=new SelectorItemCollection();
//		selector.add("*");
		selector.add("fullOrgUnit.CU.id");
		selector.add("fullOrgUnit.partFI.accountTable.id");
		selector.add("level");
		selector.add("displayName");
		selector.add("longNumber");
		selector.add("name");
		selector.add("isLeaf");
		return selector;
	}
    
    private SelectorItemCollection getAccountViewSelector(){
    	SelectorItemCollection selector=new SelectorItemCollection();
//    	selector.add("*");
    	selector.add("id");
    	selector.add("number");
    	selector.add("name");
    	selector.add("isLeaf");
    	selector.add("level");
    	selector.add("displayName");
    	selector.add("longNumber");
    	return selector;
    }
    
    private void testTime(String info){
    	if(false){
	    	logger.setLevel(Level.INFO);
	    	logger.info(info+" ����ʱ�����ϴ�ʱ��֮ǰ��ʱ:"+timer.Value());
	    	timer.reset();
	    	logger.setLevel(Level.DEBUG);
    	}
    }
    
    public void select(int row, boolean old, boolean now){
    	if(old==now) return;
    	tblMain.getCell(row, "select").setValue(Boolean.valueOf(now));
		AccountViewInfo acctSelect=(AccountViewInfo)tblMain.getRow(row).getUserObject();
		AccountViewInfo acct=null;
		int level=acctSelect.getLevel();
		//�¼�
    	for(int i=row+1;i<tblMain.getRowCount();i++){
    		acct = (AccountViewInfo)tblMain.getRow(i).getUserObject();
    		if(acct.getLevel()>level){
    			tblMain.getCell(i, "select").setValue(Boolean.valueOf(now));
    			
    		}else{
    			break;
    		}
    	}
    	
    	//�ϼ�
    	int parentLevel=level-1;
    	if(now){
        	for(int i=row-1;i>=0;i--){
        		if(parentLevel==0){
        			break;
        		}
        		acct = (AccountViewInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==parentLevel){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}

        		}
        	}
    	}else{
    		//��ѡ��,���ͬ���Ƿ���ѡ���
    		boolean hasSelected=false;
    		//�������
        	for(int i=row+1;i<tblMain.getRowCount();i++){
        		acct = (AccountViewInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==level){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()==Boolean.TRUE) {
        				hasSelected=true;
        				break;
        			}else if(acct.getLevel()<level){
        				break;
        			}
        		}
        	}
    		//�������
        	
        	if(!hasSelected){
            	for(int i=row-1;i>=0;i--){
            		acct = (AccountViewInfo)tblMain.getRow(i).getUserObject();
            		if(acct.getLevel()==level){
            			ICell cell = tblMain.getCell(i, "select");
            			if(cell.getValue()==Boolean.TRUE) {
            				hasSelected=true;
            				break;
    					}
            		}else if(acct.getLevel()<level){
            			row=i;
            			break;
            		}
            	}
        	}
        	
        	if(!hasSelected){

    			//���ø���
    			parentLevel=level-1;
            	for(int j=row;j>=0;j--){
            		if(parentLevel==0){
            			break;
            		}
            		acct = (AccountViewInfo)tblMain.getRow(j).getUserObject();
            		if(acct.getLevel()==parentLevel){
            			ICell cell = tblMain.getCell(j, "select");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
            		}
            	}
    		
        	}

    	}
		
    }
}