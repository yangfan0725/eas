package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.migrate.K3CostFactory;
import com.kingdee.eas.fdc.migrate.K3CostInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		��Զ��
 * @version		EAS7.0		
 * @createDate	2011-7-6	 
 * @see						
 */
public class FullProjectDynamicCostTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.FullProjectDynamicCostTransmissionResource";
	
	// K3�������
	K3CostInfo k3CostInfo = null;
	
	// ��Ŀ���̶���
	CurProjectInfo curProjectInfo = null;
	
	// �ɱ���Ŀ����
	CostAccountInfo costAccountInfo = null;

	/**
	 * @description		
	 * @author			��Զ��		
	 * @createDate		2011-7-6
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return K3CostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	/**
	 * @description		
	 * @author			��Զ��		
	 * @createDate		2011-7-6
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable lineData, Context ctx) throws TaskExternalException {
		// ������Ŀ����
		String fPrjLongNumber = (String) ((DataToken) lineData.get("FPrjLongNumber")).data;
		
		// ��Ŀ����
		String fAcctLongNumber = (String) ((DataToken) lineData.get("FAcctLongNumber")).data;
		fAcctLongNumber = fAcctLongNumber.replace('!', '.');
		
		// ��Ŀ����
		String fAcctName = (String) ((DataToken) lineData.get("FAcctName")).data;
		
		// δ�����ͬ���
		String fUnSettConAmt = (String) ((DataToken) lineData.get("FUnSettConAmt")).data;
		
		// δ���������
		String fUnSettleChange = (String) ((DataToken) lineData.get("FUnSettleChange")).data;
		
		// �ѽ����ͬ���
		String fSettConAmt = (String) ((DataToken) lineData.get("FSettConAmt")).data;
		
		// �ѽ��������
		String fSettleChange = (String) ((DataToken) lineData.get("FSettleChange")).data;
		
		// ������
		String fSettSignAmt = (String) ((DataToken) lineData.get("FSettSignAmt")).data;
		
		// �Ǻ�ͬ�Գɱ�
		String fConWithoutTxtCostAmt = (String) ((DataToken) lineData.get("FConWithoutTxtCostAmt")).data;
		
		// �ѷ������
		String fHappendAmt = (String) ((DataToken) lineData.get("FHappendAmt")).data;
		
		// �Ѹ�����
		String fHasPayedAmt = (String) ((DataToken) lineData.get("FHasPayedAmt")).data;
		
		// Ŀǰ������
		String fNotHappenAmt = (String) ((DataToken) lineData.get("FNotHappenAmt")).data;
		
		// ��̬�ɱ�
		String fDynCostAmt = (String) ((DataToken) lineData.get("FDynCostAmt")).data;
		
		// Ŀ��ɱ�
		String fAimCostAmt = (String) ((DataToken) lineData.get("FAimCostAmt")).data;
		
		// ����
		String fDiffAmt = (String) ((DataToken) lineData.get("FDiffAmt")).data;
		
		// ZERO���
		BigDecimal zeroAmt = new BigDecimal(0);
		
		/*
		 * ��¼��У��
		 */
		// ������Ŀ�����¼
		if (StringUtils.isEmpty(fPrjLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "PrjLongNumberNotNull"));
		}

		// ��Ŀ�����¼
		if (StringUtils.isEmpty(fAcctLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "AcctLongNumberNotNull"));
		}
		/*
		// ��Ŀ���Ʊ�¼
		if (StringUtils.isEmpty(fAcctName)) {
			throw new TaskExternalException(getResource(ctx, "AcctNameNotNull"));
		}
		*/

		/*
		// ������Ŀ���볤��У��
		if(fPrjLongNumber != null && fPrjLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "PrjLongNumberIsOver40"));
		}
		*/
		
		try {
			// ������Ŀ������EASϵͳ�д���У�飬��ȡ����Ŀ���̶���
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fPrjLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// ���������ϵͳ�в�����ʱ����ʾ������������ϵͳ�в����ڣ�
				// throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
				throw new TaskExternalException(fPrjLongNumber + getResource(ctx, "NumberNotFound"));
			}
			
			// ��Ŀ������EASϵͳ�д���У�飬��ȡ�óɱ���Ŀ����
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fAcctLongNumber));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString()));
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if (costAccountColl.size() > 0) {
				costAccountInfo = costAccountColl.get(0);
			} else {
				// ���������ϵͳ�в�����ʱ����ʾ������������ϵͳ�в����ڣ�
				// throw new TaskExternalException(getResource(ctx,"AcctLongNumberNotFound"));
				throw new TaskExternalException(fAcctLongNumber + getResource(ctx, "NumberNotFound"));
			}
			
			/*
			 * K3�����������
			 */
			k3CostInfo = new K3CostInfo();
			// ������ĿId(����)
			k3CostInfo.setProjectId(curProjectInfo.getId());
			// �ɱ���ĿId(����)
			k3CostInfo.setCostAccountId(costAccountInfo.getId());
			// ������Ŀ����
			k3CostInfo.setPrjLongNumber(fPrjLongNumber);
			// ��Ŀ����
			k3CostInfo.setAcctLongNumber(fAcctLongNumber);
			// ��Ŀ����(����Ŀ�Ŀ���ƺ��ԣ����ݿ�Ŀ���룬�Զ�����ϵͳ�ж�Ӧ�Ŀ�Ŀ����)
			k3CostInfo.setAcctName(costAccountInfo.getName());
			// δ�����ͬ���
			try {
				k3CostInfo.setUnSettConAmt(new BigDecimal(fUnSettConAmt));
			} catch (Exception e) {
				// �����δ�����ͬ���Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setUnSettConAmt(zeroAmt);
			}
			// δ���������
			try {
				k3CostInfo.setUnSettleChange(new BigDecimal(fUnSettleChange));
			} catch (Exception e) {
				// �����δ���������Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setUnSettleChange(zeroAmt);
			}
			// �ѽ����ͬ���
			try {
				k3CostInfo.setSettConAmt(new BigDecimal(fSettConAmt));
			} catch (Exception e) {
				// ������ѽ����ͬ���Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setSettConAmt(zeroAmt);
			}
			// �ѽ��������
			try {
				k3CostInfo.setSettleChange(new BigDecimal(fSettleChange));
			} catch (Exception e) {
				// ������ѽ��������Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setSettleChange(zeroAmt);
			}
			// ������
			try {
				k3CostInfo.setSettSignAmt(new BigDecimal(fSettSignAmt));
			} catch (Exception e) {
				// ����Ľ�����Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setSettSignAmt(zeroAmt);
			}
			// �Ǻ�ͬ�Գɱ�
			try {
				k3CostInfo.setConWithoutTxtCostAmt(new BigDecimal(fConWithoutTxtCostAmt));
			} catch (Exception e) {
				// ����ķǺ�ͬ�Գɱ�Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setConWithoutTxtCostAmt(zeroAmt);
			}
			// �ѷ������
			try {
				k3CostInfo.setHappendAmt(new BigDecimal(fHappendAmt));
			} catch (Exception e) {
				// ������ѷ������Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setHappendAmt(zeroAmt);
			}
			// �Ѹ�����
			try {
				k3CostInfo.setHasPayedAmt(new BigDecimal(fHasPayedAmt));
			} catch (Exception e) {
				// ������Ѹ�����Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setHasPayedAmt(zeroAmt);
			}
			// Ŀǰ������
			try {
				k3CostInfo.setNotHappenAmt(new BigDecimal(fNotHappenAmt));
			} catch (Exception e) {
				// �����Ŀǰ������Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setNotHappenAmt(zeroAmt);
			}
			// ��̬�ɱ�
			BigDecimal bdDynCostAmt = null;
			try {
				bdDynCostAmt = new BigDecimal(fDynCostAmt);				
			} catch (Exception e) {
				// ����Ķ�̬�ɱ�Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				bdDynCostAmt = zeroAmt;
			}
			k3CostInfo.setDynCostAmt(bdDynCostAmt);
			// Ŀ��ɱ�
			BigDecimal bdAimCostAmt = null;
			try {
				bdAimCostAmt = new BigDecimal(fAimCostAmt);
			} catch (Exception e) {
				// �����Ŀ��ɱ�Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				bdAimCostAmt = zeroAmt;
			}
			k3CostInfo.setAimCostAmt(bdAimCostAmt);
			// ����
			try {
				// ����Ĳ�����ԣ�����=����Ķ�̬�ɱ�-�����Ŀ��ɱ�
				k3CostInfo.setDiffAmt(FDCHelper.subtract(bdDynCostAmt, bdAimCostAmt));
			} catch (Exception e) {
				// ����Ĳ���Ϊ�ջ����ֵʱ��Ĭ��ֵ0�趨
				k3CostInfo.setDiffAmt(zeroAmt);
			}			
		} catch (BOSException e) {
			k3CostInfo = null;
			e.printStackTrace();
		}

		return k3CostInfo;
	}
	
	/**
	 * �õ���Դ�ļ�
	 * @author ��Զ��
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
