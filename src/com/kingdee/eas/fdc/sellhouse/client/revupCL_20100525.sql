--����ű���Ҫ����ִ�а�������
--drop table t_tmp_revgaizao_s;

--drop table t_tmp_revgaizao_se;

select count(*) from t_bdc_fdcreceivingbillentry;
--delete from t_bdc_fdcreceivingbillentry where fisimport=2;


select count(*) from t_bdc_fdcreceivingbill;
--delete from t_bdc_fdcreceivingbill where fisimport=2;


select count(*) from T_BDC_RevFDCCustomerEntry where fisimport=2;
--delete from T_BDC_RevFDCCustomerEntry where fisimport=2;


select count(*) from t_tmp_revgaizao_spe;
--�����������drop�������ݵ�����
--����3�䣬���ȷ�� t_tmp_revgaizao_spe �������ݡ����С��
--delete from T_SHE_SincerReceiveEntry;
--insert into T_SHE_SincerReceiveEntry select * from t_tmp_revgaizao_spe;
--drop table t_tmp_revgaizao_spe;

select count(*) from t_tmp_revgaizao_spure;
--delete from T_SHE_PurchasePayListEntry;
--insert into T_SHE_PurchasePayListEntry select * from t_tmp_revgaizao_spure;
--drop table t_tmp_revgaizao_spure;

select count(*) from t_tmp_revgaizao_spuroe;
--delete from T_SHE_PurchaseElsePayListEntry;
--insert into T_SHE_PurchaseElsePayListEntry select * from t_tmp_revgaizao_spuroe;
--drop table t_tmp_revgaizao_spuroe;
--
select * from T_SHE_PurchasePayListEntry where fisimport=2;
--delete from T_SHE_PurchasePayListEntry where fisimport=2;


select * from T_SHE_AreaCompensateRevList where fisimport=2
--delete from T_SHE_AreaCompensateRevList where fisimport=2;


select * from T_BDC_TransferSourceEntry where fisimport=2;
--delete from T_BDC_TransferSourceEntry where fisimport=2;

select * from T_SHE_CusRevList where fisimport=2;
--delete from T_SHE_CusRevList where fisimport=2;

select count(*) from t_tmp_bot_skdgz_bat_s;
--drop table t_tmp_bot_skdgz_bat_s;