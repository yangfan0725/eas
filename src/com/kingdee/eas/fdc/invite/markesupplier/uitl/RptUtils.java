package com.kingdee.eas.fdc.invite.markesupplier.uitl;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;

/**
 * @author JinXP
 * Ec��������
 */
public class RptUtils {

	public static final String RPT_QUERYRESULT = "rpt.QueryResult";
	public static final String RPT_TEMPTABLE = "rpt.TempTable";
	public static final String RPT_PARAMETERS = "rpt.Parameters";
	
	/**
	 * �����ʱ��, �����ı���������ָ��������ʵ�ʴ���/���õı���
	 * @param ctx
	 * @param sql  ��ṹ
	 * @param desc ��ժҪ
	 * @return
	 * @throws BOSException
	 */
    public static String createTempTable(Context ctx, String sql, String desc) throws BOSException {
        String tableName = null;
        tableName = desc;
        sql = "create table " + tableName + " " + sql;
		try {
			tableName = TempTablePool.getInstance(ctx).createTempTable(sql);
		} catch (Exception e) {
			throw new BOSException(e);
		}

        return tableName;
    }
    
    /**
     * �ͷ���ʱ��
     * @param ctx
     * @param tableName ����
     * @throws BOSException
     */
    public static void dropTempTable(Context ctx, String tableName) throws BOSException {

		try {
			TempTablePool.getInstance(ctx).releaseTable(tableName);
		} catch (Exception e) {
			throw new BOSException(e);
		}

    }

}
