/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomLoanProcessUI extends AbstractRoomLoanProcessUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomLoanProcessUI.class);
	
	RoomDisplaySetting setting = new RoomDisplaySetting();
	
	/**
	 * ������ķ�����ɫ��ǳ��ɫ
	 */
	private Color colorOfReady = new Color(255, 128, 128);
	
	/**
	 * �Ѱ���ķ�����ɫ��ǳ��ɫ
	 */
	private Color colorOfFinish =  new Color(255, 255, 128);
	
	/**
	 * �������ķ�����ɫ��ǳ��ɫ 
	 */
	private Color colorOfNoNeed =  new Color(128, 255, 128);
	
	/**
	 * ����Ϊ����
	 */
	private final int moneyTypeOfLoan = 0;
	
	/**
	 * ����Ϊ������
	 */
	private final int moneyTypeOfAfm = 1;
	
	/**
	 * �������Ϊ���Һ͹�����
	 */
	private final int moneyTypeOfLoanAndAfm = 2;
	
	/**
	 * ���ڱ��淿���Ӧ�Ŀ�����󣬱����ظ���ѯ
	 */
	private HashMap moneyDefineMap = new HashMap();
	
	/**
	 * �����������ҵ���ʱ�������Ϲ�������ǩԼ��
	 */
	private HashMap billObjectMap = new HashMap();

	public RoomLoanProcessUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		initControl();
		super.onLoad();

		moneyDefineMap.clear();
		billObjectMap.clear();
		
		this.actionBillAdjust.setVisible(false);
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getButton() == 1) {
			RoomInfo room = this.getSelectRoom();
			if(room == null){
				return ;
			}
		}
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		RoomInfo room = this.getSelectRoom();
		if(room == null){
			return ;
		}

		if (room != null) {
			this.fillRoomDetail(room);
		}
	}
	
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		//this.treeMain.setModel(FDCTreeHelper.getPlanisphereForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		SellProjectInfo info = new SellProjectInfo();
		if(this.getUIContext().get("selectProject")!=null){
			info = (SellProjectInfo)this.getUIContext().get("selectProject");
		}
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(info, true));
		this.treeMain.expandOnLevel(3);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		refresh(null);
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			return;
		}
		DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node.clone();
		CommerceHelper.cloneTree(newNode, node);
		newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // ���ýӿ�ʱ���ѯ�丸�ڵ�Ķ���
		CommerceHelper.removePlanisphereNode(newNode);
		SHEHelper.fillRoomTableByNode(this.tblMain, newNode, MoneySysTypeEnum.SalehouseSys, null, setting);
		
		//���跿����ɫ
		this.setRoomColor();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		ArrayList roomList = new ArrayList();
		
		ArrayList blockList = this.tblMain.getSelectManager().getBlocks();
		if(blockList==null || blockList.isEmpty()){
			return;
		}
		else if(blockList.size() > 1){  //ctrl+���������block
			for(int i=0; i<blockList.size(); i++){
				ArrayList selectRoomList = this.getSelectRoomListByBlock(((KDTSelectBlock)blockList.get(i)));
				if(selectRoomList!=null && !selectRoomList.isEmpty()){
					for(int r=0; r<selectRoomList.size(); r++){
						roomList.add(selectRoomList.get(r));
					}
				}
			}
		}
		else{  //�������߻�ѡ
			KDTSelectBlock block = (KDTSelectBlock)blockList.get(0);
			ArrayList selectRoomList = this.getSelectRoomListByBlock(block);
			if(selectRoomList!=null && !selectRoomList.isEmpty()){
				for(int r=0; r<selectRoomList.size(); r++){
					roomList.add(selectRoomList.get(r));
				}
			}
		}
		
		if(roomList!=null && !roomList.isEmpty()){  //��������
			for(int i=0; i<roomList.size(); i++){
				RoomInfo room = (RoomInfo)roomList.get(i);
				TransactionInfo transactionInfo = this.getTransactionInfo(room);  //���������������
				
				int moneyType = this.checkRoomMoneyType(room);
				if(moneyType < 0){
					FDCMsgBox.showInfo("���������ķ��䲻�����ɰ��ҷ���������ѡ��");
					SysUtil.abort();
				}
				
				if(this.moneyTypeOfLoanAndAfm == moneyType){  //���а��ң����й�������Ҫ�����������ҵ���
					//����������
					RoomLoanInfo roomAfmLoan = new RoomLoanInfo();
					roomAfmLoan.setRoom(room);
					roomAfmLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomAfmLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfAfm)));
					roomAfmLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					//���ݱ�����������ɱ���
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomAfmLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("�����ñ������");
						SysUtil.abort();
					}
					if(this.billObjectMap.get("sign") != null){
						roomAfmLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomAfmLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getAcfBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomAfmLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					//��ʼ��������Ϣ
					this.initLoanData(roomAfmLoan);
					
					//����ҵ��������Ӧ�ķ���
					SHEManageHelper.updateTransactionOverView(null, roomAfmLoan.getRoom(), SHEManageHelper.ACCFUND,
							roomAfmLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomAfmLoan);
					
					//���ҿ����
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//���ݱ�����������ɱ���
					number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("�����ñ������");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfLoan)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomAfmLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getLoanBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//��ʼ��������Ϣ
					this.initLoanData(roomLoan);
					
					//����ҵ��������Ӧ�ķ���
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
				else if(this.moneyTypeOfAfm == moneyType){  //������
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//���ݱ�����������ɱ���
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("�����ñ������");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfAfm)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getAcfBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//��ʼ��������Ϣ
					this.initLoanData(roomLoan);
					
					//����ҵ��������Ӧ�ķ���
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.ACCFUND,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
				else if(this.moneyTypeOfLoan == moneyType){  //����
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//���ݱ�����������ɱ���
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("�����ñ������");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfLoan)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getLoanBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//��ʼ��������Ϣ
					this.initLoanData(roomLoan);
					
					//����ҵ��������Ӧ�ķ���
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
			}
			
			FDCMsgBox.showInfo("�����ɹ�");
			
			this.billObjectMap.clear();
			this.actionRefresh_actionPerformed(null);
			
			//ˢ����ʱ��
			try{
				RoomLoanListUI parentListUI = (RoomLoanListUI)getUIContext().get("Owner");
				parentListUI.refresh(null);
			}catch(Exception ex){
				logger.error(ex);
			}
		}
	}
	
	private String createNumber() throws BOSException, CodingRuleException,
			EASBizException {
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		//if (!"ADDNEW".equals(this.oprtState)) {
			//return number;
		//}
		boolean isExist = true;
		RoomLoanInfo objValue = new RoomLoanInfo();
		objValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue,
					currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				// String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue,
						currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					} else {
						// ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.getNumber(objValue,
								currentOrgId);
					}
				}else{
					number = iCodingRuleManager.getNumber(objValue,
							currentOrgId);
				}
			}
		}
		return number;
	}
	
	private String getRuleNumber(IObjectValue caller, String orgId){
		
		 String number = "";
		 
		 try {
	            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	            if (orgId == null || orgId.trim().length() == 0)
	            {
	            	//��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
	                 orgId = OrgConstants.DEF_CU_ID;
	            }
	            if (iCodingRuleManager.isExist(caller, orgId))
	            {
	               
	                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
	                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
	                    if (iCodingRuleManager.isUserSelect(caller, orgId))
	                    {
	                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
	                        // KDBizPromptBox pb = new KDBizPromptBox();
	                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
	                                caller, orgId, null, null);
	                        // pb.setSelector(intermilNOF7);
	                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
	                        Object object = null;
	                        if (iCodingRuleManager.isDHExist(caller, orgId))
	                        {
	                            intermilNOF7.show();
	                            object = intermilNOF7.getData();
	                        }
	                        if (object != null)
	                        {
	                            number = object.toString();
	                        }
	                        else
	                        {
	                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
	                            number = iCodingRuleManager
	                                    .getNumber(caller, orgId);
	                        }
	                    }
	                    else
	                    {
	                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
	                        number = iCodingRuleManager.getNumber(caller, orgId);
	                    }
	                }
	                else
	                {
	                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
	                    number = iCodingRuleManager.getNumber(caller, orgId);
	                }
	            }
      
	        } catch (Exception err) {
	            //��ȡ�����������ֿ����ֹ�������룡
	           //handleCodingRuleError(err);
	        }
	        
	      return number;
	}
	
	/**
	 * �����Ϲ���ǩԼ���еĸ��������ʼ����������
	 * @param roomLoan
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initLoanData(RoomLoanInfo roomLoan) throws EASBizException, BOSException{
		String payTypeId = null;
		if(roomLoan.getSign() != null && roomLoan.getSign().getPayType().getId() != null){  //ǩԼ
			payTypeId = roomLoan.getSign().getPayType().getId().toString();
		}
		else if(roomLoan.getPurchase() != null && roomLoan.getPurchase().getPayType().getId() != null){  //�Ϲ�
			payTypeId = roomLoan.getPurchase().getPayType().getId().toString();
		}
		
		if(payTypeId != null){
			//��ȡ�����
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("isLoan"));
			selector.add(new SelectorItemInfo("loanBank.*"));
			selector.add(new SelectorItemInfo("acfBank.*"));
			selector.add(new SelectorItemInfo("loanScheme.*"));
			selector.add(new SelectorItemInfo("afScheme.*"));
			selector.add(new SelectorItemInfo("bizLists.*"));
			
			SHEPayTypeInfo payType = SHEPayTypeFactory.getRemoteInstance()
				.getSHEPayTypeInfo(new ObjectUuidPK(payTypeId), selector);
			
			if(payType != null ){
				if(payType.isIsLoan()){  //�����ң�����Ϣ���浽���ҵ���
					if(roomLoan.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){  //����
						roomLoan.setLoanBank(payType.getLoanBank());
						roomLoan.setORSOMortgaged(payType.getLoanScheme());
						this.setLoanEntry(roomLoan, payType.getLoanScheme());
					}
					else{  //������
						roomLoan.setLoanBank(payType.getAcfBank());
						roomLoan.setORSOMortgaged(payType.getAfScheme());
						this.setLoanEntry(roomLoan, payType.getAfScheme());
					}
				}
				//��������ҵ����ϸ�������ŵ�������
				if(payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
					for(int i=0; i<payType.getBizLists().size(); i++){
						BizListEntryInfo bizEntry = payType.getBizLists().get(i);
						if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.LOAN)){
							Date promDate = this.getBizPromiseDate(payType.getBizLists(), bizEntry);
							roomLoan.setPromiseDate(promDate);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * ������ϸ������Ӧ�������
	 * @param bizEntryCol
	 * @param currentBizInfo
	 * @return
	 */
	private Date getBizPromiseDate(BizListEntryCollection bizEntryCol, 
			BizListEntryInfo currentBizInfo){
		if(currentBizInfo.getPayTypeBizTime() == null){
			return null;
		}
		else if(currentBizInfo.getPayTypeBizTime().equals("ָ������")){  //��������Ϊָ������
			return currentBizInfo.getAppDate();
		}
		else{
			for(int i=0; i<bizEntryCol.size(); i++){
				BizListEntryInfo bizInfo = bizEntryCol.get(i);
				if(bizInfo.getPayTypeBizFlow().getAlias().equals(currentBizInfo.getPayTypeBizTime())){
					Date tempDate = getBizPromiseDate(bizEntryCol, bizInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentBizInfo.getMonthLimit();
						int day = currentBizInfo.getDayLimit();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * ���ݸ�����еİ��ҷ��������ð��ҵ��ݵķ�¼
	 * @param roomLoan
	 * @param afmScheme
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setLoanEntry(RoomLoanInfo roomLoan, AFMortgagedInfo afmScheme) throws EASBizException, BOSException{
		if(afmScheme == null){
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("ApproachEntrys.*"));
		selector.add(new SelectorItemInfo("DataEntrys.*"));
		
		afmScheme = AFMortgagedFactory.getRemoteInstance()
			.getAFMortgagedInfo(new ObjectUuidPK(afmScheme.getId().toString()), selector);
		
		roomLoan.getAFMortgaged().clear();
		
		//���ý��̷�¼
		RoomLoanAFMEntrysCollection afmEntryCol = new RoomLoanAFMEntrysCollection();
		AFMortgagedApproachEntryCollection appCol = afmScheme.getApproachEntrys();
		if(appCol!=null && !appCol.isEmpty()){
			for(int i=0; i<appCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedApproachEntryInfo appEntry = appCol.get(i);
				
				roomLoanEntry.setApproach(appEntry.getName());
				roomLoanEntry.setPromiseFinishDate(getApproachPromiseDate(roomLoan, appCol, appEntry));
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(appEntry.getRemark());
				roomLoanEntry.setIsFinishFlag(appEntry.isIsFinish());
				roomLoanEntry.setIsAOrB(true);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		//�������Ϸ�¼
		AFMortgagedDataEntryCollection dataCol = afmScheme.getDataEntrys();
		if(dataCol!=null && !dataCol.isEmpty()){
			for(int i=0; i<dataCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedDataEntryInfo dataEntry = dataCol.get(i);
				
				roomLoanEntry.setApproach(dataEntry.getName());
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(dataEntry.getRemark());
				roomLoanEntry.setIsAOrB(false);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		if(!afmEntryCol.isEmpty()){
			roomLoan.getAFMortgaged().addCollection(afmEntryCol);
		}
	}
	
	private Date getApproachPromiseDate(RoomLoanInfo roomLoan, AFMortgagedApproachEntryCollection afmAppCol, 
			AFMortgagedApproachEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("ָ������")){  //��������Ϊָ������
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("ǩԼ����")){  //��������ΪǩԼ����
			if(roomLoan.getSign()==null){
				return null;
			}
			else{
				return roomLoan.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(roomLoan, afmAppCol, appInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			Remove();
			refresh(e);
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected String getSelectedKeyValue() {
		return null;
	}

	public void initControl() {
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionImportData.setVisible(false);
		this.treeView.setShowControlPanel(true);
		
		//this.tblApproach.getStyleAttributes().setLocked(true);
		//this.tblData.getStyleAttributes().setLocked(true);
	}

	/**
	 * ���ѡ�еķ���
	 * 
	 * @param reQuery
	 *            �Ƿ����ѡ�з����ID���²�ѯ,��ø�����ֶ�ֵ
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private RoomInfo getSelectRoom() throws EASBizException, BOSException, UuidException {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			return null;
		}
		int left = block.getLeft();
		int top = block.getTop();
		RoomInfo room = this.getSelectRoomByIndex(top, left);
		return room;
	}
	
	/**
	 * ����ѡ���ģ���ȡ���䣬���жϷ����Ƿ���԰찴��
	 * @param block
	 * @return
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private ArrayList getSelectRoomListByBlock(KDTSelectBlock block) throws EASBizException, BOSException, UuidException{
		if(block == null){
			return null;
		}
		
		ArrayList roomList = new ArrayList();
		int top = block.getTop();
		int bottom = block.getBottom();
		int left = block.getLeft();
		int right = block.getRight();
		
		if(bottom > top){  //����
			if(right > left){  //����
				for(int i=top; i<=bottom; i++){
					for(int j=left; j<=right; j++){
						RoomInfo room = this.getSelectRoomByIndex(i, j);
						if(room != null){
							//��鷿���Ƿ���԰�����
							ICell cell = this.tblMain.getCell(i, j);
							if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
								FDCMsgBox.showInfo("���������ķ��䲻�����ɰ��ҷ���������ѡ��");
								SysUtil.abort();
							}
							roomList.add(room);
						}
					}
				}
			}
			else{  //����
				for(int i=top; i<=bottom; i++){
					RoomInfo room = this.getSelectRoomByIndex(i, left);
					if(room != null){
						//��鷿���Ƿ���԰�����
						ICell cell = this.tblMain.getCell(i, left);
						if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
							FDCMsgBox.showInfo("���������ķ��䲻�����ɰ��ҷ���������ѡ��");
							SysUtil.abort();
						}
						roomList.add(room);
					}
				}
			}
		}
		else if(right > left){  //���У�����
			for(int i=left; i<=right; i++){
				RoomInfo room = this.getSelectRoomByIndex(top, i);
				if(room != null){
					//��鷿���Ƿ���԰�����
					ICell cell = this.tblMain.getCell(top, i);
					if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
						FDCMsgBox.showInfo("���������ķ��䲻�����ɰ��ҷ���������ѡ��");
						SysUtil.abort();
					}
					roomList.add(room);
				}
			}
		}
		else{  //һ����Ԫ��
			RoomInfo room = this.getSelectRoomByIndex(top, left);
			if(room != null){
				//��鷿���Ƿ���԰�����
				ICell cell = this.tblMain.getCell(top, left);
				if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
					FDCMsgBox.showInfo("���������ķ��䲻�����ɰ��ҷ���������ѡ��");
					SysUtil.abort();
				}
				roomList.add(room);
			}
		}
		
		return roomList;
	}
	
	/**
	 * �������кŻ�ȡ����
	 * @param top
	 * @param left
	 * @return
	 * @throws Exception 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private RoomInfo getSelectRoomByIndex(int top, int left) throws EASBizException, BOSException, UuidException{
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return null;
		}
		RoomInfo room = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
			room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return null;
		}

		// Ϊ��Ч�ʴ�userObject��ֻ����һ��ID��������Ҫ�ٲ�һ��
		room = SHEHelper.queryRoomInfo(room.getId().toString());
		cell.setUserObject(room);
		return room;
	}
	
	/**
	 *����ѡ�з������ɫ
	 */
	private void setRoomColor() throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 0; j < this.tblMain.getColumnCount(); j++) {
				ICell cell = this.tblMain.getRow(i).getCell(j);
				if (cell == null) {
					continue;
				}
				RoomInfo roomInfo = (RoomInfo) cell.getUserObject();
				if (roomInfo == null){
					continue;
				}
				
				if(this.isRoomLoaned(roomInfo)){  //�Ȳ鿴�Ѱ����ҷ��䣺�ڰ��ҹ������м�¼�ķ���
					cell.getStyleAttributes().setBackground(this.colorOfFinish);
				}
				//�ٲ鿴������Ͳ������İ��ҷ���
				else if(!roomInfo.getSellState().equals(RoomSellStateEnum.Purchase) 
					    && !roomInfo.getSellState().equals(RoomSellStateEnum.Sign)){  //��������ҷ��䣺���Ϲ��ͷ�ǩԼ�ķ���
					cell.getStyleAttributes().setBackground(this.colorOfNoNeed);
				}
				else {  //�Ϲ���ǩԼ״̬
					if(this.isRoomNeedLoan(roomInfo)){  //������ϸ�а������ҿ�򹫻������ķ���
						cell.getStyleAttributes().setBackground(this.colorOfReady);
					}
					else{  //�������
						cell.getStyleAttributes().setBackground(this.colorOfNoNeed);
					}
				}
			}
		}
	}
	
	/**
	 * ��鷿���Ƿ��Ѱ�����
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private boolean isRoomLoaned(RoomInfo room) throws BOSException{
		boolean isLoaned = false;
		if(room == null){
			return false;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		
		HashSet afmStateSet = new HashSet();
		afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.BANKFUND_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));

		view.setFilter(filter);

		RoomLoanCollection roomLoanCol = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		if(roomLoanCol!=null && !roomLoanCol.isEmpty()){
			isLoaned = true;
		}
		
		return isLoaned;
	}
	
	/**
	 * ����Ϲ���ǩԼ�ķ����Ƿ���Ҫ������
	 * @param room
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isRoomNeedLoan(RoomInfo room) throws BOSException, EASBizException{
		boolean isNeedLoan = false;
		TransactionInfo transactionInfo = this.getTransactionInfo(room);
		if(transactionInfo != null){
			if(transactionInfo.getCurrentLink()!=null && ( transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)
					|| transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign))){  //�Ϲ���ǩԼ״̬
				TranBusinessOverViewCollection overViewCol = transactionInfo.getTranBusinessOverView();
				if(overViewCol!=null && !overViewCol.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
					for(int i=0; i<overViewCol.size(); i++){
						TranBusinessOverViewInfo overViewInfo = overViewCol.get(i);
						if(overViewInfo.getMoneyDefine()!=null){
							if (overViewInfo.getMoneyDefine().getMoneyType()!=null && overViewInfo.getMoneyDefine().getMoneyType()
									.equals(MoneyTypeEnum.LoanAmount)
									|| overViewInfo.getMoneyDefine().getMoneyType()!=null && overViewInfo
											.getMoneyDefine()
											.getMoneyType()
											.equals(MoneyTypeEnum.AccFundAmount)) {
								isNeedLoan = true;
							}
						}
					}
				}
			}
		}
		return isNeedLoan;
	}
	
	/**
	 * ��鷿���Ϲ���ǩԼ�Ŀ���ж��Ƿ���Ҫ�������Ű��ҵ���
	 * @param room
	 * @return 0 - ���ң�1 - ������ 2 - ����
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private int checkRoomMoneyType(RoomInfo room) throws BOSException, EASBizException{
		this.moneyDefineMap.clear();
		int retMoneyType = -1;
		
		TransactionInfo transactionInfo = this.getTransactionInfo(room);
		if(transactionInfo != null){
			if(transactionInfo.getCurrentLink()!=null && (transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)
					|| transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign))){  //�Ϲ���ǩԼ״̬
				//�����Ϲ�����ǩԼ��
				if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //�ݴ�ǩԼ��
					SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
						.getSignManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
					this.billObjectMap.put("sign", signInfo);
					
					SignPayListEntryCollection payListEntry = this.getSignPayEntry(transactionInfo.getBillId().toString());
					if(payListEntry!=null && !payListEntry.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
						for(int i=0; i<payListEntry.size(); i++){
							SignPayListEntryInfo payListInfo = payListEntry.get(i);
							if(payListInfo.getMoneyDefine()!=null) {
								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfAfm){  //���а��ң����й�����
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //����
										retMoneyType = this.moneyTypeOfLoan;
									}
								}
								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfLoan){  //���а��ң����й�����
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //������
										retMoneyType = this.moneyTypeOfAfm;
									}
								}
							}
						}
					}
				}
				else if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)){  //�ݴ��Ϲ���
					PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
						.getPurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
					this.billObjectMap.put("purchase", purInfo);
					
					PurPayListEntryCollection payListEntry = this.getPurchasePayEntry(transactionInfo.getBillId().toString());
					if(payListEntry!=null && !payListEntry.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
						for(int i=0; i<payListEntry.size(); i++){
							PurPayListEntryInfo payListInfo = payListEntry.get(i);
							if(payListInfo.getMoneyDefine()!=null) {
								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfAfm){  //���а��ң����й�����
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //����
										retMoneyType = this.moneyTypeOfLoan;
									}
								}
								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfLoan){  //���а��ң����й�����
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //������
										retMoneyType = this.moneyTypeOfAfm;
									}
								}
							}
						}
					}
				}
				
				//��ʱ���Σ�ҵ��������ϸ����δʵ��
				//����������ϸ��������
				/*TranBusinessOverViewCollection overViewCol = transactionInfo.getTranBusinessOverView();
				if(overViewCol!=null && !overViewCol.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
					for(int i=0; i<overViewCol.size(); i++){
			 			TranBusinessOverViewInfo OverViewInfo = overViewCol.get(i);
						if(OverViewInfo.getMoneyDefine()!=null) {
							if(OverViewInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
								moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), OverViewInfo.getMoneyDefine());
								if(retMoneyType == this.moneyTypeOfAfm){  //���а��ң����й�����
									return this.moneyTypeOfLoanAndAfm;
								}
								else{  //����
									retMoneyType = this.moneyTypeOfLoan;
								}
							}
							else if(OverViewInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
								moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), OverViewInfo.getMoneyDefine());
								if(retMoneyType == this.moneyTypeOfLoan){  //���а��ң����й�����
									return this.moneyTypeOfLoanAndAfm;
								}
								else{  //������
									retMoneyType = this.moneyTypeOfAfm;
								}
							}
						}
					}
				}*/
			}
		}
		
		return retMoneyType;
	}
	
	/**
	 * ��ȡ�������µĽ�������
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private TransactionInfo getTransactionInfo(RoomInfo room) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tranBusinessOverView.*");
		view.getSelector().add("tranBusinessOverView.moneyDefine.*");
		view.getSelector().add("currentLink");
		view.getSelector().add("billId");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		
		TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		if(transactionCol!=null && !transactionCol.isEmpty()){
			return transactionCol.get(0);
		}
		else{
			return null;
		}
	}

	/**
	 * �����Ϲ���id����ȡ������ϸ
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	private PurPayListEntryCollection getPurchasePayEntry(String billId) throws EASBizException, BOSException{
		PurPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("purPayListEntry.moneyDefine.moneyType");
		
		PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
			.getPurchaseManageInfo(new ObjectUuidPK(billId), selCol);
		if(purInfo.getId() != null){
			entryCol = purInfo.getPurPayListEntry();
		}
		
		return entryCol;
	}
	
	/**
	 * ����ǩԼ��id����ȡ������ϸ
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private SignPayListEntryCollection getSignPayEntry(String billId) throws EASBizException, BOSException{
		SignPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("signPayListEntry.moneyDefine.moneyType");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(billId), selCol);
		if(signInfo != null){
			entryCol = signInfo.getSignPayListEntry();
		}
		
		return entryCol;
	}

	/**
	 * ���ݷ��䣬��ȡ��Ч�İ�����Ϣ���������ҽ��̺�����
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private RoomLoanInfo getRoomLoanInfo(RoomInfo room) throws BOSException{
		RoomLoanInfo roomLoanInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("aFMortgaged.*");
		view.getSelector().add("aFMortgaged.transactor.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		
		HashSet afmStateSet = new HashSet();
		afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.BANKFUND_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));

		view.setFilter(filter);
		
		RoomLoanCollection roomLoanCol = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		if(roomLoanCol!=null && !roomLoanCol.isEmpty()){
			roomLoanInfo = roomLoanCol.get(0);
		}
		
		return roomLoanInfo;
	}
	
	/**
	 * ����ѡ�еķ��䣬��䷿�䰴�ҵ�����
	 * @throws BOSException 
	 */
	private void fillRoomDetail(RoomInfo room) throws BOSException{
		this.fillRoomBaseInfo(room);
		
		this.fillRoomLoanInfo(room);
	}
	
	/**
	 * ��䷿�������Ϣ
	 * @param room
	 */
	private void fillRoomBaseInfo(RoomInfo room){
	
	}
	
	/**
	 * ��䷿�䰴����Ϣ
	 * @param room
	 * @throws BOSException 
	 */
	private void fillRoomLoanInfo(RoomInfo room) throws BOSException{
		//����հ�����Ϣ��
		//this.tblApproach.removeRows();
		//this.tblData.removeRows();
		
		//���ð������
//		KDTSelectBlock block = this.tblMain.getSelectManager().get();
//		int left = block.getLeft();
//		int top = block.getTop();
//		ICell cell = this.tblMain.getCell(top, left);
//		//���ݷ��䵥Ԫ�ı���ɫ���жϷ����Ƿ��Ѱ�����
//		if(cell.getStyleAttributes().getBackground().equals(this.colorOfFinish)){
//			//this.AfmPane.setVisible(true);
//			
//			//�õ�����İ�����Ϣ
//			RoomLoanInfo loanInfo = this.getRoomLoanInfo(room);
//			
//			//���ð��ҽ���
//			RoomLoanAFMEntrysCollection appEntry = loanInfo.getAFMortgaged();
//			if(appEntry!=null && !appEntry.isEmpty()){
//				for(int i=0; i<appEntry.size(); i++){
//					RoomLoanAFMEntrysInfo entry = appEntry.get(i);
//					if(entry.isIsAOrB()){  //���̷�¼
//						/*IRow row = this.tblApproach.addRow();
//						row.getCell("approachName").setValue(entry.getApproach());
//						row.getCell("promiseDate").setValue(entry.getPromiseFinishDate());
//						row.getCell("isFinish").setValue(new Boolean(entry.isIsFinish()));
//						row.getCell("actFinishDate").setValue(entry.getActualFinishDate());
//						row.getCell("operator").setValue(entry.getTransactor());
//						row.getCell("remark").setValue(entry.getRemark());
//					}*/
//					else{  //���Ϸ�¼
//						/*IRow row = this.tblData.addRow();
//						row.getCell("dataName").setValue(entry.getApproach());
//						row.getCell("isReady").setValue(new Boolean(entry.isIsFinish()));
//						row.getCell("bizDate").setValue(entry.getActualFinishDate());
//						row.getCell("remark").setValue(entry.getRemark());*/
//					}
//				}
//			}
//		}
//		else{
//			//this.AfmPane.setVisible(false);
//		}
	}
}