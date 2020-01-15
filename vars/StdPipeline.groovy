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
          script {
            stages = ["local", "dev", "qa"];
            env.stage = input  message: 'Select a stage for this build',ok : 'Deploy',id :'stage_id',
            parameters:[choice(choices: stages, description: '', name: 'stage')]
          }
          echo "Deploying to ${env.stage}"
        }
      }
    }
  }
}
