pipeline {
    agent none

    stages {
        stage('Build and Test') {
            agent {
                docker {
                    image 'maven:3.8.1-adoptopenjdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            stages{
                stage('Build'){
                    steps {
                        sh '''
                            mvn -B -DskipTests clean package
                            mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
                            '''
                        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                    }
                }
                stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                    post {
                        always {
                            junit 'target/surefire-reports/*.xml'
                        }
                    }
                }
            }

        }

        stage('Docker Image') {
            agent any
            steps{
                script {
                  dockerImage = docker.build("eshopimage","-f ./jenkins/Dockerfile .")
                }
            }
        }
    }
}