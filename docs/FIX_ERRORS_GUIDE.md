# Guia de Correção de Erros - SyncoApi

Este documento descreve os erros encontrados nas funções da aplicação e como corrigi-los.

---

## Resumo dos Problemas

| Teste | Status Esperado | Status Atual | Causa Raiz |
|-------|----------------|--------------|------------|
| `shouldFailCreateClassWhenUserLacksPermission` | 403 Forbidden | 500 Internal Server Error | Exception não tratada no GlobalExceptionHandler |
| `shouldFailDeleteClassWhenNotFound` | 404 Not Found | 500 Internal Server Error | Exception não tratada no GlobalExceptionHandler |
| `shouldFailDeleteClassWhenUserLacksPermission` | 403 Forbidden | 500 Internal Server Error | Exception não tratada no GlobalExceptionHandler |
| `shouldFailGetClassWhenNotFound` | 404 Not Found | 500 Internal Server Error | Exception não tratada no GlobalExceptionHandler |
| `shouldFailUpdateClassWhenUserLacksPermission` | 403 Forbidden | 500 Internal Server Error | Exception não tratada no GlobalExceptionHandler |
| `shouldUpdateClassAsAdmin` | 202 Accepted | 500 Internal Server Error | Exception não tratada ou problema no ClassEntityId |
| `shouldUpdateCourseAsAdmin` | 202 Accepted | 400 Bad Request | Falta anotação `@RequestBody` no controller |

---

## Problema 1: Exceptions do Módulo Class não são tratadas

### Localização
- **Arquivo:** `src/main/java/com/api/synco/infrastructure/exception/GlobalExceptionHandler.java`

### Descrição
O `GlobalExceptionHandler` não possui handlers para as exceptions do módulo `class_entity`. As seguintes exceptions são lançadas mas não tratadas adequadamente:

1. **`ClassNotFoundException`** - extends `ClassDomainException`
   - Localização: `src/main/java/com/api/synco/module/class_entity/domain/exception/ClassNotFoundException.java`
   - Deveria retornar: **404 Not Found**

2. **`UserWithoutCreateClassPermissionException`** - extends `ClassDomainException`
   - Localização: `src/main/java/com/api/synco/module/class_entity/domain/exception/user/UserWithoutCreateClassPermissionException.java`
   - Deveria retornar: **403 Forbidden**

3. **`UserWithoutUpdateClassPermissionException`** - extends `ClassDomainException`
   - Localização: `src/main/java/com/api/synco/module/class_entity/domain/exception/user/UserWithoutUpdateClassPermissionException.java`
   - Deveria retornar: **403 Forbidden**

4. **`UserWithoutDeleteClassPermissionException`** - extends `ClassDomainException`
   - Localização: `src/main/java/com/api/synco/module/class_entity/domain/exception/user/UserWithoutDeleteClassPermissionException.java`
   - Deveria retornar: **403 Forbidden**

### Como Corrigir

Adicionar os seguintes handlers no arquivo `GlobalExceptionHandler.java`:

```java
// Handler para ClassNotFoundException - retorna 404 Not Found
@ExceptionHandler(ClassNotFoundException.class)
public ResponseEntity<CustomApiResponse<?>> handlerClassNotFoundException(
        ClassNotFoundException e, 
        HttpServletRequest httpServletRequest) {
    String path = httpServletRequest.getRequestURI();
    HttpStatus status = HttpStatus.NOT_FOUND;
    return ResponseEntity.status(status).body(
        CustomApiResponse.error(status.value(), "CLASS_NOT_FOUND", e.getMessage(), path)
    );
}

// Handler para exceptions de permissão do Class - retorna 403 Forbidden
@ExceptionHandler({
    UserWithoutCreateClassPermissionException.class,
    UserWithoutUpdateClassPermissionException.class,
    UserWithoutDeleteClassPermissionException.class
})
public ResponseEntity<CustomApiResponse<?>> handlerClassPermissionException(
        ClassDomainException e, 
        HttpServletRequest httpServletRequest) {
    String path = httpServletRequest.getRequestURI();
    HttpStatus status = HttpStatus.FORBIDDEN;
    return ResponseEntity.status(status).body(
        CustomApiResponse.error(status.value(), "CLASS_PERMISSION_DENIED", e.getMessage(), path)
    );
}

// Handler genérico para outras ClassDomainException - retorna 400 Bad Request
@ExceptionHandler(ClassDomainException.class)
public ResponseEntity<CustomApiResponse<?>> handlerClassDomainException(
        ClassDomainException e, 
        HttpServletRequest httpServletRequest) {
    String path = httpServletRequest.getRequestURI();
    return ResponseEntity.badRequest().body(
        CustomApiResponse.error(HttpStatus.BAD_REQUEST.value(), "CLASS_EXCEPTION", e.getMessage(), path)
    );
}
```

