package sawk

import org.specs2.mutable._
import java.io.{ File, PrintWriter, FileWriter }
import scala.collection.mutable.Map
import scala.io.Source


// awk スクリプト
class CountUpScript(lines: Iterator[String]) extends SawkScript(lines) {

  var data: Map[String, Int] = Map.empty.withDefaultValue(0)

  def using[A, R <: { def close() }](r : R)(f : R => A) : A =
    try { f(r) } finally { r.close() }

  BEGIN {
    using(new PrintWriter(new FileWriter(Data.tmpfile))) { writer =>
      writer.println("start")
    }
  }

  MAIN {
    data($1) += $2.toInt
  }

  END {
    using(new PrintWriter(new FileWriter(Data.tmpfile, true))) { writer =>
      data.map { case (k, v) => k + "\t" + v }.foreach(writer.println)
    }
  }

}

object Data {
  val tmpfile = File.createTempFile("sawk", "txt", new File("/tmp"))
  tmpfile.deleteOnExit()
}


class SawkSpec extends Specification {

  "SawkSpec" should {

    "work fine!" in {

      val data="""|りんご	1
                  |りんご	3
                  |みかん	2
                  |りんご	4
                  |みかん	5
                  |なし	3
                  |""".stripMargin

      val awkScript = new CountUpScript(data.lines)

      Sawk(awkScript)

      val expected = """|start
                        |なし	3
                        |みかん	7
                        |りんご	8
                        |""".stripMargin

      val actual = Source.fromFile(Data.tmpfile).getLines.toList.sorted.mkString("", "\n", "\n")

      actual === expected

    }

  }

}
