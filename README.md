# Github 시작하기
## 1. Git이란?
깃(Git)은 <u>형상 관리 툴</u>이다.   
일반적으로 <u>형상 관리</u>란 특정 문서나 파일이 <u>변경</u>되었을 때 이렇게 변경된 내용을 기록하고 관리하는 것을 의미한다. 즉, 어떤 문서가 언제, 어떻게, 왜 변경되었는지를 잘 관리하고 싶다면 이런 형상 관리를 이용해 변경에 대한 부분을 관리하는 것이다.   
<br/>
깃은 일반문서가 아닌 <u>소스코드</u>의 변경점을 관리하는 형상 관리 툴이다.   

## 2. Github란?
깃이 형상 관리 툴로 소스코드의 변경점을 관리한다고 했는데, 이런 소스 코드를 여러 사람들과 공유하며 협업을 하고 싶다면 인터넷을 이용해야 한다. 이런 <u>깃을 웹 호스팅 서비스 기능으로 지원</u>하는 사이트가 Github다.   

깃허브가 협업에 좋은 이유   
- 원하는 시점으로 복원 가능
- 변경된 내역 확인 가능
- 여러 사람들의 병렬적으로 작업 가능, 작업한 내용들의 충돌 방지


## 3. 동작원리
<img src="https://user-images.githubusercontent.com/96512568/166948891-d19f672a-b6bd-42f4-9396-701629fcc912.jpg"/>   

## 4. 시작하기   
1. git을 설치한다.
2. github에 가입하고 레퍼지토리(원격 저장소)를 생성한다.
3. github에 올리고 싶은 폴더,파일이 있는 폴더에서 마우스 우클릭을 이용해 git bash here을 실행하고, 실행된 git bash에서 'git init'을 입력해서 해당 폴더에 비어있는 로컬 저장소를 초기화시킨다.
4. 'git add ./폴더,파일명'을 이용해서 원하는 폴더,파일을 스테이징해준다.
5. 'git commit -m "원하는메시지내용"'을 이용해서 스테이징한 폴더,파일을 커밋해준다.
6. 'git remote add origine 원격 저장소주소'
7. 'git push -u origin main'을 이용해서 커밋한 폴더,파일을 원격 저장소로 푸시해준다.(원래는 main대신 master라는 단어를 사용했지만 인터넷에서 인종차별적 언어를 제거하자는 프로젝트를 통해 master대신 main으로 바뀌게 되었다.)
8. 이렇게 원격 저장소에 올라온 폴더,파일과 히스토리를 다른 이가 받아오고 싶으면 'git clone'을 통해 다른 이의 로컬 저장소에 클론해줄 수 있다.

## 5. 브랜치
기본적으로 github에서 main브랜치라는 하나의 흐름을 통해 작업이 수행된다. 이때 A와 B라는 인원이 동시에 작업을 하는데 서로 영향을 받지 않으며 작업을 하고 싶을 때 main브랜치에서 2개의 브랜치를 만들어내고 이 브랜치 안에서 각자 작업을 수행하게 된다. 그리고 각자 작업이 끝나면 main브랜치에 각자의 브랜치를 병합하여 다시 하나로 만들어 줄 수 있다.        

브랜치란 github를 통해 협업을 할 때 개인의 작업들이 서로 영향을 받지 않게 기본. 만약 A와 B가 같은 파일을 동시에 수정을 하는데 실시간으로 수정내용이 반영된다면 문제가 생길 수 있다. 이런 문제점을 막기 위해 A수정은 1브랜치에서, B수정은 2브랜치에서 진행하고 수정이 끝난 브랜치들을 하나로 합치면 서로 충돌하지 않고 수정을 할 수 있다.


