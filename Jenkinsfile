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
   registry = "agnieszkaq//palindrome"
   registryCredential = 'agnieszkaq-dockerhub'
   dockerImage = 'spring_boot_app'
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
      
      stage('Deploy Image') {
  steps{
    script {
      docker.withRegistry( '', registryCredential ) {
        dockerImage.push()
      }
    }
  }
}
      
  
   }
}
