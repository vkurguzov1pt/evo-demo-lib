def call() {
  pipeline {
    agent any
    stages {
      stage('Build'){
        steps {
          echo 'This is Build stage'
          echo "This is param ${params.myParameterName}"
        }
      }
      stage('Deploy'){
        steps {
          echo 'This is Deploy stage'
        }
      }
    }
  }
}
