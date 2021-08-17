pipeline {
    agent any;
    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage("Run") {
            steps {
                sh 'chmod 776 ./scripts/deliver.sh'
                sh './scripts/deliver.sh'
            }
        }
    }
}