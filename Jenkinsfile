pipeline {
   agent any
    triggers {
        githubPush()
    }
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
   

   stages {
      
        stages {
          stage('Maven Install') {
           agent {
             docker {
                image 'maven:3.5.0'
                    }
                 }
            steps {
                 sh 'mvn clean install'
                  }
                }
        }
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
         
      stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t devops_intern .'
      }
    }
         
    }
}
