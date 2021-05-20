package com.kingdee.eas.fdc.contract.app;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillEntryImport extends AbstractDataTransmission{

	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	
	ContractBillInfo  maininfo = null;//��ͬ
	ContractBillEntryInfo info = null;//��ͬ��¼��ϸ��Ϣ
	
	private static final String strName[] = new String[]{"��ע","�Ƿ񵥶�����","���Ƴɱ��Ľ��","��Ӧ����ͬ����","��Ӧ����ͬ����"};
	private static final String strData[] = new String[]{"40","20","30","40","40"};
	private static final String strRowKey[] = new String[]{"","lo","am","nu","na"};
	
	// ����һ���Լ�������
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			//factory = ContractBillEntryFactory.getLocalInstance(ctx);
			factory = ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}
	

	public String getMainField() {
		return "FCodingNumber";
	}

	//У��
	public CoreBaseInfo transmit(Hashtable htable, Context ctx)
	throws TaskExternalException {
        int seq = 0;// �������
        String oldValue="",newValue="";
        List conlist = new ArrayList();
 
		try {
			int listLen = htable.size();//excel��������
			for(int i=0 ;i<listLen; i++){
				Hashtable subHashTable = (Hashtable)htable.get(new Integer(i));//ȡ����i��Ԫ��
				String conNumber = (String) ((DataToken) subHashTable.get("FCodingNumber")).data;//��i�еĺ�ͬ����
				if(conNumber.trim().equals("") || conNumber==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"Import_fContractCodingNumberNotNull"));//��ͬ���벻��Ϊ��
				}
				newValue = conNumber;
				
				if(!newValue.trim().equals(oldValue)){//�µĺ�ͬ  ����ȡ��ͬ��ͷ���������ϸ��Ϣ��¼
					if(maininfo!=null){//ȡ�º�ͬ��ʱ���Ƚ��ϸ���ͬ�����
						this.checkDefCount(ctx);//��֤�ϸ���ͬ�ĺ�ͬ��Ϣ�����Ƿ�Ϸ�
						conlist.add(maininfo);//����һ����ͬ����Ϣ����list
					}
					
					//ȡ��ͷ
					maininfo = transmitHead(subHashTable, ctx);
					if (maininfo == null) {//û��ȡ�� ��ͷ
						FDCTransmissionHelper.isThrow(getResource(ctx, "htbmzxtzbcz"));
					}
					
					//�����¼
					List specialEntryList = transmitSpecialEntry(subHashTable, ctx , maininfo);
					Iterator it = specialEntryList.iterator();
					while(it.hasNext()){
						ContractBillEntryInfo cbEntry = (ContractBillEntryInfo)it.next();
			            seq = maininfo.getEntrys().size() + 1;
			            cbEntry.setSeq(seq);
			            cbEntry.setParent(maininfo);
			            maininfo.getEntrys().add(cbEntry);
					}
				}
				
				//(��¼)
				ContractBillEntryInfo cbEntry = transmitEntry(subHashTable, ctx, maininfo);
	            seq = maininfo.getEntrys().size() + 1;
	            cbEntry.setSeq(seq);
	            cbEntry.setParent(maininfo);
	            maininfo.getEntrys().add(cbEntry);
	            oldValue=newValue;
	            
	    		if(i==listLen-1){//���һ�е�ʱ��
	    			this.checkDefCount(ctx);//��֤���һ����ͬ�����Ƿ�Ϸ�
					conlist.add(maininfo);//�����һ����ͬ����list
				}
			}
			
			//������ͬ
			Iterator it = conlist.iterator();
			while(it.hasNext()){
				ContractBillInfo conInfo = (ContractBillInfo)it.next();//���һ����ͬ
				String fParentId = conInfo.getId().toString();//��¼�� FParentid ����
				
				ContractBillEntryCollection conEntryList =  conInfo.getEntrys();//�õ� ��¼list
				Iterator entryIt = conEntryList.iterator();
				while(entryIt.hasNext()){
	 				ContractBillEntryInfo conEntryInfo = (ContractBillEntryInfo)entryIt.next();
					//ͨ�����˲�ѯ�� ��¼����û����Ӧ�ļ�¼
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("parent",fParentId,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("detailDefID",conEntryInfo.getDetailDefID(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("detail",conEntryInfo.getDetail(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("rowkey",conEntryInfo.getRowKey(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("datatype",String.valueOf(conEntryInfo.getDataType().getValue()),CompareType.EQUALS));
					view.setFilter(filter);
			        ContractBillEntryInfo info = ContractBillEntryFactory.getLocalInstance(ctx).getContractBillEntryCollection(view).get(0);
					String id = "";
			        if(info!=null){
						id = info.getId().toString();
						conEntryInfo.setSeq(info.getSeq());//��Ų��ܱ�����
						conEntryInfo.setId(info.getId());//id���ܸ���
					}
			        //û�м�¼������
			        if (StringUtil.isEmptyString(id)) {
			        	ContractBillEntryFactory.getLocalInstance(ctx).addnew(conEntryInfo);
					} else {//�м�¼ ���޸�
						String defId = conEntryInfo.getDetailDefID();
						ContractBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(id), conEntryInfo);
					}
				}
			}
		} catch (TaskExternalException e) {
			maininfo = null;
			throw e;
		} catch (EASBizException e1) {
			maininfo = null;
		} catch (BOSException e2) {
			maininfo = null;
		}
		
		return null;
	}
	

    //��ͬ����
	private ContractBillInfo transmitHead(Hashtable htable, Context ctx)
	throws TaskExternalException {
		try {
			String FCodingNumber = (String) ((DataToken) htable.get("FCodingNumber")).data;//*��ͬ����
			//��ʽ�ж�  �Ƿ�Ϸ�   �Ƿ�Ϊ��  �Ƿ񳬹�����
			FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"),FCodingNumber,"String",true,80);//��ͬ����
			maininfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FCodingNumber)).get(0);
			if(maininfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FCodingNumber"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
		
		//����ֵ
		return maininfo;
	}
	
	
	//����ķ�¼
	private List transmitSpecialEntry(Hashtable htable, Context ctx ,ContractBillInfo cbinfo)
	throws TaskExternalException, BOSException {
		List list = new ArrayList();
		
		//ȡֵ   //excel����к�5�е�ֵ   
		String FRemark = getFieldValue(htable,"FRemark");//��ע
		String isAlongCont = getFieldValue(htable,"isAlongCont");//�Ƿ񵥶�����
		String FAmtWithoutCost = getFieldValue(htable,"FAmtWithoutCost");//���Ƴɱ��Ľ��
		String FMainContractNumber = getFieldValue(htable,"FMainContractNumber");//��Ӧ����ͬ����
		String FMainContractNname = getFieldValue(htable,"FMainContractNname");//��Ӧ����ͬ����
		
		//У�����ݵĸ�ʽ     
		FDCTransmissionHelper.valueFormat(getResource(ctx,"beizhu"),FRemark,"String",false,600);//��ע
		//�õ���ͬ����    ��ͬ����Ϊ�����򲹳��ͬʱ    �Ƿ񵥶�����,���Ƴɱ��Ľ��,��Ӧ����ͬ����������Ҫ��ʾ����
		String FContractPropert = cbinfo.getContractPropert().getValue();
		//��ͬ����Ϊ�����򲹳��ͬʱ    ���Ƴɱ��Ľ��   ��Ӧ����ͬ�������Ҫ��ʾ����       �����������ڽ�����ѡ��ģ����Ա�¼����������ν
		FContractPropert = FContractPropert.trim();
		if(FContractPropert.equals("THREE_PARTY") || FContractPropert.equals("SUPPLY")){
			FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoudandujisuan"), isAlongCont, true, "��","��",-1);//�Ƿ񵥶�����
			FDCTransmissionHelper.bdValueFormat(getResource(ctx,"bujichengbenjie"), FAmtWithoutCost, false, 15,4);//���Ƴɱ��Ľ��
			FDCTransmissionHelper.valueFormat(getResource(ctx,"duiyinzhuhetongbianma"),FMainContractNumber,"String",true,80);//��Ӧ����ͬ����
			
			//����ǵ��������������򲻼Ƴɱ��Ľ�����д��
			String iac = isAlongCont.trim();
			if(iac.equals("��") && !FAmtWithoutCost.trim().equals("")){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FAmtWithoutCost"));
			}
			//����ͬ����  ����  
			String number="";
			String name="";
			try {
				//����ͬ���벻Ϊ��
				if(!FMainContractNumber.trim().equals("")){
					//����ͬ����ֻ������������ĺ�ͬ,��������ǲ����ͬ����ѡ������2��״̬������У��ύ    
					//����ͬ���벻�����Լ�  //�������ѽ����  //��ͬһ��������Ŀ�µ�  //��ͬ���ͱ�������
					if(!this.cehckMainConNumber(cbinfo, FMainContractNumber, ctx)){//����ͬ������д����ȷ��ԭ���ǣ���ʾһ���
						FDCTransmissionHelper.isThrow(getResource(ctx,"zhtbmtxbzqyyrx"));
					}
					ContractBillInfo binfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FMainContractNumber)).get(0);
					if(binfo==null){ //����ͬ������ϵͳ�в�����
						FDCTransmissionHelper.isThrow(getResource(ctx,"zhtbmzxtzbcz"));
					}

					number = binfo.getId().toString();//����ͬ������Ҫ����дid
					name = binfo.getName();
					if(!name.equals(FMainContractNname)&&!FMainContractNname.trim().equals("") ){
						FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNumber"));
					}
				}
				//����ͬ����Ϊ�գ�����ͬ���ֲ�Ϊ��
				if((FMainContractNumber.trim().equals("")||FMainContractNumber==null)&&!FMainContractNname.trim().equals("")){
					FDCTransmissionHelper.isThrow(getResource(ctx,"txlzhtmchmtxzhtbm"));//��д������ͬ���ƣ�����Ҫ��д����ͬ����
				}
				
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//�������¼��ֵ����������
			String strValue[] = new String[5];
			strValue[0]=FRemark;
			strValue[1]=isAlongCont;
			strValue[2]=FAmtWithoutCost;
			strValue[3]=number;
			strValue[4]=name;
			
			//���ݺ�ͬ������  ���鿴�û���д����Ϣ������� �Ƿ������ݿ���
			String contypeid = cbinfo.getContractType().getId().toString();//��ͬ����id
            //�ں�ͬ��Ϣ��  �ҵ�  ��ͬ���Ͷ�Ӧ�ĺ�ͬ��Ϣ
			ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
			int len = cddiList.size();//��ͬ��Ϣ�ĸ���
			int ik = 0;
			if(len>0){//����к�ͬ��Ϣ �Ͳ���д��ע ���������û���κεĺ�ͬ��Ϣ�ͱ�¼��ע 
				ik=1;//�����������������ֵ���ֱ��Ǳ�ע���������㣬���Ƴɱ�������ͬ���룬����ͬ����  �������ֺ�ͬ��Ϣ �����±��1 ��ʼ 
			}
			for(int i=ik;i<strName.length;i++){
				info = new ContractBillEntryInfo();//��ͬ��¼��ϸ��Ϣ
				info.setDetail(strName[i]);//��ϸ��������
				info.setRowKey(strRowKey[i]);
				info.setContent(strValue[i]);//��ϸ����
				DataTypeEnum rnums  = DataTypeEnum.getEnum(Integer.parseInt(strData[i]));
				info.setDataType(rnums);//��������
				list.add(info);
			}
		}else{//���ǵ����
			FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoudandujisuan"), isAlongCont, false, "��","��",-1);
			FDCTransmissionHelper.bdValueFormat(getResource(ctx,"bujichengbenjie"), FAmtWithoutCost, false, 15,4);
			FDCTransmissionHelper.valueFormat(getResource(ctx,"duiyinzhuhetongbianma"),FMainContractNumber,"String",false,80);
			
			if(!isAlongCont.trim().equals("")){//��ͬ״̬Ϊֱ�Ӻ�ͬ��ʱ�� ������д�Ƿ񵥶����㣡
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_isAlongCont"));
			}else if(!FAmtWithoutCost.trim().equals("")){//��ͬ״̬Ϊֱ�Ӻ�ͬ��ʱ�򣬲�����д���Ƴɱ��Ľ�
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FAmtWithoutCost2"));
			}else if(!FMainContractNumber.trim().equals("")){//��ͬ״̬Ϊֱ�Ӻ�ͬ��ʱ�򣬲�����д��Ӧ����ͬ����!
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNumber2"));
			}else if(!FMainContractNname.trim().equals("")){//��ͬ״̬Ϊֱ�Ӻ�ͬ��ʱ�򣬲�����д��Ӧ����ͬ����!
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNname"));
			}
		}
		
		return list;
	}
	
	
	//��¼
	private ContractBillEntryInfo transmitEntry(Hashtable htable, Context ctx,ContractBillInfo cbinfo)
		throws TaskExternalException {
			
		info = new ContractBillEntryInfo();//��ͬ��¼��ϸ��Ϣ
       
		String FEntrys_detailDefID = getFieldValue(htable,"FEntrys_detailDefID");//*��ϸ��Ϣ�������
		String FEntrys_dataType = getFieldValue(htable,"FEntrys_dataType");//��������
		String FEntrys_detail = getFieldValue(htable,"FEntrys_detail");//*��ϸ����

		//��ʽ�ж�  �Ƿ�Ϸ�   �Ƿ�Ϊ��  �Ƿ񳬹�����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xiangxidingyibianma"),FEntrys_detailDefID,"String",true,80);//��ϸ��Ϣ�������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"shujuleixing"),FEntrys_dataType,"String",false,40);//��������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xiangxileirong"),FEntrys_detail,"String",false,600);//��ϸ����

		//���ݿ�У��
		ContractDetailDefInfo cddinfo = null;//��ϸ������붨��
		try {
	        //���ȸ��ݺ�ͬ������  ���鿴�û���д����Ϣ������� �Ƿ������ݿ���
			String contypeid = cbinfo.getContractType().getId().toString();//��ͬ����id
            //�ں�ͬ��Ϣ��  �ҵ�  ��ͬ���Ͷ�Ӧ����ϸ��Ϣ
			ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
			
			int len = cddiList.size();//��ͬ��Ϣ�ĸ���
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			//���˵�������where number=�û����� and (number=��ͬ��Ϣ or number=��ͬ��Ϣ)
			filter.getFilterItems().add(new FilterItemInfo("number",FEntrys_detailDefID,CompareType.EQUALS));
			Iterator it= cddiList.iterator();
			while(it.hasNext()){
				ContractDetailDefInfo item = (ContractDetailDefInfo)it.next();
				String strNumber = item.getNumber();
				filter.getFilterItems().add(new FilterItemInfo("number",strNumber,CompareType.EQUALS));
			}
			
			String strbuf = " #0 and (#1 ";
			for(int i=1; i<len; i++){
				strbuf = strbuf + " or #"+(i+1)+" ";
			}
			strbuf = strbuf + " ) ";
			filter.setMaskString(strbuf);
			view.setFilter(filter);
			
			cddinfo = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(view).get(0);
		    if(cddinfo==null){ //��ϸ������������ݿ��в����ڣ�
		    	FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FEntrys_detailDefID"));
		    }

		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow(e.toString());
		}
		
		//��֤��������
		String dt = FEntrys_dataType.trim();
		int ie = 0;
		if(dt.equals("��������")){
			ie=20;
		}else if(dt.equals("��ֵ����")){
			ie=30;
		}else if(dt.equals("�ַ�����")){
			ie=40;
		}else if(dt.equals("��������")){
			ie=10;
		}else if(dt.equals("��������")){
			ie=50;
		}else{//�����ģ�����ʾ������ȷ���������ͣ�
			FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FEntrys_dataType"));
		}
		DataTypeEnum dtenum = DataTypeEnum.getEnum(ie);//FEntrys_dataType
		
		//��¼������
		info.setDetailDefID(cddinfo.getId().toString());// ��ϸ��Ϣ�������
		info.setDetail(cddinfo.getName());//��ϸ����
		info.setContent(FEntrys_detail);//����
		info.setDataType(dtenum);//��������
        info.setParent(cbinfo);
        
		return info;
	}
	
	//������֤һ����ͬ����ϸ��Ϣ����
	private void checkDefCount(Context ctx) throws TaskExternalException,BOSException {
		String conPropert = maininfo.getContractPropert().getValue();//�õ���ͬ����    ��ͬ����Ϊ�����򲹳��ͬʱ   
		String contypeid = maininfo.getContractType().getId().toString();//��ͬ����id
        //�ں�ͬ��Ϣ��  �ҵ�  ��ͬ���Ͷ�Ӧ����ϸ��Ϣ
		ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
		
		//������ݿ���  ��¼�к�ͬ��Ϣ����
		ContractBillEntryCollection conBE = ContractBillEntryFactory.getLocalInstance(ctx).getContractBillEntryCollection(this.getView("parent",maininfo.getId().toString()));
		int entryLen = maininfo.getEntrys().size()-conBE.size();//�û��������ϸ��Ϣ���� 
		if(entryLen==0){
			FDCTransmissionHelper.isThrow(getResource(ctx, "qtxbz"));//����д��ע
		}
		int defCout = cddiList.size();//��ͬ��ʵ����ϸ��Ϣ�ĸ���
		//��ͬ����Ϊ�����򲹳��ͬʱ   ��ͬ��ʵ����ϸ��Ϣ����+4
		if(conPropert.trim().equals("THREE_PARTY") || conPropert.trim().equals("SUPPLY")){
			defCout = defCout+4;
		}
		if(entryLen!=defCout){//����������ڲ���
			FDCTransmissionHelper.isThrow(getResource(ctx,"contract")+maininfo.getNumber()+getResource(ctx,"drdxxxxgsysjqkbfh"));//�������ϸ��Ϣ������ʵ�����������
		}
	}

	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	
	
	//��� ����ͬ��������
	private boolean cehckMainConNumber(ContractBillInfo coninfo, String mainNumber, Context ctx) 
	throws BOSException, SQLException{
		//����ͬ���뷶Χ��
		//��ͬ���ʱ�����������ͬ���߲����ͬ��         //ֻ������������ĺ�ͬ��������ǲ����ͬ����ѡ������2��״̬������С��ύ��
		//�����ǵ�ǰ�ĺ�ͬ��         //����͵�ǰ��ͬ����ͬһ��������Ŀ��        //�������ѽ���ĺ�ͬ��       //��ͬ���ͱ������ã�
		MetaDataPK obj = new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
    	IQueryExecutor exec = QueryExecutorFactory.getLocalInstance(ctx,obj);
	
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		EntityViewInfo view = new EntityViewInfo();
		
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);//��ͬ���ʱ�����������ͬ���߲����ͬ
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled",Boolean.TRUE)); //��ͬ���ͱ�������
		
		Set stateSet = new HashSet();
		stateSet.add(FDCBillStateEnum.AUDITTED_VALUE);//ֻ������������ĺ�ͬ��������ǲ����ͬ����ѡ������2��״̬������С��ύ
		if(coninfo.getContractPropert().getValue().equals("SUPPLY")){
			stateSet.add(FDCBillStateEnum.AUDITTING_VALUE);
			stateSet.add(FDCBillStateEnum.SUBMITTED_VALUE);
		}
		filterItems.add(new FilterItemInfo("state", stateSet, CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE)); //�������ѽ���ĺ�ͬ
		filterItems.add(new FilterItemInfo("curProject.id",coninfo.getCurProject().getId().toString(),CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("id", coninfo.getId().toString(), CompareType.NOTEQUALS));
		filterItems.add(new FilterItemInfo("number", mainNumber, CompareType.EQUALS));
		  
		view.setFilter(filter);
		exec.setObjectView(view);
		
		IRowSet iRowSet = exec.executeQuery();	
        iRowSet.beforeFirst();
        
        if(iRowSet.size()>0){
        	return true;
        }else{
        	return false;
        }
       
	}
	
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	
	/**
	 * 	��Hashtable����ȡ�ֶ�ֵ
	 * @param hsData  ����Դ	
	 * @param key	��
	 * @return	���ݼ�����ֵ	
	 */
	public String getFieldValue(Hashtable hsData, String key) {
		return ((String) ((DataToken) hsData.get(key)).data).trim();
	}
	
}
