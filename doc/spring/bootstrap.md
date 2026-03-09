# Bootstratp

```java
public class SpringApplication {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public SpringApplication(@Nullable ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.resourceLoader = resourceLoader;

        Assert.notNull(primarySources, "'primarySources' must not be null");

        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));

        this.properties.setWebApplicationType(WebApplicationType.deduce());

        this.bootstrapRegistryInitializers = new ArrayList<>(getSpringFactoriesInstances(BootstrapRegistryInitializer.class));

        setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));

        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

        this.mainApplicationClass = deduceMainApplicationClass();
    }
}
```

Constructor

*   initalizares:
    *   SharedMetadataReaderFactoryContextInitializer@69
    *   ContextIdApplicationContextInitializer@70
    *   ServerPortInfoApplicationContextInitializer@71
    *   ConfigurationWarningsApplicationContextInitializer@72
    *   ProtocolResolverApplicationContextInitializer@73
    *   ConditionEvaluationReportLoggingListener@74
*   listeners:
    *   EnvironmentPostProcessorApplicationListener@99
    *   AnsiOutputApplicationListener@100
    *   LoggingApplicationListener@101
    *   BackgroundPreinitializingApplicationListener@102
    *   ParentContextCloserApplicationListener@103
    *   ClearCachesApplicationListener@104
    *   FileEncodingApplicationListener@105

```java
    public ConfigurableApplicationContext run(String... args) {
        Startup startup = Startup.create();
        if (this.properties.isRegisterShutdownHook()) {
            SpringApplication.shutdownHook.enableShutdownHookAddition();
        }
        DefaultBootstrapContext bootstrapContext = createBootstrapContext();
        ConfigurableApplicationContext context = null;
        configureHeadlessProperty();
        SpringApplicationRunListeners listeners = getRunListeners(args);
        listeners.starting(bootstrapContext, this.mainApplicationClass);
        try {
            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
            Banner printedBanner = printBanner(environment);
            context = createApplicationContext();
            context.setApplicationStartup(this.applicationStartup);
            prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
            refreshContext(context);
            afterRefresh(context, applicationArguments);
            Duration timeTakenToStarted = startup.started();
            if (this.properties.isLogStartupInfo()) {
                new StartupInfoLogger(this.mainApplicationClass, environment).logStarted(getApplicationLog(), startup);
            }
            listeners.started(context, timeTakenToStarted);
            callRunners(context, applicationArguments);
        }
        catch (Throwable ex) {
            throw handleRunFailure(context, ex, listeners);
        }
        try {
            if (context.isRunning()) {
                listeners.ready(context, startup.ready());
            }
        }
        catch (Throwable ex) {
            throw handleRunFailure(context, ex, null);
        }
        return context;
    }
```

`refreshContext(context);`

## 1\. Bootstrapping interno do Spring Boot

### 1\. Bootstrapping interno do Spring Boot

Sequência simplificada

```
main()
 └─ SpringApplication.run()
     ├─ inferWebApplicationType()
     ├─ prepareEnvironment()
     ├─ printBanner()
     ├─ createApplicationContext()
     ├─ prepareContext()
     ├─ refreshContext()
     └─ afterRefresh()
```

### 2.2. `refresh()` — o coração do Spring

Pipeline interno:

```
prepareRefresh()
obtainFreshBeanFactory()
prepareBeanFactory()
postProcessBeanFactory()
invokeBeanFactoryPostProcessors()
registerBeanPostProcessors()
initMessageSource()
initApplicationEventMulticaster()
onRefresh()   ← servidor sobe aqui
registerListeners()
finishBeanFactoryInitialization()
finishRefresh()
```

### 2.3 Pontos críticos

### BeanFactoryPostProcessor

Modifica definições de beans antes de instanciar.

### BeanPostProcessor

Intercepta criação de beans.

Base de:

*   AOP
*   @Transactional
*   @Autowired

## 3\. Auto-configuração profunda

## 3.1 MVC Auto-config principal

### `WebMvcAutoConfiguration`

Ela registra:

*   DispatcherServlet
*   RequestMappingHandlerMapping
*   HandlerAdapters
*   MessageConverters
*   Validator
*   Formatters
*   ContentNegotiation

Tudo condicional.

## .2 Condições

Boot só aplica config se:

@ConditionalOnClass
@ConditionalOnBean
@ConditionalOnMissingBean
@ConditionalOnProperty

Você substitui qualquer peça criando o bean manualmente.

---

## 4\. Subida do servidor embutido

Default:
\- Apache Tomcat

Pipeline:

```
ServletWebServerApplicationContext.onRefresh()
 └─ createWebServer()
     └─ ServletWebServerFactory
         └─ TomcatServletWebServerFactory
             └─ start()
```

---

### 5\. Runtime HTTP do Spring MVC

![https://terasolunaorg.github.io/guideline/5.2.0.RELEASE/en/_images/RequestLifecycle.png](https://terasolunaorg.github.io/guideline/5.2.0.RELEASE/en/_images/RequestLifecycle.png)

![https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/images/mvc.png](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/images/mvc.png)

![https://miro.medium.com/1%2Aw6Q0qIGHk4kxsu3D7EQfrw.png](https://miro.medium.com/1%2Aw6Q0qIGHk4kxsu3D7EQfrw.png)

Pipeline real:

```
HTTP Request
 └─ FilterChain (Servlet)
     └─ DispatcherServlet
         ├─ HandlerMapping
         ├─ HandlerAdapter
         ├─ Controller Method
         ├─ ReturnValueHandlers
         └─ HttpMessageConverters
HTTP Response
```

### 5.1 **DispatcherServlet**

**Front Controller**.

Coordena todo fluxo.

### 5.2 **HandlerMapping**

Resolve rota → método.

Implementação comum: 

*   `RequestMappingHandlerMapping`

### 5.3 **HandlerAdapter**

Invoca método correto independente do tipo.

### 5.4 Conversão JSON

Via Jackson:

*   Objeto ⇄ JSON automático.

---

## 6\. Ordem real de execução de customizações

```
Servlet Filter        (mais externo)
 ↓
HandlerInterceptor    (pré-controller)
 ↓
@Controller
 ↓
ResponseBodyAdvice
 ↓
HandlerInterceptor    (pós-controller)
```

---

## 7\. Pontos avançados de personalização

### 7.1 MVC fino

```java
@Configuration
class WebConfig implements WebMvcConfigurer {
  addInterceptors()
  addArgumentResolvers()
  configureMessageConverters()
  configurePathMatch()
}
```

### 7.2 Filtros de baixo nível

```java
@Component
class TraceFilter implements Filter { }
```

Executa antes do MVC.

### 7.3 Interceptadores

```java
registry.addInterceptor(new AuthInterceptor());
```

Executa dentro do MVC.

### 7.4 Serialização avançada

Custom ObjectMapper:

```java
@Bean
Jackson2ObjectMapperBuilderCustomizer customizer() { }
```

### 7.5 Error handling global

```java
@RestControllerAdvice
class GlobalErrors { }
```

Substitui tratamento padrão.

### 7.6 Substituir Auto-config

Criou bean manual → Boot recua.

Exemplo:

```java
@Bean
DispatcherServlet dispatcherServlet() { }
```

## 8\. Observabilidade da auto-config

Ative:

```
debug=true
```

Boot imprime:

CONDITIONS EVALUATION REPORT

Mostra:

*   Config aplicada
*   Config ignorada
*   Motivo

Ferramenta obrigatória para entender o runtime.