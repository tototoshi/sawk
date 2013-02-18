package sawk

protected abstract class BaseSawkScript(it: Iterator[Seq[String]]) {

  private var _main: () => Unit = () => ()
  private var _begin: () => Unit = () => ()
  private var _end: () => Unit = () => ()

  var $0: Seq[String] = Nil
  var $1: String = ""
  var $2: String = ""
  var $3: String = ""
  var $4: String = ""
  var $5: String = ""
  var $6: String = ""
  var $7: String = ""
  var $8: String = ""
  var $9: String = ""
  var $10: String = ""

  def BEGIN(f: => Unit): Unit = {
    _begin = () => f
  }

  def MAIN(f: => Unit): Unit = {
    _main = () => f
  }

  def run(): Unit = {
    _begin.apply

    it.foreach { fields =>
      $0 = fields
      $1 = fields.lift(0).getOrElse("")
      $2 = fields.lift(1).getOrElse("")
      $3 = fields.lift(2).getOrElse("")
      $4 = fields.lift(3).getOrElse("")
      $5 = fields.lift(4).getOrElse("")
      $6 = fields.lift(5).getOrElse("")
      $7 = fields.lift(6).getOrElse("")
      $8 = fields.lift(7).getOrElse("")
      $9 = fields.lift(8).getOrElse("")
      $10 = fields.lift(9).getOrElse("")
      _main.apply
    }

    _end.apply
  }

  def END(f: => Unit): Unit = {
    _end = () => f
  }

}
