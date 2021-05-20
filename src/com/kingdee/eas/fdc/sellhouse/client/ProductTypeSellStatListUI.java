
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.ProductTypeSellStateFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.framework.ITreeBase;

/**
 * ��Ʒ��������ͳ�Ʊ�
 * 
 * @author laiquan_luo
 * 
 */
public class ProductTypeSellStatListUI extends	AbstractProductTypeSellStatListUI
{
	private static final Logger logger = CoreUIObject.getLogger(ProductTypeSellStatListUI.class);

	private CommonQueryDialog commonQueryDialog = null;
	private ProductTypeSellStatFilterUI userFilter = null;
	
	/**
	 * ����key
	 */
	private final String tbl_key = "key";
	
	private final String tbl_productName = "productName";
	private final String tbl_roomType = "roomType";
	private final String tbl_totalCount = "totalCount";
	private final String tbl_totalArea = "totalArea";
	private final String tbl_totalStandPrice = "totalStandardPrice";
	private final String tbl_totalAvePrice= "averagePrice";
	private final String tbl_saleRate = "saleRate";
	
	private final String tbl_haveSellCount = "haveSellCount";
	private final String tbl_haveSellArea = "haveSellArea";
	private final String tbl_haveSellStandAvePrice = "haveSellStandardAveragePrice";
	private final String tbl_haveSellStandTotPrice = "haveSellStandardTotalPrice";
	
	private final String tbl_haveSellBaseAvePrice = "haveSellBaseAveragePrice";
	private final String tbl_haveSellBaseTotPrice = "haveSellBaseTotalPrice";
	private final String tbl_haveSellDealAvePrice = "haveSellDealAveragePrice";
	private final String tbl_haveSellDealTotPrice = "haveSellDealTotalPrice";
	
	private final String tbl_haveSellPrefAvePrice = "haveSellPreferentailAveragePrice";
	private final String tbl_haveSellPrefTotPrice = "haveSellPreferentialPrice";
	
	private final String tbl_unSellCount = "unSellCount";
	private final String tbl_unSellArea = "unsellArea";
	private final String tbl_unSellStandAvePrice = "unSellStandardAveragePrice";
	private final String tbl_unSellStandTotPrice = "unSellStandardTotalPrice";
	
	private final String tbl_sumGathering = "sumGathering";
	private final String tbl_RoomCompenstate = "roomComp";
	
	private final String tbl_querySellCount = "querySellCount";
	private final String tbl_querySellArea = "querySellArea";
	private final String tbl_queryPrefAvePrice = "queryPrefAvePrice";
	private final String tbl_queryPrefTotPrice = "queryPrefTotPrice";
	
	private final String tbl_queryDealAvePrice = "queryDealAvePrice";
	private final String tbl_queryDealTotPrice = "queryDealTotPrice";
	private final String tbl_queryStdAvePrice = "queryStdAvePrice";
	private final String tbl_queryStdTotPrice = "queryStdTotPrice";
	
	private final String tbl_querySumGathering = "querySumGathering";
	private final String tbl_querySellSumPrice = "querySellSumPrice";
	private final String tbl_queryPurSumPrice = "queryPurSumPrice";
	private final String tbl_querySignSumPrice = "querySignSumPrice";
	
	
	private boolean isAuditDate = false;
	
