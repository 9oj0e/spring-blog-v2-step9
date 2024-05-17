package shop.mtcoding.blog.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class UpdateDTO {
        @Size(min = 1, max = 10, message = "제목은 10자를 초과할 수 없습니다")
        @NotEmpty(message = "제목은 공백일 수 없습니다") // null도 안되고, 공백만 있는 것도 안된다
        private String title;
        @NotEmpty
        private String content;
    }

    @Data
    public static class CreateDTO {
        @Size(min = 1, max = 10, message = "제목은 10자를 초과할 수 없습니다")
        @NotEmpty(message = "제목은 공백일 수 없습니다") // null도 안되고, 공백만 있는 것도 안된다
        private String title;
        @NotEmpty
        private String content;

        // DTO를 클라이언트로 부터 받아서, PC에 전달하기 위해 사용
        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}
