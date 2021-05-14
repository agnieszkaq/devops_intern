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
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
               
            
               def dockerHome = tool 'myDocker'
               env.PATH = "${dockerHome}/bin:${env.PATH}"
   
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
