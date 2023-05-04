package net.reduck.chat.server.mock;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2023/1/13 14:49
 */
@Data
public class MocPerson {

    private int age;

    private boolean man;

    private boolean next;

    private MocPerson partner;

    private MocPerson mom;

    private MocPerson dad;

    private List<MocPerson>  children = new ArrayList<>();
}
