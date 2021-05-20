package com.kingdee.eas.fdc.schedule.image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/***
 * 
 * ����Ϊ��̬չʾЧ��ͼ������¼���
 * 
 * Ŀ�ģ�ʵ��������������ͼ��ʱ���ƶ�ָ��ͼ������Ӧ������ͼ�ϣ�����ʾ��ͼ
 * @author  ����
 *
 */
class ImageMouseListener implements MouseListener{
	KDImagePanel mainImgPanel = null;
	JLabel lPointer = null;
	Map imgs = null;
	Map grayImgs = null;
	JLabel lastFocus = null;
	BufferedImage loading = null;
	String imgName = null;
	public String getImgName() {
		return imgName;
	}

	private List imageChangeListeners = new ArrayList();
	/***
	 * 
	 * @param pl ������ʾԭʼͼ��panel����Ҫ��Ҫ����panel�Ĵ�С������ͼ�ξ�����ʾ
	 * @param iL ������ʾԭʼͼ��label����Ҫ��Ϊ����ʾͼƬ
	 * @param p  ������ʾָ��ͼ��label��������ʾָ��ͼ
	 * @param i  ��������ͼ��Map<Integer,BufferedImage> [(1,value1),(2,value2),(3,value3)]
	 * @param gi ��������ͼ�ĻҶ�ͼ���ϣ���ʽͬ�ϣ�key��valueֵ���Ӧ
	 * @param loadimg ����loadingͼ
	 * @param change ����ӿڣ����ṩ�����ʵ���࣬ʵ�ֻ�ȡԭʼͼ�ķ����������Ե����ⲿ�¼�����
	 */
	ImageMouseListener(KDImagePanel pl,JLabel p,Map i,Map gi, BufferedImage loadimg,ImageSelectChangedLisener change){
		mainImgPanel = pl;
//		mainImgLabel = iL;
		lPointer = p;
		imgs = i;
		grayImgs = gi;
		loading = loadimg;
		imageChangeListeners.add(change);
	}
	public void addImageChangeLisener(ImageSelectChangedLisener change){
		imageChangeListeners.add(change);
	}
	/***
	 * ��ָ��ͼ�ƶ���ִ�е�label�ϣ�������ԭʼͼ
	 * @param o
	 */
	public void moveToImg(Object o){
		changeImg(o);
		movePointer(o);
	}
	/***
	 * �任����ͼ���Ҷ�ͼ����ʾԭʼͼ
	 * @param o
	 */
	public void changeImg(Object o) {
		/***
		 * ��������Ļ���ͬһ��ͼƬ���������κβ���
		 */
		if(o == lastFocus)
			return;
    	if(o instanceof JLabel){
    		JLabel lo = (JLabel)o;
    		if(o!=null&&lo.getIcon() instanceof ImageIcon){
    			ImageIcon iio = (ImageIcon)lo.getIcon();
    			if(iio!=null){
    				if(lo.isEnabled()&&lo.getX()-lPointer.getX()==4)
    					lastFocus = lo;
    				else
    				{    					
    					if(lastFocus!=null)
    					{
    						lastFocus.setOpaque(false);
    						((ImageIcon)(lastFocus.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lastFocus.getName()))));
    					}
    					if(!lo.isOpaque())
    					{
    						((ImageIcon)(lo.getIcon())).setImage((BufferedImage)(imgs.get(Integer.valueOf(lo.getName()))));
    						mainImgRepaint(loading);
    						imgName = lo.getName();
    						
    						new Thread(new Runnable() {// ����һ���߳������ڲ���
    							public void run() {
    								if(imageChangeListeners!=null){
    									int i = Integer.valueOf(imgName).intValue();
    									for(Iterator it = imageChangeListeners.iterator();it.hasNext();){
    										ImageSelectChangedLisener changeEvent = (ImageSelectChangedLisener)it.next();
    										BufferedImage img = changeEvent.getOriginalImg(i); 
        									if(img!=null)
        										mainImgRepaint(img);
        									changeEvent.imageSelectChanged(i);
    									}
    									
    								}
    							}
    						}).start();
    					}
    					else
    						((ImageIcon)(lo.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lo.getName()))));
    					lo.setOpaque(!lo.isOpaque());
						
    				}
    			}
    		}
    	}
    	
	}
	/***
	 * ����ͼƬ�Ĵ�С��������ʾͼƬ
	 * @param img
	 */
	public void mainImgRepaint(BufferedImage img){
		if(img==loading)
		{
			mainImgPanel.setImage(img, false);
		}
		else
		{
			if(mainImgPanel.isStop()){
				mainImgPanel.setImage(img, true);
			}else{
				try{
					mainImgPanel.setStop(true);
					Thread.sleep(2);
					mainImgPanel.setImage(img, true);
				}catch(Exception ex){
					
				}
			}
			
		}
	}
	/***
	 * �ƶ�ָ��ͼ������Ӧ������ͼ��
	 * @param o
	 */
	public void movePointer(Object o){
    	int start_x = lPointer.getX();
    	int taget_x = 0;
		try {
			taget_x = ((JLabel)o).getX();
		} catch (NullPointerException e) {
			return;
		}
    	boolean pointerStop = false;
    	while(!pointerStop){
			if((taget_x-start_x<=4)&&(taget_x-start_x>=3)){//�ж�����Ƿ��Ƶ�ָ��λ��
				pointerStop = true;//ֹͣ�ƶ�
				((JLabel)o).setOpaque(true);
				if(lastFocus!=null&&lastFocus!=o){
					lastFocus.setOpaque(false);
					((ImageIcon)(lastFocus.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lastFocus.getName()))));
					lastFocus = (JLabel)o;
				}
				break;
			}
			start_x+=(start_x+4<=taget_x)?2:-2;
			lPointer.setLocation(start_x,3);//���¶�λ
			try {
				Thread.sleep(1);//�ƶ��ӳ�ʱ�䣬������
			}
			catch (Exception ex) {
			}	
		}
    }

	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mouseEntered(MouseEvent e) {
		final Object o = e.getSource();
		new Thread(new Runnable(){
	    	public void run(){
	    		changeImg(o);
	    		movePointer(o);
	   	     }
			
		}).start(); 
	}

	public void mouseExited(MouseEvent e) {
		final Object o = e.getSource();
		new Thread(new Runnable(){
	    	public void run(){
	    		changeImg(o);
	   	     }
			
		}).start(); 
	}

	public void mousePressed(MouseEvent e) {
		
		
	}

	public void mouseReleased(MouseEvent e) {
		
		
	}
	
};
