package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.custom.purchasebaseinfomation.IPurchasebaseinfoFacade;
import com.kingdee.eas.custom.purchasebaseinfomation.PricingApproachInfo;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseAuthorInfo;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchasebaseinfoFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * 
 * @author kingdee0001
 *
 */
public class InviteProjectEditUICTEx extends InviteProjectEditUI
{
	public InviteProjectEditUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}	
	private static final String PRICING_APPROACH_QUERY = "com.kingdee.eas.custom.purchasebaseinfomation.app.PricingApproachQuery";
	private static final String PURCHASE_AUTHORITY = "com.kingdee.eas.custom.purchasebaseinfomation.app.PurchaseAuthorQuery";
	private static final String PURCHASE_CATEGORY = "com.kingdee.eas.custom.purchasebaseinfomation.app.PurchaseCategoryQuery";
	private static EntityViewInfo viewPurchase ;
	private static EntityViewInfo view ;
	static
	{
		 view = new EntityViewInfo();
         FilterInfo filter = new FilterInfo();
         filter.getFilterItems().add(new FilterItemInfo("isEnabled", 1, CompareType.EQUALS));
         view.setFilter(filter);     
     	 viewPurchase = new EntityViewInfo();
         FilterInfo filter2 = new FilterInfo();
         filter2.getFilterItems().add(new FilterItemInfo("BIMUDF0001", 1, CompareType.EQUALS));
         viewPurchase.setFilter(filter2);
	}	
	/**
	 * F7������ʾ����F7���ø�ʽ�Լ���̬��ʼ�����õĹ���
	 */
	private void initF7Control()
	{		
         setF7Format(pbPricingApproach,1,PRICING_APPROACH_QUERY); 
         setF7Format(prmptPurchaseCategory,2,PURCHASE_CATEGORY);
         setF7Format(prmptPurchaseAuthority,2,PURCHASE_AUTHORITY);
	}	
	private void setF7Format(KDBizPromptBox  prmpt,int type,String queryInfo)
	{
		prmpt.setDisplayFormat("$name$");
		prmpt.setEditFormat("$name$");
		prmpt.setCommitFormat("$number$");
		switch (type)
		{
			case 1:
			{
				prmpt.setEntityViewInfo(view);
			
				break;
			}case 2:
			{	
				prmpt.setEntityViewInfo(viewPurchase);				
				break;
			}default:
			{
				break;
			}
		}
		prmpt.setQueryInfo(queryInfo);		
	}
		
	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		initF7Control();
		hideUnNecessaryComponents();
		combProcurementType.setRequired(false); //ȥ��ϵͳ��У��
		combAuth.setRequired(false);//ȥ��ϵͳ��У��
		prmptPurchaseCategory.setRequired(true); //��������F7�ֶ�У��
		prmptPurchaseAuthority.setRequired(true); //��������F7�ֶ�У��
		pbPricingApproach.setRequired(true);//��������F7�ֶ�У��
		
	}
	
	private void hideUnNecessaryComponents()
	{
		contPriceMode.setVisible(false);
		contProcurementType.setVisible(false);
		contAuth.setVisible(false);
	}
	   protected void verifyInputForSubmint()
       throws Exception
   {
       verifyInputForSave();
   }
	
    protected void verifyInput(ActionEvent e)
    throws Exception
    {
    	//ϵͳ֮ǰ��У��
	    FDCClientVerifyHelper.verifyEmpty(this, prmtInviteType);
	    FDCClientVerifyHelper.verifyEmpty(this, prmtInviteForm);
	    FDCClientVerifyHelper.verifyEmpty(this, prmtPurchaseMode);
	    FDCClientVerifyHelper.verifyEmpty(this, prmtScalingRule);
//	    FDCClientVerifyHelper.verifyEmpty(this, combProcurementType);
//	    FDCClientVerifyHelper.verifyEmpty(this, combAuth);
    	
    	//�����ֶε�У��
	    FDCClientVerifyHelper.verifyEmpty(this, prmptPurchaseCategory);
	    FDCClientVerifyHelper.verifyEmpty(this, prmptPurchaseAuthority);
	    FDCClientVerifyHelper.verifyEmpty(this, pbPricingApproach);

	    
	    if(isSaveAction())
	    {
	        verifyInputForSave();
	        return;
	    } else
	    {
	        verifyInputForSubmint();
	       // super.verifyInput(e);
	        return;
	    }
    }
	
	
	  protected void verifyInputForSave()
      throws Exception
  {
//		  if(getNumberCtrl().isEnabled())
//	            FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
//      if(getNumberCtrl().isEnabled())
//          FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
//      InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo)prmtPurchaseMode.getValue();
//      if(modeInfo.getType() != InvitePurchaseModeEnum.STRATEGY && prjTable.getRowCount() == 0)
//      {
//          MsgBox.showWarning("\u5206\u5F55\u4E0D\u80FD\u4E3A\u7A7A\uFF01");
//          SysUtil.abort();
//      }
//      checkPrjTableRequired();
		  super.verifyInputForSave();
     
  }
	  
  
	  
	  //���ӷ�д
	  @Override
	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		
		super.actionSave_actionPerformed(e);
		Object obj = this.editData.getId();//��õ���ID	
		if(obj==null)
		{
			return;
		}	
		String result = reUpdate(obj.toString());
		if("ʧ��".equals(result))
		{
			MsgBox.showInfo("���ٴα���,���²��ɹ�");
		}
	}
	  
	  private String reUpdate(String id ) throws BOSException
	  {
			//F7��Ϊ�գ� ��beforeSave��ʱ���Ѿ��ж��Ƿ�Ϊ��
			PurchaseCategoryInfo purchaseCategory =  (PurchaseCategoryInfo)  prmptPurchaseCategory.getValue();//�ɹ�����
			PurchaseAuthorInfo purchaseAuthority = (PurchaseAuthorInfo)prmptPurchaseAuthority.getValue(); //�ɹ�Ȩ��
			PricingApproachInfo pricingApproach =  (PricingApproachInfo)  pbPricingApproach.getValue(); //���۷�ʽ
			String  pcWorkFlow= purchaseCategory.getWorkflow();
			String pauWorkFlow = purchaseAuthority.getWorkflow();
			String papWorkFlow = pricingApproach.getWorkflow();
			ArrayList baseInfos = new ArrayList();
			baseInfos.add(pcWorkFlow);
			baseInfos.add(pauWorkFlow);
			baseInfos.add(papWorkFlow);
			//��������
			IPurchasebaseinfoFacade  updateToEnum = PurchasebaseinfoFacadeFactory.getRemoteInstance();
			String result = updateToEnum.updateBaseInfoToEnum(baseInfos, id);
			return result;
	  }
	  

	  
	  
	  //���ӷ�д
	  @Override
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.actionSubmit_actionPerformed(e);
		Object obj = this.editData.getId();//��õ���ID	
		if(obj==null)
		{
			return;
		}	
		String result = reUpdate(obj.toString());
		if("ʧ��".equals(result))
		{
			MsgBox.showInfo("���ٴα���,���²��ɹ�");
		}
	}
	  
