pipeline {
    agent any
    tools {
        maven 'maven_3_8_5' 
    }

    stages {
        stage('Prepare workspace') {
            steps {
                echo 'Prepare workspace'
                checkout scm
            }
        }

        stage('Setup environment') {
            steps {
                echo 'Setup environment'
                
                script {                   
                    switch (env.BRANCH_NAME) {
                        case 'integration':
                            GLOBAL_ENVIRONMENT = 'integration'
                            break
                        case 'develop':
                            GLOBAL_ENVIRONMENT = 'sandbox'
                            break
                        case 'master':
                            GLOBAL_ENVIRONMENT = 'production'
                            break
                        default: 
                            GLOBAL_ENVIRONMENT = 'NO_DEPLOYMENT'
                            break
                    }
            }
        }  

        stage('Build') {
            steps {
                echo 'Build ' + GLOBAL_ENVIRONMENT

                script {
                    if (GLOBAL_ENVIRONMENT == 'NO_DEPLOYMENT') {
                        echo 'This branch should not be build'
                    } else {
                        build(GLOBAL_ENVIRONMENT)

                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy ' + GLOBAL_ENVIRONMENT

                script {
                    if (GLOBAL_ENVIRONMENT == 'NO_DEPLOYMENT') {
                        echo 'This branch should not be deploy'
                    } else {
                        build(GLOBAL_ENVIRONMENT)

                    }
                }
            }
        }


        stage ('Testing Stage') {
            steps {
                    sh 'mvn test'
            }
        }
    }
}


def build(ENVIRONMENT) {
    sh 'mvn clean compile'
    sh 'mvn test'
}

def deploy(ENVIRONMENT) {
     sh 'mvn deploy'
}