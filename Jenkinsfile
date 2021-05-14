pipeline {
    agent {
        docker {
            image "maven:3.3.9-jdk-8"
            label "docker"
        }
    }
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
    }
}
