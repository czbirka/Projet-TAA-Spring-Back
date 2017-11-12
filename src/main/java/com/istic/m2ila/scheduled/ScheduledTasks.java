package com.istic.m2ila.scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
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

	//@Scheduled(fixedRate = 5000)//pour test
	@Scheduled(cron = "0 36 17 * * SUN")
	public void reportCurrentTime() {

		/*
		 * bilan hebdo regroupe les bilans de tous les users
		 */
		List<BilanUser> bilanHebdo = new ArrayList<>();

		List<User> users = userDao.findAll();
		List<Activite> activites = activiteDao.findAll();

		//on passe en revue tous les users
		for (int i = 0; i < users.size(); i++) {

			BilanUser bilanUser = new BilanUser();
			
			//liste des activités du User
			List<Activite> activitesUser = new ArrayList<Activite>();
			//liste des lieux sélectionnés par user
			List<Lieu> lieuxUser = new ArrayList<Lieu>();

			//construction de la liste des activités du user
			for (int j = 0; j < activites.size(); j++) {
				if (activites.get(j).getUser() != null) {
					if ((activites.get(j).getUser().getId() == users.get(i).getId())
							&& (!activitesUser.contains(activites.get(j)))) {
						activitesUser.add(activites.get(j));
					}
				}
			}
			
			
			//construction de la liste des lieux sélectionnés par user
			for (int j = 0; j < activitesUser.size(); j++) {

				List<Lieu> lieuxActivite = activiteDao.findLieuxById(activites.get(j).getId());
				for (int k = 0; k < lieuxActivite.size(); k++) {
					if (!lieuxUser.contains(lieuxActivite.get(k))) {
						lieuxUser.add(lieuxActivite.get(k));
					}
				}
			}
			
			//si pas de lieu sélectionné: pas de bilan
			if (lieuxUser.size() > 0) {
				bilanUser.setUser(users.get(i));
				bilanHebdo.add(bilanUser);
			}

			// recherche donnees meteo pour chaque lieu
			for (int j = 0; j < lieuxUser.size(); j++) {
				
				//bilan pour les activites du lieu pour samedi
				BilanLieu bilanLieuSamedi = new BilanLieu();
				//bilan pour les activites du lieu pour dimanche
				BilanLieu bilanLieuDimanche = new BilanLieu();
				bilanLieuSamedi.setLieu(lieuxUser.get(j));
				bilanLieuDimanche.setLieu(lieuxUser.get(j));
				bilanUser.getBilanSamedi().add(bilanLieuSamedi);
				bilanUser.getBilanDimanche().add(bilanLieuDimanche);

				//envoi de la requete vers le site openweathermap.org
				String donneesMeteo = "";
				try {
					donneesMeteo = this.sendGET(lieuxUser.get(j).getLatitude(), lieuxUser.get(j).getLongitude());
				} catch (IOException e) {
					e.printStackTrace();
				}

				//selection des donnees pour samedi 15h et dimanche 15h
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
								
								//on affecte le resultat à chaque activite
								//	OK : les prévisions de temp et de vent sont dans les limtes (inf et sup) optimales
								//	BOF : une des prévisions est hors de limites optimales
								//	NON : une des prévisions (vent ou temp) est hors des limites extemes (min et max)
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
		// Envoyer les mails 
		sendMails(bilanHebdo);
	}
	
	private void sendMails(List<BilanUser> bilans){
		// Initialize mailSender config
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		MailingService mm = new MailingService();
		MailSender ms = (MailSender) context.getBean("mailSender");
		mm.setMailSender(ms);
		
		// init vars
		String mail="";
		String userMail="";
		String bilanSamedi="";
		String bilanDimanche="";
		
		// for each user
		for (BilanUser bilanUser : bilans) {
			
			//get mail
			userMail = bilanUser.getUser().getEmail();
			
			//get saturday results
			bilanSamedi = "- Bilan Samedi :\n";
			
			// for each city
			for (BilanLieu bilanSam : bilanUser.getBilanSamedi()) {
				//get city name
				bilanSamedi +="\t * "+bilanSam.getLieu().getNom()+":\n";
				
				// for each activity
				for (BilanActivite bilanActivity : bilanSam.getBilansActivite()) {
					//get activity name and weather score
					bilanSamedi +="\t\t * "+bilanActivity.getActivite().getNom()+": "+bilanActivity.getResultat()+"\n";
				}
			}
			
			//get sunday results
			bilanDimanche = "- Bilan Dimanche :\n";
			
			// for each city
			for (BilanLieu bilanDim : bilanUser.getBilanDimanche()) {
				//get city name
				bilanDimanche +="\t * "+bilanDim.getLieu().getNom()+":\n";
				
				// for each activity
				for (BilanActivite bilanActivity : bilanDim.getBilansActivite()) {
					//get activity name and weather score
					bilanDimanche +="\t\t * "+bilanActivity.getActivite().getNom()+":"+bilanActivity.getResultat()+"\n";
				}
			}
			
			//concatenate saturday and sunday results
			mail = "\nBonjour,\n\n Vous trouvez ci-joint les prévisions météo pour ce week-end :\n"
					+bilanSamedi + "\n" + bilanDimanche
					+"\n\n Cordialement,\n\n WeekEndApp Team.";
			
			//Send mail for user i
			mm.sendMail("appweekend@gmail.com", userMail, "WeekEndApp : Bilan météo de ce week-end", mail);
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("Mail sent to :"+userMail);
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			
		}
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
			return "5/5";
		}
		else if (resultat == 2) {
			return "3/5";
		}
		else return "0/5";
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
