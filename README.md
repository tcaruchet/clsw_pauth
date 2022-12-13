# Générateur de code d'authentification pour montre connectée
Ce projet a pour objectif de créer un générateur de code d'authentification pour une montre connectée permettant une double authentification, pour entrée dans un batiment ou une pièce d'une entreprise. 
Ce code est généré à partir des données GPS et de la fréquence cardiaque de l'utilisateur à l'instant T, et est valable uniquement pendant 30 secondes.

## Procédure d'installation
## APK android smartwatch (API 30) sur Android Studio
- Téléchargez et installez la dernière version d'Android Studio sur votre ordinateur (https://developer.android.com/studio).
- Téléchargez le code source de l'application sur GitHub (https://github.com/caruchet/smartwatch-auth-generator).
- Ouvrez Android Studio et sélectionnez "Ouvrir un projet existant".
- Sélectionnez le dossier contenant le code source que vous avez téléchargé à l'étape 2.
- Connectez votre montre connectée à votre ordinateur à l'aide d'un câble USB.
- Dans Android Studio, sélectionnez "Run > Run 'app'" pour lancer l'application sur votre montre connectée.

### Publication APK
- Ouvrez votre projet dans Android Studio.
- Dans le menu "Build", sélectionnez "Generate Signed Bundle / APK".
- Dans la fenêtre "Generate Signed Bundle / APK", sélectionnez "APK" dans le menu déroulant "Destination".
- Cliquez sur "Next".
- Si vous n'avez pas encore de clé de signature, cliquez sur "Create new" et suivez les instructions pour en créer une. Si vous avez déjà une clé de signature, sélectionnez-la dans la liste déroulante et entrez son mot de passe.
- Cliquez sur "Finish" pour générer l'APK signé.
- L'APK signé sera généré dans le répertoire indiqué dans les paramètres de votre projet.

Vous pouvez maintenant transférer l'APK signé sur votre appareil Android et l'installer en suivant les instructions de votre appareil.


## Lancement serveur C# ASP .NET Core 
- Téléchargez et installez Visual Studio 2022.
- Téléchargez et installez le SDK .NET Core 3.1.
- Ouvrez Visual Studio 2022 et ouvrez le projet .SLN du serveur.
- Installez les paquets NuGet nécessaires.
- Dans le menu "Déboguer", sélectionnez "Démarrer le débogage" pour lancer le serveur.

(si vous lancez le serveur vous-même : il sera nécessaire de changer l'endpoint dans les fichiers de l'application android. (tokens/TokenRetreiver.java)

## Utilisation 
- Installez l'application android smartwatch sur votre montre connectée.
- Lancez le serveur C# ASP .NET Core.
- Connectez-vous à l'application avec les informations d'utilisateur existant (tokenapi.caruchet.dev).
- Utilisez les données GPS et de fréquence cardiaque de l'utilisateur pour générer un code d'authentification valable pendant 30 secondes.
- Utilisez ce code pour accéder à un site, un espace ou un VPN de votre choix.

Endpoint tableau des tokens demandés (https://tokenapi.caruchet.dev/token/) et endpoint de génération (https://tokenapi.caruchet.dev/token/Generate/75/4.9876728/67.678999)


## Contributeurs
- Thomas CARUCHET - Adapter MainMenuAdapter
- François KEWE - Classe de données WearAuthenticator et WearAuthenticatorRepository
- Stéphane MAURIN - Fragments FragmentAddAuth et FragmentList
- Thomas CARUCHET - Classe de token TokenRetriever
- Thomas CARUCHET - Activités principales AuthActivity et MainActivity
- Stéphane MAURIN - Classe d'objet MenuItem
- Thomas CARUCHET et François KEWE - Développement de l'API en C# avec le framework ASP.NET Core

L'API en C# a été développée en utilisant le framework ASP.NET Core. Elle permet de générer un code d'authentification unique à partir des données GPS et de la fréquence cardiaque de l'utilisateur. Ce code est ensuite stocké dans une base de données SQL Server, permettant une visualisation des appels sur un endpoint spécifique.
(https://tokenapi.caruchet.dev/token/)
