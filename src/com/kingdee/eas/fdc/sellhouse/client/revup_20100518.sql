
--�޸����տ�����ֶεĳ���
alter table t_bdc_fdcreceivingbill alter fdescription nvarchar(255);

--
--���ӵ����ʾ�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add fisimport int;

--����ԭ���е�ID�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbid varchar(44);

--����ԭ���е�headId�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbheadid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbheadid varchar(44);

--����ԭ���еľ��տ���ϸID�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbpaylistid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbpaylistid varchar(44);

--���ӵ����ʾ�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_FDCReceivingBill')
alter table T_BDC_FDCReceivingBill add fisimport int;

--����ԭID�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='T_BDC_FDCReceivingBill')
alter table T_BDC_FDCReceivingBill add folddbid varchar(44);

--���տ�ͻ���¼�������Ƿ���ı�ʶ�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add fisimport int;

--���տ�ͻ���¼������ԭ��ID�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add folddbid varchar(44);

--���տ�ͻ���¼������ԭ��HEADID�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbheadid' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add folddbheadid varchar(44);

--���ӵ����ʾ�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_PurchasePayListEntry')
alter table T_SHE_PurchasePayListEntry add fisimport int;

--����Ӧ����ϸ���ӵ������ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_AreaCompensateRevList')
alter table T_SHE_AreaCompensateRevList add fisimport int;

If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_CusRevList')
alter table T_SHE_CusRevList add fisimport int;

--��Դ��¼���ӵ����ʾ�ֶ�
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_TransferSourceEntry')
alter table T_BDC_TransferSourceEntry add fisimport int;

--��Щ�����ݲ�������Ϊ�Ժ󱸲飬�洢��Ӧ��ԭ�쵥��¼��ID
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbSettlemententryid' and KSQL_COL_TABNAME='T_BDC_TransferSourceEntry')
alter table T_BDC_TransferSourceEntry add folddbSettlemententryid varchar(44);
------------



--���������Ϳյļ�¼����Ϊ�տ�����
update t_she_fdcreceivebill set FBillTypeEnum='gathering' where fbilltypeenum is null and FMoneySysType='SalehouseSys';

--���տ����Ϊ�յļ�¼���տ����Ĭ��Ϊ�Ϲ��տ�
update t_she_fdcreceivebill set FGathering='purchase' where FGathering is null and FMoneySysType='SalehouseSys';

------------------------------------  ������¥�տ������  -----------------------------------------

--����¥�տ���뵽�м��(�ͻ���¼δά��,�ֵ���¼δά��)
select b.fid b_fid,b.fcreatorid b_fcreatorid,b.fcreatetime b_fcreatetime,b.flastupdateuserid b_flastupdateuserid,b.flastupdatetime b_flastupdatetime,b.fcontrolunitid b_fcontrolunitid,b.fnumber b_fnumber,b.fbizdate b_fbizdate,b.fdescription b_fdescription,b.fauditorid b_fauditorid,b.fcompanyid b_fcompanyid,b.fcurrencyid b_fcurrencyid,b.fauditdate b_fauditdate,b.fcashierid b_fcashierid,b.ffivouchered b_ffivouchered,b.fbillstatus b_fbillstatus,b.factrecamt b_factrecamt,b.fpayertypeid b_fpayertypeid,b.fpayerid b_fpayerid,b.fpayernumber b_fpayernumber,b.fpayername b_fpayername,b.famount b_famount,b.frecbilltypeid b_frecbilltypeid,b.ffdcreceivebillid b_ffdcreceivebillid,'b-c' as �����ָ���2,c.fid c_fid,c.fcreatorid c_fcreatorid,c.fcreatetime c_fcreatetime,c.flastupdateuserid  c_flastupdateuserid,c.flastupdatetime c_flastupdatetime,c.fcontrolunitid c_fcontrolunitid ,c.froomid  c_froomid,c.fsellprojectid c_fsellprojectid ,c.finvoiceid c_finvoiceid,c.FReceiptNumber  c_FReceiptNumber,c.fchequeid c_fchequeid,c.fmoneysystype c_fmoneysystype,c.ftenancycontractid  c_ftenancycontractid,c.fbilltypeenum c_fbilltypeenum,c.FGathering c_FGathering,c.FPurchaseID c_FPurchaseID, c.FSettlementBillID c_FSettlementBillID,c.FSinPurchaseID c_FSinPurchaseID,c.FSinObligateID c_FSinObligateID,c.FAsstActTypeID c_FAsstActTypeID,c.FIsBlankOut c_FIsBlankOut,c.FAdjustSrcBillID c_FAdjustSrcBillID,c.FReceiptState c_FReceiptState,c.FPrintCount c_FPrintCount,c.FSettleMentType c_FSettleMentType 
into t_tmp_revgaizao_s 
from t_cas_receivingbill b 
inner join t_she_fdcreceivebill c on b.ffdcreceivebillid=c.fid 
where FMoneySysType is null or fmoneysystype='SalehouseSys';

