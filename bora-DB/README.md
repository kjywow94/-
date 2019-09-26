- # AWS에 도커 설치 후 도커에 mySql 설치

  

  ## AWS에 도커 설치

  - sudo apt update : 설치는 apt를 이용해서 설치할 것임.
  - sudo apt install apt-transport-https
  - sudo apt install ca-certificates
  - sudo apt install curl : 컬은 특정한 웹사이트에서 데이터를 다운받을때 사용.
  - sudo apt install software-properties-common

  

  ##### Docker 공식 GPG 키 등록

  - curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

     

  ##### 저장소 설정 

  - sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"

  - sudo apt update

    

  ##### 도커설치

  - apt-cache policy docker-ce
  - sudo apt install docker-ce 

  

  ##### 도커는 설치하면 자동으로 시스템 서비스로 등록돼서 언제 어디서나 편하게 부를 수 있음.

  - sudo systemctl status docker

  

  ##### TEST로 hello-world 띄어줌.

  - sudo docker pull hello-world
    - 실제로 컨테이너로 만들어 띄어보는 것.
      곧바로 우리 서버위에 하나의 서버가 별도로 생성됨.
  - sudo docker run hello-world

  

  ##### 어떤 컨테이너가 동작했는지 확인

  - sudo docker ps -a

  

  ##### 해당 도커 컨테이너 삭제

  - sudo docker rm 7c4d1bf58537

  

  ##### 이미지 파일 확인

  - sudo docker images

  

  ## 도커에 mySql 설치

  ##### 하리보 mysql 실행

  - 어드민 아이디와 비번 = root/hariboadmin
  - 사용자 아이디와 비번 = haribo/haribo

  - pull
  
- sudo docker pull emblockit/haribo-mysql
  
  - 컨테이너 실행
  
  - sudo docker run -d -p 3306:3306 --name haribo-mysql emblockit/haribo-mysql
  
  - docker container의 세부정보로 아이피 주소 확인

    - sudo docker inspect 6f83c1840f9d

  - mysql client  설치 후 접속
  
  - sudo apt install mysql-client-core-5.7
    - mysql -u root -p --host 172.17.0.2 --port 3306

    

  ##### 컨테이너의 데이터를 호스트 OS에 저장하기
  
  - 컨테이너를 제거하면 데이터도 함께 제거된다.
- 그래서 컨테이너의 데이터를 호스트 OS에 저장하기
  
  - 도커는 컨테이너를 docker run 명령어로 생성할 때 컨테이너의 특정 디렉터리와 호스트 OS의 특정 디렉터리를 서로 공유할 수 있도록 -v [호스트의 디렉터리]:[컨테이너의 디렉터리] (또는 --volume)옵션을 제공
  
- aws 우분투에 폴더만들고, 기존 컨테이너를 종료 후 -v 옵션을 추가해 마운트 시킨다.( 컨테이너와 호스트가 디렉터리를 공유한다 라는 말이 맞을 수 있지만 그보다는 호스트의 특정 디렉터리(지정해준)를 컨테이너의 특정 경로에 마운트 시킨다 라고 할 수 있다.)
  - sudo docker run -d -p 3306:3306 -v /home/ubuntu/boraCoin/mysql:/var/lib/mysql --name boraCoin-mysql emblockit/haribo-mysql

  

  

  ## Jupyter Notebook 설치, HTTPS 적용, 시스템 서비스 설정하기

  - gui 환경으로 서버를 관리하는 쥬피터 노트북 설치.
  
  - 쥬피터 노트북은 웹브라우져 환경에서 해당 서버에 바로 접근해서 서버를 관리할 수 있게 해줌
  - sudo apt-get update
  - python3 //그냥 확인
  - 파이썬 관련 패키지를 설치할 수 있도록 도와주는 pip설치
    
    - sudo apt-get install python3-pip 
- sudo pip3 install notebook
    - 이제 만들어진 쥬피터 노트북을 이용해서, 서버의 외부에서 웹브라우저를 이용해서 해당 서버의  접속할 수 있도록. 하지만 아무나 접속하면 안되니까 비밀번호를 설정하자.
    쥬피터 노트북은 기본적으로 sha-1이라는 일종의 해쉬알고리즘을 이용해서 비밀번호를 기록할 수 있게 해줌.
  
- python3
  
- from notebook.auth import passwd
  
- passwd() //0506
  
- 비번 해쉬값 : sha1:4847ead73c9b:9b65efec05a0f436d73e53204514b5168b4e6d9c
  
  
  
- 서버에서 쥬피터노트북을 실행해서 외부에서 접속을 했을때 비밀번호를 입력해야만 접속할 수 있는 쥬피터 환경설정
  
  - 이를 위해서 쥬피터 환경설정 파일을 만듬.
  
    - jupyter notebook --generate-config
    - vi /root/.jupyter/jupyter_notebook_config.py
    - 맨 밑에 붙임.
      c = get_config()
    c.NotebookApp.password = u'sha1:4847ead73c9b:9b65efec05a0f436d73e53204514b5168b4e6d9c'
      c.NotebookApp.ip='172.26.15.32'
    c.NotebookApp.notebook_dir = '/'
  
  
  
- 쥬피터 노트북을 루트 권한으로 실행
  
  - sudo jupyter-notebook --allow-root
  
- 퍼플리 ip:port로 브라우져로 접속하면 이용가능해짐
  
  - 13.125.178.26:8888
  
  
  
  - 쥬피터 노트북을 항상 실행시키고 싶으면,
  
    - ctrl + Z키로 해당 서버를 잠시 종료시켜주고,
  - bg //백그라운상태로 돌아가게 하고
    - disown -h //소유권을 포기?하면 쥬피터 노트북이 항상 실행중으로 사용할 수 있음.
  - ls
  
  
  
