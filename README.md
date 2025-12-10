# Coach_app
Application Android créée dans le cadre d'un projet de cours.<br>
L'utilisateur doit indiquer son sexe, sa taille, son poids et son age, puis cliquer sur calculer.<br>
L'application calcule alors son indice de masse grasse, tel que :<br>
img = (1.2 * poids/taille²) + (0.23 * age) - (10.83 * sexe) - 5.4 <br>
*(notez que : la taille est en mètre, et que "sexe" est reglé sur 0 pour les femmes, et 1 pour les hommes)*<br>

## Sauvegarde des données
Dans la branche "master", es données sont stockées dans une base de données MySQL en local, accessible via une API REST.<br>
Si ça ne vous convient pas, on trouve deux branches alternatives, qui offrent d'autres méthodes de stockages :<br>
- "serialisation", dans lequel on stock le dernier profil dans un fichier json sur le téléphone ;<br>
- "SQLite", dans lequel on crée sur le téléphone une BdD au format SQLite pour le stockage des informations. On peut donc stocker chaque profil, bien qu'on ne fasse rien avec cette information et qu'elle n'est accessible nulle part.<br>
