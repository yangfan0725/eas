/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.OrgRangeCollection;
import com.kingdee.eas.base.permission.OrgRangeFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.ChinaNumberFormat;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.SpecialtyType;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.CopySupplierEntryCollection;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CopySupplierEntryInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.OfferEnum;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.app.YesOrNoEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * ��������� �༭����
 */
public class ChangeAuditEditUI extends AbstractChangeAuditEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeAuditEditUI.class);
    
    private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	
	private final static String CANTAUDIT = "cantAudit";

	private final static String CANTUNAUDIT = "cantUnAudit";
	
	// ��ͬ�����Ŀ�ܺ�Լ
	private static final String PRO_CON = "ProgrammingContract";
	
	//0��ͬ
	private final static int ROW_contractNum = 0;
	//1��ͬ����
	private final static int ROW_contractName = 1;
	//2����λ
	private final static int ROW_mainSupp = 2;
	//���͵�λ
	private final static int ROW_copySupp = 3;
	//����ԭʼ��ϵ���� eric_wang 2010.05.30
	private final static int Row_originalcon =4;
	//�������				
	private final static int ROW_contentDo = 5;
	//�ұ�				
	private final static int ROW_Cur = 6;
	//����
	private final static int ROW_Rate = 7;
	//ԭ��				
	private final static int ROW_OriCost = 8;
	//��λ��
	private final static int ROW_costAmt = 9;
	//�Ƿ�ȷ��������
	private final static int ROW_isSureAmount = 10;
	//����˵��
	private final static int ROW_description = 11;
	//ʩ����������
	private final static int ROW_constPirce = 12;
	
	//�����Ƿ�
	private final static int ROW_isDeduct = 13;
	//ԭ��
	private final static int ROW_OriDed = 14;
	//���οۿ���
	private final static int ROW_deductAmt = 15;
	//���οۿ�˵ing
	private final static int ROW_deductReason = 16;
	//���㷽ʽ
	private final static int ROW_banlanceType = 17;
	//������				
	private final static int ROW_reckoner = 18;
	//���β���
	private final static int ROW_dutySupp = 19;
	
	private boolean hasSaveContentEntry=true;
	//��ǰ�����Ƿ��и����б�
	private boolean isHasAttachment = false;
	
	//�������Ƿ����ѡ���������ŵ���Ա
	boolean canSelectOtherOrgPerson = false;
	//��Ʊ�����������������ʩ����λ���Ƿ����
	boolean isOfferAndConstrReq = false;
	//�Ƿ����ñ���·���Ĭ��Ϊ����
	boolean isDispatch = true;
    /**
     * output class constructor
     */
    public ChangeAuditEditUI() throws Exception
    {
        super();
        actionSave.setBindWorkFlow(false);
        jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	if(this.rbIsBigChangeYes.isSelected()){
        	this.editData.setIsImportChange(true);
        }
        if(this.rbIsBigChangeNo.isSelected()){
        	this.editData.setIsImportChange(false);
        }
        if(this.rbIsChangeContractYes.isSelected()){
        	this.editData.setIsChangeContract(true);
        }
        if(this.rbIsChangeContractNo.isSelected()){
        	this.editData.setIsChangeContract(false);
        }
        if(this.rbIsReceiptsYes.isSelected()){
        	this.editData.setIsReceipts(true);
        }
        if(this.rbIsReceiptsNo.isSelected()){
        	this.editData.setIsReceipts(false);
        }
        
        if(rbIsAlreadyExecutedNo.isSelected()){
        	this.editData.setIsAlreadyExecuted(false);
        }
        
        if(rbIsAlreadyExecutedYes.isSelected()){
        	this.editData.setIsAlreadyExecuted(true);
        }
    	try {
			storeDetailEntries();
		} catch (Exception e) {
			super.handUIException(e);
		} 
		
        super.storeFields();  
        hasSaveContentEntry=true;
        // ��רҵ����
        Object ob = this.prmtSpecialtyType.getValue();
        if( ob instanceof Object[]) {
        	Object[] obj = (Object[])ob;
        	if(obj.length >0 && obj[0] != null){
        		this.editData.getMutiSpecialtyType().clear();
            	SpecialtyTypeEntryInfo info = null;
        		for(int i = 0 ; i < obj.length ; i ++){
        			info = new SpecialtyTypeEntryInfo();
        			info.setParent(editData);
        			info.setSpecialtyType((SpecialtyTypeInfo)obj[i]);
        			this.editData.getMutiSpecialtyType().add(info);
        		}
        	}
        }
    }
    
    /**
     * ����Ǽ��·���λ��¼
     * @throws Exception
     */
    private void storeDetailEntries() throws Exception {
    	ChangeSupplierEntryInfo entryInfo = null;
    	//��¼����
    	int count = getSecondTable().getRowCount()/suppRows;

    	//�������
    	FilterInfo supp = new FilterInfo();
    	FilterItemCollection suppCon = supp.getFilterItems();	
    	suppCon.add(new FilterItemInfo("parent.id", null,CompareType.EQUALS));
    	SupplierContentEntryFactory.getRemoteInstance().delete(supp);
    	
    	//���͵�λ
    	FilterInfo copy = new FilterInfo();
    	FilterItemCollection copySupp = copy.getFilterItems();	
    	copySupp.add(new FilterItemInfo("parent.id", null,CompareType.EQUALS));
    	CopySupplierEntryFactory.getRemoteInstance().delete(copy);
    	
    	//�ܳа���
    	NewMainSupplierEntryFactory.getRemoteInstance().delete(copy);
    	
    	//���ڱ༭�ĵǼǷ�¼
		HashSet idSet=new HashSet();
		for(int j = 0; j < count; j++){
			Object audit = getSecondTable().getCell(j*suppRows+1, 4).getValue();
			if(audit instanceof ChangeSupplierEntryInfo){
				entryInfo = (ChangeSupplierEntryInfo)audit;
				if(entryInfo.getId()!=null)
					idSet.add(entryInfo.getId().toString());
			}
		}
		
		if(idSet.size()>0){
			if(!(count==editData.getSuppEntry().size())){
				EntityViewInfo v = new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.NOTINCLUDE));
				filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString(),CompareType.EQUALS));
				v.setFilter(filter);
				v.getSelector().add("id");
				v.getSelector().add("contractChange.*");
				ChangeSupplierEntryCollection coll = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(v);
				int num = coll.size();
				for(int k=0;k<num;k++){
					ChangeSupplierEntryInfo suppInfo = coll.get(k);
					if(suppInfo.getContractChange()!=null){
						ContractChangeBillInfo changeInfo = suppInfo.getContractChange();
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filte = new FilterInfo();
						filte.getFilterItems().add(
								new FilterItemInfo("id", changeInfo.getId(), CompareType.EQUALS));
						view.setFilter(filte);
						view.getSelector().add("id");
						CoreBaseCollection co = ContractChangeBillFactory.getRemoteInstance().getCollection(view);
						if(co.size()>0)
							ContractChangeBillFactory.getRemoteInstance().delete(new ObjectUuidPK(changeInfo.getId()));
					}
				}
				ChangeSupplierEntryFactory.getRemoteInstance().delete(filter);
			}
		}
		
		//�������ݶ���
		editData.getSuppEntry().clear();
		for(int j = 0; j < count; j++){	
			Object audit = getSecondTable().getCell(j*suppRows+1, 4).getValue();
			
			if(audit instanceof ChangeSupplierEntryInfo){
				entryInfo = (ChangeSupplierEntryInfo)audit;
				
				FilterInfo fi = new FilterInfo();
				FilterItemCollection it = fi.getFilterItems();	
				if(entryInfo.getId()!=null){
					it.add(new FilterItemInfo("parent.id", entryInfo.getId().toString(),CompareType.EQUALS));
					SupplierContentEntryFactory.getRemoteInstance().delete(fi);
				}		       
				FilterInfo fil = new FilterInfo();
				FilterItemCollection itl = fil.getFilterItems();	
				if(entryInfo.getId()!=null){
					itl.add(new FilterItemInfo("parent.id", entryInfo.getId().toString(),CompareType.EQUALS));
					CopySupplierEntryFactory.getRemoteInstance().delete(fil);
					NewMainSupplierEntryFactory.getRemoteInstance().delete(fil);
				}
				
				int curRow = 0;
				
				//0��ͬ
				Object contentCon = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCon instanceof ContractBillInfo){
					entryInfo.setContractBill((ContractBillInfo)contentCon);
				}else if(contentCon==null){
					entryInfo.setContractBill(null);
				}
				curRow++;
				curRow++;
				
				//2����λ
				Object content = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(content instanceof SupplierInfo){
					entryInfo.setMainSupp((SupplierInfo)content);
				}else if(content==null){
					entryInfo.setMainSupp(null);
				}
				curRow++;

				//���͵�λ
				Object contentCopy = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCopy instanceof Object[]){
					Object[] infos = (Object[])contentCopy;

					for(int i=0; i<infos.length;i++){
						CopySupplierEntryInfo info=new CopySupplierEntryInfo();
						info.setCopySupp((SupplierInfo)infos[i]);
						info.setParent(entryInfo);
						info.setSeq(i+1);
						if(entryInfo.getId()!=null)
							CopySupplierEntryFactory.getRemoteInstance().addnew(info);
					}
				}
				curRow++;
				//ԭʼ��ϵ�� eric_wang 2010.05.30
//				Object originalContactNum = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
//				if(originalContactNum != null){
//					entryInfo.setOriginalContactNum(originalContactNum.toString());
//				}else if(originalContactNum==null){
//					entryInfo.setOriginalContactNum(null);
//				}
				//�ܳа���
				Object mainSupp = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				NewMainSupplierEntryCollection colll = new NewMainSupplierEntryCollection();
				if(mainSupp instanceof Object[]){
					Object[] infos = (Object[])mainSupp;
					for(int i=0; i<infos.length;i++){
						NewMainSupplierEntryInfo info=new NewMainSupplierEntryInfo();
						info.setMainSupp((SupplierInfo)infos[i]);
						info.setParent(entryInfo);
						info.setSeq(i+1);
						colll.add(info);
						if(entryInfo.getId()!=null)
							NewMainSupplierEntryFactory.getRemoteInstance().addnew(info);
					}
				}
				entryInfo.put("mySupplier", colll);
				curRow++;
				
				//�������
				Object contentDo = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDo instanceof Object[]){
					Object[] infos = (Object[])contentDo;
					for(int i=0; i<infos.length;i++){
						SupplierContentEntryInfo info=new SupplierContentEntryInfo();
						ChangeAuditEntryInfo test = (ChangeAuditEntryInfo)infos[i];
						info.setContent(test);
						info.setParent(entryInfo);
						info.setSeq(test.getSeq());
						if(entryInfo.getId()!=null)
							SupplierContentEntryFactory.getRemoteInstance().addnew(info);
					}
				}
				curRow++;
				
				//�ұ�
				Object contentCur = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCur instanceof CurrencyInfo){
					entryInfo.setCurrency((CurrencyInfo)contentCur);
				}else if(contentCur==null){
					entryInfo.setCurrency(null);
				}
				curRow++;
				
				//����
				Object contentRate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentRate != null){
					entryInfo.setExRate(FDCHelper.toBigDecimal(contentRate));
				}else if(contentRate==null){
					entryInfo.setExRate(null);
				}
				curRow++;
				
				//ԭ��
				Object contentOriC = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentOriC != null){
					entryInfo.setOriCostAmount(FDCHelper.toBigDecimal(contentOriC));
				}else if(contentOriC==null){
					entryInfo.setOriCostAmount(null);
				}
				curRow++;
				
				
				
				//������
				Object contentCA = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCA != null){
					entryInfo.setCostAmount(FDCHelper.toBigDecimal(contentCA));
				}else if(contentCA==null){
					entryInfo.setCostAmount(null);
				}
				curRow++;
				
				//�Ƿ�ȷ��������  by cassiel
				Object isSureChangeAmt = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(isSureChangeAmt instanceof Boolean){
					if(isSureChangeAmt.equals(Boolean.TRUE))
						entryInfo.setIsSureChangeAmt(true);
					else
						entryInfo.setIsSureChangeAmt(false);
				}
				curRow++;
				
				//����˵��
				Object contentCD = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCD != null){
					entryInfo.setCostDescription(contentCD.toString());
				}else if(contentCD==null){
					entryInfo.setCostDescription(null);
				}
				curRow++;
				
				//ʩ����������
				Object constructPrice = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(constructPrice != null){
					entryInfo.setConstructPrice((BigDecimal)constructPrice);
				}else if(constructPrice==null){
					entryInfo.setConstructPrice(null);
				}
				curRow++;
				
				//�����Ƿ�
				Object contentIsDe = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentIsDe instanceof Boolean){
					if(contentIsDe.equals(Boolean.TRUE))
						entryInfo.setIsDeduct(true);
					else
						entryInfo.setIsDeduct(false);
				}
				curRow++;
				
				//ԭ��
				Object contentDA2 = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDA2 != null){
					entryInfo.setOriDeductAmount(FDCHelper.toBigDecimal(contentDA2));
				}else if(contentDA2==null){
					entryInfo.setOriDeductAmount(null);
				}
				curRow++;
				
				//���οۿ���
				Object contentDA = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDA != null){
					entryInfo.setDeductAmount(FDCHelper.toBigDecimal(contentDA));
				}else if(contentDA==null){
					entryInfo.setDeductAmount(null);
				}
				curRow++;
				
				//���οۿ�˵ing
				Object contentDR = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDR != null){
					entryInfo.setDeductReason(contentDR.toString());
				}else if(contentDR==null){
					entryInfo.setDeductReason(null);
				}
				curRow++;	
				
				//���㷽ʽ
				Object contentBT = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentBT != null){
					entryInfo.setBalanceType(contentBT.toString());
				}else if(contentBT==null){
					entryInfo.setBalanceType(null);
				}
				curRow++;
				
				//������
				Object contentBP = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentBP instanceof UserInfo ){
					entryInfo.setReckonor((UserInfo)contentBP);
				}else if(contentBP==null){
					entryInfo.setReckonor(null);
				}
				curRow++;
				
				//���β���
				Object contentDS = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDS instanceof AdminOrgUnitInfo){
					entryInfo.setDutyOrg((AdminOrgUnitInfo)contentDS);
				}else if(contentDS==null){
					entryInfo.setDutyOrg(null);
				}
				curRow++;
				
				Object startDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(startDate instanceof Date){
					entryInfo.setStartDate((Date) startDate);
				}else if(startDate==null){
					entryInfo.setStartDate(null);
				}
				curRow++;
				
				Object endDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(endDate instanceof Date){
					entryInfo.setEndDate((Date) endDate);
				}else if(endDate==null){
					entryInfo.setEndDate(null);
				}
				curRow++;
				
				Object sfDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(sfDate instanceof Date){
					entryInfo.setSfDate((Date) sfDate);
				}else if(sfDate==null){
					entryInfo.setSfDate(null);
				}
				curRow++;
				
				Object isMarkt = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(isMarkt instanceof String){
					entryInfo.setIsMarket((String) isMarkt);
				}else if(isMarkt==null){
					entryInfo.setIsMarket(null);
				}

				editData.getSuppEntry().add(entryInfo);
			}
			else{
				entryInfo = new ChangeSupplierEntryInfo();
				entryInfo.setParent(editData);
				Object con = getSecondTable().getCell(j*suppRows, 4).getValue();
				if(con instanceof ContractChangeBillInfo){
					entryInfo.setContractChange((ContractChangeBillInfo)con);
				}
				
				int curRow=0;
				
				//0 ��ͬ
				Object contentCon = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCon instanceof ContractBillInfo){
					entryInfo.setContractBill((ContractBillInfo)contentCon);
				}
				curRow++;
				curRow++;
				
				//2����λ
				Object content = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(content instanceof SupplierInfo){
					entryInfo.setMainSupp((SupplierInfo)content);
				}
				curRow++;

				//���͵�λ
				Object contentCopy = getSecondTable().getCell(j*suppRows+3, "content").getValue();
				if(contentCopy instanceof Object[]){
					Object[] infos = (Object[])contentCopy;
	//				CopySupplierEntryCollection c = entryInfo.getCopySupp();//new CopySupplierEntryCollection();
					CopySupplierEntryCollection c = new CopySupplierEntryCollection();
					for(int i=0; i<infos.length;i++){
						CopySupplierEntryInfo info=new CopySupplierEntryInfo();
						info.setCopySupp((SupplierInfo)infos[i]);
						c.add(info);
					}
					entryInfo.put("copySupp", c);
				}
				curRow++;

				//ԭʼ��ϵ���� eric_wang 2010.05.30
