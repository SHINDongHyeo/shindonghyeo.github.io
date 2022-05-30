---
author_profile: true
categories: "java"
toc: true
toc_sticky: true
---

# 1. SingleTon
객체생성에 제한을 걸기 위한 방법이다.     
ex)

```java
public class SingleTon {
	private static SingleTon instance = new SingleTon();
	private SingleTon() {}
	
	public static SingleTon getInstance() {
		return instance;
	}

	public String getCompanyname() {
		return "playdata";
	}
}
```

1. private SingleTon() {}    
먼저 기본생성자를 private처리하여 SingleTon외부에서 아무리 SingleTon클래스를 import해도 SingleTon객체를 못하게 만든다.
2. private static SingleTon instance = new SingleTon();     
즉 SingleTon객체는 SingleTon클래스 안에서만 만들 수 있으므로 클래스명을 instance로 하는 SingleTon객체를 하나 만들고, 이 작업또한 외부에서 쓰지 못하도록 private처리한다.(static설명은 뒤에)    
3. public static SingleTon getInstance() {    
		return instance;    
	}    
클래스 밖에서 객체를 생성하지는 않지만 객체를 받아서 사용해야 하기 때문에 public으로 SingleTon클래스 getInstance메서드를 선언해준다. 리턴값은 instance를 받을 것이다. <u>이때, 객체를 선언하지 않으면 해당 클래스의 메서드를 불러올 수 없는 상황이 문제를 일으킨다.</u> 객체를 여러개 생성하지 않기 위해 위에서 private으로 생성자를 막는 행위를 했는데, 객체가 없어서 메서드를 호출하지 못하는 것이다. <u>하지만 해당 메서드가 static이라면 다르다. static 메서드는 프로그램 시작과 함께 객체 생성과 무관하게 메모리의 클래스(메서드) 영역에 저장되기 때문에,</u> getInstance메서드에 static을 적용한다.     
4. 따라서 2번에서 설명하지 않았던 static도 따라 붙는다. 왜냐하면 getInstance메서드가 static으로 생성되어 반환값으로 instance를 받는데, instance도 static이어야 반환받을 수 있기 때문이다.


# 분석을 통한 총정리
코드는 올릴 수 없으므로 분석한 내용만 정리해보았다.   
![517정리](https://user-images.githubusercontent.com/96512568/168758601-65e85a90-4a67-4a9e-87e0-ca8f362cc424.jpg)