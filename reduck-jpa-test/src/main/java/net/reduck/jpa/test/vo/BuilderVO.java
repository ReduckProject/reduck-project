package net.reduck.jpa.test.vo;

import net.reduck.asm.jsr269.Builder;

/**
 * @author Reduck
 * @since 2022/9/2 18:13
 */
public class BuilderVO {

    private String boy;

    private String girl;

    public String getBoy() {
        return boy;
    }

    public void setBoy(String boy) {
        this.boy = boy;
    }

    public String getGirl() {
        return girl;
    }

    @Builder
    public void setGirl(String girl) {
        this.girl = girl;
    }
}