//				Object originalContactNum = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
//				if(originalContactNum != null){
//					entryInfo.setOriginalContactNum(originalContactNum.toString());
//				}else if(originalContactNum==null){
//					entryInfo.setOriginalContactNum(null);
//				}
				
				//�ܳа���
				Object mainSupp = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(mainSupp instanceof Object[]){
					Object[] infos = (Object[])mainSupp;
					NewMainSupplierEntryCollection coll = new NewMainSupplierEntryCollection();
					for(int i=0; i<infos.length;i++){
						NewMainSupplierEntryInfo info=new NewMainSupplierEntryInfo();
						info.setMainSupp((SupplierInfo)infos[i]);
						info.setSeq(i+1);
						coll.add(info);
					}
					entryInfo.put("newMainSupp",coll);
				}
				curRow++;

				//�������
				Object contentDo = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDo instanceof Object[]){
					Object[] infos = (Object[])contentDo;
	//				SupplierContentEntryCollection c = entryInfo.getEntrys();//new SupplierContentEntryCollection();
					SupplierContentEntryCollection c = new SupplierContentEntryCollection();
					for(int i=0; i<infos.length;i++){
						SupplierContentEntryInfo info=new SupplierContentEntryInfo();
						info.setContent((ChangeAuditEntryInfo)infos[i]);
						c.add(info);
					}
					entryInfo.put("entrys", c);
				}
				
				curRow++;
				//�ұ�
				Object contentCur = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCur instanceof CurrencyInfo){
					entryInfo.setCurrency((CurrencyInfo)contentCur);
				}
				curRow++;
				
				//����
				Object contentRate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentRate != null){
					entryInfo.setExRate(FDCHelper.toBigDecimal(contentRate));
				}
				curRow++;
				
				//ԭ��
				Object contentOriC = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentOriC != null){
					entryInfo.setOriCostAmount(FDCHelper.toBigDecimal(contentOriC));
				}
				curRow++;
				
				
				//������
				Object contentCA = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCA != null){
					entryInfo.setCostAmount(FDCHelper.toBigDecimal(contentCA));
				}
				curRow++;
				
				//�Ƿ�ȷ�������� by cassiel
				Object isSureChangeAmt = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(isSureChangeAmt instanceof Boolean){
					if(isSureChangeAmt.equals(Boolean.TRUE))
						entryInfo.setIsSureChangeAmt(true);
					else
						entryInfo.setIsSureChangeAmt(false);
				}
				curRow++;
				
				//����˵��
				Object contentCD = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentCD != null){
					entryInfo.setCostDescription(contentCD.toString());
				}				
				curRow++;
				
				//ʩ����������
				Object constructPrice = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(constructPrice != null){
					entryInfo.setConstructPrice((BigDecimal)constructPrice);
				}				
				curRow++;
				
				//
				Object contentIsDe = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentIsDe instanceof Boolean){
					if(contentIsDe.equals(Boolean.TRUE))
						entryInfo.setIsDeduct(true);
					else
						entryInfo.setIsDeduct(false);
				}
				curRow++;
				
				//ԭ��
				Object contentDA2 = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDA2 != null){
					entryInfo.setOriDeductAmount(FDCHelper.toBigDecimal(contentDA2));
				}
				curRow++;
				
				Object contentDA = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDA != null){
					entryInfo.setDeductAmount(FDCHelper.toBigDecimal(contentDA));
				}
				curRow++;
				
				//
				Object contentDR = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDR != null){
					entryInfo.setDeductReason(contentDR.toString());
				}
				curRow++;
				
				//
				Object contentBT = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentBT != null){
					entryInfo.setBalanceType(contentBT.toString());
				}
				curRow++;
				
				//
				Object contentBP = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentBP instanceof UserInfo ){
					entryInfo.setReckonor((UserInfo)contentBP);
				}
				curRow++;
				
				//
				Object contentDS = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(contentDS instanceof AdminOrgUnitInfo){
					entryInfo.setDutyOrg((AdminOrgUnitInfo)contentDS);
				}
				curRow++;
				
				Object startDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(startDate instanceof Date){
					entryInfo.setStartDate((Date) startDate);
				}
				curRow++;
				
				Object endDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(endDate instanceof Date){
					entryInfo.setEndDate((Date) endDate);
				}
				curRow++;
				
				Object sfDate = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(sfDate instanceof Date){
					entryInfo.setSfDate((Date) sfDate);
				}
				curRow++;
				
				Object isMarkt = getSecondTable().getCell(j*suppRows+curRow, "content").getValue();
				if(isMarkt instanceof String){
					entryInfo.setIsMarket((String) isMarkt);
				}
				
				editData.getSuppEntry().addObject(j, entryInfo);

				getSecondTable().getCell(j*suppRows+1, 4).setValue(editData.getSuppEntry().get(j));
			}
		}
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeSubmit();
        super.actionSave_actionPerformed(e);
    }
    public boolean isBillInWorkflow(String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeSubmit();
    	verfySuppEntrys();
    	if (!isCheckCtrlAmountPass()) {
			return;
		}
    	this.storeFields();
    	this.verifyInputForSubmint();
    	UserInfo u=SysContext.getSysContext().getCurrentUserInfo();
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if(project.isIsOA()&&this.editData.getSourceFunction()==null&&u.getPerson()!=null&&!isBillInWorkflow(this.editData.getId().toString())){
			this.editData.setOaPosition(null);
			Map map=ContractBillFactory.getRemoteInstance().getOAPosition(u.getNumber());
			if(map.size()>1){
				UIContext uiContext = new UIContext(this);
				uiContext.put("map", map);
				uiContext.put("editData", this.editData);
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(OaPositionUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        if(this.editData.getOaPosition()==null){
		        	return;
		        }
			}else if(map.size()==1){
				Iterator<Entry<String, String>> entries = map.entrySet().iterator();
				while(entries.hasNext()){
					Entry<String, String> entry = entries.next();
				    String key = entry.getKey();
				    String value = entry.getValue();
				    this.editData.setOaPosition(key+":"+value);
				}
			}else{
				FDCMsgBox.showWarning(this,"��ȡOA���ʧ�ܣ�");
	    		return;
			}
		}
		
        super.actionSubmit_actionPerformed(e);
        doAfterSubmit();
        setSaveActionStatus();
        syncDataFromDB();
        
        if (editData.getChangeState() == ChangeBillStateEnum.Auditting) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
        this.setOprtState("VIEW");
    }
    
    private Object getCtrlParam() throws EASBizException, BOSException {
		String orgPk = this.editData.getOrgUnit().getId().toString();
		IObjectPK orgpk = new ObjectUuidPK(orgPk);
		return ParamControlFactory.getRemoteInstance().getParamValue(orgpk, "FDC228_ISSTRICTCONTROL");
	}

	/**
	 * ���ڲ����� + ǩԼ��� + �ۼƱ�� > ��ܺ�Լ �� �滮��� �Ĳ������Ʋ���
	 * 
	 * @throws Exception
	 */
	private boolean isCheckCtrlAmountPass() throws Exception {
		BOSUuid id = this.editData.getId();
		if (id == null) {
			// δ����ֱ���ύ
			throw new EASBizException(new NumericExceptionSubItem("1", "���ȱ������ύ"));
		}

		Map contractMap = getEntryContracts();
		// if (contractMap.isEmpty()) {
		// // ���к�ͬ��û������ܺ�Լ�򲻼��
		// throw new EASBizException(new NumericExceptionSubItem("1", "����Ӻ�ͬ"));
		// }

		Object param = getCtrlParam();
		String paramValue = param == null ? "" : param.toString();
		if ("2".equals(paramValue)) {
			// �����ƻ��޲���
			return true;
		}

		String result = null;
		if (!contractMap.isEmpty()) {
			result = (String)checkAmount(new ObjectUuidPK(id), contractMap);
		}
		if (StringUtils.isEmpty(result)) {
			// �����ͨ��
			return true;
		}

		if ("0".equals(paramValue)) {
			// �ϸ����
			AdvMsgBox.createAdvMsgBox(this, "", "�������ύ����鿴��ϸ��Ϣ", result+"�������ύ��\n\n", AdvMsgBox.INFORMATION_MESSAGE, AdvMsgBox.DETAIL_OK_OPTION).show();
			return false;
		} else if ("1".equals(paramValue)) {
			// ��ʾ����
			return MsgBox.YES == MsgBox.showConfirm3a(this, "У�鲻ͨ������鿴��ϸ��Ϣ��\n�Ƿ��ύ��", result+"�Ƿ��ύ��\n\n");
		}
		return true;
	}

	
	/**
	 * ��ܺ�Լ֮�滮���
	 * 
	 * @param contractIdSet
	 * @return
	 */
	private String getConAmountSql(Set contractIdSet) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CB.FID CONID, CB.FNUMBER CONNUMBER, PC.FID PROID                       \n");
		sql.append(" ,ISNULL(SUM(PC.FBalance), 0) AMOUNT FROM T_CON_ProgrammingContract PC        \n");
		sql.append("	INNER JOIN T_CON_ContractBill CB ON CB.fProgrammingContract = PC.FID                 \n");
		sql.append("	WHERE CB.FID IN ").append(FDCUtils.buildBillIds(contractIdSet)).append("  \n");
		sql.append(" GROUP BY CB.FID, CB.FNUMBER, PC.FID");
		return sql.toString();
	}
	protected Object checkAmount(IObjectPK pk, Map contractMap) throws BOSException, EASBizException {
		StringBuffer result = new StringBuffer();
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(getConAmountSql(contractMap.keySet()));
		IRowSet rs = fdcSB.executeQuery();

		Map pcMappingMap = new HashMap();
		Map conDetailMap = new HashMap(); // key: ��ͬ id -- value: ��ͬ number

		try {
			while (rs.next()) {
				String proId = rs.getString("PROID");
				String conId = rs.getString("CONID");
				BigDecimal proAmount = rs.getBigDecimal("AMOUNT"); // ��ܺ�Լ�滮���
				String conNumber = rs.getString("CONNUMBER");
				conDetailMap.put(conId, conNumber);

				// ͬһ��ܺ�Լ�º�ͬ��������л���
				ProConMapping mapping = (ProConMapping) pcMappingMap.get(proId);
				if (mapping == null) {
					Set conSet = new HashSet();
					conSet.add(conId);
					mapping = new ProConMapping(proId, conSet, proAmount);
					pcMappingMap.put(proId, mapping);
				} else {
					mapping.conSet.add(conId);
				}

				// �����������ͬ������
				BigDecimal conBudgetAmount = (BigDecimal) contractMap.get(conId);
				if (conBudgetAmount != null) {
					mapping.conBudgetAmountTotal = conBudgetAmount.add(mapping.conBudgetAmountTotal);
				}
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		for (Iterator it = pcMappingMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			ProConMapping mapping = (ProConMapping) entry.getValue();
			if (!mapping.isCheckAmountPass()) {
				result.append("��ͬ ");
				for (Iterator it2 = mapping.conSet.iterator(); it2.hasNext();) {
					String conId = (String) it2.next();
					String conNumber = (String) conDetailMap.get(conId);
					result.append(" [").append(conNumber).append("] ");
				}
				result.append("\n�����������ڿ�ܺ�Լ�Ĺ滮��");
			}
		}

		return result.toString();
	}

	/**
	 * ��ͬ���ܺ�Լӳ��
	 * 
	 * @author yufan_yang
	 * 
	 */
	class ProConMapping {
		String proId;
		Set conSet;
		BigDecimal proBalanceAmount; // ��ܺ�Լ�滮���
		BigDecimal conBudgetAmountTotal; // ����º�ͬ�������ۼ�

		ProConMapping(String _proId, Set _conSet, BigDecimal _proBalanceAmount) {
			proId = _proId;
			conSet = _conSet;
			proBalanceAmount = _proBalanceAmount == null ? FDCHelper.ZERO : _proBalanceAmount;
			conBudgetAmountTotal = FDCHelper.ZERO;
		}

		boolean isCheckAmountPass() throws EASBizException, BOSException {
			ProgrammingContractInfo pc= ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(proId));
			ProgrammingInfo pchead=ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(pc.getProgramming().getId().toString()));
			boolean isExist=ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.CHANGE, conBudgetAmountTotal, false,pchead.getOrgUnit().getId().toString());
			if(!isExist){
				return proBalanceAmount.compareTo(conBudgetAmountTotal) >= 0;
			}
			return true;
		}

		void meger(ProConMapping p) {
			conSet.addAll(p.conSet);
		}

		public int hashCode() {
			return proId == null ? "".hashCode() : proId.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ProConMapping)) {
				return false;
			}
			return proId.equals(((ProConMapping) obj).proId);
		}
	}
	private Map getEntryContracts() {
		Map contractMap = new HashMap();
		for (int i = ROW_contractNum, rowCount = kdtSuppEntry.getRowCount(); i < rowCount; i += suppRows) {
			ContractBillInfo contract = (ContractBillInfo) kdtSuppEntry.getCell(i, "content").getValue();
			ProgrammingContractInfo programmingContract = contract.getProgrammingContract();
			if (programmingContract == null || programmingContract.getId() == null) {
				String conId = contract.getId().toString();
				String oql = "select id, programmingContract where id = '".concat(conId).concat("'");
				try {
					ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(oql);
					programmingContract = contractBillInfo.getProgrammingContract();
					if (programmingContract == null || programmingContract.getId() == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
			}
			BigDecimal amount = (BigDecimal) kdtSuppEntry.getCell(i + ROW_costAmt, "content").getValue();
			// �����ͬ��ͬ�Ĳ������ۼ�
			BigDecimal amountValue = (BigDecimal) contractMap.get(contract.getId().toString());
			if (amountValue != null) {
				amount = amount.add(amountValue);
			}
			contractMap.put(contract.getId().toString(), amount);
		}
		return contractMap;
	}
    
    protected void checkBeforeSubmit() throws Exception{
    	//R110114-024:��ͬ�������¼�뱣���ǰ̨��ѯ��������̨�ܲ�ѯ��.�ύ����������У�飬������ĿΪ��ʱ����ʾ����
    	Object content = prmtCurProject.getData();
    	if(content==null||(content.getClass().isArray() &&FDCHelper.isEmpty(((Object[])content)))){
    		FDCMsgBox.showWarning(this, "������Ŀ����Ϊ�գ�");
    		this.abort();
    	}
    	
    	FDCClientVerifyHelper.verifyRequire(this);
    	if (getDetailTable().getExpandedRowCount()<=0){
    		MsgBox.showWarning(this,ChangeAuditUtil.getRes("contentMust"));
			SysUtil.abort();
    	}else{
    		IRow row;
    		KDTSelectManager manager = getDetailTable().getSelectManager();
    		int rowCount = getDetailTable().getRowCount();
    		int nIndex =0;		
    		for (int i = 0; i < rowCount; i++) {
    			row = getDetailTable().getRow(i);
    			// �������		
    			nIndex = row.getCell("changeContent").getColumnIndex();
    			if (row.getCell("changeContent").getValue() == null) {
    				manager.select(i, nIndex);
    				MsgBox.showWarning(this,ChangeAuditUtil.getRes("content"));
    				SysUtil.abort();
    			}
    			
    		}
    	}
    }  
    private void doAfterSubmit() throws Exception{
    	int i = getSecondTable().getRowCount();
    	ChangeSupplierEntryInfo entryInfo = new ChangeSupplierEntryInfo();
    	int j = i/suppRows;
    	int count = editData.getSuppEntry().size();
    	if(j==count){
    		for(int num = 0; num < j; num++){
    			entryInfo = editData.getSuppEntry().get(num);
    			getSecondTable().getCell(num*suppRows+1, 4).setValue(entryInfo);
    		}
    	}
    }
    
	protected boolean checkCanSubmit() throws Exception {		
		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
			MsgBox.showWarning(this,"���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {        
		//���������������ɾ����
		if (editData != null && ChangeBillStateEnum.Audit.equals(editData.getChangeState())) {
			return;
		}
    	
    	if(tbpChangAudit.getSelectedIndex()==0){
	    	if(getDetailTable().getRowCount()>25){
	    		return;
	    	}
	        super.actionAddLine_actionPerformed(e);
	        getDetailTable().getColumn("number").getStyleAttributes().setLocked(true);
	        getDetailTable().getColumn("changeContent").getStyleAttributes().setLocked(false);
	    	getDetailTable().getColumn("effect").getStyleAttributes().setLocked(false);
	        hasSaveContentEntry=false;
    	}else if(tbpChangAudit.getSelectedIndex()==1){
    		actionAddSupp_actionPerformed(e);
    	}
    	
    }
    
    
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionEdit_actionPerformed(e);
		
		int i = getSecondTable().getRowCount();
		for(int j=0;j<i;j++){
			if(j%suppRows==(ROW_contractNum+1) || j%suppRows==(ROW_contractName+1)){
				getSecondTable().getCell(j, "content").getStyleAttributes().setLocked(true);				
			}
		}
	}

    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
    	return;
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
    	//���������������ɾ����
		if (editData != null && ChangeBillStateEnum.Audit.equals(editData.getChangeState())) {
			return;
		}
		if (tbpChangAudit.getSelectedIndex() == 0) {
			checkSupp();
			super.actionRemoveLine_actionPerformed(e);
			int i = getDetailTable().getRowCount();
			int j;
			for (j = 0; j < i; j++) {
				int k = getDetailTable().getRow(j).getRowIndex();
				char c = (char) ('A' + k);
				getDetailTable().getRow(j).getCell("number").setValue(c + "");
			}
			hasSaveContentEntry=false;
		} else if (tbpChangAudit.getSelectedIndex() == 1) {
			actionDelSupp_actionPerformed(e);
		}
	}
    
    //��������ݷ�¼
    protected void checkSupp() throws Exception{
    	checkSelected();
    	List selectedIdValues = new ArrayList();
		int[] selectedRows = KDTableUtil.getSelectedRows(getDetailTable());
		for (int i = 0; i < selectedRows.length && selectedRows[i]>=0; i++) {
			if(getDetailTable().getCell(selectedRows[i], "id")
					.getValue()!=null){
				String id = getDetailTable().getCell(selectedRows[i], "id")
						.getValue().toString();
				selectedIdValues.add(id);
			}
		} 
		if(selectedIdValues.size()>0){
	    	int count = getSecondTable().getRowCount()/suppRows;
	    	for(int i=0;i<count;i++){
	    		Object contentDo = getSecondTable().getCell(i*suppRows+4, "content").getValue();
				if(contentDo instanceof Object[]){
					Object[] infos = (Object[])contentDo;
	//				SupplierContentEntryCollection c = entryInfo.getEntrys();//new SupplierContentEntryCollection();
	//				SupplierContentEntryCollection c = new SupplierContentEntryCollection();
					for(int j=0; j<infos.length;j++){
						if(infos[j] instanceof ChangeAuditEntryInfo){
							ChangeAuditEntryInfo test = (ChangeAuditEntryInfo)infos[j];
							if(selectedIdValues.contains(test.getId().toString())){
								MsgBox.showError(this, "������ѡ��ִ�еı�����ݣ�����ɾ����");
								SysUtil.abort();
							}		
						}
					}
				}
	    	}
    	}
    }
   
    public void checkBeforeAuditOrUnAudit(ChangeBillStateEnum state, String warning) throws Exception {
    	boolean isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null,
				FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);
		
		if(isSameUserForUnAudit && editData.getAuditor() != null){
			
			if(!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())){
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		//��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
	
		boolean b = editData != null
				&& editData.getChangeState()!= null
				&&  editData.getChangeState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
		
		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(ChangeBillStateEnum.Audit)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}

    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeAuditOrUnAudit(ChangeBillStateEnum.Audit, CANTUNAUDIT);
		checkRef(getSelectBOID());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(editData.getId()!=null)
    		bill.unAudit(editData.getId());
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
//		handleOldData();
		setSaveActionStatus();
    }

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, prmtCurProject);
	}
		
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)newData;
		info.setCurProject(editData.getCurProject());
		info.setChangeState(ChangeBillStateEnum.Saved);
		getSecondTable().removeRows();
	}

	/**
	 * �����������"�������"�к�"����"���Ƿ������
	 * 
	 * @param isLocked
	 * @author owen_wen 2011-08-03
	 */
	private void setStyleAttributesLocked(boolean isLocked) {
		if (getSecondTable().getColumn("content") != null)
			getSecondTable().getColumn("content").getStyleAttributes().setLocked(isLocked);
		if (getDetailTable().getColumn("changeContent") != null)
			getDetailTable().getColumn("changeContent").getStyleAttributes().setLocked(isLocked);
		if (getDetailTable().getColumn("effect") != null)
			getDetailTable().getColumn("effect").getStyleAttributes().setLocked(isLocked);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(STATUS_FINDVIEW.equals(oprtType)){
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);	
			cbxNoUse.setEnabled(false);
		}
		
		Boolean isFromWorkflow = (Boolean) getUIContext().get("isFromWorkflow");
		if(STATUS_VIEW.equals(this.oprtState)){
			cbxNoUse.setEnabled(false);
			bmptResaon.setEnabled(false);
			txtNoUse.setEnabled(false);
			
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			setStyleAttributesLocked(true);
			
			if(editData != null && editData.getChangeState()!=null && 
	    			(editData.getChangeState().equals(ChangeBillStateEnum.AheadDisPatch)||
	    					editData.getChangeState().equals(ChangeBillStateEnum.Announce)||
	    					editData.getChangeState().equals(ChangeBillStateEnum.Audit)||
	    					editData.getChangeState().equals(ChangeBillStateEnum.Auditting)||
	    					editData.getChangeState().equals(ChangeBillStateEnum.Visa))){
	    		actionEdit.setEnabled(false);
	    	}
		}		
		else if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()&&STATUS_FINDVIEW.equals(oprtType)){
    		lockUIForViewStatus();
    		actionAttachment.setEnabled(true);
    		actionAddLine.setEnabled(false);
    		actionRemoveLine.setEnabled(false);
    		this.actionAddSupp.setEnabled(false);
    		this.actionDelSupp.setEnabled(false);
	    	actionSave.setEnabled(true);
	    	actionSubmit.setEnabled(false);
	    	actionRemove.setEnabled(false);
	    	int number = getSecondTable().getRowCount();
			int count = number/suppRows;
			for(int i=0; i<count; i++){
				getSecondTable().getCell(i*suppRows,"content").getStyleAttributes().setLocked(true);
				getSecondTable().getCell(i*suppRows+1,"content").getStyleAttributes().setLocked(true);
				getSecondTable().getCell(i*suppRows+2,"content").getStyleAttributes().setLocked(true);
				getSecondTable().getCell(i*suppRows+3,"content").getStyleAttributes().setLocked(true);
				getSecondTable().getCell(i*suppRows+4,"content").getStyleAttributes().setLocked(true);
			}
    	}else{
    		actionAddSupp.setEnabled(true);
    		actionDelSupp.setEnabled(true);
    	}
		if(STATUS_EDIT.equals(this.oprtState)){
			setStyleAttributesLocked(false);
			cbxNoUse.setEnabled(true);
			if(cbxNoUse.isSelected()){
				txtNoUse.setEnabled(true);
				bmptResaon.setEnabled(true);
				txtNoUse.setRequired(true);
			}else{
				txtNoUse.setEnabled(false);
				bmptResaon.setEnabled(false);
				txtNoUse.setRequired(false);
			}
		}
	}
	
	/**
	 * ������Դ��R100806-236��ͬ¼�롢��������㵥�ݽ�������������ť
	 * <P>
	 * Ϊʵ�ִ�BT��������д���·���
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// ��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	/**
	 * ������Դ��R100806-236��ͬ¼�롢��������㵥�ݽ�������������ť
	 * <P>
	 * Ϊʵ�ִ�BT�����޸����·���
	 * 
	 * @author owen_wen 2011-03-08
	 */
    protected void setAuditButtonStatus(String oprtType){
    	if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		ChangeAuditBillInfo bill = (ChangeAuditBillInfo)editData;
    		if(editData!=null){
    			if(ChangeBillStateEnum.Audit.equals(bill.getChangeState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else if(ChangeBillStateEnum.Submit.equals(bill.getChangeState())
    					|| ChangeBillStateEnum.Saved.equals(bill.getChangeState())){
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    	HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISCHANGEAUDITPRINT", null);
		boolean isWF=true;
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISCHANGEAUDITPRINT")!=null){
				isWF=Boolean.parseBoolean(hmAllParam.get("FDC_ISCHANGEAUDITPRINT").toString());
			}else{
				isWF=true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(isWF){
			if(editData!=null&&(ChangeBillStateEnum.Audit.equals(editData.getChangeState())||ChangeBillStateEnum.Announce.equals(editData.getChangeState()))){
				actionPrint.setEnabled(true);
				actionPrintPreview.setEnabled(true);
				
				btnPrint.setEnabled(true);
				btnPrintPreview.setEnabled(true);
			}else{
				actionPrint.setEnabled(false);
				actionPrintPreview.setEnabled(false);
				
				btnPrint.setEnabled(false);
				btnPrintPreview.setEnabled(false);
			}
		}
    }
	
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ChangeAuditEditUI.class
				.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {		
		ChangeAuditEntryInfo info = new ChangeAuditEntryInfo();
		char c = (char) ('A'+table.getRowCount());
		info.setNumber(c+"");
		info.setId(BOSUuid.create(info.getBOSType()));
		return info;
	}

	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		
		ChangeAuditBillInfo objectValue = new ChangeAuditBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));		
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e1) {
			logger.debug(e1.getMessage(), e1.getCause());
			e1.printStackTrace();
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		
		if(curProject != null) { 
			CurProjectInfo projInfo = curProject;		
			objectValue.setCurProject(projInfo);
			objectValue.setCurProjectName(projInfo.getName());
			objectValue.setCU(projInfo.getCU());
		}		
		
		objectValue.setUrgentDegree(ChangeUrgentDegreeEnum.Normal);
		objectValue.setChangeState(ChangeBillStateEnum.Saved);
		objectValue.setGraphCount(GraphCountEnum.NoFile);
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		//Ĭ����˾
		objectValue.setOffer(OfferEnum.SELFCOM);
		objectValue.setIsAlreadyExecuted(false);
		return objectValue;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnPassHistory.setIcon(EASResource.getIcon("imgTbtn_allotpopedom"));
		Set projLeafNodesIdSet = (Set)getUIContext().get("projLeafNodesIdSet");
		if (projLeafNodesIdSet != null && projLeafNodesIdSet.size() > 0 ) {
			EntityViewInfo v = new EntityViewInfo();
			FilterInfo filter  = new FilterInfo();
			//ʹ��CompareType.INCLUDEʱ�� projLeafNodesIdSet����Ϊ�� 
			filter.getFilterItems().add(new FilterItemInfo("id", projLeafNodesIdSet, CompareType.INCLUDE));
			v.setFilter(filter);
			prmtCurProject.setEntityViewInfo(v);	
		}
		
		txtNumber.setMaxLength(80);
    	txtName.setMaxLength(80);
    	txtReaDesc.setMaxLength(500);
    	txtConstrSite.setMaxLength(200);
    	initUI();   	   	
    	
    	disableAutoAddLine(getDetailTable());
		disableAutoAddLineDownArrow(getDetailTable());
		
		//new add by renliang at 2010-5-11
		FDCClientUtils.initSupplierF7(this, prmtConstrUnit);
		FDCClientUtils.initSupplierF7(this, prmtConductUnit);
		FDCClientUtils.initSupplierF7(this, prmtDesignUnit);

		kdtEntrys.getColumn("changeContent").getStyleAttributes().setWrapText(true);
		kdtEntrys.getColumn("effect").getStyleAttributes().setWrapText(true);
    	
		//�������delete�¼�
		getSecondTable().setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()||BeforeActionEvent.ACTION_PASTE==e.getType()){
					for (int i = 0; i < getSecondTable().getSelectManager().size(); i++)
					{
						KDTSelectBlock block = getSecondTable().getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int amount_index=1;	
								if(colIndex==amount_index) {
									e.setCancel(true);
									continue;
								}
							}
						}
					}
				}
			}
		});
		
		getSecondTable().setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE==e.getType()||BeforeActionEvent.ACTION_PASTE==e.getType()){
					for (int i = 0; i < getSecondTable().getSelectManager().size(); i++)
					{
						KDTSelectBlock block = getSecondTable().getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							//���㱾λ���ۼ�
							if(rowIndex%suppRows==ROW_costAmt){
								for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
									int amount_index=3;	
									if(colIndex==amount_index) {
										BigDecimal amount = FDCHelper.ZERO;
										int count = getSecondTable().getRowCount();
										for(int j=0;j<count;j++){
											if(j%suppRows==ROW_costAmt){
												Object contentCA = getSecondTable().getCell(j, "content").getValue();
												if(contentCA != null){
													amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
												}
											}
										}
										editData.setTotalCost(amount);
										txtTotalCost.setValue(amount);
									}
								}
							}
							//
							if(rowIndex%suppRows==ROW_deductAmt){
								for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
									int amount_index=3;	
									if(colIndex==amount_index) {
										BigDecimal amount = FDCHelper.ZERO;
										int count = getSecondTable().getRowCount();
										for(int j=0;j<count;j++){
											if(j%suppRows==ROW_deductAmt){
												Object contentCA = getSecondTable().getCell(j, "content").getValue();
												if(contentCA != null){
													amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
												}
											}
										}
										editData.setAmountDutySupp(amount);
										txtDutyAmount.setValue(amount);
									}
								}
							}
							if(rowIndex%suppRows==ROW_constPirce){
								for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
									int amount_index=3;	
									if(colIndex==amount_index) {
										BigDecimal amount = FDCHelper.ZERO;
										int count = getSecondTable().getRowCount();
										for(int j=0;j<count;j++){
											if(j%suppRows==ROW_constPirce){
												Object contentCA = getSecondTable().getCell(j, "content").getValue();
												if(contentCA != null){
													amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
												}
											}
										}
										editData.setTotalConstructPrice(amount);
										txtTotalConstructPrice.setValue(amount);
									}
								}
							}
						}
					}
				}
			}
		});
		
		String cuId = editData.getCU().getId().toString();
		
		FDCClientUtils.setRespDeptF7(prmtConductDept, this, canSelectOtherOrgPerson?null:cuId);
		
		//ҵ����־�ж�Ϊ��ʱȡ�ڼ��ж�
		if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
			pkbookedDate.setSupportedEmpty(false);
		}
		if(isOfferAndConstrReq){
			prmtConstrUnit.setRequired(true);
			prmtConductUnit.setRequired(true);
		}/*else{
			FDCClientUtils.setRespDeptF7(prmtConductDept, this, canSelectOtherOrgPerson?null:cuId);
		}*/
		if (editData != null && OfferEnum.SELFCOM.equals(editData.getOffer())) {
			prmtConductDept.setRequired(true);
			prmtConductUnit.setEnabled(false);
			prmtConductUnit.setRequired(false);
		} else {
			prmtConductUnit.setRequired(true);
			prmtConductDept.setRequired(false);
			prmtConductDept.setEnabled(false);
		}
		prmtAuditType.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				prmtAuditType.getQueryAgent().resetRuntimeEntityView();
				if(prmtAuditType.getQueryAgent().getEntityViewInfo() != null){
					EntityViewInfo evi = prmtAuditType.getEntityViewInfo();
				    FilterItemCollection collection = evi.getFilter().getFilterItems();
				    if(collection != null && collection.size() > 0){
				    	for(int i = 0 ;i < collection.size();i++){
				    		FilterItemInfo itemInfo = collection.get(i);
				    		collection.remove(itemInfo);
				    	}
				    }
				}				
			}});
		
		prmtChangeReason.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(prmtAuditType.getData() == null && prmtChangeReason.getData()!=null){
					ChangeTypeInfo info = null;
					try {
						info = ChangeTypeFactory.getRemoteInstance().getChangeTypeInfo(new ObjectUuidPK(((ChangeReasonInfo)prmtChangeReason.getData()).getChangeType().getId().toString()));
					} catch (EASBizException e) {
						logger.debug(e.getMessage(), e.getCause());
						e.printStackTrace();
					} catch (BOSException e) {
						logger.debug(e.getMessage(), e.getCause());
						e.printStackTrace();
					}
					 prmtAuditType.setDataNoNotify(info);
				}
			}});
		
		prmtChangeReason.setIsDefaultFilterFieldsEnabled(false);
		
		prmtChangeReason.addSelectorListener(new SelectorListener(){
       
			public void willShow(SelectorEvent e) {
				
				prmtChangeReason.getQueryAgent().resetRuntimeEntityView();
				if(prmtAuditType.getData() != null){
					if( prmtChangeReason.getQueryAgent().getEntityViewInfo() != null){
						EntityViewInfo evi = prmtChangeReason.getQueryAgent().getEntityViewInfo();
					    FilterItemCollection collection = evi.getFilter().getFilterItems();
						if(collection !=null && collection.size() >0 ){
							for(int i=0;i<collection.size();i++){
								FilterItemInfo itemInfo = collection.get(i);
								if("changeType.id".equalsIgnoreCase(itemInfo.getPropertyName())){
									collection.remove(itemInfo);
								}
								
							}
						}
					    evi.getFilter().appendFilterItem("changeType.id", ((ChangeTypeInfo)prmtAuditType.getData()).getId().toString());
						prmtChangeReason.setEntityViewInfo(evi);
					}else{
						EntityViewInfo newEvi = new EntityViewInfo();
						FilterInfo  filter = new FilterInfo();
						filter.appendFilterItem("changeType.id", ((ChangeTypeInfo)prmtAuditType.getData()).getId().toString());
						newEvi.setFilter(filter);
						prmtChangeReason.getQueryAgent().setEntityViewInfo(newEvi);			
						}
					
				}else{
					prmtChangeReason.getQueryAgent().resetRuntimeEntityView();
					if(prmtChangeReason.getQueryAgent().getEntityViewInfo() != null){
						EntityViewInfo evi = prmtChangeReason.getQueryAgent().getEntityViewInfo();
						FilterItemCollection collection = evi.getFilter().getFilterItems();
						if(collection != null && collection.size() > 0 ){
							for(int i=0;i<collection.size();i++){
								FilterItemInfo itemInfo = collection.get(i);
								collection.remove(itemInfo);
							}
						}
						prmtChangeReason.setEntityViewInfo(evi);
					}
				}
			}});
		
		fillAttachmentList();
		ContractBillInfo contract = (ContractBillInfo)getUIContext().get("contract");
		if(contract!=null){
			kdtSuppEntry.checkParsed();
			addSupp(getSecondTable());
			KDTEditEvent event = new KDTEditEvent(
					kdtEntrys, null, null, 0,
					3, true, 1);
			try {
				event.setValue(contract);
				kdtSuppEntry_editStopped(event);
			} catch (Exception e1) {
				handleException(e1);
			}
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtWFType.setEntityViewInfo(view);
		
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISCHANGEWFTYPE", null);
		boolean isWF=true;
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISCHANGEWFTYPE")!=null){
				isWF=Boolean.parseBoolean(hmAllParam.get("FDC_ISCHANGEWFTYPE").toString());
			}else{
				isWF=true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.prmtWFType.setRequired(isWF);
	}
	
	
	//�����������
	protected  void fetchInitParam() throws Exception{
		
		super.fetchInitParam();		
		
		HashMap param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());		
		if(param.get(FDCConstants.FDC_PARAM_SELECTPERSON)!=null){
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
		if(param.get(FDCConstants.FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION)!=null){
			isOfferAndConstrReq = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION).toString()).booleanValue();
		}
		if(param.get(FDCConstants.FDC_PARAM_ALLOWDISPATCH)!=null){
			isDispatch = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_ALLOWDISPATCH).toString()).booleanValue();
		}
	}
	
    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {    	
    	if(editData.getCurProject()==null){
    		return ;
    	}
    	
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  true);
    }
	
    //������Ŀ�޸ĺ�,��Ҫ�����ڼ����Ϣ
    protected void project_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(!this.isIncorporation){
    		return ;
    	}
    	Object newValue = e.getNewValue();
    	Object oldValue = e.getOldValue();
    	if(newValue!=null && !GlUtils.isEqual(newValue,oldValue)){
	    	String projectId = ((CurProjectInfo)newValue).getId().toString();
	    	getUIContext().put("projectId",BOSUuid.read(projectId));    	
	    	this.fetchInitData();
	    	
			editData.setBookedDate(bookedDate);
			editData.setPeriod(curPeriod);
			
			pkbookedDate.setValue(bookedDate);
			cbPeriod.setValue(curPeriod);
    	}
    }
    
	protected void setSaveActionStatus() {
		if (editData.getChangeState() == ChangeBillStateEnum.Submit) {
			actionSave.setEnabled(true);
			actionRegister.setEnabled(false);
		}
		else if(editData.getChangeState() == ChangeBillStateEnum.Register){
			actionSave.setEnabled(false);
		}
		if(editData.getChangeState()==ChangeBillStateEnum.Register||
				editData.getChangeState()==ChangeBillStateEnum.Saved){
			actionRegister.setEnabled(true);
		}else{
			actionRegister.setEnabled(false);
		}
	}

    //�ؼ����ݱ仯
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	if("bookedDate".equals(listener.getShortCut())){
    		bookedDate_dataChanged(e);
    	}else if("projectChange".equals(listener.getShortCut())){
    		project_dataChanged(e);
    	}
    }
    
    //ҵ�����ڱ仯�¼�
    ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");
    //������Ŀ�仯
    ControlDateChangeListener projectChangeListener = new ControlDateChangeListener("projectChange");
    	
    protected void attachListeners() {   	
    	pkbookedDate.addDataChangeListener(bookedDateChangeListener);
    	prmtCurProject.addDataChangeListener(projectChangeListener);
    			
	   	addDataChangeListener( prmtAuditType);	   	
	   	addDataChangeListener( prmtSpecialtyType);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
    	prmtCurProject.removeDataChangeListener(projectChangeListener);
    	
	   	removeDataChangeListener(prmtAuditType);
	   	removeDataChangeListener(prmtSpecialtyType);
    }
    
	public void loadFields()
    {
		//loadFields ���ע��������,����loadFields�����¼�
		detachListeners();
		
		super.loadFields();
		//����רҵ����
		SpecialtyTypeEntryCollection  coll = this.editData.getMutiSpecialtyType();
		Object[] obj = new Object[coll.size()];
		for(int i = 0 ; i < coll.size() ; i ++ ){
			obj[i] = coll.get(i).getSpecialtyType();
		}
		this.prmtSpecialtyType.setValue(obj);
		
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		}else{
			txtName.requestFocusInWindow();
		}
		
		if(STATUS_VIEW.equals(this.oprtState)){
			if(editData.getChangeState()==null){
				actionEdit.setEnabled(true);
			}
			else if(editData.getChangeState().equals(ChangeBillStateEnum.Saved)||editData.getChangeState().equals(ChangeBillStateEnum.Submit)||
						editData.getChangeState().equals(ChangeBillStateEnum.Register)){
					actionEdit.setEnabled(true);
				}
			else{
				actionEdit.setEnabled(false);
			}
		}
		
		if(STATUS_ADDNEW.equals(this.oprtState)){
			getDetailTable().setEnabled(true);
		}
		
		
		if(editData.getChangeState()!=null){
			if (editData.getChangeState() == ChangeBillStateEnum.Submit) {
				actionSave.setEnabled(true);
				actionRegister.setEnabled(false);
			}
			else if(editData.getChangeState() == ChangeBillStateEnum.Register){
				actionSave.setEnabled(false);
			}
			if(editData.getChangeState().equals(ChangeBillStateEnum.Register)||
					editData.getChangeState().equals(ChangeBillStateEnum.Saved)){
				actionRegister.setEnabled(true);
			}else{
				actionRegister.setEnabled(false);
			}
			if(editData.getChangeState().equals(ChangeBillStateEnum.Saved)){
				actionCopy.setVisible(true);
			}else
				actionCopy.setVisible(false);
		}
		getDetailTable().setRowCount(27);
		getDetailTable().getColumn("number").getStyleAttributes().setLocked(true);	
		getSecondTable().getIndexColumn().getStyleAttributes().setHided(true);
		disableAutoAddLine(getSecondTable());
		disableAutoAddLineDownArrow(getSecondTable());
		/*
		 * �ںϱ��
		 */
