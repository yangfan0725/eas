/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RESchTemplateTaskListUI extends AbstractRESchTemplateTaskListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RESchTemplateTaskListUI.class);

	/**
	 * output class constructor
	 */
	public RESchTemplateTaskListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	
	protected void bpRESchTemplate_dataChanged(DataChangeEvent e)
			throws Exception {
		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.bpRESchTemplate.getValue();
		GetAllData(tmpInfo);
		
//		this.tblMain.setRowCount(10000); 
//		super.bpRESchTemplate_dataChanged(e);
	}
	 /**
	  * ����UIToolBar
	  */
	public void initUIToolBarLayout() {
		
	}
	public void initUIMenuBarLayout() {
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnAddNew.setVisible(false);
		this.btnAddNew.setEnabled(false);
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnQueryScheme.setVisible(false);
		this.btnRefresh.setVisible(false);
		this.btnView.setVisible(false);
		this.btnOk.setEnabled(true);
		this.btnOk.setVisible(true);
		this.btnCancel1.setEnabled(true);
		this.btnCancel1.setVisible(true);
	
		
	}
	 
	public void onLoad() throws Exception {
		
		super.onLoad();
		tblMain.removeRows(false);
		boundEvent();
		tblMain.getColumn("name").setWidth(350);
		this.toolBar.setVisible(false);
		this.toolBar.setSize(0, 0);
		getUICustomToolBar().removeAll();
	}
	/**
	 * ��������Ҫʵ�ֲ�ѯ�����������õ�Ԫ����<br>
	 * 1��ȡ��ԭ����<br>
	 * 2��ȡ�������κ��Ƿ���ϸ�ڵ�<br>
	 * 3��������Ԫ�����ڵ㲢set����Ԫ��<br>
	 * 4������������������
	 * * @description �鿴����
	 * @author ����ΰ
	 * @createDate 2011-8-13
	 * @version EAS7.0
	 * @see
	 */
	public void onShow() throws Exception {
		super.onShow();
		int count = this.tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = this.tblMain.getRow(i);
			String value = (String) row.getCell("name").getValue();
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue())
					.booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();

			String longNumber = row.getCell("longNumber").getValue().toString();
			longNumber = longNumber.replaceAll("!", ".");
			row.getCell("longNumber").setValue(longNumber);

			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(value);// ��ʾ��ֵ���˴��ǡ��������ơ��ַ���
			treeNode.setTreeLevel(level);// ���Σ���0��ʼ���˴�Ϊ�����������
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// ����ǰ�湹���ĵ�Ԫ��������

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell("name").setValue("��������");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
		
	}
    
	/**
	 * 
	 * @description �õ�ģ���µ���������
	 * @author ����ΰ
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	 private void GetAllData(RESchTemplateInfo template)throws Exception{
		 List taskList = new ArrayList();
		 if (template != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("template.id", template.getId().toString()));
			view.setFilter(filter);

			SorterItemInfo sii = new SorterItemInfo();
			sii.setPropertyName("longNumber");
			sii.setSortType(SortType.ASCEND);
			view.getSorter().add(sii);

			RESchTemplateTaskCollection collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			for (int i = 0; i < collection.size(); i++) {
				taskList.add(collection.get(i));
			}

		}
		setTblMainData(taskList);
	 }
//	 /**
//	  * ���tblMain������
//	  */
//	 public void cleanTblMainData(){
//		tblMain.removeRows();
//	 }
	 /**
		 * ��������Ҫʵ��ѡ��ģ������½����������õ�Ԫ����
		 * @description ѡ��ģ����������ñ���е�����
		 * @author ����ΰ
		 * @createDate 2011-8-9
		 * @version EAS7.0
		 * @see
		 */
	 private void setTblMainData(List taskList) {
		// ��ձ�����
		tblMain.removeRows(false);
		tblMain.addRows(taskList.size());
		tblMain.setRowCount(taskList.size() + 1);
		// if(taskSet.size()==0){
		// FDCMsgBox.showWarning("��ѡ��ģ����û�������ݣ�������ѡ��ƻ�ģ��");
		// return;
		//				
		// }
		for (int k = 0; k < taskList.size(); k ++) {
			RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) taskList.get(k);
			IRow row = this.tblMain.getRow(k);
			row.getCell("id").setValue(taskInfo.getId().toString());
			row.getCell("name").setValue(taskInfo.getName());
			row.getCell("isLeaf").setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
			row.getCell("level").setValue(taskInfo.getLevel() + "");
			row.getCell("longNumber").setValue(taskInfo.getLongNumber());
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(taskInfo.getName());// ��ʾ��ֵ���˴��ǡ��������ơ��ַ���
			treeNode.setTreeLevel(taskInfo.getLevel());// ���Σ���0��ʼ���˴�Ϊ�����������
			treeNode.setHasChildren(!taskInfo.isIsLeaf());
			// treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// ����ǰ�湹���ĵ�Ԫ��������

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell("name").setValue("��������");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
		
	}
	 /**
		 * 
		 * @description ���α���ܵ��
		 * @author ����ΰ
		 * @createDate 2011-8-9
		 * @version EAS7.0
		 * @see
		 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	public void boundEvent() throws Exception {

		RESchTemplateTreeSelector selector = new RESchTemplateTreeSelector();
		this.bpRESchTemplate.setSelector(selector);
		if (bpRESchTemplate.getValue() instanceof RESchTemplateInfo) {
			RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo) bpRESchTemplate.getValue();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("template.id", schTemplateInfo.getId());
			view.setFilter(filter);
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			this.tblMain.setSize(195, rESchTemplateTaskCollection.size());
		}
		
		destroyWindow();
		
	}
	/**
	 * 
	 * @description ��Ӧѡ��ģ������ȷ����ť
	 * @author ����ΰ
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		if(this.bpRESchTemplate.getText()==null||"".equals(this.bpRESchTemplate.getText().trim())){
			 FDCMsgBox.showWarning("����ѡ��ģ�壬���ܵ���");
			 return;
		}
		else{
			RESchTemplateInfo template=(RESchTemplateInfo) this.bpRESchTemplate.getValue();
			String templateID=template.getId().toString();
			
			if(this.getUIContext().get("standatrdUI") instanceof StandardTaskGuideNewListUI){
				EntityViewInfo view =new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("template.id",templateID));
				view.setFilter(filter);
				RESchTemplateTaskCollection collection=RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
				if(collection.size()==0){
					FDCMsgBox.showWarning("��ѡ���ģ������û�����ݣ�������ѡ��");
					return;
				}
				StandardTaskGuideNewListUI standardTaskGuideNewListUI =(StandardTaskGuideNewListUI)this.getUIContext().get("standatrdUI");
				standardTaskGuideNewListUI.templateImport(templateID);
				destroyWindow();
			}
//			getUIContext().put("templateID", templateID);
			
			 /* ����ѡ���ģ��id */
			if (bpRESchTemplate.getValue() instanceof RESchTemplateInfo) {
				RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo) bpRESchTemplate.getValue();
				/* ���õ���ģ�巽�� */
				if (getUIContext().get("RESchTemplateListUI") instanceof RESchTemplateListUI) {
					RESchTemplateListUI ui = (RESchTemplateListUI) getUIContext().get("RESchTemplateListUI");
					ui.importTemplate(String.valueOf(null == schTemplateInfo.getId() ? "" : schTemplateInfo.getId().toString()));
					destroyWindow();
				}
			}
		}
		
	}
 
	public void actionNo_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}
   
	protected ICoreBase getBizInterface() throws Exception {

		return RESchTemplateTaskFactory.getRemoteInstance();

	}

	protected String getEditUIName() {
		return null;
	}
	
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}
	protected void fillFirstData(RequestRowSetEvent arg0) {
		super.fillFirstData(arg0);
		
	}
	
	/**
	 * ������cu����
	 */
	protected boolean isIgnoreCUFilter() { 
    	return true;
    }
}