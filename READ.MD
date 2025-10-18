# 📝 Sistema de Blog Simples - Projeto para Iniciantes

## 🎯 Por que este projeto?
- **Simples de entender** - Conceitos claros e diretos
- **Completo** - Mostra suas habilidades básicas
- **Rápido** - 2-3 semanas para completar
- **Prático** - Sistema real que funciona

---

## 🏗️ O que você vai construir

Um blog onde:
- Usuários podem se cadastrar e fazer login
- Usuários autenticados podem criar posts
- Qualquer pessoa pode ler os posts
- Autor pode editar/deletar seus próprios posts

---

## 📚 Passo a Passo Detalhado

### **SEMANA 1: Fundamentos**

#### **Dia 1-2: Criar o Projeto**

**O que estudar ANTES:**
- O que é Spring Boot (1 vídeo no YouTube)
- O que é API REST (conceitos de GET, POST, PUT, DELETE)
- O que é JSON

**Passo a passo:**
1. Ir em https://start.spring.io
2. Configurar:
    - Project: Maven
    - Language: Java
    - Spring Boot: 3.2.x
    - Java: 17
3. Adicionar dependências:
    - Spring Web
    - Spring Data JPA
    - PostgreSQL Driver
    - Lombok
    - Validation
4. Gerar e abrir no IntelliJ/Eclipse

**Estrutura de pastas:**
```
src/main/java/com/seunome/blog/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
└── BlogApplication.java
```

---

#### **Dia 3-4: Configurar Banco de Dados**

**O que estudar:**
- O que é banco de dados relacional
- PostgreSQL básico (CREATE TABLE)
- O que é JPA/Hibernate (vídeo de 10 min)

**Arquivo: application.yml**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/blog_db
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

**Instalar PostgreSQL:**
- Baixar e instalar PostgreSQL
- Criar banco chamado `blog_db`

---

#### **Dia 5-7: Criar Primeira Entidade (User)**

**O que estudar:**
- O que são Annotations (@Entity, @Id, @Column)
- O que é uma entidade no banco

**Criar arquivo: model/User.java**
```java
@Entity
@Table(name = "users")
@Data // Lombok - gera getters/setters automaticamente
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

**Criar: repository/UserRepository.java**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

**Testar se funciona:**
- Rodar aplicação
- Verificar no PostgreSQL se tabela `users` foi criada

---

### **SEMANA 2: CRUD Básico**

#### **Dia 1-3: Service de Usuários**

**O que estudar:**
- O que é camada Service (lógica de negócio)
- Try-catch e exceções

**Criar: service/UserService.java**
```java
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    // Criar usuário
    public User createUser(String name, String email, String password) {
        // Verificar se email já existe
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // Por enquanto sem criptografia
        
        return userRepository.save(user);
    }
    
    // Buscar por ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
    // Listar todos
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
```

---

#### **Dia 4-7: Controller de Usuários (API)**

**O que estudar:**
- O que é Controller
- @RestController, @RequestMapping
- Testar API com Postman/Insomnia

**Criar: controller/UserController.java**
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // POST /api/users - Criar usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        User user = userService.createUser(
            request.getName(), 
            request.getEmail(), 
            request.getPassword()
        );
        return ResponseEntity.status(201).body(user);
    }
    
    // GET /api/users/{id} - Buscar usuário
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    // GET /api/users - Listar todos
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
```

**Criar: dto/UserRequest.java**
```java
@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
}
```

**TESTAR NO POSTMAN:**
```
POST http://localhost:8080/api/users
Body (JSON):
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

---

### **SEMANA 3: Posts do Blog**

#### **Dia 1-3: Entidade Post**

**Criar: model/Post.java**
```java
@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @ManyToOne // Muitos posts pertencem a UM usuário
    @JoinColumn(name = "author_id")
    private User author;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
```

**Criar: repository/PostRepository.java**
```java
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
}
```

---

#### **Dia 4-7: Service e Controller de Posts**

**Criar: service/PostService.java**
```java
@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    public Post createPost(Long authorId, String title, String content) {
        User author = userRepository.findById(authorId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        
        return postRepository.save(post);
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }
    
    public Post updatePost(Long postId, String title, String content) {
        Post post = getPostById(postId);
        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
```

**Criar: controller/PostController.java**
```java
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        Post post = postService.createPost(
            request.getAuthorId(),
            request.getTitle(),
            request.getContent()
        );
        return ResponseEntity.status(201).body(post);
    }
    
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id, 
            @RequestBody PostRequest request) {
        Post post = postService.updatePost(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok(post);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🎯 Funcionalidades Prontas

✅ Cadastrar usuário  
✅ Listar usuários  
✅ Criar post  
✅ Listar todos posts  
✅ Ver detalhes de um post  
✅ Editar post  
✅ Deletar post

---

## 📖 Recursos para Estudar

**YouTube (Canais em PT-BR):**
- Michelli Brito - Spring Boot
- Fernanda Kipper
- DevSuperior

**Cursos Gratuitos:**
- Loiane Groner - Java (YouTube)
- Spring.io - Guias oficiais

**Praticar SQL:**
- SQLBolt.com (interativo)

---

## ✅ Próximos Passos (Depois de dominar o básico)

1. **Adicionar validações** (@NotBlank, @Email)
2. **Tratamento de erros** (@ControllerAdvice)
3. **Paginação** nos posts
4. **Busca** por título
5. **Comentários** nos posts
6. **Autenticação JWT** (avançado)

---

## 🚀 Ordem de Estudo

**Fase 1 (Essencial):**
- Java básico (se não souber)
- O que é API REST
- Spring Boot básico
- JPA/Hibernate conceitos

**Fase 2 (Desenvolvimento):**
- Seguir este guia passo a passo
- Testar cada endpoint no Postman
- Debugar erros (aprenda a ler logs)

**Fase 3 (Melhorias):**
- Adicionar validações
- Melhorar mensagens de erro
- Criar README no GitHub

---

## 💡 Dicas Importantes

1. **Não pule etapas** - Faça na ordem
2. **Teste cada funcionalidade** antes de seguir
3. **Copie os códigos** e entenda cada linha
4. **Use o Google** quando travar
5. **Não tenha vergonha** de errar muito no começo
6. **Comite no GitHub** desde o início

---

## ❓ Dúvidas Comuns

**"Minha aplicação não inicia"**
- Verifique se o PostgreSQL está rodando
- Confira as credenciais no application.yml

**"Deu erro 500"**
- Olhe os logs no console
- Provavelmente erro no banco ou validação

**"Não entendi JPA"**
- JPA salva objetos Java no banco automaticamente
- Você não precisa escrever SQL na mão

---

## 📦 Tecnologias (Simples)

- **Java 17** - Linguagem
- **Spring Boot** - Framework
- **PostgreSQL** - Banco de dados
- **Postman** - Testar API
- **Git/GitHub** - Versionar código

**Total de tempo:** 2-3 semanas estudando 2h por dia