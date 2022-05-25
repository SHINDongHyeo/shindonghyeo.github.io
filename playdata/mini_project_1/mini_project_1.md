# 1.주제
비회원 구매 방식을 이용한 쇼핑몰 구현

# 2.구조
mvc패턴을 이용해 출력을 관리하는 View, json데이터를 이용헤 데이터를 읽거나 저장하는 Model, 유효성 검사나 요청에 대한 로직을 구현한 Control을 만들었고 여기서 발생하는 Exception들을 따로 정의했다.     

먼저, 아직 데이터베이스를 다룰 수 없어서 임의로 json형식의 파일을 데이터베이스로 간주하여 작업했다. 이를 통해 프로그램을 종료해도 데이터가 소실되지 않게 할 수 있었다.   

큰 동작 원리는 Control단에서 3개의 static ArrayList를 생성해놓고, 이 ArrayList들을 이용해 데이터를 편집했다. productList, order, cart 이렇게 세가지인데 productlist는 현재 물품(재고)에 관한 정보를 담았고 order는 주문 정보(누적된 주문 내역) cart에는 프로그램 시작할 때마다 초기화되는 장바구니로서 프로그램 시작 이후 찜해놓은 물품 내역을 담았다.       

시간 순성 상으로 설명하면, 처음에 물건을 찜해서 cart에 넣는다. cart에 담는 행위가 끝나면 주문을 요청하고 이때 주문자에 대한 정보와 주문하는 물품에 대한 정보를 받아서 order에 넣는다. 이때 order에 넣은 물품에 대한 정보를 이용해 productlist를 수정해준다.


# 3.어려운 포인트
## 1) 오버라이드(재정의)
### 1. Object클래스 toString메서드 재정의하기     
ex) Order클래스 내용 중 다음과 같은 코드를 확인할 수 있다.
```java
  @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("상태 : ");
		builder.append(state);
		builder.append(", 결제 수단 : ");
		builder.append(payment);
		builder.append(", 결제 금액 : ");
		builder.append(total);
		builder.append(", 주문 날짜 : ");
		builder.append(date);
		builder.append(", 주문 고객 : ");
		builder.append(customer.toString());
		builder.append(", 주문 내역 : ");
		
		for(Product product : productList) {
			builder.append(product.toString());
		}
		
		return builder.toString();
	}
	public String toStringNumber() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("상태 : ");
		builder.append(state);
		builder.append(", 결제 수단 : ");
		builder.append(payment);
		builder.append(", 결제 금액 : ");
		builder.append(total);
		builder.append(", 주문 날짜 : ");
		builder.append(date);
		builder.append(", 주문 고객 : ");
		builder.append(customer.toString());
		builder.append(", 주문 내역 : ");
		
		for(Product product : productList) {
			builder.append(product.toStringNumber());
		}
		
		return builder.toString();
	}
```
toString메서드를 재정의한 부분, 새로운 메서드 toStringNumber를 생성한 부분으로 구분지을 수 있다. toString메서드를 재정의한 부분은 내가 원하는 형식으로 출력될 수 있도록 원래 있던 toString메서드를 재정의한 것이다. 이때 StringBuilder라는 클래스를 사용했다.     

- ※ StringBuilder란?    
=> 객체가 변경 가능하지 않은 String클래스와 달리 객체를 변경 가능하도록 만든 클래스가 StringBuilder다. 예를 들어 String객체 a의 값을 변경하기 위해서는 새로운 String객체 b를 만들고 여기에 변경된 a의 값을 넣어주는 형식으로 변경 시에 새로운 객체 생성이 필요하다. 하지만 StringBuilder는 StrinBuilder객체 a를 생성했다고 가정하면 a를 변경할 때 새로운 객체 생성없이 a객체의 값을 바꿔줄 수 있다.    
(출력을 위해서는 StringBuilder객체에 toString메서드를 적용해야 한다)       

toString메서드를 재정의 한 부분 밑에 toStringNumber라는 메서드는 toString과는 또 다른 형식의 출력형태가 필요해서 추가로 만들어 놓은 메서드이다.


### 2. Object클래스 equals메서드 재정의하기

```java
@Override
	public boolean equals(Object object) {
		if(object == this) return true;
		if(!(object instanceof Customer)) {
			return false;
		}
		Customer customer = (Customer)object;
		return Objects.equals(name, customer.name)
				&& Objects.equals(address, customer.address)
				&& Objects.equals(phoneNumber, customer.phoneNumber)
				&& Objects.equals(point, customer.point);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, address, phoneNumber, point);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("name", name);
		jsonObject.put("address", address);
		jsonObject.put("phoneNumber", phoneNumber);
		jsonObject.put("point", point);
		
		return jsonObject;
	}
```
이번 프로젝트에서 productList, order, cart라는 ArrayList를 만들고 이를 활용해 작업을 수행했다고 언급했다. 이때 각 ArrayList에 요소로 Product나 Order같이 우리가 직접 만들어놓은 클래스타입을 받게 제네릭 설정해주었다. 그래서 ArrayList내 요소를 비교하는 원리를 가진 메서드들이 정확하게 작동되지 않았다.     

ex) a=1, b=1에서 a와 b가 같은지를 확인하면 쉽게 같음을 알 수 있지만,     
클래스A는 name,address라는 필드를 가지고 있고 a,b가 클래스A의 객체라면     
a와 b가 같은 값을 가지고 있는지 비교하기 위해서는 a의 필드값과 b의 필드값이 같은지를 확인해야 한다. 하지만 일반적인 방법과 같이 a==b 같은 방법을 쓰면 a객체의 주소값과 b객체의 주소값을 비교하는 행위이므로 a와 b의 필드값이 같은지에 대해서는 판단할 수 없게 된다. 이 때문에 equals메서드를 사용해야 하지만 우리가 비교하려는 클래스의 구조를 반영해 equals메서드를 재정의해주어야 제대로 된 비교가 가능하다.       

- equals메서드 재정의 방법    
기본조건으로 참조값은 null이 아니다.
    - 반사성 : x.equals(x) 만족
    - 대칭성 : x.equals(y) 만족하면 y.equals(x)도 만족
    - 추이성 : x.equals(y) 만족하고 y.equals(z)도 만족하면 x.equals(z)도 만족
    - 일관성 :  x.equals(y) 반복호출하면 항상 같은 결과 유지
    - x.equals(null)를 만족하지 않는다.