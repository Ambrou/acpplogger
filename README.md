# ACPPLogger
## Projet Ambroise CPP Logger plugin pour Jenkins

### Commandes Maven
#### Construire le plugin

	mvn clean install
	
#### Tester le plugin dans un Jenkins embarqué dans Jetty
Pour tester le plugin il suffit de le lancer dans un Jenkins embarqué dans Jetty avec la commande suivante:

	mvn hpi:run -Djetty.port=8092
 
### Liens utiles
[Jenkins Plugin tutorial](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-Eclipse) 