//		KDTMergeManager mm = getSecondTable().getHeadMergeManager();
//
//		// �ں�ǰ����
//		mm.mergeBlock(0, 0, 0, 2, KDTMergeManager.SPECIFY_MERGE);	
		txtNumber.setMaxLength(80);
		txtName.setMaxLength(80);
		txtChangeSubject.setMaxLength(80);
		//����������ó���
		final KDBizMultiLangArea textField = new KDBizMultiLangArea();
		//���ı�����ݿؼ�--����Ӧ��������
		textField.setMaxLength(1000);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		textField.setToolTipText("Alt+Enter����");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
		getDetailTable().getColumn("changeContent").setEditor(editor);
		getDetailTable().getColumn("changeContent").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		getDetailTable().getColumn("changeContent").getStyleAttributes().setWrapText(true);
		
		KDTDefaultCellEditor editor1 = new KDTDefaultCellEditor(textField);
		getDetailTable().getColumn("effect").setEditor(editor1);
		getDetailTable().getColumn("effect").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		getDetailTable().getColumn("effect").getStyleAttributes().setWrapText(true);
		actionCopy.setVisible(false);
		if(cbxNoUse.isSelected()){
			txtNoUse.setEnabled(true);
			bmptResaon.setEnabled(true);
			txtNoUse.setRequired(true);
		}else{
			txtNoUse.setEnabled(false);
			bmptResaon.setEnabled(false);
			txtNoUse.setRequired(false);
		}
		btnAttachment.setText(ChangeAuditUtil.getRes("attachment"));
		actionAttachment.setEnabled(true);
		//sortPanel();
		
		if(editData != null && editData.getCurProject() != null) {
			
			String projId = editData.getCurProject().getId().toString();
			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
			FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(projId);
			
			if(costOrg!=null){
				txtOrg.setText(costOrg.getDisplayName());
				editData.setOrgUnit(costOrg);
			}
			editData.setCU(curProjectInfo.getCU());
		}
		
		try {
			if(editData.getSuppEntry().size()>0)
				loadSuppEntrys();
			else{
				getSecondTable().removeRows();
				setSuppNum(suppRows);
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		if(this.editData.isIsAlreadyExecuted()){
			this.rbIsAlreadyExecutedYes.setSelected(true);
		}else{
			this.rbIsAlreadyExecutedNo.setSelected(true);
		}
		
		handleOldData();
		
		getSecondTable().setEditable(true);
		
		attachListeners();

		// R110728-0442:�������̡��״�����-����������ı�������޷��϶�������ȫ
		// Added By Owen_wen ʹ��KDDetailedArear�ؼ��������������
		setDetailCellEditor(getDetailTable(), "changeContent", true, 1000);
		setDetailCellEditor(getDetailTable(), "effect", true, 320);
		
		if(this.editData.isIsImportChange()){
			this.rbIsBigChangeYes.setSelected(true);
		}else{
			this.rbIsBigChangeNo.setSelected(true);
		}
		if(this.editData.isIsChangeContract()){
			this.rbIsChangeContractYes.setSelected(true);
		}else{
			this.rbIsChangeContractNo.setSelected(true);
		}
		if(this.editData.isIsReceipts()){
			this.rbIsReceiptsYes.setSelected(true);
		}else{
			this.rbIsReceiptsNo.setSelected(true);
		}
		
	
    }
	
	public void afterActionPerformed(ActionEvent e)
	{
		super.afterActionPerformed(e);
		Object source = e.getSource();
		if(source==btnNext||source==btnPre||source==btnFirst||source==btnLast||source==menuItemNext
				||source==menuItemPre||source==menuItemLast||source==menuItemFirst){
			try
			{
				contAheadDisPatch.setVisible(ChangeBillStateEnum.AheadDisPatch.equals(editData.getChangeState()));
				setOprtState(getOprtState());
			} catch (Exception e1)
			{
				super.handUIException(e1);
			}
		}
		else if(source==btnSave||source==menuItemSave){
			int count = getSecondTable().getRowCount()/suppRows;
	    	if(count==0){
	    		if(editData.getId()!=null){
		    		FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString(),CompareType.EQUALS));
					try {
						ChangeSupplierEntryFactory.getRemoteInstance().delete(filter);
					} catch (EASBizException e1) {
						super.handUIException(e1);
					} catch (BOSException e1) {
						super.handUIException(e1);
					}
	    		}
	    	}
	    	Boolean isFromWorkflow = (Boolean) getUIContext().get("isFromWorkflow");
	    	if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
	    		lockUIForViewStatus();
	    		actionAttachment.setEnabled(true);
	    		actionAddLine.setEnabled(false);
	    		actionRemoveLine.setEnabled(false);
	    		this.kdtEntrys.setEnabled(false);
	    		this.actionAddSupp.setEnabled(false);
	    		this.actionDelSupp.setEnabled(false);
		    	actionSave.setEnabled(true);
		    	actionSubmit.setEnabled(false);
		    	actionRemove.setEnabled(false);
		    	int number = getSecondTable().getRowCount();
				int countNum = number/suppRows;
				for(int i=0; i<countNum; i++){
					getSecondTable().getCell(i*suppRows,"content").getStyleAttributes().setLocked(true);
					getSecondTable().getCell(i*suppRows+1,"content").getStyleAttributes().setLocked(true);
					getSecondTable().getCell(i*suppRows+2,"content").getStyleAttributes().setLocked(true);
					getSecondTable().getCell(i*suppRows+3,"content").getStyleAttributes().setLocked(true);
					getSecondTable().getCell(i*suppRows+4,"content").getStyleAttributes().setLocked(true);
				}
	    	}
		}
		else if(source==btnRemove||source==menuItemRemove){

		}
		else if(source==btnAddNew||source==menuItemAddNew){
			if(!this.isSaved()){
				return ;
			}
			
			prmtAuditor.setEditable(false);
			actionAddSupp.setEnabled(true);
			actionDelSupp.setEnabled(true);
			getDetailTable().setEnabled(true);			
			getSecondTable().removeRows();
			setSuppNum(suppRows);
			getSecondTable().setEnabled(true);
			if(getSecondTable().getColumn("content")!=null)
				getSecondTable().getColumn("content").getStyleAttributes().setLocked(false);
		}else if(source==btnEdit||source==menuItemEdit){
			prmtCreator.setEditable(false);
			prmtAuditor.setEditable(false);
			cbxNoUse.setEnabled(true);
			if(cbxNoUse.isSelected()){
				txtNoUse.setEnabled(true);
				bmptResaon.setEnabled(true);
				txtNoUse.setRequired(true);
			}else{
				txtNoUse.setEnabled(false);
				bmptResaon.setEnabled(false);
				txtNoUse.setRequired(false);
			}
		}
	}
	
	private void sortPanel() {
		tbpChangAudit.removeAll();
		tbpChangAudit.add(pnlContent,  resHelper.getString("pnlContent.constraints"));
		tbpChangAudit.add(pnlSupp, resHelper.getString("pnlSupp.constraints"));
	}
	
	private void loadSuppEntrys() throws BOSException{
		getSecondTable().checkParsed();
		getSecondTable().getColumn(0).getStyleAttributes().setLocked(true);
		//getSecondTable().getColumn(1).getStyleAttributes().setLocked(true);
		getSecondTable().getColumn(2).getStyleAttributes().setLocked(true);
		getSecondTable().getColumn(0).setWidth(60);
		getSecondTable().getColumn(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		getSecondTable().removeRows();
		int unitNum = editData.getSuppEntry().size();
		for(int i=0;i<unitNum;i++){
			ChangeSupplierEntryInfo info = editData.getSuppEntry().get(i);
			loadValues(info);
		}
		setSuppNum(suppRows);
		BigDecimal amount = FDCHelper.ZERO;
		int i = getSecondTable().getRowCount();
		for(int j=0;j<i;j++){
			//�����λ���ۼ�
			if(j%suppRows==ROW_costAmt){
				Object contentCA = getSecondTable().getCell(j, "content").getValue();
				if(contentCA != null){
					amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
				}
			}
		}
		editData.setTotalCost(amount);
		txtTotalCost.setValue(amount);
		
		amount = FDCHelper.ZERO;
		i = getSecondTable().getRowCount();
		for(int j=0;j<i;j++){
			//�����λ���ۼ�
			if(j%suppRows==ROW_constPirce){
				Object contentCA = getSecondTable().getCell(j, "content").getValue();
				if(contentCA != null){
					amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
				}
			}
		}
		editData.setTotalConstructPrice(amount);
		txtTotalConstructPrice.setValue(amount);
	}
	
	private void loadValues(ChangeSupplierEntryInfo info) throws BOSException{
		final KDTable table = getSecondTable();
		final int i=table.getRowCount();
        table.addRows(suppRows);
        
		//һ����¼������
        int curRow = 0;
        
        //��ͬ�����
        if(info.getContractChange()!=null)
        	table.getCell(i, 4).setValue(info.getContractChange());
        table.getCell(i+1, 4).setValue(info);
        
        //0 ��ͬ
        final KDBizPromptBox prmtMainSupp = new KDBizPromptBox();
        prmtMainSupp.setDisplayFormat("$name$");
        prmtMainSupp.setEditFormat("$number$");
        prmtMainSupp.setCommitFormat("$number$");
        prmtMainSupp.setRequired(true);
        prmtMainSupp.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");
        prmtMainSupp.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                	prmtMainSupp_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        prmtMainSupp.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e)
			{
				if(editData.getCurProject()==null){
					//MsgBox.showWarning(EASResource.getString(resourcePath, "SelectCurProj"));
					e.setCanceled(true);
					return;
				}
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("curProject.id",editData.getCurProject().getId().toString(),CompareType.EQUALS));
				//�ų������ύ״̬
//				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
//				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				///filter.getFilterItems().add(new FilterItemInfo("usedStatus",UsedStatusEnum.UNAPPROVE,CompareType.NOTEQUALS));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);				
			}
		});
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("partB.number"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("isCoseSplit"));	
		sic.add(new SelectorItemInfo("hasSettled"));	
		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));		
		sic.add(new SelectorItemInfo("currency.precision"));	
		sic.add(new SelectorItemInfo("currency.id"));
		
        
        prmtMainSupp.setSelectorCollection(sic);
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtMainSupp));
        if(info.getContractBill()!=null)
        	table.getCell(i+curRow, 3).setValue(info.getContractBill());

        ObjectValueRender rend = new ObjectValueRender();
        rend.setFormat(new BizDataFormat("$number$"));
        table.getCell(i+curRow, 3).setRenderer(rend);
        
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contractNum"), "mainSupp", bindCellMap);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //1��ͬ����
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contractName"), "mainSupp", bindCellMap);
        table.getCell(i+curRow, 3).getStyleAttributes().setLocked(true);
        if(info.getContractBill()!=null&&info.getContractBill().getName()!=null)
        	table.getCell(i+curRow, 3).setValue(info.getContractBill().getName());
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //2���͵�λ
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("mainSupp"), "mainSupp", bindCellMap);
        table.getCell(i+curRow, 3).getStyleAttributes().setLocked(true);
        table.getCell(i+curRow, 3).setValue(info.getMainSupp());
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);       
        curRow++;
