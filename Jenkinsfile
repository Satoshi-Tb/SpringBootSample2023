pipeline {
    agent any
    tools {
     maven "mvn3"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('input') {
            steps {
                input message: 'Do you want to deploy to staging?', ok: 'Yes, let\'s do it!'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo deploy!'
            }
        }
    }
}