--����¥�տ��¼���뵽�м��(��Դ��¼δά��)
select a.fid a_fid,a.FAmount a_famount,a.FSettlement a_FSettlement,a.FSettlementTypeID a_FSettlementTypeID,a.FAccountID a_FAccountID,a.FBankNumber a_fbanknumber,a.FReceivingBillID a_FReceivingBillID,a.FRevAccountBankID a_FRevAccountBankID,a.FMoneyDefineID a_FMoneyDefineID,a.FPayListId a_FPayListId,a.FOppSubjectID a_FOppSubjectID,a.FCounteractAmount a_FCounteractAmount,a.fseq a_fseq,a.FFCounteractId a_FFCounteractId,a.FCanCounteractAmount a_FCanCounteractAmount,a.FIsPartial a_fispartial,'a-b' as �����ָ���1,b.fid b_fid,b.fcreatorid b_fcreatorid,b.fcreatetime b_fcreatetime,b.flastupdateuserid b_flastupdateuserid,b.flastupdatetime b_flastupdatetime,b.fcontrolunitid b_fcontrolunitid,b.fnumber b_fnumber,b.fbizdate b_fbizdate,b.fdescription b_fdescription,b.fauditorid b_fauditorid,b.fcompanyid b_fcompanyid,b.fcurrencyid b_fcurrencyid,b.fauditdate b_fauditdate,b.fcashierid b_fcashierid,b.ffivouchered b_ffivouchered,b.fbillstatus b_fbillstatus,b.factrecamt b_factrecamt,b.fpayertypeid b_fpayertypeid,b.fpayerid b_fpayerid,b.fpayernumber b_fpayernumber,b.fpayername b_fpayername,b.famount b_famount,b.frecbilltypeid b_frecbilltypeid,b.ffdcreceivebillid b_ffdcreceivebillid,'b-c' as �����ָ���2,c.fid c_fid,c.fcreatorid c_fcreatorid,c.fcreatetime c_fcreatetime,c.flastupdateuserid  c_flastupdateuserid,c.flastupdatetime c_flastupdatetime,c.fcontrolunitid c_fcontrolunitid ,c.froomid  c_froomid,c.fsellprojectid c_fsellprojectid ,c.finvoiceid c_finvoiceid,c.FReceiptNumber  c_FReceiptNumber,c.fchequeid c_fchequeid,c.fmoneysystype c_fmoneysystype,c.ftenancycontractid  c_ftenancycontractid,c.fbilltypeenum c_fbilltypeenum,c.FGathering c_FGathering,c.FPurchaseID c_FPurchaseID, c.FSettlementBillID c_FSettlementBillID,c.FSinPurchaseID c_FSinPurchaseID,c.FSinObligateID c_FSinObligateID,c.FAsstActTypeID c_FAsstActTypeID,c.FIsBlankOut c_FIsBlankOut,c.FAdjustSrcBillID c_FAdjustSrcBillID,c.FReceiptState c_FReceiptState,c.FPrintCount c_FPrintCount,c.FSettleMentType c_FSettleMentType 
into t_tmp_revgaizao_se 
from t_she_fdcreceivebillentry a 
inner join t_cas_receivingbill b on a.freceivingbillid=b.fid 
inner join t_she_fdcreceivebill c on b.ffdcreceivebillid=c.fid 
where FMoneySysType is null or fmoneysystype='SalehouseSys';