	//��һ�μ���
	private boolean isFirstLoad = true;
	/**
	 * output class constructor
	 */
	public ProductTypeSellStatListUI() throws Exception
	{
		super();
		
		RoomDisplaySetting setting= new RoomDisplaySetting();
    	this.isAuditDate = setting.getBaseRoomSetting().isAuditDate();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	protected String getEditUIName()
	{
		return null;
	}
	
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		execQuery();
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,
				SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()));
		
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() throws Exception
	{
		if (this.userFilter == null)
		{
			this.userFilter = new ProductTypeSellStatFilterUI(this, this.actionOnLoad);
		}
		return this.userFilter;
	}

	protected void execQuery()
	{
		tblMain.removeRows();
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		try {
			initTableHead();
//			//��ȡ����Ĺ��˲�����
			Map paramMap = getRptParamMap();
//			
//			//��ȡ������������
			Map rptDataMap = ProductTypeSellStateFacadeFactory.getRemoteInstance().getProductSellDate(paramMap);
//			
//			//���ݻ�ȡ�����������
			fillRptDataToTable(rptDataMap);
//			
//			//���û�����
			upDateTotal();
			
		} catch (Exception e1) {
			this.handleException(e1);
		}
	}
	
	/**
	 * rptDataMap �ṹ���£�
	 * ProductType     -> �洢���ǲ�Ʒ����
	 * RptTotalSumData -> �洢���ǻ��ܵ�ǰ4��ֵ
	 * PrductToModel   -> ��Ʒ�����µĻ��ͼ���
	 * 
	 * key�е�CellֵΪ��Ʒ����ID+����ID
	 * ��Ʒ�����е�CellֵΪ��Ʒ���͵�����
	 * ��Ʒ�����е�Cell��userObjectΪ��Ʒ���͵�Info
	 * @param rptDataMapParam
	 */
	protected void fillRptDataToTable(Map rptDataMapParam)
	{
		ProductTypeCollection prdTypeCols = (ProductTypeCollection)rptDataMapParam.get("ProductType");
		
		Map prductToModel = (Map)rptDataMapParam.get("PrductToModel");
		Map rptTotalSumData = (Map)rptDataMapParam.get("RptTotalSumData");
		Map selledRoomData = (Map)rptDataMapParam.get("SelledRoomData");
		
		Map unSelledRoomData = (Map)rptDataMapParam.get("UnselledRoomData");
		Map sumGatheringData = (Map)rptDataMapParam.get("SumGathering");
		Map subAmtRoomData = (Map)rptDataMapParam.get("SubAmtData");
		
		Map periodGatheringData = (Map)rptDataMapParam.get("PeriodGatheringData");
		Map periodNoGatheringData = (Map)rptDataMapParam.get("PeriodNoGatheringData");
		
		int mergeStart = 0;
		int mergeEnd = 0;
		for(int index = 0; index < prdTypeCols.size(); ++index)
		{
			ProductTypeInfo tmpPrdType = prdTypeCols.get(index);
			String tmpPrdKey = String.valueOf(tmpPrdType.getId());
			
			if(prductToModel.containsKey(tmpPrdKey))
			{
				//���������
				Integer subRoomCount = new Integer(0);          //С�Ʒ�������
				BigDecimal subTotalArea = FDCHelper.ZERO;       //С�������
				BigDecimal subTotalStandPrice = FDCHelper.ZERO ;//С�������
				
				//���۷���
				Integer selledRoomCount = new Integer(0);          //С������������
				BigDecimal selledTotalArea = FDCHelper.ZERO;       //С�����������
				BigDecimal selledStandTotalPrice = FDCHelper.ZERO; //С�����۱�׼�ܼ�
				BigDecimal selledBaseTotalPrice = FDCHelper.ZERO;  //С�����۵׼��ܼ�
				BigDecimal selledDealTotalPrice = FDCHelper.ZERO;  //С�����۳ɽ��ܼ�
				
				//δ�۷���
				Integer unselledRoomCount = new Integer(0);           //С��δ��������
				BigDecimal unselledTotalArea = FDCHelper.ZERO;	      //С��δ�������
				BigDecimal unselledStandTotalPrice = FDCHelper.ZERO;  //С��δ�۱�׼�ܼ�
				
				//�ۼƻؿ�
				BigDecimal sumGatheringAmt = FDCHelper.ZERO;   //С���ۼƻ��
				
				//������
				BigDecimal sumRoomCompAmt = FDCHelper.ZERO;    //С�Ʋ�����
				
				//��ѯ����-�ۼƻؿ�
				BigDecimal periodSumGatheringAmt = FDCHelper.ZERO; 
				
				//��ѯ����-���ۼƻؿ������
				Integer periodSelledRoomCount = new Integer(0);
				BigDecimal periodSelledArea = FDCHelper.ZERO;
				BigDecimal periYouHuiAmt = FDCHelper.ZERO;
				BigDecimal periodDealTotPrice = FDCHelper.ZERO;
				BigDecimal periodStandTotPrice = FDCHelper.ZERO;
				BigDecimal periodSelledTotAmt = FDCHelper.ZERO;
				BigDecimal periodPuchaseAmt = FDCHelper.ZERO;
				BigDecimal periodSignSumPrice = FDCHelper.ZERO;
				
				ArrayList roomModels = (ArrayList)prductToModel.get(tmpPrdKey);
				for(int j = 0; j < roomModels.size(); ++j)
				{
					String roomModelId = String.valueOf(roomModels.get(j));
					String tmpKeyValue = tmpPrdKey + roomModelId;
					
					
					//��rptTotalSumData��ȡ����
					Object[] tmpRptSumData = (Object[])rptTotalSumData.get(tmpKeyValue);
					
					//�����������
					IRow tmpRow = tblMain.addRow();
					tmpRow.getCell(tbl_key).setValue(tmpKeyValue); //����Key��ֵ
					tmpRow.getCell(tbl_productName).setValue(tmpPrdType.getName()); //��Ʒ������
					if(tmpRptSumData != null && tmpRptSumData.length > 0)
					{
						//������ݲ���
						tmpRow.getCell(tbl_roomType).setValue(tmpRptSumData[0]);        //����
						tmpRow.getCell(tbl_totalCount).setValue(tmpRptSumData[1]);      //������
						tmpRow.getCell(tbl_totalArea).setValue(tmpRptSumData[2]);       //�����
						tmpRow.getCell(tbl_totalStandPrice).setValue(tmpRptSumData[3]); //�ܽ��
						if(tmpRptSumData[3]==null || tmpRptSumData[2]==null)
						{
							tmpRow.getCell(tbl_totalAvePrice).setValue(null); //�ܾ���
						}else
						{
							tmpRow.getCell(tbl_totalAvePrice).setValue(FDCHelper.divide(tmpRptSumData[3], tmpRptSumData[2])); //�ܾ���

						}
												
						//С������ʾ����
						subRoomCount = new Integer(FDCHelper.add(tmpRptSumData[1], subRoomCount).intValue());
						subTotalArea = FDCHelper.add(subTotalArea, tmpRptSumData[2]);
						subTotalStandPrice = FDCHelper.add(subTotalStandPrice, tmpRptSumData[3]);
					}
					
					//������۷��������Ϣ
					Object[] tmpRptSelledData = (Object[])selledRoomData.get(tmpKeyValue);
					if(tmpRptSelledData != null && tmpRptSelledData.length > 0)
					{
						tmpRow.getCell(tbl_haveSellCount).setValue(tmpRptSelledData[0]);         //������
						tmpRow.getCell(tbl_haveSellArea).setValue(tmpRptSelledData[1]);          //�����
						tmpRow.getCell(tbl_haveSellStandAvePrice).setValue(FDCHelper.divide(tmpRptSelledData[2], tmpRptSelledData[1])); //��׼����
						tmpRow.getCell(tbl_haveSellStandTotPrice).setValue(tmpRptSelledData[2]); //��׼�ܼ�
						if(tmpRptSelledData[3]==null || tmpRptSelledData[1] ==null)
						{
							tmpRow.getCell(tbl_haveSellBaseAvePrice).setValue(null);										
						}else
						{
							tmpRow.getCell(tbl_haveSellBaseAvePrice).setValue(
								FDCHelper.divide(tmpRptSelledData[5], tmpRptSelledData[1]));     //�ͼ۾���
						}
						
						tmpRow.getCell(tbl_haveSellBaseTotPrice).setValue(tmpRptSelledData[5]);  //�ͼ��ܼ�
						if(tmpRptSelledData[4]==null || tmpRptSelledData[1]==null)
						{
							tmpRow.getCell(tbl_haveSellDealAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellDealAvePrice).setValue(
								FDCHelper.divide(tmpRptSelledData[4], tmpRptSelledData[1]));     //�ɽ�����
						}
						
						tmpRow.getCell(tbl_haveSellDealTotPrice).setValue(tmpRptSelledData[4]);  //�ɽ��ܼ�
						if(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]).compareTo(new BigDecimal(0))==0 ||tmpRptSelledData[1]==null)
						{
							tmpRow.getCell(tbl_haveSellPrefAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellPrefAvePrice).setValue(
									FDCHelper.divide(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]), 
											tmpRptSelledData[1])); //�Żݾ���
						}
						if(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]).compareTo(new BigDecimal(0))==0)
						{
							tmpRow.getCell(tbl_haveSellPrefTotPrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellPrefTotPrice).setValue(
									FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]));   //���Żݶ�
						}
						
						
						
						//С������ʾ����
						selledRoomCount = new Integer(FDCHelper.add(selledRoomCount, tmpRptSelledData[0]).intValue());
						selledTotalArea = FDCHelper.add(selledTotalArea, tmpRptSelledData[1]);
						selledStandTotalPrice = FDCHelper.add(selledStandTotalPrice, tmpRptSelledData[2]);
						selledBaseTotalPrice = FDCHelper.add(selledBaseTotalPrice, tmpRptSelledData[5]);
						selledDealTotalPrice = FDCHelper.add(selledDealTotalPrice, tmpRptSelledData[4]);
						
						//���������
						tmpRow.getCell(tbl_saleRate).setValue(FDCHelper.multiply(FDCHelper.toBigDecimal(100), 
								FDCHelper.divide(tmpRptSelledData[0], tmpRow.getCell(tbl_totalCount).getValue(), 4, BigDecimal.ROUND_HALF_UP)));
					}
					
					//���δ�۷��������Ϣ
					Object[] tmpRptunselledData = (Object[])unSelledRoomData.get(tmpKeyValue);
					if(tmpRptunselledData != null && tmpRptunselledData.length > 0)
					{
						tmpRow.getCell(tbl_unSellCount).setValue(tmpRptunselledData[0]);  //������
						tmpRow.getCell(tbl_unSellArea).setValue(tmpRptunselledData[1]);   //�����
						if(tmpRptunselledData[2]==null || tmpRptunselledData[1]==null)
						{
							tmpRow.getCell(tbl_unSellStandAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(tmpRptunselledData[2], tmpRptunselledData[1]));
						}					
						tmpRow.getCell(tbl_unSellStandTotPrice).setValue(tmpRptunselledData[2]); //��׼�ܼ�
						
						//С������ʾ����
						unselledRoomCount = new Integer(FDCHelper.add(unselledRoomCount, tmpRptunselledData[0]).intValue());
						unselledTotalArea = FDCHelper.add(unselledTotalArea, tmpRptunselledData[1]);
						unselledStandTotalPrice = FDCHelper.add(unselledStandTotalPrice, tmpRptunselledData[2]);
					}
					
					//����ۼƻؿ�����
					BigDecimal tmpSumGatheringAmt = (BigDecimal)sumGatheringData.get(tmpKeyValue);
					if(tmpSumGatheringAmt != null)
					{
						tmpRow.getCell(tbl_sumGathering).setValue(tmpSumGatheringAmt); //�ۼƻؿ�
						
						//С������ʾ����
						sumGatheringAmt = FDCHelper.add(sumGatheringAmt, tmpSumGatheringAmt);
					}
					
					//��Ӳ�����
					BigDecimal tmpSumSubAmt = (BigDecimal)subAmtRoomData.get(tmpKeyValue);
					if(tmpSumSubAmt != null)
					{
						tmpRow.getCell(tbl_RoomCompenstate).setValue(tmpSumSubAmt);
						
						//С������ʾ����
						sumRoomCompAmt = FDCHelper.add(sumRoomCompAmt, tmpSumSubAmt);
					}
					
					//��ѯ����������䣨�����ۼƻؿ
					Object[] tmpPeriodRptData = new Object[9];
					if(periodNoGatheringData!=null)
					{
						tmpPeriodRptData = (Object[])periodNoGatheringData.get(tmpKeyValue);
					}
					if(tmpPeriodRptData != null && tmpPeriodRptData.length > 0)
					{
						tmpRow.getCell(tbl_querySellCount).setValue(tmpPeriodRptData[0]);//��������
						tmpRow.getCell(tbl_querySellArea).setValue(tmpPeriodRptData[1]);//�������
//						if(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])==null || FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]).compareTo(new BigDecimal(0))==0 || tmpPeriodRptData[1]==null)
//						{
//							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(null);
//						}else
//						{
//							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(
//									FDCHelper.divide(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]), tmpPeriodRptData[1])); //�Żݾ���
//						}
						tmpRow.getCell(tbl_queryPrefTotPrice).setValue(tmpPeriodRptData[8]);//�Ż��ܼ�
						if(tmpPeriodRptData[8]==null || tmpPeriodRptData[1]==null)
						{
							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[8], tmpPeriodRptData[1]));   //�Żݾ���
						}
