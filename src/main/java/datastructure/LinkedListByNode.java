package datastructure;

import lombok.Data;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lmc
 * @date 2020/3/3 9:31
 */
@Data
public class LinkedListByNode {
    private Object data;
    private LinkedListByNode next;

    public static void main(String[] args) {
        LinkedListByNode root = new LinkedListByNode();
        root.setData("头结点");
        LinkedListByNode node1 = new LinkedListByNode();

        node1.setData("子节点1");
        LinkedListByNode node2 = new LinkedListByNode();
        node2.setData("子节点2");
        //将节点连接起来
        root.setNext(node1);
        node1.setNext(node2);
        System.out.println("使用while循环遍历链表");
        getDataByLoop(root);
        System.out.println("使用递归遍历链表");
        getDataByRecursion(root);
    }

    /*
    使用循环进行遍历
     */
    public static void getDataByLoop(LinkedListByNode node) {
        while (node != null) {
            System.out.println(node.data);
            node = node.getNext();
        }
    }

    /*
    使用递归遍历
     */
    public static void getDataByRecursion(LinkedListByNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getData());
        getDataByRecursion(node.getNext());
    }
}
