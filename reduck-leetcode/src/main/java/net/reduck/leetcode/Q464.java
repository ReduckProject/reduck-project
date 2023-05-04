package net.reduck.leetcode;

/**
 * 464. 我能赢吗
 * {@link https://leetcode.cn/problems/can-i-win/}
 *
 * @author Gin
 * @since 2023/4/24 15:56
 */
public class Q464 {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if(desiredTotal <= maxChoosableInteger ) {
            return true;
        }

        if(desiredTotal % (maxChoosableInteger + 1) != 0) {
            return true;
        }

        if(maxChoosableInteger % 2 == 0) {
            return (desiredTotal - maxChoosableInteger / 2 - 1) % (maxChoosableInteger + 1) == 0;
        }

        return false;
    }

}