//	    private void checkPrjTableRequired()
//        throws BOSException, EASBizException
//    {
//        for(int i = 0; i < prjTable.getRowCount(); i++)
//        {
//            if(prjTable.getCell(i, "name").getValue() == null)
//            {
//                MsgBox.showWarning((new StringBuilder("\u7B2C")).append(i + 1).append("\u884C\u5DE5\u7A0B\u9879\u76EE\u4E0D\u80FD\u4E3A\u7A7A").toString());
//                SysUtil.abort();
//            }
//            checkPlan = false;
//            CurProjectInfo project = (CurProjectInfo)prjTable.getCell(i, "name").getValue();
//            FilterInfo filter = new FilterInfo();
//            EntityViewInfo view = new EntityViewInfo();
//            SelectorItemCollection sic = new SelectorItemCollection();
//            sic.add("*");
//            sic.add("parent.*");
//            filter.getFilterItems().add(new FilterItemInfo("parent.project.id", project.getId()));
//            view.setFilter(filter);
//            view.setSelector(sic);
//            InviteMonthPlanEntrysCollection coll = InviteMonthPlanEntrysFactory.getRemoteInstance().getInviteMonthPlanEntrysCollection(view);
//            ProgrammingContractCollection validPCColl = null;
//            try
//            {
//                validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
//                if(validPCColl.size() <= 0)
//                {
//                    filter = new FilterInfo();
//                    filter.getFilterItems().add(new FilterItemInfo("project.id", project.getId()));
//                    filter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
//                    if(ProgrammingFactory.getRemoteInstance().exists(filter))
//                        checkPlan = true;
//                } else
//                {
//                    checkPlan = true;
//                }
//            }
//            catch(EASBizException e1)
//            {
//                checkPlan = true;
//            }
//            if(checkPlan && prjTable.getCell(i, "programmingName").getValue() == null)
//            {
//                MsgBox.showWarning((new StringBuilder("\u7B2C")).append(i + 1).append("\u884C\u91C7\u8D2D\u8BA1\u5212\u4E0D\u80FD\u4E3A\u7A7A").toString());
//                SysUtil.abort();
//            }
//        }
//
//    }
	    
	    
	  //  private void checkPrjTableRequired() throws BOSException, EASBizException {
