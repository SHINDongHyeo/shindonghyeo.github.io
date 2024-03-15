---
category: java
layout: post
---

# 1. API
API : application programmng interface
- 제공받아서 사용하는 모든 클래스, 메소드, 변수 ...     
ex) 카카오 API란?     
    -   지도를 사용해서 개발해야할 개발자들이 가져다가 사용한 library의미     
    - 개발자와 map 간에 상호호완 인터페이스(중간매개체)
	
- 자바의 장점    
	- 예외처리로 인한 견고한 개발 가능    
	- 제공하는 API들이 많다(파이썬이 훨씬 많긴 함)    
	
- 주요한 유요한 API        
	데이터를 어떻게 관리할 것인가에 대한 다양한 구조의 기능 : 특정 기능의 클래스에 메서드로 제공


# 2. 자바 API 자료구조 클래스( 컬렉션 프레임워크 )        
기본관용용어        
- E(element) / T(type) / K(key) / V(value)

공통된 특징        
- 기본타입 8가지(int,double...) 사용 불가능
- 객체들만 저장가능
- 동적메모리 제공
- API로 제공하여 생성해서 사용만함 --> 사용하기 위해 [java.util.package] import필수

## 2.1 list계열구조
- ArrayList ...
	- 순서존재
	- 중복데이터 저장 가능
	- index 이용해 관리        

```java
// 선언및초기화
ArrayList<String> 어레이리스트 = new ArrayList<String>();

// 데이터추가
어레이리스트.add("안녕");
어레이리스트.add("hi");
어레이리스트.add(3,"잘가");

// 크기반환
int b = 어레이리스트.size();
System.out.println(b);

// 인덱스로 데이터반환
String a=어레이리스트.get(0);
System.out.println(a);


// 인덱스로 데이터삭제
어레이리스트.remove(0);
System.out.println(어레이리스트);
```


## 2.2 set계열구조
- HashSet, Iterator ...
- 순서존재 X
- 중복데이터 저장 X
- index 이용해 관리 X      

```java
// HashSet 선언및초기화
HashSet<String> 맵 = new HashSet<String>();

// HashSet 데이터추가
셋.add("안녕");
셋.add("hi");
셋.add("안녕"); //이미 존재하던 "안녕"을 삭제한 후 "안녕"추가
System.out.println(셋); // 출력 : [hi, 안녕]

// Iterator 선언및초기화
Iterator<String> 이터레이터 = 셋.iterator();
	
// Iterator 데이터 유무검증
System.out.println(이터레이터.hasNext()); // 출력 : true

// Iterator 출력
while(이터레이터.hasNext()) {
	System.out.println(이터레이터.next()); // 출력: hi
}										 //     안녕
//이때 next()는 데이터를 잘라오는 메서드로 이후 다시 이터레이터를 출력하면 빈 상태를 확인할 수 있다.
while(이터레이터.hasNext()) {
	System.out.println(이터레이터.next()); // 출력: 출력 아무것도 안됨
}
```

## 2.3 map계열구조
- HashTable
- key와 value로 관리
- key는 중복불가
- valuse는 중복가능
- key와 value는 일대일대응     

```java
// Hashtable 선언및초기화
Hashtable<String, String> 맵 = new Hashtable<String, String>();

// Hashtable 데이터추가
맵.put("일", "김연아");
맵.put("이", "마동석");
맵.put("삼", "신동엽");
맵.put("사", "유재석");
System.out.println(맵); // 출력화면 : {이=마동석, 일=김연아, 삼=신동엽, 사=유재석}

// Hashtable 데이터반환
System.out.println(맵.get("일")); // 출력화면 : 김연아

// Hashtable 데이터변경
맵.put("일", "김연아아님");// "일"이란 key에 대한 value값을 바꿀 수 있음
System.out.println(맵.get("일")); // 출력화면 : 김연아아님
```



# 3. 자바 API 문자열 클래스
## 3.1 String
- 편의성을 위해 " " 만으로 자동으로 객체화 가능
- 하지만    "데이터"생성  vs  new String("데이터")   차이점     
    - "데이터"생성 경우 : 동일한 객체 있을 시 객체를 참조해서 사용      
    - new String("데이터") 경우 : 무조건 새로운 메모리 생성    

