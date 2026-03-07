# project-116

Frameworks initialization

## Autor

Jean Barcellos

## Demo 001

- Sem nehuma dependência selecionada

```xml
  <dependencies>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
```

### 2. Dependências reais do spring-boot-starter

O starter que aparece no seu POM é apenas um agregador.

Ele traz três coisas principais:

```
spring-boot
spring-boot-autoconfigure
spring-boot-starter-logging
```

Arquivo `build.gradle`

```groovy
plugins {
	id "org.springframework.boot.starter"
}

description = "Core starter, including auto-configuration support, logging and YAML"
// Pacote inicial principal, incluindo suporte para configuração automática, registro de logs e YAML.

dependencies {
	api(project(":starter:spring-boot-starter-logging"))

	api(project(":core:spring-boot-autoconfigure"))

	api("jakarta.annotation:jakarta.annotation-api")
	api("org.yaml:snakeyaml")
}

```

### 3. Dependências transitivas do Spring Framework

Essas dependências puxam módulos do Spring Framework.

Principais:

```
spring-core
spring-beans
spring-context
spring-aop
spring-expression
```

Isso já fornece:

```
ApplicationContext
DI container
AOP infrastructure
bean lifecycle
```

Ou seja: o container completo do Spring já está presente.
