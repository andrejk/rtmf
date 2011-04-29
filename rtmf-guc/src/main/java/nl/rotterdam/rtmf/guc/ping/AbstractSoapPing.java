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
package nl.rotterdam.rtmf.guc.ping;

import java.io.IOException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import nl.rotterdam.ioo.guc_algemeen.componenten.functional.Pingable;

public abstract class AbstractSoapPing implements Pingable {
	private Logger logger = Logger.getLogger(AbstractSoapPing.class);
	
	private static final int TIME_OUT = 2000;
	abstract String getRequest();
	abstract String getUrl();
	abstract void setErrorMessage(String errorMessage);

	@Override
	public boolean isAlive() {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpget = new HttpPost(getUrl());
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT);
		try {
			httpget.addHeader("SOAPAction",
					"\"http://videoos.net/2/XProtectCSServerCommand/Login\"");

			StringEntity strent = new StringEntity(getRequest());
			strent.setContentType("text/xml; charset=utf-8");
			httpget.setEntity(strent);

			ResponseHandler<String> response = new BasicResponseHandler();
			String responseBody = client.execute(httpget, response);
			if (responseBody.contains("<foutBericht>")) {
				setErrorMessage(responseBody);
				return false;
			} else if (responseBody.contains("Fault")) {
				setErrorMessage(responseBody);
				return false;
			}
			return true;
		} catch (IOException ex) {
			setErrorMessage(ex.toString());
			logger.info("Er is een fout opgetreden bij het aanroepen van de service: " + getResourceName(), ex);
			return false;
		}
	}
	
//	public static void main(String[] args) {
//		PingStelselcatalogusRott pingStelselcatalogusRott = new PingStelselcatalogusRott();
//		System.out.println("isAlive? " + pingStelselcatalogusRott.isAlive() + " errorMessage: " + pingStelselcatalogusRott.getErrorMessage());
//	}
}
