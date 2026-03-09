# ✅ Checklist de Estudo — Spring Boot REST (Domínio Real)

Use como lista de verificação objetiva.
Se não sabe explicar cada item sem consultar material, ainda não domina.

---

## 🧱 Fundamentos do Bootstrapping

*   Explicar o que `SpringApplication.run()` faz internamente
*   Diferenciar ApplicationContext:
    *   Servlet vs Reactive vs Non-web
*   Entender papel de:
    *   `Environment`
    *   `Profiles`
    *   `ApplicationListeners`
*    Saber quando o servidor sobe no lifecycle
*    Entender o método `refresh()` do ApplicationContext

---

## ⚙️ Auto-configuração

*    O que é `spring-boot-autoconfigure`
*    Como Boot decide **aplicar ou não** uma configuração
*    Entender:
    *   `@ConditionalOnClass`
    *   `@ConditionalOnMissingBean`
    *   `@ConditionalOnProperty`
*    Ler e entender `WebMvcAutoConfiguration`
*    Saber como sobrescrever qualquer bean da auto-config

---

## 🌐 Infra Web (MVC)

*   Papel do DispatcherServlet
*   Diferença entre:
    *   HandlerMapping
    *   HandlerAdapter
*    Como rotas viram métodos de controller
*    Pipeline completo request → response
*    Como funciona conversão JSON automática

---

## 🧩 Extensões do Pipeline

*    Diferença entre:
    *   Servlet Filter
    *   HandlerInterceptor
*    Quando usar cada um
*    Criar interceptador próprio
*    Criar filtro próprio
*    Ordem real de execução

---

## 🧠 Serialização e HTTP

*    Como Jackson é configurado automaticamente
*    Customizar ObjectMapper global
*    Content Negotiation (JSON/XML)
*    HttpMessageConverters
*    Status codes automáticos vs manuais

---

## 🚨 Tratamento de erros

*    Ciclo de exceptions no MVC
*    `@ExceptionHandler`
*    `@RestControllerAdvice`
*    Substituir error handling padrão do Boot

---

## 🖥️ Servidor embutido

*    Como o Tomcat sobe dentro da JVM
*    Papel de:
    *   ServletWebServerFactory
*    Trocar Tomcat por Jetty ou Undertow
*    Ajustar:
    *   Threads
    *   Conexões
    *   Compressão

---

## 🧪 Observabilidade do Boot

*    Rodar app com `--debug`
*    Ler “Conditions Evaluation Report”
*    Entender por que auto-config foi aplicada ou não
*    Ativar logs de autoconfig

---

## 🧰 Customização Avançada

*    Implementar `WebMvcConfigurer`
*    Criar ArgumentResolver próprio
*    Criar MessageConverter próprio
*    Substituir DispatcherServlet
*    Criar AutoConfiguration própria

---

## ⚡ Performance real

*    Onde MVC bloqueia thread
*    Custo da serialização JSON
*    Pool de threads do servidor
*    Gargalos fora do Spring
*    Quando MVC não escala

---

## 🔄 Comparação arquitetural

*    MVC vs WebFlux
*    Bloqueante vs Não-bloqueante
*    Quando usar cada modelo