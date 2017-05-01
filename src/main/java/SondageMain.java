import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.examen_s2_2017.persistence.Personne;
import tn.esprit.examen_s2_2017.persistence.QuestionSondage;
import tn.esprit.examen_s2_2017.persistence.ReponseSondage;
import tn.esprit.examen_s2_2017.services.SondageServiceRemote;


public class SondageMain {

	public static void main(String[] args) throws NamingException {

		String jndiName = "examen-s2-2017-ear/examen-s2-2017-ejb/SondageService!tn.esprit.examen_s2_2017.services.SondageServiceRemote";
		Context context = new InitialContext();
		SondageServiceRemote sondageServiceRemote = (SondageServiceRemote) context.lookup(jndiName);

		
		//Question II.1.1
		QuestionSondage tvPrefere = new QuestionSondage("Quelle est votre chaîne TV préférée ?");
		int idTVPrefere = sondageServiceRemote.ajouterQuestion(tvPrefere);
		
		//Question II.1.2
		ReponseSondage nessma = new ReponseSondage("Nessma");
		ReponseSondage hannibal = new ReponseSondage("Hannibal"); 
		ReponseSondage ettounisia = new ReponseSondage("Ettounisia"); 

		int idNessma = sondageServiceRemote.ajouterReponse(nessma);
		int idHannibal = sondageServiceRemote.ajouterReponse(hannibal);
		int idEttounisia = sondageServiceRemote.ajouterReponse(ettounisia);

		//Question II.1.3
		sondageServiceRemote.affecterQuestionReponses(idTVPrefere, idNessma, idHannibal, idEttounisia);
		
		
		//Question II.1.4 (cascade persist)
		QuestionSondage fruitPrefere = new QuestionSondage("Quel est votre fruit préféré ?");
		ReponseSondage fraise = new ReponseSondage("Fraise");
		ReponseSondage orange = new ReponseSondage("Orange"); 
		ReponseSondage kiwi = new ReponseSondage("Kiwi"); 
		
		List<ReponseSondage> listReponsesFruitPrefere = new ArrayList<>();
		listReponsesFruitPrefere.add(fraise);
		listReponsesFruitPrefere.add(orange);
		listReponsesFruitPrefere.add(kiwi);
		
		fruitPrefere.setReponses(listReponsesFruitPrefere);
		
		sondageServiceRemote.ajouterQuestionEtReponses(fruitPrefere);
		
//		QuestionSondage couleurPrefere = new QuestionSondage("Quelle est votre couleur préférée ?");
//		ReponseSondage rouge = new ReponseSondage("Rouge");
//		ReponseSondage noir = new ReponseSondage("Noir"); 
//		ReponseSondage bleu = new ReponseSondage("Bleu"); 
//
//		List<ReponseSondage> listReponsesCouleurPrefere = new ArrayList<>();
//		listReponsesCouleurPrefere.add(rouge);
//		listReponsesCouleurPrefere.add(noir);
//		listReponsesCouleurPrefere.add(bleu);
//		
//		fruitPrefere.setReponses(listReponsesCouleurPrefere);
//		
//		sondageServiceRemote.ajouterQuestionEtReponses(couleurPrefere);
		
		
		//II.2.1
		Personne hediZITOUNI = new Personne("ZITOUNI", "Hedi", 45);
		Personne helaBOUAZIZ = new Personne("BOUAZIZ", "Hela", 23);		
		Personne mariemHENI = new Personne("HENI", "Mariem", 55);
		
		int hediZITOUNId = sondageServiceRemote.ajouterPersonneEtAffecterReponse(idHannibal, hediZITOUNI);
		int helaBOUAZIZId = sondageServiceRemote.ajouterPersonneEtAffecterReponse(idNessma, helaBOUAZIZ);
		int mariemHENIId = sondageServiceRemote.ajouterPersonneEtAffecterReponse(idHannibal, mariemHENI);
		
		
		//II.2.2
		int idFraise = 4;
		sondageServiceRemote.affecterReponseAPersonne(idFraise, hediZITOUNId);
		sondageServiceRemote.affecterReponseAPersonne(idFraise, helaBOUAZIZId);

		
		//III.3.1
		//fetch
		System.out.println("Les réponses de Hela BOUAZIZ sont : ");
		List<ReponseSondage> reponseSondages = sondageServiceRemote.getAllResponsesParPersonnes(helaBOUAZIZId);
		for (ReponseSondage reponseSondage : reponseSondages) {
			System.out.println(reponseSondage.getReponse());
		}
		
		//III.3.2
		System.out.println("Nombre de personnes qui ont répondu : " + sondageServiceRemote.nbPersonne());
	}

}
