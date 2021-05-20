/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.util.FileUtil;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.IInvoiceBill;
import com.kingdee.eas.fdc.tenancy.InvoiceBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.client.InvoiceBillListUI.InvoiceNumberInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.framework.client.FindListEvent;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IFindListListener;
import com.kingdee.eas.framework.client.ListFind;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvoiceBillFullListUI extends AbstractInvoiceBillFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvoiceBillFullListUI.class);
    public InvoiceBillFullListUI() throws Exception
    {
        super();
    }
    protected boolean isNeedfetchInitData()throws Exception{
    	return false;
    }
    public void onLoad() throws Exception {

		super.onLoad();
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.actionAdjust.setVisible(false);
		this.treeMain.setSelectionRow(0);
		
		this.actionQuery.setVisible(true);
		
		this.actionBatchReceiving.setVisible(false);
		this.actionReceive.setVisible(false);
		this.actionCanceReceive.setVisible(false);
		this.actionReceipt.setVisible(false);
		this.actionRetakeReceipt.setVisible(false);
		this.actionCreateInvoice.setVisible(false);
		this.actionClearInvoice.setVisible(false);
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"entry.amount","noTaxAmount"});
		
		this.menuItemUpdateSubject.setVisible(false);
		this.btnAdjust.setText("作废");
		this.btnAdjust.setIcon(EASResource.getIcon("imgTbtn_blankout"));
		this.btnAdjust.setVisible(true);
		this.actionTraceDown.setVisible(true);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
	}
    protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"appAmount","actRevAmount","entry.amount","noTaxAmount"});
	}

	protected void setColGroups() {
		setColGroup("type");
		setColGroup("number");
		setColGroup("amount");
		setColGroup("fiVouchered");
		setColGroup("state");
		setColGroup("description");
		setColGroup("creator.name");
		setColGroup("createTime");
		setColGroup("auditor.name");
		setColGroup("auditTime");
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
		if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("tenancyBill.sellProject.id", pro.getId().toString()));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		try {
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	// 租赁系统收款可以针对同一项目不同楼栋多房间,所以这里可能需要构建项目树
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void setUITitle(String title) {
		super.setUITitle("票据管理序时簿");
	}
	protected String getEditUIName() {
		return InvoiceBillEditUI.class.getName();
	}
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	public ArrayList getSelectedValues(String fieldName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		for (int i = 0; i < selectRows.length; i++) {
			if (selectRows[i] == -1)
				selectRows[i] = 0;
			ICell cell = getBillListTable().getRow(selectRows[i]).getCell(
					fieldName);
			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			if (cell.getValue() != null) {
				String id = cell.getValue().toString();
				if (!list.contains(id))
					list.add(id);
			}

		}
		return list;
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
			if (node.getUserObject() instanceof SellProjectInfo) {
				this.actionAddNew.setEnabled(true);
				this.actionView.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	 public InvoiceBillInfo getInfo(String id) throws EASBizException, BOSException, Exception{
	    	if(id==null) return null;
	    	SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("state");
	    	sels.add("fiVouchered");
	    	return ((InvoiceBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels));
	    }
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		InvoiceBillInfo info = getInfo(id);
		
		if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())&&!FDCBillStateEnum.SAVED.equals(info.getState())) {
			if(warning.equals("cantEdit")){
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
		
		checkBeforeEditOrRemove("cantEdit",id);
		
		IUIWindow uiWindow = showEditUI(e);
		uiWindow.show();
		if(isDoRefresh(uiWindow))
		                {
			if(UtilRequest.isPrepare("ActionRefresh", this))
				prepareRefresh(null).callHandler();
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}
	private IUIWindow showEditUI(ActionEvent e)throws Exception{
		checkSelected();
		checkObjectExists();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", getSelectedKeyValue());
		prepareUIContext(uiContext, e);
		IUIWindow uiWindow = null;
		if(SwingUtilities.getWindowAncestor(this) != null && (SwingUtilities.getWindowAncestor(this) instanceof JDialog))
			uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(getEditUIName(), uiContext, null, OprtState.EDIT);
		else
			uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null, OprtState.EDIT);
		return uiWindow;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			checkBeforeEditOrRemove("cantRemove",id.get(i).toString());
		}
		if(confirmRemove()){
			for(int i = 0; i < id.size(); i++){
				((IInvoiceBill)getBizInterface()).delete(new ObjectUuidPK(id.get(i).toString()));
			}
			FDCClientUtils.showOprtOK(this);
			this.refresh(null);
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvoiceBillFactory.getRemoteInstance();
    }
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getInfo(id.get(i).toString()).getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IInvoiceBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
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
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getInfo(id.get(i).toString()).getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IInvoiceBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getInfo(id.get(i).toString()).getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行生成凭证操作！");
				return;
			}
		}
		super.actionVoucher_actionPerformed(e);
	}
	
	private boolean hasQyeryPK = false;
    boolean isFirstFind = true;
    private int searcheRowCount=0;
    private  String searchText=null;
    private  boolean isMatch=false;
    private  FindListEvent preFindListEvent=null;
    private  String  propertyName=null;
    //定位框
    private FindDialog findDialog = null;
    private static String locateFirst = "Msg_LocateFirst";
    private static String locateLast = "Msg_LocateLast";
    //添加等待提示框
    KDDialog kddialog=null;
    protected String[] keyFieldNames;
    //分录id
    protected String   subKeyFieldName;
    //初始化辅助类
    ListUiHelper listUiHelper=null;
    protected String[] getLocateNames()
    {
    	String[] locateNames = new String[3];
        locateNames[0] = "tenancyBill.tenancyName";
        locateNames[1] = "tenancyBill.tenRoomsDes";
        locateNames[2] = "tenancyBill.tenCustomerDes";
        return locateNames;
    }
