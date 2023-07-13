package backend.refree.module.Member;

import javax.persistence.*;

public class memberEntity {
    @Id
    private int member_id;
    private String email;
    private String password;
    private String nicknama;

}
