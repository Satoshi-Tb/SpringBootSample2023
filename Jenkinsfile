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
            input {
                message "What is your first name?"
                ok "Submit"
                parameters {
                  string(defaultValue: 'Dave', name: 'FIRST_NAME', trim: true) 
                }
            }
            options {
                timeout(time: 10, unit: 'SECONDS') 
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo deploy!'
            }
        }
    }
}