### Imports Necessários
Adicionar no topo do arquivo `GlobalExceptionHandler.java`:

```java
import com.api.synco.module.class_entity.domain.exception.ClassDomainException;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutCreateClassPermissionException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutUpdateClassPermissionException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutDeleteClassPermissionException;
```

---

## Problema 2: CourseController.update() não recebe o corpo da requisição

### Localização
- **Arquivo:** `src/main/java/com/api/synco/module/course/application/controller/CourseController.java`
- **Linha:** 147

### Descrição
O método `update()` não possui a anotação `@RequestBody` no parâmetro `UpdateCourseRequest`. Isso faz com que o Spring não consiga fazer o binding do JSON enviado na requisição para o objeto DTO.

### Código Atual (Linha 147)
```java
public ResponseEntity<CustomApiResponse<UpdateCourseResponse>> update(
    @Valid UpdateCourseRequest updateCourseRequest, 
    @PathVariable long id)
```

### Como Corrigir
Adicionar a anotação `@RequestBody` antes do parâmetro:

```java
public ResponseEntity<CustomApiResponse<UpdateCourseResponse>> update(
    @Valid @RequestBody UpdateCourseRequest updateCourseRequest, 
    @PathVariable long id)
```

---

## Problema 3 (Potencial): Problema com ClassEntityId no Update

### Localização
- **Arquivo:** `src/main/java/com/api/synco/module/class_entity/domain/use_case/UpdateClassUseCase.java`

### Descrição
O teste `shouldUpdateClassAsAdmin` também falha com 500. Após resolver o Problema 1 (exception handling), pode haver um problema adicional com:
1. O `ClassEntityId` não sendo encontrado corretamente no banco de dados
2. Problemas com a chave composta (courseId + number)

### Como Investigar
1. Verificar se o `ClassEntityId` está sendo criado corretamente no `UpdateClassUseCase`
2. Verificar se o repositório está fazendo a busca correta com a chave composta
3. Adicionar logs temporários para debug:

```java
// No UpdateClassUseCase.execute()
System.out.println("Looking for class with courseId=" + classEntityId.getCourseId() + 
                   " and number=" + classEntityId.getNumber());
```

---

## Ordem de Correção Sugerida

1. **Primeiro:** Corrigir o `GlobalExceptionHandler.java` adicionando os handlers para `ClassDomainException` e suas subclasses
2. **Segundo:** Corrigir o `CourseController.java` adicionando `@RequestBody` no método `update()`
3. **Terceiro:** Re-executar os testes para verificar se há problemas adicionais com o `UpdateClassUseCase`

---

## Comandos para Testar as Correções

```bash
# Executar todos os testes
./mvnw test

# Executar apenas os testes de integração do ClassController
./mvnw test -Dtest=ClassControllerIntegrationTest

# Executar apenas os testes de integração do CourseController
./mvnw test -Dtest=CourseControllerIntegrationTest
```

---

## Testes Afetados

### ClassControllerIntegrationTest
- `shouldFailCreateClassWhenUserLacksPermission` → espera 403
- `shouldFailDeleteClassWhenNotFound` → espera 404
- `shouldFailDeleteClassWhenUserLacksPermission` → espera 403
- `shouldFailGetClassWhenNotFound` → espera 404
- `shouldFailUpdateClassWhenUserLacksPermission` → espera 403
- `shouldUpdateClassAsAdmin` → espera 202

### CourseControllerIntegrationTest
- `shouldUpdateCourseAsAdmin` → espera 202

---

## Arquivos a Modificar

1. `src/main/java/com/api/synco/infrastructure/exception/GlobalExceptionHandler.java`
2. `src/main/java/com/api/synco/module/course/application/controller/CourseController.java`

---

*Documento gerado automaticamente para auxiliar na correção dos erros identificados nos testes de integração.*
