# Calendrier liturgique catholique

Ce dépôt est un fork du projet original de [Romain Warnan](https://github.com/romain-warnan).

Modifications et maintenance par [augustin-gorge](https://github.com/augustin-gorge).

## Calendrier au format _iCalendar_

### Calendrier liturgique catholique romain

[Télécharger le calendrier ICS](https://augustin-gorge.github.io/calendrier-liturgique-catholique/calendrier-liturgique-catholique-romain.ics)

URL d'abonnement : https://augustin-gorge.github.io/calendrier-liturgique-catholique/calendrier-liturgique-catholique-romain.ics

### Calendrier complet

[Télécharger le calendrier ICS complet](https://augustin-gorge.github.io/calendrier-liturgique-catholique/saints-du-jour.ics)

URL d'abonnement : https://augustin-gorge.github.io/calendrier-liturgique-catholique/saints-du-jour.ics

[Voir le site explicatif](https://augustin-gorge.github.io/calendrier-liturgique-catholique/)

Période couverte : de 2024 à 2100 (inclus).

## S'abonner au calendrier

Applications courantes :
 - Google Agenda : Autres agendas > Ajouter via URL.
 - Apple Calendrier : Fichier > Nouvel abonnement à un calendrier.
 - Outlook : Ajouter un calendrier > S'abonner à partir du web.

Applications courantes :
 - Google Agenda : Autres agendas > Ajouter via URL.
 - Apple Calendrier : Fichier > Nouvel abonnement à un calendrier.
 - Outlook : Ajouter un calendrier > S’abonner à partir du web.

Le calendrier contient :
 - le _triduum_ pascal,
 - le mercredi des cendres,
 - le jour des morts,
 - les solennités,
 - les fêtes du Seigneur,
 - les fêtes de la Vierge Marie,
 - le saint principal du jour (calendrier complet : une entrée par date).

## Calendrier liturgique tridentin

[Consulter l'Ordo tridentin (La Porte Latine)](http://laportelatine.org/accueil/Ordo/ordo.php)

## Ordre de préséance des fêtes

[Source](http://croire.la-croix.com/Paroisses/Ressources/Calendrier-liturgique/Ordre-de-preseance-des-fetes)

1. Triduum pascal de la Passion et de la Résurrection du Seigneur.

2. Nativité du Seigneur, Épiphanie, Ascension, Pentecôte, mercredi des Cendres, dimanche des Rameaux, Jeudi Saint, dimanche de la Divine Miséricorde.

3. Solennités du Seigneur, de la Vierge et des saints du calendrier général, ainsi que la commémoration des fidèles défunts.

4. Fêtes du Seigneur (p. ex. Baptême du Seigneur, Présentation au Temple, Transfiguration, Sainte Famille).

5. Saint principal du jour.

Règle de génération du calendrier complet (`saints-du-jour.ics`) :
 - une seule entrée par jour ;
 - si une célébration liturgique est présente, elle est retenue (préséance) ;
 - le saint principal du jour est ajouté dans le libellé de cette entrée ;
 - sinon, l'entrée du jour est le saint principal (ou `Férie` si donnée absente).