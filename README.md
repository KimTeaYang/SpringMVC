# SpringMVC

초기 설정
New => Project => Spring Legacy Project => Spring MVC Project
topLevelPackage는 최소 3단계 이상으로 ex) com.tis.myapp
이때 myapp이 url에 project name이 된다.

------------------------------------------------------------------------------------------------------------------------
생성 된 후 pom.xml에서 library 추가 및 버전 변경
spring version => 5.0.7.RELEASE
java version => 1.8

servlet version => 3.1.0

<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>javax.servlet-api</artifactId>
	<version>3.1.0</version>
</dependency>

등등 추가하도록 한다.

-------------------------------------------------------------------------------------------------------------------------
root-context.xml에서 DB 설정 하도록 한다.
<context:component-scan base-package="com"/> => com package에 해당하는 component들을 scan한다.

<!-- [1] DataSource =========================== -->
	<bean id="jndiDataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
		<property name="jndiName" value="java:comp/env/oracle/myshop" />
	</bean>	
	<!-- [1] DataSource 위 아래 둘중 하나 사용 -->
	<!-- <context:property-placeholder location="classpath:경로맞춰서"/> -->
	<!-- <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="${driver}"></property>
		<property name="url" value="${url}"></property>
		<property name="username" value="${user}"></property>
		<property name="password" value="${pwd}"></property>
	</bean>  -->
	
	<!-- [2] SqlSessionFactoryBean ================ -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="jndiDataSource"/>
		<property name="configLocation" value="classpath:spring/mybatis-config.xml"/>
	</bean>
	
	<!-- [3] SqlSessionTemplate =================== -->
	<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg ref="sqlSessionFactoryBean" />
	</bean>
	
------------------------------------------------------------------------------------------------------------------------
servlet-context.xml에서 MVC에 관련된 것

<context:component-scan base-package="com" />
 com package에 해당하는 component들을 scan한다.

<annotation-driven /> 
Tag는 RequestMappingHandlerMapping/RequestMappingHandlerAdapter 클래스를
빈으로 등록해준다. 이 두 클래스는 Controller Annotation이 적용된 클래스를 컨트롤러로 사용할 수 있도록 해준다.

<resources mapping="/resources/**" location="/resources/" /> 
<resources mapping="/js/**" location="/js/" />
해당 폴더에 있는 파일들은 action 되지 않도록 막아준다.
	
<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"> 
		<beans:property name="prefix" value="/WEB-INF/views/" /> 
		<beans:property name="suffix" value=".jsp" /> 
</beans:bean>

@Controller에서 view로 갈 때
prefix property는 return된 String 값 전에 해당 value를 붙여준다.
suffix property는 return된 String 값 후에 해당 value를 붙여준다.

-------------------------------------------------------------------------------------------------------------------------
web.xml에 관련된 내용

<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
</servlet>
DispatcherServlet이 FrontController역할을 하는 Servlet ervlet-context.xml ==> Command.properties와 같은 행동
	=> MVC와 관련된 설정 정보
  
<filter>
		<description></description>
		<display-name>SpringEncodeFilter</display-name>
		<filter-name>SpringEncodeFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>SpringEncodeFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
=> POST방식일 경우 한글 인코딩처리 필터 등록 필터는 Annotation으로 처리하거나 혹은 web.xml에서 처리한다.

-------------------------------------------------------------------------------------------------------------------------
@Component => 해당 class는 Component를 의미
@Controller => 해당 class는 Controller를 의미
@Repository => Persistence 계층에 주는 Annotation
@Service => 비즈니스 계층. 로직 또는 트랜잭션 처리 등을 담당함.

@Resource => root-context.xml 혹은 servlet-context.xml 등의 resource파일에 등록된 Bean을 주입할 때 사용
@RequestMapping => action mapping
@Log4j => log를 사용하기 위한 Annotation
@Autowired => resource파일에 등록된 Bean을 자동으로 주입

@Data => loombok library를 이용한 Annotation ... 이를 사용하면 getter/setter/toString 등을 자동으로 생성해줌

@RunWith(SpringJUnit4ClassRunner.class) => JUnit4를 사용하여 test한다는 의미
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") => 해당 경로의 resource 파일의 Bean 사용
@Test => 해당 메소드는 test 메소드를 의미

-------------------------------------------------------------------------------------------------------------------------

Persistence계층 => DB를 변경하였을 때
Model의 결합력을 낮추기 위해 Service Class를 이용

-------------------------------------------------------------------------------------------------------------------------

File Upload
파일 업로드 처리를 위한 기본 설정
	 [1] pom.xml에 commons-fileupload와 commons-io를 등록한다.
	 [2] WEB-INF/spring/appServlet/servlet-context.xml에
	   		multipartResolver 빈을 등록
	  		[주의] 빈의 id는 반드시 multipartResolver로 주자.
	 
	   <beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
			<beans:property name="maxUploadSize" value="-1" />
			<beans:property name="defaultEncoding" value="UTF-8"/>
		 </beans:bean>
     
View input에 등록한 file을 Controller에서 업로드 할 때
  [1] MultipartFile을 이용하는 방법
  [2] MultipartHttpServletRequest를 이용하는 방법
   => 이 경우는 동일한 파라미터명으로 여러 개의 파일을 업로드하는 경우에 유용함
   
-------------------------------------------------------------------------------------------------------------------------
File Download
