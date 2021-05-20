package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeFactory;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class HousingRentalFacadeControllerBean extends AbstractHousingRentalFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.HousingRentalFacadeControllerBean");
    private static final String COMPANY="��˾";
//    @SuppressWarnings("unchecked")
	protected Map _getHousingRentalList(Context ctx, Map param)throws BOSException
    {
    	//by huanghefh
    	Calendar compDate = Calendar.getInstance();
    	Calendar monthsart = Calendar.getInstance();
		Calendar monthend = Calendar.getInstance();
    	compDate.setTime(new Date());
    	monthend.setTime(new Date());
    	monthsart.setTime(new Date());
    	Map result = new HashMap();
    	BigDecimal dayAmout=new BigDecimal(0);//����������
    	BigDecimal dayArea=new BigDecimal(0); //���������������
    	BigDecimal avgDayAmoutRote=new BigDecimal(0); //����
    	BigDecimal avgDayAreaRote=new BigDecimal(0); //�����
    	BigDecimal avgMonthArea=new BigDecimal(0); //����� 
    	BigDecimal avgmonthAmout=new BigDecimal(0); //����
    	
    	Calendar monthFirstDay = Calendar.getInstance(); //���µ�һ��
		Calendar MonthendDay = Calendar.getInstance(); //�������һ��
		monthFirstDay.setTime(new Date());
		monthFirstDay.set(Calendar.DAY_OF_MONTH, 1);
		monthFirstDay.set(monthFirstDay.get(Calendar.YEAR), monthFirstDay.get(Calendar.MONTH), monthFirstDay.get(Calendar.DATE),00, 00, 00);
		MonthendDay.setTime(new Date()); 
		MonthendDay.set(Calendar.DAY_OF_MONTH, MonthendDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		MonthendDay.set(MonthendDay.get(Calendar.YEAR), MonthendDay.get(Calendar.MONTH), MonthendDay.get(Calendar.DATE),23,59, 59);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String firstDay = df.format(monthFirstDay.getTime()); 
		String endDay = df.format(MonthendDay.getTime()); 

    	// ��ѯ���
		StringBuffer sql = new StringBuffer();
		sql.append(" select build.fid as id,bu.fid as org,sp.fname_l2 as projectName, ");
		sql.append(" sub.fname_l2 as subarea,build.fname_l2 as buildName  from ");
		sql.append(" t_she_building as build ");
		sql.append(" left join t_she_sellProject as sp ");
		sql.append(" on build.fsellprojectid=sp.fid ");
		sql.append(" left join T_SHE_Subarea as sub ");
		sql.append(" on sp.fid = sub.FSellProjectID " );
		sql.append(" left join T_ORG_BaseUnit as bu ");
		sql.append(" on bu.FID = sp.FOrgUnitID ");
		sql.append(" where 1=1 ");
		
		if(param!=null){
			// ��֯
			Set orgUnit = (Set) param.get("orgUnit");
			if (orgUnit != null && orgUnit.size() > 0) {
				sql.append(" and sp.FID in "+ FMHelper.setTran2String(orgUnit));
			}
			// ��Ŀ
			String project = (String) param.get("project");
			if (project != null) {
				sql.append(" and sp.FID = '" + project + "' ");
			}
		}
		//����Ĭ�ϰ���˾����Ŀ��������¥������
		sql.append(" order by bu.fid, sp.fname_l2, sub.fname_l2 ,build.fname_l2 ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		List resultentry = new ArrayList();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String org = getCompanyByOrg(ctx,rs.getString("org"));
				String projectName = rs.getString("projectName");
				String subarea=rs.getString("subarea");
				String buildName=rs.getString("buildName");
				BigDecimal totalArea=new BigDecimal(0); 
				BigDecimal totalAmount=new BigDecimal(0); 
				BigDecimal rentArea=new BigDecimal(0); 
				BigDecimal rentedArea=new BigDecimal(0); 
				BigDecimal rentRate=new BigDecimal(0); 
				
				BigDecimal rentAmount=new BigDecimal(0); 
				BigDecimal rentedAmount=new BigDecimal(0); 
				BigDecimal rentAmountRate=new BigDecimal(0); 
				//('Audited','Executing','ContinueTenancying') ����ContinueTenancying��������˹�����ʱ������ڿ�������������� ���ϼ�����@by huangehfh
				StringBuffer sql1 =new StringBuffer();
				sql1.append("select count(T_SHE_Room.fid) as totalAmount,sum(fbuildingArea) as totalArea from T_SHE_Room ");
				sql1.append("where FBuildingID = '"+id+"'");
				StringBuffer sql2 =new StringBuffer();
				sql2.append("select count(T_SHE_Room.fid) as rentAmount, isnull(sum(ftenancyArea),0) as rentArea from T_SHE_Room ");
				sql2.append("where fisForTen=1 and FBuildingID = '"+id+"' and ftenancyState <>'UNTenancy'");
				/**
				 * sql3 �Ǹ������޺�ͬ״̬��������״̬ �жϸ÷����Ƿ����⣬����״̬�ܶ࣬������ʱ״̬û�м�ʱ����
				 * �ʣ����������ѯ���ݷ�ʽ������sql4
				 * */
				StringBuffer sql3  =new StringBuffer();
				sql3.append(" select count(tt.fid) as rentedAmount ,isnull(sum(rentedArea),0) as rentedArea from ( " );
				sql3.append(" select count(tre.fid) as rentedAmount ,isnull(sum(room.FTenancyArea),0) as rentedArea ,room.fid  as fid from T_SHE_Room room" );
				sql3.append(" left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid" );
				sql3.append(" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID" );
				sql3.append(" where room.fisForTen=1 and FBuildingID = '"+id+"' ");
				sql3.append(" and tb.ftenancystate in('Audited','Executing','ContinueTenancying')");
				sql3.append(" and tre.FFlagAtTerm not in ('QuitAtTerm','QuitNotAtTerm','ReletAtTerm')");
				sql3.append(" group by room.fid" );
				sql3.append(" ) tt");
				/**
				 * ��ȡ������������ȡ�������޺�ͬ��ʼ���ڣ����������ڣ��ٸ��ݵ�ǰ���ڣ��жϵ�ǰ�·ݷ����Ƿ�����
				 * */
				StringBuffer sql4  =new StringBuffer();
				sql4.append("/*dialect*/");
				sql4.append(" select tt.fbuildid, count(1) as rentedAmount ,isnull(sum(tt.FTenancyArea),0) as rentedArea");
				sql4.append(" from (");
				sql4.append(" select bu.fid as fbuildid, min(tb.fstartDate) as FStartDate,max(tb.fendDate) as fendDate,");
				sql4.append(" max(ro.FTenancyArea) as FTenancyArea from T_TEN_TenancyBill tb ");
				sql4.append(" left join T_TEN_TenancyRoomEntry tr on tr.FTenancyID=tb.fid");
				sql4.append(" left join T_SHE_Room ro on ro.fid=tr.froomid");
				sql4.append(" left join T_SHE_Building bu on bu.fid=ro.fbuildingid");
				sql4.append(" where bu.fid='"+id+"'");
				sql4.append(" and ro.fisForTen='1'");
				sql4.append(" and  ((tb.fstartDate<='"+firstDay+"'and  tb.fendDate>='"+firstDay+"')");
				sql4.append(" 		or (tb.fstartDate>='"+firstDay+"' and  tb.fendDate<='"+endDay+"')");
				sql4.append(" 		or (tb.fstartDate<='"+endDay+"' and tb.fendDate>='"+endDay+"')");
				sql4.append(" 		or (tb.fstartDate<='"+firstDay+"' and tb.fendDate>='"+endDay+"'))");
				sql4.append(" and tb.ftenancystate not in('Saved','Submitted','Auditing','BlankOut')") ;
				sql4.append(" and tr.FFlagAtTerm not in ('QuitAtTerm','QuitNotAtTerm','ReletAtTerm') ");
				sql4.append(" group by ro.fid,bu.fid");
				sql4.append(" ) as tt");
				sql4.append(" group by  tt.fbuildid");
				
				IRowSet rs1  = DbUtil.executeQuery(ctx, sql1.toString());
				IRowSet rs2  = DbUtil.executeQuery(ctx, sql2.toString());
				IRowSet rs3  = DbUtil.executeQuery(ctx, sql4.toString());
				while (rs1.next()) {
					totalArea=rs1.getBigDecimal("totalArea");//�����=���з������֮�� 
					totalAmount = rs1.getBigDecimal("totalAmount");//������=���з�������֮�� 
				}
				while (rs2.next()) {
					rentArea=rs2.getBigDecimal("rentArea"); //������������ = ���⣬���������⣬���⣬���⣬����Ԥ�� ����Ŀ�������ܺ�
					rentAmount=rs2.getBigDecimal("rentAmount"); //����������� = ���⣬���������⣬���⣬���⣬����Ԥ�� ����Ŀ��������ܺ�
				}
				while (rs3.next()) {
					rentedArea=rs3.getBigDecimal("rentedArea");
					rentedAmount=rs3.getBigDecimal("rentedAmount");
				}
			
				BigDecimal unRentedArea=rentArea.subtract(rentedArea);
				BigDecimal unRentedAmount=rentAmount.subtract(rentedAmount);
				// ������ @by huanghefh
				if(rentArea.compareTo(new BigDecimal(0))!=0)
					rentRate=rentedArea.multiply(new BigDecimal(100)).divide(rentArea, 2, BigDecimal.ROUND_HALF_UP);
				if(rentAmount.compareTo(new BigDecimal(0))!=0)
					rentAmountRate=rentedAmount.multiply(new BigDecimal(100)).divide(rentAmount, 2, BigDecimal.ROUND_HALF_UP);
				
				//@by huanghefh ȡ������ FFlagAtTerm  ���ԣ� �������� orcl
				StringBuffer MRote  =new StringBuffer();
			boolean isorcl=false;
//			MRote=null;
//			MRote.append("/*dialect*/  ")
//			.append(" select * from   ")
//				.append(getMonthSql("DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1)", "one", 28))
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,-1)", "two", 28))
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1)", "three", 31))
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1)", "foure", 30).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1)", "five", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1)", "six", 30).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1)", "seven", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1)", "eight", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1)", "nine", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1)", "ten", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1)", "eleven", 31).toString())
//				.append(" left join ")
//				.append(getMonthSql("DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)", "DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1)", "twelve", 31).toString())
//				.append(" where one.id='"+id+"'");
//				
//				
				if(true){
					MRote.append("/*dialect*/  ")
				.append(" select onerentedday,tworentedday,threerentedday,fourerentedday,fiverentedday,sixrentedday,sevenrentedday,eightrentedday,ninerentedday,tenrentedday,elevenrentedday,twelverentedday,onerentedArea,onerentedAmount,tworentedArea,tworentedAmount,threerentedArea,threerentedAmount,threerentedArea,fourerentedAmount,fourerentedArea,fourerentedAmount,fiverentedArea,fiverentedAmount,fiverentedArea,sixrentedAmount,sixrentedArea, ")
				.append(" sevenrentedAmount,sevenrentedArea,eightrentedAmount,eightrentedArea,ninerentedAmount,ninerentedArea,tenrentedAmount,tenrentedArea, elevenrentedAmount,elevenrentedArea,twelverentedAmount,twelverentedArea from ( ")
				.append(" select id ,sum(amount) onerentedAmount,sum(DayAmount) as onerentedday  ,sum(DayArea)as onerentedArea ")
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ")
				.append(" 	from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ")
				.append(" 	"+this.getMonthConditionSql(1,31)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ")
				.append(" )bulid ")
				.append(" group by  id)one ")
				.append(" left join( ")
				.append(" select id ,sum(amount) tworentedAmount,sum(DayAmount) as tworentedday  ,sum(DayArea)as tworentedArea ")
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ")
				.append(" 	from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ")
				.append(" 	"+this.getMonthConditionSql(2, 28)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ")
				.append(" )bulid ")
				.append(" group by  id)two  on two.id=one.id ")
				.append(" left join( ")
				.append(" select id ,sum(amount) threerentedAmount,sum(DayAmount) as threerentedday  ,sum(DayArea)as threerentedArea ")
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ")
				.append(" 		from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ")
				.append(" 	"+this.getMonthConditionSql(3, 31)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)three  on three.id=one.id ") 
				.append(" left join( ")
				.append(" select id ,sum(amount) fourerentedAmount,sum(DayAmount) as fourerentedday  ,sum(DayArea)as fourerentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(4, 30)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)foure  on foure.id=one.id ") 
				.append(" left join( ") 
				.append(" select id ,sum(amount) fiverentedAmount,sum(DayAmount) as fiverentedday  ,sum(DayArea)as fiverentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(5, 31)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)five  on five.id=one.id ") 
				.append(" left join( ") 
				.append(" select id ,sum(amount) sixrentedAmount,sum(DayAmount) as sixrentedday  ,sum(DayArea)as sixrentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(6, 30)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)six  on six.id=one.id ")  
				.append(" left join( ") 
				.append(" select id ,sum(amount) sevenrentedAmount,sum(DayAmount) as sevenrentedday  ,sum(DayArea)as sevenrentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(7, 31)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 	group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)seven  on seven.id=one.id ")  
				.append(" left join( ") 
				.append(" select id ,sum(amount) eightrentedAmount,sum(DayAmount) as eightrentedday  ,sum(DayArea)as eightrentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 		from ( select buildID AS ID, roomID,count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(8, 31)+"   ") 
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)eight  on eight.id=one.id ")  
				.append(" left join( ") 
				.append(" select id ,sum(amount) ninerentedAmount,sum(DayAmount) as ninerentedday  ,sum(DayArea)as ninerentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(9, 30)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" 	)bulid ") 
				.append(" 	group by  id)nine  on nine.id=one.id ")  
				.append(" 	left join( ") 
				.append(" 	select id ,sum(amount) tenrentedAmount,sum(DayAmount) as tenrentedday  ,sum(DayArea)as tenrentedArea ") 
				.append(" 	from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 	from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(10, 31)+"   ")
				.append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)ten  on ten.id=one.id ")  
				.append(" left join( ") 
				.append(" select id ,sum(amount) elevenrentedAmount,sum(DayAmount) as elevenrentedday  ,sum(DayArea)as elevenrentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 		from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(11, 30)+"   ") 
				  .append(" 			from (")  
				.append(" "+getBuildRoomSql()) 
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)eleven  on eleven.id=one.id ")  
				.append(" left join( ") 
				.append(" select id ,sum(amount) twelverentedAmount,sum(DayAmount) as twelverentedday  ,sum(DayArea)as twelverentedArea ") 
				.append(" from(select id,rentedAmount  as amount, totalday Dayamount, rentedArea*totalday as DayArea ") 
				.append(" 		from ( select buildID AS ID,roomID, count(1) as rentedAmount ,isnull(sum(FTenancyArea),0) as rentedArea ") 
				.append(" 	"+this.getMonthConditionSql(12, 31)+"   ")
				  .append(" 			from  (")  
				.append(" "+getBuildRoomSql())  
				.append(" 		) tb ")
				.append(" 		group by buildID,tb.FStartDate,tb.FEndDate,tb.roomID ) ontb ") 
				.append(" )bulid ") 
				.append(" group by  id)twelve  on twelve.id=one.id ")  
				.append(" where one.id='"+id+"'");
				
}
				IRowSet rsMR  = DbUtil.executeQuery(ctx, MRote.toString());
				Map avgRate= new HashMap();
				if(rentArea!=null&&rentAmount!=null&&rentAmount.compareTo(new BigDecimal(0))!=0&&rentArea.compareTo(new BigDecimal(0))!=0)
				while (rsMR.next()) {
					String str=null;
					/***3.*��100 ��ȥ�����������
					 * �����㷨�� ��������*���������������*100/(��������*���������������
					 * */
					// 1 3 5 7 8 10 12 31��rentedArea
					
					//1�� 
					BigDecimal totalAmountday=(new BigDecimal(31).multiply(rentAmount).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					BigDecimal totalAreaday=(new BigDecimal(31).multiply(rentArea).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					str="one";
					avgRate.put("1monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("1monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//3��
					str="three";
					avgRate.put("3monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("3monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));
					//5��
					str="five";
					avgRate.put("5monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("5monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//7��
					str="seven";
					avgRate.put("7monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("7monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//8��
					str="eight";
					avgRate.put("8monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("8monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//10��
					str="ten";
					avgRate.put("10monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("10monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//12��
					str="twelve";
					avgRate.put("12monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("12monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//4 6 9 11 30��
					totalAmountday=(new BigDecimal(30).multiply(rentAmount).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					totalAreaday=(new BigDecimal(30).multiply(rentArea).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					//4��
					str="foure";
					avgRate.put("4monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("4monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//6��
					str="six";
					avgRate.put("6monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("6monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//9��
					str="nine";
					avgRate.put("9monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("9monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					//11��
					str="eleven";
					avgRate.put("11monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("11monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					
					//2��28�죨�������꣩
					//2��
					totalAmountday=(new BigDecimal(28).multiply(rentAmount).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					totalAreaday=(new BigDecimal(28).multiply(rentArea).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP));
					str="two";
					avgRate.put("2monthArea", rsMR.getBigDecimal(str+"rentedArea").divide(totalAreaday,2, BigDecimal.ROUND_HALF_UP));
					avgRate.put("2monthAmount", rsMR.getBigDecimal(str+"rentedday").divide(totalAmountday,2, BigDecimal.ROUND_HALF_UP));			
					
				}
				
				Map housingRental = new HashMap();
				housingRental.put("id", id);
				housingRental.put("org", org);
				housingRental.put("projectName", projectName);
				housingRental.put("subarea", subarea);
				housingRental.put("buildName", buildName);
				housingRental.put("totalArea", totalArea);
				
				housingRental.put("rentArea", rentArea);
				housingRental.put("rentedArea", rentedArea);
				housingRental.put("unRentedArea", unRentedArea);
				housingRental.put("rentRate", rentRate);
				
				housingRental.put("rentAmount", rentAmount);
				housingRental.put("rentedAmount", rentedAmount);
				housingRental.put("unRentedAmount", unRentedAmount);
				housingRental.put("rentAmountRate", rentAmountRate);
				
				housingRental.put("mrote", avgRate);
				resultentry.add(housingRental);
		}
	 } catch (SQLException e) {
			e.printStackTrace();
	 }
	result.put("build", resultentry);
	result.put("timedata", compDate.getTime());
    return result;
   }
    public StringBuffer getMonthSql(String startDate,String endDate,String month,int maxday){
    	StringBuffer sql=new StringBuffer();
    	sql.append("  (select FBuildingID as id, isnull(count(tre.fid),0) as sixrentedAmount ,isnull(sum(room.FTenancyArea),0) as sixrentedArea,isnull(sum(case  ")
		.append(" 				        when tb.FStartDate<="+startDate+"  and tb.FEndDate>="+endDate+" ")
		.append(" 								then "+maxday)
		.append(" 						when tb.FStartDate>"+startDate+"  and tb.FEndDate>="+endDate+" and tb.FStartDate<="+endDate+"  ")
		.append(" 								then "+(int)(1+maxday)+"-Day(tb.FStartDate) ")
		.append(" 				        when tb.FStartDate<="+startDate+"	  and tb.FEndDate<"+endDate+" ")
		.append(" 					            then Day(tb.FEndDate) ")
		.append(" 					    when tb.FStartDate>"+startDate+"  and tb.FEndDate<"+endDate+" ")
		.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
		.append(" 					      end),0)as ")
		.append(month)
		 .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
		.append(" 					    where room.fisForTen=1 and tb.ftenancystate not in('Saved','Submitted','Auditing','BlankOut') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
		.append(" 						    group by FBuildingID ) ")
    	.append(month +" on "+month+".id=one.id");
    	return sql;
    }
    private String getCompanyByOrg(Context ctx,String orgid) throws  BOSException{
    	FullOrgUnitInfo com=null;
		try {
			com = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(orgid));
			if(com.isIsAdminOrgUnit()){
				AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(orgid));
				OrgUnitLayerTypeInfo lay=OrgUnitLayerTypeFactory.getLocalInstance(ctx).getOrgUnitLayerTypeInfo(new ObjectUuidPK(admin.getUnitLayerType().getId()));
				if(COMPANY.equals(lay.getName())){
					return com.getName();
				}else{
					return getCompanyByOrg(ctx,com.getParent().getId().toString());
				}
				
			}else{
				return getCompanyByOrg(ctx,com.getParent().getId().toString());
			}
		}
		catch (EASBizException e) {
			e.printStackTrace();
		}
		return null;
    }
	protected boolean _ReSetDayPrice(Context ctx, Map arg1) throws BOSException {
		StringBuffer reSetDayPrice = new StringBuffer();
		reSetDayPrice.append(" /*dialect*/")
//		.append(" /*��׼�յ���*/  ")
//		.append(" ----��  ")
		.append(" update T_TEN_TenancyRoomEntry  ")
		.append(" set fdayprice=( fstandardroomrentprice/365)  ")
		.append(" where  fstandardrenttype='1RentByYear'  ")
//		.append(" ----��  ")
		.append(" update T_TEN_TenancyRoomEntry  ")
		.append(" set fdayprice=(4*fstandardroomrentprice/365)  ")
		.append(" where  fstandardrenttype='2RentByQuarter'  ")
//		.append("  ----��  ")
		.append(" update T_TEN_TenancyRoomEntry  ")
		.append(" set fdayprice=( 12*fstandardroomrentprice/365)  ")
		.append(" where  fstandardrenttype='3RentByMonth'  ")
//		.append(" ----��  ")
		.append(" update T_TEN_TenancyRoomEntry  ")
		.append(" set fdayprice=( fstandardroomrentprice/7)  ")
		.append(" where  fstandardrenttype='4RentByWeek'  ")
//		.append(" ----��  ")
		.append(" update T_TEN_TenancyRoomEntry  ")
		.append(" set fdayprice=fstandardroomrentprice  ")
		.append(" where  fstandardrenttype='5RentByDay'  ")
		
		
//		.append(" /*ʵ���յ���*/  ")
		
//		.append(" ----�� FRentType ������  ")
		.append(" select * from T_TEN_TenancyRoomEntry  ")
		.append(" update  T_TEN_TenancyRoomEntry set T_TEN_TenancyRoomEntry.cfactdayprice=(mae.fpriceAmount/365)   ")
		.append("  from T_TEN_DealAmountEntry  mae,T_TEN_TenancyRoomEntry tre, T_SHE_MoneyDefine md  ")
		.append(" where mae.FTenancyRoomID =tre.fid  ")
		.append(" and md.FID = mae.FMoneyDefineID    ")
		.append(" and md.fname_l2 ='���'   ")
		.append(" and tre.FRentType='1RentByYear'  ")
		.append(" and getDate() between mae.FSTARTdATE AND mae.FEndDate  ")
//		.append(" ----��  ")
		.append(" select * from T_TEN_TenancyRoomEntry  ")
		.append(" update  T_TEN_TenancyRoomEntry set T_TEN_TenancyRoomEntry.cfactdayprice=(4*mae.fpriceAmount/365)  ")
		.append("  from T_TEN_DealAmountEntry  mae,T_TEN_TenancyRoomEntry tre, T_SHE_MoneyDefine md  ")
		.append(" where mae.FTenancyRoomID =tre.fid  ")
		.append(" and md.FID = mae.FMoneyDefineID    ")
		.append(" and md.fname_l2 ='���'   ")
		.append(" and tre.FRentType='2RentByQuarter'  ")
		.append(" and getDate() between mae.FSTARTdATE AND mae.FEndDate  ")
//		.append(" ----��  ")
		.append(" select * from T_TEN_TenancyRoomEntry  ")
		.append(" update  T_TEN_TenancyRoomEntry set T_TEN_TenancyRoomEntry.cfactdayprice=(12*mae.fpriceAmount/365)  ")
		.append(" from T_TEN_DealAmountEntry  mae,T_TEN_TenancyRoomEntry tre, T_SHE_MoneyDefine md  ")
		.append(" where mae.FTenancyRoomID =tre.fid  ")
		.append(" and md.FID = mae.FMoneyDefineID    ")
		.append(" and md.fname_l2 ='���'   ")
		.append(" and tre.FRentType='3RentByMonth'  ")
		.append(" and getDate() between mae.FSTARTdATE AND mae.FEndDate  ")
		
//		.append(" ----��  ")
		.append(" select * from T_TEN_TenancyRoomEntry  ")
		.append(" update  T_TEN_TenancyRoomEntry set T_TEN_TenancyRoomEntry.cfactdayprice=(mae.fpriceAmount/7)  ")
		.append(" from T_TEN_DealAmountEntry  mae,T_TEN_TenancyRoomEntry tre, T_SHE_MoneyDefine md  ")
		.append(" where mae.FTenancyRoomID =tre.fid  ")
		.append(" and md.FID = mae.FMoneyDefineID    ")
		.append(" and md.fname_l2 ='���'   ")
		.append(" and tre.FRentType='4RentByWeek'  ")
		.append(" and getDate() between mae.FSTARTdATE AND mae.FEndDate  ")
		
//		.append(" ----��  ")
		.append(" select * from T_TEN_TenancyRoomEntry  ")
		.append(" update  T_TEN_TenancyRoomEntry set T_TEN_TenancyRoomEntry.cfactdayprice=(mae.fpriceAmount)  ")
		.append(" from T_TEN_DealAmountEntry  mae,T_TEN_TenancyRoomEntry tre, T_SHE_MoneyDefine md  ")
		.append(" where mae.FTenancyRoomID =tre.fid ")
		.append(" and md.FID = mae.FMoneyDefineID  ")
		.append(" and md.fname_l2 ='���' ")
		.append(" and tre.FRentType='5RentByDay'")
		.append(" and getDate() between mae.FSTARTdATE AND mae.FEndDate ");
		DbUtil.execute(ctx, reSetDayPrice.toString());
		return true;

	}
	/**
	 * ����ÿ���µĳ��������ж�һ�����ʷ�װ���������
	 * ���ܣ���ȡ���޵���ʼ���ڣ��������ڣ��ٸ��ݵ�ǰ�·ݵĵ�һ������һ�죬�ж��ڱ��·����������
	 * */
	public String getMonthConditionSql(int month,int maxDay){
		month = month - 1;
		int days = maxDay+1;
		int nextMonth = month +1;
		StringBuffer sb = new StringBuffer();
		sb.append(" ,case when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1) then "+maxDay+"");
		sb.append(" when tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1)");
		sb.append(" and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1) then "+days+"-Day(tb.FStartDate) ");
		sb.append(" when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" and tb.FEndDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1)");
		sb.append(" and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" then Day(tb.FEndDate) ");
		sb.append(" when tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" and tb.FEndDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1)");
		sb.append(" then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate)  ");
		sb.append(" when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+nextMonth+",-1) ");
		sb.append(" then 0");
		sb.append(" when tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+"+month+",0)");
		sb.append(" then 0");
		sb.append(" end as   totalday ");
		return sb.toString();
	}
	/**
	 * ����ÿ���µ�ȡ���޺�ͬ������Ϣһ�����ʷ�װ���������
	 * ���ܣ�ȡ���޺�ͬ������Ϣ���磺�����������ͬ��ʼ���ڣ���������
	 * */
	public String getBuildRoomSql(){
		StringBuffer sb = new StringBuffer();
		sb.append(" 	select room.FTenancyArea,room.fid  as roomID ,min(tb.FStartDate) as FStartDate ,max(tb.FEndDate) as FEndDate ,FBuildingID as buildID ")
		.append(" 		from T_SHE_Room room left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid ") 
		.append(" 		left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID  ")
		.append(" 		where room.fisForTen=1   ")
		.append(" 		and tb.ftenancystate not in('Saved','Submitted','Auditing','BlankOut') ") 
		.append(" 		and tre.FFlagAtTerm not in ('QuitAtTerm','QuitNotAtTerm','ReletAtTerm') ")
		.append("		group by room.FTenancyArea,room.fid,FBuildingID  ");
		return sb.toString();
	}
	void test(){
		boolean isorcl= false;
		if(isorcl){
//			MRote.append("/*dialect*/ select * from (")
//			.append("select FBuildingID as id, count(tre.fid) as onerentedAmount ,sum(room.FTenancyArea) as onerentedArea,NVL(sum(case /*һ�� ע�� cf �ĳ� f**/")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))") 
//			.append("            then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))),'dd'))")
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))")  
//			.append("           then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))))- trunc(FStartDate)") 
//			.append("       when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))")
//			.append("             then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))") 
//			.append("       when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM '))  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')))")
//			.append("            then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("       end),0)as one")
//			.append("    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID") 
//			.append("    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("	    ) one")
//			.append("	    left join (")
//			.append("	     select FBuildingID as id, count(tre.fid) as tworentedAmount ,sum(room.FTenancyArea) as tworentedArea,sum(case /*2�� ע�� cf �ĳ� f**/")
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))") 
//			.append("              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)),'dd'))")
//			.append("        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))")  
//			.append("              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)))- trunc(FStartDate)") 
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)")
//			.append("              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))") 
//			.append("        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+1))")
//			.append("          then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("       end)as two")
//			.append("    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID") 
//			.append("    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("        )two on one.id=two.id") 
//			.append("	    left join (")
//			.append("	     select FBuildingID as id, count(tre.fid) as threerentedAmount ,sum(room.FTenancyArea) as threerentedArea,sum(case /*3�� ע�� cf �ĳ� f**/")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))") 
//			.append("	              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)),'dd'))")
//			.append("        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))")  
//			.append("              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)))- trunc(FStartDate)") 
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)")
//			.append("              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))") 
//			.append("       when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+2))")
//			.append("             then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("      end)as three")
//			 .append("   from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID") 
//			.append("   where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("  group by FBuildingID")
//			.append("       )three on one.id=three.id") 
//			.append("   left join (")
//			.append("    select FBuildingID as id, count(tre.fid) as fourerentedAmount ,sum(room.FTenancyArea) as fourerentedArea,sum(case /*���� ע�� cf �ĳ� f**/")
//			.append("       when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))") 
//			.append("             then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)),'dd'))")
//			.append("       when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))")  
//			.append("             then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)))- trunc(FStartDate) ")
//			.append("      when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)")
//			.append("          then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))") 
//			.append("      when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))")
//			.append("            then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("     end)as foure")
//			.append("   from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID") 
//			.append("   where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("   group by FBuildingID")
//			.append("    )foure on foure.id=one.id") 
//			.append("     left join (")
//			.append("   select FBuildingID as id, count(tre.fid) as fiverentedAmount ,sum(room.FTenancyArea) as fiverentedArea,sum(case /*5�� ע�� cf �ĳ� f**/")
//			.append("    when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4))") 
//			.append("         then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)),'dd'))")
//			.append("   when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4))")  
//			.append("         then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)))- trunc(FStartDate) ")
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)")
//			.append("              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)) ")
//			.append("        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+4))")
//			.append("              then trunc(FEndDate)- trunc(FStartDate) ")
//			.append("	       end)as five")
//			.append("	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("	      )five on five.id=one.id ")
//			 
//			.append("	  left join (")
//			.append("	     select FBuildingID as id, count(tre.fid) as sixrentedAmount ,sum(room.FTenancyArea) as sixrentedArea,sum(case /*6 �� ע�� cf �ĳ� f**/")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)) ")
//			.append("	              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)),'dd'))")
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)) ") 
//			.append("	              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)))- trunc(FStartDate) ")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)")
//			.append("	              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)) ")
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+5))")
//			.append("              then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("	       end)as six")
//			.append("	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID") 
//			.append("	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("	      )six on six.id=one.id") 
//			 
//			.append("  	  left join (")
//			.append("  	     select FBuildingID as id, count(tre.fid) as sevenrentedAmount ,sum(room.FTenancyArea) as sevenrentedArea,sum(case /*7�� ע�� cf �ĳ� f**/")
//			.append("  	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6))") 
//			.append("  	              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6)),'dd'))")
//			.append("  	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6))")  
//			.append("  	              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6)))- trunc(FStartDate)") 
//			.append("  	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+6))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)")
//			.append("  	              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3))v") 
//			.append(" 	              then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("  	       end)as seven")
//			.append(" 	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("   	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("   	    group by FBuildingID")
//			.append("   	      )seven on seven.id=one.id") 
//			 
//			.append("   	  left join (")
//			.append("   	     select FBuildingID as id, count(tre.fid) as eightrentedAmount ,sum(room.FTenancyArea) as eightrentedArea,sum(case /*8�� ע�� cf �ĳ� f**/")
//			.append(" 	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7))") 
//			.append("  	              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)),'dd'))")
//			.append("  	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7))")  
//			.append(" 	              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)))- trunc(FStartDate) ")
//			.append(" 	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)")
//			.append(" 			then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)) ")
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+7))")
//			.append("           then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("	       end)as eight")
//			.append(" 	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append(" 	    group by FBuildingID")
//			.append(" 	      )eight on eight.id=one.id") 
//			 
//			.append(" 	  left join (")
//			.append(" 	     select FBuildingID as id, count(tre.fid) as ninerentedAmount ,sum(room.FTenancyArea) as ninerentedArea,sum(case /*9�� ע�� cf �ĳ� f**/")
//			.append(" 	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))") 
//			.append(" 	              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)),'dd'))")
//			.append(" 	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))")  
//			.append(" 	              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)))- trunc(FStartDate) ")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)")
//			.append(" 	              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))") 
//			.append(" 	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+8))")
//			.append("	              then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("	       end)as nine")
//			.append("	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("	      )nine on nine.id=one.id") 
//			 
//			.append("	  left join (")
//			.append("	     select FBuildingID as id, count(tre.fid) as tenrentedAmount ,sum(room.FTenancyArea) as tenrentedArea,sum(case /*10�� ע�� cf �ĳ� f**/")
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))") 
//			.append("              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)),'dd'))")
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))")  
//			.append("	              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)))- trunc(FStartDate)") 
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)")
//			.append("	              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))") 
//			.append("	        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+9))")
//			.append("	              then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("	       end)as ten")
//			.append("	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("	    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("	    group by FBuildingID")
//			.append("	      )ten on ten.id=one.id") 
//			.append("	  left join (")
//			.append("	     select FBuildingID as id, count(tre.fid) as elevenrentedAmount ,sum(room.FTenancyArea) as elevenrentedArea,sum(case /*11�� ע�� cf �ĳ� f**/")
//			.append("	        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10))") 
//			.append("              then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)),'dd'))")
//			.append("        when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10))")  
//			.append("              then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)))- trunc(FStartDate) ")
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)")
//			.append("              then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+3)) ")
//			.append("       when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+10))")
//			.append("	              then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("	       end)as eleven")
//			.append("	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("   group by FBuildingID")
//			.append("      )eleven on eleven.id=one.id ")
//			.append("  left join (")
//			.append("     select FBuildingID as id, count(tre.fid) as twelverentedAmount ,sum(room.FTenancyArea) as twelverentedArea,sum(case /*12�� ע�� cf �ĳ� f**/")
//			.append("        when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11))") 
//			.append("             then to_number(to_char(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)),'dd'))")
//			.append("       when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)  and FEndDate>=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11))and FStartDate<=last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11))")  
//			.append("            then trunc(last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)))- trunc(FStartDate) ")
//			.append("      when FStartDate<=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11))and FStartDate>=add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)")
//			.append("            then trunc(FEndDate)- trunc(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)) ")
//			.append("      when FStartDate>add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11)  and FEndDate<last_day(add_months(last_day(sysdate)+1,-to_char(sysdate, 'MM ')+11))")
//			.append("       then trunc(FEndDate)- trunc(FStartDate)") 
//			.append("       end)as twelve")
//			.append("    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append("    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm'")
//			.append("    group by FBuildingID")
//			.append("  )twelve on twelve.id=one.id ");
		}else{//server 2005
//			MRote.append("/*dialect*/  ")
//			.append(" select * from ( ")
//			.append(" select FBuildingID as id, isnull(count(tre.fid),0) as onerentedAmount   ,isnull(sum(case /*1��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0)  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1) ")
//			.append(" 								then 31 ")
//			.append(" 						when tb.FStartDate>DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0)  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1)and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1) ")
//			.append(" 								then 32-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1)and tb.FStartDate>=DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(yy,  DATEDIFF(yy,0,getdate()),  0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as one ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as onerentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) one  ")
//			.append(" 	left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as tworentedAmount  ,isnull(sum(case /*2��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,-1) ")
//			.append(" 								then 28 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1)and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1) ")
//			.append(" 								then 29-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1)and tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0) ")	
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+1,-1) ")
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as two ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as tworentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 				    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) two on two.id=one.id ")
//			.append(" 	left join  ")
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as threerentedAmount ,isnull(sum(case /*3��**/ ")
//			.append(" 				        when tb.FStartDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1) ")	
//			.append(" 								then 31 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1)	and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1) ")	
//			.append(" 								then 32-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1)	and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+2,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,-1) ")
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as three ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as threerentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) three on three.id=one.id ")
//			.append(" 		left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as fourerentedAmount   ,isnull(sum(case /*4��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1) ")	
//			.append(" 								then 30 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1)	and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1) ")
//			.append(" 								then 31-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1)	and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+3,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,-1) ")	
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as foure ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as fourerentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) foure on foure.id=one.id ")
//			.append(" 	left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as fiverentedAmount   ,isnull(sum(case /*5��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1) ")	
//			.append(" 								then 31 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1)	and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1) ")	
//			.append(" 								then 32-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1)	and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+4,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,-1) ")	
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as five ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as fiverentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) five on five.id=one.id ")
//			.append(" 	left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as sixrentedAmount , isnull(sum(case /*6��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0)  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1) ")
//			.append(" 								then 30 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0)  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1) ")
//			.append(" 								then 31-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1)and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+5,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,-1) ")
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as six ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as sixrentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) six on six.id=one.id ")
//			.append(" 	left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as sevenrentedAmount  ,isnull(sum(case /*7��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0)	and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1) ")
//			.append(" 								then 31 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0)	and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1)and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1) ")
//			.append(" 								then 32-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0)	and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0) ")	
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+6,0)	and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,-1) ")
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as seven ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as sevenrentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					     where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) seven on seven.id=one.id ")
//			.append(" 	left join  ")
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as eightrentedAmount  ,isnull(sum(case /*8��**/ ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1) ")	
//			.append(" 								then 31 ")
//			.append(" 						when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0)  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1)	and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1) ")	
//			.append(" 								then 32-Day(tb.FStartDate) ")
//			.append(" 				        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1)	and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0) ")
//			.append(" 					            then Day(tb.FEndDate) ")
//			.append(" 					    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+7,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,-1) ")	
//			.append(" 					            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 					      end),0)as eight ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as eightrentedArea ")
//			  .append(" 					    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 					    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 						    group by FBuildingID ")
//			.append(" 	) eight on eight.id=one.id ")
//			.append(" 	left join ") 
//			.append(" 		(select FBuildingID as id, isnull(count(tre.fid),0) as ninerentedAmount  ,isnull(sum(case /*9��**/ ")
//			.append(" 		        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1) ")	
//			.append(" 						then 30 ")
//			.append(" 			when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0)	  and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1)	and tb.FStartDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1) ")	
//			.append(" 					then 31-Day(tb.FStartDate) ")
//			.append(" 	        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1)	and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0) ")
//			.append(" 		            then Day(tb.FEndDate) ")
//			.append(" 		    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+8,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,-1) ")	
//			.append(" 	            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 	      end),0)as nine ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as ninerentedArea ")
//			  .append(" 	    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 		    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 			    group by FBuildingID ")
//			.append(" ) nine on nine.id=one.id ")
//			.append(" left join ") 
//			.append(" 	(select FBuildingID as id, count(tre.fid) as tenrentedAmount ,isnull(sum(case /*10��**/ ")
//			.append("         when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1) ")	
//			.append(" 				then 31 ")
//			.append(" 			when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,0)	and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1)and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1) ")	
//			.append(" 				then 32-Day(tb.FStartDate) ")
//			.append(" 	        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,0)	and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1)and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,DATEDIFF(yy,0,getdate()),0))+9,0) ")	
//			.append(" 		            then Day(tb.FEndDate) ")
//			.append(" 		    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+9,0)	and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,-1) ")	
//			.append(" 		            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 		      end),0)as ten ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as tenrentedArea ")
//			  .append(" 		    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 		    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 			    group by FBuildingID ")
//			.append(" ) ten on ten.id=one.id ")
//			.append(" left join ") 
//			.append(" 	(select FBuildingID as id, isnull(count(tre.fid),0) as elevenrentedAmount ,isnull(sum(case /*11��**/ ")
//			.append(" 		        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1) ")
//			.append(" 						then 30 ")
//			.append(" 				when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1) ")
//			.append(" 							then 31-Day(tb.FStartDate) ")
//			.append(" 		        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0) and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+10,0)	 and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,-1) ") 
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as eleven " )
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as elevenrentedArea ")
//			  .append(" 				    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 				    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 					    group by FBuildingID ")
//			.append(" ) eleven on eleven.id=one.id ")
//			.append(" left join  ")
//			.append(" 	(select FBuildingID as id, isnull(count(tre.fid),0) as twelverentedAmount ,isnull(sum(case /*12��**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as twelve  ")
//			.append("				,isnull(sum(room.FTenancyArea)*sum(case /*�����**/ ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 31 ")
//			.append(" 					when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	 and tb.FEndDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 							then 32-Day(tb.FStartDate) ")
//			.append(" 			        when tb.FStartDate<=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) and tb.FStartDate>=DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0) ")	
//			.append(" 				            then Day(tb.FEndDate) ")
//			.append(" 				    when tb.FStartDate>DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+11,0)	  and tb.FEndDate<DATEADD(mm,DATEDIFF(mm,0,DATEADD(yy,  DATEDIFF(yy,0,getdate()),0))+12,-1) ")
//			.append(" 				            then 1+DATEDIFF(dd,tb.FStartDate,tb.FEndDate) ")
//			.append(" 				      end),0)as twelverentedArea ")
//			  .append(" 				    from  T_SHE_Room room	  left join T_TEN_TenancyRoomEntry tre on tre.froomid = room.fid 	 left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ")
//			.append(" 				    where room.fisForTen=1 and tb.ftenancystate in('Audited','Executing','ContinueTenancying') and tre.FFlagAtTerm not between 'QuitAtTerm'and 'QuitNotAtTerm' ")
//			.append(" 					    group by FBuildingID ")
//			.append(" ) twelve on twelve.id=one.id")
//			.append(" where one.id='"+id+"'");// JquwOUXNT76kesXnEuIoJBWcbo8= 
			}
	}
}