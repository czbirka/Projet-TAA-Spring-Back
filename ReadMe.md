# We Can Go
***


Dans le cadre du projet TAA/GLI de l'année 2017-2018, il nous a été demandé de réaliser une application suivant ce cahier des charges :
[le lien ici](https://docs.google.com/document/d/1L0yHbm8z3Jzbt0x6XiOBtKPz48JjVggAFNuFvbDx5mM/edit?usp=sharing)

### Structure de notre application:

Nous avons une application 3-Tiers classique
le tiers client accessible sur [localhost:4200]  
Lien du github : [ICI](https://github.com/czbirka/Projet-TAA-Front.git)

Le tiers serveur accessible sur [localhost:9200]  
NB : il faut être authentifié.  
Lien du github : [ICI](https://github.com/czbirka/Projet-TAA-Spring-Back.git)

Le tiers base de données accessible sur un docker.  [172.17.0.2:3306/projet_taa]
projet_taa étant le nom de la base de données que nous utiliserons.

**Remarque :**  
Pour plus de flexibilité dans la manipulation et le monitoring des données, nous avons lier notre conteneur mysql à un conteneur phpmyadmin accessible via [localhost:8700]


> docker run --name mysql -v /var/projet_taa/datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql

On installe le conteneur docker avec une image de mysql.
On profite pour lui assigner un volume, où il stockera les fichiers afin de ne pas perdre nos modifications.
Le mot de passe de la base de données sera : root.

> docker run --name phpmyadmin -d --link mysql:db -p 8700:80 phpmyadmin/phpmyadmin

Installe le conteneur phpmyadmin et le lie au SGBD mysql dans le conteneur mysql.
On expose et redirige le port 80 du conteneur phpmyadmin sur le port 8700 de notre machine hôte.




## I/ Installer et lancer l'application
____

1. Créer une bd dans mysql. Se connecter à phpmyadmin (root/root) et créer une base de données vide si elle n'existe pas),du nom de "projet_taa".

2. Lancer le serveur. Toute la mécanique de Spring, JPA et Hibernate déclencheront la création des tables que l'application va manipuler.
Lancer la classe [package.name.Application]

3. Lancer l'application
Faire un npm install && npm start.
L'application démarrera sur le port 4200, comme mentionné plus haut.


## II/ Scénario
____

Cette application suit le scénario dont les étapes sont décrites ci-dessous :

1. Créer un utilisateur (onglet Inscription).
Pour utiliser notre application, il faut être préalablement enregistré.
Si ce n'est pas le cas, il est possible de s'inscrire en cliquant sur le lien Inscription et en remplissant le formulaire.

2. Se connecter (avec un utilisateur existant).
Utiliser un compte existant pour pouvoir se connecter. Seuls le login et le password des comptes sont utiles pour se connecter.
3. Aller sur la page pour planifier une activité (utiliser le logo ou le lien "Mes Activités").
Un nouvel utilisateur n'a pas d'activités planifiées.
Ajoutons donc une activité en cliquant sur "Créer une nouvelle activité".
4. Remplir le formulaire
Pour ajouter une activité, il faut :
Sélectionner le lieu où nous voulons effectuer cette activité.
Remplir les champs "Région", "Département" et "Ville" dans la section localité.
Donner un nom à votre activité.
Spécifier les conditions météorologiques idéales selon vous pour les effectuer :
   * Température : Min <= Inf <= Sup <= Max
   * Vent : Min <= Inf <= Sup <= Max
5. Soumettre
Vous serez notifiés le samedi à 15h par email des conditions meteo conformement à vos paramètres.