/**
* 找不到匹配记录时给予提示
*/
private void showMsgNoIdList(IRow row,FindListEvent e)
{
if (row == null)
{
	String hint="";
	if (e.getFindDeration() == FindListEvent.Down_Find)
	{
		if (searcheRowCount==0)
		{
		  hint=locateLast;
		}else
		{
		  hint="Msg_LocateFirst_end";
		}
	}else
	{
		if (searcheRowCount==0)
		{
			hint=locateFirst;
		}else
		{
			hint="Msg_LocateFirst_end";
		}
	}
	String msg=EASResource.getString(FrameWorkClientUtils.strResource + hint);
	if (searcheRowCount!=0)
	{
		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
		msg = MessageFormat.format(msg, objs);
	}
    MsgBox.showInfo(this, msg);
    this.findDialog.show();
}
}


//是否使用虚模式预取,在定位的时候不需要预取
private boolean bPreFetch = true;

public boolean isHasQyeryPK()
{
    return hasQyeryPK;
}
public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
	
	checkSelected();
	ArrayList id = getSelectedIdValues();
	for(int i = 0; i < id.size(); i++){
		FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
    	
		if (!FDCBillStateEnum.AUDITTED.equals(getInfo(id.get(i).toString()).getState())) {
			FDCMsgBox.showWarning("单据不是审批状态，不能进行作废操作！");
			return;
		}
		TenBillBaseInfo tenBillBaseInfo = new TenBillBaseInfo();

		tenBillBaseInfo.setId(BOSUuid.read(id.get(i).toString()));
		tenBillBaseInfo.setState(FDCBillStateEnum.INVALID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		
		InvoiceBillFactory.getRemoteInstance().updatePartial(tenBillBaseInfo, selector);
		
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("entry.*");
		InvoiceBillInfo info=InvoiceBillFactory.getRemoteInstance().getInvoiceBillInfo(new ObjectUuidPK(id.get(i).toString()),sic);
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder();
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		for(int kk=0;kk<info.getEntry().size();kk++){
			InvoiceBillEntryInfo entry=info.getEntry().get(kk);
			String table="";
			if(RevListTypeEnum.tenRoomRev.equals(entry.getRevListType())){
				table=" T_TEN_TenancyRoomPayListEntry ";
			}else if(RevListTypeEnum.tenOtherRev.equals(entry.getRevListType())){
				table=" T_TEN_TenBillOtherPay ";
			}else if(RevListTypeEnum.sincerobligate.equals(entry.getRevListType())){
				table=" .T_TEN_SincerPaylistEntrys ";
			}
			StringBuffer sql = new StringBuffer();
			sql.append("update "+table+" revList set finvoiceAmount = (select isnull(sum(entry.famount),0) from T_TEN_InvoiceBillEntry entry left join T_TEN_InvoiceBill bill on bill.fid=entry.fheadId where bill.fstate='4AUDITTED' and entry.frevListId=revList.fid) where revList.fid='"+entry.getRevListId()+"'");
			fdcSB.addBatch(sql.toString());
		}
		fdcSB.executeBatch();
	}
	FDCClientUtils.showOprtOK(this);
	this.refresh(null);
}

