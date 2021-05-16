# devops_exercise

***Create simple spring boot aplication and then build it in a Jenkins
pipeline every time a change is pushed to version control and then create
docker image and push to docker hub.***

## Table of contents
* [Stage 1](#stage-1) : *Create Spring Boot Palindrome Application*
* [Stage 2](#stage-2) : *Download Jenkins, run on localmachine*
* [Stage 3](#stage-3) : *Connect Jenkins to github repository*
* [Stage 4](#stage-4) : *Create a Jenkins Pipeline*
* [Stage 5](#stage-5) : *Build Spring Boot Application as a docker image in Jenkins*
* [Stage 6](#stage-6) : *Extend Jenkinsfile, by adding stages to build Spring Boot App and push into Docker Hub*
* [Stage 7](#stage-7) : *Extra informations*

### Stage 1
*Create a simple microservice with a single HTTP endpoint based on spring
boot, to check that given string is a Palindrome.
Requirements: use spring boot and maven tool to build this application.
Solution should be pushed into Github public repository.*

1. Go to: [Spring Boot Initializer](https://start.spring.io/). During initalization process add *Web* dependencies, then generate your Maven project to your favoutrite IDE.
2. Create Palindrome class, where it will be static method which shows the text is palindrome or no.
3. Create RestController class, to put HTTP get endpoint.
4. Add test, to *PalindromeApplicationTests*, which validates if name:"Aga" is palindrome.
5. To check, is string is palindrome, write in browser adress: 
  ```
  http://localhost:8080/isPalindrome?text=yourSpecyficWord
  
  ```
5. At browser you will get information, is that palindrome (information below "true"), or no (information below "false").

### Stage 2
*Download Jenkins and run as a docker image on your local machine. Write
step by step how to do it.*

Before we download Jenkins we need to have docker on machine, to create our image with Jenkins. 
There are steps for Linux Mint (is my system):
1. On console please write:
  ```
  sudo apt-get update
  sudo apt-get remove docker docker-engine docker.io
  sudo apt install docker.io
  sudo systemctl start docker
  ```
2. Next step is to create Jenkins container in Docker, with special privileged, that u can later in **Stage 5** build Docker Image inside Jenkins, wtihout this You will get errors (docker not found), like me before, when I did'n know about this:
  ```
  docker run -u 0 --privileged --name jenkins -d -p 8080:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts

  docker ps
  ```
  get container ID numer from Jenkins image, and then write:
  ```
  docker logs CONTAINER_ID
  ```
  Save the password process instaltion and put into page on brwoser with localhost adress with port 8080: **localhost:8080**
  On adress **localhost:8080**, click on *Install suggested plugins*, wait to installation process would be done, it takes some time.
  After that,fill the inputs and create frist administrator. 
  Jenkins is ready to use!

### Stage 3
*Connect Jenkins to github repository.*

On main Jenking page, go to: ***Manage Jenkins** -> **Manage plugins** -> **Available*** , then write into inputs: ***GitHub Integration Plugin***, click on checkbox and to comfirm click on button with information: ***Download now and install after restart***.

### Stage 4
*Create a Jenkins Pipeline that builds your spring boot applicationa when
changes are pushed to Github.*

Then on main page click on ***New Item***, then write project name, and click ***Pipeline***
In ***General*** options, check ***GitHub project***, and set URL to spring boot app.
In section *Build Triggers* check ***GitHub hook trigger for GITScm polling***.
In definiction's option, select an option ***Pipeline script from SCM*** -> ***SCM*** -> ***GIT***.
In *Repository URL*, put https://github.com/agnieszkaq/devops.git. Don't forget to put branch, it will be *main*.
The finall check is to wrtie script path - *Jenkinsfile*.

To push GitHub changes we will use Webhooks. Mine Jenkins server is on localhost adress, the problem is that Jenkins server is sitting behind a firewall and/or NAT, so the GitHub webhook cannot be delivered to your localhost Jeninks server. 
We need to use proxy server, that will be connection between GitHub and Jenkins. SocketXP helps create a secure webhook relay tunnel between the Public GitHub and your Private Jenkins server through which SocketXP will proxy the webhook notification from the public GitHub to main localhost Jenkins.

Go to [SocketXP](https://www.socketxp.com/) webpage, create an account. Get a unique JWT auth token assigned just for you.
Then in Linux console wrtie 
``` 
$ socketxp login < your-auth-token >
$ socketxp connect <your-auth-token>
```
In console, You will get Public URL, please copy that. 

Next:
1. In Jenkins:
    - go to -> http://localhost:8080/pluginManager/available, and install GitHub Integration plugin,
    - go to -> http://localhost:8080/configureTools/, to configure Git path executable, write your patch (mine is */usr/bin/git*, you can check in Linux putting command *which git*).
     
2. In GitHub:
    - go to *Project Settings* -> *Webhooks* -> fill *Payload URL*, with your Jenkins adress -that socketxp generate, like this <socketxp_adress>/github-webhook/

3. Then, you can check is working, pushing your git command.

![image](https://user-images.githubusercontent.com/59511312/118279307-47268680-b4cb-11eb-8658-40a025326ac9.png)


### Stage 5
*Wrap your application in a Docker image, building it in Jenkins. Check
that your container is running.*

1. In Linux console, find your Jenkins Container ID:
```
docker ps
```
2. Go inside the container:
```
docker exec -it -u root CONTAINER_ID /bin/bash
```
3. Inside directory with the project *:/var/jenkins_home/workspace/devops_intern*, build the project:
```
docker build . -t spring_app
```
4. Then, check if the continer with Spring Boot Application is running, by putting command:
```
docker run -p 8081:8081 spring_app

```
Below u can see the print screen from console:

![image](https://user-images.githubusercontent.com/59511312/118398687-08730680-b65a-11eb-9a6f-a2648f963b8e.png)

You can see, that on adress *http://localhost:8081/isPalindrome?text=yourSpecyficWord*, the application is running.
![image](https://user-images.githubusercontent.com/59511312/118399556-0743d880-b65e-11eb-9659-3f0ff53bd174.png)

### Stage 6
*Extend your jenkins pipeline to include stages to build docker image and
push it to Docker Hub (use free acount).*

Firstly, create Your Docker Hub account. Then, we need to generate access token, follow the steps:
click on your username on the top right, next *Accoutn Setting* ->  *Security* -> *New access token*. Please, save the token. This token it will be added to *Manage Credentials* in Jenkins, to create connection beetween Jenkins and Docker Hub. Add this, token, this process is similary like with GitHub. 

Then, we need to create repository in Docker Hub. The name of repository will be *palindrome*.
Next, in Jenkinsfile add enviroment:
```
environment {
        DOCKERHUB_CREDENTIALS = credentials('agnieszkaq-dockerhub')
   }
```
and stages:
```
stage ('docker image build'){
        steps {
               sh 'docker build . -t spring_boot_app'
               }
       }    
 stage('Docker Hub Login'){
            steps{
               sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW'
              }
         }
     
  stage ('Push  to docker hub'){
            steps {
               sh 'docker tag spring_boot_app agnieszkaq/palindrome:latest'
               sh 'docker push agnieszkaq/palindrome:latest'
            }
         }

```
After git commit, Spring Soot application is added to Docker Hub!
To download Palindrome Spring Boot Application, write in console:
```
docker pull agnieszkaq/palindrome:latest
```
Then run, conteiner with application:
```
docker run -p 8081:8081 agnieszkaq/palindrome:latest
```
# The application is running!
Now, in browser You can go to: *http://localhost:8081/isPalindrome?text=yourSpecyficWord*, to check is your word is palindrome.
Also, You can see my repository on Docker Hub: https://hub.docker.com/repository/docker/agnieszkaq/palindrome

### Stage 7
*Write README.md to allow all newbe clone source code of you application
from github and repeat step by step all stages from 2-7.*

You can download the source code, from github, then start from *Stage 1.4* and follow next steps.
In other case you can create Java Spring Boot Palindrome app on your own, like in steps above.

#### Done :) 
