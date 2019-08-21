Java Web Server Framework <small><small><small>for study</small></small></small>
---

#### How To Use

[Sample Code](https://github.com/KingCjy/web-server-framework/tree/master/wasframework-application)

##### Controller
`wasframework-application/src/main/java/com.kingcjy.was.application.board.BoardController.java`
```Java
@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(board)
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards() {
        List<BoardDto.BoardResponseDto> dtoList = boardService.findAllBoards();

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
```
##### Service
`wasframework-application/src/main/java/com.kingcjy.was.application.board.BoardService.java`

```java
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDto.BoardResponseDto> findAllBoards() {
        return boardRepository.findAllBoardsOrderByIdDesc().stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }
}
```
##### Entity
`wasframework-application/src/main/java/com.kingcjy.was.application.board.Board.java`

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;


    @Builder
    public Board(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
```
##### Repository

`wasframework-application/src/main/java/com.kingcjy.was.application.board.BoardRepository.java`

```java
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM board ORDER BY b.id DESC")
    List<Board> findAllBoardsOrderByIdDesc();
}
```

##### DTO

`wasframework-application/src/main/java/com.kingcjy.was.application.board.BoardDto.java`

```java
public class BoardDto {

    public static class BoardResponseDto {
        private Long id;
        private String title;
        private String contents;
        private String createdDateTime;
        private String updatedDateTime;

        public BoardResponseDto(Board entity) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            this.id = entity.getId();
            this.title = entity.getTitle();
            this.contents = entity.getContents();
            this.createdDateTime = entity.getCreatedDateTime().format(formatter);
            this.updatedDateTime = entity.getUpdatedDateTime().format(formatter);
        }
    }
}
```

##### Main Class

`wasframework-application/src/main/java/com.kingcjy.was.application.Application.java`

```java
public class Application {
    public static void main(String[] args) {
        WasApplication.run(Application.class, args);
    }
}
```

##### Configuration Sample

`wasframework-application/src/main/java/com.kingcjy.was.application.config.WebConfig.java`
```java
@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```


Comment
---
스프링 부트와 비슷하게 사용할 수 있게 만든 프레임워크입니다.

사용 라이브러리
- Tomcat8
- Hibernate 5
- Servlet 3
- Fasterxml
- H2 Database


### TODOS

- Repository AutoConfiguration
- Repository interface Proxy
- Transactional Annotation
- AOP