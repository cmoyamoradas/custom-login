pipeline {
    //agent any
  agent{ docker { image 'maven:3.8.4-openjdk-11-slim' } }
    stages {
        stage('Compile') { 
            steps {
                sh 'mvn --version'
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
