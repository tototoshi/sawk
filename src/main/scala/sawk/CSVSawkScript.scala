package sawk

import com.github.tototoshi.csv.CSVReader

class CSVSawkScript(reader: CSVReader) extends BaseSawkScript(new CSVIterator(reader))

protected class CSVIterator(reader: CSVReader) extends Iterator[Seq[String]]{
  private var _next: Option[Seq[String]] = None
  def hasNext: Boolean = {
    if (_next.isDefined) {
      true
    } else {
      _next = reader.readNext
      _next match {
        case Some(fields) => true
        case None => false
      }
    }
  }
  def next(): Seq[String] = {
    _next match {
      case Some(row) => {
        val _row = row
        _next = None
        _row
      }
      case None => reader.readNext.get
    }
  }
}

