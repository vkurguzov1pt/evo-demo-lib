def call() {
  pipeline {
    agent any
    stages {
      stage('Build'){
        steps {
          echo 'This is Build stage'
          echo 'Compile app'
          sh '''
            docker run --rm -v $HOST_HOME/$JOB_NAME:/usr/src/myapp -w /usr/src/myapp golang:1.8 go build -v
          '''
          echo 'Build app' 
          script {
            env.tag = input(message: 'Set tag for the build',ok : 'Set', id :'app_id',
              parameters:[string(name: 'appTag', description: '', defaultValue: 'app')]
            )
          }
          sh "docker build -t ${env.tag} ."
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
          sh "docker run --rm -d --name ${env.tag}-${env.stage} -p${params.appPort}:8081 ${env.tag}"

          script {
            yesorno = ["yes","no"]
            env.finish = input  message: 'Choose yes if you want to stop build',ok : 'Stop',id :'isfinished',
            parameters:[choice(choices: yesorno, description: '', name: 'stopbuild')]

            if(env.finish == "yes"){
              sh  "docker stop ${env.tag}-${env.stage}"
              currentBuild.result = 'SUCCESS'
            }
          }
        }
      }
    }
  }
}
