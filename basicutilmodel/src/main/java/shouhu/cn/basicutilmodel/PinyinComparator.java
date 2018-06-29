package shouhu.cn.basicutilmodel;


import java.util.Comparator;

import cn.swiftpass.cn.standardwallet.login.domain.SortEntity;

public class PinyinComparator implements Comparator<SortEntity> {

    public int compare(SortEntity o1, SortEntity o2) {
        if (o1.getLetters().equals("@") || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#") || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }

}
