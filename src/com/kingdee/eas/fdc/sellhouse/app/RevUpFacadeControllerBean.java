package com.kingdee.eas.fdc.sellhouse.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;

public class RevUpFacadeControllerBean extends AbstractRevUpFacadeControllerBean
{
    private static Logger log =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RevUpFacadeControllerBean");
    protected String _executeSQL(Context ctx, List sqls)throws BOSException
    {
		StringBuffer sb = new StringBuffer();

		long s1 = System.currentTimeMillis();

		sb.append("��ʼִ�У�");
		sb.append("\r\n");
		for (int i = 0; i < sqls.size() - 1; i++) {
			long s2 = System.currentTimeMillis();
			String tmp = (String) sqls.get(i);

			String[] ss1 = tmp.split("\r\n");

			String sqlDes = "";
			for (int j = 0; j < ss1.length; j++) {
				if (ss1[j].startsWith("--")) {
					sqlDes += ss1[j];
				}
			}
			
			//Ϊ��֤������־�������error���
			log.error("׼��ִ�У�" + sqlDes);
			sb.append("׼��ִ�У�" + sqlDes);
			sb.append("\r\n");

			DbUtil.execute(ctx, tmp);

			long s3 = System.currentTimeMillis();
			log.error("ִ����ɣ�" + sqlDes);
			sb.append("ִ����ɣ���ʱ��" + (s3 - s2) + "ms");
			sb.append("\r\n");
		}

		long s4 = System.currentTimeMillis();
		log.error("ȫ��ִ����ɣ�");

		sb.append("ȫ����ɣ���ʱ" + (s4 - s1) + "ms");
		return sb.toString();
	}
    
    /*StringBuffer sb = new StringBuffer();

		Connection conn = null;
		Statement statement = null;
		
		long s1 = System.currentTimeMillis();
		try {
			conn = EJBFactory.getConnection(ctx);
//			conn.setAutoCommit(false);
			statement = conn.createStatement();
			
			sb.append("��ʼִ�У�");
			sb.append("\r\n");
			for (int i = 0; i < sqls.size() - 1; i++) {
				long s2 = System.currentTimeMillis();
				String tmp = (String) sqls.get(i);

				String[] ss1 = tmp.split("\r\n");

				String sqlDes = "";
				for (int j = 0; j < ss1.length; j++) {
					if (ss1[j].startsWith("--")) {
						sqlDes += ss1[j];
					}
				}

				log.info("׼��ִ�У�" + sqlDes);
				sb.append("׼��ִ�У�" + sqlDes);
				sb.append("\r\n");

				statement.execute(tmp);

				long s3 = System.currentTimeMillis();
				log.info("ִ����ɣ�" + sqlDes);
				sb.append("ִ����ɣ���ʱ��" + (s3 - s2) + "ms");
				sb.append("\r\n");
			}
//			conn.commit();
//			conn.setAutoCommit(true);
		} catch (SQLException exc) {
//			try {
//				conn.rollback();
//			} catch (SQLException e) {
//				throw new BOSException(e);
//			}
			throw new BOSException(exc);
		} finally {
			SQLUtils.cleanup(statement, conn);
		}

		long s4 = System.currentTimeMillis();
		log.info("ȫ��ִ����ɣ�");

		sb.append("ȫ����ɣ���ʱ" + (s4 - s1) + "ms");
		return sb.toString();*/
}