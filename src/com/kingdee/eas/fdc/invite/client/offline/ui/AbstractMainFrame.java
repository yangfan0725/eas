package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuBar;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.eas.fdc.invite.client.offline.util.ObjectUtil;
import com.kingdee.eas.fdc.invite.client.offline.util.ResourceHelper;

/**
 * �ͻ��˽��������
 *
 * @author jianxing_zhou 2007-9-3
 */
public abstract class AbstractMainFrame extends JFrame {

	private static Logger logger = Logger.getLogger(AbstractMainFrame.class);

	protected Map context = Collections.synchronizedMap(new HashMap(5));

	protected JTabbedPane tabbedPane = new KDTabbedPane();

	protected JMenuBar menuBar;

	protected JToolBar toolBar;

	// /////////////////////////////////////////////////////////
	protected JMenu fileMenu;
	
	//20101014 zhougang
	protected JMenu operationMenu;
	protected JMenu tableMenu;
	//20101014 zhougang

	protected JMenu setMenu;

	protected JMenu helpMenu;

	// //////////////////////////////////////////////////////////
	protected JMenuItem importMenuItem;

	protected JMenuItem exportMenuItem;
	
	protected JMenuItem importExcelMenuItem;
	protected JMenuItem exportExcelMenuItem;

	protected JMenuItem saveMenuItem;
	
	//20101014 zhougang
	protected JMenuItem addLineMenuItem;
	protected JMenuItem insertLineMenuItem;
	protected JMenuItem removeLineMenuItem;
	protected JMenuItem upLineMenuItem;
	protected JMenuItem downLineMenuItem;
	//20101014 zhougang

	protected JMenuItem prePrintMenuItem;

	protected JMenuItem printMenuItem;

	protected JMenuItem exitMenuItem;

	protected JMenuItem helpMenuItem;

	protected JMenuItem aboutMenuItem;

	protected JCheckBoxMenuItem showWelcome;

	protected JMenuItem showLogMenuItem; // �鿴��־

	// //////////////////////////////////////////////////////////

	protected JButton importButton;

	protected JButton exportButton;
	
	protected JButton importExcelButton;
	protected JButton exportExcelButton;

	protected JButton saveButton;
	
	//20101014 zhougang
	protected JButton addLineButton;
	protected JButton insertLineButton;
	protected JButton removeLineButton;
	protected JButton upLineButton;
	protected JButton downLineButton;
	//20101014 zhougang

	protected JButton printButton;

	protected JButton prePrintButton;

	protected JButton exitButton;

	// ����������ֵ
	protected boolean hasZero = false;
	// ��������
	public static final String Action_exprot = "export";

	// //////////////////////////////////////
	// ״̬��
	// JPanel statusBar=new JPanel();
	// JLabel status=new JLabel("���ز��������߿ͻ���");
	StatusBar statusBar = new StatusBar();

	// ///////////////////////

	private boolean isShow = true ? Preferences.userRoot().getBoolean("isShow", true) : true;

	// ////

	final int maxFileCount = 5;

	String[] fileItems = new String[maxFileCount];

	// �Ƿ񱣴��
	boolean isSaved = true;

	public AbstractMainFrame() {
		super();
		init();
	}

	public AbstractMainFrame(String title) {
		super(title);
		init();
	}

