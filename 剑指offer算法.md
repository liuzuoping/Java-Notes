### 剑指offer

#### 链表

##### **JZ6** **从尾到头打印链表**

输入一个链表的头节点，按链表从尾到头的顺序返回每个节点的值（用数组返回）。

```java
import java.util.*;
public class Solution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<ListNode> stack=new Stack<ListNode>();
        ListNode temp=listNode;
        while(temp!=null){
            stack.push(temp);
            temp=temp.next;
        }
        int Size=stack.size();
        ArrayList<Integer> print=new ArrayList<Integer>();
        for(int i=0;i<Size;i++){
            print.add(stack.pop().val);
        }
        return print;
    }
}
```

##### **JZ24** **反转链表**

给定一个单链表的头结点pHead(该头节点是有值的，比如在下图，它的val是1)，长度为n，反转该链表后，返回新链表的表头。

```java
import java.util.*;
public class Solution {
    public ListNode ReverseList(ListNode head) {
        Stack<ListNode> stack=new Stack();
        while(head!=null){
            stack.push(head);
            head=head.next;
        }
        if(stack.isEmpty()){
            return null;
        }
        ListNode node=stack.pop();
        ListNode dummy=node;
        while(!stack.isEmpty()){
            node.next=stack.pop();
            node=node.next;
        }
        node.next=null;
        return dummy;
    }
}
```

##### **JZ25** **合并两个排序的链表**

输入两个递增的链表，单个链表的长度为n，合并这两个链表并使新链表中的节点仍然是递增排序的。

数据范围：0≤*n*≤1000，−1000≤节点值≤1000
要求：空间复杂度 O(1)*O*(1)，时间复杂度 O(n)*O*(*n*)

输入：

```
{1,3,5},{2,4,6}
```

返回值：

```
{1,2,3,4,5,6}
```

```java
import java.util.*;
public class Solution {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1==null||list2==null){
            return list1!=null?list1:list2;
        }
        if(list1.val<=list2.val){
            list1.next=Merge(list1.next,list2);
            return list1;
        }else{
            list2.next=Merge(list1,list2.next);
            return list2;
        }
    }
}
```

##### **JZ52** **两个链表的第一个公共结点**

输入两个无环的单向链表，找出它们的第一个公共结点，如果没有公共节点则返回空。（注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）

```java
public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode l1=pHead1,l2=pHead2;
        while(l1!=l2){
            l1=(l1==null)?pHead2:l1.next;
            l2=(l2==null)?pHead1:l2.next;
        }
        return l1;
    }
}
```

##### **JZ23** **链表中环的入口结点**

给一个长度为n链表，若其中包含环，请找出该链表的环的入口结点，否则，返回null。

数据范围：n*≤10000，1<=结点值<=10000

要求：空间复杂度 O(1)，时间复杂度 O(n)

```java
public class Solution {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if(pHead==null){
            return null;
        }
        ListNode fast=pHead;
        ListNode slow=pHead;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                break;
            }
        }
        if(fast==null||fast.next==null){
            return null;
        }
        fast=pHead;
        while(fast!=slow){
            fast=fast.next;
            slow=slow.next;
        }
        return fast;
    }
}
```

##### **JZ22** **链表中倒数最后k个结点**

输入一个长度为 n 的链表，设链表中的元素的值为 ai ，返回该链表中倒数第k个节点。

如果该链表长度小于k，请返回一个长度为 0 的链表。

输入：

```
{1,2,3,4,5},2
```

返回值：

```
{4,5}

```

说明：

```
返回倒数第2个节点4，系统会打印后面所有的节点来比较。 
```

```java
    public ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        if(pHead==null||k==0){
            return null;
        }
        Stack<ListNode> stack=new Stack<>();
        while(pHead!=null){
            stack.push(pHead);
            pHead=pHead.next;
        }
        if(stack.size()<k){
            return null;
        }
        ListNode node=stack.pop();
        while(--k>0){
            ListNode temp=stack.pop();
            temp.next=node;
            node=temp;
        }
        return node;
    }
```

##### **JZ35** **复杂链表的复制**

输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random指向一个随机节点），请对此链表进行深拷贝，并返回拷贝后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）。 下图是一个含有5个结点的复杂链表。图中实线箭头表示next指针，虚线箭头表示random指针。为简单起见，指向null的指针没有画出。

```java
import java.util.*;
public class Solution {
    public RandomListNode Clone(RandomListNode pHead) {
        HashMap<RandomListNode,RandomListNode> map=new HashMap<RandomListNode,RandomListNode>();
        RandomListNode node=pHead;
        while(node!=null){
            RandomListNode newNode=new RandomListNode(node.label);
            map.put(node,newNode);
            node=node.next;
        }
        node=pHead;
        while(node!=null){
            RandomListNode cur=map.get(node);
            cur.next=(node.next==null)?null:map.get(node.next);
            cur.random=(node.random==null)?null:map.get(node.random);
            node=node.next;
        }
        return map.get(pHead);
    }
}
```

##### **JZ76** **删除链表中重复的结点**

在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表 1->2->3->3->4->4->5 处理后为 1->2->5

输入：

```
{1,2,3,3,4,4,5}
```

返回值：

```
{1,2,5}
```

```java
public class Solution {
    public ListNode deleteDuplication(ListNode pHead) {
        ListNode dummy=new ListNode(-1);
        ListNode tail=dummy;
        while(pHead!=null){
            if(pHead.next==null||pHead.next.val!=pHead.val){
                tail.next=pHead;
                tail=pHead;
            }
            while(pHead.next!=null&&pHead.next.val==pHead.val){
                pHead=pHead.next;
            }
            pHead=pHead.next;
        }
        tail.next=null;
        return dummy.next;
    }
}
```

### 