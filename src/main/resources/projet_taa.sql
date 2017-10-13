-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le :  ven. 13 oct. 2017 à 12:52
-- Version du serveur :  5.7.19
-- Version de PHP :  7.0.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projet_taa`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

CREATE TABLE `activite` (
  `id` bigint(20) NOT NULL,
  `temp_inf` double NOT NULL,
  `temp_max` double NOT NULL,
  `temp_min` double NOT NULL,
  `temp_sup` double NOT NULL,
  `vent_inf` double NOT NULL,
  `vent_max` double NOT NULL,
  `vent_min` double NOT NULL,
  `vent_sup` double NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `activite`
--

INSERT INTO `activite` (`id`, `temp_inf`, `temp_max`, `temp_min`, `temp_sup`, `vent_inf`, `vent_max`, `vent_min`, `vent_sup`, `nom`, `user_id`) VALUES
(1, 15.3, 35.6, 5, 25.6, 2.3, 7.5, 1.5, 5.9, 'Football', 1),
(2, 18, 35, 12, 25, 2, 8, 0, 4, 'Jokari', 1),
(3, 15, 35, 10, 24, 8, 15, 4, 10, 'Planche à voile', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `activite_lieux`
--

CREATE TABLE `activite_lieux` (
  `activites_id` bigint(20) NOT NULL,
  `lieux_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `activite_lieux`
--

INSERT INTO `activite_lieux` (`activites_id`, `lieux_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(3, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Structure de la table `departement`
--

CREATE TABLE `departement` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `departement`
--

INSERT INTO `departement` (`id`, `nom`, `region_id`) VALUES
(1, 'Ille-et-Vilaine', 1),
(2, 'Morbihan', 1),
(3, 'Manche', 2);

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
  `id` bigint(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `departement_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `lieu`
--

INSERT INTO `lieu` (`id`, `latitude`, `longitude`, `nom`, `departement_id`) VALUES
(1, 48.11198, -1.67429, 'Rennes', 1),
(2, 48.649337, -2.025674, 'Saint-Malo', 1),
(3, 47.4833, -3.1167, 'Quiberon', 2),
(4, 49.116667, -1.083333, 'Saint-Lô', 3);

-- --------------------------------------------------------

--
-- Structure de la table `region`
--

CREATE TABLE `region` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `region`
--

INSERT INTO `region` (`id`, `nom`) VALUES
(1, 'Bretagne'),
(2, 'Normandie');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `login`, `mot_de_passe`, `nom`, `prenom`) VALUES
(1, 'jean.bambois@azertyuiop.xyz', 'jeanbambois', 'jbpassword', 'Bambois', 'Jean'),
(2, 'phil.deffaire@azertyuiop.xyz', 'phildeffaire', 'pdpassword', 'Deffaire', 'Phil'),
(3, 'alain.terieur@azertyuiop.xyz', 'alainterieur', 'atpassword', 'Terieur', 'Alain'),
(4, 'alex.terieur@azertyuiop.xyz', 'alexterieur', 'atpassword2', 'Terieur', 'Alex');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activite`
--
ALTER TABLE `activite`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfj4oiuys3hcg9kydxa879s1w2` (`user_id`);

--
-- Index pour la table `activite_lieux`
--
ALTER TABLE `activite_lieux`
  ADD KEY `FKe12eapi9y0o9q1djiybjmquk5` (`lieux_id`),
  ADD KEY `FK1vf1g3wffnp1tcluppk6pa0oy` (`activites_id`);

--
-- Index pour la table `departement`
--
ALTER TABLE `departement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh8gowqmuphi5bw438h1ikoal8` (`region_id`);

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrfqc32ci2a18p059hkfps6ihu` (`departement_id`);

--
-- Index pour la table `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activite`
--
ALTER TABLE `activite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `departement`
--
ALTER TABLE `departement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `region`
--
ALTER TABLE `region`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activite`
--
ALTER TABLE `activite`
  ADD CONSTRAINT `FKfj4oiuys3hcg9kydxa879s1w2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `activite_lieux`
--
ALTER TABLE `activite_lieux`
  ADD CONSTRAINT `FK1vf1g3wffnp1tcluppk6pa0oy` FOREIGN KEY (`activites_id`) REFERENCES `activite` (`id`),
  ADD CONSTRAINT `FKe12eapi9y0o9q1djiybjmquk5` FOREIGN KEY (`lieux_id`) REFERENCES `lieu` (`id`);

--
-- Contraintes pour la table `departement`
--
ALTER TABLE `departement`
  ADD CONSTRAINT `FKh8gowqmuphi5bw438h1ikoal8` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`);

--
-- Contraintes pour la table `lieu`
--
ALTER TABLE `lieu`
  ADD CONSTRAINT `FKrfqc32ci2a18p059hkfps6ihu` FOREIGN KEY (`departement_id`) REFERENCES `departement` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
