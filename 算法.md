# Acwing算法

### 快速排序quickSort

```java
public void quickSort(int[] nums, int left, int right) {
    if (left >= right)
        return;
    int i = left - 1, j = right + 1, x = nums[left];
    while (i < j) {
        do i++; while (nums[i] < x);
        do j--; while (nums[j] > x);
        if (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
    quickSort(nums, left, j);
    quickSort(nums, j + 1, right);
}
```

### 归并排序mergeSort

mergeSort时间复杂度是稳定O(nlogn)，空间复杂度O(n)，稳定的。quickSort时间复杂度平均O(nlogn)，最坏O(n^2)，最好O(nlogn)，空间复杂度O(nlogn)，不稳定的。

```java
public void mergeSort(int[] nums, int left, int right) {
    if (left >= right)
        return;

    int mid = left + (right - left) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    
    int k = 0, i = left, j = mid + 1;
    int[] temp = new int[right - left + 1];
    while (i <= mid && j <= right)
        if (nums[i] < nums[j])
            temp[k++] = nums[i++];
        else
            temp[k++] = nums[j++];
    while (i <= mid)
        temp[k++] = nums[i++];
    while (j <= right)
        temp[k++] = nums[j++];
    for (i = left, j = 0; i <= right; i++, j++)
        nums[i] = temp[j];

}
```



### KMP

```java
public int kmp(String text, String pattern) {
    int m = pattern.length();
    if (m == 0)
        return 0;
    int n = text.length();
    int[] next = new int[m + 1];
    for (int i = 2, j = 0; i <= m; i++) {
        while (j > 0 && pattern.charAt(i - 1) != pattern.charAt(j))
            j = next[j];
        if (pattern.charAt(i - 1) == pattern.charAt(j))
            j++;
        next[i] = j;
    }
    for (int i = 1, j = 0; i <= n; i++) {
        while (j > 0 && text.charAt(i - 1) != pattern.charAt(j))
            j = next[j];
        if (text.charAt(i - 1) == pattern.charAt(j))
            j++;
        if (j == m)
            return i - m;
    }
    return -1;
}
```

### Trie

```java
public static final int SIZE = 26;

public static class TrieNode {
    TrieNode[] children = new TrieNode[SIZE];
    int times;

    TrieNode() {
        times = 0;
        for (int i = 0; i < SIZE; i++)
            children[i] = null;
    }
}
public static TrieNode root = new TrieNode();

public static void insert(String word) {
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null)
            node.children[index] = new TrieNode();
        node = node.children[index];
    }
    node.times++;
}

public static int query(String word) {
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null)
            return 0;
        node = node.children[index];
    }
    return node.times;
}
```



### 并查集

朴素并查集

```java
public static int[] p;

public static int find(int x) {
    if (p[x] != x)
        p[x] = find(p[x]);
    return p[x];
}

p[find(a)] = find(b);
```

### 邻接矩阵


```java
public class Main{
    public static final int INF = 0x3f3f3f3f;
    public static int n;
    public static int[][] g;
    public static int[] dist;
    public static boolean[] visit;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        int m = scan.nextInt();
        g = new int[n + 1][n + 1];
        dist = new int[n + 1];
        visit = new boolean[n + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                if (i == j)
                    g[i][j] = 0;
                else
                    g[i][j] = INF;
        for (int i = 0; i < m; i++) {
            int x = scan.nextInt(), y = scan.nextInt(), z = scan.nextInt();
            g[a][b] = Math.min(g[a][b], c);
        }
        int res = dijkstra();
        System.out.println(res);
    }
}
```

### Dijkstra

边权不能是负数！
1.dist[1] = 0, dist[i] = +∞
2.for i 1 ~ n
t <- 不在s中的，距离最近的点 – n2n2 / n
s <- t – n
用t更新其他点的距离 – m / mlogn

```java
public static int dijkstra() {
    for (int i = 1; i <= n; i++)
        dist[i] = INF;
    dist[1] = 0;
    for (int i = 0; i < n - 1; i++) {
        int t = -1;
        for (int j = 1; j <= n; j++)
            if (!visit[j] && (t == -1 || dist[t] > dist[j]))
                t = j;
        if (t == n)
            break;
        for (int j = 1; j <= n; j++)
            dist[j] = Math.min(dist[j], dist[t] + g[t][j]);
        visit[t] = true;
    }
    if (dist[n] == INF)
        return -1;
    return dist[n];
}
```

### 优化Dijkstra

```java
public static int dijkstra() {
        for (int i = 1; i <= n; i++)
            dist[i] = INF;
        dist[1] = 0;
        PriorityQueue<Node> heap = new PriorityQueue<>((node1, node2) -> node1.length - node2.length);
        heap.add(new Node(1, 0));
        while (!heap.isEmpty()) {
            Node top = heap.poll();
            int length = top.length, cur = top.node;
            if (visit[cur])
                continue;
            visit[cur] = true;
            for (Node next: map.get(cur)) {
                int node = next.node, cost = next.length;
                if (dist[node] > length + cost) {
                    dist[node] = length + cost;
                    heap.add(new Node(node, dist[node]));
                }
            }
        }
        if (dist[n] == INF)
            return -1;
        return dist[n];
    }
```



### Bellman-ford

O(nm)

```java
public static int bellman_ford() {
    for (int i = 1; i <= n; i++)
        dist[i] = INF;
    dist[1] = 0;
    for (int i = 0; i < k; i++) {
        for (int j = 1; j <= n; j++)
            backup[j] = dist[j]; // deep copy
        for (int k = 0; k < m; k++) {
            int x = edges[k].x;
            int y = edges[k].y;
            int z = edges[k].z;
            dist[y] = Math.min(dist[y], backup[x] + z);
        }
    }
    if (dist[n] > INF / 2)
        return -1;
    else
        return dist[n];
}

```

### SPFA (队列优化的Bellman-ford算法)

一般O(m)，最坏O(nm)。n表示点数，m表示边数。

```java
public static int spfa() {
    for (int i = 1; i <= n; i++)
        dist[i] = INF;
    dist[1] = 0;
    Queue<Integer> queue = new LinkedList<>();
    queue.add(1);
    visit[1] = true;
    while (!queue.isEmpty()) {
        int t = queue.poll();
        visit[t] = false;
        for (Node cur: map.get(t)) {
            int node = cur.node, length = cur.length;
            if (dist[node] > dist[t] + length) {
                dist[node] = dist[t] + length;
                if (!visit[node]) {
                    queue.add(node);
                    visit[node] = true;
                }
            }
        }
    }
    return dist[n];
}

```

### SPFA 判断图中是否存在负环

O(nm)，n表示点数，m表示边数。

```java
public static boolean spfa() {
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        queue.add(i);
        visit[i] = true;
    }
    while (!queue.isEmpty()) {
        int t = queue.poll();
        visit[t] = false;
        for (Node cur: map.get(t)) {
            int node = cur.node, length = cur.length;
            if (dist[node] > dist[t] + length) {
                dist[node] = dist[t] + length;
                count[node] = count[t] + 1;
                if (count[node] >= n)
                    return true;
                if (!visit[node]) {
                    queue.add(node);
                    visit[node] = true;
                }
            }
        }
    }
    return false;
}

```

Floyd
O(n3)O(n3)

```java
public static void floyd() {
    for (int k = 1; k <= n; k++)
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
}

```



