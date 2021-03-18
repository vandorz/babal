<div align="center" style="margin-bottom:50px">

[![Babal](app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png)](https://github.com/vandorz/babal/)

# **Babal**

**Le meilleur jeu de boule du monde**

![Equipe](https://img.shields.io/badge/%C3%89quipe-Cracotte.-lightgrey?logo=github)
![Rendu](https://img.shields.io/badge/Rendu-19%2F03%2F2021-blue?color=red)
[![GitHub](https://img.shields.io/github/license/vandorz/babal?color=blue&logo=license)](LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/vandorz/babal?color=blue)](https://github.com/vandorz/babal/issues/)
[![Last commit](https://img.shields.io/github/last-commit/vandorz/babal?color=blue)](https://github.com/vandorz/babal/commits/main)
![Stability](https://img.shields.io/badge/stability-experimental-orange.svg)
![Android](https://img.shields.io/badge/Android%20API-24-success?logo=android)
[![Android CI with Gradle](https://github.com/vandorz/babal/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/vandorz/babal/actions/workflows/gradle.yml)

[Pull Requests](https://github.com/vandorz/babal/pulls) **·**
[Issues](https://github.com/vandorz/babal/issues/) **·**
[New Issue](https://github.com/vandorz/babal//issues/new)

</div>

## Installation

Il existe plusieurs moyens d'installer l'application sur votre tablette. L'API Android minimale requise est l'**API 24** (Android 7.0+).

### Android studio

Le moyen le plus simple sans s'y connaître est d'utiliser Android Studio. Voici un guide : [Build and run your app](https://developer.android.com/studio/run).

### Gradle

Vous pouvez vous passer d'Android Studio, et utiliser gradlew.
Ouvrez un terminal à la racine du projet, et tapez :

```bash
./gradlew assembleDebug
```

L'apk se trouve à cet emplacement : `app/build/outputs/apk/debug/app-debug.apk`.

Vous pouvez alors installer l'application depuis ce fichier.
Voici quelques tutoriels :

* [How to install third party apps without the Google Play Store](https://www.androidauthority.com/how-to-install-apks-31494/)
* [Comment installer un fichier APK sur son smartphone Android ?](https://www.clubic.com/tutoriels/article-844849-1-comment-installer-fichier-apk-telephone-android.html)
* [How to Install APK on Android](https://www.javatpoint.com/how-to-install-apk-on-android)
* [Android : comment installer ou désinstaller un fichier APK](https://www.phonandroid.com/tutoriel-installez-ou-desinstallez-un-fichier-apk.html)
* [Comment installer un fichier APK sur un smartphone ou une tablette Android ?](https://www.frandroid.com/comment-faire/tutoriaux/184151_comment-installer-un-fichier-apk-sur-son-terminal-android)

## Fonctionnalités

L'application Babal utilise les capteurs suivants : l'écran tactile, l'accéléromètre, et le capteur de lumière.

Le menu est lancé quand l'application est démarrée.
Il est possible de naviguer vers les autres vues depuis celle-ci, ou bien de quitter l'application. Le premier bouton permet de jouer, le second permet de consulter les règles qui régissent le jeu, le bouton suivant concerne les scores, et enfin le dernier bouton permet de quitter. Un bouton en haut à droite permet d'activer ou couper le son, une musique étant jouée lors du jeu.

Le jeu est d'apparence simple:

* Une balle apparait au milieu de l'écran et se déplace dans une direction.
* Appuyer sur l'écran augmente les points, mais cela provoque un changement de direction aléatoire de la balle. 
* La vitesse de la balle augmente au fur et à mesure.
* Plus la vitesse de la balle est élevée, plus un clic donne de points.
* Secouer le téléphone réinitialise la vitesse de la balle, mais son accélération augmente, la vitesse montera alors plus vite.
* La lumière fait disparaitre progressivement la balle, masquez le capteur de luminosité pour inverser le processus.
* Toucher un bonus donne des jokers qui feront rebondir la balle la prochaine fois qu'elle touchera un bord.

La vue des règles rappelle ses éléments.

La vue des scores affiche les scores locaux, ceux effectués sur votre tablette, qui sont enregistrés dans les `sharedPreferences`, et les scores globaux, de tout ceux ayant l'application, qui sont enregistrés dans **Firebase**.

Que ce soit pour les scores locaux ou globaux, seul le top 10 est affiché, alors battez-vous pour ~~écraser~~ vaincre vos ~~ennemis~~ camarades de jeu et inscrire votre nom sur l'intégralité du podium !

Langages supportés : Anglais, Français, Suédois.

## Équipe

Voici les membres de l'équipe **Cracotte.** :

*   <div>
        <img style="vertical-align:middle;border-radius:50%" width="50" src="https://github.com/amplul.png">
        <span style="margin-left:10px">Aymeric PINEL</span>
    </div>
*   <div>
        <img style="vertical-align:middle;border-radius:50%" width="50" src="https://github.com/CGuichard.png?size=50">
        <span style="margin-left:10px">Clément GUICHARD</span>
    </div>
*   <div>
        <img style="vertical-align:middle;border-radius:50%;" width="50" src="https://github.com/vandorz.png?size=50">
        <span style="margin-left:10px">Romain GOYHENEIX</span>
    </div>

## Licence

Babal est sous licence [**AGPL-3.0**](LICENSE).
