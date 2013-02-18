package sawk

import java.io.{ File, PrintWriter, FileWriter }
import scala.collection.mutable.Map
import scala.io.Source
import org.scalatest.FunSpec
import org.scalatest.matchers._

class SawkScriptSpec extends FunSpec with ShouldMatchers with Using {

  val tmpfile = File.createTempFile("sawk", "txt", new File("/tmp"))
  tmpfile.deleteOnExit()

  // awk スクリプト

  class CountUpScript(lines: Iterator[String]) extends SawkScript(lines) {

    var data: Map[String, Int] = Map.empty.withDefaultValue(0)

    BEGIN {
      using(new PrintWriter(new FileWriter(tmpfile))) { writer =>
        writer.println("start")
      }
    }

    MAIN {
      data($1) += $2.toInt
    }

    END {
      using(new PrintWriter(new FileWriter(tmpfile, true))) { writer =>
        data.map { case (k, v) => k + "\t" + v }.foreach(writer.println)
      }
    }
  }

  describe("SawkScript") {

    it("works fine!") {
      val data="""|りんご	1
                  |りんご	3
                  |みかん	2
                  |りんご	4
                  |みかん	5
                  |なし	3
                  |""".stripMargin

      new CountUpScript(data.lines).run

      val expected = """|start
                        |なし	3
                        |みかん	7
                        |りんご	8
                        |""".stripMargin

      val actual = Source.fromFile(tmpfile).getLines.toList.sorted.mkString("", "\n", "\n")

      actual should be (expected)
    }

  }

}

