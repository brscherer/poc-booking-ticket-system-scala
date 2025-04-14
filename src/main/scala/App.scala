class Node(val s: Int, val e: Int):
  var left: Option[Node] = None
  var right: Option[Node] = None
  var total: Long = 0L
  var mx: Long = 0L

class SegTree(start: Int, end: Int, initialValue: Int):
  val root: Node = build(start, end)
  
  private def build(l: Int, r: Int): Node =
    if l > r then
      null
    else if l == r then
      val node = Node(l, r)
      node.total = initialValue
      node.mx = initialValue
      node
    else
      val node = Node(l, r)
      val m = (l + r) / 2
      node.left = Some(build(l, m))
      node.right = Some(build(m + 1, r))
      node.mx = math.max(node.left.get.mx, node.right.get.mx)
      node.total = node.left.get.total + node.right.get.total
      node
  
  def update(index: Int, value: Int): Unit =
    def updateHelper(node: Node): Unit =
      if node.s == node.e && node.s == index then
        node.total -= value
        node.mx -= value
        return
      
      val m = (node.s + node.e) / 2
      if index <= m then
        updateHelper(node.left.get)
      else if index > m then
        updateHelper(node.right.get)
        
      node.mx = math.max(node.left.get.mx, node.right.get.mx)
      node.total = node.left.get.total + node.right.get.total
      
    updateHelper(root)
  
  def maxQuery(k: Int, maxRow: Int, seats: Int): List[Int] =
    def queryHelper(node: Node): List[Int] =
      if node.s == node.e then
        if node.e > maxRow || node.total < k then
          List.empty
        else if node.e <= maxRow && node.total >= k then
          List(node.e, seats.toInt - node.total.toInt)
        else
          List.empty
      else if node.left.get.mx >= k then
        queryHelper(node.left.get)
      else
        queryHelper(node.right.get)
    
    queryHelper(root)
  
  def sumQuery(endRow: Int): Long =
    def queryHelper(node: Node, left: Int, right: Int): Long =
      if left <= node.s && node.e <= right then
        node.total
      else
        val m = (node.s + node.e) / 2
        if right <= m then
          queryHelper(node.left.get, left, right)
        else if left > m then
          queryHelper(node.right.get, left, right)
        else
          queryHelper(node.left.get, left, m) + queryHelper(node.right.get, m + 1, right)
    
    queryHelper(root, 0, endRow)

class BookMyShow(n: Int, m: Int):
  private val seg = SegTree(0, n - 1, m)
  private val seats = Array.fill(n.toInt)(m.toInt)
  private var startRow = 0
  
  def gather(k: Int, maxRow: Int): List[Int] =
    val res = seg.maxQuery(k, maxRow, m)
    if res.nonEmpty then
      val row = res.head
      seg.update(row, k)
      seats(row) -= k
    res

  def scatter(k: Int, maxRow: Int): Boolean =
    if seg.sumQuery(maxRow) < k.toLong then
      false
    else
      var i = startRow
      var total = 0
      while total < k do
        val prevTotal = total
        total += seats(i)
        if total < k then
          seg.update(i, seats(i))
          seats(i) = 0
          i += 1
          startRow = i
        else
          seg.update(i, k - prevTotal)
          seats(i) -= k - prevTotal
      true
