/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.json.JSONString;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.metas.AssignFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.client.NewSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierReviewGatherEditUI;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.ecore.MessageCollection;
import com.kingdee.eas.fm.ecore.MessageFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SupplierStockAddressReportUI extends
		AbstractSupplierStockAddressReportUI {

	private static final Logger logger = CoreUIObject
			.getLogger(SupplierStockAddressReportUI.class);
	private boolean isQuery = false;
	private boolean isOnLoad = false;

	public SupplierStockAddressReportUI() throws Exception {
		super();
		tblMain.checkParsed();
		tblMain.getDataRequestManager().addDataRequestListener(this);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		enableExportExcel(tblMain);
	}

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new SupplierStockAddressReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SupplierStockAddressReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if (isOnLoad)
			return;
		tblMain.removeColumns();
		tblMain.removeRows();

		tblMain.getColumn("name").getStyleAttributes().setFontColor(Color.BLUE);

		mergerTable(this.tblMain, new String[] { "id" }, new String[] { "id",
				"purchaseOrgUnit", "inviteType", "name", "contractor",
				"contractorPhone", "personName", "personPhone" });
	}

	public void mergerTable(KDTable table, String coloum[],
			String mergeColoum[]) {
		int merger = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (i > 0) {
				boolean isMerge = true;
				for (int j = 0; j < coloum.length; j++) {
					Object curRow = table.getRow(i).getCell(coloum[j])
							.getValue();
					Object lastRow = table.getRow(i - 1).getCell(coloum[j])
							.getValue();
					if (getString(curRow).equals("")
							|| getString(lastRow).equals("")
							|| !getString(curRow).equals(getString(lastRow))) {
						isMerge = false;
						merger = i;
					}
				}
				if (isMerge) {
					for (int j = 0; j < mergeColoum.length; j++) {
						table.getMergeManager().mergeBlock(merger,
								table.getColumnIndex(mergeColoum[j]), i,
								table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}

	private String getString(Object value) {
		if (value == null)
			return "";
		if (value != null && value.toString().trim().equals("")) {
			return "";
		} else {
			return value.toString();
		}
	}

	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if (isQuery)
			return;
		isQuery = true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) this.orgTree
				.getLastSelectedPathComponent();
		if (treeNode != null) {
			Window win = SwingUtilities.getWindowAncestor(this);
			LongTimeDialog dialog = null;
			if (win instanceof Frame) {
				dialog = new LongTimeDialog((Frame) win);
			} else if (win instanceof Dialog) {
				dialog = new LongTimeDialog((Dialog) win);
			}
			if (dialog == null) {
				dialog = new LongTimeDialog(new Frame());
			}
			dialog.setLongTimeTask(new ILongTimeTask() {
				public Object exec() throws Exception {
					RptParams resultRpt = getRemoteInstance().query(params);
					return resultRpt;
				}

				public void afterExec(Object result) throws Exception {
					RptParams rpt = getRemoteInstance().createTempTable(params);
					RptTableHeader header = (RptTableHeader) rpt
							.getObject("header");
					KDTableUtil.setHeader(header, tblMain);

					RptRowSet rs = (RptRowSet) ((RptParams) result)
							.getObject("rowset");
					tblMain.setRowCount(rs.getRowCount()
							+ tblMain.getRowCount());
					KDTableUtil.insertRows(rs, 0, tblMain);
				}
			});
			dialog.show();
		}
		isQuery = false;
	}

	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory
				.getRemoteInstance(), filter, "采购类别");
		this.supplierTypeTree.setModel(model);
		this.supplierTypeTree.setSelectionRow(0);
	}

	protected void buildOrgTree() throws Exception {
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "非采购组织不能操作");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,
				"", cuInfo.getId().toString(), null, FDCHelper
						.getActionPK(this.actionOnLoad));
		this.orgTree.setModel(orgTreeModel);
		this.orgTree.setSelectionRow(0);
	}

	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree
					.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}

	private void refresh() throws Exception {
		DefaultKingdeeTreeNode TypeNode = this
				.getSelectedTreeNode(supplierTypeTree);
		DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
		Object TypeInfo = null;
		if (TypeNode != null && TypeNode.getUserObject() != null) {
			TypeInfo = TypeNode.getUserObject();
			kDContainer1.setTitle(TypeNode.getText());
		}
		if (TypeInfo instanceof InviteTypeInfo) {
			String longNumber = ((InviteTypeInfo) TypeInfo).getLongNumber();
			params.setObject("inviteType", longNumber);
		} else {
			params.setObject("inviteType", null);
		}
		if (OrgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) OrgNode.getUserObject();
			String longNumber = org.getLongNumber();
			params.setObject("org", longNumber);
		} else {
			params.setObject("org", null);
		}
		query();
	}

	protected void supplierTypeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		this.refresh();
	}

	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}

	public void onLoad() throws Exception {
		isOnLoad = true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		buildOrgTree();
		buildInviteTypeTree();
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad = false;

		this.refresh();

	Context ctx=null;
//	_sendToDo(ctx);
	
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			if (tblMain.getColumnKey(e.getColIndex()).equals("name")) {
				String id = (String) tblMain.getRow(e.getRowIndex()).getCell(
						"id").getValue();
				if (id == null)
					return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID, id);
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						NewSupplierStockEditUI.class.getName(), uiContext,
						null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}


	
	
		protected void _sendToDo(Context ctx) throws BOSException {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			builder.appendSql("/*dialect*/ select FASSIGNID,FSUBJECT_L2,FPERSONUSERNAME_L1,FPROCDEFID,FPROCINSTID,FPERSONUSERID," +
					"FPROCDEFNAME_L2,FPERSONUSERID,FCREATEDTIME,sendtimes from t_wfr_assign where fstate in (1,32) and sended is null");
			
			IRowSet rs=builder.executeQuery();
			String modelId=null;
			String subject=null;
			String FPERSONUSERNAME_L1=null;
			String modelName=null;
			String targets=null;
			String param1=null;
			String param2=null;
			String dateString=null;
			String fPersonId=null;
			String docCreator=null;
			String url=null;
			String link=null;
			String errorMessage=null;

//			记录已发送条数
			int send=0;
//			记录发送成功条数
			int success=0;
//			记录发送失败条数
			int failed=0;
//			一共待传条数
			int waitToSend=0;
			List<String> list=null;
			try {
				while(rs.next()){
					send=send+1;
//					数据传输次数
					int sendtimes=0;
					if(rs.getString("sendtimes")!=null){
						sendtimes=rs.getInt("sendtimes");
					}
					if(rs.getString("FASSIGNID")!=null){
						 modelId=rs.getString("FASSIGNID");
					}
					if(rs.getString("FSUBJECT_L2")!=null){
						subject=rs.getString("FSUBJECT_L2");
					}
					if(rs.getString("FPROCINSTID")!=null){
						docCreator="";
						param1=rs.getString("FPROCINSTID");
						builder.clear();
						builder.appendSql("select tpu.fnumber as num from t_pm_user tpu left join T_WFR_ProcInst twp on tpu.fid=twp.FINITIATORID where twp.FPROCINSTID='"+param1+"'");
					
						IRowSet rs2=builder.executeQuery();
								while(rs2.next()){
									docCreator=rs2.getString("num").toLowerCase();
								}
					}
					if(rs.getString("FPERSONUSERNAME_L1")!=null){
						FPERSONUSERNAME_L1=rs.getString("FPERSONUSERNAME_L1");
					}
					if(rs.getString("FPROCDEFNAME_L2")!=null){
						param2=rs.getString("FPROCDEFNAME_L2");
					}else{
						param2="";
					}
					if(rs.getTimestamp("FCREATEDTIME")!=null){
						Timestamp FCREATEDTIME=rs.getTimestamp("FCREATEDTIME");
						long createTime1=FCREATEDTIME.getTime();
						Date date2 = new Date(createTime1);
						dateString=dateFormat.format(date2);
					}
					if(rs.getString("FPERSONUSERID")!=null){
						String FPERSONUSERID=rs.getString("FPERSONUSERID");
//					获取用户编码 targets
						builder.clear();
						builder.appendSql("/*dialect*/ select fnumber as num from T_PM_User where FID='"+FPERSONUSERID+"' ");
//						System.out.println(builder.getTestSql().toString());
						IRowSet rs2=builder.executeQuery();
						while(rs2.next()){
							targets=rs2.getString("num").toLowerCase();
						}
					}
					builder.clear();
					builder.appendSql("select furl from OAURL where type ='wsdl'");
					IRowSet rsu=builder.executeQuery();
					while(rsu.next()){
						url=rsu.getString("furl");
					}
					builder.clear();
					builder.appendSql("select furl from OAURL where type ='login'");
					IRowSet rsl=builder.executeQuery();
					while(rsl.next()){
						link=rsl.getString("furl");
					}
//					 创建代办
					Service service = new Service();
					Call call = (Call) service.createCall();
					
					call.setTargetEndpointAddress(new java.net.URL(url));
					call.setOperationName("sendERPTodo");
					call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);		
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					call.setUseSOAPAction(true);
					JSONObject data = new JSONObject();
//					if(ctx.getAIS().equals("easdb")){  //easdb
//						 data.put("appName", "DCEAS");
//						 data.put("modelName", "金蝶地产");
//					}
//					else{
				        data.put("appName", "WYEAS");
				        data.put("modelName", "金蝶物业");
//					}
//			        data.put("modelName", "金蝶地产");
//			        data.put("appName", "DCEAS");
			        data.put("param1", param1);
			        data.put("param2", param2);
			        data.put("modelId", modelId);
			        data.put("subject", subject);
			        data.put("link", link);
			        data.put("type", 1);
			        data.put("key","" );
			        data.put("docCreator",docCreator);
			        data.put("targets",targets); //00641 
			        data.put("createTime",dateString);
					String dataJson = data.toString().replace("'", "");
					
					if(sendtimes<5){
					try{
					String result = (String) call.invoke(new Object[] {data.toString() });
		        	System.out.println(data.toString());
		        	JSONObject jsonObject= (JSONObject) JSON.parse(result);
		        	 int code=Integer.parseInt(jsonObject.get("returnState").toString());
		        	 errorMessage = jsonObject.get("message").toString().replace("'", "");	
		        	 if(code==2){       //means success then updte sended=1, sendtimes donot update
		        		 success=success+1;
		        		    builder.clear();
							builder.appendSql(" update t_wfr_assign set sended = 1 where FASSIGNID='"+modelId+"' ");
							builder.executeUpdate();
		        	 }else{// when failed update set sendtimes+1,sended donot update
		        		 sendtimes=sendtimes+1;
		        		 failed=failed+1;
		        		 builder.clear();
							builder.appendSql(" update t_wfr_assign set sendTimes = '"+sendtimes+"' where FASSIGNID='"+modelId+"' ");
							builder.executeUpdate();
							builder.clear();
				        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
				        			" values('"+modelId+"',"+00+",'"+targets+"','"+subject+"','"+code+"','"+dataJson+"','"+errorMessage+"')");
				        	builder.execute();
		        	 }
					}catch(Exception e){
						    e.printStackTrace();
						 	builder.clear();
				        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
				        			" values('"+modelId+"',"+00+",'"+targets+"','"+subject+"','"+110+"','"+dataJson+"','"+e.getMessage()+"')");
				        	builder.execute();
				        	failed=failed+1;
					}
//					 sleep for 2seconds
//					 try {
//			                Thread.currentThread().sleep(2000);
//			            } catch (InterruptedException e) {
//			                e.printStackTrace();
//			            }
					}
				}
				System.out.println("一共发送数量："+send);
				System.out.println("成功数量："+success);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
		}    
	

}