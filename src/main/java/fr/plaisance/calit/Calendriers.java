package fr.plaisance.calit;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
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
        // Générer les saints principaux pour chaque jour
        List<DateLiturgique> saints = new java.util.ArrayList<>();
        SaintsDuJour saintsDuJour = SaintsDuJour.getInstance();

        for (int annee = anneeDebut; annee <= anneeFin; annee++) {
            LocalDate debut = LocalDate.of(annee, 1, 1);
            LocalDate fin = LocalDate.of(annee, 12, 31);
            
            for (LocalDate jour = debut; !jour.isAfter(fin); jour = jour.plusDays(1)) {
                MonthDay monthDay = MonthDay.of(jour.getMonth(), jour.getDayOfMonth());

                String saint = saintsDuJour.getSaintPrincipalDuJour(monthDay);
                if (saint != null && !saint.isEmpty() && !saint.trim().isEmpty()) {
                    DateLiturgique event = DateLiturgique.saint(jour, saint);
                    saints.add(event);
                }
            }
        }

        return saints;
    }

    static List<DateLiturgique> calendrierCompletParPreseance(int anneeDebut, int anneeFin) {
        List<DateLiturgique> calendrier = new java.util.ArrayList<>();
        SaintsDuJour saintsDuJour = SaintsDuJour.getInstance();

        for (int annee = anneeDebut; annee <= anneeFin; annee++) {
            Map<LocalDate, DateLiturgique> evenementsLiturgiques = solennitesFetesDuSeigneurEtDeLaVierge(annee).stream()
                .collect(Collectors.toMap(
                    DateLiturgique::getDate,
                    d -> d,
                    (a, b) -> a.getPriorite() <= b.getPriorite() ? a : b,
                    TreeMap::new
                ));

            LocalDate debut = LocalDate.of(annee, 1, 1);
            LocalDate fin = LocalDate.of(annee, 12, 31);
            for (LocalDate jour = debut; !jour.isAfter(fin); jour = jour.plusDays(1)) {
                DateLiturgique evenementLiturgique = evenementsLiturgiques.get(jour);
                String saintPrincipal = saintsDuJour.getSaintPrincipalDuJour(MonthDay.from(jour));
                if (evenementLiturgique != null) {
                    String libelle = evenementLiturgique.getLibelle();
                    if (saintPrincipal != null && !saintPrincipal.trim().isEmpty()) {
                        libelle = libelle + " - Saint principal : " + saintPrincipal.trim();
                    }
                    calendrier.add(DateLiturgique.compose(
                        jour,
                        libelle,
                        evenementLiturgique.getCouleur(),
                        evenementLiturgique.getPriorite()
                    ));
                } else if (saintPrincipal != null && !saintPrincipal.trim().isEmpty()) {
                    calendrier.add(DateLiturgique.saint(jour, saintPrincipal.trim()));
                } else {
                    calendrier.add(DateLiturgique.saint(jour, "Férie"));
                }
            }
        }

        return calendrier.stream()
            .sorted(Comparator.comparing(DateLiturgique::getDate))
            .collect(Collectors.toList());
    }
}
