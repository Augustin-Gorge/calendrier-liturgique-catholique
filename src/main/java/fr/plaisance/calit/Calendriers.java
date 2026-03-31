package fr.plaisance.calit;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static fr.plaisance.calit.CalendrierLiturgique.*;

public class Calendriers {
	
	static void verifier(int annee) {
		List<DateLiturgique> calendrier = solennitesFetesDuSeigneurEtDeLaVierge(annee);
		for (int i = 0; i < calendrier.size() - 1; i ++) {
			for (int j = i + 1; j < calendrier.size(); j ++) {
				DateLiturgique a = calendrier.get(i);
				DateLiturgique b = calendrier.get(j);
				if(a.memeJourQue(b)) {
					System.out.println(String.format("En %d, la fête '%s' de priorité [%d] tombe le même jour que la fête '%s' de priorité [%d]", annee, a.getLibelle(), a.getPriorite(), b.getLibelle(), b.getPriorite()));
				}
			}
		}
	}
	
    static List<DateLiturgique> solennitesFetesDuSeigneurEtDeLaVierge(int annee) {
        return Arrays.asList(
            immaculeeConception(annee),
            noel(annee),
            sainteMarieMereDeDieu(annee),
            epiphanie(annee),
            saintJoseph(annee),
            cendres(annee),
            rameaux(annee),
            jeudiSaint(annee),
            vendrediSaint(annee),
            samediSaint(annee),
            paques(annee),
            divineMisericorde(annee),
            annonciation(annee),
            ascension(annee),
            pentecote(annee),
            sainteTrinite(annee),
            feteDieu(annee),
            sacreCoeur(annee),
            nativiteStJeanBaptiste(annee),
            saintsPierreEtPaul(annee),
            assomption(annee),
            toussaint(annee),
            jourDesMorts(annee),
            christRoi(annee),
            // Fêtes
            baptemeDuSeigneur(annee),
            presentationAuTemple(annee),
            sainteFamille(annee),
            transfiguration(annee),
            visitation(annee),
            nativiteViergeMarie(annee)
        ).stream()
		.filter(Objects::nonNull)
		.collect(Collectors.toList());
    }

    static List<DateLiturgique> saintsPrincipauxDuJour(int anneeDebut, int anneeFin) {
        // Récupérer toutes les dates liturgiques pour exclure les jours qui ont déjà un événement
        Set<MonthDay> joursAvecEvenement = new HashSet<>();
        for (int annee = anneeDebut; annee <= anneeFin; annee++) {
            solennitesFetesDuSeigneurEtDeLaVierge(annee).forEach(d -> joursAvecEvenement.add(MonthDay.of(d.getDate().getMonth(), d.getDate().getDayOfMonth())));
        }

        // Générer les saints pour les jours sans événement
        List<DateLiturgique> saints = new java.util.ArrayList<>();
        SaintsDuJour saintsDuJour = SaintsDuJour.getInstance();

        for (int annee = anneeDebut; annee <= anneeFin; annee++) {
            LocalDate debut = LocalDate.of(annee, 1, 1);
            LocalDate fin = LocalDate.of(annee, 12, 31);
            
            for (LocalDate jour = debut; !jour.isAfter(fin); jour = jour.plusDays(1)) {
                MonthDay monthDay = MonthDay.of(jour.getMonth(), jour.getDayOfMonth());
                
                // Ajouter le saint seulement si le jour n'a pas d'événement liturgique
                if (!joursAvecEvenement.contains(monthDay)) {
                    String saint = saintsDuJour.getSaintPrincipalDuJour(monthDay);
                    if (saint != null && !saint.isEmpty() && !saint.trim().isEmpty()) {
                        DateLiturgique event = DateLiturgique.saint(jour, saint);
                        saints.add(event);
                    }
                }
            }
        }

        return saints;
    }
}
