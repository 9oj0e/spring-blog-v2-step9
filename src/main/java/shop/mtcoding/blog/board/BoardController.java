package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@RestController // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // TODO: 글목록조회 API 필요 -> @GetMapping("/")
    @GetMapping("/")
    public ResponseEntity<?> main(){
        List<BoardResponse.MainDTO> respDTO = boardService.글목록조회();
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // TODO: 글상세보기 API 필요 -> @GetMapping("/api/boards/{id}/detail")
    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.글상세보기(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // TODO: 글조회 API 필요 -> @GetMapping("/api/boards/{id}")
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id){
        BoardResponse.DTO respDTO = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.CreateDTO reqDTO, Errors errors) {

//        if(errors.hasErrors()){
//            for (FieldError error : errors.getFieldErrors()){
//                System.out.println(error.getField());
//                System.out.println(error.getDefaultMessage());
//
//                throw new Exception400(error.getDefaultMessage()+" : "+error.getField());
//            }
//        }


        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BoardRequest.UpdateDTO reqDTO, Errors errors) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }

}
