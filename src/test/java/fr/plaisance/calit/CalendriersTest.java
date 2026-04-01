package fr.plaisance.calit;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendriersTest {

    @Test
    public void calendrierCompletParPreseance_donneUneEntreeParJour() {
        int anneeDebut = 2024;
        int anneeFin = 2026;

        List<DateLiturgique> calendrier = Calendriers.calendrierCompletParPreseance(anneeDebut, anneeFin);

        Map<Integer, Long> evenementsParAnnee = calendrier.stream()
            .collect(Collectors.groupingBy(d -> d.getDate().getYear(), Collectors.counting()));
        Map<Integer, Set<LocalDate>> joursParAnnee = calendrier.stream()
            .collect(Collectors.groupingBy(
                d -> d.getDate().getYear(),
                Collectors.mapping(DateLiturgique::getDate, Collectors.toSet())
            ));

        assertThat(evenementsParAnnee).containsEntry(2024, 366L);
        assertThat(evenementsParAnnee).containsEntry(2025, 365L);
        assertThat(evenementsParAnnee).containsEntry(2026, 365L);

        assertThat(joursParAnnee.get(2024)).hasSize(366);
        assertThat(joursParAnnee.get(2025)).hasSize(365);
        assertThat(joursParAnnee.get(2026)).hasSize(365);
    }

    @Test
    public void calendrierCompletParPreseance_ajouteLeSaintPrincipalSurJourLiturgique() {
        List<DateLiturgique> calendrier = Calendriers.calendrierCompletParPreseance(2024, 2024);

        DateLiturgique noel = calendrier.stream()
            .filter(d -> d.getDate().equals(LocalDate.of(2024, 12, 25)))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Entrée manquante pour Noël"));

        assertThat(noel.getPriorite()).isEqualTo(2);
        assertThat(noel.getLibelle()).contains("Noël");
        assertThat(noel.getLibelle()).contains("Saint principal :");
    }

    @Test
    public void calendrierCompletParPreseance_couvreChaqueJourDe2024a2100() {
        int anneeDebut = 2024;
        int anneeFin = 2100;

        List<DateLiturgique> calendrier = Calendriers.calendrierCompletParPreseance(anneeDebut, anneeFin);

        long joursAttendus = 0L;
        for (int annee = anneeDebut; annee <= anneeFin; annee++) {
            joursAttendus += Year.of(annee).length();
        }

        assertThat(calendrier).hasSize((int) joursAttendus);
        assertThat(calendrier.stream().map(DateLiturgique::getDate).distinct().count()).isEqualTo(joursAttendus);
    }
}
