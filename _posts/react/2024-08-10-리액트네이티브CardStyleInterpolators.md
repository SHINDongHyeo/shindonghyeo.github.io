---
category: react
title : "[React Native] CardStyleInterpolators.forHorizontalIOS 뒤로가기 시 잠깐 보이는 왼쪽 하얀색 부분 지우기( 다른 효과도 비슷할 듯 )"
layout: post
cover : /assets/image/nodejs.png
---

## 문제 상황
### CardStyleInterpolators.forHorizontalIOS 뒤로가기
리액트 네이트브에서 stack navigator를 이용해 화면을 꾸밀 때, 화면 전환 애니메이션을 CardStyleInterpolators을 이용해 구현할 수 있다. 이때 가장 흔하게 사용되는 애니메이션 중 하나가 forHorizontalIOS일 것이다. 특정 화면에서 들어가면 오른쪽에서 왼쪽 방향으로 화면이 나오고, 뒤로가기 버튼을 통해 이전 화면으로 돌아가면 해당 화면이 다시 오른쪽에서 왼쪽으로 들어가는 방식이다.

이때 하얀 배경을 적용했을 때는 인지하지 못했지만, 검은색 배경을 적용하고 해당 애니메이션을 적용해보니 문제가 발생했다. A에 화면에서 B화면으로 넘어갈 때는 문제가 되지 않는다. 하지만 B화면에서 **뒤로가기 버튼을 통해 A화면으로 되돌아가는 상황에서 화면 왼쪽에 세로로 된 하얀 배경의 부분**이 잠깐 보였다 사라지는 것이다.

이것이 계속 걸려서 여러 방법을 찾아보았고, 해당 부분을 해결한 나만의 간단한 방법을 소개한다.

## 해결 

참고 자료 : [[github 글 바로가기](https://github.com/react-navigation/react-navigation/issues/7690)]

CardStyleInterpolators.forHorizontalIOS를 이용하는 과정에 검은색 바탕화면을 적용하면 하얀색 화면이 깜빡인다는 글이다. 해당 글에서는 NavigationContainer의 theme 옵션을 변경하면 화면 전환 시 보이는 부분의 색상을 변경할 수 있어 자연스러운 화면 전환을 구현할 수 있다고 나와있다.

간단하게 다음과 같이 theme를 import 했다.
```
import {
  NavigationContainer,
  DefaultTheme,
  DarkTheme,
} from '@react-navigation/native';
```

DefaultTheme는 화면 전환 시 깜빡이는 부분이 하얀색으로 보이고, DarkTheme는 화면 전환 시 깜빡이는 부분이 검은색으로 보여 자연스럽게 화면이 전환되는 것과 같이 보이게 된다.

만약 다른 색상의 theme를 만들고 싶다면 직접 커스터마이징한다. 아래는 MyTheme를 커스터마이징하는 예시다. 여기서 `card`에 해당하는 부분이 화면 전환 시 배경색을 정한다.

```
const MyTheme = {
  dark: false,
  colors: {
    primary: 'teal',
    background: 'white',
    card: 'lightgrey',
    text: 'black',
    border: 'gray',
    notification: 'red',
  },
};
```


### 추가 정보

1. CardStyleInterpolators.forHorizontalIOS 코드로 이동
나는 VSC를 이용해 작업 중이어서, `cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,` 해당 코드에서 'ctrl + 클릭'을 이용해 forHorizontalIOS가 구현된 코드로 이동했다.

2. 구조 파악
해당 코드를 보면 다음과 같이 구성되어있다. 간단하게 A화면과 B화면으로 구성되어 있고, A화면이 첫번째 화면이고 B화면이 두번째 화면이라고 가정하고 간단한 설명을 남겨두었다.

- translateFocused
  B화면을 열 때 B화면에 적용되는 애니메이션.(기본적으로 outputRange: [screen.width, 0] 으로 화면 가장 왼쪽에서 화면 가장 오른쪽에 딱 맞춰 이동하도록 설정됨)
- translateUnfocused
  B화면을 열 때 A화면에 적용되는 애니메이션.(기본적으로 outputRange: [0, screen.width * -0.3] 으로 화면 왼쪽으로 살짝 들어가는 느낌 내도록 설정됨)
- overlayOpacity
  B화면을 열 때 A화면에 적용되는 불투명도 스타일.
- shadowOpacity
  B화면을 열 때 A화면에 적용되는 그림자 스타일.


