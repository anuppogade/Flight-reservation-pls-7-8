pipeline{
    agent any 
    stages{
        stage('Pull'){
            steps{
                git branch: 'main', url: 'https://github.com/anuppogade/Flight-reservation-pls-7-8.git'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    mvn clean package
                '''
            }
        }
        stage('QA-Test'){
            steps{
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-token-2') {
                    sh '''
                        cd FlightReservationApplication
                        mvn sonar:sonar  -Dsonar.projectKey=flight-reservation-backend
                    '''
                }       
            }
        }
        stage('Docker-build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    docker build -t anuppogade/flight-reservation-demo:latest .
                    docker push anuppogade/flight-reservation-demo:latest
                    docker rmi anuppogade/flight-reservation-demo:latest
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    kubectl apply -f k8s/
                '''
            }
        }
    }
}