	protected void init() {
		logger.info("���ڳ�ʼ��......");
		this.setSize(720, 540);
		setLayout();
		createMenuBar();
		createMenu();
		createMenuItem();
		createToolBar();
		createToolButton();
		addListener();
		setIconImage(ResourceHelper.getImage("eas_32.gif"));
		ObjectUtil.setEnabled(new Object[] { exportMenuItem,
				exportExcelMenuItem, importExcelMenuItem, printMenuItem,
				prePrintMenuItem, saveMenuItem, addLineMenuItem,
				insertLineMenuItem, removeLineMenuItem, upLineMenuItem,
				downLineMenuItem, exportButton, exportExcelButton,
				importExcelButton, printButton, prePrintButton, saveButton,
				addLineButton, insertLineButton, removeLineButton,
				upLineButton, downLineButton }, false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		tabbedPane.setBorder(new LineBorder(Color.BLACK));
		initStatusBar();
		setAccelerator();
		setRecentFiles();
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tabbedPaneChange();
			}
		});
		logger.info("��ʼ�����!");
	}

	protected void setLayout() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

	protected void createMenuBar() {
		menuBar = new KDMenuBar();
		getRootPane().setJMenuBar(menuBar);
	}

	protected void createMenu() {
		fileMenu = new KDMenu("�ļ�(F)", true);
		fileMenu.setMnemonic('f');
		
		//20101014 zhougang
		operationMenu = new KDMenu("ҵ��(O)", true);
		operationMenu.setMnemonic('o');
		tableMenu = new KDMenu("���(A)", true);
		tableMenu.setMnemonic('a');
		//20101014 zhougang
		
		setMenu = new KDMenu("��ӡ(P)");
		setMenu.setMnemonic('p');
		helpMenu = new KDMenu("����(H)");
		helpMenu.setMnemonic('h');

		menuBar.add(fileMenu);
		
		//20101014 zhougang
		menuBar.add(operationMenu);
		menuBar.add(tableMenu);
		//20101014 zhougang
		
		menuBar.add(setMenu);
		menuBar.add(helpMenu);
	}

	protected void createMenuItem() {
		importMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_Input.gif"));
		importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
		importMenuItem.setToolTipText("��ѹ���ļ��е�����");

		exportMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_Output.gif"));
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		exportMenuItem.setToolTipText("�ѱ�񵼳���ѹ���ļ�");
		
		importExcelMenuItem = new KDMenuItem("����EXCEL", ResourceHelper.getIcon("tbtn_importexcel.gif"));
		importExcelMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
		importExcelMenuItem.setToolTipText("��Excel�ļ��е�����");

		exportExcelMenuItem = new KDMenuItem("������EXCEL", ResourceHelper.getIcon("tbtn_Output.gif"));
		exportExcelMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
		exportExcelMenuItem.setToolTipText("����ȫ��ҳǩ��EXCEL�ļ���");

		saveMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_Save.gif"));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));

		//20101014 zhougang
		addLineMenuItem = new KDMenuItem("������¼", ResourceHelper.getIcon("tbtn_addline.gif"));
		addLineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK + Event.SHIFT_MASK));

		insertLineMenuItem = new KDMenuItem("�����¼", ResourceHelper.getIcon("tbtn_Insert.gif"));
		logger.info("���� create�����¼ ......");
		insertLineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK + Event.SHIFT_MASK));

		removeLineMenuItem = new KDMenuItem("ɾ����¼", ResourceHelper.getIcon("tbtn_deleteline.gif"));
		removeLineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK + Event.SHIFT_MASK));

		upLineMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_upgrade.gif"));
		upLineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK + Event.SHIFT_MASK));

		downLineMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_degrade.gif"));
		downLineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK + Event.SHIFT_MASK));
		//20101014 zhougang

		prePrintMenuItem = new KDMenuItem("��ӡ����", ResourceHelper.getIcon("tbtn_Preview.gif"));
		prePrintMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

		printMenuItem = new KDMenuItem("������ӡ", ResourceHelper.getIcon("tbtn_Print.gif"));
		printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));

		exitMenuItem = new KDMenuItem("�˳�", ResourceHelper.getIcon("tbtn_quit.gif"));
		exitMenuItem.setToolTipText("�˳�Ӧ�ó���");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK));

		helpMenuItem = new KDMenuItem("����", ResourceHelper.getIcon("tbtn_help.gif"));
		aboutMenuItem = new KDMenuItem("����(A)");
		showWelcome = new JCheckBoxMenuItem("��ʾ��������", isShow);
		showLogMenuItem = new KDMenuItem("�鿴��־");
		showLogMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));

		// �ļ�
		fileMenu.add(importMenuItem);
		fileMenu.add(exportMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(importExcelMenuItem);
		fileMenu.add(exportExcelMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		// fileMenu.add(exitMenuItem);
		
		//20101014 zhougang
		//ҵ��
		operationMenu.add(upLineMenuItem);
		operationMenu.add(downLineMenuItem);
		
		//���
		tableMenu.add(addLineMenuItem);
		tableMenu.add(insertLineMenuItem);
		tableMenu.add(removeLineMenuItem);
		//20101014 zhougang
		
		// ��ӡ
		setMenu.add(prePrintMenuItem);
		setMenu.add(printMenuItem);
		// ����
		helpMenu.add(showLogMenuItem);
		helpMenu.add(showWelcome);
		helpMenu.add(helpMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutMenuItem);

		String version = System.getProperty("java.specification.version");
		if (version != null) {
			if (version.indexOf("1.4") > -1)
				showLogMenuItem.setVisible(false);
			logger.info("java verson is " + version);
		}

	}

	protected void createToolBar() {
		toolBar = new KDToolBar();
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	protected void createToolButton() {
		importButton = new KDWorkButton("����", ResourceHelper.getIcon("tbtn_Input.gif"));
		exportButton = new KDWorkButton("����", ResourceHelper.getIcon("tbtn_Output.gif"));
		importExcelButton = new KDWorkButton("����Excel", ResourceHelper.getIcon("tbtn_importexcel.gif"));
		exportExcelButton = new KDWorkButton("������Excel", ResourceHelper.getIcon("tbtn_Output.gif"));
		saveButton = new KDWorkButton("����", ResourceHelper.getIcon("tbtn_Save.gif"));
		
		//20101014 zhougang
		addLineButton = new KDWorkButton("������¼", ResourceHelper.getIcon("tbtn_addline.gif"));
		insertLineButton = new KDWorkButton("�����¼", ResourceHelper.getIcon("tbtn_Insert.gif"));
		removeLineButton = new KDWorkButton("ɾ����¼", ResourceHelper.getIcon("tbtn_deleteline.gif"));
		upLineButton = new KDWorkButton("����", ResourceHelper.getIcon("tbtn_upgrade.gif"));
		downLineButton = new KDWorkButton("����", ResourceHelper.getIcon("tbtn_degrade.gif"));
		//20101014 zhougang
		
		prePrintButton = new KDWorkButton("��ӡ����", ResourceHelper.getIcon("tbtn_Preview.gif"));
		printButton = new KDWorkButton("������ӡ", ResourceHelper.getIcon("tbtn_Print.gif"));
		exitButton = new KDWorkButton("�˳�", ResourceHelper.getIcon("tbtn_quit.gif"));
		toolBar.add(importButton);
		toolBar.add(exportButton);
		toolBar.add(importExcelButton);
		toolBar.add(exportExcelButton);
		toolBar.addSeparator();
		toolBar.add(saveButton);
		
		//20101014 zhougang
		toolBar.addSeparator();
		toolBar.add(addLineButton);
		toolBar.add(insertLineButton);
		toolBar.add(removeLineButton);
		toolBar.addSeparator();
		toolBar.add(upLineButton);
		toolBar.add(downLineButton);
		//20101014 zhougang
		
		toolBar.addSeparator();
		toolBar.add(prePrintButton);
		toolBar.add(printButton);
		toolBar.addSeparator();
		toolBar.add(exitButton);

	}

	protected void addListener() {

		importMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_import"));
		exportMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_export"));		
		importExcelMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_importexcel"));
		exportExcelMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_exportexcel"));
		prePrintMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "printPreview"));
		printMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "print"));
		saveMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "save"));

		//20101014 zhougang
		addLineMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "addLine"));
		insertLineMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "insertLine"));
		removeLineMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "removeLine"));
		upLineMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "upLine"));
		downLineMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "downLine"));
		//20101014 zhougang
		
		exitMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_exit"));
		aboutMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "about"));
		helpMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "help"));

		importButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_import"));
		exportButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_export"));
		importExcelButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_importexcel"));
		exportExcelButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_exportexcel"));
		prePrintButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "printPreview"));
		printButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "print"));
		saveButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "save"));
		
		//20101014 zhougang
		addLineButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "addLine"));
		insertLineButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "insertLine"));
		removeLineButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "removeLine"));
		upLineButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "upLine"));
		downLineButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "downLine"));
		//20101014 zhougang
		
		exitButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "_exit"));
		showLogMenuItem.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "showLog"));
		showWelcome.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "showWelcome"));

	}

	protected void initStatusBar() {
		this.getContentPane().add(statusBar.getStatusPane(), BorderLayout.SOUTH);
		statusBar.setRightInfo(/* "<html><font color='red'>"+ */"���ز��������߿ͻ���"/* +"</font></html>" */);
		statusBar.setBackground(new ColorUIResource(0x62, 0x93, 0xBB));
		statusBar.setForeground(Color.WHITE);
		statusBar.setLeftForeground(Color.YELLOW);
		// statusBar.setLeftInfo("��Һ�");
		// statusBar.showTip("���");

	}

	private void setAccelerator() {
		// Action action = new AbstractAction() {
		//
		// public void actionPerformed(ActionEvent e) {
		// System.out.println("kao");
		// }
		// };
		// action.putValue(Action.ACCELERATOR_KEY,
		// KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
		// tabbedPane.getActionMap().put(action.getValue(Action.NAME),
		// action);// .addActionListener(action);
	}

	public final void _import() {
		logger.info("���뱨�۱�");
		if (beforeImport())
			if (importFile())
				afterImport();
	}

	public final void _export() {
		logger.info("�������۱�");
		if (beforeExport())
			if (exportFile())
				afterExport();
//		if (beforeExport()) {
//			if (/* context.containsKey(Action_exprot) && */hasZero) {
//				// context.remove(Action_exprot);
//				hasZero = false;
//				JOptionPane.showConfirmDialog(this, "����������ֵֻ�ܱ��棬��������", "������ʾ", JOptionPane.DEFAULT_OPTION);
//			} else if (exportFile()) {
//				afterExport();
//			}
//		}
	}
	public final void _importexcel() {
		logger.info("����Excel�ļ�");
		if (beforeImportExcel())
			if (importFileExcel())
				afterImportExcel();
	}
	
	public final void _exportexcel() {
		logger.info("����ȫ��ҳǩ��Excel�ļ�");
		if (beforeExportExcel())
			if (exportExcel())
				afterExportExcel();
	}

	//20101014 zhougang
	public abstract void addLine();
	public abstract void insertLine();
	public abstract void removeLine();
	public abstract void upLine();
	public abstract void downLine();
	//20101014 zhougang

	public abstract void save();

	protected boolean beforeImport() {
		return true;
	}

	abstract protected boolean importFile();

	protected void afterImport() {
	}
	
	protected boolean beforeImportExcel() {
		return true;
	}
	protected boolean beforeExportExcel() {
		return true;
	}
	abstract protected boolean importFileExcel();
	abstract protected boolean exportExcel();

	protected void afterImportExcel() {
	}
	protected void afterExportExcel() {
	}

	protected boolean beforeExport() {
		return true;
	}

	abstract protected boolean exportFile();

	protected void afterExport() {
	}

	abstract public void print();

	abstract public void printPreview();

	public final void _exit() {
		Preferences preferences = Preferences.userRoot();
		preferences.putBoolean("isShow", isShow);
		exit();
	}

	abstract protected void exit();

	public void about() {
	}

	public void help(String url) {
		help("http://192.168.19.5:6888/eashelp/zh_cn/eashelp.htm#eas/kingdeeeas.htm");
//		openUrl("explorer", url);
	}

	abstract protected void tabbedPaneChange();

	public static void openUrl(String cmd, String url) {
		try {
			Runtime.getRuntime().exec(cmd + " " + url);
		} catch (IOException ioe) {
			logger.error(ioe);
		}
	}

	public final void showLog() {
		openUrl("notepad", "offline.log");
	}

	public final void showWelcome() {
		final WaitWindow wait = new WaitWindow();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int x;

			public void run() {
				if (x++ > 3) {
					wait.dispose();
					timer.cancel();
				}
			}
		}, 0, 1000);
		isShow = showWelcome.getState();
	}

	private void setRecentFiles() {
		Preferences preferences = Preferences.userRoot();
		for (int i = 0; i < maxFileCount; i++) {
			fileItems[i] = preferences.get("fileItem" + i, null);
		}
		FileItemAction fileItemAction = new FileItemAction();
		boolean isExist = false;
		int x=0;
		for (int i = 0; i < maxFileCount; i++) {
			String filename = fileItems[i];
			if (filename != null /*&& new File(filename).exists()*/) {
				JMenuItem item = new JMenuItem(getFileName(filename));
				fileMenu.add(item);
				fileItemAction.filePath.put(item, filename);
				item.addActionListener(fileItemAction);
				item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1 + x, Event.CTRL_MASK));
				isExist = true;
				x++;
			}
		}
		if (isExist)
			fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
	}

	private String getFileName(String filename) {
		int sep = filename.lastIndexOf('\\');
		if (sep <= 0)
			return filename;
		String name = filename.substring(sep + 1, filename.lastIndexOf('.'));
		String path = filename.substring(0, sep);
		String result = name + "  [" + path + "]";
		if (result.length() > 28)
			result = result.substring(0, 25) + "...]";
		return result;
	}

	class FileItemAction implements ActionListener {

		Map filePath = new HashMap(5);

		public void actionPerformed(ActionEvent e) {
			if (!isSaved) {
				if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null, "��ǰ����δ���棬ȷ�������±�����", "����ǰ��ʾ", JOptionPane.OK_CANCEL_OPTION)) {
					return;
				}
			}
			String file = (String) filePath.get(e.getSource());
			context.put("zipFile", new File(file));
			if (importFile())
				afterImport();
		}

	}
}
