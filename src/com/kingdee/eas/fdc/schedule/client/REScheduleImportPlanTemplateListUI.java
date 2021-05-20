/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��(ר)��ƻ����������--<����ƻ�ģ�����
 *		
 * @author		��С��
 * @version		EAS7.0		
 * @createDate	2011-08-20 
 * @see
 */
public class REScheduleImportPlanTemplateListUI extends AbstractREScheduleImportPlanTemplateListUI
{
    /** ȱʡ�ַ��� */
	private static final long serialVersionUID = 1L;
	
	/** ��־�ļ� */
	private static final Logger logger = CoreUIObject.getLogger(REScheduleImportPlanTemplateListUI.class);
	
	/** ������ */
	private static final String COL_NAME = "name";
	
	/** �������� */
	private static final String COL_LONGNUMBER = "longNumber";
	
	/** ������ */
	private static final String COL_ID = "id";
	
	/** ������ */
	private static final String COL_LEVEL = "level";
	
	/** �Ƿ����ӽڵ��� */
	private static final String COL_ISLEAF = "isLeaf";
	
	/** �Ƿ����ӽڵ��� */
	private static final String COL_PREDECESSOR_DESC = "predecessorDesc";
	
	/** �Ƿ����ӽڵ��� */
	private static final String COL_TASK_TYPE = "taskType";
	
	/** �Ƿ����ӽڵ��� */
	private static final String COL_BUSINESSTYPE_DESC = "businessTypeDesc";
	
	/** �Ƿ����ӽڵ��� */
	private static final String COL_REFERENCEDAY = "referenceDay";
	
    
    /**
     * output class constructor
     */
    public REScheduleImportPlanTemplateListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return RESchTemplateTaskFactory.getRemoteInstance();
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	
    	/* ����ȷ�ϡ�ȡ������Ť����ʾ */
    	this.btnConfirm.setEnabled(false);
    	this.btnClose.setEnabled(false);
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	
    	/* ���ù���������ʾ */
    	this.toolBar.setVisible(false);
    	this.toolBar.setSize(0, 0);
    	getUICustomToolBar().removeAll();
    	
    	/* ���ñ������ʾ��� */
    	this.tblMain.getColumn(COL_NAME).setWidth(200);
    	
