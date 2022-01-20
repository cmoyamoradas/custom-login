pipeline {
    //String branchName = "master"
    //String gitCredentials = "CREDENTIAL_ID"
    //String repoUrl = "https://github.com/cmoyamoradas/custom-login.git"
    agent{ docker { image 'maven:3.8.4-openjdk-11-slim' } }
    //agent any
    stages {
        stage('Compile') { 
            steps {
                sh 'mvn --version'
            }
        }
        stage('Test') { 
            steps {
                sh 'mvn --version'
            }
        }
        stage('Package') { 
            steps {
                sh 'mvn --version' 
            }
        }
      stage('Deploy') { 
            steps {
                sh 'mvn --version' 
            }
        }
    }
}
