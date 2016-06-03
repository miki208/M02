package M02;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

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
		con = DatabaseUtils.createConnection("com.ibm.db2.jcc.DB2Driver", db_login.db_url, db_login.username, db_login.password);
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
		
		mainPanel = new JPanel(new CardLayout());
		mainPanel.add(M2P1, P1);
		mainPanel.add(M2P2, P2);
		mainPanel.add(M2P3, P3);
		mainPanel.add(M2P4, P4);
		((CardLayout) mainPanel.getLayout()).show(mainPanel, P1);
		currentPanel = P1;
		
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
		
		findIndexBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try
				{
					Scanner sc = new Scanner(indexTf.getText());
					if(sc.hasNextInt())
					{	
						PreparedStatement stmt = con.prepareStatement(selectSql);
						int index = sc.nextInt();
						stmt.setInt(1, index);
						ResultSet result = stmt.executeQuery();
						if(!result.next())
							JOptionPane.showMessageDialog(null, "Student sa navedenim indeksom\nne postoji na master studijama.");
						else
						{
							PreparedStatement stmt1 = con.prepareStatement(prosekSql);
							stmt1.setInt(1, index);
							ResultSet result1 = stmt1.executeQuery();
							result1.next();
							dosije.prosek = result1.getDouble(1);
							prosekDataTf.setText(Double.toString(Math.round(dosije.prosek * 100) / 100.0));
							result1.close();
							stmt1.close();
							
							dosije.indeks = result.getInt(1);
							indexDataTf.setText(Integer.toString(dosije.indeks));
							
							dosije.id_smera = result.getInt(2);
							idSmeraDataCb.setSelectedItem(dosije.id_smera);
							
							dosije.ime = result.getString(3);
							imeDataTf.setText(dosije.ime);
							
							dosije.prezime = result.getString(4);
							prezimeDataTf.setText(dosije.prezime);
							
							dosije.pol = result.getString(5);
							if(dosije.pol == "m")
								polDataCb.setSelectedIndex(0);
							else
								polDataCb.setSelectedIndex(1);
							
							dosije.jmbg = result.getString(6);
							jmbgDataTf.setText(dosije.jmbg);
							
							dosije.datum_rodjenja = result.getDate(7);
							if(!result.wasNull())
							{
								Calendar cal = Calendar.getInstance();
								cal.setTime(dosije.datum_rodjenja);
								datRodjDataTf.setText(cal.get(Calendar.DATE) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR));
							}
							else
							{
								datRodjDataTf.setText("nepoznato");
							}
							
							dosije.mesto_rodjenja = result.getString(8);
							if(!result.wasNull())
								mestoRodjDataTf.setText(dosije.mesto_rodjenja);
							else
								mestoRodjDataTf.setText("nepoznato");
							
							dosije.drzava_rodjenja = result.getString(9);
							if(!result.wasNull())
								drzavaRodjDataTf.setText(dosije.drzava_rodjenja);
							else
								drzavaRodjDataTf.setText("nepoznato");
							
							dosije.ime_oca = result.getString(10);
							if(!result.wasNull())
								imeOcaDataTf.setText(dosije.ime_oca);
							else
								imeOcaDataTf.setText("nepoznato");
							
							dosije.ime_majke = result.getString(11);
							if(!result.wasNull())
								imeMajkeDataTf.setText(dosije.ime_majke);
							else
								imeMajkeDataTf.setText("nepoznato");
							
							dosije.ulica_stanovanja = result.getString(12);
							if(!result.wasNull())
								ulicaDataTf.setText(dosije.ulica_stanovanja);
							else
								ulicaDataTf.setText("nepoznato");
							
							dosije.kucni_broj = result.getString(13);
							if(!result.wasNull())
								kucniBrDataTf.setText(dosije.kucni_broj);
							else
								kucniBrDataTf.setText("nepoznato");
							
							dosije.mesto_stanovanja = result.getString(14);
							if(!result.wasNull())
								mestoStanDataTf.setText(dosije.mesto_stanovanja);
							else
								mestoStanDataTf.setText("nepoznato");
							
							dosije.postanski_broj = result.getString(15);
							if(!result.wasNull())
								postBrDataTf.setText(dosije.postanski_broj);
							else
								postBrDataTf.setText("nepoznato");
							
							dosije.drzava_stanovanja = result.getString(16);
							if(!result.wasNull())
								drzavaStanDataTf.setText(dosije.drzava_stanovanja);
							else
								drzavaStanDataTf.setText("nepoznato");
							
							dosije.telefon = result.getString(17);
							if(!result.wasNull())
								telefonDataTf.setText(dosije.telefon);
							else
								telefonDataTf.setText("nepoznato");
							
							dosije.mobilni_telefon = result.getString(18);
							if(!result.wasNull())
								mobilniDataTf.setText(dosije.mobilni_telefon);
							else
								mobilniDataTf.setText("nepoznato");
							
							dosije.email = result.getString(19);
							if(!result.wasNull())
								emailDataTf.setText(dosije.email);
							else
								emailDataTf.setText("nepoznato");
							
							dosije.www_uri = result.getString(20);
							if(!result.wasNull())
								urlDataTf.setText(dosije.www_uri);
							else
								urlDataTf.setText("nepoznato");
							
							dosije.datum_upisa = result.getDate(21);
							Calendar cal = Calendar.getInstance();
							cal.setTime(dosije.datum_upisa);
							datumUpisaDataTf.setText(cal.get(Calendar.DATE) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR));
							
							((CardLayout) mainPanel.getLayout()).show(mainPanel, P2);
							currentPanel = P2;
							prevBtn.setEnabled(false);
							controlPanel.setVisible(true);
						}
						result.close();
						stmt.close();
					}
					sc.close();
				}
				catch(SQLException e1)
				{
					DatabaseUtils.errorHandler(e1);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
				
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
		layout.putConstraint(SpringLayout.WEST, indexDataTf, 75, SpringLayout.EAST, labelIndex);
		
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
		layout.putConstraint(SpringLayout.WEST, prosekDataTf, 75, SpringLayout.EAST, labelIndex);
		
		idSmeraDataModel = new Vector<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(
					  "SELECT	id_smera "
					+ "FROM		smer s "
					+ "	JOIN		nivo_kvalifikacije nk "
					+ "	ON 		nk.id_nivoa = s.id_nivoa "
					+ "WHERE	nk.naziv = 'Master akademske studije' "
					+ "ORDER BY id_smera");
			
			while(result.next())
			{
				int id_smera = result.getInt(1);
				idSmeraDataModel.add(id_smera);
			}
			
			result.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtils.errorHandler(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		idSmeraDataCb = new JComboBox<>(idSmeraDataModel);
		idSmeraDataBtn = new JButton("izmeni");
		idSmeraDataBtn.setVisible(false);
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
		
		polDataModel = new Vector<>();
		polDataModel.add("muško");
		polDataModel.add("žensko");
		polDataCb = new JComboBox<>(polDataModel);
		polDataBtn = new JButton("izmeni");
		polDataBtn.setVisible(false);
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
		imeOcaDataBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(imeOcaDataBtn.getText() == "izmeni")
				{
					imeOcaDataBtn.setText("sačuvaj");
					imeOcaDataTf.setEditable(true);
					toUpdate++;
				}
				else
				{
					if(DataValidator.validate("ime oca: "))
					{
						imeOcaDataTf.setEditable(false);
						imeOcaDataBtn.setText("izmeni");
						toUpdate--;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Neispravan unos");
					}
				}
			}
		});
		
		imeMajkeDataTf = new JTextField();
		imeMajkeDataBtn = new JButton("izmeni");
		imeMajkeDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, imeMajkeDataTf, imeMajkeDataBtn, imeOcaDataTf, imeOcaLabel, "ime majke: ");
		
		ulicaDataTf = new JTextField();
		ulicaDataBtn = new JButton("izmeni");
		ulicaDataTf.setEditable(false);
		ref.setupDataLine(layout, panel, ulicaDataTf, ulicaDataBtn, imeMajkeDataTf, imeOcaLabel, "ulica stanovanja: ");
		
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
		urlDataBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(urlDataBtn.getText() == "izmeni")
				{
					urlDataBtn.setText("sačuvaj");
					urlDataTf.setEditable(true);
					toUpdate++;
				}
				else
				{
					if(DataValidator.validate("www uri: "))
					{
						urlDataTf.setEditable(false);
						urlDataBtn.setText("izmeni");
						toUpdate--;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Neispravan unos");
					}
				}
			}
		});
		
		
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
		prevBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(currentPanel == P3)
				{
					((CardLayout) mainPanel.getLayout()).show(mainPanel, P2);
					prevBtn.setEnabled(false);
					currentPanel = P2;
				}
				else if(currentPanel == P4)
				{
					((CardLayout) mainPanel.getLayout()).show(mainPanel, P3);
					nextBtn.setEnabled(true);
					currentPanel = P3;
				}
			}
		});
		updateBtn = new JButton("ažuriraj");
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(toUpdate != 0)
					JOptionPane.showMessageDialog(null, "Postoje nesačuvana polja!");
				else
				{
					//update
				}
			}
		});
		nextBtn = new JButton("sledeća");
		nextBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentPanel == P2)
				{
					((CardLayout) mainPanel.getLayout()).show(mainPanel, P3);
					prevBtn.setEnabled(true);
					currentPanel = P3;
				}
				else if(currentPanel == P3)
				{
					((CardLayout) mainPanel.getLayout()).show(mainPanel, P4);
					nextBtn.setEnabled(false);
					currentPanel = P4;
				}
			}
		});
		prevBtn.setEnabled(false);
		
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
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(((JButton) e.getSource()).getText() == "izmeni")
				{
					((JButton) e.getSource()).setText("sačuvaj");
					if(comp.getClass() == JTextField.class)
					{
						((JTextField) comp).setEditable(true);
						((JTextField) comp).requestFocus();
					}
					toUpdate++;
				}
				else
				{
					if(DataValidator.validate(name))
					{
						((JButton) e.getSource()).setText("izmeni");
						if(comp.getClass() == JTextField.class)
							((JTextField) comp).setEditable(false);
						toUpdate--;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Neispravan unos!");
					}
				}
			}
		});
	}
	
	public void setSize(Component a, int width, int heigth)
	{
		a.setPreferredSize(new Dimension(width, heigth));
		a.setSize(width, heigth);
		a.setMaximumSize(new Dimension(width, heigth));
	}
	
	public static Connection con = null;
	public static String prosekSql = 
			  "SELECT	COALESCE(AVG(1.0 * ocena), 0) AS prosek "
			+ "FROM		ispit i "
			+ "WHERE	indeks = ? "
			+ "	AND		ocena > 5 "
			+ " AND		status_prijave = 'o' ";
	public static String selectSql = 
			  "SELECT	indeks, "
			+ "			d.id_smera, "
			+ "			TRIM(ime), "
			+ "			TRIM(prezime), "
			+ "			pol, "
			+ "			jmbg, "
			+ "			datum_rodjenja, "
			+ "			TRIM(mesto_rodjenja), "
			+ "			TRIM(drzava_rodjenja), "
			+ "			TRIM(ime_oca), "
			+ "			TRIM(ime_majke), "
			+ "			TRIM(ulica_stanovanja), "
			+ "			kucni_broj, "
			+ "			TRIM(mesto_stanovanja), "
			+ "			postanski_broj, "
			+ "			TRIM(drzava_stanovanja), "
			+ "			telefon, "
			+ "			mobilni_telefon, "
			+ "			TRIM(email), "
			+ "			TRIM(\"www uri\"), "
			+ "			datum_upisa "
			+ "FROM		dosije d "
			+ "	JOIN	smer s "
			+ "		ON	s.id_smera = d.id_smera "
			+ "	JOIN	nivo_kvalifikacije nk "
			+ "		ON	nk.id_nivoa = s.id_nivoa "
			+ "WHERE	indeks = ? "
			+ "	AND		nk.naziv = 'Master akademske studije'";
	
	public final static String P1 = "P1";
	public final static String P2 = "P2";
	public final static String P3 = "P3";
	public final static String P4 = "P4";
	
	public static Main ref;
	private JFrame mainWindow;
	private JPanel mainPanel;
	public static String currentPanel;
	public static int toUpdate = 0;
	
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
	private JTextField urlDataTf;
	private JButton urlDataBtn;
	private JTextField datumUpisaDataTf;
	private JButton datumUpisaDataBtn;
	
	Vector<String> polDataModel;
	Vector<Integer> idSmeraDataModel;
}