@Override
public void actionImportInoviceInfo_actionPerformed(ActionEvent e)
		throws Exception {
	// TODO Auto-generated method stub
	super.actionImportInoviceInfo_actionPerformed(e);
	//获取文件，解析excel文档
	KDFileChooser ch = new KDFileChooser();
	
	
	ch.setFileFilter(new FileFilter(){

		@Override
		public boolean accept(File file) {
			// TODO Auto-generated method stub
			  if(file.isDirectory())
	                return true;
	            String extension = FileUtil.getExtension(file);
	            if(extension != null)
	                return  extension.equalsIgnoreCase("txt");
	            else
	                return false;
			
			
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			 return "text file ( *.txt )";
		}});
	ch.showOpenDialog(this);
	File f = ch.getSelectedFile();
	
	
	BufferedReader r = new BufferedReader(new FileReader(f));
	String rl = null;
	int currRow = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	InvoiceNumberInfo info = null;
	List<InvoiceNumberInfo> resultList = new ArrayList<InvoiceNumberInfo>();
	while((rl = r.readLine()) != null){
		currRow ++;
		if(currRow<3){
			continue;
		}
		if(rl.startsWith("//")){
			continue;
		}
		if(rl.startsWith("0~~0~~")){
			String[] re = rl.split("~~");
			info = new InvoiceNumberInfo();
			info.setSaleNumber(re[8]);
			info.setInvoiceDate(sdf.parse(re[6]));
			info.setInvoiceNumber(re[4]);
			resultList.add(info);
		}
		
	}
	
//	XSSFWorkbook wb = new XSSFWorkbook(is);
//	XSSFSheet s  = wb.getSheetAt(0);
//	int rowNumber = s.getLastRowNum();
//	for(int i=1;i<rowNumber;i++){
//		XSSFRow row = s.getRow(i);
//		info = new InvoiceNumberInfo();
//		String saleNumber = row.getCell(0).getStringCellValue();
//		info.setSaleNumber(saleNumber);
//		String invoiceNumber = row.getCell(1).getStringCellValue();
//		info.setInvoiceNumber(invoiceNumber);
//		
//		String invoiceDate = row.getCell(2).getStringCellValue();
//		if(!StringUtils.isEmpty(invoiceDate)){
//			info.setInvoiceDate(sdf.parse(invoiceDate));
//		}
//		resultList.add(info);
//	}
	
	
	//生成语句更新
	sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	FDCSQLBuilder builder = new FDCSQLBuilder();
	builder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
	int size = resultList.size();
	for(int i=0;i<size;i++){
		info = resultList.get(i);
		String sql = "update t_ten_invoicebill set fnumber = '"+info.getInvoiceNumber()+"',fbizdate={ts'"+sdf.format(info.getInvoiceDate())+"'} where fsalenumber='"+info.getSaleNumber()+"'";
		builder.addBatch(sql);
	}
	builder.executeBatch();
	FDCMsgBox.showInfo("开票信息更新成功.");
	this.refresh(null);
	
	
	
}

class InvoiceNumberInfo{
	private String saleNumber;
	private String invoiceNumber;
	private Date invoiceDate;
	public String getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
}
}