    	/* ���õ����ƻ�ģ���� */
//    	RESchTemplateTreeSelector selector = new RESchTemplateTreeSelector();
//		this.prmpPlanTemplate.setSelector(selector);
//		
//		/* Ϊ�ƻ�ģ��F7��Ӽ��� */
//		this.prmpPlanTemplate.addDataChangeListener(new DataChangeListener(){
//			public void dataChanged(DataChangeEvent arg0) {
//				GetAllData();
//			}
//		});
    	Object ownerObj = getUIContext().get("Owner");
		if(null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(ownerObj instanceof MainScheduleEditUI){
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.MAINPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","����ƻ�ģ��",CompareType.NOTEQUALS));
			}else{
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.OTHERPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","ר��ƻ�ģ��",CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			this.prmtCatagory.setEntityViewInfo(view);
		}
		/* ��ձ����ݣ�ֻ����ѡ�����ڵ��ʱ���������� */
		this.tblMain.removeRows(false);
		
		/* ����ȷ�ϡ�ȡ������ť������ */
		this.btnConfirm.setEnabled(true);
		this.btnClose.setEnabled(true);
    }
    /**
     * output prmtCatagory_dataChanged method
     */
    protected void prmtCatagory_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	this.combTemplate.removeAllItems();
//    	this.combTemplate
        super.prmtCatagory_dataChanged(e);
        if(e.getNewValue()!=null && e.getNewValue() instanceof RESchTemplateCatagoryInfo){
        	RESchTemplateCollection cols=getRESchTemplate(((RESchTemplateCatagoryInfo)e.getNewValue()).getId().toString());
        	for(int i =0;i < cols.size();i++){
//        		this.combTemplate.addItem((cols.get(i).getName()));
        		this.combTemplate.addItem(cols.get(i));
        	}        	
        }
    }
    public  RESchTemplateCollection getRESchTemplate(String catagory) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("name");
    	sic.add("number");
    	sic.add("state");
    	sic.add("id");
    	sic.add("orgUnit.id");
    	sic.add("orgUnit.name");
    	sic.add("orgUnit.number");
    	sic.add("orgUnit.longNumber");
		view.setSelector(sic);
    	FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo org = SysContext.getSysContext().getCurrentFIUnit();
		String[] numbers = org.getLongNumber().split("!");
		Set orgSet = new HashSet();
		for (int i = 0; i < numbers.length; i++) {
			orgSet.add(numbers[i]);
		}
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", orgSet, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("catagory.id",catagory));
    	
    	/* modified by zhaoqin for R140923-0183 on 2015/01/14 */
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);
		view.setFilter(filter);
		view.setSorter(sorter);    

		RESchTemplateCollection cols = null;
		try {
			cols = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
    /**
     * output combTemplate_itemStateChanged method
     */
    protected void combTemplate_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	GetAllData();
    }
	/**
	 * @discription  <����ѡ�����Ľڵ㶯̬�ı�����������>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/20> <p>
	 * @param        <��>
	 * @return       <����ֵ����>
	 * 
	 * modifier      <��> <p>
	 * modifyDate    <��> <p>
	 * modifyInfo	 <��> <p>
	 * @see          <��ص���>
	 */
	 private void GetAllData() {
		/* ���ѡ�нڵ���ֵ���������ֹ */
//		if (null == this.prmpPlanTemplate.getValue() || "".equals(this.prmpPlanTemplate.getValue().toString().trim())) {
//			return;
//		}
		if (null == this.prmtCatagory.getValue() || "".equals(this.prmtCatagory.getValue().toString().trim())) {
			return;
		}
		if (null == this.combTemplate.getSelectedItem() || "".equals(this.combTemplate.getSelectedItem().toString().trim())) {
			return;
		}
//		if (prmpPlanTemplate.getValue() instanceof String) {
//			return;
//		}
		if (prmtCatagory.getValue() instanceof String) {
			return;
		}
		if (combTemplate.getSelectedItem() instanceof String) {
			return;
		}
//		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.prmpPlanTemplate.getValue();
		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.combTemplate.getSelectedItem();

		List taskList = new ArrayList();
		if (null != tmpInfo) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
			view.getSelector().add(new SelectorItemInfo("businessType.id"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.id"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.name"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.number"));
			
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("template.id", tmpInfo.getId().toString()));
			view.setFilter(filter);
			/* ������������ */
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo seq = new SorterItemInfo(COL_LONGNUMBER);
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			seq = new SorterItemInfo("businessType.bizType.number");
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			view.setSorter(sorter);
			RESchTemplateTaskCollection collection = new RESchTemplateTaskCollection();
			try {
				collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
				RESchTemplateTaskInfo taskInfo = new RESchTemplateTaskInfo();
				for (int i = 0; i < collection.size(); i++) {
					taskInfo = collection.get(i);
					taskList.add(taskInfo);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}

		}
		setTblMainData(taskList);
	 }
	 
	 private String getBizTypeDesc(RESchTemplateTaskBizTypeCollection cols) {
		StringBuffer str = new StringBuffer();
		RESchTemplateTaskBizTypeInfo info = null;
		int size = cols.size();
		for (int i = 0; i < size; i++) {
			info = cols.get(i);
			if (info.getBizType() != null) {
				str.append(info.getBizType().getName());
				if (i < size - 1) {
					str.append(",");
				}
			}

		}
		if (!StringUtils.isEmpty(str.toString())) {
			return str.toString();
		}
		return null;
	}
	 
	 /**
	  * @discription  <����ѡ�����ڵ�Ĳ�ͬ����̬Ϊ��ֵ>
	  * @author       <Xiaolong Luo>
	  * @createDate   <2011/08/20> <p>
	  * @param        <��>
	  * @return       <����ֵ����>
	  * 
	  * modifier      <��> <p>
	  * modifyDate    <��> <p>
	  * modifyInfo	 <��> <p>
	  * @see          <��ص���>
	  */
	 private void setTblMainData(List taskList) {
		/* ��ձ����� */
		tblMain.removeRows(false);
		tblMain.addRows(taskList.size());
		tblMain.setRowCount(taskList.size() + 1);
		/* ѭ��Ϊ��ĸ��и�ֵ */
		for (int k = 0; k < taskList.size(); k ++) {
			RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) taskList.get(k);
			IRow row = this.tblMain.getRow(k);
			/* ������ */
			row.getCell(COL_ID).setValue(taskInfo.getId().toString());
			/* ���������� */
			row.getCell(COL_NAME).setValue(taskInfo.getName());
			/* ǰ�������� */
			row.getCell(COL_PREDECESSOR_DESC).setValue(taskInfo.getPredecessorDesc());
			/* ���������� */
			row.getCell(COL_TASK_TYPE).setValue(taskInfo.getTaskType());
			/* ҵ�������� */
			String bizDesc = getBizTypeDesc(taskInfo.getBusinessType());
			if (bizDesc != null) {
				row.getCell(COL_BUSINESSTYPE_DESC).setValue(bizDesc);
			}
		
			/* �ο������� */
			row.getCell(COL_REFERENCEDAY).setValue(taskInfo.getReferenceDay() + "");
			/* �Ƿ����ӽڵ��� */
			row.getCell(COL_ISLEAF).setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
			/* ������ */
			row.getCell(COL_LEVEL).setValue(taskInfo.getLevel() + "");
			/* �������� */
			row.getCell(COL_LONGNUMBER).setValue(taskInfo.getLongNumber());
			
			row.getCell("description").setValue(taskInfo.getDescription());
			CellTreeNode treeNode = new CellTreeNode();
			
			/* ��ʾ��ֵ���˴��ǡ��������ơ��ַ��� */
			treeNode.setValue(taskInfo.getName());
			
			/* ���Σ���0��ʼ���˴�Ϊ����������� */
			treeNode.setTreeLevel(taskInfo.getLevel());
			
			treeNode.setHasChildren(!taskInfo.isIsLeaf());
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell(COL_NAME).getStyleAttributes().setLocked(false);
			
			/* ����ǰ�湹���ĵ�Ԫ�������� */
			row.getCell(COL_NAME).setValue(treeNode);

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell(COL_NAME).setValue("��������");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
	}
	 
   /**
	* @discription  <ȷ�ϵ���ģ��>
	* @author       <Xiaolong Luo>
	* @createDate   <2011/08/20> <p>
	* @param        <��>
	* @return       <����ֵ����>
	* 
	* modifier      <��> <p>
	* modifyDate    <��> <p>
	* modifyInfo	 <��> <p>
	* @see          <��ص���>
	*/	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
//		Object templateObj = this.prmpPlanTemplate.getValue();
		Object templateObj = this.combTemplate.getSelectedItem();
		if(null == templateObj){
			FDCMsgBox.showInfo("��ѡ��ƻ�ģ�壡");
			return ;
		}
		
		if(!(templateObj instanceof RESchTemplateInfo)){
			FDCMsgBox.showInfo("ѡ���ģ�����Ͳ���ȷ��");
			return ;
		}
		
		RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)templateObj;
		FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI)this.getUIContext().get("Owner");
		Date planStateDate = new Date();
		if(null != this.dpStartDate.getValue() && !"".equals(this.dpStartDate.getValue().toString())){
			planStateDate = (Date)this.dpStartDate.getValue();
		}
		
		/* ���õ���ƻ�ģ�巽�� */
		fDCScheduleBaseEditUI.importPlanTemplate(schTemplateInfo,planStateDate);
		
		/* �رմ��� */
		destroyWindow();
	}

	/**
	* @discription  <���ȡ��>
	* @author       <Xiaolong Luo>
	* @createDate   <2011/08/20> <p>
	* @param        <��>
	* @return       <����ֵ����>
	* 
	* modifier      <��> <p>
	* modifyDate    <��> <p>
	* modifyInfo	 <��> <p>
	* @see          <��ص���>
	*/	
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}
 
	
	public void onLoad() throws Exception {
		super.onLoad();
//		prmpPlanTemplate
//				.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7RESchTemplateQuery");
//		prmpPlanTemplate.setCommitFormat("$name$");
//		prmpPlanTemplate.setDisplayFormat("$name$");
//		prmpPlanTemplate.setEditFormat("$name$");

	}

	/**
	 * ���ͷ������˫����񲻵����༭���棬�����ж�
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);
	}
	
	protected String getEditUIName() {
		return null;
	}

	/**
	 *����ʾ������
	 */
	public void initUIToolBarLayout() {

	}
	
	/*
	 * ���ΰ���ͷ����
	 * @see com.kingdee.eas.framework.client.ListUI#isCanOrderTable()
	 */
	protected boolean isCanOrderTable() {
		return false;
	}
}