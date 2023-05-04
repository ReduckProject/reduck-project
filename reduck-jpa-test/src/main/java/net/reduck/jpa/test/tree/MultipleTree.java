//package net.reduck.jpa.test.tree;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author Gin
// * @since 2023/2/24 16:32
// */
//public abstract class MultipleTree<T> {
//
//    public List<T> tree(List<T> menus) {
//        List<T> rootList = new ArrayList<>();
//
//        Map<Long, Menu> menuMap = menus.stream().collect(Collectors.toMap(Menu::getId, e -> e));
//
//        for(T menu : menus) {
//            Object pare
//            if(getParentId(menu) == null) {
//                rootList.add(menu);
//            }else {
//                Menu parentMenu = menuMap.get(get);
//                if(parentMenu != null) {
//                    parentMenu.getChildren().add(menu);
//                }
//            }
//        }
//
//        return rootList;
//    }
//
//    public abstract Object getUniqueId(T menu);
//
//    public abstract Object getParentId(T menu);
//
//
//}
