package M02;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataValidator {
	public static boolean validate(String name)
	{
		switch(name)
		{
			case "ime: ":
				if(isSizeCorrect(Main.imeDataTf.getText(), 1, 25) && !isNull(Main.imeDataTf.getText()))
				{
					dosije.ime = Main.imeDataTf.getText();
					return true;
				}
				return false;
			case "prezime: ":
				if(isSizeCorrect(Main.prezimeDataTf.getText(), 1, 25) && !isNull(Main.prezimeDataTf.getText()))
				{
					dosije.prezime = Main.prezimeDataTf.getText();
					return true;
				}
				return false;
			case "jmbg: ":
				if(isSizeCorrect(Main.jmbgDataTf.getText(), 13, 13) && !isNull(Main.jmbgDataTf.getText()) && allDigits(Main.jmbgDataTf.getText()))
				{
					dosije.jmbg = Main.jmbgDataTf.getText();
					return true;
				}
				return false;
			case "datum rođenja: ":
				if(isValidDate(Main.datRodjDataTf.getText()) || isNull(Main.datRodjDataTf.getText()))
				{
					if(isNull(Main.datRodjDataTf.getText()))
						dosije.datum_rodjenja = null;
					else
					{
						String[] tokens = Main.datRodjDataTf.getText().split("\\.");
						dosije.datum_rodjenja = java.sql.Date.valueOf(tokens[2] + "-" + tokens[1] + "-" + tokens[0]);
					}
					return true;
				}
				return false;
			case "mesto rođenja: ":
				if(isSizeCorrect(Main.mestoRodjDataTf.getText(), 1, 100) || isNull(Main.mestoRodjDataTf.getText()))
				{
					dosije.mesto_rodjenja = Main.mestoRodjDataTf.getText();
					return true;
				}
				return false;
			case "država rođenja: ":
				if(isSizeCorrect(Main.drzavaRodjDataTf.getText(), 1, 100) || isNull(Main.drzavaRodjDataTf.getText()))
				{
					dosije.drzava_rodjenja = Main.drzavaRodjDataTf.getText();
					return true;
				}
				return false;
			case "ime oca: ":
				if(isSizeCorrect(Main.imeOcaDataTf.getText(), 1, 50) || isNull(Main.imeOcaDataTf.getText()))
				{
					dosije.ime_oca = Main.imeOcaDataTf.getText();
					return true;
				}
				return false;
			case "ime majke: ":
				if(isSizeCorrect(Main.imeMajkeDataTf.getText(), 1, 50) || isNull(Main.imeMajkeDataTf.getText()))
				{
					dosije.ime_majke = Main.imeMajkeDataTf.getText();
					return true;
				}
				return false;
			case "ulica stanovanja: ":
				if(isSizeCorrect(Main.ulicaDataTf.getText(), 1, 100) || isNull(Main.ulicaDataTf.getText()))
				{
					dosije.ulica_stanovanja = Main.ulicaDataTf.getText();
					return true;
				}
				return false;
			case "kućni broj: ":
				if(isSizeCorrect(Main.kucniBrDataTf.getText(), 1, 20) || isNull(Main.kucniBrDataTf.getText()))
				{
					dosije.kucni_broj = Main.kucniBrDataTf.getText();
					return true;
				}
				return false;
			case "mesto stanovanja: ":
				if(isSizeCorrect(Main.mestoStanDataTf.getText(), 1, 100) || isNull(Main.mestoStanDataTf.getText()))
				{
					dosije.mesto_stanovanja = Main.mestoStanDataTf.getText();
					return true;
				}
				return false;
			case "poštanski broj: ":
				if(isSizeCorrect(Main.postBrDataTf.getText(), 1, 20) || isNull(Main.postBrDataTf.getText()))
				{
					dosije.postanski_broj = Main.postBrDataTf.getText();
					return true;
				}
				return false;
			case "država stanovanja: ":
				if(isSizeCorrect(Main.drzavaStanDataTf.getText(), 1, 100) || isNull(Main.drzavaStanDataTf.getText()))
				{
					dosije.drzava_stanovanja = Main.drzavaStanDataTf.getText();
					return true;
				}
				return false;
			case "telefon: ":
				if(isSizeCorrect(Main.telefonDataTf.getText(), 1, 25) || isNull(Main.telefonDataTf.getText()))
				{
					dosije.telefon = Main.telefonDataTf.getText();
					return true;
				}
				return false;
			case "mobilni telefon: ":
				if(isSizeCorrect(Main.mobilniDataTf.getText(), 1, 25) || isNull(Main.mobilniDataTf.getText()))
				{
					dosije.mobilni_telefon = Main.mobilniDataTf.getText();
					return true;
				}
				return false;
			case "email: ":
				if(isSizeCorrect(Main.emailDataTf.getText(), 1, 50) || isNull(Main.emailDataTf.getText()))
				{
					dosije.email = Main.emailDataTf.getText();
					return true;
				}
				return false;
			case "www uri: ":
				if(isSizeCorrect(Main.urlDataTf.getText(), 1, 100) || isNull(Main.urlDataTf.getText()))
				{
					dosije.www_uri = Main.urlDataTf.getText();
					return true;
				}
				return false;
			case "datum upisa: ":
				if(isValidDate(Main.datumUpisaDataTf.getText()) && !isNull(Main.datumUpisaDataTf.getText()))
				{
					String[] tokens = Main.datumUpisaDataTf.getText().split("\\.");
					dosije.datum_upisa = java.sql.Date.valueOf(tokens[2] + "-" + tokens[1] + "-" + tokens[0]);
					return true;
				}
				return false;
			default:
				return false;
		}
	}
	
	public static boolean isNull(String data)
	{
		if(data == null || data.trim().equals("") || data.trim().equals("nepoznato"))
			return true;
		return false;
	}
	
	public static boolean isSizeCorrect(String data, int min, int max)
	{
		if(data.length() >= min && data.length() <= max)
			return true;
		return false;
	}
	
	public static boolean allDigits(String data)
	{
		for(int i = 0; i < data.length(); i++)
			if(!Character.isDigit(data.charAt(i)))
				return false;
		return true;
	}
	
	public static boolean isValidDate(String data)
	{
		try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            df.setLenient(false);
            df.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
}
