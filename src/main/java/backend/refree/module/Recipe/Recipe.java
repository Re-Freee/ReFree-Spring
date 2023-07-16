package backend.refree.module.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Double calorie;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private String ingredient;
    private String manuel1;
    @Column(name = "manuel_url1")
    private String manuelUrl1;
    private String manuel2;
    @Column(name = "manuel_url2")
    private String manuelUrl2;
    private String manuel3;
    @Column(name = "manuel_url3")
    private String manuelUrl3;
    private String manuel4;
    @Column(name = "manuel_url4")
    private String manuelUrl4;
    private String manuel5;
    @Column(name = "manuel_url5")
    private String manuelUrl5;
    private String manuel6;
    @Column(name = "manuel_url6")
    private String manuelUrl6;
    private String manuel7;
    @Column(name = "manuel_url7")
    private String manuelUrl7;
    private String manuel8;
    @Column(name = "manuel_url8")
    private String manuelUrl8;
    private String manuel9;
    @Column(name = "manuel_url9")
    private String manuelUrl9;
    private String manuel10;
    @Column(name = "manuel_url10")
    private String manuelUrl10;
    private String manuel11;
    @Column(name = "manuel_url11")
    private String manuelUrl11;
    private String manuel12;
    @Column(name = "manuel_url12")
    private String manuelUrl12;
    private String manuel13;
    @Column(name = "manuel_url13")
    private String manuelUrl13;
    private String manuel14;
    @Column(name = "manuel_url14")
    private String manuelUrl14;
    private String manuel15;
    @Column(name = "manuel_url15")
    private String manuelUrl15;
    private String manuel16;
    @Column(name = "manuel_url16")
    private String manuelUrl16;
    private String manuel17;
    @Column(name = "manuel_url17")
    private String manuelUrl17;
    private String manuel18;
    @Column(name = "manuel_url18")
    private String manuelUrl18;
    private String manuel19;
    @Column(name = "manuel_url19")
    private String manuelUrl19;
    private String manuel20;
    @Column(name = "manuel_url20")
    private String manuelUrl20;
}
