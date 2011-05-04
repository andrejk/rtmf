/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
package nl.rotterdam.rtmf.guc.bronhouder.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Deze bean bevat informatie over een bronhouders, hoe de bronhouder te
 * bereiken is door Mule, en de basisregistraties welke door de bronhouder worden
 * beheerd.
 * 
 * @author Enno Buis
 * 
 */
public class BronhouderInfo {

	/*
	 * naam				- Naam/Tag/Afkorting/ID/o.i.d. van de bronhouder
	 * bereikenVia		- Email of File
	 * bereikenAdres	- Email adres of path/file locatie
	 * basisregistraties			- Lijst van basisregistraties welke door bronhouder worden beheerd
	 */
	private String naam;
	private String code;
	private String bereikenVia;
	private String bereikenAdres;
	private List<String> basisregistraties = new ArrayList<String>();

	public BronhouderInfo() {
		// nop
	}

	public BronhouderInfo(String naam, String code, String bereikenVia,
			String bereikenAdres, String basisregistratie) {
		this.naam = naam;
		this.code = code;
		this.bereikenVia = bereikenVia;
		this.bereikenAdres = bereikenAdres;
		basisregistraties.add(basisregistratie);
	}

	/**
	 * Voeg een basisregistratie toe aan de lijst van basisregistratie welke beheerd worden door
	 * deze bronhouder.
	 * 
	 * @param basisregistratie
	 *            Het basisregistratie wat aan de lijst moet worden toegevoegd.
	 */
	public void addBasisregistratie(String basisregistratie) {
		basisregistraties.add(basisregistratie);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBereikenVia() {
		return bereikenVia;
	}

	public void setBereikenVia(String bereikenVia) {
		this.bereikenVia = bereikenVia;
	}

	public String getBereikenAdres() {
		return bereikenAdres;
	}

	public void setBereikenAdres(String bereikenAdres) {
		this.bereikenAdres = bereikenAdres;
	}

	public List<String> getBasisregistraties() {
		return basisregistraties;
	}

	public void setBasisregistraties(List<String> basisregistraties) {
		this.basisregistraties = basisregistraties;
	}
	
	@Override
	public String toString() {
		String result = "[Naam: " + naam + "][Code: " + code + "][bereikenVia:" + bereikenVia + "][bereikenAdres:" + bereikenAdres +"]";
		if (basisregistraties.size()>0) {
			result = result + "[basisregistratie(s):";
			for (String obj : basisregistraties) {
				result = result + "{" + obj + "}";
			}
			result = result + "]";
		}
		
		return result;
	}

}
