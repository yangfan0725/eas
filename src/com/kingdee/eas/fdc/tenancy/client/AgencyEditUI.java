/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;

import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.IAgency;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgencyEditUI extends AbstractAgencyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgencyEditUI.class);
    
    /**
     * output class constructor
     */
    public AgencyEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	handleCodingRule();
    	this.actionSave.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.txtNumber.setRequired(true);
    	this.txtName.setRequired(true);
    	
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {	
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("persons.*");
		return sels;
	}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable()
    {        
        return null;
	} 
   
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new com.kingdee.eas.fdc.tenancy.AgencyInfo();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	//super.verifyInput(e);
    	verifyPhone();

    	if (this.txtNumber.isEnabled()==true&&StringUtils.isEmpty(this.txtNumber.getText())) {
    		MsgBox.showInfo("�������¼�룡");
			abort();
		 }
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("���Ʊ���¼�룡");
			abort();
		}if(this.txtDescription.getText().length()>255)
		{
			MsgBox.showInfo("�������ܳ���255���ַ���");
			abort();
		}if(this.kdtPersons.getRowCount()>0)
		{
			for(int i=0;i<kdtPersons.getRowCount();i++)
			{
				IRow row = kdtPersons.getRow(i);
				if(row.getCell("name").getValue()==null)
				{
					MsgBox.showInfo("������Ա���Ʋ���Ϊ��!");
					this.abort();
				}
				String phone = (String)row.getCell("phone").getValue();
				if(!StringUtils.isEmpty(phone))
				{
					isValidInput(phone,"��ϵ�绰ֻ����������");
				}
			}
		}
    }
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.AgencyInfo objectValue = new com.kingdee.eas.fdc.tenancy.AgencyInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    
    private void verifyPhone()
    {
    	String fax = this.txtfax.getText();
    	if(!StringUtils.isEmpty(fax))
    	{
    		isValidInput(fax,"����ֻ����������");
    	}
    	String phone = this.txtphone.getText();
    	if(!StringUtils.isEmpty(phone))
    	{
    		isValidInput(phone,"�绰ֻ����������,����绰����֮����;�ָ�");
    	} 	
    }
    
    private static void isValidInput(String phone,String msg) {
		String regEx="[\\d]{1,}[\\d|;|��]{0,}";
        boolean result=Pattern.compile(regEx).matcher(phone).matches();
        if(!result){
        	MsgBox.showInfo(msg);
        	SysUtil.abort();
        }
	}
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
    }
    
    /**
	 * ����������
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		
		
		//by tim_gao 2010-11-17�и���
			//-- ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();
			
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}
	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}
	
	
	/**
	 *
	 * ���������ر���ؼ�
	 * @return
	 */
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	//��дEditUI�Ļ�ȡ����
    //ͨ����������ȡ���롣������á�
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
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
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                	// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                	String costCenterId = null;
            		
//            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();��¥��֯�ɱ�����Ϊ�� 
            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            		
            		
                    number =  prepareNumberForAddnew(iCodingRuleManager,(AgencyInfo)editData, orgId,costCenterId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }

            /*
            if (orgId != null && orgId.trim().length() > 0 && iCodingRuleManager.isExist(caller, orgId)) {
                String number = "";

                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) //�˴���orgId
                                                                           // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                {
                    //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                    number = iCodingRuleManager.readNumber(caller, orgId);
                } else {
                    //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);

                return;
            } else {
                //��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                String companyId = getNextCompanyId();

                if (companyId != null && companyId.trim().length() > 0 && iCodingRuleManager.isExist(caller, companyId)) {
                    String number = "";

                    if (iCodingRuleManager.isUseIntermitNumber(caller, companyId)) //�˴���orgId
                                                                                   // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    {
                        //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, companyId);
                    } else {
                        //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                        number = iCodingRuleManager.getNumber(caller, companyId);
                    }

                    //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                    prepareNumber(caller, number);

                    return;
                }
            }
            */
        } catch (Exception err) {
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }
    
  //��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,AgencyInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//��������ظ�����ȡ����
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IAgency)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
}