pipeline {
   agent any
    triggers {
        githubPush()
    }
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
   
  environment {
    DOCKERHUB_CREDENTIALS = credentials('agnieszkaq-dockerhub')
}

   stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''                
            }
        }
      
      
      stage('Docker Hub Login'){
    steps{
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password'
    }
}


      
  
        stage ('Build') {
            steps {
                sh 'mvn package'
            }
        }
        
        stage ('Test') {
            steps {
                sh 'mvn test'
            }
       }
      
         stage ('docker image build'){
            steps {
                sh 'docker build . -t spring_boot_app'
                  }
          }
      

      
      
      
  
   }
}
