Cette application a pour objectif d’avoir toutes les cartes de fidélité toujours avec le client, en les retrouvant sur son téléphone mobile. Elle permet également de comparer les prix d’un produit entre les différents magasins, de gérer les listes des courses, de géo-localiser les magasins et enfin de recevoir les nouvelles promotions.

Pour faire tourner l’application veuillez suivre ces instructions:
## géolocalisation
Vu que notre application offre comme fonctionnalité de  géolocalisation, il faut télécharger Google Play services, l’importer dans Eclipse et obtenir une clé API de Google.
### Téléchargement Google Play services:
La première étape consiste à télécharger les services Google Play pour pouvoir les intégrer par la suite à notre projet Android en tant que librairie.
Pour les télécharger, rendez-vous dans le SDK Manager puis dans la catégorie Extra, cochez le service Google Play.

### Importation de Google Play services dans Eclipse:
Il faut ajouter Google Play services dans Eclipse comme bibliothèque. Pour obtenir une clé de Google vous pouvez suivre ce tutoriel: http://www.tutos-android.com/introduction-a-google-map-v2.

## Autres instructions
1. Pour changer l’adresse du serveur, il faut ouvrir la classe « Constants » se trouvant dans le package menu.util.
2. Il faut mettre le dossier « tunshop » sous le serveur. Vous y trouverez tous les fichiers nécessaires comme les scripts PHP  et les images.