--���뵽���տ��¼��
--TODO��FRevListTypeδά��(�ɸ��������Ӧ����ϸ����������)��FHeadID(�����ű��Ѵ���)��FRoomIDδά��(�����Ǳ߲Ż��õ�,�ɲ�ά��)��ת����Դδά��
insert into t_bdc_fdcreceivingbillentry (fid,FRevAmount,FRevLocAmount,FSettlementTypeID,FSettlementNumber,FRevAccountID,FOppAccountID,FMoneyDefineID,FRevAccountBankID,FBankNumber,FRevListId,fseq,fisimport,fheadid,folddbid,folddbheadid,folddbpaylistid) 
select newbosid('26EEC414') as fid,a_famount as frevamount,a_famount as frevlocamount,a_fsettlementtypeid as fsettlementtypeid,a_fsettlement as fsettlementnumber,a_faccountid as frevaccountid,a_foppsubjectid as foppaccountid,a_FMoneyDefineID as FMoneyDefineID,a_frevaccountbankid as FRevAccountBankID,a_fbanknumber as FBankNumber,a_fpaylistid as FRevListId,a_fseq as fseq,2 as fisimport,'tnnd' as fheadid,a_fid as folddbid,a_freceivingbillid as folddbheadid,a_fpaylistid as folddbpaylistid from t_tmp_revgaizao_se where (c_FBillTypeEnum is null or c_FBillTypeEnum!='settlement');

--���뵽���տ��
--TODO��FRevBizType(�в��ּ�¼��ֵδά����,�ɸ��������ҵ�񵥾ݱ�����������)���վݷ�Ʊδά��(ȷ��Ʊ��)��FOrgUnitIDδά��(�ɲ�ά��)��FStateδά��(���ݲ�ά��)��FTenancyUserIDδά��(������ά��)���ͻ���¼(����SQL�Ѵ���)��
insert into T_BDC_FDCReceivingBill (fid,FCompanyID,FSellProjectID,FCurrencyID,FExchangeRate,FReceiptNumber,FtenancyObjID,FCustomerID,FBillStatus,ffiVouchered,FOriginalAmount,FAmount,FAuditTime,FBookedDate,fnumber,fbizdate,fdescription,fauditorid,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcontrolunitid,folddbid,fisimport,FpurchaseObjID,FsincerityObjID,FRoomID,frevbilltype,frevbiztype,FReceiptID,FInvoiceID,FReceiptState) 
select newbosid('F12182FE') as fid,b_fcompanyid as FCompanyID,c_fsellprojectid as FSellProjectID,b_fcurrencyid as FCurrencyID,to_number(1) as FExchangeRate,c_FReceiptNumber as FReceiptNumber,c_FTenancyContractID as FtenancyObjID,b_fpayerid as FCustomerID,b_fbillstatus as FBillStatus,b_ffivouchered as ffiVouchered,b_factrecamt as FOriginalAmount, b_factrecamt as FAmount,b_fauditdate as FAuditTime,b_fcreatetime as FBookedDate,b_fnumber as fnumber,b_fbizdate as fbizdate,b_fdescription as fdescription,b_fauditorid as fauditorid,b_fcreatorid as fcreatorid,b_fcreatetime as fcreatetime,b_flastupdateuserid as flastupdateuserid,b_flastupdatetime as flastupdatetime,b_fcontrolunitid as fcontrolunitid,b_fid as folddbid,2 as fisimport,c_fpurchaseid as FpurchaseObjID,c_FSinPurchaseID as FsincerityObjID,c_FRoomID as froomid,(case c_FBillTypeEnum when 'gathering' then 'gathering' when 'refundment' then 'refundment' when 'transfer' then 'transfer' when 'adjust' then 'adjust' else null end) as frevbilltype,(case c_FGathering when 'SinPurchase' then 'sincerity' when 'SaleRoom' then 'purchase' when 'CustomerRev' then 'customer' else 'purchase' end) as frevbiztype,c_fchequeid as FReceiptID,c_finvoiceid as FInvoiceID,c_FReceiptState as FReceiptState from t_tmp_revgaizao_s where (c_FBillTypeEnum is null or c_FBillTypeEnum!='settlement');

