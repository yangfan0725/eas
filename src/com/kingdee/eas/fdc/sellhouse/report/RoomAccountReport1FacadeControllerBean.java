package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomAccountReport1FacadeControllerBean extends AbstractRoomAccountReport1FacadeControllerBean
{
	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.RoomAccountReport1FacadeControllerBean");
	protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
	{
		return null;
	}
	protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
		RptParams pp = new RptParams();
		return pp;
	}
	private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
		col= new RptTableColumn(name);
		col.setWidth(width);
		col.setHided(isHide);
		header.addColumn(col);
	}

	@Override
	protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
		RptTableHeader header = new RptTableHeader();
		RptTableColumn col = null;

		initColoum(header,col,"sellProjectId",100,true);//��Ŀ
		initColoum(header,col,"sellProjectName",100,false);
		initColoum(header,col,"productTypeId",100,true);//ҵ̬
		initColoum(header,col,"productTypeName",100,false);

		//¥��
		initColoum(header,col,"buildId",100,true);
		initColoum(header,col,"¥�����",100,false);
		initColoum(header,col,"¥������",100,false);

		//�ܻ�ֵ
		initColoum(header,col,"�ܻ�ֵ����",100,false);
		initColoum(header,col,"�ܻ�ֵ�������",100,false);
		initColoum(header,col,"�ܻ�ֵ�ܽ��",100,false);
		initColoum(header,col,"�ܻ�ֵ����",100,false);

		//���۷�Դ
		initColoum(header,col,"���۷�Դ����",100,false);
		initColoum(header,col,"���۷�Դ�������",100,false);
		initColoum(header,col,"���۷�Դ�ܽ��",100,false);
		initColoum(header,col,"���۷�Դ����",100,false);

		//�Ϲ�δתǩԼ
		initColoum(header,col,"�Ϲ�δתǩԼ����",100,false);
		initColoum(header,col,"�Ϲ�δתǩԼ�������",100,false);
		initColoum(header,col,"�Ϲ�δתǩԼ�ܽ��",100,false);
		initColoum(header,col,"�Ϲ�δתǩԼ����",100,false);

		//δ���̷�Դ
		initColoum(header,col,"δ���̷�Դ����",100,false);
		initColoum(header,col,"δ���̷�Դ�������",100,false);
		initColoum(header,col,"δ���̷�Դ�ܽ��",100,false);
		initColoum(header,col,"δ���̷�Դ����",100,false);

		//���۷�Դ
		initColoum(header,col,"���۷�Դ����",100,false);
		initColoum(header,col,"���۷�Դ�������",100,false);
		initColoum(header,col,"���۷�Դ�ܽ��",100,false);
		initColoum(header,col,"���۷�Դ����",100,false);

		//����/Ԥ����Դ
		initColoum(header,col,"��Ԥ��Դ����",100,false);
		initColoum(header,col,"��Ԥ��Դ�������",100,false);
		initColoum(header,col,"��Ԥ��Դ�ܽ��",100,false);
		initColoum(header,col,"��Ԥ��Դ����",100,false);

		//���۷�Դ
		initColoum(header,col,"���۷�Դ����",100,false);
		initColoum(header,col,"���۷�Դ�������",100,false);
		initColoum(header,col,"���۷�Դ�ܽ��",100,false);
		initColoum(header,col,"���۷�Դ����",100,false);

		//1�£�ǩԼ���ݣ�
		initColoum(header,col,"1������",100,false);
		initColoum(header,col,"1�½������",100,false);
		initColoum(header,col,"1���ܽ��",100,false);
		initColoum(header,col,"1�¾���",100,false);
		//2�£�ǩԼ���ݣ�
		initColoum(header,col,"2������",100,false);
		initColoum(header,col,"2�½������",100,false);
		initColoum(header,col,"2���ܽ��",100,false);
		initColoum(header,col,"2�¾���",100,false);
		//3�£�ǩԼ���ݣ�
		initColoum(header,col,"3������",100,false);
		initColoum(header,col,"3�½������",100,false);
		initColoum(header,col,"3���ܽ��",100,false);
		initColoum(header,col,"3�¾���",100,false);
		//4�£�ǩԼ���ݣ�
		initColoum(header,col,"4������",100,false);
		initColoum(header,col,"4�½������",100,false);
		initColoum(header,col,"4���ܽ��",100,false);
		initColoum(header,col,"4�¾���",100,false);
		//5�£�ǩԼ���ݣ�
		initColoum(header,col,"5������",100,false);
		initColoum(header,col,"5�½������",100,false);
		initColoum(header,col,"5���ܽ��",100,false);
		initColoum(header,col,"5�¾���",100,false);
		//6�£�ǩԼ���ݣ�
		initColoum(header,col,"6������",100,false);
		initColoum(header,col,"6�½������",100,false);
		initColoum(header,col,"6���ܽ��",100,false);
		initColoum(header,col,"6�¾���",100,false);
		//7�£�ǩԼ���ݣ�
		initColoum(header,col,"7������",100,false);
		initColoum(header,col,"7�½������",100,false);
		initColoum(header,col,"7���ܽ��",100,false);
		initColoum(header,col,"7�¾���",100,false);
		//8�£�ǩԼ���ݣ�
		initColoum(header,col,"8������",100,false);
		initColoum(header,col,"8�½������",100,false);
		initColoum(header,col,"8���ܽ��",100,false);
		initColoum(header,col,"8�¾���",100,false);
		//9�£�ǩԼ���ݣ�
		initColoum(header,col,"9������",100,false);
		initColoum(header,col,"9�½������",100,false);
		initColoum(header,col,"9���ܽ��",100,false);
		initColoum(header,col,"9�¾���",100,false);
		//10�£�ǩԼ���ݣ�
		initColoum(header,col,"10������",100,false);
		initColoum(header,col,"10�½������",100,false);
		initColoum(header,col,"10���ܽ��",100,false);
		initColoum(header,col,"10�¾���",100,false);
		//11�£�ǩԼ���ݣ�
		initColoum(header,col,"11������",100,false);
		initColoum(header,col,"11�½������",100,false);
		initColoum(header,col,"11���ܽ��",100,false);
		initColoum(header,col,"11�¾���",100,false);
		//12�£�ǩԼ���ݣ�
		initColoum(header,col,"12������",100,false);
		initColoum(header,col,"12�½������",100,false);
		initColoum(header,col,"12���ܽ��",100,false);
		initColoum(header,col,"12�¾���",100,false);

		header.setLabels(new Object[][]{ 
				{
					"sellProjectId","��Ŀ","productTypeId","ҵ̬",
					"buildId","¥��","¥��",
					"�ܻ�ֵA=B+C+D+E","�ܻ�ֵA=B+C+D+E","�ܻ�ֵA=B+C+D+E"	,"�ܻ�ֵA=B+C+D+E",
					"���۷�ԴB"		,"���۷�ԴB"		,"���۷�ԴB"		,"���۷�ԴB",
					"�Ϲ�δתǩԼC"	,"�Ϲ�δתǩԼC"	,"�Ϲ�δתǩԼC"	,"�Ϲ�δתǩԼC",
					"δ���̷�ԴD"	,"δ���̷�ԴD"	,"δ���̷�ԴD"	,"δ���̷�ԴD",
					"���۷�ԴE"		,"���۷�ԴE"		,"���۷�ԴE"		,"���۷�ԴE",
					"����/Ԥ����ԴF"	,"����/Ԥ����ԴF","����/Ԥ����ԴF","����/Ԥ����ԴF",
					"���۷�ԴG=E-F"	,"���۷�ԴG=E-F"	,"���۷�ԴG=E-F"	,"���۷�ԴG=E-F",
					"1�£�ǩԼ���ݣ�","1�£�ǩԼ���ݣ�","1�£�ǩԼ���ݣ�","1�£�ǩԼ���ݣ�",
					"2�£�ǩԼ���ݣ�","2�£�ǩԼ���ݣ�","2�£�ǩԼ���ݣ�","2�£�ǩԼ���ݣ�",
					"3�£�ǩԼ���ݣ�","3�£�ǩԼ���ݣ�","3�£�ǩԼ���ݣ�","3�£�ǩԼ���ݣ�",
					"4�£�ǩԼ���ݣ�","4�£�ǩԼ���ݣ�","4�£�ǩԼ���ݣ�","4�£�ǩԼ���ݣ�",
					"5�£�ǩԼ���ݣ�","5�£�ǩԼ���ݣ�","5�£�ǩԼ���ݣ�","5�£�ǩԼ���ݣ�",
					"6�£�ǩԼ���ݣ�","6�£�ǩԼ���ݣ�","6�£�ǩԼ���ݣ�","6�£�ǩԼ���ݣ�",
					"7�£�ǩԼ���ݣ�","7�£�ǩԼ���ݣ�","7�£�ǩԼ���ݣ�","7�£�ǩԼ���ݣ�",
					"8�£�ǩԼ���ݣ�","8�£�ǩԼ���ݣ�","8�£�ǩԼ���ݣ�","8�£�ǩԼ���ݣ�",
					"9�£�ǩԼ���ݣ�","9�£�ǩԼ���ݣ�","9�£�ǩԼ���ݣ�","9�£�ǩԼ���ݣ�",
					"10�£�ǩԼ���ݣ�","10�£�ǩԼ���ݣ�","10�£�ǩԼ���ݣ�","10�£�ǩԼ���ݣ�",
					"11�£�ǩԼ���ݣ�","11�£�ǩԼ���ݣ�","11�£�ǩԼ���ݣ�","11�£�ǩԼ���ݣ�",
					"12�£�ǩԼ���ݣ�","12�£�ǩԼ���ݣ�","12�£�ǩԼ���ݣ�","12�£�ǩԼ���ݣ�"
				}
				,
				{
					"sellProjectId","��Ŀ","productTypeId","ҵ̬",
					"buildId","���","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����",
					"����","�������","�ܽ��","����"
				}
		},true);
		params.setObject("header", header);
		return params;
	}

	//����ѯ����
	protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);;
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
	}

	//���Ĳ�ѯ����
	protected String getSql(Context ctx,RptParams params){
		SellProjectInfo sellProject = (SellProjectInfo) params.getObject("sellProject");//��Ŀ���ϣ�T_SHE_SellProject
		String sellProjectStr=null;
		if(sellProject!=null){
			try {
				sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(ctx,sellProject));
			} catch (BOSException e) {
				e.printStackTrace();
			} 
		}
		ProductTypeInfo productType=(ProductTypeInfo)params.getObject("productType");//��Ʒ����,T_FDC_ProductType
		String productTypeId=null;
		if(productType!=null){
			productTypeId=productType.getId().toString();
		}
		String org=(String) params.getObject("org");//��֯

		Date fromDate = (Date)params.getObject("fromDate");//��ʼ����
		Date toDate =   (Date)params.getObject("toDate");//��������
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		String year = String.valueOf(calendar.get(calendar.YEAR));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		StringBuffer sb = new StringBuffer();

		//		sb.append(" /*dialect*/ select t.sellProjectId,t.sellProjectName as \"��Ŀ\",");
		//		sb.append(" t.productTypeId,");
		//		sb.append(" t.productTypeName as \"ҵ̬\",");
		//		sb.append(" t.buildFnumber as \"¥�����\",");
		//		sb.append(" t.buildFname_L2 as \"¥������\",");
		//		sb.append(" nvl(t.SignQty + t.PurchaseQty + t.InitQty + t.OnshowQty,0)  as \"�ܻ�ֵ����\",");
		//		sb.append(" nvl(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea,0.00) as \"�ܻ�ֵ�������\",");
		//		sb.append(" nvl(t.Signaccount + t.Purchaseaccount + t.Initaccount + t.Onshowaccount,0.00) as \"�ܻ�ֵ�ܽ��\",");
		//		sb.append(" nvl(round(decode(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea,0,0,(t.Signaccount + t.Purchaseaccount + t.Initaccount + t.Onshowaccount)/(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea)),2),0.00)  as \"�ܻ�ֵ����\",");
		//
		//		sb.append(" nvl(t.SignQty,0) as \"���۷�Դ����\",");            
		//		sb.append(" nvl(t.SignbuildArea,0.00) as \"���۷�Դ�������\",");                   
		//		sb.append(" nvl(t.Signaccount,0.00) as \"���۷�Դ�ܽ��\",");
		//		sb.append(" nvl(round(decode(t.SignbuildArea,0,0,t.Signaccount/t.SignbuildArea),2),0.00) as \"���۷�Դ����\",");
		//
		//		sb.append(" nvl(t.PurchaseQty,0) as \"�Ϲ�δתǩԼ����\",");
		//		sb.append(" nvl(t.PurchasebuildArea,0.00) as \"�Ϲ�δתǩԼ�������\",");
		//		sb.append(" nvl(t.Purchaseaccount,0.00) as \"�Ϲ�δתǩԼ�ܽ��\",");
		//		sb.append(" nvl(round(decode(t.PurchasebuildArea,0,0,t.Purchaseaccount/t.PurchasebuildArea),2),0.00) as \"�Ϲ�δתǩԼ����\",");
		//
		//		sb.append(" nvl(t.InitQty,0) as \"δ���̷�Դ����\",");
		//		sb.append(" nvl(t.InitbuildArea,0.00)  as \"δ���̷�Դ�������\",");
		//		sb.append(" nvl(t.Initaccount,0.00) as \"δ���̷�Դ�ܽ��\",");
		//		sb.append(" nvl(round(decode(t.InitbuildArea,0,0,t.Initaccount/t.InitbuildArea),2),0.00) as \"δ���̷�Դ����\",");
		//
		//		sb.append(" nvl(t.OnshowQty + t.KeepDownQty,0) as \"���۷�Դ����\",");
		//		sb.append(" nvl(t.OnshowbuildArea + t.KeepDownbuildArea,0.00) as \"���۷�Դ�������\",");
		//		sb.append(" nvl(t.Onshowaccount + t.KeepDownaccount,0.00) as \"���۷�Դ�ܽ��\",");
		//		sb.append(" nvl(decode(t.OnshowbuildArea + t.KeepDownbuildArea ,0,0,(t.Onshowaccount + t.KeepDownaccount)/(t.OnshowbuildArea + t.KeepDownbuildArea)),0.00) as \"���۷�Դ����\",");
		//
		//		sb.append(" nvl(t.KeepDownQty,0) as \"��Ԥ��Դ����\",");
		//		sb.append(" nvl(t.KeepDownbuildArea,0.00) as \"��Ԥ��Դ�������\",");
		//		sb.append(" nvl(t.KeepDownaccount,0.00) \"��Ԥ��Դ�ܽ��\",");
		//		sb.append(" nvl(round(decode(t.KeepDownbuildArea,0,0,t.KeepDownaccount/t.KeepDownbuildArea),2),0.00) as \"��Ԥ��Դ����\",");
		//
		//		sb.append(" nvl(t.OnshowQty + t.KeepDownQty - t.KeepDownQty,0) as \"���۷�Դ����\",");
		//		sb.append(" nvl(t.OnshowbuildArea + t.KeepDownbuildArea - t.KeepDownbuildArea,0.00) as \"���۷�Դ�������\",");
		//		sb.append(" nvl(t.Onshowaccount + t.KeepDownaccount - t.KeepDownaccount,0.00) as \"���۷�Դ�ܽ��\",");
		//		sb.append(" nvl(decode(t.OnshowbuildArea - t.KeepDownbuildArea,0,0,(t.Onshowaccount - t.KeepDownaccount)/(t.OnshowbuildArea - t.KeepDownbuildArea)),0.00) as \"���۷�Դ����\",");
		//
		//		sb.append(" nvl(t1.JanQty,0) as \"1������\",");
		//		sb.append(" nvl(t1.JanbuildArea,0.00)  as \"1�½������\",");
		//		sb.append(" nvl(t1.Janaccount,0.00) as \"1���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.JanbuildArea ,0,0,Janaccount/JanbuildArea ),2),0.00) as \"1�¾���\",");
		//
		//		sb.append(" nvl(t1.FebQty,0) as \"2������\",");
		//		sb.append(" nvl(t1.FebbuildArea,0.00) as \"2�½������\",");
		//		sb.append(" nvl(t1.Febaccount,0.00) as \"2���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.FebbuildArea,0,0,Febaccount/FebbuildArea),2),0.00) as \"2�¾���\" ,");
		//
		//		sb.append(" nvl(t1.MarQty,0) as \"3������\",");
		//		sb.append(" nvl(t1.MarbuildArea,0.00) as \"3�½������\",");
		//		sb.append(" nvl(t1.Maraccount,0.00) as \"3���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.MarbuildArea,0,0,Maraccount/MarbuildArea),2),0.00) as \"3�¾���\",");
		//
		//		sb.append(" nvl(t1.AprQty,0) as \"4������\",");
		//		sb.append(" nvl(t1.AprbuildArea,0.00) as \"4�½������\",");
		//		sb.append(" nvl(t1.Apraccount,0.00) as \"4���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.AprbuildArea,0,0,Apraccount/AprbuildArea),2),0.00) as \"4�¾���\",");
		//
		//		sb.append(" nvl(t1.MayQty,0) as \"5������\",");
		//		sb.append(" nvl(t1.MaybuildArea,0.00) as \"5�½������\",");
		//		sb.append(" nvl(t1.Mayaccount,0.00) as \"5���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.MaybuildArea,0,0,Mayaccount/MaybuildArea),2),0.00) as \"5�¾���\",");
		//
		//		sb.append(" nvl(t1.JunQty,0) as \"6������\",");
		//		sb.append(" nvl(t1.JunbuildArea,0.00) as \"6�½������\",");
		//		sb.append(" nvl(t1.Junaccount,0.00) as \"6���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.JunbuildArea,0,0,Junaccount/JunbuildArea),2),0.00) as \"6�¾���\",");
		//
		//		sb.append(" nvl(t1.JulQty,0) as \"7������\",");
		//		sb.append(" nvl(t1.JulbuildArea,0.00) as \"7�½������\",");
		//		sb.append(" nvl(t1.Julaccount,0.00) as \"7���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.JulbuildArea,0,0,Julaccount/JulbuildArea),2),0.00) as \"7�¾���\",");
		//
		//		sb.append(" nvl(t1.AugQty,0) as \"8������\",");
		//		sb.append(" nvl(t1.AugbuildArea,0.00) as \"8�½������\",");
		//		sb.append(" nvl(t1.Augaccount,0.00) as \"8���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.AugbuildArea,0,0,Augaccount/AugbuildArea),2),0.00) as \"8�¾���\",");
		//
		//		sb.append(" nvl(t1.SepQty,0) as \"9������\",");
		//		sb.append(" nvl(t1.SepbuildArea,0.00) as \"9�½������\",");
		//		sb.append(" nvl(t1.Sepaccount,0.00) as \"9���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.SepbuildArea,0,0,Sepaccount/SepbuildArea),2),0.00) as \"9�¾���\",");
		//
		//		sb.append(" nvl(t1.OctQty,0) as \"10������\",");
		//		sb.append(" nvl(t1.OctbuildArea,0.00) as \"10�½������\",");
		//		sb.append(" nvl(t1.Octaccount,0.00) as \"10���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.OctbuildArea,0,0,Octaccount/OctbuildArea),2),0.00) as \"10�¾���\",");
		//
		//		sb.append(" nvl(t1.NovQty,0) as \"11������\",");
		//		sb.append(" nvl(t1.NovbuildArea,0.00) as \"11�½������\",");
		//		sb.append(" nvl(t1.Novaccount,0.00) as \"11���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.NovbuildArea,0,0,Novaccount/NovbuildArea),2),0.00) as \"11�¾���\",");
		//
		//		sb.append(" nvl(t1.DecQty,0) as \"12������\",");
		//		sb.append(" nvl(t1.DecbuildArea,0.00) as \"12�½������\",");
		//		sb.append(" nvl(t1.Decaccount,0.00) as \"12���ܽ��\",");
		//		sb.append(" nvl(round(decode(t1.DecbuildArea,0,0,Decaccount/DecbuildArea),2),0.00) as \"12�¾���\"");
		//
		//		sb.append(" from (select sellProject.fid sellProjectId,");
		//		sb.append(" sellProject.fname_l2 sellProjectName,");
		//		sb.append(" productType.fid productTypeId,");
		//		sb.append(" productType.fname_l2 productTypeName,");
		//		sb.append(" build.fnumber buildFnumber,");
		//		sb.append(" build.FName_l2  buildFname_L2,");
		//
		//		sb.append(" count(decode(room.fsellstate,'Sign',1)) SignQty,");
		//		sb.append(" sum(decode(room.fsellstate,'Sign',room.factualBuildingArea,0)) SignbuildArea,");
		//		sb.append(" sum(decode(room.fsellstate,'Sign',room.FDealTotalAmount,0)) Signaccount,");
		//
		//		sb.append(" count(decode(room.fsellstate,'Purchase',1)) PurchaseQty,");
		//		sb.append(" sum(decode(room.fsellstate,'Purchase',room.factualBuildingArea,0)) PurchasebuildArea,");
		//		sb.append(" sum(decode(room.fsellstate,'Purchase',room.fstandardTotalAmount,0)) Purchaseaccount,");
		//
		//		sb.append(" count(decode(room.fsellstate,'Init',1)) InitQty,");
		//		sb.append(" sum(decode(room.fsellstate,'Init',room.factualBuildingArea,0)) InitbuildArea,");
		//		sb.append(" sum(decode(room.fsellstate,'Init',room.FBaseStandardPrice,0)) Initaccount,");
		//
		//		sb.append(" count(decode(room.fsellstate,'Onshow',1)) OnshowQty,");
		//		sb.append(" sum(decode(room.fsellstate,'Onshow',room.factualBuildingArea,0)) OnshowbuildArea,");
		//		sb.append(" sum(decode(room.fsellstate,'Onshow',room.FBaseStandardPrice,0)) Onshowaccount,");
		//
		//		sb.append(" count(decode(room.fsellstate,'KeepDown',1)) KeepDownQty,");
		//		sb.append(" sum(decode(room.fsellstate,'KeepDown',room.factualBuildingArea,0)) KeepDownbuildArea,");
		//		sb.append(" sum(decode(room.fsellstate,'KeepDown',room.FBaseStandardPrice,0)) KeepDownaccount");
		//
		//		sb.append(" from T_SHE_Room room");
		//		sb.append(" left join t_she_building build on build.fid = room.fbuildingid");
		//		sb.append(" left join t_she_sellProject sellproject on sellProject.fid = build.fsellProjectid");
		//		sb.append(" inner join T_FDC_ProductType productType on productType.fid = room.fproductTypeid");
		//		sb.append(" where 1=1");
		//
		//    	if(sellProjectStr!=null){
		//			sb.append(" and sellProject.fid in ("+sellProjectStr+")");
		//		}else if(org!=null){
		//			sb.append(" and sellProject.fid in ("+org+")");
		//		}else{
		//			sb.append(" and sellProject.fid in ('null')");
		//		}
		//		if(productTypeId!=null){
		//			sb.append(" and productType.fid ='"+productTypeId+"'");
		//		}
		//
		//		sb.append(" group by sellProject.fid,");
		//		sb.append(" sellProject.fname_l2,");
		//		sb.append(" build.fnumber, ");
		//		sb.append(" build.FName_l2,");
		//		sb.append(" productType.fid,");
		//		sb.append(" productType.fname_l2) t");
		//		
		//		sb.append(" left join (select  sellProject.fid sellProjectId,");
		//		sb.append(" sellProject.fname_l2 sellProjectName,");
		//		sb.append(" productType.fid productTypeId,");
		//		sb.append(" productType.fname_l2 productTypeName,");
		//		sb.append(" build.fnumber buildFnumber,");
		//		sb.append(" build.FName_l2  buildFname_L2,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',1)) JanQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',room.factualBuildingArea,0)) JanbuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',room.FDealTotalAmount,0)) Janaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',1)) FebQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',room.factualBuildingArea,0)) FebbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',room.FDealTotalAmount,0)) Febaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',1)) MarQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',room.factualBuildingArea,0)) MarbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',room.FDealTotalAmount,0)) Maraccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',1)) AprQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',room.factualBuildingArea,0)) AprbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',room.FDealTotalAmount,0)) Apraccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',1)) MayQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',room.factualBuildingArea,0)) MaybuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',room.FDealTotalAmount,0)) Mayaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',1)) JunQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',room.factualBuildingArea,0)) JunbuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',room.FDealTotalAmount,0)) Junaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',1)) JulQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',room.factualBuildingArea,0)) JulbuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',room.FDealTotalAmount,0)) Julaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',1)) AugQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',room.factualBuildingArea,0)) AugbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',room.FDealTotalAmount,0)) Augaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',1)) SepQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',room.factualBuildingArea,0)) SepbuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',room.FDealTotalAmount,0)) Sepaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',1)) OctQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',room.factualBuildingArea,0)) OctbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',room.FDealTotalAmount,0)) Octaccount,");  
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',1)) NovQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',room.factualBuildingArea,0)) NovbuildArea,");   
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',room.FDealTotalAmount,0)) Novaccount,");
		//
		//		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',1)) DecQty,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',room.factualBuildingArea,0)) DecbuildArea,");
		//		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',room.FDealTotalAmount,0)) Decaccount"); 
		//
		//		sb.append(" from T_SHE_Room room");
		//		sb.append(" left join t_she_building build on build.fid = room.fbuildingid");
		//		sb.append(" left join t_she_sellProject sellproject on sellProject.fid = build.fsellProjectid");
		//		sb.append(" inner join T_FDC_ProductType productType on productType.fid = room.fproductTypeid");
		//		sb.append(" inner join T_SHE_SIGNMANAGE signmanage on signmanage.FRoomID = room.fid");
		//		sb.append(" where 1=1");
		//		sb.append(" and to_char(signmanage.FBusAdscriptionDate,'yyyy-mm-dd') >="+"'"+sf.format(fromDate)+"'");
		//		sb.append(" and to_char(signmanage.FBusAdscriptionDate,'yyyy-mm-dd') <="+"'"+sf.format(toDate)+"'");
		//		sb.append(" group by sellProject.fid,");
		//		sb.append(" sellProject.fname_l2,");
		//		sb.append(" build.fnumber,");
		//		sb.append(" build.FName_l2,");
		//		sb.append(" productType.fid,");
		//		sb.append(" productType.fname_l2) t1");
		//		sb.append(" on t.sellProjectId = t1.sellProjectId");
		//		sb.append(" and t.sellProjectName = t1.sellProjectName");
		//		sb.append(" and t.productTypeId = t1.productTypeId");
		//		sb.append(" and t.productTypeName = t1.productTypeName");
		//		sb.append(" and t.buildFnumber = t1.buildFnumber");
		//		sb.append(" and t.buildFname_L2 = t1.buildFname_L2");
		//
		//		sb.append(" order by t.sellProjectName desc,");
		//		sb.append(" t.productTypeName desc,");
		//		sb.append(" t.buildFnumber desc,");
		//		sb.append(" t.buildFname_L2 desc");

		sb.append(" /*dialect*/ select t.sellProjectId,t.sellProjectName as \"��Ŀ\",");
		sb.append(" t.productTypeId,");
		sb.append(" t.productTypeName as \"ҵ̬\",");
		sb.append(" t.buildId,");
		sb.append(" t.buildFnumber as \"¥�����\",");
		sb.append(" t.buildFname_L2 as \"¥������\",");
		sb.append(" nvl(t.SignQty + t.PurchaseQty + t.InitQty + t.OnshowQty + t.KeepDownQty,0)  as \"�ܻ�ֵ����\",");
		sb.append(" nvl(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea + t.KeepDownbuildArea,0.00) as \"�ܻ�ֵ�������\",");
		sb.append(" nvl(t.Signaccount + t.Purchaseaccount + t.Initaccount + t.Onshowaccount + t.KeepDownaccount,0.00) as \"�ܻ�ֵ�ܽ��\",");
		sb.append(" nvl(decode(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea + t.KeepDownbuildArea,0,0,(t.Signaccount + t.Purchaseaccount + t.Initaccount + t.Onshowaccount + t.KeepDownaccount)/(t.SignbuildArea + t.PurchasebuildArea + t.InitbuildArea + t.OnshowbuildArea + t.KeepDownbuildArea)),0.00)  as \"�ܻ�ֵ����\",");

		sb.append(" nvl(t.SignQty,0) as \"���۷�Դ����\",   ");            
		sb.append(" nvl(t.SignbuildArea,0.00)   as \"���۷�Դ�������\", ");
		sb.append(" nvl(t.Signaccount,0.00)  as \"���۷�Դ�ܽ��\", ");
		sb.append(" nvl(decode(t.SignbuildArea,0,0,t.Signaccount/t.SignbuildArea),0.00) as \"���۷�Դ����\",");

		sb.append(" nvl(t.PurchaseQty,0) as \"�Ϲ�δתǩԼ����\",");
		sb.append(" nvl(t.PurchasebuildArea,0.00) as \"�Ϲ�δתǩԼ�������\",");
		sb.append(" nvl(t.Purchaseaccount,0.00) as \"�Ϲ�δתǩԼ�ܽ��\",");
		sb.append(" nvl(decode(t.PurchasebuildArea,0,0,t.Purchaseaccount/t.PurchasebuildArea),0.00) as \"�Ϲ�δתǩԼ����\",");

		sb.append(" nvl(t.InitQty,0) as \"δ���̷�Դ����\",");
		sb.append(" nvl(t.InitbuildArea,0.00)  as \"δ���̷�Դ�������\",");
		sb.append(" nvl(t.Initaccount,0.00) as \"δ���̷�Դ�ܽ��\",");
		sb.append(" nvl(decode(t.InitbuildArea,0,0,t.Initaccount/t.InitbuildArea),0.00) as \"δ���̷�Դ����\",");

		sb.append(" nvl(t.OnshowQty + t.KeepDownQty,0) as \"���۷�Դ����\",");
		sb.append(" nvl(t.OnshowbuildArea + t.KeepDownbuildArea,0.00) as \"���۷�Դ�������\",");
		sb.append(" nvl(t.Onshowaccount + t.KeepDownaccount,0.00) as \"���۷�Դ�ܽ��\",");
		sb.append(" nvl(decode(t.OnshowbuildArea + t.KeepDownbuildArea ,0,0,(t.Onshowaccount + t.KeepDownaccount)/(t.OnshowbuildArea + t.KeepDownbuildArea)),0.00) as \"���۷�Դ����\",");

		sb.append(" nvl(t.KeepDownQty,0) as \"��Ԥ��Դ����\",");
		sb.append(" nvl(t.KeepDownbuildArea,0.00) as \"��Ԥ��Դ�������\",");
		sb.append(" nvl(t.KeepDownaccount,0.00) \"��Ԥ��Դ�ܽ��\",");
		sb.append(" nvl(decode(t.KeepDownbuildArea,0,0,t.KeepDownaccount/t.KeepDownbuildArea),0.00) as \"��Ԥ��Դ����\",");

		sb.append(" nvl(t.OnshowQty + t.KeepDownQty - t.KeepDownQty,0) as \"���۷�Դ����\",");
		sb.append(" nvl(t.OnshowbuildArea + t.KeepDownbuildArea - t.KeepDownbuildArea,0.00) as \"���۷�Դ�������\",");
		sb.append(" nvl(t.Onshowaccount + t.KeepDownaccount - t.KeepDownaccount,0.00) as \"���۷�Դ�ܽ��\",");
		sb.append(" nvl(decode(t.OnshowbuildArea - t.KeepDownbuildArea,0,0,(t.Onshowaccount - t.KeepDownaccount)/(t.OnshowbuildArea - t.KeepDownbuildArea)),0.00) as \"���۷�Դ����\",");

		sb.append(" nvl(t1.JanQty,0) as \"1������\",");
		sb.append(" nvl(t1.JanbuildArea,0.00)  as \"1�½������\",");
		sb.append(" nvl(t1.Janaccount,0.00) as \"1���ܽ��\",");
		sb.append(" nvl(decode(t1.JanbuildArea ,0,0,Janaccount/JanbuildArea ),0.00) as \"1�¾���\", ");

		sb.append(" nvl(t1.FebQty,0) as \"2������\",");
		sb.append(" nvl(t1.FebbuildArea,0.00) as \"2�½������\",");
		sb.append(" nvl(t1.Febaccount,0.00) as \"2���ܽ��\",");
		sb.append(" nvl(decode(t1.FebbuildArea,0,0,Febaccount/FebbuildArea),0.00) as \"2�¾���\" ,");

		sb.append(" nvl(t1.MarQty,0) as \"3������\",");
		sb.append(" nvl(t1.MarbuildArea,0.00) as \"3�½������\",");
		sb.append(" nvl(t1.Maraccount,0.00) as \"3���ܽ��\",");
		sb.append(" nvl(decode(t1.MarbuildArea,0,0,Maraccount/MarbuildArea),0.00) as \"3�¾���\", ");

		sb.append(" nvl(t1.AprQty,0) as \"4������\",");
		sb.append(" nvl(t1.AprbuildArea,0.00) as \"4�½������\",");
		sb.append(" nvl(t1.Apraccount,0.00) as \"4���ܽ��\",");
		sb.append(" nvl(decode(t1.AprbuildArea,0,0,Apraccount/AprbuildArea),0.00) as \"4�¾���\",");

		sb.append(" nvl(t1.MayQty,0) as \"5������\",");
		sb.append(" nvl(t1.MaybuildArea,0.00) as \"5�½������\",");
		sb.append(" nvl(t1.Mayaccount,0.00) as \"5���ܽ��\",");
		sb.append(" nvl(decode(t1.MaybuildArea,0,0,Mayaccount/MaybuildArea),0.00) as \"5�¾���\", ");

		sb.append(" nvl(t1.JunQty,0) as \"6������\",");
		sb.append(" nvl(t1.JunbuildArea,0.00) as \"6�½������\",");
		sb.append(" nvl(t1.Junaccount,0.00) as \"6���ܽ��\",");
		sb.append(" nvl(decode(t1.JunbuildArea,0,0,Junaccount/JunbuildArea),0.00) as \"6�¾���\",");

		sb.append(" nvl(t1.JulQty,0) as \"7������\",");
		sb.append(" nvl(t1.JulbuildArea,0.00) as \"7�½������\",");
		sb.append(" nvl(t1.Julaccount,0.00) as \"7���ܽ��\",");
		sb.append(" nvl(decode(t1.JulbuildArea,0,0,Julaccount/JulbuildArea),0.00) as \"7�¾���\",");

		sb.append(" nvl(t1.AugQty,0) as \"8������\",");
		sb.append(" nvl(t1.AugbuildArea,0.00) as \"8�½������\",");
		sb.append(" nvl(t1.Augaccount,0.00) as \"8���ܽ��\",");
		sb.append(" nvl(decode(t1.AugbuildArea,0,0,Augaccount/AugbuildArea),0.00) as \"8�¾���\",");

		sb.append(" nvl(t1.SepQty,0) as \"9������\",");
		sb.append(" nvl(t1.SepbuildArea,0.00) as \"9�½������\",");
		sb.append(" nvl(t1.Sepaccount,0.00) as \"9���ܽ��\",");
		sb.append(" nvl(decode(t1.SepbuildArea,0,0,Sepaccount/SepbuildArea),0.00) as \"9�¾���\",");

		sb.append(" nvl(t1.OctQty,0) as \"10������\",");
		sb.append(" nvl(t1.OctbuildArea,0.00) as \"10�½������\",");
		sb.append(" nvl(t1.Octaccount,0.00) as \"10���ܽ��\",");
		sb.append(" nvl(decode(t1.OctbuildArea,0,0,Octaccount/OctbuildArea),0.00) as \"10�¾���\",");

		sb.append(" nvl(t1.NovQty,0) as \"11������\",");
		sb.append(" nvl(t1.NovbuildArea,0.00) as \"11�½������\",");
		sb.append(" nvl(t1.Novaccount,0.00) as \"11���ܽ��\",");
		sb.append(" nvl(decode(t1.NovbuildArea,0,0,Novaccount/NovbuildArea),0.00) as \"11�¾���\",");

		sb.append(" nvl(t1.DecQty,0) as \"12������\",");
		sb.append(" nvl(t1.DecbuildArea,0.00) as \"12�½������\",");
		sb.append(" nvl(t1.Decaccount,0.00) as \"12���ܽ��\",");
		sb.append(" nvl(decode(t1.DecbuildArea,0,0,Decaccount/DecbuildArea),0.00) as \"12�¾���\"");

		sb.append(" from (select sellProject.fid sellProjectId,");
		sb.append(" sellProject.fname_l2 sellProjectName,");
		sb.append(" productType.fid productTypeId,");
		sb.append(" productType.fname_l2 productTypeName,");
		sb.append(" build.fid buildId,   ");
		sb.append(" build.fnumber buildFnumber,   ");
		sb.append(" build.FName_l2  buildFname_L2,");


		sb.append(" count(decode(room.fsellstate,'Sign',1)) SignQty,");
		sb.append(" sum(decode(room.fsellstate,'Sign',room.fbuildingarea,0)) SignbuildArea, ");  
		sb.append(" sum(decode(room.fsellstate,'Sign',signmanage.FDealTotalAmount,0)) Signaccount,  ");

		sb.append(" count(decode(room.fsellstate,'Purchase',1)) PurchaseQty,");
		sb.append(" sum(decode(room.fsellstate,'Purchase',room.fbuildingarea,0)) PurchasebuildArea,  ");                        
		sb.append(" sum(decode(room.fsellstate,'Purchase',room.FBaseStandardPrice,0)) Purchaseaccount,  ");

		sb.append(" count(decode(room.fsellstate,'Init',1)) InitQty,");
		sb.append(" sum(decode(room.fsellstate,'Init',room.fbuildingarea,0)) InitbuildArea, ");                        
		sb.append(" sum(decode(room.fsellstate,'Init',room.FBaseStandardPrice,0)) Initaccount,  ");

		sb.append(" count(decode(room.fsellstate,'Onshow',1)) OnshowQty,");
		sb.append(" sum(decode(room.fsellstate,'Onshow',room.fbuildingarea,0)) OnshowbuildArea, ");
		sb.append(" sum(decode(room.fsellstate,'Onshow',room.FBaseStandardPrice,0)) Onshowaccount,  ");

		sb.append(" count(decode(room.fsellstate,'KeepDown',1)) KeepDownQty,");
		sb.append(" sum(decode(room.fsellstate,'KeepDown',room.fbuildingarea,0)) KeepDownbuildArea,  ");                        
		sb.append(" sum(decode(room.fsellstate,'KeepDown',room.FBaseStandardPrice,0)) KeepDownaccount  ");

		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid = room.fbuildingid");
		sb.append(" left join t_she_sellProject sellproject on sellProject.fid = build.fsellProjectid");
		sb.append(" left join (select distinct froomid,FDealTotalAmount from t_she_signmanage where fbizstate in('SignApple','SignAudit','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing')) signmanage on signmanage.froomid = room.fid");
		sb.append(" inner join T_FDC_ProductType productType on productType.fid = room.fproductTypeid");
		sb.append(" where 1=1 ");
		
		if(sellProjectStr!=null){
			sb.append(" and sellProject.fid in ("+sellProjectStr+")");
		}else if(org!=null){
			sb.append(" and sellProject.fid in ("+org+")");
		}else{
			sb.append(" and sellProject.fid in ('null')");
		}
		if(productTypeId!=null){
			sb.append(" and productType.fid ='"+productTypeId+"'");
		}
		
		sb.append(" group by sellProject.fid,");
		sb.append(" sellProject.fname_l2,");
		sb.append(" build.fid, ");
		sb.append(" build.fnumber, ");
		sb.append(" build.FName_l2,");
		sb.append(" productType.fid,");
		sb.append(" productType.fname_l2) t");
		sb.append(" left join (select  sellProject.fid sellProjectId,");
		sb.append(" sellProject.fname_l2 sellProjectName,");
		sb.append(" productType.fid productTypeId,");
		sb.append(" productType.fname_l2 productTypeName,");
		sb.append(" build.fnumber buildFnumber, ");
		sb.append(" build.FName_l2  buildFname_L2,");


		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',1)) JanQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',room.fbuildingarea,0)) JanbuildArea,  "); 
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'01',signmanage.FDealTotalAmount,0)) Janaccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',1)) FebQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',room.fbuildingarea,0)) FebbuildArea,   ");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'02',signmanage.FDealTotalAmount,0)) Febaccount,");


		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',1)) MarQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',room.fbuildingarea,0)) MarbuildArea,   ");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'03',signmanage.FDealTotalAmount,0)) Maraccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',1)) AprQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',room.fbuildingarea,0)) AprbuildArea,   ");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'04',signmanage.FDealTotalAmount,0)) Apraccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',1)) MayQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',room.fbuildingarea,0)) MaybuildArea, ");  
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'05',signmanage.FDealTotalAmount,0)) Mayaccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',1)) JunQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',room.fbuildingarea,0)) JunbuildArea,   ");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'06',signmanage.FDealTotalAmount,0)) Junaccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',1)) JulQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',room.fbuildingarea,0)) JulbuildArea,  "); 
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'07',signmanage.FDealTotalAmount,0)) Julaccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',1)) AugQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',room.fbuildingarea,0)) AugbuildArea,");   
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'08',signmanage.FDealTotalAmount,0)) Augaccount,");

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',1)) SepQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',room.fbuildingarea,0)) SepbuildArea, ");  
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'09',signmanage.FDealTotalAmount,0)) Sepaccount,");            

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',1)) OctQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',room.fbuildingarea,0)) OctbuildArea, ");  
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'10',signmanage.FDealTotalAmount,0)) Octaccount, "); 

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',1)) NovQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',room.fbuildingarea,0)) NovbuildArea,");   
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'11',signmanage.FDealTotalAmount,0)) Novaccount,");  

		sb.append(" count(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',1)) DecQty,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',room.fbuildingarea,0)) DecbuildArea,");
		sb.append(" sum(decode(to_char(signmanage.FBusAdscriptionDate,'MM'),'12',signmanage.FDealTotalAmount,0)) Decaccount"); 


		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid = room.fbuildingid");
		sb.append(" left join t_she_sellProject sellproject on sellProject.fid = build.fsellProjectid");
		sb.append(" left join T_FDC_ProductType productType on productType.fid = room.fproductTypeid");
		sb.append(" left join (select distinct froomid,FBusAdscriptionDate,FDealTotalAmount from t_she_signmanage where fbizstate in('SignApple','SignAudit','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing') ) signmanage on signmanage.froomid = room.fid ");
		sb.append(" where 1=1 ");

		sb.append(" and room.FSellState = 'Sign'");		
		sb.append(" and to_char(signmanage.FBusAdscriptionDate,'yyyy-mm-dd') >="+"'"+sf.format(fromDate)+"'");
		sb.append(" and to_char(signmanage.FBusAdscriptionDate,'yyyy-mm-dd') <="+"'"+sf.format(toDate)+"'");
		
		sb.append(" group by sellProject.fid,");
		sb.append(" sellProject.fname_l2,");
		sb.append(" build.fnumber,");
		sb.append(" build.FName_l2,");
		sb.append(" productType.fid,");
		sb.append(" productType.fname_l2) t1 ");
		sb.append(" on t.sellProjectId = t1.sellProjectId ");
		sb.append(" and t.sellProjectName = t1.sellProjectName ");
		sb.append(" and t.productTypeId = t1.productTypeId ");
		sb.append(" and t.productTypeName = t1.productTypeName");
		sb.append(" and t.buildFnumber = t1.buildFnumber");
		sb.append(" and t.buildFname_L2 = t1.buildFname_L2");

		sb.append(" order by t.sellProjectName desc,");
		sb.append(" t.productTypeName desc,");
		sb.append(" t.buildFnumber desc,");
		sb.append(" t.buildFname_L2 desc");


		System.out.println("SQL:"+sb.toString());
		return sb.toString();
	}
	/*	
	private StringBuffer getRoom(StringBuffer sb){
		sb.append(" select sellProject.fid sellProjectId,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )buildArea,");
		sb.append(" sum(case when FIsActualAreaAudited='1' then room.factualRoomArea else case when fispre='1' then room.fRoomArea else case when FIsPlan='1' then room.fplanRoomArea else room.fplanRoomArea end end end )RoomArea,");
		sb.append(" sum(room.fstandardTotalAmount)account,");
		sb.append(" case when sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )=0 then 0 else sum(room.fstandardTotalAmount)/sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end ) end price");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join t_she_sellProject sellproject on sellProject.fid=build.fsellProjectid  inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
//		sb.append(" where build.FIsGetCertificated='1' and room.fsellstate<>'Sign'");
		sb.append(" group by sellProject.fid,sellProject.fname_l2,productType.fid,productType.fname_l2,room.fid");
		return sb;
	}
	private StringBuffer getOnShowRoom(StringBuffer sb){
		sb.append(" select sellProject.fid sellProjectId,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )buildArea,");
		sb.append(" sum(case when FIsActualAreaAudited='1' then room.factualRoomArea else case when fispre='1' then room.fRoomArea else case when FIsPlan='1' then room.fplanRoomArea else room.fplanRoomArea end end end )RoomArea,");
		sb.append(" sum(room.fstandardTotalAmount)account,");
		sb.append(" case when sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )=0 then 0 else sum(room.fstandardTotalAmount)/sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end ) end price");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join t_she_sellProject sellproject on sellProject.fid=build.fsellProjectid  inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where room.fsellstate='Onshow' or room.fsellstate='Init'");
		sb.append(" group by sellProject.fid,sellProject.fname_l2,productType.fid,productType.fname_l2,room.fid");
		return sb;
	}
	private StringBuffer getBaseTransaction(StringBuffer sb,String table,String state,Date fromDate,Date toDate){
		sb.append(" select deal.fsellProjectid sellProjectId ,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end )buildArea,");
		sb.append(" sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanRoomArea when 'PreSell' then deal.froomArea else deal.fstrdActualRoomArea end )roomArea,sum(deal.fdealTotalAmount) account,case when sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end )=0 then 0 else sum(deal.fdealTotalAmount)/sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end ) end price");
		sb.append(" from "+table+" deal");
		sb.append(" left join t_she_sellProject sellproject on sellProject.fid=deal.fsellProjectid left join t_she_room room on room.fid=deal.froomid  left join t_she_building build on build.fid=room.fbuildingid inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where deal.fbizState in("+state+") and productType.fid is not null");
		if(fromDate!=null){
			sb.append(" and deal.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and deal.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		sb.append(" group by productType.fid,deal.fsellProjectid,sellProject.fname_l2,deal.froomid,productType.fname_l2");
		return sb;
	}
	 */	
}