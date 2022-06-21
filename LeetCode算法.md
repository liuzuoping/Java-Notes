# LeetCode算法

### LeetCode

#### 综合算法：

http://www.cyc2018.xyz/#%E7%AE%97%E6%B3%95

#### 左神笔记：

https://blog.csdn.net/weixin_54884881/article/details/121140623

#### [1524. 和为奇数的子数组数目](https://leetcode-cn.com/problems/number-of-sub-arrays-with-odd-sum/)

给你一个整数数组 `arr` 。请你返回和为 **奇数** 的子数组数目。

由于答案可能会很大，请你将结果对 `10^9 + 7` 取余后返回。

```java
class Solution {
    public int numOfSubarrays(int[] arr) {
        final int MODULO = (int) 1e9 + 7;;
        int odd = 0, even = 1;
        int subarrays = 0;
        int sum = 0;
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            sum += arr[i];
            subarrays = (subarrays + (sum % 2 == 0 ? odd : even)) % MODULO;
            if (sum % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        return subarrays;
    }
}
```

### 微软

#### [415. 字符串相加](https://leetcode-cn.com/problems/add-strings/)

给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。

你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。

```java
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0, i = num1.length()-1, j = num2.length()-1;
        while(i >= 0 || j >= 0 || carry != 0){
            if(i>=0) carry += num1.charAt(i--)-'0';
            if(j>=0) carry += num2.charAt(j--)-'0';
            sb.append(carry%10);
            carry /= 10;
        }
        return sb.reverse().toString();
    }
}
```

字符串拼接

s="aabbcc"

arr=["a","abb","cc"]

return:Ture

判断s是否能由arr中的元素组成，元素可以重复，但不能拆开

#### [139. 单词拆分](https://leetcode-cn.com/problems/word-break/)

给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。

注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。

```java
public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
```

##### **NC13** **二叉树的最大深度**

求给定二叉树的最大深度，深度是指树的根节点到任一叶子节点路径上节点的数量。最大深度是所有叶子节点的深度的最大值。（注：叶子节点是指没有子节点的节点。）

```java
public class Solution {
    public int maxDepth(TreeNode root){
        if(root==null)
            return 0;
        int lDepth = maxDepth(root.left);
        int rDepth = maxDepth(root.right);
        return 1+(lDepth>rDepth?lDepth:rDepth);
    }
}
```

#### [160. 相交链表](https://leetcode-cn.com/problems/intersection-of-two-linked-lists/)

给你两个单链表的头节点 `headA` 和 `headB` ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 `null` 。

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode l1=headA,l2=headB;
        while(l1!=l2){
            l1=(l1==null)?headB:l1.next;
            l2=(l2==null)?headA:l2.next;
        }
        return l1;
    }
}
```

##### **JZ68** **二叉搜索树的最近公共祖先**

给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

1.对于该题的最近的公共祖先定义:对于有根树T的两个节点p、q，最近公共祖先LCA(T,p,q)表示一个节点x，满足x是p和q的祖先且x的深度尽可能大。在这里，一个节点也可以是它自己的祖先.

2.二叉搜索树是若它的左子树不空，则左子树上所有节点的值均小于它的根节点的值； 若它的右子树不空，则右子树上所有节点的值均大于它的根节点的值

3.所有节点的值都是唯一的。

4.p、q 为不同节点且均存在于给定的二叉搜索树中。

```java
import java.util.*;
public class Solution {
    public TreeNode commonAncestor (TreeNode root, int p, int q) {
        if (null == root) return null;
        if (root.val == p || root.val == q) return root;
        // 通过递归假设我们知道了运算结果 题目含义是不会出现重复节点
        TreeNode left = commonAncestor(root.left, p, q);
        TreeNode right = commonAncestor(root.right, p, q);
        if (left == null) return right;
        else if (right == null) return left;
        else return root;
    } 
    public int lowestCommonAncestor (TreeNode root, int p, int q) {
        // write code here
        return commonAncestor(root, p, q).val;
    }
}
```

#### [LeetCode-077-组合](https://segmentfault.com/a/1190000040909773)

给定两个整数 `n` 和 `k`，返回范围 `[1, n]` 中所有可能的 `k` 个数的组合。

你可以按 **任何顺序** 返回答案。

```
输入：n = 4, k = 2
输出：
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
```

