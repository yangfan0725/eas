/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ISupplierApply;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.eas.fdc.contract.SupplierApplyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
/**
 * output class name
 */
public class SupplierApplyListUI extends AbstractSupplierApplyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierApplyListUI.class);
    protected static final String CANTEDIT = "cantEdit";
    protected static final String CANTREMOVE = "cantRemove";
    public SupplierApplyListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception
	{
    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		initControl();
		
		actionQuery.setEnabled(true);
		
	}
    protected void initControl(){
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
    }
    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
    public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((SupplierApplyInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
    protected String getEditUIName() {
		return SupplierApplyEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return SupplierApplyFactory.getRemoteInstance();
	}
    /**
     * 审批
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((ISupplierApply)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((ISupplierApply)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum bizState = getBizState(id);
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	checkBeforeEditOrRemove(CANTEDIT,id);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void fetchInitData() throws Exception {
    }
	@Override
	public void actionToYB_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionToYB_actionPerformed(e);
		FDCSQLBuilder builder1=new FDCSQLBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = null;
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("supplierType.*");
		
  		JSONObject initjson=new JSONObject();
		 JSONObject esbjson=new JSONObject();
		 String instId=null;
		 String requestTime=null;
		 JSONObject datajson=new JSONObject();
		 JSONArray ybarr=new JSONArray();
		 String instsId=null;
		 String requestsTime=null;
	
		 builder1.clear();
		 builder1.appendSql(" select instId,requestTime from esbInfo where source='supplier'");
		 IRowSet rs3=builder1.executeQuery();
		 try {
			while(rs3.next()){
				  instId=rs3.getString("instId");
				  requestTime=rs3.getString("requestTime");
			 }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 if(instsId!=null){
			 esbjson.put("instId",instsId);
		 }
		 if(requestsTime!=null){
			 esbjson.put("requestTime",requestsTime);
		 }
//			上次返回在时间戳
		 builder1.clear();
		 builder1.appendSql(" select ybtime from ybTimeRecord where source='supplier'");
			IRowSet rs1=builder1.executeQuery();
			try {
				if(rs1.first()&&rs1!=null){
				 timestamp=rs1.getString("ybtime");
				}else{
					timestamp="";
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int ii = 0; ii < id.size(); ii++){
				JSONObject ybjson=new JSONObject();
				SupplierApplyInfo info=SupplierApplyFactory.getRemoteInstance().getSupplierApplyInfo(new ObjectUuidPK(id.get(ii).toString()), sic);
				if(info.getId()!=null){
					ybjson.put("builderId",info.getId().toString());
				}else{
					ybjson.put("builderId","");
				}
				if(info.getName()!=null){
					ybjson.put("builderName",info.getName().replaceAll("\\s", ""));
				}else{
					ybjson.put("builderName","");
				}
				
				ybjson.put("contactName",null);
				ybjson.put("contactDetail",null);
				ybjson.put("contactAddress",null);
				ybjson.put("contactEmail",null);
				ybjson.put("contactFax",null);
				ybjson.put("remark",null);
				ybjson.put("state","1");
				if(info.getCreateTime()!=null){
					String createTime=sdf.format(info.getCreateTime());
					ybjson.put("createTime",createTime);
				}else{
					ybjson.put("createTime","");
				}
				if(info.getLastUpdateTime()!=null){
					String updateTime=sdf.format(info.getLastUpdateTime());
					ybjson.put("updateTime",updateTime);
				}else{
					ybjson.put("modifiedTime","");
				}

				if(info.getTaxerNum()!=null){
    				
    					ybjson.put("creditCode", info.getTaxerNum().toString());
    				
    			}else{
    				ybjson.put("creditCode", "");
    			}
				

				ybarr.add(ybjson);
				 datajson.put("datas",ybarr);
					datajson.put("timestamp",timestamp);
					initjson.put("esbInfo", esbjson);
					initjson.put("requestInfo",datajson);
					
			}
//	      		to_yb
		      		try {

		      			String rs11=SHEManageHelper.execPostYBsupplier(null, initjson.toJSONString(),timestamp);
		      			JSONObject rso = JSONObject.parseObject(rs11);
		      			if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
		      				throw new EASBizException(new NumericExceptionSubItem("100",rso.getJSONObject("esbInfo").getString("returnMsg")));
		      			}else{
		      				JSONObject rst=rso.getJSONObject("esbInfo");
		      				 instId=rst.getString("instId");
		      				 requestTime=rst.getString("requestTime");
		      				 builder1.clear();
		      				 builder1.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='supplier'");
		      				 builder1.executeUpdate();
		      				 JSONObject rst1=rso.getJSONObject("resultInfo");
		      				 String ts1=rst1.getString("timestamp");
		      				 builder1.clear();
		      				 builder1.appendSql("update ybTimeRecord set ybTime='"+ts1+"' where source='supplier' ");
		      				 builder1.executeUpdate();
		      				MsgBox.showInfo("供应商同步成功");
		      			}
		      		} catch (SQLException e1) {
		      			e1.printStackTrace();
		      		} catch (IOException e1) {
		      			e1.printStackTrace();
		      		}
		      		
	}

}