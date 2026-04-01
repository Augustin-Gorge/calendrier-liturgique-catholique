package fr.plaisance.calit;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static fr.plaisance.calit.CalendrierFabrique.creerCalendrier;
import static java.util.stream.Collectors.toList;

public class FabricationCalendriers {

    public static void main(String[] args) throws IOException {
        final int anneeDebut = 2024;
        final int anneeFin = 2100;

        // Générer le calendrier liturgique
        final List<DateLiturgique> dates = IntStream.rangeClosed(anneeDebut, anneeFin)
            .mapToObj(Calendriers::solennitesFetesDuSeigneurEtDeLaVierge)
            .flatMap(Collection::stream)
            .collect(toList());
        creerCalendrier("docs/calendrier-liturgique-catholique-romain.ics", dates, "Calendrier liturgique catholique romain", "Solennités, fêtes du Seigneur et de la Vierge Marie selon le calendrier romain - 2024 à 2100");

        // Générer le calendrier complet avec une entrée quotidienne arbitré par préséance
        final List<DateLiturgique> complet = Calendriers.calendrierCompletParPreseance(anneeDebut, anneeFin);
        creerCalendrier("docs/saints-du-jour.ics", complet, "Calendrier liturgique & saints", "Fêtes liturgiques catholiques et saints du jour - 2024 à 2100");
    }
}
