package com.istic.m2ila.scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.istic.m2ila.meteo.BilanActivite;
import com.istic.m2ila.meteo.BilanLieu;
import com.istic.m2ila.meteo.BilanUser;
import com.istic.m2ila.model.Activite;
import com.istic.m2ila.model.Lieu;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.ActiviteDAO;
import com.istic.m2ila.service.UserDAO;

@Component
public class ScheduledTasks {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private ActiviteDAO activiteDao;

	@Scheduled(cron = "0 0 23 * * TUE")
	public void reportCurrentTime() {

	}

	@Scheduled(fixedRate = 5000)
	public void test() {

		List<BilanUser> bilanHebdo = new ArrayList<>();

		List<User> users = userDao.findAll();
		List<Activite> activites = activiteDao.findAll();

		
		for (int i = 0; i < users.size(); i++) {

			BilanUser bilanUser = new BilanUser();
			
			List<Activite> activitesUser = new ArrayList<Activite>();
			List<Lieu> lieuxUser = new ArrayList<Lieu>();

			for (int j = 0; j < activites.size(); j++) {
				if (activites.get(j).getUser() != null) {
					if ((activites.get(j).getUser().getId() == users.get(i).getId())
							&& (!activitesUser.contains(activites.get(j)))) {
						activitesUser.add(activites.get(j));
					}
				}
			}

			for (int j = 0; j < activitesUser.size(); j++) {

				List<Lieu> lieuxActivite = activiteDao.findLieuxById(activites.get(j).getId());
				for (int k = 0; k < lieuxActivite.size(); k++) {
					if (!lieuxUser.contains(lieuxActivite.get(k))) {
						lieuxUser.add(lieuxActivite.get(k));
					}
				}
			}
			
			if (lieuxUser.size() > 0) {
				bilanUser.setUser(users.get(i));
				bilanHebdo.add(bilanUser);
			}

			// recherche meteo lieu
			for (int j = 0; j < lieuxUser.size(); j++) {

				BilanLieu bilanLieuSamedi = new BilanLieu();
				BilanLieu bilanLieuDimanche = new BilanLieu();
				bilanLieuSamedi.setLieu(lieuxUser.get(j));
				bilanLieuDimanche.setLieu(lieuxUser.get(j));
				bilanUser.getBilanSamedi().add(bilanLieuSamedi);
				bilanUser.getBilanDimanche().add(bilanLieuDimanche);

				String donneesMeteo = "";
				try {
					donneesMeteo = this.sendGET(lieuxUser.get(j).getLatitude(), lieuxUser.get(j).getLongitude());
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (!donneesMeteo.equals("")) {
					// recherche temp et vent
					int indexTemp = 0;
					int indexVent = 0;
					for (int k = 0; k < 34; k++) {
						indexTemp = donneesMeteo.indexOf("\"temp\":", indexTemp + 1);
						indexVent = donneesMeteo.indexOf("\"speed\":", indexVent + 1);
						// samedi 15H
						if (k == 29) {
							int debutTemp = donneesMeteo.indexOf(":", indexTemp);
							int finTemp = donneesMeteo.indexOf(",", indexTemp);
							String tempString = donneesMeteo.substring(debutTemp + 1, finTemp);
							int debutVent = donneesMeteo.indexOf(":", indexVent);
							int finVent = donneesMeteo.indexOf(",", indexVent);
							String ventString = donneesMeteo.substring(debutVent + 1, finVent);

							bilanLieuSamedi.setTemp(Double.parseDouble(tempString));
							bilanLieuSamedi.setVent(Double.parseDouble(ventString));
						}
						// dimanche 15H
						if (k == 33) {
							int debutTemp = donneesMeteo.indexOf(":", indexTemp);
							int finTemp = donneesMeteo.indexOf(",", indexTemp);
							String tempString = donneesMeteo.substring(debutTemp + 1, finTemp);
							int debutVent = donneesMeteo.indexOf(":", indexVent);
							int finVent = donneesMeteo.indexOf(",", indexVent);
							String ventString = donneesMeteo.substring(debutVent + 1, finVent);

							bilanLieuDimanche.setTemp(Double.parseDouble(tempString));
							bilanLieuDimanche.setVent(Double.parseDouble(ventString));
						}

					}
					
					// recherche bilanactivites du lieu
					for (int k = 0; k < activitesUser.size(); k++) {

						List<Lieu> lieuxActivite = activiteDao.findLieuxById(activites.get(k).getId());
						for(int ii=0; ii<lieuxActivite.size(); ii++) {

							if (lieuxActivite.get(ii).getId() == lieuxUser.get(j).getId()) {

								BilanActivite bilanActiviteS = new BilanActivite();
								bilanActiviteS.setActivite(activitesUser.get(k));
								bilanLieuSamedi.getBilansActivite().add(bilanActiviteS);
								BilanActivite bilanActiviteD = new BilanActivite();
								bilanActiviteD.setActivite(activitesUser.get(k));
								bilanLieuDimanche.getBilansActivite().add(bilanActiviteD);
								
								bilanActiviteS.setResultat(resultatMeteo(bilanActiviteS.getActivite(), 
										bilanLieuSamedi.getTemp(), bilanLieuSamedi.getVent()));
								bilanActiviteD.setResultat(resultatMeteo(bilanActiviteD.getActivite(), 
										bilanLieuDimanche.getTemp(), bilanLieuDimanche.getVent()));
							}
						}

					}

				}

			}

		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		// Envoyer mails
		//
		// envoyerMails(bilanHebdo);
		//
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////

	}
	
	private String resultatMeteo(Activite activite, double temp, double vent) {
		
		int resTemp = 0;
		int resVent = 0;
		if ((temp >= activite.getCondition().getTempInf()) && (temp <= activite.getCondition().getTempSup())) {
			resTemp = 2;
		}
		else if ((temp < activite.getCondition().getTempMin()) && (temp > activite.getCondition().getTempMax())) {
			resTemp = 0;
		}
		else {
			resTemp = 1;
		}
		if ((vent >= activite.getCondition().getVentInf()) && (vent <= activite.getCondition().getVentSup())) {
			resVent = 2;
		}
		else if ((vent < activite.getCondition().getVentMin()) && (vent > activite.getCondition().getVentMax())) {
			resVent = 0;
		}
		else {
			resVent = 1;
		}
		
		int resultat = resTemp*resVent;
		if (resultat == 4) {
			return "OK";
		}
		else if (resultat == 2) {
			return "BOF";
		}
		else return "NON";
	}

	private String sendGET(double latitude, double longitude) throws IOException {
		String GET_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude
				+ "&APPID=79ab92ee7aba089ef7c6dbb6c96c9a54&units=metric";
		String USER_AGENT = "Mozilla/5.0";

		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			String response = "";

			while ((inputLine = in.readLine()) != null) {
				response += inputLine;
			}
			in.close();

			return response;

		} else {
			System.out.println("GET request not worked");
			return null;
		}

	}

}
