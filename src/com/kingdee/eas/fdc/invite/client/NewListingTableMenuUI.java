package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.eas.framework.client.TableMenuUI;

public class NewListingTableMenuUI extends TableMenuUI {

	public NewListingTableMenuUI() throws Exception {
		super();
	}
	
	/**
	 * �б��嵥�༭�����Ҽ���������ʱ���Ҽ��˵��ġ�������Excel���͡�����ѡ�񲿷ֵ�excel�����ظ���ȥ���������
	 * ���ȫ��ȥ��pop.add�Ļ�����Ӱ�쵽�б�˵���ͱ��ۻ��ܱ���Ҽ��˵��᲻��ʾ����ˣ���pop.add���һ���˵������������ʾ��
	 * Modidied by Owen_wen 2011-08-31
	 */
	public KDMenu getMenu()
    {
        KDMenu pop = null;
            pop = new KDMenu();
		pop.add(menuItemExportExcel);
		pop.getItem(0).setVisible(false);
		// pop.add(menuItemExportSelected);
        return pop;
    }

}