- ssl 인증서가 적용되지 않은 상태라서 https를 적용하는 방법
  
  - 현재 실행중인 주피터 노트북을 종료.
  
  - sudo netstat -nap | grep 8888
  
  - sudo kill -9 23465
  
  - mkdir ssl_jupyter
  
  - cd ssl
  
  - sudo openssl req -x509 -nodes -days 365 -newkey rsa:1024 -keyout "cert.key" -out "cert.pem" -batch
  
    - vi /root/.jupyter/jupyter_notebook_config.py
  
    - 내용추가
        c.NotebookApp.certfile = u'/home/ubuntu/boraCoin/ssl_jupyter/cert.pem'
      c.NotebookApp.keyfile = u'/home/ubuntu/boraCoin/ssl_jupyter/cert.key'
  
    - sudo jupyter-notebook --allow-root

      - 사설인증서라서 크롬이 우리 인증서를 신뢰하지 못하는데, 그냥 무시하고 실행함.
      이제 우리는 ssl이 적용된 상태로 우리의 서버에 접속할 수 있게 됨.
  
      
  
  - 쥬피터 노트북을 시스템 서비스로서 등록을 하고자 함.(이 부분은 넘어감)
      우리의 재부팅하면 노트북도 자동으로 종료가 되니까 다시 실행하는 명령어를 넣어줘야함.
    재부팅됐을때도 자동으로 이런한 쥬피터 노트북을 자동실행할 수 있도록 처리하기 위해서 시스템서비스로서 등록.
  
    - which jupyter-notebook //위치 확인
  
      - sudo vi/etc/systemd/system/system/jupyter.service //서비스 파일을 작성

        [Unit]
        Description=Jupyter Notebook Server
  
        [Service]
        Type=simple
      User=ubuntu
        ExeStart=경로~
      //환경설정 파일을 작성 후.
  
  
  
- sudo systemctl daemon-reload
  
- sudo systemctl enable jupyter
  
- sudo systemctl start jupyter
  
- sudo systemctl status jupyter
  
- sudo systemctl  
  
- 쥬피터 주소 : https://13.125.178.26:8888 
  

  
# 백엔드, 프론트엔드 배포

## 백엔드 배포

- 자바 JAR File 이란?
  
    - 일종의 자바 프로젝트 압축 파일
    -  JAR 파일은 웹브라우저에서 빠르게 다운로드할 수 있도록, 자바 애플릿을 위한 클래스, 이미지 및 사운드 파일들을 하나의 파일에 압축하여 담고 있는 파일
  - https://galid1.tistory.com/428 블로그를 참고하여 jar파일을 만듦
  
- AWS에서
  
  - docker pull rtfpessoa/ubuntu-jdk8
  
  - vi ./Dockerfile
  
  - 입력
  
      - FROM rtfpessoa/ubuntu-jdk8
        ADD haribo-1.0.0.jar boraCoin.jar
        ENV JAVA_OPTS=""
      ENTRYPOINT ["java","-jar","/boraCoin.jar"]
  
  - docker build --tag boraCoint:0.1 ./
  
- docker run -d -p 8080:8080 boracoinjar:0.1
  
## 프론트엔드 배포

- Nginx로 실행
  
  - 트래픽이 많은 웹사이트를 위해 확장성을 위해 설계한 비동기 이벤트 기반구조의 웹서버 소프트웨어
  
  
  
- Nginx Docker 설치하기
  
  - 참고블로그 : https://minimilab.tistory.com/8 
      - sudo docker pull nginx:latest
      - AWS에 폴더만들어서 프론트엔드 옮겨놓음
      - docker run --name nginx-boraCoin -v /home/ubuntu/boraCoin/webFront/haribo-frontend:/usr/share/nginx/html:ro -d -p 80:80 nginx
  
- 주소 : 13.125.178.26:80
  
  

# 백엔드, 프론트엔드 재배포

## 백엔드 재배포

- docker ps에서 확인해서 전에 올렸던 jar 컨테이너를 중지 or 삭제한다.
- jar 파일 다시 올려서 aws 폴더에 올린다.
- docker build --tag brc:0.2 ./
- docker run -d -p 8080:8080 brc:0.2
- 컨테이너에 도커 이미지 폴더 추가
  - 도커 컨테이너 
    - 도커 컨테이너 접속
      - docker exec 5f72dd244e8b ls /
  - docker cp /home/ubuntu/boraCoin/worksImage 5f72dd244e8b:/
    - 참고 : https://www.reddit.com/r/docker/comments/6xvk7o/how_do_we_copy_a_folder_from_host_to_container/ 
- *주의할 점 : 도커 컨테이너 안에 도커 이미지 폴더에 이미지가 추가되므로 DB에는 연동되지만, 우리 개발 환경의 도커 이미지 폴더에는 연동되지않음!
- 백엔드 에러남, 
  - 로그 찍어서 확인해봄. 근데 블록이 계속 생성되서 너무 빨리 올라가서 타이밍 잘 맞춰야함.
    - docker logs 5f72dd244e8b
  - admin.wallet 파일을 AWS 폴더에 옮겨놓고.
  - docker cp /home/ubuntu/boraCoin/backEnd_jar/admin.wallet 5f72dd244e8b:/

## 프론트엔드 재배포

- 프론트엔드는 컨테이너 중지시킨 후,  프론트엔드 폴더만 AWS에 바꿔주고 다시 재시작
  - docker stop '이미지ID'
  - docker restart '이미지ID' 

