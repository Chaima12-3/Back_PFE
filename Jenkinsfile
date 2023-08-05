pipeline {
    agent { docker { image 'Apache Maven 4.0.0-alpha-4' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn compile quarkus:dev'
            }
        }
    }
}