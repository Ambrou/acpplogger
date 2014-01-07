# ACPPLogger
## Projet Ambroise CPP Logger plugin pour Jenkins

### Commandes Maven
#### Construire le plugin

	mvn clean package
	
#### Tester le plugin dans un Jenkins embarqué dans Jetty
Pour tester le plugin il suffit de le lancer dans un Jenkins embarqué dans Jetty avec la commande suivante:

	mvn hpi:run -Djetty.port=8092
	
Ensuite aller dans votre navigateur à l'adresse suivante http://localhost:8092/jenkins 
 
### Liens utiles
  * [Jenkins Plugin tutorial](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-Eclipse) 
  * [Jenkins Plugin tutorial jelly](https://wiki.jenkins-ci.org/display/JENKINS/Basic+guide+to+Jelly+usage+in+Jenkins)
  * [Jenkins Plugin tutorial jelly 2](https://blog.codecentric.de/en/2012/08/tutorial-create-a-jenkins-plugin-to-integrate-jenkins-and-nexus-repository/)

### Autres informations

#### Packages Eclipse utilisés
  * Eclipse EGit version 3.0.0
  * Eclipse.org - m2e version 1.4.0

#### Installation externe à Eclipse :
  * Apache Maven version 3.1.1 à ajouter ensuite dans Windows/Preferences/Maven/installations