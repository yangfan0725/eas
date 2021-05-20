/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.WorkAreaInfo;
import com.kingdee.eas.fdc.tenancy.BusinessScopeInfo;
import com.kingdee.eas.fdc.tenancy.CooperateModeInfo;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo;
import com.kingdee.eas.framework.BizDataFormat;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ImportExcelFDCCustomerUI extends AbstractImportExcelFDCCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(ImportExcelFDCCustomerUI.class);
    
    private Map mapSexEnum = null;
    private Map mapTrackPhaseEnum = null;
    private Map mapEnterprisePropertyEnum = null;
    private Map mapCertifacateNameEnum = null;
    private Map mapProvince = null;
    private Map mapHabitationArea = null;
    private Map mapWorkArea = null;
    private Map mapIndustry = null;
    private Map mapCooperateMode = null;
    private Map mapBusinessScope = null;
    private Map mapCustomerManager = null;
    private Map mapCountry = null;
    private Map mapCustomerOrigin = null;
    private Map mapReceptionType = null;
    private Map mapCustomerGrade = null;
    private Map mapFamillyEarning = null;
    private Map mapCustomerTypeEnum = null;
    private Map mapSellProject = null;
    private Map mapUser = null;
    
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
		this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	//	this.tblMain.getColumn("officeTel").setRequired(true);
	//	this.tblMain.getColumn("QQ").setRequired(true);
		this.tblMain.getColumn("receptionType.name").setRequired(true);
		
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
					  }
					}else{
						this.setRowStyleLockStatus(row,"����ʧ��,�������� \r\n"+"����Ϊ��");	
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
		//id            ����Ҫ����
		//number
		if(!existNumberRule)
			this.tblMain.getColumn("number").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		//name		      ���뵼��
		this.tblMain.getColumn("name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("sex").setEditor(CommerceHelper.getKDComboBoxEditor(SexEnum.getEnumList())); 
		this.tblMain.getColumn("lastTrackDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblMain.getColumn("saleTrackPhase").setEditor(CommerceHelper.getKDComboBoxEditor(CommerceStatusEnum.getEnumList()));
		this.tblMain.getColumn("tenancyTrackPhase").setEditor(CommerceHelper.getKDComboBoxEditor(CommerceStatusEnum.getEnumList()));
		//isImportantTrack
		//phone          ���뵼��
		this.tblMain.getColumn("phone").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("lastTrackDate").setEditor(CommerceHelper.getKDDatePickerEditor());  
		//email
		//this.tblMain.getColumn("certificateName").setEditor(CommerceHelper.getKDComboBoxEditor(CertifacateNameEnum.getEnumList()));
		
		//new update by renliang at 2011-3-4
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		evi.setFilter(filterInfo);
		KDBizPromptBox certificateName = new KDBizPromptBox();
		certificateName
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CertificateSearchQuery");
		certificateName.setEntityViewInfo(evi);
		certificateName.setEditable(false);
		certificateName.setDisplayFormat("$name$");
		certificateName.setEditFormat("$number$");
		certificateName.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(certificateName);
		this.tblMain.getColumn("certificateName").setEditor(f7Editor);
		//this.tblCustomerInfo.getColumn("certificateName").setRequired(true);
		
		//certificateNumber
		//mailAddress
		this.tblMain.getColumn("province.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.hr.emp.app.ProvinceQuery",null));
		this.tblMain.getColumn("habitationArea.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HabitationAreaQuery",null));
		this.tblMain.getColumn("customerOrigin.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CustomerOriginQuery",null));
		this.tblMain.getColumn("receptionType.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ReceptionTypeQuery",null));
		this.tblMain.getColumn("customerGrade.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CustomerGradeQuery",null));
		
		//workCompany
		//employment
		//postalcode
		this.tblMain.getColumn("famillyEarning.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.FamillyEarningQuery",null));
		this.tblMain.getColumn("customerType").setEditor(CommerceHelper.getKDComboBoxEditor(CustomerTypeEnum.getEnumList()));
		this.tblMain.getColumn("customerType").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);

		//isEnabled		  ����Ҫ����
		///this.tblMain.getColumn("isEnabled").getStyleAttributes().setLocked(true);
		//description		
		try{
			this.tblMain.getColumn("project.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery",CommerceHelper.getPermitProjectView()));
			this.tblMain.getColumn("project.name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMain.getColumn("salesman.name").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.base.permission.app.F7UserQuery",CommerceHelper.getPermitSalemanView(null)));  //���뵼��
			this.tblMain.getColumn("salesman.name").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		}catch(Exception e){
			e.printStackTrace();
			this.abort();
		}
		//bookedPlace
		//isForShe
		//isForTen
		//isForPpm
		//phone2
		//officeTel
		//tel
		//QQ
		//enterpriceSize
		//enterpriceHomepage
		//fax
		//		com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor
		//workArea
		/**
		 * ����workare��f7�����������ѡ��Ľ��
		 */
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.WorkAreaQuery");
		f7.setEditable(true);
		f7.setDisplayFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setCommitFormat("$number$");
		f7.setSelector(new KDCommonPromptDialog(){
				public Object getData()
				{	
					//by tim_gao 2010-09-29
					Object[] data = (Object[]) super.getData();
					StringBuffer scol = new StringBuffer();
	 				for(int i = 0; i < data.length; ++i){
	 					scol.append(data[i]);
	 					if(i!=data.length-1){
	 						scol.append(",");
	 					}
	 				}
	 				return scol;
				}
			});
		
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(f7);
		this.tblMain.getColumn("workArea").setEditor(editor);
		WorkAreaCellRenderImpl avr = new WorkAreaCellRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		this.tblMain.getColumn("workArea").setRenderer(avr);
		
		//this.tblMain.getColumn("workArea").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.WorkAreaQuery",null));
		//enterpriceProperty
