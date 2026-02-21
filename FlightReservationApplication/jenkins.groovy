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
        stage('Docker-build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    docker build -t mayurwagh/flight-reservation-demo:latest .
                    docker push mayurwagh/flight-reservation-demo:latest
                    docker rmi mayurwagh/flight-reservation-demo:latest
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
