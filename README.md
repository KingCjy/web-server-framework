Java Web Server Framework <small><small><small>for study version 3</small></small></small>
---

## Comment

스프링 프레임워크 따라만들기 3번째 버전입니다.
이전 버전은 [이곳](https://github.com/KingCjy/web-server-framework)에서 확인하실 수 있습니다.

### TODOS
- ~~BeanFactory~~
- ~~HandlerMethodArgumentResolver~~
- ~~AnnotationHandler~~
- ~~BeanDefinitionScannerProvider~~
- Jpa Repository AutoConfiguration
- Repository interface Proxy
- Transactional Annotation
- Query Annotation
- AOP
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

    @RequestMapping("/boards")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards() {
        List<BoardDto.BoardResponseDto> dtoList = boardService.findAllBoards();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findBoardById(@PathVariable(name = "id") Long id) {
        BoardDto.BoardResponseDto dto = boardService.findBoardById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    public ResponseEntity<?> addBoard(@RequestBody BoardDto.BoardRequestDto dto) {
        boardService.addBoard(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBoardById(@PathVariable(name = "id", required = true) Long id,
                                            @RequestBody BoardDto.BoardRequestDto dto) {
        boardService.updateBoard(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBoardById(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
        return boardRepository.findAll().stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardDto.BoardResponseDto findBoardById(Long id) {
        Board board = boardRepository.findById(id);

        return new BoardDto.BoardResponseDto(board);
    }

    public void addBoard(BoardDto.BoardRequestDto dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .build();

        boardRepository.save(board);
    }

    public void updateBoard(Long id, BoardDto.BoardRequestDto dto) {
        Board board = boardRepository.findById(id);

        board.setTitle(dto.getTitle());
        board.setContents(dto.getContents());

        boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
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

    @Getter
    @Setter
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

    @Getter
    @Setter
    public static class BoardRequestDto {
        private String title;
        private String contents;
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