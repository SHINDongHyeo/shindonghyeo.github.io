# shindonghyeo.github.io

[Astro](https://astro.build)로 만든 블로그예요. https://shindonghyeo.github.io 에 배포돼요.

## 글 쓰기

`src/content/blog/`에 마크다운 파일을 추가하고 `main`에 push하면 GitHub Actions가 빌드해서 배포해요.

```md
---
title: '글 제목'
description: '한 줄 설명'
pubDate: '2026-10-28'
heroImage: '../../assets/이미지.jpg' # 선택
---

본문
```

## 로컬에서 보기

```bash
npm install
npm run dev
```

http://localhost:4321 에서 확인할 수 있어요.
