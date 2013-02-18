package sawk

import java.io.{ File, PrintWriter, FileWriter }
import scala.collection.mutable.Map
import scala.io.Source
import org.scalatest.FunSpec
import org.scalatest.matchers._
import com.github.tototoshi.csv._

class CSVSawkScriptSpec extends FunSpec with ShouldMatchers with Using {

  val tmpfile = File.createTempFile("sawk", "txt", new File("/tmp"))
  tmpfile.deleteOnExit()

  class CSVCountUpScript(reader: CSVReader) extends CSVSawkScript(reader) {

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
      val writer = CSVWriter.open(tmpfile, append=true)
      data.map { case (k, v) => List(k,v) }.foreach(writer.writeRow)
      writer.close()
    }
  }

  describe("CSVSawkScript") {

    it("works fine!") {
      val data="""|りんご,1
                  |りんご,3
                  |みかん,2
                  |りんご,4
                  |みかん,5
                  |なし,3""".stripMargin

      val testCSV = File.createTempFile("sawk", "txt", new File("/tmp"))
      testCSV.deleteOnExit()

      using(new java.io.PrintWriter(testCSV)) { writer =>
        writer.println(data)
      }

      using (CSVReader.open(testCSV)) { reader =>
        new CSVCountUpScript(reader).run
      }

      val expected = """|"なし","3"
                        |"みかん","7"
                        |"りんご","8"
                        |start
                        |""".stripMargin

      val actual = Source.fromFile(tmpfile).getLines.toList.sorted.mkString("", "\n", "\n")

      actual should be (expected)
    }

  }
}

