/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyEnum;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.eas.fdc.sellhouse.SellStageEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SHEAttachListEditUI extends AbstractSHEAttachListEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHEAttachListEditUI.class);
    public SHEAttachListEditUI() throws Exception
    {
        super();
    }
    
    @Override
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic= super.getSelectors();
    	sic.add("isEnabled");
    	return sic;
	}
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void loadFields() {
		kdtEntry.checkParsed();
		super.loadFields();
	}
	protected IObjectValue createNewData() {
		SHEAttachListInfo info=new SHEAttachListInfo();
		info.setIsEnabled(true);
		info.setProductTypeProperty((ProductTypePropertyEnum)this.getUIContext().get("productTypeProperty"));
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SHEAttachListFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbProductTypeProperty);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbSellStage);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbSellType);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"明细不能为空！");
			SysUtil.abort();
		}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
				
			PropertyEnum property = (PropertyEnum) row.getCell("property").getValue();
			if (property == null) {
				FDCMsgBox.showWarning(this,"性质不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("property"));
				SysUtil.abort();
			} 
			String context = (String)row.getCell("context").getValue();
			if (context==null||"".equals(context.trim())){
				FDCMsgBox.showWarning(this,"内容不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("context"));
				SysUtil.abort();
			}
			if(context.indexOf("/")>=0){
				FDCMsgBox.showWarning(this,"内容不能包含字符'/'！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("context"));
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("productTypeProperty", ((ProductTypePropertyEnum)this.cbProductTypeProperty.getSelectedItem()).getValue()));
		filter.getFilterItems().add(new FilterItemInfo("sellStage", ((SellStageEnum)this.cbSellStage.getSelectedItem()).getValue()));
		filter.getFilterItems().add(new FilterItemInfo("sellType", ((SellTypeEnum)this.cbSellType.getSelectedItem()).getValue()));
		filter.getFilterItems().add(new FilterItemInfo("room.id", null,CompareType.EQUALS));
		if (this.editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, this.editData.getId(), CompareType.NOTEQUALS));
		}
		if(SHEAttachListFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"该附件清单类型已存在！");
			SysUtil.abort();
		}
		super.verifyInput(e);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.actionILine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionCopy.setVisible(true);
		this.txtDescription.setMaxLength(255);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.cbProductTypeProperty.setRequired(true);
		this.cbProductTypeProperty.setEnabled(true);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
    	KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < PropertyEnum.getEnumList().size(); i++){
        	combo.addItem(PropertyEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("property").setEditor(comboEditor);
		this.kdtEntry.getColumn("property").setRequired(true);
		this.kdtEntry.getColumn("context").setRequired(true);
	}
	 protected final boolean isTableColumnSelected(KDTable table)
     {
		 if(table.getSelectManager().size() > 0)
         {
			 KDTSelectBlock block = table.getSelectManager().get();
			 if(block.getMode() == 4 || block.getMode() == 8)
				 return true;
         }
		 return false;
     }
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(kdtEntry)) {
				row = kdtEntry.addRow();
				SHEAttachListEntryInfo entry = new SHEAttachListEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			} else {
				row = kdtEntry.addRow(top);
				SHEAttachListEntryInfo entry = new SHEAttachListEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
		} else {
			row = kdtEntry.addRow();
			SHEAttachListEntryInfo entry = new SHEAttachListEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			row.setUserObject(entry);
		}
	}

	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		SHEAttachListEntryInfo entry = new SHEAttachListEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtEntry.removeRow(activeRowIndex);
	}
}