//        for (int j = 0; j < 6; j++) {
//        	if(j==3){
//        		continue ;
//        	}
//        	table.getRow(i+j).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//		}

        //���͵�λ
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("copySupp"), "mainSupp", bindCellMap);
        
        KDBizPromptBox prmtCopySupp = new KDBizPromptBox();
        prmtCopySupp.setDisplayFormat("$name$");
        prmtCopySupp.setEditFormat("$number$");
        prmtCopySupp.setCommitFormat("$number$");
        prmtCopySupp.setRequired(false);
        prmtCopySupp.setEnabledMultiSelection(true);
        prmtCopySupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        EntityViewInfo v = FDCClientUtils.getApprovedSupplierView();
        prmtCopySupp.setEntityViewInfo(v);
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtCopySupp));
        ObjectValueRender ren = new ObjectValueRender();
        ren.setFormat(new BizDataFormat("$name$"));
        table.getCell(i+curRow, 3).setRenderer(ren);
        
        EntityViewInfo vie = new EntityViewInfo();
		FilterInfo fil = new FilterInfo();
		FilterItemCollection itl = fil.getFilterItems();
		if(info.getId()!=null)
			itl.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
		vie.setFilter(fil);
		vie.getSelector().add("copySupp.*");
		CopySupplierEntryCollection coll = CopySupplierEntryFactory.getRemoteInstance().getCopySupplierEntryCollection(vie);
		
        int copyNum = coll.size();
        SupplierInfo[] copy = new SupplierInfo[copyNum];
        for(int j=0;j<copyNum;j++){
        	copy[j]=coll.get(j).getCopySupp();
        }
        if(copy!=null)
        	table.getCell(i+curRow, 3).setValue(copy);
        curRow++;
        //����һ��ԭʼ��ϵ����  eric_wang 2010.05.30
//        ChangeAuditUtil.bindCell(table, i+curRow, 2, "�ܳа���","mainSupp",bindCellMap);
//        KDTextField originalContactNum = new KDTextField();
//        originalContactNum.setMaxLength(80);
//        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(originalContactNum));
//        getSecondTable().getRow(i+curRow).getStyleAttributes().setHided(true);
//        //��ֵ
//        if(info.getOriginalContactNum()!=null){
//        	table.getCell(i+curRow, 3).setValue(info.getOriginalContactNum());
//        }
//        table.getCell(i+curRow, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//        curRow++;
        
      //���ӿ��а���  eric_wang 2010.05.30
        ChangeAuditUtil.bindCell(table, i+curRow, 2, "�ܳа���","mainSupp",bindCellMap);
        KDBizPromptBox prmtSupp = new KDBizPromptBox();
        prmtSupp.setDisplayFormat("$name$");
        prmtSupp.setEditFormat("$number$");
        prmtSupp.setCommitFormat("$number$");
        prmtSupp.setRequired(false);
        prmtSupp.setEnabledMultiSelection(true);
        prmtSupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        EntityViewInfo vii = FDCClientUtils.getApprovedSupplierView();
        prmtSupp.setEntityViewInfo(vii);
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtSupp));
        ObjectValueRender ren1 = new ObjectValueRender();
        ren1.setFormat(new BizDataFormat("$name$"));
        table.getCell(i+curRow, 3).setRenderer(ren);
        
        EntityViewInfo viie = new EntityViewInfo();
		FilterInfo fiil = new FilterInfo();
		FilterItemCollection iitl = fiil.getFilterItems();
		if(info.getId()!=null)
			iitl.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
		viie.setFilter(fiil);
		viie.getSelector().add("mainSupp.*");
		NewMainSupplierEntryCollection colll = NewMainSupplierEntryFactory.getRemoteInstance().getNewMainSupplierEntryCollection(viie);
        
		copyNum = colll.size();
        copy = new SupplierInfo[copyNum];
        for(int j=0;j<copyNum;j++){
        	copy[j]=colll.get(j).getMainSupp();
        }
        if(copy!=null)
        	table.getCell(i+curRow, 3).setValue(copy);
	     curRow++;
        
        
        
        //4 �������
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contentDo"), "mainSupp", bindCellMap);
        KDBizPromptBox prmtContent = new KDBizPromptBox();
        //ִ������������ʾΪ"�������"������Ӧ��ʾ"����"
        prmtContent.setDisplayFormat("$changeContent$");
        prmtContent.setEditFormat("$changeContent$");
        prmtContent.setCommitFormat("$changeContent$");
        prmtContent.setRequired(true);
        prmtContent.setQueryInfo("com.kingdee.eas.fdc.contract.app.ChangeAuditEntryQuery");
        prmtContent.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e)
			{
				if(editData.getEntrys().size()<=0){
					//MsgBox.showWarning(EASResource.getString(resourcePath, "SelectCurProj"));
					e.setCanceled(true);
					return;
				}
				if(!hasSaveContentEntry){
					MsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(),"ִ�������Ѿ��ı䣬����֮ǰ���ȱ��棡");
					e.setCanceled(true);
				}
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				if(editData.getId()==null){
					MsgBox.showWarning(ChangeAuditUtil.getRes("registerCheck"));
					SysUtil.abort();
				}
				filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString(),CompareType.EQUALS));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);				
			}
		});
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtContent)); 
        ObjectValueRender renderer = new ObjectValueRender();
        renderer.setFormat(new BizDataFormat("$changeContent$"));
        table.getCell(i+curRow, 3).setRenderer(renderer);
        EntityViewInfo vi = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		FilterItemCollection it = fi.getFilterItems();	
		if(info.getId()!=null)
			it.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
		vi.setFilter(fi);
		vi.getSelector().add("content.*");
		SupplierContentEntryCollection c = SupplierContentEntryFactory.getRemoteInstance().getSupplierContentEntryCollection(vi);
        int entryNum = c.size();
        ChangeAuditEntryInfo[] con = new ChangeAuditEntryInfo[entryNum];
        for(int j=0;j<entryNum;j++){
        	con[j]=c.get(j).getContent();
        }
        if(con!=null)
        	table.getCell(i+curRow, 3).setValue(con);        
        prmtContent.setEnabledMultiSelection(true);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        final int number = 0;
        //�ұ�
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�ұ�", "mainSupp", bindCellMap);
        KDBizPromptBox prmtCurrency = new KDBizPromptBox();
        prmtCurrency.setDisplayFormat("$name$");
        prmtCurrency.setEditFormat("$number$");
        prmtCurrency.setCommitFormat("$number$");
        prmtCurrency.setRequired(true);
        prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtCurrency)); 
        table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
        if(info.getCurrency()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getCurrency());
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //����
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "����", "mainSupp", bindCellMap);  
        KDFormattedTextField rate = new KDFormattedTextField();
        rate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        
    	Date bookedDate = (Date)pkbookedDate.getValue();		
    	ExchangeRateInfo exchangeRate = null;
    	try {
    		if(info.getCurrency()!=null)
    			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, info.getCurrency().getId(),company,bookedDate);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if(info.getCurrency()!=null)
		{
			int exPrecision = info.getCurrency().getPrecision();
			if(exchangeRate!=null){
	    		exPrecision = exchangeRate.getPrecision();
	    	}
	    	
	        rate.setPrecision(exPrecision);
	        table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(
	        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(exPrecision,true));
		}
        
        rate.setMinimumValue(FDCHelper.MIN_VALUE);
        rate.setMaximumValue(FDCHelper.MAX_VALUE);
        rate.setHorizontalAlignment(JTextField.RIGHT);
        rate.setSupportedEmpty(true);
        rate.setVisible(true);
        rate.setEnabled(true);
        rate.setRequired(true);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(rate));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        if(info.getExRate()!=null )
        	table.getCell(i+curRow+number, 3).setValue(info.getExRate());
        //���ɱ༭
        if(info.getCurrency()!=null && this.baseCurrency.getId().toString().equals(info.getCurrency().getId().toString())){
        	table.getRow(i+curRow+number).getStyleAttributes().setLocked(true);
        }
        curRow++;
        
        //ԭ��
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "������ԭ��", "mainSupp", bindCellMap);  
        final KDFormattedTextField ca2 = new KDFormattedTextField();
        ca2.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        int pre = info.getCurrency()!=null?info.getCurrency().getPrecision():2;
        ca2.setPrecision(pre);
        ca2.setMinimumValue(FDCHelper.MIN_VALUE);
        ca2.setMaximumValue(FDCHelper.MAX_VALUE);
        ca2.setHorizontalAlignment(JTextField.RIGHT);
        ca2.setSupportedEmpty(true);
        ca2.setVisible(true);
        ca2.setEnabled(true);
        ca2.setRequired(true);
        
        /**
         *  �ύ״̬�ı��ָ��ڲ�ֺ��ٴ��޸ı�����ʱ����ͻ���ʾ 
         *  ����dataChanged��focusGained�¼�������,  by Cassiel
         */
        
       /* ca2.addDataChangeListener(new DataChangeListener(){
        	boolean amtWarned = false;
        	public void dataChanged(DataChangeEvent eventObj) {
        	}
        });*/
        ca2.addFocusListener(new FocusAdapter(){
        	boolean amtWarned = false;
        	public void focusGained(FocusEvent e) {
        		super.focusGained(e);
        		
    			SelectorItemCollection itemCollection=new SelectorItemCollection();
        		itemCollection.add("suppEntry");
        		itemCollection.add("suppEntry.contractChange.contractBill.isCoseSplit");
        		ChangeAuditBillInfo info=null;
        		Set idSets=new HashSet();
        		try {
        			//���Բ����������ַ���Ҳ����ֱ�ӵ��� checkBeforeRemoveOrEdit()
        			info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()),itemCollection);
        			FilterInfo filtertemp = new FilterInfo();
        			for(int i=0;i<info.getSuppEntry().size();i++){
        				ContractChangeBillInfo entryInfo = info.getSuppEntry().get(i).getContractChange();
        				if(entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()){
        					filtertemp.getFilterItems().add(
            						new FilterItemInfo("parent.contractChange.id", idSets ,CompareType.INCLUDE));
            				// ���ϵ��ݲ���
            				filtertemp.getFilterItems().add(
            						new FilterItemInfo("parent.state",
            								FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
            				idSets.add(entryInfo.getId());
            				boolean existsSplitBill=ConChangeSplitEntryFactory.getRemoteInstance().exists(filtertemp);
            				if(existsSplitBill&&STATUS_EDIT.equals(getOprtState())&&!amtWarned){
            					MsgBox.showWarning("�˱���Ѿ���֣��޸ĺ���������ֵĺϼƽ�һ�½������ּ���صĽ����֡������֣�");
            					amtWarned=true;
            				}
        				}
        				if(entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()){//�ǳɱ�����
        					filtertemp.getFilterItems().add(
            						new FilterItemInfo("parent.contractChange.id", idSets ,CompareType.INCLUDE));
            				// ���ϵ��ݲ���
            				filtertemp.getFilterItems().add(
            						new FilterItemInfo("parent.state",
            								FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
            				idSets.add(entryInfo.getId());
            				boolean existsSplitBill=ConChangeNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
            				if(existsSplitBill&&STATUS_EDIT.equals(getOprtState())&&!amtWarned){
            					MsgBox.showWarning("�˱���Ѿ���֣��޸ĺ���������ֵĺϼƽ�һ�½������ּ���صĽ����֡������֣�");
            					amtWarned=true;
            				}
        				}
        			}
        		} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
        });
        
        
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(ca2));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        
        table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(
        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(pre,true));
        if(info.getOriCostAmount()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getOriCostAmount());
        
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //��λ��
   
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("costAmt"), "mainSupp", bindCellMap);  
        KDFormattedTextField ca = new KDFormattedTextField();
        ca.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        
        ca.setPrecision(2);
        ca.setMinimumValue(FDCHelper.MIN_VALUE);
        ca.setMaximumValue(FDCHelper.MAX_VALUE);
        ca.setHorizontalAlignment(JTextField.RIGHT);
        ca.setSupportedEmpty(true);
        ca.setVisible(true);
        ca.setEnabled(true);
        ca.setRequired(true);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(ca));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        if(info.getCostAmount()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getCostAmount());
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        
        table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(
        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(pre,true));
        //���ɱ༭
        if(info.getCurrency()!=null &&  this.baseCurrency.getId().toString().equals(info.getCurrency().getId().toString())){
        	table.getRow(i+curRow+number).getStyleAttributes().setLocked(true);
        }
        curRow++;
        
      //�Ƿ����οۿ�
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�Ƿ�ȷ��������", "mainSupp", bindCellMap);       
        table.getCell(i+curRow+number, 3).setValue(Boolean.valueOf(info.isIsSureChangeAmt()));
        //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow++;
        
        //����˵��
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("description"), "mainSupp", bindCellMap);   
        //R110509-0581����ͬ�������������˵���ֶ�̫С  by zhiyuan_tang 2010-05-13
        KDDetailedArea area = new KDDetailedArea(200, 150);
        area.setRequired(false);
        area.setEnabled(true);
        area.setMaxLength(500);
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(area));
		if(info.getCostDescription()!=null)
			table.getCell(i+curRow+number, 3).setValue(info.getCostDescription());       
		curRow++;
		
		//ʩ����������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "ʩ����������", "mainSupp", bindCellMap);       
        final KDFormattedTextField kdf = new KDFormattedTextField();
        kdf.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdf.setPrecision(2);
        kdf.setMinimumValue(FDCHelper.MIN_VALUE);
        kdf.setMaximumValue(FDCHelper.MAX_VALUE);
        kdf.setHorizontalAlignment(JTextField.RIGHT);
        kdf.setSupportedEmpty(true);
        kdf.setRequired(true);
        kdf.setVisible(true);
        kdf.setEnabled(true);
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(kdf));
		table.getRow(i+curRow+number).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat("##,###.00");
		if(info.getConstructPrice()!=null)
			table.getCell(i+curRow+number, 3).setValue(info.getConstructPrice());
		 //���ص� xin_wang 2011.09.01