//			for( int i=0; i<prjTable.getRowCount(); i++ ) {
//				if(prjTable.getCell(i, "name").getValue()==null  ) {
//					MsgBox.showWarning("��" + (i+1) + "�й�����Ŀ����Ϊ��");
//					SysUtil.abort();
//				}
//				this.checkPlan=false;
//				CurProjectInfo project=(CurProjectInfo) prjTable.getCell(i, "name").getValue();
//				FilterInfo filter = new FilterInfo();
//				
//				EntityViewInfo view = new EntityViewInfo();
//				SelectorItemCollection sic = new SelectorItemCollection();
//				sic.add("*");
//				sic.add("parent.*");
//				filter.getFilterItems().add(new FilterItemInfo("parent.project.id",project.getId()));
//				 
//				view.setFilter(filter);
//				view.setSelector(sic);
//				 
//				InviteMonthPlanEntrysCollection coll = InviteMonthPlanEntrysFactory.getRemoteInstance().getInviteMonthPlanEntrysCollection(view);
//				ProgrammingContractCollection validPCColl=null;
//				try {
//					 validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
//					 if(validPCColl.size() <= 0) {
//						 filter = new FilterInfo();
//						 filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId()));
//						 filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//						 if(ProgrammingFactory.getRemoteInstance().exists(filter)){
//							 this.checkPlan = true;
//						 }
//					 }else {
//						 this.checkPlan = true;
//					 }
//				 }catch(EASBizException e1) {
//					 this.checkPlan = true;
//				 }
//				 if(this.checkPlan&&prjTable.getCell(i, "programmingName").getValue()==null) {
//					MsgBox.showWarning("��" + (i+1) + "�вɹ��ƻ�����Ϊ��");
//					SysUtil.abort();
//				}
//			}
//		}    
	  
	  
//		protected void prjTable_editStopped( KDTEditEvent e ) throws Exception {
//			int colIndex = e.getColIndex();
//			IRow row = prjTable.getRow(e.getRowIndex());
//			if (row == null ) {
//				return;
//			}
//			String key = prjTable.getColumnKey(colIndex);
//			if ("name".equals(key)) { //������Ŀ����
//				
//				CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
//				if(project==null) {
//					return;
//				}else if(!project.isIsLeaf()) {
//					row.getCell("name").setValue(null);
//					return;
//				}
//				
//				CurProjectInfo oldProject = (CurProjectInfo) e.getOldValue();
//				if( oldProject != null && project.getId().equals(oldProject.getId())) {
//					return;
//				}
//				
//				if(this.checkProjectExist(project,row.getRowIndex()) ) {
//					MsgBox.showConfirm2("��ѡ��Ŀ��"+project.getName() + "�Ѵ���" );
//					row.getCell("name").setValue(null);
//					return;
//				}
//				
//				if( oldProject != null ) {//������ֵ���������Ŀ�����У�������ʼ��
//					removeDataChangeListener(this.prmtPurchaseMode);
//				    row.getCell("name").setValue(oldProject);	//������ֵ���Ա���ҿ�ʼ����λ��
//				    row = this.clearPlans(oldProject, e.getRowIndex());
//				    row.getCell("name").setValue(project);
//				    addDataChangeListener(this.prmtPurchaseMode);
//				    
//				    int start = prjTable.getColumnIndex("name") + 1;
//				    for( int i=start; i<prjTable.getColumnCount(); i++ ) {
//				    	row.getCell(i).setValue(null);
//				    }
//				}
//				
//				row.getCell("number").setValue(project.getLongNumber().replaceAll("!", "."));
////				row.getCell("name").getStyleAttributes().setLocked(true);
//				 for( int i=3; i<prjTable.getColumnCount(); i++ ) {
//					 prjTable.getCell(0, i).getStyleAttributes().setLocked(false);
//					 prjTable.getCell(0, i).getStyleAttributes().setBackground(Color.WHITE);
//				 }
//			}else if("programmingName".equals(key)) { //�ɹ��ƻ�����
//				Object value = e.getValue();
//				if( value instanceof InviteMonthPlanEntrysInfo) {//��ѡ
//					InviteMonthPlanEntrysInfo info = (InviteMonthPlanEntrysInfo) value;
//					InviteMonthPlanEntrysCollection coll = new InviteMonthPlanEntrysCollection();
//					coll.add(info);
//					
//					ProgrammingContractCollection validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
//					CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
//					this.checkProgrammingContractCollection(validPCColl, project, row.getRowIndex());
//					if(validPCColl.size()>0) {
//						row.getCell("programmingName").setValue(validPCColl.get(0));
//						row.getCell("programmingId").setValue(validPCColl.get(0).getId());
//					}
//				}else if( value instanceof Object[] ) {//��ѡ
////					InviteMonthPlanEntrysInfo[] infos = (InviteMonthPlanEntrysInfo[]) value;
//					Object[] valueArray = (Object[]) value;
//					InviteMonthPlanEntrysCollection coll = new InviteMonthPlanEntrysCollection();
//					for(int i=0; i<valueArray.length; i++) {
//						coll.add((InviteMonthPlanEntrysInfo) valueArray[i]);
//					}
//					
//					ProgrammingContractCollection validPCColl=null;
//					try {
//						validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
//					}catch(EASBizException e1) {
//						row.getCell("programmingName").setValue(null);
//						row.getCell("programmingId").setValue(null);
//						MsgBox.showWarning(e1.getMessage());
//						return;
//					}
//					
//					if(validPCColl.size()<coll.size() ) {
////						row.getCell("programmingName").setValue(null);
//						MsgBox.showWarning("��ѡ�ƻ����б������б��������õļƻ���");
//					}
//					
//					CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
//					this.checkProgrammingContractCollection(validPCColl, project, row.getRowIndex());
//					int selectedIndex = row.getRowIndex();
//					CurProjectInfo prjInfo = (CurProjectInfo) row.getCell("name").getValue();
//					for(int i=0; i<validPCColl.size(); i++) {
//						if(i>0) {
//							row = prjTable.addRow(selectedIndex);
//							InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
//							row.setUserObject(entry);
//						}
//						
//						row.getCell("number").setValue(prjInfo.getLongNumber().replaceAll("!", "."));
//						row.getCell("name").setValue(prjInfo);
//						row.getCell("programmingName").setValue(validPCColl.get(i));
//						row.getCell("programmingId").setValue(validPCColl.get(i).getId());
//					}
//					
//					if(validPCColl.size() == 0) {
//						row.getCell("programmingName").setValue(e.getOldValue());
//					}
//				}
//				
//				KDTMergeManager merge = prjTable.getMergeManager();
//				CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
//				if(project == null) {
//					return;
//				}
//				
//				int end = this.getProjectEndIndex(project, row.getRowIndex());
//				int start = this.getProjectStartIndex(project, row.getRowIndex());
//				merge.mergeBlock(start, 1, end, 1);
//				merge.mergeBlock(start, 2, end, 2);
//			}
//    }
//	  
//		private int getProjectEndIndex( CurProjectInfo project, int selectedIndex ) {
//			for( int i=selectedIndex+1; i<prjTable.getBody().size(); i++ ) {
//				CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
//				if( prj == null || !prj.getId().equals(project.getId()) ) {
//					return i-1;
//				}
//			}
//			
//			
//			return prjTable.getBody().size()-1;
//		}
//		
//		
//		/**
//		 * ������Ŀ��ʼ��λ�á�
//		 * @param project
//		 * @param selectedIndex
//		 * @return
//		 */
//		private int getProjectStartIndex( CurProjectInfo project, int selectedIndex ) {
//			for( int i=selectedIndex-1; i>=0; i-- ) {
//				CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
//				if( prj==null || !prj.getId().equals(project.getId()) ) {
//					return i+1;
//				}
//			}
//			
//			return 0;
//		}
	    
}