--���õ�����տ��¼��HeadIDΪ��Ӧ���տID
update t_bdc_fdcreceivingbillentry set fheadid=
(select top 1 a.fid from T_BDC_FDCReceivingBill a where a.folddbid=t_bdc_fdcreceivingbillentry.folddbheadid)
where fisimport=2 and exists (select a.fid from T_BDC_FDCReceivingBill a where a.folddbid=t_bdc_fdcreceivingbillentry.folddbheadid);

--���տ�ͻ����뵽���տ�ͻ���¼��(FHeadID δά��,�������ά��)
insert into T_BDC_RevFDCCustomerEntry (fid,FHeadID,FFdcCustomerID,folddbid,folddbheadid,fisimport) 
select newbosid('0F394DB6') as fid,'hehe' as fheadid,c.fid as FFdcCustomerID,a.fid as folddbid,a.fheadid as folddbheadid,2 as fisimport from T_SHE_CustomerEntry a 
inner join t_tmp_revgaizao_s b on a.fheadid=b.c_fid 
inner join T_SHE_FDCCustomer c on a.FCustomerId=c.FSysCustomerID
where b.c_FBillTypeEnum!='settlement';

--���õ�����տ�ͻ���¼��HeadIdΪ��Ӧ�տ��ID(���Կ���ִ�к��м�������δ������,��֪���ǲ�������������)
select m.fid as eid,a.fid hid 
into t_tmp_revcushead_bat 
from t_bdc_revfdccustomerentry m 
inner join t_tmp_revgaizao_s b on m.folddbheadid=b.c_fid 
inner join t_bdc_fdcreceivingbill a on a.folddbid=b.b_fid;

update t_bdc_revfdccustomerentry set fheadid=
(select top 1 a.hid from t_tmp_revcushead_bat a where a.eid=t_bdc_revfdccustomerentry.fid )
where  exists(select top 1 a.hid from t_tmp_revcushead_bat a where a.eid=t_bdc_revfdccustomerentry.fid) 
and fisimport=2;

--�տ���ϸ���͵�ֵ����,areaCompensateδ����(�����ű�����),fdcCustomerRevδ����
update t_bdc_fdcreceivingbillentry set frevlisttype='sincerityPur' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_SincerReceiveEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purchaseRev' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_PurchasePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purElseRev' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_PurchaseElsePayListEntry);

------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------
---------------------------------------  �����Ƕ�Ӧ����ϸ������  -------------------------------------------
------------------------------------------------------------------------------------------------------------


-----------------------------------------  �����Ϲ�Ӧ����ϸ  -----------------------------------------------

--���ݳ����Ϲ�Ӧ����ϸ������ݵ�t_tmp_revgaizao_spe
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spe')
select *  
into t_tmp_revgaizao_spe 
from T_SHE_SincerReceiveEntry;

--�����Ϲ�����ϸ�����ݵ�t_tmp_revgaizao_spure
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spure')
select *  
into t_tmp_revgaizao_spure 
from T_SHE_PurchasePayListEntry where fisimport is null;

--�����Ϲ���������ϸ�����ݵ�t_tmp_revgaizao_spuroe
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spuroe')
select *  
into t_tmp_revgaizao_spuroe 
from T_SHE_PurchaseElsePayListEntry;

--���³����Ϲ�Ӧ����ϸ������ΪsincerityPur
update T_SHE_SincerReceiveEntry set FrevMoneyType='sincerityPur';
--���³����Ϲ�Ӧ����ϸ���˿�����ΪAppRevRefundment
update T_SHE_SincerReceiveEntry set FrefundmentMoneyType='AppRevRefundment';
--����Ϊ���Գ���
update T_SHE_SincerReceiveEntry set FisCanRevBeyond=1 where FisCanRevBeyond is null;
--����Ϊ�Ƿ���
update T_SHE_SincerReceiveEntry set FfeeMoneyType='NotFee';

--�����Ϲ���ߣ�ʵ�ս��ĺ������ģʽ�µ����ս���ֶκ�����ͬ
update T_SHE_SincerReceiveEntry set factRevAmount=factAmount where factRevAmount is null;
--�������ʣ������˵�
update T_SHE_SincerReceiveEntry set FIsRemainCanRefundment=1 where FIsRemainCanRefundment is null;

