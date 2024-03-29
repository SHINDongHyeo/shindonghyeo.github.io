---
category: java
layout: post
---

# 1.Stream이란?
스트림이란 자바 8에서 추가한 기술이다. **람다식**을 이용해 **컬렉션**에 저장된 요소들을 순회하며 여러 작업을 할 수 있게 해준다. 먼저 간단한 예제를 알아본다.

```java
ArrayList<Integer> b = new ArrayList<Integer>();
b.add(1);
b.add(2);
b.add(3);

b.stream().filter(x -> x<2).forEach(System.out::println);
// 출력화면 : 1
```

컬렉션 자료구조의 ArrayList를 이용해 객체 b를 생성했고, b에 1,2,3값을 넣었다. 이때 stream메서드를 이용해 스트림을 생성하고 filter메서드에서 람다식 'x -> x<2'를 이용해 2미만인 수만을 필터링했고 forEach(System.out::println);를 통해 필터링된 요소들을 출력했다.

## 1.1 컬렉션이란?
JAVA에서 Collection이란 정확하게 표현하면 JCF(Java Collections Framework)이다. 이는 자바에서 제공하는 표준화된 데이터 저장 자료구조와 처리 알고리즘이 포함된 클래스들의 집합이다. 다음 그림은 JCF를 대략적으로 나타낸 구조도이다.

<img width="516" alt="컬렉션프레임워크" src="https://user-images.githubusercontent.com/96512568/173366245-270f4f1e-8d58-433d-9e09-35140364658c.png">


JCF에서 데이터를 저장하는 자료구조에 따라 크게 4가지 인터페이스로 구분이 된다.
1. List 인터페이스 : 순서 존재, 중복 허용
2. Set 인터페이스 : 순서 미존재, 중복 불허
3. Queue 인터페이스 : 순서 존재, 중복 허용
4. Map 인터페이스 : 순서 미존재, key 중복 불허, value 중복 허용       

위 그림을 보면 Map 인터페이스는 혼자 Collection 인터페이스를 상속받지 않고 있음을 확인할 수 있다. 하지만 Map은 Collection으로 분류된다.

## 1.2 람다식이란?
람다식이란 프로그래밍 언어에서 익명 함수(Anonymous functions)를 지칭하는 용어이다. 함수에 따로 이름을 부여하지 않고 간단하게 사용하는 방식이다. 자바에서는 '->'를 이용해 표현한다. 간단한 예제를 본다.

```java
(int x) -> x+1
x -> x+1
```

## 1.3 Stream사용가능 인터페이스
Stream을 위에서  **람다식**을 이용해 **컬렉션**에 저장된 요소들을 순회하며 여러 작업을 할 수 있게 해주는 기술이라고 언급했다. 1.1번의 구조도를 통해 Collection인터페이스는 Iterable인터페이스를 상속받고 있음을 알 수 있다. **Iterable이란 '반복 가능한 객체'를 뜻한다.** 즉, 반복 가능한 객체 성질을 상속받은 Collection인터페이스에서 이런 반복 가능한 객체의 요소를 순회하면서 여러 작업이 가능하도록 하는 특별한 메소드를 만들었고, 이것이 stream인 것이다. 당연하겠지만 stream은 Collection인터페이스에서 작성되었으므로 **Collection인터페이스를 상속받은 List,Set,Queue인터페이스가 사용할 수 있는 기능이다.** Collection으로 분류되긴 하지만 직접적으로 **Collection을 상속받지 못한 Map인터페이스는 Stream메서드를 사용하지 못한다.**


# 2.Stream 구성
Stream은 크게 3파트로 구분지을 수 있다.
1. 스트림 생성
2. 스트림 조작
3. 스트림 사용

## 2.1 스트림 생성
- Stream : 범용 스트림
- IntStream : int타입 스트림
- LongStream : long타입 스트림
- DoubleStream : double타입 스트림

## 2.2 스트림 조작
- Stream < T > distinct() : 스트림 중복 요소 제거
- Stream < T > sorted() : 스트림 요소 정렬
- Stream < T > filter(Predicate < T > predicate) : 람다식 조건에 맞는 요소 필터링
- Stream < T > limit(long maxSize) : maxSize까지 요소 제한
- Stream < T > skip(ling n) : 처음 n개의 요소 제외
- Stream < T > peek(Consumer< T > action) : T타입 요소에 맞는 작업 수행
- Stream < R > flatMap(Function< T, stream<? extends R>> Tmapper) : T타입 요소를 1:N의 R타입 요소로 변환
- Stream < R > map(Function<? super T, ? extends R> mapper) : 입력 T타입을 R타입 요소로 변환
- Stream mapToInt(),mapToLong(),mapToDobule() : 만약 map Type이 숫자가 아닌 경우 변환

## 2.3 스트림 사용
- void forEach(Consumer <? super T> action) : 스트림 각 요소에 지정된 작업 수행
- long count() : 스트림 요소 개수
- Optional < T > sum (Comparator <? super T> comparator) :스트림 요소 합
- Optional < T > max (Comparator <? super T> comparator) : 스트림 요소의 최대 값
- Optional < T > min (Comparator <? super T> comparator) : 스트림 요소의 최소 값
- Object[] toArray() :  스트림 요소를 배열로 반환
- Collectors.toList() : 스트림 요소를 List로 반환