# ACPPLogger
## Projet Ambroise CPP Logger plugin pour Jenkins

### Commandes Maven
#### Construire le plugin

	mvn clean install
	
#### Tester le plugin dans un Jenkins embarqu� dans Jetty
Pour tester le plugin il suffit de le lancer dans un Jenkins embarqu� dans Jetty avec la commande suivante:

	mvn hpi:run -Djetty.port=8092
 
### Liens utiles
[Jenkins Plugin tutorial](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-Eclipse) 
[Jenkins Plugin tutorial jelly](https://wiki.jenkins-ci.org/display/JENKINS/Basic+guide+to+Jelly+usage+in+Jenkins)
[Jenkins Plugin tutorial jelly 2](https://blog.codecentric.de/en/2012/08/tutorial-create-a-jenkins-plugin-to-integrate-jenkins-and-nexus-repository/)
