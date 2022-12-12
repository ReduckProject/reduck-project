package net.reduck.api.doc.descriptor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/12/12 15:19
 */
@Data
public class ApiDescriptor {

    private String url;

    private boolean valid;

    private boolean deprecated;

    private List<String> methods = new ArrayList<>();
}