//						if(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])==null ||FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]).compareTo(new BigDecimal(0))==0)
//						{
//							tmpRow.getCell(tbl_queryPrefTotPrice).setValue(null);
//						}else
//						{
//							tmpRow.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])); //�Ż��ܼ�
//						}
						
						if(tmpPeriodRptData[2]==null || tmpPeriodRptData[1]==null)
						{
							tmpRow.getCell(tbl_queryDealAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[2], tmpPeriodRptData[1]));   //�ɽ�����
						}						
						tmpRow.getCell(tbl_queryDealTotPrice).setValue(tmpPeriodRptData[2]);//�ɽ��ܼ�
						if(tmpPeriodRptData[3]==null || tmpPeriodRptData[1] ==null)
						{
							tmpRow.getCell(tbl_queryStdAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[3], tmpPeriodRptData[1]));   //��׼����
						}						
						tmpRow.getCell(tbl_queryStdTotPrice).setValue(tmpPeriodRptData[3]);//��׼�ܼ�
						tmpRow.getCell(tbl_querySellSumPrice).setValue(tmpPeriodRptData[6]);//�����ܼ�
						tmpRow.getCell(tbl_queryPurSumPrice).setValue(tmpPeriodRptData[4]);//���ۺ�ͬ�ܼ�
						tmpRow.getCell(tbl_querySignSumPrice).setValue(tmpPeriodRptData[7]);//��ǩԼ��ͬ�ܼ�
						
						//С������ʾ����
						periodSelledRoomCount =new Integer(FDCHelper.add(periodSelledRoomCount, tmpPeriodRptData[0]).intValue());
						periodSelledArea = FDCHelper.add(periodSelledArea, tmpPeriodRptData[1]);
						periYouHuiAmt = FDCHelper.add(periYouHuiAmt, tmpPeriodRptData[8]);
						periodDealTotPrice = FDCHelper.add(periodDealTotPrice, tmpPeriodRptData[2]);
						periodStandTotPrice = FDCHelper.add(periodStandTotPrice, tmpPeriodRptData[3]);
						periodSelledTotAmt = FDCHelper.add(periodSelledTotAmt, tmpPeriodRptData[6]);
						periodPuchaseAmt = FDCHelper.add(periodPuchaseAmt, tmpPeriodRptData[4]);
						periodSignSumPrice = FDCHelper.add(periodSignSumPrice, tmpPeriodRptData[7]);
					}
					
					//��ѯ�����������-�ۼƻؿ�
					BigDecimal tmpPeriodSumGatheringAmt = null;
					if(periodGatheringData!=null)
					{
						tmpPeriodSumGatheringAmt = (BigDecimal)periodGatheringData.get(tmpKeyValue);
					}
					if(tmpPeriodSumGatheringAmt != null)
					{
						tmpRow.getCell(tbl_querySumGathering).setValue(tmpPeriodSumGatheringAmt);
						
						//С������ʾ����
						periodSumGatheringAmt = FDCHelper.add(periodSumGatheringAmt, tmpPeriodSumGatheringAmt);
					}
					//tmpRow.setTreeLevel(1);
				}
				
				//���С����
				IRow tmpSubSum = tblMain.addRow();
				//tmpSubSum.setTreeLevel(2);
				tmpSubSum.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
				tmpSubSum.getStyleAttributes().setFontColor(Color.RED);
				tmpSubSum.getCell(tbl_productName).setValue(tmpPrdType.getName());
				tmpSubSum.getCell(tbl_roomType).setValue("С��");
				tmpSubSum.getCell(tbl_key).setValue("SubSum");
				
				//������
				tmpSubSum.getCell(tbl_totalCount).setValue(subRoomCount);   //С��������
				tmpSubSum.getCell(tbl_totalArea).setValue(subTotalArea);    //С���������
				tmpSubSum.getCell(tbl_totalStandPrice).setValue(subTotalStandPrice); //С���ܽ��

				if(subTotalStandPrice.compareTo(new BigDecimal(0))==0 || subTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_totalAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_totalAvePrice).setValue(subTotalStandPrice.divide(subTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //С���ܾ���
				}
				
				tmpSubSum.getCell(tbl_saleRate).setValue(FDCHelper.multiply(FDCHelper.toBigDecimal(100), 
						FDCHelper.divide(selledRoomCount, subRoomCount, 4, BigDecimal.ROUND_HALF_UP)));            //������
				
				//���۷�������
				tmpSubSum.getCell(tbl_haveSellCount).setValue(selledRoomCount);
				tmpSubSum.getCell(tbl_haveSellArea).setValue(selledTotalArea);
				
				if(selledStandTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(selledStandTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //С���ܾ���
				}
//				tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(
//						FDCHelper.divide(selledStandTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellStandTotPrice).setValue(selledStandTotalPrice);
				
				if(selledBaseTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(selledBaseTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //С���ܾ���
				}
//				tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(
//						FDCHelper.divide(selledBaseTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellBaseTotPrice).setValue(selledBaseTotalPrice);
				
				if(selledDealTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(selledDealTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //С���ܾ���
				}
//				tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(
//						FDCHelper.divide(selledDealTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellDealTotPrice).setValue(selledDealTotalPrice);
				
				tmpSubSum.getCell(tbl_haveSellPrefTotPrice).setValue(FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice));
				if(FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice)==null || FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice).compareTo(new BigDecimal(0))==0
						||selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellPrefAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellPrefAvePrice).setValue(
							 FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice).divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				
				
				//δ�۷�������
				tmpSubSum.getCell(tbl_unSellCount).setValue(unselledRoomCount);
				tmpSubSum.getCell(tbl_unSellArea).setValue(unselledTotalArea);
				if(unselledStandTotalPrice.compareTo(new BigDecimal(0))==0 || unselledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(unselledStandTotalPrice.divide(unselledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //С���ܾ���
				}
//				tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(unselledStandTotalPrice, unselledTotalArea));
				tmpSubSum.getCell(tbl_unSellStandTotPrice).setValue(unselledStandTotalPrice);
				
				//�ۼƻؿ�
				tmpSubSum.getCell(tbl_sumGathering).setValue(sumGatheringAmt);
				//������
				tmpSubSum.getCell(tbl_RoomCompenstate).setValue(sumRoomCompAmt);
				
				//�����ѯ����С��
				tmpSubSum.getCell(tbl_querySumGathering).setValue(periodSumGatheringAmt);
				tmpSubSum.getCell(tbl_querySellCount).setValue(periodSelledRoomCount);
				tmpSubSum.getCell(tbl_querySellArea).setValue(periodSelledArea);
//				if(FDCHelper.divide(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice), periodSelledArea)==null)
//				{
//					tmpSubSum.getCell(tbl_queryPrefAvePrice).setValue(new BigDecimal(0));
//				}else
//				{
//					tmpSubSum.getCell(tbl_queryPrefAvePrice).setValue(
//							FDCHelper.divide(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice), periodSelledArea));
//				}
				
//				tmpSubSum.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice));
				tmpSubSum.getCell(tbl_queryPrefTotPrice).setValue(periYouHuiAmt);
				if(FDCHelper.divide(periYouHuiAmt, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(periYouHuiAmt, periodSelledArea));
				}
				if(FDCHelper.divide(periodDealTotPrice, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(periodDealTotPrice, periodSelledArea));
				}
				
				tmpSubSum.getCell(tbl_queryDealTotPrice).setValue(periodDealTotPrice);
				if(FDCHelper.divide(periodStandTotPrice, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryStdAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(periodStandTotPrice, periodSelledArea));
				}				
				tmpSubSum.getCell(tbl_queryStdTotPrice).setValue(periodStandTotPrice);
				tmpSubSum.getCell(tbl_querySellSumPrice).setValue(periodSelledTotAmt);
				tmpSubSum.getCell(tbl_queryPurSumPrice).setValue(periodPuchaseAmt);
				tmpSubSum.getCell(tbl_querySignSumPrice).setValue(periodSignSumPrice);
				
				mergeEnd = mergeStart + roomModels.size();
				
				this.tblMain.getMergeManager().mergeBlock(mergeStart,1,mergeEnd,1);
				mergeStart = mergeEnd+1;
			}
		}
	}
	
	protected Map getRptParamMap() throws Exception
	{
		//��ȡ��ѡ�Ľڵ�
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node == null || node.getUserObject() == null)
		{
			node = new DefaultKingdeeTreeNode();
			node.setUserObject(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		}
		else if(node.getUserObject() instanceof OrgStructureInfo)
		{
			OrgStructureInfo orgStrInfo = (OrgStructureInfo)node.getUserObject();
			node.setUserObject(orgStrInfo.getUnit());
		}
		
		//������ѡ�Ľڵ��ȡ��ص�����
		 /**
	     * paramMap
	     * ������
	     * selectNode        = ��ѡ���Ľڵ㣬���û��ѡ������Ϊ��ǰ��֯
	     * IncludeAttachment = ��������������Ϊ���ǰ�����Ϊ��ʱ������
	     * BuildArea         = ��ʾ������ͣ�Ϊ���ǽ��������Ϊ��ʱΪ�������
	     * PreArea           = ��ʾ������ͣ�Ϊ����Ԥ�������Ϊ��ʱΪʵ�����
	     * BeginDate         = ��ʼ����
	     * EndDate           = ��������
	     * IncludeOrder      = ����Ԥ��ҵ�� Ϊ���ǰ�����Ϊ��ʱ������
	     * IsAuditDate       = ���Ǹ�ʱ��Ϊ��׼��Ϊ��ʱ����������Ϊ��׼��Ϊ��ʱ�Ѷ�Ӧ������Ϊ��׼��
	     */
		Map paramMap = new HashMap();
		paramMap.put("selectNode", node);
		
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttachment()));
		paramMap.put("BuildArea", Boolean.valueOf(isBuildArea()));
		paramMap.put("PreArea", Boolean.valueOf(isPreArea()));
		paramMap.put("BeginDate", getBeginQueryDate());
		paramMap.put("EndDate", getEndQueryDate());
		paramMap.put("IncludeOrder", Boolean.valueOf(isIncludePreOrder()));
		paramMap.put("IsAuditDate", Boolean.valueOf(this.isAuditDate));
		paramMap.put("userInfo",SysContext.getSysContext().getCurrentUserInfo());
		return paramMap;
	}

	protected void checkTableParsed()
	{
		tblMain.checkParsed();
	}

	public void onLoad() throws Exception
	{
		this.tblMain.checkParsed();
		initTable();
		
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		
		isFirstLoad = false;
		
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);		
		this.chkShowEtyLine.setVisible(false);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionLocate.setVisible(false);
	}
	
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	
	
	protected void initTableHead() throws Exception
	{
		for(int colIndex = tblMain.getColumnCount()-1; colIndex > 23; --colIndex)
		{
			tblMain.removeColumn(colIndex);
		}
		
		//��Ӷ�̬��
		Calendar cal = new GregorianCalendar();
		cal.setTime(getEndQueryDate());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE));
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		String des = "��ѯ��������(" + FORMAT_DAY.format(getBeginQueryDate()) + "��"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
		
		addDynamicCol(tblMain, tbl_querySellCount, des, "��������");
		addDynamicCol(tblMain, tbl_querySellArea, des, "�������");
		addDynamicCol(tblMain, tbl_queryPrefAvePrice, des, "�Żݾ���");
		addDynamicCol(tblMain, tbl_queryPrefTotPrice, des, "�Żݶ�");
		
		addDynamicCol(tblMain, tbl_queryDealAvePrice, des, "�ɽ�����");
		addDynamicCol(tblMain, tbl_queryDealTotPrice, des, "�ɽ��ܼ�");
		addDynamicCol(tblMain, tbl_queryStdAvePrice, des, "��׼����");
		addDynamicCol(tblMain, tbl_queryStdTotPrice, des, "��׼�ܼ�");
		
		addDynamicCol(tblMain, tbl_querySumGathering, des, "�ۼƻؿ�");
		addDynamicCol(tblMain, tbl_querySellSumPrice, des, "�����ܼ�");
		addDynamicCol(tblMain, tbl_queryPurSumPrice, des, "���ۺ�ͬ�ܼ�");
		addDynamicCol(tblMain, tbl_querySignSumPrice, des, "ǩԼ��ͬ�ܼ�");
		
		tblMain.checkParsed();
		//���ñ�ͷ�ں�
		this.tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		this.tblMain.getHeadMergeManager().mergeBlock(0, 24, 0, 35);
		
		tblMain.getColumn(tbl_productName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		tblMain.getColumn(tbl_roomType).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		FDCHelper.formatTableNumber(tblMain, tbl_totalArea);
		FDCHelper.formatTableNumber(tblMain, tbl_totalStandPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_totalAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_saleRate);
		
		//�����и�ʽ����
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellArea);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellBaseAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellBaseTotPrice);
		
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellDealAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellDealTotPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellPrefAvePrice);
		
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellPrefTotPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellStandAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellStandTotPrice);
		
		//δ���и�ʽ����
		FDCHelper.formatTableNumber(tblMain, tbl_unSellArea);
		FDCHelper.formatTableNumber(tblMain, tbl_unSellStandAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_unSellStandTotPrice);
		
		//�ۼƻؿ�Ͳ���������
		FDCHelper.formatTableNumber(tblMain, tbl_sumGathering);
		FDCHelper.formatTableNumber(tblMain, tbl_RoomCompenstate);
		tblMain.getColumn(tbl_querySellCount).getStyleAttributes().setNumberFormat("0");
	}
	
	protected IColumn addDynamicCol(KDTable table, String colKey, String head0Value, String head1Value)
	{
		IColumn col = table.addColumn();
		col.setKey(colKey);
		
		table.getHeadRow(0).getCell(colKey).setValue(head0Value);
		table.getHeadRow(1).getCell(colKey).setValue(head1Value);
		
		FDCHelper.formatTableNumber(table, colKey);
		return col;
	}
	protected void initTableBody()
	{
		
	}
	protected void initTable() throws Exception
	{
//		initTableHead();
	}
	

	protected String getKeyFieldName()
	{
		return "productName";
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		if(!isFirstLoad)
			this.execQuery();
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
	}
	
	protected void refresh(ActionEvent e) throws Exception
	{
		this.execQuery();
	}
	
	protected boolean initDefaultFilter()
	{
		return true;
	}
	
	public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
	
	/**
	 * ��Ʒ��������ͳ�ƺϼ�
	 * FDCTableHelper.periodTotalColor)
	 * FDCTableHelper.daySubTotalColor);
	 */
	private void upDateTotal()
	{
		KDTFootManager footRowManager= tblMain.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			String total="�ϼ�";
			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			tblMain.getIndexColumn().setWidth(50);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//���õ���һ��������
			footRowManager.addIndexText(0, total);
		}else{
			footRow=tblMain.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=tblMain.addFootRow(1);
			};
		}
		
		//������������
		BigDecimal footTotalCount = FDCHelper.ZERO;
		BigDecimal footTotalArea = FDCHelper.ZERO;
		BigDecimal footTotalStandPrice = FDCHelper.ZERO;
		
		BigDecimal footSelledCount = FDCHelper.ZERO;
		BigDecimal footSelledArea = FDCHelper.ZERO;
		BigDecimal footSelledStandAmt = FDCHelper.ZERO;
		
		BigDecimal footSelledBaseTotalPrice = FDCHelper.ZERO;
		BigDecimal footSelledDealTotalPrice = FDCHelper.ZERO;
		
		BigDecimal footUnsellCount = FDCHelper.ZERO;
		BigDecimal footUnsellArea = FDCHelper.ZERO;
		BigDecimal footUnsellStandPrice = FDCHelper.ZERO;
		
		BigDecimal footSumGathering = FDCHelper.ZERO;
		BigDecimal footRoomComp = FDCHelper.ZERO;
		
		BigDecimal footPeriodRoomCount = FDCHelper.ZERO;
		BigDecimal footPeriodSellArea = FDCHelper.ZERO;
		BigDecimal footPeriodDealTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodStandTotAmt = FDCHelper.ZERO;
		
		BigDecimal footPeriodSumGathering = FDCHelper.ZERO;
		BigDecimal footPeriodSellTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodPurTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodSignTotAmt = FDCHelper.ZERO;
		
		for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
		{
			IRow tmpRow = tblMain.getRow(rowIndex);
			if(tmpRow.getCell(tbl_key).getValue() != null &&
					"SubSum".equals(tmpRow.getCell(tbl_key).getValue().toString()))
			{
				footTotalCount = FDCHelper.add(footTotalCount, tmpRow.getCell(tbl_totalCount).getValue());
				footTotalArea = FDCHelper.add(footTotalArea, tmpRow.getCell(tbl_totalArea).getValue());
				footTotalStandPrice = FDCHelper.add(footTotalStandPrice, tmpRow.getCell(tbl_totalStandPrice).getValue());
				
				footSelledCount = FDCHelper.add(footSelledCount, tmpRow.getCell(tbl_haveSellCount).getValue());
				footSelledArea = FDCHelper.add(footSelledArea, tmpRow.getCell(tbl_haveSellArea).getValue());
				footSelledStandAmt = FDCHelper.add(footSelledStandAmt, tmpRow.getCell(tbl_haveSellStandTotPrice).getValue());
				footSelledBaseTotalPrice = FDCHelper.add(footSelledBaseTotalPrice, tmpRow.getCell(tbl_haveSellBaseTotPrice).getValue());
				footSelledDealTotalPrice = FDCHelper.add(footSelledDealTotalPrice, tmpRow.getCell(tbl_haveSellDealTotPrice).getValue());
				
				footUnsellCount = FDCHelper.add(footUnsellCount, tmpRow.getCell(tbl_unSellCount).getValue());
				footUnsellArea = FDCHelper.add(footUnsellArea, tmpRow.getCell(tbl_unSellArea).getValue());
				footUnsellStandPrice = FDCHelper.add(footUnsellStandPrice, tmpRow.getCell(tbl_unSellStandTotPrice).getValue());
				
				footSumGathering = FDCHelper.add(footSumGathering, tmpRow.getCell(tbl_sumGathering).getValue());
				footRoomComp = FDCHelper.add(footRoomComp, tmpRow.getCell(tbl_RoomCompenstate).getValue());
				
				footPeriodRoomCount = FDCHelper.add(footPeriodRoomCount, tmpRow.getCell(tbl_querySellCount).getValue());
				footPeriodSellArea = FDCHelper.add(footPeriodSellArea, tmpRow.getCell(tbl_querySellArea).getValue());
				footPeriodDealTotAmt = FDCHelper.add(footPeriodDealTotAmt, tmpRow.getCell(tbl_queryDealTotPrice).getValue());
				footPeriodStandTotAmt = FDCHelper.add(footPeriodStandTotAmt, tmpRow.getCell(tbl_queryStdTotPrice).getValue());
				
				footPeriodSumGathering = FDCHelper.add(footPeriodSumGathering, tmpRow.getCell(tbl_querySumGathering).getValue());
				footPeriodSellTotAmt = FDCHelper.add(footPeriodSellTotAmt, tmpRow.getCell(tbl_querySellSumPrice).getValue());
				footPeriodPurTotAmt = FDCHelper.add(footPeriodPurTotAmt, tmpRow.getCell(tbl_queryPurSumPrice).getValue());
				footPeriodSignTotAmt = FDCHelper.add(footPeriodSignTotAmt, tmpRow.getCell(tbl_querySignSumPrice).getValue());
			}
		}
		
		footRow.getCell(tbl_totalCount).setValue(new Integer(footTotalCount.intValue()));
		footRow.getCell(tbl_totalArea).setValue(footTotalArea);
		footRow.getCell(tbl_totalStandPrice).setValue(footTotalStandPrice);
		footRow.getCell(tbl_totalAvePrice).setValue(FDCHelper.divide(footTotalStandPrice, footTotalArea));
		
		footRow.getCell(tbl_haveSellCount).setValue(new Integer(footSelledCount.intValue()));
		footRow.getCell(tbl_saleRate).setValue(FDCHelper.multiply(new BigDecimal("100"), 
				FDCHelper.divide(footSelledCount, footTotalCount, 4, BigDecimal.ROUND_HALF_UP)));
		footRow.getCell(tbl_haveSellArea).setValue(footSelledArea);
		if(footSelledStandAmt==null || footSelledStandAmt.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellStandAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellStandAvePrice).setValue(footSelledStandAmt.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		footRow.getCell(tbl_haveSellStandTotPrice).setValue(footSelledStandAmt);
		//����С�ƺͺϼ�Ϊ����ʾΪ0,
		if(footSelledBaseTotalPrice==null || footSelledBaseTotalPrice.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellBaseAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellBaseAvePrice).setValue(footSelledBaseTotalPrice.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		
		footRow.getCell(tbl_haveSellBaseTotPrice).setValue(footSelledBaseTotalPrice);
		if(footSelledDealTotalPrice==null || footSelledDealTotalPrice.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellDealAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellDealAvePrice).setValue(footSelledDealTotalPrice.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		footRow.getCell(tbl_haveSellDealTotPrice).setValue(footSelledDealTotalPrice);
		
		footRow.getCell(tbl_haveSellPrefTotPrice).setValue(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice));
		if(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice)==null || FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice).compareTo(new BigDecimal(0))==0
				|| footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellPrefAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellPrefAvePrice).setValue(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice).divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}				
		footRow.getCell(tbl_unSellCount).setValue(new Integer(footUnsellCount.intValue()));
		footRow.getCell(tbl_unSellArea).setValue(footUnsellArea);
		if(footUnsellStandPrice==null || footUnsellStandPrice.compareTo(new BigDecimal(0))==0 
				|| footUnsellArea==null || footUnsellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_unSellStandAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_unSellStandAvePrice).setValue(footUnsellStandPrice.divide(footUnsellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(footUnsellStandPrice, footUnsellArea));
		footRow.getCell(tbl_unSellStandTotPrice).setValue(footUnsellStandPrice);
		
		footRow.getCell(tbl_sumGathering).setValue(footSumGathering);
		footRow.getCell(tbl_RoomCompenstate).setValue(footRoomComp);
		
		footRow.getCell(tbl_querySellCount).setValue(new Integer(footPeriodRoomCount.intValue()));
		footRow.getCell(tbl_querySellArea).setValue(footPeriodSellArea);
		if(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt)==null || FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt).compareTo(new BigDecimal(0))==0
				|| footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryPrefAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt).divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}	
