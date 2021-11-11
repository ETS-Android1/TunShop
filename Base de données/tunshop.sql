--
-- Base de données :  `tunshop`
--

-- --------------------------------------------------------

--
-- Structure de la table `cartefid`
--

CREATE TABLE IF NOT EXISTS `cartefid` (
  `code_carte` varchar(30) NOT NULL,
  `libf` varchar(100) NOT NULL,
  `nbrpoints` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `validite` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `cartefid`
--

INSERT INTO `cartefid` (`code_carte`, `libf`, `nbrpoints`, `mid`, `validite`) VALUES
('2423492384921', 'Wafa', 33290, 3, '2015-12-12'),
('3234897289341', 'Cartetna', 1000, 1, '2015-12-12'),
('4444444444411', 'LACARTE', 2000, 2, '2015-12-12'),
('4492492744791', 'Carte FID', 4000, 4, '2015-12-12');

-- --------------------------------------------------------

--
-- Structure de la table `geo_point`
--

CREATE TABLE IF NOT EXISTS `geo_point` (
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `id_g` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `geo_point`
--

INSERT INTO `geo_point` (`lat`, `lng`, `id_g`) VALUES
(37.2696852, 9.8684317, 1),
(37.2709531, 9.8684317, 2),
(35.7727031, 10.8267955, 3),
(36.84502, 11.094997, 4),
(36.652892, 10.589627, 5),
(36.414595, 10.651523, 6),
(36.747477, 10.310263, 7),
(36.809349, 10.13831, 8),
(36.812368, 10.181006, 9),
(36.9021707, 10.090727799999968, 10),
(34.755547, 10.762419, 11),
(34.74087, 10.760793, 12),
(34.740717, 10.757468, 13),
(35.8149993, 10.6302916, 15),
(35.8319541, 10.6283497, 14),
(35.829468, 10.641943, 16),
(35.504852, 11.064791, 17),
(35.5070159, 11.0523051, 18),
(35.644666, 10.889978, 19),
(35.7723113, 10.830935, 20),
(36.8525139, 10.1722482, 21),
(36.856124, 10.157852, 22),
(36.848376, 10.18077, 23);

-- --------------------------------------------------------

--
-- Structure de la table `magasin`
--

CREATE TABLE IF NOT EXISTS `magasin` (
  `mid` int(11) NOT NULL,
  `libm` varchar(100) NOT NULL,
  `logo` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `magasin`
--

INSERT INTO `magasin` (`mid`, `libm`, `logo`) VALUES
(1, 'Magasin General', 'mg.png'),
(2, 'Monoprix', 'monoprix.png'),
(3, 'Carrefour', 'carrefour.png'),
(4, 'Geant', 'geant.png');

-- --------------------------------------------------------

--
-- Structure de la table `magasin_produit`
--

CREATE TABLE IF NOT EXISTS `magasin_produit` (
  `codeP` varchar(30) NOT NULL,
  `mid` int(11) NOT NULL,
  `prixP` double NOT NULL,
  `desProd` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `magasin_produit`
--

INSERT INTO `magasin_produit` (`codeP`, `mid`, `prixP`, `desProd`) VALUES
('4005808801046', 1, 2.6, 'ette creme Nivea est certes agreable a utiliser pour bien des personnes '),
('4005808801046', 2, 2.8, 'ette creme Nivea est certes agreable a utiliser pour bien des personnes '),
('4005808801046', 3, 3.1, 'ette creme Nivea est certes agreable a utiliser pour bien des personnes '),
('4005808801046', 4, 2.7, 'ette creme Nivea est certes agreable a utiliser pour bien des personnes '),
('44005809846206', 1, 10.8, 'Une creme riche et hydrantante qui adoucit les peaux les plus seches.'),
('44005809846206', 2, 10.7, 'Une creme riche et hydrantante qui adoucit les peaux les plus seches.'),
('44005809846206', 3, 11, 'Une creme riche et hydrantante qui adoucit les peaux les plus seches.'),
('44005809846206', 4, 9.8, 'Une creme riche et hydrantante qui adoucit les peaux les plus seches.'),
('6192011803672', 1, 0.15, 'Mouchoirs Lilas Nett Classic'),
('6192011803672', 2, 0.2, 'Mouchoirs Lilas Nett Classic'),
('6192011803672', 3, 0.15, 'Mouchoirs Lilas Nett Classic'),
('6192011803672', 4, 0.21, 'Mouchoirs Lilas Nett Classic'),
('61924300038068', 1, 3.6, 'offre une protection efficace contre les odeurs de transpiration et d''humidite'),
('61924300038068', 2, 4, 'offre une protection efficace contre les odeurs de transpiration et d''humidite'),
('61924300038068', 3, 3.8, 'Sans alcool ni colorant vous assure un respect total de la peau '),
('61924300038068', 4, 3.7, 'Sans alcool ni colorant vous assure un respect total de la peau '),
('6194001800111', 1, 0.6, 'Eaux mineral SAFIA'),
('6194001800111', 2, 0.65, 'Eaux mineral Safia '),
('6194001800111', 3, 0.65, 'Eaux mineral SAFIA'),
('6194001800111', 4, 0.5, 'Eaux mineral SAFIA'),
('6194002510194', 1, 0.9, ' Un delicieux. biscuit sandwiche. Fabrique a votre. intention a partir des meilleures cereales'),
('6194002510194', 2, 1, ' Un delicieux. biscuit sandwiche. Fabrique a votre. intention a partir des meilleures cereales'),
('6194002510194', 3, 0.95, ' Un delicieux. biscuit sandwiche. Fabrique a votre. intention a partir des meilleures cereales'),
('6194002510194', 4, 1.1, ' Un delicieux. biscuit sandwiche. Fabrique a votre. intention a partir des meilleures cereales'),
('6194043401000', 2, 0, 'makrouna Fell2 500g'),
('6194043401000', 3, 0, 'makrouna Fell2 500g'),
('6194043401000', 4, 0, 'makrouna Fell2 500g'),
('8003520044082', 1, 5, 'Sa formule vous assure une protection optimale pendant 24h'),
('8003520044082', 2, 5, 'le deodorant Tahiti enrichi en en extraits naturels vous offre le plaisir d''un parfum frais et gourmand'),
('8003520044082', 3, 5, 'Sa formule vous assure une protection optimale pendant 24h'),
('8003520044082', 4, 5, 'le deodorant Tahiti enrichi en en extraits naturels vous offre le plaisir d''un parfum frais et gourmand');

-- --------------------------------------------------------

--
-- Structure de la table `pointsvente`
--

CREATE TABLE IF NOT EXISTS `pointsvente` (
  `id_pv` int(11) NOT NULL,
  `idm` int(11) NOT NULL,
  `ville` varchar(50) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `ouverture` varchar(70) NOT NULL,
  `fermeture` varchar(70) NOT NULL,
  `id_g` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `pointsvente`
--

INSERT INTO `pointsvente` (`id_pv`, `idm`, `ville`, `adresse`, `ouverture`, `fermeture`, `id_g`) VALUES
(1, 1, 'Bizerte', 'avenue Bourguiba', '8:00', '22:00', 1),
(2, 2, 'Bizerte', 'Ave Habib Bourguiba', '08:30', '22:00', 2),
(3, 3, 'Mounastir', 'L'' Independence,Monastir 5000', '8:30', '22:00', 3),
(4, 1, 'Nabeul', 'Avenue de Martyrs-Kelibia', '08:00', '22:00', 4),
(5, 1, 'Nabeul', 'C42,Beni Khalled', '08:30', '22:00', 5),
(6, 2, 'Nabeul', 'C28,Mrezga', '08:30', '22:00', 6),
(7, 2, 'Tunis', 'Rue JILANI MERCHEN,Ez Zahra', '08:30', '22:00', 7),
(8, 3, 'Tunis', 'Avenue Habib Bourguiba', '08:00', '22:00', 8),
(9, 1, 'Tunis', 'Rue de La Palestine,Tunis 1002', '08:30', '22:00', 9),
(10, 4, 'Tunis', 'Centre commercial Tunis City 2032-Ariana', '09:00', '22:00', 10),
(11, 1, 'Sfax', 'route de tunis km 2, immeuble nafissa', '08:00', '22:00', 11),
(12, 2, 'Sfax', 'Avenue des Martyrs,Sfax 3000', '08:00', '22:00', 12),
(13, 3, 'Sfax', 'Avenue Carthage,Sfax 3027', '08:00', '22:00', 13),
(14, 2, 'Sousse', 'Rue Tarek Ibn Zied Sousse 4000', '08:00', '22:00', 14),
(15, 3, 'Sousse', 'Rue Jaafar Mansour', '08:30', '20:00', 15),
(16, 1, 'Sousse', 'Rue de l''Independance ', '08:00', '21:00', 16),
(17, 1, 'Mahdia', 'Ave Hbib Bourguiba', '08:00', '21:00', 17),
(18, 3, 'Mahdia', 'Rue 2 Mars 1934', '08:00', '21:00', 18),
(19, 1, 'Mounastir', 'Rue Neo Destour Ksar Hellal', '08:30', '21:00', 19),
(20, 2, 'Mounastir', 'Avenu Trimeche', '08:00', '22:00', 20),
(21, 1, 'Ariana', 'Cite Olympique Tunis 1003', '08:30', '21:00', 21),
(22, 2, 'Ariana', 'Avenue de Hedi Nouira', '08:30', '21:00', 22),
(23, 3, 'Ariana', '3 avenue d''Afrique', '08:00', '22:00', 23);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE IF NOT EXISTS `produit` (
  `codeP` varchar(30) NOT NULL,
  `libP` varchar(50) NOT NULL,
  `img` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`codeP`, `libP`, `img`) VALUES
('4005808801046', 'Creme Nivea', 'cremeboite.png'),
('44005809846206', 'Tube Creme Nivea', 'tubenivea.jpg'),
('6192011803672', 'Papier mouchoir Lilas', 'lilas.png'),
('61924300038068', 'Roll-on Souplesse', 'souplesserollon.jpg'),
('6194001800111', 'Eaux Safia', 'safia.png'),
('6194002510194', 'Bscuit Smile', 'smile.jpg'),
('6194043401000', 'Fell2', 'fell2.jpg'),
('8003520044082', 'Deodorant Tahiti ', NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `cartefid`
--
ALTER TABLE `cartefid`
  ADD PRIMARY KEY (`code_carte`), ADD KEY `mid` (`mid`);

--
-- Index pour la table `geo_point`
--
ALTER TABLE `geo_point`
  ADD KEY `id_g` (`id_g`);

--
-- Index pour la table `magasin`
--
ALTER TABLE `magasin`
  ADD PRIMARY KEY (`mid`);

--
-- Index pour la table `magasin_produit`
--
ALTER TABLE `magasin_produit`
  ADD PRIMARY KEY (`codeP`,`mid`), ADD KEY `mid` (`mid`);

--
-- Index pour la table `pointsvente`
--
ALTER TABLE `pointsvente`
  ADD PRIMARY KEY (`id_pv`), ADD KEY `co_ma` (`idm`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`codeP`);

