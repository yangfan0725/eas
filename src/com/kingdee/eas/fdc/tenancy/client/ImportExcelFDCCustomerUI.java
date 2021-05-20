/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.invite.supplier.CurrencyEnum;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCCustomerHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ImportExcelFDCCustomerUI extends AbstractImportExcelFDCCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(ImportExcelFDCCustomerUI.class);
    
    private Map mapCurrencyEnum = null;
    private Map mapEnterprisePropertyEnum = null;
    private Map mapProvince = null;
    private Map mapCity = null;
    private Map mapLevel = null;
    private Map mapReceptionType = null;
    private Map mapCustomerTypeEnum = null;
    private Map mapSellProject = null;
    private Map mapClassify = null;
    
    boolean existNumberRule = CommerceHelper.isExistNumberRule(new FDCCustomerInfo());
    /**
     * output class constructor
     */
    public ImportExcelFDCCustomerUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	
    	super.onLoad();
		
		this.btnImportExcel.setEnabled(true);
		this.btnSave.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);

		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.menuView.setVisible(false);
		
		this.txtFilePath.setEnabled(false);
		this.txtExcelCount.setEnabled(false);
		this.txtInvaluidCount.setEnabled(false);
		
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getColumn("receptionType.name").setRequired(true);
		this.tblMain.getColumn("level.name").setRequired(true);
		this.tblMain.getColumn("firstDate").setRequired(true);
		comboImportType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�������м�¼У�� 
				for(int i=0;i<tblMain.getRowCount();i++)	{
					logger.info("**���ز��ͻ�����--��֤��"+i+"������...");
					vertifyARowImportDate(i);
					logger.info("**���ز��ͻ�����--��֤��"+i+"���������");
				}
			}
		});
		initColumnEditor();
	}

    public void onShow() throws Exception {
     	super.onShow();
     	
     	int indexNum = this.tblMain.getColumn("importStatus").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
    }

    //����
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
		
		this.checkSelected();
		
		List idValues = getSelectedIdValuesThis();
		if(idValues.size()>0) {
			FDCCustomerFactory.getRemoteInstance().pickUp(idValues);
			CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
			refresh(e);		
		}
	}

	private ArrayList getSelectedIdValuesThis() {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectRows.length;i++) {
			Object id = this.tblMain.getRow(selectRows[i]).getCell("id").getValue();
			if(id!=null && !((String)id).trim().equals(""))
			list.add(id);
		}
		return list;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List idValues = getSelectedIdValuesThis();
		if(idValues.size()==0)
			return;
		
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List idValues = getSelectedIdValuesThis();
		if(idValues.size()==0) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			for(int i=selectRows.length-1;i>-1;i--) {
				this.tblMain.removeRow(selectRows[i]);		
			}	
		}else{
			super.actionRemove_actionPerformed(e);
		}	
		
		setTheTextCount();
	}

	/**
     * output actionImportFromExcel_actionPerformed
     */
    public void actionImportFromExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportFromExcel_actionPerformed(e);

        
        String importType = (String)this.comboImportType.getSelectedItem();
        if(importType==null) {
			MsgBox.showInfo("������ѡ��'��������'��");
			return;
        }
        
        int rowCount = tblMain.getRowCount();
		for(int i=0;i<rowCount;i++) {
		   tblMain.removeRow(0);
		}

		
		
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.txtFilePath.setText(fileName);
		
		logger.info("**���ز��ͻ�����--��excel���뿪ʼ....");
		
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(true);
		int excelNum = CommerceHelper.importExcelToTable(fileName, this.tblMain);  //�����ص��в�д��
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(false);
		
		logger.info("**���ز��ͻ�����--��excel���������������"+excelNum+"�����ݣ�");
		
		this.tblMain.setRowCount(excelNum);
		this.txtExcelCount.setText(String.valueOf(excelNum));
		
	
	    //�������м�¼У�� 
		for(int i=0;i<this.tblMain.getRowCount();i++)	{
			logger.info("**���ز��ͻ�����--��֤��"+i+"������...");
			vertifyARowImportDate(i);
			logger.info("**���ز��ͻ�����--��֤��"+i+"���������");
		}
		
		
		setTheTextCount();
		
		
		this.btnSave.setEnabled(true);
		this.actionCancelCancel.setVisible(false);   //
    }

    
	/**
	 *���ò��ɵ������� 
	 */
	private void setTheTextCount() {
		int invalildCount = 0;
		
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);			
			if(isHasErrorRow(row))
				invalildCount ++;
		}	
		
		this.txtInvaluidCount.setText(String.valueOf(invalildCount));		
	}
    
	
	private boolean isHasErrorRow(IRow row) {
		boolean hasErrorRow = false;
		String importStatus = (String)row.getCell("importStatus").getValue();
		if(importStatus!=null && !importStatus.trim().equals("")) {
			if(importStatus.indexOf("У��ͨ��")<0 && importStatus.indexOf("����ɹ�")<0)
				hasErrorRow = true;			
		}
		return hasErrorRow;
	}
    
    
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);

		int rowIndex = e.getRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String importStatus = (String)row.getCell("importStatus").getValue();
		
		if(importStatus!=null && importStatus.indexOf("����ɹ�")>=0)
			return;
		
		
		vertifyARowImportDate(rowIndex);
		
		setTheTextCount();
	}
	
	
	
    /**
     * output actionSaveImport_actionPerformed
     */
    public void actionSaveImport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSaveImport_actionPerformed(e);
        
		//����ȫ��У��ͨ������ܱ���
		String invalildCount = this.txtInvaluidCount.getText();
		if(invalildCount==null) return;
		if(invalildCount!=null && !invalildCount.equals("0")) {
			MsgBox.showInfo("����"+invalildCount+"��δУ��ͨ��,����ȫ��У��ͨ������ܱ��棡");
			return;
		}
		
		boolean hasChanged = false;
		Map rMap = null;
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);
			String importStatus = (String)row.getCell("importStatus").getValue(); 
			//ֻ���У��ͨ�����н��б��� (�ѱ�����ĳ���)
			if(importStatus!=null && importStatus.indexOf("У��ͨ��")>=0){	
				String importType = (String)this.comboImportType.getSelectedItem();
				FDCCustomerInfo thisInfo = (FDCCustomerInfo)row.getUserObject();
				if(importType.equals("�޸�")) {
					if(thisInfo.getId()!=null) {  //�޸�
						/*SelectorItemCollection selector = new SelectorItemCollection();
						selector.add(new SelectorItemInfo("name"));
						selector.add(new SelectorItemInfo("sex"));
						selector.add(new SelectorItemInfo("saleTrackPhase"));
						selector.add(new SelectorItemInfo("tenancyTrackPhase"));
						selector.add(new SelectorItemInfo("isImportantTrack"));
						selector.add(new SelectorItemInfo("phone"));
						selector.add(new SelectorItemInfo("lastTrackDate"));
						selector.add(new SelectorItemInfo("email"));
						selector.add(new SelectorItemInfo("certificateName"));
						selector.add(new SelectorItemInfo("certificateNumber"));
						selector.add(new SelectorItemInfo("mailAddress"));
						selector.add(new SelectorItemInfo("province"));
						selector.add(new SelectorItemInfo("habitationArea"));
						selector.add(new SelectorItemInfo("customerOrigin"));
						selector.add(new SelectorItemInfo("receptionType"));
						selector.add(new SelectorItemInfo("customerGrade"));
						selector.add(new SelectorItemInfo("workCompany"));
						selector.add(new SelectorItemInfo("employment"));
						selector.add(new SelectorItemInfo("postalcode"));
						selector.add(new SelectorItemInfo("famillyEarning"));
						selector.add(new SelectorItemInfo("customerType"));
						selector.add(new SelectorItemInfo("description"));
						selector.add(new SelectorItemInfo("project"));
						selector.add(new SelectorItemInfo("salesman"));
						selector.add(new SelectorItemInfo("bookedPlace"));
						selector.add(new SelectorItemInfo("tradeTime"));
						selector.add(new SelectorItemInfo("tenTradeTime"));

						selector.add(new SelectorItemInfo("phone2"));
						selector.add(new SelectorItemInfo("officeTel"));
						selector.add(new SelectorItemInfo("tel"));
						selector.add(new SelectorItemInfo("QQ"));						
						selector.add(new SelectorItemInfo("workArea"));						
						selector.add(new SelectorItemInfo("enterpriceProperty"));						
						selector.add(new SelectorItemInfo("enterpriceSize"));						
						selector.add(new SelectorItemInfo("industry"));						
						selector.add(new SelectorItemInfo("enterpriceHomepage"));						
						selector.add(new SelectorItemInfo("cooperateMode"));						
						selector.add(new SelectorItemInfo("businessScope"));						
						selector.add(new SelectorItemInfo("country"));						
						selector.add(new SelectorItemInfo("fax"));						
						selector.add(new SelectorItemInfo("customerManager"));	*/	
		
						
						try{		
							/***
							 * �������޸Ļ���������ֱ�ӵ���save����
							 * save���������Զ�����info�����õ�ֵ��������Ӧ���ֶ�
							 * �ڸ��²�������saveʱ������������ĳһ���ֶ�����Ϊnull����������Ϊnull�Ļ���µ����ݿ���
							 */
							//FDCCustomerFactory.getRemoteInstance().updatePartial(thisInfo,selector);
							rMap = FDCCustomerFactory.getRemoteInstance().verifySave(thisInfo,false);
							if(FDCCustomerHelper.verifyPhoneAndName(rMap, this)){
								abort();
							}
							FDCCustomerFactory.getRemoteInstance().save(thisInfo);
							
							row.getCell("importStatus").setValue("����ɹ�,�޸�����");
						}catch(Exception ee){
							this.setRowStyleLockStatus(row,"����ʧ��,�޸����� \r\n");				
						}
						hasChanged = true;
					}
				} else {   //����
					String number = thisInfo.getNumber();
					thisInfo.setId(null);
					if(existNumberRule) {
						FDCCustomerInfo custInfo = new FDCCustomerInfo();
						if(row.getCell("name")!=null){
							String name = row.getCell("name").getValue().toString();
							custInfo.setName(name);
						}
						CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
						if(cuInfo!=null){
							custInfo.setCU(cuInfo);
						}
						
						number = CommerceHelper.getNumberByRule(custInfo);
						thisInfo.setNumber(number);
					}
				
					
					if(number!=null && !number.trim().equals("")) {					
					  try{
						  rMap = FDCCustomerFactory.getRemoteInstance().verifySave(thisInfo,false);
							if(FDCCustomerHelper.verifyPhoneAndName(rMap, this)){
								abort();
							}
						  FDCCustomerFactory.getRemoteInstance().addnew(thisInfo); //IObjectPK ioPk = 
						  row.getCell("importStatus").setValue("����ɹ�,��������");
					  }catch(Exception ee) {
						  this.setRowStyleLockStatus(row,"����ʧ��,�������� \r\n");
						  break;
					  }
					}else{
						this.setRowStyleLockStatus(row,"����ʧ��,�������� \r\n"+"����Ϊ��");
						break;
					}
					hasChanged = true;
				}
			}
		}
		
        //if(hasChanged)  //�����ݿ����,��Ҫ���»���
        	CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
        
        
    }


	protected String getEditUIName() {
		return CustomerEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}


	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String key = this.getSelectedKeyValue();
		if(key==null) return;		
		
		super.tblMain_tableClicked(e);
	}






	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}


	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		//ֻ��ʾ����״̬���û�����    --��Ϊʲô������ʾ
		viewInfo = (EntityViewInfo)viewInfo.clone();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}


	
	/**
	 * ��ʼ����Ԫ����Ŀؼ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initColumnEditor() throws EASBizException, BOSException{	
		if(!existNumberRule)
			this.tblMain.getColumn("number").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("phone").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("firstDate").setEditor(CommerceHelper.getKDDatePickerEditor()); 
		this.tblMain.getColumn("clrq").setEditor(CommerceHelper.getKDDatePickerEditor()); 
		this.tblMain.getColumn("bb").setEditor(CommerceHelper.getKDComboBoxEditor(CurrencyEnum.getEnumList()));
		
		//new update by renliang at 2011-3-4
//		EntityViewInfo evi = new EntityViewInfo();
//		FilterInfo filterInfo = new FilterInfo();
//		evi.setFilter(filterInfo);
//		KDBizPromptBox certificateName = new KDBizPromptBox();
//		certificateName
//				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CertificateSearchQuery");
//		certificateName.setEntityViewInfo(evi);
//		certificateName.setEditable(false);
//		certificateName.setDisplayFormat("$name$");
//		certificateName.setEditFormat("$number$");
//		certificateName.setCommitFormat("$number$");
//		ICellEditor f7Editor = new KDTDefaultCellEditor(certificateName);
//		this.tblMain.getColumn("certificateName").setEditor(f7Editor);
		//this.tblCustomerInfo.getColumn("certificateName").setRequired(true);
		
		//certificateNumber
		//mailAddress
		this.tblMain.getColumn("province.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.hr.emp.app.ProvinceQuery",null));
		this.tblMain.getColumn("city.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.hr.emp.app.CityQuery",null));
		this.tblMain.getColumn("receptionType.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ReceptionTypeQuery",null));
		
		EntityViewInfo view= new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		view.setFilter(filter);
		
		HashMap ctx=new HashMap();
		ctx.put("EntityViewInfo", view);
		ctx.put("EnableMultiSelection", new Boolean(false));
		ctx.put("HasCUDefaultFilter", new Boolean(true));
	    
	    KDBizPromptBox f7Prompt = new KDBizPromptBox();
		f7Prompt.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeQuery");
		f7Prompt.setEditable(true);
		f7Prompt.setDisplayFormat("$name$");
		f7Prompt.setEditFormat("$name$");
		f7Prompt.setCommitFormat("$number$");
		ICellEditor f7editor = new KDTDefaultCellEditor(f7Prompt);
		this.tblMain.getColumn("classify").setEditor(f7editor);
		
		view=new EntityViewInfo();
		filter = SHEHelper.getDefaultFilterForTree();
		filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE,
							CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("type.id",
						CRMConstants.COMMERCECHANCE_LEVEL_ID,
						CompareType.EQUALS));
		view.setFilter(filter);
		this.tblMain.getColumn("level.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForSHEQuery",view));
		
		this.tblMain.getColumn("businessNature").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.tenancy.app.NatureEnterpriseQuery",null));

		try{
			view=CommerceHelper.getPermitProjectView();
			view.getFilter().getFilterItems().add(new FilterItemInfo("level",1));
			this.tblMain.getColumn("project.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery",view));
			this.tblMain.getColumn("project.name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMain.getColumn("salesman.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.base.permission.app.F7UserQuery",CommerceHelper.getPermitSalemanView(null)));  //���뵼��
			this.tblMain.getColumn("salesman.name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		}catch(Exception e){
			e.printStackTrace();
			this.abort();
		}
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setPrecision(0);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("gyzs").setEditor(formatTextEditor);
		this.tblMain.getColumn("area").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblMain.getColumn("zczj").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
	}	
	
	private void setRowStyleLockStatus(IRow row,String str ) {
		if(str==null || str.trim().equals("")) return;
		
		if(str.indexOf("У��ͨ��")>=0 || str.indexOf("����ɹ�")>=0) {
			row.getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.WHITE);			
			if(str.indexOf("����ɹ�")>=0) 
			  row.getStyleAttributes().setLocked(true);
		}else{			
			row.getStyleAttributes().setLocked(false);
			
			row.getCell("importStatus").getStyleAttributes().setLocked(true);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.RED);
		}
		row.getCell("importStatus").setValue(str);			
	}	
	
	private void vertifyARowImportDate(int rowNum) {
		IRow row = this.tblMain.getRow(rowNum);
		if(row==null)  return;
		
		String importType = (String)this.comboImportType.getSelectedItem();
		
		FDCCustomerInfo customer = (FDCCustomerInfo)row.getUserObject();
		if(customer==null) {
			customer = new FDCCustomerInfo();
		}
		customer.setIsForTen(true);
		customer.setIsEnabled(true);
		customer.setCustomerType(CustomerTypeEnum.EnterpriceCustomer);
		
		//���ѡ������  ��������򲻴��� �����¼��
    	String number  = (String)row.getCell("number").getValue();
    	if(number!=null) customer.setNumber(number.trim());    	
    	
    	if(importType.equals("����")){
    		if(!existNumberRule){
	        	if(number==null || number.trim().equals(""))  {
					setRowStyleLockStatus(row,"���ͻ����롯����¼�룡\r\n");
					return;
	        	}else if(number.length()>80) {
						setRowStyleLockStatus(row,"���ͻ����롯�ַ�����>80��\r\n");
						return;	        			
	        	}
	        	try{
	        		
	        		FDCCustomerFactory.getRemoteInstance().checkNumberDup(customer);
	        	}catch(Exception ex){
	        		//�޸Ĵ�����ʾ�еı���Ϊ�ͻ����� by renliang at 2010-6-26
	        		String errMsg = "�ͻ�����"+number+"�Ѿ����ڣ������ظ�";
	        		setRowStyleLockStatus(row,errMsg+"��\r\n");
	        		return;
	        	}
    		}else{
    			row.getCell("number").setValue(null);
    		}
    	}else if(importType.equals("�޸�")){
    		/**
    		 * �����޸�ģʽ�¿ͻ������У��
    		 * @author liang_ren969
    		 * @date at 2010-6-26
    		 */
    		
    		FDCCustomerCollection custColl = null;			
			try {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				if(customer.getNumber()!=null) {
					filter.getFilterItems().add(new FilterItemInfo("number",customer.getNumber()));
				}
				try{
					UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
					String sqlPerSalmanIdStr = CommerceHelper.getPermitSaleManIdSql(null,currentUserInfo);
					filter.getFilterItems().add(new FilterItemInfo("salesman.id",sqlPerSalmanIdStr,CompareType.INNER));
				} catch (EASBizException e) {
					SysUtil.abort();
				}	
				view.setFilter(filter);
				custColl = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
			} catch (BOSException e) {
				this.handUIException(e);
				this.abort();	
			} 

			if(custColl==null || custColl.size()<=0) {
				setRowStyleLockStatus(row,"���ͻ����롯,�޷�ʶ��\r\n");
				return;
			}
    	}
    	
    	String name  = (String)row.getCell("name").getValue();
    	if(name==null || name.trim().equals(""))  {
    		setRowStyleLockStatus(row,"���ͻ����ơ�����¼�룡\r\n");
			return;   		
    	}else if(name.length()>80) {
			setRowStyleLockStatus(row,"���ͻ����ơ��ַ�����>80��\r\n");
			return;    		
    	}    	
    	if(name!=null) customer.setName(name.trim());
    	
    	//���ѡ������޸�  1ͨ���û��������Ƿ����   ������,����ڶ������ʾ����  
    	if(importType.equals("�޸�")) {    		
			FDCCustomerCollection custColl = null;			
			try {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				filter.getFilterItems().add(new FilterItemInfo("name",name));
				if(customer.getNumber()!=null) filter.getFilterItems().add(new FilterItemInfo("number",customer.getNumber()));
				try{
					UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
					String sqlPerSalmanIdStr = CommerceHelper.getPermitSaleManIdSql(null,currentUserInfo);
					filter.getFilterItems().add(new FilterItemInfo("salesman.id",sqlPerSalmanIdStr,CompareType.INNER));
				} catch (EASBizException e) {
					SysUtil.abort();
				}	
				view.setFilter(filter);
				custColl = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
			} catch (BOSException e) {
				this.handUIException(e);
				this.abort();	
			} 

			if(custColl!=null && custColl.size()>0) {
				if(custColl.size()==1) {
					FDCCustomerInfo custInfo = custColl.get(0);
					customer.setId(custInfo.getId());
					customer.setNumber(custInfo.getNumber());
					customer.setName(custInfo.getName());
				}else{
					setRowStyleLockStatus(row,"���ͻ����ơ�,����"+custColl.size()+"��ͬ����,�޷�ʶ��\r\n");
					return;
				}
			}else{
				setRowStyleLockStatus(row,"���ͻ����ơ�,�޷�ʶ��\r\n");
				return;
			}
    	}
    	Object saleMan = row.getCell("salesman.name").getValue();
    	if(saleMan!=null) {
    		if(saleMan instanceof String) {
    			UserInfo thisInfo = getUserByNameStr((String)saleMan);
    			if(thisInfo!=null){
    				row.getCell("salesman.name").setValue(thisInfo);
    				customer.setSalesman(thisInfo);
    				row.getCell("salesman.name").setValue(thisInfo.getName());
    			}else{
    				setRowStyleLockStatus(row,"��ҵ����ʡ��޷�ʶ��\r\n");
    				return;  
    			}        
    		}else if(saleMan instanceof UserInfo){
    			customer.setSalesman((UserInfo)saleMan);
    		}
    	}else{
			setRowStyleLockStatus(row,"��ҵ����ʡ�����¼�룡\r\n");
			return;      	
    	}
    	Object project = row.getCell("project.name").getValue();
    	if(project!=null) {
    		if(project instanceof String) {
    			SellProjectInfo thisInfo = getSellProjectByNameStr((String)project);
    			if(thisInfo!=null){
    				row.getCell("project.name").setValue(thisInfo);
    				customer.setProject(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"����ʼ�Ǽ���Ŀ���޷�ʶ��\r\n");
    				return;  
    			}        
    		}else if(project instanceof SellProjectInfo){
    			customer.setProject((SellProjectInfo)project);
    		}
    	}else{
			setRowStyleLockStatus(row,"����ʼ�Ǽ���Ŀ������¼�룡\r\n");
			return;      		
    	}
    	Object classify = row.getCell("classify").getValue();
    	if(classify!=null) {
    		if(classify instanceof String) {
    			ChannelTypeInfo thisInfo = getClassifyByNameStr((String)classify);
    			if(thisInfo!=null){
    				row.getCell("project.name").setValue(thisInfo);
    				customer.setClassify(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"��ý���������޷�ʶ��\r\n");
    				return;  
    			}        
    		}else if(classify instanceof ChannelTypeInfo){
    			customer.setClassify((ChannelTypeInfo)classify);
    		}
    	}
    	Object province = row.getCell("province.name").getValue();
    	if(province!=null){
    		if(province instanceof String) {
    			ProvinceInfo thisInfo = getProvinceByNameStr((String)province);
    			if(thisInfo!=null){
    				row.getCell("province.name").setValue(thisInfo);
    				customer.setProvince(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"������ʡ�ݡ��޷�ʶ��\r\n");
    				return;
    			}        			
    		}else if(province instanceof ProvinceInfo) {
    			customer.setProvince((ProvinceInfo)province);
    		}        		
    	}
    	
    	Object city = row.getCell("city.name").getValue();
    	if(city!=null){
    		if(city instanceof String) {
    			CityInfo thisInfo = getCityByNameStr((String)city);
    			if(thisInfo!=null){
    				row.getCell("city.name").setValue(thisInfo);
    				customer.setCity(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"�����ڳ��С��޷�ʶ��\r\n");
    				return;
    			}        			
    		}else if(city instanceof CityInfo) {
    			customer.setCity((CityInfo)city);
    		}        		
    	}
    	Object level = row.getCell("level.name").getValue();
    	if(level!=null){
    		if(level instanceof String) {
    			CommerceChanceAssistantInfo thisInfo = getLevelNameStr((String)level);
    			if(thisInfo!=null){
    				row.getCell("level.name").setValue(thisInfo);
    				customer.setLevel(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���̻������޷�ʶ��\r\n");
    				return;
    			}        			
    		}else if(level instanceof CommerceChanceAssistantInfo) {
    			customer.setLevel((CommerceChanceAssistantInfo)level);
    		}        		
    	}else{
			setRowStyleLockStatus(row,"���̻����𡯱���¼�룡\r\n");
			return;      		
    	}
    	Object receptionType = row.getCell("receptionType.name").getValue();
    	if(receptionType!=null) {
    		if(receptionType instanceof String) {
    			ReceptionTypeInfo thisInfo = getReceptionTypeByNameStr((String)receptionType);
    			if(thisInfo!=null){
    				row.getCell("receptionType.name").setValue(thisInfo);
    				customer.setReceptionType(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���״νӴ���ʽ���޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(receptionType instanceof ReceptionTypeInfo){
    			customer.setReceptionType((ReceptionTypeInfo)receptionType);
    		}
    	}else{
    		setRowStyleLockStatus(row,"���״νӴ���ʽ������Ϊ�գ�\r\n");
			return;
    	}
    	Object firstDate  = row.getCell("firstDate").getValue();
    	if(firstDate!=null && firstDate instanceof String){
    		if(CommerceHelper.isDateFormat(firstDate.toString(),"yyyy-MM-dd")) {
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date thisDate = formatter.parse(firstDate.toString());
					customer.setFirstDate(thisDate);
        			row.getCell("firstDate").setValue(thisDate);
				} catch (ParseException e) {
					e.printStackTrace();					
				}        			
    		}else {
    			setRowStyleLockStatus(row,"���״ν�Ǣʱ�䡯�޷�ʶ����ȷ��ʽΪ'yyyy-MM-dd'\r\n");
    			return;
    		}
    	}else if(firstDate!=null&&firstDate instanceof Date){
    		customer.setFirstDate((Date)firstDate);
    	}else {
    		setRowStyleLockStatus(row,"���״ν�Ǣʱ�䡯����Ϊ�գ�\r\n");
			return;
    	}  
    	String phone  = (String)row.getCell("phone").getValue();
    	if(phone==null)  {
			setRowStyleLockStatus(row,"����˾�绰������¼�룡\r\n");
			return;		
    	}else{
    		if(phone.length()>80) {
    			setRowStyleLockStatus(row,"����˾�绰���ַ�>80��\r\n");
    			return;    			
    		}else if(phone.length()<5) {
    			setRowStyleLockStatus(row,"����˾�绰���ַ�<5��\r\n");
    			return;    			
    		}
    		customer.setPhone(phone.toString());
    	}
    	String fax  = (String)row.getCell("fax").getValue();
		if(fax!=null&&fax.length()>80) {
			setRowStyleLockStatus(row,"����˾���桯�ַ�>80��\r\n");
			return;    			
		}
		customer.setFax(fax);
		
    	String email = (String)row.getCell("email").getValue();
    	if(email!=null && email.length()>70){
			setRowStyleLockStatus(row,"���������䡯�ַ�>80��\r\n");
			return;		
    	}
    	customer.setEmail(email);    
    	
    	String postalcode = (String)row.getCell("postalcode").getValue();
    	if(postalcode!=null && postalcode.length()>70){
			setRowStyleLockStatus(row,"���������롯���ȹ�����\r\n");
			return;  
    	} 
    	customer.setPostalcode(postalcode);
    	
    	String address = (String)row.getCell("address").getValue();
    	if(address!=null && address.length()>250){
			setRowStyleLockStatus(row,"��ע���ַ�����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setAddress(address);
    
    	String mailAddress = (String)row.getCell("mailAddress").getValue();
    	if(mailAddress!=null && mailAddress.length()>250){
			setRowStyleLockStatus(row,"��ͨ�ŵ�ַ(��Ӫ)�����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setMailAddress(mailAddress);
    	
    	Object businessNature = row.getCell("businessNature").getValue();
    	if(businessNature!=null) {
    		if(businessNature instanceof String) {
    			NatureEnterpriseInfo natureEnterpriseName;
				try {
					natureEnterpriseName = getEnterpriceProperty((String)businessNature);
				
    			if(natureEnterpriseName!=null){
    				row.getCell("businessNature").setValue(natureEnterpriseName);
    				customer.setBusinessNature(natureEnterpriseName);
    			}else{
    				setRowStyleLockStatus(row,"����ҵ���ʡ��޷�ʶ��\r\n");
    				return;
    			}
				} catch (BOSException e) {
					e.printStackTrace();
				}
    		}else if(businessNature instanceof NatureEnterpriseInfo){
    			customer.setBusinessNature((NatureEnterpriseInfo)businessNature);
    		}        		
    	}
    	
    	String zz = (String)row.getCell("zz").getValue();
    	if(zz!=null && zz.length()>200){
			setRowStyleLockStatus(row,"����ҵ���ʡ����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setZz(zz);
    	
    	try {
    		if(row.getCell("area").getValue()!=null){
    			BigDecimal area = new BigDecimal(row.getCell("area").getValue().toString());
            	customer.setArea(area);
    		}
		} catch (Exception e) {
			setRowStyleLockStatus(row,"������Ӫҵ����������޷�ʶ��\r\n");	
			return;
		} 
    	
    	String zb = (String)row.getCell("zb").getValue();
    	if(zb!=null && zb.length()>200){
			setRowStyleLockStatus(row,"�������ڵĲ���ָ�ꡯ���ȹ�����\r\n");
			return;	  
    	}        
    	customer.setZb(zb);
    	
    	String description = (String)row.getCell("description").getValue();
    	if(description!=null && description.length()>255){
			setRowStyleLockStatus(row,"����˾��顯���ȹ�����\r\n");
			return;	  
    	}        
    	customer.setDescription(description);
    	
    	String fr = (String)row.getCell("fr").getValue();
    	if(fr!=null && fr.length()>80){
			setRowStyleLockStatus(row,"�����˴������ȹ�����\r\n");
			return;	  
    	}        
    	customer.setFr(fr);
    	
    	Object clrq  = row.getCell("clrq").getValue();
    	if(clrq!=null && clrq instanceof String){
    		if(CommerceHelper.isDateFormat(clrq.toString(),"yyyy-MM-dd")) {
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date thisDate = formatter.parse(clrq.toString());
					customer.setClrq(thisDate);
        			row.getCell("clrq").setValue(clrq);
				} catch (ParseException e) {
					e.printStackTrace();					
				}        			
    		}else{
    			setRowStyleLockStatus(row,"���������ڡ��޷�ʶ����ȷ��ʽΪ'yyyy-MM-dd'\r\n");
    			return;		
    		}
    	}else if(clrq!=null && clrq instanceof Date){
			customer.setClrq((Date)clrq);
		}
    	try {
    		if(row.getCell("zczj").getValue()!=null){
    			BigDecimal zczj = new BigDecimal(row.getCell("zczj").getValue().toString());
            	customer.setZczj(zczj);
    		}
		} catch (Exception e) {
			setRowStyleLockStatus(row,"��ע���ʽ�(��Ԫ)���޷�ʶ��\r\n");		
			return;	
		} 
		
    	Object bb = row.getCell("bb").getValue();
    	if(bb!=null) {
    		if(bb instanceof String) {
    			CurrencyEnum currency = getCurrencyEnum((String)bb);
    			if(currency!=null) {
    				row.getCell("bb").setValue(currency);
    				customer.setBb(currency);
    			}else{
    				setRowStyleLockStatus(row,"���ұ��޷�ʶ��\r\n");
    				return;
    			}
    		}else if(bb instanceof CurrencyEnum) {
    			customer.setBb((CurrencyEnum)bb);
    		}
    	}     
    	
    	try {
    		if(row.getCell("gyzs").getValue()!=null){
    			Integer gyzs = Integer.valueOf(row.getCell("gyzs").getValue().toString());
            	if(gyzs!=null)customer.setGyzs(gyzs);
    		}
		} catch (Exception e) {
			setRowStyleLockStatus(row,"����Ա�������޷�ʶ��\r\n");	
			return;	
		} 
    	
    	String yyzz = (String)row.getCell("yyzz").getValue();
    	if(yyzz!=null && yyzz.length()>80){
			setRowStyleLockStatus(row,"��Ӫҵִ�ա����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setYyzz(yyzz);
    	
    	String taxNo = (String)row.getCell("taxNo").getValue();
    	if(taxNo!=null && taxNo.length()>80){
			setRowStyleLockStatus(row,"��˰��ǼǺš����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setTaxNO(taxNo);
    	
    	String gszch = (String)row.getCell("gszch").getValue();
    	if(gszch!=null && gszch.length()>80){
			setRowStyleLockStatus(row,"������ע��š����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setGsNo(gszch);
    	

    	String remark = (String)row.getCell("remark").getValue();
    	if(remark!=null && remark.length()>500){
			setRowStyleLockStatus(row,"����ע�����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setRemark(remark);
    	
    	row.setUserObject(customer);
    	setRowStyleLockStatus(row,"У��ͨ��");		
	}
	private NatureEnterpriseInfo getEnterpriceProperty(String natureEnterpriseName) throws BOSException{
		if(natureEnterpriseName==null || natureEnterpriseName.trim().equals("")) return null;
		
		if(mapEnterprisePropertyEnum==null) 
			mapEnterprisePropertyEnum = CommerceHelper.getNatureEnterpriseMap();    	
		return (NatureEnterpriseInfo)mapEnterprisePropertyEnum.get(natureEnterpriseName.trim());
	}
	private ProvinceInfo getProvinceByNameStr(String province){
		if(province==null || province.trim().equals("")) return null;
		
		if(mapProvince==null)  {
			try{
				mapProvince = CommerceHelper.getProvinceMap();
			}catch(BOSException e){this.abort();}
		}
    	return (ProvinceInfo)mapProvince.get(province.trim());
	}
	private CommerceChanceAssistantInfo getLevelNameStr(String level){
		if(level==null || level.trim().equals("")) return null;
		
		if(mapLevel==null)  {
			try{
				mapLevel = CommerceHelper.getLevelMap();
			}catch(BOSException e){this.abort();}
		}
    	return (CommerceChanceAssistantInfo)mapLevel.get(level.trim());
	}
	private CityInfo getCityByNameStr(String city){
		if(city==null || city.trim().equals("")) return null;
		
		if(mapCity==null)  {
			try{
				mapCity = CommerceHelper.getCityMap();
			}catch(BOSException e){this.abort();}
		}
    	return (CityInfo)mapCity.get(city.trim());
	}
	private ReceptionTypeInfo getReceptionTypeByNameStr(String receptionType){
		if(receptionType==null || receptionType.trim().equals("")) return null;
		
		if(mapReceptionType==null) {
			try{
				mapReceptionType = CommerceHelper.getReceptionTypeMap();
			}catch(BOSException e){this.abort();}
		}
    	return (ReceptionTypeInfo)mapReceptionType.get(receptionType.trim());
	}
	private CurrencyEnum getCurrencyEnum(String currency){
		if(currency==null || currency.trim().equals("")) return null;
		
		if(mapCurrencyEnum==null) 
			mapCurrencyEnum = CommerceHelper.getCurrencyMap();    	
    	return (CurrencyEnum)mapCurrencyEnum.get(currency.trim());
	}
	
	private SellProjectInfo getSellProjectByNameStr(String project){
		if(project==null || project.trim().equals("")) return null;
		
		if(mapSellProject==null) { 
			try{
				mapSellProject = CommerceHelper.getSellProjectMap();
			}catch(BOSException e){this.abort();}
		}
    	return (SellProjectInfo)mapSellProject.get(project.trim());
	}
	private ChannelTypeInfo getClassifyByNameStr(String classify){
		if(classify==null || classify.trim().equals("")) return null;
		
		if(mapClassify==null) { 
			try{
				mapClassify = CommerceHelper.getClassifyMap();
			}catch(BOSException e){this.abort();}
		}
    	return (ChannelTypeInfo)mapClassify.get(classify.trim());
	}
	/**
	 * 
	 * <p>@author tim_gao<p>
	 * <p>�����ֶεõ��û�<p>
	 * <p>2010-9-27<p>
	 */
	private UserInfo getUserByNameStr(String saleMan){
		if(saleMan==null || saleMan.trim().equals("")) return null;
		UserInfo getUser=null;
			try {
				 getUser=CommerceHelper.getUserMapByNumber(null,saleMan);
			} catch (BOSException e1) {
				this.abort();
			}
		if(getUser!=null){
			return getUser;
		}
		else{
			return null;
		}
	}
	
}