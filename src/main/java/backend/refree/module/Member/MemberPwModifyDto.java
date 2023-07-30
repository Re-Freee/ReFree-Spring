package backend.refree.module.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberPwModifyDto {
    private String email;
    @NotBlank(message = "새 비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호를 다시 입력해 주세요.")
    private String checkNewPassword;

    public memberEntity toEntity(){
        memberEntity member = memberEntity.builder()
                .password(newPassword)
                .checkPassword(checkNewPassword).build();
        return member;
    }
}
