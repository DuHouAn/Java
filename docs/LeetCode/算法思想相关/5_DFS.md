# DFS

## 1、岛屿的个数（200）

[200. 岛屿的个数](https://leetcode-cn.com/problems/number-of-islands/)

给定一个由 `'1'`（陆地）和 `'0'`（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。

**示例 1:**

```
输入:
11110
11010
11000
00000

输出: 1


```

**示例 2:**

```
输入:
11000
11000
00100
00011

输出: 3


```

```java
private int m;
private int n;

//用来标记是否是同一个岛屿
private boolean[][] visited;

//TODO:二维数组四个方向查找的小技巧
private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

private boolean isArea(int x,int y){
    return (x>=0 && x<m) && (y>=0 && y<n);
}

//找到一个岛，就将这个岛标记
private void dfs(char[][] grid, int x, int y) {
    //[x,y]是陆地，并且标记为已经访问的了
    visited[x][y]=true;
    //向四个方向去扩散
    for(int i=0;i<4;i++){
        int newx=x+d[i][0];
        int newy=y+d[i][1];
        if(isArea(newx,newy)){
            //[newx,newy]合法
            if(grid[newx][newy]=='1' && visited[newx][newy]==false){
                //[newx,newy]位置陆地，并且未被标记，进行深度优先遍历
                //如果已经被标记了，则直接忽略
                ////找到一个岛，就将整个岛进行标记
                dfs(grid,newx,newy);
            }
        }
    }
}

public int numIslands(char[][] grid) {
    m=grid.length;
    if(m==0){
        return 0;
    }
    n=grid[0].length;
    visited=new boolean[m][n];
    int res=0;
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            //有陆地，并且这块陆地未被标记，则就是一个新的岛屿
            if(grid[i][j]=='1' && visited[i][j]==false){
                res++;
                //使用floodfill算法来标记这个新岛屿岛屿
                dfs(grid,i,j);
            }
        }
    }
    return res;
}


```

## 2、岛屿的最大面积（695）

[695. 岛屿的最大面积](https://leetcode-cn.com/problems/max-area-of-island/)

给定一个包含了一些 0 和 1的非空二维数组 `grid` , 一个 **岛屿** 是由四个方向 (水平或垂直) 的 `1` (代表土地) 构成的组合。你可以假设二维矩阵的四个边缘都被水包围着。

找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为0。)

**示例 1:**

```
[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]


```

对于上面这个给定矩阵应返回 `6`。注意答案不应该是11，因为岛屿只能包含水平或垂直的四个方向的‘1’。

**示例 2:**

```
[[0,0,0,0,0,0,0,0]]


```

对于上面这个给定的矩阵, 返回 `0`。

**注意:** 给定的矩阵`grid` 的长度和宽度都不超过 50。

```java
private int m;
private int n;

//用来标记是否是同一个岛屿
private boolean[][] visited;

//TODO:二维数组四个方向查找的小技巧
private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

private boolean isArea(int x,int y){
    return (x>=0 && x<m) && (y>=0 && y<n);
}

//找到一个岛，就将这个岛标记
//返回的值就是[x,y]所在岛屿的面积
private int dfs(int[][] grid, int x, int y) {
    if(grid[x][y] == 0){
        return 0;
    }

    //[x,y]是陆地，并且标记为已经访问的了
    visited[x][y]=true;

    int area=1;

    //向四个方向去扩散
    for(int i=0;i<4;i++){
        int newx=x+d[i][0];
        int newy=y+d[i][1];
        if(isArea(newx,newy)){
            //[newx,newy]合法
            if(grid[newx][newy]==1 && visited[newx][newy]==false){
                //[newx,newy]位置陆地，并且未被标记，进行深度优先遍历
                //如果已经被标记了，则直接忽略
                //找到一个岛，就将整个岛进行标记
                area+=dfs(grid,newx,newy);
            }
        }
    }
    return area;
}

public int maxAreaOfIsland(int[][] grid) {
    m=grid.length;
    if(m==0){
        return 0;
    }
    n=grid[0].length;
    visited=new boolean[m][n];
    int res=0;
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            //有陆地，并且这块陆地未被标记，则就是一个新的岛屿
            if(grid[i][j]==1 && visited[i][j]==false){
                //使用floodfill算法来标记这个新岛屿岛屿
                res=Math.max(res,dfs(grid,i,j));
            }
        }
    }
    return res;
}


```

## 3、朋友圈（547）

[547. 朋友圈](https://leetcode-cn.com/problems/friend-circles/)

班上有 **N** 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。

给定一个 **N \* N** 的矩阵 **M**，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生**互为**朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。

**示例 1:**

```
输入: 
[[1,1,0],
 [1,1,0],
 [0,0,1]]
输出: 2 
说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
第2个学生自己在一个朋友圈。所以返回2。


```

**示例 2:**

```
输入: 
[[1,1,0],
 [1,1,1],
 [0,1,1]]
输出: 1
说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。



```

**注意：**

1. N 在[1,200]的范围内。
2. 对于所有学生，有M[i][i] = 1。
3. 如果有M[i][j] = 1，则有M[j][i] = 1。

```java
//思路：深度优先遍历
//1、假设M是(n*n) 矩阵，说明有 n 个人。
//2、遍历编号为 i 的朋友，进行朋友圈标记，是同一个朋友圈的必然会被标记。
//3、是同一个朋友圈，只统计一次。
private int n;
private boolean[] friend;

public int findCircleNum(int[][] M) {
    n = M.length;
    if( n == 0){
        return 0;
    }
    friend = new boolean[n];

    int res = 0;
    for(int i=0;i<n;i++){
        if(!friend[i]){
            res++;
            dfs(M,i);
        }
    }
    return res;
}

//遍历编号为 x 的人员的朋友圈，并进行标记
//标记后，只统计一次
private void dfs(int[][] M,int x){
    friend[x] = true;
    for(int i=0;i<n;i++){
        if(i != x){ //看看其他人员是否是朋友
            if(M[x][i] == 1 && !friend[i]){ //是朋友，但是还未被标记
                dfs(M,i);
            }
        }
    }
}


```



## 4、被围绕的区域（130）

[130. 被围绕的区域](https://leetcode-cn.com/problems/surrounded-regions/)
 给定一个二维的矩阵，包含 `'X'` 和 `'O'`（**字母 O**）。

找到所有被 `'X'` 围绕的区域，并将这些区域里所有的 `'O'` 用 `'X'` 填充。

**示例:**

```
X X X X
X O O X
X X O X
X O X X



```

运行你的函数后，矩阵变为：

```
X X X X
X X X X
X X X X
X O X X



```

**解释:**

被围绕的区间不会存在于边界上，换句话说，任何边界上的 `'O'` 都不会被填充为 `'X'`。 任何不在边界上，或不与边界上的 `'O'` 相连的 `'O'` 最终都会被填充为 `'X'`。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。

```java
class Solution {
    private int m;
    private int n;

    //标记边界上的元素是否是'O'
    private boolean[][] isO;

    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    private boolean isArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    //深度优先遍历，来标记边界 'O'
    private void dfs(char[][] board, int x, int y) {
        isO[x][y]=true;
        for(int i=0;i<4;i++){
            int newx=x+d[i][0];
            int newy=y+d[i][1];
            if(isArea(newx,newy)){
                if(board[newx][newy]=='O' && isO[newx][newy]==false){
                        dfs(board,newx,newy);
                }
            }
        }
    }

    /**
     * 思路：
     * 除了和边界有接触的’O'的区域，其他的‘O'的区域都会变成'X'。
     * 所以扫描一遍边界，对于在边界上的’O', 通过BFS标记与它相邻的'O'。
     */
    public void solve(char[][] board) {
        m=board.length;
        if(m==0){
            return;
        }
        n=board[0].length;
        isO= new boolean[m][n];
        //对边界进行深度优先遍历，标记‘O’
        for(int j=0;j<n;j++){
            if(board[0][j]=='O' && isO[0][j]==false){
                dfs(board,0,j);
            }
            if(board[m-1][j]=='O' && isO[m-1][j]==false){
                dfs(board,m-1,j);
            }
        }
        for(int i=0;i<m;i++){
            if(board[i][0]=='O' && isO[i][0]==false){
                dfs(board,i,0);
            }
            if(board[i][n-1]=='O' && isO[i][n-1]==false){
                dfs(board,i,n-1);
            }
        }

        //对整个数组进行遍历，覆盖非边界的 'O'
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(isO[i][j]==false && board[i][j]=='O'){
                    board[i][j]='X';
                }
            }
        }
    }
}


```



## 5、太平洋大西洋水流问题（417）

[417. 太平洋大西洋水流问题](https://leetcode-cn.com/problems/pacific-atlantic-water-flow/)

给定一个 `m x n` 的非负整数矩阵来表示一片大陆上各个单元格的高度。“太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。

规定水流只能按照上、下、左、右四个方向流动，且只能从高到低或者在同等高度上流动。

请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。

**提示：**

1. 输出坐标的顺序不重要
2. *m* 和 *n* 都小于150 

**示例：**

```
给定下面的 5x5 矩阵:

  太平洋 ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * 大西洋

返回:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (上图中带括号的单元).



```

```java
private int m;
private int n;

private boolean[][] pacific;
private boolean[][] atlantic;


//TODO:二维数组四个方向查找的小技巧
private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

private boolean isArea(int x,int y){
    return (x>=0 && x<m) && (y>=0 && y<n);
}

private void dfs(int[][] matrix,boolean[][] visited,int x,int y){
    visited[x][y]=true;
    for(int i=0;i<4;i++){
        int newx=x+d[i][0];
        int newy=y+d[i][1];
        if(isArea(newx,newy)){
            //[newx,newy]地势高，并且未被标记
            if(visited[newx][newy]==false && (matrix[newx][newy]>=matrix[x][y])){
                dfs(matrix,visited,newx,newy);
            }
        }
    }
}

public List<int[]> pacificAtlantic(int[][] matrix) {
    List<int[]> res=new ArrayList<>();
    m=matrix.length;
    if(m==0){
        return res;
    }
    n=matrix[0].length;
    pacific=new boolean[m][n];
    atlantic=new boolean[m][n];

    for(int i=0;i<m;i++){
        dfs(matrix,pacific,i,0);
        dfs(matrix,atlantic,i,n-1);
    }
    for(int j=0;j<n;j++){
        dfs(matrix,pacific,0,j);
        dfs(matrix,atlantic,m-1,j);
    }
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            if(pacific[i][j] && atlantic[i][j]){
                res.add(new int[]{i,j});
            }
        }
    }
    return res;
}
```