```java
// String 선언및초기화
String s1 = "data";
String s2 = "data";
String s3 = new String("data");
String s4 = new String("data");

System.out.println(s1==s2);// 출력화면 : true   's1가 소유한주소' vs 's2 소유한주소'
System.out.println(s2==s3);// 출력화면 : false  's2가 소유한주소' vs 's3 소유한주소'
System.out.println(s3==s4);// 출력화면 : false  's3가 소유한주소' vs 's4 소유한주소'
// 즉, 변수가 가리키고 있는 String객체를 보는 것이 아니라 가리키고 있는 String객체의 주소를
// 비교하는 형식이기 때문에 다음과 같다. 큰따움표를 이용한 선언및초기화는 원래 있던 객체를 참조
// 함을 알 수 있다.

// String 데이터추가
s1.concat(" data2");
System.out.println(s1); // 출력화면 : data
// concat메서드가 원래있던 객체를 변경하는 것이 아니라 새로운 변수를 만들어내는 메서드라 그럼
String s5=s1.concat(" data2");
System.out.println(s5); // 출력화면 : data data2
// 변수 이름 그대로 유지하며 concat하고 싶다면 이렇게 해야됨.	
s1=s1.concat(" data2");
System.out.println(s1); // 출력화면 : data data2
```
		
## 3.2 StringBuilder

- 한번 생성시킨 문자열 객체의 <u>내용값 갱신시에 새로운 객체 생성하지 않고 기존 객체를 수정</u>
- jdk1.5부터 제시됨
- StringBuffer 보다 문자열 처리 속도 빠름

```java
// StringBuilder 선언및초기화
StringBuilder sb = new StringBuilder("data");

// StringBuilder 데이터추가
sb.append(" data2");
System.out.println(sb); // 출력화면 : data data2
// String과 달리 StringBuilder는 append메서드를 사용하면 원래 변수가 가리키던 객체에 변경이 적용됨.
```
	
## 3.3 StringBuffer (기능은 StringBuilder와 동일)

- (한번 생성시킨 문자열 객체의 <u>내용값 갱신시에 새로운 객체 생성하지 않고 기존 객체를 수정</u>)
- jdk1.0부터 제시됨



#  4. 제네릭( Generic )
컴파일 시에 미리 타입을 정해주는 기능       
ex) 제네릭을 사용하는 이유

```java     
public void m12() {
	ArrayList a=new ArrayList();
	a.add("안녕");
	System.out.println(a);  // 출력화면 : [안녕]
	
	String hi = a.get(0);
	// 오류발생 : Type mismatch
}
 ```

다음처럼 a.get(0)은 "안녕"이라는 String값인데 왜 Type mismatch라는 오류가 생길까?      

이유는 a.get(0)에 들어간 "안녕"이 ArrayList에 <u>제네릭으로 String타입지정을 하지 않았으므로 더 상위단계인 Object타입으로 생각</u>되기 때문이다.      

--->해결을 위해 : String hi = a.get(0);   --->  String hi = (String)a.get(0);   이런식의 형변환이 필요하다.       

이런 <u>추가적인 형변환 작업의 불편함을 없에기 위해</u> 제네릭을 사용한다.

## 4.1 형변환이란?
형변환은 데이터타입을 바꾸는 작업을 뜻한다. 이때 기본자료형에서 boolean(논리타입)은 다른 타입과 형변환이 불가하다.

- upcasting(묵시적 형변환)    

ex)    
int a = 20;    
byte b = 2;    

a = b;    

int는 32bit이고 byte는 8비트다. 8비트짜리 변수b를 32비트짜리 변수a에 넣는다면 문제없이 들어간다. 그러므로 단순하게 'a=b;'와 같은 식으로 형변환을 진행한다.

- downcasting(명시적 형변환)      

ex)    
int a = 20;    
byte b = 2;     

b = (byte)a;    

int는 32bit이고 byte는 8비트다. 32비트짜리 변수a를 8비트짜리 변수b에 넣는다면 비트가 모자라 문제가 생긴다. 그러므로 32비트 변수a를 8비트 변수로 'b = (byte)a;'와 같은 식을 통해 형변환을 진행한다.




## 4.2 Autoboxing / Unboxing

```java
	public void m3() {
		int i =3;
		Integer i2 = new Integer(3);
		
		//unboxing
		int i3 = new Integer(3); //int i3=(new Integer(3)).intValue(); 즉 알아서 intValue메서드를 실행해서 int기본타입으로 변경함.
		
		//autoboxing
		Integer i4=3; //Integer i4=new Integer(3);  즉 int 기본값3을 객체로 변화해서 대입해준다	
	}
```
java.lang.package안에 기본타입8가지를 지원해주는 8개의 class가 있다. 위 코드의 경우...     

int는 기본타입으로 위와 같이 선언 및 초기화를 할 수 있다. 그에 반해 Integer는 참조타입으로 new생성자를 이용해 위와 같이 선언 및 초기화를 할 수 있다.       
--> 이때 Integer객체타입 3을 생성자로 생성하면 자동으로  .intvalue() 라는 메서드가 붙어 int기본타입으로 변형해준다. 이를 unboxing이라한다. 또한 Integer변수에 바로 int타입 3을 대입하면 자동으로 int타입 3을 Integer객체로 변경해주게 되고 이를 autoboxing이라고 한다.
