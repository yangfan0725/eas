package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryInfo;
import com.kingdee.eas.fdc.basedata.AdjustReasonFactory;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

public class DynamicCostImport extends AbstractDataTransmission{
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";
	
	private static final Logger logger = CoreUIObject.getLogger(DynamicCostImport.class);

	DynamicCostInfo info = null;
	//DynamicCostInfo(�������ɱ�)-> CostAccountInfo(��Ŀ)-> CurProjectInfo(����) 
	CostAccountInfo maininfo = null;
	CurProjectInfo subinfo = null;
	
	//DynamicCostInfo-> adjustEntrys(������Ϣ��¼)
	AdjustRecordEntryInfo areInfo =  null;
	
	//DynamicCostInfo-> IntendingCostEntryInfo(�������ɱ���¼)
	IntendingCostEntryInfo iceInfo = new IntendingCostEntryInfo();
	
	// У��
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
	throws TaskExternalException {
		int seq = 0;
		   
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			// (������Ϣ��¼)
			AdjustRecordEntryInfo areEntry = transmitadjustEntrys(hsData, ctx,info );
			seq = info.getAdjustEntrys().size() + 1;
			areEntry.setSeq(seq);
			areEntry.setParent(info);
			info.getAdjustEntrys().add(areEntry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		
		return info;
	}
	
	
	
	//�ύ��ʱ�� �������ݿ����Ƿ������ͬ�����ݣ����ھ͸������ݿ� �������ھ� ����
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof DynamicCostInfo == false) {
			return;
		}
		
