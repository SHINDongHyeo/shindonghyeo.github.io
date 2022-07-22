---
author_profile: true
categories: "spring"
toc: true
toc_sticky: true
---

# 1.Entity란?
Entity는 실제 DB테이블과 맵핑하는 객체다. 그러므로 DB테이블의 컬럼들과 Entity의 필드는 같아야 한다. 또한 Entity는 DB에서 실제 데이터를 가져오는 느낌이라서 직접 수정하지 않는다. 그래서 setter를 설정해주지 않는다.



# 2.DTO란?
데이터 교환을 위한 클래스다. 즉, Entity는 DB와 직접 연결되어 Entity자체를 수정하긴 부담되니, DTO가 Entity의 분신 역할을 맡아 수정, 생성, 삽입 등의 작업을 Entity 대신 해주는 것이다.


# 3.관계
## 3.1 방향이란?
방향이란 특정 Entity가 다른 Entity를 필드값으로 가지고 있는지를 뜻한다. 선수와 팀의 관계를 생각해본다. 선수는 팀에 속한다. 즉, 팀이 선수를 가지는 느낌으로 볼 수 있다. 팀 필드값으로 선수들의 고유값들이 들어가는 것이다. 이렇게 팀이 해당 팀에 속한 선수들의 정보를 필드값으로 가지므로 **'팀->선수'**라는 방향성이 생기게 된다. 이렇게 한 방향만 가진 상태를 **단방향 관계**라고 한다. 그런데 만약 선수들도 팀에 대한 정보를 필드값으로 가지고 있다면 어떻게 될까? 선수Entity 필드값에 자신이 속한 팀의 고유값이 있는 것이다. 이 경우 선수Entity가 팀Entity를 필드값으로 가지고 있으므로 **'선수->팀'**이라는 방향성이 추가된다. 결국 '팀->선수' + '선수->팀' = '팀<->선수'라는 **양방향 관계**가 형성된다.

## 3.2 OneToMany(단방향)
OneToMany 관계에서는 Entity 필드에 Collection필드가 생긴다.       
onetomany : one인 entity에서 onetomany설정. one인 entity에 many를 담을 **컬렉션**이 **추가로** 필드로 생긴다.(추가로 생성된 필드이므로 DB와 맵핑을 위해 이 필드가 many의 table에서 어떤 컬럼을 참조하고 있는지를 joincolumn을 통해 알려줘야 한다) **이때 entity는 db와 1대1로 맵핑되므로 db에 있는 컬럼만큼 필드가 생겨야 하지 않나? db에 없던 컬렉션을 필드로 만들면 문제가 되지 않나?**


## 3.3 ManyToOne(단방향)
manytoone : many인 entity에서 manytoone설정. many인 entity에 one을 담을 **필드가 entity로 바뀌어** 생긴다.(이때도 해당 필드가 맵핑될 컬럼을 joincolumn을 통해 알려줘야 한다)

## 3.4 OneToMany + ManyToOne (양방향)
onetomany + manytoone : onetomany에서 mappedBy로
<img width="1110" alt="양방향관계" src="https://user-images.githubusercontent.com/96512568/180339545-8790768c-1759-4db4-a691-c85b121d9861.png">







