package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.util.Map;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class PaymentClientUtils {
	
	
	public static String checkPaymentSplitState(String billId, Map param) throws Exception{
		String msg = "�����δ���в�֣�����ͨ������֡���ť���߸�������ʱ����ֹ��ܽ��в�֣��������ύ";
		if(billId==null){
			msg="���ȱ��浥��,����ȫ��ֺ��ύ";
		}else{
			boolean isClosed = true;//�ر��˷�Ʊ���һ��
			FDCSQLBuilder builder=new FDCSQLBuilder();
			IRowSet rs;
			
			boolean isSimpleInvoice = Boolean.valueOf(param.get("isSimpleInvoice").toString()).booleanValue();
			boolean isSimpleFinancial = Boolean.valueOf(param.get("isSimpleFinancial").toString()).booleanValue();
			boolean isSimpleFinancialExtend = Boolean.valueOf(param.get("isSimpleFinancialExtend").toString()).booleanValue();
			boolean isFinancial = Boolean.valueOf(param.get("isFinancial").toString()).booleanValue();;
			boolean isSeparate = Boolean.valueOf(param.get("isSeparate").toString()).booleanValue();
			//�ж��Ƿ��бȵ�ǰ����������,��������������ǰ���ݲ��ǵ�һ��
			builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
			builder.addParam(billId);
			if (builder.isExist()) {
				isClosed = false ;
			}
			builder.clear();
			builder.appendSql("select pb.flocalamount famount,pb.fprojectpriceincontract fprojectpriceincontract,pr.finvoiceamt finvoiceamt,con.fiscostsplit fiscostsplit from t_cas_paymentbill pb ");
			builder.appendSql("inner join t_con_payrequestbill pr on pr.fid=pb.ffdcpayreqid ");
			builder.appendSql("inner join t_con_contractbill con on con.fid=pb.fcontractbillid ");
			builder.appendSql("where pb.fid=? ");
			builder.addParam(billId);
			builder.appendSql("union ");
			builder.appendSql("select pb.flocalamount famount,pb.fprojectpriceincontract fprojectpriceincontract,pr.finvoiceamt finvoiceamt,con.fiscostsplit fiscostsplit from t_cas_paymentbill pb ");
			builder.appendSql("inner join t_con_payrequestbill pr on pr.fid=pb.ffdcpayreqid ");
			builder.appendSql("inner join t_con_contractwithouttext con on con.fid=pb.fcontractbillid ");
			builder.appendSql("where pb.fid=? ");
			builder.addParam(billId);
			
			rs=builder.executeQuery();
			BigDecimal amount = FDCHelper.ZERO;
			BigDecimal amtInContract = FDCHelper.ZERO;
			BigDecimal invoiceAmt = FDCHelper.ZERO;
			boolean isCostSplit = true;
			if(rs!=null&&rs.size()==1){
				rs.next();
				amount = FDCHelper.toBigDecimal(rs.getBigDecimal("famount"),2);
				amtInContract = FDCHelper.toBigDecimal(rs.getBigDecimal("fprojectpriceincontract"),2);
				invoiceAmt = isClosed?FDCHelper.toBigDecimal(rs.getBigDecimal("finvoiceamt"),2):FDCHelper.ZERO;
				isCostSplit = rs.getBoolean("fiscostsplit");
				
			}
			
			String tableentry = isCostSplit?"t_fnc_paymentsplitentry":"t_fnc_paymentnocostsplitentry";
			String tablehead =isCostSplit?"t_fnc_paymentsplit":"t_fnc_paymentnocostsplit";
			builder.clear();
			builder.appendSql("select sum(e.finvoiceamt) finvoiceamt,sum(e.fpayedamt) fpayedamt,h.fisconwithouttext fisnotext from "+tableentry+" e ");
			builder.appendSql("inner join "+tablehead+" h on h.fid=e.fparentid ");
			builder.appendSql("where h.fpaymentbillid=? and e.fisleaf=1 and h.fisinvalid=0 ");
			builder.addParam(billId);
			builder.appendSql("group by h.fisconwithouttext ");//ֻ��һ�ŵ���
			rs = builder.executeQuery();
			if (rs != null && rs.size() == 1) {
				rs.next();
				boolean isNoText = rs.getBoolean("fisnotext");
				BigDecimal splitPay = FDCHelper.toBigDecimal(rs.getBigDecimal("fpayedamt"),2);
				BigDecimal splitInv = FDCHelper.toBigDecimal(rs.getBigDecimal("finvoiceamt"),2);
				//δ���ü򵥷�Ʊ��δ���ù���������������Ʊ
				if(!isSimpleInvoice && !isSeparate){
					invoiceAmt = FDCHelper.ZERO;
					splitInv = FDCHelper.ZERO;
				}
				//���ı���Ʊ���ô���ֱ�����
				if(isNoText){
					splitInv = splitPay;
				}
				if(isSimpleFinancial){
					if(!isNoText&&isSimpleFinancialExtend){
						//�򵥣������ı�������չ�����Ϊ��ͬ�ڹ��̿�
						amount = amtInContract;
					}else{
						//
					}
				}else if(isFinancial){
					//����ģʽ���������������Ǻ�ͬ�ڹ��̿�
					amount = amtInContract;
				} else{
					amount = amtInContract;
				}
				
				
				if (amount.compareTo(
						splitPay) != 0
						|| invoiceAmt
								.compareTo(splitInv) != 0) {
					msg = "�����δ���в�֣�����ͨ������֡���ť���߸�������ʱ����ֹ��ܽ��в�֣��������ύ";
				} else {
					msg = "";
				}
			} 
			
		}
		
		return msg;
	}
	
	
}