--�����˿����Ӧ�����˽����µ���Ӧ�ļ�¼��
--�������Կ��������²����󣬱��뼴�̽��д�������������������˲��ܼ���ʹ��.һ�������տ���������ҵ�񣬴��������ᵼ�����ݴ���
update T_SHE_SincerReceiveEntry set fhasRefundmentAmount=
(select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='refundment' and c_FSinPurchaseID is not null 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid) 
where exists (select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='refundment' and c_FSinPurchaseID is not null 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid);

--������ת���
update T_SHE_SincerReceiveEntry set FHasTransferredAmount=
(select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='settlement' 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid) 
where exists (select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='settlement' 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid);

------------------------------------------------------------------------------------------------------------



------------------------------------------------ Ԥ��������� ------------------------------------------

--Ԥ���������ӵ�Ӧ����ϸ��
insert into T_SHE_PurchasePayListEntry (fid,fheadid,fseq,FMoneyDefineID,FactRevAmount,fisimport,FHasRefundmentAmount,FLimitAmount,fiscanrevbeyond) 
select newbosid('8B4211A8') as fid,a.c_fpurchaseid as fheadid,-1 as fseq,a.a_fmoneydefineid as fmoneydefineid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount,2 as fisimport,sum(case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end) as FHasRefundmentAmount,sum((case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end)+(case when a.a_FCanCounteractAmount is null then 0 else a.a_FCanCounteractAmount end)) as FLimitAmount,1 as fiscanrevbeyond from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum!='refundment' and a.c_FBillTypeEnum!='settlement' and a.c_FBillTypeEnum!='adjust' group by a.c_fpurchaseid,a.a_fmoneydefineid;

--���������տ���ϸID���µ��տ��¼��
--����������Ԥ������ϸID����Ӧ�տ��¼��
update T_BDC_FDCReceivingBillEntry set FRevListId=
(select top 1 a.fid from T_SHE_PurchasePayListEntry a 
inner join t_bdc_fdcreceivingbill b on a.fheadid=b.FpurchaseObjID 
inner join t_she_moneydefine c on a.fmoneydefineid=c.fid 
where a.fisimport=2 and c.FMoneyType='PreconcertMoney' and T_BDC_FDCReceivingBillEntry.FHeadID=b.fid and T_BDC_FDCReceivingBillEntry.fmoneydefineid=a.fmoneydefineid)
where fisimport=2 and exists (select top 1 a.fid from T_SHE_PurchasePayListEntry a 
inner join t_bdc_fdcreceivingbill b on a.fheadid=b.FpurchaseObjID 
inner join t_she_moneydefine c on a.fmoneydefineid=c.fid 
where a.fisimport=2 and c.FMoneyType='PreconcertMoney' and T_BDC_FDCReceivingBillEntry.FHeadID=b.fid and T_BDC_FDCReceivingBillEntry.fmoneydefineid=a.fmoneydefineid);

--�������������Ϲ���,Ԥ����϶���ȫ��ת�����ƻ��Կ�����,��������ת���
update T_SHE_PurchasePayListEntry set FhasTransferredAmount=FactRevAmount
where fid in (select a.fid from T_SHE_PurchasePayListEntry a 
inner join t_she_purchase b on a.fheadid=b.fid 
where a.fseq=-1 and a.fisimport=2 and b.faudittime is not null);

--TODO Ԥ����Ŀ��ˣ��⼸�ά��Ҳ�ɣ��˷����㣬�������㲻��ȡ�����ֵ��

--Ԥ���˷��˿�Ĳ������˽��.����Ԥ������˿����Ԥ����Ӧ����ϸ�����˽��
--����ֳ���2�ֻ����ϵ�Ԥ�������͵��տ�,������¾ͻ������� TODO
update T_SHE_PurchasePayListEntry set FHasRefundmentAmount=
(select abs(m.FactRevAmount) from (select a.c_fpurchaseid as fheadid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum='refundment' group by a.c_fpurchaseid,a.a_fmoneydefineid) m where m.fheadid=T_SHE_PurchasePayListEntry.fheadid)
where fisimport=2 and exists (select m.FactRevAmount from (select a.c_fpurchaseid as fheadid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum='refundment' group by a.c_fpurchaseid,a.a_fmoneydefineid) m where m.fheadid=T_SHE_PurchasePayListEntry.fheadid);

