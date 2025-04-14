import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

class BookMyShowSpec extends AnyFlatSpec with should.Matchers {
  "BookMyShow.gather" should "return row and position when seats are available" in {
    val bms = new BookMyShow(3, 5)
    val result = bms.gather(3, 2)
    result should be (List(0, 0))
  }

  it should "return empty list when seats are not available in a single row" in {
    val bms = new BookMyShow(2, 4)
    bms.gather(4, 0)
    val result = bms.gather(5, 1)
    result should be (List.empty)
  }

  it should "return seats from lowest available row" in {
    val bms = new BookMyShow(3, 5)
    bms.gather(5, 2)
    val result = bms.gather(3, 2)
    result should be (List(1, 0))
  }

  "BookMyShow.scatter" should "return true when enough seats are available across rows" in {
    val bms = new BookMyShow(3, 4)
    val result = bms.scatter(10, 2)
    result should be (true)
  }

  it should "return false when not enough seats are available" in {
    val bms = new BookMyShow(2, 5)
    val result = bms.scatter(11, 1)
    result should be (false)
  }

  "BookMyShow" should "properly track remaining seats after operations" in {
    val bms = new BookMyShow(2, 5)
    bms.gather(4, 0)
    bms.gather(2, 0)
    bms.scatter(5, 1)
    val result = bms.scatter(5, 1)
    result should be (false)
  }
  
  "BookMyShow" should "properly track remaining seats after more complex operations" in {
    val bms = new BookMyShow(3,999999999)
    val result = bms.scatter(1000000000,2)
    result should be (true)
  }

  it should "handle interleaved gather and scatter operations" in {
    val bms = new BookMyShow(5, 9)
    bms.gather(5, 4)
    bms.scatter(10, 1)
    val result = bms.gather(5, 4)
    result should be (List(2, 0))
  }
}