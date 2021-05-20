/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.editor.EditorFactory;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection;
import com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoInfo;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class UserSupplierAssoUI extends AbstractUserSupplierAssoUI
{
    private static final Logger logger = CoreUIObject.getLogger(UserSupplierAssoUI.class);
    
    /**
     * output class constructor
     */
    public UserSupplierAssoUI() throws Exception
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

    /**
     * output actionSaveAsso_actionPerformed
     */
    public void actionSaveAsso_actionPerformed(ActionEvent e) throws Exception
    {
       WebUserInfo user =  (WebUserInfo) this.getUserObject();
       UserSupplierAssoCollection entry = user.getEntry();
       entry.clear();
       
       int rowCount = this.tblSupplier.getRowCount();
       UserSupplierAssoInfo as = null;
       boolean selected = false;
       for(int i=0;i<rowCount;i++){
    	   Object o  =this.tblSupplier.getCell(i, "isAsso").getValue();
    	   if(o != null ){
    		   selected = Boolean.valueOf(o+"");
    	   }else{
    		   selected = false;
    	   }
    	   if(selected){
    		   as = new UserSupplierAssoInfo();
    		   as.setSupplier((SupplierStockInfo) this.tblSupplier.getCell(i, "supplierId").getUserObject());
    		   as.setParent(user);
    		   as.setSeq(i+1);
    		   entry.add(as);
    	   }
       }
       user.put("formAsso", "true");
       IObjectPK pk =WebUserFactory.getRemoteInstance().save(user);
       if(pk != null){
    	   FDCMsgBox.showInfo("保存关联信息成功。");
       }
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	
    	this.tblSupplier.checkParsed();
    	this.tblSupplier.getColumn("isAsso").setEditor(new EditorFactory.BooleanEditor());
    	
    	Map uiContext =  getUIContext();
    	WebUserInfo userInfo = (WebUserInfo) uiContext.get("webUser");
    	loadUserInfo(userInfo);
    	this.setUserObject(userInfo);
    	
    }

	private void loadUserInfo(WebUserInfo userInfo) {
		
		//加载基础信息到界面 
		this.txtCompanyName.setText(userInfo.getCompanyName());
		this.txtMobileNumber.setText(userInfo.getMobileNumber());
		this.txtEmail.setText(userInfo.getEmail());
		this.txtName.setText(userInfo.getName());
		this.txtPhone.setText(userInfo.getTelephone());
		this.txtPosition.setText(userInfo.getPosition());
		this.txtPassword.setText(userInfo.getPassword());
		this.combRegisterState.setSelectedItem(userInfo.getRegisterState());
		this.pkCreateDate.setValue(userInfo.getCreateTime());
		
		//加载关联供应商信息到列表上
		this.tblSupplier.checkParsed();
		UserSupplierAssoCollection entry = userInfo.getEntry();
		int size = entry.size();
		UserSupplierAssoInfo assoInfo = null;
		IRow row  = null;
		SupplierStockInfo supplier = null;
		for(int i=0;i<size;i++){
			assoInfo = entry.get(i);
			row = this.tblSupplier.addRow();
			row.setUserObject(assoInfo);
			supplier = assoInfo.getSupplier();
			supplier.setBoolean("isAsso",Boolean.TRUE);
			loadSupplierInfo(row, supplier);
			
		}
		
	}
	@Override
	public boolean destroyWindow() {
		// TODO Auto-generated method stub
		WebUserListUI lui =  (WebUserListUI) getUIContext().get("pUI");
		try {
			lui.refreshList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.destroyWindow();
	}

	private void loadSupplierInfo(IRow row, SupplierStockInfo supplier) {
		row.getCell("supplierId").setUserObject(supplier);
		row.getCell("supplierId").setValue(supplier.getId()+"");
		row.getCell("supplierNumber").setValue(supplier.getNumber());
		row.getCell("supplierName").setValue(supplier.getName());
		if(supplier.getInviteType()!=null){
			row.getCell("inviteType").setValue(supplier.getInviteType().getName());
		}
		row.getCell("isAsso").setValue(supplier.getBoolean("isAsso"));
	}
	
	@Override
	public void actionMatch_actionPerformed(ActionEvent e) throws Exception {
		
		String queryName = this.txtMatchName.getText();
		if(StringUtils.isEmpty(queryName)){
			FDCMsgBox.showError("必须输入供应商名称，才能进行匹配。");
			SysUtil.abort();
		}
		
		
		this.tblSupplier.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("inviteType.*");
		sic.add("*");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name","%"+queryName.trim()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("state",SupplierStateEnum.APPROVE,CompareType.EQUALS));
		view.setFilter(filter);
		SorterItemCollection sorters = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo();
		si.setSortType(SortType.ASCEND);
		si.setPropertyName("number");
		sorters.add(si);
		view.setSorter(sorters);
		SupplierStockCollection suppliers=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
		
		int size = suppliers.size();
		IRow row = null;		
		SupplierStockInfo supplier = null;
		for(int i=0;i<size;i++){
			supplier = suppliers.get(i);
//			if(isAlreadyInclude(supplier.getId().toString())){
//				supplier.setBoolean("isAsso",Boolean.TRUE);
//			}
			row = this.tblSupplier.addRow();
			loadSupplierInfo(row, supplier);
		}
		
	}
	
	private boolean isAlreadyInclude(String SupplierId){
		boolean isExists = false;
		WebUserInfo dataObject = (WebUserInfo) this.getUserObject();
		UserSupplierAssoCollection assoSuppliers = dataObject.getEntry();
		UserSupplierAssoInfo as = null;
		int size = assoSuppliers.size();
		for(int i=0;i<size;i++){
			as = assoSuppliers.get(i);
			if(SupplierId.equals(as.getSupplier().getId().toString())){
				isExists = true;
				break;
			}
		}
		
		return isExists;
	}
	

}