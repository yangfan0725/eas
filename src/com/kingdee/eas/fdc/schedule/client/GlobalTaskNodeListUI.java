/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.UuidException;


/**
 * 
 * @(#)						
 * ��Ȩ�������������������޹�˾��Ȩ���� ������ ���Źܿؽڵ���ʱ������
 * 
 * @author ����ͩ
 * @version EAS7.0.5
 * @createDate 2013-4-27
 * @see
 */
public class GlobalTaskNodeListUI extends AbstractGlobalTaskNodeListUI
{
	private static final Logger logger = CoreUIObject.getLogger(GlobalTaskNodeListUI.class);
	
    
    public GlobalTaskNodeListUI() throws Exception
    {
        super();
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
//        checkSelected();
    	super.actionAddNew_actionPerformed(e);
        
    }


    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {   	
    	verifyRefRESchTemplate();
		super.actionRemove_actionPerformed(e);
    }
    
    protected FDCDataBaseInfo getBaseDataInfo() {
    	String id = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	GlobalTaskNodeInfo  info  = null;
    	try {
			info = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (UuidException e) {
			handUIException(e);
		}
    	return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return GlobalTaskNodeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {		
		return GlobalTaskNodeEditUI.class.getName();
	}
	
	public void onLoad() throws Exception {
		this.tblMain.checkParsed();
		if (SysContext.getSysContext().getCurrentOrgUnit() != null
				//�жϵ�ǰ��֯�Ƿ�Ϊ���ڹ���Ԫ��
				//&& OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()))
				&& OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))
		{
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);

			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		for(int i = 0;i < tblMain.getColumnCount();i ++){
			tblMain.getColumn(i).setMergeable(false);
		}		

		this.tblMain.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		super.onLoad();
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//����ɾ������
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
}
	
	//ȥ��������
//	protected boolean isCanOrderTable() {
//		return false;
//	}
	
	public void onShow() throws Exception {
		super.onShow();
		getMainTable().setColumnMoveable(false);
		// �������ý��ð�ť
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);

	}
	
	protected void afterTableFillData(KDTDataRequestEvent e) {	
        super.afterTableFillData(e);               
 
	}
	

	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		//������Щ�¼������Ǳ༭��������ť��
		if(e.getActionCommand().contains("AddNew")||e.getActionCommand().contains("View")||e.getActionCommand().contains("Double Clicked")||e.getActionCommand().contains("Edit")){
			//ȡ�����ڱ���
			uiContext.put("GTNNodes", getExistNode());
		}
	}
	
	/**
	 * ������ڱ����в�Ϊ�գ����ӵ����ڱ����б�
	 */
	private List getExistNode() {
		List<String> numberList = new ArrayList<String>();
		for(int i=0;i<tblMain.getRowCount();i++){
			if(tblMain.getRow(i).getCell("number").getValue()!=null){
				numberList.add(tblMain.getRow(i).getCell("number").getValue().toString());
			}
		}
		return numberList;
	}
	
	private void verifyRefRESchTemplate() {
		RESchTemplateTaskCollection cols = FDCScheduleBaseHelper.getRESchTemplateTask();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			RESchTemplateTaskInfo tasks = cols.get(i);
//			newMap.put(taskNode.getId().toString(), taskNode);
			//name����
			newMap.put(tasks.getName().toString(), tasks);
		}
		if (newMap.isEmpty()) {
			return;
		}
		ArrayList alist = this.tblMain.getSelectManager().getBlocks();
		int top = -1;
		int bottom = -1;
		if(alist != null){			
			top = ((KDTSelectBlock)alist.get(0)).getTop();
			bottom = ((KDTSelectBlock)alist.get(0)).getBottom();
		}
		Map msgMap = new HashMap();
		if( top != -1 && bottom != -1){
			int rowIndex = top;
			if (rowIndex < 0) {
				return;
			}
			for(int j=top ; j < bottom + 1; j++ ){
				String key=this.tblMain.getRow(j).getCell("name").getValue().toString();
	             if(newMap.containsKey(key)){
	  				msgMap.put(key, newMap.get(key));
	              }
			}
		}
			
//		GlobalTaskNodeCollection nodes = FDCScheduleBaseHelper.getGlobalTaskNode();
//	    GlobalTaskNodeInfo task = null;
//	    int size = nodes.size();
//		for (int i = 0; i < size; i++) {
//             task = nodes.get(i);
//             //name����
//             if(newMap.containsKey(task.getName().toString())){
// 				newMap.remove(task.getName().toString());
//             }
////			if (StringUtils.isEmpty(task.getSrcGroupNode())) {
////				continue;
////			} else {
////				newMap.remove(task.getSrcGroupNode());
////			}
//		}
		
		/* modified by zhaoqin for BT829256 on 2014/06/09 */
		if (!msgMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = msgMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				RESchTemplateTaskInfo reinfo =(RESchTemplateTaskInfo) ((Entry) it.next()).getValue();
				errMsg.append("ģ������ ");
				errMsg.append(reinfo.getName());
				errMsg.append(" �ѱ�����ƻ�ģ�� ");
//				errMsg.append(reinfo.getTemplate().getNumber());
				errMsg.append(reinfo.getTemplate().getName());
				errMsg.append(" ���ã�");
				errMsg.append("\n");
			}
			FDCMsgBox.showDetailAndOK(this, "��ǰģ�������ѱ�����ƻ�ģ�����ã�����ɾ��!", errMsg.toString(), MsgBox.ICONERROR);
			abort();
		}
		newMap.clear();
	}
	public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(!e.getActionCommand().contains("AddNew")&&!e.getActionCommand().contains("Print")&&!e.getActionCommand().contains("PrintPreview")){
			if(e.getActionCommand().contains("Edit")&&tblMain.getRow(rowIndex).getUserObject() != null){
				FDCMsgBox.showError("��ǰ�в���ִ�д˲�����");
				abort();
			}else if(e.getActionCommand().contains("Remove")&&tblMain.getRow(rowIndex).getUserObject()!= null){
				FDCMsgBox.showError("��ǰ�в���ִ�д˲�����");
				abort();
			}else if(e.getActionCommand().contains("Double Clicked")&&tblMain.getRow(rowIndex).getUserObject()!= null){
				FDCMsgBox.showError("��ǰ�в���ִ�д˲�����");
				abort();
			}
		}
	}
}