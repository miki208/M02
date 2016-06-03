package M02;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/*
 * Miloš Samardžija 304/2013
 * Nikola Grulović 409/2013
 */

public class Main {
	
	static
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		        DatabaseUtils.close(con);
		    }
		});
		
	}
	
	public static void main(String[] args)
	{
		DatabaseUtils.createConnection("com.ibm.db2.jcc.DB2Driver", db_login.db_url, db_login.username, db_login.password);
		Main.ref = new Main();
		Main.ref.createGui();
	}
	
	
	
	public void createGui()
	{
		mainWindow = new JFrame("M02");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(new Dimension(500, 500));
		mainWindow.setResizable(false);
		Container cont = mainWindow.getContentPane();
		SpringLayout layout = new SpringLayout();
		cont.setLayout(layout);
		
		M2P1 = ref.createPanelP1();
		
		M2P2 = ref.createPanelP2();
		
		M2P3 = ref.createPanelP3();
		
		M2P4 = ref.createPanelP4();
		
		mainPanel = M2P1;
		cont.add(mainPanel);
		
		controlPanel = ref.createControlPanel();
		cont.add(controlPanel);
		layout.putConstraint(SpringLayout.NORTH, controlPanel, 0, SpringLayout.SOUTH, mainPanel);
		
		mainWindow.setVisible(true);
	}
	
	public JPanel createPanelP1()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		ref.setSize(panel, 500, 400);
		panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		JLabel label = new JLabel("indeks studenta:");
		indexTf = new JTextField();
		ref.setSize(indexTf, 200, 20);
		label.setLabelFor(indexTf);
		findIndexBtn = new JButton("pronađi studenta");
		panel.add(label);
		panel.add(indexTf);
		panel.add(findIndexBtn);

		layout.putConstraint(SpringLayout.NORTH, label, panel.getHeight() / 2, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, label, 50, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, indexTf, panel.getHeight() / 2, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, indexTf, 10, SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, findIndexBtn, 20, SpringLayout.SOUTH, indexTf);
		layout.putConstraint(SpringLayout.WEST, findIndexBtn, 10, SpringLayout.EAST, label);
		
		return panel;
	}
	
	public JPanel createPanelP2()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		ref.setSize(panel, 500, 400);
		
		JLabel labelIndex = new JLabel("indeks: ");
		indexDataTf = new JTextField();
		indexDataTf.setEditable(false);
		labelIndex.setLabelFor(indexDataTf);
		ref.setSize(indexDataTf, 200, 20);
		panel.add(labelIndex);
		panel.add(indexDataTf);
		layout.putConstraint(SpringLayout.NORTH, labelIndex, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, labelIndex, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, indexDataTf, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, indexDataTf, 60, SpringLayout.EAST, labelIndex);
		
		JLabel labelProsek = new JLabel("prosek: ");
		prosekDataTf = new JTextField();
		prosekDataTf.setEditable(false);
		labelProsek.setLabelFor(prosekDataTf);
		ref.setSize(prosekDataTf, 200, 20);
		panel.add(labelProsek);
		panel.add(prosekDataTf);
		layout.putConstraint(SpringLayout.NORTH, labelProsek, 15, SpringLayout.SOUTH, indexDataTf);
		layout.putConstraint(SpringLayout.WEST, labelProsek, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, prosekDataTf, 15, SpringLayout.SOUTH, indexDataTf);
		layout.putConstraint(SpringLayout.WEST, prosekDataTf, 60, SpringLayout.EAST, labelIndex);
		
		
		idSmeraDataCb = new JComboBox<>();
		idSmeraDataBtn = new JButton("izmeni");
		idSmeraDataCb.setEditable(false);
		ref.setupDataLine(layout, panel, idSmeraDataCb, idSmeraDataBtn, prosekDataTf, labelIndex, "id smera: ");
		
		imeDataTf = new JTextField();
		imeDataBtn = new JButton("izmeni");
		imeDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, imeDataTf, imeDataBtn, idSmeraDataCb, labelIndex, "ime: ");
		
		prezimeDataTf = new JTextField();
		prezimeDataBtn = new JButton("izmeni");
		prezimeDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, prezimeDataTf, prezimeDataBtn, imeDataTf, labelIndex, "prezime: ");
		
		polDataCb = new JComboBox<>();
		polDataBtn = new JButton("izmeni");
		polDataCb.setEditable(false);
		ref.setupDataLine(layout, panel, polDataCb, polDataBtn, prezimeDataTf, labelIndex, "pol: ");
		
		jmbgDataTf = new JTextField();
		jmbgDataBtn = new JButton("izmeni");
		jmbgDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, jmbgDataTf, jmbgDataBtn, polDataCb, labelIndex, "jmbg: ");
		
		datRodjDataTf = new JTextField();
		datRodjDataBtn = new JButton("izmeni");
		datRodjDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, datRodjDataTf, datRodjDataBtn, jmbgDataTf, labelIndex, "datum rođenja: ");
		
		mestoRodjDataTf = new JTextField();
		mestoRodjDataBtn = new JButton("izmeni");
		mestoRodjDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, mestoRodjDataTf, mestoRodjDataBtn, datRodjDataTf, labelIndex, "mesto rođenja: ");
		
		drzavaRodjDataTf = new JTextField();
		drzavaRodjDataBtn = new JButton("izmeni");
		drzavaRodjDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, drzavaRodjDataTf, drzavaRodjDataBtn, mestoRodjDataTf, labelIndex, "država rođenja: ");
		
		return panel;
	}
	
	public JPanel createPanelP3()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		ref.setSize(panel, 500, 400);
		
		imeOcaDataTf = new JTextField();
		imeOcaDataBtn = new JButton("izmeni");
		imeOcaDataTf.setEditable(false);		
		JLabel imeOcaLabel = new JLabel("ime oca: ");
		imeOcaLabel.setLabelFor(imeOcaDataTf);
		ref.setSize(imeOcaDataTf, 200, 20);
		panel.add(imeOcaLabel);
		panel.add(imeOcaDataTf);
		panel.add(imeOcaDataBtn);
		layout.putConstraint(SpringLayout.NORTH, imeOcaLabel, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imeOcaLabel, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, imeOcaDataTf, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imeOcaDataTf, 75, SpringLayout.EAST, imeOcaLabel);
		layout.putConstraint(SpringLayout.NORTH, imeOcaDataBtn, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, imeOcaDataBtn, -10, SpringLayout.EAST, panel);
		
		imeMajkeDataTf = new JTextField();
		imeMajkeDataBtn = new JButton("izmeni");
		imeMajkeDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, imeMajkeDataTf, imeMajkeDataBtn, imeOcaDataTf, imeOcaLabel, "ime majke: ");
		
		ulicaDataTf = new JTextField();
		ulicaDataBtn = new JButton("izmeni");
		ulicaDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, ulicaDataTf, ulicaDataBtn, imeMajkeDataTf, imeOcaLabel, "ulica stanovanja:");
		
		kucniBrDataTf = new JTextField();
		kucniBrDataBtn = new JButton("izmeni");
		kucniBrDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, kucniBrDataTf, kucniBrDataBtn, ulicaDataTf, imeOcaLabel, "kućni broj: ");
		
		mestoStanDataTf = new JTextField();
		mestoStanDataBtn = new JButton("izmeni");
		mestoStanDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, mestoStanDataTf, mestoStanDataBtn, kucniBrDataTf, imeOcaLabel, "mesto stanovanja: ");
		
		postBrDataTf = new JTextField();
		postBrDataBtn = new JButton("izmeni");
		postBrDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, postBrDataTf, postBrDataBtn, mestoStanDataTf, imeOcaLabel, "poštanski broj: ");
		
		drzavaStanDataTf = new JTextField();
		drzavaStanDataBtn = new JButton("izmeni");
		drzavaStanDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, drzavaStanDataTf, drzavaStanDataBtn, postBrDataTf, imeOcaLabel, "država stanovanja: ");
		
		telefonDataTf = new JTextField();
		telefonDataBtn = new JButton("izmeni");
		telefonDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, telefonDataTf, telefonDataBtn, drzavaStanDataTf, imeOcaLabel, "telefon: ");
		
		mobilniDataTf = new JTextField();
		mobilniDataBtn = new JButton("izmeni");
		mobilniDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, mobilniDataTf, mobilniDataBtn, telefonDataTf, imeOcaLabel, "mobilni telefon: ");
		
		emailDataTf = new JTextField();
		emailDataBtn = new JButton("izmeni");
		emailDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, emailDataTf, emailDataBtn, mobilniDataTf, imeOcaLabel, "email: ");
		
		return panel;
	}
	
	public JPanel createPanelP4()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		ref.setSize(panel, 500, 400);
		
		urlDataTf = new JTextField();
		urlDataBtn = new JButton("izmeni");
		urlDataTf.setEditable(false);		
		JLabel urlLabel = new JLabel("www uri: ");
		urlLabel.setLabelFor(urlDataTf);
		ref.setSize(urlDataTf, 200, 20);
		panel.add(urlLabel);
		panel.add(urlDataTf);
		panel.add(urlDataBtn);
		layout.putConstraint(SpringLayout.NORTH, urlLabel, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, urlLabel, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, urlDataTf, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, urlDataTf, 75, SpringLayout.EAST, urlLabel);
		layout.putConstraint(SpringLayout.NORTH, urlDataBtn, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, urlDataBtn, -10, SpringLayout.EAST, panel);
		
		datumUpisaDataTf = new JTextField();
		datumUpisaDataBtn = new JButton("izmeni");
		datumUpisaDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, datumUpisaDataTf, datumUpisaDataBtn, urlDataTf, urlLabel, "datum upisa: ");
		
		return panel;
	}
	
	public JPanel createControlPanel()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		ref.setSize(panel, 500, 100);
		panel.setVisible(false);
		
		prevBtn = new JButton("prethodna");
		updateBtn = new JButton("ažuriraj");
		nextBtn = new JButton("sledeća");
		prevBtn.setEnabled(false);
		updateBtn.setEnabled(false);
		
		panel.add(prevBtn);
		panel.add(nextBtn);
		panel.add(updateBtn);
		
		layout.putConstraint(SpringLayout.NORTH, prevBtn, 20, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.NORTH, nextBtn, 20, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.NORTH, updateBtn, 20, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, prevBtn, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, nextBtn, 10, SpringLayout.EAST, prevBtn);
		layout.putConstraint(SpringLayout.EAST, updateBtn, -10, SpringLayout.EAST, panel);
		
		return panel;
	}
	
	public void setupDataLine(SpringLayout layout, JPanel panel, Component comp, JButton btn, Component position, JLabel position1, String name)
	{
		JLabel label = new JLabel(name);
		label.setLabelFor(comp);
		ref.setSize(comp, 200, 20);
		panel.add(label);
		panel.add(comp);
		panel.add(btn);
		layout.putConstraint(SpringLayout.NORTH, label, 15, SpringLayout.SOUTH, position);
		layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, comp, 15, SpringLayout.SOUTH, position);
		layout.putConstraint(SpringLayout.WEST, comp, 75, SpringLayout.EAST, position1);
		layout.putConstraint(SpringLayout.NORTH, btn, 15, SpringLayout.SOUTH, position);
		layout.putConstraint(SpringLayout.EAST, btn, -10, SpringLayout.EAST, panel);
	}
	
	public void setSize(Component a, int width, int heigth)
	{
		a.setPreferredSize(new Dimension(width, heigth));
		a.setSize(width, heigth);
		a.setMaximumSize(new Dimension(width, heigth));
	}
	
	public static Connection con = null;
	
	public static Main ref;
	private JFrame mainWindow;
	private JPanel mainPanel;
	
	private JPanel controlPanel;
	private JButton prevBtn;
	private JButton updateBtn;
	private JButton nextBtn;
	
	private JPanel M2P1;
	private JTextField indexTf;
	private JButton findIndexBtn;
	
	private JPanel M2P2;
	private JTextField indexDataTf;
	private JTextField prosekDataTf;
	private JComboBox<Integer> idSmeraDataCb;
	private JButton idSmeraDataBtn;
	private JTextField imeDataTf;
	private JButton imeDataBtn;
	private JTextField prezimeDataTf;
	private JButton prezimeDataBtn;
	private JComboBox<String> polDataCb;
	private JButton polDataBtn;
	private JTextField jmbgDataTf;
	private JButton jmbgDataBtn;
	private JTextField datRodjDataTf;
	private JButton datRodjDataBtn;
	private JTextField mestoRodjDataTf;
	private JButton mestoRodjDataBtn;
	private JTextField drzavaRodjDataTf;
	private JButton drzavaRodjDataBtn;
	private JTextField urlDataTf;
	private JButton urlDataBtn;
	private JTextField datumUpisaDataTf;
	private JButton datumUpisaDataBtn;
	
	private JPanel M2P3;
	private JTextField imeOcaDataTf;
	private JButton imeOcaDataBtn;
	private JTextField imeMajkeDataTf;
	private JButton imeMajkeDataBtn;
	private JTextField ulicaDataTf;
	private JButton ulicaDataBtn;
	private JTextField kucniBrDataTf;
	private JButton kucniBrDataBtn;
	private JTextField mestoStanDataTf;
	private JButton mestoStanDataBtn;
	private JTextField postBrDataTf;
	private JButton postBrDataBtn;
	private JTextField drzavaStanDataTf;
	private JButton drzavaStanDataBtn;
	private JTextField telefonDataTf;
	private JButton telefonDataBtn;
	private JTextField mobilniDataTf;
	private JButton mobilniDataBtn;
	private JTextField emailDataTf;
	private JButton emailDataBtn;
	
	private JPanel M2P4;
}
