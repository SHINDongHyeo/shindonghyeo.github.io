---
author_profile: true
categories: "python"
toc: true
toc_sticky: true
---

# 1.Django란?
Django는 장고라고 불리고 파이썬을 이용한 웹 개발을 도와주는 프레임워크다.

# 2.간단한 웹 개발 순서
1. Django 설치 : pip install django (혹은 아나콘다 사용 시 conda install django)
1. 프로젝트 만들기 : django-admin startproject 프로젝트명
1. 로컬서버 실행하기 : python manage.py runserver
1. template 만들기 : 경로는 어플리케이션명/templates/어플리케이션명 폴더를 구성하고 이 하위 폴더에 html파일을 저장한다.
1. 어플리케이션 만들기
    - python manage.py startapp 어플리케이션명
    - 어플리케이션명/views.py 에 template상 html페이지와 연결해주는 함수를 만든다.              
        ```python
        from django.shortcuts import render
        from django.http import HttpResponse


        def 함수명(request):
            return render(request, '어플리케이션명/연결할html파일명.html')
        ```            
    - 어플리케이션명/urls.py 에 views.py를 import하고 urlpatterns에 path를 추가해준다.                                
        ```python
        from django.urls import path
        from . import views

        urlpatterns = [
            path('', views.views연결할함수명, name='views연결할함수명'), 
        ]
        ```            
    - 프로젝트명/urls.py 에 어플리케이션명/urls.py를 추가해준다.                      
        ```python
        from django.contrib import admin
        from django.urls import include, path

        urlpatterns = [
            path('url에주소에넣어줄값/', include('어플리케이션명.urls')), # url주소 : "http://127.0.0.1:8000/url에주소에넣어줄값"으로 연결됨을 의미한다.
            path('admin/', admin.site.urls),  # 기본적으로 있음
        ] 
        ```           
1. 모델 만들기
    - 어플리케이션/models.py 에 모델 클래스를 생성한다.
    - 클래스 구성 예시          
        ```python
        class 클래스명(models.Model):
            속성값 = models.속성타입(속성크기)
        ```     
    - 어플리케이션명/apps.py 에 있는 Config클래스명 알아내기      
    - 프로젝트명/setting.py 에 INSTALLED_APPS 에 해당 Config클래스명 추가해준다.               
        ```python
        INSTALLED_APPS = [
            '어플리케이션명.apps.Config클래스명',
                ...
        ]
        ```             
    - 마이그레이션1 : python manage.py makemigrations polls
    - 마이그레이션2 : python manage.py migrate