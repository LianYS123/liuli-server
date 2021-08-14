pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
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
                sh 'nohup java -jar target/liuli-0.0.1-SNAPSHOT.jar &'
            }
        }
    }
}