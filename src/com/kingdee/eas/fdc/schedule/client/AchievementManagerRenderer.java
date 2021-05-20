package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.table.render.RenderObject;
import com.kingdee.bos.ctrl.kdf.util.render.CellIconRender;
import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.bos.ctrl.kdf.util.render.TextIconRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;

/**
 * �׶��Գɹ�������
 * <p>
 * ����չʾ��Ԫ���е�List
 * 
 * @author emanon
 * 
 */
public class AchievementManagerRenderer extends TextIconRender {
	private static CellTextRender render = new CellTextRender();
	private static CellIconRender iconRender = new CellIconRender();

	public void draw(Graphics graphics, Shape clip, Object object,
			Style cellStyle, Object extObject) {
		if (object instanceof List && extObject instanceof RenderObject) {
			List lst = (List) object;
			Rectangle rect = (Rectangle) clip;
			RenderObject ro = (RenderObject) extObject;
			String sltID = (String) ro.getTable().getUserObject();
			sltID = sltID == null ? "" : sltID;
			for (int i = 0; i < lst.size(); i++) {
				Map cellData = (Map) lst.get(i);
				String id = (String) cellData.get("id");
				String name = (String) cellData.get("name");
				String state = (String) cellData.get("state");
				// Rectangle rText = new Rectangle(rect.x, rect.y + (19 * i),
				// rect.width, 19);
				// ��ɫ��ɫ����δ�ύ�ĳɹ���
				if (FDCBillStateEnum.SAVED_VALUE.equals(state)) {
					graphics.setColor(AchievementManagerCellEditor.COLOR_GRAY);
					// ��ɫ��ɫ�������еĳɹ���
				} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state)
						|| FDCBillStateEnum.AUDITTING_VALUE.equals(state)) {
					graphics
							.setColor(AchievementManagerCellEditor.COLOR_YELLOW);
					// ��ɫ��ɫ��������ͨ���ĳɹ���
				} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)) {
					graphics.setColor(AchievementManagerCellEditor.COLOR_GREEN);
				}
				// ����ɫ
				graphics
						.fillRect(rect.x, rect.y + (19 * i) + 1, rect.width, 18);
				// ���߿�
				if (sltID.equals(id)) {
					graphics.setColor(Color.RED);
					graphics
							.drawRect(rect.x, rect.y + (19 * i), rect.width, 19);
					// graphics
					// .drawString("��", rect.x + 1, rect.y + (19 * i) + 15);
					// graphics.setColor(Color.BLACK);
					// graphics.drawString(name, rect.x + 12, rect.y + (19 * i)
					// + 15);
				} else {
					graphics.setColor(Color.BLACK);
					if (i != 0) {
						graphics.drawLine(rect.x, rect.y + (19 * i), rect.x,
								rect.y + (19 * (i + 1)));
						graphics.drawLine(rect.x + rect.width, rect.y
								+ (19 * i), rect.x + rect.width, rect.y
								+ (19 * (i + 1)));
						graphics.drawLine(rect.x, rect.y + (19 * (i + 1)),
								rect.x + rect.width, rect.y + (19 * (i + 1)));
					} else {
						graphics.drawRect(rect.x, rect.y + (19 * i),
								rect.width, 19);
					}
				}
				// ������
				graphics.setColor(Color.BLACK);
				graphics.drawString(name, rect.x + 1, rect.y + (19 * i) + 15);
			}
		}
	}
}