//		footRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.divide(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt), footPeriodSellArea));
		footRow.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt));
		
		if(footPeriodDealTotAmt==null || footPeriodDealTotAmt.compareTo(new BigDecimal(0))==0 
				|| footPeriodSellArea==null || footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryDealAvePrice).setValue(footPeriodDealTotAmt.divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(footPeriodDealTotAmt, footPeriodSellArea));
		footRow.getCell(tbl_queryDealTotPrice).setValue(footPeriodDealTotAmt);
		if(footPeriodStandTotAmt==null || footPeriodStandTotAmt.compareTo(new BigDecimal(0))==0 
				|| footPeriodSellArea==null || footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryStdAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryStdAvePrice).setValue(footPeriodStandTotAmt.divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(footPeriodStandTotAmt, footPeriodSellArea));
		footRow.getCell(tbl_queryStdTotPrice).setValue(footPeriodStandTotAmt);
		
		footRow.getCell(tbl_querySumGathering).setValue(footPeriodSumGathering);
		footRow.getCell(tbl_querySellSumPrice).setValue(footPeriodSellTotAmt);
		footRow.getCell(tbl_queryPurSumPrice).setValue(footPeriodPurTotAmt);
		footRow.getCell(tbl_querySignSumPrice).setValue(footPeriodSignTotAmt);
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//��ȡ���˽���Ĳ���
	/**
	 * ��������
	 * @return
	 * @throws Exception
	 */
	private Date getEndQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).getEndQueryDate(para);
	}
	/**
	 * ��ʼ����
	 * @return
	 * @throws Exception
	 */
	private Date getBeginQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).getBeginQueryDate(para);
	}
	
	private boolean isIncludeAttachment() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).isIncludeAttach(param);
	}
	private boolean isIncludePreOrder() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isPreIntoSale(param);
	}
	
	/**
	 * ���Ϊ����Ϊ������������Ϊ����Ϊ�������
	 * @return
	 * @throws Exception
	 */
	private boolean isBuildArea() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isBuildArea(param);
	}
	
	/**
	 * ���Ϊ����ΪԤ����������Ϊ����Ϊʵ�����
	 * @return
	 * @throws Exception
	 */
	private boolean isPreArea() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isPreArea(param);
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		//super.tblMain_tableSelectChanged(e);
	}
	
	protected void chkShowEtyLine_actionPerformed(ActionEvent e)
	throws Exception {

		for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
		{
			IRow tmpRow = tblMain.getRow(rowIndex);
			if(tmpRow.getCell(tbl_key).getValue() != null && checkIsEmptyLine(tmpRow))
			{
				tmpRow.getStyleAttributes().setHided(chkShowEtyLine.isSelected());
			}
		}
	}
	
	private boolean checkIsEmptyLine(IRow paramRow)
	{
		boolean empty = true;
		
		for(int colIndex = 2; colIndex < tblMain.getColumnCount(); ++colIndex)
		{
			if(paramRow.getCell(colIndex).getValue() != null)
			{
				empty = false;
				break;
			}
		}
		
		return empty;
	}
}