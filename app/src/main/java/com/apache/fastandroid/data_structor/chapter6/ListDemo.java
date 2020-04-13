package com.apache.fastandroid.data_structor.chapter6;

/**
 * Created by Jerry on 2020-04-12.
 */
public class ListDemo {
    public static void main(String[] args) {

        Node origin =  new Node(1, new Node(2,new Node(3,new Node(4,new Node(2,null)))));
        //System.out.println("origin:"+ origin);
        //Node reverse = reverse(origin);


        //System.out.println("reverse2:"+ reverse2(origin));
        Node one = createNode(1);
        Node two = createNode(2);
        Node three = createNode(3);
        Node four = createNode(4);
        Node five = createNode(5);
        one.setNext(two);
        two.setNext(three);
        three.setNext(four);
        four.setNext(five);
        five.setNext(two);






        System.out.println("checkCircle:"+checkCircle(one));
        System.out.println("checkCircle2:"+ checkCircle2(one));


        System.out.println("findLoopPort:"+findLoopPort(one).data);
        System.out.println("findLoopPort2:"+ findLoopPort2(one).data);



        Node one1 = createNode(1);
        Node two1 = createNode(2);
        Node three1 = createNode(3);
        Node four1 = createNode(4);
        one1.setNext(two1);
        two1.setNext(three1);
        three1.setNext(four1);
        System.out.println(deleteLastKth2(one1, 2));
    }



    // 检测环
    public static boolean checkCircle(Node list) {
        if (list == null) return false;

        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (slow == fast) return true;
        }

        return false;
    }

    public  static  boolean checkCircle2(Node list){
        if (list == null){
            return  false;
        }
        Node fast = list.next;
        Node slow = list;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){
                return true;
            }
        }
        return  false;

    }


    public static Node findLoopPort(Node head) {
        Node slow = head;
        Node fast = head;
        //先判断该链表是否有环
        while(fast != null && fast.next !=null) {
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                System.out.println(String.format("相遇了 meet node: %s", fast.data));
                break;
            }
        }
        if(fast == null || fast.next == null) {
            return null;
        }
        //如果链表有环，则将slow设置指向链表头，此时fast指向相遇点，然后同时开始移动，直到两个指针相遇
        slow = head;
        System.out.println(String.format("slow: %s, fast: %s", slow.data,fast.data));
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }



    public  static  Node findLoopPort2(Node head){
        if (head == null){
            return  null;
        }
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){
                System.out.println(String.format("findLoopPort2 相遇了 meet node: %s", fast.data));
                break;
            }
        }
        if (fast == null || fast.next == null){
            return null;
        }
        slow = head;
        System.out.println(String.format("findLoopPort2 slow: %s, fast: %s", slow.data,fast.data));

        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;

    }


    public static Node deleteLastKth2(Node header, int k) {
        Node dummy = createNode(0);
        dummy.next = header;
        System.out.println(String.format("deleteLastKth2 dummy: %s",dummy));

        Node fast = dummy;
        Node slow = dummy;
        //fast先向前移动 k +1个位置
        for (int i = 1; i <= k+1; i++){
            fast = fast.next;
        }

        //将fast移动到null的位置
        while (fast != null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;

        //虚拟节点的下一个节点
        return dummy.next;
    }

    //[1,2,3,4]

    /**
     * [1,2,3,4]
     * step1: 将当前节点1的下一个节点2保存在临时变量
     * step2: 将当前节点1的下一个节点置为空，也就是myNext
     * step3: 将myNext置为1， 也就是当前的current
     * step4: 将当前节点curr变为2（也就是step1中临时保存的nextTemp），之前的curr为1
     *
     * @param list
     * @return
     */
    public static Node reverse2(Node list) {
        Node curr = list, myNext = null;
        while (curr != null) {
            //step1
            Node nextTemp = curr.next;
            System.out.println("stpe1: 当前节点为:"+ curr.getData() );
            if (nextTemp != null ){
                System.out.println(String.format("当前节点: %s 的下一个节点 nextTemp 为 : %s",curr.data,nextTemp.data));
            }else{
                System.out.println(String.format("当前节点: %s 的下一个节点 nextTemp 为空",curr.data));
            }
            //step2 将2的下一个节点变为 1
            curr.next = myNext;
            if (myNext != null){
                System.out.println(String.format("stpe2: 将当前节点 %s 的下一个节点置为 %s:  ",curr.data,myNext.data));
            }else {
                System.out.println(String.format("stpe2: 将当前节点 %s 的下一个节点置为空 ",curr.data));
            }

            //step3
            myNext = curr; //将当前节点 1 变为下一个current的next节点
            System.out.println("stpe3: 将下一个节点myNex变为:  "+curr.data);

            //step4
            curr = nextTemp; //将当前节点的下一个节点变为curr，也就是2
            if (curr != null){
                System.out.println("stpe4: 将当前节点curr变为:  "+curr.data);
            }else{
                System.out.println("stpe4: 将当前节点curr变为空，结束循环  ");
            }

            System.out.println("------------> myNext:"+ myNext);

        }
        return myNext;
    }


    public static Node createNode(int value){
        return new Node(value,null);
    }

    public static class Node{
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }


        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getData() {
            return data;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"data\":").append(data);
            sb.append(",\"next\":").append(next);
            sb.append('}');
            return sb.toString();
        }
    }
}
