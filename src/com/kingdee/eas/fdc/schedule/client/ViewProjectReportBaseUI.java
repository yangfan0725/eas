/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.RectangleEdge;
import com.kingdee.bos.ctrl.freechart.util.Rotation;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;

/**
 * output class name
 */
public class ViewProjectReportBaseUI extends AbstractViewProjectReportBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(ViewProjectReportBaseUI.class);
    private Map data = null;
    
    /**
     * output class constructor
     */
    public ViewProjectReportBaseUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	fetchData();
		super.onLoad();
		 initUI();
		fillData();
		initPreTime();
	}

    protected DefaultKingdeeTreeNode DefaultNode;
	private void initPreTime() {
		// ��ǰ�û�
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// ��ǰ����֯
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, getFuncation(getUIName()));
			if (!FDCHelper.isEmpty(treeId)) {
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo info = (CurProjectInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo) {
						ProjectSpecialInfo info = (ProjectSpecialInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof AdminOrgUnitInfo) {
						AdminOrgUnitInfo info = (AdminOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
						CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CompanyOrgUnitInfo) {
						CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /*
     *��Map�е�������䵽UI
     *TABLE�� 
     */
    public void fillData() {
		
		
	}

	/*
     * �ӷ�����һ�λ�ȡ��������Ҫ������
     * �����ݴ洢��ȫ�ֱ���map��
     * 
     * ע�⣺
     * 1�������п����ǴӼƻ�ִ�н�����ã�����ǴӼƻ�ִ�н�����ã�����Ҫ�����ط�����ȡ��
     * �Ӽƻ�ִ�н�����е���ʱ����UIContext���԰ѱ���Ķ��󴫵ݵ�������
     * Ȼ���ٽ���ͼ�λ��������ݵ�չʾ
     * 
     */
	public void fetchData() {
		
		
	}
	/**
	 * ��ѡ��ı�
	 * 
	 * 1����Ҫ������ѡ��Ŀ���³�ʼ��ѡ��ؼ���ֵ
	 * 2��Ĭ����ʱ���һ����Ŀ����
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

	}

	/**
	 * ���ѡ��ı�
	 * ��Ҫ���´ӷ�����ȡ��
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
	}

	/**
	 * ��ʼ������<br>
	 * ��Ϊ������<br>
	 * 1�������������⣬��ʼ����<br>
	 * 2������ͼ�������⣬��ʼ�����<br>
	 * 3����ʼ�����
	 * 4������ǴӼƻ�ִ�н�����ã���Ҫ����������Ҫ��������ѡ��Ŀؼ�
	 * 
	 * 
	 * @throws Exception
	 */
	protected void initUI() throws Exception {
		this.sptTop.setDividerLocation(100);
		this.sptDown.setDividerLocation(200);
		initTree();
		// initChart();
		initTable();
	}


	/**
	 * ��ʼ����<br>
	 * ���Թ���һ���ɱ����Ļ��߹�����Ŀ��
	 */
	protected void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
	}

	/**
	 * ��ʼ��ͼ�����<br>
	 * �����������������ֵ�����÷ָ�����������߶ȵ�<br>
	 * ע�����ͼ�����ݲ��ڴ����
	 */
	protected void initChart() {
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
	}

	/**
	 * ��ʼ�����<br>
	 * ������û�����Ԫ�������<br>
	 */
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
	}
	

	/**
	 * ����һ��ͼ�����<br>
	 * 
	 * @return
	 */
	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * ����ͼ�����������Դ
	 * 
	 * @return
	 */
	protected Dataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("��ʱ���", new Double(43.2));
        result.setValue("��ʱ���", new Double(10.0));
        result.setValue("��ȷ��", new Double(17.5));
        result.setValue("�ӳ�δ���", new Double(32.5));
        
        
        return result;
	}
	/*
	 * ��ȡͼƬ��Title
	 */
	public String getChartTitle() {
		// TODO Auto-generated method stub
		return "YYYY���WW���ܱ�";
	}
	public void onShow() throws Exception {
		super.onShow();
		sptTop.setDividerLocation(450);
		sptDown.setDividerLocation(250);
	}

	/**
	 * ����һ��ͼ��
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		plot.setOutlineStroke(new BasicStroke(0));// �߿��ϸ
		plot.setOutlinePaint(Color.white);// �߿���ɫ
		plot.setLabelFont(new Font("����", Font.PLAIN, 10)); 
		plot.setForegroundAlpha(0.75f);
		plot.setBackgroundPaint(Color.white);
		 //����ͻ����ʾ�����ݿ�         
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// pie.setSectionLabelFont(new Font("����", Font.TRUETYPE_FONT, 12));
		// �趨����͸���ȣ�0-1.0֮�䣩
		plot.setBackgroundAlpha(0.6f);
		// �趨ǰ��͸���ȣ�0-1.0֮�䣩
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.08);
		plot.setNoDataMessage("δ��������");
		plot.setSectionPaint("��ʱ���", Color.GREEN);
		plot.setSectionPaint("��ʱ���", Color.RED);
		plot.setSectionPaint("��ȷ��", Color.ORANGE);
		plot.setSectionPaint("�ӳ�δ���", new Color(139, 0, 180));
		plot.setSectionPaint("ִ����", new Color(0, 82, 41));
		
		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("����", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("����", Font.BOLD, 12)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 12)); 
		return chart;
	}
	
	/**
	 * @description �رմ����Զ��������ѡ��Ĺ�����Ŀ
	 * @author �ź���
	 * @createDate 2011-10-25
	 * @version EAS7.0
	 * @see
	 */

	public boolean destroyWindow() {
		try {
			DefaultKingdeeTreeNode lastSelectedNode = getLastSelectedNode();
			if (lastSelectedNode == null) {
				return super.destroyWindow();
			}
			CurProjectInfo curProject = null;
			ProjectSpecialInfo projectSpecial = null;
			AdminOrgUnitInfo adminOrgUnit = null;
			CostCenterOrgUnitInfo costCenterUnit = null;
			CompanyOrgUnitInfo companyUnit = null;
			Object obj = lastSelectedNode.getUserObject();
			if (lastSelectedNode != null) {
				if (obj instanceof ProjectSpecialInfo) {
					projectSpecial = (ProjectSpecialInfo) obj;
				} else if (obj instanceof CurProjectInfo) {
					curProject = (CurProjectInfo) obj;
				} else if (obj instanceof AdminOrgUnitInfo) {
					adminOrgUnit = (AdminOrgUnitInfo) obj;
				} else if (obj instanceof CostCenterOrgUnitInfo) {
					costCenterUnit = (CostCenterOrgUnitInfo) obj;
				} else if (obj instanceof CompanyOrgUnitInfo) {
					companyUnit = (CompanyOrgUnitInfo) obj;
				} else {
					return super.destroyWindow();
				}
					
				IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
				Object[] objs = new Object[] { user, orgUnit };
				if (exits(objs)) {
					String userID = user.getId().toString();
					String orgUnitID = orgUnit.getId().toString();
					String function = getFuncation(getUIName());
					String value = null;
					if (curProject != null && projectSpecial == null) {
						value = curProject.getId().toString();
					} else if (projectSpecial != null) {
						value = projectSpecial.getId().toString();
					} else if (adminOrgUnit != null) {
						value = adminOrgUnit.getId().toString();
					} else if (costCenterUnit != null) {
						value = costCenterUnit.getId().toString();
					} else if (companyUnit != null) {
						value = companyUnit.getId().toString();
					}
					if (value != null) {
						autoRemember.save(userID, orgUnitID, function, value);
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.destroyWindow();
	}

	private String getFuncation(String function) {
		if (getUIName().equals(ViewProjectWeekReportUI.class.getName())) {
			function = "ViewProjectWeekReportProject";
		}
		if (getUIName().equals(ViewProjectMonthReportUI.class.getName())) {
			function = "ViewProjectMonthReportUI";
		}
		if (getUIName().equals(ViewCompanyMonthReportUI.class.getName())) {
			function = "ViewCompanyMonthReportUI";
		}
		if (getUIName().equals(ViewDepartMonthReportUI.class.getName())) {
			function = "ViewDepartMonthReportUI";
		}
		return function;
	}
	/**
	 * @description �����е�ֵ�Ƿ����
	 * @author �ź���
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */
	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}

	protected DefaultKingdeeTreeNode getLastSelectedNode() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		return node;
	}
	
	// TODO ���ڼƻ�ִ���Ǳߵ�����������һ,���Ի���Ҫ�޸�
	protected int getCurrWeekOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.WEEK_OF_YEAR) - 1;
	}

	protected int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}
	
	protected int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}
	
	protected void setDefalutYearSelected() throws BOSException {
		// ��ʼ���ص�ǰ�ܵ����� modify by duhongming
		String currSelectString = getCompareStr();
		
		for (int index = 0; index < cbYear.getItemCount(); index++) {
			String item = cbYear.getItemAt(index).toString();
			if (item.equals(currSelectString)) {
				cbYear.setSelectedItem(currSelectString);
				return;
			}
		}
	}
    
	private String getCompareStr() throws BOSException {
		Date currDate = FDCDateHelper.getServerTimeStamp();
		int year = OpReportBaseHelper.getDateFiled(currDate, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(currDate, Calendar.WEEK_OF_YEAR);
		int month = OpReportBaseHelper.getDateFiled(currDate, Calendar.MONTH) + 1;
		
		if (getUIName().equals(ViewProjectWeekReportUI.class.getName())) {
			return year + "-" + (week < 10 ? "0" + week : week);
		}
		return year + "-" + (month < 10 ? "0" + month : month);
	}
	
	protected String getUIName() {
		return "";
	}
	
	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}
}