//        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow++;
		
		//�Ƿ����οۿ�
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("isDeduct"), "mainSupp", bindCellMap);       
        table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
        table.getCell(i+curRow+number, 3).setValue(Boolean.valueOf(info.isIsDeduct()));
      //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow++;
        
        //ԭ��
        final KDFormattedTextField kdc2 = new KDFormattedTextField();
        kdc2.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc2.setPrecision(2);
        kdc2.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc2.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc2.setHorizontalAlignment(JTextField.RIGHT);
        kdc2.setSupportedEmpty(true);
        kdc2.setVisible(true);
        kdc2.setEnabled(true);
        kdc2.setRequired(true);       

		final ICell cell82 = table.getCell(i+curRow+number, 3);
        if(info.isIsDeduct()){
        	kdc2.setEnabled(true);
			kdc2.setEditable(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(false);
			cell82.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        }else{
        	kdc2.setEnabled(false);
        	kdc2.setEditable(false);
        	table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);		
        }
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "���οۿ���ԭ��", "mainSupp", bindCellMap);               
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(kdc2));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        if(info.getOriDeductAmount()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getOriDeductAmount());
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow++;
        
        //���οۿ���
        final KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(JTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        kdc.setRequired(true);       

		final ICell cell8 = table.getCell(i+curRow+number, 3);
        if(info.isIsDeduct()){
        	kdc.setEnabled(true);
			kdc.setEditable(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(false);
			cell8.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        }else{
        	kdc.setEnabled(false);
        	kdc.setEditable(false);
        	table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);		
        }
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("deductAmt"), "mainSupp", bindCellMap);               
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(kdc));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        if(info.getDeductAmount()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getDeductAmount().setScale(2));
        //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow++;

		//�Ƿ����οۿλ
        final KDTextField matmdtf=new KDTextField();
		matmdtf.setRequired(true);
		matmdtf.setEnabled(true);
		matmdtf.setMaxLength(80);
		final ICell cell9 = table.getCell(i+curRow+number, 3);
        if(info.isIsDeduct()){
			matmdtf.setEnabled(true);
			matmdtf.setEditable(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(false);
			cell9.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        }else{
        	matmdtf.setEditable(false);
        	matmdtf.setEnabled(false);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
			table.getCell(i+curRow+number, 3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);

        }        
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("deductReason"), "mainSupp", bindCellMap);       
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(matmdtf));
		if(info.getDeductReason()!=null)
			table.getCell(i+curRow+number, 3).setValue(info.getDeductReason());
		//���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow++;
        
		//���㷽ʽ
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("banlanceType"), "mainSupp", bindCellMap);              
        KDTextField btype=new KDTextField();
        btype.setRequired(true);
        btype.setEnabled(true);
        btype.setMaxLength(80);
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(btype));
		if(info.getBalanceType()!=null)
			table.getCell(i+curRow+number, 3).setValue(info.getBalanceType());
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow++;
		
		//������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("reckoner"), "mainSupp", bindCellMap);
        KDBizPromptBox prmtReckonor = new KDBizPromptBox();
        prmtReckonor.setDisplayFormat("$name$");
        prmtReckonor.setEditFormat("$name$");
        prmtReckonor.setCommitFormat("$name$");
//        prmtReckonor.setRequired(true);
        prmtReckonor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
//        prmtReckonor.getQueryAgent().getRuntimeEntityView().getFilter().mergeFilter(getReckonorF7Filter(), "and");
        
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(prmtReckonor));
        if(info.getReckonor()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getReckonor());
        ObjectValueRender reck = new ObjectValueRender();
        reck.setFormat(new BizDataFormat("$name$"));
        table.getCell(i+curRow+number, 3).setRenderer(reck);
//        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //���β���
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("dutySupp"), "mainSupp", bindCellMap);
        
        KDBizPromptBox prmtDutySupp = new KDBizPromptBox();
        prmtDutySupp.setDisplayFormat("$name$");
        prmtDutySupp.setEditFormat("$number$");
        prmtDutySupp.setCommitFormat("$number$");
        prmtDutySupp.setRequired(false);
        prmtDutySupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        EntityViewInfo view = FDCClientUtils.getApprovedSupplierView();
        prmtDutySupp.setEntityViewInfo(view);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(prmtDutySupp));
        if(info.getDutyOrg()!=null)
        	table.getCell(i+curRow+number, 3).setValue(info.getDutyOrg());
        curRow++;
        
        
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ�ƿ���ʱ��", "mainSupp", bindCellMap);               
        String formatString = "yyyy-MM-dd";
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		table.getCell(i+curRow+number, 3).setValue(info.getStartDate());
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ���깤ʱ��", "mainSupp", bindCellMap);               
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		table.getCell(i+curRow+number, 3).setValue(info.getEndDate());
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ���շ�ʱ��", "mainSupp", bindCellMap);               
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		table.getCell(i+curRow+number, 3).setValue(info.getSfDate());
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�Ƿ�ΪӪ������", "mainSupp", bindCellMap);   
		
		KDTextField isMarket=new KDTextField();
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(isMarket));
		table.getCell(i+curRow+number, 3).setValue(info.getIsMarket());
        
        /*
		 * �ںϱ��
		 */
		KDTMergeManager mm = getSecondTable().getMergeManager();
		
		// �ں� 4->5 5->6
		mm.mergeBlock(i, 0, i+curRow+number, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(i, 1, i+5+number, 1, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(i+6+number, 1, i+curRow+number, 1, KDTMergeManager.SPECIFY_MERGE);
		
		String str =// ChangeAuditUtil.getRes("supp")+
		ChinaNumberFormat.getChinaNumberValue(i/(suppRows+number)+1);
		table.getCell(i, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
        table.getCell(i, 0).setValue(str);
		String test=ChangeAuditUtil.getRes("subjectOne");
		String te=ChangeAuditUtil.getRes("subjectTwo");
		setNode(table, i, 6+number, test);
		setNode(table, i+6+number, 14, te);
		getSecondTable().getColumn(0).getStyleAttributes().setLocked(true);
		getSecondTable().getColumn(2).getStyleAttributes().setLocked(true);	
        //afterAddLine(table, detailData);
		
        FDCClientUtils.setRespDeptF7(prmtDutySupp, this, canSelectOtherOrgPerson?null:editData.getCU().getId().toString());
	}
	
	public void onShow() throws Exception {
		super.onShow();
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		}else{
			txtName.requestFocusInWindow();
		}
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionPageSetup.setVisible(false);
		btnPrint.setEnabled(true);
		btnPrintPreview.setEnabled(true);
		actionPrint.setVisible(true);
		actionPrintPreview.setVisible(true);
		actionCreateFrom.setVisible(false);
		actionTraceDown.setVisible(false);
		actionTraceUp.setVisible(false);
		actionCopyFrom.setVisible(false);
		actionCopy.setVisible(false);
		menuTable1.setVisible(false);
		txtNoUse.setPrecision(2);
		txtNoUse.setRemoveingZeroInDispaly(false);
		txtNoUse.setSupportedEmpty(true);
		txtNoUse.setMinimumValue(FDCHelper.MIN_VALUE);
		txtNoUse.setMaximumValue(FDCHelper.MAX_VALUE);
		txtNoUse.setHorizontalAlignment(JTextField.RIGHT);
		
		txtAmountA.setPrecision(2);
		txtAmountA.setRemoveingZeroInDispaly(false);
		txtAmountA.setSupportedEmpty(true);
		txtAmountA.setMinimumValue(FDCHelper.MIN_VALUE);
		txtAmountA.setMaximumValue(FDCHelper.MAX_VALUE);
		txtAmountA.setHorizontalAlignment(JTextField.RIGHT);
		
		txtDutyAmount.setPrecision(2);
		txtDutyAmount.setRemoveingZeroInDispaly(false);
		txtDutyAmount.setSupportedEmpty(true);
		txtDutyAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtDutyAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		txtDutyAmount.setHorizontalAlignment(JTextField.RIGHT);
		
		txtTotalCost.setPrecision(2);
		txtTotalCost.setRemoveingZeroInDispaly(false);
		txtTotalCost.setSupportedEmpty(true);
		txtTotalCost.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtTotalCost.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtTotalCost.setEnabled(false);
		txtTotalCost.setHorizontalAlignment(JTextField.RIGHT);
		
		txtTotalConstructPrice.setPrecision(2);
		txtTotalConstructPrice.setRemoveingZeroInDispaly(false);
		txtTotalConstructPrice.setSupportedEmpty(true);
		txtTotalConstructPrice.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtTotalConstructPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtTotalConstructPrice.setEnabled(false);
		txtTotalConstructPrice.setHorizontalAlignment(JTextField.RIGHT);
		
		if((editData.getChangeState()!=null)&&(editData.getChangeState().equals(ChangeBillStateEnum.AheadDisPatch))){
			contAheadDisPatch.setVisible(true);
		}else{
			contAheadDisPatch.setVisible(false);
		}
		actionRegister.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_makeknown"));
		actionDisPatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		actionDisPatch.setVisible(false);
		setOprtState(getOprtState());
	}

	private void initUI() throws Exception {
		// ��¼����ɾ��ť��������¼�Ϸ�
		JButton btnAddRuleNew = ctnEntrys.add(actionAddLine);
		JButton btnDelRuleNew = ctnEntrys.add(actionRemoveLine);
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddRuleNew.setToolTipText(getRes("addRule"));
		btnAddRuleNew.setText(getRes("addRule"));
		btnAddRuleNew.setSize(22, 19);
		btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelRuleNew.setToolTipText(getRes("delRule"));
		btnDelRuleNew.setText(getRes("delRule"));
		btnDelRuleNew.setSize(22, 19);
		
		JButton btnAddSuppNew = ctnSuppEntrys.add(actionAddLine);
		JButton btnDelSuppNew = ctnSuppEntrys.add(actionRemoveLine);
		btnAddSuppNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddSuppNew.setToolTipText(getRes("addSupp"));
		btnAddSuppNew.setText(getRes("addSupp"));
		btnAddSuppNew.setSize(22, 19);
		btnDelSuppNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelSuppNew.setToolTipText(getRes("delSupp"));
		btnDelSuppNew.setText(getRes("delSupp"));
		btnDelSuppNew.setSize(22, 19);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isEnabled", new Integer(1)));
		filterItems.add(new FilterItemInfo("isLeaf", new Integer(1)));
		
		view.setFilter(filter);
		bmptResaon.setEntityViewInfo(view);
		this.menuSubmitOption.setVisible(false);		
	}

	/**
	 * ���ñ��ĳ�в���KDDetailedArea��Ϊ�༭��
	 * 
	 * @param table
	 * @param colName
	 *            ����
	 * @param isRequired
	 *            �Ƿ��¼
	 * @param maxLength
	 *            �����¼�볤��
	 * @author owen_wen 2011-008-03
	 */
	private void setDetailCellEditor(KDTable table, String colName, boolean isRequired, int maxLength) {
		KDDetailedArea area = new KDDetailedArea(280, 250);
		area.setRequired(isRequired);
		area.setEnabled(true);
		area.setMaxLength(maxLength);
		table.getColumn(colName).setEditor(new KDTDefaultCellEditor(area));
	}
	
	public static String getRes(String resName) {
		return EASResource.getString("com.kingdee.eas.fdc.contract.client.ChangeAuditResource", resName);
	}
	
	protected KDTable getSecondTable(){
		return kdtSuppEntry;
	}

	public void actionAddSupp_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAddSupp();
		if(getSecondTable() != null)
        {
            addSupp(getSecondTable());
        }
	}
	
	/**
	 * ���ڰ�cell����ֵ������map keyΪ���Ե�info��������valueΪcell������
	 */
	private HashMap bindCellMap = new HashMap(24);
	
	public static int suppRows = 24;
	/**
     * ��ָ������������У����������һ�У�
     *
     * @param table
	 * @throws BOSException 
     */
    protected void addSupp(final KDTable table) throws BOSException
    {
        if(table == null)
        {
            return;
        }
		if(!hasSaveContentEntry){
			MsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(),"ִ�������Ѿ��ı䣬����֮ǰ���ȱ��棡");
			return;
		}
		
		//һ����¼������
        int curRow = 0;
		
        IObjectValue detailData = createNewSuppData(table);
        final int i=table.getRowCount();
        if(i==24){
        	FDCMsgBox.showWarning(this,"ֻ������һ���·���λ��");
        	return;
        }
        table.addRows(suppRows);
//        for (int j = 0; j < 6; j++) {
//        	if(j==3){
//        		continue ;
//        	}
//        	table.getRow(i+j).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//		}
                
        //0��ͬ
        final KDBizPromptBox prmtMainSupp = new KDBizPromptBox();
        prmtMainSupp.setDisplayFormat("$name$");
        prmtMainSupp.setEditFormat("$number$");
        prmtMainSupp.setCommitFormat("$number$");
        prmtMainSupp.setRequired(true);
        prmtMainSupp.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");
        prmtMainSupp.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                	prmtMainSupp_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        prmtMainSupp.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e)
			{
				if(editData.getCurProject()==null){
					//MsgBox.showWarning(EASResource.getString(resourcePath, "SelectCurProj"));
					e.setCanceled(true);
					return;
				}
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("curProject.id",editData.getCurProject().getId().toString(),CompareType.EQUALS));
				//�ų������ύ״̬
//				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
//				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);				
			}
		});
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("partB.number"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		
		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));		
		sic.add(new SelectorItemInfo("currency.precision"));	
		sic.add(new SelectorItemInfo("isCoseSplit"));	
		sic.add(new SelectorItemInfo("hasSettled"));	
		sic.add(new SelectorItemInfo("currency.precision"));	
		prmtMainSupp.setSelectorCollection(sic);
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtMainSupp));
        ObjectValueRender rend = new ObjectValueRender();
        rend.setFormat(new BizDataFormat("$number$"));
        table.getCell(i+curRow, 3).setRenderer(rend);
        
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contractNum"), "mainSupp", bindCellMap);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //1��ͬ����
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contractName"), "mainSupp", bindCellMap);
        table.getCell(i+curRow, 3).getStyleAttributes().setLocked(true);
        curRow++;
        
        //2���͵�λ
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("mainSupp"), "mainSupp", bindCellMap);
        table.getCell(i+curRow, 3).getStyleAttributes().setLocked(true);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow++;
        
        //3���͵�λ;
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("copySupp"), "mainSupp", bindCellMap);       
        KDBizPromptBox prmtCopySupp = new KDBizPromptBox();
        prmtCopySupp.setDisplayFormat("$name$");
        prmtCopySupp.setEditFormat("$number$");
        prmtCopySupp.setCommitFormat("$number$");
        prmtCopySupp.setRequired(false);
        prmtCopySupp.setEnabledMultiSelection(true);
        prmtCopySupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        EntityViewInfo v = FDCClientUtils.getApprovedSupplierView();
        prmtCopySupp.setEntityViewInfo(v);
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtCopySupp));
        ObjectValueRender ren = new ObjectValueRender();
        ren.setFormat(new BizDataFormat("$name$"));
        table.getCell(i+curRow, 3).setRenderer(ren);
        //prmtCopySupp.setEnabledMultiSelection(true);
        curRow++;
        //����һ��ԭʼ��ϵ���� eric_wang 2010.05.30
