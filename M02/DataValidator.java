package M02;

public class DataValidator {
	public static boolean validate(String name)
	{
		switch(name)
		{
			case "ime: ":
				return true;
			case "prezime: ":
				return true;
			case "jmbg: ":
				return true;
			case "datum rođenja: ":
				return true;
			case "mesto rođenja: ":
				return true;
			case "država rođenja: ":
				return true;
			case "ime oca: ":
				return true;
			case "ime majke: ":
				return true;
			case "ulica stanovanja: ":
				return true;
			case "kućni broj: ":
				return true;
			case "mesto stanovanja: ":
				return true;
			case "poštanski broj: ":
				return true;
			case "država stanovanja: ":
				return true;
			case "telefon: ":
				return true;
			case "mobilni telefon: ":
				return true;
			case "email: ":
				return true;
			case "www uri: ":
				return true;
			case "datum upisa: ":
				return true;
			default:
				return false;
		}
	}
}
