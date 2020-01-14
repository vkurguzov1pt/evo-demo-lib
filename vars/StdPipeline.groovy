def call() {
  pipeline {
    agent any
    stages {
      stage('Build'){
        steps {
          echo 'This is Build stage'
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