//        ChangeAuditUtil.bindCell(table, i+curRow, 2, "ԭʼ��ϵ����","mainSupp",bindCellMap);
//        KDTextField originalContactNum = new KDTextField();
//        originalContactNum.setMaxLength(80);
//        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(originalContactNum));
//        table.getCell(i+curRow, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//        getSecondTable().getRow(i+curRow).getStyleAttributes().setHided(true);
//        curRow++;
        
        //���ο��������ܳа��� 2011.9.02
       ChangeAuditUtil.bindCell(table, i+curRow, 2, "�ܳа���","mainSupp",bindCellMap);
       table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtCopySupp));
       table.getCell(i+curRow, 3).setRenderer(ren);
       curRow++;
        
        //4ִ������
        ChangeAuditUtil.bindCell(table, i+curRow, 2, ChangeAuditUtil.getRes("contentDo"), "mainSupp", bindCellMap);       
        KDBizPromptBox prmtContent = new KDBizPromptBox();
        prmtContent.setDisplayFormat("$changeContent$");
        prmtContent.setEditFormat("$changeContent$");
        prmtContent.setCommitFormat("$changeContent$");
        prmtContent.setRequired(true);
        prmtContent.setQueryInfo("com.kingdee.eas.fdc.contract.app.ChangeAuditEntryQuery");
        
		if(editData.getId()!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString(),CompareType.EQUALS));
			view.setFilter(filter);
			prmtContent.setEntityViewInfo(view);	
		}	
		
        prmtContent.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e)
			{
				if(editData.getEntrys().size()<=0){
					//MsgBox.showWarning(EASResource.getString(resourcePath, "SelectCurProj"));
					e.setCanceled(true);
					return;
				}
				if(!hasSaveContentEntry){
					MsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(),"ִ�������Ѿ��ı䣬����֮ǰ���ȱ��棡");
					e.setCanceled(true);
				}
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				if(editData.getId()==null){
					MsgBox.showWarning(ChangeAuditUtil.getRes("registerCheck"));
					SysUtil.abort();
				}
				filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString(),CompareType.EQUALS));

				view.setFilter(filter);
				f7.setEntityViewInfo(view);				
			}
		});       
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtContent)); 
        ObjectValueRender renderer = new ObjectValueRender();
        renderer.setFormat(new BizDataFormat("$changeContent$"));
        table.getCell(i+curRow, 3).setRenderer(renderer);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        prmtContent.setEnabledMultiSelection(true);
        curRow++;
        
        final int number = 0;      

        //5�ұ�
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�ұ�", "mainSupp", bindCellMap);
        KDBizPromptBox prmtCurrency = new KDBizPromptBox();
        prmtCurrency.setDisplayFormat("$name$");
        prmtCurrency.setEditFormat("$number$");
        prmtCurrency.setCommitFormat("$number$");
        prmtCurrency.setRequired(true);
        prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        table.getCell(i+curRow, 3).setEditor(new KDTDefaultCellEditor(prmtCurrency)); 
        table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow ++;
        
        //6����
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "����", "mainSupp", bindCellMap);
        KDFormattedTextField rate = new KDFormattedTextField();
        rate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        rate.setPrecision(2);
        rate.setMinimumValue(FDCHelper.MIN_VALUE);
        rate.setMaximumValue(FDCHelper.MAX_VALUE);
        rate.setHorizontalAlignment(JTextField.RIGHT);
        rate.setSupportedEmpty(true);
        rate.setVisible(true);
        rate.setEnabled(true);
        rate.setRequired(true);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(rate));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        curRow ++;
        
        //7ԭ��
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "������ԭ��", "mainSupp", bindCellMap);
        KDFormattedTextField oriCost = new KDFormattedTextField();
        oriCost.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        oriCost.setMinimumValue(FDCHelper.MIN_VALUE);
        oriCost.setMaximumValue(FDCHelper.MAX_VALUE);
        oriCost.setHorizontalAlignment(JTextField.RIGHT);
        oriCost.setSupportedEmpty(true);
        oriCost.setVisible(true);
        oriCost.setEnabled(true);
        oriCost.setRequired(true);
//        oriCost.addDataChangeListener(new DataChangeListener(){
//
//			public void dataChanged(DataChangeEvent eventObj) {
//				// TODO �Զ����ɷ������
//				
//				//setAmount();
//			}
//        	
//        });
//        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(oriCost));
//        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(oriCost));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(
        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(2,true));
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        
        curRow ++;
        
        //6..8��λ��        
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("costAmt"), "mainSupp", bindCellMap);       
        KDFormattedTextField ca = new KDFormattedTextField();
        ca.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        ca.setPrecision(2);
        ca.setMinimumValue(FDCHelper.MIN_VALUE);
        ca.setMaximumValue(FDCHelper.MAX_VALUE);
        ca.setHorizontalAlignment(JTextField.RIGHT);
        ca.setSupportedEmpty(true);
        ca.setVisible(true);
        ca.setEnabled(true);
        ca.setRequired(true);
//      ca.addDataChangeListener(new DataChangeListener(){
//
//			public void dataChanged(DataChangeEvent eventObj) {
//				// TODO �Զ����ɷ������
//				BigDecimal newValue = FDCHelper.toBigDecimal(eventObj.getNewValue());
//				BigDecimal oldValue = FDCHelper.toBigDecimal(eventObj.getOldValue());
//				BigDecimal amount = FDCHelper.toBigDecimal(editData.getTotalCost());
//				
//				editData.setTotalCost(amount.subtract(oldValue).add(newValue));
//				txtTotalCost.setValue(editData.getTotalCost());
//			}
//        	
//        });
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(ca));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(
        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(2,true));
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        curRow ++;
        
        // �Ƿ�ȷ��������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�Ƿ�ȷ��������", "mainSupp", bindCellMap); 
        table.getCell(i+curRow+number, 3).setValue(Boolean.FALSE);
       //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow ++;
        
        //7..9����˵��
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("description"), "mainSupp", bindCellMap); 
        //R110509-0581����ͬ�������������˵���ֶ�̫С  by zhiyuan_tang 2010-05-13
        KDDetailedArea area = new KDDetailedArea(200, 150);
        area.setRequired(false);
        area.setEnabled(true);
        area.setMaxLength(500);
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(area));
		curRow ++;
		
		 //ʩ����������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "ʩ����������", "mainSupp", bindCellMap);       
        final KDFormattedTextField kdf = new KDFormattedTextField();
        kdf.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdf.setPrecision(2);
        kdf.setMinimumValue(FDCHelper.ZERO);
        kdf.setMaximumValue(FDCHelper.MAX_VALUE);
        kdf.setHorizontalAlignment(JTextField.RIGHT);
        kdf.setSupportedEmpty(true);
        kdf.setVisible(true);
        kdf.setEnabled(true);
        kdf.setRequired(true);    
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(kdf));
		table.getRow(i+curRow+number).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat("##,###.00");
		table.getCell(i+curRow+number, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //���ص� xin_wang 2011.09.01
//        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow++;
		
		//10�Ƿ����οۿλ
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("isDeduct"), "mainSupp", bindCellMap);       
//        final KDCheckBox cbIsDeduct = new KDCheckBox();
		table.getCell(i+curRow+number, 3).setValue(Boolean.FALSE);
        table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
      //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow ++;
        		
		//8���οۿ���ԭ��
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "���οۿ���ԭ��", "mainSupp", bindCellMap);
        KDFormattedTextField oriDeduct = new KDFormattedTextField();
        oriDeduct.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        oriDeduct.setPrecision(2);
        oriDeduct.setMinimumValue(FDCHelper.MIN_VALUE);
        oriDeduct.setMaximumValue(FDCHelper.MAX_VALUE);
        oriDeduct.setHorizontalAlignment(JTextField.RIGHT);
        oriDeduct.setSupportedEmpty(true);
        oriDeduct.setVisible(true);
        oriDeduct.setEnabled(true);
        oriDeduct.setRequired(true);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(oriDeduct));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setBackground(new Color(0xE8E8E3));
        table.getCell(i+curRow+number, 3).getStyleAttributes().setLocked(true);
        //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow++;
        
        //9���οۿ���
		final ICell cell8 = table.getCell(i+curRow+number, 3);
		cell8.getStyleAttributes().setLocked(true);
		cell8.getStyleAttributes().setBackground(new Color(0xE8E8E3));

        final KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(JTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEditable(false);
        kdc.setEnabled(false);
        kdc.setRequired(true);
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("deductAmt"), "mainSupp", bindCellMap);               
        cell8.setEditor(new KDTDefaultCellEditor(kdc));
        cell8.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        //���ص� xin_wang 2011.09.01
        getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
        curRow ++;
        
		//10�ۿ�ԭ��
		final ICell cell9 = table.getCell(i+curRow+number, 3);
		cell9.getStyleAttributes().setLocked(true);
		cell9.getStyleAttributes().setBackground(new Color(0xE8E8E3));
       
        final KDTextField matmdtf=new KDTextField();
		matmdtf.setRequired(true);
		matmdtf.setEnabled(false);
		matmdtf.setEditable(false);
		matmdtf.setMaxLength(80);
        
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("deductReason"), "mainSupp", bindCellMap);
		cell9.setEditor(new KDTDefaultCellEditor(matmdtf));
		
		//���ص� xin_wang 2011.09.01
		getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow ++;
		
		//11���㷽ʽ
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("banlanceType"), "mainSupp", bindCellMap);               
        KDTextField btype=new KDTextField();
        btype.setRequired(true);
        btype.setEnabled(true);
        btype.setMaxLength(80);
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(btype));
		getSecondTable().getRow(i+curRow+number).getStyleAttributes().setHided(true);
		curRow ++;
		 
		//12������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("reckoner"), "mainSupp", bindCellMap);
        KDBizPromptBox prmtReckonor = new KDBizPromptBox();
        prmtReckonor.setDisplayFormat("$name$");
        prmtReckonor.setEditFormat("$name$");
        prmtReckonor.setCommitFormat("$name$");
        prmtReckonor.setRequired(true);
        prmtReckonor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
//        prmtReckonor.getQueryAgent().getRuntimeEntityView().getFilter().mergeFilter(getReckonorF7Filter(), "and");
        
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(prmtReckonor));
        //      �����ˣ�Ĭ�ϵ�ǰ�û���liupd��2007-11
        table.getCell(i+curRow+number, 3).setValue(SysContext.getSysContext().getCurrentUserInfo()); 
        
        ObjectValueRender reck = new ObjectValueRender();
        reck.setFormat(new BizDataFormat("$name$"));
        table.getCell(i+curRow+number, 3).setRenderer(reck);
        curRow ++;
        table.getRow(i+curRow).getCell(3).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        
        //13���ι�������
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, ChangeAuditUtil.getRes("dutySupp"), "mainSupp", bindCellMap);
        KDBizPromptBox prmtDutySupp = new KDBizPromptBox();
        prmtDutySupp.setDisplayFormat("$name$");
        prmtDutySupp.setEditFormat("$number$");
        prmtDutySupp.setCommitFormat("$number$");
        prmtDutySupp.setRequired(false);
//        prmtDutySupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        
        FDCClientUtils.setRespDeptF7(prmtDutySupp, this, canSelectOtherOrgPerson?null:editData.getCU().getId().toString());
        
        EntityViewInfo view = FDCClientUtils.getApprovedSupplierView();
        prmtDutySupp.setEntityViewInfo(view);
        table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(prmtDutySupp));
        //���ι������ţ�Ĭ��Ϊ��Ŀ������ѡ��Ŀ���ڲ���ʵ���������ơ�Ӫ�����ɹ����ɱ��Ȳ���
        curRow ++;
        
        ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ�ƿ���ʱ��", "mainSupp", bindCellMap);               
        String formatString = "yyyy-MM-dd";
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ���깤ʱ��", "mainSupp", bindCellMap);               
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "Ԥ���շ�ʱ��", "mainSupp", bindCellMap);               
		table.getCell(i+curRow+number, 3).setEditor(dateEditor);
		table.getCell(i+curRow+number, 3).getStyleAttributes().setNumberFormat(formatString);
		curRow ++;
		
		ChangeAuditUtil.bindCell(table, i+curRow+number, 2, "�Ƿ�ΪӪ������", "mainSupp", bindCellMap);   
		
		KDTextField isMarket=new KDTextField();
		table.getCell(i+curRow+number, 3).setEditor(new KDTDefaultCellEditor(isMarket));
        /*
		 * �ںϱ��
		 */
		KDTMergeManager mm = getSecondTable().getMergeManager();
