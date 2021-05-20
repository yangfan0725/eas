/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MainScheduleListUI extends AbstractMainScheduleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MainScheduleListUI.class);
    
    /**
     * output class constructor
     */
    public MainScheduleListUI() throws Exception
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
    
    protected FilterInfo getMainFilter() throws Exception {
    	//����ڵ㰴������Ŀ����,�ܿ���������Ŀ�ſ��Կ����ƻ�
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	Set prjIdSet=getProjectLeafsOfNode(node);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("project.id",prjIdSet,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("scheduleType.id",TaskTypeInfo.TASKTYPE_MAINTASK));
    	filter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ZERO,CompareType.GREATER));
		return filter;
    }
    
    protected String getEditUIName() {
    	return MainScheduleEditUI.class.getName();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnCancelCancel.setText("ִ��");
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    }
    
    protected void buildTree() throws Exception {
    	super.buildTree();
//    	ScheduleTaskTreeBuilder.build(this, treeMain, "nOH5XOgQSTqP92mLeIdcQvnl6Ss=");
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		//ѡ�����ϸ���̽ڵ�ʱ����������мƻ�����
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!node.isLeaf()) {
			FDCMsgBox.showWarning(this, "��ѡ�񹤳���Ŀ��ϸ�ڵ�");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
}