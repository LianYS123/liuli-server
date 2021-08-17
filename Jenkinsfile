pipeline {
    agent {
        docker {
            image 'maven:3.8.2-jdk-11-slim'
            args '-v /root/.m2:/root/.m2'
        }
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