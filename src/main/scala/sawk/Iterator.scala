package sawk

protected class TSVIterator(it: Iterator[String]) extends SeparatedValueIterator(it, "\t")

protected class SeparatedValueIterator(it: Iterator[String], FS: String) extends Iterator[Seq[String]]{
  def hasNext: Boolean = it.hasNext
  def next(): Seq[String] = it.next.split(FS)
}

class SawkScript(it: Iterator[String]) extends BaseSawkScript(new TSVIterator(it))