--����ʱlimitAmountΪ��ȷ������FHasRefundmentAmount��ȷ������������
update T_SHE_PurchasePayListEntry set flimitamount=(case when flimitamount is null then 0 else flimitamount end)+(case when FHasRefundmentAmount is null then 0 else FHasRefundmentAmount end) where fisimport=2;

--TODO ����Ԥ����ת���ת�



-------------------------------------  �Ϲ����ƻ��Կ���Ӧ����ϸ������  ------------------------------------
--����Ӧ�����ڣ�Ӧ�ս�ʵ�����ڣ����˽����˽���ת��� �ֶε����ֶ���
update T_SHE_PurchasePayListEntry set fappDate=fapDate where fisimport is null and fappdate is null;
update T_SHE_PurchasePayListEntry set fappAmount=fapAmount where fisimport is null and fappAmount is null;
update T_SHE_PurchasePayListEntry set factRevDate=factPayDate where fisimport is null and factRevDate is null;
update T_SHE_PurchasePayListEntry set fhasRefundmentAmount=frefundmentAmount where fisimport is null and fhasRefundmentAmount is null;
update T_SHE_PurchasePayListEntry set fhasTransferredAmount=fhasRemitAmount where fisimport is null and fhasTransferredAmount is null;

--���ƽ��Ϊ �ɿ��˽��+�����˽��
update T_SHE_PurchasePayListEntry set flimitAmount=TO_DECIMAL(isnull(fcanRefundmentAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where fisimport is null and flimitAmount is null;

--ʵ�ս���ֶΣ���Ҫ�þ�ʵ�ս��+�����˽����
update T_SHE_PurchasePayListEntry set factRevAmount=TO_DECIMAL(isnull(factPayAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where fisimport is null and factRevAmount is null;

--TODO ����(ʵ�ʻ���)���ý��,(ʵ����ת)�ݶ������Щ���ǻ��������õ�����Ӱ�����̵�ǰ���£��ɲ�����


-------------------------------------  �Ϲ�������Ӧ����ϸ������  ------------------------------------
update T_SHE_PurchaseElsePayListEntry set fappDate=fapDate where fappdate is null;
update T_SHE_PurchaseElsePayListEntry set fappAmount=fapAmount where fappAmount is null;
update T_SHE_PurchaseElsePayListEntry set factRevDate=factPayDate where factRevDate is null;
update T_SHE_PurchaseElsePayListEntry set fhasRefundmentAmount=frefundmentAmount where fhasRefundmentAmount is null;

--���ƽ��Ϊ �ɿ��˽��+�����˽��
update T_SHE_PurchaseElsePayListEntry set flimitAmount=TO_DECIMAL(isnull(fcanRefundmentAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where flimitAmount is null;

--ʵ�ս���ֶΣ���Ҫ�þ�ʵ�ս��+�����˽����
update T_SHE_PurchaseElsePayListEntry set factRevAmount=TO_DECIMAL(isnull(factPayAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where factRevAmount is null;

-------------------------------------   �����Ӧ����ϸ������ ------------------------------------

--���ݲ�����ɲ���Ӧ����ϸ
insert into T_SHE_AreaCompensateRevList (fid,fheadid,fmoneydefineid,fRefundmentMoneyType,fAppAmount,fLimitAmount,fRevMoneyType,fisimport)
select newbosid('179EB1A2') as fid,a.fid as fheadid,(select top 1 fid from t_she_moneydefine where FMoneyType='CompensateAmount' and fsystype='SalehouseSys') as fmoneydefineid,(case when a.FCompensateAmount<0 then 'AppRefundment' else 'AppRevRefundment' end) as fRefundmentMoneyType,(case when a.FCompensateAmount is null then 0 else a.FCompensateAmount end) as fapprevamount,abs(case when a.FCompensateAmount is null then 0 else a.FCompensateAmount end) as flimitamount,'AppRev' as frevmoneytype,2 as fisimport 
from T_SHE_RoomAreaCompensate a 
where a.FCompensateState in ('COMAUDITTED','COMRECEIVED');

--���ÿ���Ϊ�������ϸ����Ϊ��������
update t_bdc_fdcreceivingbillentry set frevlisttype='areaCompensate' where fisimport=2 and fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys');

--���ÿ���Ϊ������տ��¼��revListId
update t_bdc_fdcreceivingbillentry set frevlistid=
(select top 1 g.fid from t_bdc_fdcreceivingbill f 
inner join t_she_room b on f.froomid=b.fid 
inner join t_she_purchase c on b.flastpurchaseid=c.fid 
inner join T_SHE_RoomAreaCompensate e on e.froomid=b.fid 
inner join T_SHE_AreaCompensateRevList g on g.fheadid=e.fid 
where t_bdc_fdcreceivingbillentry.fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys') and f.fisimport=2 and t_bdc_fdcreceivingbillentry.fheadid=f.fid)
where fisimport=2 and exists (select top 1 g.fid from t_bdc_fdcreceivingbill f 
inner join t_she_room b on f.froomid=b.fid 
inner join t_she_purchase c on b.flastpurchaseid=c.fid 
inner join T_SHE_RoomAreaCompensate e on e.froomid=b.fid 
inner join T_SHE_AreaCompensateRevList g on g.fheadid=e.fid 
where t_bdc_fdcreceivingbillentry.fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys') and f.fisimport=2 and t_bdc_fdcreceivingbillentry.fheadid=f.fid);

--�����տ�Ľ�������ϸ�����ս����
update T_SHE_AreaCompensateRevList set FActRevAmount=(
select sum(case when FRevAmount is null then 0 else FRevAmount end) 
from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys'
group by a.FRevListId having a.frevlistid=T_SHE_AreaCompensateRevList.fid
)where fisimport=2 and exists (
select a.FRevListId 
from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys'
group by a.FRevListId having a.frevlistid=T_SHE_AreaCompensateRevList.fid);

--�����տ���Ҫ�������˽���ֶ�
update T_SHE_AreaCompensateRevList set FHasRefundmentAmount=abs(FActRevAmount) where fisimport=2 and FActRevAmount<0;

--�����Ĳ�����տ�ĵ��������޸�Ϊ�˿�
update t_bdc_fdcreceivingbill set FRevBillType='refundment' where fid in (
select c.fid from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
inner join t_bdc_fdcreceivingbill c on a.fheadid=c.fid 
where a.fisimport=2 and b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys' and c.FRevBillType='gathering' and a.FRevAmount<0
);

-----------------------------------  �������㼰�ͻ��տ���ϸ������  ---------------------------------

insert into T_SHE_CusRevList (fid,FFdcCustomerID,fmoneydefineid,FactRevAmount,fisimport,FHasRefundmentAmount,FLimitAmount) 
select newbosid('C64ADF7B') as fid,b.fid as FFdcCustomerID,a.a_fmoneydefineid as fmoneydefineid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount,2 as fisimport,sum(case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end) as FHasRefundmentAmount,sum((case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end)+(case when a.a_FCanCounteractAmount is null then 0 else a.a_FCanCounteractAmount end)) as FLimitAmount from t_tmp_revgaizao_se a 
left join t_she_fdccustomer b on a.b_fpayerid=b.fsyscustomerid 
inner join t_she_moneydefine c on a.a_FMONEYDEFINEID=c.fid 
where fmoneytype='PreMoney' and a.c_FBillTypeEnum!='refundment' and a.c_FBillTypeEnum!='settlement' and a.c_FBillTypeEnum!='adjust' and c_FGathering='CustomerRev'
group by b.fid,a.a_fmoneydefineid;

--TODO �����ݶ����տ��revlistid


--ת����Դ��¼�Ĵ���

--TODO ������
--��Դ��¼��Ҫ��Ԥ����ϸID�ȶ����úú��ٽ���



--����������setleID����嵥�ĶԷ���Ŀ���������տ��Ŀ��ͬ������෴��
insert into T_BDC_TransferSourceEntry (fid,FHeadId,FFromRevListId,FFromRevListType,FAmount,fisimport,folddbSettlemententryid,ffrommoneydefineid)
select newbosid('DCBE7750') as fid,d.fid as fheadid,c.frevlistid as ffromrevlistid,'tnnd' as ffromrevlisttype,abs(case when b.a_famount is null then 0 else b.a_famount end) as famount,2 as fisimport,b.a_fid as folddbSettlemententryid,b.a_fmoneydefineid from t_tmp_revgaizao_se a 
inner join t_tmp_revgaizao_se b on a.c_FSettlementBillID=b.b_fid and a.a_FAccountID=b.a_FOppSubjectID and a.a_FAmount is not null and b.a_famount is not null and a.a_famount+b.a_famount=0 
left join t_bdc_fdcreceivingbillentry c on b.a_ffcounteractid=c.folddbid and c.fisimport=2  
inner join t_bdc_fdcreceivingbillentry d on a.a_fid=d.folddbid and d.fisimport=2
where b.c_FBillTypeEnum='settlement';


--------------------------------------------
update t_bdc_fdcreceivingbillentry set frevlisttype='sincerityPur' where fisimport=2 and frevlistid in (select fid from T_SHE_SincerReceiveEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purchaseRev' where fisimport=2 and frevlistid in (select fid from T_SHE_PurchasePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purElseRev' where fisimport=2 and frevlistid in (select fid from T_SHE_PurchaseElsePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='fdcCustomerRev' where fisimport=2 and frevlistid in (select fid from T_she_cusrevlist);
update t_bdc_fdcreceivingbillentry set frevlisttype='areaCompensate' where fisimport=2 and frevlistid in (select fid from T_SHE_AreaCompensateRevList);


update T_BDC_TransferSourceEntry set FFromRevListType='sincerityPur' where fisimport=2 and FFromRevListId in (select fid from T_SHE_SincerReceiveEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='purchaseRev' where fisimport=2 and FFromRevListId in (select fid from T_SHE_PurchasePayListEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='purElseRev' where fisimport=2 and FFromRevListId in (select fid from T_SHE_PurchaseElsePayListEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='fdcCustomerRev' where fisimport=2 and FFromRevListId in (select fid from T_she_cusrevlist);
update T_BDC_TransferSourceEntry set FFromRevListType='areaCompensate' where fisimport=2 and FFromRevListId in (select fid from T_SHE_AreaCompensateRevList);

--������տ���տ�ҵ�������޸�Ϊ�������
update t_bdc_fdcreceivingbill set FRevBizType='areaCompensate' where fid in 
(select fheadid from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.FMoneyType='CompensateAmount' and a.fisimport=2);

--ԭ�����������ɵ��տ��δ��¼�տ����Ҫ���������������տ�ϻ��ܷ�¼��������
update t_bdc_fdcreceivingbill set FAmount=(select sum(a.frevamount) from t_bdc_fdcreceivingbillentry a where a.fheadid=t_bdc_fdcreceivingbill.fid) 
where fisimport=2 and FRevBillType='transfer' and (case when FAmount is null then 0 else FAmount end)=0;

update t_bdc_fdcreceivingbill set FOriginalAmount=famount where fisimport=2 and FRevBillType='transfer' and (case when FOriginalAmount is null then 0 else FOriginalAmount end)=0;

--����ԭ�տ��BOTP��¼(��¥��)
select b.fid b_fid,a.fid a_fid,a.fsrcobjectid,a.fdestobjectid 
into t_tmp_bot_skdgz_bat_s 
from T_BOT_Relation a 
inner join t_bdc_fdcreceivingbill b on a.fsrcobjectid=b.folddbid 
where a.fsrcentityid='FA44FD5B' and a.fdestentityid='2652E01E' and b.fisimport=2;

--����BOTP��¼(��¥��)
update T_BOT_Relation set fsrcobjectid=
(select a.fid from t_bdc_fdcreceivingbill a where a.fisimport=2 and t_bot_relation.fsrcobjectid=a.folddbid)
where fsrcentityid='FA44FD5B' and fdestentityid='2652E01E' and exists (select a.fid from t_bdc_fdcreceivingbill a where a.fisimport=2 and t_bot_relation.fsrcobjectid=a.folddbid)