//		
//		// �ں� 4->5 5->6 eric_wang
		mm.mergeBlock(i, 0, i+curRow+number, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(i, 1, i+5+number, 1, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(i+6+number, 1, i+curRow+number, 1, KDTMergeManager.SPECIFY_MERGE);
//		
		String str =//  ChangeAuditUtil.getRes("supp")+
		ChinaNumberFormat.getChinaNumberValue(i/(suppRows+number)+1);
        table.getCell(i, 0).setValue(str);
		String test = ChangeAuditUtil.getRes("subjectOne"); // �Ǽ���Ϣ
		String te = ChangeAuditUtil.getRes("subjectTwo");// ������Ϣ
		setNode(table, i, 6+number, test);
		setNode(table, i+6+number, suppRows-6, te);
		getSecondTable().getColumn(0).getStyleAttributes().setLocked(true);
		getSecondTable().getColumn(2).getStyleAttributes().setLocked(true);	
        //afterAddLine(table, detailData);
		ChangeAuditUtil.getValueFromCell(detailData, bindCellMap);
		setSuppNum(suppRows);	
		
    }

    /**
     * ��ȡ������F7��������
     * @author owen_wen 2010-09-21
     * @return ������F7��������
     * @throws BOSException
     */
	private FilterInfo getReckonorF7Filter() throws BOSException {
		// ȡ��ǰ��¼��֯�Լ����¼���֯������id�ŵ�orgIDSet��
        EntityViewInfo orgView = new EntityViewInfo();
        FilterInfo orgFilter = new FilterInfo();
        orgFilter.getFilterItems().add(new FilterItemInfo("LongNumber", SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%", 
        		CompareType.LIKE));
        orgView.setFilter(orgFilter);
        FullOrgUnitCollection fullOrgUnitCol = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(orgView);
        Set orgIDSet = new HashSet();
        for (int j = 0, size = fullOrgUnitCol.size(); j < size; j++){
        	orgIDSet.add(fullOrgUnitCol.get(j).getId().toString());
        }
        
        // ȡ������¼��֯�Լ����¼���֯������֯��Χ��T_PM_OrgRange��������Щ��֯���û�id�ŵ�userIDSet�У���f7Filter����
        EntityViewInfo orgRangeView = new EntityViewInfo();
        FilterInfo orgRangeFilter = new FilterInfo();
        orgRangeFilter.getFilterItems().add(new FilterItemInfo("org", orgIDSet, CompareType.INCLUDE));
        orgRangeView.setFilter(orgRangeFilter);
        OrgRangeCollection orgRangeCol = OrgRangeFactory.getRemoteInstance().getOrgRangeCollection(orgRangeView);
        Set userIDSet = new HashSet();        
        for (int j = 0, size = orgRangeCol.size(); j < size; j++){
        	userIDSet.add(orgRangeCol.get(j).getUser().getId().toString());
        }
        
        FilterInfo f7Filter = new FilterInfo();
        f7Filter.getFilterItems().add(new FilterItemInfo("id",userIDSet,CompareType.INCLUDE));
		return f7Filter;
	}
	 
	
    private void setAmount(){
    	BigDecimal amount = FDCHelper.ZERO;
		int i = getSecondTable().getRowCount();
		for(int j=0;j<i;j++){
			//�����λ���ۼ�
			if(j%suppRows==ROW_costAmt){
				Object contentCA = getSecondTable().getCell(j, "content").getValue();
				if(contentCA != null){
					amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
				}
			}
		}
		editData.setTotalCost(amount);
		txtTotalCost.setValue(amount);
		
		amount = FDCHelper.ZERO;
		i = getSecondTable().getRowCount();
		for(int j=0;j<i;j++){
			//�����λ���ۼ�
			if(j%suppRows==ROW_constPirce){
				Object contentCA = getSecondTable().getCell(j, "content").getValue();
				if(contentCA != null){
					amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
				}
			}
		}
		editData.setTotalConstructPrice(amount);
		txtTotalConstructPrice.setValue(amount);
    }
    
    private void setSuppNum(int suppRows){
    	int i = getSecondTable().getRowCount();
    	ctnSuppEntrys.setTitle(ChangeAuditUtil.getRes("suppCount")+i/suppRows);
    }
    
    private void prmtMainSupp_dataChanged(DataChangeEvent e) throws EASBizException, BOSException {
//    	if(e.getNewValue()!=null){
//			ContractBillInfo info = (ContractBillInfo)e.getNewValue();
//			String test = info.getPartB().getId().toString();
//			SupplierInfo te = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(test));
//			e.setNewValue(te);
//    	}
	}

	private void setNode(KDTable table, int i, final int count, String value) {
		CellTreeNode node=new CellTreeNode();
		
		// ��������_��ԭ��Ŀ��R110411-644(���㵥)
		// R110411-643����ͬ�޶���R110112-212���������R110509-0581����
		// Ҫ������ʱĬ��չ���·���λ������ע�����¼��У�Added by Owen_wen 2011-06-08
		
		// node.setCollapse(true);
		// for(int k=i+1;k<i+count;k++){
		// getSecondTable().getRow(k).getStyleAttributes().setHided(true);
		// }
		
		node.addClickListener(new NodeClickListener(){			
			public void doClick(CellTreeNode source, ICell cell, int type) {
				if(source.isCollapse()){
					for(int k=cell.getRowIndex()+1;k<cell.getRowIndex()+count;k++){
						getSecondTable().getRow(k).getStyleAttributes().setHided(true);
					}
				}else{
					for(int k=cell.getRowIndex()+1;k<cell.getRowIndex()+count;k++){
						if(k%suppRows!=16 && k%suppRows!=13){
							//���ص�10�еĽ��㷽ʽ by sxhong
							getSecondTable().getRow(k).getStyleAttributes().setHided(false);
						}
					}
				}				
			}			
		});		
		node.setHasChildren(true);
		node.setTreeLevel(0);
		node.setValue(value);
		table.getCell(i, 1).setValue(node);
		//return node;
	}
    
    /**
     * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
     */
    protected IObjectValue createNewSuppData(KDTable table){
    	return new ChangeSupplierEntryInfo();
    }
    
    /**
     * ��ʾ������
     */
    protected void loadSuppFields(KDTable table, IRow row, IObjectValue obj)
    {
        dataBinder.loadLineFields(table, row, obj);
    }

	public void actionDelSupp_actionPerformed(ActionEvent e) throws Exception {		
		super.actionDelSupp_actionPerformed(e);
		if(getSecondTable() != null)
        {
            removeSupp(getSecondTable());
        }
		if(getSecondTable() != null){
			int i=getSecondTable().getRowCount();
			for(int j=0; j<i;j++){
				if(j%suppRows==0){
					String str = ChinaNumberFormat.getChinaNumberValue(j/suppRows+1);
					getSecondTable().getCell(j, 0).setValue(str);
				}
			}
		}
	}
	
	 /**
     * ��ָ�������ɾ����ǰѡ����
     *
     * @param table
     */
    protected void removeSupp(KDTable table)
    {
        if(table == null)
        {
            return;
        } 

        if ((table.getSelectManager().size() == 0)
                || isTableColumnSelected(table))
        {
            MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
            return;
        }

        //[begin]����ɾ����¼����ʾ����
        if(confirmRemove())
        {
            int top = table.getSelectManager().get().getBeginRow();
            int bottom = table.getSelectManager().get().getEndRow();

            int start = top/suppRows;
            int end = bottom/suppRows+1;
            
            for(int i=end*suppRows-1;i>=start*suppRows;i--)
            {
                table.removeRow(i);
            }
            
            if(table!=null){
	            BigDecimal amount = FDCHelper.ZERO;
	            BigDecimal amountDedut = FDCHelper.ZERO;
	            BigDecimal amountCp = FDCHelper.ZERO;
				int i = table.getRowCount();
				for(int j=0;j<i;j++){
					//�����λ��
					if(j%suppRows==ROW_costAmt){
						Object contentCA = table.getCell(j, "content").getValue();
						if(contentCA != null){
							amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
					//ROW_deductAmt
					if(j%suppRows==ROW_deductAmt){
						Object contentCA = table.getCell(j, "content").getValue();
						if(contentCA != null){
							amountDedut = amountDedut.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
					
					if(j%suppRows==ROW_constPirce){
						Object contentCA = table.getCell(j, "content").getValue();
						if(contentCA != null){
							amountCp = amountCp.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
				}
				editData.setTotalCost(amount);
				txtTotalCost.setValue(amount);
				
				editData.setAmountDutySupp(amountDedut);
				txtDutyAmount.setValue(amountDedut);
				
				editData.setTotalConstructPrice(amountCp);
				txtTotalConstructPrice.setValue(amountCp);
            }
        }
        setSuppNum(suppRows);
    }

	protected void prmtSpecialtyType_willShow(SelectorEvent e) throws Exception {
		if(prmtAuditType.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("changeTypeFirst"));
			return;
		}
		setSpecialtyTypeEvi();
		super.prmtSpecialtyType_willShow(e);		
	}
	
	private void setSpecialtyTypeEvi(){
		prmtSpecialtyType.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ChangeTypeInfo info = (ChangeTypeInfo) prmtAuditType.getValue();
		filter.getFilterItems().add(new FilterItemInfo("changeType.id",info.getId()));
		evi.setFilter(filter);
		prmtSpecialtyType.setEntityViewInfo(evi);
	}

	protected void prmtAuditType_dataChanged(DataChangeEvent e) throws Exception {
		if (OprtState.VIEW.equals(getOprtState())) {
    		return;
    	}
		if(this.editData.getAuditType()!=null){
			if(this.editData.getAuditType().equals(this.prmtAuditType.getValue())){
				return;
			}
			else if((e.getOldValue()!=null)&&(e.getOldValue().equals(e.getNewValue()))){
				return;
			}
		}
		else if((e.getOldValue()!=null)&&(e.getOldValue().equals(e.getNewValue()))){
			return;
		}
		if(e.getSource() == prmtAuditType ){
			prmtChangeReason.setData(null);
		}
		
		prmtSpecialtyType.setValue(null);
		super.prmtAuditType_dataChanged(e);
	}

	//���ֹͣ�༭
	protected void kdtSuppEntry_editStopped(KDTEditEvent e) throws Exception {
		Boolean isFromWorkflow = (Boolean) getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.booleanValue()) {

			if (e.getValue() != null
					&& e.getRowIndex() % suppRows == ROW_OriCost
					&& e.getColIndex() == getSecondTable().getColumnIndex(
							"content")) {
				ICell conCell = getSecondTable().getCell(e.getRowIndex()-7,"content");
				if(conCell!=null && conCell.getValue() instanceof ContractBillInfo){
					ContractBillInfo info=(ContractBillInfo)conCell.getValue();
					boolean isCostSplit = info.isIsCoseSplit();
					if(!isCostSplit&&FDCHelper.toBigDecimal(e.getValue()).compareTo(FDCHelper.ZERO)<0){
						String msg = "�ǳɱ����ͬ���������������ı��! ���޸ĺ�ͬ��\""+info.getNumber()+"\"�Ĳ�����!";
						MsgBox.showWarning(this,msg);
						SysUtil.abort();
					}
				}
			}
		}
		
		if (e.getRowIndex() % suppRows == ROW_description && e.getColIndex() == getSecondTable().getColumnIndex("content")) {
			KDDetailedAreaUtil.setWrapFalse(getSecondTable().getCell(e.getRowIndex(), e.getColIndex()));
		}
		

		if(e.getRowIndex()%suppRows==0&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				ContractBillInfo info = (ContractBillInfo)e.getValue();
				if(info!=null){
					getSecondTable().getCell(e.getRowIndex()+ROW_contractNum, e.getColIndex()).setValue(info);//������ʱ��Ԫ����������ʾnumber������ֱ�����ö���
//					getSecondTable().getCell(e.getRowIndex()+ROW_contractName, e.getColIndex()).setValue(info.getNumber());
					getSecondTable().getCell(e.getRowIndex()+ROW_contractName, e.getColIndex()).setValue(info.getName());
					if(info.getPartB()!=null){
						String test = info.getPartB().getId().toString();
						SupplierInfo te = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(test));
						getSecondTable().getCell(e.getRowIndex()+ROW_mainSupp, e.getColIndex()).setValue(te);
					}
					else{
						getSecondTable().getCell(e.getRowIndex()+ROW_mainSupp, e.getColIndex()).setValue(null);
					}
					
					if(info.getCurrency()!=null){
						getSecondTable().getCell(e.getRowIndex()+ROW_Cur, e.getColIndex()).setValue(info.getCurrency());	
						
						//�������,�Լ���λ��
					 	BigDecimal exRate = FDCHelper.ONE;
						if(this.baseCurrency.getId().toString().equals(info.getCurrency().getId().toString())){
							getSecondTable().getRow(e.getRowIndex()+ROW_costAmt).getStyleAttributes().setLocked(true);
							getSecondTable().getRow(e.getRowIndex()+ROW_Rate).getStyleAttributes().setLocked(true);
							getSecondTable().getCell(e.getRowIndex()+ROW_Rate, e.getColIndex()).setValue(exRate);	
							
							getSecondTable().getCell(e.getRowIndex()+ROW_Rate, e.getColIndex()).getStyleAttributes().setNumberFormat(
					        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(2,true));
							
							getSecondTable().getCell(e.getRowIndex()+ROW_OriCost, e.getColIndex()).getStyleAttributes().setNumberFormat(
					        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(2,true));
							
							
						}else{
							getSecondTable().getRow(e.getRowIndex()+ROW_costAmt).getStyleAttributes().setLocked(false);
							getSecondTable().getRow(e.getRowIndex()+ROW_Rate).getStyleAttributes().setLocked(false);
							
					    	Date bookedDate = (Date)pkbookedDate.getValue();					    	
					    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, info.getCurrency().getId(),company,bookedDate);
					   
					    	int exPrecision = info.getCurrency().getPrecision();
					    	getSecondTable().getCell(e.getRowIndex()+ROW_OriCost, e.getColIndex()).getStyleAttributes().setNumberFormat(
					        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(exPrecision,true));
					    	
					    	if(exchangeRate!=null){
					    		exRate = exchangeRate.getConvertRate();
					    		exPrecision = exchangeRate.getPrecision();
					    	}
							getSecondTable().getCell(e.getRowIndex()+ROW_Rate, e.getColIndex()).setValue(exRate);	
							
							((KDFormattedTextField)(getSecondTable().getCell(e.getRowIndex()+ROW_Rate, e.getColIndex())
									.getEditor().getComponent())).setPrecision(exPrecision);
							
							getSecondTable().getCell(e.getRowIndex()+ROW_Rate, e.getColIndex()).getStyleAttributes().setNumberFormat(
					        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(exPrecision,true));
						}
							
						Object obj  = getSecondTable().getCell(e.getRowIndex()+ROW_OriCost, e.getColIndex()).getValue();
						if(obj!=null){							
							getSecondTable().getCell(e.getRowIndex()+ROW_costAmt, e.getColIndex()).setValue(((BigDecimal)obj).multiply(exRate));	
						}
						
//						getSecondTable().getCell(e.getRowIndex()+ROW_costAmt, e.getColIndex()).getStyleAttributes().setNumberFormat(
//				        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(2,true));
					}
				}
			}else{
//				getSecondTable().getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
				getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).setValue(null);
				getSecondTable().getCell(e.getRowIndex()+2, e.getColIndex()).setValue(null);
			}
		}
		else if(e.getRowIndex()%suppRows==ROW_costAmt&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				BigDecimal amount = FDCHelper.ZERO;
				int i = getSecondTable().getRowCount();
				for(int j=0;j<i;j++){
					if(j%suppRows==ROW_costAmt){
						Object contentCA = getSecondTable().getCell(j, "content").getValue();
						if(contentCA != null){
							amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
				}
				if(e.getOldValue()==null)
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				else{
					amount = amount.subtract(FDCHelper.toBigDecimal(e.getOldValue()));
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				}
				editData.setTotalCost(amount);
				txtTotalCost.setValue(amount);
			}
		}
		
		else if(e.getRowIndex()%suppRows==ROW_deductAmt&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				BigDecimal amount = FDCHelper.ZERO;
				int i = getSecondTable().getRowCount();
				for(int j=0;j<i;j++){
					if(j%suppRows==ROW_deductAmt){
						Object contentCA = getSecondTable().getCell(j, "content").getValue();
						if(contentCA != null){
							amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
				}
				if(e.getOldValue()==null)
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				else{
					amount = amount.subtract(FDCHelper.toBigDecimal(e.getOldValue()));
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				}
				editData.setAmountDutySupp(amount);
				txtDutyAmount.setValue(amount);
			}
		}
		else if(e.getRowIndex()%suppRows==ROW_constPirce&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				BigDecimal amount = FDCHelper.ZERO;
				int i = getSecondTable().getRowCount();
				for(int j=0;j<i;j++){
					if(j%suppRows==ROW_constPirce){
						Object contentCA = getSecondTable().getCell(j, "content").getValue();
						if(contentCA != null){
							amount = amount.add((FDCHelper.toBigDecimal(contentCA)));
						}
					}
				}
				if(e.getOldValue()==null)
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				else{
					amount = amount.subtract(FDCHelper.toBigDecimal(e.getOldValue()));
					amount = amount.add(FDCHelper.toBigDecimal(e.getValue()));
				}
				editData.setTotalConstructPrice(amount);
				txtTotalConstructPrice.setValue(amount);
			}
		}
		
		//�ұ��޸�
		else if(e.getRowIndex()%suppRows==ROW_Cur&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				CurrencyInfo currency = (CurrencyInfo)e.getValue();
				//�������,�Լ���λ��
			 	BigDecimal exRate = FDCHelper.ONE;
				if(this.baseCurrency.getId().toString().equals(currency.getId().toString())){
					getSecondTable().getRow(e.getRowIndex()+3).getStyleAttributes().setLocked(true);
					getSecondTable().getRow(e.getRowIndex()+1).getStyleAttributes().setLocked(true);
					getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).setValue(exRate);	
				}else{
					getSecondTable().getRow(e.getRowIndex()+3).getStyleAttributes().setLocked(false);
					getSecondTable().getRow(e.getRowIndex()+1).getStyleAttributes().setLocked(false);
					
			    	Date bookedDate = (Date)pkbookedDate.getValue();					    	
			    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
			   
			    	int exPrecision = currency.getPrecision();	
			    	
					getSecondTable().getCell(e.getRowIndex()+2, e.getColIndex()).getStyleAttributes().setNumberFormat(
			        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(exPrecision,true));
					
			    	if(exchangeRate!=null){
			    		exRate = exchangeRate.getConvertRate();
			    		exPrecision = exchangeRate.getPrecision();
			    	}
					getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).setValue(exRate);	
					
					getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).getStyleAttributes().setNumberFormat(
			        		com.kingdee.eas.framework.report.util.KDTableUtil.getNumberFormat(exPrecision,true));
				}
					
				Object obj  = getSecondTable().getCell(e.getRowIndex()+2, e.getColIndex()).getValue();
				if(obj!=null){							
					getSecondTable().getCell(e.getRowIndex()+3, e.getColIndex()).setValue(((BigDecimal)obj).multiply(exRate));	
				}
			}else{
				
			}
		}
		//ԭ���޸�
		else if(e.getRowIndex()%suppRows==ROW_OriCost&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				BigDecimal newValue = FDCHelper.toBigDecimal(e.getValue());
				BigDecimal oldValue = FDCHelper.toBigDecimal(e.getOldValue());
				Object obj  = getSecondTable().getCell(e.getRowIndex()-1, e.getColIndex()).getValue();
				if(obj!=null){	
					BigDecimal newCostAmt = FDCHelper.toBigDecimal(obj).multiply(newValue);
					BigDecimal oldCostAmt = FDCHelper.toBigDecimal(obj).multiply(oldValue);
					getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).setValue(newCostAmt);
					BigDecimal totalCost = FDCHelper.toBigDecimal(editData.getTotalCost());
					this.editData.setTotalCost(totalCost.subtract(oldCostAmt).add(newCostAmt));
					this.txtTotalCost.setValue(editData.getTotalCost());
				}
			}
		}
		//�����޸�
		else if(e.getRowIndex()%suppRows==ROW_Rate&&e.getColIndex()==getSecondTable().getColumnIndex("content")){
			if(e.getValue()!=null){
				BigDecimal exRate = (BigDecimal)e.getValue();
				Object obj  = getSecondTable().getCell(e.getRowIndex()+1, e.getColIndex()).getValue();
				if(obj!=null){							
					getSecondTable().getCell(e.getRowIndex()+2, e.getColIndex()).setValue(((BigDecimal)obj).multiply(exRate));	
				}
			}
		}
		//TODO һ���Ե������¼������, ����Ĵ�����ؼ���������Ż� by hpw 2009-07-28
		setAmount();
	
	}
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if (e.getColIndex() == getDetailTable().getColumnIndex("changeContent")) {
			KDDetailedAreaUtil.setWrapFalse(getDetailTable().getCell(e.getRowIndex(), e.getColIndex()));
		}
		
		if (e.getColIndex() == getDetailTable().getColumnIndex("effect")) {
			KDDetailedAreaUtil.setWrapFalse(getDetailTable().getCell(e.getRowIndex(), e.getColIndex()));
		}
	}

	protected void kdtSuppEntry_editStopping(KDTEditEvent e) throws Exception {
		
	}
	
	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == 0) // ��������ͷ��Ҫ����
			return;
		if (e.getColIndex() == getDetailTable().getColumnIndex("changeContent")) {
			if (STATUS_VIEW.equals(oprtState) || STATUS_FINDVIEW.equals(oprtState)) {
				KDDetailedAreaUtil.showDialog(this, getDetailTable(), 250, 200, 1000);
			}
		} else if (e.getColIndex() == getDetailTable().getColumnIndex("effect")) {
			if (STATUS_VIEW.equals(oprtState) || STATUS_FINDVIEW.equals(oprtState)) {
				KDDetailedAreaUtil.showDialog(this, getDetailTable(), 250, 200, 500);
			}
		}
	}

	protected void kdtSuppEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getRowIndex() % suppRows == ROW_description && e.getColIndex() == getSecondTable().getColumnIndex("content")) {
			if (STATUS_VIEW.equals(oprtState) || STATUS_FINDVIEW.equals(oprtState)) {				
				KDDetailedAreaUtil.showDialog(this, kdtSuppEntry,250,200,500);
			}
		}
		
		if(!this.oprtState.equals(STATUS_EDIT)){
			return;
		}
		if (e.getRowIndex() % suppRows == ROW_isDeduct && e.getColIndex() == getSecondTable().getColumnIndex("content")) {
			final ICell cell8 = getSecondTable().getCell(e.getRowIndex()+1, 3);
			final ICell cell9 = getSecondTable().getCell(e.getRowIndex()+2, 3);
			final ICell cell10 = getSecondTable().getCell(e.getRowIndex()+3, 3);
			
			final Object value = getSecondTable().getCell(e.getRowIndex(), 3).getValue();
//			KDFormattedTextField kdc=(KDFormattedTextField)cell8.getEditor().getComponent();
			KDFormattedTextField kdc2=(KDFormattedTextField)cell9.getEditor().getComponent();
			KDTextField matmdtf =(KDTextField)cell10.getEditor().getComponent();
			
//			kdc.setEnabled(true);
			kdc2.setEnabled(true);
			matmdtf.setEnabled(true);
			cell8.getStyleAttributes().setLocked(false);
			cell9.getStyleAttributes().setLocked(false);
			cell10.getStyleAttributes().setLocked(false);
			
			if(value.equals(Boolean.TRUE)){
//				Object oldObj=cell8.getValue();
//				kdc.setValue(null);
//				cell8.setValue(null);
//				kdc.setEditable(false);
//				cell8.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
//				if(oldObj instanceof BigDecimal){
//					BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmountDutySupp()).subtract(FDCHelper.toBigDecimal(oldObj));
//					editData.setAmountDutySupp(amount);
//					txtDutyAmount.setValue(amount);
//				}
				
				Object oldObj=cell9.getValue();
				kdc2.setValue(null);
				cell9.setValue(null);
				kdc2.setEditable(false);
				cell9.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				if(oldObj instanceof BigDecimal){
					BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmountDutySupp()).subtract(FDCHelper.toBigDecimal(oldObj));
					editData.setAmountDutySupp(amount);
					txtDutyAmount.setValue(amount);
				}
				
				matmdtf.setText(null);
				cell10.setValue(null);
				matmdtf.setEditable(false);
//				cell8.getStyleAttributes().setLocked(true);
//				cell9.getStyleAttributes().setLocked(true);
				
				cell10.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);

				getSecondTable().getCell(e.getRowIndex(), 3).setValue(Boolean.FALSE);
			}
			else{
//				kdc.setEditable(true);
				kdc2.setEditable(true);
				matmdtf.setEditable(true);
				cell8.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				cell9.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				cell10.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				getSecondTable().getCell(e.getRowIndex(), 3).setValue(Boolean.TRUE);
			}
			getSecondTable().getEditManager().editCellAt(e.getRowIndex()+2,3);
		}
	}

	public void actionRegister_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRegister();
		storeFields();
		ctnSuppEntrys.setVisible(true);
//		tbpChangAudit.setSelectedComponent(pnlSupp);
		tbpChangAudit.setSelectedIndex(1);
		super.actionRegister_actionPerformed(e);
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
		Set idSet = new HashSet();
		idSet.add(editData.getId().toString());
		actionSave.setDaemonRun(false);
		ActionEvent event = new ActionEvent(btnSave,
				ActionEvent.ACTION_PERFORMED, btnSave
						.getActionCommand());
		actionSave.actionPerformed(event);
		bill.register(idSet);
		syncDataFromDB();
		handleOldData();
//		FDCClientUtils.showOprtOK(this);
		setSaveActionStatus();
	}
	
	protected void syncDataFromDB() throws Exception {
		if(getOprtState().equals(OprtState.ADDNEW)){
			return;
		}
		//�ɴ��ݹ�����ID��ȡֵ����
        if(editData.getId() == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(editData.getId().toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
	
	protected void handleOldData() {
		this.storeFields();
		this.initOldData(this.editData);
	}
	
	private void checkBeforeRegister(){
		if (getSecondTable().getExpandedRowCount()<=0){
    		MsgBox.showWarning(this,ChangeAuditUtil.getRes("suppNoNull"));
			SysUtil.abort();
    	}
		int number = getSecondTable().getRowCount();
		int count = number/suppRows;
		for(int i=0; i<count; i++){
			Object content = getSecondTable().getCell(i*suppRows, "content").getValue();
			if(content == null){
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("mainSuppNoNull"));
				SysUtil.abort();
			}
			Object contentCon = getSecondTable().getCell(i*suppRows+1, "content").getValue();
			if(contentCon == null){
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("contractNoNull"));
				SysUtil.abort();
			}
//			Object contentCopy = getSecondTable().getCell(i*suppRows+3, "content").getValue();
//			if(contentCopy == null){
//				MsgBox.showWarning(this,ChangeAuditUtil.getRes("copySuppNoNull"));
//				SysUtil.abort();
//			}			
			Object contentDo = getSecondTable().getCell(i*suppRows + ROW_contentDo, "content").getValue();			
			if(contentDo == null){
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("contentNoNull"));
				SysUtil.abort();
			}
		}
	}
	
	private void checkBeforeAddSupp() throws EASBizException, BOSException{
		if(editData.getId()==null){
			MsgBox.showWarning(ChangeAuditUtil.getRes("registerCheck"));
			SysUtil.abort();
		}else if(editData.getEntrys().size()==0){
			MsgBox.showWarning(ChangeAuditUtil.getRes("registerCheck"));
			SysUtil.abort();
		}else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString()));
			if(!ChangeAuditEntryFactory.getRemoteInstance().exists(filter)){
				MsgBox.showWarning(ChangeAuditUtil.getRes("registerCheck"));
				SysUtil.abort();
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=super.getSelectors();
		sic.add(new SelectorItemInfo("*"));		
		sic.add(new SelectorItemInfo("isAlreadyExecuted"));		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("suppEntry.*"));
		
		sic.add(new SelectorItemInfo("suppEntry.mainSupp.*"));
		sic.add(new SelectorItemInfo("suppEntry.copySupp.*"));
		sic.add(new SelectorItemInfo("suppEntry.currency.*"));
		sic.add(new SelectorItemInfo("suppEntry.contractBill.*"));
		sic.add(new SelectorItemInfo("suppEntry.reckonor.*"));
		
		sic.add(new SelectorItemInfo("suppEntry.contractChange.*"));
		sic.add(new SelectorItemInfo("suppEntry.entrys.*"));
		sic.add(new SelectorItemInfo("suppEntry.entrys.content.*"));
		sic.add(new SelectorItemInfo("suppEntry.entrys.content.number"));
		//����ԭʼ��ϵ���� eric_wang 2010.05.31
		sic.add(new SelectorItemInfo("suppEntry.originalContactNum"));
		//���ӡ��Ƿ�ȷ��������ֶ� by cassiel 2010-07-27
		sic.add(new SelectorItemInfo("suppEntry.isSureChangeAmt"));
		sic.add(new SelectorItemInfo("suppEntry.constructPrice"));
		sic.add(new SelectorItemInfo("suppEntry.dutyOrg.*"));
		
		sic.add("curProject.id");
		sic.add("curProject.displayName");
		sic.add("curProject.CU.number");
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		
		//רҵ����֧�ֶ�ѡ
		sic.add(new SelectorItemInfo("mutiSpecialtyType.*"));
		sic.add(new SelectorItemInfo("mutiSpecialtyType.specialtyType.*"));