//		this.tblMain.getColumn("enterpriceProperty").setEditor(CommerceHelper.getKDComboBoxEditor(EnterprisePropertyEnum.getEnumList()));
		this.tblMain.getColumn("businessNature").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.tenancy.app.NatureEnterpriseQuery",null));

		//industry
		this.tblMain.getColumn("industry").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.IndustryQuery",null));
		//cooperateMode
		this.tblMain.getColumn("cooperateMode").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.tenancy.app.CooperateModeQuery",null));
		//businessScope
		/**
		 * ����businessScope��f7�����������ѡ��Ľ��
		 */
		KDBizPromptBox f7bus = new KDBizPromptBox();
		f7bus.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.BusinessScopeQuery");
		f7bus.setEditable(true);
		f7bus.setDisplayFormat("$name$");
		f7bus.setEditFormat("$name$");
		f7bus.setCommitFormat("$number$");
		f7bus.setSelector(new KDCommonPromptDialog(){
				public Object getData()
				{	
					//by tim_gao 2010-09-29
					Object[] data = (Object[]) super.getData();
					StringBuffer scol = new StringBuffer();
	 				for(int i = 0; i < data.length; ++i){
	 					scol.append(data[i]);
	 					if(i!=data.length-1){
	 						scol.append(",");
	 					}
	 				}
	 				return scol;
				}
			});
		KDTDefaultCellEditor editorbus = new KDTDefaultCellEditor(f7bus);
		this.tblMain.getColumn("businessScope").setEditor(editorbus);
		BusinessScopeCellRenderImpl avrbus = new BusinessScopeCellRenderImpl();
		avrbus.getText(new BizDataFormat("$name$"));
		this.tblMain.getColumn("businessScope").setRenderer(avrbus);
	
		
		//this.tblMain.getColumn("businessScope").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.tenancy.app.BusinessScopeQuery",null));
		//country
		this.tblMain.getColumn("country").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.hr.base.app.CountryQuery",null));
		//customerManager
		KDBizPromptBox f7CustomManager = new KDBizPromptBox();
		f7CustomManager.setDisplayFormat("$name$");
		f7CustomManager.setEditFormat("$name$");
		f7CustomManager.setCommitFormat("$name$");
		f7CustomManager.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		f7CustomManager.setEntityViewInfo(CommerceHelper.getPermitSalemanView(null));
		KDTDefaultCellEditor customerEditor = new KDTDefaultCellEditor(f7CustomManager);
		this.tblMain.getColumn("customerManager").setEditor(customerEditor);
		//����F7��ѡ
		setSelectMode("workArea");
		setSelectMode("businessScope");
	}	
	
	private void setSelectMode(String colName){
		ICellEditor cellEditor  = (ICellEditor)this.tblMain.getColumn(colName).getEditor();
		KDBizPromptBox workArea = (KDBizPromptBox)cellEditor.getComponent();
		workArea.setEnabledMultiSelection(true);
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
		
    	Object isImport = row.getCell("isImportantTrack").getValue();			//�ص����
    	if(isImport!=null) {
    		if(isImport instanceof String) {
	    		if(isImport.toString().trim().equals("��")){
	    			customer.setIsImportantTrack(true);
	    			row.getCell("isImportantTrack").setValue(Boolean.TRUE);
	    		}else if(isImport.toString().trim().equals("��")){
	    			customer.setIsImportantTrack(false);
	    			row.getCell("isImportantTrack").setValue(Boolean.FALSE);
	    		}else{
					setRowStyleLockStatus(row,"���ص�������޷�ʶ��\r\n");
					return;
	    		}        
    		}else if(isImport instanceof Boolean){
    			customer.setIsImportantTrack(((Boolean)isImport).booleanValue());
    		}
    	}
    	row.getCell("isImportantTrack").setValue(new Boolean(customer.isIsImportantTrack()));

		Object isForShe = row.getCell("isForShe").getValue();			//��¥����
    	if(isForShe!=null) {
    		if(isForShe instanceof String) {
	    		if(isForShe.toString().trim().equals("��")){
	    			customer.setIsForSHE(true);
	    			row.getCell("isForShe").setValue(Boolean.TRUE);
	    		}else if(isForShe.toString().trim().equals("��")){
	    			customer.setIsForSHE(false);
	    			row.getCell("isForShe").setValue(Boolean.FALSE);
	    		}else{
					setRowStyleLockStatus(row,"����¥���ԡ��޷�ʶ��\r\n");
					return;
	    		}
    		}else if(isForShe instanceof Boolean){
    			customer.setIsForSHE(((Boolean)isForShe).booleanValue());
    		}    		
    	}
    	row.getCell("isForShe").setValue(new Boolean(customer.isIsForSHE()));  	
    	    	
    	Object isForTen = row.getCell("isForTen").getValue();			//��������
    	if(isForTen!=null ) {
    		if(isForTen instanceof String) {
	    		if(isForTen.toString().trim().equals("��")){
	    			customer.setIsForTen(true);
	    			row.getCell("isForTen").setValue(Boolean.TRUE);
	    		}else if(isForTen.toString().trim().equals("��")){
	    			customer.setIsForTen(false);
	    			row.getCell("isForTen").setValue(Boolean.FALSE);
	    		}else{
					setRowStyleLockStatus(row,"���������ԡ��޷�ʶ��\r\n");
					return;
	    		}
    		}else if(isForTen instanceof Boolean){
    			customer.setIsForTen(((Boolean)isForTen).booleanValue());
    		}    		
    	}
      	row.getCell("isForTen").setValue(new Boolean(customer.isIsForTen()));
    	
    	Object isForPpm = row.getCell("isForPpm").getValue();			//��ҵ����
    	if(isForPpm!=null) {
    		if(isForPpm instanceof String) {
	    		if(isForPpm.toString().trim().equals("��")){
	    			customer.setIsForPPM(true);
	    			row.getCell("isForPpm").setValue(Boolean.TRUE);
	    		}else if(isForPpm.toString().trim().equals("��")){
	    			customer.setIsForPPM(false);
	    			row.getCell("isForPpm").setValue(Boolean.FALSE);
	    		}else{
					setRowStyleLockStatus(row,"����ҵ���ԡ��޷�ʶ��\r\n");
					return;
	    		}        		
    		}else if(isForPpm instanceof Boolean){
    			customer.setIsForPPM(((Boolean)isForPpm).booleanValue());
    		}    	
    	}    	
    	row.getCell("isForPpm").setValue(new Boolean(customer.isIsForPPM()));
    	
    	
		
		//���ѡ������  ��������򲻴��� �����¼��
    	String number  = (String)row.getCell("number").getValue();	 //����
    	
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
    	
    	String name  = (String)row.getCell("name").getValue();		 //�ͻ�����
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
    	
    	Object sex = row.getCell("sex").getValue();			//����Ա�
    	if(sex!=null) {
    		if(sex instanceof String) {
    			SexEnum rsex = getSexEnumByNameStr((String)sex);
    			if(rsex!=null) {
    				row.getCell("sex").setValue(rsex);
    				customer.setSex(rsex);
    			}else{
    				setRowStyleLockStatus(row,"���Ա��޷�ʶ��\r\n");
    				return;
    			}
    		}else if(sex instanceof SexEnum) {
    			customer.setSex((SexEnum)sex);
    		}
    	}        	

    	Object saleTrackPhase = row.getCell("saleTrackPhase").getValue();			//��¥�����׶�
    	if(saleTrackPhase!=null) {
    		if(saleTrackPhase instanceof String) {
    			CommerceStatusEnum saleTrackEnum = getTrackPhaseEnum((String)saleTrackPhase);
    			if(saleTrackEnum!=null){
    				row.getCell("saleTrackPhase").setValue(saleTrackEnum);
    				customer.setSaleTrackPhase(saleTrackEnum);
    			}else{
    				setRowStyleLockStatus(row,"����¥�����׶Ρ��޷�ʶ��\r\n");
    				return;
    			}
    		}else if(saleTrackPhase instanceof CommerceStatusEnum){
    			customer.setSaleTrackPhase((CommerceStatusEnum)saleTrackPhase);
    		}        		
    	}       
    	
    	Object tenTrackPhase = row.getCell("tenancyTrackPhase").getValue();			//���޸����׶�
    	if(tenTrackPhase!=null) {
    		if(tenTrackPhase instanceof String) {
    			CommerceStatusEnum tenTrackEnum = getTrackPhaseEnum((String)tenTrackPhase);
    			if(tenTrackEnum!=null){
    				row.getCell("tenancyTrackPhase").setValue(tenTrackEnum);
    				customer.setTenancyTrackPhase(tenTrackEnum);
    			}else{
    				setRowStyleLockStatus(row,"�����޸����׶Ρ��޷�ʶ��\r\n");
    				return;
    			}
    		}else if(tenTrackPhase instanceof CommerceStatusEnum){
    			customer.setTenancyTrackPhase((CommerceStatusEnum)tenTrackPhase);
    		}        		
    	} 



    	
    	String phone  = (String)row.getCell("phone").getValue();	//�ƶ��绰
    	if(phone==null)  {
			setRowStyleLockStatus(row,"���ƶ��绰������¼�룡\r\n");
			return;		
    	}else{
    		if(phone.length()>80) {
    			setRowStyleLockStatus(row,"���ƶ��绰���ַ�>80��\r\n");
    			return;    			
    		}else if(phone.length()<5) {
    			setRowStyleLockStatus(row,"���ƶ��绰���ַ�<5��\r\n");
    			return;    			
    		}
    		customer.setPhone(phone.toString());
    	}
    	
    	Object lastTrackDate  = row.getCell("lastTrackDate").getValue();	//���¸���ʱ��
    	if(lastTrackDate!=null && lastTrackDate instanceof String){
    		if(CommerceHelper.isDateFormat(lastTrackDate.toString(),"yyyy-MM-dd")) {
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date thisDate = formatter.parse(lastTrackDate.toString());
					customer.setLastTrackDate(thisDate);
        			row.getCell("lastTrackDate").setValue(thisDate);
				} catch (ParseException e) {
					e.printStackTrace();					
				}        			
    		}else{
    			setRowStyleLockStatus(row,"�����¸������޷�ʶ����ȷ��ʽΪ'yyyy-MM-dd'\r\n");
    			return;		
    		}
    	}else if(lastTrackDate instanceof Date) {
    		customer.setLastTrackDate((Date)lastTrackDate);
    	}    	
    	
    	String email = (String)row.getCell("email").getValue();    //�ʼ�
    	if(email!=null && email.length()>70){
			setRowStyleLockStatus(row,"���ʼ����ַ�>80��\r\n");
			return;		
    	}
    	customer.setEmail(email);      	
    	
    	Object certificateName = row.getCell("certificateName").getValue();			//֤������
    	if(certificateName!=null) {
    		if(certificateName instanceof String) {
    			//CertifacateNameEnum thisEnum = getCertifacateNameEnum((String)certificateName);
    			CertificateInfo info  = getCertifacateNameF7((String)certificateName);
    			if(info!=null){
    				row.getCell("certificateName").setValue(info);
    				customer.setCertificateName(info);
    			}else{
    				setRowStyleLockStatus(row,"��֤�����ơ��޷�ʶ��\r\n");
    				return;
    			}
    		}else if(certificateName instanceof CertificateInfo){
    			customer.setCertificateName((CertificateInfo)certificateName);
    		}        		
    	}        	
    	
    	String certificateNumber = (String)row.getCell("certificateNumber").getValue();    	//֤������
    	if(certificateNumber!=null && certificateNumber.length()>80){
			setRowStyleLockStatus(row,"��֤�����롯���ȹ�����\r\n");
			return;	  
    	}        	
    	customer.setCertificateNumber(certificateNumber);
    	
    	String mailAddress = (String)row.getCell("mailAddress").getValue();    //ͨ�ŵ�ַ
    	if(mailAddress!=null && mailAddress.length()>250){
			setRowStyleLockStatus(row,"��ͨ�ŵ�ַ�����ȹ�����\r\n");
			return;	  
    	}        
    	customer.setMailAddress(mailAddress);
    	
    	Object province = row.getCell("province.name").getValue();	  //ʡ��
    	if(province!=null){
    		if(province instanceof String) {
    			ProvinceInfo thisInfo = getProvinceByNameStr((String)province);
    			if(thisInfo!=null){
    				row.getCell("province.name").setValue(thisInfo);
    				customer.setProvince(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"��ʡ�С��޷�ʶ��\r\n");
    				return;
    			}        			
    		}else if(province instanceof ProvinceInfo) {
    			customer.setProvince((ProvinceInfo)province);
    		}        		
    	}

    	Object habitationArea = row.getCell("habitationArea.name").getValue();   //��ס����/��������
    	if(habitationArea!=null) {
    		if(habitationArea instanceof String) {
    			HabitationAreaInfo thisInfo = getHabitationAreaByNameStr((String)habitationArea);
    			if(thisInfo!=null){
    				row.getCell("habitationArea.name").setValue(thisInfo);
    				customer.setHabitationArea(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"����ס����/���������޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(habitationArea instanceof HabitationAreaInfo){
    			customer.setHabitationArea((HabitationAreaInfo)habitationArea);
    		}
    	}
    	
    	Object customerOrigin = row.getCell("customerOrigin.name").getValue();   //�ͻ���Դ
    	if(customerOrigin!=null) {
    		if(customerOrigin instanceof String) {
    			CustomerOriginInfo thisInfo = getCustomerOriginByNameStr((String)customerOrigin);
    			if(thisInfo!=null){
    				row.getCell("customerOrigin.name").setValue(thisInfo);
    				customer.setCustomerOrigin(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���ͻ���Դ���޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(customerOrigin instanceof CustomerOriginInfo){
    			customer.setCustomerOrigin((CustomerOriginInfo)customerOrigin);
    		}
    	}
    	
    	Object receptionType = row.getCell("receptionType.name").getValue();   //�Ӵ���ʽ
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

    	Object customerGrade = row.getCell("customerGrade.name").getValue();   //�ͻ��ȼ� 
    	if(customerGrade!=null) {
    		if(customerGrade instanceof String) {
    			CustomerGradeInfo thisInfo = getCustomerGradeByNameStr((String)customerGrade);
    			if(thisInfo!=null){
    				row.getCell("customerGrade.name").setValue(thisInfo);
    				customer.setCustomerGrade(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���ͻ��ȼ����޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(customerGrade instanceof CustomerGradeInfo){
    			customer.setCustomerGrade((CustomerGradeInfo)customerGrade);
    		}
    	}    	
    	
    	String tradeTime = (String)row.getCell("tradeTime").getValue();
    	if(tradeTime!=null && !CommerceHelper.isNumber(tradeTime)) {
    		setRowStyleLockStatus(row,"�����۽��״�����Ϊ�Ǹ�������\r\n");
    		return;
    	}
    	if(tradeTime==null) tradeTime = "0";
    	else if(tradeTime.length()>10) {
    		setRowStyleLockStatus(row,"�����۽��״���������̫��\r\n");
    		return;
    	}
    	customer.setTradeTime(Long.parseLong(tradeTime));    	
    	
    	String tenTradeTime = (String)row.getCell("tenancyTradeTime").getValue();
    	if(tenTradeTime!=null && !CommerceHelper.isNumber(tenTradeTime)) {
    		setRowStyleLockStatus(row,"�����޽��״�����Ϊ�Ǹ�������\r\n");
    		return;
    	}
    	if(tenTradeTime==null) tenTradeTime = "0";
    	else if(tenTradeTime.length()>10){
    		setRowStyleLockStatus(row,"�����޽��״���������̫��\r\n");
    		return;
    	}
    	customer.setTenTradeTime(Long.parseLong(tenTradeTime));

    	String workCompany = (String)row.getCell("workCompany").getValue();   	//��˾
    	if(workCompany!=null && workCompany.length()>70 ){
			setRowStyleLockStatus(row,"����˾�����ȹ�����\r\n");
			return;  
    	}     
    	customer.setWorkCompany(workCompany);    	
    	
    	String employment = (String)row.getCell("employment").getValue();    //ְҵ
    	if(employment!=null && employment.length()>70){
			setRowStyleLockStatus(row,"��ְҵ�����ȹ�����\r\n");
			return;  
    	} 
    	customer.setEmployment(employment);    	
    	
    	String postalcode = (String)row.getCell("postalcode").getValue();   //�ʱ�
    	if(postalcode!=null && postalcode.length()>70){
			setRowStyleLockStatus(row,"���ʱ࡯���ȹ�����\r\n");
			return;  
    	} 
    	customer.setPostalcode(postalcode);        	
    	
    	Object famillyEarning = row.getCell("famillyEarning.name").getValue();   //��ͥ���� 
    	if(famillyEarning!=null) {
    		if(famillyEarning instanceof String) {
    			FamillyEarningInfo thisInfo = getFamillyEarningByNameStr((String)famillyEarning);
    			if(thisInfo!=null){
    				row.getCell("famillyEarning.name").setValue(thisInfo);
    				customer.setFamillyEarning(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"����ͥ���롯�޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(famillyEarning instanceof FamillyEarningInfo){
    			customer.setFamillyEarning((FamillyEarningInfo)famillyEarning);
    		}
    	}        	
    	
    	Object customerType = row.getCell("customerType").getValue();   //�ͻ����� 
    	if(customerType!=null) {
    		if(customerType instanceof String) {
    			CustomerTypeEnum thisInfo = getCustomerTypeEnum((String)customerType);
    			if(thisInfo!=null){
    				row.getCell("customerType").setValue(thisInfo);
    				customer.setCustomerType(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���ͻ����͡��޷�ʶ��\r\n");
    				return;  
    			}        
    		}else if(customerType instanceof CustomerTypeEnum){
    			customer.setCustomerType((CustomerTypeEnum)customerType);
    		}
    	}
    	if(customer.getCustomerType()==null) {
			setRowStyleLockStatus(row,"���ͻ����͡��������룡\r\n");
			return;  
    	}
    		    	
    	row.getCell("isEnabled").setValue(Boolean.TRUE);
    	customer.setIsEnabled(true);
    	
    	String description = (String)row.getCell("description").getValue();    //����
    	if((description!=null && description.length()>250)){
			setRowStyleLockStatus(row,"�����������ȹ�����\r\n");
			return;   
    	}    
    	customer.setDescription(description);
    	 	
    	Object project = row.getCell("project.name").getValue();   //��ʼ�Ǽ���Ŀ 
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
    	
    	Object saleMan = row.getCell("salesman.name").getValue();   //���۹���
    	if(saleMan!=null) {
    		if(saleMan instanceof String) {
    			UserInfo thisInfo = getUserByNameStr((String)saleMan);
    			if(thisInfo!=null){
    				row.getCell("salesman.name").setValue(thisInfo);
    				customer.setSalesman(thisInfo);
    				row.getCell("salesman.name").setValue(thisInfo.getName());
    			}else{
    				setRowStyleLockStatus(row,"�����۹��ʡ��޷�ʶ��\r\n");
    				return;  
    			}        
    		}else if(saleMan instanceof UserInfo){
    			customer.setSalesman((UserInfo)saleMan);
    		}
    	}else{
			setRowStyleLockStatus(row,"�����۹��ʡ�����¼�룡\r\n");
			return;      	
    	}
    	
    	Object bookedPlace = row.getCell("bookedPlace").getValue();  //�Ǽǵص�
    	if(bookedPlace!=null && bookedPlace instanceof String){
    		String bookedString = (String)bookedPlace;
    		if(bookedString.length()>75) {
    			setRowStyleLockStatus(row,"���Ǽǵص㡯���ȹ�����\r\n");
    			return;   
    		}else{
    			customer.setBookedPlace(bookedString);
    		}
    	}    	
    	
    	
    	
    	String phone2  = (String)row.getCell("phone2").getValue();	//�����绰
    	if(phone2==null)  {
//			setRowStyleLockStatus(row,"�������绰������¼�룡\r\n");
//			return;		
    	}else{
    		if(phone2.length()>80) {
    			setRowStyleLockStatus(row,"�������绰���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setPhone2(phone2.toString());
    	}   	

    	String officeTel  = (String)row.getCell("officeTel").getValue();	//�칫�绰
    	if(officeTel==null)  {
			////setRowStyleLockStatus(row,"���칫�绰������¼�룡\r\n");
			///return;		
    	}else{
    		if(officeTel.length()>80) {
    			setRowStyleLockStatus(row,"���칫�绰���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setOfficeTel(officeTel.toString());
    	} 
    	
    	String tel  = (String)row.getCell("tel").getValue();	//סլ�绰
    	if(tel==null)  {
//			setRowStyleLockStatus(row,"��סլ�绰������¼�룡\r\n");
//			return;		
    	}else{
    		if(tel.length()>80) {
    			setRowStyleLockStatus(row,"��סլ�绰���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setTel(tel.toString());
    	} 
    	
    	/**
    	 * ��ͻ�һ�㶼���ṩqq����msn
    	 * ����ȥ����У��
    	 * by  renliang at 2010-9-8
    	 */
    	String qq  = (String)row.getCell("QQ").getValue();	//QQ\MSN
    	/*if(qq==null)  {
    		setRowStyleLockStatus(row,"��QQ\\MSN������¼�룡\r\n");
    		return;		
    	}else{*/
    	if(qq!=null){
    		if(qq.length()>80) {
    			setRowStyleLockStatus(row,"��QQ\\MSN���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setQQ(qq.toString());
    	}
    	/*} */
    	
    	Object workArea = row.getCell("workArea").getValue();   //��������/��������
    	if(workArea!=null) {
    		if(workArea instanceof String) {
    			String [] strs = ((String)workArea).split(",");
    			CustomerWorkAreaEntryCollection workAreaColl = new CustomerWorkAreaEntryCollection();
    			for (int i = 0; i < strs.length; i++) {
    				WorkAreaInfo thisInfo = getWorkArea((String)strs[i]);
    				if(thisInfo!=null){
    					CustomerWorkAreaEntryInfo entryInfo = new CustomerWorkAreaEntryInfo();
    					entryInfo.setWorkArea(thisInfo);
    					workAreaColl.add(entryInfo);
    				}else{
        				setRowStyleLockStatus(row,"����������/���������޷�ʶ��\r\n");
        				return;
        			} 
				}
    			customer.getWorkArea().clear();
    			customer.getWorkArea().addCollection(workAreaColl);
    		}else if(workArea instanceof StringBuffer) {
    			String str=workArea.toString();
    			String [] strs = str.split(",");
    			CustomerWorkAreaEntryCollection workAreaColl = new CustomerWorkAreaEntryCollection();
    			for (int i = 0; i < strs.length; i++) {
    				WorkAreaInfo thisInfo = getWorkArea((String)strs[i]);
    				if(thisInfo!=null){
    					CustomerWorkAreaEntryInfo entryInfo = new CustomerWorkAreaEntryInfo();
    					entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
    					entryInfo.setWorkArea(thisInfo);
    					
//    					entryInfo.setWorkArea(thisInfo);
    					workAreaColl.add(entryInfo);
    				}else{
        				setRowStyleLockStatus(row,"����������/���������޷�ʶ��\r\n");
        				return;
        			} 
				}
    			customer.getWorkArea().clear();
    			customer.getWorkArea().addCollection(workAreaColl);
    		} else if(workArea instanceof Object){
    			
    			Object [] objs = null;
    			try{
    				 objs = (Object [])workArea;
    			}catch (Exception e) {
					logger.error(e.getMessage());
				}
    			CustomerWorkAreaEntryCollection workAreaColl = new CustomerWorkAreaEntryCollection();
    			int length = 0;
    			if(objs != null){
    				length =  objs.length;;
    			}
				for (int i = 0; i < length; i++) {
					Object obj = objs[i];
					WorkAreaInfo thisInfo = getWorkArea(((WorkAreaInfo)obj).getName());	
					CustomerWorkAreaEntryInfo entryInfo = new CustomerWorkAreaEntryInfo();
					entryInfo.setWorkArea(thisInfo);
					workAreaColl.add(entryInfo);
				}
				customer.getWorkArea().clear();
    			customer.getWorkArea().addCollection(workAreaColl);
    		}
    	}
    	
    	Object businessNature = row.getCell("businessNature").getValue();			//��ҵ����
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}else if(businessNature instanceof NatureEnterpriseInfo){
    			customer.setBusinessNature((NatureEnterpriseInfo)businessNature);
    		}        		
    	}
    	
    	String enterpriceSize  = (String)row.getCell("enterpriceSize").getValue();	//��ҵ��ģ
    	if(enterpriceSize==null)  {
//			setRowStyleLockStatus(row,"����ҵ��ģ������¼�룡\r\n");
//			return;		
    	}else{
    		if(enterpriceSize.length()>80) {
    			setRowStyleLockStatus(row,"����ҵ��ģ���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setEnterpriceSize(enterpriceSize.toString());
    	} 
    	
    	Object industry = row.getCell("industry").getValue();   //��ҵ
    	if(industry!=null) {
    		if(industry instanceof String) {
    			IndustryInfo thisInfo = getIndustry((String)industry);
    			if(thisInfo!=null){
    				row.getCell("industry").setValue(thisInfo);
    				customer.setIndustry(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"����ҵ���޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(industry instanceof IndustryInfo){
    			customer.setIndustry((IndustryInfo)industry);
    		}
    	}
    	
    	String enterpriceHomepage  = (String)row.getCell("enterpriceHomepage").getValue();	//��ҵ��ַ
    	if(enterpriceHomepage==null)  {
//			setRowStyleLockStatus(row,"����ҵ��ַ������¼�룡\r\n");
//			return;		
    	}else{
    		if(enterpriceHomepage.length()>80) {
    			setRowStyleLockStatus(row,"����ҵ��ַ���ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setEnterpriceHomepage(enterpriceHomepage.toString());
    	} 

    	Object cooperateMode = row.getCell("cooperateMode").getValue();   //����ģʽ
    	if(cooperateMode!=null) {
    		if(cooperateMode instanceof String) {
    			CooperateModeInfo thisInfo = getCooperateMode((String)cooperateMode);
    			if(thisInfo!=null){
    				row.getCell("cooperateMode").setValue(thisInfo);
    				customer.setCooperateMode(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"������ģʽ���޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(cooperateMode instanceof CooperateModeInfo){
    			customer.setCooperateMode((CooperateModeInfo)cooperateMode);
    		}
    	}
    	
    	Object businessScope = row.getCell("businessScope").getValue();   //ҵ��Χ
    	if(businessScope!=null) {
    		if(businessScope instanceof StringBuffer) {
    			String str=businessScope.toString();
    			String [] strs = str.split(",");
    			//String [] strs = ((String)workArea).split(",");
    			//�޸������workAreaΪbusinessScope by renliang at 2010-6-26
//    			String [] strs = ((String)businessScope).split(",");
    			CustomerBusinessScopeEntryCollection businessColl = new CustomerBusinessScopeEntryCollection();
    			for (int i = 0; i < strs.length; i++) {
    				BusinessScopeInfo thisInfo = getBusinessScope((String)strs[i]);
    				if(thisInfo!=null){
    					CustomerBusinessScopeEntryInfo businessInfo = new CustomerBusinessScopeEntryInfo();
    					businessInfo.setId(BOSUuid.create(businessInfo.getBOSType()));
    					businessInfo.setBusinessScope(thisInfo);
    					businessColl.add(businessInfo);
    				}else{
    					setRowStyleLockStatus(row,"��ҵ��Χ���޷�ʶ��\r\n");
        				return;
        			} 
				}
    			customer.getBusinessScope().clear();
    			customer.getBusinessScope().addCollection(businessColl);
    		}else if(businessScope instanceof String){
    			String [] strs = ((String)businessScope).split(",");
    			//�޸������workAreaΪbusinessScope by renliang at 2010-6-26
//    			String [] strs = ((String)businessScope).split(",");
    			CustomerBusinessScopeEntryCollection businessColl = new CustomerBusinessScopeEntryCollection();
    			for (int i = 0; i < strs.length; i++) {
    				BusinessScopeInfo thisInfo = getBusinessScope((String)strs[i]);
    				if(thisInfo!=null){
    					CustomerBusinessScopeEntryInfo businessInfo = new CustomerBusinessScopeEntryInfo();
    					businessInfo.setId(BOSUuid.create(businessInfo.getBOSType()));
    					businessInfo.setBusinessScope(thisInfo);
    					businessColl.add(businessInfo);
    				}else{
    					setRowStyleLockStatus(row,"��ҵ��Χ���޷�ʶ��\r\n");
        				return;
        			} 
				}
    			customer.getBusinessScope().clear();
    			customer.getBusinessScope().addCollection(businessColl);
    		}
    		else if(businessScope instanceof Object){
    			Object [] objs = null;
    			try{
    				 objs = (Object [])businessScope;
    			}catch (Exception e) {
					logger.error(e.getMessage());
				}
    			
    			CustomerBusinessScopeEntryCollection businessColl = new CustomerBusinessScopeEntryCollection();
    			int length = 0;
    			if(objs != null){
    				length =  objs.length;;
    			}
				for (int i = 0; i < length; i++) {
					Object obj = objs[i];
					BusinessScopeInfo thisInfo = getBusinessScope(((BusinessScopeInfo)obj).getName());	
					CustomerBusinessScopeEntryInfo businessInfo = new CustomerBusinessScopeEntryInfo();
					businessInfo.setId(BOSUuid.create(businessInfo.getBOSType()));
					businessInfo.setBusinessScope(thisInfo);
					businessColl.add(businessInfo);
				}
				customer.getBusinessScope().clear();
    			customer.getBusinessScope().addCollection(businessColl);
    		}
    	}
    	
    	Object country = row.getCell("country").getValue();   //����
    	if(country!=null) {
    		if(country instanceof String) {
    			CountryInfo thisInfo = getCountryMode((String)country);
    			if(thisInfo!=null){
    				row.getCell("country").setValue(thisInfo);
    				customer.setCountry(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"�����ҡ��޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(country instanceof CountryInfo){
    			customer.setCountry((CountryInfo)country);
    		}
    	}

    	String fax  = (String)row.getCell("fax").getValue();	//����
    	if(fax==null)  {
//    		setRowStyleLockStatus(row,"�����桯����¼�룡\r\n");
//    		return;		
    	}else{
    		if(fax.length()>80) {
    			setRowStyleLockStatus(row,"�����桯�ַ�>80��\r\n");
    			return;    			
    		}
    		customer.setFax(fax);
    	}
    	
    	Object customerManager = row.getCell("customerManager").getValue();   //�ͻ�����
    	if(customerManager!=null) {
    		if(customerManager instanceof String) {
    			UserInfo thisInfo = getCustomerManager((String)customerManager);
    			if(thisInfo!=null){
    				row.getCell("customerManager").setValue(thisInfo);
    				customer.setCustomerManager(thisInfo);
    			}else{
    				setRowStyleLockStatus(row,"���ͻ������޷�ʶ��\r\n");
    				return;
    			}        
    		}else if(customerManager instanceof UserInfo){
    			customer.setCustomerManager((UserInfo)customerManager);
    		}
    	}    	   
    	
    	if(!customer.isIsForPPM() && !customer.isIsForSHE() && !customer.isIsForTen()) {
			setRowStyleLockStatus(row,"����¥�����ޡ���ҵ���ԡ�����ѡ��һ�� ��\r\n");
			return;
    	}
		
    	row.setUserObject(customer);
    	setRowStyleLockStatus(row,"У��ͨ��");		
	}
	

	//�Ա�  -- �������������Ƿ�����Ѵ��ڵ��Ա������У������ض�Ӧ���Ա�
	private SexEnum getSexEnumByNameStr(String nameStr){
		if(nameStr==null || nameStr.trim().equals("")) return null;
		
		if(mapSexEnum==null) 
			mapSexEnum = CommerceHelper.getSexEnumMap();    	
    	return (SexEnum)mapSexEnum.get(nameStr.trim());
    }
	
	
	private CommerceStatusEnum getTrackPhaseEnum(String trackPhase){
		if(trackPhase==null || trackPhase.trim().equals("")) return null;
		
		if(mapTrackPhaseEnum==null) 
			mapTrackPhaseEnum = CommerceHelper.getTrackPhaseCSMap();    	
    	return (CommerceStatusEnum)mapTrackPhaseEnum.get(trackPhase.trim());
	}

	private NatureEnterpriseInfo getEnterpriceProperty(String natureEnterpriseName) throws BOSException{
		if(natureEnterpriseName==null || natureEnterpriseName.trim().equals("")) return null;
		
		if(mapEnterprisePropertyEnum==null) 
			mapEnterprisePropertyEnum = CommerceHelper.getNatureEnterpriseMap();    	
		return (NatureEnterpriseInfo)mapEnterprisePropertyEnum.get(natureEnterpriseName.trim());
	}
	
	private CertifacateNameEnum getCertifacateNameEnum(String certificateName){
		if(certificateName==null || certificateName.trim().equals("")) return null;
		
		if(mapCertifacateNameEnum==null) 
			mapCertifacateNameEnum = CommerceHelper.getCertifacateNameMap();    	
    	return (CertifacateNameEnum)mapCertifacateNameEnum.get(certificateName.trim());
	}
	
	private CertificateInfo getCertifacateNameF7(String certificateName){
		CertificateInfo info  = null;
		try {
			info = CertificateFactory.getRemoteInstance().getCertificateInfo(
					"select id,name,number where name='" + certificateName + "'");
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		return info;
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
	
	private HabitationAreaInfo getHabitationAreaByNameStr(String habitationArea){
		if(habitationArea==null || habitationArea.trim().equals("")) return null;
		
		if(mapHabitationArea==null) {
			try{
				mapHabitationArea = CommerceHelper.getHabitationAreaMap();
			}catch(BOSException e){this.abort();}
		}	    	
    	return (HabitationAreaInfo)mapHabitationArea.get(habitationArea.trim());
	}

	
	private WorkAreaInfo getWorkArea(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapWorkArea==null) {
			try{
				mapWorkArea = CommerceHelper.getWorkAreaMap();
			}catch(BOSException e){this.abort();}
		}	    	
		return (WorkAreaInfo)mapWorkArea.get(fdcCustomer.trim());
	}

	private IndustryInfo getIndustry(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapIndustry==null) {
			try{
				mapIndustry = CommerceHelper.getIndustryMap();
			}catch(BOSException e){this.abort();}
		}	    	
		return (IndustryInfo)mapIndustry.get(fdcCustomer.trim());
	}

	private CooperateModeInfo getCooperateMode(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapCooperateMode==null) {
			try{
				mapCooperateMode = CommerceHelper.getCooperateModeMap();
			}catch(BOSException e){
				e.printStackTrace();
				this.abort();}
		}	    	
		return (CooperateModeInfo)mapCooperateMode.get(fdcCustomer.trim());
	}
	private BusinessScopeInfo getBusinessScope(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapBusinessScope==null) {
			try{
				mapBusinessScope = CommerceHelper.getBusinessScopeMap();
			}catch(BOSException e){this.abort();}
		}	    	
		return (BusinessScopeInfo)mapBusinessScope.get(fdcCustomer.trim());
	}

	private UserInfo getCustomerManager(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapCustomerManager==null) {
			try{
				mapCustomerManager = CommerceHelper.getCustomerManagerMap();
			}catch(BOSException e){this.abort();}
		}	    	
		return (UserInfo)mapCustomerManager.get(fdcCustomer.trim());
	}

	private CountryInfo getCountryMode(String fdcCustomer){
		if(fdcCustomer==null || fdcCustomer.trim().equals("")) return null;
		
		if(mapCountry==null) {
			try{
				mapCountry = CommerceHelper.getCountryModeMap();
			}catch(BOSException e){this.abort();}
		}	    	
		return (CountryInfo)mapCountry.get(fdcCustomer.trim());
	}
	
	
	private CustomerOriginInfo getCustomerOriginByNameStr(String customerOrigin){
		if(customerOrigin==null || customerOrigin.trim().equals("")) return null;
		
		if(mapCustomerOrigin==null) {
			try{
				mapCustomerOrigin = CommerceHelper.getCustomerOriginMap();
			}catch(BOSException e){this.abort();}	
		}
    	return (CustomerOriginInfo)mapCustomerOrigin.get(customerOrigin.trim());
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
	
	private CustomerGradeInfo getCustomerGradeByNameStr(String customerGrade){
		if(customerGrade==null || customerGrade.trim().equals("")) return null;
		
		if(mapCustomerGrade==null) {
			try{
				mapCustomerGrade = CommerceHelper.getCustomerGradeMap();
			}catch(BOSException e){this.abort();}
		}	    	
    	return (CustomerGradeInfo)mapCustomerGrade.get(customerGrade.trim());
	}
	
	private FamillyEarningInfo getFamillyEarningByNameStr(String famillyEarning){
		if(famillyEarning==null || famillyEarning.trim().equals("")) return null;
		
		if(mapFamillyEarning==null) {
			try{
				mapFamillyEarning = CommerceHelper.getFamillyEarningMap();
			}catch(BOSException e){this.abort();}
		}
    	return (FamillyEarningInfo)mapFamillyEarning.get(famillyEarning.trim());
	}
	
	private CustomerTypeEnum getCustomerTypeEnum(String customerType){
		if(customerType==null || customerType.trim().equals("")) return null;
		
		if(mapCustomerTypeEnum==null) 
			mapCustomerTypeEnum = CommerceHelper.getCustomerTypeMap();    	
    	return (CustomerTypeEnum)mapCustomerTypeEnum.get(customerType.trim());
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
				// TODO Auto-generated catch block
				this.abort();
			}
		if(getUser!=null){
			return getUser;
		}
		else{
			return null;
		}
//		try{
//			UserInfo currentUserInfo = new UserInfo();
//			currentUserInfo.setNumber(saleMan);
//			mapUser = CommerceHelper.getPermitUserMap(currentUserInfo); 
//		}catch(BOSException e){this.abort();}
//
//		if(mapUser!=null) { 
//			return (UserInfo)mapUser.get((String)saleMan.trim());
//		}else{
//			try{
//				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
//				mapUser = CommerceHelper.getPermitUserMap(currentUserInfo); 
//			}catch(BOSException e){this.abort();}
//			return null;
//		}
		
    	
	}
	
}