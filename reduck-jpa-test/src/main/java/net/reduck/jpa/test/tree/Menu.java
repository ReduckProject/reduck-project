package net.reduck.jpa.test.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin
 * @since 2023/2/24 16:37
 */
@Data
public class Menu {

    private Long id;

    private Long parentId;

    private List<Menu> children = new ArrayList<>();

}