//		sic.add(new SelectorItemInfo("mutiSpecialtyType.specialtyType.name"));
//		sic.add(new SelectorItemInfo("mutiSpecialtyType.specialtyType.number"));
//		sic.add(new SelectorItemInfo("mutiSpecialtyType.specialtyType.id"));
		
		sic.add(new SelectorItemInfo("sourceFunction"));
		sic.add(new SelectorItemInfo("oaPosition"));
		return sic;
	}
    
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		// ���������ɱ����ģ���������ɾ����
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionRegister.setVisible(false);
//		}
		if(!isDispatch){
			actionDisPatch.setVisible(false);
			actionDisPatch.setEnabled(false);
		}
	}
		
	protected void handleActionStatusByCurOrg() {
		
	}
	
	public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeDisPatch();
		super.actionDisPatch_actionPerformed(e);
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
		Set idSet = new HashSet();
		idSet.add(editData.getId().toString());
		bill.disPatch(idSet);
		editData.setChangeState(ChangeBillStateEnum.Announce);
		comboChangeState.setSelectedItem(editData.getChangeState());
		FDCClientUtils.showOprtOK(this);
		setSaveActionStatus();
	}
	
	private void checkBeforeDisPatch(){
		if(!(editData.getChangeState().equals(ChangeBillStateEnum.Audit))){
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("noDispatch"));
			SysUtil.abort();
		}
	}

	public void verfySuppEntrys(){
		if (getSecondTable().getExpandedRowCount()<=0){
			if(isOfferAndConstrReq){
				return;//����ģʽ�ɲ�¼
			}
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("suppNoNull"));
			SysUtil.abort();
    	}
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		int number = getSecondTable().getRowCount();
		int count = number/suppRows;
		List list=new ArrayList();
		List listAmt=new ArrayList();
		for(int i=0; i<count; i++){
			Object contentCon = getSecondTable().getCell(i*suppRows, "content").getValue();
			ContractBillInfo contractBillInfo = null;
			if(contentCon == null){
				getSecondTable().getEditManager().editCellAt(i*suppRows, 3, true);
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("contractNoNull"));
				SysUtil.abort();
			}else{
				//����ͬ�Ƿ��Ѿ�����
				if(contentCon instanceof ContractBillInfo&&((ContractBillInfo)contentCon).isHasSettled()){
					String inf = ChangeAuditUtil.getRes("contracthasSettle");
					String []args=new String[]{""+getSecondTable().getCell(i*suppRows,"supp").getValue(),((ContractBillInfo)contentCon).getNumber()};
					MsgBox.showWarning(this,FDCClientHelper.formatMessage(inf, args));
					SysUtil.abort();
				}
				contractBillInfo = (ContractBillInfo)contentCon;
				//���������ʾ
				list.add(((ContractBillInfo)contentCon).getId());
				Object amt = getSecondTable().getCell(i*suppRows+ROW_costAmt, "content").getValue();
				listAmt.add(FDCHelper.toBigDecimal(amt));
			}
			Object content = getSecondTable().getCell(i*suppRows+ROW_mainSupp, "content").getValue();
			if(content == null){
				getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_mainSupp, 3);
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("mainSuppNoNull"));
				SysUtil.abort();
			}
			//���͵�λ����Ϊ�գ�liupd��2007-11
//			Object contentCopy = getSecondTable().getCell(i*suppRows+3, "content").getValue();
//			if(contentCopy instanceof Object[]){
//				Object[] infos = (Object[])contentCopy;
//				if(infos.length<=0){					
//					getSecondTable().getEditManager().editCellAt(i*suppRows+3, 3);
//					MsgBox.showWarning(this,ChangeAuditUtil.getRes("copySuppNoNull"));
//					SysUtil.abort();
//				}
//			}else{
//				getSecondTable().getEditManager().editCellAt(i*suppRows+3, 3);
//				MsgBox.showWarning(this,ChangeAuditUtil.getRes("copySuppNoNull"));
//				SysUtil.abort();
//			}
			Object contentDo = getSecondTable().getCell(i*suppRows+ROW_contentDo, "content").getValue();			
			if(contentDo instanceof Object[]){
				Object[] infos = (Object[])contentDo;
				if(infos.length<=0){
					getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_contentDo, 3);
					MsgBox.showWarning(this,ChangeAuditUtil.getRes("contentNoNull"));
					SysUtil.abort();					
				}
			}else{
				getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_contentDo, 3);
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("contentNoNull"));
				SysUtil.abort();
			}
			Object contentCA = getSecondTable().getCell(i*suppRows+ROW_costAmt, "content").getValue();
			if(contentCA == null){
				getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_costAmt, 3);
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("amountNoNull"));
				SysUtil.abort();				
			} else if (contractBillInfo != null
					&& !contractBillInfo.isIsCoseSplit()
					&& FDCHelper.toBigDecimal(contentCA).compareTo(
							FDCHelper.ZERO) < 0) {
				String msg = "�ǳɱ����ͬ���������������ı��! ���޸ĺ�ͬ��\""+contractBillInfo.getNumber()+"\"�Ĳ�����!";
				MsgBox.showWarning(this,msg);
				SysUtil.abort();
			}
////			Object contentBT = getSecondTable().getCell(i*suppRows+10, "content").getValue();
////			if(contentBT == null){
////				MsgBox.showWarning(this,"���㷽ʽ����Ϊ�գ�");
////				SysUtil.abort();
////			}	
			//��������οۿλ����ۿ�����ۿ�ԭ���¼
			Object value = getSecondTable().getCell(i*suppRows+ROW_isDeduct,"content").getValue();
			if(value.equals(Boolean.TRUE)){
				Object contentDA = getSecondTable().getCell(i*suppRows+ROW_deductAmt, "content").getValue();
				if(contentDA == null){
					getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_deductAmt, 3);
					MsgBox.showWarning(this,"���οۿ����ע������Ϊ�գ�");
					SysUtil.abort();				
				}
				
				Object contentDR = getSecondTable().getCell(i*suppRows+ROW_deductReason, "content").getValue();
				if(contentDR == null){
					getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_deductReason, 3);
					MsgBox.showWarning(this,"�ۿ�ԭ����Ϊ�գ�");
					SysUtil.abort();				
				}
			}
			
//			Object contentBP = getSecondTable().getCell(i*suppRows+ROW_reckoner, "content").getValue();
//			if(contentBP == null){
//				getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_reckoner, 3);
//				MsgBox.showWarning(this,ChangeAuditUtil.getRes("personNoNull"));
//				SysUtil.abort();				
//			}
			Object contentBP = getSecondTable().getCell(i*suppRows+ROW_constPirce, "content").getValue();
			if(contentBP == null){
				getSecondTable().getEditManager().editCellAt(i*suppRows+ROW_constPirce, 3);
				MsgBox.showWarning(this,"ʩ�����������Ϊ�գ�");
				SysUtil.abort();				
			}
		}
		boolean isWarn=false;
		String str="���±���ĺ�ͬ�ı��ָ�����ۼ��Ѵﵽ�˱����ʾ�ı���:\n";
		if(list.size()>0){
			//����
			List mylist=new ArrayList();
			List mylistAmt=new ArrayList();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				BigDecimal amt=(BigDecimal)listAmt.get(i);
				if(obj==null) continue;
				if(i==list.size()-1){
					//���һ��ֱ�����
					mylist.add(obj);
					mylistAmt.add(amt);
				}
				for(int j=i+1;j<list.size();j++){
					if(obj.equals(list.get(j))){
						listAmt.set(j, amt.add((BigDecimal)listAmt.get(j)));
						break;
					}
					mylist.add(obj);
					mylistAmt.add(amt);
				}
			}
			list=mylist;
			listAmt=mylistAmt;
			
			SelectorItemCollection selector1=new SelectorItemCollection();
			selector1.add("amount");
			selector1.add("chgPercForWarn");
			SelectorItemCollection selector2=new SelectorItemCollection();
			selector2.add("amount");
			selector2.add("balanceAmount");
			selector2.add("hasSettled");
			for(int i=0;i<list.size();i++){
				try{
					final String id = list.get(i).toString();
					final ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
					EntityViewInfo view=new EntityViewInfo();
					view.setFilter(new FilterInfo());
					view.getFilter().appendFilterItem("contractBill.id", id);
					if(editData!=null&&editData.getId()!=null){
						view.getFilter().getFilterItems().add(new FilterItemInfo("changeAudit.id",editData.getId().toString(),CompareType.NOTEQUALS));
					}
					view.put("selector", selector2);
					final ContractChangeBillCollection contractChangeBillCollection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
					BigDecimal sum=FDCHelper.ZERO;
					for(int j=0;j<contractChangeBillCollection.size();j++){
						final ContractChangeBillInfo info = contractChangeBillCollection.get(j);
						if(info.isHasSettled()){
							sum=sum.add(FDCHelper.toBigDecimal(info.getBalanceAmount()));
						}else{
							sum=sum.add(FDCHelper.toBigDecimal(info.getAmount()));
						}
					}
					sum=sum.add(FDCHelper.toBigDecimal(listAmt.get(i)));
					BigDecimal rate = FDCHelper.toBigDecimal(contractBillInfo.getAmount()).multiply(FDCHelper.toBigDecimal(contractBillInfo.getChgPercForWarn()));
					rate=rate.divide(FDCHelper.ONE_HUNDRED, 4);
					if(sum.compareTo(FDCHelper.toBigDecimal(rate))>0){
						isWarn=true;
						str+="��ͬ���룺"+contractBillInfo.getNumber()+"\n\t���ָ���ۼ�:"+sum.setScale(2)+
						"\n\t���ָ����ʾ���: "+rate.setScale(2)+"("+FDCHelper.toBigDecimal(contractBillInfo.getAmount()).setScale(2)+"*"+FDCHelper.toBigDecimal(contractBillInfo.getChgPercForWarn()).setScale(2)+"%)\n";
					}
				}catch(Exception e){
				}
			}
			if(isWarn){
				MsgBox.showDetailAndOK(this, "���������ʾ��������鿴��ϸ��Ϣ", str.substring(0, str.length()-1), 1);
			}
		}
		
	}
	
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}else{
			int count = getSecondTable().getRowCount()/suppRows;
			if(!(count==editData.getSuppEntry().size())){
				return true;
			}
		}
		return super.isModify();
	}
	
	/**
     * ���ǳ����ദ�����������Ϊ,ͳһ��FDCBillEditUI.handCodingRule�����д���
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }    
    protected boolean confirmRemove(){   	
    	if(editData.getChangeState()!=null&&editData.getChangeState().equals(ChangeBillStateEnum.SUBMIT_VALUE)){
    		int isYes = MsgBox.showConfirm2(this, ChangeAuditUtil.getRes("hasChange"));
    		if (MsgBox.isYes(isYes)) {	
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return super.confirmRemove();
    	}  	
    }
    
    public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}
		this.savePrintSetting(this.getTableForPrintSetting());
		boolean b = true;
		if (!b) {
			return b;
		} else {
			// У��storeFields()�Ƿ��׳��쳣
			// Exception err = verifyStoreFields();
			// storeFields()�׳��쳣����editdata�иı䣬ѯ���Ƿ񱣴��˳�
			if (isModify()) {
				// editdata�иı�
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));
				if (result == KDOptionPane.YES_OPTION) {
					try {
						if (editData.getChangeState() == null
								|| editData.getChangeState() == ChangeBillStateEnum.Saved) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}
				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}
		}		
	}
    
	protected void cbxNoUse_actionPerformed(ActionEvent e) throws Exception {
		super.cbxNoUse_actionPerformed(e);
		if(cbxNoUse.isSelected()){
			txtNoUse.setEnabled(true);
			txtNoUse.setRequired(true);
			bmptResaon.setEnabled(true);
		}else{
			txtNoUse.setEnabled(false);
			bmptResaon.setEnabled(false);
			txtNoUse.setRequired(false);
			txtNoUse.setValue(null);
			bmptResaon.setValue(null);
		}
	}
    
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		return;
	}
	
	private void checkBeforeRemoveOrEdit()throws Exception{
		String id = editData.getId().toString();
		SelectorItemCollection itemCollection=new SelectorItemCollection();
		itemCollection.add("suppEntry");
		itemCollection.add("suppEntry.contractChange.contractBill.isCoseSplit");
		itemCollection.add("changeState");
		ChangeAuditBillInfo	info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(id),itemCollection);
    		if(info!=null){
    			if (!info.getChangeState().equals(ChangeBillStateEnum.Saved) && !info.getChangeState().equals(ChangeBillStateEnum.Submit)) {
    				MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ�ɾ��������");
    				abort();
    			}
    			boolean isCostSplit =false;
    			for(int i=0;i<info.getSuppEntry().size();i++){
    				ContractChangeBillInfo entryInfo = info.getSuppEntry().get(i).getContractChange();
    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()){
    					isCostSplit = FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "t_con_conchangesplit", "FContractChangeID");
    					if(isCostSplit){
    						break;//ֻҪ��һ��ָ�����ֹ�����Ҫ��ʾ
    					}
    				}
    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()){//�ǳɱ�����
    					isCostSplit=FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "T_CON_ConChangeNoCostSplit", "FContractChangeID");
    					if(isCostSplit){
    						break;//ֻҪ��һ��ָ�����ֹ�����Ҫ��ʾ
    					}
    				}
    			}//�����ǳɱ����ֻ��Ƿǳɱ����֣�ֻҪ��ָ�����ֹ��͵ø��ͻ���ʾ
    			if(isCostSplit){
    				MsgBox.showWarning("�˱�����������ɵ�ָ��Ѿ���֣�����ɾ����");
    				SysUtil.abort();
    			}
    		}
    		
		FDCClientUtils.checkBillInWorkflow(this, id);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeRemoveOrEdit();
		
		String id = editData.getId().toString();
		super.actionRemove_actionPerformed(e);
		if(editData!=null&&!editData.getId().toString().equals(id)){
			ctnSuppEntrys.setVisible(true);
			int unitNum = editData.getSuppEntry().size();
			for(int i=0;i<unitNum;i++){
				ChangeSupplierEntryInfo info = editData.getSuppEntry().get(i);
				if(info.getId()==null)
					return;
			}
			getSecondTable().removeRows();
			loadSuppEntrys();
		}
	}
	
	protected void checkBeforeEditOrRemove(String warning) {
		ChangeBillStateEnum state = editData.getChangeState();
		if (state != null
				&& (state == ChangeBillStateEnum.Auditting || state == ChangeBillStateEnum.Audit 
						|| state == ChangeBillStateEnum.Announce ||  state == ChangeBillStateEnum.Visa
						||  state == ChangeBillStateEnum.AheadDisPatch )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}	
		
	}
	
	protected KDTable getTableForPrintSetting() {
		return getDetailTable();
	}
	
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }          
//      ChangeAuditBillProvider data = new ChangeAuditBillProvider(editData.getString("id"),getTDQueryPK(),getATTCHQueryPK());
        ChangeAuditBillDataProvider data = new ChangeAuditBillDataProvider(editData.getString("id"),getTDQueryPK(),this.prmtSpecialtyType.getText());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
//        ChangeAuditBillProvider data = new ChangeAuditBillProvider(editData.getString("id"),getTDQueryPK(),getATTCHQueryPK());
        ChangeAuditBillDataProvider data = new ChangeAuditBillDataProvider(editData.getString("id"),getTDQueryPK(),this.prmtSpecialtyType.getText());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
    
    /**
     * �״��Ӧ��Ŀ¼
     */
	protected String getTDFileName() {
    	return "/bim/fdc/contract/changeAuditbill";
	}
	
	/**
	*  �״��Ӧ��Query
	*/
	protected IMetaDataPK getTDQueryPK() {
		//ChangeAuditForPrintQuery
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ChangeAuditBillPrintQuery");
	}
	
	// ��Ӧ���״�Query
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.AttchmentForPrintQuery");
	}
	
	protected void comboOffer_itemStateChanged(ItemEvent e) throws Exception {
		if (e!=null && e.getStateChange() == ItemEvent.SELECTED) {
			OfferEnum offer = (OfferEnum)e.getItem();
			if(OfferEnum.SELFCOM.equals(offer)){
				prmtConductDept.setEnabled(true);
				prmtConductDept.setRequired(true);
				
				prmtConductUnit.setEnabled(false);
				prmtConductUnit.setRequired(false);
				prmtConductUnit.setValue(null);
			}else{
				prmtConductUnit.setEnabled(true);
				prmtConductUnit.setRequired(true);
				
				prmtConductDept.setRequired(false);
				prmtConductDept.setValue(null);
				prmtConductDept.setEnabled(false);
			}
		}
	}
    
	public void fillAttachmentList(){
		this.cmbAttachment.removeAllItems();
		String boId = null;
		if(this.editData.getId() == null){
			return;
		}else{
			boId = this.editData.getId().toString();
		}
		
		if(boId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boId));
			
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			 try {
				cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
			if(cols != null && cols.size()>0){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.cmbAttachment.addItem(attachment);
				}
				this.isHasAttachment = true;
			}else{
				this.isHasAttachment =false;
			}
		}
		this.btnViewAttachment.setEnabled(this.isHasAttachment);
	}

	public void actionViewAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionViewAttachment_actionPerformed(e);
    	String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionAttachment_actionPerformed(e);
    	fillAttachmentList();
    }

    protected void lockContainer(Container container) {
    	 if(lblAttachmentContainer.getName().equals(container.getName())){
          	return;
          }
          else{
          	super.lockContainer(container);
          }
    }	
    
    public void actionPassHistory_actionPerformed(ActionEvent e)
    		throws Exception {
    	UIContext uiContext = new UIContext(this);
        uiContext.put("ID", this.editData.getId()!=null?this.editData.getId().toString():null);
        IUIWindow uiWindow = null;
        uiWindow = UIFactory.createUIFactory((com.kingdee.eas.base.uiframe.client.UIModelDialogFactory.class).getName()).create((com.kingdee.eas.base.multiapprove.client.PassHistoryUI.class).getName(), uiContext);
        uiWindow.show();
    }
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	String id = this.editData.getId().toString();
    	ChangeAuditBillInfo info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
}