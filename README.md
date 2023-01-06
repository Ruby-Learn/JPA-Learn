## H2 Database
- 개발이나 테스트 용도로 적합한 가볍고 편리한 DB

### 다운로드 및 설치
- https://www.h2database.com
- 실행 및 접속
  - 설치된 경로에서 h2.sh 파일 실행
    - .../h2/bin/h2.sh
  - http://localhost:8082 접속
  - jdbc:h2:~/schemaName 으로 최소 한 번 실행
    - 해당 DB 파일이 생성됨
  - 이후, jdbc:h2:tcp://localhost/~/schemaName 으로 접속