pipeline {
    agent any

    tools {
        maven 'Maven 3.x' // Nombre del Maven configurado en Jenkins
        jdk 'Java 11'     // Nombre del JDK configurado en Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Descarga el código del repositorio
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Compilación del proyecto
                bat 'mvn clean compile'
            }
        }

        stage('Test & Coverage') {
            steps {
                // Ejecución de pruebas unitarias y cobertura de JaCoCo
                bat 'mvn test'
            }
            post {
                always {
                    // Publica los reportes de JUnit en Jenkins
                    junit '**/target/surefire-reports/*.xml'
                    // Analiza la cobertura de código
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Envía el código a SonarQube. "SonarQubeServer" es el nombre del servidor configurado en Jenkins.
                withSonarQubeEnv('SonarQubeServer') {
                    bat 'mvn sonar:sonar'
                }
            }
        }

        stage('Checkmarx Security Scan') {
            steps {
                // Integración típica para análisis estático de vulnerabilidades de seguridad con Checkmarx
                step([$class: 'CxScanBuilder', 
                    credentialsId: 'checkmarx-creds',
                    serverUrl: 'https://checkmarx.tu-institucion.edu',
                    projectName: 'RestaurantePOSWeb',
                    groupId: '1',
                    preset: '36', // Filtro de escaneo (ej. OWASP Top 10)
                    vulnerabilityThresholdResult: 'FAILURE'
                ])
            }
        }
    }
}
