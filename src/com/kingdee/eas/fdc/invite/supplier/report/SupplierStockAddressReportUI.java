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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
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
import com.kingdee.eas.common.EASBizException;
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
import com.kingdee.eas.fdc.contract.app.HttpClientUtil;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.client.NewSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierReviewGatherEditUI;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
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
import com.kingdee.util.NumericExceptionSubItem;

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

//	Context ctx=null;
////	_sendToDo(ctx);
//	mkdangan(ctx);
	
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
			JSONObject login=new JSONObject();
    		String tokenId=null;
    		String entCode=null;
    		String appCode=null;
    		String secret=null;
    		String mturl=null;
    		Timestamp ts = new Timestamp(System.currentTimeMillis());
    		long lt = ts.getTime();
    		try {
    			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    			builder.appendSql("select * from dl_mk where type='bx' ");
    			IRowSet rs=builder.executeQuery();
    			while(rs.next()){
    				mturl=rs.getString("url");
    				entCode=rs.getString("entCode");
    				appCode=rs.getString("appCode");
    				secret=rs.getString("secret");
    			}
    			login.put("appCode", appCode);
    			login.put("secret", SHA(secret+lt));
    			login.put("timestamp", lt);
    			
    			String respStr = HttpClientUtil.sendRequest(mturl+"/auth/login", "POST", "application/json;charse=UTF-8", "UTF-8", null, login.toJSONString());
    			
    			JSONObject crso = JSONObject.parseObject(respStr);
    			JSONObject d=crso.getJSONObject("data");
    			if(!"true".equals(crso.getString("success"))){
    				throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
    			}else{
    				tokenId=d.getString("tokenId");
    				entCode=d.getString("entCode");
    			}
    		} catch (Exception e) {
    			try {
					throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		//get invoices
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		Date now = new Date();
    		Calendar c = Calendar.getInstance();
    		c.setTime(now);
    		c.add(Calendar.MONTH, -1);
    		Date ago = c.getTime();
    		String exportAtEnd = simpleDateFormat.format(now);
    		String exportAtStart=simpleDateFormat.format(ago);
    		
    		HashMap header=new HashMap();
    		header.put("tokenId", tokenId);
    		header.put("entCode", entCode);
    		JSONObject dataJson=new JSONObject();
//    		dataJson.put("offset",0);
//    		dataJson.put("limit", 10);
//    		dataJson.put("exportAtStart", exportAtStart);
//    		dataJson.put("exportAtEnd", exportAtEnd);
//    		dataJson.put("employeeId", "00718");
//    		try {
//				String respStr = HttpClientUtil.sendRequest(mturl+"/invoice/search", "POST", "application/json;charse=UTF-8", "UTF-8", header, dataJson.toJSONString());
//				JSONObject crso = JSONObject.parseObject(respStr);
//			    com.alibaba.fastjson.JSONArray dJson=crso.getJSONArray("data");
//				System.out.print(dJson.size());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			

		}
		
		protected void mkdangan(Context ctx) throws BOSException {
			JSONObject login=new JSONObject();
    		String tokenId=null;
    		String entCode=null;
    		String appCode=null;
    		String secret=null;
    		String mturl=null;
    		Timestamp ts = new Timestamp(System.currentTimeMillis());
    		long lt = ts.getTime();
    		try {
    			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    			builder.appendSql("select * from dl_mk where type='da'");
    			IRowSet rs=builder.executeQuery();
    			while(rs.next()){
    				mturl=rs.getString("url");
    				entCode=rs.getString("entCode");
    				appCode=rs.getString("appCode");
    				secret=rs.getString("secret");
    			}
    			login.put("userCode", appCode);
    			login.put("secretToken", SHA(secret+appCode+lt));
    			login.put("timestamp", lt);
    			
    			HttpClient httpClient =new HttpClient();
        		PostMethod post = new PostMethod("http://172.17.4.52:8080/open/auth/login");
        		post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
        		post.setRequestBody(login.toJSONString());
        		httpClient.executeMethod(post);
        		
        		String respStr = post.getResponseBodyAsString();
                post.releaseConnection();
    			
    			JSONObject crso = JSONObject.parseObject(respStr);
    			JSONObject d=crso.getJSONObject("data");
    			if(200!=(crso.getInteger("code"))){
    				throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
    			}else{
    				tokenId=d.getString("token");
    			}
    		} catch (Exception e) {
    			try {
					throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		Date now = new Date();
    		Calendar c = Calendar.getInstance();
    		c.setTime(now);
    		c.add(Calendar.MONTH, -1);
    		Date ago = c.getTime();
    		String exportAtEnd = simpleDateFormat.format(now);
    		String exportAtStart=simpleDateFormat.format(ago);
    		
    		HashMap header=new HashMap();
    		header.put("tokenId", tokenId);
    		header.put("entCode", entCode);
    		JSONObject dataJson=new JSONObject();
//			获取发票查看链接
    		try {
				dataJson.put("invoiceNumber", "18875941");
//    			dataJson.put("invoiceCode", "3200211130");
    			HttpClient httpClient =new HttpClient();
        		PostMethod post = new PostMethod("http://172.17.4.52:8080/open/ecm-invoice/search");
    			post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
    			post.setRequestHeader("Authorization", tokenId) ;
        		post.setRequestBody(dataJson.toJSONString());
        		httpClient.executeMethod(post);
        		
        		String respStr = post.getResponseBodyAsString();
                post.releaseConnection();
				JSONObject crso = JSONObject.parseObject(respStr);
				JSONObject data = crso.getJSONObject("data");
				com.alibaba.fastjson.JSONArray pageDataArray=data.getJSONArray("pageData");
				
				if(pageDataArray.size()>0){
					JSONObject view = pageDataArray.getJSONObject(0);
					if(view!=null){
						String fileUrl = view.getString("fileUrl");
						if(fileUrl!=null&&fileUrl!=""){
//							给VIEWURL赋值
							String viewUrl = new StringBuffer().append("http://172.17.4.52:8080").append(fileUrl).toString();
							int i = 0 ;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public static String SHA(final String strText) {
			MessageDigest messageDigest;
	        String encodeStr = "";
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
	            messageDigest.update(strText.getBytes("UTF-8"));
	            encodeStr = byte2Hex(messageDigest.digest());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return encodeStr;
		}
		private static String byte2Hex(byte[] bytes) {
	        StringBuffer stringBuffer = new StringBuffer();
	        String temp = null;
	        for (int i = 0; i < bytes.length; i++) {
	            temp = Integer.toHexString(bytes[i] & 0xFF);
	            if (temp.length() == 1) {
	                stringBuffer.append("0");
	            }
	            stringBuffer.append(temp);
	        }
	        return stringBuffer.toString();
	    }

}