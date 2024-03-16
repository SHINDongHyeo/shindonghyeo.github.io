---
category: git
layout: post
cover: /assets/image/github_logo.jpg
---

github에 내 프로젝트를 업로드하는 과정에 여러 문제가 발생할 수 있습니다. 오늘은 이 과정 중 기본적으로 100mb 크기 이상의 파일은 업로드할 수 없는 github 문제 해결 방법에 대해 알아보겠습니다.

## this exceeds GitHub’s file size limit of 100.00 MB

### 1. 상황
<figure>
<img src="/assets/image/image-25.webp" alt="에러 메시지 화면">
<figcaption>에러 메시지 화면</figcaption>
</figure>


(에러 로그 캡처를 깜빡해서 위 이미지는 다른 블로그에서 가져왔습니다.https://iambeginnerdeveloper.tistory.com/76)

위 문제가 발생한 상황부터 공유하겠습니다. 저는 기존에 로컬 환경에서 개발하던 프로젝트를 공유하기 위해 깃허브에 업로드해야 하는 상황이 발생했습니다. 그래서 github에 새로운 레퍼지토리를 생성 후 로컬에 존재하는 개발 중인 프로젝트 폴더에 들어가 해당 프로젝트 업로드를 시도했습니다.

1. 개발 중 프로젝트 폴더에서 git bash 실행
2. git remote add origin "github레퍼지토리 주소"
3. git add .
4. git commit -m "커밋메시지"
5. git push -u origin main
6. 에러 발생!!!

이때 발생한 에러는 다음과 같은 에러였습니다.

```
error: src refspec master does not match any
```

해당 오류는 push 과정에 발생할 수 있는 오류로 보통 commit 과정에 문제가 있을 경우 이어지는 오류라고 합니다. 그래서 위 쪽 commit 명령어 결과 로그를 확인했더니 아래와 같은 메시지를 확인할 수 있었습니다.

error: File "특정파일명" is "파일크기"MB; this exceeds GitHub’s file size limit of 100.00 MB

즉, 너무 큰 용량으로 인해 commit 하지 못했고, 이로 인해 push 과정에서도 문제가 발생한 것입니다.

### 2. 해결 방법

저는 git-lfs와 bfg를 활용해 문제를 해결했습니다. 상황에 맞게 선택하시면 되는데, 저는 bfg를 활용했습니다.

- git-lfs
용량이 큰 파일을 github 업로드할 수 있도록 제외시켜줌
- bfg
commit 내용 중 문제가 생긴 부분들 제거해줘 commit을 정상화시킴

git-lfs는 commit 하기 전에 사용하는 방법이라고 생각하시면 쉽고, bfg는 이미 commit 했을 때 사용할 수 있는 방법입니다. 보통은 commit 후 push 과정에서 에러 메시지를 확인하고 해결 방법을 검색하셨을 것이므로, **bfg를 이용하시면 해결될 가능성이 높습니다.**

#### 방법1. Git lfs 이용하기

1. lfs 다운로드
```
$ git lfs install
Updated pre-push hook.
Git LFS initialized.
```
2. lfs 실행
lfs로 관리할 파일들을 직접 정해주어야 합니다. 와일드 카드 문자를 사용할 수 있습니다. 저는 문제가 생긴 파일이 3개 밖에 되지 않았기 때문에 직접 이름을 붙여 지정했습니다.
```
$ git lfs track “용량이 커서 문제 생긴 파일 경로”
Tracking “용량이 커서 문제 생긴 파일 경로”
```
3. 해결

이 후 commit을 진행하면 위에서 lfs로 지정한 파일은 제외한 채 commit이 진행됩니다.

#### 방법2. BFG 이용하기

1.BFG 다운로드

링크 : [https://rtyley.github.io/bfg-repo-cleaner/](https://rtyley.github.io/bfg-repo-cleaner/)

위 링크에 접속하여 bfg 다운로드 진행해 줍니다. 별로 안 걸립니다. 10초면 다운로드되는 가벼운 파일입니다.


<figure>
<img src="/assets/image/image-23-1024x639.webp" alt="BFG 다운로드 화면">
<figcaption>BFG 다운로드 화면</figcaption>
</figure>
2.BFG 실행

다운로드 완료한 파일을 .git 파일이 존재하는(내가 github에 업로드하려는 폴더 내부) 경로에 옮겨줍니다. 그리고 해당 경로에서 git bash 실행 후 아래 명령어를 실행합니다.



<figure>
<img src="/assets/image/image-24.webp" alt="bfg 파일 경로">
<figcaption>bfg 파일 경로</figcaption>
</figure>
```
java -jar bfg-x.x.x.jar --strip-blobs-bigger-than 100M --no-blob-protection
```

3.해결

그럼 deleted 파일을 출력해주는 화면을 확인할 수 있습니다. 이 경우 다시 push를 진행하시면 문제 없이 진행됩니다.

## 참고 자료

- [https://medium.com/@stargt/github%EC%97%90-100mb-%EC%9D%B4%EC%83%81%EC%9D%98-%ED%8C%8C%EC%9D%BC%EC%9D%84-%EC%98%AC%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95-9d9e6e3b94ef](https://medium.com/@stargt/github%EC%97%90-100mb-%EC%9D%B4%EC%83%81%EC%9D%98-%ED%8C%8C%EC%9D%BC%EC%9D%84-%EC%98%AC%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95-9d9e6e3b94ef)
- [https://stackoverflow.com/questions/43231061/how-to-remove-a-protected-commit-using-bfg](https://stackoverflow.com/questions/43231061/how-to-remove-a-protected-commit-using-bfg)
- [https:shindonghyeo.github.io/git/](https:shindonghyeo.github.io/git/)

