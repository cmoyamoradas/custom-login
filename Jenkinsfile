pipeline {
    //String branchName = "master"
    //String gitCredentials = "CREDENTIAL_ID"
    //String repoUrl = "https://github.com/cmoyamoradas/custom-login.git"
    //agent{ docker { image 'maven:3.8.4-openjdk-11-slim' } }
    agent any
    stages {
        /*
        stage ('Clone'){
            // Clones the repository from the current branch name
            echo 'Make the output directory'
            sh 'mkdir -p build'
            echo 'Cloning files from (branch: "' + branchName + '" )'
            dir('build') {
                git branch: branchName, url: repoUrl
            }     
        }
        */
        stage('Compile') { 
            steps {
                //sh 'mvn --version'
              
            }
        }
        stage('Test') { 
            steps {
                // 
            }
        }
        stage('Package') { 
            steps {
                // 
            }
        }
      stage('Deploy') { 
            steps {
                // 
            }
        }
    }
}