		try {  
	        DynamicCostInfo billBase = (DynamicCostInfo) coreBaseInfo;
			CostAccountInfo costInfo = billBase.getAccount();//��DynamicCost���гɱ���Ŀ��id
			String idName = costInfo.getId().toString();
	        //��ѯDynamicCost�� ȷ���Ƿ���  �����ɱ���Ŀ
			DynamicCostInfo syCostin = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(
					this.getView("account", idName)).get(0);
			
			String id = "";
			AdjustRecordEntryInfo  adReEntry = null;
			if (syCostin != null) {
				id = syCostin.getId().toString();
				adReEntry = billBase.getAdjustEntrys().get(0);//ֻ��һ����¼
			}
			
			if (StringUtil.isEmptyString(id)) {
				getController(ctx).addnew(coreBaseInfo);
			} else {
				adReEntry.setParent(syCostin);
				adReEntry.setSeq(syCostin.getAdjustEntrys().size()+1);
				AdjustRecordEntryFactory.getLocalInstance(ctx).addnew(adReEntry);
			}

		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}

	}
	
	
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = DynamicCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	//DynamicCostInfo-> CostAccountInfo(��Ŀ)-> CurProjectInfo(����) 
	private DynamicCostInfo transmitHead(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		
		info = new DynamicCostInfo();
		//DynamicCostInfo-> CostAccountInfo(��Ŀ)-> CurProjectInfo(����) 
		maininfo = new CostAccountInfo();
		subinfo = new CurProjectInfo();

		//ȡֵ
		String costOrg_number = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject$costOrg_number");//��֯����
		String curProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject_longNumber");//*���̱���
		String curProject_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject_name_l2");//������Ŀ����
		String FAccount_number = FDCTransmissionHelper.getFieldValue(hsData, "FAccount_number");//*��Ŀ����
		FAccount_number = FAccount_number.replace('!', '.');
		String FAccount_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAccount_name_l2");//������Ŀ����
		//��֤��ʽ�Ƿ���ȷ  �Ƿ�Ϊ��   �����Ƿ񳬳�
		FDCTransmissionHelper.valueFormat("��֯����", costOrg_number, "String", false, 80);
		FDCTransmissionHelper.valueFormat("���̱���", curProject_longNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("������Ŀ����", curProject_name_l2, "String", false, 200);
		FDCTransmissionHelper.valueFormat("��Ŀ����", FAccount_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("�ɱ���Ŀ����", FAccount_name_l2, "String", false, 200);
		//���ݿ�У��  
		//DynamicCostInfo-> CostAccountInfo(��Ŀ)-> CurProjectInfo(����) 
		FullOrgUnitInfo finfo = null;//��֯
		CurProjectInfo cpinfo = null;//������Ŀ
		CostAccountInfo cainfo = null;//��Ŀ
		CostCenterOrgUnitInfo ojkk = null;//�ɱ�����
		
		try {
			//������Ŀ
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", curProject_longNumber.replace('.', '!'))).get(0);
			if (cpinfo == null) { // ������Ŀ��ϵͳ�в�����
				FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectNumberNotFound"), "");
			}
			String id = cpinfo.getCostCenter().getId().toString();
			ojkk = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", id)).get(0);
			String costCenterName = ojkk.getLongNumber();//�ɱ����ĳ�����
			
			//�ɱ���������   Ϊ��ȡ����֯����  ȡ������Ŀ�������Ӧ�ɱ���������
			if(costOrg_number.trim().equals("") || costOrg_number==null){//Ϊ�յ�ʱ��
				finfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();//ǿת���̶���-����֯����
			}else{//��Ϊ�յ�ʱ��
				if (!costOrg_number.replace('.', '!').equals(costCenterName)) {// ��д����
					// ������Ŀ����Ӧ�ĳɱ����Ĳ����ڣ�
					FDCTransmissionHelper.isThrow(getResource(ctx, "FullOrgUnitNotFound"), "");
				}else{//û����д����
					finfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();//ǿת���̶���-����֯����
				}
			}
			
			// ��Ŀ
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("codingnumber", FAccount_number, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("curproject", cpinfo.getId().toString(), CompareType.EQUALS));
			view.setFilter(filter);// �õ������еĿ�Ŀ
			cainfo = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view).get(0);
			if (cainfo == null) {// �ɱ���Ŀ������ϵͳ�в����ڣ�
				FDCTransmissionHelper.isThrow(getResource(ctx, "kmbmzxtzbcz"), "");
			}
		
			
			//����ֵ
			//DynamicCostInfo-> CostAccountInfo(��Ŀ)-> CurProjectInfo(����) 
			subinfo.setFullOrgUnit(finfo);//���ɱ����ķ��� ������Ŀ
			subinfo.setNumber(cpinfo.getLongNumber());//������Ŀ����
			subinfo.setName(cpinfo.getName());//������Ŀ����
			//����֯����  ������Ŀ���� ������Ŀ����  ���õ� �ɱ�����CostAccountInfo��
			maininfo.setCurProject(subinfo);
			maininfo.setNumber(cainfo.getNumber());//�ɱ���Ŀ����
			maininfo.setName(cainfo.getName());//�ɱ���Ŀ����
			maininfo.setId(cainfo.getId());
			//���ɱ���Ŀ ��֯����  ������Ŀ���� ������Ŀ����  ���õ��������ɱ�ʵ�� DynamicCostInfo��
			info.setAccount(maininfo);
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
    //adjustEntrys(������Ϣ��¼)
	private AdjustRecordEntryInfo transmitadjustEntrys(Hashtable hsData, Context ctx ,DynamicCostInfo dcMainInfo)
	throws TaskExternalException {
		//DynamicCostInfo-> adjustEntrys(������Ϣ��¼)
		areInfo = new AdjustRecordEntryInfo();
		
		//ȡֵ
		String costAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_costAmount");//*�������
		String product_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$product_name_l2");//������Ʒ
		String adjustType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjustType_name_l2");//��������
		String adjustReason_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjustReason_name_l2");//����ԭ��
		String adjuster_number = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjuster_number");//*�����˱���
		String adjustDate = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_adjustDate");//*����ʱ��
		String description = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_description");//˵��
		String workload = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_workload");// ������
		String unit = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_unit");// ��λ
		String price = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_price");// ����
		
		//��ʽУ��   
		FDCTransmissionHelper.bdValueFormat("�������", costAmount,  true, 13,2);
		FDCTransmissionHelper.valueFormat("������Ʒ", product_name_l2, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��������", adjustType_name_l2, "String", false, 80);
		FDCTransmissionHelper.valueFormat("����ԭ��", adjustReason_name_l2, "String", false, 200);
		FDCTransmissionHelper.valueFormat("�����˱���", adjuster_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("����ʱ��", adjustDate, "Date", true, 80);
		FDCTransmissionHelper.valueFormat("˵��", description, "String", false, 200);
		FDCTransmissionHelper.valueFormat("������", workload, "Double", false, -1);
		// ��λ��������д���ǷǱ�¼�����Ҫ�жϿ�
		FDCTransmissionHelper.valueFormat("��λ", unit, "String", false, 200);
		this.bdValueFormat("����", price, false, 13, 4);
		
		//���ݿ�У��//DynamicCostInfo-> adjustEntrys(������Ϣ��¼)
		ProductTypeInfo prinfo = null;// ������Ʒ
		AdjustTypeInfo atinfo = null;//��������
		AdjustReasonInfo arinfo = null;//����ԭ��
		UserInfo userinfo = null;//������
		MeasureUnitInfo muinfo = null;// ��λ

		try {
			//������Ʒ
			prinfo = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(this.getView("name", product_name_l2)).get(0);
		    
			if(!StringUtils.isEmpty(adjustType_name_l2))
			{//��������
				atinfo = AdjustTypeFactory.getLocalInstance(ctx).getAdjustTypeCollection(this.getView("name", adjustType_name_l2)).get(0);
				if(atinfo==null){
					// ����������ϵͳ�в�����
					FDCTransmissionHelper.isThrow(getResource(ctx, "tzlxzxtzbcz"), "");
				}else
				{
					areInfo.setAdjustType(atinfo);//�������� FAdjustEntrys$adjustType_name_l2
				}
			}
			
			//����ԭ��
			arinfo = AdjustReasonFactory.getLocalInstance(ctx).getAdjustReasonCollection(this.getView("name", adjustReason_name_l2)).get(0);
			
			//������
			userinfo = UserFactory.getLocalInstance(ctx).getUserCollection("where number='"+adjuster_number+"'").get(0);
			if (userinfo == null) {// ��������ϵͳ�в�����
				FDCTransmissionHelper.isThrow(getResource(ctx, "tzrzxtzbcuz"), "");
			}
			
			if (!StringUtils.isEmpty(unit.trim())) {
				// �Ӽ�����λ���õ� ��λ��Ϣ
				muinfo = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(this.getView("name", unit)).get(0);
				if (muinfo == null) {// ��λ��ϵͳ�в�����
					FDCTransmissionHelper.isThrow(getResource(ctx, "MeasureUnitNotFound"), "");
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//�������ɱ�����
		CostAccountInfo costAccObj =  dcMainInfo.getAccount();//�õ��ɱ���Ϣ
		String costAccName = "";
		if(costAccObj!=null){
			 costAccName = costAccObj.getName();//�ɱ���Ŀ����
		}
		//���ݿ����У����ǵ���ı����û�е��ֶ�
		areInfo.setAdjustAcctName(costAccName);
		areInfo.setAdjusterName(userinfo.getName());
		
		
		//DynamicCostInfo-> AdjustRecordEntryInfo(������Ϣ��¼)
		areInfo.setCostAmount(FDCTransmissionHelper.strToBigDecimal(costAmount));//�������
		areInfo.setProduct(prinfo);//������Ʒ
		areInfo.setAdjustReason(arinfo);//����ԭ��FAdjustEntrys$adjustReason_name_l2
		areInfo.setAdjuster(userinfo);//*�����˱���FAdjustEntrys$adjuster_number
		areInfo.setAdjustDate(FDCTransmissionHelper.strToDate(adjustDate));//*����ʱ��FAdjustEntrys_adjustDate
		areInfo.setDescription(description);//˵��FAdjustEntrys_description
		areInfo.setWorkload(FDCTransmissionHelper.strToBigDecimal(workload));// ������
		areInfo.setUnit(unit);// ���õ�λ
		areInfo.setPrice(FDCTransmissionHelper.strToBigDecimal(price));// ����

		return areInfo;
	}
	

	//������ͼ  
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	/**
	 * �õ���Դ�ļ�
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	/**
	 * �ж� �����͵������Ƿ�Ϸ�  �������Ƿ�Ϊ�ա� ���ݵĳ����ǲ��ǹ���   �ķ���  
	 * name �������� value ֵ  b�Ƿ���� iv����λ fvС��λ
	 */
	public  void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("(-{0,1})([1-9]\\d{0,"+(iv-1)+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})||(--{0,1})([1-9]\\d{0,"+(iv-1)+"})")){
				FDCTransmissionHelper.isThrow(name,"������ 1��"+iv+"λ������� 1��"+fv+"λС�����ɣ�" );
    		}
		}else{
			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
				FDCTransmissionHelper.isThrow(name,"����Ϊ�գ�");	
			}
		}
	}
}
